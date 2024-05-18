package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagByte extends HorizonCode_Horizon_È
{
    private byte Â;
    private static final String Ý = "CL_00001214";
    
    NBTTagByte() {
    }
    
    public NBTTagByte(final byte data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeByte(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        sizeTracker.HorizonCode_Horizon_È(8L);
        this.Â = input.readByte();
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 1;
    }
    
    @Override
    public String toString() {
        return this.Â + "b";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagByte(this.Â);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (super.equals(p_equals_1_)) {
            final NBTTagByte var2 = (NBTTagByte)p_equals_1_;
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
        return this.Â;
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
