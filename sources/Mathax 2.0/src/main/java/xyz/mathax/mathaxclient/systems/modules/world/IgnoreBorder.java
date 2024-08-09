package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class IgnoreBorder extends Module {
    public IgnoreBorder(Category category) {
        super(category, "Ignore Border", "Disables world border restrictions.");
    }
}