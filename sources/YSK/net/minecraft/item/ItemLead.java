package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class ItemLead extends Item
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
            if (2 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static boolean attachToFence(final EntityPlayer entityPlayer, final World world, final BlockPos blockPos) {
        EntityLeashKnot entityLeashKnot = EntityLeashKnot.getKnotForPosition(world, blockPos);
        int n = "".length();
        final double n2 = 7.0;
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        final Iterator<EntityLiving> iterator = world.getEntitiesWithinAABB((Class<? extends EntityLiving>)EntityLiving.class, new AxisAlignedBB(x - n2, y - n2, z - n2, x + n2, y + n2, z + n2)).iterator();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityLiving entityLiving = iterator.next();
            if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == entityPlayer) {
                if (entityLeashKnot == null) {
                    entityLeashKnot = EntityLeashKnot.createKnot(world, blockPos);
                }
                entityLiving.setLeashedToEntity(entityLeashKnot, " ".length() != 0);
                n = " ".length();
            }
        }
        return n != 0;
    }
    
    public ItemLead() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!(world.getBlockState(blockPos).getBlock() instanceof BlockFence)) {
            return "".length() != 0;
        }
        if (world.isRemote) {
            return " ".length() != 0;
        }
        attachToFence(entityPlayer, world, blockPos);
        return " ".length() != 0;
    }
}
