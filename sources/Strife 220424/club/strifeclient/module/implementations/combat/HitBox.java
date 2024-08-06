package club.strifeclient.module.implementations.combat;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.DoubleSetting;

import java.util.function.Supplier;

@ModuleInfo(name = "HitBox", description = "Change the size of player hitboxes.", category = Category.COMBAT)
public final class HitBox extends Module {
    public final DoubleSetting sizeSetting = new DoubleSetting("Size", 0.1, 0.1, 2, 0.1);
    @Override
    public Supplier<Object> getSuffix() {
        return sizeSetting::getValue;
    }
}
