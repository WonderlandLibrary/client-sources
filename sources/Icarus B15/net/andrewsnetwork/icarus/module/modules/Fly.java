// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.block.Block;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
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

public class Fly extends Module
{
    private final Value<Boolean> nocheat;
    private final Value<Float> speed;
    private final TimeHelper time;
    private int ticks;
    private boolean setLocation;
    private double firsty;
    private double lasty;
    private double uptime;
    private float height;
    
    public Fly() {
        super("Fly", -16711681, 33, Category.MOVEMENT);
        this.nocheat = new Value<Boolean>("fly_NoCheat", "nocheat", false, this);
        this.speed = (Value<Float>)new ConstrainedValue("fly_Speed", "speed", 1.0f, 0.5f, 10.0f, this);
        this.time = new TimeHelper();
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Fly.mc.thePlayer != null && Fly.mc.thePlayer.onGround) {
            if (this.nocheat.getValue()) {
                final double x = Fly.mc.thePlayer.posX;
                final double y = Fly.mc.thePlayer.posY;
                final double z = Fly.mc.thePlayer.posZ;
                if (Fly.mc.thePlayer != null) {
                    for (int i = 0; i < 81; ++i) {
                        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.05, Minecraft.getMinecraft().thePlayer.posZ, false));
                        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, false));
                    }
                }
                this.firsty = Fly.mc.thePlayer.posY - 0.6000000238418579;
            }
            this.time.reset();
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
            final boolean freeze = false;
            final PreMotion event = (PreMotion)e;
            if (this.nocheat.getValue()) {
                if (this.time.hasReached(500L)) {
                    ++this.ticks;
                    Fly.mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(Fly.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
                    this.setTag("Fly §7" + this.uptime);
                    if (Fly.mc.gameSettings.keyBindJump.getIsKeyPressed() && Fly.mc.thePlayer.posY < this.firsty + 1.0) {
                        Fly.mc.thePlayer.motionY = 0.5;
                    }
                    else {
                        Fly.mc.thePlayer.motionY = -1.0E-4;
                    }
                    if (Fly.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                        Fly.mc.thePlayer.motionY = -0.5;
                    }
                    if (Fly.mc.thePlayer.fallDistance < 2.0f) {
                        Fly.mc.thePlayer.fallDistance = 2.0f;
                    }
                    if (!Fly.mc.thePlayer.onGround && !Fly.mc.gameSettings.keyBindJump.getIsKeyPressed() && !Fly.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                        Fly.mc.thePlayer.motionY = -0.03127;
                        final Block highBlock = Fly.mc.theWorld.getBlockState(new BlockPos((int)Math.round(Fly.mc.thePlayer.posX), (int)Math.round(Fly.mc.thePlayer.boundingBox.minY - 1.5), (int)Math.round(Fly.mc.thePlayer.posZ))).getBlock();
                        if (!(highBlock instanceof BlockAir)) {
                            this.setLocation = true;
                        }
                        else {
                            this.setLocation = false;
                            this.height = 0.6f;
                        }
                    }
                    else if (this.setLocation && Fly.mc.thePlayer.onGround && this.height >= 0.11) {
                        this.height -= 0.005;
                    }
                }
            }
            else {
                this.setTag("Fly");
                if (!Fly.mc.thePlayer.capabilities.isFlying) {
                    Fly.mc.thePlayer.capabilities.isFlying = true;
                }
                Fly.mc.thePlayer.capabilities.setFlySpeed(this.speed.getValue() / 10.0f);
                if (Fly.mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                    Fly.mc.thePlayer.motionY = this.speed.getValue() / 2.0f;
                }
                if (Fly.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                    Fly.mc.thePlayer.motionY = -this.speed.getValue() / 2.0f;
                }
            }
        }
        if (e instanceof SentPacket && this.nocheat.getValue() && this.time.hasReached(500L)) {
            final SentPacket event2 = (SentPacket)e;
            if (event2.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event2.getPacket();
                if (player.getPositionY() > this.lasty && !Fly.mc.thePlayer.onGround) {
                    ++this.uptime;
                }
                if (Fly.mc.thePlayer.onGround) {
                    this.uptime = 0.0;
                }
                this.lasty = player.getPositionY();
                if (this.time.hasReached(1000L)) {
                    player.field_149474_g = false;
                    final C03PacketPlayer c03PacketPlayer = player;
                    c03PacketPlayer.y += this.height;
                }
            }
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Fly.mc.thePlayer != null) {
            if (Fly.mc.thePlayer.capabilities.isFlying) {
                Fly.mc.thePlayer.capabilities.isFlying = false;
            }
            Fly.mc.thePlayer.capabilities.setFlySpeed(0.05f);
        }
    }
}
