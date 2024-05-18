package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;

public class BlockStem extends BlockBush implements IGrowable
{
    public static final PropertyInteger AGE;
    private static final String[] I;
    private final Block crop;
    public static final PropertyDirection FACING;
    
    @Override
    public int getRenderColor(final IBlockState blockState) {
        if (blockState.getBlock() != this) {
            return super.getRenderColor(blockState);
        }
        final int intValue = blockState.getValue((IProperty<Integer>)BlockStem.AGE);
        return intValue * (0x5F ^ 0x7F) << (0xBC ^ 0xAC) | 125 + 124 - 104 + 110 - intValue * (0xF ^ 0x7) << (0x29 ^ 0x21) | intValue * (0x6E ^ 0x6A);
    }
    
    public void growStem(final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockStem.AGE, Math.min(0x94 ^ 0x93, blockState.getValue((IProperty<Integer>)BlockStem.AGE) + MathHelper.getRandomIntegerInRange(world.rand, "  ".length(), 0x2C ^ 0x29))), "  ".length());
    }
    
    @Override
    public IBlockState getActualState(IBlockState blockState, final IBlockAccess blockAccess, final BlockPos blockPos) {
        blockState = blockState.withProperty((IProperty<Comparable>)BlockStem.FACING, EnumFacing.UP);
        final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EnumFacing enumFacing = iterator.next();
            if (blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock() == this.crop) {
                blockState = blockState.withProperty((IProperty<Comparable>)BlockStem.FACING, enumFacing);
                "".length();
                if (3 < 2) {
                    throw null;
                }
                break;
            }
        }
        return blockState;
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        final Item seedItem = this.getSeedItem();
        Item item;
        if (seedItem != null) {
            item = seedItem;
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            item = null;
        }
        return item;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty((IProperty<Comparable>)BlockStem.AGE, n);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        super.dropBlockAsItemWithChance(world, blockPos, blockState, n, n2);
        if (!world.isRemote) {
            final Item seedItem = this.getSeedItem();
            if (seedItem != null) {
                final int intValue = blockState.getValue((IProperty<Integer>)BlockStem.AGE);
                int i = "".length();
                "".length();
                if (3 < -1) {
                    throw null;
                }
                while (i < "   ".length()) {
                    if (world.rand.nextInt(0xAE ^ 0xA1) <= intValue) {
                        Block.spawnAsEntity(world, blockPos, new ItemStack(seedItem));
                    }
                    ++i;
                }
            }
        }
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("-=\u0017", "LZrBs");
        BlockStem.I[" ".length()] = I("\u001e-\u000b\u001d\b\u001f", "xLhtf");
    }
    
    static {
        I();
        AGE = PropertyInteger.create(BlockStem.I["".length()], "".length(), 0x2E ^ 0x29);
        FACING = PropertyDirection.create(BlockStem.I[" ".length()], (Predicate<EnumFacing>)new Predicate<EnumFacing>() {
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
            
            public boolean apply(final Object o) {
                return this.apply((EnumFacing)o);
            }
            
            public boolean apply(final EnumFacing enumFacing) {
                if (enumFacing != EnumFacing.DOWN) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        });
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
    }
    
    @Override
    public void updateTick(final World world, BlockPos offset, IBlockState withProperty, final Random random) {
        super.updateTick(world, offset, withProperty, random);
        if (world.getLightFromNeighbors(offset.up()) >= (0xCF ^ 0xC6) && random.nextInt((int)(25.0f / BlockCrops.getGrowthChance(this, world, offset)) + " ".length()) == 0) {
            final int intValue = withProperty.getValue((IProperty<Integer>)BlockStem.AGE);
            if (intValue < (0xBE ^ 0xB9)) {
                withProperty = withProperty.withProperty((IProperty<Comparable>)BlockStem.AGE, intValue + " ".length());
                world.setBlockState(offset, withProperty, "  ".length());
                "".length();
                if (-1 == 1) {
                    throw null;
                }
            }
            else {
                final Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    if (world.getBlockState(offset.offset(iterator.next())).getBlock() == this.crop) {
                        return;
                    }
                }
                offset = offset.offset(EnumFacing.Plane.HORIZONTAL.random(random));
                final Block block = world.getBlockState(offset.down()).getBlock();
                if (world.getBlockState(offset).getBlock().blockMaterial == Material.air && (block == Blocks.farmland || block == Blocks.dirt || block == Blocks.grass)) {
                    world.setBlockState(offset, this.crop.getDefaultState());
                }
            }
        }
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty["  ".length()];
        array["".length()] = BlockStem.AGE;
        array[" ".length()] = BlockStem.FACING;
        return new BlockState(this, array);
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return null;
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue((IProperty<Integer>)BlockStem.AGE);
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess blockAccess, final BlockPos blockPos, final int n) {
        return this.getRenderColor(blockAccess.getBlockState(blockPos));
    }
    
    protected BlockStem(final Block crop) {
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockStem.AGE, "".length()).withProperty((IProperty<Comparable>)BlockStem.FACING, EnumFacing.UP));
        this.crop = crop;
        this.setTickRandomly(" ".length() != 0);
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, 0.25f, 0.5f + n);
        this.setCreativeTab(null);
    }
    
    @Override
    public boolean canUseBonemeal(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        return " ".length() != 0;
    }
    
    protected Item getSeedItem() {
        Item item;
        if (this.crop == Blocks.pumpkin) {
            item = Items.pumpkin_seeds;
            "".length();
            if (-1 == 0) {
                throw null;
            }
        }
        else if (this.crop == Blocks.melon_block) {
            item = Items.melon_seeds;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            item = null;
        }
        return item;
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected boolean canPlaceBlockOn(final Block block) {
        if (block == Blocks.farmland) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean canGrow(final World world, final BlockPos blockPos, final IBlockState blockState, final boolean b) {
        if (blockState.getValue((IProperty<Integer>)BlockStem.AGE) != (0xC3 ^ 0xC4)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.maxY = (blockAccess.getBlockState(blockPos).getValue((IProperty<Integer>)BlockStem.AGE) * "  ".length() + "  ".length()) / 16.0f;
        final float n = 0.125f;
        this.setBlockBounds(0.5f - n, 0.0f, 0.5f - n, 0.5f + n, (float)this.maxY, 0.5f + n);
    }
    
    @Override
    public void grow(final World world, final Random random, final BlockPos blockPos, final IBlockState blockState) {
        this.growStem(world, blockPos, blockState);
    }
}
