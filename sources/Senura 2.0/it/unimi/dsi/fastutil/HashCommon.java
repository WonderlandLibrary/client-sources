/*     */ package it.unimi.dsi.fastutil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashCommon
/*     */ {
/*     */   private static final int INT_PHI = -1640531527;
/*     */   private static final int INV_INT_PHI = 340573321;
/*     */   private static final long LONG_PHI = -7046029254386353131L;
/*     */   private static final long INV_LONG_PHI = -1018231460777725123L;
/*     */   
/*     */   public static int murmurHash3(int x) {
/*  44 */     x ^= x >>> 16;
/*  45 */     x *= -2048144789;
/*  46 */     x ^= x >>> 13;
/*  47 */     x *= -1028477387;
/*  48 */     x ^= x >>> 16;
/*  49 */     return x;
/*     */   }
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
/*     */   public static long murmurHash3(long x) {
/*  62 */     x ^= x >>> 33L;
/*  63 */     x *= -49064778989728563L;
/*  64 */     x ^= x >>> 33L;
/*  65 */     x *= -4265267296055464877L;
/*  66 */     x ^= x >>> 33L;
/*  67 */     return x;
/*     */   }
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
/*     */   public static int mix(int x) {
/*  82 */     int h = x * -1640531527;
/*  83 */     return h ^ h >>> 16;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int invMix(int x) {
/*  92 */     return (x ^ x >>> 16) * 340573321;
/*     */   }
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
/*     */   public static long mix(long x) {
/* 106 */     long h = x * -7046029254386353131L;
/* 107 */     h ^= h >>> 32L;
/* 108 */     return h ^ h >>> 16L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long invMix(long x) {
/* 117 */     x ^= x >>> 32L;
/* 118 */     x ^= x >>> 16L;
/* 119 */     return (x ^ x >>> 32L) * -1018231460777725123L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int float2int(float f) {
/* 130 */     return Float.floatToRawIntBits(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int double2int(double d) {
/* 140 */     long l = Double.doubleToRawLongBits(d);
/* 141 */     return (int)(l ^ l >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int long2int(long l) {
/* 150 */     return (int)(l ^ l >>> 32L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int nextPowerOfTwo(int x) {
/* 161 */     if (x == 0) return 1; 
/* 162 */     x--;
/* 163 */     x |= x >> 1;
/* 164 */     x |= x >> 2;
/* 165 */     x |= x >> 4;
/* 166 */     x |= x >> 8;
/* 167 */     return (x | x >> 16) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long nextPowerOfTwo(long x) {
/* 178 */     if (x == 0L) return 1L; 
/* 179 */     x--;
/* 180 */     x |= x >> 1L;
/* 181 */     x |= x >> 2L;
/* 182 */     x |= x >> 4L;
/* 183 */     x |= x >> 8L;
/* 184 */     x |= x >> 16L;
/* 185 */     return (x | x >> 32L) + 1L;
/*     */   }
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
/*     */   public static int maxFill(int n, float f) {
/* 198 */     return Math.min((int)Math.ceil((n * f)), n - 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long maxFill(long n, float f) {
/* 210 */     return Math.min((long)Math.ceil(((float)n * f)), n - 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int arraySize(int expected, float f) {
/* 221 */     long s = Math.max(2L, nextPowerOfTwo((long)Math.ceil((expected / f))));
/* 222 */     if (s > 1073741824L) throw new IllegalArgumentException("Too large (" + expected + " expected elements with load factor " + f + ")"); 
/* 223 */     return (int)s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long bigArraySize(long expected, float f) {
/* 233 */     return nextPowerOfTwo((long)Math.ceil(((float)expected / f)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\HashCommon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */