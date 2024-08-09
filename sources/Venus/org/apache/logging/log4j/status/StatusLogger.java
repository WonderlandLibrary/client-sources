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
    private final Queue<StatusData> messages = new BoundedQueue<StatusData>(this, MAX_ENTRIES);
    private final Lock msgLock = new ReentrantLock();
    private int listenersLevel;

    private StatusLogger(String string, MessageFactory messageFactory) {
        super(string, messageFactory);
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
    public void registerListener(StatusListener statusListener) {
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.add(statusListener);
            Level level = statusListener.getStatusLevel();
            if (this.listenersLevel < level.intLevel()) {
                this.listenersLevel = level.intLevel();
            }
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeListener(StatusListener statusListener) {
        StatusLogger.closeSilently(statusListener);
        this.listenersLock.writeLock().lock();
        try {
            this.listeners.remove(statusListener);
            int n = Level.toLevel(DEFAULT_STATUS_LEVEL, Level.WARN).intLevel();
            for (StatusListener statusListener2 : this.listeners) {
                int n2 = statusListener2.getStatusLevel().intLevel();
                if (n >= n2) continue;
                n = n2;
            }
            this.listenersLevel = n;
        } finally {
            this.listenersLock.writeLock().unlock();
        }
    }

    public void updateListenerLevel(Level level) {
        if (level.intLevel() > this.listenersLevel) {
            this.listenersLevel = level.intLevel();
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
            for (StatusListener statusListener : this.listeners) {
                StatusLogger.closeSilently(statusListener);
            }
        } finally {
            this.listeners.clear();
            this.listenersLock.writeLock().unlock();
            this.clear();
        }
    }

    private static void closeSilently(Closeable closeable) {
        try {
            closeable.close();
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
    public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
        StackTraceElement stackTraceElement = null;
        if (string != null) {
            stackTraceElement = this.getStackTraceElement(string, Thread.currentThread().getStackTrace());
        }
        StatusData statusData = new StatusData(stackTraceElement, level, message, throwable, null);
        this.msgLock.lock();
        try {
            this.messages.add(statusData);
        } finally {
            this.msgLock.unlock();
        }
        if (this.listeners.size() > 0) {
            for (StatusListener statusListener : this.listeners) {
                if (!statusData.getLevel().isMoreSpecificThan(statusListener.getStatusLevel())) continue;
                statusListener.log(statusData);
            }
        } else {
            this.logger.logMessage(string, level, marker, message, throwable);
        }
    }

    private StackTraceElement getStackTraceElement(String string, StackTraceElement[] stackTraceElementArray) {
        if (string == null) {
            return null;
        }
        boolean bl = false;
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            String string2 = stackTraceElement.getClassName();
            if (bl && !string.equals(string2)) {
                return stackTraceElement;
            }
            if (string.equals(string2)) {
                bl = true;
                continue;
            }
            if (NOT_AVAIL.equals(string2)) break;
        }
        return null;
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Throwable throwable) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object ... objectArray) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, String string, Object object, Object object2, Object object3, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9, Object object10) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
        return this.isEnabled(level, marker);
    }

    @Override
    public boolean isEnabled(Level level, Marker marker) {
        if (this.listeners.size() > 0) {
            return this.listenersLevel >= level.intLevel();
        }
        return this.logger.isEnabled(level, marker);
    }

    static Queue access$000(StatusLogger statusLogger) {
        return statusLogger.messages;
    }

    private class BoundedQueue<E>
    extends ConcurrentLinkedQueue<E> {
        private static final long serialVersionUID = -3945953719763255337L;
        private final int size;
        final StatusLogger this$0;

        BoundedQueue(StatusLogger statusLogger, int n) {
            this.this$0 = statusLogger;
            this.size = n;
        }

        @Override
        public boolean add(E e) {
            super.add(e);
            while (StatusLogger.access$000(this.this$0).size() > this.size) {
                StatusLogger.access$000(this.this$0).poll();
            }
            return this.size > 0;
        }
    }
}

