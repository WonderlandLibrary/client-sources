package HORIZON-6-0-SKIDPROTECTION;

public class ExtendedBlockStorage
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private char[] Ø­áŒŠá;
    private NibbleArray Âµá€;
    private NibbleArray Ó;
    private static final String à = "CL_00000375";
    
    public ExtendedBlockStorage(final int y, final boolean storeSkylight) {
        this.HorizonCode_Horizon_È = y;
        this.Ø­áŒŠá = new char[4096];
        this.Âµá€ = new NibbleArray();
        if (storeSkylight) {
            this.Ó = new NibbleArray();
        }
    }
    
    public IBlockState HorizonCode_Horizon_È(final int x, final int y, final int z) {
        final IBlockState var4 = (IBlockState)Block.Â.HorizonCode_Horizon_È(this.Ø­áŒŠá[y << 8 | z << 4 | x]);
        return (var4 != null) ? var4 : Blocks.Â.¥à();
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z, final IBlockState state) {
        final IBlockState var5 = this.HorizonCode_Horizon_È(x, y, z);
        final Block var6 = var5.Ý();
        final Block var7 = state.Ý();
        if (var6 != Blocks.Â) {
            --this.Â;
            if (var6.ˆÏ­()) {
                --this.Ý;
            }
        }
        if (var7 != Blocks.Â) {
            ++this.Â;
            if (var7.ˆÏ­()) {
                ++this.Ý;
            }
        }
        this.Ø­áŒŠá[y << 8 | z << 4 | x] = (char)Block.Â.HorizonCode_Horizon_È(state);
    }
    
    public Block Â(final int x, final int y, final int z) {
        return this.HorizonCode_Horizon_È(x, y, z).Ý();
    }
    
    public int Ý(final int x, final int y, final int z) {
        final IBlockState var4 = this.HorizonCode_Horizon_È(x, y, z);
        return var4.Ý().Ý(var4);
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.Â == 0;
    }
    
    public boolean Â() {
        return this.Ý > 0;
    }
    
    public int Ý() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z, final int value) {
        this.Ó.HorizonCode_Horizon_È(x, y, z, value);
    }
    
    public int Ø­áŒŠá(final int x, final int y, final int z) {
        return this.Ó.HorizonCode_Horizon_È(x, y, z);
    }
    
    public void Â(final int x, final int y, final int z, final int value) {
        this.Âµá€.HorizonCode_Horizon_È(x, y, z, value);
    }
    
    public int Âµá€(final int x, final int y, final int z) {
        return this.Âµá€.HorizonCode_Horizon_È(x, y, z);
    }
    
    public void Ø­áŒŠá() {
        this.Â = 0;
        this.Ý = 0;
        for (int var1 = 0; var1 < 16; ++var1) {
            for (int var2 = 0; var2 < 16; ++var2) {
                for (int var3 = 0; var3 < 16; ++var3) {
                    final Block var4 = this.Â(var1, var2, var3);
                    if (var4 != Blocks.Â) {
                        ++this.Â;
                        if (var4.ˆÏ­()) {
                            ++this.Ý;
                        }
                    }
                }
            }
        }
    }
    
    public char[] Âµá€() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final char[] dataArray) {
        this.Ø­áŒŠá = dataArray;
    }
    
    public NibbleArray Ó() {
        return this.Âµá€;
    }
    
    public NibbleArray à() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final NibbleArray newBlocklightArray) {
        this.Âµá€ = newBlocklightArray;
    }
    
    public void Â(final NibbleArray newSkylightArray) {
        this.Ó = newSkylightArray;
    }
}
