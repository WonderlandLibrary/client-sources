package club.strifeclient.module.implementations.player;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.DoubleSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "FastUse", description = "Change how fast you can place blocks.", category = Category.PLAYER)
public final class FastUse extends Module {
    public final BooleanSetting onlyBlocksSetting = new BooleanSetting("Only Blocks", false);
    public final DoubleSetting delaySetting  = new DoubleSetting("Delay", 3, 0, 4, 1);

    @Override
    public Supplier<Object> getSuffix() {
        return delaySetting::getInt;
    }
}
