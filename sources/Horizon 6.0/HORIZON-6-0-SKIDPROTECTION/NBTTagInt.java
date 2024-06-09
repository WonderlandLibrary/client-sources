package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagInt extends HorizonCode_Horizon_È
{
    private int Â;
    private static final String Ý = "CL_00001223";
    
    NBTTagInt() {
    }
    
    public NBTTagInt(final int data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeInt(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(32L);
        this.Â = input.readInt();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 3;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(this.Â).toString();
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagInt(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagInt var2 = (NBTTagInt)p_equals_1_;
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
        return (short)(this.Â & 0xFFFF);
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
