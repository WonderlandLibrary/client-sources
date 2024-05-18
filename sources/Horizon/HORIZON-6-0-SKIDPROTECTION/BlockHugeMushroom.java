package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockHugeMushroom extends Block
{
    public static final PropertyEnum Õ;
    private final Block à¢;
    private static final String ŠÂµà = "CL_00000258";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockHugeMushroom(final Material p_i45711_1_, final Block p_i45711_2_) {
        super(p_i45711_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockHugeMushroom.Õ, HorizonCode_Horizon_È.á));
        this.à¢ = p_i45711_2_;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return Math.max(0, random.nextInt(10) - 7);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(this.à¢);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(this.à¢);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockHugeMushroom.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockHugeMushroom.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockHugeMushroom.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("NORTH_WEST", 0, "NORTH_WEST", 0, 1, "north_west"), 
        Â("NORTH", 1, "NORTH", 1, 2, "north"), 
        Ý("NORTH_EAST", 2, "NORTH_EAST", 2, 3, "north_east"), 
        Ø­áŒŠá("WEST", 3, "WEST", 3, 4, "west"), 
        Âµá€("CENTER", 4, "CENTER", 4, 5, "center"), 
        Ó("EAST", 5, "EAST", 5, 6, "east"), 
        à("SOUTH_WEST", 6, "SOUTH_WEST", 6, 7, "south_west"), 
        Ø("SOUTH", 7, "SOUTH", 7, 8, "south"), 
        áŒŠÆ("SOUTH_EAST", 8, "SOUTH_EAST", 8, 9, "south_east"), 
        áˆºÑ¢Õ("STEM", 9, "STEM", 9, 10, "stem"), 
        ÂµÈ("ALL_INSIDE", 10, "ALL_INSIDE", 10, 0, "all_inside"), 
        á("ALL_OUTSIDE", 11, "ALL_OUTSIDE", 11, 14, "all_outside"), 
        ˆÏ­("ALL_STEM", 12, "ALL_STEM", 12, 15, "all_stem");
        
        private static final HorizonCode_Horizon_È[] £á;
        private final int Å;
        private final String £à;
        private static final HorizonCode_Horizon_È[] µà;
        private static final String ˆà = "CL_00002105";
        
        static {
            ¥Æ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­ };
            £á = new HorizonCode_Horizon_È[16];
            µà = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­ };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.£á[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45710_1_, final int p_i45710_2_, final int p_i45710_3_, final String p_i45710_4_) {
            this.Å = p_i45710_3_;
            this.£à = p_i45710_4_;
        }
        
        public int Â() {
            return this.Å;
        }
        
        @Override
        public String toString() {
            return this.£à;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176895_0_) {
            if (p_176895_0_ < 0 || p_176895_0_ >= HorizonCode_Horizon_È.£á.length) {
                p_176895_0_ = 0;
            }
            final HorizonCode_Horizon_È var1 = HorizonCode_Horizon_È.£á[p_176895_0_];
            return (var1 == null) ? HorizonCode_Horizon_È.£á[0] : var1;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.£à;
        }
    }
}
