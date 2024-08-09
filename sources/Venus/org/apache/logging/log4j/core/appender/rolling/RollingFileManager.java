/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LifeCycle2;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConfigurationFactoryData;
import org.apache.logging.log4j.core.appender.FileManager;
import org.apache.logging.log4j.core.appender.ManagerFactory;
import org.apache.logging.log4j.core.appender.rolling.DirectFileRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.FileExtension;
import org.apache.logging.log4j.core.appender.rolling.PatternProcessor;
import org.apache.logging.log4j.core.appender.rolling.RolloverDescription;
import org.apache.logging.log4j.core.appender.rolling.RolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.TriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.action.AbstractAction;
import org.apache.logging.log4j.core.appender.rolling.action.Action;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.util.Constants;
import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;

public class RollingFileManager
extends FileManager {
    private static RollingFileManagerFactory factory = new RollingFileManagerFactory(null);
    private static final int MAX_TRIES = 3;
    private static final int MIN_DURATION = 100;
    protected long size;
    private long initialTime;
    private final PatternProcessor patternProcessor;
    private final Semaphore semaphore = new Semaphore(1);
    private final Log4jThreadFactory threadFactory = Log4jThreadFactory.createThreadFactory("RollingFileManager");
    private volatile TriggeringPolicy triggeringPolicy;
    private volatile RolloverStrategy rolloverStrategy;
    private volatile boolean renameEmptyFiles = false;
    private volatile boolean initialized = false;
    private volatile String fileName;
    private FileExtension fileExtension;
    private ExecutorService asyncExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.MILLISECONDS, (BlockingQueue<Runnable>)new EmptyQueue(), this.threadFactory);
    private static final AtomicReferenceFieldUpdater<RollingFileManager, TriggeringPolicy> triggeringPolicyUpdater = AtomicReferenceFieldUpdater.newUpdater(RollingFileManager.class, TriggeringPolicy.class, "triggeringPolicy");
    private static final AtomicReferenceFieldUpdater<RollingFileManager, RolloverStrategy> rolloverStrategyUpdater = AtomicReferenceFieldUpdater.newUpdater(RollingFileManager.class, RolloverStrategy.class, "rolloverStrategy");

    @Deprecated
    protected RollingFileManager(String string, String string2, OutputStream outputStream, boolean bl, long l, long l2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, int n, boolean bl2) {
        this(string, string2, outputStream, bl, l, l2, triggeringPolicy, rolloverStrategy, string3, layout, bl2, ByteBuffer.wrap(new byte[Constants.ENCODER_BYTE_BUFFER_SIZE]));
    }

    @Deprecated
    protected RollingFileManager(String string, String string2, OutputStream outputStream, boolean bl, long l, long l2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, boolean bl2, ByteBuffer byteBuffer) {
        super(string, outputStream, bl, false, string3, layout, bl2, byteBuffer);
        this.size = l;
        this.initialTime = l2;
        this.triggeringPolicy = triggeringPolicy;
        this.rolloverStrategy = rolloverStrategy;
        this.patternProcessor = new PatternProcessor(string2);
        this.patternProcessor.setPrevFileTime(l2);
        this.fileName = string;
        this.fileExtension = FileExtension.lookupForFile(string2);
    }

    protected RollingFileManager(LoggerContext loggerContext, String string, String string2, OutputStream outputStream, boolean bl, boolean bl2, long l, long l2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, boolean bl3, ByteBuffer byteBuffer) {
        super(loggerContext, string, outputStream, bl, false, bl2, string3, layout, bl3, byteBuffer);
        this.size = l;
        this.initialTime = l2;
        this.triggeringPolicy = triggeringPolicy;
        this.rolloverStrategy = rolloverStrategy;
        this.patternProcessor = new PatternProcessor(string2);
        this.patternProcessor.setPrevFileTime(l2);
        this.fileName = string;
        this.fileExtension = FileExtension.lookupForFile(string2);
    }

    public void initialize() {
        if (!this.initialized) {
            LOGGER.debug("Initializing triggering policy {}", (Object)this.triggeringPolicy);
            this.initialized = true;
            this.triggeringPolicy.initialize(this);
            if (this.triggeringPolicy instanceof LifeCycle) {
                ((LifeCycle)((Object)this.triggeringPolicy)).start();
            }
        }
    }

    public static RollingFileManager getFileManager(String string, String string2, boolean bl, boolean bl2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, int n, boolean bl3, boolean bl4, Configuration configuration) {
        String string4 = string == null ? string2 : string;
        return (RollingFileManager)RollingFileManager.getManager(string4, new FactoryData(string, string2, bl, bl2, triggeringPolicy, rolloverStrategy, string3, layout, n, bl3, bl4, configuration), factory);
    }

    @Override
    public String getFileName() {
        if (this.rolloverStrategy instanceof DirectFileRolloverStrategy) {
            this.fileName = ((DirectFileRolloverStrategy)((Object)this.rolloverStrategy)).getCurrentFileName(this);
        }
        return this.fileName;
    }

    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    @Override
    protected synchronized void write(byte[] byArray, int n, int n2, boolean bl) {
        super.write(byArray, n, n2, bl);
    }

    @Override
    protected synchronized void writeToDestination(byte[] byArray, int n, int n2) {
        this.size += (long)n2;
        super.writeToDestination(byArray, n, n2);
    }

    public boolean isRenameEmptyFiles() {
        return this.renameEmptyFiles;
    }

    public void setRenameEmptyFiles(boolean bl) {
        this.renameEmptyFiles = bl;
    }

    public long getFileSize() {
        return this.size + (long)this.byteBuffer.position();
    }

    public long getFileTime() {
        return this.initialTime;
    }

    public synchronized void checkRollover(LogEvent logEvent) {
        if (this.triggeringPolicy.isTriggeringEvent(logEvent)) {
            this.rollover();
        }
    }

    @Override
    public boolean releaseSub(long l, TimeUnit timeUnit) {
        boolean bl;
        block13: {
            LOGGER.debug("Shutting down RollingFileManager {}" + this.getName());
            boolean bl2 = true;
            if (this.triggeringPolicy instanceof LifeCycle2) {
                bl2 &= ((LifeCycle2)((Object)this.triggeringPolicy)).stop(l, timeUnit);
            } else if (this.triggeringPolicy instanceof LifeCycle) {
                ((LifeCycle)((Object)this.triggeringPolicy)).stop();
                bl2 &= true;
            }
            bl = super.releaseSub(l, timeUnit) && bl2;
            this.asyncExecutor.shutdown();
            try {
                long l2 = timeUnit.toMillis(l);
                long l3 = 100L < l2 ? l2 : 100L;
                for (int i = 1; i <= 3 && !this.asyncExecutor.isTerminated(); ++i) {
                    this.asyncExecutor.awaitTermination(l3 * (long)i, TimeUnit.MILLISECONDS);
                }
                if (this.asyncExecutor.isTerminated()) {
                    LOGGER.debug("All asynchronous threads have terminated");
                    break block13;
                }
                this.asyncExecutor.shutdownNow();
                try {
                    this.asyncExecutor.awaitTermination(l, timeUnit);
                    if (this.asyncExecutor.isTerminated()) {
                        LOGGER.debug("All asynchronous threads have terminated");
                        break block13;
                    }
                    LOGGER.debug("RollingFileManager shutting down but some asynchronous services may not have completed");
                } catch (InterruptedException interruptedException) {
                    LOGGER.warn("RollingFileManager stopped but some asynchronous services may not have completed.");
                }
            } catch (InterruptedException interruptedException) {
                this.asyncExecutor.shutdownNow();
                try {
                    this.asyncExecutor.awaitTermination(l, timeUnit);
                    if (this.asyncExecutor.isTerminated()) {
                        LOGGER.debug("All asynchronous threads have terminated");
                    }
                } catch (InterruptedException interruptedException2) {
                    LOGGER.warn("RollingFileManager stopped but some asynchronous services may not have completed.");
                }
                Thread.currentThread().interrupt();
            }
        }
        LOGGER.debug("RollingFileManager shutdown completed with status {}", (Object)bl);
        return bl;
    }

    public synchronized void rollover() {
        if (!this.hasOutputStream()) {
            return;
        }
        if (this.rollover(this.rolloverStrategy)) {
            try {
                this.size = 0L;
                this.initialTime = System.currentTimeMillis();
                this.createFileAfterRollover();
            } catch (IOException iOException) {
                this.logError("Failed to create file after rollover", iOException);
            }
        }
    }

    protected void createFileAfterRollover() throws IOException {
        this.setOutputStream(this.createOutputStream());
    }

    public PatternProcessor getPatternProcessor() {
        return this.patternProcessor;
    }

    public void setTriggeringPolicy(TriggeringPolicy triggeringPolicy) {
        triggeringPolicy.initialize(this);
        TriggeringPolicy triggeringPolicy2 = this.triggeringPolicy;
        int n = 0;
        boolean bl = false;
        while (!(bl = triggeringPolicyUpdater.compareAndSet(this, this.triggeringPolicy, triggeringPolicy)) && ++n < 3) {
        }
        if (bl) {
            if (triggeringPolicy instanceof LifeCycle) {
                ((LifeCycle)((Object)triggeringPolicy)).start();
            }
            if (triggeringPolicy2 instanceof LifeCycle) {
                ((LifeCycle)((Object)triggeringPolicy2)).stop();
            }
        } else if (triggeringPolicy instanceof LifeCycle) {
            ((LifeCycle)((Object)triggeringPolicy)).stop();
        }
    }

    public void setRolloverStrategy(RolloverStrategy rolloverStrategy) {
        rolloverStrategyUpdater.compareAndSet(this, this.rolloverStrategy, rolloverStrategy);
    }

    public <T extends TriggeringPolicy> T getTriggeringPolicy() {
        return (T)this.triggeringPolicy;
    }

    public RolloverStrategy getRolloverStrategy() {
        return this.rolloverStrategy;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean rollover(RolloverStrategy rolloverStrategy) {
        boolean bl = false;
        try {
            this.semaphore.acquire();
            bl = true;
        } catch (InterruptedException interruptedException) {
            this.logError("Thread interrupted while attempting to check rollover", interruptedException);
            return true;
        }
        boolean bl2 = true;
        try {
            RolloverDescription rolloverDescription = rolloverStrategy.rollover(this);
            if (rolloverDescription != null) {
                this.writeFooter();
                this.closeOutputStream();
                if (rolloverDescription.getSynchronous() != null) {
                    LOGGER.debug("RollingFileManager executing synchronous {}", (Object)rolloverDescription.getSynchronous());
                    try {
                        bl2 = rolloverDescription.getSynchronous().execute();
                    } catch (Exception exception) {
                        bl2 = false;
                        this.logError("Caught error in synchronous task", exception);
                    }
                }
                if (bl2 && rolloverDescription.getAsynchronous() != null) {
                    LOGGER.debug("RollingFileManager executing async {}", (Object)rolloverDescription.getAsynchronous());
                    this.asyncExecutor.execute(new AsyncAction(rolloverDescription.getAsynchronous(), this));
                    bl = false;
                }
                boolean bl3 = true;
                return bl3;
            }
            boolean bl4 = false;
            return bl4;
        } finally {
            if (bl) {
                this.semaphore.release();
            }
        }
    }

    @Override
    public void updateData(Object object) {
        FactoryData factoryData = (FactoryData)object;
        this.setRolloverStrategy(factoryData.getRolloverStrategy());
        this.setTriggeringPolicy(factoryData.getTriggeringPolicy());
    }

    static Semaphore access$100(RollingFileManager rollingFileManager) {
        return rollingFileManager.semaphore;
    }

    static Logger access$500() {
        return LOGGER;
    }

    static Logger access$600() {
        return LOGGER;
    }

    static Logger access$1400() {
        return LOGGER;
    }

    static class 1 {
    }

    private static class EmptyQueue
    extends ArrayBlockingQueue<Runnable> {
        EmptyQueue() {
            super(1);
        }

        @Override
        public int remainingCapacity() {
            return 1;
        }

        @Override
        public boolean add(Runnable runnable) {
            throw new IllegalStateException("Queue is full");
        }

        @Override
        public void put(Runnable runnable) throws InterruptedException {
            throw new InterruptedException("Unable to insert into queue");
        }

        @Override
        public boolean offer(Runnable runnable, long l, TimeUnit timeUnit) throws InterruptedException {
            Thread.sleep(timeUnit.toMillis(l));
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends Runnable> collection) {
            if (collection.size() > 0) {
                throw new IllegalArgumentException("Too many items in collection");
            }
            return true;
        }

        @Override
        public boolean offer(Object object, long l, TimeUnit timeUnit) throws InterruptedException {
            return this.offer((Runnable)object, l, timeUnit);
        }

        @Override
        public void put(Object object) throws InterruptedException {
            this.put((Runnable)object);
        }

        @Override
        public boolean add(Object object) {
            return this.add((Runnable)object);
        }
    }

    private static class RollingFileManagerFactory
    implements ManagerFactory<RollingFileManager, FactoryData> {
        private RollingFileManagerFactory() {
        }

        @Override
        public RollingFileManager createManager(String string, FactoryData factoryData) {
            int n;
            long l = 0L;
            boolean bl = !FactoryData.access$200(factoryData);
            File file = null;
            if (FactoryData.access$300(factoryData) != null) {
                file = new File(FactoryData.access$300(factoryData));
                bl = !FactoryData.access$200(factoryData) || !file.exists();
                try {
                    FileUtils.makeParentDirs(file);
                    n = FactoryData.access$400(factoryData) ? 0 : file.createNewFile();
                    RollingFileManager.access$500().trace("New file '{}' created = {}", (Object)string, (Object)(n != 0));
                } catch (IOException iOException) {
                    RollingFileManager.access$600().error("Unable to create file " + string, (Throwable)iOException);
                    return null;
                }
                l = FactoryData.access$200(factoryData) ? file.length() : 0L;
            }
            try {
                n = FactoryData.access$700(factoryData) ? FactoryData.access$800(factoryData) : Constants.ENCODER_BYTE_BUFFER_SIZE;
                ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[n]);
                FileOutputStream fileOutputStream = FactoryData.access$400(factoryData) || FactoryData.access$300(factoryData) == null ? null : new FileOutputStream(FactoryData.access$300(factoryData), FactoryData.access$200(factoryData));
                long l2 = FactoryData.access$400(factoryData) || file == null ? System.currentTimeMillis() : file.lastModified();
                return new RollingFileManager(factoryData.getLoggerContext(), FactoryData.access$300(factoryData), FactoryData.access$900(factoryData), fileOutputStream, FactoryData.access$200(factoryData), FactoryData.access$400(factoryData), l, l2, FactoryData.access$1000(factoryData), FactoryData.access$1100(factoryData), FactoryData.access$1200(factoryData), FactoryData.access$1300(factoryData), bl, byteBuffer);
            } catch (IOException iOException) {
                RollingFileManager.access$1400().error("RollingFileManager (" + string + ") " + iOException, (Throwable)iOException);
                return null;
            }
        }

        @Override
        public Object createManager(String string, Object object) {
            return this.createManager(string, (FactoryData)object);
        }

        RollingFileManagerFactory(1 var1_1) {
            this();
        }
    }

    private static class FactoryData
    extends ConfigurationFactoryData {
        private final String fileName;
        private final String pattern;
        private final boolean append;
        private final boolean bufferedIO;
        private final int bufferSize;
        private final boolean immediateFlush;
        private final boolean createOnDemand;
        private final TriggeringPolicy policy;
        private final RolloverStrategy strategy;
        private final String advertiseURI;
        private final Layout<? extends Serializable> layout;

        public FactoryData(String string, String string2, boolean bl, boolean bl2, TriggeringPolicy triggeringPolicy, RolloverStrategy rolloverStrategy, String string3, Layout<? extends Serializable> layout, int n, boolean bl3, boolean bl4, Configuration configuration) {
            super(configuration);
            this.fileName = string;
            this.pattern = string2;
            this.append = bl;
            this.bufferedIO = bl2;
            this.bufferSize = n;
            this.policy = triggeringPolicy;
            this.strategy = rolloverStrategy;
            this.advertiseURI = string3;
            this.layout = layout;
            this.immediateFlush = bl3;
            this.createOnDemand = bl4;
        }

        public TriggeringPolicy getTriggeringPolicy() {
            return this.policy;
        }

        public RolloverStrategy getRolloverStrategy() {
            return this.strategy;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("[pattern=");
            stringBuilder.append(this.pattern);
            stringBuilder.append(", append=");
            stringBuilder.append(this.append);
            stringBuilder.append(", bufferedIO=");
            stringBuilder.append(this.bufferedIO);
            stringBuilder.append(", bufferSize=");
            stringBuilder.append(this.bufferSize);
            stringBuilder.append(", policy=");
            stringBuilder.append(this.policy);
            stringBuilder.append(", strategy=");
            stringBuilder.append(this.strategy);
            stringBuilder.append(", advertiseURI=");
            stringBuilder.append(this.advertiseURI);
            stringBuilder.append(", layout=");
            stringBuilder.append(this.layout);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }

        static boolean access$200(FactoryData factoryData) {
            return factoryData.append;
        }

        static String access$300(FactoryData factoryData) {
            return factoryData.fileName;
        }

        static boolean access$400(FactoryData factoryData) {
            return factoryData.createOnDemand;
        }

        static boolean access$700(FactoryData factoryData) {
            return factoryData.bufferedIO;
        }

        static int access$800(FactoryData factoryData) {
            return factoryData.bufferSize;
        }

        static String access$900(FactoryData factoryData) {
            return factoryData.pattern;
        }

        static TriggeringPolicy access$1000(FactoryData factoryData) {
            return factoryData.policy;
        }

        static RolloverStrategy access$1100(FactoryData factoryData) {
            return factoryData.strategy;
        }

        static String access$1200(FactoryData factoryData) {
            return factoryData.advertiseURI;
        }

        static Layout access$1300(FactoryData factoryData) {
            return factoryData.layout;
        }
    }

    private static class AsyncAction
    extends AbstractAction {
        private final Action action;
        private final RollingFileManager manager;

        public AsyncAction(Action action, RollingFileManager rollingFileManager) {
            this.action = action;
            this.manager = rollingFileManager;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean execute() throws IOException {
            try {
                boolean bl = this.action.execute();
                return bl;
            } finally {
                RollingFileManager.access$100(this.manager).release();
            }
        }

        @Override
        public void close() {
            this.action.close();
        }

        @Override
        public boolean isComplete() {
            return this.action.isComplete();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toString());
            stringBuilder.append("[action=");
            stringBuilder.append(this.action);
            stringBuilder.append(", manager=");
            stringBuilder.append(this.manager);
            stringBuilder.append(", isComplete()=");
            stringBuilder.append(this.isComplete());
            stringBuilder.append(", isInterrupted()=");
            stringBuilder.append(this.isInterrupted());
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }
}

