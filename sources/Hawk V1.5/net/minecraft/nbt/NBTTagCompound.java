package net.minecraft.nbt;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagCompound extends NBTBase {
   private Map tagMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00001215";
   private static final Logger logger = LogManager.getLogger();

   public short getShort(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getShort();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public int[] getIntArray(String var1) {
      try {
         return !this.hasKey(var1, 11) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(var1)).getIntArray();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 11, var3));
      }
   }

   public float getFloat(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getFloat();
      } catch (ClassCastException var3) {
         return 0.0F;
      }
   }

   public NBTTagList getTagList(String var1, int var2) {
      try {
         if (this.getTagType(var1) != 9) {
            return new NBTTagList();
         } else {
            NBTTagList var3 = (NBTTagList)this.tagMap.get(var1);
            return var3.tagCount() > 0 && var3.getTagType() != var2 ? new NBTTagList() : var3;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.createCrashReport(var1, 9, var4));
      }
   }

   public boolean hasKey(String var1, int var2) {
      byte var3 = this.getTagType(var1);
      if (var3 == var2) {
         return true;
      } else if (var2 != 99) {
         if (var3 > 0) {
         }

         return false;
      } else {
         return var3 == 1 || var3 == 2 || var3 == 3 || var3 == 4 || var3 == 5 || var3 == 6;
      }
   }

   public void merge(NBTTagCompound var1) {
      Iterator var2 = var1.tagMap.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         NBTBase var4 = (NBTBase)var1.tagMap.get(var3);
         if (var4.getId() == 10) {
            if (this.hasKey(var3, 10)) {
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

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      if (var2 > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.tagMap.clear();

         byte var4;
         while((var4 = readType(var1, var3)) != 0) {
            String var5 = readKey(var1, var3);
            var3.read((long)(16 * var5.length()));
            NBTBase var6 = readNBT(var4, var5, var1, var2 + 1, var3);
            this.tagMap.put(var5, var6);
         }

      }
   }

   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagCompound var2 = (NBTTagCompound)var1;
         return this.tagMap.entrySet().equals(var2.tagMap.entrySet());
      } else {
         return false;
      }
   }

   public void setShort(String var1, short var2) {
      this.tagMap.put(var1, new NBTTagShort(var2));
   }

   public void setBoolean(String var1, boolean var2) {
      this.setByte(var1, (byte)(var2 ? 1 : 0));
   }

   private static void writeEntry(String var0, NBTBase var1, DataOutput var2) throws IOException {
      var2.writeByte(var1.getId());
      if (var1.getId() != 0) {
         var2.writeUTF(var0);
         var1.write(var2);
      }

   }

   public double getDouble(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0.0D : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getDouble();
      } catch (ClassCastException var3) {
         return 0.0D;
      }
   }

   public void setByteArray(String var1, byte[] var2) {
      this.tagMap.put(var1, new NBTTagByteArray(var2));
   }

   public void removeTag(String var1) {
      this.tagMap.remove(var1);
   }

   private static byte readType(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readByte();
   }

   public void setFloat(String var1, float var2) {
      this.tagMap.put(var1, new NBTTagFloat(var2));
   }

   public byte[] getByteArray(String var1) {
      try {
         return !this.hasKey(var1, 7) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(var1)).getByteArray();
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 7, var3));
      }
   }

   static NBTBase readNBT(byte var0, String var1, DataInput var2, int var3, NBTSizeTracker var4) {
      NBTBase var5 = NBTBase.createNewByType(var0);

      try {
         var5.read(var2, var3, var4);
         return var5;
      } catch (IOException var9) {
         CrashReport var7 = CrashReport.makeCrashReport(var9, "Loading NBT data");
         CrashReportCategory var8 = var7.makeCategory("NBT Tag");
         var8.addCrashSection("Tag name", var1);
         var8.addCrashSection("Tag type", var0);
         throw new ReportedException(var7);
      }
   }

   public String toString() {
      String var1 = "{";

      String var2;
      for(Iterator var3 = this.tagMap.keySet().iterator(); var3.hasNext(); var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(var2).append(':').append(this.tagMap.get(var2)).append(','))) {
         var2 = (String)var3.next();
      }

      return String.valueOf((new StringBuilder(String.valueOf(var1))).append("}"));
   }

   static Map access$0(NBTTagCompound var0) {
      return var0.tagMap;
   }

   public int getInteger(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getInt();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public NBTTagCompound getCompoundTag(String var1) {
      try {
         return !this.hasKey(var1, 10) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(var1);
      } catch (ClassCastException var3) {
         throw new ReportedException(this.createCrashReport(var1, 10, var3));
      }
   }

   public void setTag(String var1, NBTBase var2) {
      this.tagMap.put(var1, var2);
   }

   public boolean hasKey(String var1) {
      return this.tagMap.containsKey(var1);
   }

   private static String readKey(DataInput var0, NBTSizeTracker var1) throws IOException {
      return var0.readUTF();
   }

   public Set getKeySet() {
      return this.tagMap.keySet();
   }

   public NBTBase getTag(String var1) {
      return (NBTBase)this.tagMap.get(var1);
   }

   public void setLong(String var1, long var2) {
      this.tagMap.put(var1, new NBTTagLong(var2));
   }

   public byte getId() {
      return 10;
   }

   public void setByte(String var1, byte var2) {
      this.tagMap.put(var1, new NBTTagByte(var2));
   }

   public void setInteger(String var1, int var2) {
      this.tagMap.put(var1, new NBTTagInt(var2));
   }

   public boolean getBoolean(String var1) {
      return this.getByte(var1) != 0;
   }

   public void setIntArray(String var1, int[] var2) {
      this.tagMap.put(var1, new NBTTagIntArray(var2));
   }

   public byte getByte(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getByte();
      } catch (ClassCastException var3) {
         return 0;
      }
   }

   public long getLong(String var1) {
      try {
         return !this.hasKey(var1, 99) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(var1)).getLong();
      } catch (ClassCastException var3) {
         return 0L;
      }
   }

   public void setDouble(String var1, double var2) {
      this.tagMap.put(var1, new NBTTagDouble(var2));
   }

   void write(DataOutput var1) throws IOException {
      Iterator var2 = this.tagMap.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         NBTBase var4 = (NBTBase)this.tagMap.get(var3);
         writeEntry(var3, var4, var1);
      }

      var1.writeByte(0);
   }

   private CrashReport createCrashReport(String var1, int var2, ClassCastException var3) {
      CrashReport var4 = CrashReport.makeCrashReport(var3, "Reading NBT data");
      CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
      var5.addCrashSectionCallable("Tag type found", new Callable(this, var1) {
         private final String val$key;
         private static final String __OBFID = "CL_00001216";
         final NBTTagCompound this$0;

         public String call() {
            return NBTBase.NBT_TYPES[((NBTBase)NBTTagCompound.access$0(this.this$0).get(this.val$key)).getId()];
         }

         {
            this.this$0 = var1;
            this.val$key = var2;
         }

         public Object call() throws Exception {
            return this.call();
         }
      });
      var5.addCrashSectionCallable("Tag type expected", new Callable(this, var2) {
         private final int val$expectedType;
         final NBTTagCompound this$0;
         private static final String __OBFID = "CL_00001217";

         {
            this.this$0 = var1;
            this.val$expectedType = var2;
         }

         public Object call() throws Exception {
            return this.call();
         }

         public String call() {
            return NBTBase.NBT_TYPES[this.val$expectedType];
         }
      });
      var5.addCrashSection("Tag name", var1);
      return var4;
   }

   public NBTBase copy() {
      NBTTagCompound var1 = new NBTTagCompound();
      Iterator var2 = this.tagMap.keySet().iterator();

      while(var2.hasNext()) {
         String var3 = (String)var2.next();
         var1.setTag(var3, ((NBTBase)this.tagMap.get(var3)).copy());
      }

      return var1;
   }

   public byte getTagType(String var1) {
      NBTBase var2 = (NBTBase)this.tagMap.get(var1);
      return var2 != null ? var2.getId() : 0;
   }

   public void setString(String var1, String var2) {
      this.tagMap.put(var1, new NBTTagString(var2));
   }

   public String getString(String var1) {
      try {
         return !this.hasKey(var1, 8) ? "" : ((NBTBase)this.tagMap.get(var1)).getString();
      } catch (ClassCastException var3) {
         return "";
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.tagMap.hashCode();
   }

   public boolean hasNoTags() {
      return this.tagMap.isEmpty();
   }
}
