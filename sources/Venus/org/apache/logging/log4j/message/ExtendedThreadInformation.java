/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import org.apache.logging.log4j.message.ThreadInformation;
import org.apache.logging.log4j.util.StringBuilders;

class ExtendedThreadInformation
implements ThreadInformation {
    private final ThreadInfo threadInfo;

    ExtendedThreadInformation(ThreadInfo threadInfo) {
        this.threadInfo = threadInfo;
    }

    @Override
    public void printThreadInfo(StringBuilder stringBuilder) {
        StringBuilders.appendDqValue(stringBuilder, this.threadInfo.getThreadName());
        stringBuilder.append(" Id=").append(this.threadInfo.getThreadId()).append(' ');
        this.formatState(stringBuilder, this.threadInfo);
        if (this.threadInfo.isSuspended()) {
            stringBuilder.append(" (suspended)");
        }
        if (this.threadInfo.isInNative()) {
            stringBuilder.append(" (in native)");
        }
        stringBuilder.append('\n');
    }

    @Override
    public void printStack(StringBuilder stringBuilder, StackTraceElement[] stackTraceElementArray) {
        int n = 0;
        for (StackTraceElement stackTraceElement : stackTraceElementArray) {
            stringBuilder.append("\tat ").append(stackTraceElement.toString());
            stringBuilder.append('\n');
            if (n == 0 && this.threadInfo.getLockInfo() != null) {
                Object object = this.threadInfo.getThreadState();
                switch (1.$SwitchMap$java$lang$Thread$State[((Enum)object).ordinal()]) {
                    case 1: {
                        stringBuilder.append("\t-  blocked on ");
                        this.formatLock(stringBuilder, this.threadInfo.getLockInfo());
                        stringBuilder.append('\n');
                        break;
                    }
                    case 2: {
                        stringBuilder.append("\t-  waiting on ");
                        this.formatLock(stringBuilder, this.threadInfo.getLockInfo());
                        stringBuilder.append('\n');
                        break;
                    }
                    case 3: {
                        stringBuilder.append("\t-  waiting on ");
                        this.formatLock(stringBuilder, this.threadInfo.getLockInfo());
                        stringBuilder.append('\n');
                        break;
                    }
                }
            }
            for (MonitorInfo monitorInfo : this.threadInfo.getLockedMonitors()) {
                if (monitorInfo.getLockedStackDepth() != n) continue;
                stringBuilder.append("\t-  locked ");
                this.formatLock(stringBuilder, monitorInfo);
                stringBuilder.append('\n');
            }
            ++n;
        }
        Object[] objectArray = this.threadInfo.getLockedSynchronizers();
        if (objectArray.length > 0) {
            stringBuilder.append("\n\tNumber of locked synchronizers = ").append(objectArray.length).append('\n');
            for (Object object : objectArray) {
                stringBuilder.append("\t- ");
                this.formatLock(stringBuilder, (LockInfo)object);
                stringBuilder.append('\n');
            }
        }
    }

    private void formatLock(StringBuilder stringBuilder, LockInfo lockInfo) {
        stringBuilder.append('<').append(lockInfo.getIdentityHashCode()).append("> (a ");
        stringBuilder.append(lockInfo.getClassName()).append(')');
    }

    private void formatState(StringBuilder stringBuilder, ThreadInfo threadInfo) {
        Thread.State state = threadInfo.getThreadState();
        stringBuilder.append((Object)state);
        switch (1.$SwitchMap$java$lang$Thread$State[state.ordinal()]) {
            case 1: {
                stringBuilder.append(" (on object monitor owned by \"");
                stringBuilder.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId()).append(')');
                break;
            }
            case 2: {
                StackTraceElement stackTraceElement = threadInfo.getStackTrace()[0];
                String string = stackTraceElement.getClassName();
                String string2 = stackTraceElement.getMethodName();
                if (string.equals("java.lang.Object") && string2.equals("wait")) {
                    stringBuilder.append(" (on object monitor");
                    if (threadInfo.getLockOwnerName() != null) {
                        stringBuilder.append(" owned by \"");
                        stringBuilder.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                    }
                    stringBuilder.append(')');
                    break;
                }
                if (string.equals("java.lang.Thread") && string2.equals("join")) {
                    stringBuilder.append(" (on completion of thread ").append(threadInfo.getLockOwnerId()).append(')');
                    break;
                }
                stringBuilder.append(" (parking for lock");
                if (threadInfo.getLockOwnerName() != null) {
                    stringBuilder.append(" owned by \"");
                    stringBuilder.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                }
                stringBuilder.append(')');
                break;
            }
            case 3: {
                StackTraceElement stackTraceElement = threadInfo.getStackTrace()[0];
                String string = stackTraceElement.getClassName();
                String string3 = stackTraceElement.getMethodName();
                if (string.equals("java.lang.Object") && string3.equals("wait")) {
                    stringBuilder.append(" (on object monitor");
                    if (threadInfo.getLockOwnerName() != null) {
                        stringBuilder.append(" owned by \"");
                        stringBuilder.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                    }
                    stringBuilder.append(')');
                    break;
                }
                if (string.equals("java.lang.Thread") && string3.equals("sleep")) {
                    stringBuilder.append(" (sleeping)");
                    break;
                }
                if (string.equals("java.lang.Thread") && string3.equals("join")) {
                    stringBuilder.append(" (on completion of thread ").append(threadInfo.getLockOwnerId()).append(')');
                    break;
                }
                stringBuilder.append(" (parking for lock");
                if (threadInfo.getLockOwnerName() != null) {
                    stringBuilder.append(" owned by \"");
                    stringBuilder.append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
                }
                stringBuilder.append(')');
                break;
            }
        }
    }

    static class 1 {
        static final int[] $SwitchMap$java$lang$Thread$State = new int[Thread.State.values().length];

        static {
            try {
                1.$SwitchMap$java$lang$Thread$State[Thread.State.BLOCKED.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$lang$Thread$State[Thread.State.WAITING.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$lang$Thread$State[Thread.State.TIMED_WAITING.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

