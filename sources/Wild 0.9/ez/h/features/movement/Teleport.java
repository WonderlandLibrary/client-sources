package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import java.awt.*;
import ez.h.features.another.*;
import ez.h.event.*;
import ez.h.event.events.*;

public class Teleport extends Feature
{
    double z;
    OptionBoolean bedwars;
    double y;
    double x;
    
    public Teleport() {
        super("Teleport", "\u0422\u0435\u043b\u0435\u043f\u043e\u0440\u0442\u0438\u0440\u0443\u0435\u0442 \u0432\u0430\u0441 \u043f\u0435\u0440\u0432\u043e\u043d\u0430\u0447\u0430\u043b\u044c\u043d\u0443\u044e \u043f\u043e\u0437\u0438\u0446\u0438\u044e \u043f\u0440\u0438 \u043d\u0430\u0436\u0430\u0442\u0438\u0438 \u043d\u0430 Shift.", Category.MOVEMENT);
        this.bedwars = new OptionBoolean(this, "Bedwars", true);
        this.addOptions(this.bedwars);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.x = Teleport.mc.h.p;
        this.y = Teleport.mc.h.q;
        this.z = Teleport.mc.h.r;
        Notifications.addNotification("Position saved", "Press LSHIFT for teleport", new Color(-1), new Color(-1));
        Teleport.mc.h.d.a((ht)new lk.a(Teleport.mc.h.p, Teleport.mc.h.q, Teleport.mc.h.r, true));
    }
    
    @EventTarget
    public void onPreUpdate(final EventMotion eventMotion) {
        eventMotion.setOnGround(false);
        Teleport.mc.h.f(false);
        if (Teleport.mc.t.Y.e()) {
            if (Teleport.mc.h.T % 2 == 0) {
                if (this.bedwars.enabled) {
                    Teleport.mc.h.d.a((ht)new lk.a(Teleport.mc.h.p, Teleport.mc.h.q * Teleport.mc.h.p + Teleport.mc.h.q + Teleport.mc.h.r, Teleport.mc.h.r, false));
                    Teleport.mc.h.d.a((ht)new lk.a(this.x, this.y, this.z, true));
                }
                else {
                    Teleport.mc.h.d.a((ht)new lk.a(Teleport.mc.h.p, Teleport.mc.h.q + 100.0, Teleport.mc.h.r, false));
                }
            }
            Teleport.mc.h.d.a((ht)new lk.a(this.x, 1.0, this.z, true));
        }
        if (Teleport.mc.h.bO.b && this.bedwars.enabled) {
            Teleport.mc.h.b(this.x, this.y, this.z);
            Teleport.mc.h.a(this.x, this.y, this.z);
        }
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D eventRender3D) {
    }
}
