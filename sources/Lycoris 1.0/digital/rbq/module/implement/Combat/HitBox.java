package digital.rbq.module.implement.Combat;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.FloatValue;

public class HitBox extends Module {
    public static FloatValue size = new FloatValue("HitBox", "Size", 0.3f, 0.1f, 0.4f, 0.1f);

    public HitBox() {
        super("HitBox", Category.Ghost, false);
    }
}
