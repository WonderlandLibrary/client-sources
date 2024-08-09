/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.override;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class PlayerControllerOF
extends PlayerController {
    private boolean acting = false;
    private BlockPos lastClickBlockPos = null;
    private Entity lastClickEntity = null;

    public PlayerControllerOF(Minecraft minecraft, ClientPlayNetHandler clientPlayNetHandler) {
        super(minecraft, clientPlayNetHandler);
    }

    @Override
    public boolean clickBlock(BlockPos blockPos, Direction direction) {
        this.acting = true;
        this.lastClickBlockPos = blockPos;
        boolean bl = super.clickBlock(blockPos, direction);
        this.acting = false;
        return bl;
    }

    @Override
    public boolean onPlayerDamageBlock(BlockPos blockPos, Direction direction) {
        this.acting = true;
        this.lastClickBlockPos = blockPos;
        boolean bl = super.onPlayerDamageBlock(blockPos, direction);
        this.acting = false;
        return bl;
    }

    @Override
    public ActionResultType processRightClick(PlayerEntity playerEntity, World world, Hand hand) {
        this.acting = true;
        ActionResultType actionResultType = super.processRightClick(playerEntity, world, hand);
        this.acting = false;
        return actionResultType;
    }

    @Override
    public ActionResultType processRightClickBlock(ClientPlayerEntity clientPlayerEntity, ClientWorld clientWorld, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        this.acting = true;
        this.lastClickBlockPos = blockRayTraceResult.getPos();
        ActionResultType actionResultType = super.processRightClickBlock(clientPlayerEntity, clientWorld, hand, blockRayTraceResult);
        this.acting = false;
        return actionResultType;
    }

    @Override
    public ActionResultType interactWithEntity(PlayerEntity playerEntity, Entity entity2, Hand hand) {
        this.lastClickEntity = entity2;
        return super.interactWithEntity(playerEntity, entity2, hand);
    }

    @Override
    public ActionResultType interactWithEntity(PlayerEntity playerEntity, Entity entity2, EntityRayTraceResult entityRayTraceResult, Hand hand) {
        this.lastClickEntity = entity2;
        return super.interactWithEntity(playerEntity, entity2, entityRayTraceResult, hand);
    }

    public boolean isActing() {
        return this.acting;
    }

    public BlockPos getLastClickBlockPos() {
        return this.lastClickBlockPos;
    }

    public Entity getLastClickEntity() {
        return this.lastClickEntity;
    }
}

