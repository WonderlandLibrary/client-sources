package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class KeyPearl extends Feature
{
    OptionBoolean silent;
    
    @EventTarget
    public void onMiddle(final MouseKeyEvent mouseKeyEvent) {
        if (mouseKeyEvent.key != 2) {
            return;
        }
        if (KeyPearl.mc.v() == null) {
            return;
        }
        for (int i = 0; i < 9; ++i) {
            final ain c = KeyPearl.mc.h.bv.a(i).c();
            if (c instanceof aib && !KeyPearl.mc.h.dt().a(c)) {
                Utils.rotationCounter = 5;
                KeyPearl.mc.h.d.a((ht)new lk.c(KeyPearl.mc.h.v, KeyPearl.mc.h.w, KeyPearl.mc.h.z));
                if (this.silent.enabled) {
                    KeyPearl.mc.h.d.a((ht)new lv(i));
                    KeyPearl.mc.h.d.a((ht)new mb(ub.a));
                    KeyPearl.mc.h.d.a((ht)new lv(0));
                }
                else {
                    KeyPearl.mc.h.bv.d = i;
                    KeyPearl.mc.aB();
                    KeyPearl.mc.h.bv.d = 0;
                }
            }
        }
    }
    
    public KeyPearl() {
        super("KeyPearl", "\u041f\u0440\u0438 \u0432\u043a\u043b\u044e\u0447\u0435\u043d\u0438\u0438, \u043a\u0438\u0434\u0430\u0435\u0442 \u044d\u043d\u0434\u0435\u0440-\u0436\u0435\u043c\u0447\u0443\u0433 \u0438\u0437 \u0445\u043e\u0442\u0431\u0430\u0440\u0430.", Category.ANOTHER);
        this.silent = new OptionBoolean(this, "Silent", true);
        this.addOptions(this.silent);
    }
}
