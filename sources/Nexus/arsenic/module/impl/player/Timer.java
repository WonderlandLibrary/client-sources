package arsenic.module.impl.player;

import arsenic.injection.accessor.IMixinMinecraft;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;


@ModuleInfo(name = "Timer", category = ModuleCategory.Player)
public class Timer extends Module {

    public final DoubleProperty multiplier = new DoubleProperty("Multiplier", new DoubleValue(0, 5, 1, 0.1)) {
        @Override
        public void onValueUpdate() {
            onEnable();
        }
    };

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
