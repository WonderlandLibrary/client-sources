package HORIZON-6-0-SKIDPROTECTION;

import java.io.DataInput;
import java.io.IOException;
import java.io.DataOutput;

public abstract class NBTBase
{
    public static final String[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00001229";
    
    static {
        HorizonCode_Horizon_È = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
    }
    
    abstract void HorizonCode_Horizon_È(final DataOutput p0) throws IOException;
    
    abstract void HorizonCode_Horizon_È(final DataInput p0, final int p1, final NBTSizeTracker p2) throws IOException;
    
    @Override
    public abstract String toString();
    
    public abstract byte HorizonCode_Horizon_È();
    
    protected static NBTBase HorizonCode_Horizon_È(final byte id) {
        switch (id) {
            case 0: {
                return new NBTTagEnd();
            }
            case 1: {
                return new NBTTagByte();
            }
            case 2: {
                return new NBTTagShort();
            }
            case 3: {
                return new NBTTagInt();
            }
            case 4: {
                return new NBTTagLong();
            }
            case 5: {
                return new NBTTagFloat();
            }
            case 6: {
                return new NBTTagDouble();
            }
            case 7: {
                return new NBTTagByteArray();
            }
            case 8: {
                return new NBTTagString();
            }
            case 9: {
                return new NBTTagList();
            }
            case 10: {
                return new NBTTagCompound();
            }
            case 11: {
                return new NBTTagIntArray();
            }
            default: {
                return null;
            }
        }
    }
    
    public abstract NBTBase Â();
    
    public boolean Ý() {
        return false;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof NBTBase)) {
            return false;
        }
        final NBTBase var2 = (NBTBase)p_equals_1_;
        return this.HorizonCode_Horizon_È() == var2.HorizonCode_Horizon_È();
    }
    
    @Override
    public int hashCode() {
        return this.HorizonCode_Horizon_È();
    }
    
    protected String Ø­áŒŠá() {
        return this.toString();
    }
    
    public abstract static class HorizonCode_Horizon_È extends NBTBase
    {
        private static final String Â = "CL_00001230";
        
        public abstract long Âµá€();
        
        public abstract int Ó();
        
        public abstract short à();
        
        public abstract byte Ø();
        
        public abstract double áŒŠÆ();
        
        public abstract float áˆºÑ¢Õ();
    }
}
