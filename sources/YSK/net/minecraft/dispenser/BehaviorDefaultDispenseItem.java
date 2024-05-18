package net.minecraft.dispenser;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem
{
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private int func_82488_a(final EnumFacing enumFacing) {
        return enumFacing.getFrontOffsetX() + " ".length() + (enumFacing.getFrontOffsetZ() + " ".length()) * "   ".length();
    }
    
    public static void doDispense(final World world, final ItemStack itemStack, final int n, final EnumFacing enumFacing, final IPosition position) {
        final double x = position.getX();
        final double y = position.getY();
        final double z = position.getZ();
        double n2;
        if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
            n2 = y - 0.125;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            n2 = y - 0.15625;
        }
        final EntityItem entityItem = new EntityItem(world, x, n2, z, itemStack);
        final double n3 = world.rand.nextDouble() * 0.1 + 0.2;
        entityItem.motionX = enumFacing.getFrontOffsetX() * n3;
        entityItem.motionY = 0.20000000298023224;
        entityItem.motionZ = enumFacing.getFrontOffsetZ() * n3;
        final EntityItem entityItem2 = entityItem;
        entityItem2.motionX += world.rand.nextGaussian() * 0.007499999832361937 * n;
        final EntityItem entityItem3 = entityItem;
        entityItem3.motionY += world.rand.nextGaussian() * 0.007499999832361937 * n;
        final EntityItem entityItem4 = entityItem;
        entityItem4.motionZ += world.rand.nextGaussian() * 0.007499999832361937 * n;
        world.spawnEntityInWorld(entityItem);
    }
    
    protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
        doDispense(blockSource.getWorld(), itemStack.splitStack(" ".length()), 0xBA ^ 0xBC, BlockDispenser.getFacing(blockSource.getBlockMetadata()), BlockDispenser.getDispensePosition(blockSource));
        return itemStack;
    }
    
    protected void spawnDispenseParticles(final IBlockSource blockSource, final EnumFacing enumFacing) {
        blockSource.getWorld().playAuxSFX(729 + 1261 - 135 + 145, blockSource.getBlockPos(), this.func_82488_a(enumFacing));
    }
    
    @Override
    public final ItemStack dispense(final IBlockSource blockSource, final ItemStack itemStack) {
        final ItemStack dispenseStack = this.dispenseStack(blockSource, itemStack);
        this.playDispenseSound(blockSource);
        this.spawnDispenseParticles(blockSource, BlockDispenser.getFacing(blockSource.getBlockMetadata()));
        return dispenseStack;
    }
    
    protected void playDispenseSound(final IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(630 + 844 - 1163 + 689, blockSource.getBlockPos(), "".length());
    }
}
