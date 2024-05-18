/*    */ package net.minecraft.world;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WorldSavedData
/*    */ {
/*    */   public final String mapName;
/*    */   private boolean dirty;
/*    */   
/*    */   public WorldSavedData(String name) {
/* 15 */     this.mapName = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void readFromNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void writeToNBT(NBTTagCompound paramNBTTagCompound);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void markDirty() {
/* 33 */     setDirty(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDirty(boolean isDirty) {
/* 41 */     this.dirty = isDirty;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDirty() {
/* 49 */     return this.dirty;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\WorldSavedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */