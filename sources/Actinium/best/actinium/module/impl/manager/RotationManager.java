package best.actinium.module.impl.manager;

import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;

@ModuleInfo(
        name = "Rotation Manager",
        description = "i wonder.",
        category = ModuleCategory.MANAGER
)
public class RotationManager extends Module {
    public NumberProperty minRotationSpeed = new NumberProperty("Min Rotation Speed",this,0,5,10,1);
    public NumberProperty maxRotationSpeed = new NumberProperty("Max Rotation Speed",this,0,5,10,1);
    public BooleanProperty moveFix = new BooleanProperty("Move Fix",this,true);
}
