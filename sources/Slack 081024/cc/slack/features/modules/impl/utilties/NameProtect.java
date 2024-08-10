package cc.slack.features.modules.impl.utilties;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.StringValue;

@ModuleInfo(
        name = "NameProtect",
        category = Category.UTILITIES
)
public class NameProtect extends Module {
    public static final StringValue nametoprotect = new StringValue("Name", "§cSlack User"); // For new clickgui Display value


    public static String nameprotect;

    public static String getNameProtect() {
        return NameProtect.nameprotect;
    }

    static {
        NameProtect.nameprotect = nametoprotect.getValue();
    }

}
