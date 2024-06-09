package me.teus.eclipse.modules.impl.misc;

import me.teus.eclipse.events.packet.EventReceivePacket;
import me.teus.eclipse.events.packet.EventSendPacket;
import me.teus.eclipse.modules.Category;
import me.teus.eclipse.modules.Info;
import me.teus.eclipse.modules.Module;
import me.teus.eclipse.modules.value.impl.ModeValue;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import org.lwjgl.Sys;
import xyz.lemon.event.bus.Listener;

@Info(name = "Disabler", displayName = "Disabler", category = Category.MISC)
public class Disabler extends Module {

    public ModeValue disMode = new ModeValue("Disable", "BMC Scaff", "BMC Scaff", "OFF");

    public Listener<EventSendPacket> eventSendPacketListener = event -> {
        switch (disMode.getMode()){
            case "BMC Scaff":
                if(event.getPacket() instanceof C08PacketPlayerBlockPlacement){
                    C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();
                    System.out.println("Sent Nulled");
                    packet.stack = null;
                }
                break;
        }
    };

    public Listener<EventReceivePacket> eventReceivePacketListener = event -> {
        switch (disMode.getMode()){
            case "BMC Scaff":
                if(event.getPacket() instanceof C08PacketPlayerBlockPlacement){
                    C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();
                    System.out.println("Received Nulled");
                    packet.stack = null;
                }
                break;
        }
    };

}
