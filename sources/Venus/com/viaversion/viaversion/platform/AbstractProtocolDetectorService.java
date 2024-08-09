/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.platform;

import com.viaversion.viaversion.api.platform.ProtocolDetectorService;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractProtocolDetectorService
implements ProtocolDetectorService {
    protected final Object2IntMap<String> detectedProtocolIds = new Object2IntOpenHashMap<String>();
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    protected AbstractProtocolDetectorService() {
        this.detectedProtocolIds.defaultReturnValue(-1);
    }

    @Override
    public int serverProtocolVersion(String string) {
        int n;
        this.lock.readLock().lock();
        try {
            n = this.detectedProtocolIds.getInt(string);
        } finally {
            this.lock.readLock().unlock();
        }
        if (n != -1) {
            return n;
        }
        Map<String, Integer> map = this.configuredServers();
        Integer n2 = map.get(string);
        if (n2 != null) {
            return n2;
        }
        Integer n3 = map.get("default");
        if (n3 != null) {
            return n3;
        }
        return this.lowestSupportedProtocolVersion();
    }

    @Override
    public void setProtocolVersion(String string, int n) {
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.put(string, n);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public int uncacheProtocolVersion(String string) {
        this.lock.writeLock().lock();
        try {
            int n = this.detectedProtocolIds.removeInt(string);
            return n;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Object2IntMap<String> detectedProtocolVersions() {
        this.lock.readLock().lock();
        try {
            Object2IntOpenHashMap<String> object2IntOpenHashMap = new Object2IntOpenHashMap<String>(this.detectedProtocolIds);
            return object2IntOpenHashMap;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    protected abstract Map<String, Integer> configuredServers();

    protected abstract int lowestSupportedProtocolVersion();
}

