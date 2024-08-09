/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.ThreadInformation;
import org.apache.logging.log4j.util.StringBuilders;

class BasicThreadInformation
implements ThreadInformation {
    private static final int HASH_SHIFT = 32;
    private static final int HASH_MULTIPLIER = 31;
    private final long id;
    private final String name;
    private final String longName;
    private final Thread.State state;
    private final int priority;
    private final boolean isAlive;
    private final boolean isDaemon;
    private final String threadGroupName;

    BasicThreadInformation(Thread thread2) {
        this.id = thread2.getId();
        this.name = thread2.getName();
        this.longName = thread2.toString();
        this.state = thread2.getState();
        this.priority = thread2.getPriority();
        this.isAlive = thread2.isAlive();
        this.isDaemon = thread2.isDaemon();
        ThreadGroup threadGroup = thread2.getThreadGroup();
        this.threadGroupName = threadGroup == null ? null : threadGroup.getName();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        BasicThreadInformation basicThreadInformation = (BasicThreadInformation)object;
        if (this.id != basicThreadInformation.id) {
            return true;
        }
        return this.name != null ? !this.name.equals(basicThreadInformation.name) : basicThreadInformation.name != null;
    }

    public int hashCode() {
        int n = (int)(this.id ^ this.id >>> 32);
        n = 31 * n + (this.name != null ? this.name.hashCode() : 0);
        return n;
    }

    @Override
    public void printThreadInfo(StringBuilder stringBuilder) {
        StringBuilders.appendDqValue(stringBuilder, this.name).append(' ');
        if (this.isDaemon) {
            stringBuilder.append("daemon ");
        }
        stringBuilder.append("prio=").append(this.priority).append(" tid=").append(this.id).append(' ');
        if (this.threadGroupName != null) {
            StringBuilders.appendKeyDqValue(stringBuilder, "group", this.threadGroupName);
        }
        stringBuilder.append('\n');
        stringBuilder.append("\tThread state: ").append(this.state.name()).append('\n');
    }

    @Override
    public void printStack(StringBuilder stringBuilder, StackTraceElement[] stackTraceElementArray) {
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            stringBuilder.append("\tat ").append(stackTraceElement).append('\n');
        }
    }
}

