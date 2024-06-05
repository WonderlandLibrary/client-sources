package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;

import java.util.ArrayList;

@ModuleAnnotation(name = "Blink", category = ModuleCategory.MOVEMENT)
public class NCPBlink extends Module {

    private final ArrayList<Packet<?>> packetQueue = new ArrayList<>();

    @EventTarget
    public void onPacket(PacketEvent eventPacket) {
        if (eventPacket != null) {
            final Packet<?> packet = eventPacket.getPacket();
            if (packet instanceof C03PacketPlayer) {
                eventPacket.setCancelled(true);
                packetQueue.add(packet);

            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {

    }


    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {
        if (!packetQueue.isEmpty()) {
            for (Packet<?> packet : packetQueue)
                MC.getNetHandler().addToSendQueue(packet);
        }
        packetQueue.clear();
    }
}
