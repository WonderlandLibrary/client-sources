/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.BlockCullEvent;
import me.thekirkayt.event.events.BoundingBoxEvent;
import me.thekirkayt.event.events.InsideBlockRenderEvent;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.PushOutOfBlocksEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

@Module.Mod
public class Freecam
extends Module {
    @Option.Op(min=0.1, max=2.0, increment=0.1)
    private double speed = 1.0;
    private EntityOtherPlayerMP freecamEntity;

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
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            event.setCancelled(true);
        }
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        if (ClientUtils.movementInput().jump) {
            ClientUtils.player().motionY = this.speed;
            event.setY(ClientUtils.player().motionY);
        } else if (ClientUtils.movementInput().sneak) {
            ClientUtils.player().motionY = -this.speed;
            event.setY(ClientUtils.player().motionY);
        } else {
            ClientUtils.player().motionY = 0.0;
            event.setY(0.0);
        }
        ClientUtils.setMoveSpeed(event, this.speed);
    }

    @EventTarget
    private void onBoundingBox(BoundingBoxEvent event) {
        event.setBoundingBox(null);
    }

    @EventTarget
    private void onPushOutOfBlocks(PushOutOfBlocksEvent event) {
        event.setCancelled(true);
    }

    @EventTarget
    private void onInsideBlockRender(InsideBlockRenderEvent event) {
        event.setCancelled(true);
    }

    @EventTarget
    private void onBlockCull(BlockCullEvent event) {
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

