package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collections;
import java.io.FilenameFilter;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.DataOutput;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter extends SaveFormatOld
{
    private static final Logger Â;
    private static final String Ý = "CL_00000582";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public AnvilSaveConverter(final File p_i2144_1_) {
        super(p_i2144_1_);
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Anvil";
    }
    
    @Override
    public List Â() throws AnvilConverterException {
        if (this.HorizonCode_Horizon_È != null && this.HorizonCode_Horizon_È.exists() && this.HorizonCode_Horizon_È.isDirectory()) {
            final ArrayList var1 = Lists.newArrayList();
            final File[] var3;
            final File[] var2 = var3 = this.HorizonCode_Horizon_È.listFiles();
            for (int var4 = var2.length, var5 = 0; var5 < var4; ++var5) {
                final File var6 = var3[var5];
                if (var6.isDirectory()) {
                    final String var7 = var6.getName();
                    final WorldInfo var8 = this.Ý(var7);
                    if (var8 != null && (var8.ÂµÈ() == 19132 || var8.ÂµÈ() == 19133)) {
                        final boolean var9 = var8.ÂµÈ() != this.Ý();
                        String var10 = var8.áˆºÑ¢Õ();
                        if (StringUtils.isEmpty((CharSequence)var10)) {
                            var10 = var7;
                        }
                        final long var11 = 0L;
                        var1.add(new SaveFormatComparator(var7, var10, var8.á(), var11, var8.µà(), var9, var8.¥Æ(), var8.µÕ()));
                    }
                }
            }
            return var1;
        }
        throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
    }
    
    protected int Ý() {
        return 19133;
    }
    
    @Override
    public void Ø­áŒŠá() {
        RegionFileCache.HorizonCode_Horizon_È();
    }
    
    @Override
    public ISaveHandler HorizonCode_Horizon_È(final String p_75804_1_, final boolean p_75804_2_) {
        return new AnvilSaveHandler(this.HorizonCode_Horizon_È, p_75804_1_, p_75804_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final String p_154334_1_) {
        final WorldInfo var2 = this.Ý(p_154334_1_);
        return var2 != null && var2.ÂµÈ() == 19132;
    }
    
    @Override
    public boolean Â(final String p_75801_1_) {
        final WorldInfo var2 = this.Ý(p_75801_1_);
        return var2 != null && var2.ÂµÈ() != this.Ý();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final String p_75805_1_, final IProgressUpdate p_75805_2_) {
        p_75805_2_.HorizonCode_Horizon_È(0);
        final ArrayList var3 = Lists.newArrayList();
        final ArrayList var4 = Lists.newArrayList();
        final ArrayList var5 = Lists.newArrayList();
        final File var6 = new File(this.HorizonCode_Horizon_È, p_75805_1_);
        final File var7 = new File(var6, "DIM-1");
        final File var8 = new File(var6, "DIM1");
        AnvilSaveConverter.Â.info("Scanning folders...");
        this.HorizonCode_Horizon_È(var6, var3);
        if (var7.exists()) {
            this.HorizonCode_Horizon_È(var7, var4);
        }
        if (var8.exists()) {
            this.HorizonCode_Horizon_È(var8, var5);
        }
        final int var9 = var3.size() + var4.size() + var5.size();
        AnvilSaveConverter.Â.info("Total conversion count is " + var9);
        final WorldInfo var10 = this.Ý(p_75805_1_);
        Object var11 = null;
        if (var10.Ø­à() == WorldType.Ø­áŒŠá) {
            var11 = new WorldChunkManagerHell(BiomeGenBase.µà, 0.5f);
        }
        else {
            var11 = new WorldChunkManager(var10.Â(), var10.Ø­à(), var10.Ñ¢á());
        }
        this.HorizonCode_Horizon_È(new File(var6, "region"), var3, (WorldChunkManager)var11, 0, var9, p_75805_2_);
        this.HorizonCode_Horizon_È(new File(var7, "region"), var4, new WorldChunkManagerHell(BiomeGenBase.Ï­Ðƒà, 0.0f), var3.size(), var9, p_75805_2_);
        this.HorizonCode_Horizon_È(new File(var8, "region"), var5, new WorldChunkManagerHell(BiomeGenBase.áŒŠà, 0.0f), var3.size() + var4.size(), var9, p_75805_2_);
        var10.Ø­áŒŠá(19133);
        if (var10.Ø­à() == WorldType.áŒŠÆ) {
            var10.HorizonCode_Horizon_È(WorldType.Ý);
        }
        this.à(p_75805_1_);
        final ISaveHandler var12 = this.HorizonCode_Horizon_È(p_75805_1_, false);
        var12.HorizonCode_Horizon_È(var10);
        return true;
    }
    
    private void à(final String p_75809_1_) {
        final File var2 = new File(this.HorizonCode_Horizon_È, p_75809_1_);
        if (!var2.exists()) {
            AnvilSaveConverter.Â.warn("Unable to create level.dat_mcr backup");
        }
        else {
            final File var3 = new File(var2, "level.dat");
            if (!var3.exists()) {
                AnvilSaveConverter.Â.warn("Unable to create level.dat_mcr backup");
            }
            else {
                final File var4 = new File(var2, "level.dat_mcr");
                if (!var3.renameTo(var4)) {
                    AnvilSaveConverter.Â.warn("Unable to create level.dat_mcr backup");
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final File p_75813_1_, final Iterable p_75813_2_, final WorldChunkManager p_75813_3_, int p_75813_4_, final int p_75813_5_, final IProgressUpdate p_75813_6_) {
        for (final File var8 : p_75813_2_) {
            this.HorizonCode_Horizon_È(p_75813_1_, var8, p_75813_3_, p_75813_4_, p_75813_5_, p_75813_6_);
            ++p_75813_4_;
            final int var9 = (int)Math.round(100.0 * p_75813_4_ / p_75813_5_);
            p_75813_6_.HorizonCode_Horizon_È(var9);
        }
    }
    
    private void HorizonCode_Horizon_È(final File p_75811_1_, final File p_75811_2_, final WorldChunkManager p_75811_3_, final int p_75811_4_, final int p_75811_5_, final IProgressUpdate p_75811_6_) {
        try {
            final String var7 = p_75811_2_.getName();
            final RegionFile var8 = new RegionFile(p_75811_2_);
            final RegionFile var9 = new RegionFile(new File(p_75811_1_, String.valueOf(var7.substring(0, var7.length() - ".mcr".length())) + ".mca"));
            for (int var10 = 0; var10 < 32; ++var10) {
                for (int var11 = 0; var11 < 32; ++var11) {
                    if (var8.Ý(var10, var11) && !var9.Ý(var10, var11)) {
                        final DataInputStream var12 = var8.HorizonCode_Horizon_È(var10, var11);
                        if (var12 == null) {
                            AnvilSaveConverter.Â.warn("Failed to fetch input stream");
                        }
                        else {
                            final NBTTagCompound var13 = CompressedStreamTools.HorizonCode_Horizon_È(var12);
                            var12.close();
                            final NBTTagCompound var14 = var13.ˆÏ­("Level");
                            final ChunkLoader.HorizonCode_Horizon_È var15 = ChunkLoader.HorizonCode_Horizon_È(var14);
                            final NBTTagCompound var16 = new NBTTagCompound();
                            final NBTTagCompound var17 = new NBTTagCompound();
                            var16.HorizonCode_Horizon_È("Level", var17);
                            ChunkLoader.HorizonCode_Horizon_È(var15, var17, p_75811_3_);
                            final DataOutputStream var18 = var9.Â(var10, var11);
                            CompressedStreamTools.HorizonCode_Horizon_È(var16, (DataOutput)var18);
                            var18.close();
                        }
                    }
                }
                int var11 = (int)Math.round(100.0 * (p_75811_4_ * 1024) / (p_75811_5_ * 1024));
                final int var19 = (int)Math.round(100.0 * ((var10 + 1) * 32 + p_75811_4_ * 1024) / (p_75811_5_ * 1024));
                if (var19 > var11) {
                    p_75811_6_.HorizonCode_Horizon_È(var19);
                }
            }
            var8.HorizonCode_Horizon_È();
            var9.HorizonCode_Horizon_È();
        }
        catch (IOException var20) {
            var20.printStackTrace();
        }
    }
    
    private void HorizonCode_Horizon_È(final File p_75810_1_, final Collection p_75810_2_) {
        final File var3 = new File(p_75810_1_, "region");
        final File[] var4 = var3.listFiles(new FilenameFilter() {
            private static final String Â = "CL_00000583";
            
            @Override
            public boolean accept(final File p_accept_1_, final String p_accept_2_) {
                return p_accept_2_.endsWith(".mcr");
            }
        });
        if (var4 != null) {
            Collections.addAll(p_75810_2_, var4);
        }
    }
}
