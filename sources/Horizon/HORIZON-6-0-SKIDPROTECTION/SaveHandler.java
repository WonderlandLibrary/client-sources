package HORIZON-6-0-SKIDPROTECTION;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import org.apache.logging.log4j.LogManager;
import java.io.File;
import org.apache.logging.log4j.Logger;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
    private static final Logger HorizonCode_Horizon_È;
    private final File Â;
    private final File Ý;
    private final File Ø­áŒŠá;
    private final long Âµá€;
    private final String Ó;
    private static final String à = "CL_00000585";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public SaveHandler(final File p_i2146_1_, final String p_i2146_2_, final boolean p_i2146_3_) {
        this.Âµá€ = MinecraftServer.Œà();
        (this.Â = new File(p_i2146_1_, p_i2146_2_)).mkdirs();
        this.Ý = new File(this.Â, "playerdata");
        (this.Ø­áŒŠá = new File(this.Â, "data")).mkdirs();
        this.Ó = p_i2146_2_;
        if (p_i2146_3_) {
            this.Ý.mkdirs();
        }
        this.Ø();
    }
    
    private void Ø() {
        try {
            final File var1 = new File(this.Â, "session.lock");
            final DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
            try {
                var2.writeLong(this.Âµá€);
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
    
    @Override
    public File Ó() {
        return this.Â;
    }
    
    @Override
    public void Ø­áŒŠá() throws MinecraftException {
        try {
            final File var1 = new File(this.Â, "session.lock");
            final DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
            try {
                if (var2.readLong() != this.Âµá€) {
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
    public IChunkLoader HorizonCode_Horizon_È(final WorldProvider p_75763_1_) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }
    
    @Override
    public WorldInfo Ý() {
        File var1 = new File(this.Â, "level.dat");
        if (var1.exists()) {
            try {
                final NBTTagCompound var2 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var1));
                final NBTTagCompound var3 = var2.ˆÏ­("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        var1 = new File(this.Â, "level.dat_old");
        if (var1.exists()) {
            try {
                final NBTTagCompound var2 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var1));
                final NBTTagCompound var3 = var2.ˆÏ­("Data");
                return new WorldInfo(var3);
            }
            catch (Exception var5) {
                var5.printStackTrace();
            }
        }
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final WorldInfo p_75755_1_, final NBTTagCompound p_75755_2_) {
        final NBTTagCompound var3 = p_75755_1_.HorizonCode_Horizon_È(p_75755_2_);
        final NBTTagCompound var4 = new NBTTagCompound();
        var4.HorizonCode_Horizon_È("Data", var3);
        try {
            final File var5 = new File(this.Â, "level.dat_new");
            final File var6 = new File(this.Â, "level.dat_old");
            final File var7 = new File(this.Â, "level.dat");
            CompressedStreamTools.HorizonCode_Horizon_È(var4, new FileOutputStream(var5));
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
    public void HorizonCode_Horizon_È(final WorldInfo p_75761_1_) {
        final NBTTagCompound var2 = p_75761_1_.HorizonCode_Horizon_È();
        final NBTTagCompound var3 = new NBTTagCompound();
        var3.HorizonCode_Horizon_È("Data", var2);
        try {
            final File var4 = new File(this.Â, "level.dat_new");
            final File var5 = new File(this.Â, "level.dat_old");
            final File var6 = new File(this.Â, "level.dat");
            CompressedStreamTools.HorizonCode_Horizon_È(var3, new FileOutputStream(var4));
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
    public void HorizonCode_Horizon_È(final EntityPlayer p_75753_1_) {
        try {
            final NBTTagCompound var2 = new NBTTagCompound();
            p_75753_1_.Âµá€(var2);
            final File var3 = new File(this.Ý, String.valueOf(p_75753_1_.£áŒŠá().toString()) + ".dat.tmp");
            final File var4 = new File(this.Ý, String.valueOf(p_75753_1_.£áŒŠá().toString()) + ".dat");
            CompressedStreamTools.HorizonCode_Horizon_È(var2, new FileOutputStream(var3));
            if (var4.exists()) {
                var4.delete();
            }
            var3.renameTo(var4);
        }
        catch (Exception var5) {
            SaveHandler.HorizonCode_Horizon_È.warn("Failed to save player data for " + p_75753_1_.v_());
        }
    }
    
    @Override
    public NBTTagCompound Â(final EntityPlayer p_75752_1_) {
        NBTTagCompound var2 = null;
        try {
            final File var3 = new File(this.Ý, String.valueOf(p_75752_1_.£áŒŠá().toString()) + ".dat");
            if (var3.exists() && var3.isFile()) {
                var2 = CompressedStreamTools.HorizonCode_Horizon_È(new FileInputStream(var3));
            }
        }
        catch (Exception var4) {
            SaveHandler.HorizonCode_Horizon_È.warn("Failed to load player data for " + p_75752_1_.v_());
        }
        if (var2 != null) {
            p_75752_1_.Ó(var2);
        }
        return var2;
    }
    
    @Override
    public IPlayerFileData Âµá€() {
        return this;
    }
    
    @Override
    public String[] Â() {
        String[] var1 = this.Ý.list();
        if (var1 == null) {
            var1 = new String[0];
        }
        for (int var2 = 0; var2 < var1.length; ++var2) {
            if (var1[var2].endsWith(".dat")) {
                var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
            }
        }
        return var1;
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
    }
    
    @Override
    public File HorizonCode_Horizon_È(final String p_75758_1_) {
        return new File(this.Ø­áŒŠá, String.valueOf(p_75758_1_) + ".dat");
    }
    
    @Override
    public String à() {
        return this.Ó;
    }
}
