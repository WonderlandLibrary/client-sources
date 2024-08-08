package net.futureclient.client;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.Entity;

public class WI
{
    private WI() {
        super();
    }
    
    public static boolean B(final Entity entity) {
        return entity instanceof EntityWolf && !((EntityWolf)entity).isAngry();
    }
    
    public static boolean C(final Entity entity) {
        return entity instanceof EntityCreeper || entity instanceof EntityIllusionIllager || entity instanceof EntitySkeleton || (entity instanceof EntityZombie && !(entity instanceof EntityPigZombie)) || entity instanceof EntityBlaze || entity instanceof EntitySpider || entity instanceof EntityWitch || entity instanceof EntitySlime || entity instanceof EntitySilverfish || entity instanceof EntityGuardian || entity instanceof EntityEndermite || entity instanceof EntityGhast || entity instanceof EntityEvoker || entity instanceof EntityShulker || entity instanceof EntityWitherSkeleton || entity instanceof EntityStray || entity instanceof EntityVex || entity instanceof EntityVindicator || (entity instanceof EntityPolarBear && !M(entity)) || (entity instanceof EntityWolf && !B(entity)) || (entity instanceof EntityPigZombie && !c(entity)) || (entity instanceof EntityEnderman && !J(entity)) || (entity instanceof EntityRabbit && !b(entity)) || (entity instanceof EntityIronGolem && !h(entity));
    }
    
    public static boolean J(final Entity entity) {
        return entity instanceof EntityEnderman && !((EntityEnderman)entity).isScreaming();
    }
    
    public static boolean c(final Entity entity) {
        return entity instanceof EntityPigZombie && ((EntityPigZombie)entity).rotationPitch == 0.0f && ((EntityPigZombie)entity).getRevengeTimer() <= 0;
    }
    
    public static boolean b(final Entity entity) {
        return entity instanceof EntityRabbit && ((EntityRabbit)entity).getRabbitType() != 99;
    }
    
    public static boolean H(final Entity entity) {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }
    
    public static boolean e(final Entity entity) {
        return entity instanceof EntityDragon || entity instanceof EntityWither || entity instanceof EntityGiantZombie;
    }
    
    public static boolean i(final Entity entity) {
        return (entity instanceof EntityWolf && B(entity)) || (entity instanceof EntityPolarBear && M(entity)) || (entity instanceof EntityIronGolem && h(entity)) || (entity instanceof EntityEnderman && J(entity)) || (entity instanceof EntityPigZombie && c(entity));
    }
    
    public static boolean g(final Entity entity) {
        return entity instanceof EntityPig || entity instanceof EntityParrot || entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager || entity instanceof EntityOcelot || entity instanceof EntityHorse || entity instanceof EntityLlama || entity instanceof EntityMule || entity instanceof EntityDonkey || entity instanceof EntitySkeletonHorse || entity instanceof EntityZombieHorse || entity instanceof EntitySnowman || (entity instanceof EntityRabbit && b(entity));
    }
    
    public static boolean K(final Entity entity) {
        return entity instanceof EntityEnderCrystal || entity instanceof EntityEvokerFangs || entity instanceof EntityShulkerBullet || entity instanceof EntityFallingBlock || entity instanceof EntityFireball || entity instanceof EntityEnderEye || entity instanceof EntityEnderPearl;
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        final int n = string.length() - 1;
        final int n2 = 9;
        final int n3 = 97;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public static boolean M(final Entity entity) {
        return entity instanceof EntityPolarBear && ((EntityPolarBear)entity).rotationPitch == 0.0f && ((EntityPolarBear)entity).getRevengeTimer() <= 0;
    }
    
    public static boolean h(final Entity entity) {
        return entity instanceof EntityIronGolem && ((EntityIronGolem)entity).rotationPitch == 0.0f;
    }
}
