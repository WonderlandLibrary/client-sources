package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public abstract class BlockStoneSlab extends BlockSlab
{
    public static final PropertyBool à¢;
    public static final PropertyEnum ŠÂµà;
    private static final String ¥à = "CL_00000320";
    
    static {
        à¢ = PropertyBool.HorizonCode_Horizon_È("seamless");
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockStoneSlab() {
        super(Material.Âµá€);
        IBlockState var1 = this.á€.Â();
        if (this.È()) {
            var1 = var1.HorizonCode_Horizon_È(BlockStoneSlab.à¢, false);
        }
        else {
            var1 = var1.HorizonCode_Horizon_È(BlockStoneSlab.Õ, BlockSlab.HorizonCode_Horizon_È.Â);
        }
        this.Ø(var1.HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Âµ);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.Ø­Âµ);
    }
    
    @Override
    public String Âµá€(final int p_150002_1_) {
        return String.valueOf(super.Çªà¢()) + "." + HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_150002_1_).Ý();
    }
    
    @Override
    public IProperty áŠ() {
        return BlockStoneSlab.ŠÂµà;
    }
    
    @Override
    public Object HorizonCode_Horizon_È(final ItemStack p_176553_1_) {
        return HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_176553_1_.Ø() & 0x7);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        if (itemIn != Item_1028566121.HorizonCode_Horizon_È(Blocks.£ÂµÄ)) {
            for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
                if (var7 != HorizonCode_Horizon_È.Ý) {
                    list.add(new ItemStack(itemIn, 1, var7.Â()));
                }
            }
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à().HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7));
        if (this.È()) {
            var2 = var2.HorizonCode_Horizon_È(BlockStoneSlab.à¢, (meta & 0x8) != 0x0);
        }
        else {
            var2 = var2.HorizonCode_Horizon_È(BlockStoneSlab.Õ, ((meta & 0x8) == 0x0) ? BlockSlab.HorizonCode_Horizon_È.Â : BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà)).Â();
        if (this.È()) {
            if (state.HorizonCode_Horizon_È(BlockStoneSlab.à¢)) {
                var3 |= 0x8;
            }
        }
        else if (state.HorizonCode_Horizon_È(BlockStoneSlab.Õ) == BlockSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return this.È() ? new BlockState(this, new IProperty[] { BlockStoneSlab.à¢, BlockStoneSlab.ŠÂµà }) : new BlockState(this, new IProperty[] { BlockStoneSlab.Õ, BlockStoneSlab.ŠÂµà });
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockStoneSlab.ŠÂµà)).Â();
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("STONE", 0, "STONE", 0, 0, "stone"), 
        Â("SAND", 1, "SAND", 1, 1, "sandstone", "sand"), 
        Ý("WOOD", 2, "WOOD", 2, 2, "wood_old", "wood"), 
        Ø­áŒŠá("COBBLESTONE", 3, "COBBLESTONE", 3, 3, "cobblestone", "cobble"), 
        Âµá€("BRICK", 4, "BRICK", 4, 4, "brick"), 
        Ó("SMOOTHBRICK", 5, "SMOOTHBRICK", 5, 5, "stone_brick", "smoothStoneBrick"), 
        à("NETHERBRICK", 6, "NETHERBRICK", 6, 6, "nether_brick", "netherBrick"), 
        Ø("QUARTZ", 7, "QUARTZ", 7, 7, "quartz");
        
        private static final HorizonCode_Horizon_È[] áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private final String ÂµÈ;
        private final String á;
        private static final HorizonCode_Horizon_È[] ˆÏ­;
        private static final String £á = "CL_00002056";
        
        static {
            Å = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø };
            áŒŠÆ = new HorizonCode_Horizon_È[values().length];
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.áŒŠÆ[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45677_1_, final int p_i45677_2_, final int p_i45677_3_, final String p_i45677_4_) {
            this(s, n, p_i45677_1_, p_i45677_2_, p_i45677_3_, p_i45677_4_, p_i45677_4_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45678_1_, final int p_i45678_2_, final int p_i45678_3_, final String p_i45678_4_, final String p_i45678_5_) {
            this.áˆºÑ¢Õ = p_i45678_3_;
            this.ÂµÈ = p_i45678_4_;
            this.á = p_i45678_5_;
        }
        
        public int Â() {
            return this.áˆºÑ¢Õ;
        }
        
        @Override
        public String toString() {
            return this.ÂµÈ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176625_0_) {
            if (p_176625_0_ < 0 || p_176625_0_ >= HorizonCode_Horizon_È.áŒŠÆ.length) {
                p_176625_0_ = 0;
            }
            return HorizonCode_Horizon_È.áŒŠÆ[p_176625_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.ÂµÈ;
        }
        
        public String Ý() {
            return this.á;
        }
    }
}
