/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakHint;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentMap;

public class ResourceLeakDetector<T> {
    private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
    private static final String PROP_LEVEL = "io.netty.leakDetection.level";
    private static final Level DEFAULT_LEVEL;
    private static final String PROP_MAX_RECORDS = "io.netty.leakDetection.maxRecords";
    private static final int DEFAULT_MAX_RECORDS = 4;
    private static final int MAX_RECORDS;
    private static Level level;
    private static final InternalLogger logger;
    static final int DEFAULT_SAMPLING_INTERVAL = 128;
    private final ConcurrentMap<DefaultResourceLeak, LeakEntry> allLeaks = PlatformDependent.newConcurrentHashMap();
    private final ReferenceQueue<Object> refQueue = new ReferenceQueue();
    private final ConcurrentMap<String, Boolean> reportedLeaks = PlatformDependent.newConcurrentHashMap();
    private final String resourceType;
    private final int samplingInterval;
    private static final String[] STACK_TRACE_ELEMENT_EXCLUSIONS;

    @Deprecated
    public static void setEnabled(boolean enabled) {
        ResourceLeakDetector.setLevel(enabled ? Level.SIMPLE : Level.DISABLED);
    }

    public static boolean isEnabled() {
        return ResourceLeakDetector.getLevel().ordinal() > Level.DISABLED.ordinal();
    }

    public static void setLevel(Level level) {
        if (level == null) {
            throw new NullPointerException("level");
        }
        ResourceLeakDetector.level = level;
    }

    public static Level getLevel() {
        return level;
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> resourceType) {
        this(StringUtil.simpleClassName(resourceType));
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType) {
        this(resourceType, 128, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> resourceType, int samplingInterval, long maxActive) {
        this(resourceType, samplingInterval);
    }

    public ResourceLeakDetector(Class<?> resourceType, int samplingInterval) {
        this(StringUtil.simpleClassName(resourceType), samplingInterval, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(String resourceType, int samplingInterval, long maxActive) {
        if (resourceType == null) {
            throw new NullPointerException("resourceType");
        }
        this.resourceType = resourceType;
        this.samplingInterval = samplingInterval;
    }

    @Deprecated
    public final ResourceLeak open(T obj) {
        return this.track0(obj);
    }

    public final ResourceLeakTracker<T> track(T obj) {
        return this.track0(obj);
    }

    private DefaultResourceLeak track0(T obj) {
        Level level = ResourceLeakDetector.level;
        if (level == Level.DISABLED) {
            return null;
        }
        if (level.ordinal() < Level.PARANOID.ordinal()) {
            if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) == 0) {
                this.reportLeak(level);
                return new DefaultResourceLeak(obj);
            }
            return null;
        }
        this.reportLeak(level);
        return new DefaultResourceLeak(obj);
    }

    private void reportLeak(Level level) {
        DefaultResourceLeak ref;
        if (!logger.isErrorEnabled()) {
            DefaultResourceLeak ref2;
            while ((ref2 = (DefaultResourceLeak)this.refQueue.poll()) != null) {
                ref2.close();
            }
            return;
        }
        while ((ref = (DefaultResourceLeak)this.refQueue.poll()) != null) {
            String records;
            ref.clear();
            if (!ref.close() || this.reportedLeaks.putIfAbsent(records = ref.toString(), Boolean.TRUE) != null) continue;
            if (records.isEmpty()) {
                this.reportUntracedLeak(this.resourceType);
                continue;
            }
            this.reportTracedLeak(this.resourceType, records);
        }
    }

    protected void reportTracedLeak(String resourceType, String records) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", (Object)resourceType, (Object)records);
    }

