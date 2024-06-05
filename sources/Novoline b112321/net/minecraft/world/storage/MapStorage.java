package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.ISaveHandler;

public class MapStorage {
   private ISaveHandler saveHandler;
   protected Map loadedDataMap = Maps.newHashMap();
   private List loadedDataList = Lists.newArrayList();
   private Map idCounts = Maps.newHashMap();

   public MapStorage(ISaveHandler var1) {
      this.saveHandler = var1;
      this.loadIdCounts();
   }

   public WorldSavedData loadData(Class var1, String var2) {
      WorldSavedData var3 = (WorldSavedData)this.loadedDataMap.get(var2);
      if(this.saveHandler != null) {
         try {
            File var4 = this.saveHandler.getMapFileFromName(var2);
            if(var4.exists()) {
               try {
                  var3 = (WorldSavedData)var1.getConstructor(new Class[]{String.class}).newInstance(new Object[]{var2});
               } catch (Exception var7) {
                  throw new RuntimeException("Failed to instantiate " + var1.toString(), var7);
               }

               FileInputStream var5 = new FileInputStream(var4);
               NBTTagCompound var6 = CompressedStreamTools.readCompressed(var5);
               var5.close();
               var3.readFromNBT(var6.getCompoundTag("data"));
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      this.loadedDataMap.put(var2, var3);
      this.loadedDataList.add(var3);
      return var3;
   }

   public void setData(String var1, WorldSavedData var2) {
      if(this.loadedDataMap.containsKey(var1)) {
         this.loadedDataList.remove(this.loadedDataMap.remove(var1));
      }

      this.loadedDataMap.put(var1, var2);
      this.loadedDataList.add(var2);
   }

   public void saveAllData() {
      for(WorldSavedData var2 : this.loadedDataList) {
         if(var2.isDirty()) {
            this.saveData(var2);
            var2.setDirty(false);
         }
      }

   }

   private void saveData(WorldSavedData var1) {
      if(this.saveHandler != null) {
         try {
            File var2 = this.saveHandler.getMapFileFromName(var1.mapName);
            NBTTagCompound var3 = new NBTTagCompound();
            var1.writeToNBT(var3);
            NBTTagCompound var4 = new NBTTagCompound();
            var4.setTag("data", var3);
            FileOutputStream var5 = new FileOutputStream(var2);
            CompressedStreamTools.writeCompressed(var4, var5);
            var5.close();
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

   }

   private void loadIdCounts() {
      try {
         this.idCounts.clear();
         if(this.saveHandler == null) {
            return;
         }

         File var1 = this.saveHandler.getMapFileFromName("idcounts");
         if(var1.exists()) {
            DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
            NBTTagCompound var3 = CompressedStreamTools.read(var2);
            var2.close();

            for(String var5 : var3.getKeySet()) {
               NBTBase var6 = var3.getTag(var5);
               if(var6 instanceof NBTTagShort) {
                  NBTTagShort var7 = (NBTTagShort)var6;
                  short var8 = var7.getShort();
                  this.idCounts.put(var5, Short.valueOf(var8));
               }
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }

   }

   public int getUniqueDataId(String var1) {
      Short var2 = (Short)this.idCounts.get(var1);
      var2 = Short.valueOf((short)0);
      this.idCounts.put(var1, var2);
      if(this.saveHandler != null) {
         try {
            File var3 = this.saveHandler.getMapFileFromName("idcounts");
            NBTTagCompound var4 = new NBTTagCompound();

            for(String var6 : this.idCounts.keySet()) {
               short var7 = ((Short)this.idCounts.get(var6)).shortValue();
               var4.setShort(var6, var7);
            }

            DataOutputStream var10 = new DataOutputStream(new FileOutputStream(var3));
            CompressedStreamTools.write(var4, (DataOutput)var10);
            var10.close();
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      return var2.shortValue();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
