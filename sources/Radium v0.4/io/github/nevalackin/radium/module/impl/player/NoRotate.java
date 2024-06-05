package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(label = "No Rotate", category = ModuleCategory.SELF)
public final class NoRotate extends Module {

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) e.getPacket();
            e.setPacket(new S08PacketPlayerPosLook(packet.getX(), packet.getY(),
                    packet.getZ(), Wrapper.getPlayer().currentEvent.getYaw(),
                    Wrapper.getPlayer().currentEvent.getPitch(), packet.func_179834_f()));
        }
    }

}
