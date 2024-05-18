package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public class NBTTagString extends NBTBase
{
    private String Â;
    private static final String Ý = "CL_00001228";
    
    public NBTTagString() {
        this.Â = "";
    }
    
    public NBTTagString(final String data) {
        this.Â = data;
        if (data == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
        output.writeUTF(this.Â);
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
        this.Â = input.readUTF();
        sizeTracker.HorizonCode_Horizon_È(16 * this.Â.length());
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 8;
    }
    
    @Override
    public String toString() {
        return "\"" + this.Â.replace("\"", "\\\"") + "\"";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagString(this.Â);
    }
    
    @Override
    public boolean Ý() {
        return this.Â.isEmpty();
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!super.equals(p_equals_1_)) {
            return false;
        }
        final NBTTagString var2 = (NBTTagString)p_equals_1_;
        return (this.Â == null && var2.Â == null) || (this.Â != null && this.Â.equals(var2.Â));
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() ^ this.Â.hashCode();
    }
    
    public String Ø­áŒŠá() {
        return this.Â;
    }
}
