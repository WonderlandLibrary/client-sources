// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.minecraft.util.AxisAlignedBB;
import net.andrewsnetwork.icarus.event.events.BlockBB;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.events.PushOut;
import net.andrewsnetwork.icarus.event.events.InsideBlock;
import org.lwjgl.input.Keyboard;
import net.andrewsnetwork.icarus.event.events.PlayerMovement;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.andrewsnetwork.icarus.module.Module;

public class Freecam extends Module
{
    private double x;
    private double y;
    private double minY;
    private double z;
    private float yaw;
    private float pitch;
    
    public Freecam() {
        super("Freecam", -5192482, Category.PLAYER);
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Freecam.mc.thePlayer != null) {
            Freecam.mc.thePlayer.noClip = false;
            Freecam.mc.theWorld.removeEntityFromWorld(-1);
            Freecam.mc.thePlayer.setPositionAndRotation(this.x, this.y, this.z, this.yaw, this.pitch);
        }
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Freecam.mc.thePlayer != null) {
            this.x = Freecam.mc.thePlayer.posX;
            this.y = Freecam.mc.thePlayer.posY;
            this.minY = Freecam.mc.thePlayer.boundingBox.minY;
            this.z = Freecam.mc.thePlayer.posZ;
            this.yaw = Freecam.mc.thePlayer.rotationYaw;
            this.pitch = Freecam.mc.thePlayer.rotationPitch;
            final EntityOtherPlayerMP ent = new EntityOtherPlayerMP(Freecam.mc.theWorld, Freecam.mc.thePlayer.getGameProfile());
            ent.inventory = Freecam.mc.thePlayer.inventory;
            ent.inventoryContainer = Freecam.mc.thePlayer.inventoryContainer;
            ent.setPositionAndRotation(this.x, this.minY, this.z, this.yaw, this.pitch);
            ent.rotationYawHead = Freecam.mc.thePlayer.rotationYawHead;
            Freecam.mc.theWorld.addEntityToWorld(-1, ent);
            Freecam.mc.thePlayer.noClip = true;
        }
    }
    
    @Override
    public void onEvent(final Event event) {
        Label_0040: {
            if (event instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (event instanceof PlayerMovement) {
            if (Freecam.mc.thePlayer.hurtTime > 1) {
                this.setEnabled(false);
            }
            final PlayerMovement movement = (PlayerMovement)event;
            double speed = 4.0;
            if (Keyboard.isKeyDown(29)) {
                speed /= 2.0;
            }
            movement.setY(0.0);
            Freecam.mc.thePlayer.motionY = 0.0;
            if (!Freecam.mc.inGameHasFocus) {
                return;
            }
            if (Freecam.mc.thePlayer.movementInput.moveForward != 0.0f || Freecam.mc.thePlayer.movementInput.moveStrafe != 0.0f) {
                movement.setX(movement.getX() * speed);
                movement.setZ(movement.getZ() * speed);
            }
            if (Keyboard.isKeyDown(Freecam.mc.gameSettings.keyBindJump.getKeyCode())) {
                movement.setY(speed / 4.0);
            }
            else if (Keyboard.isKeyDown(Freecam.mc.gameSettings.keyBindSneak.getKeyCode())) {
                movement.setY(-(speed / 4.0));
            }
        }
        else if (event instanceof InsideBlock) {
            ((InsideBlock)event).setCancelled(true);
        }
        else if (event instanceof PushOut) {
            ((PushOut)event).setCancelled(true);
        }
        else if (event instanceof PreMotion) {
            final EntityPlayerSP thePlayer = Freecam.mc.thePlayer;
            thePlayer.renderArmPitch += 400.0f;
        }
        else if (event instanceof BlockBB) {
            ((BlockBB)event).setBoundingBox(null);
        }
        else if (event instanceof SentPacket) {
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                player.x = this.x;
                player.y = this.y;
                player.z = this.z;
                player.yaw = this.yaw;
                player.pitch = this.pitch;
            }
        }
    }
}
