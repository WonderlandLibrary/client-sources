package org.spongepowered.asm.util;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionNumber implements Comparable, Serializable {
   private static final long serialVersionUID = 1L;
   public static final VersionNumber NONE = new VersionNumber();
   private static final Pattern PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
   private final long value;
   private final String suffix;

   private VersionNumber() {
      this.value = 0L;
      this.suffix = "";
   }

   private VersionNumber(short[] var1) {
      this(var1, (String)null);
   }

   private VersionNumber(short[] var1, String var2) {
      this.value = pack(var1);
      this.suffix = var2 != null ? var2 : "";
   }

   private VersionNumber(short var1, short var2, short var3, short var4) {
      this(var1, var2, var3, var4, (String)null);
   }

   private VersionNumber(short var1, short var2, short var3, short var4, String var5) {
      this.value = pack(var1, var2, var3, var4);
      this.suffix = var5 != null ? var5 : "";
   }

   public String toString() {
      short[] var1 = unpack(this.value);
      return String.format("%d.%d%3$s%4$s%5$s", var1[0], var1[1], (this.value & 2147483647L) > 0L ? String.format(".%d", var1[2]) : "", (this.value & 32767L) > 0L ? String.format(".%d", var1[3]) : "", this.suffix);
   }

   public int compareTo(VersionNumber var1) {
      if (var1 == null) {
         return 1;
      } else {
         long var2 = this.value - var1.value;
         return var2 > 0L ? 1 : (var2 < 0L ? -1 : 0);
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof VersionNumber)) {
         return false;
      } else {
         return ((VersionNumber)var1).value == this.value;
      }
   }

   public int hashCode() {
      return (int)(this.value >> 32) ^ (int)(this.value & 4294967295L);
   }

   private static long pack(short... var0) {
      return (long)var0[0] << 48 | (long)var0[1] << 32 | (long)(var0[2] << 16) | (long)var0[3];
   }

   private static short[] unpack(long var0) {
      return new short[]{(short)((int)(var0 >> 48)), (short)((int)(var0 >> 32 & 32767L)), (short)((int)(var0 >> 16 & 32767L)), (short)((int)(var0 & 32767L))};
   }

   public static VersionNumber parse(String var0) {
      return parse(var0, NONE);
   }

   public static VersionNumber parse(String var0, String var1) {
      return parse(var0, parse(var1));
   }

   private static VersionNumber parse(String var0, VersionNumber var1) {
      if (var0 == null) {
         return var1;
      } else {
         Matcher var2 = PATTERN.matcher(var0);
         if (!var2.matches()) {
            return var1;
         } else {
            short[] var3 = new short[4];

            for(int var4 = 0; var4 < 4; ++var4) {
               String var5 = var2.group(var4 + 1);
               if (var5 != null) {
                  int var6 = Integer.parseInt(var5);
                  if (var6 > 32767) {
                     throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + var6);
                  }

                  var3[var4] = (short)var6;
               }
            }

            return new VersionNumber(var3, var2.group(5));
         }
      }
   }

   public int compareTo(Object var1) {
      return this.compareTo((VersionNumber)var1);
   }
}
