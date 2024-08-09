package ez.h.features.another;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class SelfDamage extends Feature
{
    int jumps;
    OptionMode mode;
    
    public SelfDamage() {
        super("SelfDamage", "\u0412\u044b \u043f\u043e\u043b\u0443\u0447\u0430\u0435\u0442\u0435 \u0443\u0440\u043e\u043d \u0441 \u043c\u0435\u0441\u0442\u0430.", Category.ANOTHER);
        this.mode = new OptionMode(this, "Mode", "Jump", new String[] { "Jump", "Sunrise" }, 0);
        this.addOptions(this.mode);
    }
    
    @Override
    public void onEnable() {
        this.jumps = 0;
        super.onEnable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.mode.isMode("Jump")) {
            if (this.jumps < 3) {
                SelfDamage.mc.Y.b = 4.0f;
            }
            eventMotion.setOnGround(false);
            if (SelfDamage.mc.h.z) {
                if (this.jumps < 3) {
                    SelfDamage.mc.h.cu();
                    ++this.jumps;
                }
                else {
                    this.toggle();
                }
            }
        }
        else {
            SelfDamage.mc.h.z = false;
            if (SelfDamage.mc.h.T % 2 == 0) {
                eventMotion.y += 0.009999999776482582;
            }
            else {
                eventMotion.y -= 9.999999747378752E-5;
            }
            if (SelfDamage.mc.h.L > 3.0f || SelfDamage.mc.h.ay != 0) {
                this.toggle();
            }
        }
    }
}
