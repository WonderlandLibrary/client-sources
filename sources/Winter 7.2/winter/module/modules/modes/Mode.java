/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules.modes;

import net.minecraft.client.Minecraft;
import winter.event.events.BBEvent;
import winter.event.events.MoveEvent;
import winter.event.events.PacketEvent;
import winter.event.events.Render2DEvent;
import winter.event.events.Render3DEvent;
import winter.event.events.TickEvent;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class Mode {
    protected static Minecraft mc;
    public Module parent;
    public String name;

    public Mode(Module part, String name) {
        this.parent = part;
        this.name = name;
        mc = Minecraft.getMinecraft();
    }

    public void enable() {
    }

    public void disable() {
    }

    public String getName() {
        return this.name;
    }

    public String getDisplay() {
        return " " + this.name;
    }

    public String getArrayDisplay() {
        return " [" + this.name + "]";
    }

    public boolean isCurrentMode() {
        return this.parent.getMode().equals(" " + this.name);
    }

    public void onTick(TickEvent event) {
    }

    public void onMove(MoveEvent event) {
    }

    public void onBB(BBEvent event) {
    }

    public void onUpdate(UpdateEvent event) {
    }

    public void onPacket(PacketEvent event) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void onRender2D(Render2DEvent event) {
    }
}

