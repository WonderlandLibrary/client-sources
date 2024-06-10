/*  1:   */ package net.minecraft.world.storage;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import net.minecraft.nbt.NBTTagCompound;
/*  5:   */ import net.minecraft.world.MinecraftException;
/*  6:   */ import net.minecraft.world.WorldProvider;
/*  7:   */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*  8:   */ 
/*  9:   */ public class SaveHandlerMP
/* 10:   */   implements ISaveHandler
/* 11:   */ {
/* 12:   */   private static final String __OBFID = "CL_00000602";
/* 13:   */   
/* 14:   */   public WorldInfo loadWorldInfo()
/* 15:   */   {
/* 16:18 */     return null;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void checkSessionLock()
/* 20:   */     throws MinecraftException
/* 21:   */   {}
/* 22:   */   
/* 23:   */   public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
/* 24:   */   {
/* 25:31 */     return null;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {}
/* 29:   */   
/* 30:   */   public void saveWorldInfo(WorldInfo par1WorldInfo) {}
/* 31:   */   
/* 32:   */   public IPlayerFileData getSaveHandler()
/* 33:   */   {
/* 34:49 */     return null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void flush() {}
/* 38:   */   
/* 39:   */   public File getMapFileFromName(String par1Str)
/* 40:   */   {
/* 41:62 */     return null;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public String getWorldDirectoryName()
/* 45:   */   {
/* 46:70 */     return "none";
/* 47:   */   }
/* 48:   */   
/* 49:   */   public File getWorldDirectory()
/* 50:   */   {
/* 51:78 */     return null;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.SaveHandlerMP
 * JD-Core Version:    0.7.0.1
 */