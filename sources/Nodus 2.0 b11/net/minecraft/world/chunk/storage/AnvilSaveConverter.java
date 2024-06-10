/*   1:    */ package net.minecraft.world.chunk.storage;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FilenameFilter;
/*   7:    */ import java.io.IOException;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import net.minecraft.client.AnvilConverterException;
/*  14:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  15:    */ import net.minecraft.nbt.NBTTagCompound;
/*  16:    */ import net.minecraft.util.IProgressUpdate;
/*  17:    */ import net.minecraft.util.MathHelper;
/*  18:    */ import net.minecraft.world.WorldType;
/*  19:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  20:    */ import net.minecraft.world.biome.WorldChunkManager;
/*  21:    */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*  22:    */ import net.minecraft.world.storage.ISaveHandler;
/*  23:    */ import net.minecraft.world.storage.SaveFormatComparator;
/*  24:    */ import net.minecraft.world.storage.SaveFormatOld;
/*  25:    */ import net.minecraft.world.storage.WorldInfo;
/*  26:    */ import org.apache.logging.log4j.LogManager;
/*  27:    */ import org.apache.logging.log4j.Logger;
/*  28:    */ 
/*  29:    */ public class AnvilSaveConverter
/*  30:    */   extends SaveFormatOld
/*  31:    */ {
/*  32: 31 */   private static final Logger logger = ;
/*  33:    */   private static final String __OBFID = "CL_00000582";
/*  34:    */   
/*  35:    */   public AnvilSaveConverter(File par1File)
/*  36:    */   {
/*  37: 36 */     super(par1File);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getSaveList()
/*  41:    */     throws AnvilConverterException
/*  42:    */   {
/*  43: 41 */     if ((this.savesDirectory != null) && (this.savesDirectory.exists()) && (this.savesDirectory.isDirectory()))
/*  44:    */     {
/*  45: 43 */       ArrayList var1 = new ArrayList();
/*  46: 44 */       File[] var2 = this.savesDirectory.listFiles();
/*  47: 45 */       File[] var3 = var2;
/*  48: 46 */       int var4 = var2.length;
/*  49: 48 */       for (int var5 = 0; var5 < var4; var5++)
/*  50:    */       {
/*  51: 50 */         File var6 = var3[var5];
/*  52: 52 */         if (var6.isDirectory())
/*  53:    */         {
/*  54: 54 */           String var7 = var6.getName();
/*  55: 55 */           WorldInfo var8 = getWorldInfo(var7);
/*  56: 57 */           if ((var8 != null) && ((var8.getSaveVersion() == 19132) || (var8.getSaveVersion() == 19133)))
/*  57:    */           {
/*  58: 59 */             boolean var9 = var8.getSaveVersion() != getSaveVersion();
/*  59: 60 */             String var10 = var8.getWorldName();
/*  60: 62 */             if ((var10 == null) || (MathHelper.stringNullOrLengthZero(var10))) {
/*  61: 64 */               var10 = var7;
/*  62:    */             }
/*  63: 67 */             long var11 = 0L;
/*  64: 68 */             var1.add(new SaveFormatComparator(var7, var10, var8.getLastTimePlayed(), var11, var8.getGameType(), var9, var8.isHardcoreModeEnabled(), var8.areCommandsAllowed()));
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68: 73 */       return var1;
/*  69:    */     }
/*  70: 77 */     throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected int getSaveVersion()
/*  74:    */   {
/*  75: 83 */     return 19133;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void flushCache() {}
/*  79:    */   
/*  80:    */   public ISaveHandler getSaveLoader(String par1Str, boolean par2)
/*  81:    */   {
/*  82: 96 */     return new AnvilSaveHandler(this.savesDirectory, par1Str, par2);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isOldMapFormat(String par1Str)
/*  86:    */   {
/*  87:104 */     WorldInfo var2 = getWorldInfo(par1Str);
/*  88:105 */     return (var2 != null) && (var2.getSaveVersion() != getSaveVersion());
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate)
/*  92:    */   {
/*  93:113 */     par2IProgressUpdate.setLoadingProgress(0);
/*  94:114 */     ArrayList var3 = new ArrayList();
/*  95:115 */     ArrayList var4 = new ArrayList();
/*  96:116 */     ArrayList var5 = new ArrayList();
/*  97:117 */     File var6 = new File(this.savesDirectory, par1Str);
/*  98:118 */     File var7 = new File(var6, "DIM-1");
/*  99:119 */     File var8 = new File(var6, "DIM1");
/* 100:120 */     logger.info("Scanning folders...");
/* 101:121 */     addRegionFilesToCollection(var6, var3);
/* 102:123 */     if (var7.exists()) {
/* 103:125 */       addRegionFilesToCollection(var7, var4);
/* 104:    */     }
/* 105:128 */     if (var8.exists()) {
/* 106:130 */       addRegionFilesToCollection(var8, var5);
/* 107:    */     }
/* 108:133 */     int var9 = var3.size() + var4.size() + var5.size();
/* 109:134 */     logger.info("Total conversion count is " + var9);
/* 110:135 */     WorldInfo var10 = getWorldInfo(par1Str);
/* 111:136 */     Object var11 = null;
/* 112:138 */     if (var10.getTerrainType() == WorldType.FLAT) {
/* 113:140 */       var11 = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
/* 114:    */     } else {
/* 115:144 */       var11 = new WorldChunkManager(var10.getSeed(), var10.getTerrainType());
/* 116:    */     }
/* 117:147 */     convertFile(new File(var6, "region"), var3, (WorldChunkManager)var11, 0, var9, par2IProgressUpdate);
/* 118:148 */     convertFile(new File(var7, "region"), var4, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), var3.size(), var9, par2IProgressUpdate);
/* 119:149 */     convertFile(new File(var8, "region"), var5, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), var3.size() + var4.size(), var9, par2IProgressUpdate);
/* 120:150 */     var10.setSaveVersion(19133);
/* 121:152 */     if (var10.getTerrainType() == WorldType.DEFAULT_1_1) {
/* 122:154 */       var10.setTerrainType(WorldType.DEFAULT);
/* 123:    */     }
/* 124:157 */     createFile(par1Str);
/* 125:158 */     ISaveHandler var12 = getSaveLoader(par1Str, false);
/* 126:159 */     var12.saveWorldInfo(var10);
/* 127:160 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void createFile(String par1Str)
/* 131:    */   {
/* 132:168 */     File var2 = new File(this.savesDirectory, par1Str);
/* 133:170 */     if (!var2.exists())
/* 134:    */     {
/* 135:172 */       logger.warn("Unable to create level.dat_mcr backup");
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:176 */       File var3 = new File(var2, "level.dat");
/* 140:178 */       if (!var3.exists())
/* 141:    */       {
/* 142:180 */         logger.warn("Unable to create level.dat_mcr backup");
/* 143:    */       }
/* 144:    */       else
/* 145:    */       {
/* 146:184 */         File var4 = new File(var2, "level.dat_mcr");
/* 147:186 */         if (!var3.renameTo(var4)) {
/* 148:188 */           logger.warn("Unable to create level.dat_mcr backup");
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void convertFile(File par1File, Iterable par2Iterable, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
/* 155:    */   {
/* 156:196 */     Iterator var7 = par2Iterable.iterator();
/* 157:198 */     while (var7.hasNext())
/* 158:    */     {
/* 159:200 */       File var8 = (File)var7.next();
/* 160:201 */       convertChunks(par1File, var8, par3WorldChunkManager, par4, par5, par6IProgressUpdate);
/* 161:202 */       par4++;
/* 162:203 */       int var9 = (int)Math.round(100.0D * par4 / par5);
/* 163:204 */       par6IProgressUpdate.setLoadingProgress(var9);
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void convertChunks(File par1File, File par2File, WorldChunkManager par3WorldChunkManager, int par4, int par5, IProgressUpdate par6IProgressUpdate)
/* 168:    */   {
/* 169:    */     try
/* 170:    */     {
/* 171:215 */       String var7 = par2File.getName();
/* 172:216 */       RegionFile var8 = new RegionFile(par2File);
/* 173:217 */       RegionFile var9 = new RegionFile(new File(par1File, var7.substring(0, var7.length() - ".mcr".length()) + ".mca"));
/* 174:219 */       for (int var10 = 0; var10 < 32; var10++)
/* 175:    */       {
/* 176:223 */         for (int var11 = 0; var11 < 32; var11++) {
/* 177:225 */           if ((var8.isChunkSaved(var10, var11)) && (!var9.isChunkSaved(var10, var11)))
/* 178:    */           {
/* 179:227 */             DataInputStream var12 = var8.getChunkDataInputStream(var10, var11);
/* 180:229 */             if (var12 == null)
/* 181:    */             {
/* 182:231 */               logger.warn("Failed to fetch input stream");
/* 183:    */             }
/* 184:    */             else
/* 185:    */             {
/* 186:235 */               NBTTagCompound var13 = CompressedStreamTools.read(var12);
/* 187:236 */               var12.close();
/* 188:237 */               NBTTagCompound var14 = var13.getCompoundTag("Level");
/* 189:238 */               ChunkLoader.AnvilConverterData var15 = ChunkLoader.load(var14);
/* 190:239 */               NBTTagCompound var16 = new NBTTagCompound();
/* 191:240 */               NBTTagCompound var17 = new NBTTagCompound();
/* 192:241 */               var16.setTag("Level", var17);
/* 193:242 */               ChunkLoader.convertToAnvilFormat(var15, var17, par3WorldChunkManager);
/* 194:243 */               DataOutputStream var18 = var9.getChunkDataOutputStream(var10, var11);
/* 195:244 */               CompressedStreamTools.write(var16, var18);
/* 196:245 */               var18.close();
/* 197:    */             }
/* 198:    */           }
/* 199:    */         }
/* 200:250 */         var11 = (int)Math.round(100.0D * (par4 * 1024) / (par5 * 1024));
/* 201:251 */         int var20 = (int)Math.round(100.0D * ((var10 + 1) * 32 + par4 * 1024) / (par5 * 1024));
/* 202:253 */         if (var20 > var11) {
/* 203:255 */           par6IProgressUpdate.setLoadingProgress(var20);
/* 204:    */         }
/* 205:    */       }
/* 206:259 */       var8.close();
/* 207:260 */       var9.close();
/* 208:    */     }
/* 209:    */     catch (IOException var19)
/* 210:    */     {
/* 211:264 */       var19.printStackTrace();
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   private void addRegionFilesToCollection(File par1File, Collection par2Collection)
/* 216:    */   {
/* 217:273 */     File var3 = new File(par1File, "region");
/* 218:274 */     File[] var4 = var3.listFiles(new FilenameFilter()
/* 219:    */     {
/* 220:    */       private static final String __OBFID = "CL_00000583";
/* 221:    */       
/* 222:    */       public boolean accept(File par1File, String par2Str)
/* 223:    */       {
/* 224:279 */         return par2Str.endsWith(".mcr");
/* 225:    */       }
/* 226:    */     });
/* 227:283 */     if (var4 != null) {
/* 228:285 */       Collections.addAll(par2Collection, var4);
/* 229:    */     }
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.AnvilSaveConverter
 * JD-Core Version:    0.7.0.1
 */