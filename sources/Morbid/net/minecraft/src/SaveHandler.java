package net.minecraft.src;

import java.io.*;
import net.minecraft.server.*;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private final File worldDirectory;
    private final File playersDirectory;
    private final File mapDataDir;
    private final long initializationTime;
    private final String saveDirectoryName;
    
    public SaveHandler(final File par1File, final String par2Str, final boolean par3) {
        this.initializationTime = System.currentTimeMillis();
        (this.worldDirectory = new File(par1File, par2Str)).mkdirs();
        this.playersDirectory = new File(this.worldDirectory, "players");
        (this.mapDataDir = new File(this.worldDirectory, "data")).mkdirs();
        this.saveDirectoryName = par2Str;
        if (par3) {
            this.playersDirectory.mkdirs();
        }
        this.setSessionLock();
    }
    
    private void setSessionLock() {
        try {
            final File var1 = new File(this.worldDirectory, "session.lock");
            final DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
            try {
                var2.writeLong(this.initializationTime);
            }
            finally {
                var2.close();
            }
            var2.close();
        }
        catch (IOException var3) {
            var3.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }
    
    protected File getWorldDirectory() {
        return this.worldDirectory;
    }
    
    @Override
    public void checkSessionLock() throws MinecraftException {
        try {
            final File var1 = new File(this.worldDirectory, "session.lock");
            final DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
            try {
                if (var2.readLong() != this.initializationTime) {
                    throw new MinecraftException("The save is being accessed from another location, aborting");
                }
            }
            finally {
                var2.close();
            }
            var2.close();
        }
        catch (IOException var3) {
            throw new MinecraftException("Failed to check session lock, aborting");
        }
    }
    
    @Override
    public IChunkLoader getChunkLoader(final WorldProvider par1WorldProvider) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }
    
    @Override
    public WorldInfo loadWorldInfo() {
        File var1 = new File(this.worldDirectory, "level.dat");
        if (var1.exists()) {
            try {
                final NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                final NBTTagCompound var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        var1 = new File(this.worldDirectory, "level.dat_old");
        if (var1.exists()) {
            try {
                final NBTTagCompound var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
                final NBTTagCompound var3 = var2.getCompoundTag("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        return null;
    }
    
    @Override
    public void saveWorldInfoWithPlayer(final WorldInfo par1WorldInfo, final NBTTagCompound par2NBTTagCompound) {
        final NBTTagCompound var3 = par1WorldInfo.cloneNBTCompound(par2NBTTagCompound);
        final NBTTagCompound var4 = new NBTTagCompound();
        var4.setTag("Data", var3);
        try {
            final File var5 = new File(this.worldDirectory, "level.dat_new");
            final File var6 = new File(this.worldDirectory, "level.dat_old");
            final File var7 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var4, new FileOutputStream(var5));
            if (var6.exists()) {
                var6.delete();
            }
            var7.renameTo(var6);
            if (var7.exists()) {
                var7.delete();
            }
            var5.renameTo(var7);
            if (var5.exists()) {
                var5.delete();
            }
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
    }
    
    @Override
    public void saveWorldInfo(final WorldInfo par1WorldInfo) {
        final NBTTagCompound var2 = par1WorldInfo.getNBTTagCompound();
        final NBTTagCompound var3 = new NBTTagCompound();
        var3.setTag("Data", var2);
        try {
            final File var4 = new File(this.worldDirectory, "level.dat_new");
            final File var5 = new File(this.worldDirectory, "level.dat_old");
            final File var6 = new File(this.worldDirectory, "level.dat");
            CompressedStreamTools.writeCompressed(var3, new FileOutputStream(var4));
            if (var5.exists()) {
                var5.delete();
            }
            var6.renameTo(var5);
            if (var6.exists()) {
                var6.delete();
            }
            var4.renameTo(var6);
            if (var4.exists()) {
                var4.delete();
            }
        }
        catch (Exception var7) {
            var7.printStackTrace();
        }
    }
    
    @Override
    public void writePlayerData(final EntityPlayer par1EntityPlayer) {
        try {
            final NBTTagCompound var2 = new NBTTagCompound();
            par1EntityPlayer.writeToNBT(var2);
            final File var3 = new File(this.playersDirectory, String.valueOf(par1EntityPlayer.username) + ".dat.tmp");
            final File var4 = new File(this.playersDirectory, String.valueOf(par1EntityPlayer.username) + ".dat");
            CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));
            if (var4.exists()) {
                var4.delete();
            }
            var3.renameTo(var4);
        }
        catch (Exception var5) {
            MinecraftServer.getServer().getLogAgent().logWarning("Failed to save player data for " + par1EntityPlayer.username);
        }
    }
    
    @Override
    public NBTTagCompound readPlayerData(final EntityPlayer par1EntityPlayer) {
        final NBTTagCompound var2 = this.getPlayerData(par1EntityPlayer.username);
        if (var2 != null) {
            par1EntityPlayer.readFromNBT(var2);
        }
        return var2;
    }
    
    public NBTTagCompound getPlayerData(final String par1Str) {
        try {
            final File var2 = new File(this.playersDirectory, String.valueOf(par1Str) + ".dat");
            if (var2.exists()) {
                return CompressedStreamTools.readCompressed(new FileInputStream(var2));
            }
        }
        catch (Exception var3) {
            MinecraftServer.getServer().getLogAgent().logWarning("Failed to load player data for " + par1Str);
        }
        return null;
    }
    
    @Override
    public IPlayerFileData getSaveHandler() {
        return this;
    }
    
    @Override
    public String[] getAvailablePlayerDat() {
        final String[] var1 = this.playersDirectory.list();
        for (int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2].endsWith(".dat")) {
                var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
            }
        }
        return var1;
    }
    
    @Override
    public void flush() {
    }
    
    @Override
    public File getMapFileFromName(final String par1Str) {
        return new File(this.mapDataDir, String.valueOf(par1Str) + ".dat");
    }
    
    @Override
    public String getWorldDirectoryName() {
        return this.saveDirectoryName;
    }
}
