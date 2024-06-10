/*  1:   */ package net.minecraft.world.chunk.storage;
/*  2:   */ 
/*  3:   */ import java.io.DataInputStream;
/*  4:   */ import java.io.DataOutputStream;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.IOException;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.Iterator;
/* 10:   */ import java.util.Map;
/* 11:   */ 
/* 12:   */ public class RegionFileCache
/* 13:   */ {
/* 14:14 */   private static final Map regionsByFilename = new HashMap();
/* 15:   */   private static final String __OBFID = "CL_00000383";
/* 16:   */   
/* 17:   */   public static synchronized RegionFile createOrLoadRegionFile(File par0File, int par1, int par2)
/* 18:   */   {
/* 19:19 */     File var3 = new File(par0File, "region");
/* 20:20 */     File var4 = new File(var3, "r." + (par1 >> 5) + "." + (par2 >> 5) + ".mca");
/* 21:21 */     RegionFile var5 = (RegionFile)regionsByFilename.get(var4);
/* 22:23 */     if (var5 != null) {
/* 23:25 */       return var5;
/* 24:   */     }
/* 25:29 */     if (!var3.exists()) {
/* 26:31 */       var3.mkdirs();
/* 27:   */     }
/* 28:34 */     if (regionsByFilename.size() >= 256) {
/* 29:36 */       clearRegionFileReferences();
/* 30:   */     }
/* 31:39 */     RegionFile var6 = new RegionFile(var4);
/* 32:40 */     regionsByFilename.put(var4, var6);
/* 33:41 */     return var6;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static synchronized void clearRegionFileReferences()
/* 37:   */   {
/* 38:50 */     Iterator var0 = regionsByFilename.values().iterator();
/* 39:52 */     while (var0.hasNext())
/* 40:   */     {
/* 41:54 */       RegionFile var1 = (RegionFile)var0.next();
/* 42:   */       try
/* 43:   */       {
/* 44:58 */         if (var1 != null) {
/* 45:60 */           var1.close();
/* 46:   */         }
/* 47:   */       }
/* 48:   */       catch (IOException var3)
/* 49:   */       {
/* 50:65 */         var3.printStackTrace();
/* 51:   */       }
/* 52:   */     }
/* 53:69 */     regionsByFilename.clear();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public static DataInputStream getChunkInputStream(File par0File, int par1, int par2)
/* 57:   */   {
/* 58:77 */     RegionFile var3 = createOrLoadRegionFile(par0File, par1, par2);
/* 59:78 */     return var3.getChunkDataInputStream(par1 & 0x1F, par2 & 0x1F);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public static DataOutputStream getChunkOutputStream(File par0File, int par1, int par2)
/* 63:   */   {
/* 64:86 */     RegionFile var3 = createOrLoadRegionFile(par0File, par1, par2);
/* 65:87 */     return var3.getChunkDataOutputStream(par1 & 0x1F, par2 & 0x1F);
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.RegionFileCache
 * JD-Core Version:    0.7.0.1
 */