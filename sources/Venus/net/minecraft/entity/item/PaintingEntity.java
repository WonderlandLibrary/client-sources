/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnPaintingPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class PaintingEntity
extends HangingEntity {
    public PaintingType art;

    public PaintingEntity(EntityType<? extends PaintingEntity> entityType, World world) {
        super((EntityType<? extends HangingEntity>)entityType, world);
    }

    public PaintingEntity(World world, BlockPos blockPos, Direction direction) {
        super(EntityType.PAINTING, world, blockPos);
        PaintingType paintingType;
        ArrayList<PaintingType> arrayList = Lists.newArrayList();
        int n = 0;
        Iterator<Object> iterator2 = Registry.MOTIVE.iterator();
        while (iterator2.hasNext()) {
            this.art = paintingType = (PaintingType)iterator2.next();
            this.updateFacingWithBoundingBox(direction);
            if (!this.onValidSurface()) continue;
            arrayList.add(paintingType);
            int n2 = paintingType.getWidth() * paintingType.getHeight();
            if (n2 <= n) continue;
            n = n2;
        }
        if (!arrayList.isEmpty()) {
            iterator2 = arrayList.iterator();
            while (iterator2.hasNext()) {
                paintingType = (PaintingType)iterator2.next();
                if (paintingType.getWidth() * paintingType.getHeight() >= n) continue;
                iterator2.remove();
            }
            this.art = (PaintingType)arrayList.get(this.rand.nextInt(arrayList.size()));
        }
        this.updateFacingWithBoundingBox(direction);
    }

    public PaintingEntity(World world, BlockPos blockPos, Direction direction, PaintingType paintingType) {
        this(world, blockPos, direction);
        this.art = paintingType;
        this.updateFacingWithBoundingBox(direction);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.putString("Motive", Registry.MOTIVE.getKey(this.art).toString());
        compoundNBT.putByte("Facing", (byte)this.facingDirection.getHorizontalIndex());
        super.writeAdditional(compoundNBT);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        this.art = Registry.MOTIVE.getOrDefault(ResourceLocation.tryCreate(compoundNBT.getString("Motive")));
        this.facingDirection = Direction.byHorizontalIndex(compoundNBT.getByte("Facing"));
        super.readAdditional(compoundNBT);
        this.updateFacingWithBoundingBox(this.facingDirection);
    }

    @Override
    public int getWidthPixels() {
        return this.art == null ? 1 : this.art.getWidth();
    }

    @Override
    public int getHeightPixels() {
        return this.art == null ? 1 : this.art.getHeight();
    }

    @Override
    public void onBroken(@Nullable Entity entity2) {
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0f, 1.0f);
            if (entity2 instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity)entity2;
                if (playerEntity.abilities.isCreativeMode) {
                    return;
                }
            }
            this.entityDropItem(Items.PAINTING);
        }
    }

    @Override
    public void playPlaceSound() {
        this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0f, 1.0f);
    }

    @Override
    public void setLocationAndAngles(double d, double d2, double d3, float f, float f2) {
        this.setPosition(d, d2, d3);
    }

    @Override
    public void setPositionAndRotationDirect(double d, double d2, double d3, float f, float f2, int n, boolean bl) {
        BlockPos blockPos = this.hangingPosition.add(d - this.getPosX(), d2 - this.getPosY(), d3 - this.getPosZ());
        this.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return new SSpawnPaintingPacket(this);
    }
}

