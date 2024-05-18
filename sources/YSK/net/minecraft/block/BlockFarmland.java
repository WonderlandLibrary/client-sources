package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockFarmland extends Block
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyInteger MOISTURE;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFarmland.MOISTURE, n & (0x6 ^ 0x1));
    }
    
    private boolean hasCrops(final World world, final BlockPos blockPos) {
        final Block block = world.getBlockState(blockPos.up()).getBlock();
        if (!(block instanceof BlockCrops) && !(block instanceof BlockStem)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final int intValue = blockState.getValue((IProperty<Integer>)BlockFarmland.MOISTURE);
        if (!this.hasWater(world, blockPos) && !world.canLightningStrike(blockPos.up())) {
            if (intValue > 0) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFarmland.MOISTURE, intValue - " ".length()), "  ".length());
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else if (!this.hasCrops(world, blockPos)) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
        }
        else if (intValue < (0x24 ^ 0x23)) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockFarmland.MOISTURE, 0x38 ^ 0x3F), "  ".length());
        }
    }
    
    @Override
    public void onFallenUpon(final World world, final BlockPos blockPos, final Entity entity, final float n) {
        if (entity instanceof EntityLivingBase) {
            if (!world.isRemote && world.rand.nextFloat() < n - 0.5f) {
                if (!(entity instanceof EntityPlayer) && !world.getGameRules().getBoolean(BlockFarmland.I[" ".length()])) {
                    return;
                }
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
            }
            super.onFallenUpon(world, blockPos, entity, n);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockFarmland.MOISTURE);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0019\r=\u0004'\u0001\u00101", "tbTwS");
        BlockFarmland.I[" ".length()] = I("\u0006\u0005)\f\u0006\u0002\u000f-\"\u001a\f", "kjKKt");
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockFarmland.MOISTURE;
        return new BlockState(this, array);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        super.onNeighborBlockChange(world, blockPos, blockState, block);
        if (world.getBlockState(blockPos.up()).getBlock().getMaterial().isSolid()) {
            world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
        }
    }
    
    static {
        I();
        MOISTURE = PropertyInteger.create(BlockFarmland.I["".length()], "".length(), 0xA3 ^ 0xA4);
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
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + " ".length(), blockPos.getY() + " ".length(), blockPos.getZ() + " ".length());
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    protected BlockFarmland() {
        super(Material.ground);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockFarmland.MOISTURE, "".length()));
        this.setTickRandomly(" ".length() != 0);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(5 + 116 + 20 + 114);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.dirt);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 2: {
                return " ".length() != 0;
            }
            case 3:
            case 4:
            case 5:
            case 6: {
                final Block block = blockAccess.getBlockState(blockPos).getBlock();
                if (!block.isOpaqueCube() && block != Blocks.farmland) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            default: {
                return super.shouldSideBeRendered(blockAccess, blockPos, enumFacing);
            }
        }
    }
    
    private boolean hasWater(final World world, final BlockPos blockPos) {
        final Iterator<BlockPos.MutableBlockPos> iterator = BlockPos.getAllInBoxMutable(blockPos.add(-(0x82 ^ 0x86), "".length(), -(0x5 ^ 0x1)), blockPos.add(0x33 ^ 0x37, " ".length(), 0x93 ^ 0x97)).iterator();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            if (world.getBlockState(iterator.next()).getBlock().getMaterial() == Material.water) {
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockFarmland.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x54 ^ 0x52);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x22 ^ 0x26);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x10 ^ 0x15);
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockFarmland.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
}
