package HORIZON-6-0-SKIDPROTECTION;

public class ChunkPrimer
{
    private final short[] HorizonCode_Horizon_È;
    private final IBlockState Â;
    private static final String Ý = "CL_00002007";
    
    public ChunkPrimer() {
        this.HorizonCode_Horizon_È = new short[65536];
        this.Â = Blocks.Â.¥à();
    }
    
    public IBlockState HorizonCode_Horizon_È(final int x, final int y, final int z) {
        final int var4 = x << 12 | z << 8 | y;
        return this.HorizonCode_Horizon_È(var4);
    }
    
    public IBlockState HorizonCode_Horizon_È(final int index) {
        if (index >= 0 && index < this.HorizonCode_Horizon_È.length) {
            final IBlockState var2 = (IBlockState)Block.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È[index]);
            return (var2 != null) ? var2 : this.Â;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z, final IBlockState state) {
        final int var5 = x << 12 | z << 8 | y;
        this.HorizonCode_Horizon_È(var5, state);
    }
    
    public void HorizonCode_Horizon_È(final int index, final IBlockState state) {
        if (index >= 0 && index < this.HorizonCode_Horizon_È.length) {
            this.HorizonCode_Horizon_È[index] = (short)Block.Â.HorizonCode_Horizon_È(state);
            return;
        }
        throw new IndexOutOfBoundsException("The coordinate is out of range");
    }
}
