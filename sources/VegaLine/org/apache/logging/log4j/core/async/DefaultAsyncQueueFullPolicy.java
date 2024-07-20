/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.EventRoute;

public class DefaultAsyncQueueFullPolicy
implements AsyncQueueFullPolicy {
    @Override
    public EventRoute getRoute(long backgroundThreadId, Level level) {
        return EventRoute.SYNCHRONOUS;
    }
}

