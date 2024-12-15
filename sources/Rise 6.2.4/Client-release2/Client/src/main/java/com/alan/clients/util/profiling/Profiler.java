package com.alan.clients.util.profiling;

import lombok.Getter;

public class Profiler {
    private long start;
    @Getter
    private long total;
    @Getter
    private final String name;

    public Profiler(String name) {
        this.start();
        this.name = name;
    }

    public void start() {
        start = System.nanoTime();
    }

    public void pause() {
        total += System.nanoTime() - start;
    }

    public void reset() {
        total = 0;
    }
}
