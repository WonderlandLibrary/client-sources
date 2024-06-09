package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public abstract class BlockLog extends BlockRotatedPillar
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000266";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("axis", HorizonCode_Horizon_È.class);
    }
    
    public BlockLog() {
        super(Material.Ø­áŒŠá);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
        this.Ý(2.0f);
        this.HorizonCode_Horizon_È(BlockLog.Ø­áŒŠá);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final byte var4 = 4;
        final int var5 = var4 + 1;
        if (worldIn.HorizonCode_Horizon_È(pos.Â(-var5, -var5, -var5), pos.Â(var5, var5, var5))) {
            for (final BlockPos var7 : BlockPos.Â(pos.Â(-var4, -var4, -var4), pos.Â(var4, var4, var4))) {
                final IBlockState var8 = worldIn.Â(var7);
                if (var8.Ý().Ó() == Material.áˆºÑ¢Õ && !(boolean)var8.HorizonCode_Horizon_È(BlockLeaves.à¢)) {
                    worldIn.HorizonCode_Horizon_È(var7, var8.HorizonCode_Horizon_È(BlockLeaves.à¢, true), 4);
                }
            }
        }
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return super.HorizonCode_Horizon_È(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).HorizonCode_Horizon_È(BlockLog.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(facing.á()));
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("X", 0, "X", 0, "x"), 
        Â("Y", 1, "Y", 1, "y"), 
        Ý("Z", 2, "Z", 2, "z"), 
        Ø­áŒŠá("NONE", 3, "NONE", 3, "none");
        
        private final String Âµá€;
        private static final HorizonCode_Horizon_È[] Ó;
        private static final String à = "CL_00002100";
        
        static {
            Ø = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45708_1_, final int p_i45708_2_, final String p_i45708_3_) {
            this.Âµá€ = p_i45708_3_;
        }
        
        @Override
        public String toString() {
            return this.Âµá€;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final EnumFacing.HorizonCode_Horizon_È p_176870_0_) {
            switch (BlockLog.Â.HorizonCode_Horizon_È[p_176870_0_.ordinal()]) {
                case 1: {
                    return HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                }
                case 2: {
                    return HorizonCode_Horizon_È.Â;
                }
                case 3: {
                    return HorizonCode_Horizon_È.Ý;
                }
                default: {
                    return HorizonCode_Horizon_È.Ø­áŒŠá;
                }
            }
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002101";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                BlockLog.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockLog.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockLog.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
        }
    }
}
