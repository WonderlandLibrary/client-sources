package Hydro.module.modules.misc;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.event.events.EventMotion;
import Hydro.event.events.EventPacket;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.util.Timer;

public class Disabler extends Module {

    public Disabler() {
        super("Disabler", 0, true, Category.MISC, "Disable the anticheat");
        ArrayList<String> options = new ArrayList<>();
        options.add("Redesky");
        Client.settingsManager.rSetting(new Setting("DisablerMode", "Mode", this, "Redesky", options));
    }

    double oldPosX;
    double oldPosZ;
    double oldPosY;
    Timer timer = new Timer();
    double oldPitch;
    double oldYaw;

    public void onEvent(Event var1) {
        this.setDisplayName(EnumChatFormatting.GRAY + "Redesky");
        if (var1 instanceof EventPacket) {

            if (((EventPacket) var1).isRecieving() && ((EventPacket) var1).getPacket() instanceof C0FPacketConfirmTransaction) {
                var1.setCancelled(true);
            } else if (((EventPacket) var1).isRecieving() && ((EventPacket) var1).getPacket() instanceof C0BPacketEntityAction) {
                var1.setCancelled(true);
            } else {
                boolean var10000 = ((EventPacket) var1).isRecieving() && ((EventPacket) var1).getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook;
            }
        }
    }

}
