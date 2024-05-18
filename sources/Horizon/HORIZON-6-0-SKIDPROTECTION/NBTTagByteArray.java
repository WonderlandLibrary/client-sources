package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagByteArray extends NBTBase
{
    private byte[] Â;
    private static final String Ý = "CL_00001213";
    
    NBTTagByteArray() {
    }
    
    public NBTTagByteArray(final byte[] data) {
        this.Â = data;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeInt(this.Â.length);
        output.write(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        final int var4 = input.readInt();
        sizeTracker.HorizonCode_Horizon_È(8 * var4);
        input.readFully(this.Â = new byte[var4]);
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 7;
    }
    
    @Override
    public String toString() {
        return "[" + this.Â.length + " bytes]";
    }
    
    @Override
    public NBTBase Â() {
        final byte[] var1 = new byte[this.Â.length];
        System.arraycopy(this.Â, 0, var1, 0, this.Â.length);
        return new NBTTagByteArray(var1);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return super.equals(p_equals_1_) && Arrays.equals(this.Â, ((NBTTagByteArray)p_equals_1_).Â);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.Â);
    }
    
    public byte[] Âµá€() {
        return this.Â;
    }
}
