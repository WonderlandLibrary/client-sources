package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagShort extends HorizonCode_Horizon_È
{
    private short Â;
    private static final String Ý = "CL_00001227";
    
    public NBTTagShort() {
    }
    
    public NBTTagShort(final short data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeShort(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(16L);
        this.Â = input.readShort();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 2;
    }
    
    @Override
    public String toString() {
        return this.Â + "s";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagShort(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagShort var2 = (NBTTagShort)p_equals_1_;
            return this.Â == var2.Â;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.Â;
    }
    
    @Override
    public long Âµá€() {
        return this.Â;
    }
    
    @Override
    public int Ó() {
        return this.Â;
    }
    
    @Override
    public short à() {
        return this.Â;
    }
    
    @Override
    public byte Ø() {
        return (byte)(this.Â & 0xFF);
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
