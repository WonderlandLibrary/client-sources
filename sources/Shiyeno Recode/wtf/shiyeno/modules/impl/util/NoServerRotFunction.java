package wtf.shiyeno.modules.impl.util;

import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.packet.EventPacket;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.Setting;
import wtf.shiyeno.modules.settings.imp.ModeSetting;

@FunctionAnnotation(
        name = "NoServerRot",
        type = Type.Util
)
public class NoServerRotFunction extends Function {
    private ModeSetting serverRotMode = new ModeSetting("Тип", "Обычный", new String[]{"Обычный", "RW"});

    public NoServerRotFunction() {
        this.addSettings(new Setting[]{this.serverRotMode});
    }

    public void onEvent(Event event) {
        if (!this.serverRotMode.is("RW") && event instanceof EventPacket packet) {
            if (packet.isReceivePacket()) {
                IPacket var4 = packet.getPacket();
                if (var4 instanceof SPlayerPositionLookPacket) {
                    SPlayerPositionLookPacket packet1 = (SPlayerPositionLookPacket)var4;
                    packet1.yaw = mc.player.rotationYaw;
                    packet1.pitch = mc.player.rotationPitch;
                }
            }
        }
    }
}