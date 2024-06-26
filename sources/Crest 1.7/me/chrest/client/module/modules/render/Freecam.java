// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.render;

import me.chrest.event.events.BlockCullEvent;
import me.chrest.event.events.InsideBlockRenderEvent;
import me.chrest.event.events.PushOutOfBlocksEvent;
import net.minecraft.util.AxisAlignedBB;
import me.chrest.event.events.BoundingBoxEvent;
import me.chrest.event.events.MoveEvent;
import me.chrest.event.EventTarget;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.chrest.utils.ClientUtils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

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
