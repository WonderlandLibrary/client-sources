package me.xatzdevelopments.modules.player;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.notifications.NotificationType;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.ModulesUtils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;


public class NoFall extends Module
{
    int offGroundTicks;
    int onGroundTicks;
    int ticks;
    public ModeSetting Mode;
    
    public NoFall() {
        super("NoFall", 0, Category.PLAYER, null);
        this.Mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "OnGround", "ACR", "AAC" });
        this.addSettings(this.Mode);
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            if (this.Mode.getMode() != "Matrix") {
                ++this.ticks;
            }
            if (this.mc.thePlayer.onGround) {
                this.offGroundTicks = 0;
                ++this.onGroundTicks;
            }
            else {
                ++this.offGroundTicks;
                this.onGroundTicks = 0;
            }
            final String mode;
            switch (mode = this.Mode.getMode()) {
                case "Hypixel": {
                    if (this.mc.thePlayer.fallDistance > 2.0f) {
                        this.mc.thePlayer.fallDistance = 0.0f;
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                        break;
                    }
                    break;
                }
                default:
                    break;
            }
        }
        if (e instanceof EventMotion) {
            final String mode2;
            switch (mode2 = this.Mode.getMode()) {
                case "AAC": {
                    this.mc.timer.timerSpeed = 1.0f;
                    if (this.mc.thePlayer.fallDistance > 2.5) {
                        this.mc.thePlayer.motionY = 0.0;
                        this.mc.thePlayer.fallDistance = 0.0f;
                        this.mc.thePlayer.onGround = true;
                        ((EventMotion)e).setOnGround(true);
                        final KeyBinding keyBindJump = this.mc.gameSettings.keyBindJump;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindJump.getKeyCode(), false);
                        break;
                    }
                    break;
                }
                case "ACR": {
                    if (this.mc.thePlayer.ticksExisted % 2 == 0 && this.mc.thePlayer.fallDistance > 2.0f) {
                        ((EventMotion)e).setOnGround(true);
                        break;
                    }
                    break;
                }
                case "OnGround": {
                    if (!this.mc.thePlayer.onGround && this.mc.thePlayer.ticksExisted % 60 != 0) {
                        ((EventMotion)e).setOnGround(true);
                        break;
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }
}
