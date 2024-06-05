package Ascii4UwUWareClient.Module.Modules.Render;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketReceive;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

public class TimeChanger extends Module {
    public TimeChanger() {
        super("TimeChanger", new String[]{"changer"}, ModuleType.Render);
    }

    @EventHandler
    public void onPacketRecive(EventPacketReceive e) {
        if (((EventPacketReceive) e).getPacket() instanceof S03PacketTimeUpdate) {
            ((S03PacketTimeUpdate) ((EventPacketReceive) e).getPacket()).setWorldTime(18000);
        }
    }
}
