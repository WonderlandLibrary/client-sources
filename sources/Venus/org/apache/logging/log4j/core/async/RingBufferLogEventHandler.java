/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.LifecycleAware
 *  com.lmax.disruptor.Sequence
 *  com.lmax.disruptor.SequenceReportingEventHandler
 */
package org.apache.logging.log4j.core.async;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import org.apache.logging.log4j.core.async.RingBufferLogEvent;

public class RingBufferLogEventHandler
implements SequenceReportingEventHandler<RingBufferLogEvent>,
LifecycleAware {
    private static final int NOTIFY_PROGRESS_THRESHOLD = 50;
    private Sequence sequenceCallback;
    private int counter;
    private long threadId = -1L;

    public void setSequenceCallback(Sequence sequence) {
        this.sequenceCallback = sequence;
    }

    public void onEvent(RingBufferLogEvent ringBufferLogEvent, long l, boolean bl) throws Exception {
        ringBufferLogEvent.execute(bl);
        ringBufferLogEvent.clear();
        if (++this.counter > 50) {
            this.sequenceCallback.set(l);
            this.counter = 0;
        }
    }

    public long getThreadId() {
        return this.threadId;
    }

    public void onStart() {
        this.threadId = Thread.currentThread().getId();
    }

    public void onShutdown() {
    }

    public void onEvent(Object object, long l, boolean bl) throws Exception {
        this.onEvent((RingBufferLogEvent)object, l, bl);
    }
}

