package gq.vapu.czfclient.Module.Modules.Misc;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.awt.*;

public class NoRotate extends Module {
    public NoRotate() {
        super("NoRotate", new String[]{""}, ModuleType.Player);
        this.setColor(new Color(17, 250, 154).getRGB());
    }

    @EventHandler
    private void onPacket(EventPacketReceive e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook look = (S08PacketPlayerPosLook) e.getPacket();
            look.yaw = mc.thePlayer.rotationYaw;
            look.pitch = mc.thePlayer.rotationPitch;
        }
    }
}
