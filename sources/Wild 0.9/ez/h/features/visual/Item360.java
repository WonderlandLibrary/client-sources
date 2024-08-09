package ez.h.features.visual;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Item360 extends Feature
{
    public static OptionSlider speed;
    public static OptionSlider x;
    public static OptionSlider y;
    public static OptionSlider z;
    
    public Item360() {
        super("Item360", "\u0412\u0440\u0430\u0449\u0430\u0435\u0442 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b \u0432 \u0440\u0443\u043a\u0430\u0445.", Category.VISUAL);
        Item360.x = new OptionSlider(this, "X Factor", 1.0f, -1.0f, 1.0f, OptionSlider.SliderType.NULL);
        Item360.y = new OptionSlider(this, "Y Factor", 0.0f, -1.0f, 1.0f, OptionSlider.SliderType.NULL);
        Item360.z = new OptionSlider(this, "Z Factor", 0.0f, -1.0f, 1.0f, OptionSlider.SliderType.NULL);
        Item360.speed = new OptionSlider(this, "Speed", 4.0f, 0.0f, 10.0f, OptionSlider.SliderType.NULL);
        this.addOptions(Item360.x, Item360.y, Item360.z, Item360.speed);
    }
}
