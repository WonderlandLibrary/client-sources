package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.Arrays;
import java.util.Iterator;
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
import optifine.Config;
import optifine.MatchBlock;
import optifine.StrUtils;
import org.apache.commons.lang3.StringEscapeUtils;

public class NbtTagValue {
   private String[] parents;
   private String name;
   private int g;
   private String value;
   private static final int TYPE_TEXT = 0;
   private static final int TYPE_PATTERN = 1;
   private static final int TYPE_IPATTERN = 2;
   private static final int TYPE_REGEX = 3;
   private static final int TYPE_IREGEX = 4;
   private static final String c = "pattern:";
   private static final String l = "ipattern:";
   private static final String m = "regex:";
   private static final String h = "iregex:";

   public NbtTagValue(String var1, String var2) {
      MatchBlock.b();
      super();
      this.parents = null;
      this.name = null;
      this.g = 0;
      this.value = null;
      String[] var4 = Config.tokenize(var1, ".");
      this.parents = (String[])((String[])Arrays.copyOfRange(var4, 0, var4.length - 1));
      this.name = var4[var4.length - 1];
      if(var2.startsWith("pattern:")) {
         this.g = 1;
         var2 = var2.substring("pattern:".length());
      }

      if(var2.startsWith("ipattern:")) {
         this.g = 2;
         var2 = var2.substring("ipattern:".length()).toLowerCase();
      }

      if(var2.startsWith("regex:")) {
         this.g = 3;
         var2 = var2.substring("regex:".length());
      }

      if(var2.startsWith("iregex:")) {
         this.g = 4;
         var2 = var2.substring("iregex:".length()).toLowerCase();
      }

      this.g = 0;
      var2 = StringEscapeUtils.unescapeJava(var2);
      this.value = var2;
   }

   public boolean matches(NBTTagCompound var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return false;
      } else {
         NBTBase var3 = var1;
         int var4 = 0;
         if(var4 < this.parents.length) {
            String var5 = this.parents[var4];
            var3 = getChildTag(var1, var5);
            if(var3 == null) {
               return false;
            }

            ++var4;
         }

         if(this.name.equals("*")) {
            return this.matchesAnyChild(var3);
         } else {
            var3 = getChildTag(var3, this.name);
            return var3 == null?false:this.b(var3);
         }
      }
   }

   private boolean matchesAnyChild(NBTBase var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 instanceof NBTTagCompound) {
         NBTTagCompound var3 = (NBTTagCompound)var1;
         Iterator var4 = var3.getKeySet().iterator();
         if(var4.hasNext()) {
            String var5 = (String)var4.next();
            NBTBase var6 = var3.getTag(var5);
            if(this.b(var6)) {
               return true;
            }
         }
      }

      if(var1 instanceof NBTTagList) {
         NBTTagList var7 = (NBTTagList)var1;
         int var8 = var7.tagCount();
         int var9 = 0;
         if(var9 < var8) {
            NBTBase var11 = var7.get(var9);
            if(this.b(var11)) {
               return true;
            }

            ++var9;
         }
      }

      return false;
   }

   private static NBTBase getChildTag(NBTBase var0, String var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var0 instanceof NBTTagCompound) {
         NBTTagCompound var5 = (NBTTagCompound)var0;
         return var5.getTag(var1);
      } else if(var0 instanceof NBTTagList) {
         NBTTagList var3 = (NBTTagList)var0;
         int var4 = Config.parseInt(var1, -1);
         return null;
      } else {
         return null;
      }
   }

   private boolean b(NBTBase var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1 == null) {
         return false;
      } else {
         String var3 = getValue(var1);
         return false;
      }
   }

   private boolean matchesPattern(String var1, String var2) {
      return StrUtils.equalsMask(var1, var2, '*', '?');
   }

   private boolean matchesRegex(String var1, String var2) {
      return var1.matches(var2);
   }

   private static String getValue(NBTBase var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(var0 == null) {
         return null;
      } else if(var0 instanceof NBTTagString) {
         NBTTagString var8 = (NBTTagString)var0;
         return var8.getString();
      } else if(var0 instanceof NBTTagInt) {
         NBTTagInt var7 = (NBTTagInt)var0;
         return Integer.toString(var7.getInt());
      } else if(var0 instanceof NBTTagByte) {
         NBTTagByte var6 = (NBTTagByte)var0;
         return Byte.toString(var6.getByte());
      } else if(var0 instanceof NBTTagShort) {
         NBTTagShort var5 = (NBTTagShort)var0;
         return Short.toString(var5.getShort());
      } else if(var0 instanceof NBTTagLong) {
         NBTTagLong var4 = (NBTTagLong)var0;
         return Long.toString(var4.getLong());
      } else if(var0 instanceof NBTTagFloat) {
         NBTTagFloat var3 = (NBTTagFloat)var0;
         return Float.toString(var3.getFloat());
      } else if(var0 instanceof NBTTagDouble) {
         NBTTagDouble var2 = (NBTTagDouble)var0;
         return Double.toString(var2.getDouble());
      } else {
         return var0.toString();
      }
   }

   public String toString() {
      MatchBlock.b();
      StringBuffer var2 = new StringBuffer();
      int var3 = 0;
      if(var3 < this.parents.length) {
         String var4 = this.parents[var3];
         if(var3 > 0) {
            var2.append(".");
         }

         var2.append(var4);
         ++var3;
      }

      if(var2.length() > 0) {
         var2.append(".");
      }

      var2.append(this.name);
      var2.append(" = ");
      var2.append(this.value);
      return var2.toString();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
