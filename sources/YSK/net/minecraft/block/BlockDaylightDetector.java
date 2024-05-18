package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;

public class BlockDaylightDetector extends BlockContainer
{
    private static final String[] I;
    public static final PropertyInteger POWER;
    private final boolean inverted;
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        return blockState.getValue((IProperty<Integer>)BlockDaylightDetector.POWER);
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockDaylightDetector.POWER, n);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockDaylightDetector.POWER;
        return new BlockState(this, array);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        if (!this.inverted) {
            super.getSubBlocks(item, creativeTabs, list);
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    public void updatePower(final World world, final BlockPos blockPos) {
        if (!world.provider.getHasNoSky()) {
            final IBlockState blockState = world.getBlockState(blockPos);
            final int n = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
            final float celestialAngleRadians = world.getCelestialAngleRadians(1.0f);
            float n2;
            if (celestialAngleRadians < 3.1415927f) {
                n2 = 0.0f;
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                n2 = 6.2831855f;
            }
            int clamp_int = MathHelper.clamp_int(Math.round(n * MathHelper.cos(celestialAngleRadians + (n2 - celestialAngleRadians) * 0.2f)), "".length(), 0x5E ^ 0x51);
            if (this.inverted) {
                clamp_int = (0x32 ^ 0x3D) - clamp_int;
            }
            if (blockState.getValue((IProperty<Integer>)BlockDaylightDetector.POWER) != clamp_int) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockDaylightDetector.POWER, clamp_int), "   ".length());
            }
        }
    }
    
    public BlockDaylightDetector(final boolean inverted) {
        super(Material.wood);
        this.inverted = inverted;
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockDaylightDetector.POWER, "".length()));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setHardness(0.2f);
        this.setStepSound(BlockDaylightDetector.soundTypeWood);
        this.setUnlocalizedName(BlockDaylightDetector.I[" ".length()]);
    }
    
    static {
        I();
        POWER = PropertyInteger.create(BlockDaylightDetector.I["".length()], "".length(), 0x30 ^ 0x3F);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.isAllowEdit()) {
            return super.onBlockActivated(world, blockPos, blockState, entityPlayer, enumFacing, n, n2, n3);
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        if (this.inverted) {
            world.setBlockState(blockPos, Blocks.daylight_detector.getDefaultState().withProperty((IProperty<Comparable>)BlockDaylightDetector.POWER, (Integer)blockState.getValue((IProperty<V>)BlockDaylightDetector.POWER)), 0x83 ^ 0x87);
            Blocks.daylight_detector.updatePower(world, blockPos);
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            world.setBlockState(blockPos, Blocks.daylight_detector_inverted.getDefaultState().withProperty((IProperty<Comparable>)BlockDaylightDetector.POWER, (Integer)blockState.getValue((IProperty<V>)BlockDaylightDetector.POWER)), 0xA2 ^ 0xA6);
            Blocks.daylight_detector_inverted.updatePower(world, blockPos);
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.daylight_detector);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0006&\".\u0001", "vIUKs");
        BlockDaylightDetector.I[" ".length()] = I("\u0003 \u0013\u000e\n\u0000)\u001e&\u0006\u0013$\t\u0016\f\u0015", "gAjbc");
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockDaylightDetector.POWER);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
}
