package optifine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import org.apache.commons.lang3.StringEscapeUtils;

public class NbtTagValue {
   private static final String PREFIX_PATTERN = "pattern:";
   private static final int TYPE_REGEX = 3;
   private static final String PREFIX_IREGEX = "iregex:";
   private int type = 0;
   private String[] parents = null;
   private static final String PREFIX_IPATTERN = "ipattern:";
   private static final int TYPE_IPATTERN = 2;
   private static final int TYPE_PATTERN = 1;
   private String value = null;
   private static final int TYPE_IREGEX = 4;
   private static final String PREFIX_REGEX = "regex:";
   private static final int TYPE_TEXT = 0;
   private String name = null;

   private boolean matches(NBTBase var1) {
      if (var1 == null) {
         return false;
      } else {
         String var2 = getValue(var1);
         if (var2 == null) {
            return false;
         } else {
            switch(this.type) {
            case 0:
               return var2.equals(this.value);
            case 1:
               return this.matchesPattern(var2, this.value);
            case 2:
               return this.matchesPattern(var2.toLowerCase(), this.value);
            case 3:
               return this.matchesRegex(var2, this.value);
            case 4:
               return this.matchesRegex(var2.toLowerCase(), this.value);
            default:
               throw new IllegalArgumentException(String.valueOf((new StringBuilder("Unknown NbtTagValue type: ")).append(this.type)));
            }
         }
      }
   }

   public boolean matches(NBTTagCompound var1) {
      if (var1 == null) {
         return false;
      } else {
         Object var2 = var1;

         for(int var3 = 0; var3 < this.parents.length; ++var3) {
            String var4 = this.parents[var3];
            var2 = getChildTag((NBTBase)var2, var4);
            if (var2 == null) {
               return false;
            }
         }

         if (this.name.equals("*")) {
            return this.matchesAnyChild((NBTBase)var2);
         } else {
            NBTBase var5 = getChildTag((NBTBase)var2, this.name);
            if (var5 == null) {
               return false;
            } else {
               return this.matches(var5);
            }
         }
      }
   }

   private static String getValue(NBTBase var0) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof NBTTagString) {
         NBTTagString var7 = (NBTTagString)var0;
         return var7.getString();
      } else if (var0 instanceof NBTTagInt) {
         NBTTagInt var6 = (NBTTagInt)var0;
         return Integer.toString(var6.getInt());
      } else if (var0 instanceof NBTTagByte) {
         NBTTagByte var5 = (NBTTagByte)var0;
         return Byte.toString(var5.getByte());
      } else if (var0 instanceof NBTTagShort) {
         NBTTagShort var4 = (NBTTagShort)var0;
         return Short.toString(var4.getShort());
      } else if (var0 instanceof NBTTagLong) {
         NBTTagLong var3 = (NBTTagLong)var0;
         return Long.toString(var3.getLong());
      } else if (var0 instanceof NBTTagFloat) {
         NBTTagFloat var2 = (NBTTagFloat)var0;
         return Float.toString(var2.getFloat());
      } else if (var0 instanceof NBTTagDouble) {
         NBTTagDouble var1 = (NBTTagDouble)var0;
         return Double.toString(var1.getDouble());
      } else {
         return var0.toString();
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();

      for(int var2 = 0; var2 < this.parents.length; ++var2) {
         String var3 = this.parents[var2];
         if (var2 > 0) {
            var1.append(".");
         }

         var1.append(var3);
      }

      if (var1.length() > 0) {
         var1.append(".");
      }

      var1.append(this.name);
      var1.append(" = ");
      var1.append(this.value);
      return var1.toString();
   }

   private boolean matchesAnyChild(NBTBase var1) {
      if (var1 instanceof NBTTagCompound) {
         NBTTagCompound var2 = (NBTTagCompound)var1;
         Set var3 = var2.getKeySet();
         Iterator var4 = var3.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            NBTBase var6 = var2.getTag(var5);
            if (this.matches(var6)) {
               return true;
            }
         }
      }

      if (var1 instanceof NBTTagList) {
         NBTTagList var7 = (NBTTagList)var1;
         int var8 = var7.tagCount();

         for(int var9 = 0; var9 < var8; ++var9) {
            NBTBase var10 = var7.get(var9);
            if (this.matches(var10)) {
               return true;
            }
         }
      }

      return false;
   }

   private static NBTBase getChildTag(NBTBase var0, String var1) {
      if (var0 instanceof NBTTagCompound) {
         NBTTagCompound var4 = (NBTTagCompound)var0;
         return var4.getTag(var1);
      } else if (var0 instanceof NBTTagList) {
         NBTTagList var2 = (NBTTagList)var0;
         int var3 = Config.parseInt(var1, -1);
         return var3 < 0 ? null : var2.get(var3);
      } else {
         return null;
      }
   }

   private boolean matchesPattern(String var1, String var2) {
      return StrUtils.equalsMask(var1, var2, '*', '?');
   }

   public NbtTagValue(String var1, String var2) {
      String[] var3 = Config.tokenize(var1, ".");
      this.parents = (String[])Arrays.copyOfRange(var3, 0, var3.length - 1);
      this.name = var3[var3.length - 1];
      if (var2.startsWith("pattern:")) {
         this.type = 1;
         var2 = var2.substring("pattern:".length());
      } else if (var2.startsWith("ipattern:")) {
         this.type = 2;
         var2 = var2.substring("ipattern:".length()).toLowerCase();
      } else if (var2.startsWith("regex:")) {
         this.type = 3;
         var2 = var2.substring("regex:".length());
      } else if (var2.startsWith("iregex:")) {
         this.type = 4;
         var2 = var2.substring("iregex:".length()).toLowerCase();
      } else {
         this.type = 0;
      }

      var2 = StringEscapeUtils.unescapeJava(var2);
      this.value = var2;
   }

   private boolean matchesRegex(String var1, String var2) {
      return var1.matches(var2);
   }
}
