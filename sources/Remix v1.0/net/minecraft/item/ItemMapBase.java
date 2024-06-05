package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.*;

public class ItemMapBase extends Item
{
    @Override
    public boolean isMap() {
        return true;
    }
    
    public Packet createMapDataPacket(final ItemStack p_150911_1_, final World worldIn, final EntityPlayer p_150911_3_) {
        return null;
    }
}
