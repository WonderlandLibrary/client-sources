package net.minecraft.tileentity;

import net.minecraft.nbt.*;

public class TileEntityComparator extends TileEntity
{
    private int outputSignal;
    private static final String[] I;
    
    public void setOutputSignal(final int outputSignal) {
        this.outputSignal = outputSignal;
    }
    
    static {
        I();
    }
    
    public int getOutputSignal() {
        return this.outputSignal;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.outputSignal = nbtTagCompound.getInteger(TileEntityComparator.I[" ".length()]);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(TileEntityComparator.I["".length()], this.outputSignal);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\n\u001c\u0018\u001b\u00111:\u0005\f\n$\u0005", "Eilkd");
        TileEntityComparator.I[" ".length()] = I("\u001d\u0017\u000e\u00186&1\u0013\u000f-3\u000e", "RbzhC");
    }
}
