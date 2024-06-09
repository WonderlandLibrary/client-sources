package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockTallGrass extends BlockBush implements IGrowable
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000321";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("type", HorizonCode_Horizon_È.class);
    }
    
    protected BlockTallGrass() {
        super(Material.á);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTallGrass.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        final float var1 = 0.4f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }
    
    @Override
    public int Ï­Ðƒà() {
        return ColorizerGrass.HorizonCode_Horizon_È(0.5, 1.0);
    }
    
    @Override
    public boolean Ó(final World worldIn, final BlockPos p_180671_2_, final IBlockState p_180671_3_) {
        return this.Ý(worldIn.Â(p_180671_2_.Âµá€()).Ý());
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        return true;
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        if (state.Ý() != this) {
            return super.Âµá€(state);
        }
        final HorizonCode_Horizon_È var2 = (HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockTallGrass.Õ);
        return (var2 == HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? 16777215 : ColorizerGrass.HorizonCode_Horizon_È(0.5, 1.0);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return worldIn.Ý(pos).Â(pos);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return (rand.nextInt(8) == 0) ? Items.¥à : null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final int fortune, final Random random) {
        return 1 + random.nextInt(fortune * 2 + 1);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Blocks.áƒ, 1, ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockTallGrass.Õ)).Â()));
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos);
        return var3.Ý().Ý(var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (int var4 = 1; var4 < 3; ++var4) {
            list.add(new ItemStack(itemIn, 1, var4));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return p_176473_3_.HorizonCode_Horizon_È(BlockTallGrass.Õ) != HorizonCode_Horizon_È.HorizonCode_Horizon_È;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        BlockDoublePlant.Â var5 = BlockDoublePlant.Â.Ý;
        if (p_176474_4_.HorizonCode_Horizon_È(BlockTallGrass.Õ) == HorizonCode_Horizon_È.Ý) {
            var5 = BlockDoublePlant.Â.Ø­áŒŠá;
        }
        if (Blocks.ÇªÇªÉ.Ø­áŒŠá(worldIn, p_176474_3_)) {
            Blocks.ÇªÇªÉ.HorizonCode_Horizon_È(worldIn, p_176474_3_, var5, 2);
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockTallGrass.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockTallGrass.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTallGrass.Õ });
    }
    
    @Override
    public Block.HorizonCode_Horizon_È Âµà() {
        return Block.HorizonCode_Horizon_È.Ý;
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DEAD_BUSH", 0, "DEAD_BUSH", 0, 0, "dead_bush"), 
        Â("GRASS", 1, "GRASS", 1, 1, "tall_grass"), 
        Ý("FERN", 2, "FERN", 2, 2, "fern");
        
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private final int Âµá€;
        private final String Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002055";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            Ø­áŒŠá = new HorizonCode_Horizon_È[values().length];
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ø­áŒŠá[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45676_1_, final int p_i45676_2_, final int p_i45676_3_, final String p_i45676_4_) {
            this.Âµá€ = p_i45676_3_;
            this.Ó = p_i45676_4_;
        }
        
        public int Â() {
            return this.Âµá€;
        }
        
        @Override
        public String toString() {
            return this.Ó;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_177045_0_) {
            if (p_177045_0_ < 0 || p_177045_0_ >= HorizonCode_Horizon_È.Ø­áŒŠá.length) {
                p_177045_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ø­áŒŠá[p_177045_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ó;
        }
    }
}
