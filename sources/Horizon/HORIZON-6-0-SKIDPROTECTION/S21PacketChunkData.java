package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.io.IOException;

public class S21PacketChunkData implements Packet
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private HorizonCode_Horizon_È Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001304";
    
    public S21PacketChunkData() {
    }
    
    public S21PacketChunkData(final Chunk p_i45196_1_, final boolean p_i45196_2_, final int p_i45196_3_) {
        this.HorizonCode_Horizon_È = p_i45196_1_.HorizonCode_Horizon_È;
        this.Â = p_i45196_1_.Â;
        this.Ø­áŒŠá = p_i45196_2_;
        this.Ý = HorizonCode_Horizon_È(p_i45196_1_, p_i45196_2_, !p_i45196_1_.£à().£à.Å(), p_i45196_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final PacketBuffer data) throws IOException {
        this.HorizonCode_Horizon_È = data.readInt();
        this.Â = data.readInt();
        this.Ø­áŒŠá = data.readBoolean();
        this.Ý = new HorizonCode_Horizon_È();
        this.Ý.Â = data.readShort();
        this.Ý.HorizonCode_Horizon_È = data.HorizonCode_Horizon_È();
    }
    
    @Override
    public void Â(final PacketBuffer data) throws IOException {
        data.writeInt(this.HorizonCode_Horizon_È);
        data.writeInt(this.Â);
        data.writeBoolean(this.Ø­áŒŠá);
        data.writeShort((short)(this.Ý.Â & 0xFFFF));
        data.HorizonCode_Horizon_È(this.Ý.HorizonCode_Horizon_È);
    }
    
    public void HorizonCode_Horizon_È(final INetHandlerPlayClient handler) {
        handler.HorizonCode_Horizon_È(this);
    }
    
    public byte[] HorizonCode_Horizon_È() {
        return this.Ý.HorizonCode_Horizon_È;
    }
    
    protected static int HorizonCode_Horizon_È(final int p_180737_0_, final boolean p_180737_1_, final boolean p_180737_2_) {
        final int var3 = p_180737_0_ * 2 * 16 * 16 * 16;
        final int var4 = p_180737_0_ * 16 * 16 * 16 / 2;
        final int var5 = p_180737_1_ ? (p_180737_0_ * 16 * 16 * 16 / 2) : 0;
        final int var6 = p_180737_2_ ? 256 : 0;
        return var3 + var4 + var5 + var6;
    }
    
    public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final Chunk p_179756_0_, final boolean p_179756_1_, final boolean p_179756_2_, final int p_179756_3_) {
        final ExtendedBlockStorage[] var4 = p_179756_0_.Â();
        final HorizonCode_Horizon_È var5 = new HorizonCode_Horizon_È();
        final ArrayList var6 = Lists.newArrayList();
        for (int var7 = 0; var7 < var4.length; ++var7) {
            final ExtendedBlockStorage var8 = var4[var7];
            if (var8 != null && (!p_179756_1_ || !var8.HorizonCode_Horizon_È()) && (p_179756_3_ & 1 << var7) != 0x0) {
                final HorizonCode_Horizon_È horizonCode_Horizon_È = var5;
                horizonCode_Horizon_È.Â |= 1 << var7;
                var6.add(var8);
            }
        }
        var5.HorizonCode_Horizon_È = new byte[HorizonCode_Horizon_È(Integer.bitCount(var5.Â), p_179756_2_, p_179756_1_)];
        int var7 = 0;
        for (final ExtendedBlockStorage var10 : var6) {
            final char[] var12;
            final char[] var11 = var12 = var10.Âµá€();
            for (int var13 = var11.length, var14 = 0; var14 < var13; ++var14) {
                final char var15 = var12[var14];
                var5.HorizonCode_Horizon_È[var7++] = (byte)(var15 & 'ÿ');
                var5.HorizonCode_Horizon_È[var7++] = (byte)(var15 >> 8 & 'ÿ');
            }
        }
        for (final ExtendedBlockStorage var10 : var6) {
            var7 = HorizonCode_Horizon_È(var10.Ó().HorizonCode_Horizon_È(), var5.HorizonCode_Horizon_È, var7);
        }
        if (p_179756_2_) {
            for (final ExtendedBlockStorage var10 : var6) {
                var7 = HorizonCode_Horizon_È(var10.à().HorizonCode_Horizon_È(), var5.HorizonCode_Horizon_È, var7);
            }
        }
        if (p_179756_1_) {
            HorizonCode_Horizon_È(p_179756_0_.ÂµÈ(), var5.HorizonCode_Horizon_È, var7);
        }
        return var5;
    }
    
    private static int HorizonCode_Horizon_È(final byte[] p_179757_0_, final byte[] p_179757_1_, final int p_179757_2_) {
        System.arraycopy(p_179757_0_, 0, p_179757_1_, p_179757_2_, p_179757_0_.length);
        return p_179757_2_ + p_179757_0_.length;
    }
    
    public int Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public int Ý() {
        return this.Â;
    }
    
    public int Ø­áŒŠá() {
        return this.Ý.Â;
    }
    
    public boolean Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final INetHandler handler) {
        this.HorizonCode_Horizon_È((INetHandlerPlayClient)handler);
    }
    
    public static class HorizonCode_Horizon_È
    {
        public byte[] HorizonCode_Horizon_È;
        public int Â;
        private static final String Ý = "CL_00001305";
    }
}
