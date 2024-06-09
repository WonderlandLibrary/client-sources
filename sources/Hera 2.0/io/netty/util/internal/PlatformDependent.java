/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.CharsetUtil;
/*     */ import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.ServerSocket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicLongFieldUpdater;
/*     */ import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public final class PlatformDependent
/*     */ {
/*  56 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
/*     */   
/*  58 */   private static final Pattern MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN = Pattern.compile("\\s*-XX:MaxDirectMemorySize\\s*=\\s*([0-9]+)\\s*([kKmMgG]?)\\s*$");
/*     */ 
/*     */   
/*  61 */   private static final boolean IS_ANDROID = isAndroid0();
/*  62 */   private static final boolean IS_WINDOWS = isWindows0();
/*  63 */   private static final boolean IS_ROOT = isRoot0();
/*     */   
/*  65 */   private static final int JAVA_VERSION = javaVersion0();
/*     */   
/*  67 */   private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
/*     */   
/*  69 */   private static final boolean HAS_UNSAFE = hasUnsafe0();
/*  70 */   private static final boolean CAN_USE_CHM_V8 = (HAS_UNSAFE && JAVA_VERSION < 8);
/*  71 */   private static final boolean DIRECT_BUFFER_PREFERRED = (HAS_UNSAFE && !SystemPropertyUtil.getBoolean("io.netty.noPreferDirect", false));
/*     */   
/*  73 */   private static final long MAX_DIRECT_MEMORY = maxDirectMemory0();
/*     */   
/*  75 */   private static final long ARRAY_BASE_OFFSET = arrayBaseOffset0();
/*     */   
/*  77 */   private static final boolean HAS_JAVASSIST = hasJavassist0();
/*     */   
/*  79 */   private static final File TMPDIR = tmpdir0();
/*     */   
/*  81 */   private static final int BIT_MODE = bitMode0();
/*     */   
/*  83 */   private static final int ADDRESS_SIZE = addressSize0();
/*     */   
/*     */   static {
/*  86 */     if (logger.isDebugEnabled()) {
/*  87 */       logger.debug("-Dio.netty.noPreferDirect: {}", Boolean.valueOf(!DIRECT_BUFFER_PREFERRED));
/*     */     }
/*     */     
/*  90 */     if (!hasUnsafe() && !isAndroid()) {
/*  91 */       logger.info("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential system unstability.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAndroid() {
/* 102 */     return IS_ANDROID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isWindows() {
/* 109 */     return IS_WINDOWS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isRoot() {
/* 117 */     return IS_ROOT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int javaVersion() {
/* 124 */     return JAVA_VERSION;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canEnableTcpNoDelayByDefault() {
/* 131 */     return CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasUnsafe() {
/* 139 */     return HAS_UNSAFE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean directBufferPreferred() {
/* 147 */     return DIRECT_BUFFER_PREFERRED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long maxDirectMemory() {
/* 154 */     return MAX_DIRECT_MEMORY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasJavassist() {
/* 161 */     return HAS_JAVASSIST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static File tmpdir() {
/* 168 */     return TMPDIR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int bitMode() {
/* 175 */     return BIT_MODE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int addressSize() {
/* 183 */     return ADDRESS_SIZE;
/*     */   }
/*     */   
/*     */   public static long allocateMemory(long size) {
/* 187 */     return PlatformDependent0.allocateMemory(size);
/*     */   }
/*     */   
/*     */   public static void freeMemory(long address) {
/* 191 */     PlatformDependent0.freeMemory(address);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void throwException(Throwable t) {
/* 198 */     if (hasUnsafe()) {
/* 199 */       PlatformDependent0.throwException(t);
/*     */     } else {
/* 201 */       throwException0(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static <E extends Throwable> void throwException0(Throwable t) throws E {
/* 207 */     throw (E)t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
/* 214 */     if (CAN_USE_CHM_V8) {
/* 215 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8();
/*     */     }
/* 217 */     return new ConcurrentHashMap<K, V>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity) {
/* 225 */     if (CAN_USE_CHM_V8) {
/* 226 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity);
/*     */     }
/* 228 */     return new ConcurrentHashMap<K, V>(initialCapacity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor) {
/* 236 */     if (CAN_USE_CHM_V8) {
/* 237 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity, loadFactor);
/*     */     }
/* 239 */     return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
/* 248 */     if (CAN_USE_CHM_V8) {
/* 249 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(initialCapacity, loadFactor, concurrencyLevel);
/*     */     }
/* 251 */     return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor, concurrencyLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(Map<? extends K, ? extends V> map) {
/* 259 */     if (CAN_USE_CHM_V8) {
/* 260 */       return (ConcurrentMap<K, V>)new ConcurrentHashMapV8(map);
/*     */     }
/* 262 */     return new ConcurrentHashMap<K, V>(map);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void freeDirectBuffer(ByteBuffer buffer) {
/* 271 */     if (hasUnsafe() && !isAndroid())
/*     */     {
/*     */       
/* 274 */       PlatformDependent0.freeDirectBuffer(buffer);
/*     */     }
/*     */   }
/*     */   
/*     */   public static long directBufferAddress(ByteBuffer buffer) {
/* 279 */     return PlatformDependent0.directBufferAddress(buffer);
/*     */   }
/*     */   
/*     */   public static Object getObject(Object object, long fieldOffset) {
/* 283 */     return PlatformDependent0.getObject(object, fieldOffset);
/*     */   }
/*     */   
/*     */   public static Object getObjectVolatile(Object object, long fieldOffset) {
/* 287 */     return PlatformDependent0.getObjectVolatile(object, fieldOffset);
/*     */   }
/*     */   
/*     */   public static int getInt(Object object, long fieldOffset) {
/* 291 */     return PlatformDependent0.getInt(object, fieldOffset);
/*     */   }
/*     */   
/*     */   public static long objectFieldOffset(Field field) {
/* 295 */     return PlatformDependent0.objectFieldOffset(field);
/*     */   }
/*     */   
/*     */   public static byte getByte(long address) {
/* 299 */     return PlatformDependent0.getByte(address);
/*     */   }
/*     */   
/*     */   public static short getShort(long address) {
/* 303 */     return PlatformDependent0.getShort(address);
/*     */   }
/*     */   
/*     */   public static int getInt(long address) {
/* 307 */     return PlatformDependent0.getInt(address);
/*     */   }
/*     */   
/*     */   public static long getLong(long address) {
/* 311 */     return PlatformDependent0.getLong(address);
/*     */   }
/*     */   
/*     */   public static void putOrderedObject(Object object, long address, Object value) {
/* 315 */     PlatformDependent0.putOrderedObject(object, address, value);
/*     */   }
/*     */   
/*     */   public static void putByte(long address, byte value) {
/* 319 */     PlatformDependent0.putByte(address, value);
/*     */   }
/*     */   
/*     */   public static void putShort(long address, short value) {
/* 323 */     PlatformDependent0.putShort(address, value);
/*     */   }
/*     */   
/*     */   public static void putInt(long address, int value) {
/* 327 */     PlatformDependent0.putInt(address, value);
/*     */   }
/*     */   
/*     */   public static void putLong(long address, long value) {
/* 331 */     PlatformDependent0.putLong(address, value);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long srcAddr, long dstAddr, long length) {
/* 335 */     PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
/*     */   }
/*     */   
/*     */   public static void copyMemory(byte[] src, int srcIndex, long dstAddr, long length) {
/* 339 */     PlatformDependent0.copyMemory(src, ARRAY_BASE_OFFSET + srcIndex, null, dstAddr, length);
/*     */   }
/*     */   
/*     */   public static void copyMemory(long srcAddr, byte[] dst, int dstIndex, long length) {
/* 343 */     PlatformDependent0.copyMemory(null, srcAddr, dst, ARRAY_BASE_OFFSET + dstIndex, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <U, W> AtomicReferenceFieldUpdater<U, W> newAtomicReferenceFieldUpdater(Class<U> tclass, String fieldName) {
/* 353 */     if (hasUnsafe()) {
/*     */       try {
/* 355 */         return PlatformDependent0.newAtomicReferenceFieldUpdater(tclass, fieldName);
/* 356 */       } catch (Throwable ignore) {}
/*     */     }
/*     */ 
/*     */     
/* 360 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> AtomicIntegerFieldUpdater<T> newAtomicIntegerFieldUpdater(Class<?> tclass, String fieldName) {
/* 370 */     if (hasUnsafe()) {
/*     */       try {
/* 372 */         return PlatformDependent0.newAtomicIntegerFieldUpdater(tclass, fieldName);
/* 373 */       } catch (Throwable ignore) {}
/*     */     }
/*     */ 
/*     */     
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> AtomicLongFieldUpdater<T> newAtomicLongFieldUpdater(Class<?> tclass, String fieldName) {
/* 387 */     if (hasUnsafe()) {
/*     */       try {
/* 389 */         return PlatformDependent0.newAtomicLongFieldUpdater(tclass, fieldName);
/* 390 */       } catch (Throwable ignore) {}
/*     */     }
/*     */ 
/*     */     
/* 394 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Queue<T> newMpscQueue() {
/* 402 */     return new MpscLinkedQueue<T>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getClassLoader(Class<?> clazz) {
/* 409 */     return PlatformDependent0.getClassLoader(clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getContextClassLoader() {
/* 416 */     return PlatformDependent0.getContextClassLoader();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClassLoader getSystemClassLoader() {
/* 423 */     return PlatformDependent0.getSystemClassLoader();
/*     */   }
/*     */   
/*     */   private static boolean isAndroid0() {
/*     */     boolean bool;
/*     */     try {
/* 429 */       Class.forName("android.app.Application", false, getSystemClassLoader());
/* 430 */       bool = true;
/* 431 */     } catch (Exception e) {
/*     */       
/* 433 */       bool = false;
/*     */     } 
/*     */     
/* 436 */     if (bool) {
/* 437 */       logger.debug("Platform: Android");
/*     */     }
/* 439 */     return bool;
/*     */   }
/*     */   
/*     */   private static boolean isWindows0() {
/* 443 */     boolean windows = SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
/* 444 */     if (windows) {
/* 445 */       logger.debug("Platform: Windows");
/*     */     }
/* 447 */     return windows;
/*     */   }
/*     */   
/*     */   private static boolean isRoot0() {
/* 451 */     if (isWindows()) {
/* 452 */       return false;
/*     */     }
/*     */     
/* 455 */     String[] ID_COMMANDS = { "/usr/bin/id", "/bin/id", "id", "/usr/xpg4/bin/id" };
/* 456 */     Pattern UID_PATTERN = Pattern.compile("^(?:0|[1-9][0-9]*)$");
/* 457 */     for (String idCmd : ID_COMMANDS) {
/* 458 */       Process p = null;
/* 459 */       BufferedReader in = null;
/* 460 */       String uid = null;
/*     */       try {
/* 462 */         p = Runtime.getRuntime().exec(new String[] { idCmd, "-u" });
/* 463 */         in = new BufferedReader(new InputStreamReader(p.getInputStream(), CharsetUtil.US_ASCII));
/* 464 */         uid = in.readLine();
/* 465 */         in.close();
/*     */         
/*     */         while (true) {
/*     */           try {
/* 469 */             int exitCode = p.waitFor();
/* 470 */             if (exitCode != 0) {
/* 471 */               uid = null;
/*     */             }
/*     */             break;
/* 474 */           } catch (InterruptedException e) {}
/*     */         }
/*     */       
/*     */       }
/* 478 */       catch (Exception e) {
/*     */         
/* 480 */         uid = null;
/*     */       } finally {
/* 482 */         if (in != null) {
/*     */           try {
/* 484 */             in.close();
/* 485 */           } catch (IOException e) {}
/*     */         }
/*     */ 
/*     */         
/* 489 */         if (p != null) {
/*     */           try {
/* 491 */             p.destroy();
/* 492 */           } catch (Exception e) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 498 */       if (uid != null && UID_PATTERN.matcher(uid).matches()) {
/* 499 */         logger.debug("UID: {}", uid);
/* 500 */         return "0".equals(uid);
/*     */       } 
/*     */     } 
/*     */     
/* 504 */     logger.debug("Could not determine the current UID using /usr/bin/id; attempting to bind at privileged ports.");
/*     */     
/* 506 */     Pattern PERMISSION_DENIED = Pattern.compile(".*(?:denied|not.*permitted).*");
/* 507 */     for (int i = 1023; i > 0; i--) {
/* 508 */       ServerSocket ss = null;
/*     */     }
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
/* 539 */     logger.debug("UID: non-root (failed to bind at any privileged ports)");
/* 540 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int javaVersion0() {
/*     */     byte b;
/* 550 */     if (isAndroid()) {
/* 551 */       b = 6;
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/* 556 */         Class.forName("java.time.Clock", false, getClassLoader(Object.class));
/* 557 */         b = 8;
/*     */       }
/* 559 */       catch (Exception e) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 564 */           Class.forName("java.util.concurrent.LinkedTransferQueue", false, getClassLoader(BlockingQueue.class));
/* 565 */           b = 7;
/*     */         }
/* 567 */         catch (Exception exception) {
/*     */ 
/*     */ 
/*     */           
/* 571 */           b = 6;
/*     */         } 
/*     */       } 
/*     */     } 
/* 575 */     if (logger.isDebugEnabled()) {
/* 576 */       logger.debug("Java version: {}", Integer.valueOf(b));
/*     */     }
/* 578 */     return b;
/*     */   }
/*     */   
/*     */   private static boolean hasUnsafe0() {
/* 582 */     boolean tryUnsafe, noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
/* 583 */     logger.debug("-Dio.netty.noUnsafe: {}", Boolean.valueOf(noUnsafe));
/*     */     
/* 585 */     if (isAndroid()) {
/* 586 */       logger.debug("sun.misc.Unsafe: unavailable (Android)");
/* 587 */       return false;
/*     */     } 
/*     */     
/* 590 */     if (noUnsafe) {
/* 591 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.noUnsafe)");
/* 592 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 597 */     if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
/* 598 */       tryUnsafe = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
/*     */     } else {
/* 600 */       tryUnsafe = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
/*     */     } 
/*     */     
/* 603 */     if (!tryUnsafe) {
/* 604 */       logger.debug("sun.misc.Unsafe: unavailable (io.netty.tryUnsafe/org.jboss.netty.tryUnsafe)");
/* 605 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 609 */       boolean hasUnsafe = PlatformDependent0.hasUnsafe();
/* 610 */       logger.debug("sun.misc.Unsafe: {}", hasUnsafe ? "available" : "unavailable");
/* 611 */       return hasUnsafe;
/* 612 */     } catch (Throwable t) {
/*     */       
/* 614 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static long arrayBaseOffset0() {
/* 619 */     if (!hasUnsafe()) {
/* 620 */       return -1L;
/*     */     }
/*     */     
/* 623 */     return PlatformDependent0.arrayBaseOffset();
/*     */   }
/*     */   
/*     */   private static long maxDirectMemory0() {
/* 627 */     long maxDirectMemory = 0L;
/*     */     
/*     */     try {
/* 630 */       Class<?> vmClass = Class.forName("sun.misc.VM", true, getSystemClassLoader());
/* 631 */       Method m = vmClass.getDeclaredMethod("maxDirectMemory", new Class[0]);
/* 632 */       maxDirectMemory = ((Number)m.invoke(null, new Object[0])).longValue();
/* 633 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/* 637 */     if (maxDirectMemory > 0L) {
/* 638 */       return maxDirectMemory;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 644 */       Class<?> mgmtFactoryClass = Class.forName("java.lang.management.ManagementFactory", true, getSystemClassLoader());
/*     */       
/* 646 */       Class<?> runtimeClass = Class.forName("java.lang.management.RuntimeMXBean", true, getSystemClassLoader());
/*     */ 
/*     */       
/* 649 */       Object runtime = mgmtFactoryClass.getDeclaredMethod("getRuntimeMXBean", new Class[0]).invoke(null, new Object[0]);
/*     */ 
/*     */       
/* 652 */       List<String> vmArgs = (List<String>)runtimeClass.getDeclaredMethod("getInputArguments", new Class[0]).invoke(runtime, new Object[0]);
/* 653 */       for (int i = vmArgs.size() - 1; i >= 0; ) {
/* 654 */         Matcher m = MAX_DIRECT_MEMORY_SIZE_ARG_PATTERN.matcher(vmArgs.get(i));
/* 655 */         if (!m.matches()) {
/*     */           i--;
/*     */           continue;
/*     */         } 
/* 659 */         maxDirectMemory = Long.parseLong(m.group(1));
/* 660 */         switch (m.group(2).charAt(0)) { case 'K':
/*     */           case 'k':
/* 662 */             maxDirectMemory *= 1024L; break;
/*     */           case 'M':
/*     */           case 'm':
/* 665 */             maxDirectMemory *= 1048576L; break;
/*     */           case 'G':
/*     */           case 'g':
/* 668 */             maxDirectMemory *= 1073741824L;
/*     */             break; }
/*     */ 
/*     */       
/*     */       } 
/* 673 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/* 677 */     if (maxDirectMemory <= 0L) {
/* 678 */       maxDirectMemory = Runtime.getRuntime().maxMemory();
/* 679 */       logger.debug("maxDirectMemory: {} bytes (maybe)", Long.valueOf(maxDirectMemory));
/*     */     } else {
/* 681 */       logger.debug("maxDirectMemory: {} bytes", Long.valueOf(maxDirectMemory));
/*     */     } 
/*     */     
/* 684 */     return maxDirectMemory;
/*     */   }
/*     */   
/*     */   private static boolean hasJavassist0() {
/* 688 */     if (isAndroid()) {
/* 689 */       return false;
/*     */     }
/*     */     
/* 692 */     boolean noJavassist = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
/* 693 */     logger.debug("-Dio.netty.noJavassist: {}", Boolean.valueOf(noJavassist));
/*     */     
/* 695 */     if (noJavassist) {
/* 696 */       logger.debug("Javassist: unavailable (io.netty.noJavassist)");
/* 697 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 701 */       JavassistTypeParameterMatcherGenerator.generate(Object.class, getClassLoader(PlatformDependent.class));
/* 702 */       logger.debug("Javassist: available");
/* 703 */       return true;
/* 704 */     } catch (Throwable t) {
/*     */       
/* 706 */       logger.debug("Javassist: unavailable");
/* 707 */       logger.debug("You don't have Javassist in your class path or you don't have enough permission to load dynamically generated classes.  Please check the configuration for better performance.");
/*     */ 
/*     */       
/* 710 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static File tmpdir0() {
/*     */     File f;
/*     */     try {
/* 717 */       f = toDirectory(SystemPropertyUtil.get("io.netty.tmpdir"));
/* 718 */       if (f != null) {
/* 719 */         logger.debug("-Dio.netty.tmpdir: {}", f);
/* 720 */         return f;
/*     */       } 
/*     */       
/* 723 */       f = toDirectory(SystemPropertyUtil.get("java.io.tmpdir"));
/* 724 */       if (f != null) {
/* 725 */         logger.debug("-Dio.netty.tmpdir: {} (java.io.tmpdir)", f);
/* 726 */         return f;
/*     */       } 
/*     */ 
/*     */       
/* 730 */       if (isWindows()) {
/* 731 */         f = toDirectory(System.getenv("TEMP"));
/* 732 */         if (f != null) {
/* 733 */           logger.debug("-Dio.netty.tmpdir: {} (%TEMP%)", f);
/* 734 */           return f;
/*     */         } 
/*     */         
/* 737 */         String userprofile = System.getenv("USERPROFILE");
/* 738 */         if (userprofile != null) {
/* 739 */           f = toDirectory(userprofile + "\\AppData\\Local\\Temp");
/* 740 */           if (f != null) {
/* 741 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\AppData\\Local\\Temp)", f);
/* 742 */             return f;
/*     */           } 
/*     */           
/* 745 */           f = toDirectory(userprofile + "\\Local Settings\\Temp");
/* 746 */           if (f != null) {
/* 747 */             logger.debug("-Dio.netty.tmpdir: {} (%USERPROFILE%\\Local Settings\\Temp)", f);
/* 748 */             return f;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 752 */         f = toDirectory(System.getenv("TMPDIR"));
/* 753 */         if (f != null) {
/* 754 */           logger.debug("-Dio.netty.tmpdir: {} ($TMPDIR)", f);
/* 755 */           return f;
/*     */         } 
/*     */       } 
/* 758 */     } catch (Exception ignored) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 763 */     if (isWindows()) {
/* 764 */       f = new File("C:\\Windows\\Temp");
/*     */     } else {
/* 766 */       f = new File("/tmp");
/*     */     } 
/*     */     
/* 769 */     logger.warn("Failed to get the temporary directory; falling back to: {}", f);
/* 770 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   private static File toDirectory(String path) {
/* 775 */     if (path == null) {
/* 776 */       return null;
/*     */     }
/*     */     
/* 779 */     File f = new File(path);
/* 780 */     f.mkdirs();
/*     */     
/* 782 */     if (!f.isDirectory()) {
/* 783 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 787 */       return f.getAbsoluteFile();
/* 788 */     } catch (Exception ignored) {
/* 789 */       return f;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int bitMode0() {
/* 795 */     int bitMode = SystemPropertyUtil.getInt("io.netty.bitMode", 0);
/* 796 */     if (bitMode > 0) {
/* 797 */       logger.debug("-Dio.netty.bitMode: {}", Integer.valueOf(bitMode));
/* 798 */       return bitMode;
/*     */     } 
/*     */ 
/*     */     
/* 802 */     bitMode = SystemPropertyUtil.getInt("sun.arch.data.model", 0);
/* 803 */     if (bitMode > 0) {
/* 804 */       logger.debug("-Dio.netty.bitMode: {} (sun.arch.data.model)", Integer.valueOf(bitMode));
/* 805 */       return bitMode;
/*     */     } 
/* 807 */     bitMode = SystemPropertyUtil.getInt("com.ibm.vm.bitmode", 0);
/* 808 */     if (bitMode > 0) {
/* 809 */       logger.debug("-Dio.netty.bitMode: {} (com.ibm.vm.bitmode)", Integer.valueOf(bitMode));
/* 810 */       return bitMode;
/*     */     } 
/*     */ 
/*     */     
/* 814 */     String arch = SystemPropertyUtil.get("os.arch", "").toLowerCase(Locale.US).trim();
/* 815 */     if ("amd64".equals(arch) || "x86_64".equals(arch)) {
/* 816 */       bitMode = 64;
/* 817 */     } else if ("i386".equals(arch) || "i486".equals(arch) || "i586".equals(arch) || "i686".equals(arch)) {
/* 818 */       bitMode = 32;
/*     */     } 
/*     */     
/* 821 */     if (bitMode > 0) {
/* 822 */       logger.debug("-Dio.netty.bitMode: {} (os.arch: {})", Integer.valueOf(bitMode), arch);
/*     */     }
/*     */ 
/*     */     
/* 826 */     String vm = SystemPropertyUtil.get("java.vm.name", "").toLowerCase(Locale.US);
/* 827 */     Pattern BIT_PATTERN = Pattern.compile("([1-9][0-9]+)-?bit");
/* 828 */     Matcher m = BIT_PATTERN.matcher(vm);
/* 829 */     if (m.find()) {
/* 830 */       return Integer.parseInt(m.group(1));
/*     */     }
/* 832 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int addressSize0() {
/* 837 */     if (!hasUnsafe()) {
/* 838 */       return -1;
/*     */     }
/* 840 */     return PlatformDependent0.addressSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\nett\\util\internal\PlatformDependent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */