/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Bootstrap;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerHangWatchdog
implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DedicatedServer server;
    private final long maxTickTime;

    public ServerHangWatchdog(DedicatedServer dedicatedServer) {
        this.server = dedicatedServer;
        this.maxTickTime = dedicatedServer.getMaxTickTime();
    }

    @Override
    public void run() {
        while (this.server.isServerRunning()) {
            long l = this.server.getServerTime();
            long l2 = Util.milliTime();
            long l3 = l2 - l;
            if (l3 > this.maxTickTime) {
                LOGGER.fatal("A single server tick took {} seconds (should be max {})", (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf((float)l3 / 1000.0f)), (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf(0.05f)));
                LOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                ThreadInfo[] threadInfoArray = threadMXBean.dumpAllThreads(true, true);
                StringBuilder stringBuilder = new StringBuilder();
                Error error2 = new Error("Watchdog");
                for (ThreadInfo threadInfo : threadInfoArray) {
                    if (threadInfo.getThreadId() == this.server.getExecutionThread().getId()) {
                        error2.setStackTrace(threadInfo.getStackTrace());
                    }
                    stringBuilder.append(threadInfo);
                    stringBuilder.append("\n");
                }
                CrashReport crashReport = new CrashReport("Watching Server", error2);
                this.server.addServerInfoToCrashReport(crashReport);
                CrashReportCategory crashReportCategory = crashReport.makeCategory("Thread Dump");
                crashReportCategory.addDetail("Threads", stringBuilder);
                CrashReportCategory crashReportCategory2 = crashReport.makeCategory("Performance stats");
                crashReportCategory2.addDetail("Random tick rate", this::lambda$run$0);
                crashReportCategory2.addDetail("Level stats", this::lambda$run$2);
                Bootstrap.printToSYSOUT("Crash report:\n" + crashReport.getCompleteReport());
                File object2 = new File(new File(this.server.getDataDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
                if (crashReport.saveToFile(object2)) {
                    LOGGER.error("This crash report has been saved to: {}", (Object)object2.getAbsolutePath());
                } else {
                    LOGGER.error("We were unable to save this crash report to disk.");
                }
                this.scheduleHalt();
            }
            try {
                Thread.sleep(l + this.maxTickTime - l2);
            } catch (InterruptedException interruptedException) {}
        }
    }

    private void scheduleHalt() {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask(this){
                final ServerHangWatchdog this$0;
                {
                    this.this$0 = serverHangWatchdog;
                }

                @Override
                public void run() {
                    Runtime.getRuntime().halt(1);
                }
            }, 10000L);
            System.exit(1);
        } catch (Throwable throwable) {
            Runtime.getRuntime().halt(1);
        }
    }

    private String lambda$run$2() throws Exception {
        return Streams.stream(this.server.getWorlds()).map(ServerHangWatchdog::lambda$run$1).collect(Collectors.joining(",\n"));
    }

    private static String lambda$run$1(ServerWorld serverWorld) {
        return serverWorld.getDimensionKey() + ": " + serverWorld.func_244521_F();
    }

    private String lambda$run$0() throws Exception {
        return this.server.func_240793_aU_().getGameRulesInstance().get(GameRules.RANDOM_TICK_SPEED).toString();
    }
}

