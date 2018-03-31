package com.yifan.benchmark.akka.akkabenchmark.threadpool;

import com.yifan.benchmark.akka.akkabenchmark.util.SpinCpu;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class BigCallable implements Runnable {
    private ThreadPoolTopo topo;
    private long id;

    public BigCallable(ThreadPoolTopo topo, long id) {
        this.topo = topo;
        this.id = id;
    }

    @Override public void run() {

        try {
            Path path = Paths.get("/home/steven/workplace/demos/akka-benchmark/tmp/io_" + id + ".io");

            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write("Hello World ~~~");
            }

            TimeUnit.MILLISECONDS.sleep(80);

            SpinCpu.spin(150);

            topo.callback(id);

        } catch (Exception e){

        }


    }
}
