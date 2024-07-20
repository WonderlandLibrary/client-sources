/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.routing;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.routing.RoutingAppender;

public interface PurgePolicy {
    public void purge();

    public void update(String var1, LogEvent var2);

    public void initialize(RoutingAppender var1);
}

