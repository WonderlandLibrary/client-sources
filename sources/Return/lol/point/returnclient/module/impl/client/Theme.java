package lol.point.returnclient.module.impl.client;

import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;

@ModuleInfo(
        name = "Theme",
        description = "manage the theme of the client",
        category = Category.CLIENT,
        hidden = true,
        frozen = true
)
public class Theme extends Module {
    public final StringSetting theme = StringSetting.themeSetting("Theme", "Holiday blue");

    public final NumberSetting iterations = new NumberSetting("Shader Iterations", 3.0, 1.0, 5.0, 0);
    public final NumberSetting offset = new NumberSetting("Shader Offset", 2.0, 1.0, 5.0, 0);
    public final NumberSetting multiplier = new NumberSetting("Shader Multiplier", 1.0, 0.5, 1.0, 2);

    public Theme() {
        addSettings(theme, iterations, offset, multiplier);
    }

    public String getSuffix() {
        return theme.value;
    }
}