package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagLong extends HorizonCode_Horizon_È
{
    private long Â;
    private static final String Ý = "CL_00001225";
    
    NBTTagLong() {
    }
    
    public NBTTagLong(final long data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeLong(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(64L);
        this.Â = input.readLong();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 4;
    }
    
    @Override
    public String toString() {
        return this.Â + "L";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagLong(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagLong var2 = (NBTTagLong)p_equals_1_;
            return this.Â == var2.Â;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ (int)(this.Â ^ this.Â >>> 32);
    }
    
    @Override
    public long Âµá€() {
        return this.Â;
    }
    
    @Override
    public int Ó() {
        return (int)(this.Â & -1L);
    }
    
    @Override
    public short à() {
        return (short)(this.Â & 0xFFFFL);
    }
    
    @Override
    public byte Ø() {
        return (byte)(this.Â & 0xFFL);
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
