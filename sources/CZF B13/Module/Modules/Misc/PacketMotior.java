package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Blatant.Fly;
import gq.vapu.czfclient.Module.Modules.Blatant.Teleport;
import gq.vapu.czfclient.UI.ClientNotification;
import gq.vapu.czfclient.Util.ClientUtil;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketMotior
        extends Module {
    public static int packetcount;
    private final TimerUtil time = new TimerUtil();

    public PacketMotior() {
        super("PacketMotior", new String[]{"PacketMotior"}, ModuleType.Player);
    }

    @EventHandler
    public void OnUpdate(EventPreUpdate event) {
        if (this.time.delay(1000.0f)) {
            this.setSuffix("PPS:" + packetcount);
            if (packetcount > 25) {
                //Helper.sendMessage((String)"您的发包数量不正常!");
                ClientUtil.sendClientMessage("Packet quantity Exception!", ClientNotification.Type.WARNING);
                //Client.getModuleManager().getModuleByClass(Speed.class).setEnabled(false);
            }
            packetcount = 0;
            this.time.reset();
        }
    }

    @EventHandler
    public void Packet(EventPacketSend event) {
        if (EventPacketSend.getPacket() instanceof C03PacketPlayer && !ModuleManager.getModuleByClass(Fly.class).isEnabled() && !ModuleManager.getModuleByClass(Teleport.class).isEnabled()) {
            ++packetcount;
        }
    }
}

