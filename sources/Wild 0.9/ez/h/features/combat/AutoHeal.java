package ez.h.features.combat;

import ez.h.event.events.*;
import java.util.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;

public class AutoHeal extends Feature
{
    OptionSlider delay;
    Counter refillCounter;
    public static boolean healing;
    OptionSlider refillSlot;
    OptionBoolean refill;
    OptionSlider onHealth;
    OptionSlider refillDelay;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        if (this.refillCounter.hasReached(this.refillDelay.getNum()) && this.refill.enabled) {
            for (final agr agr : AutoHeal.mc.h.bx.c) {
                if (agr.d().c() instanceof ajx && this.isStackHealingItem(agr.d()) && !(AutoHeal.mc.h.bv.a((int)this.refillSlot.getNum()).c() instanceof ajx)) {
                    this.swap(agr.e, (int)this.refillSlot.getNum());
                }
            }
            this.refillCounter.reset();
        }
        if (AutoHeal.mc.h.cd() > this.onHealth.getNum()) {
            return;
        }
        if (this.getSlot() == -1) {
            return;
        }
        AutoHeal.healing = true;
        if (this.counter.hasReached(this.delay.getNum())) {
            Utils.rotationCounter = 5;
            AutoHeal.mc.h.d.a((ht)new lk.c(AutoHeal.mc.h.v, 90.0f, AutoHeal.mc.h.z));
            AutoHeal.mc.h.d.a((ht)new lv(this.getSlot()));
            AutoHeal.mc.h.d.a((ht)new mb(ub.a));
            AutoHeal.mc.h.d.a((ht)new lv(AutoHeal.mc.h.bv.d));
            this.counter.reset();
        }
        AutoHeal.healing = false;
    }
    
    @Override
    public void updateElements() {
        this.refillDelay.display = this.refill.enabled;
        this.refillSlot.display = this.refill.enabled;
        super.updateElements();
    }
    
    public AutoHeal() {
        super("AutoHeal", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043a\u0438\u0434\u0430\u0435\u0442 \u043f\u043e\u0434 \u0432\u0430\u0441 \u0437\u0435\u043b\u044c\u0435 \u0438\u0441\u0446\u0435\u043b\u0435\u043d\u0438\u044f.", Category.COMBAT);
        this.refillCounter = new Counter();
        this.onHealth = new OptionSlider(this, "On Health", 15.0f, 1.0f, 20.0f, OptionSlider.SliderType.NULLINT);
        this.delay = new OptionSlider(this, "Delay", 50.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.refill = new OptionBoolean(this, "Refill", true);
        this.refillSlot = new OptionSlider(this, "Refill Slot", 4.0f, 1.0f, 9.0f, OptionSlider.SliderType.NULLINT);
        this.refillDelay = new OptionSlider(this, "Refill Delay", 50.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.onHealth, this.delay, this.refill, this.refillSlot, this.refillDelay);
    }
    
    int getSlot() {
        for (int i = 0; i < 9; ++i) {
            if (this.isStackHealingItem(AutoHeal.mc.h.bv.a(i))) {
                return i;
            }
        }
        return -1;
    }
    
    void swap(final int n, final int n2) {
        if (n2 == n - (0x2B ^ 0xF)) {
            return;
        }
        AutoHeal.mc.h.d.a((ht)new lq((vg)AutoHeal.mc.h, lq.a.h));
        InventoryUtils.swap(n, n2);
    }
    
    boolean isStackHealingItem(final aip aip) {
        if (aip == null) {
            return false;
        }
        final ain c = aip.c();
        if (c == air.bI) {
            final Iterator<va> iterator = aki.a(aip).iterator();
            while (iterator.hasNext()) {
                if (iterator.next().a() == uz.a(6)) {
                    return true;
                }
            }
        }
        return c == air.D;
    }
}
