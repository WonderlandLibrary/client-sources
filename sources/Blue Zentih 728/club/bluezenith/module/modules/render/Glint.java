package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;

import static club.bluezenith.util.render.ColorUtil.rainbow;

public class Glint extends Module {

    public static Glint glint;

    public final BooleanValue rainbow = new BooleanValue("Rainbow", false).setIndex(-1);
    public final ColorValue color = new ColorValue("Glint color").setIndex(1).showIf(() -> !rainbow.get());

    public Glint() {
        super("Glint", ModuleCategory.RENDER);
        glint = this;
    }

    public int color() {
        return rainbow.get() ? rainbow(1, 0.5F, 0.8F, 0.9F).getRGB() : color.getRGB();
    }
}
