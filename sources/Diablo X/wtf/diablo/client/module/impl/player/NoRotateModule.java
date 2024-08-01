package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import wtf.diablo.client.event.impl.network.RecievePacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(name = "No Rotate", description = "Attempts to block the server from forcing you to rotate.", category = wtf.diablo.client.module.api.data.ModuleCategoryEnum.PLAYER)
public final class NoRotateModule extends AbstractModule {

    @EventHandler
    public final Listener<RecievePacketEvent> packetEventListener = e -> {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) e.getPacket();
            if (mc.thePlayer != null && mc.theWorld != null && mc.thePlayer.rotationYaw != -180) {
                packet.setYaw(mc.thePlayer.rotationYaw);
                packet.setPitch(mc.thePlayer.rotationPitch);
            }
        }
    };
}
