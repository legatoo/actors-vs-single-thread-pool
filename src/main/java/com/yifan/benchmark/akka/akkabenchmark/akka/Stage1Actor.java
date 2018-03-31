package com.yifan.benchmark.akka.akkabenchmark.akka;

import akka.actor.AbstractActor;
import com.yifan.benchmark.akka.akkabenchmark.msg.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Stage1Actor extends AbstractActor{
    private static final Logger logger = LoggerFactory.getLogger(Stage1Actor.class);

    private AkkaTopo topo;

    public Stage1Actor(AkkaTopo topo) {
        this.topo = topo;
    }

    @Override public Receive createReceive() {
        return receiveBuilder().match(Msg.class, o -> {
            Path path = Paths.get("tmp/io_" + o.getId() + ".io");

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write("Hello World #" + o.getId());
            }

            TimeUnit.MILLISECONDS.sleep(20);


            o.setStage(2);
            topo.callback(o);

        }).build();
    }
}
