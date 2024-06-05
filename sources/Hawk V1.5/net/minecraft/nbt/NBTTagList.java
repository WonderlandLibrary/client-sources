package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase {
   private List tagList = Lists.newArrayList();
   private static final String __OBFID = "CL_00001224";
   private static final Logger LOGGER = LogManager.getLogger();
   private byte tagType = 0;

   public void appendTag(NBTBase var1) {
      if (this.tagType == 0) {
         this.tagType = var1.getId();
      } else if (this.tagType != var1.getId()) {
         LOGGER.warn("Adding mismatching tag types to tag list");
         return;
      }

      this.tagList.add(var1);
   }

   public boolean equals(Object var1) {
      if (super.equals(var1)) {
         NBTTagList var2 = (NBTTagList)var1;
         if (this.tagType == var2.tagType) {
            return this.tagList.equals(var2.tagList);
         }
      }

      return false;
   }

   void read(DataInput var1, int var2, NBTSizeTracker var3) throws IOException {
      if (var2 > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         var3.read(8L);
         this.tagType = var1.readByte();
         int var4 = var1.readInt();
         this.tagList = Lists.newArrayList();

         for(int var5 = 0; var5 < var4; ++var5) {
            NBTBase var6 = NBTBase.createNewByType(this.tagType);
            var6.read(var1, var2 + 1, var3);
            this.tagList.add(var6);
         }

      }
   }

   public int[] getIntArray(int var1) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         NBTBase var2 = (NBTBase)this.tagList.get(var1);
         return var2.getId() == 11 ? ((NBTTagIntArray)var2).getIntArray() : new int[0];
      } else {
         return new int[0];
      }
   }

   public NBTBase get(int var1) {
      return (NBTBase)(var1 >= 0 && var1 < this.tagList.size() ? (NBTBase)this.tagList.get(var1) : new NBTTagEnd());
   }

   public double getDouble(int var1) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         NBTBase var2 = (NBTBase)this.tagList.get(var1);
         return var2.getId() == 6 ? ((NBTTagDouble)var2).getDouble() : 0.0D;
      } else {
         return 0.0D;
      }
   }

   public String toString() {
      String var1 = "[";
      int var2 = 0;

      for(Iterator var3 = this.tagList.iterator(); var3.hasNext(); ++var2) {
         NBTBase var4 = (NBTBase)var3.next();
         var1 = String.valueOf((new StringBuilder(String.valueOf(var1))).append(var2).append(':').append(var4).append(','));
      }

      return String.valueOf((new StringBuilder(String.valueOf(var1))).append("]"));
   }

   public int hashCode() {
      return super.hashCode() ^ this.tagList.hashCode();
   }

   public String getStringTagAt(int var1) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         NBTBase var2 = (NBTBase)this.tagList.get(var1);
         return var2.getId() == 8 ? var2.getString() : var2.toString();
      } else {
         return "";
      }
   }

   public int getTagType() {
      return this.tagType;
   }

   void write(DataOutput var1) throws IOException {
      if (!this.tagList.isEmpty()) {
         this.tagType = ((NBTBase)this.tagList.get(0)).getId();
      } else {
         this.tagType = 0;
      }

      var1.writeByte(this.tagType);
      var1.writeInt(this.tagList.size());

      for(int var2 = 0; var2 < this.tagList.size(); ++var2) {
         ((NBTBase)this.tagList.get(var2)).write(var1);
      }

   }

   public int tagCount() {
      return this.tagList.size();
   }

   public byte getId() {
      return 9;
   }

   public boolean hasNoTags() {
      return this.tagList.isEmpty();
   }

   public NBTTagCompound getCompoundTagAt(int var1) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         NBTBase var2 = (NBTBase)this.tagList.get(var1);
         return var2.getId() == 10 ? (NBTTagCompound)var2 : new NBTTagCompound();
      } else {
         return new NBTTagCompound();
      }
   }

   public void set(int var1, NBTBase var2) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         if (this.tagType == 0) {
            this.tagType = var2.getId();
         } else if (this.tagType != var2.getId()) {
            LOGGER.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.tagList.set(var1, var2);
      } else {
         LOGGER.warn("index out of bounds to set tag in tag list");
      }

   }

   public NBTBase removeTag(int var1) {
      return (NBTBase)this.tagList.remove(var1);
   }

   public float getFloat(int var1) {
      if (var1 >= 0 && var1 < this.tagList.size()) {
         NBTBase var2 = (NBTBase)this.tagList.get(var1);
         return var2.getId() == 5 ? ((NBTTagFloat)var2).getFloat() : 0.0F;
      } else {
         return 0.0F;
      }
   }

   public NBTBase copy() {
      NBTTagList var1 = new NBTTagList();
      var1.tagType = this.tagType;
      Iterator var2 = this.tagList.iterator();

      while(var2.hasNext()) {
         NBTBase var3 = (NBTBase)var2.next();
         NBTBase var4 = var3.copy();
         var1.tagList.add(var4);
      }

      return var1;
   }
}
