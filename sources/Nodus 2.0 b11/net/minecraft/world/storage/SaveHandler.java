/*   1:    */ package net.minecraft.world.storage;
/*   2:    */ 
/*   3:    */ import java.io.DataInputStream;
/*   4:    */ import java.io.DataOutputStream;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import net.minecraft.entity.player.EntityPlayer;
/*  10:    */ import net.minecraft.nbt.CompressedStreamTools;
/*  11:    */ import net.minecraft.nbt.NBTTagCompound;
/*  12:    */ import net.minecraft.server.MinecraftServer;
/*  13:    */ import net.minecraft.world.MinecraftException;
/*  14:    */ import net.minecraft.world.WorldProvider;
/*  15:    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*  16:    */ import org.apache.logging.log4j.LogManager;
/*  17:    */ import org.apache.logging.log4j.Logger;
/*  18:    */ 
/*  19:    */ public class SaveHandler
/*  20:    */   implements ISaveHandler, IPlayerFileData
/*  21:    */ {
/*  22: 21 */   private static final Logger logger = ;
/*  23:    */   private final File worldDirectory;
/*  24:    */   private final File playersDirectory;
/*  25:    */   private final File mapDataDir;
/*  26: 33 */   private final long initializationTime = MinecraftServer.getSystemTimeMillis();
/*  27:    */   private final String saveDirectoryName;
/*  28:    */   private static final String __OBFID = "CL_00000585";
/*  29:    */   
/*  30:    */   public SaveHandler(File par1File, String par2Str, boolean par3)
/*  31:    */   {
/*  32: 41 */     this.worldDirectory = new File(par1File, par2Str);
/*  33: 42 */     this.worldDirectory.mkdirs();
/*  34: 43 */     this.playersDirectory = new File(this.worldDirectory, "players");
/*  35: 44 */     this.mapDataDir = new File(this.worldDirectory, "data");
/*  36: 45 */     this.mapDataDir.mkdirs();
/*  37: 46 */     this.saveDirectoryName = par2Str;
/*  38: 48 */     if (par3) {
/*  39: 50 */       this.playersDirectory.mkdirs();
/*  40:    */     }
/*  41: 53 */     setSessionLock();
/*  42:    */   }
/*  43:    */   
/*  44:    */   private void setSessionLock()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 63 */       File var1 = new File(this.worldDirectory, "session.lock");
/*  49: 64 */       DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
/*  50:    */       try
/*  51:    */       {
/*  52: 68 */         var2.writeLong(this.initializationTime);
/*  53:    */       }
/*  54:    */       finally
/*  55:    */       {
/*  56: 72 */         var2.close();
/*  57:    */       }
/*  58:    */     }
/*  59:    */     catch (IOException var7)
/*  60:    */     {
/*  61: 77 */       var7.printStackTrace();
/*  62: 78 */       throw new RuntimeException("Failed to check session lock, aborting");
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public File getWorldDirectory()
/*  67:    */   {
/*  68: 87 */     return this.worldDirectory;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void checkSessionLock()
/*  72:    */     throws MinecraftException
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76: 97 */       File var1 = new File(this.worldDirectory, "session.lock");
/*  77: 98 */       DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
/*  78:    */       try
/*  79:    */       {
/*  80:102 */         if (var2.readLong() != this.initializationTime) {
/*  81:104 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*  82:    */         }
/*  83:    */       }
/*  84:    */       finally
/*  85:    */       {
/*  86:109 */         var2.close();
/*  87:    */       }
/*  88:109 */       var2.close();
/*  89:    */     }
/*  90:    */     catch (IOException var7)
/*  91:    */     {
/*  92:114 */       throw new MinecraftException("Failed to check session lock, aborting");
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
/*  97:    */   {
/*  98:123 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*  99:    */   }
/* 100:    */   
/* 101:    */   public WorldInfo loadWorldInfo()
/* 102:    */   {
/* 103:131 */     File var1 = new File(this.worldDirectory, "level.dat");
/* 104:135 */     if (var1.exists()) {
/* 105:    */       try
/* 106:    */       {
/* 107:139 */         NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
/* 108:140 */         NBTTagCompound var3 = var2.getCompoundTag("Data");
/* 109:141 */         return new WorldInfo(var3);
/* 110:    */       }
/* 111:    */       catch (Exception var5)
/* 112:    */       {
/* 113:145 */         var5.printStackTrace();
/* 114:    */       }
/* 115:    */     }
/* 116:149 */     var1 = new File(this.worldDirectory, "level.dat_old");
/* 117:151 */     if (var1.exists()) {
/* 118:    */       try
/* 119:    */       {
/* 120:155 */         NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
/* 121:156 */         NBTTagCompound var3 = var2.getCompoundTag("Data");
/* 122:157 */         return new WorldInfo(var3);
/* 123:    */       }
/* 124:    */       catch (Exception var4)
/* 125:    */       {
/* 126:161 */         var4.printStackTrace();
/* 127:    */       }
/* 128:    */     }
/* 129:165 */     return null;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound)
/* 133:    */   {
/* 134:173 */     NBTTagCompound var3 = par1WorldInfo.cloneNBTCompound(par2NBTTagCompound);
/* 135:174 */     NBTTagCompound var4 = new NBTTagCompound();
/* 136:175 */     var4.setTag("Data", var3);
/* 137:    */     try
/* 138:    */     {
/* 139:179 */       File var5 = new File(this.worldDirectory, "level.dat_new");
/* 140:180 */       File var6 = new File(this.worldDirectory, "level.dat_old");
/* 141:181 */       File var7 = new File(this.worldDirectory, "level.dat");
/* 142:182 */       CompressedStreamTools.writeCompressed(var4, new FileOutputStream(var5));
/* 143:184 */       if (var6.exists()) {
/* 144:186 */         var6.delete();
/* 145:    */       }
/* 146:189 */       var7.renameTo(var6);
/* 147:191 */       if (var7.exists()) {
/* 148:193 */         var7.delete();
/* 149:    */       }
/* 150:196 */       var5.renameTo(var7);
/* 151:198 */       if (var5.exists()) {
/* 152:200 */         var5.delete();
/* 153:    */       }
/* 154:    */     }
/* 155:    */     catch (Exception var8)
/* 156:    */     {
/* 157:205 */       var8.printStackTrace();
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void saveWorldInfo(WorldInfo par1WorldInfo)
/* 162:    */   {
/* 163:214 */     NBTTagCompound var2 = par1WorldInfo.getNBTTagCompound();
/* 164:215 */     NBTTagCompound var3 = new NBTTagCompound();
/* 165:216 */     var3.setTag("Data", var2);
/* 166:    */     try
/* 167:    */     {
/* 168:220 */       File var4 = new File(this.worldDirectory, "level.dat_new");
/* 169:221 */       File var5 = new File(this.worldDirectory, "level.dat_old");
/* 170:222 */       File var6 = new File(this.worldDirectory, "level.dat");
/* 171:223 */       CompressedStreamTools.writeCompressed(var3, new FileOutputStream(var4));
/* 172:225 */       if (var5.exists()) {
/* 173:227 */         var5.delete();
/* 174:    */       }
/* 175:230 */       var6.renameTo(var5);
/* 176:232 */       if (var6.exists()) {
/* 177:234 */         var6.delete();
/* 178:    */       }
/* 179:237 */       var4.renameTo(var6);
/* 180:239 */       if (var4.exists()) {
/* 181:241 */         var4.delete();
/* 182:    */       }
/* 183:    */     }
/* 184:    */     catch (Exception var7)
/* 185:    */     {
/* 186:246 */       var7.printStackTrace();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void writePlayerData(EntityPlayer par1EntityPlayer)
/* 191:    */   {
/* 192:    */     try
/* 193:    */     {
/* 194:257 */       NBTTagCompound var2 = new NBTTagCompound();
/* 195:258 */       par1EntityPlayer.writeToNBT(var2);
/* 196:259 */       File var3 = new File(this.playersDirectory, par1EntityPlayer.getCommandSenderName() + ".dat.tmp");
/* 197:260 */       File var4 = new File(this.playersDirectory, par1EntityPlayer.getCommandSenderName() + ".dat");
/* 198:261 */       CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));
/* 199:263 */       if (var4.exists()) {
/* 200:265 */         var4.delete();
/* 201:    */       }
/* 202:268 */       var3.renameTo(var4);
/* 203:    */     }
/* 204:    */     catch (Exception var5)
/* 205:    */     {
/* 206:272 */       logger.warn("Failed to save player data for " + par1EntityPlayer.getCommandSenderName());
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public NBTTagCompound readPlayerData(EntityPlayer par1EntityPlayer)
/* 211:    */   {
/* 212:281 */     NBTTagCompound var2 = getPlayerData(par1EntityPlayer.getCommandSenderName());
/* 213:283 */     if (var2 != null) {
/* 214:285 */       par1EntityPlayer.readFromNBT(var2);
/* 215:    */     }
/* 216:288 */     return var2;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public NBTTagCompound getPlayerData(String par1Str)
/* 220:    */   {
/* 221:    */     try
/* 222:    */     {
/* 223:298 */       File var2 = new File(this.playersDirectory, par1Str + ".dat");
/* 224:300 */       if (var2.exists()) {
/* 225:302 */         return CompressedStreamTools.readCompressed(new FileInputStream(var2));
/* 226:    */       }
/* 227:    */     }
/* 228:    */     catch (Exception var3)
/* 229:    */     {
/* 230:307 */       logger.warn("Failed to load player data for " + par1Str);
/* 231:    */     }
/* 232:310 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public IPlayerFileData getSaveHandler()
/* 236:    */   {
/* 237:318 */     return this;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public String[] getAvailablePlayerDat()
/* 241:    */   {
/* 242:326 */     String[] var1 = this.playersDirectory.list();
/* 243:328 */     for (int var2 = 0; var2 < var1.length; var2++) {
/* 244:330 */       if (var1[var2].endsWith(".dat")) {
/* 245:332 */         var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
/* 246:    */       }
/* 247:    */     }
/* 248:336 */     return var1;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void flush() {}
/* 252:    */   
/* 253:    */   public File getMapFileFromName(String par1Str)
/* 254:    */   {
/* 255:349 */     return new File(this.mapDataDir, par1Str + ".dat");
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getWorldDirectoryName()
/* 259:    */   {
/* 260:357 */     return this.saveDirectoryName;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.SaveHandler
 * JD-Core Version:    0.7.0.1
 */