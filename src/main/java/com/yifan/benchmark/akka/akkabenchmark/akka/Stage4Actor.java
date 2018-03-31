package com.yifan.benchmark.akka.akkabenchmark.akka;

import akka.actor.AbstractActor;
import com.yifan.benchmark.akka.akkabenchmark.msg.Msg;
import com.yifan.benchmark.akka.akkabenchmark.util.SpinCpu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Stage4Actor extends AbstractActor{
    private static final Logger logger = LoggerFactory.getLogger(Stage4Actor.class);

    private AkkaTopo topo;

    public Stage4Actor(AkkaTopo topo) {
        this.topo = topo;
    }

    @Override public Receive createReceive() {
        return receiveBuilder().match(Msg.class, o -> {

            TimeUnit.MILLISECONDS.sleep(20);
            SpinCpu.spin(50);
            o.setStage(0);
            topo.callback(o);

        }).build();
    }
}
