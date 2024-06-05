package digital.rbq.module.implement.Render;

import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.util.EnumChatFormatting;
import digital.rbq.Lycoris;
import digital.rbq.event.PacketReceiveEvent;
import digital.rbq.event.TickEvent;
import digital.rbq.event.WorldRenderEvent;
import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.ModeValue;

public class Ambience extends Module {
    public static ModeValue Mode = new ModeValue("Ambience", "Mode", "Darkness", "Sunset", "Day", "Sunrise");

    public Ambience() {
        super("Ambience", Category.Render, false);
    }

    @EventTarget
    public void onRenderWorld(WorldRenderEvent event) {
        if (Mode.getValue().equals("Darkness")) {
            this.mc.theWorld.setWorldTime(-18000);
        } else if (Mode.getValue().equals("Sunset")) {
            this.mc.theWorld.setWorldTime(-13000);
        } else if (Mode.getValue().equals("Day")) {
            this.mc.theWorld.setWorldTime(2000);
        } else if(Mode.getValue().equals("Sunrise")) {
            this.mc.theWorld.setWorldTime(22500);
        }
    }

    @EventTarget
    public void onRecv(PacketReceiveEvent event) {
        if (event.packet instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
}
