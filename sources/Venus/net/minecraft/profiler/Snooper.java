/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import com.google.common.collect.Maps;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import net.minecraft.profiler.ISnooperInfo;

public class Snooper {
    private final Map<String, Object> snooperStats = Maps.newHashMap();
    private final Map<String, Object> clientStats = Maps.newHashMap();
    private final String uniqueID = UUID.randomUUID().toString();
    private final URL serverUrl;
    private final ISnooperInfo playerStatsCollector;
    private final Timer timer = new Timer("Snooper Timer", true);
    private final Object syncLock = new Object();
    private final long minecraftStartTimeMilis;
    private boolean isRunning;

    public Snooper(String string, ISnooperInfo iSnooperInfo, long l) {
        try {
            this.serverUrl = new URL("http://snoop.minecraft.net/" + string + "?version=2");
        } catch (MalformedURLException malformedURLException) {
            throw new IllegalArgumentException();
        }
        this.playerStatsCollector = iSnooperInfo;
        this.minecraftStartTimeMilis = l;
    }

    public void start() {
        if (!this.isRunning) {
            // empty if block
        }
    }

    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
        this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
        this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
        this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.fillSnooper(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addClientStat(String string, Object object) {
        Object object2 = this.syncLock;
        synchronized (object2) {
            this.clientStats.put(string, object);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addStatToSnooper(String string, Object object) {
        Object object2 = this.syncLock;
        synchronized (object2) {
            this.snooperStats.put(string, object);
        }
    }

    public boolean isSnooperRunning() {
        return this.isRunning;
    }

    public void stop() {
        this.timer.cancel();
    }

    public String getUniqueID() {
        return this.uniqueID;
    }

    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
}

