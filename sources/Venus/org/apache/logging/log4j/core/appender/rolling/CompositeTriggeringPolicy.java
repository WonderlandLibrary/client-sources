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

    private CompositeTriggeringPolicy(TriggeringPolicy ... triggeringPolicyArray) {
        this.triggeringPolicies = triggeringPolicyArray;
    }

    public TriggeringPolicy[] getTriggeringPolicies() {
        return this.triggeringPolicies;
    }

    @Override
    public void initialize(RollingFileManager rollingFileManager) {
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            triggeringPolicy.initialize(rollingFileManager);
        }
    }

    @Override
    public boolean isTriggeringEvent(LogEvent logEvent) {
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            if (!triggeringPolicy.isTriggeringEvent(logEvent)) continue;
            return false;
        }
        return true;
    }

    @PluginFactory
    public static CompositeTriggeringPolicy createPolicy(@PluginElement(value="Policies") TriggeringPolicy ... triggeringPolicyArray) {
        return new CompositeTriggeringPolicy(triggeringPolicyArray);
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = true;
        for (TriggeringPolicy triggeringPolicy : this.triggeringPolicies) {
            if (triggeringPolicy instanceof LifeCycle2) {
                bl &= ((LifeCycle2)((Object)triggeringPolicy)).stop(l, timeUnit);
                continue;
            }
            if (!(triggeringPolicy instanceof LifeCycle)) continue;
            ((LifeCycle)((Object)triggeringPolicy)).stop();
            bl &= true;
        }
        this.setStopped();
        return bl;
    }

    public String toString() {
        return "CompositeTriggeringPolicy(policies=" + Arrays.toString(this.triggeringPolicies) + ")";
    }
}

