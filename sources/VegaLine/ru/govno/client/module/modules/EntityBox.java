/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;

public class EntityBox
extends Module {
    public static EntityBox get;
    public Settings BoxScalling = new Settings("BoxScalling", true, (Module)this);
    public Settings BoxScale;
    public Settings Predict;
    public Settings TicksCount;
    public Settings ExtendedRange;
    public Settings EntitiesReach;
    public Settings BlocksReach;
    private static float boxScale;
    private static float blocksReach;
    private static float entitiesReach;
    private static float ticksOffset;

    public EntityBox() {
        super("EntityBox", 0, Module.Category.COMBAT);
        this.settings.add(this.BoxScalling);
        this.BoxScale = new Settings("BoxScale", 1.2f, 5.0f, 0.75f, this, () -> this.BoxScalling.bValue);
        this.settings.add(this.BoxScale);
        this.Predict = new Settings("Predict", true, (Module)this);
        this.settings.add(this.Predict);
        this.TicksCount = new Settings("TicksCount", 1.0f, 4.0f, 0.25f, this, () -> this.Predict.bValue);
        this.settings.add(this.TicksCount);
        this.ExtendedRange = new Settings("ExtendedRange", true, (Module)this);
        this.settings.add(this.ExtendedRange);
        this.EntitiesReach = new Settings("EntitiesReach", 0.6f, 3.0f, 0.0f, this, () -> this.ExtendedRange.bValue);
        this.settings.add(this.EntitiesReach);
        this.BlocksReach = new Settings("BlocksReach", 0.4f, 2.0f, 0.0f, this, () -> this.ExtendedRange.bValue);
        this.settings.add(this.BlocksReach);
        get = this;
    }

    public static AxisAlignedBB getExtendedHitbox(Vec3d addPos, Entity entityIn, float scale, AxisAlignedBB prevBox) {
        if (mc.isSingleplayer()) {
            return prevBox;
        }
        if (entityIn == null) {
            return null;
        }
        if (prevBox == null) {
            return null;
        }
        if (entityIn instanceof EntityPlayerSP) {
            return entityIn.boundingBox;
        }
        double x = entityIn.posX + addPos.xCoord;
        float w = (float)(prevBox.maxX - prevBox.minX) / 2.0f;
        double y = entityIn.posY + addPos.yCoord;
        double z = entityIn.posZ + addPos.zCoord;
        Vec3d firstPos = new Vec3d(x - (double)(w * scale), y, z - (double)(w * scale));
        float h = (float)(prevBox.maxY - prevBox.minY);
        Vec3d secondPos = new Vec3d(x + (double)(w * scale), y + (double)h, z + (double)(w * scale));
        AxisAlignedBB aabb = new AxisAlignedBB(firstPos, secondPos);
        return aabb == null ? entityIn.boundingBox : aabb;
    }

    @Override
    public void onUpdate() {
        boxScale = EntityBox.get.BoxScalling.bValue ? EntityBox.get.BoxScale.fValue : 1.0f;
        blocksReach = EntityBox.get.ExtendedRange.bValue ? EntityBox.get.BlocksReach.fValue : 1.0f;
        entitiesReach = EntityBox.get.ExtendedRange.bValue ? EntityBox.get.EntitiesReach.fValue : 1.0f;
        ticksOffset = EntityBox.get.Predict.bValue ? EntityBox.get.TicksCount.fValue : 1.0f;
    }

    public static boolean hitboxModState() {
        return EntityBox.get.actived;
    }

    public static float hitboxModSizeBox() {
        return boxScale;
    }

    public static float hitboxModReachBlocks() {
        return blocksReach;
    }

    public static float hitboxModReachEntities() {
        return entitiesReach;
    }

    public static float hitboxModPredictSize() {
        return ticksOffset;
    }

    public static Vec3d hitboxModPredictVec(Entity entityIn, float ticks) {
        return new Vec3d(-(entityIn.prevPosX - entityIn.posX) * (double)ticks, -(entityIn.prevPosY - entityIn.posY) * (double)ticks, -(entityIn.prevPosZ - entityIn.posZ) * (double)ticks);
    }

    public static boolean entityIsCurrentToExtend(Entity entityIn) {
        return entityIn != null && entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayerSP);
    }

    static {
        boxScale = 1.0f;
    }
}

