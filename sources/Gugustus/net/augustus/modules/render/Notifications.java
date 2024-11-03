package net.augustus.modules.render;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.StringValue;
import net.lenni0451.eventapi.reflection.EventTarget;

import java.awt.*;

public class Notifications extends Module {
    public StringValue mode = new StringValue(1, "Mode", this, "Xenza", new String[]{"Xenza", "Rise5", "New"});

    public Notifications() {
        super("Notifications", Color.green, Categorys.RENDER);
    }
}
