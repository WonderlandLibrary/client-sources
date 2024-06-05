package koks.modules.impl.visuals;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.event.impl.PacketEvent;
import koks.modules.Module;
import koks.utilities.value.values.NumberValue;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 17:50
 */
public class Ambiance extends Module {

    public NumberValue<Integer> time = new NumberValue<Integer>("Time", 0, 18000, 0, this);

    public Ambiance() {
        super("Ambiance", "You change the world time", Category.VISUALS);
        addValue(time);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EventUpdate) {
            mc.theWorld.setWorldTime(time.getDefaultValue());
        }
        if(event instanceof PacketEvent) {
            PacketEvent packetEvent = (PacketEvent) event;
            if(packetEvent.getType() == PacketEvent.Type.RECIVE) {
                if(packetEvent.getPacket() instanceof S03PacketTimeUpdate) {
                    packetEvent.setCanceled(true);
                }
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
