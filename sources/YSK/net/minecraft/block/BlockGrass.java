package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.item.*;

public class BlockGrass extends Block implements IGrowable
{
    private static final String[] I;
    public static final PropertyBool SNOWY;
    
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected BlockGrass() {
        super(Material.grass);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockGrass.SNOWY, (boolean)("".length() != 0)));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        final PropertyBool snowy = BlockGrass.SNOWY;
        int n;
        if (block != Blocks.snow && block != Blocks.snow_layer) {
            n = "".length();
            "".length();
            if (0 >= 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return blockState.withProperty((IProperty<Comparable>)snowy, n != 0);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return "".length();
    }
    
    static {
        I();
        SNOWY = PropertyBool.create(BlockGrass.I["".length()]);
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        final BlockPos up = blockPos.up();
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
    Label_0361_Outer:
        while (i < 9 + 63 + 18 + 38) {
            BlockPos add = up;
            int j = "".length();
            while (true) {
                while (j < i / (0xD1 ^ 0xC1)) {
                    add = add.add(random.nextInt("   ".length()) - " ".length(), (random.nextInt("   ".length()) - " ".length()) * random.nextInt("   ".length()) / "  ".length(), random.nextInt("   ".length()) - " ".length());
                    if (world.getBlockState(add.down()).getBlock() == Blocks.grass) {
                        if (world.getBlockState(add).getBlock().isNormalCube()) {
                            "".length();
                            if (3 != 3) {
                                throw null;
                            }
                        }
                        else {
                            ++j;
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                            continue Label_0361_Outer;
                        }
                    }
                    ++i;
                    continue Label_0361_Outer;
                }
                if (world.getBlockState(add).getBlock().blockMaterial != Material.air) {
                    continue;
                }
                if (random.nextInt(0x53 ^ 0x5B) == 0) {
                    final BlockFlower.EnumFlowerType pickRandomFlower = world.getBiomeGenForCoords(add).pickRandomFlower(random, add);
                    final BlockFlower block = pickRandomFlower.getBlockType().getBlock();
                    final IBlockState withProperty = block.getDefaultState().withProperty(block.getTypeProperty(), pickRandomFlower);
                    if (!block.canBlockStay(world, add, withProperty)) {
                        continue;
                    }
                    world.setBlockState(add, withProperty, "   ".length());
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final IBlockState withProperty2 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
                    if (!Blocks.tallgrass.canBlockStay(world, add, withProperty2)) {
                        continue;
                    }
                    world.setBlockState(add, withProperty2, "   ".length());
                    "".length();
                    if (4 == 2) {
                        throw null;
                    }
                    continue;
                }
                break;
            }
        }
    }
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        return this.getBlockColor();
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.up()) < (0xA7 ^ 0xA3) && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > "  ".length()) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState());
                "".length();
                if (-1 == 0) {
                    throw null;
                }
            }
            else if (world.getLightFromNeighbors(blockPos.up()) >= (0x98 ^ 0x91)) {
                int i = "".length();
                "".length();
                if (0 == 3) {
                    throw null;
                }
                while (i < (0x5 ^ 0x1)) {
                    final BlockPos add = blockPos.add(random.nextInt("   ".length()) - " ".length(), random.nextInt(0x14 ^ 0x11) - "   ".length(), random.nextInt("   ".length()) - " ".length());
                    final Block block = world.getBlockState(add.up()).getBlock();
                    final IBlockState blockState2 = world.getBlockState(add);
                    if (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(add.up()) >= (0x14 ^ 0x10) && block.getLightOpacity() <= "  ".length()) {
                        world.setBlockState(add, Blocks.grass.getDefaultState());
                    }
                    ++i;
                }
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockGrass.SNOWY;
        return new BlockState(this, array);
    }
    
    @Override
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5, 1.0);
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        return " ".length() != 0;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return BiomeColorHelper.getGrassColorAtPos(blockAccess, blockPos);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001a\u001f6\u000e\u0012", "iqYyk");
    }
}
