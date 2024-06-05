package net.minecraft.client.renderer.entity;

import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class RenderPotion extends RenderSnowball
{
    public RenderPotion(final RenderManager p_i46136_1_, final RenderItem p_i46136_2_) {
        super(p_i46136_1_, Items.potionitem, p_i46136_2_);
    }
    
    public ItemStack func_177085_a(final EntityPotion p_177085_1_) {
        return new ItemStack(this.field_177084_a, 1, p_177085_1_.getPotionDamage());
    }
    
    @Override
    public ItemStack func_177082_d(final Entity p_177082_1_) {
        return this.func_177085_a((EntityPotion)p_177082_1_);
    }
}
