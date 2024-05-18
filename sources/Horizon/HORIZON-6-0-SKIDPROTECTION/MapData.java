package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.Iterator;
import com.google.common.collect.Maps;
import com.google.common.collect.Lists;
import java.util.Map;
import java.util.List;

public class MapData extends WorldSavedData
{
    public int Â;
    public int Ý;
    public byte Ø­áŒŠá;
    public byte Âµá€;
    public byte[] Ó;
    public List à;
    private Map áŒŠÆ;
    public Map Ø;
    private static final String áˆºÑ¢Õ = "CL_00000577";
    
    public MapData(final String p_i2140_1_) {
        super(p_i2140_1_);
        this.Ó = new byte[16384];
        this.à = Lists.newArrayList();
        this.áŒŠÆ = Maps.newHashMap();
        this.Ø = Maps.newLinkedHashMap();
    }
    
    public void HorizonCode_Horizon_È(final double p_176054_1_, final double p_176054_3_, final int p_176054_5_) {
        final int var6 = 128 * (1 << p_176054_5_);
        final int var7 = MathHelper.Ý((p_176054_1_ + 64.0) / var6);
        final int var8 = MathHelper.Ý((p_176054_3_ + 64.0) / var6);
        this.Â = var7 * var6 + var6 / 2 - 64;
        this.Ý = var8 * var6 + var6 / 2 - 64;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final NBTTagCompound nbt) {
        this.Ø­áŒŠá = nbt.Ø­áŒŠá("dimension");
        this.Â = nbt.Ó("xCenter");
        this.Ý = nbt.Ó("zCenter");
        this.Âµá€ = nbt.Ø­áŒŠá("scale");
        this.Âµá€ = (byte)MathHelper.HorizonCode_Horizon_È(this.Âµá€, 0, 4);
        final short var2 = nbt.Âµá€("width");
        final short var3 = nbt.Âµá€("height");
        if (var2 == 128 && var3 == 128) {
            this.Ó = nbt.ÂµÈ("colors");
        }
        else {
            final byte[] var4 = nbt.ÂµÈ("colors");
            this.Ó = new byte[16384];
            final int var5 = (128 - var2) / 2;
            final int var6 = (128 - var3) / 2;
            for (int var7 = 0; var7 < var3; ++var7) {
                final int var8 = var7 + var6;
                if (var8 >= 0 || var8 < 128) {
                    for (int var9 = 0; var9 < var2; ++var9) {
                        final int var10 = var9 + var5;
                        if (var10 >= 0 || var10 < 128) {
                            this.Ó[var10 + var8 * 128] = var4[var9 + var7 * var2];
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void Ý(final NBTTagCompound nbt) {
        nbt.HorizonCode_Horizon_È("dimension", this.Ø­áŒŠá);
        nbt.HorizonCode_Horizon_È("xCenter", this.Â);
        nbt.HorizonCode_Horizon_È("zCenter", this.Ý);
        nbt.HorizonCode_Horizon_È("scale", this.Âµá€);
        nbt.HorizonCode_Horizon_È("width", (short)128);
        nbt.HorizonCode_Horizon_È("height", (short)128);
        nbt.HorizonCode_Horizon_È("colors", this.Ó);
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayer p_76191_1_, final ItemStack p_76191_2_) {
        if (!this.áŒŠÆ.containsKey(p_76191_1_)) {
            final HorizonCode_Horizon_È var3 = new HorizonCode_Horizon_È(p_76191_1_);
            this.áŒŠÆ.put(p_76191_1_, var3);
            this.à.add(var3);
        }
        if (!p_76191_1_.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_76191_2_)) {
            this.Ø.remove(p_76191_1_.v_());
        }
        for (int var4 = 0; var4 < this.à.size(); ++var4) {
            final HorizonCode_Horizon_È var5 = this.à.get(var4);
            if (!var5.HorizonCode_Horizon_È.ˆáŠ && (var5.HorizonCode_Horizon_È.Ø­Ñ¢Ï­Ø­áˆº.Ý(p_76191_2_) || p_76191_2_.áŒŠà())) {
                if (!p_76191_2_.áŒŠà() && var5.HorizonCode_Horizon_È.ÇªÔ == this.Ø­áŒŠá) {
                    this.HorizonCode_Horizon_È(0, var5.HorizonCode_Horizon_È.Ï­Ðƒà, var5.HorizonCode_Horizon_È.v_(), var5.HorizonCode_Horizon_È.ŒÏ, var5.HorizonCode_Horizon_È.Ê, var5.HorizonCode_Horizon_È.É);
                }
            }
            else {
                this.áŒŠÆ.remove(var5.HorizonCode_Horizon_È);
                this.à.remove(var5);
            }
        }
        if (p_76191_2_.áŒŠà()) {
            final EntityItemFrame var6 = p_76191_2_.ŠÄ();
            final BlockPos var7 = var6.ˆÏ­();
            this.HorizonCode_Horizon_È(1, p_76191_1_.Ï­Ðƒà, "frame-" + var6.ˆá(), var7.HorizonCode_Horizon_È(), var7.Ý(), var6.Â.Ý() * 90);
        }
        if (p_76191_2_.£á() && p_76191_2_.Å().Â("Decorations", 9)) {
            final NBTTagList var8 = p_76191_2_.Å().Ý("Decorations", 10);
            for (int var9 = 0; var9 < var8.Âµá€(); ++var9) {
                final NBTTagCompound var10 = var8.Â(var9);
                if (!this.Ø.containsKey(var10.áˆºÑ¢Õ("id"))) {
                    this.HorizonCode_Horizon_È(var10.Ø­áŒŠá("type"), p_76191_1_.Ï­Ðƒà, var10.áˆºÑ¢Õ("id"), var10.áŒŠÆ("x"), var10.áŒŠÆ("z"), var10.áŒŠÆ("rot"));
                }
            }
        }
    }
    
    private void HorizonCode_Horizon_È(int p_82567_1_, final World worldIn, final String p_82567_3_, final double p_82567_4_, final double p_82567_6_, double p_82567_8_) {
        final int var10 = 1 << this.Âµá€;
        final float var11 = (float)(p_82567_4_ - this.Â) / var10;
        final float var12 = (float)(p_82567_6_ - this.Ý) / var10;
        byte var13 = (byte)(var11 * 2.0f + 0.5);
        byte var14 = (byte)(var12 * 2.0f + 0.5);
        final byte var15 = 63;
        byte var16;
        if (var11 >= -var15 && var12 >= -var15 && var11 <= var15 && var12 <= var15) {
            p_82567_8_ += ((p_82567_8_ < 0.0) ? -8.0 : 8.0);
            var16 = (byte)(p_82567_8_ * 16.0 / 360.0);
            if (this.Ø­áŒŠá < 0) {
                final int var17 = (int)(worldIn.ŒÏ().à() / 10L);
                var16 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(var11) >= 320.0f || Math.abs(var12) >= 320.0f) {
                this.Ø.remove(p_82567_3_);
                return;
            }
            p_82567_1_ = 6;
            var16 = 0;
            if (var11 <= -var15) {
                var13 = (byte)(var15 * 2 + 2.5);
            }
            if (var12 <= -var15) {
                var14 = (byte)(var15 * 2 + 2.5);
            }
            if (var11 >= var15) {
                var13 = (byte)(var15 * 2 + 1);
            }
            if (var12 >= var15) {
                var14 = (byte)(var15 * 2 + 1);
            }
        }
        this.Ø.put(p_82567_3_, new Vec4b((byte)p_82567_1_, var13, var14, var16));
    }
    
    public Packet HorizonCode_Horizon_È(final ItemStack p_176052_1_, final World worldIn, final EntityPlayer p_176052_3_) {
        final HorizonCode_Horizon_È var4 = this.áŒŠÆ.get(p_176052_3_);
        return (var4 == null) ? null : var4.HorizonCode_Horizon_È(p_176052_1_);
    }
    
    public void HorizonCode_Horizon_È(final int p_176053_1_, final int p_176053_2_) {
        super.Ø­áŒŠá();
        for (final HorizonCode_Horizon_È var4 : this.à) {
            var4.HorizonCode_Horizon_È(p_176053_1_, p_176053_2_);
        }
    }
    
    public HorizonCode_Horizon_È HorizonCode_Horizon_È(final EntityPlayer p_82568_1_) {
        HorizonCode_Horizon_È var2 = this.áŒŠÆ.get(p_82568_1_);
        if (var2 == null) {
            var2 = new HorizonCode_Horizon_È(p_82568_1_);
            this.áŒŠÆ.put(p_82568_1_, var2);
            this.à.add(var2);
        }
        return var2;
    }
    
    public class HorizonCode_Horizon_È
    {
        public final EntityPlayer HorizonCode_Horizon_È;
        private boolean Ø­áŒŠá;
        private int Âµá€;
        private int Ó;
        private int à;
        private int Ø;
        private int áŒŠÆ;
        public int Â;
        private static final String áˆºÑ¢Õ = "CL_00000578";
        
        public HorizonCode_Horizon_È(final EntityPlayer p_i2138_2_) {
            this.Ø­áŒŠá = true;
            this.Âµá€ = 0;
            this.Ó = 0;
            this.à = 127;
            this.Ø = 127;
            this.HorizonCode_Horizon_È = p_i2138_2_;
        }
        
        public Packet HorizonCode_Horizon_È(final ItemStack p_176101_1_) {
            if (this.Ø­áŒŠá) {
                this.Ø­áŒŠá = false;
                return new S34PacketMaps(p_176101_1_.Ø(), MapData.this.Âµá€, MapData.this.Ø.values(), MapData.this.Ó, this.Âµá€, this.Ó, this.à + 1 - this.Âµá€, this.Ø + 1 - this.Ó);
            }
            return (this.áŒŠÆ++ % 5 == 0) ? new S34PacketMaps(p_176101_1_.Ø(), MapData.this.Âµá€, MapData.this.Ø.values(), MapData.this.Ó, 0, 0, 0, 0) : null;
        }
        
        public void HorizonCode_Horizon_È(final int p_176102_1_, final int p_176102_2_) {
            if (this.Ø­áŒŠá) {
                this.Âµá€ = Math.min(this.Âµá€, p_176102_1_);
                this.Ó = Math.min(this.Ó, p_176102_2_);
                this.à = Math.max(this.à, p_176102_1_);
                this.Ø = Math.max(this.Ø, p_176102_2_);
            }
            else {
                this.Ø­áŒŠá = true;
                this.Âµá€ = p_176102_1_;
                this.Ó = p_176102_2_;
                this.à = p_176102_1_;
                this.Ø = p_176102_2_;
            }
        }
    }
}
