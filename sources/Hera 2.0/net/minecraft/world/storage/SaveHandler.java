/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveHandler implements ISaveHandler, IPlayerFileData {
/*  21 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final File worldDirectory;
/*     */ 
/*     */   
/*     */   private final File playersDirectory;
/*     */ 
/*     */   
/*     */   private final File mapDataDir;
/*     */ 
/*     */   
/*  33 */   private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
/*     */ 
/*     */   
/*     */   private final String saveDirectoryName;
/*     */ 
/*     */   
/*     */   public SaveHandler(File savesDirectory, String directoryName, boolean playersDirectoryIn) {
/*  40 */     this.worldDirectory = new File(savesDirectory, directoryName);
/*  41 */     this.worldDirectory.mkdirs();
/*  42 */     this.playersDirectory = new File(this.worldDirectory, "playerdata");
/*  43 */     this.mapDataDir = new File(this.worldDirectory, "data");
/*  44 */     this.mapDataDir.mkdirs();
/*  45 */     this.saveDirectoryName = directoryName;
/*     */     
/*  47 */     if (playersDirectoryIn)
/*     */     {
/*  49 */       this.playersDirectory.mkdirs();
/*     */     }
/*     */     
/*  52 */     setSessionLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSessionLock() {
/*     */     try {
/*  62 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  63 */       DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/*  67 */         dataoutputstream.writeLong(this.initializationTime);
/*     */       }
/*     */       finally {
/*     */         
/*  71 */         dataoutputstream.close();
/*     */       }
/*     */     
/*  74 */     } catch (IOException ioexception) {
/*     */       
/*  76 */       ioexception.printStackTrace();
/*  77 */       throw new RuntimeException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldDirectory() {
/*  86 */     return this.worldDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSessionLock() throws MinecraftException {
/*     */     try {
/*  96 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  97 */       DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/* 101 */         if (datainputstream.readLong() != this.initializationTime)
/*     */         {
/* 103 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 108 */         datainputstream.close();
/*     */       }
/*     */     
/* 111 */     } catch (IOException var7) {
/*     */       
/* 113 */       throw new MinecraftException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 122 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo loadWorldInfo() {
/* 130 */     File file1 = new File(this.worldDirectory, "level.dat");
/*     */     
/* 132 */     if (file1.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 136 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 137 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/* 138 */         return new WorldInfo(nbttagcompound3);
/*     */       }
/* 140 */       catch (Exception exception1) {
/*     */         
/* 142 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 146 */     file1 = new File(this.worldDirectory, "level.dat_old");
/*     */     
/* 148 */     if (file1.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 152 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 153 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 154 */         return new WorldInfo(nbttagcompound1);
/*     */       }
/* 156 */       catch (Exception exception) {
/*     */         
/* 158 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 170 */     NBTTagCompound nbttagcompound = worldInformation.cloneNBTCompound(tagCompound);
/* 171 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 172 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */ 
/*     */     
/*     */     try {
/* 176 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 177 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 178 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 179 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 181 */       if (file2.exists())
/*     */       {
/* 183 */         file2.delete();
/*     */       }
/*     */       
/* 186 */       file3.renameTo(file2);
/*     */       
/* 188 */       if (file3.exists())
/*     */       {
/* 190 */         file3.delete();
/*     */       }
/*     */       
/* 193 */       file1.renameTo(file3);
/*     */       
/* 195 */       if (file1.exists())
/*     */       {
/* 197 */         file1.delete();
/*     */       }
/*     */     }
/* 200 */     catch (Exception exception) {
/*     */       
/* 202 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfo(WorldInfo worldInformation) {
/* 211 */     NBTTagCompound nbttagcompound = worldInformation.getNBTTagCompound();
/* 212 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 213 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */ 
/*     */     
/*     */     try {
/* 217 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 218 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 219 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 220 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 222 */       if (file2.exists())
/*     */       {
/* 224 */         file2.delete();
/*     */       }
/*     */       
/* 227 */       file3.renameTo(file2);
/*     */       
/* 229 */       if (file3.exists())
/*     */       {
/* 231 */         file3.delete();
/*     */       }
/*     */       
/* 234 */       file1.renameTo(file3);
/*     */       
/* 236 */       if (file1.exists())
/*     */       {
/* 238 */         file1.delete();
/*     */       }
/*     */     }
/* 241 */     catch (Exception exception) {
/*     */       
/* 243 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePlayerData(EntityPlayer player) {
/*     */     try {
/* 254 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 255 */       player.writeToNBT(nbttagcompound);
/* 256 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat.tmp");
/* 257 */       File file2 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat");
/* 258 */       CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));
/*     */       
/* 260 */       if (file2.exists())
/*     */       {
/* 262 */         file2.delete();
/*     */       }
/*     */       
/* 265 */       file1.renameTo(file2);
/*     */     }
/* 267 */     catch (Exception var5) {
/*     */       
/* 269 */       logger.warn("Failed to save player data for " + player.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound readPlayerData(EntityPlayer player) {
/* 278 */     NBTTagCompound nbttagcompound = null;
/*     */ 
/*     */     
/*     */     try {
/* 282 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getUniqueID().toString()) + ".dat");
/*     */       
/* 284 */       if (file1.exists() && file1.isFile())
/*     */       {
/* 286 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/*     */       }
/*     */     }
/* 289 */     catch (Exception var4) {
/*     */       
/* 291 */       logger.warn("Failed to load player data for " + player.getName());
/*     */     } 
/*     */     
/* 294 */     if (nbttagcompound != null)
/*     */     {
/* 296 */       player.readFromNBT(nbttagcompound);
/*     */     }
/*     */     
/* 299 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPlayerFileData getPlayerNBTManager() {
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAvailablePlayerDat() {
/* 312 */     String[] astring = this.playersDirectory.list();
/*     */     
/* 314 */     if (astring == null)
/*     */     {
/* 316 */       astring = new String[0];
/*     */     }
/*     */     
/* 319 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 321 */       if (astring[i].endsWith(".dat"))
/*     */       {
/* 323 */         astring[i] = astring[i].substring(0, astring[i].length() - 4);
/*     */       }
/*     */     } 
/*     */     
/* 327 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getMapFileFromName(String mapName) {
/* 342 */     return new File(this.mapDataDir, String.valueOf(mapName) + ".dat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldDirectoryName() {
/* 350 */     return this.saveDirectoryName;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\storage\SaveHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */