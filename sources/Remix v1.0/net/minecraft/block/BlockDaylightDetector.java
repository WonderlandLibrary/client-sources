package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import java.util.*;

public class BlockDaylightDetector extends BlockContainer
{
    public static final PropertyInteger field_176436_a;
    private final boolean field_176435_b;
    
    public BlockDaylightDetector(final boolean p_i45729_1_) {
        super(Material.wood);
        this.field_176435_b = p_i45729_1_;
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDaylightDetector.field_176436_a, 0));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(0.2f);
        this.setStepSound(BlockDaylightDetector.soundTypeWood);
        this.setUnlocalizedName("daylightDetector");
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess access, final BlockPos pos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (int)state.getValue(BlockDaylightDetector.field_176436_a);
    }
    
    public void func_180677_d(final World worldIn, final BlockPos p_180677_2_) {
        if (!worldIn.provider.getHasNoSky()) {
            final IBlockState var3 = worldIn.getBlockState(p_180677_2_);
            int var4 = worldIn.getLightFor(EnumSkyBlock.SKY, p_180677_2_) - worldIn.getSkylightSubtracted();
            float var5 = worldIn.getCelestialAngleRadians(1.0f);
            final float var6 = (var5 < 3.1415927f) ? 0.0f : 6.2831855f;
            var5 += (var6 - var5) * 0.2f;
            var4 = Math.round(var4 * MathHelper.cos(var5));
            var4 = MathHelper.clamp_int(var4, 0, 15);
            if (this.field_176435_b) {
                var4 = 15 - var4;
            }
            if ((int)var3.getValue(BlockDaylightDetector.field_176436_a) != var4) {
                worldIn.setBlockState(p_180677_2_, var3.withProperty(BlockDaylightDetector.field_176436_a, var4), 3);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.func_175142_cm()) {
            return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
        }
        if (worldIn.isRemote) {
            return true;
        }
        if (this.field_176435_b) {
            worldIn.setBlockState(pos, Blocks.daylight_detector.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, state.getValue(BlockDaylightDetector.field_176436_a)), 4);
            Blocks.daylight_detector.func_180677_d(worldIn, pos);
        }
        else {
            worldIn.setBlockState(pos, Blocks.daylight_detector_inverted.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, state.getValue(BlockDaylightDetector.field_176436_a)), 4);
            Blocks.daylight_detector_inverted.func_180677_d(worldIn, pos);
        }
        return true;
    }
    
    @Override
    public Item getItemDropped(final IBlockState state, final Random rand, final int fortune) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public Item getItem(final World worldIn, final BlockPos pos) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    @Override
    public boolean isFullCube() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public int getRenderType() {
        return 3;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World worldIn, final int meta) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int meta) {
        return this.getDefaultState().withProperty(BlockDaylightDetector.field_176436_a, meta);
    }
    
    @Override
    public int getMetaFromState(final IBlockState state) {
        return (int)state.getValue(BlockDaylightDetector.field_176436_a);
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockDaylightDetector.field_176436_a });
    }
    
    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs tab, final List list) {
        if (!this.field_176435_b) {
            super.getSubBlocks(itemIn, tab, list);
        }
    }
    
    static {
        field_176436_a = PropertyInteger.create("power", 0, 15);
    }
}
