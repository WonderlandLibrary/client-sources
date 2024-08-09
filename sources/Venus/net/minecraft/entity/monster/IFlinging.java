/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;

public interface IFlinging {
    public int func_230290_eL_();

    public static boolean func_234403_a_(LivingEntity livingEntity, LivingEntity livingEntity2) {
        float f = (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f2 = !livingEntity.isChild() && (int)f > 0 ? f / 2.0f + (float)livingEntity.world.rand.nextInt((int)f) : f;
        boolean bl = livingEntity2.attackEntityFrom(DamageSource.causeMobDamage(livingEntity), f2);
        if (bl) {
            livingEntity.applyEnchantments(livingEntity, livingEntity2);
            if (!livingEntity.isChild()) {
                IFlinging.func_234404_b_(livingEntity, livingEntity2);
            }
        }
        return bl;
    }

    public static void func_234404_b_(LivingEntity livingEntity, LivingEntity livingEntity2) {
        double d;
        double d2 = livingEntity.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        double d3 = d2 - (d = livingEntity2.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
        if (!(d3 <= 0.0)) {
            double d4 = livingEntity2.getPosX() - livingEntity.getPosX();
            double d5 = livingEntity2.getPosZ() - livingEntity.getPosZ();
            float f = livingEntity.world.rand.nextInt(21) - 10;
            double d6 = d3 * (double)(livingEntity.world.rand.nextFloat() * 0.5f + 0.2f);
            Vector3d vector3d = new Vector3d(d4, 0.0, d5).normalize().scale(d6).rotateYaw(f);
            double d7 = d3 * (double)livingEntity.world.rand.nextFloat() * 0.5;
            livingEntity2.addVelocity(vector3d.x, d7, vector3d.z);
            livingEntity2.velocityChanged = true;
        }
    }
}

