package net.minecraft.block;

import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockCocoa extends BlockDirectional implements IGrowable
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyInteger AGE;
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (world.rand.nextInt(0x58 ^ 0x5D) == 0) {
            final int intValue = blockState.getValue((IProperty<Integer>)BlockCocoa.AGE);
            if (intValue < "  ".length()) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCocoa.AGE, intValue + " ".length()), "  ".length());
            }
        }
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, EnumFacing north, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        if (!north.getAxis().isHorizontal()) {
            north = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCocoa.FACING, north.getOpposite()).withProperty((IProperty<Comparable>)BlockCocoa.AGE, "".length());
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockCocoa.I["".length()], "".length(), "  ".length());
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("%>&", "DYCXJ");
    }
    
    public BlockCocoa() {
        super(Material.plants);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockCocoa.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockCocoa.AGE, "".length()));
        this.setTickRandomly(" ".length() != 0);
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockCocoa.FACING, EnumFacing.getHorizontal(n)).withProperty((IProperty<Comparable>)BlockCocoa.AGE, (n & (0x6C ^ 0x63)) >> "  ".length());
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCocoa.FACING, EnumFacing.fromAngle(entityLivingBase.rotationYaw)), "  ".length());
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockCocoa.AGE, blockState.getValue((IProperty<Integer>)BlockCocoa.AGE) + " ".length()), "  ".length());
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!this.canBlockStay(world, blockPos, blockState)) {
            this.dropBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        if (blockState.getValue((IProperty<Integer>)BlockCocoa.AGE) < "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        final int intValue = blockState.getValue((IProperty<Integer>)BlockCocoa.AGE);
        int n3 = " ".length();
        if (intValue >= "  ".length()) {
            n3 = "   ".length();
        }
        int i = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (i < n3) {
            Block.spawnAsEntity(world, blockPos, new ItemStack(Items.dye, " ".length(), EnumDyeColor.BROWN.getDyeDamage()));
            ++i;
        }
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockCocoa.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x42 ^ 0x44);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x44 ^ 0x40);
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x47 ^ 0x42);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockCocoa.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockCocoa.FACING;
        array[" ".length()] = BlockCocoa.AGE;
        return new BlockState(this, array);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.dye;
    }
    
    public boolean canBlockStay(final World world, BlockPos offset, final IBlockState blockState) {
        offset = offset.offset(blockState.getValue((IProperty<EnumFacing>)BlockCocoa.FACING));
        final IBlockState blockState2 = world.getBlockState(offset);
        if (blockState2.getBlock() == Blocks.log && blockState2.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void dropBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, Blocks.air.getDefaultState(), "   ".length());
        this.dropBlockAsItem(world, blockPos, blockState, "".length());
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length() | blockState.getValue((IProperty<EnumFacing>)BlockCocoa.FACING).getHorizontalIndex() | blockState.getValue((IProperty<Integer>)BlockCocoa.AGE) << "  ".length();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockCocoa.FACING);
        final int intValue = blockState.getValue((IProperty<Integer>)BlockCocoa.AGE);
        final int n = (0x7E ^ 0x7A) + intValue * "  ".length();
        final int n2 = (0x83 ^ 0x86) + intValue * "  ".length();
        final float n3 = n / 2.0f;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 4: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, (15.0f - n) / 16.0f, (8.0f + n3) / 16.0f, 0.75f, 0.9375f);
                "".length();
                if (4 == 2) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds((8.0f - n3) / 16.0f, (12.0f - n2) / 16.0f, 0.0625f, (8.0f + n3) / 16.0f, 0.75f, (1.0f + n) / 16.0f);
                "".length();
                if (4 == 1) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(0.0625f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, (1.0f + n) / 16.0f, 0.75f, (8.0f + n3) / 16.0f);
                "".length();
                if (2 == 1) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.setBlockBounds((15.0f - n) / 16.0f, (12.0f - n2) / 16.0f, (8.0f - n3) / 16.0f, 0.9375f, 0.75f, (8.0f + n3) / 16.0f);
                break;
            }
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        return EnumDyeColor.BROWN.getDyeDamage();
    }
}
