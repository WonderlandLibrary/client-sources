package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
        name = "Chams",
        category = Category.RENDER
)
public class Chams extends Module {

    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    public final NumberValue<Integer> redValue = new NumberValue<>("Red", 116, 0, 255, 1);
    public final NumberValue<Integer> greenValue = new NumberValue<>("Green", 202, 0, 255, 1);
    public final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);

    public Chams () {
        addSettings(colormodes,redValue, greenValue, blueValue);
    }

}
