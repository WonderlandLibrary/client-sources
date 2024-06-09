/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PooledByteBufAllocator
/*     */   extends AbstractByteBufAllocator
/*     */ {
/*  30 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PooledByteBufAllocator.class);
/*     */   
/*     */   private static final int DEFAULT_NUM_HEAP_ARENA;
/*     */   
/*     */   private static final int DEFAULT_NUM_DIRECT_ARENA;
/*     */   
/*     */   private static final int DEFAULT_PAGE_SIZE;
/*     */   
/*     */   private static final int DEFAULT_MAX_ORDER;
/*     */   private static final int DEFAULT_TINY_CACHE_SIZE;
/*     */   private static final int DEFAULT_SMALL_CACHE_SIZE;
/*     */   private static final int DEFAULT_NORMAL_CACHE_SIZE;
/*     */   private static final int DEFAULT_MAX_CACHED_BUFFER_CAPACITY;
/*     */   private static final int DEFAULT_CACHE_TRIM_INTERVAL;
/*     */   
/*     */   static {
/*  46 */     int defaultPageSize = SystemPropertyUtil.getInt("io.netty.allocator.pageSize", 8192);
/*  47 */     Throwable pageSizeFallbackCause = null;
/*     */     try {
/*  49 */       validateAndCalculatePageShifts(defaultPageSize);
/*  50 */     } catch (Throwable t) {
/*  51 */       pageSizeFallbackCause = t;
/*  52 */       defaultPageSize = 8192;
/*     */     } 
/*  54 */     DEFAULT_PAGE_SIZE = defaultPageSize;
/*     */     
/*  56 */     int defaultMaxOrder = SystemPropertyUtil.getInt("io.netty.allocator.maxOrder", 11);
/*  57 */     Throwable maxOrderFallbackCause = null;
/*     */     try {
/*  59 */       validateAndCalculateChunkSize(DEFAULT_PAGE_SIZE, defaultMaxOrder);
/*  60 */     } catch (Throwable t) {
/*  61 */       maxOrderFallbackCause = t;
/*  62 */       defaultMaxOrder = 11;
/*     */     } 
/*  64 */     DEFAULT_MAX_ORDER = defaultMaxOrder;
/*     */ 
/*     */ 
/*     */     
/*  68 */     Runtime runtime = Runtime.getRuntime();
/*  69 */     int defaultChunkSize = DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER;
/*  70 */     DEFAULT_NUM_HEAP_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numHeapArenas", (int)Math.min(runtime.availableProcessors(), Runtime.getRuntime().maxMemory() / defaultChunkSize / 2L / 3L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     DEFAULT_NUM_DIRECT_ARENA = Math.max(0, SystemPropertyUtil.getInt("io.netty.allocator.numDirectArenas", (int)Math.min(runtime.availableProcessors(), PlatformDependent.maxDirectMemory() / defaultChunkSize / 2L / 3L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     DEFAULT_TINY_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.tinyCacheSize", 512);
/*  85 */     DEFAULT_SMALL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.smallCacheSize", 256);
/*  86 */     DEFAULT_NORMAL_CACHE_SIZE = SystemPropertyUtil.getInt("io.netty.allocator.normalCacheSize", 64);
/*     */ 
/*     */ 
/*     */     
/*  90 */     DEFAULT_MAX_CACHED_BUFFER_CAPACITY = SystemPropertyUtil.getInt("io.netty.allocator.maxCachedBufferCapacity", 32768);
/*     */ 
/*     */ 
/*     */     
/*  94 */     DEFAULT_CACHE_TRIM_INTERVAL = SystemPropertyUtil.getInt("io.netty.allocator.cacheTrimInterval", 8192);
/*     */ 
/*     */     
/*  97 */     if (logger.isDebugEnabled()) {
/*  98 */       logger.debug("-Dio.netty.allocator.numHeapArenas: {}", Integer.valueOf(DEFAULT_NUM_HEAP_ARENA));
/*  99 */       logger.debug("-Dio.netty.allocator.numDirectArenas: {}", Integer.valueOf(DEFAULT_NUM_DIRECT_ARENA));
/* 100 */       if (pageSizeFallbackCause == null) {
/* 101 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE));
/*     */       } else {
/* 103 */         logger.debug("-Dio.netty.allocator.pageSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE), pageSizeFallbackCause);
/*     */       } 
/* 105 */       if (maxOrderFallbackCause == null) {
/* 106 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER));
/*     */       } else {
/* 108 */         logger.debug("-Dio.netty.allocator.maxOrder: {}", Integer.valueOf(DEFAULT_MAX_ORDER), maxOrderFallbackCause);
/*     */       } 
/* 110 */       logger.debug("-Dio.netty.allocator.chunkSize: {}", Integer.valueOf(DEFAULT_PAGE_SIZE << DEFAULT_MAX_ORDER));
/* 111 */       logger.debug("-Dio.netty.allocator.tinyCacheSize: {}", Integer.valueOf(DEFAULT_TINY_CACHE_SIZE));
/* 112 */       logger.debug("-Dio.netty.allocator.smallCacheSize: {}", Integer.valueOf(DEFAULT_SMALL_CACHE_SIZE));
/* 113 */       logger.debug("-Dio.netty.allocator.normalCacheSize: {}", Integer.valueOf(DEFAULT_NORMAL_CACHE_SIZE));
/* 114 */       logger.debug("-Dio.netty.allocator.maxCachedBufferCapacity: {}", Integer.valueOf(DEFAULT_MAX_CACHED_BUFFER_CAPACITY));
/* 115 */       logger.debug("-Dio.netty.allocator.cacheTrimInterval: {}", Integer.valueOf(DEFAULT_CACHE_TRIM_INTERVAL));
/*     */     } 
/*     */ 
/*     */     
/* 119 */     DEFAULT = new PooledByteBufAllocator(PlatformDependent.directBufferPreferred());
/*     */   }
/*     */ 
/*     */   
/*     */   private static final int MIN_PAGE_SIZE = 4096;
/*     */   
/*     */   private static final int MAX_CHUNK_SIZE = 1073741824;
/*     */   
/*     */   public static final PooledByteBufAllocator DEFAULT;
/*     */   private final PoolArena<byte[]>[] heapArenas;
/*     */   
/*     */   public PooledByteBufAllocator() {
/* 131 */     this(false);
/*     */   }
/*     */   private final PoolArena<ByteBuffer>[] directArenas; private final int tinyCacheSize; private final int smallCacheSize; private final int normalCacheSize; final PoolThreadLocalCache threadCache;
/*     */   public PooledByteBufAllocator(boolean preferDirect) {
/* 135 */     this(preferDirect, DEFAULT_NUM_HEAP_ARENA, DEFAULT_NUM_DIRECT_ARENA, DEFAULT_PAGE_SIZE, DEFAULT_MAX_ORDER);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 139 */     this(false, nHeapArena, nDirectArena, pageSize, maxOrder);
/*     */   }
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder) {
/* 143 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, DEFAULT_TINY_CACHE_SIZE, DEFAULT_SMALL_CACHE_SIZE, DEFAULT_NORMAL_CACHE_SIZE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize) {
/* 149 */     super(preferDirect);
/* 150 */     this.threadCache = new PoolThreadLocalCache();
/* 151 */     this.tinyCacheSize = tinyCacheSize;
/* 152 */     this.smallCacheSize = smallCacheSize;
/* 153 */     this.normalCacheSize = normalCacheSize;
/* 154 */     int chunkSize = validateAndCalculateChunkSize(pageSize, maxOrder);
/*     */     
/* 156 */     if (nHeapArena < 0) {
/* 157 */       throw new IllegalArgumentException("nHeapArena: " + nHeapArena + " (expected: >= 0)");
/*     */     }
/* 159 */     if (nDirectArena < 0) {
/* 160 */       throw new IllegalArgumentException("nDirectArea: " + nDirectArena + " (expected: >= 0)");
/*     */     }
/*     */     
/* 163 */     int pageShifts = validateAndCalculatePageShifts(pageSize);
/*     */     
/* 165 */     if (nHeapArena > 0) {
/* 166 */       this.heapArenas = newArenaArray(nHeapArena);
/* 167 */       for (int i = 0; i < this.heapArenas.length; i++) {
/* 168 */         this.heapArenas[i] = new PoolArena.HeapArena(this, pageSize, maxOrder, pageShifts, chunkSize);
/*     */       }
/*     */     } else {
/* 171 */       this.heapArenas = null;
/*     */     } 
/*     */     
/* 174 */     if (nDirectArena > 0) {
/* 175 */       this.directArenas = newArenaArray(nDirectArena);
/* 176 */       for (int i = 0; i < this.directArenas.length; i++) {
/* 177 */         this.directArenas[i] = new PoolArena.DirectArena(this, pageSize, maxOrder, pageShifts, chunkSize);
/*     */       }
/*     */     } else {
/* 180 */       this.directArenas = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PooledByteBufAllocator(boolean preferDirect, int nHeapArena, int nDirectArena, int pageSize, int maxOrder, int tinyCacheSize, int smallCacheSize, int normalCacheSize, long cacheThreadAliveCheckInterval) {
/* 189 */     this(preferDirect, nHeapArena, nDirectArena, pageSize, maxOrder, tinyCacheSize, smallCacheSize, normalCacheSize);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> PoolArena<T>[] newArenaArray(int size) {
/* 195 */     return (PoolArena<T>[])new PoolArena[size];
/*     */   }
/*     */   
/*     */   private static int validateAndCalculatePageShifts(int pageSize) {
/* 199 */     if (pageSize < 4096) {
/* 200 */       throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: " + 'က' + "+)");
/*     */     }
/*     */     
/* 203 */     if ((pageSize & pageSize - 1) != 0) {
/* 204 */       throw new IllegalArgumentException("pageSize: " + pageSize + " (expected: power of 2)");
/*     */     }
/*     */ 
/*     */     
/* 208 */     return 31 - Integer.numberOfLeadingZeros(pageSize);
/*     */   }
/*     */   
/*     */   private static int validateAndCalculateChunkSize(int pageSize, int maxOrder) {
/* 212 */     if (maxOrder > 14) {
/* 213 */       throw new IllegalArgumentException("maxOrder: " + maxOrder + " (expected: 0-14)");
/*     */     }
/*     */ 
/*     */     
/* 217 */     int chunkSize = pageSize;
/* 218 */     for (int i = maxOrder; i > 0; i--) {
/* 219 */       if (chunkSize > 536870912) {
/* 220 */         throw new IllegalArgumentException(String.format("pageSize (%d) << maxOrder (%d) must not exceed %d", new Object[] { Integer.valueOf(pageSize), Integer.valueOf(maxOrder), Integer.valueOf(1073741824) }));
/*     */       }
/*     */       
/* 223 */       chunkSize <<= 1;
/*     */     } 
/* 225 */     return chunkSize;
/*     */   }
/*     */   
/*     */   protected ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity) {
/*     */     ByteBuf buf;
/* 230 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
/* 231 */     PoolArena<byte[]> heapArena = cache.heapArena;
/*     */ 
/*     */     
/* 234 */     if (heapArena != null) {
/* 235 */       buf = heapArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     } else {
/* 237 */       buf = new UnpooledHeapByteBuf(this, initialCapacity, maxCapacity);
/*     */     } 
/*     */     
/* 240 */     return toLeakAwareBuffer(buf);
/*     */   }
/*     */   
/*     */   protected ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity) {
/*     */     ByteBuf buf;
/* 245 */     PoolThreadCache cache = (PoolThreadCache)this.threadCache.get();
/* 246 */     PoolArena<ByteBuffer> directArena = cache.directArena;
/*     */ 
/*     */     
/* 249 */     if (directArena != null) {
/* 250 */       buf = directArena.allocate(cache, initialCapacity, maxCapacity);
/*     */     }
/* 252 */     else if (PlatformDependent.hasUnsafe()) {
/* 253 */       buf = new UnpooledUnsafeDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */     } else {
/* 255 */       buf = new UnpooledDirectByteBuf(this, initialCapacity, maxCapacity);
/*     */     } 
/*     */ 
/*     */     
/* 259 */     return toLeakAwareBuffer(buf);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirectBufferPooled() {
/* 264 */     return (this.directArenas != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public boolean hasThreadLocalCache() {
/* 273 */     return this.threadCache.isSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void freeThreadLocalCache() {
/* 281 */     this.threadCache.remove();
/*     */   }
/*     */   
/*     */   final class PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
/* 285 */     private final AtomicInteger index = new AtomicInteger();
/*     */     protected PoolThreadCache initialValue() {
/*     */       PoolArena<byte[]> heapArena;
/*     */       PoolArena<ByteBuffer> directArena;
/* 289 */       int idx = this.index.getAndIncrement();
/*     */ 
/*     */ 
/*     */       
/* 293 */       if (PooledByteBufAllocator.this.heapArenas != null) {
/* 294 */         heapArena = PooledByteBufAllocator.this.heapArenas[Math.abs(idx % PooledByteBufAllocator.this.heapArenas.length)];
/*     */       } else {
/* 296 */         heapArena = null;
/*     */       } 
/*     */       
/* 299 */       if (PooledByteBufAllocator.this.directArenas != null) {
/* 300 */         directArena = PooledByteBufAllocator.this.directArenas[Math.abs(idx % PooledByteBufAllocator.this.directArenas.length)];
/*     */       } else {
/* 302 */         directArena = null;
/*     */       } 
/*     */       
/* 305 */       return new PoolThreadCache(heapArena, directArena, PooledByteBufAllocator.this.tinyCacheSize, PooledByteBufAllocator.this.smallCacheSize, PooledByteBufAllocator.this.normalCacheSize, PooledByteBufAllocator.DEFAULT_MAX_CACHED_BUFFER_CAPACITY, PooledByteBufAllocator.DEFAULT_CACHE_TRIM_INTERVAL);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onRemoval(PoolThreadCache value) {
/* 312 */       value.free();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\PooledByteBufAllocator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */