/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name="Queue", category="Core", elementType="appender", printObject=true)
public class QueueLogAppender
extends AbstractAppender {
    private static final int MAX_CAPACITY = 250;
    private static final Map<String, BlockingQueue<String>> QUEUES = new HashMap<String, BlockingQueue<String>>();
    private static final ReadWriteLock QUEUE_LOCK = new ReentrantReadWriteLock();
    private final BlockingQueue<String> queue;

    public QueueLogAppender(String string, Filter filter, Layout<? extends Serializable> layout, boolean bl, BlockingQueue<String> blockingQueue) {
        super(string, filter, layout, bl);
        this.queue = blockingQueue;
    }

    @Override
    public void append(LogEvent logEvent) {
        if (this.queue.size() >= 250) {
            this.queue.clear();
        }
        this.queue.add(this.getLayout().toSerializable(logEvent).toString());
    }

    @PluginFactory
    public static QueueLogAppender createAppender(@PluginAttribute(value="name") String string, @PluginAttribute(value="ignoreExceptions") String string2, @PluginElement(value="Layout") Layout<? extends Serializable> patternLayout, @PluginElement(value="Filters") Filter filter, @PluginAttribute(value="target") String string3) {
        boolean bl = Boolean.parseBoolean(string2);
        if (string == null) {
            LOGGER.error("No name provided for QueueLogAppender");
            return null;
        }
        if (string3 == null) {
            string3 = string;
        }
        QUEUE_LOCK.writeLock().lock();
        BlockingQueue<String> blockingQueue = QUEUES.get(string3);
        if (blockingQueue == null) {
            blockingQueue = new LinkedBlockingQueue<String>();
            QUEUES.put(string3, blockingQueue);
        }
        QUEUE_LOCK.writeLock().unlock();
        if (patternLayout == null) {
            patternLayout = PatternLayout.createLayout(null, null, null, null, null);
        }
        return new QueueLogAppender(string, filter, patternLayout, bl, blockingQueue);
    }

    public static String getNextLogEvent(String string) {
        QUEUE_LOCK.readLock().lock();
        BlockingQueue<String> blockingQueue = QUEUES.get(string);
        QUEUE_LOCK.readLock().unlock();
        if (blockingQueue != null) {
            try {
                return blockingQueue.take();
            } catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return null;
    }
}

