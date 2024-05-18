package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;

public class BlockVine extends Block
{
    public static final PropertyBool NORTH;
    public static final PropertyBool EAST;
    public static final PropertyBool WEST;
    private static final String[] I;
    public static final PropertyBool[] ALL_FACES;
    public static final PropertyBool UP;
    public static final PropertyBool SOUTH;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int length = "".length();
        if (blockState.getValue((IProperty<Boolean>)BlockVine.SOUTH)) {
            length |= " ".length();
        }
        if (blockState.getValue((IProperty<Boolean>)BlockVine.WEST)) {
            length |= "  ".length();
        }
        if (blockState.getValue((IProperty<Boolean>)BlockVine.NORTH)) {
            length |= (0xA7 ^ 0xA3);
        }
        if (blockState.getValue((IProperty<Boolean>)BlockVine.EAST)) {
            length |= (0x6C ^ 0x64);
        }
        return length;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0xD ^ 0x8];
        array["".length()] = BlockVine.UP;
        array[" ".length()] = BlockVine.NORTH;
        array["  ".length()] = BlockVine.EAST;
        array["   ".length()] = BlockVine.SOUTH;
        array[0x70 ^ 0x74] = BlockVine.WEST;
        return new BlockState(this, array);
    }
    
    static {
        I();
        UP = PropertyBool.create(BlockVine.I["".length()]);
        NORTH = PropertyBool.create(BlockVine.I[" ".length()]);
        EAST = PropertyBool.create(BlockVine.I["  ".length()]);
        SOUTH = PropertyBool.create(BlockVine.I["   ".length()]);
        WEST = PropertyBool.create(BlockVine.I[0x36 ^ 0x32]);
        final PropertyBool[] all_FACES = new PropertyBool[0x98 ^ 0x9D];
        all_FACES["".length()] = BlockVine.UP;
        all_FACES[" ".length()] = BlockVine.NORTH;
        all_FACES["  ".length()] = BlockVine.SOUTH;
        all_FACES["   ".length()] = BlockVine.WEST;
        all_FACES[0xC0 ^ 0xC4] = BlockVine.EAST;
        ALL_FACES = all_FACES;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockVine.UP, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.WEST, "".length() != 0);
        IBlockState withProperty2;
        if (enumFacing.getAxis().isHorizontal()) {
            withProperty2 = withProperty.withProperty((IProperty<Comparable>)getPropertyFor(enumFacing.getOpposite()), " ".length() != 0);
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            withProperty2 = withProperty;
        }
        return withProperty2;
    }
    
    private boolean recheckGrownSides(final World world, final BlockPos blockPos, IBlockState withProperty) {
        final IBlockState blockState = withProperty;
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator.next();
            final PropertyBool property = getPropertyFor(enumFacing);
            if (withProperty.getValue((IProperty<Boolean>)property) && !this.canPlaceOn(world.getBlockState(blockPos.offset(enumFacing)).getBlock())) {
                final IBlockState blockState2 = world.getBlockState(blockPos.up());
                if (blockState2.getBlock() == this && blockState2.getValue((IProperty<Boolean>)property)) {
                    continue;
                }
                withProperty = withProperty.withProperty((IProperty<Comparable>)property, "".length() != 0);
            }
        }
        if (getNumGrownFaces(withProperty) == 0) {
            return "".length() != 0;
        }
        if (blockState != withProperty) {
            world.setBlockState(blockPos, withProperty, "  ".length());
        }
        return " ".length() != 0;
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!world.isRemote && !this.recheckGrownSides(world, blockToAir, blockState)) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
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
            if (0 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return blockAccess.getBiomeGenForCoords(blockPos).getFoliageColorAtPos(blockPos);
    }
    
    @Override
    public void harvestBlock(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final IBlockState blockState, final TileEntity tileEntity) {
        if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
            entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            Block.spawnAsEntity(world, blockPos, new ItemStack(Blocks.vine, " ".length(), "".length()));
            "".length();
            if (3 == 4) {
                throw null;
            }
        }
        else {
            super.harvestBlock(world, entityPlayer, blockPos, blockState, tileEntity);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    private boolean canPlaceOn(final Block block) {
        if (block.isFullCube() && block.blockMaterial.blocksMovement()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    private static void I() {
        (I = new String[0xD ^ 0xB])["".length()] = I("\u00143", "aCXEH");
        BlockVine.I[" ".length()] = I("\u0007\u000b0>\u001f", "idBJw");
        BlockVine.I["  ".length()] = I("\",\u001a\u0002", "GMivs");
        BlockVine.I["   ".length()] = I("\u000376?\t", "pXCKa");
        BlockVine.I[0x58 ^ 0x5C] = I("/\u00165!", "XsFUR");
        BlockVine.I[0x40 ^ 0x45] = I("g\u001a\u0018e\u0016)S\u0002+\u0001&\u001f\u0002!W$\u001b\u0004,\u0014\"", "GskEw");
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        return blockState.withProperty((IProperty<Comparable>)BlockVine.UP, blockAccess.getBlockState(blockPos.up()).getBlock().isBlockNormalCube());
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && world.rand.nextInt(0xC2 ^ 0xC6) == 0) {
            final int n = 0x24 ^ 0x20;
            int n2 = 0xA7 ^ 0xA2;
            int n3 = "".length();
            int i = -n;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        Label_0188:
            while (i <= n) {
                int j = -n;
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
                while (j <= n) {
                    int k = -" ".length();
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    while (k <= " ".length()) {
                        if (world.getBlockState(blockPos.add(i, k, j)).getBlock() == this && --n2 <= 0) {
                            n3 = " ".length();
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                            break Label_0188;
                        }
                        else {
                            ++k;
                        }
                    }
                    ++j;
                }
                ++i;
            }
            final EnumFacing random2 = EnumFacing.random(random);
            final BlockPos up = blockPos.up();
            if (random2 == EnumFacing.UP && blockPos.getY() < 169 + 218 - 368 + 236 && world.isAirBlock(up)) {
                if (n3 == 0) {
                    IBlockState withProperty = blockState;
                    final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final EnumFacing enumFacing = iterator.next();
                        if (random.nextBoolean() || !this.canPlaceOn(world.getBlockState(up.offset(enumFacing)).getBlock())) {
                            withProperty = withProperty.withProperty((IProperty<Comparable>)getPropertyFor(enumFacing), "".length() != 0);
                        }
                    }
                    if (withProperty.getValue((IProperty<Boolean>)BlockVine.NORTH) || withProperty.getValue((IProperty<Boolean>)BlockVine.EAST) || withProperty.getValue((IProperty<Boolean>)BlockVine.SOUTH) || withProperty.getValue((IProperty<Boolean>)BlockVine.WEST)) {
                        world.setBlockState(up, withProperty, "  ".length());
                        "".length();
                        if (4 < -1) {
                            throw null;
                        }
                    }
                }
            }
            else if (random2.getAxis().isHorizontal() && !blockState.getValue((IProperty<Boolean>)getPropertyFor(random2))) {
                if (n3 == 0) {
                    final BlockPos offset = blockPos.offset(random2);
                    final Block block = world.getBlockState(offset).getBlock();
                    if (block.blockMaterial == Material.air) {
                        final EnumFacing rotateY = random2.rotateY();
                        final EnumFacing rotateYCCW = random2.rotateYCCW();
                        final boolean booleanValue = blockState.getValue((IProperty<Boolean>)getPropertyFor(rotateY));
                        final boolean booleanValue2 = blockState.getValue((IProperty<Boolean>)getPropertyFor(rotateYCCW));
                        final BlockPos offset2 = offset.offset(rotateY);
                        final BlockPos offset3 = offset.offset(rotateYCCW);
                        if (booleanValue && this.canPlaceOn(world.getBlockState(offset2).getBlock())) {
                            world.setBlockState(offset, this.getDefaultState().withProperty((IProperty<Comparable>)getPropertyFor(rotateY), (boolean)(" ".length() != 0)), "  ".length());
                            "".length();
                            if (4 < 0) {
                                throw null;
                            }
                        }
                        else if (booleanValue2 && this.canPlaceOn(world.getBlockState(offset3).getBlock())) {
                            world.setBlockState(offset, this.getDefaultState().withProperty((IProperty<Comparable>)getPropertyFor(rotateYCCW), (boolean)(" ".length() != 0)), "  ".length());
                            "".length();
                            if (-1 < -1) {
                                throw null;
                            }
                        }
                        else if (booleanValue && world.isAirBlock(offset2) && this.canPlaceOn(world.getBlockState(blockPos.offset(rotateY)).getBlock())) {
                            world.setBlockState(offset2, this.getDefaultState().withProperty((IProperty<Comparable>)getPropertyFor(random2.getOpposite()), (boolean)(" ".length() != 0)), "  ".length());
                            "".length();
                            if (4 <= 2) {
                                throw null;
                            }
                        }
                        else if (booleanValue2 && world.isAirBlock(offset3) && this.canPlaceOn(world.getBlockState(blockPos.offset(rotateYCCW)).getBlock())) {
                            world.setBlockState(offset3, this.getDefaultState().withProperty((IProperty<Comparable>)getPropertyFor(random2.getOpposite()), (boolean)(" ".length() != 0)), "  ".length());
                            "".length();
                            if (0 == 4) {
                                throw null;
                            }
                        }
                        else if (this.canPlaceOn(world.getBlockState(offset.up()).getBlock())) {
                            world.setBlockState(offset, this.getDefaultState(), "  ".length());
                            "".length();
                            if (-1 >= 3) {
                                throw null;
                            }
                        }
                    }
                    else if (block.blockMaterial.isOpaque() && block.isFullCube()) {
                        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)getPropertyFor(random2), (boolean)(" ".length() != 0)), "  ".length());
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                }
            }
            else if (blockPos.getY() > " ".length()) {
                final BlockPos down = blockPos.down();
                final IBlockState blockState2 = world.getBlockState(down);
                final Block block2 = blockState2.getBlock();
                if (block2.blockMaterial == Material.air) {
                    IBlockState withProperty2 = blockState;
                    final Iterator iterator2 = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (2 >= 4) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final EnumFacing enumFacing2 = iterator2.next();
                        if (random.nextBoolean()) {
                            withProperty2 = withProperty2.withProperty((IProperty<Comparable>)getPropertyFor(enumFacing2), "".length() != 0);
                        }
                    }
                    if (withProperty2.getValue((IProperty<Boolean>)BlockVine.NORTH) || withProperty2.getValue((IProperty<Boolean>)BlockVine.EAST) || withProperty2.getValue((IProperty<Boolean>)BlockVine.SOUTH) || withProperty2.getValue((IProperty<Boolean>)BlockVine.WEST)) {
                        world.setBlockState(down, withProperty2, "  ".length());
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                    }
                }
                else if (block2 == this) {
                    IBlockState withProperty3 = blockState2;
                    final Iterator iterator3 = EnumFacing.Plane.HORIZONTAL.iterator();
                    "".length();
                    if (2 == 1) {
                        throw null;
                    }
                    while (iterator3.hasNext()) {
                        final PropertyBool property = getPropertyFor(iterator3.next());
                        if (random.nextBoolean() && blockState.getValue((IProperty<Boolean>)property)) {
                            withProperty3 = withProperty3.withProperty((IProperty<T>)property, " ".length() != 0);
                        }
                    }
                    if (withProperty3.getValue((IProperty<Boolean>)BlockVine.NORTH) || withProperty3.getValue((IProperty<Boolean>)BlockVine.EAST) || withProperty3.getValue((IProperty<Boolean>)BlockVine.SOUTH) || withProperty3.getValue((IProperty<Boolean>)BlockVine.WEST)) {
                        world.setBlockState(down, withProperty3, "  ".length());
                    }
                }
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 2: {
                return this.canPlaceOn(world.getBlockState(blockPos.up()).getBlock());
            }
            case 3:
            case 4:
            case 5:
            case 6: {
                return this.canPlaceOn(world.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock());
            }
            default: {
                return "".length() != 0;
            }
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    public BlockVine() {
        super(Material.vine);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockVine.UP, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockVine.WEST, (boolean)("".length() != 0)));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockVine.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x63 ^ 0x65);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0xAE ^ 0xAA);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x6D ^ 0x68);
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockVine.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    public static int getNumGrownFaces(final IBlockState blockState) {
        int length = "".length();
        final PropertyBool[] all_FACES;
        final int length2 = (all_FACES = BlockVine.ALL_FACES).length;
        int i = "".length();
        "".length();
        if (3 <= -1) {
            throw null;
        }
        while (i < length2) {
            if (blockState.getValue((IProperty<Boolean>)all_FACES[i])) {
                ++length;
            }
            ++i;
        }
        return length;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        float min = 1.0f;
        float min2 = 1.0f;
        float min3 = 1.0f;
        float max = 0.0f;
        float n = 0.0f;
        float max2 = 0.0f;
        int n2 = "".length();
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockVine.WEST)) {
            max = Math.max(max, 0.0625f);
            min = 0.0f;
            min2 = 0.0f;
            n = 1.0f;
            min3 = 0.0f;
            max2 = 1.0f;
            n2 = " ".length();
        }
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockVine.EAST)) {
            min = Math.min(min, 0.9375f);
            max = 1.0f;
            min2 = 0.0f;
            n = 1.0f;
            min3 = 0.0f;
            max2 = 1.0f;
            n2 = " ".length();
        }
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockVine.NORTH)) {
            max2 = Math.max(max2, 0.0625f);
            min3 = 0.0f;
            min = 0.0f;
            max = 1.0f;
            min2 = 0.0f;
            n = 1.0f;
            n2 = " ".length();
        }
        if (blockAccess.getBlockState(blockPos).getValue((IProperty<Boolean>)BlockVine.SOUTH)) {
            min3 = Math.min(min3, 0.9375f);
            max2 = 1.0f;
            min = 0.0f;
            max = 1.0f;
            min2 = 0.0f;
            n = 1.0f;
            n2 = " ".length();
        }
        if (n2 == 0 && this.canPlaceOn(blockAccess.getBlockState(blockPos.up()).getBlock())) {
            min2 = Math.min(min2, 0.9375f);
            n = 1.0f;
            min = 0.0f;
            max = 1.0f;
            min3 = 0.0f;
            max2 = 1.0f;
        }
        this.setBlockBounds(min, min2, min3, max, n, max2);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool south = BlockVine.SOUTH;
        int n2;
        if ((n & " ".length()) > 0) {
            n2 = " ".length();
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final IBlockState withProperty = defaultState.withProperty((IProperty<Comparable>)south, n2 != 0);
        final PropertyBool west = BlockVine.WEST;
        int n3;
        if ((n & "  ".length()) > 0) {
            n3 = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)west, n3 != 0);
        final PropertyBool north = BlockVine.NORTH;
        int n4;
        if ((n & (0x63 ^ 0x67)) > 0) {
            n4 = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final IBlockState withProperty3 = withProperty2.withProperty((IProperty<Comparable>)north, n4 != 0);
        final PropertyBool east = BlockVine.EAST;
        int n5;
        if ((n & (0x98 ^ 0x90)) > 0) {
            n5 = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        return withProperty3.withProperty((IProperty<Comparable>)east, n5 != 0);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    public static PropertyBool getPropertyFor(final EnumFacing enumFacing) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 2: {
                return BlockVine.UP;
            }
            case 3: {
                return BlockVine.NORTH;
            }
            case 4: {
                return BlockVine.SOUTH;
            }
            case 6: {
                return BlockVine.EAST;
            }
            case 5: {
                return BlockVine.WEST;
            }
            default: {
                throw new IllegalArgumentException(enumFacing + BlockVine.I[0x4E ^ 0x4B]);
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return " ".length() != 0;
    }
}
