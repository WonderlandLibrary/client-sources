package mathax.client.systems.modules.misc;

import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.StringSetting;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NameProtect extends Module {
    private String username = "NULL";

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<String> name = sgGeneral.add(new StringSetting.Builder()
        .name("name")
        .description("Name to be replaced with.")
        .defaultValue("Envy User")
        .build()
    );

    public NameProtect() {
        super(Categories.Misc, Items.NAME_TAG, "name-protect", "Hides your username client side.");
    }

    @Override
    public boolean onActivate() {
        username = mc.getSession().getUsername();
        return false;
    }

    public String replaceName(String string) {
        if (string != null && isActive()) return string.replace(username, name.get());

        return string;
    }

    public String getName(String original) {
        if (name.get().length() > 0 && isActive()) return name.get();

        return original;
    }
}
