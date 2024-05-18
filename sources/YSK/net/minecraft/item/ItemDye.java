package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class ItemDye extends Item
{
    private static final String[] I;
    public static final int[] dyeColors;
    
    @Override
    public boolean itemInteractionForEntity(final ItemStack itemStack, final EntityPlayer entityPlayer, final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntitySheep) {
            final EntitySheep entitySheep = (EntitySheep)entityLivingBase;
            final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
            if (!entitySheep.getSheared() && entitySheep.getFleeceColor() != byDyeDamage) {
                entitySheep.setFleeceColor(byDyeDamage);
                itemStack.stackSize -= " ".length();
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public static boolean applyBonemeal(final ItemStack itemStack, final World world, final BlockPos blockPos) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IGrowable) {
            final IGrowable growable = (IGrowable)blockState.getBlock();
            if (growable.canGrow(world, blockPos, blockState, world.isRemote)) {
                if (!world.isRemote) {
                    if (growable.canUseBonemeal(world, world.rand, blockPos, blockState)) {
                        growable.grow(world, world.rand, blockPos, blockState);
                    }
                    itemStack.stackSize -= " ".length();
                }
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    @Override
    public void getSubItems(final Item item, final CreativeTabs creativeTabs, final List<ItemStack> list) {
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < (0x20 ^ 0x30)) {
            list.add(new ItemStack(item, " ".length(), i));
            ++i;
        }
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.canPlayerEdit(offset.offset(enumFacing), enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(itemStack.getMetadata());
        if (byDyeDamage == EnumDyeColor.WHITE) {
            if (applyBonemeal(itemStack, world, offset)) {
                if (!world.isRemote) {
                    world.playAuxSFX(386 + 347 + 164 + 1108, offset, "".length());
                }
                return " ".length() != 0;
            }
        }
        else if (byDyeDamage == EnumDyeColor.BROWN) {
            final IBlockState blockState = world.getBlockState(offset);
            if (blockState.getBlock() == Blocks.log && blockState.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.JUNGLE) {
                if (enumFacing == EnumFacing.DOWN) {
                    return "".length() != 0;
                }
                if (enumFacing == EnumFacing.UP) {
                    return "".length() != 0;
                }
                offset = offset.offset(enumFacing);
                if (world.isAirBlock(offset)) {
                    world.setBlockState(offset, Blocks.cocoa.onBlockPlaced(world, offset, enumFacing, n, n2, n3, "".length(), entityPlayer), "  ".length());
                    if (!entityPlayer.capabilities.isCreativeMode) {
                        itemStack.stackSize -= " ".length();
                    }
                }
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    static {
        I();
        final int[] dyeColors2 = new int[0x82 ^ 0x92];
        dyeColors2["".length()] = 642963 + 1929472 - 635367 + 35951;
        dyeColors2[" ".length()] = 1911622 + 999786 - 2596403 + 11428527;
        dyeColors2["  ".length()] = 2247815 + 350863 - 130979 + 1419687;
        dyeColors2["   ".length()] = 1829773 + 1480578 - 2882665 + 4893044;
        dyeColors2[0xA8 ^ 0xAC] = 990828 + 672649 - 2902 + 776947;
        dyeColors2[0x5A ^ 0x5F] = 3517757 + 5531513 - 4916700 + 3940580;
        dyeColors2[0x3B ^ 0x3D] = 423237 + 2048189 - 1680587 + 1860960;
        dyeColors2[0x28 ^ 0x2F] = 1211124 + 6116146 - 3264594 + 7187927;
        dyeColors2[0xA1 ^ 0xA9] = 2024311 + 1307619 - 2588840 + 3665041;
        dyeColors2[0x1E ^ 0x17] = 882059 + 10806448 + 499397 + 2001048;
        dyeColors2[0x85 ^ 0x8F] = 982270 + 1995807 - 129055 + 1463350;
        dyeColors2[0x1C ^ 0x17] = 2663133 + 9825921 - 10444202 + 12557174;
        dyeColors2[0x68 ^ 0x64] = 3429706 + 752361 - 257418 + 2795306;
        dyeColors2[0xB1 ^ 0xBC] = 3076821 + 403777 - 270586 + 9591217;
        dyeColors2[0xF ^ 0x1] = 6991717 + 9948006 - 6229679 + 4725800;
        dyeColors2[0x15 ^ 0x1A] = 5267454 + 13723748 - 13555134 + 10354252;
        dyeColors = dyeColors2;
    }
    
    public static void spawnBonemealParticles(final World world, final BlockPos blockPos, int n) {
        if (n == 0) {
            n = (0x3D ^ 0x32);
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(world, blockPos);
            int i = "".length();
            "".length();
            if (4 < 2) {
                throw null;
            }
            while (i < n) {
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, blockPos.getX() + ItemDye.itemRand.nextFloat(), blockPos.getY() + ItemDye.itemRand.nextFloat() * block.getBlockBoundsMaxY(), blockPos.getZ() + ItemDye.itemRand.nextFloat(), ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, ItemDye.itemRand.nextGaussian() * 0.02, new int["".length()]);
                ++i;
            }
        }
    }
    
    public ItemDye() {
        this.setHasSubtypes(" ".length() != 0);
        this.setMaxDamage("".length());
        this.setCreativeTab(CreativeTabs.tabMaterials);
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
    public String getUnlocalizedName(final ItemStack itemStack) {
        return String.valueOf(super.getUnlocalizedName()) + ItemDye.I["".length()] + EnumDyeColor.byDyeDamage(itemStack.getMetadata()).getUnlocalizedName();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("d", "JagDl");
    }
}
