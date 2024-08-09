package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ItemPhysics extends Feature
{
    public static OptionSlider rotationSpeed;
    
    public ItemPhysics() {
        super("ItemPhysics", "\u0410\u043d\u0438\u043c\u0438\u0440\u0443\u0435\u0442 \u043f\u0430\u0434\u0435\u043d\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432.", Category.VISUAL);
        ItemPhysics.rotationSpeed = new OptionSlider(this, "Rotation Speed", 1.0f, 0.0f, 1.0f, OptionSlider.SliderType.NULL);
        this.addOptions(ItemPhysics.rotationSpeed);
    }
}
