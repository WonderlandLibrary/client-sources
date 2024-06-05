/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.player;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import digital.rbq.annotations.Label;
import digital.rbq.events.packet.ReceivePacketEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;

@Label(value="Anti Desync")
@Category(value=ModuleCategory.PLAYER)
@Aliases(value={"antidesync", "norotations"})
public final class AntiDesyncMod
extends Module {
    @Listener(value=ReceivePacketEvent.class)
    public void onEvent(ReceivePacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
            packet.setYaw(AntiDesyncMod.mc.thePlayer.rotationYaw);
            packet.setPitch(AntiDesyncMod.mc.thePlayer.rotationPitch);
        }
    }
}

