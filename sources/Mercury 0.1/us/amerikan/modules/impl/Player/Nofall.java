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
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;
import us.amerikan.amerikan;
import us.amerikan.events.EventUpdate;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class Nofall
extends Module {
    public Nofall() {
        super("Nofall", "Nofall", 0, Category.PLAYER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("Cubecraft");
        options.add("AAC");
        options.add("Vanilla");
        amerikan.setmgr.rSetting(new Setting("Nofall Mode", this, "Cubecraft", options));
    }

    public void AAC() {
        if (Minecraft.thePlayer.fallDistance > 3.0f) {
            for (int i2 = 0; i2 < 24; ++i2) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.4, Minecraft.thePlayer.posZ, false));
            }
        }
    }

    public void CC() {
        if (Minecraft.thePlayer.fallDistance > 3.0f) {
            for (int i2 = 0; i2 < 64; ++i2) {
            }
        } else if (Minecraft.thePlayer.onGround) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.05, Minecraft.thePlayer.posZ, true));
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.05, Minecraft.thePlayer.posZ, true));
        }
    }

    public void Van() {
        if (Minecraft.thePlayer.fallDistance > 3.0f) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (amerikan.setmgr.getSettingByName("Nofall Mode").getValString().equalsIgnoreCase("Cubecraft")) {
            this.setAddon("Cubecraft");
            this.CC();
        } else if (amerikan.setmgr.getSettingByName("Nofall Mode").getValString().equalsIgnoreCase("Vanilla")) {
            this.setAddon("Vanilla");
            this.Van();
        } else if (amerikan.setmgr.getSettingByName("Nofall Mode").getValString().equalsIgnoreCase("AAC")) {
            this.setAddon("AAC");
            this.AAC();
        }
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        Timer.timerSpeed = 1.0f;
    }

    @Override
    public void onEnable() {
        EventManager.register(this);
        Timer.timerSpeed = 1.0f;
    }
}

