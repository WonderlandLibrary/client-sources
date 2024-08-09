package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ItemScroller extends Feature
{
    public static OptionSlider delay;
    
    public ItemScroller() {
        super("ItemScroller", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0431\u044b\u0441\u0442\u0440\u043e \u0437\u0430\u0431\u0438\u0440\u0430\u0442\u044c/\u0441\u043a\u043b\u0430\u0434\u044b\u0432\u0430\u0442\u044c \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0437\u0430\u0436\u0438\u043c\u0430\u044f \u041b\u041a\u041c + Shift.", Category.ANOTHER);
        ItemScroller.delay = new OptionSlider(this, "Delay", 0.0f, 0.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.addOptions(ItemScroller.delay);
    }
}
