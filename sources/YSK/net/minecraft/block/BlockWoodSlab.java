package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.block.material.*;

public abstract class BlockWoodSlab extends BlockSlab
{
    public static final PropertyEnum<BlockPlanks.EnumType> VARIANT;
    private static final String[] I;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        IBlockState blockState = this.getDefaultState().withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.byMetadata(n & (0x40 ^ 0x47)));
        if (!this.isDouble()) {
            final IBlockState blockState2 = blockState;
            final PropertyEnum<EnumBlockHalf> half = BlockWoodSlab.HALF;
            EnumBlockHalf enumBlockHalf;
            if ((n & (0xB6 ^ 0xBE)) == 0x0) {
                enumBlockHalf = EnumBlockHalf.BOTTOM;
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                enumBlockHalf = EnumBlockHalf.TOP;
            }
            blockState = blockState2.withProperty((IProperty<Comparable>)half, enumBlockHalf);
        }
        return blockState;
    }
    
    @Override
    protected BlockState createBlockState() {
        BlockState blockState;
        if (this.isDouble()) {
            final IProperty[] array;
            blockState = new BlockState(this, array);
            array = new IProperty[" ".length()];
            array["".length()] = BlockWoodSlab.VARIANT;
            "".length();
            if (3 == 0) {
                throw null;
            }
        }
        else {
            final IProperty[] array2;
            blockState = new BlockState(this, array2);
            array2 = new IProperty["  ".length()];
            array2["".length()] = BlockWoodSlab.HALF;
            array2[" ".length()] = BlockWoodSlab.VARIANT;
        }
        return blockState;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockWoodSlab.VARIANT).getMetadata();
    }
    
    @Override
    public IProperty<?> getVariantProperty() {
        return BlockWoodSlab.VARIANT;
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001f\f\u0018.+\u0007\u0019", "imjGJ");
        BlockWoodSlab.I[" ".length()] = I("B", "leNsn");
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    public String getUnlocalizedName(final int n) {
        return String.valueOf(super.getUnlocalizedName()) + BlockWoodSlab.I[" ".length()] + BlockPlanks.EnumType.byMetadata(n).getUnlocalizedName();
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockWoodSlab.VARIANT).func_181070_c();
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        if (item != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
            final BlockPlanks.EnumType[] values;
            final int length = (values = BlockPlanks.EnumType.values()).length;
            int i = "".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
            while (i < length) {
                list.add(new ItemStack(item, " ".length(), values[i].getMetadata()));
                ++i;
            }
        }
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n = "".length() | blockState.getValue(BlockWoodSlab.VARIANT).getMetadata();
        if (!this.isDouble() && blockState.getValue(BlockWoodSlab.HALF) == EnumBlockHalf.TOP) {
            n |= (0x53 ^ 0x5B);
        }
        return n;
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
            if (4 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Item.getItemFromBlock(Blocks.wooden_slab);
    }
    
    @Override
    public Object getVariant(final ItemStack itemStack) {
        return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata() & (0x99 ^ 0x9E));
    }
    
    static {
        I();
        VARIANT = PropertyEnum.create(BlockWoodSlab.I["".length()], BlockPlanks.EnumType.class);
    }
    
    public BlockWoodSlab() {
        super(Material.wood);
        IBlockState blockState = this.blockState.getBaseState();
        if (!this.isDouble()) {
            blockState = blockState.withProperty(BlockWoodSlab.HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(blockState.withProperty(BlockWoodSlab.VARIANT, BlockPlanks.EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
}
