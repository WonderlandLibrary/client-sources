/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.BlockBB;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.BlockHelper;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

@Module.Mod(displayName="FlyOldNCP")
public class FlyOldNCP
extends Module {
    private final float motion;
    @Option.Op(name="damage")
    private boolean damage;
    private final Timer time = new Timer();
    private Minecraft mc = Minecraft.getMinecraft();

    public FlyOldNCP() {
        this.motion = 1.0f;
    }

    @Override
    public void enable() {
        super.enable();
        if (Minecraft.thePlayer != null && Minecraft.thePlayer.onGround) {
            if (this.damage) {
                this.damagePlayer();
                Minecraft.thePlayer.capabilities.setFlySpeed(0.015f);
                return;
            }
            double x = Minecraft.thePlayer.posX;
            double y = Minecraft.thePlayer.posY;
            double z = Minecraft.thePlayer.posZ;
            if (Minecraft.thePlayer != null) {
                for (int i = 0; i < 81; ++i) {
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.05, Minecraft.thePlayer.posZ, false));
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getMinecraft();
                    Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                }
            }
            this.time.reset();
        }
    }

    @Override
    public void disable() {
        super.disable();
        if (Minecraft.thePlayer != null) {
            Minecraft.thePlayer.capabilities.isFlying = false;
        }
    }

    @EventTarget
    private boolean onEvent(UpdateEvent e) {
        if (e.getState().equals((Object)Event.State.PRE)) {
            if (this.damage) {
                Minecraft.thePlayer.capabilities.isFlying = true;
            }
            if (!Minecraft.thePlayer.capabilities.isFlying && Minecraft.thePlayer.fallDistance > 0.0f && !Minecraft.thePlayer.isSneaking() && this.time.hasReached(500L)) {
                Minecraft.thePlayer.motionY = -0.03127;
            }
            if (this.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                Minecraft.thePlayer.motionY = -0.5;
            }
        }
        return true;
    }

    @EventTarget
    private void blockBB(BlockBB e) {
        if (!this.damage) {
            BlockBB event = e;
            if (!Minecraft.thePlayer.capabilities.isFlying && Minecraft.thePlayer.fallDistance > 0.0f && !Minecraft.thePlayer.isSneaking() && this.time.hasReached(500L) && (double)event.getY() < Minecraft.thePlayer.posY + 0.5) {
                event.setBoundingBox(null);
                if (BlockHelper.isInsideBlock(Minecraft.thePlayer)) {
                    Minecraft.getMinecraft();
                    Minecraft.thePlayer.func_174826_a(Minecraft.thePlayer.getEntityBoundingBox().offset(0.0, 0.0626, 0.0));
                }
            }
        }
    }

    @EventTarget
    private void onMove(MoveEvent e) {
        if (!this.damage) {
            MoveEvent event2 = e;
            if (this.time.hasReached(200L) && !this.mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
                event2.setY(event2.getY() * 1.0);
            }
        }
    }

    public void damagePlayer() {
        if (Minecraft.thePlayer != null) {
            int i = 0;
            while ((double)i < 92.5) {
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.049, Minecraft.thePlayer.posZ, false));
                Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                ++i;
            }
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
        }
    }
}

