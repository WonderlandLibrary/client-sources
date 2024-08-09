/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity;

import javax.annotation.Nullable;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public interface ICrossbowUser
extends IRangedAttackMob {
    public void setCharging(boolean var1);

    public void func_230284_a_(LivingEntity var1, ItemStack var2, ProjectileEntity var3, float var4);

    @Nullable
    public LivingEntity getAttackTarget();

    public void func_230283_U__();

    default public void func_234281_b_(LivingEntity livingEntity, float f) {
        Hand hand = ProjectileHelper.getHandWith(livingEntity, Items.CROSSBOW);
        ItemStack itemStack = livingEntity.getHeldItem(hand);
        if (livingEntity.canEquip(Items.CROSSBOW)) {
            CrossbowItem.fireProjectiles(livingEntity.world, livingEntity, hand, itemStack, f, 14 - livingEntity.world.getDifficulty().getId() * 4);
        }
        this.func_230283_U__();
    }

    default public void func_234279_a_(LivingEntity livingEntity, LivingEntity livingEntity2, ProjectileEntity projectileEntity, float f, float f2) {
        double d = livingEntity2.getPosX() - livingEntity.getPosX();
        double d2 = livingEntity2.getPosZ() - livingEntity.getPosZ();
        double d3 = MathHelper.sqrt(d * d + d2 * d2);
        double d4 = livingEntity2.getPosYHeight(0.3333333333333333) - projectileEntity.getPosY() + d3 * (double)0.2f;
        Vector3f vector3f = this.func_234280_a_(livingEntity, new Vector3d(d, d4, d2), f);
        projectileEntity.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), f2, 14 - livingEntity.world.getDifficulty().getId() * 4);
        livingEntity.playSound(SoundEvents.ITEM_CROSSBOW_SHOOT, 1.0f, 1.0f / (livingEntity.getRNG().nextFloat() * 0.4f + 0.8f));
    }

    default public Vector3f func_234280_a_(LivingEntity livingEntity, Vector3d vector3d, float f) {
        Vector3d vector3d2 = vector3d.normalize();
        Vector3d vector3d3 = vector3d2.crossProduct(new Vector3d(0.0, 1.0, 0.0));
        if (vector3d3.lengthSquared() <= 1.0E-7) {
            vector3d3 = vector3d2.crossProduct(livingEntity.getUpVector(1.0f));
        }
        Quaternion quaternion = new Quaternion(new Vector3f(vector3d3), 90.0f, true);
        Vector3f vector3f = new Vector3f(vector3d2);
        vector3f.transform(quaternion);
        Quaternion quaternion2 = new Quaternion(vector3f, f, true);
        Vector3f vector3f2 = new Vector3f(vector3d2);
        vector3f2.transform(quaternion2);
        return vector3f2;
    }
}

