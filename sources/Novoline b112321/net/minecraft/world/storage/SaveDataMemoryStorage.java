package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;

public class SaveDataMemoryStorage extends MapStorage {
   public SaveDataMemoryStorage() {
      super((ISaveHandler)null);
   }

   public WorldSavedData loadData(Class var1, String var2) {
      return (WorldSavedData)this.loadedDataMap.get(var2);
   }

   public void setData(String var1, WorldSavedData var2) {
      this.loadedDataMap.put(var1, var2);
   }

   public void saveAllData() {
   }

   public int getUniqueDataId(String var1) {
      return 0;
   }
}
