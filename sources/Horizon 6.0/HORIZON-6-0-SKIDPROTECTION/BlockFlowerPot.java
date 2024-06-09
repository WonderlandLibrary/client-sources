package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockFlowerPot extends BlockContainer
{
    public static final PropertyInteger Õ;
    public static final PropertyEnum à¢;
    private static final String ŠÂµà = "CL_00000247";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("legacy_data", 0, 15);
        à¢ = PropertyEnum.HorizonCode_Horizon_È("contents", HorizonCode_Horizon_È.class);
    }
    
    public BlockFlowerPot() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFlowerPot.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockFlowerPot.Õ, 0));
        this.ŠÄ();
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.375f;
        final float var2 = var1 / 2.0f;
        this.HorizonCode_Horizon_È(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, var1, 0.5f + var2);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof TileEntityFlowerPot) {
            final Item_1028566121 var5 = ((TileEntityFlowerPot)var4).HorizonCode_Horizon_È();
            if (var5 instanceof ItemBlock) {
                return Block.HorizonCode_Horizon_È(var5).HorizonCode_Horizon_È(worldIn, pos, renderPass);
            }
        }
        return 16777215;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final ItemStack var9 = playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ø­áŒŠá();
        if (var9 == null || !(var9.HorizonCode_Horizon_È() instanceof ItemBlock)) {
            return false;
        }
        final TileEntityFlowerPot var10 = this.áŒŠÆ(worldIn, pos);
        if (var10 == null) {
            return false;
        }
        if (var10.HorizonCode_Horizon_È() != null) {
            return false;
        }
        final Block var11 = Block.HorizonCode_Horizon_È(var9.HorizonCode_Horizon_È());
        if (!this.HorizonCode_Horizon_È(var11, var9.Ø())) {
            return false;
        }
        var10.HorizonCode_Horizon_È(var9.HorizonCode_Horizon_È(), var9.Ø());
        var10.ŠÄ();
        worldIn.áŒŠÆ(pos);
        if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            final ItemStack itemStack = var9;
            if (--itemStack.Â <= 0) {
                playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý(playerIn.Ø­Ñ¢Ï­Ø­áˆº.Ý, null);
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final Block p_149928_1_, final int p_149928_2_) {
        return p_149928_1_ == Blocks.Âµà || p_149928_1_ == Blocks.Ç || p_149928_1_ == Blocks.Ñ¢Ç || p_149928_1_ == Blocks.È || p_149928_1_ == Blocks.áŠ || p_149928_1_ == Blocks.Ø || p_149928_1_ == Blocks.á€ || (p_149928_1_ == Blocks.áƒ && p_149928_2_ == BlockTallGrass.HorizonCode_Horizon_È.Ý.Â());
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        final TileEntityFlowerPot var3 = this.áŒŠÆ(worldIn, pos);
        return (var3 != null && var3.HorizonCode_Horizon_È() != null) ? var3.HorizonCode_Horizon_È() : Items.µÐƒÓ;
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final TileEntityFlowerPot var3 = this.áŒŠÆ(worldIn, pos);
        return (var3 != null && var3.HorizonCode_Horizon_È() != null) ? var3.Â() : 0;
    }
    
    @Override
    public boolean áƒ() {
        return true;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && World.HorizonCode_Horizon_È(worldIn, pos.Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!World.HorizonCode_Horizon_È(worldIn, pos.Âµá€())) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntityFlowerPot var4 = this.áŒŠÆ(worldIn, pos);
        if (var4 != null && var4.HorizonCode_Horizon_È() != null) {
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(var4.HorizonCode_Horizon_È(), 1, var4.Â()));
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn);
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            final TileEntityFlowerPot var5 = this.áŒŠÆ(worldIn, pos);
            if (var5 != null) {
                var5.HorizonCode_Horizon_È(null, 0);
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.µÐƒÓ;
    }
    
    private TileEntityFlowerPot áŒŠÆ(final World worldIn, final BlockPos p_176442_2_) {
        final TileEntity var3 = worldIn.HorizonCode_Horizon_È(p_176442_2_);
        return (var3 instanceof TileEntityFlowerPot) ? ((TileEntityFlowerPot)var3) : null;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        Object var3 = null;
        int var4 = 0;
        switch (meta) {
            case 1: {
                var3 = Blocks.Ç;
                var4 = BlockFlower.Â.Â.Ý();
                break;
            }
            case 2: {
                var3 = Blocks.Âµà;
                break;
            }
            case 3: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â();
                break;
            }
            case 4: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.Â.Â();
                break;
            }
            case 5: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.Ý.Â();
                break;
            }
            case 6: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â();
                break;
            }
            case 7: {
                var3 = Blocks.áŠ;
                break;
            }
            case 8: {
                var3 = Blocks.È;
                break;
            }
            case 9: {
                var3 = Blocks.Ñ¢Ç;
                break;
            }
            case 10: {
                var3 = Blocks.á€;
                break;
            }
            case 11: {
                var3 = Blocks.áƒ;
                var4 = BlockTallGrass.HorizonCode_Horizon_È.Ý.Â();
                break;
            }
            case 12: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.Âµá€.Â();
                break;
            }
            case 13: {
                var3 = Blocks.Ø;
                var4 = BlockPlanks.HorizonCode_Horizon_È.Ó.Â();
                break;
            }
        }
        return new TileEntityFlowerPot(Item_1028566121.HorizonCode_Horizon_È((Block)var3), var4);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFlowerPot.à¢, BlockFlowerPot.Õ });
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockFlowerPot.Õ);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        HorizonCode_Horizon_È var4 = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        final TileEntity var5 = worldIn.HorizonCode_Horizon_È(pos);
        if (var5 instanceof TileEntityFlowerPot) {
            final TileEntityFlowerPot var6 = (TileEntityFlowerPot)var5;
            final Item_1028566121 var7 = var6.HorizonCode_Horizon_È();
            if (var7 instanceof ItemBlock) {
                final int var8 = var6.Â();
                final Block var9 = Block.HorizonCode_Horizon_È(var7);
                if (var9 == Blocks.Ø) {
                    switch (Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var8).ordinal()]) {
                        case 1: {
                            var4 = HorizonCode_Horizon_È.á;
                            break;
                        }
                        case 2: {
                            var4 = HorizonCode_Horizon_È.ˆÏ­;
                            break;
                        }
                        case 3: {
                            var4 = HorizonCode_Horizon_È.£á;
                            break;
                        }
                        case 4: {
                            var4 = HorizonCode_Horizon_È.Å;
                            break;
                        }
                        case 5: {
                            var4 = HorizonCode_Horizon_È.£à;
                            break;
                        }
                        case 6: {
                            var4 = HorizonCode_Horizon_È.µà;
                            break;
                        }
                        default: {
                            var4 = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                            break;
                        }
                    }
                }
                else if (var9 == Blocks.áƒ) {
                    switch (var8) {
                        case 0: {
                            var4 = HorizonCode_Horizon_È.Ø­à;
                            break;
                        }
                        case 2: {
                            var4 = HorizonCode_Horizon_È.µÕ;
                            break;
                        }
                        default: {
                            var4 = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                            break;
                        }
                    }
                }
                else if (var9 == Blocks.Âµà) {
                    var4 = HorizonCode_Horizon_È.ÂµÈ;
                }
                else if (var9 == Blocks.Ç) {
                    switch (Â.Â[BlockFlower.Â.HorizonCode_Horizon_È(BlockFlower.HorizonCode_Horizon_È.Â, var8).ordinal()]) {
                        case 1: {
                            var4 = HorizonCode_Horizon_È.Â;
                            break;
                        }
                        case 2: {
                            var4 = HorizonCode_Horizon_È.Ý;
                            break;
                        }
                        case 3: {
                            var4 = HorizonCode_Horizon_È.Ø­áŒŠá;
                            break;
                        }
                        case 4: {
                            var4 = HorizonCode_Horizon_È.Âµá€;
                            break;
                        }
                        case 5: {
                            var4 = HorizonCode_Horizon_È.Ó;
                            break;
                        }
                        case 6: {
                            var4 = HorizonCode_Horizon_È.à;
                            break;
                        }
                        case 7: {
                            var4 = HorizonCode_Horizon_È.Ø;
                            break;
                        }
                        case 8: {
                            var4 = HorizonCode_Horizon_È.áŒŠÆ;
                            break;
                        }
                        case 9: {
                            var4 = HorizonCode_Horizon_È.áˆºÑ¢Õ;
                            break;
                        }
                        default: {
                            var4 = HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                            break;
                        }
                    }
                }
                else if (var9 == Blocks.áŠ) {
                    var4 = HorizonCode_Horizon_È.ˆà;
                }
                else if (var9 == Blocks.È) {
                    var4 = HorizonCode_Horizon_È.¥Æ;
                }
                else if (var9 == Blocks.á€) {
                    var4 = HorizonCode_Horizon_È.Ø­à;
                }
                else if (var9 == Blocks.Ñ¢Ç) {
                    var4 = HorizonCode_Horizon_È.Æ;
                }
            }
        }
        return state.HorizonCode_Horizon_È(BlockFlowerPot.à¢, var4);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("EMPTY", 0, "EMPTY", 0, "empty"), 
        Â("POPPY", 1, "POPPY", 1, "rose"), 
        Ý("BLUE_ORCHID", 2, "BLUE_ORCHID", 2, "blue_orchid"), 
        Ø­áŒŠá("ALLIUM", 3, "ALLIUM", 3, "allium"), 
        Âµá€("HOUSTONIA", 4, "HOUSTONIA", 4, "houstonia"), 
        Ó("RED_TULIP", 5, "RED_TULIP", 5, "red_tulip"), 
        à("ORANGE_TULIP", 6, "ORANGE_TULIP", 6, "orange_tulip"), 
        Ø("WHITE_TULIP", 7, "WHITE_TULIP", 7, "white_tulip"), 
        áŒŠÆ("PINK_TULIP", 8, "PINK_TULIP", 8, "pink_tulip"), 
        áˆºÑ¢Õ("OXEYE_DAISY", 9, "OXEYE_DAISY", 9, "oxeye_daisy"), 
        ÂµÈ("DANDELION", 10, "DANDELION", 10, "dandelion"), 
        á("OAK_SAPLING", 11, "OAK_SAPLING", 11, "oak_sapling"), 
        ˆÏ­("SPRUCE_SAPLING", 12, "SPRUCE_SAPLING", 12, "spruce_sapling"), 
        £á("BIRCH_SAPLING", 13, "BIRCH_SAPLING", 13, "birch_sapling"), 
        Å("JUNGLE_SAPLING", 14, "JUNGLE_SAPLING", 14, "jungle_sapling"), 
        £à("ACACIA_SAPLING", 15, "ACACIA_SAPLING", 15, "acacia_sapling"), 
        µà("DARK_OAK_SAPLING", 16, "DARK_OAK_SAPLING", 16, "dark_oak_sapling"), 
        ˆà("MUSHROOM_RED", 17, "MUSHROOM_RED", 17, "mushroom_red"), 
        ¥Æ("MUSHROOM_BROWN", 18, "MUSHROOM_BROWN", 18, "mushroom_brown"), 
        Ø­à("DEAD_BUSH", 19, "DEAD_BUSH", 19, "dead_bush"), 
        µÕ("FERN", 20, "FERN", 20, "fern"), 
        Æ("CACTUS", 21, "CACTUS", 21, "cactus");
        
        private final String Šáƒ;
        private static final HorizonCode_Horizon_È[] Ï­Ðƒà;
        private static final String áŒŠà = "CL_00002115";
        
        static {
            ŠÄ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ };
            Ï­Ðƒà = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø, HorizonCode_Horizon_È.áŒŠÆ, HorizonCode_Horizon_È.áˆºÑ¢Õ, HorizonCode_Horizon_È.ÂµÈ, HorizonCode_Horizon_È.á, HorizonCode_Horizon_È.ˆÏ­, HorizonCode_Horizon_È.£á, HorizonCode_Horizon_È.Å, HorizonCode_Horizon_È.£à, HorizonCode_Horizon_È.µà, HorizonCode_Horizon_È.ˆà, HorizonCode_Horizon_È.¥Æ, HorizonCode_Horizon_È.Ø­à, HorizonCode_Horizon_È.µÕ, HorizonCode_Horizon_È.Æ };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45715_1_, final int p_i45715_2_, final String p_i45715_3_) {
            this.Šáƒ = p_i45715_3_;
        }
        
        @Override
        public String toString() {
            return this.Šáƒ;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Šáƒ;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00002116";
        
        static {
            Â = new int[BlockFlower.Â.values().length];
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.à.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.Ø.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.áŒŠÆ.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                BlockFlowerPot.Â.Â[BlockFlower.Â.áˆºÑ¢Õ.ordinal()] = 9;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            HorizonCode_Horizon_È = new int[BlockPlanks.HorizonCode_Horizon_È.values().length];
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                BlockFlowerPot.Â.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
        }
    }
}
