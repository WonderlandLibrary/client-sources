/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.FileInputStream;
/*   5:    */ import java.io.FileOutputStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import net.minecraft.client.AnvilConverterException;
/*   9:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  10:    */ import net.minecraft.nbt.NBTTagCompound;
/*  11:    */ import net.minecraft.util.IProgressUpdate;
/*  12:    */ import org.apache.logging.log4j.LogManager;
/*  13:    */ import org.apache.logging.log4j.Logger;
/*  14:    */ 
/*  15:    */ public class SaveFormatOld
/*  16:    */   implements ISaveFormat
/*  17:    */ {
/*  18: 17 */   private static final Logger logger = ;
/*  19:    */   protected final File savesDirectory;
/*  20:    */   private static final String __OBFID = "CL_00000586";
/*  21:    */   
/*  22:    */   public SaveFormatOld(File par1File)
/*  23:    */   {
/*  24: 27 */     if (!par1File.exists()) {
/*  25: 29 */       par1File.mkdirs();
/*  26:    */     }
/*  27: 32 */     this.savesDirectory = par1File;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List getSaveList()
/*  31:    */     throws AnvilConverterException
/*  32:    */   {
/*  33: 37 */     ArrayList var1 = new ArrayList();
/*  34: 39 */     for (int var2 = 0; var2 < 5; var2++)
/*  35:    */     {
/*  36: 41 */       String var3 = "World" + (var2 + 1);
/*  37: 42 */       WorldInfo var4 = getWorldInfo(var3);
/*  38: 44 */       if (var4 != null) {
/*  39: 46 */         var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
/*  40:    */       }
/*  41:    */     }
/*  42: 50 */     return var1;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void flushCache() {}
/*  46:    */   
/*  47:    */   public WorldInfo getWorldInfo(String par1Str)
/*  48:    */   {
/*  49: 60 */     File var2 = new File(this.savesDirectory, par1Str);
/*  50: 62 */     if (!var2.exists()) {
/*  51: 64 */       return null;
/*  52:    */     }
/*  53: 68 */     File var3 = new File(var2, "level.dat");
/*  54: 72 */     if (var3.exists()) {
/*  55:    */       try
/*  56:    */       {
/*  57: 76 */         NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
/*  58: 77 */         NBTTagCompound var5 = var4.getCompoundTag("Data");
/*  59: 78 */         return new WorldInfo(var5);
/*  60:    */       }
/*  61:    */       catch (Exception var7)
/*  62:    */       {
/*  63: 82 */         logger.error("Exception reading " + var3, var7);
/*  64:    */       }
/*  65:    */     }
/*  66: 86 */     var3 = new File(var2, "level.dat_old");
/*  67: 88 */     if (var3.exists()) {
/*  68:    */       try
/*  69:    */       {
/*  70: 92 */         NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
/*  71: 93 */         NBTTagCompound var5 = var4.getCompoundTag("Data");
/*  72: 94 */         return new WorldInfo(var5);
/*  73:    */       }
/*  74:    */       catch (Exception var6)
/*  75:    */       {
/*  76: 98 */         logger.error("Exception reading " + var3, var6);
/*  77:    */       }
/*  78:    */     }
/*  79:102 */     return null;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void renameWorld(String par1Str, String par2Str)
/*  83:    */   {
/*  84:113 */     File var3 = new File(this.savesDirectory, par1Str);
/*  85:115 */     if (var3.exists())
/*  86:    */     {
/*  87:117 */       File var4 = new File(var3, "level.dat");
/*  88:119 */       if (var4.exists()) {
/*  89:    */         try
/*  90:    */         {
/*  91:123 */           NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
/*  92:124 */           NBTTagCompound var6 = var5.getCompoundTag("Data");
/*  93:125 */           var6.setString("LevelName", par2Str);
/*  94:126 */           CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
/*  95:    */         }
/*  96:    */         catch (Exception var7)
/*  97:    */         {
/*  98:130 */           var7.printStackTrace();
/*  99:    */         }
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean deleteWorldDirectory(String par1Str)
/* 105:    */   {
/* 106:142 */     File var2 = new File(this.savesDirectory, par1Str);
/* 107:144 */     if (!var2.exists()) {
/* 108:146 */       return true;
/* 109:    */     }
/* 110:150 */     logger.info("Deleting level " + par1Str);
/* 111:152 */     for (int var3 = 1; var3 <= 5; var3++)
/* 112:    */     {
/* 113:154 */       logger.info("Attempt " + var3 + "...");
/* 114:156 */       if (deleteFiles(var2.listFiles())) {
/* 115:    */         break;
/* 116:    */       }
/* 117:161 */       logger.warn("Unsuccessful in deleting contents.");
/* 118:163 */       if (var3 < 5) {
/* 119:    */         try
/* 120:    */         {
/* 121:167 */           Thread.sleep(500L);
/* 122:    */         }
/* 123:    */         catch (InterruptedException localInterruptedException) {}
/* 124:    */       }
/* 125:    */     }
/* 126:176 */     return var2.delete();
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected static boolean deleteFiles(File[] par0ArrayOfFile)
/* 130:    */   {
/* 131:186 */     for (int var1 = 0; var1 < par0ArrayOfFile.length; var1++)
/* 132:    */     {
/* 133:188 */       File var2 = par0ArrayOfFile[var1];
/* 134:189 */       logger.debug("Deleting " + var2);
/* 135:191 */       if ((var2.isDirectory()) && (!deleteFiles(var2.listFiles())))
/* 136:    */       {
/* 137:193 */         logger.warn("Couldn't delete directory " + var2);
/* 138:194 */         return false;
/* 139:    */       }
/* 140:197 */       if (!var2.delete())
/* 141:    */       {
/* 142:199 */         logger.warn("Couldn't delete file " + var2);
/* 143:200 */         return false;
/* 144:    */       }
/* 145:    */     }
/* 146:204 */     return true;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public ISaveHandler getSaveLoader(String par1Str, boolean par2)
/* 150:    */   {
/* 151:212 */     return new SaveHandler(this.savesDirectory, par1Str, par2);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean isOldMapFormat(String par1Str)
/* 155:    */   {
/* 156:220 */     return false;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate)
/* 160:    */   {
/* 161:228 */     return false;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean canLoadWorld(String par1Str)
/* 165:    */   {
/* 166:236 */     File var2 = new File(this.savesDirectory, par1Str);
/* 167:237 */     return var2.isDirectory();
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.SaveFormatOld
 * JD-Core Version:    0.7.0.1
 */