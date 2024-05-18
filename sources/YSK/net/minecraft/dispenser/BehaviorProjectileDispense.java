package net.minecraft.dispenser;

import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem
{
    @Override
    protected void playDispenseSound(final IBlockSource blockSource) {
        blockSource.getWorld().playAuxSFX(257 + 912 - 644 + 477, blockSource.getBlockPos(), "".length());
    }
    
    protected float func_82500_b() {
        return 1.1f;
    }
    
    public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
        final World world = blockSource.getWorld();
        final IPosition dispensePosition = BlockDispenser.getDispensePosition(blockSource);
        final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
        final IProjectile projectileEntity = this.getProjectileEntity(world, dispensePosition);
        projectileEntity.setThrowableHeading(facing.getFrontOffsetX(), facing.getFrontOffsetY() + 0.1f, facing.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
        world.spawnEntityInWorld((Entity)projectileEntity);
        itemStack.splitStack(" ".length());
        return itemStack;
    }
    
    protected abstract IProjectile getProjectileEntity(final World p0, final IPosition p1);
    
    protected float func_82498_a() {
        return 6.0f;
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
