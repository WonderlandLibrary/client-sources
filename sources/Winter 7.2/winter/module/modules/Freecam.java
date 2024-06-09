/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;
import winter.event.EventListener;
import winter.event.events.BBEvent;
import winter.event.events.PacketEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.module.modules.Phase;

public class Freecam
extends Module {
    private double oldX;
    private double oldY;
    private double oldZ;
    private float oldYaw;
    private float oldPitch;
    private EntityOtherPlayerMP player;

    public Freecam() {
        super("Freecam", Module.Category.Render, -5318);
        this.setBind(0);
    }

    @Override
    public void onEnable() {
        this.mc.thePlayer.noClip = true;
        this.oldX = this.mc.thePlayer.posX;
        this.oldY = this.mc.thePlayer.posY;
        this.oldZ = this.mc.thePlayer.posZ;
        this.oldYaw = this.mc.thePlayer.rotationYaw;
        this.oldPitch = this.mc.thePlayer.rotationPitch;
        this.player = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
        this.player.clonePlayer(this.mc.thePlayer, true);
        this.player.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
        this.player.rotationYawHead = this.mc.thePlayer.rotationYaw;
        this.player.rotationPitch = this.mc.thePlayer.rotationPitch;
        this.player.setSneaking(this.mc.thePlayer.isSneaking());
        this.mc.theWorld.addEntityToWorld(-1337, this.player);
    }

    @Override
    public void onDisable() {
        this.mc.thePlayer.noClip = false;
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.thePlayer.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, this.oldYaw, this.oldPitch);
        this.mc.theWorld.removeEntity(this.player);
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if (PacketEvent.packet instanceof C03PacketPlayer || PacketEvent.packet instanceof C0BPacketEntityAction || PacketEvent.packet instanceof C0APacketAnimation || PacketEvent.packet instanceof C02PacketUseEntity || PacketEvent.packet instanceof C09PacketHeldItemChange || PacketEvent.packet instanceof C07PacketPlayerDigging) {
            event.setCancelled(true);
        }
    }

    @EventListener
    public void onBB(BBEvent event) {
        if (event.getBounds() != null) {
            event.setBounds(null);
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        this.mc.thePlayer.noClip = true;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.jumpMovementFactor *= 7.0f;
        if (this.mc.gameSettings.keyBindSneak.pressed) {
            this.mc.thePlayer.motionY -= 1.0;
        } else if (this.mc.gameSettings.keyBindJump.pressed) {
            this.mc.thePlayer.motionY += 1.0;
        }
        double mx2 = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        double mz2 = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        MovementInput movementInput = this.mc.thePlayer.movementInput;
        double n = (double)MovementInput.moveForward * 0.3 * mx2;
        double x2 = n + (double)MovementInput.moveStrafe * 0.3 * mz2;
        double n2 = (double)MovementInput.moveForward * 0.3 * mz2;
        double z2 = n2 - (double)MovementInput.moveStrafe * 0.3 * mx2;
        if (this.mc.thePlayer.isCollidedHorizontally && !this.mc.thePlayer.isOnLadder() && !Phase.isInsideBlock()) {
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + x2 * 10.0, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + z2 * 10.0);
        }
    }
}

