package net.minecraft.block;

import com.google.common.base.*;
import net.minecraft.block.state.pattern.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.boss.*;
import net.minecraft.stats.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.block.state.*;

public class BlockSkull extends BlockContainer
{
    private static final Predicate<BlockWorldState> IS_WITHER_SKELETON;
    private static final String[] I;
    private BlockPattern witherPattern;
    public static final PropertyBool NODROP;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    public static final PropertyDirection FACING;
    private BlockPattern witherBasePattern;
    
    protected BlockPattern getWitherPattern() {
        if (this.witherPattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockSkull.I[0xBA ^ 0xBD];
            array[" ".length()] = BlockSkull.I[0x2D ^ 0x25];
            array["  ".length()] = BlockSkull.I[0x71 ^ 0x78];
            this.witherPattern = start.aisle(array).where((char)(0x7A ^ 0x59), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.soul_sand))).where((char)(0xD9 ^ 0x87), BlockSkull.IS_WITHER_SKELETON).where((char)(0x12 ^ 0x6C), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherPattern;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue((IProperty<EnumFacing>)BlockSkull.FACING).getIndex();
        if (blockState.getValue((IProperty<Boolean>)BlockSkull.NODROP)) {
            n |= (0xBA ^ 0xB2);
        }
        return n;
    }
    
