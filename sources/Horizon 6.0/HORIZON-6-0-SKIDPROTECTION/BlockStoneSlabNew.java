package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public abstract class BlockStoneSlabNew extends BlockSlab
{
    public static final PropertyBool à¢;
    public static final PropertyEnum ŠÂµà;
    private static final String ¥à = "CL_00002087";
    
    static {
        à¢ = PropertyBool.HorizonCode_Horizon_È("seamless");
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockStoneSlabNew() {
        super(Material.Âµá€);
        IBlockState var1 = this.á€.Â();
        if (this.È()) {
            var1 = var1.HorizonCode_Horizon_È(BlockStoneSlabNew.à¢, false);
        }
        else {
            var1 = var1.HorizonCode_Horizon_È(BlockStoneSlabNew.Õ, BlockSlab.HorizonCode_Horizon_È.Â);
        }
        this.Ø(var1.HorizonCode_Horizon_È(BlockStoneSlabNew.ŠÂµà, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.µØ);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.µØ);
    }
    
    @Override
    public String Âµá€(final int p_150002_1_) {
        return String.valueOf(super.Çªà¢()) + "." + HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_150002_1_).Ý();
    }
    
    @Override
    public IProperty áŠ() {
        return BlockStoneSlabNew.ŠÂµà;
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final ItemStack p_176553_1_) {
        return HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176553_1_.Ø() & 0x7);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        if (itemIn != Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇªØ)) {
            for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
                list.add(new ItemStack(itemIn, 1, var7.Â()));
            }
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à().HorizonCode_Horizon_È(BlockStoneSlabNew.ŠÂµà, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7));
        if (this.È()) {
            var2 = var2.HorizonCode_Horizon_È(BlockStoneSlabNew.à¢, (meta & 0x8) != 0x0);
        }
        else {
            var2 = var2.HorizonCode_Horizon_È(BlockStoneSlabNew.Õ, ((meta & 0x8) == 0x0) ? BlockSlab.HorizonCode_Horizon_È.Â : BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneSlabNew.ŠÂµà)).Â();
        if (this.È()) {
            if (state.HorizonCode_Horizon_È(BlockStoneSlabNew.à¢)) {
                var3 |= 0x8;
            }
        }
        else if (state.HorizonCode_Horizon_È(BlockStoneSlabNew.Õ) == BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return this.È() ? new BlockState(this, new IProperty[] { BlockStoneSlabNew.à¢, BlockStoneSlabNew.ŠÂµà }) : new BlockState(this, new IProperty[] { BlockStoneSlabNew.Õ, BlockStoneSlabNew.ŠÂµà });
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneSlabNew.ŠÂµà)).Â();
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("RED_SANDSTONE", 0, "RED_SANDSTONE", 0, 0, "red_sandstone");
        
        private static final HorizonCode_Horizon_È[] Â;
        private final int Ý;
        private final String Ø­áŒŠá;
        private static final HorizonCode_Horizon_È[] Âµá€;
        private static final String Ó = "CL_00002086";
        
        static {
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È };
            Â = new HorizonCode_Horizon_È[values().length];
            Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Â[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45697_1_, final int p_i45697_2_, final int p_i45697_3_, final String p_i45697_4_) {
            this.Ý = p_i45697_3_;
            this.Ø­áŒŠá = p_i45697_4_;
        }
        
        public int Â() {
            return this.Ý;
        }
        
        @Override
        public String toString() {
            return this.Ø­áŒŠá;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176916_0_) {
            if (p_176916_0_ < 0 || p_176916_0_ >= HorizonCode_Horizon_È.Â.length) {
                p_176916_0_ = 0;
            }
            return HorizonCode_Horizon_È.Â[p_176916_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá;
        }
        
        public String Ý() {
            return this.Ø­áŒŠá;
        }
    }
}
