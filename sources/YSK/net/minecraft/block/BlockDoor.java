package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import com.google.common.base.*;
import net.minecraft.util.*;

public class BlockDoor extends Block
{
    public static final PropertyEnum<EnumDoorHalf> HALF;
    public static final PropertyDirection FACING;
    public static final PropertyEnum<EnumHingePosition> HINGE;
    private static final String[] I;
    public static final PropertyBool OPEN;
    public static final PropertyBool POWERED;
    
    protected static boolean isTop(final int n) {
        if ((n & (0x8B ^ 0x83)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public MovingObjectPosition collisionRayTrace(final World world, final BlockPos blockPos, final Vec3 vec3, final Vec3 vec4) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.collisionRayTrace(world, blockPos, vec3, vec4);
    }
    
    public static EnumFacing getFacing(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return getFacing(combineMetadata(blockAccess, blockPos));
    }
    
    private Item getItem() {
        Item item;
        if (this == Blocks.iron_door) {
            item = Items.iron_door;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this == Blocks.spruce_door) {
            item = Items.spruce_door;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this == Blocks.birch_door) {
            item = Items.birch_door;
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else if (this == Blocks.jungle_door) {
            item = Items.jungle_door;
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else if (this == Blocks.acacia_door) {
            item = Items.acacia_door;
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else if (this == Blocks.dark_oak_door) {
            item = Items.dark_oak_door;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            item = Items.oak_door;
        }
        return item;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        Item item;
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
            item = null;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            item = this.getItem();
        }
        return item;
    }
    
    private static void I() {
        (I = new String[0x8D ^ 0x85])["".length()] = I("\u000b8\u0010=\u0000\n", "mYsTn");
        BlockDoor.I[" ".length()] = I("\u00054\n\u0018", "jDovL");
        BlockDoor.I["  ".length()] = I("<\"\u001d/&", "TKsHC");
        BlockDoor.I["   ".length()] = I("$\u0000?\u001011\u000b", "ToHuC");
        BlockDoor.I[0x1E ^ 0x1A] = I("\u001f\u0014\u000e?", "wubYc");
        BlockDoor.I[0x67 ^ 0x62] = I("Z\u0019\u0007+\u0001", "twfFd");
        BlockDoor.I[0x40 ^ 0x46] = I("\u0013\u0013\u0007\"", "gzkGp");
        BlockDoor.I[0x93 ^ 0x94] = I("9\u00136(", "PgSEp");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    protected static int removeHalfBit(final int n) {
        return n & (0x89 ^ 0x8E);
    }
    
    protected static boolean isOpen(final int n) {
        if ((n & (0x3B ^ 0x3F)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        int n;
        if (blockPos.getY() >= 59 + 34 - 74 + 236) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && super.canPlaceBlockAt(world, blockPos) && super.canPlaceBlockAt(world, blockPos.up())) {
            n = " ".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public static EnumFacing getFacing(final int n) {
        return EnumFacing.getHorizontal(n & "   ".length()).rotateYCCW();
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBox(final World world, final BlockPos blockPos) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getSelectedBoundingBox(world, blockPos);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x82 ^ 0x87];
        array["".length()] = BlockDoor.HALF;
        array[" ".length()] = BlockDoor.FACING;
        array["  ".length()] = BlockDoor.OPEN;
        array["   ".length()] = BlockDoor.HINGE;
        array[0x2B ^ 0x2F] = BlockDoor.POWERED;
        return new BlockState(this, array);
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        final BlockPos down = blockPos.down();
        if (entityPlayer.capabilities.isCreativeMode && blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER && world.getBlockState(down).getBlock() == this) {
            world.setBlockToAir(down);
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int length = "".length();
        int n;
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
            n = (length | (0x73 ^ 0x7B));
            if (blockState.getValue(BlockDoor.HINGE) == EnumHingePosition.RIGHT) {
                n |= " ".length();
            }
            if (blockState.getValue((IProperty<Boolean>)BlockDoor.POWERED)) {
                n |= "  ".length();
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
        }
        else {
            n = (length | blockState.getValue((IProperty<EnumFacing>)BlockDoor.FACING).rotateY().getHorizontalIndex());
            if (blockState.getValue((IProperty<Boolean>)BlockDoor.OPEN)) {
                n |= (0xA ^ 0xE);
            }
        }
        return n;
    }
    
    public static int combineMetadata(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
        final boolean top = isTop(metaFromState);
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.down());
        final int metaFromState2 = blockState2.getBlock().getMetaFromState(blockState2);
        int n;
        if (top) {
            n = metaFromState2;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = metaFromState;
        }
        final int n2 = n;
        final IBlockState blockState3 = blockAccess.getBlockState(blockPos.up());
        final int metaFromState3 = blockState3.getBlock().getMetaFromState(blockState3);
        int n3;
        if (top) {
            n3 = metaFromState;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n3 = metaFromState3;
        }
        final int n4 = n3;
        int n5;
        if ((n4 & " ".length()) != 0x0) {
            n5 = " ".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        final int n6 = n5;
        int n7;
        if ((n4 & "  ".length()) != 0x0) {
            n7 = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            n7 = "".length();
        }
        final int n8 = n7;
        final int removeHalfBit = removeHalfBit(n2);
        int length;
        if (top) {
            length = (0x90 ^ 0x98);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        final int n9 = removeHalfBit | length;
        int length2;
        if (n6 != 0) {
            length2 = (0x2A ^ 0x3A);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            length2 = "".length();
        }
        final int n10 = n9 | length2;
        int length3;
        if (n8 != 0) {
            length3 = (0x41 ^ 0x61);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            length3 = "".length();
        }
        return n10 | length3;
    }
    
    private void setBoundBasedOnMeta(final int n) {
        final float n2 = 0.1875f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f);
        final EnumFacing facing = getFacing(n);
        final boolean open = isOpen(n);
        final boolean hingeLeft = isHingeLeft(n);
        if (open) {
            if (facing == EnumFacing.EAST) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                    "".length();
                    if (false == true) {
                        throw null;
                    }
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
            }
            else if (facing == EnumFacing.SOUTH) {
                if (!hingeLeft) {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
            }
            else if (facing == EnumFacing.WEST) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
            }
            else if (facing == EnumFacing.NORTH) {
                if (!hingeLeft) {
                    this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
                else {
                    this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    "".length();
                    if (2 < 0) {
                        throw null;
                    }
                }
            }
        }
        else if (facing == EnumFacing.EAST) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, n2, 1.0f, 1.0f);
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (facing == EnumFacing.SOUTH) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, n2);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (facing == EnumFacing.WEST) {
            this.setBlockBounds(1.0f - n2, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else if (facing == EnumFacing.NORTH) {
            this.setBlockBounds(0.0f, 0.0f, 1.0f - n2, 1.0f, 1.0f, 1.0f);
        }
    }
    
    protected BlockDoor(final Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockDoor.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockDoor.OPEN, "".length() != 0).withProperty(BlockDoor.HINGE, EnumHingePosition.LEFT).withProperty((IProperty<Comparable>)BlockDoor.POWERED, "".length() != 0).withProperty(BlockDoor.HALF, EnumDoorHalf.LOWER));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBoundBasedOnMeta(combineMetadata(blockAccess, blockPos));
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal((String.valueOf(this.getUnlocalizedName()) + BlockDoor.I[0x12 ^ 0x17]).replaceAll(BlockDoor.I[0x41 ^ 0x47], BlockDoor.I[0x3A ^ 0x3D]));
    }
    
    @Override
    public int getMobilityFlag() {
        return " ".length();
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return this.getItem();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockDoor.I["".length()], (Predicate<EnumFacing>)EnumFacing.Plane.HORIZONTAL);
        OPEN = PropertyBool.create(BlockDoor.I[" ".length()]);
        HINGE = PropertyEnum.create(BlockDoor.I["  ".length()], EnumHingePosition.class);
        POWERED = PropertyBool.create(BlockDoor.I["   ".length()]);
        HALF = PropertyEnum.create(BlockDoor.I[0x4E ^ 0x4A], EnumDoorHalf.class);
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.UPPER) {
            final BlockPos down = blockToAir.down();
            final IBlockState blockState2 = world.getBlockState(down);
            if (blockState2.getBlock() != this) {
                world.setBlockToAir(blockToAir);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else if (block != this) {
                this.onNeighborBlockChange(world, down, blockState2, block);
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
        }
        else {
            int n = "".length();
            final BlockPos up = blockToAir.up();
            final IBlockState blockState3 = world.getBlockState(up);
            if (blockState3.getBlock() != this) {
                world.setBlockToAir(blockToAir);
                n = " ".length();
            }
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
                world.setBlockToAir(blockToAir);
                n = " ".length();
                if (blockState3.getBlock() == this) {
                    world.setBlockToAir(up);
                }
            }
            if (n != 0) {
                if (!world.isRemote) {
                    this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                    "".length();
                    if (4 == 3) {
                        throw null;
                    }
                }
            }
            else {
                int n2;
                if (!world.isBlockPowered(blockToAir) && !world.isBlockPowered(up)) {
                    n2 = "".length();
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                else {
                    n2 = " ".length();
                }
                final int n3 = n2;
                if ((n3 || block.canProvidePower()) && block != this && n3 != (((boolean)blockState3.getValue((IProperty<Boolean>)BlockDoor.POWERED)) ? 1 : 0)) {
                    world.setBlockState(up, blockState3.withProperty((IProperty<Comparable>)BlockDoor.POWERED, (boolean)(n3 != 0)), "  ".length());
                    if (n3 != (((boolean)blockState.getValue((IProperty<Boolean>)BlockDoor.OPEN)) ? 1 : 0)) {
                        world.setBlockState(blockToAir, blockState.withProperty((IProperty<Comparable>)BlockDoor.OPEN, (boolean)(n3 != 0)), "  ".length());
                        world.markBlockRangeForRenderUpdate(blockToAir, blockToAir);
                        final EntityPlayer entityPlayer = null;
                        int n4;
                        if (n3 != 0) {
                            n4 = 488 + 45 + 255 + 215;
                            "".length();
                            if (3 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n4 = 803 + 136 - 467 + 534;
                        }
                        world.playAuxSFXAtEntity(entityPlayer, n4, blockToAir, "".length());
                    }
                }
            }
        }
    }
    
    public void toggleDoor(final World world, final BlockPos blockPos, final boolean b) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this) {
            BlockPos down;
            if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
                down = blockPos;
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                down = blockPos.down();
            }
            final BlockPos blockPos2 = down;
            IBlockState blockState2;
            if (blockPos == blockPos2) {
                blockState2 = blockState;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                blockState2 = world.getBlockState(blockPos2);
            }
            final IBlockState blockState3 = blockState2;
            if (blockState3.getBlock() == this && blockState3.getValue((IProperty<Boolean>)BlockDoor.OPEN) != b) {
                world.setBlockState(blockPos2, blockState3.withProperty((IProperty<Comparable>)BlockDoor.OPEN, b), "  ".length());
                world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
                final EntityPlayer entityPlayer = null;
                int n;
                if (b) {
                    n = 418 + 525 - 866 + 926;
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                }
                else {
                    n = 687 + 791 - 602 + 130;
                }
                world.playAuxSFXAtEntity(entityPlayer, n, blockPos, "".length());
            }
        }
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (blockState.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
            final IBlockState blockState2 = blockAccess.getBlockState(blockPos.up());
            if (blockState2.getBlock() == this) {
                blockState = blockState.withProperty(BlockDoor.HINGE, (EnumHingePosition)blockState2.getValue((IProperty<V>)BlockDoor.HINGE)).withProperty((IProperty<Comparable>)BlockDoor.POWERED, (Boolean)blockState2.getValue((IProperty<V>)BlockDoor.POWERED));
                "".length();
                if (4 < 2) {
                    throw null;
                }
            }
        }
        else {
            final IBlockState blockState3 = blockAccess.getBlockState(blockPos.down());
            if (blockState3.getBlock() == this) {
                blockState = blockState.withProperty((IProperty<Comparable>)BlockDoor.FACING, (EnumFacing)blockState3.getValue((IProperty<V>)BlockDoor.FACING)).withProperty((IProperty<Comparable>)BlockDoor.OPEN, (Boolean)blockState3.getValue((IProperty<V>)BlockDoor.OPEN));
            }
        }
        return blockState;
    }
    
    @Override
    public boolean isPassable(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return isOpen(combineMetadata(blockAccess, blockPos));
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
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
            if (4 <= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState;
        if ((n & (0x20 ^ 0x28)) > 0) {
            final IBlockState withProperty = this.getDefaultState().withProperty(BlockDoor.HALF, EnumDoorHalf.UPPER);
            final PropertyEnum<EnumHingePosition> hinge = BlockDoor.HINGE;
            EnumHingePosition enumHingePosition;
            if ((n & " ".length()) > 0) {
                enumHingePosition = EnumHingePosition.RIGHT;
                "".length();
                if (4 == 0) {
                    throw null;
                }
            }
            else {
                enumHingePosition = EnumHingePosition.LEFT;
            }
            final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)hinge, enumHingePosition);
            final PropertyBool powered = BlockDoor.POWERED;
            int n2;
            if ((n & "  ".length()) > 0) {
                n2 = " ".length();
                "".length();
                if (1 == -1) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            blockState = withProperty2.withProperty((IProperty<Comparable>)powered, n2 != 0);
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            final IBlockState withProperty3 = this.getDefaultState().withProperty(BlockDoor.HALF, EnumDoorHalf.LOWER).withProperty((IProperty<Comparable>)BlockDoor.FACING, EnumFacing.getHorizontal(n & "   ".length()).rotateYCCW());
            final PropertyBool open = BlockDoor.OPEN;
            int n3;
            if ((n & (0x6F ^ 0x6B)) > 0) {
                n3 = " ".length();
                "".length();
                if (4 == 3) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            blockState = withProperty3.withProperty((IProperty<Comparable>)open, n3 != 0);
        }
        return blockState;
    }
    
    public static boolean isOpen(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return isOpen(combineMetadata(blockAccess, blockPos));
    }
    
    protected static boolean isHingeLeft(final int n) {
        if ((n & (0x20 ^ 0x30)) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (this.blockMaterial == Material.iron) {
            return " ".length() != 0;
        }
        BlockPos down;
        if (cycleProperty.getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
            down = blockPos;
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            down = blockPos.down();
        }
        final BlockPos blockPos2 = down;
        IBlockState blockState;
        if (blockPos.equals(blockPos2)) {
            blockState = cycleProperty;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            blockState = world.getBlockState(blockPos2);
        }
        final IBlockState blockState2 = blockState;
        if (blockState2.getBlock() != this) {
            return "".length() != 0;
        }
        cycleProperty = blockState2.cycleProperty((IProperty<Comparable>)BlockDoor.OPEN);
        world.setBlockState(blockPos2, cycleProperty, "  ".length());
        world.markBlockRangeForRenderUpdate(blockPos2, blockPos);
        int n4;
        if (cycleProperty.getValue((IProperty<Boolean>)BlockDoor.OPEN)) {
            n4 = 712 + 400 - 595 + 486;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else {
            n4 = 656 + 151 - 26 + 225;
        }
        world.playAuxSFXAtEntity(entityPlayer, n4, blockPos, "".length());
        return " ".length() != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    public enum EnumDoorHalf implements IStringSerializable
    {
        LOWER(EnumDoorHalf.I[" ".length()], " ".length()), 
        UPPER(EnumDoorHalf.I["".length()], "".length());
        
        private static final String[] I;
        private static final EnumDoorHalf[] ENUM$VALUES;
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        @Override
        public String getName() {
            String s;
            if (this == EnumDoorHalf.UPPER) {
                s = EnumDoorHalf.I["  ".length()];
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                s = EnumDoorHalf.I["   ".length()];
            }
            return s;
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0xA0 ^ 0xA4])["".length()] = I("8\"*\t7", "mrzLe");
            EnumDoorHalf.I[" ".length()] = I("\u0002:/\u001d\u0013", "NuxXA");
            EnumDoorHalf.I["  ".length()] = I("=)\u001c\u0000\u001b", "HYlei");
            EnumDoorHalf.I["   ".length()] = I("\b\u0001\u001e5!", "dniPS");
        }
        
        static {
            I();
            final EnumDoorHalf[] enum$VALUES = new EnumDoorHalf["  ".length()];
            enum$VALUES["".length()] = EnumDoorHalf.UPPER;
            enum$VALUES[" ".length()] = EnumDoorHalf.LOWER;
            ENUM$VALUES = enum$VALUES;
        }
        
        private EnumDoorHalf(final String s, final int n) {
        }
    }
    
    public enum EnumHingePosition implements IStringSerializable
    {
        private static final String[] I;
        private static final EnumHingePosition[] ENUM$VALUES;
        
        LEFT(EnumHingePosition.I["".length()], "".length()), 
        RIGHT(EnumHingePosition.I[" ".length()], " ".length());
        
        @Override
        public String getName() {
            String s;
            if (this == EnumHingePosition.LEFT) {
                s = EnumHingePosition.I["  ".length()];
                "".length();
                if (3 < 3) {
                    throw null;
                }
            }
            else {
                s = EnumHingePosition.I["   ".length()];
            }
            return s;
        }
        
        private EnumHingePosition(final String s, final int n) {
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        static {
            I();
            final EnumHingePosition[] enum$VALUES = new EnumHingePosition["  ".length()];
            enum$VALUES["".length()] = EnumHingePosition.LEFT;
            enum$VALUES[" ".length()] = EnumHingePosition.RIGHT;
            ENUM$VALUES = enum$VALUES;
        }
        
        private static void I() {
            (I = new String[0x14 ^ 0x10])["".length()] = I("\u001c\u0017\u0002\u001f", "PRDKq");
            EnumHingePosition.I[" ".length()] = I("\u00001\u0017\t9", "RxPAm");
            EnumHingePosition.I["  ".length()] = I("\u0001\u001c,?", "myJKi");
            EnumHingePosition.I["   ".length()] = I("\u0000\u0001=\u000b1", "rhZcE");
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
                if (2 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
