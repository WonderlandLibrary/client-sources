package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    public static final PropertyBool Õ;
    private final HorizonCode_Horizon_È à¢;
    private static final String ŠÂµà = "CL_00000289";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("powered");
    }
    
    protected BlockPressurePlate(final Material p_i45693_1_, final HorizonCode_Horizon_È p_i45693_2_) {
        super(p_i45693_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPressurePlate.Õ, false));
        this.à¢ = p_i45693_2_;
    }
    
    @Override
    protected int áˆºÑ¢Õ(final IBlockState p_176576_1_) {
        return p_176576_1_.HorizonCode_Horizon_È(BlockPressurePlate.Õ) ? 15 : 0;
    }
    
    @Override
    protected IBlockState HorizonCode_Horizon_È(final IBlockState p_176575_1_, final int p_176575_2_) {
        return p_176575_1_.HorizonCode_Horizon_È(BlockPressurePlate.Õ, p_176575_2_ > 0);
    }
    
    @Override
    protected int áˆºÑ¢Õ(final World worldIn, final BlockPos pos) {
        final AxisAlignedBB var3 = this.HorizonCode_Horizon_È(pos);
        List var4 = null;
        switch (Â.HorizonCode_Horizon_È[this.à¢.ordinal()]) {
            case 1: {
                var4 = worldIn.Â(null, var3);
                break;
            }
            case 2: {
                var4 = worldIn.HorizonCode_Horizon_È(EntityLivingBase.class, var3);
                break;
            }
            default: {
                return 0;
            }
        }
        if (!var4.isEmpty()) {
            for (final Entity var6 : var4) {
                if (!var6.Ñ¢à()) {
                    return 15;
                }
            }
        }
        return 0;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPressurePlate.Õ, meta == 1);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((boolean)state.HorizonCode_Horizon_È(BlockPressurePlate.Õ)) ? 1 : 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPressurePlate.Õ });
    }
    
    public enum HorizonCode_Horizon_È
    {
        HorizonCode_Horizon_È("EVERYTHING", 0, "EVERYTHING", 0), 
        Â("MOBS", 1, "MOBS", 1);
        
        private static final HorizonCode_Horizon_È[] Ý;
        private static final String Ø­áŒŠá = "CL_00000290";
        
        static {
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45417_1_, final int p_i45417_2_) {
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002078";
        
        static {
            HorizonCode_Horizon_È = new int[BlockPressurePlate.HorizonCode_Horizon_È.values().length];
            try {
                BlockPressurePlate.Â.HorizonCode_Horizon_È[BlockPressurePlate.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockPressurePlate.Â.HorizonCode_Horizon_È[BlockPressurePlate.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
