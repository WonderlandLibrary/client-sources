package wtf.resolute.moduled.impl.player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.play.client.CPlayerPacket;
import wtf.resolute.evented.EventPacket;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;

@ModuleAnontion(name = "NoRotate", type = Categories.Player,server = "")
public class NoRotate extends Module {

    private float targetYaw;
    private float targetPitch;
    private boolean isPacketSent;

    @Subscribe
    public void onPacket(EventPacket event) {
        if (event.isSend()) {
            if (this.isPacketSent) {
                if (event.getPacket() instanceof CPlayerPacket playerPacket) {
                    playerPacket.setRotation(targetYaw, targetPitch);
                    this.isPacketSent = false;
                }
            }
        }
    }

    public void sendRotationPacket(float yaw, float pitch) {
        this.targetYaw = yaw;
        this.targetPitch = pitch;
        this.isPacketSent = true;
    }
}
