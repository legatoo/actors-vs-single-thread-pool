package com.yifan.benchmark.akka.akkabenchmark.threadpool;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadPoolTopo {
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolTopo.class);
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private ExecutorService executorService = Executors.newFixedThreadPool(40);

    private Map<Long, Boolean> taskSet = new ConcurrentHashMap<>();

    public void submitTask(long taskCnt){
        for(long i = 0; i< taskCnt; i++){
            taskSet.put(i, true);
        }

        logger.warn("[TP] SUBMIT BEGIN AT {}", formatter.print(System.currentTimeMillis()));

        for(long index = 0; index < taskCnt; index++) {
            long id = index;

            BigCallable task = new BigCallable(this, index);
            executorService.submit(task);
        }
    }

    public void callback(long id){
        taskSet.remove(id);
        //logger.warn("{} is done", id);

        if(taskSet.isEmpty()){
            logger.warn("[TP] FINISHED AT {}", formatter.print(System.currentTimeMillis()));
        }
    }
}
