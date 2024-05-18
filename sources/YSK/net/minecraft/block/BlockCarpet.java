package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class BlockCarpet extends Block
{
    private static final String[] I;
    public static final PropertyEnum<EnumDyeColor> COLOR;
    
    @Override
    public void onNeighborBlockChange(final World world, final BlockPos blockPos, final IBlockState blockState, final Block block) {
        this.checkForDrop(world, blockPos, blockState);
    }
    
    static {
        I();
        COLOR = PropertyEnum.create(BlockCarpet.I["".length()], EnumDyeColor.class);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World world, final BlockPos blockPos) {
        if (super.canPlaceBlockAt(world, blockPos) && this.canBlockStay(world, blockPos)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean checkForDrop(final World world, final BlockPos blockToAir, final IBlockState blockState) {
        if (!this.canBlockStay(world, blockToAir)) {
            this.dropBlockAsItem(world, blockToAir, blockState, "".length());
            world.setBlockToAir(blockToAir);
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public int damageDropped(final IBlockState blockState) {
        return blockState.getValue(BlockCarpet.COLOR).getMetadata();
    }
    
    protected BlockCarpet() {
        super(Material.carpet);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockCarpet.COLOR, EnumDyeColor.WHITE));
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setTickRandomly(" ".length() != 0);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setBlockBoundsFromMeta("".length());
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockCarpet.COLOR;
        return new BlockState(this, array);
    }
    
    @Override
    public void getSubBlocks(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        int i = "".length();
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (i < (0x59 ^ 0x49)) {
            list.add(new ItemStack(item, " ".length(), i));
            ++i;
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (enumFacing == EnumFacing.UP) {
            n = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public MapColor getMapColor(final IBlockState blockState) {
        return blockState.getValue(BlockCarpet.COLOR).getMapColor();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess blockAccess, final BlockPos blockPos) {
        this.setBlockBoundsFromMeta("".length());
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        return blockState.getValue(BlockCarpet.COLOR).getMetadata();
    }
    
    @Override
    public boolean isFullCube() {
        return "".length() != 0;
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBoundsFromMeta("".length());
    }
    
    protected void setBlockBoundsFromMeta(final int n) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, " ".length() * (" ".length() + "".length()) / 16.0f, 1.0f);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("*\u001b9\n\u0010", "ItUeb");
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockCarpet.COLOR, EnumDyeColor.byMetadata(n));
    }
    
    private boolean canBlockStay(final World world, final BlockPos blockPos) {
        int n;
        if (world.isAirBlock(blockPos.down())) {
            n = "".length();
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
}
