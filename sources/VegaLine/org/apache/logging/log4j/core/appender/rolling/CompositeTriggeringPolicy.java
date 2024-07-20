/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.AbstractTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="Policies", category="Core", printObject=true)
public final class CompositeTriggeringPolicy
extends AbstractTriggeringPolicy {
    private final TriggeringPolicy[] triggeringPolicies;

    private CompositeTriggeringPolicy(TriggeringPolicy ... triggeringPolicies) {
        this.triggeringPolicies = triggeringPolicies;
    }

    public TriggeringPolicy[] getTriggeringPolicies() {
        return this.triggeringPolicies;
    }

    @Override
    public void initialize(RollingFileManager manager) {
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            triggeringPolicy.initialize(manager);
        }
    }

    @Override
    public boolean isTriggeringEvent(LogEvent event) {
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            if (!triggeringPolicy.isTriggeringEvent(event)) continue;
            return true;
        }
        return false;
    }

    @PluginFactory
    public static CompositeTriggeringPolicy createPolicy(@PluginElement(value="Policies") TriggeringPolicy ... triggeringPolicy) {
        return new CompositeTriggeringPolicy(triggeringPolicy);
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        boolean stopped = true;
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            if (triggeringPolicy instanceof LifeCycle2) {
                stopped &= ((LifeCycle2)((Object)triggeringPolicy)).stop(timeout, timeUnit);
                continue;
            }
            if (!(triggeringPolicy instanceof LifeCycle)) continue;
            ((LifeCycle)((Object)triggeringPolicy)).stop();
            stopped &= true;
        }
        this.setStopped();
        return stopped;
    }

    public String toString() {
        return "CompositeTriggeringPolicy(policies=" + Arrays.toString(this.triggeringPolicies) + ")";
    }
}

