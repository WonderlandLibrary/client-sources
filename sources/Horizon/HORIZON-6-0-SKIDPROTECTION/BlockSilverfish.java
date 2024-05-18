package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000271";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockSilverfish() {
        super(Material.ŒÏ);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSilverfish.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.Ý(0.0f);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    public static boolean áŒŠÆ(final IBlockState p_176377_0_) {
        final Block var1 = p_176377_0_.Ý();
        return p_176377_0_ == Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È) || var1 == Blocks.Ó || var1 == Blocks.£áƒ;
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        switch (Â.HorizonCode_Horizon_È[((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSilverfish.Õ)).ordinal()]) {
            case 1: {
                return new ItemStack(Blocks.Ó);
            }
            case 2: {
                return new ItemStack(Blocks.£áƒ);
            }
            case 3: {
                return new ItemStack(Blocks.£áƒ, 1, BlockStoneBrick.HorizonCode_Horizon_È.Â.Â());
            }
            case 4: {
                return new ItemStack(Blocks.£áƒ, 1, BlockStoneBrick.HorizonCode_Horizon_È.Ý.Â());
            }
            case 5: {
                return new ItemStack(Blocks.£áƒ, 1, BlockStoneBrick.HorizonCode_Horizon_È.Ø­áŒŠá.Â());
            }
            default: {
                return new ItemStack(Blocks.Ý);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        if (!worldIn.ŠÄ && worldIn.Çªà¢().Â("doTileDrops")) {
            final EntitySilverfish var6 = new EntitySilverfish(worldIn);
            var6.Â(pos.HorizonCode_Horizon_È() + 0.5, pos.Â(), pos.Ý() + 0.5, 0.0f, 0.0f);
            worldIn.HorizonCode_Horizon_È(var6);
            var6.£Ø­à();
        }
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos);
        return var3.Ý().Ý(var3);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSilverfish.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSilverfish.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSilverfish.Õ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È(0, "STONE", 0, 0, "stone", (Â)null) {
            private static final String à = "CL_00002097";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            }
        }, 
        Â(1, "COBBLESTONE", 1, 1, "cobblestone", "cobble", (Â)null) {
            private static final String à = "CL_00002096";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.Ó.¥à();
            }
        }, 
        Ý(2, "STONEBRICK", 2, 2, "stone_brick", "brick", (Â)null) {
            private static final String à = "CL_00002095";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.£áƒ.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, BlockStoneBrick.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            }
        }, 
        Ø­áŒŠá(3, "MOSSY_STONEBRICK", 3, 3, "mossy_brick", "mossybrick", (Â)null) {
            private static final String à = "CL_00002094";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.£áƒ.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, BlockStoneBrick.HorizonCode_Horizon_È.Â);
            }
        }, 
        Âµá€(4, "CRACKED_STONEBRICK", 4, 4, "cracked_brick", "crackedbrick", (Â)null) {
            private static final String à = "CL_00002093";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.£áƒ.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, BlockStoneBrick.HorizonCode_Horizon_È.Ý);
            }
        }, 
        Ó(5, "CHISELED_STONEBRICK", 5, 5, "chiseled_brick", "chiseledbrick", (Â)null) {
            private static final String à = "CL_00002092";
            
            @Override
            public IBlockState Ø­áŒŠá() {
                return Blocks.£áƒ.¥à().HorizonCode_Horizon_È(BlockStoneBrick.Õ, BlockStoneBrick.HorizonCode_Horizon_È.Ø­áŒŠá);
            }
        };
        
        private static final HorizonCode_Horizon_È[] à;
        private final int Ø;
        private final String áŒŠÆ;
        private final String áˆºÑ¢Õ;
        private static final HorizonCode_Horizon_È[] ÂµÈ;
        private static final String á = "CL_00002098";
        
        static {
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            à = new HorizonCode_Horizon_È[values().length];
            ÂµÈ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.à[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45704_1_, final int p_i45704_2_, final int p_i45704_3_, final String p_i45704_4_) {
            this(s, n, p_i45704_1_, p_i45704_2_, p_i45704_3_, p_i45704_4_, p_i45704_4_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45705_1_, final int p_i45705_2_, final int p_i45705_3_, final String p_i45705_4_, final String p_i45705_5_) {
            this.Ø = p_i45705_3_;
            this.áŒŠÆ = p_i45705_4_;
            this.áˆºÑ¢Õ = p_i45705_5_;
        }
        
        public int Â() {
            return this.Ø;
        }
        
        @Override
        public String toString() {
            return this.áŒŠÆ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176879_0_) {
            if (p_176879_0_ < 0 || p_176879_0_ >= HorizonCode_Horizon_È.à.length) {
                p_176879_0_ = 0;
            }
            return HorizonCode_Horizon_È.à[p_176879_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.áŒŠÆ;
        }
        
        public String Ý() {
            return this.áˆºÑ¢Õ;
        }
        
        public abstract IBlockState Ø­áŒŠá();
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final IBlockState p_176878_0_) {
            for (final HorizonCode_Horizon_È var4 : values()) {
                if (p_176878_0_ == var4.Ø­áŒŠá()) {
                    return var4;
                }
            }
            return HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45706_1_, final int p_i45706_2_, final int p_i45706_3_, final String p_i45706_4_, final Â p_i45706_5_) {
            this(s, n, p_i45706_1_, p_i45706_2_, p_i45706_3_, p_i45706_4_);
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45707_1_, final int p_i45707_2_, final int p_i45707_3_, final String p_i45707_4_, final String p_i45707_5_, final Â p_i45707_6_) {
            this(s, n, p_i45707_1_, p_i45707_2_, p_i45707_3_, p_i45707_4_, p_i45707_5_);
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002099";
        
        static {
            HorizonCode_Horizon_È = new int[BlockSilverfish.HorizonCode_Horizon_È.values().length];
            try {
                BlockSilverfish.Â.HorizonCode_Horizon_È[BlockSilverfish.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockSilverfish.Â.HorizonCode_Horizon_È[BlockSilverfish.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockSilverfish.Â.HorizonCode_Horizon_È[BlockSilverfish.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockSilverfish.Â.HorizonCode_Horizon_È[BlockSilverfish.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockSilverfish.Â.HorizonCode_Horizon_È[BlockSilverfish.HorizonCode_Horizon_È.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
