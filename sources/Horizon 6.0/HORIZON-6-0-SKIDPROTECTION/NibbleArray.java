package HORIZON-6-0-SKIDPROTECTION;

public class NibbleArray
{
    private final byte[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00000371";
    
    public NibbleArray() {
        this.HorizonCode_Horizon_È = new byte[2048];
    }
    
    public NibbleArray(final byte[] storageArray) {
        this.HorizonCode_Horizon_È = storageArray;
        if (storageArray.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
        }
    }
    
    public int HorizonCode_Horizon_È(final int x, final int y, final int z) {
        return this.HorizonCode_Horizon_È(this.Â(x, y, z));
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z, final int value) {
        this.HorizonCode_Horizon_È(this.Â(x, y, z), value);
    }
    
    private int Â(final int x, final int y, final int z) {
        return y << 8 | z << 4 | x;
    }
    
    public int HorizonCode_Horizon_È(final int index) {
        final int var2 = this.Ý(index);
        return this.Â(index) ? (this.HorizonCode_Horizon_È[var2] & 0xF) : (this.HorizonCode_Horizon_È[var2] >> 4 & 0xF);
    }
    
    public void HorizonCode_Horizon_È(final int index, final int value) {
        final int var3 = this.Ý(index);
        if (this.Â(index)) {
            this.HorizonCode_Horizon_È[var3] = (byte)((this.HorizonCode_Horizon_È[var3] & 0xF0) | (value & 0xF));
        }
        else {
            this.HorizonCode_Horizon_È[var3] = (byte)((this.HorizonCode_Horizon_È[var3] & 0xF) | (value & 0xF) << 4);
        }
    }
    
    private boolean Â(final int p_177479_1_) {
        return (p_177479_1_ & 0x1) == 0x0;
    }
    
    private int Ý(final int p_177478_1_) {
        return p_177478_1_ >> 1;
    }
    
    public byte[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
}
