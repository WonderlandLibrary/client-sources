/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.ResourceLeak;
import io.netty.util.ResourceLeakHint;
import io.netty.util.ResourceLeakTracker;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class ResourceLeakDetector<T> {
    private static final String PROP_LEVEL_OLD = "io.netty.leakDetectionLevel";
    private static final String PROP_LEVEL = "io.netty.leakDetection.level";
    private static final Level DEFAULT_LEVEL;
    private static final String PROP_TARGET_RECORDS = "io.netty.leakDetection.targetRecords";
    private static final int DEFAULT_TARGET_RECORDS = 4;
    private static final int TARGET_RECORDS;
    private static Level level;
    private static final InternalLogger logger;
    static final int DEFAULT_SAMPLING_INTERVAL = 128;
    private final ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> allLeaks = PlatformDependent.newConcurrentHashMap();
    private final ReferenceQueue<Object> refQueue = new ReferenceQueue();
    private final ConcurrentMap<String, Boolean> reportedLeaks = PlatformDependent.newConcurrentHashMap();
    private final String resourceType;
    private final int samplingInterval;
    private static final AtomicReference<String[]> excludedMethods;

    @Deprecated
    public static void setEnabled(boolean bl) {
        ResourceLeakDetector.setLevel(bl ? Level.SIMPLE : Level.DISABLED);
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
    public ResourceLeakDetector(Class<?> clazz) {
        this(StringUtil.simpleClassName(clazz));
    }

    @Deprecated
    public ResourceLeakDetector(String string) {
        this(string, 128, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(Class<?> clazz, int n, long l) {
        this(clazz, n);
    }

    public ResourceLeakDetector(Class<?> clazz, int n) {
        this(StringUtil.simpleClassName(clazz), n, Long.MAX_VALUE);
    }

    @Deprecated
    public ResourceLeakDetector(String string, int n, long l) {
        if (string == null) {
            throw new NullPointerException("resourceType");
        }
        this.resourceType = string;
        this.samplingInterval = n;
    }

    @Deprecated
    public final ResourceLeak open(T t) {
        return this.track0(t);
    }

    public final ResourceLeakTracker<T> track(T t) {
        return this.track0(t);
    }

    private DefaultResourceLeak track0(T t) {
        Level level = ResourceLeakDetector.level;
        if (level == Level.DISABLED) {
            return null;
        }
        if (level.ordinal() < Level.PARANOID.ordinal()) {
            if (PlatformDependent.threadLocalRandom().nextInt(this.samplingInterval) == 0) {
                this.reportLeak();
                return new DefaultResourceLeak(t, this.refQueue, this.allLeaks);
            }
            return null;
        }
        this.reportLeak();
        return new DefaultResourceLeak(t, this.refQueue, this.allLeaks);
    }

    private void clearRefQueue() {
        DefaultResourceLeak defaultResourceLeak;
        while ((defaultResourceLeak = (DefaultResourceLeak)this.refQueue.poll()) != null) {
            defaultResourceLeak.dispose();
        }
    }

    private void reportLeak() {
        DefaultResourceLeak defaultResourceLeak;
        if (!logger.isErrorEnabled()) {
            this.clearRefQueue();
            return;
        }
        while ((defaultResourceLeak = (DefaultResourceLeak)this.refQueue.poll()) != null) {
            String string;
            if (!defaultResourceLeak.dispose() || this.reportedLeaks.putIfAbsent(string = defaultResourceLeak.toString(), Boolean.TRUE) != null) continue;
            if (string.isEmpty()) {
                this.reportUntracedLeak(this.resourceType);
                continue;
            }
            this.reportTracedLeak(this.resourceType, string);
        }
    }

    protected void reportTracedLeak(String string, String string2) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. See http://netty.io/wiki/reference-counted-objects.html for more information.{}", (Object)string, (Object)string2);
    }

    protected void reportUntracedLeak(String string) {
        logger.error("LEAK: {}.release() was not called before it's garbage-collected. Enable advanced leak reporting to find out where the leak occurred. To enable advanced leak reporting, specify the JVM option '-D{}={}' or call {}.setLevel() See http://netty.io/wiki/reference-counted-objects.html for more information.", string, PROP_LEVEL, Level.ADVANCED.name().toLowerCase(), StringUtil.simpleClassName(this));
    }

    @Deprecated
    protected void reportInstancesLeak(String string) {
    }

    public static void addExclusions(Class clazz, String ... stringArray) {
        String[] stringArray2;
        Method method;
        int n;
        HashSet<String> hashSet = new HashSet<String>(Arrays.asList(stringArray));
        Object[] objectArray = clazz.getDeclaredMethods();
        int n2 = objectArray.length;
        for (n = 0; !(n >= n2 || hashSet.remove((method = objectArray[n]).getName()) && hashSet.isEmpty()); ++n) {
        }
        if (!hashSet.isEmpty()) {
            throw new IllegalArgumentException("Can't find '" + hashSet + "' in " + clazz.getName());
        }
        do {
            objectArray = excludedMethods.get();
            stringArray2 = (String[])Arrays.copyOf(objectArray, objectArray.length + 2 * stringArray.length);
            for (n = 0; n < stringArray.length; ++n) {
                stringArray2[objectArray.length + n * 2] = clazz.getName();
                stringArray2[objectArray.length + n * 2 + 1] = stringArray[n];
            }
        } while (!excludedMethods.compareAndSet((String[])objectArray, stringArray2));
    }

    static Level access$000() {
        return DEFAULT_LEVEL;
    }

    static int access$200() {
        return TARGET_RECORDS;
    }

    static AtomicReference access$500() {
        return excludedMethods;
    }

    static {
        boolean bl;
        DEFAULT_LEVEL = Level.SIMPLE;
        logger = InternalLoggerFactory.getInstance(ResourceLeakDetector.class);
        if (SystemPropertyUtil.get("io.netty.noResourceLeakDetection") != null) {
            bl = SystemPropertyUtil.getBoolean("io.netty.noResourceLeakDetection", false);
            logger.debug("-Dio.netty.noResourceLeakDetection: {}", (Object)bl);
            logger.warn("-Dio.netty.noResourceLeakDetection is deprecated. Use '-D{}={}' instead.", (Object)PROP_LEVEL, (Object)DEFAULT_LEVEL.name().toLowerCase());
        } else {
            bl = false;
        }
        Level level = bl ? Level.DISABLED : DEFAULT_LEVEL;
        String string = SystemPropertyUtil.get(PROP_LEVEL_OLD, level.name());
        string = SystemPropertyUtil.get(PROP_LEVEL, string);
        Level level2 = Level.parseLevel(string);
        TARGET_RECORDS = SystemPropertyUtil.getInt(PROP_TARGET_RECORDS, 4);
        ResourceLeakDetector.level = level2;
        if (logger.isDebugEnabled()) {
            logger.debug("-D{}: {}", (Object)PROP_LEVEL, (Object)level2.name().toLowerCase());
            logger.debug("-D{}: {}", (Object)PROP_TARGET_RECORDS, (Object)TARGET_RECORDS);
        }
        excludedMethods = new AtomicReference<String[]>(EmptyArrays.EMPTY_STRINGS);
    }

    private static final class LeakEntry {
        static final LeakEntry INSTANCE = new LeakEntry();
        private static final int HASH = System.identityHashCode(INSTANCE);

        private LeakEntry() {
        }

        public int hashCode() {
            return HASH;
        }

        public boolean equals(Object object) {
            return object == this;
        }
    }

    private static final class Record
    extends Throwable {
        private static final long serialVersionUID = 6065153674892850720L;
        private static final Record BOTTOM = new Record();
        private final String hintString;
        private final Record next;
        private final int pos;

        Record(Record record, Object object) {
            this.hintString = object instanceof ResourceLeakHint ? ((ResourceLeakHint)object).toHintString() : object.toString();
            this.next = record;
            this.pos = record.pos + 1;
        }

        Record(Record record) {
            this.hintString = null;
            this.next = record;
            this.pos = record.pos + 1;
        }

        private Record() {
            this.hintString = null;
            this.next = null;
            this.pos = -1;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(2048);
            if (this.hintString != null) {
                stringBuilder.append("\tHint: ").append(this.hintString).append(StringUtil.NEWLINE);
            }
            StackTraceElement[] stackTraceElementArray = this.getStackTrace();
            block0: for (int i = 3; i < stackTraceElementArray.length; ++i) {
                StackTraceElement stackTraceElement = stackTraceElementArray[i];
                String[] stringArray = (String[])ResourceLeakDetector.access$500().get();
                for (int j = 0; j < stringArray.length; j += 2) {
                    if (stringArray[j].equals(stackTraceElement.getClassName()) && stringArray[j + 1].equals(stackTraceElement.getMethodName())) continue block0;
                }
                stringBuilder.append('\t');
                stringBuilder.append(stackTraceElement.toString());
                stringBuilder.append(StringUtil.NEWLINE);
            }
            return stringBuilder.toString();
        }

        static Record access$100() {
            return BOTTOM;
        }

        static int access$300(Record record) {
            return record.pos;
        }

        static Record access$400(Record record) {
            return record.next;
        }
    }

    private static final class DefaultResourceLeak<T>
    extends WeakReference<Object>
    implements ResourceLeakTracker<T>,
    ResourceLeak {
        private static final AtomicReferenceFieldUpdater<DefaultResourceLeak<?>, Record> headUpdater;
        private static final AtomicIntegerFieldUpdater<DefaultResourceLeak<?>> droppedRecordsUpdater;
        private volatile Record head;
        private volatile int droppedRecords;
        private final ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> allLeaks;
        private final int trackedHash;
        static final boolean $assertionsDisabled;

        DefaultResourceLeak(Object object, ReferenceQueue<Object> referenceQueue, ConcurrentMap<DefaultResourceLeak<?>, LeakEntry> concurrentMap) {
            super(object, referenceQueue);
            if (!$assertionsDisabled && object == null) {
                throw new AssertionError();
            }
            this.trackedHash = System.identityHashCode(object);
            concurrentMap.put(this, LeakEntry.INSTANCE);
            headUpdater.set(this, new Record(Record.access$100()));
            this.allLeaks = concurrentMap;
        }

        @Override
        public void record() {
            this.record0(null);
        }

        @Override
        public void record(Object object) {
            this.record0(object);
        }

        private void record0(Object object) {
            if (ResourceLeakDetector.access$200() > 0) {
                boolean bl;
                Record record;
                Record record2;
                Record record3;
                do {
                    record = record3 = headUpdater.get(this);
                    if (record3 == null) {
                        return;
                    }
                    int n = Record.access$300(record3) + 1;
                    if (n >= ResourceLeakDetector.access$200()) {
                        int n2 = Math.min(n - ResourceLeakDetector.access$200(), 30);
                        bl = PlatformDependent.threadLocalRandom().nextInt(1 << n2) != 0;
                        if (!bl) continue;
                        record = Record.access$400(record3);
                        continue;
                    }
                    bl = false;
                } while (!headUpdater.compareAndSet(this, record3, record2 = object != null ? new Record(record, object) : new Record(record)));
                if (bl) {
                    droppedRecordsUpdater.incrementAndGet(this);
                }
            }
        }

        boolean dispose() {
            this.clear();
            return this.allLeaks.remove(this, LeakEntry.INSTANCE);
        }

        @Override
        public boolean close() {
            if (this.allLeaks.remove(this, LeakEntry.INSTANCE)) {
                this.clear();
                headUpdater.set(this, null);
                return false;
            }
            return true;
        }

        @Override
        public boolean close(T t) {
            if (!$assertionsDisabled && this.trackedHash != System.identityHashCode(t)) {
                throw new AssertionError();
            }
            return this.close() && t != null;
        }

        public String toString() {
            Record record = headUpdater.getAndSet(this, null);
            if (record == null) {
                return "";
            }
            int n = droppedRecordsUpdater.get(this);
            int n2 = 0;
            int n3 = Record.access$300(record) + 1;
            StringBuilder stringBuilder = new StringBuilder(n3 * 2048).append(StringUtil.NEWLINE);
            stringBuilder.append("Recent access records: ").append(StringUtil.NEWLINE);
            int n4 = 1;
            HashSet<String> hashSet = new HashSet<String>(n3);
            while (record != Record.access$100()) {
                String string = record.toString();
                if (hashSet.add(string)) {
                    if (Record.access$400(record) == Record.access$100()) {
                        stringBuilder.append("Created at:").append(StringUtil.NEWLINE).append(string);
                    } else {
                        stringBuilder.append('#').append(n4++).append(':').append(StringUtil.NEWLINE).append(string);
                    }
                } else {
                    ++n2;
                }
                record = Record.access$400(record);
            }
            if (n2 > 0) {
                stringBuilder.append(": ").append(n).append(" leak records were discarded because they were duplicates").append(StringUtil.NEWLINE);
            }
            if (n > 0) {
                stringBuilder.append(": ").append(n).append(" leak records were discarded because the leak record count is targeted to ").append(ResourceLeakDetector.access$200()).append(". Use system property ").append(ResourceLeakDetector.PROP_TARGET_RECORDS).append(" to increase the limit.").append(StringUtil.NEWLINE);
            }
            stringBuilder.setLength(stringBuilder.length() - StringUtil.NEWLINE.length());
            return stringBuilder.toString();
        }

        static {
            $assertionsDisabled = !ResourceLeakDetector.class.desiredAssertionStatus();
            headUpdater = AtomicReferenceFieldUpdater.newUpdater(DefaultResourceLeak.class, Record.class, "head");
            droppedRecordsUpdater = AtomicIntegerFieldUpdater.newUpdater(DefaultResourceLeak.class, "droppedRecords");
        }
    }

    public static enum Level {
        DISABLED,
        SIMPLE,
        ADVANCED,
        PARANOID;


        static Level parseLevel(String string) {
            String string2 = string.trim();
            for (Level level : Level.values()) {
                if (!string2.equalsIgnoreCase(level.name()) && !string2.equals(String.valueOf(level.ordinal()))) continue;
                return level;
            }
            return ResourceLeakDetector.access$000();
        }
    }
}

