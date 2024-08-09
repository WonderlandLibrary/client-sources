package xyz.mathax.mathaxclient.systems.modules.misc;

import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.settings.StringSetting;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class NameProtect extends Module {
    private String username = "NULL";

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<String> nameSetting = generalSettings.add(new StringSetting.Builder()
            .name("Name")
            .description("Name to be replaced with.")
            .defaultValue("Matejko06")
            .build()
    );

    public NameProtect(Category category) {
        super(category, "Name Protect", "Hides your name client-side.");
    }

    @Override
    public void onEnable() {
        username = mc.getSession().getUsername();
    }

    public String replaceName(String text) {
        if (isEnabled() && text != null) {
            return text.replace(username, nameSetting.get());
        }

        return text;
    }

    public String getName() {
        return getName(mc.getSession().getUsername());
    }

    public String getName(String original) {
        if (isEnabled() && nameSetting.get().length() > 0) {
            return nameSetting.get();
        }

        return original;
    }
}