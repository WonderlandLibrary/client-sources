package HORIZON-6-0-SKIDPROTECTION;

import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat
{
    private static final Logger Â;
    protected final File HorizonCode_Horizon_È;
    private static final String Ý = "CL_00000586";
    
    static {
        Â = LogManager.getLogger();
    }
    
    public SaveFormatOld(final File p_i2147_1_) {
        if (!p_i2147_1_.exists()) {
            p_i2147_1_.mkdirs();
        }
        this.HorizonCode_Horizon_È = p_i2147_1_;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Old Format";
    }
    
    @Override
    public List Â() throws AnvilConverterException {
        final ArrayList var1 = Lists.newArrayList();
        for (int var2 = 0; var2 < 5; ++var2) {
            final String var3 = "World" + (var2 + 1);
            final WorldInfo var4 = this.Ý(var3);
            if (var4 != null) {
                var1.add(new SaveFormatComparator(var3, "", var4.á(), var4.Ø(), var4.µà(), false, var4.¥Æ(), var4.µÕ()));
            }
        }
        return var1;
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public WorldInfo Ý(final String p_75803_1_) {
        final File var2 = new File(this.HorizonCode_Horizon_È, p_75803_1_);
        if (!var2.exists()) {
            return null;
        }
        File var3 = new File(var2, "level.dat");
        if (var3.exists()) {
            try {
                final NBTTagCompound var4 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var3));
                final NBTTagCompound var5 = var4.ˆÏ­("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var6) {
                SaveFormatOld.Â.error("Exception reading " + var3, (Throwable)var6);
            }
        }
        var3 = new File(var2, "level.dat_old");
        if (var3.exists()) {
            try {
                final NBTTagCompound var4 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var3));
                final NBTTagCompound var5 = var4.ˆÏ­("Data");
                return new WorldInfo(var5);
            }
            catch (Exception var7) {
                SaveFormatOld.Â.error("Exception reading " + var3, (Throwable)var7);
            }
        }
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String p_75806_1_, final String p_75806_2_) {
        final File var3 = new File(this.HorizonCode_Horizon_È, p_75806_1_);
        if (var3.exists()) {
            final File var4 = new File(var3, "level.dat");
            if (var4.exists()) {
                try {
                    final NBTTagCompound var5 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var4));
                    final NBTTagCompound var6 = var5.ˆÏ­("Data");
                    var6.HorizonCode_Horizon_È("LevelName", p_75806_2_);
                    CompressedStreamTools.HorizonCode_Horizon_È(var5, new FileOutputStream(var4));
                }
                catch (Exception var7) {
                    var7.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final String p_154335_1_) {
        final File var2 = new File(this.HorizonCode_Horizon_È, p_154335_1_);
        if (var2.exists()) {
            return false;
        }
        try {
            var2.mkdir();
            var2.delete();
            return true;
        }
        catch (Throwable var3) {
            SaveFormatOld.Â.warn("Couldn't make new level", var3);
            return false;
        }
    }
    
    @Override
    public boolean Âµá€(final String p_75802_1_) {
        final File var2 = new File(this.HorizonCode_Horizon_È, p_75802_1_);
        if (!var2.exists()) {
            return true;
        }
        SaveFormatOld.Â.info("Deleting level " + p_75802_1_);
        for (int var3 = 1; var3 <= 5; ++var3) {
            SaveFormatOld.Â.info("Attempt " + var3 + "...");
            if (HorizonCode_Horizon_È(var2.listFiles())) {
                break;
            }
            SaveFormatOld.Â.warn("Unsuccessful in deleting contents.");
            if (var3 < 5) {
                try {
                    Thread.sleep(500L);
                }
                catch (InterruptedException ex) {}
            }
        }
        return var2.delete();
    }
    
    protected static boolean HorizonCode_Horizon_È(final File[] p_75807_0_) {
        for (int var1 = 0; var1 < p_75807_0_.length; ++var1) {
            final File var2 = p_75807_0_[var1];
            SaveFormatOld.Â.debug("Deleting " + var2);
            if (var2.isDirectory() && !HorizonCode_Horizon_È(var2.listFiles())) {
                SaveFormatOld.Â.warn("Couldn't delete directory " + var2);
                return false;
            }
            if (!var2.delete()) {
                SaveFormatOld.Â.warn("Couldn't delete file " + var2);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public ISaveHandler HorizonCode_Horizon_È(final String p_75804_1_, final boolean p_75804_2_) {
        return new SaveHandler(this.HorizonCode_Horizon_È, p_75804_1_, p_75804_2_);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final String p_154334_1_) {
        return false;
    }
    
    @Override
    public boolean Â(final String p_75801_1_) {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final String p_75805_1_, final IProgressUpdate p_75805_2_) {
        return false;
    }
    
    @Override
    public boolean Ó(final String p_90033_1_) {
        final File var2 = new File(this.HorizonCode_Horizon_È, p_90033_1_);
        return var2.isDirectory();
    }
}
