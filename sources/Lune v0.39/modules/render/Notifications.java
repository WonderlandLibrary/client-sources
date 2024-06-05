package me.superskidder.lune.modules.render;

import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.values.type.Mode;

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
