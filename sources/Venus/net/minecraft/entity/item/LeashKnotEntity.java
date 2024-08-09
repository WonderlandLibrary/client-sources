/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class LeashKnotEntity
extends HangingEntity {
    public LeashKnotEntity(EntityType<? extends LeashKnotEntity> entityType, World world) {
        super((EntityType<? extends HangingEntity>)entityType, world);
    }

    public LeashKnotEntity(World world, BlockPos blockPos) {
        super(EntityType.LEASH_KNOT, world, blockPos);
        this.setPosition((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
        float f = 0.125f;
        float f2 = 0.1875f;
        float f3 = 0.25f;
        this.setBoundingBox(new AxisAlignedBB(this.getPosX() - 0.1875, this.getPosY() - 0.25 + 0.125, this.getPosZ() - 0.1875, this.getPosX() + 0.1875, this.getPosY() + 0.25 + 0.125, this.getPosZ() + 0.1875));
        this.forceSpawn = true;
    }

    @Override
    public void setPosition(double d, double d2, double d3) {
        super.setPosition((double)MathHelper.floor(d) + 0.5, (double)MathHelper.floor(d2) + 0.5, (double)MathHelper.floor(d3) + 0.5);
    }

    @Override
    protected void updateBoundingBox() {
        this.setRawPosition((double)this.hangingPosition.getX() + 0.5, (double)this.hangingPosition.getY() + 0.5, (double)this.hangingPosition.getZ() + 0.5);
    }

    @Override
    public void updateFacingWithBoundingBox(Direction direction) {
    }

    @Override
    public int getWidthPixels() {
        return 0;
    }

    @Override
    public int getHeightPixels() {
        return 0;
    }

    @Override
    protected float getEyeHeight(Pose pose, EntitySize entitySize) {
        return -0.0625f;
    }

    @Override
    public boolean isInRangeToRenderDist(double d) {
        return d < 1024.0;
    }

    @Override
    public void onBroken(@Nullable Entity entity2) {
        this.playSound(SoundEvents.ENTITY_LEASH_KNOT_BREAK, 1.0f, 1.0f);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        if (this.world.isRemote) {
            return ActionResultType.SUCCESS;
        }
        boolean bl = false;
        double d = 7.0;
        List<MobEntity> list = this.world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(this.getPosX() - 7.0, this.getPosY() - 7.0, this.getPosZ() - 7.0, this.getPosX() + 7.0, this.getPosY() + 7.0, this.getPosZ() + 7.0));
        for (MobEntity mobEntity : list) {
            if (mobEntity.getLeashHolder() != playerEntity) continue;
            mobEntity.setLeashHolder(this, false);
            bl = true;
        }
        if (!bl) {
            this.remove();
            if (playerEntity.abilities.isCreativeMode) {
                for (MobEntity mobEntity : list) {
                    if (!mobEntity.getLeashed() || mobEntity.getLeashHolder() != this) continue;
                    mobEntity.clearLeashed(true, true);
                }
            }
        }
        return ActionResultType.CONSUME;
    }

    @Override
    public boolean onValidSurface() {
        return this.world.getBlockState(this.hangingPosition).getBlock().isIn(BlockTags.FENCES);
    }

    public static LeashKnotEntity create(World world, BlockPos blockPos) {
        int n = blockPos.getX();
        int n2 = blockPos.getY();
        int n3 = blockPos.getZ();
        for (LeashKnotEntity leashKnotEntity : world.getEntitiesWithinAABB(LeashKnotEntity.class, new AxisAlignedBB((double)n - 1.0, (double)n2 - 1.0, (double)n3 - 1.0, (double)n + 1.0, (double)n2 + 1.0, (double)n3 + 1.0))) {
            if (!leashKnotEntity.getHangingPosition().equals(blockPos)) continue;
            return leashKnotEntity;
        }
        LeashKnotEntity leashKnotEntity = new LeashKnotEntity(world, blockPos);
        world.addEntity(leashKnotEntity);
        leashKnotEntity.playPlaceSound();
        return leashKnotEntity;
    }

    @Override
    public void playPlaceSound() {
        this.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1.0f, 1.0f);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnObjectPacket(this, this.getType(), 0, this.getHangingPosition());
    }

    @Override
    public Vector3d getLeashPosition(float f) {
        return this.func_242282_l(f).add(0.0, 0.2, 0.0);
    }
}

