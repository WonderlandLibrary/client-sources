package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

@ModuleAnnotation(name = "ItemAnimate", category = Category.RENDER)
public class ItemAnimate extends Module {
    public static ModeSetting mode = new ModeSetting("Animation", "Smooth", "Smooth", "Self", "Block", "Block Old");
    public static NumberSetting angle = new NumberSetting("Angle", 100, 0, 360, 1, () -> mode.is("Self") || mode.is("Block"));
    public static NumberSetting swipePower = new NumberSetting("Swipe Power", 70, 10, 100, 5, () -> mode.is("Self") || mode.is("Block"));
    public static NumberSetting swipeSpeed = new NumberSetting("Swipe Speed", 6, 1, 20, 1);
    public static BooleanSetting onlyAura = new BooleanSetting("Only Aura", false);
    public static BooleanSetting spin = new BooleanSetting("Spin", false);
    public static ModeSetting spinMode = new ModeSetting("Spin Mode", "Horizontal", () -> spin.get(), "Horizontal", "Vertical", "Zoom");
    public static ModeSetting spinHand = new ModeSetting("Spin Hand", "All", () -> spin.get(), "All", "Left", "Right");
    public static NumberSetting spinSpeed = new NumberSetting("Spin Speed", 8, 1, 15, 1, () -> spin.get());
    public static BooleanSetting stopOnHit = new BooleanSetting("Stop On Hit", true, () -> spin.get());
}