package HORIZON-6-0-SKIDPROTECTION;

public class ChunkCoordIntPair
{
    public final int HorizonCode_Horizon_È;
    public final int Â;
    private static final String Ý = "CL_00000133";
    private int Ø­áŒŠá;
    
    public ChunkCoordIntPair(final int x, final int z) {
        this.Ø­áŒŠá = 0;
        this.HorizonCode_Horizon_È = x;
        this.Â = z;
    }
    
    public static long HorizonCode_Horizon_È(final int x, final int z) {
        return (x & 0xFFFFFFFFL) | (z & 0xFFFFFFFFL) << 32;
    }
    
    @Override
    public int hashCode() {
        if (this.Ø­áŒŠá == 0) {
            final int var1 = 1664525 * this.HorizonCode_Horizon_È + 1013904223;
            final int var2 = 1664525 * (this.Â ^ 0xDEADBEEF) + 1013904223;
            this.Ø­áŒŠá = (var1 ^ var2);
        }
        return this.Ø­áŒŠá;
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChunkCoordIntPair)) {
            return false;
        }
        final ChunkCoordIntPair var2 = (ChunkCoordIntPair)p_equals_1_;
        return this.HorizonCode_Horizon_È == var2.HorizonCode_Horizon_È && this.Â == var2.Â;
    }
    
    public int HorizonCode_Horizon_È() {
        return (this.HorizonCode_Horizon_È << 4) + 8;
    }
    
    public int Â() {
        return (this.Â << 4) + 8;
    }
    
    public int Ý() {
        return this.HorizonCode_Horizon_È << 4;
    }
    
    public int Ø­áŒŠá() {
        return this.Â << 4;
    }
    
    public int Âµá€() {
        return (this.HorizonCode_Horizon_È << 4) + 15;
    }
    
    public int Ó() {
        return (this.Â << 4) + 15;
    }
    
    public BlockPos HorizonCode_Horizon_È(final int x, final int y, final int z) {
        return new BlockPos((this.HorizonCode_Horizon_È << 4) + x, y, (this.Â << 4) + z);
    }
    
    public BlockPos HorizonCode_Horizon_È(final int y) {
        return new BlockPos(this.HorizonCode_Horizon_È(), y, this.Â());
    }
    
    @Override
    public String toString() {
        return "[" + this.HorizonCode_Horizon_È + ", " + this.Â + "]";
    }
}
