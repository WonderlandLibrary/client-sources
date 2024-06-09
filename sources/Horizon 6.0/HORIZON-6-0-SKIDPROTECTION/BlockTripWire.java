package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    public static final PropertyBool Âµà;
    public static final PropertyBool Ç;
    public static final PropertyBool È;
    public static final PropertyBool áŠ;
    private static final String ˆáŠ = "CL_00000328";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("powered");
        à¢ = PropertyBool.HorizonCode_Horizon_È("suspended");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("attached");
        ¥à = PropertyBool.HorizonCode_Horizon_È("disarmed");
        Âµà = PropertyBool.HorizonCode_Horizon_È("north");
        Ç = PropertyBool.HorizonCode_Horizon_È("east");
        È = PropertyBool.HorizonCode_Horizon_È("south");
        áŠ = PropertyBool.HorizonCode_Horizon_È("west");
    }
    
    public BlockTripWire() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTripWire.Õ, false).HorizonCode_Horizon_È(BlockTripWire.à¢, false).HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, false).HorizonCode_Horizon_È(BlockTripWire.¥à, false).HorizonCode_Horizon_È(BlockTripWire.Âµà, false).HorizonCode_Horizon_È(BlockTripWire.Ç, false).HorizonCode_Horizon_È(BlockTripWire.È, false).HorizonCode_Horizon_È(BlockTripWire.áŠ, false));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockTripWire.Âµà, Ý(worldIn, pos, state, EnumFacing.Ý)).HorizonCode_Horizon_È(BlockTripWire.Ç, Ý(worldIn, pos, state, EnumFacing.Ó)).HorizonCode_Horizon_È(BlockTripWire.È, Ý(worldIn, pos, state, EnumFacing.Ø­áŒŠá)).HorizonCode_Horizon_È(BlockTripWire.áŠ, Ý(worldIn, pos, state, EnumFacing.Âµá€));
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
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
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ˆá;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ˆá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final boolean var5 = (boolean)state.HorizonCode_Horizon_È(BlockTripWire.à¢);
        final boolean var6 = !World.HorizonCode_Horizon_È(worldIn, pos.Âµá€());
        if (var5 != var6) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final IBlockState var3 = access.Â(pos);
        final boolean var4 = (boolean)var3.HorizonCode_Horizon_È(BlockTripWire.ŠÂµà);
        final boolean var5 = (boolean)var3.HorizonCode_Horizon_È(BlockTripWire.à¢);
        if (!var5) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!var4) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, IBlockState state) {
        state = state.HorizonCode_Horizon_È(BlockTripWire.à¢, !World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()));
        worldIn.HorizonCode_Horizon_È(pos, state, 3);
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state.HorizonCode_Horizon_È(BlockTripWire.Õ, true));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockTripWire.¥à, true), 4);
        }
    }
    
    private void Âµá€(final World worldIn, final BlockPos p_176286_2_, final IBlockState p_176286_3_) {
        for (final EnumFacing var7 : new EnumFacing[] { EnumFacing.Ø­áŒŠá, EnumFacing.Âµá€ }) {
            int var8 = 1;
            while (var8 < 42) {
                final BlockPos var9 = p_176286_2_.HorizonCode_Horizon_È(var7, var8);
                final IBlockState var10 = worldIn.Â(var9);
                if (var10.Ý() == Blocks.ˆÂ) {
                    if (var10.HorizonCode_Horizon_È(BlockTripWireHook.Õ) == var7.Âµá€()) {
                        Blocks.ˆÂ.HorizonCode_Horizon_È(worldIn, var9, var10, false, true, var8, p_176286_3_);
                        break;
                    }
                    break;
                }
                else {
                    if (var10.Ý() != Blocks.áŒŠÈ) {
                        break;
                    }
                    ++var8;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Entity entityIn) {
        if (!worldIn.ŠÄ && !(boolean)state.HorizonCode_Horizon_È(BlockTripWire.Õ)) {
            this.áŒŠÆ(worldIn, pos);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && (boolean)worldIn.Â(pos).HorizonCode_Horizon_È(BlockTripWire.Õ)) {
            this.áŒŠÆ(worldIn, pos);
        }
    }
    
    private void áŒŠÆ(final World worldIn, final BlockPos p_176288_2_) {
        IBlockState var3 = worldIn.Â(p_176288_2_);
        final boolean var4 = (boolean)var3.HorizonCode_Horizon_È(BlockTripWire.Õ);
        boolean var5 = false;
        final List var6 = worldIn.Â(null, new AxisAlignedBB(p_176288_2_.HorizonCode_Horizon_È() + this.ŠÄ, p_176288_2_.Â() + this.Ñ¢á, p_176288_2_.Ý() + this.ŒÏ, p_176288_2_.HorizonCode_Horizon_È() + this.Çªà¢, p_176288_2_.Â() + this.Ê, p_176288_2_.Ý() + this.ÇŽÉ));
        if (!var6.isEmpty()) {
            for (final Entity var8 : var6) {
                if (!var8.Ñ¢à()) {
                    var5 = true;
                    break;
                }
            }
        }
        if (var5 != var4) {
            var3 = var3.HorizonCode_Horizon_È(BlockTripWire.Õ, var5);
            worldIn.HorizonCode_Horizon_È(p_176288_2_, var3, 3);
            this.Âµá€(worldIn, p_176288_2_, var3);
        }
        if (var5) {
            worldIn.HorizonCode_Horizon_È(p_176288_2_, this, this.HorizonCode_Horizon_È(worldIn));
        }
    }
    
    public static boolean Ý(final IBlockAccess p_176287_0_, final BlockPos p_176287_1_, final IBlockState p_176287_2_, final EnumFacing p_176287_3_) {
        final BlockPos var4 = p_176287_1_.HorizonCode_Horizon_È(p_176287_3_);
        final IBlockState var5 = p_176287_0_.Â(var4);
        final Block var6 = var5.Ý();
        if (var6 == Blocks.ˆÂ) {
            final EnumFacing var7 = p_176287_3_.Âµá€();
            return var5.HorizonCode_Horizon_È(BlockTripWireHook.Õ) == var7;
        }
        if (var6 == Blocks.áŒŠÈ) {
            final boolean var8 = (boolean)p_176287_2_.HorizonCode_Horizon_È(BlockTripWire.à¢);
            final boolean var9 = (boolean)var5.HorizonCode_Horizon_È(BlockTripWire.à¢);
            return var8 == var9;
        }
        return false;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockTripWire.Õ, (meta & 0x1) > 0).HorizonCode_Horizon_È(BlockTripWire.à¢, (meta & 0x2) > 0).HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, (meta & 0x4) > 0).HorizonCode_Horizon_È(BlockTripWire.¥à, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        if (state.HorizonCode_Horizon_È(BlockTripWire.Õ)) {
            var2 |= 0x1;
        }
        if (state.HorizonCode_Horizon_È(BlockTripWire.à¢)) {
            var2 |= 0x2;
        }
        if (state.HorizonCode_Horizon_È(BlockTripWire.ŠÂµà)) {
            var2 |= 0x4;
        }
        if (state.HorizonCode_Horizon_È(BlockTripWire.¥à)) {
            var2 |= 0x8;
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTripWire.Õ, BlockTripWire.à¢, BlockTripWire.ŠÂµà, BlockTripWire.¥à, BlockTripWire.Âµà, BlockTripWire.Ç, BlockTripWire.áŠ, BlockTripWire.È });
    }
}
