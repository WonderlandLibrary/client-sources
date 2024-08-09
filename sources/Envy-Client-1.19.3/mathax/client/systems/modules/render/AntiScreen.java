package mathax.client.systems.modules.render;

import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class AntiScreen extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Boolean> endScreen = sgGeneral.add(new BoolSetting.Builder()
        .name("end-screen")
        .description("Removes the end screen after finishing the game.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> demoScreen = sgGeneral.add(new BoolSetting.Builder()
        .name("demo-screen")
        .description("Removes the demo screen.")
        .defaultValue(true)
        .build()
    );

    // Constructor

    public AntiScreen() {
        super(Categories.Misc, Items.END_PORTAL_FRAME, "anti-screen", "Removes certain screens in the game.");
    }

    // Getter

    public boolean cancelEndScreen() {
        return this.endScreen.get();
    }

    public boolean cancelDemoScreen() {
        return this.demoScreen.get();
    }
}

