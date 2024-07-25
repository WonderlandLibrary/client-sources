package club.bluezenith.module.modules.render;

import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;

public class AntiBlind extends Module {
    public BooleanValue noPumpkin = new BooleanValue("Pumpkins", false, true, null).setIndex(1);
    public BooleanValue noBlindnessFog = new BooleanValue("Blindness", false, true, null).setIndex(2);
    public BooleanValue noLavaFog = new BooleanValue("Lava fog", false, true, null).setIndex(3);
    public BooleanValue noWaterFog = new BooleanValue("Water fog", false, true, null).setIndex(4);
    public AntiBlind() {
        super("AntiBlind", ModuleCategory.RENDER);
    }
}
