package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockCake extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000211";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("bites", 0, 6);
    }
    
    protected BlockCake() {
        super(Material.ˆá);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockCake.Õ, 0));
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final float var3 = 0.0625f;
        final float var4 = (1 + (int)access.Â(pos).HorizonCode_Horizon_È(BlockCake.Õ) * 2) / 16.0f;
        final float var5 = 0.5f;
        this.HorizonCode_Horizon_È(var4, 0.0f, var3, 1.0f - var3, var5, 1.0f - var3);
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.0625f;
        final float var2 = 0.5f;
        this.HorizonCode_Horizon_È(var1, 0.0f, var1, 1.0f - var1, var2, 1.0f - var1);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        final float var4 = 0.0625f;
        final float var5 = (1 + (int)state.HorizonCode_Horizon_È(BlockCake.Õ) * 2) / 16.0f;
        final float var6 = 0.5f;
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È() + var5, pos.Â(), pos.Ý() + var4, pos.HorizonCode_Horizon_È() + 1 - var4, pos.Â() + var6, pos.Ý() + 1 - var4);
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        return this.HorizonCode_Horizon_È(worldIn, pos, worldIn.Â(pos));
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        this.Â(worldIn, pos, state, playerIn);
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EntityPlayer playerIn) {
        this.Â(worldIn, pos, worldIn.Â(pos), playerIn);
    }
    
    private void Â(final World worldIn, final BlockPos p_180682_2_, final IBlockState p_180682_3_, final EntityPlayer p_180682_4_) {
        if (p_180682_4_.Ý(false)) {
            p_180682_4_.ŠÏ­áˆºá().HorizonCode_Horizon_È(2, 0.1f);
            final int var5 = (int)p_180682_3_.HorizonCode_Horizon_È(BlockCake.Õ);
            if (var5 < 6) {
                worldIn.HorizonCode_Horizon_È(p_180682_2_, p_180682_3_.HorizonCode_Horizon_È(BlockCake.Õ, var5 + 1), 3);
            }
            else {
                worldIn.Ø(p_180682_2_);
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return super.Ø­áŒŠá(worldIn, pos) && this.áŒŠÆ(worldIn, pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.áŒŠÆ(worldIn, pos)) {
            worldIn.Ø(pos);
        }
    }
    
    private boolean áŒŠÆ(final World worldIn, final BlockPos p_176588_2_) {
        return worldIn.Â(p_176588_2_.Âµá€()).Ý().Ó().Â();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.µÐƒáƒ;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockCake.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockCake.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockCake.Õ });
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return (7 - (int)worldIn.Â(pos).HorizonCode_Horizon_È(BlockCake.Õ)) * 2;
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
}
