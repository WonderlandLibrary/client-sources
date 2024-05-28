package arsenic.module.impl.other;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventTick;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;

@ModuleInfo(name = "CustomFOV", category = ModuleCategory.Other )
public class CustomFOV extends Module {
    public final DoubleProperty fov = new DoubleProperty("FOV", new DoubleValue(0, 180, 90, 1));
    //Incredibly complicated module isn't it
    @EventLink
    public final Listener<EventTick> onTick = event -> {
            mc.gameSettings.fovSetting = ((float)fov.getValue().getInput());
    };
}
