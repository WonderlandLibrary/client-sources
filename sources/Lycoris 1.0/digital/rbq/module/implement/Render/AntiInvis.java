package digital.rbq.module.implement.Render;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.module.value.FloatValue;

public class AntiInvis extends Module {
    public static BooleanValue trancperent = new BooleanValue("AntiInvis", "Transperent", true);
    public static FloatValue transAlpha = new FloatValue("AntiInvis", "transAlpha", 35f, 1f, 100f, 1f,"%");

    public AntiInvis() {
        super("AntiInvis", Category.Render, false);
    }
}
