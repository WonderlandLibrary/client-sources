/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.apache.logging.log4j.message.BasicThreadInformation;
import org.apache.logging.log4j.message.ExtendedThreadInformation;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ThreadInformation;
import org.apache.logging.log4j.util.StringBuilderFormattable;

@AsynchronouslyFormattable
public class ThreadDumpMessage
implements Message,
StringBuilderFormattable {
    private static final long serialVersionUID = -1103400781608841088L;
    private static final ThreadInfoFactory FACTORY;
    private volatile Map<ThreadInformation, StackTraceElement[]> threads;
    private final String title;
    private String formattedMessage;

    public ThreadDumpMessage(String string) {
        this.title = string == null ? "" : string;
        this.threads = FACTORY.createThreadInfo();
    }

    private ThreadDumpMessage(String string, String string2) {
        this.formattedMessage = string;
        this.title = string2 == null ? "" : string2;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage != null) {
            return this.formattedMessage;
        }
        StringBuilder stringBuilder = new StringBuilder(255);
        this.formatTo(stringBuilder);
        return stringBuilder.toString();
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        stringBuilder.append(this.title);
        if (this.title.length() > 0) {
            stringBuilder.append('\n');
        }
        for (Map.Entry<ThreadInformation, StackTraceElement[]> entry : this.threads.entrySet()) {
            ThreadInformation threadInformation = entry.getKey();
            threadInformation.printThreadInfo(stringBuilder);
            threadInformation.printStack(stringBuilder, entry.getValue());
            stringBuilder.append('\n');
        }
    }

    @Override
    public String getFormat() {
        return this.title == null ? "" : this.title;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    protected Object writeReplace() {
        return new ThreadDumpMessageProxy(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    static String access$200(ThreadDumpMessage threadDumpMessage) {
        return threadDumpMessage.title;
    }

    ThreadDumpMessage(String string, String string2, 1 var3_3) {
        this(string, string2);
    }

    static {
        Method[] methodArray = ThreadInfo.class.getMethods();
        boolean bl = true;
        for (Method method : methodArray) {
            if (!method.getName().equals("getLockInfo")) continue;
            bl = false;
            break;
        }
        FACTORY = bl ? new BasicThreadInfoFactory(null) : new ExtendedThreadInfoFactory(null);
    }

    static class 1 {
    }

    private static class ExtendedThreadInfoFactory
    implements ThreadInfoFactory {
        private ExtendedThreadInfoFactory() {
        }

        @Override
        public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfoArray = threadMXBean.dumpAllThreads(true, true);
            HashMap<ThreadInformation, StackTraceElement[]> hashMap = new HashMap<ThreadInformation, StackTraceElement[]>(threadInfoArray.length);
            for (ThreadInfo threadInfo : threadInfoArray) {
                hashMap.put(new ExtendedThreadInformation(threadInfo), threadInfo.getStackTrace());
            }
            return hashMap;
        }

        ExtendedThreadInfoFactory(1 var1_1) {
            this();
        }
    }

    private static class BasicThreadInfoFactory
    implements ThreadInfoFactory {
        private BasicThreadInfoFactory() {
        }

        @Override
        public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            HashMap<ThreadInformation, StackTraceElement[]> hashMap = new HashMap<ThreadInformation, StackTraceElement[]>(map.size());
            for (Map.Entry<Thread, StackTraceElement[]> entry : map.entrySet()) {
                hashMap.put(new BasicThreadInformation(entry.getKey()), entry.getValue());
            }
            return hashMap;
        }

        BasicThreadInfoFactory(1 var1_1) {
            this();
        }
    }

    private static interface ThreadInfoFactory {
        public Map<ThreadInformation, StackTraceElement[]> createThreadInfo();
    }

    private static class ThreadDumpMessageProxy
    implements Serializable {
        private static final long serialVersionUID = -3476620450287648269L;
        private final String formattedMsg;
        private final String title;

        ThreadDumpMessageProxy(ThreadDumpMessage threadDumpMessage) {
            this.formattedMsg = threadDumpMessage.getFormattedMessage();
            this.title = ThreadDumpMessage.access$200(threadDumpMessage);
        }

        protected Object readResolve() {
            return new ThreadDumpMessage(this.formattedMsg, this.title, null);
        }
    }
}

