package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.Helper;
import net.minecraft.network.play.server.S29PacketSoundEffect;

public class LightningCheck extends Module {
    public LightningCheck() {
        super("LightningCheck", new String[]{}, ModuleType.World);
    }

    @EventHandler
    public void onPacket(EventPacketReceive e) {
        if ((e.getPacket() instanceof S29PacketSoundEffect) && "ambient.weather.thunder".equals(((S29PacketSoundEffect) e.getPacket()).getSoundName())) {
            Helper.sendMessage("LightningCheck on:" + ((S29PacketSoundEffect) e.getPacket()).getX() + " " + ((S29PacketSoundEffect) e.getPacket()).getY() + " " + ((S29PacketSoundEffect) e.getPacket()).getZ());
        }
    }
}
