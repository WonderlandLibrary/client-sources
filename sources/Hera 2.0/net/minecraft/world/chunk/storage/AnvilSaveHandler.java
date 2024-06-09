/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.storage.SaveHandler;
/*    */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnvilSaveHandler
/*    */   extends SaveHandler
/*    */ {
/*    */   public AnvilSaveHandler(File savesDirectory, String p_i2142_2_, boolean storePlayerdata) {
/* 16 */     super(savesDirectory, p_i2142_2_, storePlayerdata);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 24 */     File file1 = getWorldDirectory();
/*    */     
/* 26 */     if (provider instanceof net.minecraft.world.WorldProviderHell) {
/*    */       
/* 28 */       File file3 = new File(file1, "DIM-1");
/* 29 */       file3.mkdirs();
/* 30 */       return new AnvilChunkLoader(file3);
/*    */     } 
/* 32 */     if (provider instanceof net.minecraft.world.WorldProviderEnd) {
/*    */       
/* 34 */       File file2 = new File(file1, "DIM1");
/* 35 */       file2.mkdirs();
/* 36 */       return new AnvilChunkLoader(file2);
/*    */     } 
/*    */ 
/*    */     
/* 40 */     return new AnvilChunkLoader(file1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 49 */     worldInformation.setSaveVersion(19133);
/* 50 */     super.saveWorldInfoWithPlayer(worldInformation, tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {
/*    */     try {
/* 60 */       ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
/*    */     }
/* 62 */     catch (InterruptedException interruptedexception) {
/*    */       
/* 64 */       interruptedexception.printStackTrace();
/*    */     } 
/*    */     
/* 67 */     RegionFileCache.clearRegionFileReferences();
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\chunk\storage\AnvilSaveHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */