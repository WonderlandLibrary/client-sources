package club.mega.module.impl.player;

import club.mega.event.impl.EventPacket;
import club.mega.module.Category;
import club.mega.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "NoRotate", description = "Prevents server from rotating you", category = Category.PLAYER)
public class NoRotate extends Module {

    @Handler
    public final void packet(final EventPacket event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            packet.setYaw(MC.thePlayer.rotationYawHead);
            packet.setPitch(MC.thePlayer.rotationPitch);
            event.setPacket(packet);
        }
    }

}
