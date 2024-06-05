package de.tired.module.impl.list.misc;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

@ModuleAnnotation(name = "ServerActions", category = ModuleCategory.MISC)
public class StopServerActions extends Module {


    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof S2EPacketCloseWindow) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
