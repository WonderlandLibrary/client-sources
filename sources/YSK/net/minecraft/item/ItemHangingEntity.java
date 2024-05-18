package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.creativetab.*;

public class ItemHangingEntity extends Item
{
    private final Class<? extends EntityHanging> hangingEntityClass;
    
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing == EnumFacing.DOWN) {
            return "".length() != 0;
        }
        if (enumFacing == EnumFacing.UP) {
            return "".length() != 0;
        }
        final BlockPos offset = blockPos.offset(enumFacing);
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final EntityHanging entity = this.createEntity(world, offset, enumFacing);
        if (entity != null && entity.onValidSurface()) {
            if (!world.isRemote) {
                world.spawnEntityInWorld(entity);
            }
            itemStack.stackSize -= " ".length();
        }
        return " ".length() != 0;
    }
    
    private EntityHanging createEntity(final World world, final BlockPos blockPos, final EnumFacing enumFacing) {
        EntityHanging entityHanging;
        if (this.hangingEntityClass == EntityPainting.class) {
            entityHanging = new EntityPainting(world, blockPos, enumFacing);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (this.hangingEntityClass == EntityItemFrame.class) {
            entityHanging = new EntityItemFrame(world, blockPos, enumFacing);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            entityHanging = null;
        }
        return entityHanging;
    }
    
    public ItemHangingEntity(final Class<? extends EntityHanging> hangingEntityClass) {
        this.hangingEntityClass = hangingEntityClass;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
