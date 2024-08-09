/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.BedExplosionDamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;

public class DamageSource {
    public static final DamageSource IN_FIRE = new DamageSource("inFire").setDamageBypassesArmor().setFireDamage();
    public static final DamageSource LIGHTNING_BOLT = new DamageSource("lightningBolt");
    public static final DamageSource ON_FIRE = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
    public static final DamageSource LAVA = new DamageSource("lava").setFireDamage();
    public static final DamageSource HOT_FLOOR = new DamageSource("hotFloor").setFireDamage();
    public static final DamageSource IN_WALL = new DamageSource("inWall").setDamageBypassesArmor();
    public static final DamageSource CRAMMING = new DamageSource("cramming").setDamageBypassesArmor();
    public static final DamageSource DROWN = new DamageSource("drown").setDamageBypassesArmor();
    public static final DamageSource STARVE = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
    public static final DamageSource CACTUS = new DamageSource("cactus");
    public static final DamageSource FALL = new DamageSource("fall").setDamageBypassesArmor();
    public static final DamageSource FLY_INTO_WALL = new DamageSource("flyIntoWall").setDamageBypassesArmor();
    public static final DamageSource OUT_OF_WORLD = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
    public static final DamageSource GENERIC = new DamageSource("generic").setDamageBypassesArmor();
    public static final DamageSource MAGIC = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
    public static final DamageSource WITHER = new DamageSource("wither").setDamageBypassesArmor();
    public static final DamageSource ANVIL = new DamageSource("anvil");
    public static final DamageSource FALLING_BLOCK = new DamageSource("fallingBlock");
    public static final DamageSource DRAGON_BREATH = new DamageSource("dragonBreath").setDamageBypassesArmor();
    public static final DamageSource DRYOUT = new DamageSource("dryout");
    public static final DamageSource SWEET_BERRY_BUSH = new DamageSource("sweetBerryBush");
    private boolean isUnblockable;
    private boolean isDamageAllowedInCreativeMode;
    private boolean damageIsAbsolute;
    private float hungerDamage = 0.1f;
    private boolean fireDamage;
    private boolean projectile;
    private boolean difficultyScaled;
    private boolean magicDamage;
    private boolean explosion;
    public final String damageType;

    public static DamageSource causeBeeStingDamage(LivingEntity livingEntity) {
        return new EntityDamageSource("sting", livingEntity);
    }

    public static DamageSource causeMobDamage(LivingEntity livingEntity) {
        return new EntityDamageSource("mob", livingEntity);
    }

    public static DamageSource causeIndirectDamage(Entity entity2, LivingEntity livingEntity) {
        return new IndirectEntityDamageSource("mob", entity2, livingEntity);
    }

    public static DamageSource causePlayerDamage(PlayerEntity playerEntity) {
        return new EntityDamageSource("player", playerEntity);
    }

    public static DamageSource causeArrowDamage(AbstractArrowEntity abstractArrowEntity, @Nullable Entity entity2) {
        return new IndirectEntityDamageSource("arrow", abstractArrowEntity, entity2).setProjectile();
    }

    public static DamageSource causeTridentDamage(Entity entity2, @Nullable Entity entity3) {
        return new IndirectEntityDamageSource("trident", entity2, entity3).setProjectile();
    }

    public static DamageSource func_233548_a_(FireworkRocketEntity fireworkRocketEntity, @Nullable Entity entity2) {
        return new IndirectEntityDamageSource("fireworks", fireworkRocketEntity, entity2).setExplosion();
    }

    public static DamageSource func_233547_a_(AbstractFireballEntity abstractFireballEntity, @Nullable Entity entity2) {
        return entity2 == null ? new IndirectEntityDamageSource("onFire", abstractFireballEntity, abstractFireballEntity).setFireDamage().setProjectile() : new IndirectEntityDamageSource("fireball", abstractFireballEntity, entity2).setFireDamage().setProjectile();
    }

    public static DamageSource func_233549_a_(WitherSkullEntity witherSkullEntity, Entity entity2) {
        return new IndirectEntityDamageSource("witherSkull", witherSkullEntity, entity2).setProjectile();
    }

    public static DamageSource causeThrownDamage(Entity entity2, @Nullable Entity entity3) {
        return new IndirectEntityDamageSource("thrown", entity2, entity3).setProjectile();
    }

    public static DamageSource causeIndirectMagicDamage(Entity entity2, @Nullable Entity entity3) {
        return new IndirectEntityDamageSource("indirectMagic", entity2, entity3).setDamageBypassesArmor().setMagicDamage();
    }

    public static DamageSource causeThornsDamage(Entity entity2) {
        return new EntityDamageSource("thorns", entity2).setIsThornsDamage().setMagicDamage();
    }

    public static DamageSource causeExplosionDamage(@Nullable Explosion explosion) {
        return DamageSource.causeExplosionDamage(explosion != null ? explosion.getExplosivePlacedBy() : null);
    }

    public static DamageSource causeExplosionDamage(@Nullable LivingEntity livingEntity) {
        return livingEntity != null ? new EntityDamageSource("explosion.player", livingEntity).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
    }

    public static DamageSource func_233546_a_() {
        return new BedExplosionDamageSource();
    }

    public String toString() {
        return "DamageSource (" + this.damageType + ")";
    }

    public boolean isProjectile() {
        return this.projectile;
    }

    public DamageSource setProjectile() {
        this.projectile = true;
        return this;
    }

    public boolean isExplosion() {
        return this.explosion;
    }

    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }

    public boolean isUnblockable() {
        return this.isUnblockable;
    }

    public float getHungerDamage() {
        return this.hungerDamage;
    }

    public boolean canHarmInCreative() {
        return this.isDamageAllowedInCreativeMode;
    }

    public boolean isDamageAbsolute() {
        return this.damageIsAbsolute;
    }

    protected DamageSource(String string) {
        this.damageType = string;
    }

    @Nullable
    public Entity getImmediateSource() {
        return this.getTrueSource();
    }

    @Nullable
    public Entity getTrueSource() {
        return null;
    }

    protected DamageSource setDamageBypassesArmor() {
        this.isUnblockable = true;
        this.hungerDamage = 0.0f;
        return this;
    }

    protected DamageSource setDamageAllowedInCreativeMode() {
        this.isDamageAllowedInCreativeMode = true;
        return this;
    }

    protected DamageSource setDamageIsAbsolute() {
        this.damageIsAbsolute = true;
        this.hungerDamage = 0.0f;
        return this;
    }

    protected DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }

    public ITextComponent getDeathMessage(LivingEntity livingEntity) {
        LivingEntity livingEntity2 = livingEntity.getAttackingEntity();
        String string = "death.attack." + this.damageType;
        String string2 = string + ".player";
        return livingEntity2 != null ? new TranslationTextComponent(string2, livingEntity.getDisplayName(), livingEntity2.getDisplayName()) : new TranslationTextComponent(string, livingEntity.getDisplayName());
    }

    public boolean isFireDamage() {
        return this.fireDamage;
    }

    public String getDamageType() {
        return this.damageType;
    }

    public DamageSource setDifficultyScaled() {
        this.difficultyScaled = true;
        return this;
    }

    public boolean isDifficultyScaled() {
        return this.difficultyScaled;
    }

    public boolean isMagicDamage() {
        return this.magicDamage;
    }

    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }

    public boolean isCreativePlayer() {
        Entity entity2 = this.getTrueSource();
        return entity2 instanceof PlayerEntity && ((PlayerEntity)entity2).abilities.isCreativeMode;
    }

    @Nullable
    public Vector3d getDamageLocation() {
        return null;
    }
}

