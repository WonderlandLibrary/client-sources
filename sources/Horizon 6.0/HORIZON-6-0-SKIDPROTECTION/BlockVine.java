package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;

public class BlockVine extends Block
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    public static final PropertyBool Âµà;
    public static final PropertyBool[] Ç;
    public static final int È;
    public static final int áŠ;
    public static final int ˆáŠ;
    public static final int áŒŠ;
    private static final String £ÂµÄ = "CL_00000330";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("up");
        à¢ = PropertyBool.HorizonCode_Horizon_È("north");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("east");
        ¥à = PropertyBool.HorizonCode_Horizon_È("south");
        Âµà = PropertyBool.HorizonCode_Horizon_È("west");
        Ç = new PropertyBool[] { BlockVine.Õ, BlockVine.à¢, BlockVine.¥à, BlockVine.Âµà, BlockVine.ŠÂµà };
        È = Â(EnumFacing.Ø­áŒŠá);
        áŠ = Â(EnumFacing.Ý);
        ˆáŠ = Â(EnumFacing.Ó);
        áŒŠ = Â(EnumFacing.Âµá€);
    }
    
    public BlockVine() {
        super(Material.á);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockVine.Õ, false).HorizonCode_Horizon_È(BlockVine.à¢, false).HorizonCode_Horizon_È(BlockVine.ŠÂµà, false).HorizonCode_Horizon_È(BlockVine.¥à, false).HorizonCode_Horizon_È(BlockVine.Âµà, false));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockVine.Õ, worldIn.Â(pos.Ø­áŒŠá()).Ý().à());
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        return true;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final float var3 = 0.0625f;
        float var4 = 1.0f;
        float var5 = 1.0f;
        float var6 = 1.0f;
        float var7 = 0.0f;
        float var8 = 0.0f;
        float var9 = 0.0f;
        boolean var10 = false;
        if (access.Â(pos).HorizonCode_Horizon_È(BlockVine.Âµà)) {
            var7 = Math.max(var7, 0.0625f);
            var4 = 0.0f;
            var5 = 0.0f;
            var8 = 1.0f;
            var6 = 0.0f;
            var9 = 1.0f;
            var10 = true;
        }
        if (access.Â(pos).HorizonCode_Horizon_È(BlockVine.ŠÂµà)) {
            var4 = Math.min(var4, 0.9375f);
            var7 = 1.0f;
            var5 = 0.0f;
            var8 = 1.0f;
            var6 = 0.0f;
            var9 = 1.0f;
            var10 = true;
        }
        if (access.Â(pos).HorizonCode_Horizon_È(BlockVine.à¢)) {
            var9 = Math.max(var9, 0.0625f);
            var6 = 0.0f;
            var4 = 0.0f;
            var7 = 1.0f;
            var5 = 0.0f;
            var8 = 1.0f;
            var10 = true;
        }
        if (access.Â(pos).HorizonCode_Horizon_È(BlockVine.¥à)) {
            var6 = Math.min(var6, 0.9375f);
            var9 = 1.0f;
            var4 = 0.0f;
            var7 = 1.0f;
            var5 = 0.0f;
            var8 = 1.0f;
            var10 = true;
        }
        if (!var10 && this.Ý(access.Â(pos.Ø­áŒŠá()).Ý())) {
            var5 = Math.min(var5, 0.9375f);
            var8 = 1.0f;
            var4 = 0.0f;
            var7 = 1.0f;
            var6 = 0.0f;
            var9 = 1.0f;
        }
        this.HorizonCode_Horizon_È(var4, var5, var6, var7, var8, var9);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[side.ordinal()]) {
            case 1: {
                return this.Ý(worldIn.Â(pos.Ø­áŒŠá()).Ý());
            }
            case 2:
            case 3:
            case 4:
            case 5: {
                return this.Ý(worldIn.Â(pos.HorizonCode_Horizon_È(side.Âµá€())).Ý());
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean Ý(final Block p_150093_1_) {
        return p_150093_1_.áˆºÑ¢Õ() && p_150093_1_.É.Ø­áŒŠá();
    }
    
    private boolean Âµá€(final World worldIn, final BlockPos p_176269_2_, IBlockState p_176269_3_) {
        final IBlockState var4 = p_176269_3_;
        for (final EnumFacing var6 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            final PropertyBool var7 = HorizonCode_Horizon_È(var6);
            if ((boolean)p_176269_3_.HorizonCode_Horizon_È(var7) && !this.Ý(worldIn.Â(p_176269_2_.HorizonCode_Horizon_È(var6)).Ý())) {
                final IBlockState var8 = worldIn.Â(p_176269_2_.Ø­áŒŠá());
                if (var8.Ý() == this && (boolean)var8.HorizonCode_Horizon_È(var7)) {
                    continue;
                }
                p_176269_3_ = p_176269_3_.HorizonCode_Horizon_È(var7, false);
            }
        }
        if (áŒŠÆ(p_176269_3_) == 0) {
            return false;
        }
        if (var4 != p_176269_3_) {
            worldIn.HorizonCode_Horizon_È(p_176269_2_, p_176269_3_, 2);
        }
        return true;
    }
    
    @Override
    public int Ï­Ðƒà() {
        return ColorizerFoliage.Ý();
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        return ColorizerFoliage.Ý();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return worldIn.Ý(pos).Ý(pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ && !this.Âµá€(worldIn, pos, state)) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && worldIn.Å.nextInt(4) == 0) {
            final byte var5 = 4;
            int var6 = 5;
            boolean var7 = false;
        Label_0115:
            for (int var8 = -var5; var8 <= var5; ++var8) {
                for (int var9 = -var5; var9 <= var5; ++var9) {
                    for (int var10 = -1; var10 <= 1; ++var10) {
                        if (worldIn.Â(pos.Â(var8, var10, var9)).Ý() == this && --var6 <= 0) {
                            var7 = true;
                            break Label_0115;
                        }
                    }
                }
            }
            final EnumFacing var11 = EnumFacing.HorizonCode_Horizon_È(rand);
            if (var11 == EnumFacing.Â && pos.Â() < 255 && worldIn.Ø­áŒŠá(pos.Ø­áŒŠá())) {
                if (!var7) {
                    IBlockState var12 = state;
                    for (final EnumFacing var14 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                        if (rand.nextBoolean() || !this.Ý(worldIn.Â(pos.HorizonCode_Horizon_È(var14).Ø­áŒŠá()).Ý())) {
                            var12 = var12.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var14), false);
                        }
                    }
                    if ((boolean)var12.HorizonCode_Horizon_È(BlockVine.à¢) || (boolean)var12.HorizonCode_Horizon_È(BlockVine.ŠÂµà) || (boolean)var12.HorizonCode_Horizon_È(BlockVine.¥à) || (boolean)var12.HorizonCode_Horizon_È(BlockVine.Âµà)) {
                        worldIn.HorizonCode_Horizon_È(pos.Ø­áŒŠá(), var12, 2);
                    }
                }
            }
            else if (var11.á().Ø­áŒŠá() && !(boolean)state.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var11))) {
                if (!var7) {
                    final BlockPos var15 = pos.HorizonCode_Horizon_È(var11);
                    final Block var16 = worldIn.Â(var15).Ý();
                    if (var16.É == Material.HorizonCode_Horizon_È) {
                        final EnumFacing var14 = var11.Ó();
                        final EnumFacing var17 = var11.à();
                        final boolean var18 = (boolean)state.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var14));
                        final boolean var19 = (boolean)state.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var17));
                        final BlockPos var20 = var15.HorizonCode_Horizon_È(var14);
                        final BlockPos var21 = var15.HorizonCode_Horizon_È(var17);
                        if (var18 && this.Ý(worldIn.Â(var20).Ý())) {
                            worldIn.HorizonCode_Horizon_È(var15, this.¥à().HorizonCode_Horizon_È(HorizonCode_Horizon_È(var14), true), 2);
                        }
                        else if (var19 && this.Ý(worldIn.Â(var21).Ý())) {
                            worldIn.HorizonCode_Horizon_È(var15, this.¥à().HorizonCode_Horizon_È(HorizonCode_Horizon_È(var17), true), 2);
                        }
                        else if (var18 && worldIn.Ø­áŒŠá(var20) && this.Ý(worldIn.Â(pos.HorizonCode_Horizon_È(var14)).Ý())) {
                            worldIn.HorizonCode_Horizon_È(var20, this.¥à().HorizonCode_Horizon_È(HorizonCode_Horizon_È(var11.Âµá€()), true), 2);
                        }
                        else if (var19 && worldIn.Ø­áŒŠá(var21) && this.Ý(worldIn.Â(pos.HorizonCode_Horizon_È(var17)).Ý())) {
                            worldIn.HorizonCode_Horizon_È(var21, this.¥à().HorizonCode_Horizon_È(HorizonCode_Horizon_È(var11.Âµá€()), true), 2);
                        }
                        else if (this.Ý(worldIn.Â(var15.Ø­áŒŠá()).Ý())) {
                            worldIn.HorizonCode_Horizon_È(var15, this.¥à(), 2);
                        }
                    }
                    else if (var16.É.áˆºÑ¢Õ() && var16.áˆºÑ¢Õ()) {
                        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var11), true), 2);
                    }
                }
            }
            else if (pos.Â() > 1) {
                final BlockPos var15 = pos.Âµá€();
                final IBlockState var22 = worldIn.Â(var15);
                final Block var23 = var22.Ý();
                if (var23.É == Material.HorizonCode_Horizon_È) {
                    IBlockState var24 = state;
                    for (final EnumFacing var26 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                        if (rand.nextBoolean()) {
                            var24 = var24.HorizonCode_Horizon_È(HorizonCode_Horizon_È(var26), false);
                        }
                    }
                    if ((boolean)var24.HorizonCode_Horizon_È(BlockVine.à¢) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.ŠÂµà) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.¥à) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.Âµà)) {
                        worldIn.HorizonCode_Horizon_È(var15, var24, 2);
                    }
                }
                else if (var23 == this) {
                    IBlockState var24 = var22;
                    for (final EnumFacing var26 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                        final PropertyBool var27 = HorizonCode_Horizon_È(var26);
                        if (rand.nextBoolean() || !(boolean)state.HorizonCode_Horizon_È(var27)) {
                            var24 = var24.HorizonCode_Horizon_È(var27, false);
                        }
                    }
                    if ((boolean)var24.HorizonCode_Horizon_È(BlockVine.à¢) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.ŠÂµà) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.¥à) || (boolean)var24.HorizonCode_Horizon_È(BlockVine.Âµà)) {
                        worldIn.HorizonCode_Horizon_È(var15, var24, 2);
                    }
                }
            }
        }
    }
    
    private static int Â(final EnumFacing p_176270_0_) {
        return 1 << p_176270_0_.Ý();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final IBlockState var9 = this.¥à().HorizonCode_Horizon_È(BlockVine.Õ, false).HorizonCode_Horizon_È(BlockVine.à¢, false).HorizonCode_Horizon_È(BlockVine.ŠÂµà, false).HorizonCode_Horizon_È(BlockVine.¥à, false).HorizonCode_Horizon_È(BlockVine.Âµà, false);
        return facing.á().Ø­áŒŠá() ? var9.HorizonCode_Horizon_È(HorizonCode_Horizon_È(facing.Âµá€()), true) : var9;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Blocks.ÇŽà, 1, 0));
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockVine.à¢, (meta & BlockVine.áŠ) > 0).HorizonCode_Horizon_È(BlockVine.ŠÂµà, (meta & BlockVine.ˆáŠ) > 0).HorizonCode_Horizon_È(BlockVine.¥à, (meta & BlockVine.È) > 0).HorizonCode_Horizon_È(BlockVine.Âµà, (meta & BlockVine.áŒŠ) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        if (state.HorizonCode_Horizon_È(BlockVine.à¢)) {
            var2 |= BlockVine.áŠ;
        }
        if (state.HorizonCode_Horizon_È(BlockVine.ŠÂµà)) {
            var2 |= BlockVine.ˆáŠ;
        }
        if (state.HorizonCode_Horizon_È(BlockVine.¥à)) {
            var2 |= BlockVine.È;
        }
        if (state.HorizonCode_Horizon_È(BlockVine.Âµà)) {
            var2 |= BlockVine.áŒŠ;
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockVine.Õ, BlockVine.à¢, BlockVine.ŠÂµà, BlockVine.¥à, BlockVine.Âµà });
    }
    
    public static PropertyBool HorizonCode_Horizon_È(final EnumFacing p_176267_0_) {
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[p_176267_0_.ordinal()]) {
            case 1: {
                return BlockVine.Õ;
            }
            case 2: {
                return BlockVine.à¢;
            }
            case 3: {
                return BlockVine.¥à;
            }
            case 4: {
                return BlockVine.ŠÂµà;
            }
            case 5: {
                return BlockVine.Âµà;
            }
            default: {
                throw new IllegalArgumentException(p_176267_0_ + " is an invalid choice");
            }
        }
    }
    
    public static int áŒŠÆ(final IBlockState p_176268_0_) {
        int var1 = 0;
        for (final PropertyBool var5 : BlockVine.Ç) {
            if (p_176268_0_.HorizonCode_Horizon_È(var5)) {
                ++var1;
            }
        }
        return var1;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002049";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockVine.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockVine.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockVine.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockVine.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockVine.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
