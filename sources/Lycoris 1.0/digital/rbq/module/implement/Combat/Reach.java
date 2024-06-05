package digital.rbq.module.implement.Combat;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.module.value.FloatValue;

public class Reach extends Module {
    public static FloatValue range = new FloatValue("Reach", "Range", 4.0f, 3.0f, 6.0f, 0.1f);
    public static BooleanValue block = new BooleanValue("Reach", "Block Interact", true);

    public Reach() {
        super("Reach", Category.Ghost, false);
    }
}
