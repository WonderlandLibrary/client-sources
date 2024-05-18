package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockStainedGlassPane extends BlockPane
{
    public static final PropertyEnum<EnumDyeColor> COLOR;
    private static final String[] I;
    
    static {
        I();
        COLOR = PropertyEnum.create(BlockStainedGlassPane.I["".length()], EnumDyeColor.class);
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[0x98 ^ 0x9D];
        array["".length()] = BlockStainedGlassPane.NORTH;
        array[" ".length()] = BlockStainedGlassPane.EAST;
        array["  ".length()] = BlockStainedGlassPane.WEST;
        array["   ".length()] = BlockStainedGlassPane.SOUTH;
        array[0x22 ^ 0x26] = BlockStainedGlassPane.COLOR;
        return new BlockState(this, array);
    }
    
    public BlockStainedGlassPane() {
        super(Material.glass, "".length() != 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockStainedGlassPane.NORTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockStainedGlassPane.EAST, "".length() != 0).withProperty((IProperty<Comparable>)BlockStainedGlassPane.SOUTH, "".length() != 0).withProperty((IProperty<Comparable>)BlockStainedGlassPane.WEST, "".length() != 0).withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.WHITE));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockStainedGlassPane.COLOR).getMapColor();
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockStainedGlassPane.COLOR).getMetadata();
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockStainedGlassPane.COLOR, EnumDyeColor.byMetadata(n));
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("!* \u001b;", "BELtI");
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockStainedGlassPane.COLOR).getMetadata();
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
    
    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        int i = "".length();
        "".length();
        if (3 == 1) {
            throw null;
        }
        while (i < EnumDyeColor.values().length) {
            list.add(new ItemStack(item, " ".length(), i));
            ++i;
        }
    }
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            BlockBeacon.updateColorAsync(world, blockPos);
        }
    }
}
