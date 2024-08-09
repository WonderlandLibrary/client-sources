/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.item.minecart;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MinecartEntity
extends AbstractMinecartEntity {
    public MinecartEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    public MinecartEntity(World world, double d, double d2, double d3) {
        super(EntityType.MINECART, world, d, d2, d3);
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity playerEntity, Hand hand) {
        if (playerEntity.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        }
        if (this.isBeingRidden()) {
            return ActionResultType.PASS;
        }
        if (!this.world.isRemote) {
            return playerEntity.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onActivatorRailPass(int n, int n2, int n3, boolean bl) {
        if (bl) {
            if (this.isBeingRidden()) {
                this.removePassengers();
            }
            if (this.getRollingAmplitude() == 0) {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setDamage(50.0f);
                this.markVelocityChanged();
            }
        }
    }

    @Override
    public AbstractMinecartEntity.Type getMinecartType() {
        return AbstractMinecartEntity.Type.RIDEABLE;
    }
}

