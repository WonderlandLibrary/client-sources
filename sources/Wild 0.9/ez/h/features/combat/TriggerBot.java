package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.managers.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class TriggerBot extends Feature
{
    float lastDelay;
    OptionBoolean crits;
    OptionSlider cps;
    OptionMode clickType;
    OptionBoolean mobs;
    OptionBoolean players;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (TriggerBot.mc.s.d == null) {
            return;
        }
        if (FriendManager.isFriend(TriggerBot.mc.s.d.h_())) {
            return;
        }
        if (this.clickType.isMode("1.9+")) {
            if (TriggerBot.mc.h.n(0.5f) < 1.0f) {
                return;
            }
            if (!this.counter.hasReached(this.lastDelay)) {
                return;
            }
            this.lastDelay = MathUtils.nextFloat(this.getCPSDelay((int)this.cps.getNum())[0], this.getCPSDelay((int)this.cps.getNum())[1]);
            this.counter.reset();
        }
        if (TriggerBot.mc.h.L < 0.1 && !this.crits.enabled) {
            return;
        }
        if (TriggerBot.mc.s.d instanceof aed && !this.players.enabled) {
            return;
        }
        if (TriggerBot.mc.s.d instanceof ade && !this.mobs.enabled) {
            return;
        }
        TriggerBot.mc.c.a((aed)TriggerBot.mc.h, TriggerBot.mc.s.d);
        TriggerBot.mc.h.a(ub.a);
        TriggerBot.mc.h.ds();
    }
    
    public TriggerBot() {
        super("TriggerBot", "\u0410\u0442\u0430\u043a\u0443\u0435\u0442 \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0438 \u043d\u0430 \u043f\u0440\u0438\u0446\u0435\u043b\u0435.", Category.COMBAT);
        this.lastDelay = 100.0f;
        this.clickType = new OptionMode(this, "Click Type", "1.9+", new String[] { "1.9+", "1.8" }, 0);
        this.crits = new OptionBoolean(this, "Criticals", true);
        this.players = new OptionBoolean(this, "Players", true);
        this.mobs = new OptionBoolean(this, "Mobs", false);
        this.cps = new OptionSlider(this, "CPS", 14.0f, 1.0f, 30.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.clickType, this.crits, this.cps, this.players, this.mobs);
    }
    
    float[] getCPSDelay(final int n) {
        if (Math.random() >= 0.9) {
            return new float[] { 300.0f, 350.0f };
        }
        return new float[] { (float)(1000.0f / n - Math.random()), (float)(1000.0f / n + Math.random()) };
    }
}
