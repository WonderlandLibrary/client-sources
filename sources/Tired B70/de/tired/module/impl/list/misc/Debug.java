package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.Packet;

import java.util.ArrayList;

@ModuleAnnotation(name = "Debug", category = ModuleCategory.MISC)
public class Debug extends Module {

    private ArrayList<Packet> packets = new ArrayList<>();

    @EventTarget
    public void onPre(EventPreMotion e) {

    }

    @EventTarget
    public void onPacket(PacketEvent e) {

    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

        for (Packet<?> packet : packets) {
            if (MC.thePlayer.ticksExisted % 52 == 0) {
                sendPacket(packet);
            }
        }

    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        MC.timer.timerSpeed = 1F;
    }
}
