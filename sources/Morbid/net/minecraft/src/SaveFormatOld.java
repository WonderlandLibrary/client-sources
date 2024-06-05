package net.minecraft.src;

import java.util.*;
import java.io.*;

public class SaveFormatOld implements ISaveFormat
{
    protected final File savesDirectory;
    
    public SaveFormatOld(final File par1File) {
        if (!par1File.exists()) {
            par1File.mkdirs();
        }
        this.savesDirectory = par1File;
    }
    
    @Override
    public List getSaveList() throws AnvilConverterException {
        final ArrayList var1 = new ArrayList();
        for (int var2 = 0; var2 < 5; ++var2) {
            final String var3 = "World" + (var2 + 1);
            final WorldInfo var4 = this.getWorldInfo(var3);
            if (var4 != null) {
                var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
            }
        }
        return var1;
    }
    
    @Override
    public void flushCache() {
    }
    
    @Override
    public WorldInfo getWorldInfo(final String par1Str) {
        final File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            return null;
        }
        File var3 = new File(var2, "level.dat");
        if (var3.exists()) {
            try {
                final NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                final NBTTagCompound var5 = var4.getCompoundTag("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
        var3 = new File(var2, "level.dat_old");
        if (var3.exists()) {
            try {
                final NBTTagCompound var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
                final NBTTagCompound var5 = var4.getCompoundTag("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var7) {
                var7.printStackTrace();
            }
        }
        return null;
    }
    
    @Override
    public void renameWorld(final String par1Str, final String par2Str) {
        final File var3 = new File(this.savesDirectory, par1Str);
        if (var3.exists()) {
            final File var4 = new File(var3, "level.dat");
            if (var4.exists()) {
                try {
                    final NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
                    final NBTTagCompound var6 = var5.getCompoundTag("Data");
                    var6.setString("LevelName", par2Str);
                    CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
                }
                catch (Exception var7) {
                    var7.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public boolean deleteWorldDirectory(final String par1Str) {
        final File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            return true;
        }
        System.out.println("Deleting level " + par1Str);
        for (int var3 = 1; var3 <= 5; ++var3) {
            System.out.println("Attempt " + var3 + "...");
            if (deleteFiles(var2.listFiles())) {
                break;
            }
            System.out.println("Unsuccessful in deleting contents.");
            if (var3 < 5) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {}
            }
        }
        return var2.delete();
    }
    
    protected static boolean deleteFiles(final File[] par0ArrayOfFile) {
        for (int var1 = 0; var1 < par0ArrayOfFile.length; ++var1) {
            final File var2 = par0ArrayOfFile[var1];
            System.out.println("Deleting " + var2);
            if (var2.isDirectory() && !deleteFiles(var2.listFiles())) {
                System.out.println("Couldn't delete directory " + var2);
                return false;
            }
            if (!var2.delete()) {
                System.out.println("Couldn't delete file " + var2);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String par1Str, final boolean par2) {
        return new SaveHandler(this.savesDirectory, par1Str, par2);
    }
    
    @Override
    public boolean isOldMapFormat(final String par1Str) {
        return false;
    }
    
    @Override
    public boolean convertMapFormat(final String par1Str, final IProgressUpdate par2IProgressUpdate) {
        return false;
    }
    
    @Override
    public boolean canLoadWorld(final String par1Str) {
        final File var2 = new File(this.savesDirectory, par1Str);
        return var2.isDirectory();
    }
}
