/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.buffer.AbstractReferenceCountedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufAllocatorMetric;
import io.netty.buffer.ByteBufAllocatorMetricProvider;
import io.netty.buffer.PoolArena;
import io.netty.buffer.PoolArenaMetric;
import io.netty.buffer.PoolThreadCache;
import io.netty.buffer.PooledByteBufAllocatorMetric;
import io.netty.buffer.UnpooledDirectByteBuf;
import io.netty.buffer.UnpooledHeapByteBuf;
import io.netty.buffer.UnpooledUnsafeHeapByteBuf;
import io.netty.buffer.UnsafeByteBufUtil;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PooledByteBufAllocator
extends AbstractByteBufAllocator
implements ByteBufAllocatorMetricProvider {
    private static final InternalLogger logger;
    private static final int DEFAULT_NUM_HEAP_ARENA;
    private static final int DEFAULT_NUM_DIRECT_ARENA;
    private static final int DEFAULT_PAGE_SIZE;
    private static final int DEFAULT_MAX_ORDER;
    private static final int DEFAULT_TINY_CACHE_SIZE;
    private static final int DEFAULT_SMALL_CACHE_SIZE;
    private static final int DEFAULT_NORMAL_CACHE_SIZE;
    private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
    private static final int DEFAULT_CACHE_TRIM_INTERVAL;
    private static final boolean DEFAULT_USE_CACHE_FOR_ALL_THREADS;
    private static final int DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT;
    private static final int MIN_PAGE_SIZE = 4096;
    private static final int MAX_CHUNK_SIZE = 0x40000000;
    public static final PooledByteBufAllocator DEFAULT;
    private final PoolArena<byte[]>[] heapArenas;
    private final PoolArena<ByteBuffer>[] directArenas;
    private final int tinyCacheSize;
    private final int smallCacheSize;
    private final int normalCacheSize;
    private final List<PoolArenaMetric> heapArenaMetrics;
    private final List<PoolArenaMetric> directArenaMetrics;
    private final PoolThreadLocalCache threadCache;
    private final int chunkSize;
    private final PooledByteBufAllocatorMetric metric;
    static final boolean $assertionsDisabled;

    public PooledByteBufAllocator() {
        this(false);
    }

    public PooledByteBufAllocator(boolean bl) {
        this(bl, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
    }

    public PooledByteBufAllocator(int n, int n2, int n3, int n4) {
        this(false, n, n2, n3, n4);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean bl, int n, int n2, int n3, int n4) {
        this(bl, n, n2, n3, n4, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
    }

    @Deprecated
    public PooledByteBufAllocator(boolean bl, int n, int n2, int n3, int n4, int n5, int n6, int n7) {
        this(bl, n, n2, n3, n4, n5, n6, n7, DEFAULT_USE_CACHE_FOR_ALL_THREADS, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    public PooledByteBufAllocator(boolean bl, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2) {
        this(bl, n, n2, n3, n4, n5, n6, n7, bl2, DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT);
    }

    public PooledByteBufAllocator(boolean bl, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl2, int n8) {
        super(bl);
        PoolArena poolArena;
        int n9;
        ArrayList<PoolArena.HeapArena> arrayList;
        this.threadCache = new PoolThreadLocalCache(this, bl2);
        this.tinyCacheSize = n5;
        this.smallCacheSize = n6;
        this.normalCacheSize = n7;
        this.chunkSize = PooledByteBufAllocator.validateAndCalculateChunkSize(n3, n4);
        if (n < 0) {
            throw new IllegalArgumentException("nHeapArena: " + n + " (expected: >= 0)");
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("nDirectArea: " + n2 + " (expected: >= 0)");
        }
        if (n8 < 0) {
            throw new IllegalArgumentException("directMemoryCacheAlignment: " + n8 + " (expected: >= 0)");
        }
        if (n8 > 0 && !PooledByteBufAllocator.isDirectMemoryCacheAlignmentSupported()) {
            throw new IllegalArgumentException("directMemoryCacheAlignment is not supported");
        }
        if ((n8 & -n8) != n8) {
            throw new IllegalArgumentException("directMemoryCacheAlignment: " + n8 + " (expected: power of two)");
        }
        int n10 = PooledByteBufAllocator.validateAndCalculatePageShifts(n3);
        if (n > 0) {
            this.heapArenas = PooledByteBufAllocator.newArenaArray(n);
            arrayList = new ArrayList<PoolArena.HeapArena>(this.heapArenas.length);
            for (n9 = 0; n9 < this.heapArenas.length; ++n9) {
                this.heapArenas[n9] = poolArena = new PoolArena.HeapArena(this, n3, n4, n10, this.chunkSize, n8);
                arrayList.add((PoolArena.HeapArena)poolArena);
            }
            this.heapArenaMetrics = Collections.unmodifiableList(arrayList);
        } else {
            this.heapArenas = null;
            this.heapArenaMetrics = Collections.emptyList();
        }
        if (n2 > 0) {
            this.directArenas = PooledByteBufAllocator.newArenaArray(n2);
            arrayList = new ArrayList(this.directArenas.length);
            for (n9 = 0; n9 < this.directArenas.length; ++n9) {
                this.directArenas[n9] = poolArena = new PoolArena.DirectArena(this, n3, n4, n10, this.chunkSize, n8);
                arrayList.add((PoolArena.HeapArena)poolArena);
            }
            this.directArenaMetrics = Collections.unmodifiableList(arrayList);
        } else {
            this.directArenas = null;
            this.directArenaMetrics = Collections.emptyList();
        }
        this.metric = new PooledByteBufAllocatorMetric(this);
    }

    private static <T> PoolArena<T>[] newArenaArray(int n) {
        return new PoolArena[n];
    }

    private static int validateAndCalculatePageShifts(int n) {
        if (n < 4096) {
            throw new IllegalArgumentException("pageSize: " + n + " (expected: " + 4096 + ")");
        }
        if ((n & n - 1) != 0) {
            throw new IllegalArgumentException("pageSize: " + n + " (expected: power of 2)");
        }
        return 31 - Integer.numberOfLeadingZeros(n);
    }

    private static int validateAndCalculateChunkSize(int n, int n2) {
        if (n2 > 14) {
            throw new IllegalArgumentException("maxOrder: " + n2 + " (expected: 0-14)");
        }
        int n3 = n;
        for (int i = n2; i > 0; --i) {
            if (n3 > 0x20000000) {
                throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", n, n2, 0x40000000));
            }
            n3 <<= 1;
        }
        return n3;
    }

    @Override
    protected ByteBuf newHeapBuffer(int n, int n2) {
        PoolThreadCache poolThreadCache = (PoolThreadCache)this.threadCache.get();
        PoolArena<byte[]> poolArena = poolThreadCache.heapArena;
        AbstractReferenceCountedByteBuf abstractReferenceCountedByteBuf = poolArena != null ? poolArena.allocate(poolThreadCache, n, n2) : (PlatformDependent.hasUnsafe() ? new UnpooledUnsafeHeapByteBuf((ByteBufAllocator)this, n, n2) : new UnpooledHeapByteBuf((ByteBufAllocator)this, n, n2));
        return PooledByteBufAllocator.toLeakAwareBuffer(abstractReferenceCountedByteBuf);
    }

    @Override
    protected ByteBuf newDirectBuffer(int n, int n2) {
        PoolThreadCache poolThreadCache = (PoolThreadCache)this.threadCache.get();
        PoolArena<ByteBuffer> poolArena = poolThreadCache.directArena;
        AbstractReferenceCountedByteBuf abstractReferenceCountedByteBuf = poolArena != null ? poolArena.allocate(poolThreadCache, n, n2) : (PlatformDependent.hasUnsafe() ? UnsafeByteBufUtil.newUnsafeDirectByteBuf(this, n, n2) : new UnpooledDirectByteBuf((ByteBufAllocator)this, n, n2));
        return PooledByteBufAllocator.toLeakAwareBuffer(abstractReferenceCountedByteBuf);
    }

    public static int defaultNumHeapArena() {
        return DEFAULT_NUM_HEAP_ARENA;
    }

    public static int defaultNumDirectArena() {
        return DEFAULT_NUM_DIRECT_ARENA;
    }

    public static int defaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }

    public static int defaultMaxOrder() {
        return DEFAULT_MAX_ORDER;
    }

    public static boolean defaultUseCacheForAllThreads() {
        return DEFAULT_USE_CACHE_FOR_ALL_THREADS;
    }

    public static boolean defaultPreferDirect() {
        return PlatformDependent.directBufferPreferred();
    }

    public static int defaultTinyCacheSize() {
        return DEFAULT_TINY_CACHE_SIZE;
    }

    public static int defaultSmallCacheSize() {
        return DEFAULT_SMALL_CACHE_SIZE;
    }

    public static int defaultNormalCacheSize() {
        return DEFAULT_NORMAL_CACHE_SIZE;
    }

    public static boolean isDirectMemoryCacheAlignmentSupported() {
        return PlatformDependent.hasUnsafe();
    }

    @Override
    public boolean isDirectBufferPooled() {
        return this.directArenas != null;
    }

    @Deprecated
    public boolean hasThreadLocalCache() {
        return this.threadCache.isSet();
    }

    @Deprecated
    public void freeThreadLocalCache() {
        this.threadCache.remove();
    }

    @Override
    public PooledByteBufAllocatorMetric metric() {
        return this.metric;
    }

    @Deprecated
    public int numHeapArenas() {
        return this.heapArenaMetrics.size();
    }

    @Deprecated
    public int numDirectArenas() {
        return this.directArenaMetrics.size();
    }

    @Deprecated
    public List<PoolArenaMetric> heapArenas() {
        return this.heapArenaMetrics;
    }

    @Deprecated
    public List<PoolArenaMetric> directArenas() {
        return this.directArenaMetrics;
    }

    @Deprecated
    public int numThreadLocalCaches() {
        PoolArena<Object>[] poolArenaArray;
        PoolArena<Object>[] poolArenaArray2 = poolArenaArray = this.heapArenas != null ? this.heapArenas : this.directArenas;
        if (poolArenaArray == null) {
            return 1;
        }
        int n = 0;
        for (PoolArena<Object> poolArena : poolArenaArray) {
            n += poolArena.numThreadCaches.get();
        }
        return n;
    }

    @Deprecated
    public int tinyCacheSize() {
        return this.tinyCacheSize;
    }

    @Deprecated
    public int smallCacheSize() {
        return this.smallCacheSize;
    }

    @Deprecated
    public int normalCacheSize() {
        return this.normalCacheSize;
    }

    @Deprecated
    public final int chunkSize() {
        return this.chunkSize;
    }

    final long usedHeapMemory() {
        return PooledByteBufAllocator.usedMemory(this.heapArenas);
    }

    final long usedDirectMemory() {
        return PooledByteBufAllocator.usedMemory(this.directArenas);
    }

    private static long usedMemory(PoolArena<?> ... poolArenaArray) {
        if (poolArenaArray == null) {
            return -1L;
        }
        long l = 0L;
        for (PoolArena<?> poolArena : poolArenaArray) {
            if ((l += poolArena.numActiveBytes()) >= 0L) continue;
            return Long.MAX_VALUE;
        }
        return l;
    }

    final PoolThreadCache threadCache() {
        PoolThreadCache poolThreadCache = (PoolThreadCache)this.threadCache.get();
        if (!$assertionsDisabled && poolThreadCache == null) {
            throw new AssertionError();
        }
        return poolThreadCache;
    }

    public String dumpStats() {
        int n = this.heapArenas == null ? 0 : this.heapArenas.length;
        StringBuilder stringBuilder = new StringBuilder(512).append(n).append(" heap arena(s):").append(StringUtil.NEWLINE);
        if (n > 0) {
            for (PoolArena<byte[]> poolArena : this.heapArenas) {
                stringBuilder.append(poolArena);
            }
        }
        int n2 = this.directArenas == null ? 0 : this.directArenas.length;
        stringBuilder.append(n2).append(" direct arena(s):").append(StringUtil.NEWLINE);
        if (n2 > 0) {
            for (PoolArena<ByteBuffer> poolArena : this.directArenas) {
                stringBuilder.append(poolArena);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public ByteBufAllocatorMetric metric() {
        return this.metric();
    }

    static PoolArena[] access$000(PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.heapArenas;
    }

    static PoolArena[] access$100(PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.directArenas;
    }

    static int access$200(PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.tinyCacheSize;
    }

    static int access$300(PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.smallCacheSize;
    }

    static int access$400(PooledByteBufAllocator pooledByteBufAllocator) {
        return pooledByteBufAllocator.normalCacheSize;
    }

    static int access$500() {
        return DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
    }

    static int access$600() {
        return DEFAULT_CACHE_TRIM_INTERVAL;
    }

    static {
        $assertionsDisabled = !PooledByteBufAllocator.class.desiredAssertionStatus();
        logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
        int n = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
        Throwable throwable = null;
        try {
            PooledByteBufAllocator.validateAndCalculatePageShifts(n);
        } catch (Throwable throwable2) {
            throwable = throwable2;
            n = 8192;
        }
        DEFAULT_PAGE_SIZE = n;
        int n2 = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
        Throwable throwable3 = null;
        try {
            PooledByteBufAllocator.validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, n2);
        } catch (Throwable throwable4) {
            throwable3 = throwable4;
            n2 = 11;
        }
        DEFAULT_MAX_ORDER = n2;
        Runtime runtime = Runtime.getRuntime();
        int n3 = NettyRuntime.availableProcessors() * 2;
        int n4 = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
        DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min((long)n3, runtime.maxMemory() / (long)n4 / 2L / 3L)));
        DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min((long)n3, PlatformDependent.maxDirectMemory() / (long)n4 / 2L / 3L)));
        DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
        DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
        DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
        DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
        DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
        DEFAULT_USE_CACHE_FOR_ALL_THREADS = SystemPropertyUtil.getBoolean("io.netty.allocator.useCacheForAllThreads", true);
        DEFAULT_DIRECT_MEMORY_CACHE_ALIGNMENT = SystemPropertyUtil.getInt("io.netty.allocator.directMemoryCacheAlignment", 0);
        if (logger.isDebugEnabled()) {
            logger.debug("-Dio.netty.allocator.numHeapArenas: {}", (Object)DEFAULT_NUM_HEAP_ARENA);
            logger.debug("-Dio.netty.allocator.numDirectArenas: {}", (Object)DEFAULT_NUM_DIRECT_ARENA);
            if (throwable == null) {
                logger.debug("-Dio.netty.allocator.pageSize: {}", (Object)DEFAULT_PAGE_SIZE);
            } else {
                logger.debug("-Dio.netty.allocator.pageSize: {}", (Object)DEFAULT_PAGE_SIZE, (Object)throwable);
            }
            if (throwable3 == null) {
                logger.debug("-Dio.netty.allocator.maxOrder: {}", (Object)DEFAULT_MAX_ORDER);
            } else {
                logger.debug("-Dio.netty.allocator.maxOrder: {}", (Object)DEFAULT_MAX_ORDER, (Object)throwable3);
            }
            logger.debug("-Dio.netty.allocator.chunkSize: {}", (Object)(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
            logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", (Object)DEFAULT_TINY_CACHE_SIZE);
            logger.debug("-Dio.netty.allocator.smallCacheSize: {}", (Object)DEFAULT_SMALL_CACHE_SIZE);
            logger.debug("-Dio.netty.allocator.normalCacheSize: {}", (Object)DEFAULT_NORMAL_CACHE_SIZE);
            logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", (Object)DEFAULT_MAX_CACHED_BUFFER_CAPACITY);
            logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", (Object)DEFAULT_CACHE_TRIM_INTERVAL);
            logger.debug("-Dio.netty.allocator.useCacheForAllThreads: {}", (Object)DEFAULT_USE_CACHE_FOR_ALL_THREADS);
        }
        DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    final class PoolThreadLocalCache
    extends FastThreadLocal<PoolThreadCache> {
        private final boolean useCacheForAllThreads;
        final PooledByteBufAllocator this$0;

        PoolThreadLocalCache(PooledByteBufAllocator pooledByteBufAllocator, boolean bl) {
            this.this$0 = pooledByteBufAllocator;
            this.useCacheForAllThreads = bl;
        }

        @Override
        protected synchronized PoolThreadCache initialValue() {
            PoolArena<byte[]> poolArena = this.leastUsedArena(PooledByteBufAllocator.access$000(this.this$0));
            PoolArena<ByteBuffer> poolArena2 = this.leastUsedArena(PooledByteBufAllocator.access$100(this.this$0));
            Thread thread2 = Thread.currentThread();
            if (this.useCacheForAllThreads || thread2 instanceof FastThreadLocalThread) {
                return new PoolThreadCache(poolArena, poolArena2, PooledByteBufAllocator.access$200(this.this$0), PooledByteBufAllocator.access$300(this.this$0), PooledByteBufAllocator.access$400(this.this$0), PooledByteBufAllocator.access$500(), PooledByteBufAllocator.access$600());
            }
            return new PoolThreadCache(poolArena, poolArena2, 0, 0, 0, 0, 0);
        }

        @Override
        protected void onRemoval(PoolThreadCache poolThreadCache) {
            poolThreadCache.free();
        }

        private <T> PoolArena<T> leastUsedArena(PoolArena<T>[] poolArenaArray) {
            if (poolArenaArray == null || poolArenaArray.length == 0) {
                return null;
            }
            PoolArena<T> poolArena = poolArenaArray[0];
            for (int i = 1; i < poolArenaArray.length; ++i) {
                PoolArena<T> poolArena2 = poolArenaArray[i];
                if (poolArena2.numThreadCaches.get() >= poolArena.numThreadCaches.get()) continue;
                poolArena = poolArena2;
            }
            return poolArena;
        }

        @Override
        protected void onRemoval(Object object) throws Exception {
            this.onRemoval((PoolThreadCache)object);
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    }
}

