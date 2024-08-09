/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.Optional;
import javax.annotation.Nullable;
import mpp.venusfr.events.EventSpawnEntity;
import mpp.venusfr.venusfr;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.server.ServerWorld;

public class EnderCrystalEntity
extends Entity {
    private static final DataParameter<Optional<BlockPos>> BEAM_TARGET = EntityDataManager.createKey(EnderCrystalEntity.class, DataSerializers.OPTIONAL_BLOCK_POS);
    private static final DataParameter<Boolean> SHOW_BOTTOM = EntityDataManager.createKey(EnderCrystalEntity.class, DataSerializers.BOOLEAN);
    public int innerRotation;

    public EnderCrystalEntity(EntityType<? extends EnderCrystalEntity> entityType, World world) {
        super(entityType, world);
        this.preventEntitySpawning = true;
        this.innerRotation = this.rand.nextInt(100000);
        venusfr.getInstance().getEventBus().post(new EventSpawnEntity(this));
    }

    public EnderCrystalEntity(World world, double d, double d2, double d3) {
        this((EntityType<? extends EnderCrystalEntity>)EntityType.END_CRYSTAL, world);
        this.setPosition(d, d2, d3);
        venusfr.getInstance().getEventBus().post(new EventSpawnEntity(this));
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(BEAM_TARGET, Optional.empty());
        this.getDataManager().register(SHOW_BOTTOM, true);
    }

    @Override
    public void tick() {
        ++this.innerRotation;
        if (this.world instanceof ServerWorld) {
            BlockPos blockPos = this.getPosition();
            if (((ServerWorld)this.world).func_241110_C_() != null && this.world.getBlockState(blockPos).isAir()) {
                this.world.setBlockState(blockPos, AbstractFireBlock.getFireForPlacement(this.world, blockPos));
            }
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        if (this.getBeamTarget() != null) {
            compoundNBT.put("BeamTarget", NBTUtil.writeBlockPos(this.getBeamTarget()));
        }
        compoundNBT.putBoolean("ShowBottom", this.shouldShowBottom());
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("BeamTarget", 1)) {
            this.setBeamTarget(NBTUtil.readBlockPos(compoundNBT.getCompound("BeamTarget")));
        }
        if (compoundNBT.contains("ShowBottom", 0)) {
            this.setShowBottom(compoundNBT.getBoolean("ShowBottom"));
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isInvulnerableTo(damageSource)) {
            return true;
        }
        if (damageSource.getTrueSource() instanceof EnderDragonEntity) {
            return true;
        }
        if (!this.removed && !this.world.isRemote) {
            this.remove();
            if (!damageSource.isExplosion()) {
                this.world.createExplosion(null, this.getPosX(), this.getPosY(), this.getPosZ(), 6.0f, Explosion.Mode.DESTROY);
            }
            this.onCrystalDestroyed(damageSource);
        }
        return false;
    }

    @Override
    public void onKillCommand() {
        this.onCrystalDestroyed(DamageSource.GENERIC);
        super.onKillCommand();
    }

    private void onCrystalDestroyed(DamageSource damageSource) {
        DragonFightManager dragonFightManager;
        if (this.world instanceof ServerWorld && (dragonFightManager = ((ServerWorld)this.world).func_241110_C_()) != null) {
            dragonFightManager.onCrystalDestroyed(this, damageSource);
        }
    }

    public void setBeamTarget(@Nullable BlockPos blockPos) {
        this.getDataManager().set(BEAM_TARGET, Optional.ofNullable(blockPos));
    }

    @Nullable
    public BlockPos getBeamTarget() {
        return this.getDataManager().get(BEAM_TARGET).orElse(null);
    }

    public void setShowBottom(boolean bl) {
        this.getDataManager().set(SHOW_BOTTOM, bl);
    }

    public boolean shouldShowBottom() {
        return this.getDataManager().get(SHOW_BOTTOM);
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        return super.isInRangeToRenderDist(d) || this.getBeamTarget() != null;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this);
    }
}

