package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class ItemBlock extends Item
{
    private static final String[] I;
    protected final Block block;
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        this.block.getSubBlocks(item, creativeTabs, list);
    }
    
    static {
        I();
    }
    
    public ItemBlock(final Block block) {
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    @Override
    public String getUnlocalizedName() {
        return this.block.getUnlocalizedName();
    }
    
    @Override
    public Item setUnlocalizedName(final String unlocalizedName) {
        return this.setUnlocalizedName(unlocalizedName);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.block.getUnlocalizedName();
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
            if (1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static boolean setTileEntityNBT(final World world, final EntityPlayer entityPlayer, final BlockPos blockPos, final ItemStack itemStack) {
        final MinecraftServer server = MinecraftServer.getServer();
        if (server == null) {
            return "".length() != 0;
        }
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(ItemBlock.I["".length()], 0xA7 ^ 0xAD)) {
            final TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity != null) {
                if (!world.isRemote && tileEntity.func_183000_F() && !server.getConfigurationManager().canSendCommands(entityPlayer.getGameProfile())) {
                    return "".length() != 0;
                }
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                final NBTTagCompound nbtTagCompound2 = (NBTTagCompound)nbtTagCompound.copy();
                tileEntity.writeToNBT(nbtTagCompound);
                nbtTagCompound.merge((NBTTagCompound)itemStack.getTagCompound().getTag(ItemBlock.I[" ".length()]));
                nbtTagCompound.setInteger(ItemBlock.I["  ".length()], blockPos.getX());
                nbtTagCompound.setInteger(ItemBlock.I["   ".length()], blockPos.getY());
                nbtTagCompound.setInteger(ItemBlock.I[0xB ^ 0xF], blockPos.getZ());
                if (!nbtTagCompound.equals(nbtTagCompound2)) {
                    tileEntity.readFromNBT(nbtTagCompound);
                    tileEntity.markDirty();
                    return " ".length() != 0;
                }
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public ItemBlock setUnlocalizedName(final String unlocalizedName) {
        super.setUnlocalizedName(unlocalizedName);
        return this;
    }
    
    private static void I() {
        (I = new String[0x8B ^ 0x8E])["".length()] = I("(\u0007\u000e*\u001d/\u0005\u0015 \u0002\u0013?\u0000.", "jkaIv");
        ItemBlock.I[" ".length()] = I("$\t6%>#\u000b-/!\u001f18!", "feYFU");
        ItemBlock.I["  ".length()] = I("\u001f", "gmMeO");
        ItemBlock.I["   ".length()] = I("\u001c", "ebyCl");
        ItemBlock.I[0x9C ^ 0x98] = I("\f", "vaKJp");
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return this.block.getCreativeTabToDisplayOn();
    }
    
    public boolean canPlaceBlockOnSide(final World world, BlockPos offset, EnumFacing up, final EntityPlayer entityPlayer, final ItemStack itemStack) {
        final Block block = world.getBlockState(offset).getBlock();
        if (block == Blocks.snow_layer) {
            up = EnumFacing.UP;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        return world.canBlockBePlaced(this.block, offset, "".length() != 0, up, null, itemStack);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            offset = offset.offset(enumFacing);
        }
        if (itemStack.stackSize == 0) {
            return "".length() != 0;
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (world.canBlockBePlaced(this.block, offset, "".length() != 0, enumFacing, null, itemStack)) {
            if (world.setBlockState(offset, this.block.onBlockPlaced(world, offset, enumFacing, n, n2, n3, this.getMetadata(itemStack.getMetadata()), entityPlayer), "   ".length())) {
                final IBlockState blockState = world.getBlockState(offset);
                if (blockState.getBlock() == this.block) {
                    setTileEntityNBT(world, entityPlayer, offset, itemStack);
                    this.block.onBlockPlacedBy(world, offset, blockState, entityPlayer, itemStack);
                }
                world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
                itemStack.stackSize -= " ".length();
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
}
