package de.tired.module.impl.list.movement;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.event.events.PacketEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "Clip", key = Keyboard.KEY_NONE, category = ModuleCategory.MOVEMENT, clickG = "Clip down.")
public class Phase extends Module {

    @EventTarget
    public void onSend(PacketEvent e) {
    }


    @EventTarget
    public void onUpdate(EventPreMotion e) {

    }

    @Override
    public void onState() {
        MC.thePlayer.setPositionAndUpdate(MC.thePlayer.posX,MC.thePlayer.posY -3,MC.thePlayer.posZ);
    }

    @Override
    public void onUndo() {
    }
}
