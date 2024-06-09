package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagFloat extends HorizonCode_Horizon_È
{
    private float Â;
    private static final String Ý = "CL_00001220";
    
    NBTTagFloat() {
    }
    
    public NBTTagFloat(final float data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeFloat(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(32L);
        this.Â = input.readFloat();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 5;
    }
    
    @Override
    public String toString() {
        return this.Â + "f";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagFloat(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagFloat var2 = (NBTTagFloat)p_equals_1_;
            return this.Â == var2.Â;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Float.floatToIntBits(this.Â);
    }
    
    @Override
    public long Âµá€() {
        return (long)this.Â;
    }
    
    @Override
    public int Ó() {
        return MathHelper.Ø­áŒŠá(this.Â);
    }
    
    @Override
    public short à() {
        return (short)(MathHelper.Ø­áŒŠá(this.Â) & 0xFFFF);
    }
    
    @Override
    public byte Ø() {
        return (byte)(MathHelper.Ø­áŒŠá(this.Â) & 0xFF);
    }
    
    @Override
    public double áŒŠÆ() {
        return this.Â;
    }
    
    @Override
    public float áˆºÑ¢Õ() {
        return this.Â;
    }
}
