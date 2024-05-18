package net.minecraft.block;

import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public abstract class BlockLeaves extends BlockLeavesBase
{
    public static final PropertyBool CHECK_DECAY;
    private static final String[] I;
    int[] surroundings;
    protected boolean isTransparent;
    protected int iconIndex;
    public static final PropertyBool DECAYABLE;
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote && blockState.getValue((IProperty<Boolean>)BlockLeaves.CHECK_DECAY) && blockState.getValue((IProperty<Boolean>)BlockLeaves.DECAYABLE)) {
            final int n = 0x11 ^ 0x15;
            final int n2 = n + " ".length();
            final int x = blockPos.getX();
            final int y = blockPos.getY();
            final int z = blockPos.getZ();
            final int n3 = 0x99 ^ 0xB9;
            final int n4 = n3 * n3;
            final int n5 = n3 / "  ".length();
            if (this.surroundings == null) {
                this.surroundings = new int[n3 * n3 * n3];
            }
            if (world.isAreaLoaded(new BlockPos(x - n2, y - n2, z - n2), new BlockPos(x + n2, y + n2, z + n2))) {
                final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                int i = -n;
                "".length();
                if (4 == 0) {
                    throw null;
                }
                while (i <= n) {
                    int j = -n;
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                    while (j <= n) {
                        int k = -n;
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        while (k <= n) {
                            final Block block = world.getBlockState(mutableBlockPos.func_181079_c(x + i, y + j, z + k)).getBlock();
                            if (block != Blocks.log && block != Blocks.log2) {
                                if (block.getMaterial() == Material.leaves) {
                                    this.surroundings[(i + n5) * n4 + (j + n5) * n3 + k + n5] = -"  ".length();
                                    "".length();
                                    if (1 <= -1) {
                                        throw null;
                                    }
                                }
                                else {
                                    this.surroundings[(i + n5) * n4 + (j + n5) * n3 + k + n5] = -" ".length();
                                    "".length();
                                    if (-1 == 1) {
                                        throw null;
                                    }
                                }
                            }
                            else {
                                this.surroundings[(i + n5) * n4 + (j + n5) * n3 + k + n5] = "".length();
                            }
                            ++k;
                        }
                        ++j;
                    }
                    ++i;
                }
                int l = " ".length();
                "".length();
                if (1 <= -1) {
                    throw null;
                }
                while (l <= (0xB ^ 0xF)) {
                    int n6 = -n;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    while (n6 <= n) {
                        int n7 = -n;
                        "".length();
                        if (0 == -1) {
                            throw null;
                        }
                        while (n7 <= n) {
                            int n8 = -n;
                            "".length();
                            if (4 <= 0) {
                                throw null;
                            }
                            while (n8 <= n) {
                                if (this.surroundings[(n6 + n5) * n4 + (n7 + n5) * n3 + n8 + n5] == l - " ".length()) {
                                    if (this.surroundings[(n6 + n5 - " ".length()) * n4 + (n7 + n5) * n3 + n8 + n5] == -"  ".length()) {
                                        this.surroundings[(n6 + n5 - " ".length()) * n4 + (n7 + n5) * n3 + n8 + n5] = l;
                                    }
                                    if (this.surroundings[(n6 + n5 + " ".length()) * n4 + (n7 + n5) * n3 + n8 + n5] == -"  ".length()) {
                                        this.surroundings[(n6 + n5 + " ".length()) * n4 + (n7 + n5) * n3 + n8 + n5] = l;
                                    }
                                    if (this.surroundings[(n6 + n5) * n4 + (n7 + n5 - " ".length()) * n3 + n8 + n5] == -"  ".length()) {
                                        this.surroundings[(n6 + n5) * n4 + (n7 + n5 - " ".length()) * n3 + n8 + n5] = l;
                                    }
                                    if (this.surroundings[(n6 + n5) * n4 + (n7 + n5 + " ".length()) * n3 + n8 + n5] == -"  ".length()) {
                                        this.surroundings[(n6 + n5) * n4 + (n7 + n5 + " ".length()) * n3 + n8 + n5] = l;
                                    }
                                    if (this.surroundings[(n6 + n5) * n4 + (n7 + n5) * n3 + (n8 + n5 - " ".length())] == -"  ".length()) {
                                        this.surroundings[(n6 + n5) * n4 + (n7 + n5) * n3 + (n8 + n5 - " ".length())] = l;
                                    }
                                    if (this.surroundings[(n6 + n5) * n4 + (n7 + n5) * n3 + n8 + n5 + " ".length()] == -"  ".length()) {
                                        this.surroundings[(n6 + n5) * n4 + (n7 + n5) * n3 + n8 + n5 + " ".length()] = l;
                                    }
                                }
                                ++n8;
                            }
                            ++n7;
                        }
                        ++n6;
                    }
                    ++l;
                }
            }
            if (this.surroundings[n5 * n4 + n5 * n3 + n5] >= 0) {
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, (boolean)("".length() != 0)), 0x90 ^ 0x94);
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                this.destroy(world, blockPos);
            }
        }
    }
    
    @Override
    public int quantityDropped(final Random random) {
        int n;
        if (random.nextInt(0xD7 ^ 0xC3) == 0) {
            n = " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        EnumWorldBlockLayer enumWorldBlockLayer;
        if (this.isTransparent) {
            enumWorldBlockLayer = EnumWorldBlockLayer.CUTOUT_MIPPED;
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else {
            enumWorldBlockLayer = EnumWorldBlockLayer.SOLID;
        }
        return enumWorldBlockLayer;
    }
    
    protected int getSaplingDropChance(final IBlockState blockState) {
        return 0xA8 ^ 0xBC;
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerFoliage.getFoliageColor(0.5, 1.0);
    }
    
    @Override
    public boolean isOpaqueCube() {
        int n;
        if (this.fancyGraphics) {
            n = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public boolean isVisuallyOpaque() {
        return "".length() != 0;
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return ColorizerFoliage.getFoliageColorBasic();
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
            if (4 < 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void destroy(final World world, final BlockPos blockToAir) {
        this.dropBlockAsItem(world, blockToAir, world.getBlockState(blockToAir), "".length());
        world.setBlockToAir(blockToAir);
    }
    
    static {
        I();
        DECAYABLE = PropertyBool.create(BlockLeaves.I["".length()]);
        CHECK_DECAY = PropertyBool.create(BlockLeaves.I[" ".length()]);
    }
    
    public void setGraphicsLevel(final boolean b) {
        this.isTransparent = b;
        this.fancyGraphics = b;
        int iconIndex;
        if (b) {
            iconIndex = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            iconIndex = " ".length();
        }
        this.iconIndex = iconIndex;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.getFoliageColorAtPos(blockAccess, blockPos);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int length = " ".length();
        final int n = length + " ".length();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        if (world.isAreaLoaded(new BlockPos(x - n, y - n, z - n), new BlockPos(x + n, y + n, z + n))) {
            int i = -length;
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i <= length) {
                int j = -length;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (j <= length) {
                    int k = -length;
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                    while (k <= length) {
                        final BlockPos add = blockPos.add(i, j, k);
                        final IBlockState blockState2 = world.getBlockState(add);
                        if (blockState2.getBlock().getMaterial() == Material.leaves && !blockState2.getValue((IProperty<Boolean>)BlockLeaves.CHECK_DECAY)) {
                            world.setBlockState(add, blockState2.withProperty((IProperty<Comparable>)BlockLeaves.CHECK_DECAY, (boolean)(" ".length() != 0)), 0x45 ^ 0x41);
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
    }
    
    protected void dropApple(final World world, final BlockPos blockPos, final IBlockState blockState, final int n) {
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    public abstract BlockPlanks.EnumType getWoodType(final int p0);
    
    public BlockLeaves() {
        super(Material.leaves, "".length() != 0);
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2f);
        this.setLightOpacity(" ".length());
        this.setStepSound(BlockLeaves.soundTypeGrass);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0007\u000e7$\n\u0002\t8 ", "ckTEs");
        BlockLeaves.I[" ".length()] = I("\u0013\u001c5\t\u0002/\u00105\t\b\t", "ptPji");
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (world.canLightningStrike(blockPos.up()) && !World.doesBlockHaveSolidTopSurface(world, blockPos.down()) && random.nextInt(0x7D ^ 0x72) == " ".length()) {
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, blockPos.getX() + random.nextFloat(), blockPos.getY() - 0.05, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            int saplingDropChance = this.getSaplingDropChance(blockState);
            if (n2 > 0) {
                saplingDropChance -= "  ".length() << n2;
                if (saplingDropChance < (0x72 ^ 0x78)) {
                    saplingDropChance = (0x73 ^ 0x79);
                }
            }
            if (world.rand.nextInt(saplingDropChance) == 0) {
                Block.spawnAsEntity(world, blockPos, new ItemStack(this.getItemDropped(blockState, world.rand, n2), " ".length(), this.damageDropped(blockState)));
            }
            int n3 = 160 + 54 - 71 + 57;
            if (n2 > 0) {
                n3 -= (0x43 ^ 0x49) << n2;
                if (n3 < (0x5E ^ 0x76)) {
                    n3 = (0x58 ^ 0x70);
                }
            }
            this.dropApple(world, blockPos, blockState, n3);
        }
    }
}
