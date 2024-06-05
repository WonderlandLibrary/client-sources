package digital.rbq.module.implement.Render;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.ColorValue;
import digital.rbq.module.value.ModeValue;

import java.awt.*;

public class EnchantEffect extends Module {
    public EnchantEffect() {
        super("EnchantEffect", Category.Render, false);
    }

    public static ColorValue enchantEffectColours = new ColorValue("EnchantEffect", "Enchant Glint", Color.RED);
}
