package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class BlockJukebox extends BlockContainer
{
    public static final PropertyBool HAS_RECORD;
    private static final String[] I;
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityJukebox();
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
        if (!world.isRemote) {
            super.dropBlockAsItemWithChance(world, blockPos, blockState, n, "".length());
        }
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        this.dropRecord(world, blockPos, blockState);
        super.breakBlock(world, blockPos, blockState);
    }
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        final IBlockState defaultState = this.getDefaultState();
        final PropertyBool has_RECORD = BlockJukebox.HAS_RECORD;
        int n2;
        if (n > 0) {
            n2 = " ".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        return defaultState.withProperty((IProperty<Comparable>)has_RECORD, n2 != 0);
    }
    
    @Override
    public int getRenderType() {
        return "   ".length();
    }
    
    static {
        I();
        HAS_RECORD = PropertyBool.create(BlockJukebox.I["".length()]);
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        int n;
        if (blockState.getValue((IProperty<Boolean>)BlockJukebox.HAS_RECORD)) {
            n = " ".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n;
    }
    
    public void insertRecord(final World world, final BlockPos blockPos, final IBlockState blockState, final ItemStack itemStack) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityJukebox) {
                ((TileEntityJukebox)tileEntity).setRecord(new ItemStack(itemStack.getItem(), " ".length(), itemStack.getMetadata()));
                world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockJukebox.HAS_RECORD, (boolean)(" ".length() != 0)), "  ".length());
            }
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u00046<>\u000b\t4 \u0013\u001d", "lWOay");
    }
    
    private void dropRecord(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (!world.isRemote) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityJukebox) {
                final TileEntityJukebox tileEntityJukebox = (TileEntityJukebox)tileEntity;
                final ItemStack record = tileEntityJukebox.getRecord();
                if (record != null) {
                    world.playAuxSFX(128 + 240 - 174 + 811, blockPos, "".length());
                    world.playRecord(blockPos, null);
                    tileEntityJukebox.setRecord(null);
                    final float n = 0.7f;
                    final EntityItem entityItem = new EntityItem(world, blockPos.getX() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), blockPos.getY() + (world.rand.nextFloat() * n + (1.0f - n) * 0.2 + 0.6), blockPos.getZ() + (world.rand.nextFloat() * n + (1.0f - n) * 0.5), record.copy());
                    entityItem.setDefaultPickupDelay();
                    world.spawnEntityInWorld(entityItem);
                }
            }
        }
    }
    
    protected BlockJukebox() {
        super(Material.wood, MapColor.dirtColor);
        this.setDefaultState(this.blockState.getBaseState().withProperty((IProperty<Comparable>)BlockJukebox.HAS_RECORD, (boolean)("".length() != 0)));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public int getComparatorInputOverride(final World world, final BlockPos blockPos) {
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityJukebox) {
            final ItemStack record = ((TileEntityJukebox)tileEntity).getRecord();
            if (record != null) {
                return Item.getIdFromItem(record.getItem()) + " ".length() - Item.getIdFromItem(Items.record_13);
            }
        }
        return "".length();
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
            if (4 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected BlockState createBlockState() {
        final IProperty[] array = new IProperty[" ".length()];
        array["".length()] = BlockJukebox.HAS_RECORD;
        return new BlockState(this, array);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState withProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (withProperty.getValue((IProperty<Boolean>)BlockJukebox.HAS_RECORD)) {
            this.dropRecord(world, blockPos, withProperty);
            withProperty = withProperty.withProperty((IProperty<Comparable>)BlockJukebox.HAS_RECORD, "".length() != 0);
            world.setBlockState(blockPos, withProperty, "  ".length());
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return " ".length() != 0;
    }
    
    public static class TileEntityJukebox extends TileEntity
    {
        private static final String[] I;
        private ItemStack record;
        
        @Override
        public void readFromNBT(final NBTTagCompound nbtTagCompound) {
            super.readFromNBT(nbtTagCompound);
            if (nbtTagCompound.hasKey(TileEntityJukebox.I["".length()], 0x9E ^ 0x94)) {
                this.setRecord(ItemStack.loadItemStackFromNBT(nbtTagCompound.getCompoundTag(TileEntityJukebox.I[" ".length()])));
                "".length();
                if (3 == 1) {
                    throw null;
                }
            }
            else if (nbtTagCompound.getInteger(TileEntityJukebox.I["  ".length()]) > 0) {
                this.setRecord(new ItemStack(Item.getItemById(nbtTagCompound.getInteger(TileEntityJukebox.I["   ".length()])), " ".length(), "".length()));
            }
        }
        
        public void setRecord(final ItemStack record) {
            this.record = record;
            this.markDirty();
        }
        
        @Override
        public void writeToNBT(final NBTTagCompound nbtTagCompound) {
            super.writeToNBT(nbtTagCompound);
            if (this.getRecord() != null) {
                nbtTagCompound.setTag(TileEntityJukebox.I[0x70 ^ 0x74], this.getRecord().writeToNBT(new NBTTagCompound()));
            }
        }
        
        static {
            I();
        }
        
        public ItemStack getRecord() {
            return this.record;
        }
        
        private static void I() {
            (I = new String[0x3D ^ 0x38])["".length()] = I("!44+*\u0017\u0018#!5", "sQWDX");
            TileEntityJukebox.I[" ".length()] = I("\u0003\u00132(\u00035?%\"\u001c", "QvQGq");
            TileEntityJukebox.I["  ".length()] = I("\u001b1\u0016\u001e\u0005-", "ITuqw");
            TileEntityJukebox.I["   ".length()] = I("13.\u0019(\u0007", "cVMvZ");
            TileEntityJukebox.I[0x3D ^ 0x39] = I("!\u0014\u0014?\u0017\u00178\u00035\b", "sqwPe");
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
                if (0 == 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
