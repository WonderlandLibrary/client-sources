package me.napoleon.napoline.modules.render;

import me.napoleon.napoline.events.EventRender2D;
import me.napoleon.napoline.junk.values.type.Mode;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.client.ClientUtils;

public class Notifications extends Mod {
    public static Mode mod = new Mode("Mode",mode.values(),mode.Black1);
    public Notifications() {
        super("Notifacations", ModCategory.Render, "A noti on right of screen");
        addValues(mod);
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        ClientUtils.INSTANCE.drawNotifications();
    }

    public enum mode{
        Black1,
        Black2,
//        While,//还没写好
//        RainBow,
    }
}
