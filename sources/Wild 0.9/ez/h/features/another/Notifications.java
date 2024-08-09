package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.event.*;
import java.util.stream.*;
import java.util.*;
import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import ez.h.*;
import ez.h.ui.fonts.*;
import ez.h.utils.*;

public class Notifications extends Feature
{
    public static OptionColor rectColor;
    public static OptionBoolean shadow;
    static ArrayList<Notification> notifs;
    
    static {
        Notifications.notifs = new ArrayList<Notification>();
    }
    
    @EventTarget
    public void onRender(final EventRender2D eventRender2D) {
        Notifications.notifs.removeIf(notification -> notification.age > 500.0f);
        if (Notifications.notifs.size() == 0) {
            return;
        }
        bus.g();
        Notifications.notifs.forEach(Notification::draw);
    }
    
    @Override
    public void deltaTickEvent() {
        if (Notifications.notifs.size() == 0) {
            return;
        }
        for (final Notification notification2 : Notifications.notifs) {
            for (int i = 0; i < Notifications.notifs.size(); ++i) {
                final Notification notification3 = notification2;
                notification3.age += 2.0f;
            }
            notification2.x = ((notification2.age <= 300.0f) ? ((int)MathUtils.lerp((float)notification2.x, 30.0f, 0.05f)) : ((int)MathUtils.lerp((float)notification2.x, (float)(-notification2.width * 2), 0.05f)));
            notification2.y = (int)MathUtils.lerp((float)notification2.y, (float)(Notifications.notifs.stream().filter(notification -> notification.age <= 299.0f).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()).indexOf(notification2) * (0x72 ^ 0x6C) + (0x4B ^ 0x77)), 0.2f);
        }
        super.deltaTickEvent();
    }
    
    public Notifications() {
        super("Notifications", "\u041f\u043e\u043a\u0430\u0437\u044b\u0432\u0430\u0435\u0442 \u0443\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u044f \u043d\u0430 \u044d\u043a\u0440\u0430\u043d\u0435.", Category.ANOTHER);
        Notifications.rectColor = new OptionColor(this, "Rect Color", new Color(0x79 ^ 0x66, 0x18 ^ 0x7F, 243 + 62 - 260 + 210), true);
        Notifications.shadow = new OptionBoolean(this, "Shadow", true);
        this.addOptions(Notifications.rectColor, Notifications.shadow);
    }
    
    public static void addNotification(final String s, final String s2, final Color color, final Color color2) {
        if (!Main.aye) {
            return;
        }
        if (!Main.getFeatureByName("Notifications").isEnabled()) {
            return;
        }
        Notifications.notifs.add(new Notification(s, s2, color, color2));
    }
    
    static class Notification
    {
        public Color firstColor;
        public float age;
        public String firstText;
        public int width;
        public String secondText;
        public int x;
        public Color secondColor;
        public int y;
        
        public Notification(final String firstText, final String secondText, final Color firstColor, final Color secondColor) {
            this.firstText = firstText;
            this.secondText = secondText;
            this.firstColor = firstColor;
            this.secondColor = secondColor;
            this.width = Math.max(CFontManager.manrope.getStringWidth(this.firstText), CFontManager.manropesmall.getStringWidth(this.secondText)) + (0xD ^ 0x1);
            this.age = 0.0f;
        }
        
        public void updateAge() {
            ++this.age;
        }
        
        void draw() {
            if (Notifications.shadow.enabled) {
                RenderUtils.drawBlurredShadow((float)(this.x - 2), (float)(this.y - 2), (float)(this.width + 4), 25.0f, 8, new Color(-1962934271, true));
            }
            RenderUtils.drawRectWH((float)this.x, (float)this.y, (float)this.width, 22.0f, new Color(-1962934271, true).getRGB());
            if (Notifications.shadow.enabled) {
                RenderUtils.drawBlurredShadow((float)(this.x - 2), (float)(this.y - 2), 3.0f, 24.0f, 8, Notifications.rectColor.getColor());
            }
            RenderUtils.drawRectWH((float)this.x, (float)this.y, 1.0f, 22.0f, Notifications.rectColor.getColor().getRGB());
            CFontManager.montserrat.drawScaledString(this.firstText, this.x + 4.5f, (float)(this.y + 1), -1, 0.8f, false);
            CFontManager.montserrat.drawScaledString(this.secondText, this.x + 5.5f, (float)(this.y + (0xA ^ 0x1)), new Color(-1).darker().getRGB(), 0.8f, false);
        }
    }
}
