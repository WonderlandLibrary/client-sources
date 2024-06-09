// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.render;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.BlockCullEvent;
import net.minecraft.client.triton.management.event.events.BoundingBoxEvent;
import net.minecraft.client.triton.management.event.events.InsideBlockRenderEvent;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.PushOutOfBlocksEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Freecam")
public class Freecam extends Module
{
    @Option.Op(min = 0.1, max = 2.0, increment = 0.1)
    private double speed;
    private EntityOtherPlayerMP freecamEntity;
    
    public Freecam() {
        this.speed = 1.0;
    }
    
    @Override
    public void enable() {
        if (ClientUtils.player() == null) {
            return;
        }
        this.freecamEntity = new EntityOtherPlayerMP(ClientUtils.world(), new GameProfile(new UUID(69L, 96L), "Freecam"));
        this.freecamEntity.inventory = ClientUtils.player().inventory;
        this.freecamEntity.inventoryContainer = ClientUtils.player().inventoryContainer;
        this.freecamEntity.setPositionAndRotation(ClientUtils.x(), ClientUtils.y(), ClientUtils.z(), ClientUtils.yaw(), ClientUtils.pitch());
        this.freecamEntity.rotationYawHead = ClientUtils.player().rotationYawHead;
        ClientUtils.world().addEntityToWorld(this.freecamEntity.getEntityId(), this.freecamEntity);
        super.enable();
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        if (ClientUtils.movementInput().jump) {
            event.setY(ClientUtils.player().motionY = this.speed);
        }
        else if (ClientUtils.movementInput().sneak) {
            event.setY(ClientUtils.player().motionY = -this.speed);
        }
        else {
            event.setY(ClientUtils.player().motionY = 0.0);
        }
        ClientUtils.setMoveSpeed(event, this.speed);
    }
    
    @EventTarget
    private void onBoundingBox(final BoundingBoxEvent event) {
        event.setBoundingBox(null);
    }
    
    @EventTarget
    private void onPushOutOfBlocks(final PushOutOfBlocksEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget
    private void onInsideBlockRender(final InsideBlockRenderEvent event) {
        event.setCancelled(true);
    }
    
    @EventTarget
    private void onBlockCull(final BlockCullEvent event) {
        event.setCancelled(true);
    }
    
    @Override
    public void disable() {
        ClientUtils.player().setPositionAndRotation(this.freecamEntity.posX, this.freecamEntity.posY, this.freecamEntity.posZ, this.freecamEntity.rotationYaw, this.freecamEntity.rotationPitch);
        ClientUtils.world().removeEntityFromWorld(this.freecamEntity.getEntityId());
        ClientUtils.mc().renderGlobal.loadRenderers();
        super.disable();
    }
}
