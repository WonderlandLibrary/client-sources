package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockDaylightDetector extends BlockContainer
{
    public static final PropertyInteger Õ;
    private final boolean à¢;
    private static final String ŠÂµà = "CL_00000223";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("power", 0, 15);
    }
    
    public BlockDaylightDetector(final boolean p_i45729_1_) {
        super(Material.Ø­áŒŠá);
        this.à¢ = p_i45729_1_;
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockDaylightDetector.Õ, 0));
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
        this.Ý(0.2f);
        this.HorizonCode_Horizon_È(BlockDaylightDetector.Ø­áŒŠá);
        this.Â("daylightDetector");
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (int)state.HorizonCode_Horizon_È(BlockDaylightDetector.Õ);
    }
    
    public void áŒŠÆ(final World worldIn, final BlockPos p_180677_2_) {
        if (!worldIn.£à.Å()) {
            final IBlockState var3 = worldIn.Â(p_180677_2_);
            int var4 = worldIn.Â(EnumSkyBlock.HorizonCode_Horizon_È, p_180677_2_) - worldIn.¥à();
            float var5 = worldIn.Ø­áŒŠá(1.0f);
            final float var6 = (var5 < 3.1415927f) ? 0.0f : 6.2831855f;
            var5 += (var6 - var5) * 0.2f;
            var4 = Math.round(var4 * MathHelper.Â(var5));
            var4 = MathHelper.HorizonCode_Horizon_È(var4, 0, 15);
            if (this.à¢) {
                var4 = 15 - var4;
            }
            if ((int)var3.HorizonCode_Horizon_È(BlockDaylightDetector.Õ) != var4) {
                worldIn.HorizonCode_Horizon_È(p_180677_2_, var3.HorizonCode_Horizon_È(BlockDaylightDetector.Õ, var4), 3);
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.ŠáˆºÂ()) {
            return super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        if (this.à¢) {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.ÐƒÓ.¥à().HorizonCode_Horizon_È(BlockDaylightDetector.Õ, state.HorizonCode_Horizon_È(BlockDaylightDetector.Õ)), 4);
            Blocks.ÐƒÓ.áŒŠÆ(worldIn, pos);
        }
        else {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.áˆºÕ.¥à().HorizonCode_Horizon_È(BlockDaylightDetector.Õ, state.HorizonCode_Horizon_È(BlockDaylightDetector.Õ)), 4);
            Blocks.áˆºÕ.áŒŠÆ(worldIn, pos);
        }
        return true;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÐƒÓ);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÐƒÓ);
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
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockDaylightDetector.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockDaylightDetector.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockDaylightDetector.Õ });
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        if (!this.à¢) {
            super.HorizonCode_Horizon_È(itemIn, tab, list);
        }
    }
}
