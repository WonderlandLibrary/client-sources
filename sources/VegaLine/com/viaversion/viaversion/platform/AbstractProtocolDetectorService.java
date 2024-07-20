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
    public int serverProtocolVersion(String serverName) {
        int detectedProtocol;
        this.lock.readLock().lock();
        try {
            detectedProtocol = this.detectedProtocolIds.getInt(serverName);
        } finally {
            this.lock.readLock().unlock();
        }
        if (detectedProtocol != -1) {
            return detectedProtocol;
        }
        Map<String, Integer> servers = this.configuredServers();
        Integer protocol = servers.get(serverName);
        if (protocol != null) {
            return protocol;
        }
        Integer defaultProtocol = servers.get("default");
        if (defaultProtocol != null) {
            return defaultProtocol;
        }
        return this.lowestSupportedProtocolVersion();
    }

    @Override
    public void setProtocolVersion(String serverName, int protocolVersion) {
        this.lock.writeLock().lock();
        try {
            this.detectedProtocolIds.put(serverName, protocolVersion);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public int uncacheProtocolVersion(String serverName) {
        this.lock.writeLock().lock();
        try {
            int n = this.detectedProtocolIds.removeInt(serverName);
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

