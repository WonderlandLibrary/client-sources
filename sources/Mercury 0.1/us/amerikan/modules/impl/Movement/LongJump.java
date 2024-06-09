/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class LongJump
extends Module {
    public static boolean dont = false;

    public LongJump() {
        super("LongJump", "LongJump", 0, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        this.CC();
        this.setAddon("Cubecraft");
    }

    public void CCDamage(double damage) {
        Minecraft.getMinecraft();
        if (Minecraft.thePlayer != null) {
            for (int i2 = 0; i2 < 32; ++i2) {
                double damage1 = 0.1;
                double lol = damage1 * damage;
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (0.0 + lol), Minecraft.thePlayer.posZ, false));
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft();
                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - (0.0 + lol), Minecraft.thePlayer.posZ, false));
            }
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
        }
    }

    public void CC() {
        if (Minecraft.thePlayer.onGround) {
            Timer.timerSpeed = 1.0f;
            this.CCDamage(0.5);
            Minecraft.thePlayer.motionY = 0.4;
        } else {
            Timer.timerSpeed = 0.33f;
            double speed = 3.7;
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
        }
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
    }

    @Override
    public void onDisable() {
        Timer.timerSpeed = 1.0f;
        Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
        EventManager.unregister(this);
    }
}

