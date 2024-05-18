/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import net.minecraft.world.WorldSavedData;
/*    */ 
/*    */ public class SaveDataMemoryStorage
/*    */   extends MapStorage
/*    */ {
/*    */   public SaveDataMemoryStorage() {
/*  9 */     super(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/* 18 */     return this.loadedDataMap.get(dataIdentifier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setData(String dataIdentifier, WorldSavedData data) {
/* 26 */     this.loadedDataMap.put(dataIdentifier, data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveAllData() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getUniqueDataId(String key) {
/* 41 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\storage\SaveDataMemoryStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */