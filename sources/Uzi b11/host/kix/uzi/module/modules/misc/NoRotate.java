package host.kix.uzi.module.modules.misc;

import com.darkmagician6.eventapi.SubscribeEvent;
import host.kix.uzi.events.RecievePacketEvent;
import host.kix.uzi.module.Module;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/**
 * Created by Kix on 6/3/2017.
 * Made for the eclipse project.
 */
public class NoRotate extends Module {

    public NoRotate() {
        super("NoRotate", 0, Category.MISC);
    }

    @SubscribeEvent
    public void packet(RecievePacketEvent event){
        if (this.mc.thePlayer == null || this.mc.theWorld == null) {
            return;
        }
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            final S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)event.getPacket();
            if (this.mc.thePlayer.rotationYaw != -180.0f && this.mc.thePlayer.rotationPitch != 0.0f) {
                poslook.field_148936_d = this.mc.thePlayer.rotationYaw;
                poslook.field_148937_e = this.mc.thePlayer.rotationPitch;
            }
        }
    }

}
