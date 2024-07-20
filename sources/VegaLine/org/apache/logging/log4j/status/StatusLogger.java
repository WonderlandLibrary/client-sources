/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.status;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedNoReferenceMessageFactory;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.status.StatusData;
import org.apache.logging.log4j.status.StatusListener;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class StatusLogger
extends AbstractLogger {
    public static final String MAX_STATUS_ENTRIES = "log4j2.status.entries";
    public static final String DEFAULT_STATUS_LISTENER_LEVEL = "log4j2.StatusLogger.level";
    private static final long serialVersionUID = 2L;
    private static final String NOT_AVAIL = "?";
    private static final PropertiesUtil PROPS = new PropertiesUtil("log4j2.StatusLogger.properties");
    private static final int MAX_ENTRIES = PROPS.getIntegerProperty("log4j2.status.entries", 200);
    private static final String DEFAULT_STATUS_LEVEL = PROPS.getStringProperty("log4j2.StatusLogger.level");
    private static final StatusLogger STATUS_LOGGER = new StatusLogger(StatusLogger.class.getName(), ParameterizedNoReferenceMessageFactory.INSTANCE);
    private final SimpleLogger logger;
    private final Collection<StatusListener> listeners = new CopyOnWriteArrayList<StatusListener>();
    private final ReadWriteLock listenersLock = new ReentrantReadWriteLock();
    private final Queue<StatusData> messages = new BoundedQueue<StatusData>(MAX_ENTRIES);
    private final Lock msgLock = new ReentrantLock();
    private int listenersLevel;

    private StatusLogger(String name, MessageFactory messageFactory) {
        super(name, messageFactory);
        this.logger = new SimpleLogger("StatusLogger", Level.ERROR, false, true, false, false, "", messageFactory, PROPS, System.err);
        this.listenersLevel = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
    }

    public static StatusLogger getLogger() {
        return STATUS_LOGGER;
    }

    public void setLevel(Level level) {
        this.logger.setLevel(level);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void registerListener(StatusListener listener) {
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.add(listener);
            Level lvl = listener.getStatusLevel();
            if (this.listenersLevel < lvl.intLevel()) {
                this.listenersLevel = lvl.intLevel();
            }
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeListener(StatusListener listener) {
        StatusLogger.closeSilently(listener);
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.remove(listener);
            int lowest = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
            for (StatusListener statusListener : this.listeners) {
                int level = statusListener.getStatusLevel().intLevel();
                if (lowest >= level) continue;
                lowest = level;
            }
            this.listenersLevel = lowest;
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    public void updateListenerLevel(Level status) {
        if (status.intLevel() > this.listenersLevel) {
            this.listenersLevel = status.intLevel();
        }
    }

    public Iterable<StatusListener> getListeners() {
        return this.listeners;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void reset() {
        this.listenersLock.writeLock().lock();
        try {
            for (StatusListener listener : this.listeners) {
                StatusLogger.closeSilently(listener);
            }
        } finally {
            this.listeners.clear();
            this.listenersLock.writeLock().unlock();
            this.clear();
        }
    }

    private static void closeSilently(Closeable resource) {
        try {
            resource.close();
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<StatusData> getStatusData() {
        this.msgLock.lock();
        try {
            ArrayList<StatusData> arrayList = new ArrayList<StatusData>(this.messages);
            return arrayList;
        } finally {
            this.msgLock.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        this.msgLock.lock();
        try {
            this.messages.clear();
        } finally {
            this.msgLock.unlock();
        }
    }

    @Override
    public Level getLevel() {
        return this.logger.getLevel();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void logMessage(String fqcn, Level level, Marker marker, Message msg, Throwable t) {
        StackTraceElement element = null;
        if (fqcn != null) {
            element = this.getStackTraceElement(fqcn, Thread.currentThread().getStackTrace());
        }
        StatusData data = new StatusData(element, level, msg, t, null);
        this.msgLock.lock();
        try {
            this.messages.add(data);
        } finally {
            this.msgLock.unlock();
        }
        if (this.listeners.size() > 0) {
            for (StatusListener listener : this.listeners) {
                if (!data.getLevel().isMoreSpecificThan(listener.getStatusLevel())) continue;
                listener.log(data);
            }
        } else {
            this.logger.logMessage(fqcn, level, marker, msg, t);
        }
    }

    private StackTraceElement getStackTraceElement(String fqcn, StackTraceElement[] stackTrace) {
        if (fqcn == null) {
            return null;
        }
        boolean next = false;
        for (StackTraceElement element : stackTrace) {
            String className = element.getClassName();
            if (next && !fqcn.equals(className)) {
                return element;
            }
            if (fqcn.equals(className)) {
                next = true;
                continue;
            }
            if (NOT_AVAIL.equals(className)) break;
        }
        return null;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Throwable t) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object ... params) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String message, Object p0, Object p1, Object p2, Object p3, Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence message, Throwable t) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object message, Throwable t) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable t) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker) {
        if (this.listeners.size() > 0) {
            return this.listenersLevel >= level.intLevel();
        }
        return this.logger.isEnabled(level, marker);
    }

    private class BoundedQueue<E>
    extends ConcurrentLinkedQueue<E> {
        private static final long serialVersionUID = -3945953719763255337L;
        private final int size;

        BoundedQueue(int size) {
            this.size = size;
        }

        @Override
        public boolean add(E object) {
            super.add(object);
            while (StatusLogger.this.messages.size() > this.size) {
                StatusLogger.this.messages.poll();
            }
            return this.size > 0;
        }
    }
}

