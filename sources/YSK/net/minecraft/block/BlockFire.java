package net.minecraft.block;

import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockFire extends Block
{
    public static final PropertyBool FLIP;
    private static final String[] I;
    public static final PropertyInteger AGE;
    public static final PropertyBool EAST;
    public static final PropertyBool ALT;
    public static final PropertyBool SOUTH;
    public static final PropertyInteger UPPER;
    private final Map<Block, Integer> encouragements;
    public static final PropertyBool NORTH;
    public static final PropertyBool WEST;
    private final Map<Block, Integer> flammabilities;
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (world.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(world, blockToAir)) {
            if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down()) && !this.canNeighborCatchFire(world, blockToAir)) {
                world.setBlockToAir(blockToAir);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            else {
                world.scheduleUpdate(blockToAir, this, this.tickRate(world) + world.rand.nextInt(0x2B ^ 0x21));
            }
        }
    }
    
    public void setFireInfo(final Block block, final int n, final int n2) {
        this.encouragements.put(block, n);
        this.flammabilities.put(block, n2);
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return MapColor.tntColor;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockFire.AGE, n);
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BlockFire() {
        super(Material.fire);
        this.encouragements = (Map<Block, Integer>)Maps.newIdentityHashMap();
        this.flammabilities = (Map<Block, Integer>)Maps.newIdentityHashMap();
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockFire.AGE, "".length()).withProperty((IProperty<Comparable>)BlockFire.FLIP, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.ALT, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.WEST, "".length() != 0).withProperty((IProperty<Comparable>)BlockFire.UPPER, "".length()));
        this.setTickRandomly(" ".length() != 0);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (!World.doesBlockHaveSolidTopSurface(blockAccess, blockPos.down()) && !Blocks.fire.canCatchFire(blockAccess, blockPos.down())) {
            int n;
            if ((x + y + z & " ".length()) == " ".length()) {
                n = " ".length();
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            int n3;
            if ((x / "  ".length() + y / "  ".length() + z / "  ".length() & " ".length()) == " ".length()) {
                n3 = " ".length();
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            final int n4 = n3;
            int length = "".length();
            if (this.canCatchFire(blockAccess, blockPos.up())) {
                int n5;
                if (n2 != 0) {
                    n5 = " ".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
                else {
                    n5 = "  ".length();
                }
                length = n5;
            }
            return blockState.withProperty((IProperty<Comparable>)BlockFire.NORTH, this.canCatchFire(blockAccess, blockPos.north())).withProperty((IProperty<Comparable>)BlockFire.EAST, this.canCatchFire(blockAccess, blockPos.east())).withProperty((IProperty<Comparable>)BlockFire.SOUTH, this.canCatchFire(blockAccess, blockPos.south())).withProperty((IProperty<Comparable>)BlockFire.WEST, this.canCatchFire(blockAccess, blockPos.west())).withProperty((IProperty<Comparable>)BlockFire.UPPER, length).withProperty((IProperty<Comparable>)BlockFire.FLIP, n4 != 0).withProperty((IProperty<Comparable>)BlockFire.ALT, n2 != 0);
        }
        return this.getDefaultState();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0xCF ^ 0xC7];
        array["".length()] = BlockFire.AGE;
        array[" ".length()] = BlockFire.NORTH;
        array["  ".length()] = BlockFire.EAST;
        array["   ".length()] = BlockFire.SOUTH;
        array[0x3 ^ 0x7] = BlockFire.WEST;
        array[0x13 ^ 0x16] = BlockFire.UPPER;
        array[0xF ^ 0x9] = BlockFire.FLIP;
        array[0x1F ^ 0x18] = BlockFire.ALT;
        return new BlockState(this, array);
    }
    
    protected boolean canDie(final World world, final BlockPos blockPos) {
        if (!world.canLightningStrike(blockPos) && !world.canLightningStrike(blockPos.west()) && !world.canLightningStrike(blockPos.east()) && !world.canLightningStrike(blockPos.north()) && !world.canLightningStrike(blockPos.south())) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public int tickRate(final World world) {
        return 0x6D ^ 0x73;
    }
    
    private boolean canNeighborCatchFire(final World world, final BlockPos blockPos) {
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < length) {
            if (this.canCatchFire(world, blockPos.offset(values[i]))) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0xA ^ 0x0])["".length()] = I("\u00102\u0004", "qUajT");
        BlockFire.I[" ".length()] = I("(\b?\u0017", "NdVgd");
        BlockFire.I["  ".length()] = I("\"8\f", "CTxwn");
        BlockFire.I["   ".length()] = I("\u0014(90\u0011", "zGKDy");
        BlockFire.I[0x3B ^ 0x3F] = I("\u00169#'", "sXPSa");
        BlockFire.I[0x27 ^ 0x22] = I("\t:,\u0005%", "zUYqM");
        BlockFire.I[0x78 ^ 0x7E] = I("6\u0003\u001c\f", "Afoxo");
        BlockFire.I[0x12 ^ 0x15] = I("\u0011$4\"\b", "dTDGz");
        BlockFire.I[0xC9 ^ 0xC1] = I("\u001d74\u0007*\u001c\f\u001b\r3", "yXrnX");
        BlockFire.I[0x4A ^ 0x43] = I("\u0010\u0018+\u0016\u007f\u0010\u0018+\u0016", "vqYsQ");
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public boolean requiresUpdates() {
        return "".length() != 0;
    }
    
    private int getNeighborEncouragement(final World world, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos)) {
            return "".length();
        }
        int n = "".length();
        final EnumFacing[] values;
        final int length = (values = EnumFacing.values()).length;
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < length) {
            n = Math.max(this.getEncouragement(world.getBlockState(blockPos.offset(values[i])).getBlock()), n);
            ++i;
        }
        return n;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !this.canNeighborCatchFire(world, blockPos)) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    private int getFlammability(final Block block) {
        final Integer n = this.flammabilities.get(block);
        int n2;
        if (n == null) {
            n2 = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    private int getEncouragement(final Block block) {
        final Integer n = this.encouragements.get(block);
        int n2;
        if (n == null) {
            n2 = "".length();
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    private void catchOnFire(final World world, final BlockPos blockToAir, final int n, final Random random, final int n2) {
        if (random.nextInt(n) < this.getFlammability(world.getBlockState(blockToAir).getBlock())) {
            final IBlockState blockState = world.getBlockState(blockToAir);
            if (random.nextInt(n2 + (0x97 ^ 0x9D)) < (0x15 ^ 0x10) && !world.canLightningStrike(blockToAir)) {
                int n3 = n2 + random.nextInt(0x5E ^ 0x5B) / (0xA ^ 0xE);
                if (n3 > (0xA6 ^ 0xA9)) {
                    n3 = (0x8D ^ 0x82);
                }
                world.setBlockState(blockToAir, this.getDefaultState().withProperty((IProperty<Comparable>)BlockFire.AGE, n3), "   ".length());
                "".length();
                if (1 <= -1) {
                    throw null;
                }
            }
            else {
                world.setBlockToAir(blockToAir);
            }
            if (blockState.getBlock() == Blocks.tnt) {
                Blocks.tnt.onBlockDestroyedByPlayer(world, blockToAir, blockState.withProperty((IProperty<Comparable>)BlockTNT.EXPLODE, (boolean)(" ".length() != 0)));
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (random.nextInt(0x6 ^ 0x1E) == 0) {
            world.playSound(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, BlockFire.I[0x7C ^ 0x75], 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, "".length() != 0);
        }
        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && !Blocks.fire.canCatchFire(world, blockPos.down())) {
            if (Blocks.fire.canCatchFire(world, blockPos.west())) {
                int i = "".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
                while (i < "  ".length()) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble() * 0.10000000149011612, blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                    ++i;
                }
            }
            if (Blocks.fire.canCatchFire(world, blockPos.east())) {
                int j = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (j < "  ".length()) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + " ".length() - random.nextDouble() * 0.10000000149011612, blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                    ++j;
                }
            }
            if (Blocks.fire.canCatchFire(world, blockPos.north())) {
                int k = "".length();
                "".length();
                if (3 == 4) {
                    throw null;
                }
                while (k < "  ".length()) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble(), blockPos.getZ() + random.nextDouble() * 0.10000000149011612, 0.0, 0.0, 0.0, new int["".length()]);
                    ++k;
                }
            }
            if (Blocks.fire.canCatchFire(world, blockPos.south())) {
                int l = "".length();
                "".length();
                if (2 <= -1) {
                    throw null;
                }
                while (l < "  ".length()) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble(), blockPos.getZ() + " ".length() - random.nextDouble() * 0.10000000149011612, 0.0, 0.0, 0.0, new int["".length()]);
                    ++l;
                }
            }
            if (Blocks.fire.canCatchFire(world, blockPos.up())) {
                int length = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (length < "  ".length()) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + " ".length() - random.nextDouble() * 0.10000000149011612, blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                    ++length;
                }
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        else {
            int length2 = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
            while (length2 < "   ".length()) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, blockPos.getX() + random.nextDouble(), blockPos.getY() + random.nextDouble() * 0.5 + 0.5, blockPos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0, new int["".length()]);
                ++length2;
            }
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, IBlockState withProperty, final Random random) {
        if (world.getGameRules().getBoolean(BlockFire.I[0x7D ^ 0x75])) {
            if (!this.canPlaceBlockAt(world, blockPos)) {
                world.setBlockToAir(blockPos);
            }
            final Block block = world.getBlockState(blockPos.down()).getBlock();
            int n;
            if (block == Blocks.netherrack) {
                n = " ".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            int length = n;
            if (world.provider instanceof WorldProviderEnd && block == Blocks.bedrock) {
                length = " ".length();
            }
            if (length == 0 && world.isRaining() && this.canDie(world, blockPos)) {
                world.setBlockToAir(blockPos);
                "".length();
                if (1 < -1) {
                    throw null;
                }
            }
            else {
                final int intValue = withProperty.getValue((IProperty<Integer>)BlockFire.AGE);
                if (intValue < (0x57 ^ 0x58)) {
                    withProperty = withProperty.withProperty((IProperty<Comparable>)BlockFire.AGE, intValue + random.nextInt("   ".length()) / "  ".length());
                    world.setBlockState(blockPos, withProperty, 0x8B ^ 0x8F);
                }
                world.scheduleUpdate(blockPos, this, this.tickRate(world) + random.nextInt(0x2E ^ 0x24));
                if (length == 0) {
                    if (!this.canNeighborCatchFire(world, blockPos)) {
                        if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down()) || intValue > "   ".length()) {
                            world.setBlockToAir(blockPos);
                        }
                        return;
                    }
                    if (!this.canCatchFire(world, blockPos.down()) && intValue == (0x50 ^ 0x5F) && random.nextInt(0x7 ^ 0x3) == 0) {
                        world.setBlockToAir(blockPos);
                        return;
                    }
                }
                final boolean blockinHighHumidity = world.isBlockinHighHumidity(blockPos);
                int length2 = "".length();
                if (blockinHighHumidity) {
                    length2 = -(0x90 ^ 0xA2);
                }
                this.catchOnFire(world, blockPos.east(), 180 + 51 - 155 + 224 + length2, random, intValue);
                this.catchOnFire(world, blockPos.west(), 178 + 98 - 133 + 157 + length2, random, intValue);
                this.catchOnFire(world, blockPos.down(), 182 + 14 - 98 + 152 + length2, random, intValue);
                this.catchOnFire(world, blockPos.up(), 114 + 18 + 36 + 82 + length2, random, intValue);
                this.catchOnFire(world, blockPos.north(), 266 + 88 - 352 + 298 + length2, random, intValue);
                this.catchOnFire(world, blockPos.south(), 172 + 293 - 344 + 179 + length2, random, intValue);
                int i = -" ".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (i <= " ".length()) {
                    int j = -" ".length();
                    "".length();
                    if (3 == -1) {
                        throw null;
                    }
                    while (j <= " ".length()) {
                        int k = -" ".length();
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (k <= (0xB5 ^ 0xB1)) {
                            if (i != 0 || k != 0 || j != 0) {
                                int n2 = 0x50 ^ 0x34;
                                if (k > " ".length()) {
                                    n2 += (k - " ".length()) * (0xF3 ^ 0x97);
                                }
                                final BlockPos add = blockPos.add(i, k, j);
                                final int neighborEncouragement = this.getNeighborEncouragement(world, add);
                                if (neighborEncouragement > 0) {
                                    int n3 = (neighborEncouragement + (0xED ^ 0xC5) + world.getDifficulty().getDifficultyId() * (0xA7 ^ 0xA0)) / (intValue + (0x80 ^ 0x9E));
                                    if (blockinHighHumidity) {
                                        n3 /= "  ".length();
                                    }
                                    if (n3 > 0 && random.nextInt(n2) <= n3 && (!world.isRaining() || !this.canDie(world, add))) {
                                        int n4 = intValue + random.nextInt(0x84 ^ 0x81) / (0x1C ^ 0x18);
                                        if (n4 > (0xA9 ^ 0xA6)) {
                                            n4 = (0x80 ^ 0x8F);
                                        }
                                        world.setBlockState(add, withProperty.withProperty((IProperty<Comparable>)BlockFire.AGE, n4), "   ".length());
                                    }
                                }
                            }
                            ++k;
                        }
                        ++j;
                    }
                    ++i;
                }
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockToAir, final IBlockState blockState, final Block block) {
        if (!World.doesBlockHaveSolidTopSurface(world, blockToAir.down()) && !this.canNeighborCatchFire(world, blockToAir)) {
            world.setBlockToAir(blockToAir);
        }
    }
    
    public static void init() {
        Blocks.fire.setFireInfo(Blocks.planks, 0x2 ^ 0x7, 0xA4 ^ 0xB0);
        Blocks.fire.setFireInfo(Blocks.double_wooden_slab, 0x81 ^ 0x84, 0xA1 ^ 0xB5);
        Blocks.fire.setFireInfo(Blocks.wooden_slab, 0x47 ^ 0x42, 0x66 ^ 0x72);
        Blocks.fire.setFireInfo(Blocks.oak_fence_gate, 0xB8 ^ 0xBD, 0x1B ^ 0xF);
        Blocks.fire.setFireInfo(Blocks.spruce_fence_gate, 0x10 ^ 0x15, 0xF ^ 0x1B);
        Blocks.fire.setFireInfo(Blocks.birch_fence_gate, 0x54 ^ 0x51, 0x53 ^ 0x47);
        Blocks.fire.setFireInfo(Blocks.jungle_fence_gate, 0xC ^ 0x9, 0x18 ^ 0xC);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence_gate, 0x3C ^ 0x39, 0x64 ^ 0x70);
        Blocks.fire.setFireInfo(Blocks.acacia_fence_gate, 0x25 ^ 0x20, 0xA1 ^ 0xB5);
        Blocks.fire.setFireInfo(Blocks.oak_fence, 0x31 ^ 0x34, 0xAA ^ 0xBE);
        Blocks.fire.setFireInfo(Blocks.spruce_fence, 0xB3 ^ 0xB6, 0xE ^ 0x1A);
        Blocks.fire.setFireInfo(Blocks.birch_fence, 0x15 ^ 0x10, 0x39 ^ 0x2D);
        Blocks.fire.setFireInfo(Blocks.jungle_fence, 0x91 ^ 0x94, 0xB ^ 0x1F);
        Blocks.fire.setFireInfo(Blocks.dark_oak_fence, 0x18 ^ 0x1D, 0x9 ^ 0x1D);
        Blocks.fire.setFireInfo(Blocks.acacia_fence, 0x6A ^ 0x6F, 0x6F ^ 0x7B);
        Blocks.fire.setFireInfo(Blocks.oak_stairs, 0x86 ^ 0x83, 0x4A ^ 0x5E);
        Blocks.fire.setFireInfo(Blocks.birch_stairs, 0x9A ^ 0x9F, 0x78 ^ 0x6C);
        Blocks.fire.setFireInfo(Blocks.spruce_stairs, 0x1 ^ 0x4, 0x4A ^ 0x5E);
        Blocks.fire.setFireInfo(Blocks.jungle_stairs, 0x61 ^ 0x64, 0xB3 ^ 0xA7);
        Blocks.fire.setFireInfo(Blocks.log, 0x9E ^ 0x9B, 0x1 ^ 0x4);
        Blocks.fire.setFireInfo(Blocks.log2, 0xAE ^ 0xAB, 0x9C ^ 0x99);
        Blocks.fire.setFireInfo(Blocks.leaves, 0x4D ^ 0x53, 0xC ^ 0x30);
        Blocks.fire.setFireInfo(Blocks.leaves2, 0x69 ^ 0x77, 0xC ^ 0x30);
        Blocks.fire.setFireInfo(Blocks.bookshelf, 0x1B ^ 0x5, 0x25 ^ 0x31);
        Blocks.fire.setFireInfo(Blocks.tnt, 0x65 ^ 0x6A, 0x2B ^ 0x4F);
        Blocks.fire.setFireInfo(Blocks.tallgrass, 0x7E ^ 0x42, 0xD6 ^ 0xB2);
        Blocks.fire.setFireInfo(Blocks.double_plant, 0x96 ^ 0xAA, 0x36 ^ 0x52);
        Blocks.fire.setFireInfo(Blocks.yellow_flower, 0x6B ^ 0x57, 0x46 ^ 0x22);
        Blocks.fire.setFireInfo(Blocks.red_flower, 0x3D ^ 0x1, 0xE2 ^ 0x86);
        Blocks.fire.setFireInfo(Blocks.deadbush, 0x52 ^ 0x6E, 0x5A ^ 0x3E);
        Blocks.fire.setFireInfo(Blocks.wool, 0x4A ^ 0x54, 0x8F ^ 0xB3);
        Blocks.fire.setFireInfo(Blocks.vine, 0x87 ^ 0x88, 0xD2 ^ 0xB6);
        Blocks.fire.setFireInfo(Blocks.coal_block, 0x28 ^ 0x2D, 0x2F ^ 0x2A);
        Blocks.fire.setFireInfo(Blocks.hay_block, 0x5C ^ 0x60, 0x6D ^ 0x79);
        Blocks.fire.setFireInfo(Blocks.carpet, 0x28 ^ 0x14, 0x41 ^ 0x55);
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
    public boolean canCatchFire(final IBlockAccess blockAccess, final BlockPos blockPos) {
        if (this.getEncouragement(blockAccess.getBlockState(blockPos).getBlock()) > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockFire.AGE);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockFire.I["".length()], "".length(), 0x40 ^ 0x4F);
        FLIP = PropertyBool.create(BlockFire.I[" ".length()]);
        ALT = PropertyBool.create(BlockFire.I["  ".length()]);
        NORTH = PropertyBool.create(BlockFire.I["   ".length()]);
        EAST = PropertyBool.create(BlockFire.I[0x9B ^ 0x9F]);
        SOUTH = PropertyBool.create(BlockFire.I[0x62 ^ 0x67]);
        WEST = PropertyBool.create(BlockFire.I[0xA8 ^ 0xAE]);
        UPPER = PropertyInteger.create(BlockFire.I[0xA1 ^ 0xA6], "".length(), "  ".length());
    }
    
    @Override
    public boolean isCollidable() {
        return "".length() != 0;
    }
}
