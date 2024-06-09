package us.dev.direkt.util.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.EntityFireball;

/**
 * @author Meckimp
 */
public class EntityUtils {
    private EntityUtils() {}

    public static boolean isPassiveMob(Entity entity) {
        return (entity instanceof EntityPig
                || entity instanceof EntityCow
                || entity instanceof EntitySheep
                || entity instanceof EntityChicken
                || entity instanceof EntitySquid
                || entity instanceof EntityBat
                || entity instanceof EntityVillager
                || entity instanceof EntityOcelot
                || entity instanceof EntityHorse
                || (entity instanceof EntityRabbit && isPeacefulRabbit(entity)));
    }

    public static boolean isNeutralMob(Entity entity) {
        return (entity instanceof EntityWolf && isCalmWolf(entity)
                || entity instanceof EntityIronGolem && isCalmIronGolem(entity)
                || entity instanceof EntityEnderman && isCalmEnderman(entity)
                || entity instanceof EntityPigZombie && isCalmPigman(entity));
    }

    public static boolean isHostileMob(Entity entity) {
        return entity instanceof EntityCreeper
                || entity instanceof EntitySkeleton
                || (entity instanceof EntityZombie && !(entity instanceof EntityPigZombie))
                || entity instanceof EntityBlaze
                || entity instanceof EntitySpider
                || entity instanceof EntityWitch
                || entity instanceof EntitySlime
                || entity instanceof EntitySilverfish
                || entity instanceof EntityGuardian
                || entity instanceof EntityEndermite
                || entity instanceof EntityGhast
                || entity instanceof EntityShulker
                || (entity instanceof EntityWolf && !isCalmWolf(entity))
                || (entity instanceof EntityPigZombie && !isCalmPigman(entity))
                || (entity instanceof EntityEnderman && !isCalmEnderman(entity))
                || (entity instanceof EntityRabbit && !isPeacefulRabbit(entity))
                || (entity instanceof EntityIronGolem && !isCalmIronGolem(entity));
    }

    public static boolean isBossMob(Entity entity) {
        return entity instanceof EntityDragon
                || entity instanceof EntityWither
                || entity instanceof EntityGiantZombie;
    }

    public static boolean isVehicleEntity(Entity entity) {
        return entity instanceof EntityBoat
                || entity instanceof EntityMinecart;
    }

    public static boolean isMiscellaneousEntity(Entity entity) {
        return entity instanceof EntityEnderCrystal
                || entity instanceof EntityFallingBlock
                || entity instanceof EntityFireball;
    }

    public static boolean isCalmWolf(Entity entity) {
        return entity instanceof EntityWolf && !((EntityWolf) entity).isAngry();
    }

    public static boolean isCalmPigman(Entity entity) {
        return entity instanceof EntityPigZombie && !(((EntityPigZombie) entity).rotationPitch != 0 || ((EntityPigZombie) entity).getRevengeTimer() > 0);
    }

    public static boolean isCalmIronGolem(Entity entity) {
        return entity instanceof EntityIronGolem && ((EntityIronGolem) entity).rotationPitch == 0;
    }

    public static boolean isCalmEnderman(Entity entity) {
        return entity instanceof EntityEnderman && !((EntityEnderman) entity).isScreaming();
    }

    public static boolean isPeacefulRabbit(Entity entity) {
        return entity instanceof EntityRabbit && ((EntityRabbit) entity).getRabbitType() != 99;
    }
}
