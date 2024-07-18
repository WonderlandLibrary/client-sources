package com.alan.clients.util.interfaces;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface ThreadAccess {
    Executor threadPool = Executors.newFixedThreadPool(Math.max(1, Math.min(2, Runtime.getRuntime().availableProcessors() - 1)));
}
