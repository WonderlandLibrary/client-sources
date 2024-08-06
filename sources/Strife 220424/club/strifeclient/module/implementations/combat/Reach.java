package club.strifeclient.module.implementations.combat;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "Reach", description = "Attack entities from farther away.", category = Category.COMBAT)
public final class Reach extends Module {
    public final DoubleSetting reachSetting = new DoubleSetting("Reach", 3, 1, 6, 0.1);

    @Override
    public Supplier<Object> getSuffix() {
        return reachSetting::getValue;
    }
}
