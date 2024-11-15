// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.utils;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.mixin.LivingEntityAccessor;
import dev.lvstrng.argon.mixin.OtherClientPlayerEntityAccessor;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("all")
// nobody cares about this lol
public class FakePlayerEntity extends OtherClientPlayerEntity {
    private final List field442;
    public boolean field440;
    public boolean field441;
    boolean field443;
    @Nullable
    private PlayerListEntry field439;

    public FakePlayerEntity(final PlayerEntity player, final String name, final float health, final boolean copyInv, final boolean stare) {
        super(Argon.mc.world, new GameProfile(UUID.randomUUID(), name));
        this.field442 = Lists.newArrayList();
        this.copyPositionAndRotation((Entity) player);
        this.field443 = stare;
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
        this.headYaw = player.headYaw;
        final int n = 0;
        this.prevHeadYaw = this.headYaw;
        this.bodyYaw = player.bodyYaw;
        this.prevBodyYaw = this.bodyYaw;
        this.setInvulnerable(false);
        this.setNoGravity(false);
        createLivingAttributes();
        createPlayerAttributes();
        this.copyPositionAndRotation((Entity) player);
        this.resetPosition();
        this.dataTracker.set(PlayerEntity.PLAYER_MODEL_PARTS, player.getDataTracker().get(PlayerEntity.PLAYER_MODEL_PARTS));
        this.getAttributes().setFrom(player.getAttributes());
        final int n2 = n;
        this.setPose(player.getPose());
        this.capeX = this.getX();
        this.capeY = this.getY();
        this.capeZ = this.getZ();
        Label_0223:
        {
            if (n2 == 0) {
                if (health <= 20.0f) {
                    this.setHealth(health);
                    if (n2 == 0) {
                        break Label_0223;
                    }
                }
                this.setHealth(health);
            }
            this.setAbsorptionAmount(health - 20.0f);
        }
        if (copyInv) {
            this.getInventory().clone(player.getInventory());
        }
        this.createBrainProfile();
    }

    public void method274() {
        this.unsetRemoved();
        Argon.mc.world.addEntity(this);
    }

    public void method275() {
        Argon.mc.world.removeEntity(this.getId(), RemovalReason.DISCARDED);
        this.setRemoved(RemovalReason.DISCARDED);
    }

    @Nullable
    protected PlayerListEntry method276() {
        if (this.field439 == null) {
            this.field439 = Argon.mc.getNetworkHandler().getPlayerListEntry(Argon.mc.player.getUuid());
        }
        return this.field439;
    }

    public void method277() {
        if (this.bodyTrackingIncrements > 0) {
            this.lerpPosAndRotation(this.bodyTrackingIncrements, this.serverX, this.serverY, this.serverZ, this.serverYaw, this.serverPitch);
            --this.bodyTrackingIncrements;
        }
        if (this.headTrackingIncrements > 0) {
            this.method_52539(this.headTrackingIncrements, this.serverHeadYaw);
            --this.headTrackingIncrements;
        }
        if (((OtherClientPlayerEntityAccessor) this).getVelocityLerpDivisor() > 0) {
            this.addVelocity(new Vec3d((((OtherClientPlayerEntityAccessor) this).getClientVelocity().x - this.getVelocity().x) / ((OtherClientPlayerEntityAccessor) this).getVelocityLerpDivisor(), (((OtherClientPlayerEntityAccessor) this).getClientVelocity().y - this.getVelocity().y) / ((OtherClientPlayerEntityAccessor) this).getVelocityLerpDivisor(), (((OtherClientPlayerEntityAccessor) this).getClientVelocity().z - this.getVelocity().z) / ((OtherClientPlayerEntityAccessor) this).getVelocityLerpDivisor()));
            ((OtherClientPlayerEntityAccessor) this).setVelocityLerpDivisor(((OtherClientPlayerEntityAccessor) this).getVelocityLerpDivisor() - 1);
        }
        this.prevStrideDistance = this.strideDistance;
        this.tickHandSwing();
        float n;
        if (this.isOnGround() && !this.isDead()) {
            n = (float) Math.min(0.1, this.getVelocity().horizontalLength());
        } else {
            n = 0.0f;
        }
        this.strideDistance += (n - this.strideDistance) * 0.4f;
        MinecraftClient.getInstance().world.getProfiler().push("push");
        this.tickCramming();
        MinecraftClient.getInstance().world.getProfiler().pop();
    }

