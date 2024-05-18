package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockPistonBase extends Block
{
    private static final String[] I;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyBool EXTENDED;
    private final boolean isSticky;
    public static final PropertyDirection FACING;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockPistonBase.FACING).getIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            n |= (0x2F ^ 0x27);
        }
        return n;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        if (blockState.getBlock() == this && blockState.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockPistonBase.FACING);
            if (enumFacing != null) {
                switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                    case 1: {
                        this.setBlockBounds(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                        return;
                    }
                    case 2: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                        "".length();
                        if (-1 != -1) {
                            throw null;
                        }
                        return;
                    }
                    case 3: {
                        this.setBlockBounds(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                        "".length();
                        if (3 >= 4) {
                            throw null;
                        }
                        return;
                    }
                    case 4: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                        return;
                    }
                    case 5: {
                        this.setBlockBounds(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        return;
                    }
                    case 6: {
                        this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                        break;
                    }
                }
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockPistonBase.FACING);
        if (!world.isRemote) {
            final boolean shouldBeExtended = this.shouldBeExtended(world, blockPos, enumFacing);
            if (shouldBeExtended && n == " ".length()) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, (boolean)(" ".length() != 0)), "  ".length());
                return "".length() != 0;
            }
            if (!shouldBeExtended && n == 0) {
                return "".length() != 0;
            }
        }
        if (n == 0) {
            if (!this.doMove(world, blockPos, enumFacing, " ".length() != 0)) {
                return "".length() != 0;
            }
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, (boolean)(" ".length() != 0)), "  ".length());
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockPistonBase.I["  ".length()], 0.5f, world.rand.nextFloat() * 0.25f + 0.6f);
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else if (n == " ".length()) {
            final TileEntity tileEntity = world.getTileEntity(blockPos.offset(enumFacing));
            if (tileEntity instanceof TileEntityPiston) {
                ((TileEntityPiston)tileEntity).clearPistonTileEntity();
            }
            final IBlockState withProperty = Blocks.piston_extension.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonMoving.FACING, enumFacing);
            final PropertyEnum<BlockPistonExtension.EnumPistonType> type = BlockPistonMoving.TYPE;
            BlockPistonExtension.EnumPistonType enumPistonType;
            if (this.isSticky) {
                enumPistonType = BlockPistonExtension.EnumPistonType.STICKY;
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
            else {
                enumPistonType = BlockPistonExtension.EnumPistonType.DEFAULT;
            }
            world.setBlockState(blockPos, withProperty.withProperty((IProperty<Comparable>)type, enumPistonType), "   ".length());
            world.setTileEntity(blockPos, BlockPistonMoving.newTileEntity(this.getStateFromMeta(n2), enumFacing, (boolean)("".length() != 0), (boolean)(" ".length() != 0)));
            if (this.isSticky) {
                final BlockPos add = blockPos.add(enumFacing.getFrontOffsetX() * "  ".length(), enumFacing.getFrontOffsetY() * "  ".length(), enumFacing.getFrontOffsetZ() * "  ".length());
                final Block block = world.getBlockState(add).getBlock();
                int n3 = "".length();
                if (block == Blocks.piston_extension) {
                    final TileEntity tileEntity2 = world.getTileEntity(add);
                    if (tileEntity2 instanceof TileEntityPiston) {
                        final TileEntityPiston tileEntityPiston = (TileEntityPiston)tileEntity2;
                        if (tileEntityPiston.getFacing() == enumFacing && tileEntityPiston.isExtending()) {
                            tileEntityPiston.clearPistonTileEntity();
                            n3 = " ".length();
                        }
                    }
                }
                if (n3 == 0 && block.getMaterial() != Material.air && canPush(block, world, add, enumFacing.getOpposite(), "".length() != 0) && (block.getMobilityFlag() == 0 || block == Blocks.piston || block == Blocks.sticky_piston)) {
                    this.doMove(world, blockPos, enumFacing, "".length() != 0);
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
            }
            else {
                world.setBlockToAir(blockPos.offset(enumFacing));
            }
            world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, BlockPistonBase.I["   ".length()], 0.5f, world.rand.nextFloat() * 0.15f + 0.6f);
        }
        return " ".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockPistonBase.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x9E ^ 0x98);
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x9F ^ 0x9B);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x5B ^ 0x5E);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockPistonBase.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    private boolean shouldBeExtended(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < length) {
            final EnumFacing enumFacing2 = values[i];
            if (enumFacing2 != enumFacing && world.isSidePowered(blockPos.offset(enumFacing2), enumFacing2)) {
                return " ".length() != 0;
            }
            ++i;
        }
        if (world.isSidePowered(blockPos, EnumFacing.DOWN)) {
            return " ".length() != 0;
        }
        final BlockPos up = blockPos.up();
        final EnumFacing[] values2;
        final int length2 = (values2 = EnumFacing.values()).length;
        int j = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (j < length2) {
            final EnumFacing enumFacing3 = values2[j];
            if (enumFacing3 != EnumFacing.DOWN && world.isSidePowered(up.offset(enumFacing3), enumFacing3)) {
                return " ".length() != 0;
            }
            ++j;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityLivingBase entityLivingBase, final ItemStack itemStack) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockPistonBase.FACING, getFacingFromEntity(world, blockPos, entityLivingBase)), "  ".length());
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    public static boolean canPush(final Block block, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final boolean b) {
        if (block == Blocks.obsidian) {
            return "".length() != 0;
        }
        if (!world.getWorldBorder().contains(blockPos)) {
            return "".length() != 0;
        }
        if (blockPos.getY() < 0 || (enumFacing == EnumFacing.DOWN && blockPos.getY() == 0)) {
            return "".length() != 0;
        }
        if (blockPos.getY() <= world.getHeight() - " ".length() && (enumFacing != EnumFacing.UP || blockPos.getY() != world.getHeight() - " ".length())) {
            if (block != Blocks.piston && block != Blocks.sticky_piston) {
                if (block.getBlockHardness(world, blockPos) == -1.0f) {
                    return "".length() != 0;
                }
                if (block.getMobilityFlag() == "  ".length()) {
                    return "".length() != 0;
                }
                if (block.getMobilityFlag() == " ".length()) {
                    if (!b) {
                        return "".length() != 0;
                    }
                    return " ".length() != 0;
                }
            }
            else if (world.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
                return "".length() != 0;
            }
            int n;
            if (block instanceof ITileEntityProvider) {
                n = "".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            return n != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonBase.FACING, getFacingFromEntity(world, blockPos, entityLivingBase)).withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, "".length() != 0);
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockPistonBase.I["".length()]);
        EXTENDED = PropertyBool.create(BlockPistonBase.I[" ".length()]);
    }
    
    private boolean doMove(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final boolean b) {
        if (!b) {
            world.setBlockToAir(blockPos.offset(enumFacing));
        }
        final BlockPistonStructureHelper blockPistonStructureHelper = new BlockPistonStructureHelper(world, blockPos, enumFacing, b);
        final List<BlockPos> blocksToMove = blockPistonStructureHelper.getBlocksToMove();
        final List<BlockPos> blocksToDestroy = blockPistonStructureHelper.getBlocksToDestroy();
        if (!blockPistonStructureHelper.canMove()) {
            return "".length() != 0;
        }
        int n = blocksToMove.size() + blocksToDestroy.size();
        final Block[] array = new Block[n];
        EnumFacing opposite;
        if (b) {
            opposite = enumFacing;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            opposite = enumFacing.getOpposite();
        }
        final EnumFacing enumFacing2 = opposite;
        int i = blocksToDestroy.size() - " ".length();
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i >= 0) {
            final BlockPos blockToAir = blocksToDestroy.get(i);
            final Block block = world.getBlockState(blockToAir).getBlock();
            block.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), "".length());
            world.setBlockToAir(blockToAir);
            --n;
            array[n] = block;
            --i;
        }
        int j = blocksToMove.size() - " ".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (j >= 0) {
            final BlockPos blockToAir2 = blocksToMove.get(j);
            final IBlockState blockState = world.getBlockState(blockToAir2);
            final Block block2 = blockState.getBlock();
            block2.getMetaFromState(blockState);
            world.setBlockToAir(blockToAir2);
            final BlockPos offset = blockToAir2.offset(enumFacing2);
            world.setBlockState(offset, Blocks.piston_extension.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonBase.FACING, enumFacing), 0x9F ^ 0x9B);
            world.setTileEntity(offset, BlockPistonMoving.newTileEntity(blockState, enumFacing, b, (boolean)("".length() != 0)));
            --n;
            array[n] = block2;
            --j;
        }
        final BlockPos offset2 = blockPos.offset(enumFacing);
        if (b) {
            BlockPistonExtension.EnumPistonType enumPistonType;
            if (this.isSticky) {
                enumPistonType = BlockPistonExtension.EnumPistonType.STICKY;
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else {
                enumPistonType = BlockPistonExtension.EnumPistonType.DEFAULT;
            }
            final IBlockState withProperty = Blocks.piston_head.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonExtension.FACING, enumFacing).withProperty(BlockPistonExtension.TYPE, enumPistonType);
            final IBlockState withProperty2 = Blocks.piston_extension.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonMoving.FACING, enumFacing);
            final PropertyEnum<BlockPistonExtension.EnumPistonType> type = BlockPistonMoving.TYPE;
            BlockPistonExtension.EnumPistonType enumPistonType2;
            if (this.isSticky) {
                enumPistonType2 = BlockPistonExtension.EnumPistonType.STICKY;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                enumPistonType2 = BlockPistonExtension.EnumPistonType.DEFAULT;
            }
            world.setBlockState(offset2, withProperty2.withProperty((IProperty<Comparable>)type, enumPistonType2), 0x73 ^ 0x77);
            world.setTileEntity(offset2, BlockPistonMoving.newTileEntity(withProperty, enumFacing, (boolean)(" ".length() != 0), (boolean)("".length() != 0)));
        }
        int k = blocksToDestroy.size() - " ".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (k >= 0) {
            world.notifyNeighborsOfStateChange(blocksToDestroy.get(k), array[n++]);
            --k;
        }
        int l = blocksToMove.size() - " ".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (l >= 0) {
            world.notifyNeighborsOfStateChange(blocksToMove.get(l), array[n++]);
            --l;
        }
        if (b) {
            world.notifyNeighborsOfStateChange(offset2, Blocks.piston_head);
            world.notifyNeighborsOfStateChange(blockPos, this);
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public BlockPistonBase(final boolean isSticky) {
        super(Material.piston);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockPistonBase.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, (boolean)("".length() != 0)));
        this.isSticky = isSticky;
        this.setStepSound(BlockPistonBase.soundTypePiston);
        this.setHardness(0.5f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public IBlockState getStateForEntityRender(final IBlockState blockState) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonBase.FACING, EnumFacing.UP);
    }
    
    private static void I() {
        (I = new String[0xB8 ^ 0xBC])["".length()] = I("7(/';6", "QILNU");
        BlockPistonBase.I[" ".length()] = I("\u0000\u001d2&%\u0001\u0000\"", "eeFCK");
        BlockPistonBase.I["  ".length()] = I("%\u0001(/O!\u00017>\u000e?F+?\u0015", "QhDJa");
        BlockPistonBase.I["   ".length()] = I("6\u0000 4X2\u0000?%\u0019,G%?", "BiLQv");
    }
    
    @Override
    public void addCollisionBoxesToList(final World world, final BlockPos blockPos, final IBlockState blockState, final AxisAlignedBB axisAlignedBB, final List<AxisAlignedBB> list, final Entity entity) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.addCollisionBoxesToList(world, blockPos, blockState, axisAlignedBB, list, entity);
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
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockPistonBase.FACING;
        array[" ".length()] = BlockPistonBase.EXTENDED;
        return new BlockState(this, array);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockPistonBase.FACING, getFacing(n));
        final PropertyBool extended = BlockPistonBase.EXTENDED;
        int n2;
        if ((n & (0x71 ^ 0x79)) > 0) {
            n2 = " ".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)extended, n2 != 0);
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote && world.getTileEntity(blockPos) == null) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        if (!world.isRemote) {
            this.checkForMove(world, blockPos, blockState);
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public static EnumFacing getFacingFromEntity(final World world, final BlockPos blockPos, final EntityLivingBase entityLivingBase) {
        if (MathHelper.abs((float)entityLivingBase.posX - blockPos.getX()) < 2.0f && MathHelper.abs((float)entityLivingBase.posZ - blockPos.getZ()) < 2.0f) {
            final double n = entityLivingBase.posY + entityLivingBase.getEyeHeight();
            if (n - blockPos.getY() > 2.0) {
                return EnumFacing.UP;
            }
            if (blockPos.getY() - n > 0.0) {
                return EnumFacing.DOWN;
            }
        }
        return entityLivingBase.getHorizontalFacing().getOpposite();
    }
    
    private void checkForMove(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final EnumFacing enumFacing = blockState.getValue((IProperty<EnumFacing>)BlockPistonBase.FACING);
        final boolean shouldBeExtended = this.shouldBeExtended(world, blockPos, enumFacing);
        if (shouldBeExtended && !blockState.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            if (new BlockPistonStructureHelper(world, blockPos, enumFacing, " ".length() != 0).canMove()) {
                world.addBlockEvent(blockPos, this, "".length(), enumFacing.getIndex());
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
        }
        else if (!shouldBeExtended && blockState.getValue((IProperty<Boolean>)BlockPistonBase.EXTENDED)) {
            world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockPistonBase.EXTENDED, (boolean)("".length() != 0)), "  ".length());
            world.addBlockEvent(blockPos, this, " ".length(), enumFacing.getIndex());
        }
    }
    
    public static EnumFacing getFacing(final int n) {
        final int n2 = n & (0x23 ^ 0x24);
        EnumFacing front;
        if (n2 > (0x3E ^ 0x3B)) {
            front = null;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            front = EnumFacing.getFront(n2);
        }
        return front;
    }
}
