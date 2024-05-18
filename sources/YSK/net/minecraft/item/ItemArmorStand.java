package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;

public class ItemArmorStand extends Item
{
    private static final String[] I;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return "".length() != 0;
        }
        BlockPos offset;
        if (world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos)) {
            offset = blockPos;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            offset = blockPos.offset(enumFacing);
        }
        final BlockPos blockToAir = offset;
        if (!entityPlayer.canPlayerEdit(blockToAir, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final BlockPos up = blockToAir.up();
        int n4;
        if (!world.isAirBlock(blockToAir) && !world.getBlockState(blockToAir).getBlock().isReplaceable(world, blockToAir)) {
            n4 = " ".length();
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        int n6;
        if (!world.isAirBlock(up) && !world.getBlockState(up).getBlock().isReplaceable(world, up)) {
            n6 = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n6 = "".length();
        }
        if ((n5 | n6) != 0x0) {
            return "".length() != 0;
        }
        final double n7 = blockToAir.getX();
        final double n8 = blockToAir.getY();
        final double n9 = blockToAir.getZ();
        if (world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.fromBounds(n7, n8, n9, n7 + 1.0, n8 + 2.0, n9 + 1.0)).size() > 0) {
            return "".length() != 0;
        }
        if (!world.isRemote) {
            world.setBlockToAir(blockToAir);
            world.setBlockToAir(up);
            final EntityArmorStand entityArmorStand = new EntityArmorStand(world, n7 + 0.5, n8, n9 + 0.5);
            entityArmorStand.setLocationAndAngles(n7 + 0.5, n8, n9 + 0.5, MathHelper.floor_float((MathHelper.wrapAngleTo180_float(entityPlayer.rotationYaw - 180.0f) + 22.5f) / 45.0f) * 45.0f, 0.0f);
            this.applyRandomRotations(entityArmorStand, world.rand);
            final NBTTagCompound tagCompound = itemStack.getTagCompound();
            if (tagCompound != null && tagCompound.hasKey(ItemArmorStand.I["".length()], 0xD ^ 0x7)) {
                final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                entityArmorStand.writeToNBTOptional(nbtTagCompound);
                nbtTagCompound.merge(tagCompound.getCompoundTag(ItemArmorStand.I[" ".length()]));
                entityArmorStand.readFromNBT(nbtTagCompound);
            }
            world.spawnEntityInWorld(entityArmorStand);
        }
        itemStack.stackSize -= " ".length();
        return " ".length() != 0;
    }
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0013)\u0005\u001b\u0015/\u0013\u0010\u0015", "VGqra");
        ItemArmorStand.I[" ".length()] = I("\u001f\u0007&\u000b1#=3\u0005", "ZiRbE");
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private void applyRandomRotations(final EntityArmorStand entityArmorStand, final Random random) {
        final Rotations headRotation = entityArmorStand.getHeadRotation();
        entityArmorStand.setHeadRotation(new Rotations(headRotation.getX() + random.nextFloat() * 5.0f, headRotation.getY() + (random.nextFloat() * 20.0f - 10.0f), headRotation.getZ()));
        final Rotations bodyRotation = entityArmorStand.getBodyRotation();
        entityArmorStand.setBodyRotation(new Rotations(bodyRotation.getX(), bodyRotation.getY() + (random.nextFloat() * 10.0f - 5.0f), bodyRotation.getZ()));
    }
    
    public ItemArmorStand() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
