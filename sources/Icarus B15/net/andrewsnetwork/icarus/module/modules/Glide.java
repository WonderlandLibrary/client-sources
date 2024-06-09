// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.PlayerMovement;
import net.minecraft.entity.player.EntityPlayer;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.minecraft.util.AxisAlignedBB;
import net.andrewsnetwork.icarus.event.events.BlockBB;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Glide extends Module
{
    private final Value<Float> motion;
    private final Value<Boolean> latest;
    private final TimeHelper time;
    
    public Glide() {
        super("Glide", -2417, 34, Category.MOVEMENT);
        this.motion = (Value<Float>)new ConstrainedValue("glide_Motion", "motion", 1.0f, 0.0f, 3.0f, this);
        this.latest = new Value<Boolean>("glide_Latest", "latest", false, this);
        this.time = new TimeHelper();
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Glide.mc.thePlayer != null && Glide.mc.thePlayer.onGround) {
            if (this.latest.getValue()) {
                this.damagePlayer();
                Glide.mc.thePlayer.capabilities.setFlySpeed(0.015f);
                return;
            }
            final double x = Glide.mc.thePlayer.posX;
            final double y = Glide.mc.thePlayer.posY;
            final double z = Glide.mc.thePlayer.posZ;
            if (Glide.mc.thePlayer != null) {
                for (int i = 0; i < 81; ++i) {
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.05, Minecraft.getMinecraft().thePlayer.posZ, false));
                    Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
                }
            }
            this.time.reset();
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Glide.mc.thePlayer != null) {
            Glide.mc.thePlayer.capabilities.isFlying = false;
        }
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
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
        if (e instanceof PreMotion) {
            if (this.latest.getValue()) {
                Glide.mc.thePlayer.capabilities.isFlying = true;
                return;
            }
            if (!Glide.mc.thePlayer.capabilities.isFlying && Glide.mc.thePlayer.fallDistance > 0.0f && !Glide.mc.thePlayer.isSneaking() && this.time.hasReached(500L)) {
                Glide.mc.thePlayer.motionY = -0.03127;
            }
            if (Glide.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                Glide.mc.thePlayer.motionY = -0.5;
            }
        }
        else if (e instanceof BlockBB && !this.latest.getValue()) {
            final BlockBB event = (BlockBB)e;
            if (!Glide.mc.thePlayer.capabilities.isFlying && Glide.mc.thePlayer.fallDistance > 0.0f && !Glide.mc.thePlayer.isSneaking() && this.time.hasReached(500L) && event.getY() < Glide.mc.thePlayer.posY + 0.5) {
                event.setBoundingBox(null);
                if (BlockHelper.isInsideBlock(Glide.mc.thePlayer)) {
                    Glide.mc.thePlayer.func_174826_a(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().offset(0.0, 0.0626, 0.0));
                }
            }
        }
        else if (e instanceof PlayerMovement && !this.latest.getValue()) {
            final PlayerMovement event2 = (PlayerMovement)e;
            if (this.time.hasReached(200L) && !Glide.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                event2.setY(event2.getY() * this.motion.getValue());
            }
        }
    }
    
    public void damagePlayer() {
        if (Glide.mc.thePlayer != null) {
            for (int i = 0; i < 92.5; ++i) {
                Glide.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Glide.mc.thePlayer.posX, Glide.mc.thePlayer.posY + 0.049, Glide.mc.thePlayer.posZ, false));
                Glide.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Glide.mc.thePlayer.posX, Glide.mc.thePlayer.posY, Glide.mc.thePlayer.posZ, false));
            }
            Glide.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Glide.mc.thePlayer.posX, Glide.mc.thePlayer.posY, Glide.mc.thePlayer.posZ, false));
        }
    }
}
