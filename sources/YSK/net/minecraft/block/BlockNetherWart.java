package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.state.*;

public class BlockNetherWart extends BlockBush
{
    public static final PropertyInteger AGE;
    private static final String[] I;
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        if (block == Blocks.soul_sand) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockNetherWart.I["".length()], "".length(), "   ".length());
    }
    
    protected BlockNetherWart() {
        super(Material.plants, MapColor.redColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockNetherWart.AGE, "".length()));
        this.setTickRandomly(" ".length() != 0);
        final float n = 0.5f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockNetherWart.AGE, n);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockNetherWart.AGE);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            int length = " ".length();
            if (blockState.getValue((IProperty<Integer>)BlockNetherWart.AGE) >= "   ".length()) {
                length = "  ".length() + world.rand.nextInt("   ".length());
                if (n2 > 0) {
                    length += world.rand.nextInt(n2 + " ".length());
                }
            }
            int i = "".length();
            "".length();
            if (3 >= 4) {
                throw null;
            }
            while (i < length) {
                Block.spawnAsEntity(world, blockPos, new ItemStack(Items.nether_wart));
                ++i;
            }
        }
    }
    
    @Override
    public boolean canBlockStay(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\n/\r", "kHhmi");
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.nether_wart;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockNetherWart.AGE;
        return new BlockState(this, array);
    }
    
    @Override
    public int quantityDropped(final Random random) {
        return "".length();
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, IBlockState withProperty, final Random random) {
        final int intValue = withProperty.getValue((IProperty<Integer>)BlockNetherWart.AGE);
        if (intValue < "   ".length() && random.nextInt(0x29 ^ 0x23) == 0) {
            withProperty = withProperty.withProperty((IProperty<Comparable>)BlockNetherWart.AGE, intValue + " ".length());
            world.setBlockState(blockPos, withProperty, "  ".length());
        }
        super.updateTick(world, blockPos, withProperty, random);
    }
}
