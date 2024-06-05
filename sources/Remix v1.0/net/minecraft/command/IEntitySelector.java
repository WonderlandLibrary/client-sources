package net.minecraft.command;

import com.google.common.base.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;

public final class IEntitySelector
{
    public static final Predicate selectAnything;
    public static final Predicate field_152785_b;
    public static final Predicate selectInventories;
    public static final Predicate field_180132_d;
    
    static {
        selectAnything = (Predicate)new Predicate() {
            public boolean func_180131_a(final Entity p_180131_1_) {
                return p_180131_1_.isEntityAlive();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180131_a((Entity)p_apply_1_);
            }
        };
        field_152785_b = (Predicate)new Predicate() {
            public boolean func_180130_a(final Entity p_180130_1_) {
                return p_180130_1_.isEntityAlive() && p_180130_1_.riddenByEntity == null && p_180130_1_.ridingEntity == null;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180130_a((Entity)p_apply_1_);
            }
        };
        selectInventories = (Predicate)new Predicate() {
            public boolean func_180102_a(final Entity p_180102_1_) {
                return p_180102_1_ instanceof IInventory && p_180102_1_.isEntityAlive();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180102_a((Entity)p_apply_1_);
            }
        };
        field_180132_d = (Predicate)new Predicate() {
            public boolean func_180103_a(final Entity p_180103_1_) {
                return !(p_180103_1_ instanceof EntityPlayer) || !((EntityPlayer)p_180103_1_).func_175149_v();
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_180103_a((Entity)p_apply_1_);
            }
        };
    }
    
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
}
