package me.travis.wurstplus.util;

import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.Explosion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.CombatRules;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class Crystal {

    public static Minecraft mc = Minecraft.getMinecraft();
    private boolean playerPlaced;
    private int damage;
    private EntityEnderCrystal entity;

    public Crystal(EntityEnderCrystal entity, boolean playerPlaced) {
        this.entity = entity;
        this.playerPlaced = playerPlaced;
    }

    public EntityEnderCrystal getCrystal() {
        return this.entity;
    }

    public float getDamage(Entity target) {
        return enemyDamage(target);
    }

    private float playerDamage() {
        return calculateDamage(mc.player.posX, mc.player.posY, mc.player.posZ, this.entity);
    }

    private float enemyDamage(Entity target) {
        return calculateDamage(target.posX, target.posY, target.posZ, this.entity);
    }

    static float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0f;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double)doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0 - distancedsize) * blockDensity;
        float damage = (int)((v * v + v) / 2.0 * 7.0 * (double)doubleExplosionSize + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)mc.world, null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }

    public static float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
          EntityPlayer ep = (EntityPlayer) entity;
          DamageSource ds = DamageSource.causeExplosionDamage(explosion);
          damage = CombatRules.getDamageAfterAbsorb(damage, ep.getTotalArmorValue(),
              (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
          int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
          float f = MathHelper.clamp(k, 0.0F, 20.0F);
          damage *= (1.0F - f / 25.0F);
          if (entity.isPotionActive(Potion.getPotionById(11)))
            damage -= damage / 4.0F;
          return Math.max(damage - ep.getAbsorptionAmount(), 0.0F);
        }
        return CombatRules.getDamageAfterAbsorb(damage, entity.getTotalArmorValue(),
            (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
      }

      private static float getDamageMultiplied(float damage) {
        int diff = mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0F : ((diff == 2) ? 1.0F : ((diff == 1) ? 0.5F : 1.5F)));
      }

}