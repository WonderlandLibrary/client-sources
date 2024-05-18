package net.minecraft.src;

import net.minecraft.server.*;
import java.io.*;
import java.util.*;

public class AnvilSaveConverter extends SaveFormatOld
{
    public AnvilSaveConverter(final File par1File) {
        super(par1File);
    }
    
    @Override
    public List getSaveList() throws AnvilConverterException {
        if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
            final ArrayList var1 = new ArrayList();
            final File[] var3;
            final File[] var2 = var3 = this.savesDirectory.listFiles();
            for (int var4 = var2.length, var5 = 0; var5 < var4; ++var5) {
                final File var6 = var3[var5];
                if (var6.isDirectory()) {
                    final String var7 = var6.getName();
                    final WorldInfo var8 = this.getWorldInfo(var7);
                    if (var8 != null && (var8.getSaveVersion() == 19132 || var8.getSaveVersion() == 19133)) {
                        final boolean var9 = var8.getSaveVersion() != this.getSaveVersion();
                        String var10 = var8.getWorldName();
                        if (var10 == null || MathHelper.stringNullOrLengthZero(var10)) {
                            var10 = var7;
                        }
                        final long var11 = 0L;
                        var1.add(new SaveFormatComparator(var7, var10, var8.getLastTimePlayed(), var11, var8.getGameType(), var9, var8.isHardcoreModeEnabled(), var8.areCommandsAllowed()));
                    }
                }
            }
            return var1;
        }
        throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
    }
    
    protected int getSaveVersion() {
        return 19133;
    }
    
    @Override
    public void flushCache() {
        RegionFileCache.clearRegionFileReferences();
    }
    
    @Override
    public ISaveHandler getSaveLoader(final String par1Str, final boolean par2) {
        return new AnvilSaveHandler(this.savesDirectory, par1Str, par2);
    }
    
    @Override
    public boolean isOldMapFormat(final String par1Str) {
        final WorldInfo var2 = this.getWorldInfo(par1Str);
        return var2 != null && var2.getSaveVersion() != this.getSaveVersion();
    }
    
    @Override
    public boolean convertMapFormat(final String par1Str, final IProgressUpdate par2IProgressUpdate) {
        par2IProgressUpdate.setLoadingProgress(0);
        final ArrayList var3 = new ArrayList();
        final ArrayList var4 = new ArrayList();
        final ArrayList var5 = new ArrayList();
        final File var6 = new File(this.savesDirectory, par1Str);
        final File var7 = new File(var6, "DIM-1");
        final File var8 = new File(var6, "DIM1");
        MinecraftServer.getServer().getLogAgent().logInfo("Scanning folders...");
        this.addRegionFilesToCollection(var6, var3);
        if (var7.exists()) {
            this.addRegionFilesToCollection(var7, var4);
        }
        if (var8.exists()) {
            this.addRegionFilesToCollection(var8, var5);
        }
        final int var9 = var3.size() + var4.size() + var5.size();
        MinecraftServer.getServer().getLogAgent().logInfo("Total conversion count is " + var9);
        final WorldInfo var10 = this.getWorldInfo(par1Str);
        Object var11 = null;
        if (var10.getTerrainType() == WorldType.FLAT) {
            var11 = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5f, 0.5f);
        }
        else {
            var11 = new WorldChunkManager(var10.getSeed(), var10.getTerrainType());
        }
        this.convertFile(new File(var6, "region"), var3, (WorldChunkManager)var11, 0, var9, par2IProgressUpdate);
        this.convertFile(new File(var7, "region"), var4, new WorldChunkManagerHell(BiomeGenBase.hell, 1.0f, 0.0f), var3.size(), var9, par2IProgressUpdate);
        this.convertFile(new File(var8, "region"), var5, new WorldChunkManagerHell(BiomeGenBase.sky, 0.5f, 0.0f), var3.size() + var4.size(), var9, par2IProgressUpdate);
        var10.setSaveVersion(19133);
        if (var10.getTerrainType() == WorldType.DEFAULT_1_1) {
            var10.setTerrainType(WorldType.DEFAULT);
        }
        this.createFile(par1Str);
        final ISaveHandler var12 = this.getSaveLoader(par1Str, false);
        var12.saveWorldInfo(var10);
        return true;
    }
    
    private void createFile(final String par1Str) {
        final File var2 = new File(this.savesDirectory, par1Str);
        if (!var2.exists()) {
            System.out.println("Warning: Unable to create level.dat_mcr backup");
        }
        else {
            final File var3 = new File(var2, "level.dat");
            if (!var3.exists()) {
                System.out.println("Warning: Unable to create level.dat_mcr backup");
            }
            else {
                final File var4 = new File(var2, "level.dat_mcr");
                if (!var3.renameTo(var4)) {
                    System.out.println("Warning: Unable to create level.dat_mcr backup");
                }
            }
        }
    }
    
    private void convertFile(final File par1File, final Iterable par2Iterable, final WorldChunkManager par3WorldChunkManager, int par4, final int par5, final IProgressUpdate par6IProgressUpdate) {
        for (final File var8 : par2Iterable) {
            this.convertChunks(par1File, var8, par3WorldChunkManager, par4, par5, par6IProgressUpdate);
            ++par4;
            final int var9 = (int)Math.round(100.0 * par4 / par5);
            par6IProgressUpdate.setLoadingProgress(var9);
        }
    }
    
    private void convertChunks(final File par1File, final File par2File, final WorldChunkManager par3WorldChunkManager, final int par4, final int par5, final IProgressUpdate par6IProgressUpdate) {
        try {
            final String var7 = par2File.getName();
            final RegionFile var8 = new RegionFile(par2File);
            final RegionFile var9 = new RegionFile(new File(par1File, String.valueOf(var7.substring(0, var7.length() - ".mcr".length())) + ".mca"));
            for (int var10 = 0; var10 < 32; ++var10) {
                for (int var11 = 0; var11 < 32; ++var11) {
                    if (var8.isChunkSaved(var10, var11) && !var9.isChunkSaved(var10, var11)) {
                        final DataInputStream var12 = var8.getChunkDataInputStream(var10, var11);
                        if (var12 == null) {
                            MinecraftServer.getServer().getLogAgent().logWarning("Failed to fetch input stream");
                        }
                        else {
                            final NBTTagCompound var13 = CompressedStreamTools.read(var12);
                            var12.close();
                            final NBTTagCompound var14 = var13.getCompoundTag("Level");
                            final AnvilConverterData var15 = ChunkLoader.load(var14);
                            final NBTTagCompound var16 = new NBTTagCompound();
                            final NBTTagCompound var17 = new NBTTagCompound();
                            var16.setTag("Level", var17);
                            ChunkLoader.convertToAnvilFormat(var15, var17, par3WorldChunkManager);
                            final DataOutputStream var18 = var9.getChunkDataOutputStream(var10, var11);
                            CompressedStreamTools.write(var16, var18);
                            var18.close();
                        }
                    }
                }
                int var11 = (int)Math.round(100.0 * (par4 * 1024) / (par5 * 1024));
                final int var19 = (int)Math.round(100.0 * ((var10 + 1) * 32 + par4 * 1024) / (par5 * 1024));
                if (var19 > var11) {
                    par6IProgressUpdate.setLoadingProgress(var19);
                }
            }
            var8.close();
            var9.close();
        }
        catch (IOException var20) {
            var20.printStackTrace();
        }
    }
    
    private void addRegionFilesToCollection(final File par1File, final Collection par2Collection) {
        final File var3 = new File(par1File, "region");
        final File[] var4 = var3.listFiles(new AnvilSaveConverterFileFilter(this));
        if (var4 != null) {
            Collections.addAll(par2Collection, var4);
        }
    }
}
