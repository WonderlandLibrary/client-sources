package ez.h.features.visual;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class EnchantColor extends Feature
{
    float hue;
    public static OptionColor color;
    public static OptionBoolean rainbow;
    
    public EnchantColor() {
        super("EnchantColor", "\u041c\u0435\u043d\u044f\u0435\u0442 \u0446\u0432\u0435\u0442 \u0437\u0430\u0447\u0430\u0440\u043e\u0432\u0430\u043d\u0438\u0439.", Category.VISUAL);
        EnchantColor.color = new OptionColor(this, "Color", new Color(0, 4 + 161 - 98 + 133, 124 + 144 - 148 + 80));
        EnchantColor.rainbow = new OptionBoolean(this, "Rainbow", false);
        this.addOptions(EnchantColor.color, EnchantColor.rainbow);
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix("R: " + EnchantColor.color.getColor().getRed() + " G:" + EnchantColor.color.getColor().getGreen() + " B:" + EnchantColor.color.getColor().getBlue());
        if (!EnchantColor.rainbow.enabled) {
            return;
        }
        this.hue += (float)0.01;
        EnchantColor.color.setColor(new Color(Color.getHSBColor(this.hue, 0.69f, 0.8f).getRGB()));
        if (this.hue > 1.0f) {
            this.hue = 0.0f;
        }
    }
}
