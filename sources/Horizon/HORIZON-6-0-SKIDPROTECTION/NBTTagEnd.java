package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;

public class NBTTagEnd extends NBTBase
{
    private static final String Â = "CL_00001219";
    
    @Override
    void HorizonCode_Horizon_È(final DataInput input, final int depth, final NBTSizeTracker sizeTracker) throws IOException {
    }
    
    @Override
    void HorizonCode_Horizon_È(final DataOutput output) throws IOException {
    }
    
    @Override
    public byte HorizonCode_Horizon_È() {
        return 0;
    }
    
    @Override
    public String toString() {
        return "END";
    }
    
    @Override
    public NBTBase Â() {
        return new NBTTagEnd();
    }
}