    protected void reportUntracedLeak(String resourceType) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", resourceType, PROP_LEVEL, Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this));
    }

    @Deprecated
    protected void reportInstancesLeak(String resourceType) {
    }

    static String newRecord(Object hint, int recordsToSkip) {
        StackTraceElement[] array;
        StringBuilder buf = new StringBuilder(4096);
        if (hint != null) {
            buf.append("\tHint: ");
            if (hint instanceof ResourceLeakHint) {
                buf.append(((ResourceLeakHint)hint).toHintString());
            } else {
                buf.append(hint);
            }
            buf.append(StringUtil.NEWLINE);
        }
        for (StackTraceElement e : array = new Throwable().getStackTrace()) {
            if (recordsToSkip > 0) {
                --recordsToSkip;
                continue;
            }
            String estr = e.toString();
            boolean excluded = false;
            for (String exclusion : STACK_TRACE_ELEMENT_EXCLUSIONS) {
                if (!estr.startsWith(exclusion)) continue;
                excluded = true;
                break;
            }
            if (excluded) continue;
            buf.append('\t');
            buf.append(estr);
            buf.append(StringUtil.NEWLINE);
        }
        return buf.toString();
    }

    static {
        boolean disabled;
        DEFAULT_LEVEL = Level.SIMPLE;
        logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            disabled = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            logger.debug("-Dio.netty.noResourceLeakDetection: {}", (Object)disabled);
            logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", (Object)PROP_LEVEL, (Object)DEFAULT_LEVEL.name().toLowerCase());
        } else {
            disabled = false;
        }
        Level defaultLevel = disabled ? Level.DISABLED : DEFAULT_LEVEL;
        String levelStr = SystemPropertyUtil.get(PROP_LEVEL_OLD, defaultLevel.name());
        levelStr = SystemPropertyUtil.get(PROP_LEVEL, levelStr);
        Level level = Level.parseLevel(levelStr);
        MAX_RECORDS = SystemPropertyUtil.getInt(PROP_MAX_RECORDS, 4);
        ResourceLeakDetector.level = level;
        if (logger.isDebugEnabled()) {
            logger.debug("-D{}: {}", (Object)PROP_LEVEL, (Object)level.name().toLowerCase());
            logger.debug("-D{}: {}", (Object)PROP_MAX_RECORDS, (Object)MAX_RECORDS);
        }
        STACK_TRACE_ELEMENT_EXCLUSIONS = new String[]{"io.netty.util.ReferenceCountUtil.touch(", "io.netty.buffer.AdvancedLeakAwareByteBuf.touch(", "io.netty.buffer.AbstractByteBufAllocator.toLeakAwareBuffer(", "io.netty.buffer.AdvancedLeakAwareByteBuf.recordLeakNonRefCountingOperation("};
    }

    private static final class LeakEntry {
        static final LeakEntry INSTANCE = new LeakEntry();
        private static final int HASH = System.identityHashCode(INSTANCE);

        private LeakEntry() {
        }

        public int hashCode() {
            return HASH;
        }

        public boolean equals(Object obj) {
            return obj == this;
        }
    }

    private final class DefaultResourceLeak
    extends PhantomReference<Object>
    implements ResourceLeakTracker<T>,
    ResourceLeak {
        private final String creationRecord;
        private final Deque<String> lastRecords;
        private final int trackedHash;
        private int removedRecords;

        DefaultResourceLeak(Object referent) {
            super(referent, ResourceLeakDetector.this.refQueue);
            this.lastRecords = new ArrayDeque<String>();
            assert (referent != null);
            this.trackedHash = System.identityHashCode(referent);
            Level level = ResourceLeakDetector.getLevel();
            this.creationRecord = level.ordinal() >= Level.ADVANCED.ordinal() ? ResourceLeakDetector.newRecord(null, 3) : null;
            ResourceLeakDetector.this.allLeaks.put(this, LeakEntry.INSTANCE);
        }

        @Override
        public void record() {
            this.record0(null, 3);
        }

        @Override
        public void record(Object hint) {
            this.record0(hint, 3);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void record0(Object hint, int recordsToSkip) {
            if (this.creationRecord != null) {
                String value = ResourceLeakDetector.newRecord(hint, recordsToSkip);
                Deque<String> deque = this.lastRecords;
                synchronized (deque) {
                    int size = this.lastRecords.size();
                    if (size == 0 || !this.lastRecords.getLast().equals(value)) {
                        this.lastRecords.add(value);
                    }
                    if (size > MAX_RECORDS) {
                        this.lastRecords.removeFirst();
                        ++this.removedRecords;
                    }
                }
            }
        }

        @Override
        public boolean close() {
            return ResourceLeakDetector.this.allLeaks.remove(this, LeakEntry.INSTANCE);
        }

        @Override
        public boolean close(T trackedObject) {
            assert (this.trackedHash == System.identityHashCode(trackedObject));
            return this.close() && trackedObject != null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public String toString() {
            int removedRecords;
            Object[] array;
            if (this.creationRecord == null) {
                return "";
            }
            Deque<String> deque = this.lastRecords;
            synchronized (deque) {
                array = this.lastRecords.toArray();
                removedRecords = this.removedRecords;
            }
            StringBuilder buf = new StringBuilder(16384).append(StringUtil.NEWLINE);
            if (removedRecords > 0) {
                buf.append("WARNING: ").append(removedRecords).append(" leak records were discarded because the leak record count is limited to ").append(MAX_RECORDS).append(". Use system property ").append(ResourceLeakDetector.PROP_MAX_RECORDS).append(" to increase the limit.").append(StringUtil.NEWLINE);
            }
            buf.append("Recent access records: ").append(array.length).append(StringUtil.NEWLINE);
            if (array.length > 0) {
                for (int i = array.length - 1; i >= 0; --i) {
                    buf.append('#').append(i + 1).append(':').append(StringUtil.NEWLINE).append(array[i]);
                }
            }
            buf.append("Created at:").append(StringUtil.NEWLINE).append(this.creationRecord);
            buf.setLength(buf.length() - StringUtil.NEWLINE.length());
            return buf.toString();
        }
    }

    public static enum Level {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID;


        static Level parseLevel(String levelStr) {
            String trimmedLevelStr = levelStr.trim();
            for (Level l : Level.values()) {
                if (!trimmedLevelStr.equalsIgnoreCase(l.name()) && !trimmedLevelStr.equals(String.valueOf(l.ordinal()))) continue;
                return l;
            }
            return DEFAULT_LEVEL;
        }
    }
}

