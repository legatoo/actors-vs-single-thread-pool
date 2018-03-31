package com.yifan.benchmark.akka.akkabenchmark.util;

public class SpinCpu {
    public static void spin(int milliseconds) {
        long sleepTime = milliseconds*1000000L; // convert to nanoseconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {}
    }
}
