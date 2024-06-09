package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.io.DataInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

public class MapStorage
{
    private ISaveHandler Â;
    protected Map HorizonCode_Horizon_È;
    private List Ý;
    private Map Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000604";
    
    public MapStorage(final ISaveHandler p_i2162_1_) {
        this.HorizonCode_Horizon_È = Maps.newHashMap();
        this.Ý = Lists.newArrayList();
        this.Ø­áŒŠá = Maps.newHashMap();
        this.Â = p_i2162_1_;
        this.Â();
    }
    
    public WorldSavedData HorizonCode_Horizon_È(final Class p_75742_1_, final String p_75742_2_) {
        WorldSavedData var3 = this.HorizonCode_Horizon_È.get(p_75742_2_);
        if (var3 != null) {
            return var3;
        }
        if (this.Â != null) {
            try {
                final File var4 = this.Â.HorizonCode_Horizon_È(p_75742_2_);
                if (var4 != null && var4.exists()) {
                    try {
                        var3 = p_75742_1_.getConstructor(String.class).newInstance(p_75742_2_);
                    }
                    catch (Exception var5) {
                        throw new RuntimeException("Failed to instantiate " + p_75742_1_.toString(), var5);
                    }
                    final FileInputStream var6 = new FileInputStream(var4);
                    final NBTTagCompound var7 = CompressedStreamTools.HorizonCode_Horizon_È(var6);
                    var6.close();
                    var3.HorizonCode_Horizon_È(var7.ˆÏ­("data"));
                }
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
        }
        if (var3 != null) {
            this.HorizonCode_Horizon_È.put(p_75742_2_, var3);
            this.Ý.add(var3);
        }
        return var3;
    }
    
    public void HorizonCode_Horizon_È(final String p_75745_1_, final WorldSavedData p_75745_2_) {
        if (this.HorizonCode_Horizon_È.containsKey(p_75745_1_)) {
            this.Ý.remove(this.HorizonCode_Horizon_È.remove(p_75745_1_));
        }
        this.HorizonCode_Horizon_È.put(p_75745_1_, p_75745_2_);
        this.Ý.add(p_75745_2_);
    }
    
    public void HorizonCode_Horizon_È() {
        for (int var1 = 0; var1 < this.Ý.size(); ++var1) {
            final WorldSavedData var2 = this.Ý.get(var1);
            if (var2.Âµá€()) {
                this.HorizonCode_Horizon_È(var2);
                var2.HorizonCode_Horizon_È(false);
            }
        }
    }
    
    private void HorizonCode_Horizon_È(final WorldSavedData p_75747_1_) {
        if (this.Â != null) {
            try {
                final File var2 = this.Â.HorizonCode_Horizon_È(p_75747_1_.HorizonCode_Horizon_È);
                if (var2 != null) {
                    final NBTTagCompound var3 = new NBTTagCompound();
                    p_75747_1_.Ý(var3);
                    final NBTTagCompound var4 = new NBTTagCompound();
                    var4.HorizonCode_Horizon_È("data", var3);
                    final FileOutputStream var5 = new FileOutputStream(var2);
                    CompressedStreamTools.HorizonCode_Horizon_È(var4, var5);
                    var5.close();
                }
            }
            catch (Exception var6) {
                var6.printStackTrace();
            }
        }
    }
    
    private void Â() {
        try {
            this.Ø­áŒŠá.clear();
            if (this.Â == null) {
                return;
            }
            final File var1 = this.Â.HorizonCode_Horizon_È("idcounts");
            if (var1 != null && var1.exists()) {
                final DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
                final NBTTagCompound var3 = CompressedStreamTools.HorizonCode_Horizon_È(var2);
                var2.close();
                for (final String var5 : var3.Âµá€()) {
                    final NBTBase var6 = var3.HorizonCode_Horizon_È(var5);
                    if (var6 instanceof NBTTagShort) {
                        final NBTTagShort var7 = (NBTTagShort)var6;
                        final short var8 = var7.à();
                        this.Ø­áŒŠá.put(var5, var8);
                    }
                }
            }
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
    }
    
    public int HorizonCode_Horizon_È(final String p_75743_1_) {
        Short var2 = this.Ø­áŒŠá.get(p_75743_1_);
        if (var2 == null) {
            var2 = 0;
        }
        else {
            var2 = (short)(var2 + 1);
        }
        this.Ø­áŒŠá.put(p_75743_1_, var2);
        if (this.Â == null) {
            return var2;
        }
        try {
            final File var3 = this.Â.HorizonCode_Horizon_È("idcounts");
            if (var3 != null) {
                final NBTTagCompound var4 = new NBTTagCompound();
                for (final String var6 : this.Ø­áŒŠá.keySet()) {
                    final short var7 = this.Ø­áŒŠá.get(var6);
                    var4.HorizonCode_Horizon_È(var6, var7);
                }
                final DataOutputStream var8 = new DataOutputStream(new FileOutputStream(var3));
                CompressedStreamTools.HorizonCode_Horizon_È(var4, (DataOutput)var8);
                var8.close();
            }
        }
        catch (Exception var9) {
            var9.printStackTrace();
        }
        return var2;
    }
}
