package ez.h.features.another;

import java.awt.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class StaffAlert extends Feature
{
    static OptionMode sending;
    
    public static void alert(final String s, final boolean b) {
        if (s.isEmpty()) {
            return;
        }
        if (s.contains("MODER") || s.contains("HELPER") || s.contains("TIGER") || s.contains("ADMIN")) {
            if (StaffAlert.sending.isMode("Notification")) {
                if (b) {
                    Notifications.addNotification("Staff Alert", "Joined " + s, new Color(14642052 + 3554863 - 6153108 + 4667873), new Color(-1));
                }
                if (!b) {
                    Notifications.addNotification("Staff Alert", "Leave " + s, new Color(9322730 + 6601094 - 12427995 + 13216106), new Color(-1));
                }
            }
            if (StaffAlert.sending.isMode("Chat")) {
                StaffAlert.mc.h.a((hh)new ho(a.m + "[STAFF ALERT] " + a.v + (b ? "Joined" : "Leave") + " " + s + a.v));
            }
        }
    }
    
    public StaffAlert() {
        super("StaffAlert", "\u041e\u043f\u043e\u0432\u0435\u0449\u0430\u0435\u0442 \u0432\u0430\u0441 \u043f\u0440\u0438 \u0437\u0430\u0445\u043e\u0434\u0435 \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u0438 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440.", Category.ANOTHER);
        StaffAlert.sending = new OptionMode(this, "Sending", "Notification", new String[] { "Notification", "Chat" }, 0);
        this.addOptions(StaffAlert.sending);
    }
}
