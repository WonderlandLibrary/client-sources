package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.event.EventTarget;
import de.tired.event.events.PacketEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.impl.list.movement.Flight;
import de.tired.module.impl.list.movement.Speed;
import de.tired.module.impl.list.world.ScaffoldWalk;
import de.tired.notification.NotificationRenderer;
import de.tired.tired.Tired;
import de.tired.tired.TiredCore;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.awt.*;

@ModuleAnnotation(name = "FlagDetector", category = ModuleCategory.MISC, clickG = "Disable modules when flagged")
public class FlagDetector extends Module {
    public boolean disable;

    @EventTarget
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            if (Tired.INSTANCE.moduleManager.findModuleByClass(Speed.class).isState()) {
                disable = true;
                Tired.INSTANCE.moduleManager.findModuleByClass(Speed.class).executeMod();
            }
            if (Tired.INSTANCE.moduleManager.findModuleByClass(ScaffoldWalk.class).isState()) {
                disable = true;
                Tired.INSTANCE.moduleManager.findModuleByClass(ScaffoldWalk.class).executeMod();
            }
            if (Tired.INSTANCE.moduleManager.findModuleByClass(Flight.class).isState()) {
                disable = true;
                Tired.INSTANCE.moduleManager.findModuleByClass(Flight.class).executeMod();
            }
        }
        if (disable) {
            alertFlag("Disabled module during flag, if you think its because the bypass, contact: PeeakorX#2753");
            this.disable = false;
        }
    }

    @Override
    public void onState() {

    }

    public void alertFlag(String mod) {
        TiredCore.CORE.notificationRenderer.sendNotification(NotificationRenderer.notifyType.ERROR, mod, "You got flagged. (Flag Detector) - watch ur settings.", Color.RED);
    }

    @Override
    public void onUndo() {

    }
}
