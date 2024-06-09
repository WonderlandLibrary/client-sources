package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockSnow extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000309";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("layers", 1, 8);
    }
    
    protected BlockSnow() {
        super(Material.áŒŠà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSnow.Õ, 1));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.ŠÄ();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return (int)blockAccess.Â(pos).HorizonCode_Horizon_È(BlockSnow.Õ) < 5;
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        final int var4 = (int)state.HorizonCode_Horizon_È(BlockSnow.Õ) - 1;
        final float var5 = 0.125f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + this.ŠÄ, pos.Â() + this.Ñ¢á, pos.Ý() + this.ŒÏ, pos.HorizonCode_Horizon_È() + this.Çªà¢, pos.Â() + var4 * var5, pos.Ý() + this.ÇŽÉ);
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
    public void ŠÄ() {
        this.Âµá€(0);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final IBlockState var3 = access.Â(pos);
        this.Âµá€((int)var3.HorizonCode_Horizon_È(BlockSnow.Õ));
    }
    
    protected void Âµá€(final int p_150154_1_) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, p_150154_1_ / 8.0f, 1.0f);
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        final IBlockState var3 = worldIn.Â(pos.Âµá€());
        final Block var4 = var3.Ý();
        return var4 != Blocks.¥Ï && var4 != Blocks.ŠÂµÏ && (var4.Ó() == Material.áˆºÑ¢Õ || (var4 == this && (int)var3.HorizonCode_Horizon_È(BlockSnow.Õ) == 7) || (var4.Å() && var4.É.Ø­áŒŠá()));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    private boolean Âµá€(final World worldIn, final BlockPos p_176314_2_, final IBlockState p_176314_3_) {
        if (!this.Ø­áŒŠá(worldIn, p_176314_2_)) {
            this.HorizonCode_Horizon_È(worldIn, p_176314_2_, p_176314_3_, 0);
            worldIn.Ø(p_176314_2_);
            return false;
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Items.Ñ¢à, (int)state.HorizonCode_Horizon_È(BlockSnow.Õ) + 1, 0));
        worldIn.Ø(pos);
        playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.Ñ¢à;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (worldIn.Â(EnumSkyBlock.Â, pos) > 11) {
            this.HorizonCode_Horizon_È(worldIn, pos, worldIn.Â(pos), 0);
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side == EnumFacing.Â || super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSnow.Õ, (meta & 0x7) + 1);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        return (int)worldIn.Â(pos).HorizonCode_Horizon_È(BlockSnow.Õ) == 1;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockSnow.Õ) - 1;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSnow.Õ });
    }
}
