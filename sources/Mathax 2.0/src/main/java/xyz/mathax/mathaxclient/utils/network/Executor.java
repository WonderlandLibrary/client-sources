package xyz.mathax.mathaxclient.utils.network;


import xyz.mathax.mathaxclient.init.PreInit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    public static ExecutorService executor;

    @PreInit
    public static void init() {
        executor = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable task) {
        executor.execute(task);
    }
}
