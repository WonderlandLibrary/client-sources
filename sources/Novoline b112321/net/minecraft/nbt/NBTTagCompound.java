package net.minecraft.nbt;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTBase$NBTPrimitive;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound$1;
import net.minecraft.nbt.NBTTagCompound$2;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ReportedException;

public class NBTTagCompound extends NBTBase {
   private Map tagMap = Maps.newHashMap();

   void write(DataOutput var1) throws IOException {
      for(String var3 : this.tagMap.keySet()) {
         NBTBase var4 = (NBTBase)this.tagMap.get(var3);
         writeEntry(var3, var4, var1);
      }

      var1.writeByte(0);
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      var3.read(384L);
      if(var2 > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.tagMap.clear();

         byte var4;
         while((var4 = readType(var1, var3)) != 0) {
            String var5 = readKey(var1, var3);
            var3.read((long)(224 + 16 * var5.length()));
            NBTBase var6 = readNBT(var4, var5, var1, var2 + 1, var3);
            if(this.tagMap.put(var5, var6) != null) {
               var3.read(288L);
            }
         }

      }
   }

   public Set getKeySet() {
      return this.tagMap.keySet();
   }

   public byte getId() {
      return (byte)10;
   }

   public void setTag(String var1, NBTBase var2) {
      this.tagMap.put(var1, var2);
   }

   public void setByte(String var1, byte var2) {
      this.tagMap.put(var1, new NBTTagByte(var2));
   }

   public void setShort(String var1, short var2) {
      this.tagMap.put(var1, new NBTTagShort(var2));
   }

   public void setInteger(String var1, int var2) {
      this.tagMap.put(var1, new NBTTagInt(var2));
   }

   public void setLong(String var1, long var2) {
      this.tagMap.put(var1, new NBTTagLong(var2));
   }

   public void setFloat(String var1, float var2) {
      this.tagMap.put(var1, new NBTTagFloat(var2));
   }

   public void setDouble(String var1, double var2) {
      this.tagMap.put(var1, new NBTTagDouble(var2));
   }

   public void setString(String var1, String var2) {
      this.tagMap.put(var1, new NBTTagString(var2));
   }

   public void setByteArray(String var1, byte[] var2) {
      this.tagMap.put(var1, new NBTTagByteArray(var2));
   }

   public void setIntArray(String var1, int[] var2) {
      this.tagMap.put(var1, new NBTTagIntArray(var2));
   }

   public void setBoolean(String var1, boolean var2) {
      this.setByte(var1, (byte)1);
   }

   public NBTBase getTag(String var1) {
      return (NBTBase)this.tagMap.get(var1);
   }

   public byte getTagId(String var1) {
      NBTBase var2 = (NBTBase)this.tagMap.get(var1);
      return var2.getId();
   }

   public boolean hasKey(String var1) {
      return this.tagMap.containsKey(var1);
   }

   public boolean hasKey(String var1, int var2) {
      byte var3 = this.getTagId(var1);
      return var3 == var2?true:(var2 != 99?false:var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6);
   }

