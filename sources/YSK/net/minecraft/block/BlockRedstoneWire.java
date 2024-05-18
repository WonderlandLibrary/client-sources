package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;

public class BlockRedstoneWire extends Block
{
    public static final PropertyEnum<EnumAttachPosition> EAST;
    public static final PropertyEnum<EnumAttachPosition> NORTH;
    public static final PropertyEnum<EnumAttachPosition> WEST;
    private static final String[] I;
    private boolean canProvidePower;
    public static final PropertyEnum<EnumAttachPosition> SOUTH;
    private final Set<BlockPos> blocksNeedingUpdate;
    public static final PropertyInteger POWER;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        int n2;
        if (blockState.getBlock() != this) {
            n2 = super.colorMultiplier(blockAccess, blockPos, n);
            "".length();
            if (2 == 1) {
                throw null;
            }
        }
        else {
            n2 = this.colorMultiplier(blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER));
        }
        return n2;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    private boolean func_176339_d(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        final IBlockState blockState = blockAccess.getBlockState(offset);
        final Block block = blockState.getBlock();
        final boolean normalCube = block.isNormalCube();
        int n;
        if (!blockAccess.getBlockState(blockPos.up()).getBlock().isNormalCube() && normalCube && canConnectUpwardsTo(blockAccess, offset.up())) {
            n = " ".length();
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else if (canConnectTo(blockState, enumFacing)) {
            n = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else if (block == Blocks.powered_repeater && blockState.getValue((IProperty<Comparable>)BlockRedstoneDiode.FACING) == enumFacing) {
            n = " ".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (!normalCube && canConnectUpwardsTo(blockAccess, offset.down())) {
            n = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        final int intValue = blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
        if (intValue != 0) {
            final double n = blockPos.getX() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final double n2 = blockPos.getY() + 0.0625f;
            final double n3 = blockPos.getZ() + 0.5 + (random.nextFloat() - 0.5) * 0.2;
            final float n4 = intValue / 15.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE, n, n2, n3, n4 * 0.6f + 0.4f, Math.max(0.0f, n4 * n4 * 0.7f - 0.5f), Math.max(0.0f, n4 * n4 * 0.6f - 0.7f), new int["".length()]);
        }
    }
    
    protected static boolean canConnectTo(final IBlockState blockState, final EnumFacing enumFacing) {
        final Block block = blockState.getBlock();
        if (block == Blocks.redstone_wire) {
            return " ".length() != 0;
        }
        if (Blocks.unpowered_repeater.isAssociated(block)) {
            final EnumFacing enumFacing2 = blockState.getValue((IProperty<EnumFacing>)BlockRedstoneRepeater.FACING);
            if (enumFacing2 != enumFacing && enumFacing2.getOpposite() != enumFacing) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        else {
            if (block.canProvidePower() && enumFacing != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    private int colorMultiplier(final int n) {
        final float n2 = n / 15.0f;
        float n3 = n2 * 0.6f + 0.4f;
        if (n == 0) {
            n3 = 0.3f;
        }
        float n4 = n2 * n2 * 0.7f - 0.5f;
        float n5 = n2 * n2 * 0.6f - 0.7f;
        if (n4 < 0.0f) {
            n4 = 0.0f;
        }
        if (n5 < 0.0f) {
            n5 = 0.0f;
        }
        return -(5678746 + 12441081 - 4062131 + 2719520) | MathHelper.clamp_int((int)(n3 * 255.0f), "".length(), 23 + 154 + 55 + 23) << (0x15 ^ 0x5) | MathHelper.clamp_int((int)(n4 * 255.0f), "".length(), 208 + 180 - 140 + 7) << (0x91 ^ 0x99) | MathHelper.clamp_int((int)(n5 * 255.0f), "".length(), 44 + 84 + 80 + 47);
    }
    
    @Override
    public boolean canProvidePower() {
        return this.canProvidePower;
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.VERTICAL.iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                world.notifyNeighborsOfStateChange(blockPos.offset(iterator.next()), this);
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (2 != 2) {
                throw null;
            }
            while (iterator2.hasNext()) {
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(iterator2.next()));
            }
            final Iterator iterator3 = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (iterator3.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator3.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, offset.up());
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    this.notifyWireNeighborsOfStateChange(world, offset.down());
                }
            }
        }
    }
    
    private void notifyWireNeighborsOfStateChange(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() == this) {
            world.notifyNeighborsOfStateChange(blockPos, this);
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[i]), this);
                ++i;
            }
        }
    }
    
    private IBlockState calculateCurrentChanges(final World world, final BlockPos blockPos, final BlockPos blockPos2, IBlockState withProperty) {
        final IBlockState blockState = withProperty;
        final int intValue = withProperty.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
        int n = this.getMaxCurrentStrength(world, blockPos2, "".length());
        this.canProvidePower = ("".length() != 0);
        final int blockIndirectlyGettingPowered = world.isBlockIndirectlyGettingPowered(blockPos);
        this.canProvidePower = (" ".length() != 0);
        if (blockIndirectlyGettingPowered > 0 && blockIndirectlyGettingPowered > n - " ".length()) {
            n = blockIndirectlyGettingPowered;
        }
        int n2 = "".length();
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final BlockPos offset = blockPos.offset(iterator.next());
            int n3;
            if (offset.getX() == blockPos2.getX() && offset.getZ() == blockPos2.getZ()) {
                n3 = "".length();
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                n3 = " ".length();
            }
            final int n4 = n3;
            if (n4 != 0) {
                n2 = this.getMaxCurrentStrength(world, offset, n2);
            }
            if (world.getBlockState(offset).getBlock().isNormalCube() && !world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
                if (n4 == 0 || blockPos.getY() < blockPos2.getY()) {
                    continue;
                }
                n2 = this.getMaxCurrentStrength(world, offset.up(), n2);
                "".length();
                if (2 < 0) {
                    throw null;
                }
                continue;
            }
            else {
                if (world.getBlockState(offset).getBlock().isNormalCube() || n4 == 0 || blockPos.getY() > blockPos2.getY()) {
                    continue;
                }
                n2 = this.getMaxCurrentStrength(world, offset.down(), n2);
            }
        }
        if (n2 > n) {
            n = n2 - " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (n > 0) {
            --n;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        if (blockIndirectlyGettingPowered > n - " ".length()) {
            n = blockIndirectlyGettingPowered;
        }
        if (intValue != n) {
            withProperty = withProperty.withProperty((IProperty<Comparable>)BlockRedstoneWire.POWER, n);
            if (world.getBlockState(blockPos) == blockState) {
                world.setBlockState(blockPos, withProperty, "  ".length());
            }
            this.blocksNeedingUpdate.add(blockPos);
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < length) {
                this.blocksNeedingUpdate.add(blockPos.offset(values[i]));
                ++i;
            }
        }
        return withProperty;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockRedstoneWire.POWER, n);
    }
    
    private EnumAttachPosition getAttachPosition(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
        if (!canConnectTo(blockAccess.getBlockState(offset), enumFacing) && (block.isBlockNormalCube() || !canConnectUpwardsTo(blockAccess.getBlockState(offset.down())))) {
            EnumAttachPosition enumAttachPosition;
            if (!blockAccess.getBlockState(blockPos.up()).getBlock().isBlockNormalCube() && block.isBlockNormalCube() && canConnectUpwardsTo(blockAccess.getBlockState(offset.up()))) {
                enumAttachPosition = EnumAttachPosition.UP;
                "".length();
                if (3 <= 1) {
                    throw null;
                }
            }
            else {
                enumAttachPosition = EnumAttachPosition.NONE;
            }
            return enumAttachPosition;
        }
        return EnumAttachPosition.SIDE;
    }
    
    @Override
    public int getStrongPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        int n;
        if (!this.canProvidePower) {
            n = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = this.getWeakPower(blockAccess, blockPos, blockState, enumFacing);
        }
        return n;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            if (this.canPlaceBlockAt(world, blockToAir)) {
                this.updateSurroundingRedstone(world, blockToAir, blockState);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.dropBlockAsItem(world, blockToAir, blockState, "".length());
                world.setBlockToAir(blockToAir);
            }
        }
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.redstone;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x9B ^ 0x9E];
        array["".length()] = BlockRedstoneWire.NORTH;
        array[" ".length()] = BlockRedstoneWire.EAST;
        array["  ".length()] = BlockRedstoneWire.SOUTH;
        array["   ".length()] = BlockRedstoneWire.WEST;
        array[0x49 ^ 0x4D] = BlockRedstoneWire.POWER;
        return new BlockState(this, array);
    }
    
    static {
        I();
        NORTH = PropertyEnum.create(BlockRedstoneWire.I["".length()], EnumAttachPosition.class);
        EAST = PropertyEnum.create(BlockRedstoneWire.I[" ".length()], EnumAttachPosition.class);
        SOUTH = PropertyEnum.create(BlockRedstoneWire.I["  ".length()], EnumAttachPosition.class);
        WEST = PropertyEnum.create(BlockRedstoneWire.I["   ".length()], EnumAttachPosition.class);
        POWER = PropertyInteger.create(BlockRedstoneWire.I[0xC0 ^ 0xC4], "".length(), 0x7B ^ 0x74);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && world.getBlockState(blockPos.down()).getBlock() != Blocks.glowstone) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty(BlockRedstoneWire.WEST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.WEST));
        blockState = blockState.withProperty(BlockRedstoneWire.EAST, this.getAttachPosition(blockAccess, blockPos, EnumFacing.EAST));
        blockState = blockState.withProperty(BlockRedstoneWire.NORTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.NORTH));
        blockState = blockState.withProperty(BlockRedstoneWire.SOUTH, this.getAttachPosition(blockAccess, blockPos, EnumFacing.SOUTH));
        return blockState;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    public BlockRedstoneWire() {
        super(Material.circuits);
        this.canProvidePower = (" ".length() != 0);
        this.blocksNeedingUpdate = (Set<BlockPos>)Sets.newHashSet();
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneWire.NORTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.EAST, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.SOUTH, EnumAttachPosition.NONE).withProperty(BlockRedstoneWire.WEST, EnumAttachPosition.NONE).withProperty((IProperty<Comparable>)BlockRedstoneWire.POWER, "".length()));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }
    
    private static void I() {
        (I = new String[0x9A ^ 0x9F])["".length()] = I(" \"\u0011\u000e1", "NMczY");
        BlockRedstoneWire.I[" ".length()] = I("\u0010\u0007\u0011\"", "ufbVR");
        BlockRedstoneWire.I["  ".length()] = I("\u001d\u00002=\"", "noGIJ");
        BlockRedstoneWire.I["   ".length()] = I("#<\u00107", "TYcCP");
        BlockRedstoneWire.I[0x70 ^ 0x74] = I("8\f%\u001c?", "HcRyM");
    }
    
    @Override
    public int getWeakPower(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState, final EnumFacing enumFacing) {
        if (!this.canProvidePower) {
            return "".length();
        }
        final int intValue = blockState.getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
        if (intValue == 0) {
            return "".length();
        }
        if (enumFacing == EnumFacing.UP) {
            return intValue;
        }
        final EnumSet<EnumFacing> none = EnumSet.noneOf(EnumFacing.class);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing2 = iterator.next();
            if (this.func_176339_d(blockAccess, blockPos, enumFacing2)) {
                none.add(enumFacing2);
            }
        }
        if (enumFacing.getAxis().isHorizontal() && none.isEmpty()) {
            return intValue;
        }
        if (none.contains(enumFacing) && !none.contains(enumFacing.rotateYCCW()) && !none.contains(enumFacing.rotateY())) {
            return intValue;
        }
        return "".length();
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.redstone;
    }
    
    protected static boolean canConnectUpwardsTo(final IBlockAccess blockAccess, final BlockPos blockPos) {
        return canConnectUpwardsTo(blockAccess.getBlockState(blockPos));
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        if (!world.isRemote) {
            final EnumFacing[] values;
            final int length = (values = EnumFacing.values()).length;
            int i = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (i < length) {
                world.notifyNeighborsOfStateChange(blockPos.offset(values[i]), this);
                ++i;
            }
            this.updateSurroundingRedstone(world, blockPos, blockState);
            final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.notifyWireNeighborsOfStateChange(world, blockPos.offset(iterator.next()));
            }
            final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
            "".length();
            if (0 >= 3) {
                throw null;
            }
            while (iterator2.hasNext()) {
                final BlockPos offset = blockPos.offset(iterator2.next());
                if (world.getBlockState(offset).getBlock().isNormalCube()) {
                    this.notifyWireNeighborsOfStateChange(world, offset.up());
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    continue;
                }
                else {
                    this.notifyWireNeighborsOfStateChange(world, offset.down());
                }
            }
        }
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    protected static boolean canConnectUpwardsTo(final IBlockState blockState) {
        return canConnectTo(blockState, null);
    }
    
    private IBlockState updateSurroundingRedstone(final World world, final BlockPos blockPos, IBlockState calculateCurrentChanges) {
        calculateCurrentChanges = this.calculateCurrentChanges(world, blockPos, blockPos, calculateCurrentChanges);
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.blocksNeedingUpdate);
        this.blocksNeedingUpdate.clear();
        final Iterator<BlockPos> iterator = arrayList.iterator();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            world.notifyNeighborsOfStateChange(iterator.next(), this);
        }
        return calculateCurrentChanges;
    }
    
    private int getMaxCurrentStrength(final World world, final BlockPos blockPos, final int n) {
        if (world.getBlockState(blockPos).getBlock() != this) {
            return n;
        }
        final int intValue = world.getBlockState(blockPos).getValue((IProperty<Integer>)BlockRedstoneWire.POWER);
        int n2;
        if (intValue > n) {
            n2 = intValue;
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    enum EnumAttachPosition implements IStringSerializable
    {
        UP(EnumAttachPosition.I["".length()], "".length(), EnumAttachPosition.I[" ".length()]);
        
        private static final String[] I;
        private static final EnumAttachPosition[] ENUM$VALUES;
        
        NONE(EnumAttachPosition.I[0x95 ^ 0x91], "  ".length(), EnumAttachPosition.I[0xBB ^ 0xBE]), 
        SIDE(EnumAttachPosition.I["  ".length()], " ".length(), EnumAttachPosition.I["   ".length()]);
        
        private final String name;
        
        private static void I() {
            (I = new String[0x69 ^ 0x6F])["".length()] = I("\u001f\u0017", "JGPBO");
            EnumAttachPosition.I[" ".length()] = I("'6", "RFtNq");
            EnumAttachPosition.I["  ".length()] = I("\u0012=!<", "AteyH");
            EnumAttachPosition.I["   ".length()] = I("+'2+", "XNVNj");
            EnumAttachPosition.I[0x3B ^ 0x3F] = I("\u00079\u001e\"", "IvPgu");
            EnumAttachPosition.I[0x46 ^ 0x43] = I(">8-\"", "PWCGW");
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        private EnumAttachPosition(final String s, final int n, final String name) {
            this.name = name;
        }
        
        static {
            I();
            final EnumAttachPosition[] enum$VALUES = new EnumAttachPosition["   ".length()];
            enum$VALUES["".length()] = EnumAttachPosition.UP;
            enum$VALUES[" ".length()] = EnumAttachPosition.SIDE;
            enum$VALUES["  ".length()] = EnumAttachPosition.NONE;
            ENUM$VALUES = enum$VALUES;
        }
        
        @Override
        public String toString() {
            return this.getName();
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
                if (0 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