    @Override
    public void onBlockHarvested(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode) {
            withProperty = withProperty.withProperty((IProperty<Comparable>)BlockSkull.NODROP, " ".length() != 0);
            world.setBlockState(blockPos, withProperty, 0x2D ^ 0x29);
        }
        super.onBlockHarvested(world, blockPos, withProperty, entityPlayer);
    }
    
    private static void I() {
        (I = new String[0x7A ^ 0x70])["".length()] = I("\u0016%2\f:\u0017", "pDQeT");
        BlockSkull.I[" ".length()] = I("-\n#\u001383", "CeGaW");
        BlockSkull.I["  ".length()] = I("\u0005\u000e>,`\u0002\f'%\"_\u00149,\"\u0014\u0013='`\u001f\u0006?,", "qgRIN");
        BlockSkull.I["   ".length()] = I("\u0015$&\u001b\u0004\t8=\u0012\u001a", "FOSwh");
        BlockSkull.I[0xB7 ^ 0xB3] = I("CnU", "cNukB");
        BlockSkull.I[0xC5 ^ 0xC0] = I("GAz", "dbYUS");
        BlockSkull.I[0x2B ^ 0x2D] = I("8O3", "FlMVk");
        BlockSkull.I[0x23 ^ 0x24] = I("\t\u001a(", "WDvKG");
        BlockSkull.I[0xBA ^ 0xB2] = I("yJD", "ZigwJ");
        BlockSkull.I[0x53 ^ 0x5A] = I("&s\f", "XPrOD");
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = BlockSkull.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0x10 ^ 0x16);
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x7 ^ 0x3);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x96 ^ 0x93);
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return BlockSkull.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
    }
    
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(BlockSkull.I["  ".length()]);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntitySkull();
    }
    
    @Override
    public int getDamageValue(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        int n;
        if (tileEntity instanceof TileEntitySkull) {
            n = ((TileEntitySkull)tileEntity).getSkullType();
            "".length();
            if (-1 == 2) {
                throw null;
            }
        }
        else {
            n = super.getDamageValue(world, blockPos);
        }
        return n;
    }
    
    protected BlockPattern getWitherBasePattern() {
        if (this.witherBasePattern == null) {
            final FactoryBlockPattern start = FactoryBlockPattern.start();
            final String[] array = new String["   ".length()];
            array["".length()] = BlockSkull.I[0x7 ^ 0x3];
            array[" ".length()] = BlockSkull.I[0x4E ^ 0x4B];
            array["  ".length()] = BlockSkull.I[0x3E ^ 0x38];
            this.witherBasePattern = start.aisle(array).where((char)(0x35 ^ 0x16), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.soul_sand))).where((char)(0xDD ^ 0xA3), BlockWorldState.hasState((Predicate<IBlockState>)BlockStateHelper.forBlock(Blocks.air))).build();
        }
        return this.witherBasePattern;
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        FACING = PropertyDirection.create(BlockSkull.I["".length()]);
        NODROP = PropertyBool.create(BlockSkull.I[" ".length()]);
        IS_WITHER_SKELETON = (Predicate)new Predicate<BlockWorldState>() {
            public boolean apply(final BlockWorldState blockWorldState) {
                if (blockWorldState.getBlockState() != null && blockWorldState.getBlockState().getBlock() == Blocks.skull && blockWorldState.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull)blockWorldState.getTileEntity()).getSkullType() == " ".length()) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((BlockWorldState)o);
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        return super.getCollisionBoundingBox(world, blockPos, blockState);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[blockAccess.getBlockState(blockPos).getValue((IProperty<EnumFacing>)BlockSkull.FACING).ordinal()]) {
            default: {
                this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.setBlockBounds(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                "".length();
                if (3 == 0) {
                    throw null;
                }
                break;
            }
            case 4: {
                this.setBlockBounds(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                "".length();
                if (4 < 4) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.setBlockBounds(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.setBlockBounds(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    protected BlockSkull() {
        super(Material.circuits);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockSkull.FACING, EnumFacing.NORTH).withProperty((IProperty<Comparable>)BlockSkull.NODROP, (boolean)("".length() != 0)));
        this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.skull;
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.skull;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockSkull.FACING, entityLivingBase.getHorizontalFacing()).withProperty((IProperty<Comparable>)BlockSkull.NODROP, "".length() != 0);
    }
    
    public void checkWitherSpawn(final World world, final BlockPos blockPos, final TileEntitySkull tileEntitySkull) {
        if (tileEntitySkull.getSkullType() == " ".length() && blockPos.getY() >= "  ".length() && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote) {
            final BlockPattern witherPattern = this.getWitherPattern();
            final BlockPattern.PatternHelper match = witherPattern.match(world, blockPos);
            if (match != null) {
                int i = "".length();
                "".length();
                if (0 == -1) {
                    throw null;
                }
                while (i < "   ".length()) {
                    final BlockWorldState translateOffset = match.translateOffset(i, "".length(), "".length());
                    world.setBlockState(translateOffset.getPos(), translateOffset.getBlockState().withProperty((IProperty<Comparable>)BlockSkull.NODROP, (boolean)(" ".length() != 0)), "  ".length());
                    ++i;
                }
                int j = "".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (j < witherPattern.getPalmLength()) {
                    int k = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (k < witherPattern.getThumbLength()) {
                        world.setBlockState(match.translateOffset(j, k, "".length()).getPos(), Blocks.air.getDefaultState(), "  ".length());
                        ++k;
                    }
                    ++j;
                }
                final BlockPos pos = match.translateOffset(" ".length(), "".length(), "".length()).getPos();
                final EntityWither entityWither = new EntityWither(world);
                final BlockPos pos2 = match.translateOffset(" ".length(), "  ".length(), "".length()).getPos();
                final EntityWither entityWither2 = entityWither;
                final double n = pos2.getX() + 0.5;
                final double n2 = pos2.getY() + 0.55;
                final double n3 = pos2.getZ() + 0.5;
                float n4;
                if (match.getFinger().getAxis() == EnumFacing.Axis.X) {
                    n4 = 0.0f;
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    n4 = 90.0f;
                }
                entityWither2.setLocationAndAngles(n, n2, n3, n4, 0.0f);
                final EntityWither entityWither3 = entityWither;
                float renderYawOffset;
                if (match.getFinger().getAxis() == EnumFacing.Axis.X) {
                    renderYawOffset = 0.0f;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    renderYawOffset = 90.0f;
                }
                entityWither3.renderYawOffset = renderYawOffset;
                entityWither.func_82206_m();
                final Iterator<EntityPlayer> iterator = world.getEntitiesWithinAABB((Class<? extends EntityPlayer>)EntityPlayer.class, entityWither.getEntityBoundingBox().expand(50.0, 50.0, 50.0)).iterator();
                "".length();
                if (1 < 0) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    iterator.next().triggerAchievement(AchievementList.spawnWither);
                }
                world.spawnEntityInWorld(entityWither);
                int l = "".length();
                "".length();
                if (-1 >= 2) {
                    throw null;
                }
                while (l < (0xCC ^ 0xB4)) {
                    world.spawnParticle(EnumParticleTypes.SNOWBALL, pos.getX() + world.rand.nextDouble(), pos.getY() - "  ".length() + world.rand.nextDouble() * 3.9, pos.getZ() + world.rand.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                    ++l;
                }
                int length = "".length();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
                while (length < witherPattern.getPalmLength()) {
                    int length2 = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (length2 < witherPattern.getThumbLength()) {
                        world.notifyNeighborsRespectDebug(match.translateOffset(length, length2, "".length()).getPos(), Blocks.air);
                        ++length2;
                    }
                    ++length;
                }
            }
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            if (!blockState.getValue((IProperty<Boolean>)BlockSkull.NODROP)) {
                final TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntitySkull) {
                    final TileEntitySkull tileEntitySkull = (TileEntitySkull)tileEntity;
                    final ItemStack itemStack = new ItemStack(Items.skull, " ".length(), this.getDamageValue(world, blockPos));
                    if (tileEntitySkull.getSkullType() == "   ".length() && tileEntitySkull.getPlayerProfile() != null) {
                        itemStack.setTagCompound(new NBTTagCompound());
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        NBTUtil.writeGameProfile(nbtTagCompound, tileEntitySkull.getPlayerProfile());
                        itemStack.getTagCompound().setTag(BlockSkull.I["   ".length()], nbtTagCompound);
                    }
                    Block.spawnAsEntity(world, blockPos, itemStack);
                }
            }
            super.breakBlock(world, blockPos, blockState);
        }
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState withProperty = this.getDefaultState().withProperty((IProperty<Comparable>)BlockSkull.FACING, EnumFacing.getFront(n & (0xF ^ 0x8)));
        final PropertyBool nodrop = BlockSkull.NODROP;
        int n2;
        if ((n & (0xCC ^ 0xC4)) > 0) {
            n2 = " ".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return withProperty.withProperty((IProperty<Comparable>)nodrop, n2 != 0);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockSkull.FACING;
        array[" ".length()] = BlockSkull.NODROP;
        return new BlockState(this, array);
    }
    
    public boolean canDispenserPlace(final World world, final BlockPos blockPos, final ItemStack itemStack) {
        int n;
        if (itemStack.getMetadata() == " ".length() && blockPos.getY() >= "  ".length() && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote) {
            if (this.getWitherBasePattern().match(world, blockPos) != null) {
                n = " ".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
}
