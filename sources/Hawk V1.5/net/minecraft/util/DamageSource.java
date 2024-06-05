package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.world.Explosion;

public class DamageSource {
   private boolean explosion;
   public static DamageSource fall = (new DamageSource("fall")).setDamageBypassesArmor();
   public static DamageSource magic = (new DamageSource("magic")).setDamageBypassesArmor().setMagicDamage();
   private boolean isDamageAllowedInCreativeMode;
   private boolean projectile;
   public static DamageSource anvil = new DamageSource("anvil");
   public static DamageSource starve = (new DamageSource("starve")).setDamageBypassesArmor().setDamageIsAbsolute();
   private float hungerDamage = 0.3F;
   public static DamageSource onFire = (new DamageSource("onFire")).setDamageBypassesArmor().setFireDamage();
   private boolean fireDamage;
   private boolean isUnblockable;
   public static DamageSource drown = (new DamageSource("drown")).setDamageBypassesArmor();
   public static DamageSource inFire = (new DamageSource("inFire")).setFireDamage();
   private boolean magicDamage;
   private boolean damageIsAbsolute;
   public static DamageSource generic = (new DamageSource("generic")).setDamageBypassesArmor();
   public static DamageSource field_180137_b = new DamageSource("lightningBolt");
   public static DamageSource inWall = (new DamageSource("inWall")).setDamageBypassesArmor();
   public static DamageSource wither = (new DamageSource("wither")).setDamageBypassesArmor();
   public String damageType;
   public static DamageSource lava = (new DamageSource("lava")).setFireDamage();
   private static final String __OBFID = "CL_00001521";
   public static DamageSource cactus = new DamageSource("cactus");
   private boolean difficultyScaled;
   public static DamageSource fallingBlock = new DamageSource("fallingBlock");
   public static DamageSource outOfWorld = (new DamageSource("outOfWorld")).setDamageBypassesArmor().setDamageAllowedInCreativeMode();

   public float getHungerDamage() {
      return this.hungerDamage;
   }

   public static DamageSource causeFireballDamage(EntityFireball var0, Entity var1) {
      return var1 == null ? (new EntityDamageSourceIndirect("onFire", var0, var0)).setFireDamage().setProjectile() : (new EntityDamageSourceIndirect("fireball", var0, var1)).setFireDamage().setProjectile();
   }

   protected DamageSource setDamageBypassesArmor() {
      this.isUnblockable = true;
      this.hungerDamage = 0.0F;
      return this;
   }

   public DamageSource setExplosion() {
      this.explosion = true;
      return this;
   }

   public boolean func_180136_u() {
      Entity var1 = this.getEntity();
      return var1 instanceof EntityPlayer && ((EntityPlayer)var1).capabilities.isCreativeMode;
   }

   public boolean isMagicDamage() {
      return this.magicDamage;
   }

   public IChatComponent getDeathMessage(EntityLivingBase var1) {
      EntityLivingBase var2 = var1.func_94060_bK();
      String var3 = String.valueOf((new StringBuilder("death.attack.")).append(this.damageType));
      String var4 = String.valueOf((new StringBuilder(String.valueOf(var3))).append(".player"));
      return var2 != null && StatCollector.canTranslate(var4) ? new ChatComponentTranslation(var4, new Object[]{var1.getDisplayName(), var2.getDisplayName()}) : new ChatComponentTranslation(var3, new Object[]{var1.getDisplayName()});
   }

   public DamageSource setDifficultyScaled() {
      this.difficultyScaled = true;
      return this;
   }

   public static DamageSource causeMobDamage(EntityLivingBase var0) {
      return new EntityDamageSource("mob", var0);
   }

   public boolean isProjectile() {
      return this.projectile;
   }

   public boolean isUnblockable() {
      return this.isUnblockable;
   }

   public static DamageSource causePlayerDamage(EntityPlayer var0) {
      return new EntityDamageSource("player", var0);
   }

   public static DamageSource setExplosionSource(Explosion var0) {
      return var0 != null && var0.getExplosivePlacedBy() != null ? (new EntityDamageSource("explosion.player", var0.getExplosivePlacedBy())).setDifficultyScaled().setExplosion() : (new DamageSource("explosion")).setDifficultyScaled().setExplosion();
   }

   public boolean canHarmInCreative() {
      return this.isDamageAllowedInCreativeMode;
   }

   public Entity getSourceOfDamage() {
      return this.getEntity();
   }

   public boolean isDamageAbsolute() {
      return this.damageIsAbsolute;
   }

   public boolean isExplosion() {
      return this.explosion;
   }

   public static DamageSource causeIndirectMagicDamage(Entity var0, Entity var1) {
      return (new EntityDamageSourceIndirect("indirectMagic", var0, var1)).setDamageBypassesArmor().setMagicDamage();
   }

   public static DamageSource causeThornsDamage(Entity var0) {
      return (new EntityDamageSource("thorns", var0)).func_180138_v().setMagicDamage();
   }

   protected DamageSource setFireDamage() {
      this.fireDamage = true;
      return this;
   }

   protected DamageSource(String var1) {
      this.damageType = var1;
   }

   public static DamageSource causeArrowDamage(EntityArrow var0, Entity var1) {
      return (new EntityDamageSourceIndirect("arrow", var0, var1)).setProjectile();
   }

   public boolean isFireDamage() {
      return this.fireDamage;
   }

   public static DamageSource causeThrownDamage(Entity var0, Entity var1) {
      return (new EntityDamageSourceIndirect("thrown", var0, var1)).setProjectile();
   }

   public String getDamageType() {
      return this.damageType;
   }

   public Entity getEntity() {
      return null;
   }

   protected DamageSource setDamageIsAbsolute() {
      this.damageIsAbsolute = true;
      this.hungerDamage = 0.0F;
      return this;
   }

   protected DamageSource setDamageAllowedInCreativeMode() {
      this.isDamageAllowedInCreativeMode = true;
      return this;
   }

   public DamageSource setMagicDamage() {
      this.magicDamage = true;
      return this;
   }

   public DamageSource setProjectile() {
      this.projectile = true;
      return this;
   }

   public boolean isDifficultyScaled() {
      return this.difficultyScaled;
   }
}