    public void method278(final DamageSource damageSource) {
        final int n = 0;
        super.onDeath(damageSource);
        this.refreshPosition();
        final int n2 = n;
        FakePlayerEntity class94 = this;
        Label_0030:
        {
            if (n2 == 0) {
                if (this.isSpectator()) {
                    break Label_0030;
                }
                class94 = this;
            }
            class94.drop(damageSource);
        }
        Label_0099:
        {
            if (damageSource != null) {
                this.setVelocity((double) (-MathHelper.cos((this.getDamageTiltYaw() + this.getYaw()) * 0.017453292f) * 0.1f), 0.10000000149011612, (double) (-MathHelper.sin((this.getDamageTiltYaw() + this.getYaw()) * 0.017453292f) * 0.1f));
                if (n2 == 0) {
                    break Label_0099;
                }
            }
            this.setVelocity(0.0, 0.1, 0.0);
        }
        this.incrementStat(Stats.DEATHS);
        this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
        this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
        this.extinguish();
        this.setOnFire(false);
        this.setLastDeathPos((Optional) Optional.of(GlobalPos.create(MinecraftClient.getInstance().world.getRegistryKey(), this.getBlockPos())));
    }

    public boolean method279(final DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.isDead()) {
            return false;
        }
        if (source.isIn(DamageTypeTags.IS_FIRE) && this.hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
            return false;
        }
        if (this.isSleeping() && !Argon.mc.player.clientWorld.isClient) {
            this.wakeUp();
        }
        this.despawnCounter = 0;
        final float n = amount;
        boolean b = false;
        final Item item = Argon.mc.player.getMainHandStack().getItem();
        float n2;
        if (item instanceof final SwordItem swordItem) {
            n2 = (float) DamageUtil.method269(Argon.mc.player, true);
        } else {
            n2 = amount;
        }
        amount = n2;
        if (amount > 0.0f && this.blockedByShield(source)) {
            this.damageShield(amount);
            amount = 0.0f;
            if (!source.isIn(DamageTypeTags.IS_PROJECTILE)) {
                final Entity attacker = source.getAttacker();
                if (attacker instanceof final LivingEntity livingEntity2) {
                    this.takeShieldHit(livingEntity2);
                }
            }
            b = true;
        }
        if (source.isIn(DamageTypeTags.IS_FREEZING) && this.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)) {
            amount *= 5.0f;
        }
        this.limbAnimator.setSpeed((this.hurtTime == 0) ? 1.5f : 0.0f);
        boolean b2 = true;
        if (this.timeUntilRegen > 10.0f && !source.isIn(DamageTypeTags.BYPASSES_COOLDOWN)) {
            if (amount <= this.lastDamageTaken) {
                return false;
            }
            this.method282(source, amount - this.lastDamageTaken);
            this.lastDamageTaken = amount;
            b2 = false;
        } else {
            this.lastDamageTaken = amount;
            this.timeUntilRegen = 20;
            this.method282(source, amount);
            this.maxHurtTime = 10;
            this.hurtTime = this.maxHurtTime;
        }
        if (source.isIn(DamageTypeTags.DAMAGES_HELMET) && !this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            this.damageHelmet(source, amount);
            amount *= 0.75f;
        }
        final Entity attacker2 = source.getAttacker();
        if (attacker2 != null) {
            if (attacker2 instanceof final LivingEntity livingEntity) {
                if (!source.isIn(DamageTypeTags.NO_ANGER)) {
                    this.setAttacker(livingEntity);
                }
            }
            if (attacker2 instanceof final PlayerEntity attackingPlayer) {
                this.playerHitTimer = 100;
                this.attackingPlayer = attackingPlayer;
            } else if (attacker2 instanceof final WolfEntity wolfEntity) {
                if (wolfEntity.isTamed()) {
                    this.playerHitTimer = 100;
                    final LivingEntity getOwner = wolfEntity.getOwner();
                    if (getOwner instanceof final PlayerEntity playerEntity) {
                        this.attackingPlayer = playerEntity;
                    } else {
                        this.attackingPlayer = null;
                    }
                }
            }
        }
        if (b2) {
            if (b) {
                Argon.mc.player.clientWorld.sendEntityStatus((Entity) this, (byte) 29);
            } else {
                Argon.mc.player.clientWorld.sendEntityDamage((Entity) this, source);
            }
            if (!source.isIn(DamageTypeTags.IS_FALL) && (!b || amount > 0.0f)) {
                this.scheduleVelocityUpdate();
            }
            if (attacker2 != null && !source.isIn(DamageTypeTags.NO_KNOCKBACK)) {
                double n3;
                double n4;
                for (n3 = attacker2.getX() - this.getX(), n4 = attacker2.getZ() - this.getZ(); n3 * n3 + n4 * n4 < 1.0E-4; n3 = (Math.random() - Math.random()) * 0.01, n4 = (Math.random() - Math.random()) * 0.01) {
                }
                this.method283((EnchantmentHelper.getKnockback(Argon.mc.player)) * 0.5f, MathHelper.sin(this.getYaw() * 0.017453292f), -MathHelper.cos(this.getYaw() * 0.017453292f));
                if (!b) {
                    this.tiltScreen(n3, n4);
                }
            }
        }
        if (this.isDead()) {
            if (!this.method281(source)) {
                final SoundEvent getMemories = this.getDeathSound();
                if (b2 && getMemories != null) {
                    this.playSound(getMemories, this.getSoundVolume(), this.getSoundPitch());
                }
                Argon.INSTANCE.getModuleManager().getModuleByClass(FakePlayerEntity.class).setEnabled(false);
                this.dropInventory();
            }
        } else if (b2) {
            this.playHurtSound(source);
        }
        final boolean b3 = !b || amount > 0.0f;
        if (b3) {
            ((LivingEntityAccessor) this).setLastDamageSource(source);
            ((LivingEntityAccessor) this).setLastDamageTime(this.clientWorld.getTime());
        }
        if (attacker2 instanceof final ServerPlayerEntity serverPlayerEntity) {
            Criteria.PLAYER_HURT_ENTITY.trigger(serverPlayerEntity, this, source, n, amount, b);
        }
        return b3;
    }

    public void method280() {
        final int n = 0;
        this.lastHandSwingProgress = this.handSwingProgress;
        final int n2 = n;
        if (this.firstUpdate) {
        }
        this.tickNewAi();
        boolean isOnSoulSpeedBlock;
        boolean shouldDisplaySoulSpeedEffects;
        boolean b2;
        final boolean b = b2 = (shouldDisplaySoulSpeedEffects = (isOnSoulSpeedBlock = (this.getMainHandStack().getItem() instanceof ShieldItem)));
        if (n2 == 0) {
            if (b) {
                TypedActionResult.consume((Object) this.getMainHandStack().use((World) Argon.mc.world, (PlayerEntity) this, Hand.MAIN_HAND).getValue());
            }
            shouldDisplaySoulSpeedEffects = (b2 = (isOnSoulSpeedBlock = (this.getOffHandStack().getItem() instanceof ShieldItem)));
        }
        if (n2 == 0) {
            if (b2) {
                this.getOffHandStack().use((World) Argon.mc.world, (PlayerEntity) this, Hand.OFF_HAND);
            }
            isOnSoulSpeedBlock = (shouldDisplaySoulSpeedEffects = this.shouldDisplaySoulSpeedEffects());
        }
        double n3 = 0.0;
        final int n9;
        Label_0169:
        {
            Label_0165:
            {
                FakePlayerEntity class94 = null;
                Label_0162:
                {
                    if (n2 == 0) {
                        if (shouldDisplaySoulSpeedEffects) {
                            this.displaySoulSpeedEffects();
                        }
                        this.clientWorld.getProfiler().push("livingEntityBaseTick");
                        class94 = this;
                        if (n2 != 0) {
                            break Label_0162;
                        }
                        isOnSoulSpeedBlock = this.isFireImmune();
                    }
                    if (!isOnSoulSpeedBlock) {
                        final int n8;
                        final double n7;
                        final double n6;
                        final double n5;
                        final double n4;
                        n3 = (n4 = (n5 = (n6 = (n7 = (n8 = (clientWorld.isClient ? 1 : 0))))));
                        if (n2 != 0) {
                            break Label_0169;
                        }
                        if (n3 == 0) {
                            break Label_0165;
                        }
                    }
                    class94 = this;
                }
                class94.extinguish();
            }
            int n8;
            n9 = (n8 = (this.isAlive() ? 1 : 0));
        }
        double n20 = 0.0;
        Label_0767:
        {
            if (n2 == 0) {
                if (n3 != 0) {
                    final boolean b3 = this instanceof PlayerEntity;
                    int n8;
                    double n7;
                    double n6;
                    double n5;
                    double n4;
                    double n12;
                    double n11;
                    final double n10 = n11 = (n12 = (n4 = (n5 = (n6 = (n7 = (n8 = (this.clientWorld.isClient ? 1 : 0)))))));
                    boolean contains = false;
                    Label_0336:
                    {
                        if (n2 == 0) {
                            Label_0329:
                            {
                                if (n10 == 0) {
                                    double n14;
                                    final double n13 = n14 = (n11 = (n12 = (n4 = (n5 = (n6 = (n7 = (n8 = (this.isInsideWall() ? 1 : 0))))))));
                                    if (n2 == 0) {
                                        if (n13 != 0) {
                                            this.method279(this.getDamageSources().inWall(), 1.0f);
                                            if (n2 == 0) {
                                                break Label_0329;
                                            }
                                        }
                                        contains = MinecraftClient.getInstance().world.getWorldBorder().contains(this.getBoundingBox());
                                    }
                                    if (n2 != 0) {
                                        break Label_0336;
                                    }
                                    if (n13 == 0) {
                                        /*final double n15 = MinecraftClient.getInstance().world.getWorldBorder().getDistanceInsideBorder((Entity) this) + this.method_48926().getWorldBorder().getSafeZone();
                                        final double n16 = n14 = (n11 = (n12 = (n4 = (n5 = (n6 = (n7 = (n8 = dcmpg(n15, 0.0))))))));
                                        if (n2 != 0) {
                                            break Label_0336;
                                        }
                                        if (n16 < 0) {
                                            final double damagePerBlock = MinecraftClient.getInstance().world.getWorldBorder().getDamagePerBlock();
                                            final double n17 = n14 = (n11 = (n12 = (n4 = (n5 = (n6 = (n7 = (n8 = dcmpl(damagePerBlock, 0.0))))))));
                                            if (n2 != 0) {
                                                break Label_0336;
                                            }
                                            if (n17 > 0) {
                                                this.method279(this.getDamageSources().outsideBorder(), (float) Math.max(1, MathHelper.floor(-n15 * damagePerBlock)));
                                            }
                                        }*/
                                    }
                                }
                            }
                            this.isSubmergedIn(FluidTags.WATER);
                        }
                    }
                    Label_0712:
                    {
                        Label_0705:
                        {
                            double n18 = 0.0;
                            Label_0682:
                            {
                                if (n2 == 0) {
                                    if (n10 != 0) {
                                        n18 = (n12 = (n4 = (n5 = (n6 = (n7 = (n8 = (MinecraftClient.getInstance().world.getBlockState(BlockPos.ofFloored(this.getX(), this.getEyeY(), this.getZ())).isOf(Blocks.BUBBLE_COLUMN) ? 1 : 0)))))));
                                        if (n2 != 0) {
                                            break Label_0682;
                                        }
                                        if (n18 == 0) {
                                            boolean b7;
                                            boolean b6;
                                            boolean b5;
                                            final boolean b4 = b5 = (b6 = (b7 = this.canBreatheInWater()));
                                            boolean invulnerable = false;
                                            Label_0427:
                                            {
                                                if (n2 == 0) {
                                                    if (b4) {
                                                        break Label_0427;
                                                    }
                                                    b6 = (b5 = (b7 = StatusEffectUtil.hasWaterBreathing(this)));
                                                }
                                                if (n2 == 0) {
                                                    if (b5) {
                                                        break Label_0427;
                                                    }
                                                    b6 = b3;
                                                }
                                                if (n2 == 0) {
                                                    if (b6) {
                                                        invulnerable = this.getAbilities().invulnerable;
                                                        if (n2 == 0) {
                                                            if (!invulnerable) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            boolean b9;
                                            int isClient;
                                            final boolean b8 = (isClient = ((b9 = invulnerable) ? 1 : 0)) != 0;
                                            int n19 = 0;
                                            Label_0627:
                                            {
                                                if (n2 == 0) {
                                                    Label_0620:
                                                    {
                                                        if (b8) {
                                                            this.setAir(this.getNextAirUnderwater(this.getAir()));
                                                            n19 = ((b9 = (this.getAir() != 0)) ? 1 : 0);
                                                            if (n2 != 0) {
                                                                break Label_0627;
                                                            }
                                                            if (n19 == -20) {
                                                                this.setAir(0);
                                                                final Vec3d tryRemoveModifier = this.getVelocity();
                                                                int i = 0;
                                                                while (i < 8) {
                                                                    MinecraftClient.getInstance().world.addParticle((ParticleEffect) ParticleTypes.BUBBLE, this.getX() + (this.random.nextDouble() - this.random.nextDouble()), this.getY() + (this.random.nextDouble() - this.random.nextDouble()), this.getZ() + (this.random.nextDouble() - this.random.nextDouble()), tryRemoveModifier.x, tryRemoveModifier.y, tryRemoveModifier.z);
                                                                    ++i;
                                                                    if (n2 != 0) {
                                                                        break Label_0620;
                                                                    }
                                                                    if (n2 != 0) {
                                                                        break;
                                                                    }
                                                                }
                                                                this.method279(this.getDamageSources().drown(), 2.0f);
                                                            }
                                                        }
                                                    }
                                                    b9 = ((isClient = (MinecraftClient.getInstance().world.isClient ? 1 : 0)) != 0);
                                                }
                                            }
                                            Label_0674:
                                            {
                                                Object hasModifierForAttribute = null;
                                                FakePlayerEntity hasAttribute = null;
                                                Label_0649:
                                                {
                                                    if (n2 == 0) {
                                                        if (n19 != 0) {
                                                            break Label_0674;
                                                        }
                                                        hasModifierForAttribute = this;
                                                        hasAttribute = this;
                                                        if (n2 != 0) {
                                                            break Label_0649;
                                                        }
                                                        b9 = this.hasVehicle();
                                                    }
                                                    if (!b9) {
                                                        break Label_0674;
                                                    }
                                                    hasModifierForAttribute = (hasAttribute = (FakePlayerEntity) this.getVehicle());
                                                }
                                                FakePlayerEntity class95 = null;
                                                Label_0671:
                                                {
                                                    if (n2 == 0) {
                                                        if (hasAttribute == null) {
                                                            break Label_0674;
                                                        }
                                                        class95 = this;
                                                        if (n2 != 0) {
                                                            break Label_0671;
                                                        }
                                                        hasModifierForAttribute = this.getVehicle();
                                                    }
                                                    if (!((Entity) hasModifierForAttribute).shouldDismountUnderwater()) {
                                                        break Label_0674;
                                                    }
                                                    class95 = this;
                                                }
                                                class95.stopRiding();
                                            }
                                            if (n2 == 0) {
                                                break Label_0705;
                                            }
                                        }
                                    }
                                    this.getAir();
                                }
                            }
                            if (n2 != 0) {
                                break Label_0712;
                            }
                            if (n18 < this.getMaxAir()) {
                                this.setAir(this.getNextAirOnLand(this.getAir()));
                            }
                        }
                        n8 = (MinecraftClient.getInstance().world.isClient ? 1 : 0);
                    }
                    if (n2 != 0) {
                        break Label_0767;
                    }
                    if (!contains) {
                        final BlockPos getModifierValue = this.getBlockPos();
                        n20 = (n5 = (n6 = (n7 = (n8 = (Objects.equal(((LivingEntityAccessor) this).getLastBlockPos(), getModifierValue) ? 1 : 0)))));
                        if (n2 != 0) {
                            break Label_0767;
                        }
                        if (n20 == 0) {
                            ((LivingEntityAccessor) this).setLastBlockPos(getModifierValue);
                            this.applyMovementEffects(getModifierValue);
                        }
                    }
                }
                this.isAlive();
            }
        }
        double n21 = 0.0;
        Label_0804:
        {
            if (n2 == 0) {
                Label_0800:
                {
                    if (n20 != 0) {
                        FakePlayerEntity class96 = this;
                        if (n2 == 0) {
                            if (!this.isWet()) {
                                final int n8;
                                final double n7;
                                final double n6;
                                n21 = (n6 = (n7 = (n8 = (this.inPowderSnow ? 1 : 0))));
                                if (n2 != 0) {
                                    break Label_0804;
                                }
                                if (n21 == 0) {
                                    break Label_0800;
                                }
                            }
                            class96 = this;
                        }
                        class96.extinguishWithSound();
                    }
                }
                int n8 = this.hurtTime;
            }
        }
        if (n2 == 0) {
            if (n21 > 0) {
                --this.hurtTime;
            }
            final int n8 = this.timeUntilRegen;
        }
        if (n2 == 0) {
            if (n9 > 0) {
                --this.timeUntilRegen;
            }
            this.isDead();
        }
        Label_0902:
        {
            FakePlayerEntity class97 = null;
            Label_0898:
            {
                int shouldUpdatePostDeath = 0;
                Label_0880:
                {
                    if (n2 == 0) {
                        if (n9 != 0) {
                            shouldUpdatePostDeath = (MinecraftClient.getInstance().world.shouldUpdatePostDeath((Entity) this) ? 1 : 0);
                            if (n2 != 0) {
                                break Label_0880;
                            }
                            if (shouldUpdatePostDeath != 0) {
                                this.updatePostDeath();
                            }
                        }
                        class97 = this;
                        if (n2 != 0) {
                            break Label_0898;
                        }
                        final int n8 = this.playerHitTimer;
                    }
                }
                if (shouldUpdatePostDeath > 0) {
                    --this.playerHitTimer;
                    if (n2 == 0) {
                        break Label_0902;
                    }
                }
                class97 = this;
            }
            class97.attackingPlayer = null;
        }
        final LivingEntity getModifierValue = this.getAttacking();
        FakePlayerEntity class98 = null;
        Label_1040:
        {
            Label_0945:
            {
                if (n2 == 0) {
                    if (getModifierValue != null) {
                        final LivingEntity hasModifier = this.getAttacking();
                        if (n2 != 0) {
                            break Label_0945;
                        }
                        if (!hasModifier.isAlive()) {
                            ((LivingEntityAccessor) this).setAttacking(null);
                        }
                    }
                    class98 = this;
                    if (n2 != 0) {
                        break Label_1040;
                    }
                    this.getAttacker();
                }
            }
            Label_0995:
            {
                if (getModifierValue != null) {
                    final int has = this.getAttacker().isAlive() ? 1 : 0;
                    if (n2 == 0) {
                        if (has == 0) {
                            this.setAttacker((LivingEntity) null);
                            if (n2 == 0) {
                                break Label_0995;
                            }
                        }
                        class98 = this;
                        if (n2 != 0) {
                            break Label_1040;
                        }
                        final int n22 = this.age - this.getLastAttackedTime();
                    }
                    if (has > 100) {
                        this.setAttacker((LivingEntity) null);
                    }
                }
            }
            this.tickStatusEffects();
            this.prevLookDirection = this.lookDirection;
            this.prevBodyYaw = this.bodyYaw;
            this.prevHeadYaw = this.headYaw;
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
            class98 = this;
        }
        MinecraftClient.getInstance().world.getProfiler().pop();
    }

    private boolean method281(final DamageSource damageSource) {
        if (damageSource.isOf(DamageTypes.GENERIC)) {
            return false;
        }
        ItemStack copy = null;
        final Hand[] values = Hand.values();
        for (int length = values.length, i = 0; i < length; ++i) {
            final ItemStack getStackInHand = this.getStackInHand(values[i]);
            if (getStackInHand.isOf(Items.TOTEM_OF_UNDYING)) {
                copy = getStackInHand.copy();
                break;
            }
        }
        if (copy != null) {
            this.setHealth(1.0f);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            Argon.mc.player.clientWorld.sendEntityStatus((Entity) this, (byte) 35);
        }
        return copy != null;
    }

    protected void method282(final DamageSource source, float amount) {
        if (!this.isInvulnerableTo(source)) {
            amount = this.applyArmorToDamage(source, amount);
            final float modifyAppliedDamage;
            amount = (modifyAppliedDamage = this.modifyAppliedDamage(source, amount));
            amount = Math.max(amount - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (modifyAppliedDamage - amount));
            final float n = modifyAppliedDamage - amount;
            if (n > 0.0f && n < 3.4028235E37f) {
                final Entity attacker = source.getAttacker();
                if (attacker instanceof final ServerPlayerEntity serverPlayerEntity) {
                    serverPlayerEntity.increaseStat(Stats.DAMAGE_DEALT_ABSORBED, Math.round(n * 10.0f));
                }
            }
            if (amount != 0.0f) {
                this.getDamageTracker().onDamage(source, amount);
                this.setHealth(this.getHealth() - amount);
                this.setAbsorptionAmount(this.getAbsorptionAmount() - amount);
                this.emitGameEvent(GameEvent.ENTITY_DAMAGE);
            }
        }
    }

    public void method283(final double strength, final double x, final double z) {
        if (!this.isAlive() || this.isSleeping()) {
            return;
        }
        this.setVelocity(this.getVelocity().add(x * strength, 0.5, z * strength));
    }

    public void method284(final DamageSource damageSource) {
        this.limbAnimator.setSpeed(1.5f);
        this.timeUntilRegen = 20;
        this.maxHurtTime = 10;
        this.hurtTime = this.maxHurtTime;
        final boolean b = false;
        final SoundEvent getHurtSound = this.getHurtSound(damageSource);
        if (!b) {
            if (getHurtSound != null) {
                this.playSound(getHurtSound, this.getSoundVolume(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            }
            this.method279(this.getDamageSources().generic(), 0.0f);
            ((LivingEntityAccessor) this).setLastDamageSource(damageSource);
            ((LivingEntityAccessor) this).setLastDamageTime(MinecraftClient.getInstance().world.getTime());
        }
    }

    public void method285(final float yaw) {
        super.animateDamage(yaw);
        this.damageTiltYaw = yaw;
    }
}
