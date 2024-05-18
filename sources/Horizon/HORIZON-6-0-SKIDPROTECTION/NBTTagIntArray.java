package HORIZON-6-0-SKIDPROTECTION;

import java.util.Arrays;
import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagIntArray extends NBTBase
{
    private int[] Â;
    private static final String Ý = "CL_00001221";
    
    NBTTagIntArray() {
    }
    
    public NBTTagIntArray(final int[] p_i45132_1_) {
        this.Â = p_i45132_1_;
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeInt(this.Â.length);
        for (int var2 = 0; var2 < this.Â.length; ++var2) {
            output.writeInt(this.Â[var2]);
        }
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        final int var4 = input.readInt();
        sizeTracker.HorizonCode_Horizon_È(32 * var4);
        this.Â = new int[var4];
        for (int var5 = 0; var5 < var4; ++var5) {
            this.Â[var5] = input.readInt();
        }
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 11;
    }
    
    @Override
    public String toString() {
        String var1 = "[";
        for (final int var5 : this.Â) {
            var1 = String.valueOf(var1) + var5 + ",";
        }
        return String.valueOf(var1) + "]";
    }
    
    @Override
    public NBTBase Â() {
        final int[] var1 = new int[this.Â.length];
        System.arraycopy(this.Â, 0, var1, 0, this.Â.length);
        return new NBTTagIntArray(var1);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return super.equals(p_equals_1_) && Arrays.equals(this.Â, ((NBTTagIntArray)p_equals_1_).Â);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ Arrays.hashCode(this.Â);
    }
    
    public int[] Âµá€() {
        return this.Â;
    }
}
