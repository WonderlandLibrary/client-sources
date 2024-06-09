/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Overlay
extends Module {
    public static NumberValue hue;
    public static BooleanValue watermark;
    public static BooleanValue time;
    public static BooleanValue tabgui;
    public static BooleanValue org;
    public static BooleanValue rainbow;

    public Overlay() {
        super("Overlay", Module.Category.Render, -1);
        this.setBind(0);
        hue = new NumberValue("Hue", 25.0, 1.0, 255.0, 1.0);
        this.addValue(hue);
        watermark = new BooleanValue("Watermark", true);
        this.addValue(watermark);
        time = new BooleanValue("Time", true);
        this.addValue(time);
        tabgui = new BooleanValue("Tabbed Gui", true);
        this.addValue(tabgui);
        org = new BooleanValue("Organized", true);
        this.addValue(org);
        rainbow = new BooleanValue("Rainbow", true);
        this.addValue(rainbow);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        this.visible(false);
    }
}

