package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.stats.*;

public class ItemEnderEye extends Item
{
    private static final String[] I;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u001d\u0006\n\u0019:)\u001a\u0017\u001a0", "NrxvT");
        ItemEnderEye.I[" ".length()] = I("(/)!\u00007`%*\u0018", "ZNGEo");
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack) || blockState.getBlock() != Blocks.end_portal_frame || blockState.getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
            return "".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        world.setBlockState(blockPos, blockState.withProperty((IProperty<Comparable>)BlockEndPortalFrame.EYE, (boolean)(" ".length() != 0)), "  ".length());
        world.updateComparatorOutputLevel(blockPos, Blocks.end_portal_frame);
        itemStack.stackSize -= " ".length();
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < (0x8 ^ 0x18)) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockPos.getX() + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f, blockPos.getY() + 0.8125f, blockPos.getZ() + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f, 0.0, 0.0, 0.0, new int["".length()]);
            ++i;
        }
        final EnumFacing enumFacing2 = blockState.getValue((IProperty<EnumFacing>)BlockEndPortalFrame.FACING);
        int length = "".length();
        int length2 = "".length();
        int n4 = "".length();
        int n5 = " ".length();
        final EnumFacing rotateY = enumFacing2.rotateY();
        int j = -"  ".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (j <= "  ".length()) {
            final IBlockState blockState2 = world.getBlockState(blockPos.offset(rotateY, j));
            if (blockState2.getBlock() == Blocks.end_portal_frame) {
                if (!blockState2.getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
                    n5 = "".length();
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                else {
                    length2 = j;
                    if (n4 == 0) {
                        length = j;
                        n4 = " ".length();
                    }
                }
            }
            ++j;
        }
        if (n5 != 0 && length2 == length + "  ".length()) {
            final BlockPos offset = blockPos.offset(enumFacing2, 0xAB ^ 0xAF);
            int k = length;
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (k <= length2) {
                final IBlockState blockState3 = world.getBlockState(offset.offset(rotateY, k));
                if (blockState3.getBlock() != Blocks.end_portal_frame || !blockState3.getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
                    n5 = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    break;
                }
                else {
                    ++k;
                }
            }
            int l = length - " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (l <= length2 + " ".length()) {
                final BlockPos offset2 = blockPos.offset(rotateY, l);
                int length3 = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (length3 <= "   ".length()) {
                    final IBlockState blockState4 = world.getBlockState(offset2.offset(enumFacing2, length3));
                    if (blockState4.getBlock() != Blocks.end_portal_frame || !blockState4.getValue((IProperty<Boolean>)BlockEndPortalFrame.EYE)) {
                        n5 = "".length();
                        "".length();
                        if (4 == 1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++length3;
                    }
                }
                l += 4;
            }
            if (n5 != 0) {
                int n6 = length;
                "".length();
                if (true != true) {
                    throw null;
                }
                while (n6 <= length2) {
                    final BlockPos offset3 = blockPos.offset(rotateY, n6);
                    int length4 = " ".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                    while (length4 <= "   ".length()) {
                        world.setBlockState(offset3.offset(enumFacing2, length4), Blocks.end_portal.getDefaultState(), "  ".length());
                        ++length4;
                    }
                    ++n6;
                }
            }
        }
        return " ".length() != 0;
    }
    
    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
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
    
    static {
        I();
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, "".length() != 0);
        if (movingObjectPositionFromPlayer != null && movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && world.getBlockState(movingObjectPositionFromPlayer.getBlockPos()).getBlock() == Blocks.end_portal_frame) {
            return itemStack;
        }
        if (!world.isRemote) {
            final BlockPos strongholdPos = world.getStrongholdPos(ItemEnderEye.I["".length()], new BlockPos(entityPlayer));
            if (strongholdPos != null) {
                final EntityEnderEye entityEnderEye = new EntityEnderEye(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ);
                entityEnderEye.moveTowards(strongholdPos);
                world.spawnEntityInWorld(entityEnderEye);
                world.playSoundAtEntity(entityPlayer, ItemEnderEye.I[" ".length()], 0.5f, 0.4f / (ItemEnderEye.itemRand.nextFloat() * 0.4f + 0.8f));
                world.playAuxSFXAtEntity(null, 91 + 706 - 16 + 221, new BlockPos(entityPlayer), "".length());
                if (!entityPlayer.capabilities.isCreativeMode) {
                    itemStack.stackSize -= " ".length();
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
}
