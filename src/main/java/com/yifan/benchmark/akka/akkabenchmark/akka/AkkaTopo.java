package com.yifan.benchmark.akka.akkabenchmark.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.yifan.benchmark.akka.akkabenchmark.msg.Msg;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AkkaTopo implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(AkkaTopo.class);
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private ActorSystem system;

    private ActorRef stage1;
    private ActorRef stage2;
    private ActorRef stage3;
    private ActorRef stage4;

    private Map<Long, Boolean> taskSet = new ConcurrentHashMap<>();

    @Override public void afterPropertiesSet() throws Exception {
        this.system = ActorSystem.create("demo-akka-system");

        this.stage1 = this.system.actorOf(Props.create(Stage1Actor.class, this),"stage1Actor");
        this.stage2 = this.system.actorOf(Props.create(Stage2Actor.class, this),"stage2Actor");
        this.stage3 = this.system.actorOf(Props.create(Stage3Actor.class, this),"stage3Actor");
        this.stage4 = this.system.actorOf(Props.create(Stage4Actor.class, this),"stage4Actor");

    }

    public void callback(Msg msg){
        switch (msg.getStage()){
            case 2:
                stage2.tell(msg, ActorRef.noSender());
                break;
            case 3:
                stage3.tell(msg, ActorRef.noSender());
                break;
            case 4:
                stage4.tell(msg, ActorRef.noSender());
                break;
            case 0:
                done(msg);
                break;
        }
    }

    public void addTask(long cnt){
        for(long i = 0; i< cnt; i++){
            taskSet.put(i, true);
        }
    }

    public void launch(){
        logger.warn("[AP] SUBMIT BEGIN AT {}", formatter.print(System.currentTimeMillis()));

        for(long each : taskSet.keySet()){
            Msg msg = new Msg();
            msg.setId(each);
            msg.setStage(1);

            stage1.tell(msg, ActorRef.noSender());
        }

    }

    public void done(Msg msg){
        taskSet.remove(msg.getId());
        //logger.warn("{} is done", msg.getId());
        if(taskSet.isEmpty()){
            logger.warn("[AP] FINISHED AT {}", formatter.print(System.currentTimeMillis()));
        }
    }
}
