package HORIZON-6-0-SKIDPROTECTION;

public class MovingObjectPosition
{
    private BlockPos Âµá€;
    public HorizonCode_Horizon_È HorizonCode_Horizon_È;
    public EnumFacing Â;
    public Vec3 Ý;
    public Entity Ø­áŒŠá;
    private static final String Ó = "CL_00000610";
    
    public MovingObjectPosition(final Vec3 p_i45551_1_, final EnumFacing p_i45551_2_, final BlockPos p_i45551_3_) {
        this(MovingObjectPosition.HorizonCode_Horizon_È.Â, p_i45551_1_, p_i45551_2_, p_i45551_3_);
    }
    
    public MovingObjectPosition(final Vec3 p_i45552_1_, final EnumFacing p_i45552_2_) {
        this(MovingObjectPosition.HorizonCode_Horizon_È.Â, p_i45552_1_, p_i45552_2_, BlockPos.HorizonCode_Horizon_È);
    }
    
    public MovingObjectPosition(final Entity p_i2304_1_) {
        this(p_i2304_1_, new Vec3(p_i2304_1_.ŒÏ, p_i2304_1_.Çªà¢, p_i2304_1_.Ê));
    }
    
    public MovingObjectPosition(final HorizonCode_Horizon_È p_i45553_1_, final Vec3 p_i45553_2_, final EnumFacing p_i45553_3_, final BlockPos p_i45553_4_) {
        this.HorizonCode_Horizon_È = p_i45553_1_;
        this.Âµá€ = p_i45553_4_;
        this.Â = p_i45553_3_;
        this.Ý = new Vec3(p_i45553_2_.HorizonCode_Horizon_È, p_i45553_2_.Â, p_i45553_2_.Ý);
    }
    
    public MovingObjectPosition(final Entity p_i45482_1_, final Vec3 p_i45482_2_) {
        this.HorizonCode_Horizon_È = MovingObjectPosition.HorizonCode_Horizon_È.Ý;
        this.Ø­áŒŠá = p_i45482_1_;
        this.Ý = p_i45482_2_;
    }
    
    public BlockPos HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    @Override
    public String toString() {
        return "HitResult{type=" + this.HorizonCode_Horizon_È + ", blockpos=" + this.Âµá€ + ", f=" + this.Â + ", pos=" + this.Ý + ", entity=" + this.Ø­áŒŠá + '}';
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("MISS", 0, "MISS", 0), 
        Â("BLOCK", 1, "BLOCK", 1), 
        Ý("ENTITY", 2, "ENTITY", 2);
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000611";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i2302_1_, final int p_i2302_2_) {
        }
    }
}
