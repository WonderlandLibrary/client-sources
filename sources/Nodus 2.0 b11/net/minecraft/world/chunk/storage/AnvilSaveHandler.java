/*  1:   */ package net.minecraft.world.chunk.storage;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.world.WorldProvider;
/*  6:   */ import net.minecraft.world.WorldProviderEnd;
/*  7:   */ import net.minecraft.world.WorldProviderHell;
/*  8:   */ import net.minecraft.world.storage.SaveHandler;
/*  9:   */ import net.minecraft.world.storage.ThreadedFileIOBase;
/* 10:   */ import net.minecraft.world.storage.WorldInfo;
/* 11:   */ 
/* 12:   */ public class AnvilSaveHandler
/* 13:   */   extends SaveHandler
/* 14:   */ {
/* 15:   */   private static final String __OBFID = "CL_00000581";
/* 16:   */   
/* 17:   */   public AnvilSaveHandler(File par1File, String par2Str, boolean par3)
/* 18:   */   {
/* 19:18 */     super(par1File, par2Str, par3);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
/* 23:   */   {
/* 24:26 */     File var2 = getWorldDirectory();
/* 25:29 */     if ((par1WorldProvider instanceof WorldProviderHell))
/* 26:   */     {
/* 27:31 */       File var3 = new File(var2, "DIM-1");
/* 28:32 */       var3.mkdirs();
/* 29:33 */       return new AnvilChunkLoader(var3);
/* 30:   */     }
/* 31:35 */     if ((par1WorldProvider instanceof WorldProviderEnd))
/* 32:   */     {
/* 33:37 */       File var3 = new File(var2, "DIM1");
/* 34:38 */       var3.mkdirs();
/* 35:39 */       return new AnvilChunkLoader(var3);
/* 36:   */     }
/* 37:43 */     return new AnvilChunkLoader(var2);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound)
/* 41:   */   {
/* 42:52 */     par1WorldInfo.setSaveVersion(19133);
/* 43:53 */     super.saveWorldInfoWithPlayer(par1WorldInfo, par2NBTTagCompound);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void flush()
/* 47:   */   {
/* 48:   */     try
/* 49:   */     {
/* 50:63 */       ThreadedFileIOBase.threadedIOInstance.waitForFinish();
/* 51:   */     }
/* 52:   */     catch (InterruptedException var2)
/* 53:   */     {
/* 54:67 */       var2.printStackTrace();
/* 55:   */     }
/* 56:70 */     RegionFileCache.clearRegionFileReferences();
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.AnvilSaveHandler
 * JD-Core Version:    0.7.0.1
 */