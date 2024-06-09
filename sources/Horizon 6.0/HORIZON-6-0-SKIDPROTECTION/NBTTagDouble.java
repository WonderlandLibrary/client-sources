package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagDouble extends HorizonCode_Horizon_È
{
    private double Â;
    private static final String Ý = "CL_00001218";
    
    NBTTagDouble() {
    }
    
    public NBTTagDouble(final double data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeDouble(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(64L);
        this.Â = input.readDouble();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 6;
    }
    
    @Override
    public String toString() {
        return this.Â + "d";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagDouble(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagDouble var2 = (NBTTagDouble)p_equals_1_;
            return this.Â == var2.Â;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final long var1 = Double.doubleToLongBits(this.Â);
        return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
    }
    
    @Override
    public long Âµá€() {
        return (long)Math.floor(this.Â);
    }
    
    @Override
    public int Ó() {
        return MathHelper.Ý(this.Â);
    }
    
    @Override
    public short à() {
        return (short)(MathHelper.Ý(this.Â) & 0xFFFF);
    }
    
    @Override
    public byte Ø() {
        return (byte)(MathHelper.Ý(this.Â) & 0xFF);
    }
    
    @Override
    public double áŒŠÆ() {
        return this.Â;
    }
    
    @Override
    public float áˆºÑ¢Õ() {
        return (float)this.Â;
    }
}
