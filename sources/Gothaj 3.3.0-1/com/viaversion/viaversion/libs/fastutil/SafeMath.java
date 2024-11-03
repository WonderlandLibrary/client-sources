package com.viaversion.viaversion.libs.fastutil;

public final class SafeMath {
   private SafeMath() {
   }

   public static char safeIntToChar(int value) {
      if (value >= 0 && 65535 >= value) {
         return (char)value;
      } else {
         throw new IllegalArgumentException(value + " can't be represented as char");
      }
   }

   public static byte safeIntToByte(int value) {
      if (value >= -128 && 127 >= value) {
         return (byte)value;
      } else {
         throw new IllegalArgumentException(value + " can't be represented as byte (out of range)");
      }
   }

   public static short safeIntToShort(int value) {
      if (value >= -32768 && 32767 >= value) {
         return (short)value;
      } else {
         throw new IllegalArgumentException(value + " can't be represented as short (out of range)");
      }
   }

   public static char safeLongToChar(long value) {
      if (value >= 0L && 65535L >= value) {
         return (char)((int)value);
      } else {
         throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
      }
   }

   public static byte safeLongToByte(long value) {
      if (value >= -128L && 127L >= value) {
         return (byte)((int)value);
      } else {
         throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
      }
   }

   public static short safeLongToShort(long value) {
      if (value >= -32768L && 32767L >= value) {
         return (short)((int)value);
      } else {
         throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
      }
   }

   public static int safeLongToInt(long value) {
      if (value >= -2147483648L && 2147483647L >= value) {
         return (int)value;
      } else {
         throw new IllegalArgumentException(value + " can't be represented as int (out of range)");
      }
   }

   public static float safeDoubleToFloat(double value) {
      if (Double.isNaN(value)) {
         return Float.NaN;
      } else if (Double.isInfinite(value)) {
         return value < 0.0 ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY;
      } else if (!(value < -Float.MAX_VALUE) && !(Float.MAX_VALUE < value)) {
         float floatValue = (float)value;
         if ((double)floatValue != value) {
            throw new IllegalArgumentException(value + " can't be represented as float (imprecise)");
         } else {
            return floatValue;
         }
      } else {
         throw new IllegalArgumentException(value + " can't be represented as float (out of range)");
      }
   }
}
