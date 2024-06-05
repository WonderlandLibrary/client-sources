package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.guis.clickgui.setting.NumberSetting;
import de.tired.event.EventTarget;
import de.tired.event.events.EventPreMotion;
import de.tired.event.events.PacketEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.network.play.server.S03PacketTimeUpdate;

@ModuleAnnotation(name = "WorldTime", category = ModuleCategory.MISC, clickG = "Changes world time")
public class WorldTime extends Module {

    public NumberSetting time = new NumberSetting("Time", this, -1000, -1000, 50000, 1);

    @EventTarget
    public void onTime(PacketEvent e) {
        if (e.getPacket() instanceof S03PacketTimeUpdate) {
            e.setCancelled(true);
        }

    }

    @EventTarget
    public void onPre(EventPreMotion ev) {
        MC.theWorld.setWorldTime(time.getValueInt());
    }

    @EventTarget
    public void onUpdate() {
        MC.theWorld.setWorldTime(time.getValueInt());
    }

    @Override
    public void onState() {
        MC.theWorld.setWorldTime(time.getValueInt());
    }

    @Override
    public void onUndo() {

    }
}
