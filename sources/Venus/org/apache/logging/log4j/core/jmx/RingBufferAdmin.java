/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.RingBuffer
 */
package org.apache.logging.log4j.core.jmx;

import com.lmax.disruptor.RingBuffer;
import javax.management.ObjectName;
import org.apache.logging.log4j.core.jmx.RingBufferAdminMBean;
import org.apache.logging.log4j.core.jmx.Server;

public class RingBufferAdmin
implements RingBufferAdminMBean {
    private final RingBuffer<?> ringBuffer;
    private final ObjectName objectName;

    public static RingBufferAdmin forAsyncLogger(RingBuffer<?> ringBuffer, String string) {
        String string2 = Server.escape(string);
        String string3 = String.format("org.apache.logging.log4j2:type=%s,component=AsyncLoggerRingBuffer", string2);
        return new RingBufferAdmin(ringBuffer, string3);
    }

    public static RingBufferAdmin forAsyncLoggerConfig(RingBuffer<?> ringBuffer, String string, String string2) {
        String string3 = Server.escape(string);
        String string4 = Server.escape(string2);
        String string5 = String.format("org.apache.logging.log4j2:type=%s,component=Loggers,name=%s,subtype=RingBuffer", string3, string4);
        return new RingBufferAdmin(ringBuffer, string5);
    }

    protected RingBufferAdmin(RingBuffer<?> ringBuffer, String string) {
        this.ringBuffer = ringBuffer;
        try {
            this.objectName = new ObjectName(string);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public long getBufferSize() {
        return this.ringBuffer == null ? 0L : (long)this.ringBuffer.getBufferSize();
    }

    @Override
    public long getRemainingCapacity() {
        return this.ringBuffer == null ? 0L : this.ringBuffer.remainingCapacity();
    }

    public ObjectName getObjectName() {
        return this.objectName;
    }
}

