package net.minecraft.block;

import net.minecraft.item.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class BlockTripWire extends Block
{
    public static final PropertyBool SOUTH;
    public static final PropertyBool POWERED;
    public static final PropertyBool EAST;
    public static final PropertyBool NORTH;
    public static final PropertyBool DISARMED;
    public static final PropertyBool WEST;
    public static final PropertyBool ATTACHED;
    public static final PropertyBool SUSPENDED;
    private static final String[] I;
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.string;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        if (blockState.getValue((IProperty<Boolean>)BlockTripWire.POWERED)) {
            length |= " ".length();
        }
        if (blockState.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED)) {
            length |= "  ".length();
        }
        if (blockState.getValue((IProperty<Boolean>)BlockTripWire.ATTACHED)) {
            length |= (0x71 ^ 0x75);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockTripWire.DISARMED)) {
            length |= (0xA ^ 0x2);
        }
        return length;
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED);
        int n;
        if (World.doesBlockHaveSolidTopSurface(world, blockToAir.down())) {
            n = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        if ((booleanValue ? 1 : 0) != n) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool powered = BlockTripWire.POWERED;
        int n2;
        if ((n & " ".length()) > 0) {
            n2 = " ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty = defaultState.withProperty((IProperty<Comparable>)powered, n2 != 0);
        final PropertyBool suspended = BlockTripWire.SUSPENDED;
        int n3;
        if ((n & "  ".length()) > 0) {
            n3 = " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)suspended, n3 != 0);
        final PropertyBool attached = BlockTripWire.ATTACHED;
        int n4;
        if ((n & (0x8E ^ 0x8A)) > 0) {
            n4 = " ".length();
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final IBlockState withProperty3 = withProperty2.withProperty((IProperty<Comparable>)attached, n4 != 0);
        final PropertyBool disarmed = BlockTripWire.DISARMED;
        int n5;
        if ((n & (0xCF ^ 0xC7)) > 0) {
            n5 = " ".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        return withProperty3.withProperty((IProperty<Comparable>)disarmed, n5 != 0);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !blockState.getValue((IProperty<Boolean>)BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && world.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockTripWire.POWERED)) {
            this.updateState(world, blockPos);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    static {
        I();
        POWERED = PropertyBool.create(BlockTripWire.I["".length()]);
        SUSPENDED = PropertyBool.create(BlockTripWire.I[" ".length()]);
        ATTACHED = PropertyBool.create(BlockTripWire.I["  ".length()]);
        DISARMED = PropertyBool.create(BlockTripWire.I["   ".length()]);
        NORTH = PropertyBool.create(BlockTripWire.I[0x85 ^ 0x81]);
        EAST = PropertyBool.create(BlockTripWire.I[0x22 ^ 0x27]);
        SOUTH = PropertyBool.create(BlockTripWire.I[0x44 ^ 0x42]);
        WEST = PropertyBool.create(BlockTripWire.I[0x9D ^ 0x9A]);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, IBlockState withProperty) {
        final IBlockState blockState = withProperty;
        final PropertyBool suspended = BlockTripWire.SUSPENDED;
        int n;
        if (World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
            n = "".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        withProperty = blockState.withProperty((IProperty<Comparable>)suspended, n != 0);
        world.setBlockState(blockPos, withProperty, "   ".length());
        this.notifyHook(world, blockPos, withProperty);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.string;
    }
    
    private void updateState(final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockTripWire.POWERED);
        int n = "".length();
        final List<Entity> entitiesWithinAABBExcludingEntity = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(blockPos.getX() + this.minX, blockPos.getY() + this.minY, blockPos.getZ() + this.minZ, blockPos.getX() + this.maxX, blockPos.getY() + this.maxY, blockPos.getZ() + this.maxZ));
        if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
            final Iterator<Entity> iterator = entitiesWithinAABBExcludingEntity.iterator();
            "".length();
            if (true != true) {
                throw null;
            }
            while (iterator.hasNext()) {
                if (!iterator.next().doesEntityNotTriggerPressurePlate()) {
                    n = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    break;
                }
            }
        }
        if (n != (booleanValue ? 1 : 0)) {
            final IBlockState withProperty = blockState.withProperty((IProperty<Comparable>)BlockTripWire.POWERED, n != 0);
            world.setBlockState(blockPos, withProperty, "   ".length());
            this.notifyHook(world, blockPos, withProperty);
        }
        if (n != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty((IProperty<Comparable>)BlockTripWire.NORTH, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.NORTH)).withProperty((IProperty<Comparable>)BlockTripWire.EAST, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.EAST)).withProperty((IProperty<Comparable>)BlockTripWire.SOUTH, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.SOUTH)).withProperty((IProperty<Comparable>)BlockTripWire.WEST, isConnectedTo(blockAccess, blockPos, blockState, EnumFacing.WEST));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.notifyHook(world, blockPos, blockState.withProperty((IProperty<Comparable>)BlockTripWire.POWERED, (boolean)(" ".length() != 0)));
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x19 ^ 0x11];
        array["".length()] = BlockTripWire.POWERED;
        array[" ".length()] = BlockTripWire.SUSPENDED;
        array["  ".length()] = BlockTripWire.ATTACHED;
        array["   ".length()] = BlockTripWire.DISARMED;
        array[0x8C ^ 0x88] = BlockTripWire.NORTH;
        array[0x14 ^ 0x11] = BlockTripWire.EAST;
        array[0x75 ^ 0x73] = BlockTripWire.WEST;
        array[0x7A ^ 0x7D] = BlockTripWire.SOUTH;
        return new BlockState(this, array);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockTripWire.DISARMED, (boolean)(" ".length() != 0)), 0x7C ^ 0x78);
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockTripWire.ATTACHED);
        if (!blockState.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED)) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else if (!booleanValue) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    private void notifyHook(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing[] array = new EnumFacing["  ".length()];
        array["".length()] = EnumFacing.SOUTH;
        array[" ".length()] = EnumFacing.WEST;
        final EnumFacing[] array2 = array;
        final int length = array.length;
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing = array2[i];
            int j = " ".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (j < (0x61 ^ 0x4B)) {
                final BlockPos offset = blockPos.offset(enumFacing, j);
                final IBlockState blockState2 = world.getBlockState(offset);
                if (blockState2.getBlock() == Blocks.tripwire_hook) {
                    if (blockState2.getValue((IProperty<Comparable>)BlockTripWireHook.FACING) != enumFacing.getOpposite()) {
                        break;
                    }
                    Blocks.tripwire_hook.func_176260_a(world, offset, blockState2, "".length() != 0, " ".length() != 0, j, blockState);
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    break;
                }
                else if (blockState2.getBlock() != Blocks.tripwire) {
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++j;
                }
            }
            ++i;
        }
    }
    
    private static void I() {
        (I = new String[0x78 ^ 0x70])["".length()] = I("(.1\u00028=%", "XAFgJ");
        BlockTripWire.I[" ".length()] = I(")#\u00019\u001f42\u0017-", "ZVrIz");
        BlockTripWire.I["  ".length()] = I("\u000b\u001f\u0011\u0018\u001b\u0002\u000e\u0001", "jkeyx");
        BlockTripWire.I["   ".length()] = I("3;5&5:7\"", "WRFGG");
        BlockTripWire.I[0x76 ^ 0x72] = I("859\u0018\u001f", "VZKlw");
        BlockTripWire.I[0x9E ^ 0x9B] = I("./\u0019#", "KNjWX");
        BlockTripWire.I[0xB3 ^ 0xB5] = I("\u0014*\u0000\u0013-", "gEugE");
        BlockTripWire.I[0x2B ^ 0x2C] = I("$\u001c\u0004<", "SywHI");
    }
    
    public BlockTripWire() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockTripWire.POWERED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.SUSPENDED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.ATTACHED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.DISARMED, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockTripWire.WEST, (boolean)("".length() != 0)));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.setTickRandomly(" ".length() != 0);
    }
    
    public static boolean isConnectedTo(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        final IBlockState blockState2 = blockAccess.getBlockState(blockPos.offset(enumFacing));
        final Block block = blockState2.getBlock();
        if (block == Blocks.tripwire_hook) {
            if (blockState2.getValue((IProperty<Comparable>)BlockTripWireHook.FACING) == enumFacing.getOpposite()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        else {
            if (block != Blocks.tripwire) {
                return "".length() != 0;
            }
            if (blockState.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED) == (boolean)blockState2.getValue((IProperty<Boolean>)BlockTripWire.SUSPENDED)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
}
