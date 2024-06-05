package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

public class SaveDataMemoryStorage extends MapStorage {
   private static final String __OBFID = "CL_00001963";

   public void saveAllData() {
   }

   public SaveDataMemoryStorage() {
      super((ISaveHandler)null);
   }

   public void setData(String var1, WorldSavedData var2) {
      this.loadedDataMap.put(var1, var2);
   }

   public WorldSavedData loadData(Class var1, String var2) {
      return (WorldSavedData)this.loadedDataMap.get(var2);
   }

   public int getUniqueDataId(String var1) {
      return 0;
   }
}
