/*    */ package it.unimi.dsi.fastutil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SafeMath
/*    */ {
/*    */   public static char safeIntToChar(int value) {
/* 23 */     if (value < 0 || 65535 < value) throw new IllegalArgumentException(value + " can't be represented as char"); 
/* 24 */     return (char)value;
/*    */   }
/*    */   
/*    */   public static byte safeIntToByte(int value) {
/* 28 */     if (value < -128 || 127 < value) throw new IllegalArgumentException(value + " can't be represented as byte (out of range)"); 
/* 29 */     return (byte)value;
/*    */   }
/*    */   
/*    */   public static short safeIntToShort(int value) {
/* 33 */     if (value < -32768 || 32767 < value) throw new IllegalArgumentException(value + " can't be represented as short (out of range)"); 
/* 34 */     return (short)value;
/*    */   }
/*    */   
/*    */   public static int safeLongToInt(long value) {
/* 38 */     if (value < -2147483648L || 2147483647L < value) throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
/* 39 */     return (int)value;
/*    */   }
/*    */   
/*    */   public static float safeDoubleToFloat(double value) {
/* 43 */     if (Double.isNaN(value)) return Float.NaN; 
/* 44 */     if (Double.isInfinite(value)) return (value < 0.0D) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY; 
/* 45 */     if (value < 1.401298464324817E-45D || 3.4028234663852886E38D < value) throw new IllegalArgumentException(value + " can't be represented as float (out of range)"); 
/* 46 */     float floatValue = (float)value;
/* 47 */     if (floatValue != value) throw new IllegalArgumentException(value + " can't be represented as float (imprecise)"); 
/* 48 */     return floatValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\i\\unimi\dsi\fastutil\SafeMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */