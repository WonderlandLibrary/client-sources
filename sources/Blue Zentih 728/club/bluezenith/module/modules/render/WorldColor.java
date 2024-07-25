package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.ColorValue;

public class WorldColor extends Module {

    public WorldColor() {
        super("WorldColor", ModuleCategory.RENDER);
    }

    public final ColorValue color = new ColorValue("Color").setIndex(1);
}
