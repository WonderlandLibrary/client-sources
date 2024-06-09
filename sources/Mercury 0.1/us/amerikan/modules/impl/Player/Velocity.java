/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class Velocity
extends Module {
    public Velocity() {
        super("Velocity", "Velocity", 0, Category.PLAYER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Null");
        options.add("Packet");
        options.add("AAC Push");
        options.add("AAC Reduce");
        options.add("Bypass");
        options.add("Full");
        options.add("Custom");
        options.add("Gomme");
        amerikan.setmgr.rSetting(new Setting("Velocity Mode", this, "Bypass", options));
        amerikan.setmgr.rSetting(new Setting("Custom Y", this, 0.0, 0.0, 100.0, true));
        amerikan.setmgr.rSetting(new Setting("Custom X/Z", this, 0.0, 0.0, 100.0, true));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        double speed;
        double yaw;
        block19 : {
            block20 : {
                if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Bypass")) {
                    this.setAddon("Bypass");
                    amerikan.instance.Packet = false;
                    if (Minecraft.thePlayer.hurtTime != 0) {
                        if (Keyboard.isKeyDown(57)) {
                            if (Minecraft.thePlayer.onGround) {
                                return;
                            }
                        }
                        Minecraft.thePlayer.onGround = true;
                    }
                }
                if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Gomme")) {
                    if (Minecraft.thePlayer.hurtTime > 0) {
                        if (!Minecraft.thePlayer.isInWater()) {
                            Minecraft.thePlayer.motionX *= 0.8;
                            Minecraft.thePlayer.motionZ *= 0.5;
                        }
                    }
                }
                if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Full")) {
                    this.setAddon("Full");
                    amerikan.instance.Packet = false;
                    if (Minecraft.thePlayer.hurtTime != 0) {
                        Minecraft.thePlayer.motionX = 0.0;
                        Minecraft.thePlayer.motionZ = 0.0;
                        Minecraft.thePlayer.motionY = 0.0;
                        Minecraft.thePlayer.hurtTime = 0;
                    }
                }
                if (!amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Custom")) break block19;
                this.setAddon("Custom");
                amerikan.instance.Packet = false;
                if (Minecraft.thePlayer.hurtTime == 10) break block20;
                if (Minecraft.thePlayer.hurtTime == 0 || Velocity.isPressed()) break block19;
                if (!Minecraft.thePlayer.onGround) break block19;
            }
            Minecraft.thePlayer.motionX = Minecraft.thePlayer.motionX / 100.0 * amerikan.setmgr.getSettingByName("Custom X/Z").getValDouble();
            Minecraft.thePlayer.motionY = Minecraft.thePlayer.motionY / 87.0 * amerikan.setmgr.getSettingByName("Custom Y").getValDouble();
            Minecraft.thePlayer.motionZ = Minecraft.thePlayer.motionZ / 100.0 * amerikan.setmgr.getSettingByName("Custom X/Z").getValDouble();
        }
        if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Packet")) {
            this.setAddon("Packet");
            amerikan.instance.Packet = true;
        }
        if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("Null")) {
            this.setAddon("Null");
            amerikan.instance.Packet = false;
            if (Minecraft.thePlayer.hurtTime > 0) {
                Minecraft.thePlayer.motionY *= 0.0;
                Minecraft.thePlayer.motionX *= 0.0;
                Minecraft.thePlayer.motionZ *= 0.0;
            }
        }
        if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("AAC Push")) {
            this.setAddon("AAC Push");
            if (Minecraft.thePlayer.hurtTime > 0) {
                yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                speed = 0.1;
                Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
                Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
            }
        }
        if (amerikan.setmgr.getSettingByName("Velocity Mode").getValString().equalsIgnoreCase("AAC Reduce")) {
            this.setAddon("AAC Reduce");
            if (Minecraft.thePlayer.hurtTime > 0) {
                yaw = Math.toRadians(Minecraft.thePlayer.rotationYaw);
                speed = -0.06;
                Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
                Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
            }
        }
    }

    private boolean misPressed() {
        return false;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        this.setAddon("null");
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
    }
}

