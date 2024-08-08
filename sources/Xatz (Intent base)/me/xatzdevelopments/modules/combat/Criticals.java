package me.xatzdevelopments.modules.combat;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventAttack;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.events.listeners.EventPacket;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.notifications.NotificationType;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.ModulesUtils;
import me.xatzdevelopments.util.movement.MovementUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import me.xatzdevelopments.util.Timer;

public class Criticals extends Module
{
    int offGroundTicks;
    
    public Criticals() {
        super("Criticals", 0, Category.COMBAT, "Critical hit someone, (extra damage to enemy)");
        this.offGroundTicks = 0;;
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
                if (e instanceof EventMotion && e.isPre()) {
                    if (this.mc.thePlayer.onGround) {
                        this.offGroundTicks = 0;
                    }
                    else {
                        ++this.offGroundTicks;
                    }
                    if (this.mc.thePlayer.fallDistance > 3.0f) {
                        return;
                    }
                    final double y = this.mc.thePlayer.posY;
                    if (this.mc.thePlayer.onGround) {
                        ((EventMotion)e).onGround = false;
                        ((EventMotion)e).y = y + 1.0E-9;
                    }
                    if (this.offGroundTicks == 1) {
                        ((EventMotion)e).onGround = true;
                    }
                }
                if (e instanceof EventAttack) {
                    this.mc.thePlayer.onCriticalHit(((EventAttack)e).entity);
        }
    }
}
