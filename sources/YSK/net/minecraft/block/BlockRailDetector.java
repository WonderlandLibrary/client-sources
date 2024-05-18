package net.minecraft.block;

import net.minecraft.block.properties.*;
import com.google.common.base.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.block.state.*;

public class BlockRailDetector extends BlockRailBase
{
    public static final PropertyBool POWERED;
    public static final PropertyEnum<EnumRailDirection> SHAPE;
    private static final String[] I;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("71\b9\b", "DYiIm");
        BlockRailDetector.I[" ".length()] = I("4\b8\u001c*!\u0003", "DgOyX");
    }
    
    @Override
    public int tickRate(final World world) {
        return 0x5C ^ 0x48;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World world, final BlockPos blockPos, final IBlockState blockState, final Entity entity) {
        if (!world.isRemote && !blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }
    
    protected <T extends EntityMinecart> List<T> findMinecarts(final World world, final BlockPos blockPos, final Class<T> clazz, final Predicate<Entity>... array) {
        final AxisAlignedBB dectectionBox = this.getDectectionBox(blockPos);
        List<Entity> list;
        if (array.length != " ".length()) {
            list = world.getEntitiesWithinAABB((Class<? extends Entity>)clazz, dectectionBox);
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            list = world.getEntitiesWithinAABB((Class<? extends Entity>)clazz, dectectionBox, (com.google.common.base.Predicate<? super Entity>)array["".length()]);
        }
        return (List<T>)list;
    }
    
    @Override
    public boolean canProvidePower() {
        return " ".length() != 0;
    }
    
    public BlockRailDetector() {
        super(" ".length() != 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockRailDetector.POWERED, "".length() != 0).withProperty(BlockRailDetector.SHAPE, EnumRailDirection.NORTH_SOUTH));
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public void randomTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty(BlockRailDetector.SHAPE, EnumRailDirection.byMetadata(n & (0x83 ^ 0x84)));
        final PropertyBool powered = BlockRailDetector.POWERED;
        int n2;
        if ((n & (0x75 ^ 0x7D)) > 0) {
            n2 = " ".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)powered, n2 != 0);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        this.updatePoweredState(world, blockPos, blockState);
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            n = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (enumFacing == EnumFacing.UP) {
            n = (0x23 ^ 0x2C);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockRailDetector.SHAPE).getMetadata();
        if (blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            n |= (0x61 ^ 0x69);
        }
        return n;
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            final List<EntityMinecartCommandBlock> minecarts = this.findMinecarts(world, blockPos, EntityMinecartCommandBlock.class, (Predicate<Entity>[])new Predicate["".length()]);
            if (!minecarts.isEmpty()) {
                return minecarts.get("".length()).getCommandBlockLogic().getSuccessCount();
            }
            final Class<EntityMinecart> clazz = EntityMinecart.class;
            final Predicate[] array = new Predicate[" ".length()];
            array["".length()] = EntitySelectors.selectInventories;
            final List<EntityMinecart> minecarts2 = this.findMinecarts(world, blockPos, clazz, (Predicate<Entity>[])array);
            if (!minecarts2.isEmpty()) {
                return Container.calcRedstoneFromInventory((IInventory)minecarts2.get("".length()));
            }
        }
        return "".length();
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
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
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int length;
        if (blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            length = (0x53 ^ 0x5C);
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    private void updatePoweredState(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED);
        int n = "".length();
        if (!this.findMinecarts(world, blockPos, EntityMinecart.class, (Predicate<Entity>[])new Predicate["".length()]).isEmpty()) {
            n = " ".length();
        }
        if (n != 0 && !booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockRailDetector.POWERED, (boolean)(" ".length() != 0)), "   ".length());
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (n == 0 && booleanValue) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockRailDetector.POWERED, (boolean)("".length() != 0)), "   ".length());
            world.notifyNeighborsOfStateChange(blockPos, this);
            world.notifyNeighborsOfStateChange(blockPos.down(), this);
            world.markBlockRangeForRenderUpdate(blockPos, blockPos);
        }
        if (n != 0) {
            world.scheduleUpdate(blockPos, this, this.tickRate(world));
        }
        world.updateComparatorOutputLevel(blockPos, this);
    }
    
    private AxisAlignedBB getDectectionBox(final BlockPos blockPos) {
        return new AxisAlignedBB(blockPos.getX() + 0.2f, blockPos.getY(), blockPos.getZ() + 0.2f, blockPos.getX() + " ".length() - 0.2f, blockPos.getY() + " ".length() - 0.2f, blockPos.getZ() + " ".length() - 0.2f);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockRailDetector.SHAPE;
        array[" ".length()] = BlockRailDetector.POWERED;
        return new BlockState(this, array);
    }
    
    @Override
    public IProperty<EnumRailDirection> getShapeProperty() {
        return BlockRailDetector.SHAPE;
    }
    
    static {
        I();
        SHAPE = PropertyEnum.create(BlockRailDetector.I["".length()], EnumRailDirection.class, (com.google.common.base.Predicate<EnumRailDirection>)new Predicate<EnumRailDirection>() {
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
                    if (3 == 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final EnumRailDirection enumRailDirection) {
                if (enumRailDirection != EnumRailDirection.NORTH_EAST && enumRailDirection != EnumRailDirection.NORTH_WEST && enumRailDirection != EnumRailDirection.SOUTH_EAST && enumRailDirection != EnumRailDirection.SOUTH_WEST) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((EnumRailDirection)o);
            }
        });
        POWERED = PropertyBool.create(BlockRailDetector.I[" ".length()]);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && blockState.getValue((IProperty<Boolean>)BlockRailDetector.POWERED)) {
            this.updatePoweredState(world, blockPos, blockState);
        }
    }
}
