package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;

public static class ArmoredMob implements Predicate
{
    private final ItemStack field_96567_c;
    
    public ArmoredMob(final ItemStack p_i1584_1_) {
        this.field_96567_c = p_i1584_1_;
    }
    
    public boolean func_180100_a(final Entity p_180100_1_) {
        if (!p_180100_1_.isEntityAlive()) {
            return false;
        }
        if (!(p_180100_1_ instanceof EntityLivingBase)) {
            return false;
        }
        final EntityLivingBase var2 = (EntityLivingBase)p_180100_1_;
        return var2.getEquipmentInSlot(EntityLiving.getArmorPosition(this.field_96567_c)) == null && ((var2 instanceof EntityLiving) ? ((EntityLiving)var2).canPickUpLoot() : (var2 instanceof EntityArmorStand || var2 instanceof EntityPlayer));
    }
    
    public boolean apply(final Object p_apply_1_) {
        return this.func_180100_a((Entity)p_apply_1_);
    }
}
