package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AntiAim extends Feature
{
    final OptionMode mode;
    final OptionMode side;
    float pitch;
    float yaw;
    
    @Override
    public void onDisable() {
        AntiAim.mc.h.aN = AntiAim.mc.h.aP;
        super.onDisable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode() + " - " + this.side.getMode());
        if (Main.isEnabledFeature("KillAura")) {
            return;
        }
        final String mode = this.mode.getMode();
        switch (mode) {
            case "Random": {
                this.pitch = MathUtils.nextFloat(-90.0f, 90.0f);
                this.yaw = MathUtils.nextFloat(-180.0f, 180.0f);
                break;
            }
            case "Jitter": {
                this.yaw = AntiAim.mc.h.v + MathUtils.nextFloat(-5.0f, 5.0f);
                this.pitch = rk.a(AntiAim.mc.h.w + MathUtils.nextFloat(-5.0f, 5.0f), -90.0f, 90.0f);
                break;
            }
            case "Backwards": {
                this.yaw = AntiAim.mc.h.v - 180.0f;
                break;
            }
        }
        if (this.side.isMode("Server")) {
            eventMotion.yaw = this.yaw;
            eventMotion.pitch = this.pitch;
        }
        AntiAim.mc.h.aP = this.yaw;
        AntiAim.mc.h.aN = this.yaw;
        AntiAim.mc.h.aO = this.yaw;
    }
    
    public AntiAim() {
        super("AntiAim", "\u041f\u043e\u0432\u043e\u0440\u0430\u0447\u0438\u0432\u0430\u0435\u0442 \u0442\u0435\u043b\u043e \u0438\u0433\u0440\u043e\u043a\u0430.", Category.ANOTHER);
        this.side = new OptionMode(this, "Side", "Server", new String[] { "Server", "Client" }, 0);
        this.mode = new OptionMode(this, "Mode", "Random", new String[] { "Random", "Jitter", "Backwards" }, 0);
        this.addOptions(this.side, this.mode);
    }
}
