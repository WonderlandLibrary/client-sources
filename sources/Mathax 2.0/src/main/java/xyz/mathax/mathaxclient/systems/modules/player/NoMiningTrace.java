package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.item.PickaxeItem;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class NoMiningTrace extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Boolean> onlyWhenHoldingPickaxeSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Only when holding a pickaxe")
            .description("Whether or not to work only when holding a pickaxe.")
            .defaultValue(true)
            .build()
    );

    public NoMiningTrace(Category category) {
        super(category, "No Mining Trace", "Allows you to mine blocks through entities.");
    }

    public boolean canWork() {
        if (!isEnabled()) {
            return false;
        }

        if (onlyWhenHoldingPickaxeSetting.get()) {
            return mc.player.getMainHandStack().getItem() instanceof PickaxeItem || mc.player.getOffHandStack().getItem() instanceof PickaxeItem;
        }

        return true;
    }
}
