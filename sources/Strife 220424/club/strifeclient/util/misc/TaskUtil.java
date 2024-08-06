package club.strifeclient.util.misc;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class TaskUtil extends MinecraftUtil {
    @Getter
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Getter
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    private static final List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();
}
