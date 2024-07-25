package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.IntegerValue;

public class Chams extends Module {
    public final BooleanValue blend = new BooleanValue("Blend colors", false, true, null).setIndex(1);
    public final ColorValue throughWallsColor = new ColorValue("Non-Visible").setIndex(2);
    public final ColorValue visibleColor = new ColorValue("Visible").setIndex(3);
    public final IntegerValue a = new IntegerValue("Non-Visible Alpha", 255, 0, 255, 5, true, null).setIndex(4).showIf(blend::get);
    public final IntegerValue a2 = new IntegerValue("Visible Alpha", 255, 0, 255, 5, true, null).setIndex(5).showIf(blend::get);
    public Chams(){
        super("Chams", ModuleCategory.RENDER);
    }
}
