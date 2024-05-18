package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.creativetab.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    private static int[] $SWITCH_TABLE$net$minecraft$block$BlockDirt$DirtType;
    
    @Override
    public boolean isFull3D() {
        return " ".length() != 0;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
    
    protected boolean useHoe(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final IBlockState blockState) {
        world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, blockState.getBlock().stepSound.getStepSound(), (blockState.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, blockState.getBlock().stepSound.getFrequency() * 0.8f);
        if (world.isRemote) {
            return " ".length() != 0;
        }
        world.setBlockState(blockPos, blockState);
        itemStack.damageItem(" ".length(), entityPlayer);
        return " ".length() != 0;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$block$BlockDirt$DirtType() {
        final int[] $switch_TABLE$net$minecraft$block$BlockDirt$DirtType = ItemHoe.$SWITCH_TABLE$net$minecraft$block$BlockDirt$DirtType;
        if ($switch_TABLE$net$minecraft$block$BlockDirt$DirtType != null) {
            return $switch_TABLE$net$minecraft$block$BlockDirt$DirtType;
        }
        final int[] $switch_TABLE$net$minecraft$block$BlockDirt$DirtType2 = new int[BlockDirt.DirtType.values().length];
        try {
            $switch_TABLE$net$minecraft$block$BlockDirt$DirtType2[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = "  ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockDirt$DirtType2[BlockDirt.DirtType.DIRT.ordinal()] = " ".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$block$BlockDirt$DirtType2[BlockDirt.DirtType.PODZOL.ordinal()] = "   ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        return ItemHoe.$SWITCH_TABLE$net$minecraft$block$BlockDirt$DirtType = $switch_TABLE$net$minecraft$block$BlockDirt$DirtType2;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final IBlockState blockState = world.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (enumFacing != EnumFacing.DOWN && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
            if (block == Blocks.grass) {
                return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
            }
            if (block == Blocks.dirt) {
                switch ($SWITCH_TABLE$net$minecraft$block$BlockDirt$DirtType()[blockState.getValue(BlockDirt.VARIANT).ordinal()]) {
                    case 1: {
                        return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.farmland.getDefaultState());
                    }
                    case 2: {
                        return this.useHoe(itemStack, entityPlayer, world, blockPos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return "".length() != 0;
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
    
    public ItemHoe(final ToolMaterial theToolMaterial) {
        this.theToolMaterial = theToolMaterial;
        this.maxStackSize = " ".length();
        this.setMaxDamage(theToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
}
