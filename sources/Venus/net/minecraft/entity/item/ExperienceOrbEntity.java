/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.Map;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnExperienceOrbPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ExperienceOrbEntity
extends Entity {
    public int xpColor;
    public int xpOrbAge;
    public int delayBeforeCanPickup;
    private int xpOrbHealth = 5;
    private int xpValue;
    private PlayerEntity closestPlayer;
    private int xpTargetColor;

    public ExperienceOrbEntity(World world, double d, double d2, double d3, int n) {
        this((EntityType<? extends ExperienceOrbEntity>)EntityType.EXPERIENCE_ORB, world);
        this.setPosition(d, d2, d3);
        this.rotationYaw = (float)(this.rand.nextDouble() * 360.0);
        this.setMotion((this.rand.nextDouble() * (double)0.2f - (double)0.1f) * 2.0, this.rand.nextDouble() * 0.2 * 2.0, (this.rand.nextDouble() * (double)0.2f - (double)0.1f) * 2.0);
        this.xpValue = n;
    }

    public ExperienceOrbEntity(EntityType<? extends ExperienceOrbEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        Vector3d vector3d;
        double d;
        super.tick();
        if (this.delayBeforeCanPickup > 0) {
            --this.delayBeforeCanPickup;
        }
        this.prevPosX = this.getPosX();
        this.prevPosY = this.getPosY();
        this.prevPosZ = this.getPosZ();
        if (this.areEyesInFluid(FluidTags.WATER)) {
            this.applyFloatMotion();
        } else if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0, -0.03, 0.0));
        }
        if (this.world.getFluidState(this.getPosition()).isTagged(FluidTags.LAVA)) {
            this.setMotion((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f, 0.2f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f);
            this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
        }
        if (!this.world.hasNoCollisions(this.getBoundingBox())) {
            this.pushOutOfBlocks(this.getPosX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getPosZ());
        }
        double d2 = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.closestPlayer == null || this.closestPlayer.getDistanceSq(this) > 64.0) {
                this.closestPlayer = this.world.getClosestPlayer(this, 8.0);
            }
            this.xpTargetColor = this.xpColor;
        }
        if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
            this.closestPlayer = null;
        }
        if (this.closestPlayer != null && (d = (vector3d = new Vector3d(this.closestPlayer.getPosX() - this.getPosX(), this.closestPlayer.getPosY() + (double)this.closestPlayer.getEyeHeight() / 2.0 - this.getPosY(), this.closestPlayer.getPosZ() - this.getPosZ())).lengthSquared()) < 64.0) {
            double d3 = 1.0 - Math.sqrt(d) / 8.0;
            this.setMotion(this.getMotion().add(vector3d.normalize().scale(d3 * d3 * 0.1)));
        }
        this.move(MoverType.SELF, this.getMotion());
        float f = 0.98f;
        if (this.onGround) {
            f = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 1.0, this.getPosZ())).getBlock().getSlipperiness() * 0.98f;
        }
        this.setMotion(this.getMotion().mul(f, 0.98, f));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(1.0, -0.9, 1.0));
        }
        ++this.xpColor;
        ++this.xpOrbAge;
        if (this.xpOrbAge >= 6000) {
            this.remove();
        }
    }

    private void applyFloatMotion() {
        Vector3d vector3d = this.getMotion();
        this.setMotion(vector3d.x * (double)0.99f, Math.min(vector3d.y + (double)5.0E-4f, (double)0.06f), vector3d.z * (double)0.99f);
    }

    @Override
    protected void doWaterSplashEffect() {
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        this.markVelocityChanged();
        this.xpOrbHealth = (int)((float)this.xpOrbHealth - f);
        if (this.xpOrbHealth <= 0) {
            this.remove();
        }
        return true;
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putShort("Health", (short)this.xpOrbHealth);
        compoundNBT.putShort("Age", (short)this.xpOrbAge);
        compoundNBT.putShort("Value", (short)this.xpValue);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.xpOrbHealth = compoundNBT.getShort("Health");
        this.xpOrbAge = compoundNBT.getShort("Age");
        this.xpValue = compoundNBT.getShort("Value");
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote && this.delayBeforeCanPickup == 0 && playerEntity.xpCooldown == 0) {
            ItemStack itemStack;
            playerEntity.xpCooldown = 2;
            playerEntity.onItemPickup(this, 1);
            Map.Entry<EquipmentSlotType, ItemStack> entry = EnchantmentHelper.getRandomEquippedWithEnchantment(Enchantments.MENDING, playerEntity, ItemStack::isDamaged);
            if (entry != null && !(itemStack = entry.getValue()).isEmpty() && itemStack.isDamaged()) {
                int n = Math.min(this.xpToDurability(this.xpValue), itemStack.getDamage());
                this.xpValue -= this.durabilityToXp(n);
                itemStack.setDamage(itemStack.getDamage() - n);
            }
            if (this.xpValue > 0) {
                playerEntity.giveExperiencePoints(this.xpValue);
            }
            this.remove();
        }
    }

    private int durabilityToXp(int n) {
        return n / 2;
    }

    private int xpToDurability(int n) {
        return n * 2;
    }

    public int getXpValue() {
        return this.xpValue;
    }

    public int getTextureByXP() {
        if (this.xpValue >= 2477) {
            return 1;
        }
        if (this.xpValue >= 1237) {
            return 0;
        }
        if (this.xpValue >= 617) {
            return 1;
        }
        if (this.xpValue >= 307) {
            return 0;
        }
        if (this.xpValue >= 149) {
            return 1;
        }
        if (this.xpValue >= 73) {
            return 0;
        }
        if (this.xpValue >= 37) {
            return 1;
        }
        if (this.xpValue >= 17) {
            return 0;
        }
        if (this.xpValue >= 7) {
            return 1;
        }
        return this.xpValue >= 3 ? 1 : 0;
    }

    public static int getXPSplit(int n) {
        if (n >= 2477) {
            return 0;
        }
        if (n >= 1237) {
            return 0;
        }
        if (n >= 617) {
            return 0;
        }
        if (n >= 307) {
            return 0;
        }
        if (n >= 149) {
            return 0;
        }
        if (n >= 73) {
            return 0;
        }
        if (n >= 37) {
            return 0;
        }
        if (n >= 17) {
            return 0;
        }
        if (n >= 7) {
            return 0;
        }
        return n >= 3 ? 3 : 1;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnExperienceOrbPacket(this);
    }
}

