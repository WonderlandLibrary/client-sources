/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Movement;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.Timer;
import us.amerikan.amerikan;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.TimeHelper;

public class Fly
extends Module {
    private int state;
    private TimeHelper time = new TimeHelper();
    private double speed = 0.0;

    public Fly() {
        super("Fly", "Fly", 0, Category.MOVEMENT);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Cubecraft");
        options.add("Hypixel");
        options.add("Vanilla");
        amerikan.setmgr.rSetting(new Setting("Fly Mode", this, "Cubecraft", options));
        amerikan.setmgr.rSetting(new Setting("Vanilla Speed", this, 1.0, 1.0, 10.0, true));
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

    public void CCFast() {
        Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
        Minecraft.thePlayer.capabilities.allowFlying = false;
        Minecraft.thePlayer.capabilities.isFlying = false;
        if (Minecraft.thePlayer.onGround) {
            for (int i2 = 0; i2 < 32; ++i2) {
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.06, Minecraft.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.06, Minecraft.thePlayer.posZ, false));
            }
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.5, Minecraft.thePlayer.posZ);
        } else if (!Minecraft.thePlayer.onGround) {
            double x2 = Minecraft.thePlayer.posX;
            double y2 = Minecraft.thePlayer.posY;
            double z2 = Minecraft.thePlayer.posZ;
            Minecraft.thePlayer.motionY = 0.0;
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            double speed = 3.8;
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
            Timer.timerSpeed = 0.2f;
            ++this.state;
            switch (this.state) {
                case 1: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-10, z2);
                    break;
                }
                case 2: {
                    Minecraft.thePlayer.setPosition(x2, y2 - 1.0E-10, z2);
                    break;
                }
                case 3: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-10, z2);
                    this.state = 0;
                    break;
                }
            }
        }
    }

    public void damagePlayer() {
        int count = 67 + (int)(EntityLivingBase.getActivePotionEffect(Potion.jump) != null ? (float)EntityLivingBase.getActivePotionEffect(Potion.jump).getAmplifier() / 3.0f : 0.0f);
        for (int i2 = 0; i2 < count; ++i2) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.045, Minecraft.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 2.5E-9, Minecraft.thePlayer.posZ, false));
        }
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
    }

    public void HyFast() {
        if (Minecraft.thePlayer.onGround) {
            this.speed = 1.25;
            this.damagePlayer();
            Minecraft.thePlayer.jump();
        } else {
            ++this.state;
            double x2 = Minecraft.thePlayer.posX;
            double y2 = Minecraft.thePlayer.posY;
            double z2 = Minecraft.thePlayer.posZ;
            switch (this.state) {
                case 1: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    break;
                }
                case 2: {
                    Minecraft.thePlayer.setPosition(x2, y2 - 1.0E-12, z2);
                    break;
                }
                case 3: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    this.state = 0;
                    break;
                }
            }
            Minecraft.thePlayer.motionY = 0.0;
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * this.speed;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * this.speed;
            if (TimeHelper.hasReached(25L) && this.speed > 0.275) {
                this.speed -= 0.01;
                TimeHelper.reset();
            }
        }
    }

    public void CC() {
        if (Minecraft.thePlayer.onGround) {
            this.speed = 1.4;
            this.damagePlayer();
            Minecraft.thePlayer.jump();
        } else {
            ++this.state;
            double x2 = Minecraft.thePlayer.posX;
            double y2 = Minecraft.thePlayer.posY;
            double z2 = Minecraft.thePlayer.posZ;
            switch (this.state) {
                case 1: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    break;
                }
                case 2: {
                    Minecraft.thePlayer.setPosition(x2, y2 - 1.0E-12, z2);
                    break;
                }
                case 3: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    this.state = 0;
                    break;
                }
            }
            Minecraft.thePlayer.motionY = 0.0;
            double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * this.speed;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * this.speed;
            if (TimeHelper.hasReached(25L) && this.speed > 0.26) {
                this.speed -= 0.035;
                TimeHelper.reset();
            }
        }
    }

    public void Hypixel() {
        Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
        Timer.timerSpeed = 1.0f;
        Minecraft.thePlayer.capabilities.allowFlying = false;
        Minecraft.thePlayer.capabilities.isFlying = false;
        if (Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.jump();
        } else if (!Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionY = 0.0;
            ++this.state;
            double x2 = Minecraft.thePlayer.posX;
            double y2 = Minecraft.thePlayer.posY;
            double z2 = Minecraft.thePlayer.posZ;
            switch (this.state) {
                case 1: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    break;
                }
                case 2: {
                    Minecraft.thePlayer.setPosition(x2, y2 - 1.0E-12, z2);
                    break;
                }
                case 3: {
                    Minecraft.thePlayer.setPosition(x2, y2 + 1.0E-12, z2);
                    this.state = 0;
                    break;
                }
            }
        }
    }

    public void Vanilla() {
        float flyspeed = 0.05f;
        Minecraft.thePlayer.capabilities.allowFlying = true;
        Minecraft.thePlayer.capabilities.isFlying = true;
        flyspeed = (float)(0.05 * amerikan.setmgr.getSettingByName("Vanilla Speed").getValDouble());
        Minecraft.thePlayer.capabilities.setFlySpeed(flyspeed);
    }

    @EventTarget
    @Override
    public void onUpdate() {
        if (amerikan.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Cubecraft")) {
            this.setAddon("Cubecraft");
            this.CC();
        } else if (amerikan.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel")) {
            this.setAddon("Hypixel");
            this.Hypixel();
        } else if (amerikan.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel Fast")) {
            this.setAddon("Hypixel Fast");
            this.HyFast();
        } else if (amerikan.setmgr.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.setAddon("Vanilla");
            this.Vanilla();
        }
    }

    @Override
    public void onEnable() {
        if (Fly.mc.theWorld != null) {
            if (Minecraft.thePlayer != null) {
                Minecraft.thePlayer.capabilities.allowFlying = false;
                Minecraft.thePlayer.capabilities.isFlying = false;
                Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
                EventManager.register(this);
            }
        }
    }

    @Override
    public void onDisable() {
        this.speed = 0.0;
        double yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
        double speed = 0.2;
        Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
        Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
        Minecraft.thePlayer.capabilities.allowFlying = false;
        Minecraft.thePlayer.capabilities.isFlying = false;
        Minecraft.thePlayer.capabilities.setFlySpeed(0.05f);
        Timer.timerSpeed = 1.0f;
        EventManager.unregister(this);
    }
}

