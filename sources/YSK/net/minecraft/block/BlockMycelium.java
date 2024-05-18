package net.minecraft.block;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;

public class BlockMycelium extends Block
{
    private static final String[] I;
    public static final PropertyBool SNOWY;
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), random, n);
    }
    
    @Override
    public void randomDisplayTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        super.randomDisplayTick(world, blockPos, blockState, random);
        if (random.nextInt(0x1C ^ 0x16) == 0) {
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, blockPos.getX() + random.nextFloat(), blockPos.getY() + 1.1f, blockPos.getZ() + random.nextFloat(), 0.0, 0.0, 0.0, new int["".length()]);
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0003/\u0000\u001a\n", "pAoms");
    }
    
    protected BlockMycelium() {
        super(Material.grass, MapColor.purpleColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockMycelium.SNOWY, (boolean)("".length() != 0)));
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (!world.isRemote) {
            if (world.getLightFromNeighbors(blockPos.up()) < (0x6F ^ 0x6B) && world.getBlockState(blockPos.up()).getBlock().getLightOpacity() > "  ".length()) {
                world.setBlockState(blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                "".length();
                if (2 <= 0) {
                    throw null;
                }
            }
            else if (world.getLightFromNeighbors(blockPos.up()) >= (0xCA ^ 0xC3)) {
                int i = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (i < (0x39 ^ 0x3D)) {
                    final BlockPos add = blockPos.add(random.nextInt("   ".length()) - " ".length(), random.nextInt(0x35 ^ 0x30) - "   ".length(), random.nextInt("   ".length()) - " ".length());
                    final IBlockState blockState2 = world.getBlockState(add);
                    final Block block = world.getBlockState(add.up()).getBlock();
                    if (blockState2.getBlock() == Blocks.dirt && blockState2.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && world.getLightFromNeighbors(add.up()) >= (0xB ^ 0xF) && block.getLightOpacity() <= "  ".length()) {
                        world.setBlockState(add, this.getDefaultState());
                    }
                    ++i;
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
            if (1 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockMycelium.SNOWY;
        return new BlockState(this, array);
    }
    
    @Override
    public IBlockState getActualState(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        final Block block = blockAccess.getBlockState(blockPos.up()).getBlock();
        final PropertyBool snowy = BlockMycelium.SNOWY;
        int n;
        if (block != Blocks.snow && block != Blocks.snow_layer) {
            n = "".length();
            "".length();
            if (0 == 2) {
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
        SNOWY = PropertyBool.create(BlockMycelium.I["".length()]);
    }
}