   public byte getByte(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getByte();
      } catch (ClassCastException var3) {
         return (byte)0;
      }
   }

   public short getShort(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getShort();
      } catch (ClassCastException var3) {
         return (short)0;
      }
   }

   public int getInteger(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getInt();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public Integer getInteger(String var1, Integer var2) {
      NBTBase var3 = (NBTBase)this.tagMap.get(var1);
      return var3.getId() != 3?var2:Integer.valueOf(((NBTTagInt)var3).getInt());
   }

   public long getLong(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0L:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getLong();
      } catch (ClassCastException var3) {
         return 0L;
      }
   }

   public Long getLong(String var1, Long var2) {
      NBTBase var3 = (NBTBase)this.tagMap.get(var1);
      return var3.getId() != 4?var2:Long.valueOf(((NBTTagLong)var3).getLong());
   }

   public float getFloat(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0.0F:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getFloat();
      } catch (ClassCastException var3) {
         return 0.0F;
      }
   }

   public double getDouble(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 99;

      try {
         return !var10000.hasKey(var10001, var10002)?0.0D:((NBTBase$NBTPrimitive)this.tagMap.get(var1)).getDouble();
      } catch (ClassCastException var3) {
         return 0.0D;
      }
   }

   public String getString(String var1) {
      NBTTagCompound var10000 = this;
      String var10001 = var1;
      byte var10002 = 8;

      try {
         return !var10000.hasKey(var10001, var10002)?"":((NBTBase)this.tagMap.get(var1)).getString();
      } catch (ClassCastException var3) {
         return "";
      }
   }

   public String getString(String var1, String var2) {
      NBTBase var3 = (NBTBase)this.tagMap.get(var1);
      return var3.getId() != 8?var2:var3.getString();
   }

   public byte[] getByteArray(String var1) {
      try {
         return !this.hasKey(var1, 7)?new byte[0]:((NBTTagByteArray)this.tagMap.get(var1)).getByteArray();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 7, var3));
      }
   }

   public int[] getIntArray(String var1) {
      try {
         return !this.hasKey(var1, 11)?new int[0]:((NBTTagIntArray)this.tagMap.get(var1)).getIntArray();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 11, var3));
      }
   }

   public NBTTagCompound getCompoundTag(String var1) {
      try {
         return !this.hasKey(var1, 10)?new NBTTagCompound():(NBTTagCompound)this.tagMap.get(var1);
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 10, var3));
      }
   }

   public NBTTagCompound getCompoundTag(String var1, NBTTagCompound var2) {
      NBTBase var3 = (NBTBase)this.tagMap.get(var1);
      return var3.getId() != 10?var2:(NBTTagCompound)var3;
   }

   public NBTTagList getTagList(String var1, int var2) {
      NBTTagList var3;
      NBTTagList var10000;
      try {
         if(this.getTagId(var1) != 9) {
            return new NBTTagList();
         }

         var3 = (NBTTagList)this.tagMap.get(var1);
         if(var3.tagCount() > 0 && var3.getTagType() != var2) {
            var10000 = new NBTTagList();
            return var10000;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.createCrashReport(var1, 9, var4));
      }

      var10000 = var3;
      return var10000;
   }

   public boolean getBoolean(String var1) {
      return this.getByte(var1) != 0;
   }

   public void removeTag(String var1) {
      this.tagMap.remove(var1);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder("{");

      for(Entry var3 : this.tagMap.entrySet()) {
         if(var1.length() != 1) {
            var1.append(',');
         }

         var1.append((String)var3.getKey()).append(':').append(var3.getValue());
      }

      return var1.append('}').toString();
   }

   public boolean hasNoTags() {
      return this.tagMap.isEmpty();
   }

   private CrashReport createCrashReport(String var1, int var2, ClassCastException var3) {
      CrashReport var4 = CrashReport.makeCrashReport(var3, "Reading NBT data");
      CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
      var5.addCrashSectionCallable("Tag type found", new NBTTagCompound$1(this, var1));
      var5.addCrashSectionCallable("Tag type expected", new NBTTagCompound$2(this, var2));
      var5.addCrashSection("Tag name", var1);
      return var4;
   }

   public NBTBase copy() {
      NBTTagCompound var1 = new NBTTagCompound();

      for(String var3 : this.tagMap.keySet()) {
         var1.setTag(var3, ((NBTBase)this.tagMap.get(var3)).copy());
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if(super.equals(var1)) {
         NBTTagCompound var2 = (NBTTagCompound)var1;
         return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.tagMap.hashCode();
   }

   private static void writeEntry(String var0, NBTBase var1, DataOutput var2) throws IOException {
      var2.writeByte(var1.getId());
      if(var1.getId() != 0) {
         var2.writeUTF(var0);
         var1.write(var2);
      }

   }

   private static byte readType(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readByte();
   }

   private static String readKey(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readUTF();
   }

   static NBTBase readNBT(byte var0, String var1, DataInput var2, int var3, NBTSizeTracker var4) throws IOException {
      NBTBase var5 = NBTBase.createNewByType(var0);
      NBTBase var10000 = var5;
      DataInput var10001 = var2;
      int var10002 = var3;
      NBTSizeTracker var10003 = var4;

      try {
         var10000.read(var10001, var10002, var10003);
         return var5;
      } catch (IOException var9) {
         CrashReport var7 = CrashReport.makeCrashReport(var9, "Loading NBT data");
         CrashReportCategory var8 = var7.makeCategory("NBT Tag");
         var8.addCrashSection("Tag name", var1);
         var8.addCrashSection("Tag type", Byte.valueOf(var0));
         throw new ReportedException(var7);
      }
   }

   public void merge(NBTTagCompound var1) {
      for(String var3 : var1.tagMap.keySet()) {
         NBTBase var4 = (NBTBase)var1.tagMap.get(var3);
         if(var4.getId() == 10) {
            if(this.hasKey(var3, 10)) {
               NBTTagCompound var5 = this.getCompoundTag(var3);
               var5.merge((NBTTagCompound)var4);
            } else {
               this.setTag(var3, var4.copy());
            }
         } else {
            this.setTag(var3, var4.copy());
         }
      }

   }

   static Map access$000(NBTTagCompound var0) {
      return var0.tagMap;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
