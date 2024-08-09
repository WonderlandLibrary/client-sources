package ez.h.features.combat;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AutoTotem extends Feature
{
    OptionSlider minDelay;
    public static boolean swapping;
    long lastDelay;
    OptionMode swapBack;
    OptionSlider randomDelay;
    OptionBoolean explosion;
    int prevSlot;
    ain prevItem;
    public static OptionSlider health;
    int go;
    
    public int getItemSlot(final ain ain) {
        for (int i = 0; i < (0x10 ^ 0x3D); ++i) {
            if (AutoTotem.mc.h.bx.a(i).d().c() == ain) {
                return i;
            }
        }
        return -1;
    }
    
    static {
        AutoTotem.swapping = false;
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.swapBack.getMode() + " | " + AutoTotem.health.getNum());
        this.go = 0;
        if (this.explosion.enabled && AutoTotem.mc.f.e.stream().filter(vg -> vg instanceof acm || vg instanceof afm || (vg instanceof abc && vg.q - 0.7 < AutoTotem.mc.h.q)).anyMatch(vg2 -> AutoTotem.mc.h.getDistance(vg2) <= 7.0f)) {
            ++this.go;
        }
        if (this.go > 0 || AutoTotem.mc.h.cd() + AutoTotem.mc.h.cD() <= AutoTotem.health.getNum()) {
            final int itemSlot = this.getItemSlot(air.TOTEM);
            if (AutoTotem.mc.h.cp().c() == air.TOTEM) {
                return;
            }
            if (itemSlot != -1 && this.counter.hasReached((float)this.lastDelay)) {
                AutoTotem.swapping = true;
                this.prevItem = AutoTotem.mc.h.cp().c();
                AutoTotem.mc.c.a(0, itemSlot, 0, afw.a, (aed)AutoTotem.mc.h);
                AutoTotem.mc.c.a(0, 0x4E ^ 0x63, 0, afw.a, (aed)AutoTotem.mc.h);
                AutoTotem.mc.c.a(0, itemSlot, 0, afw.a, (aed)AutoTotem.mc.h);
                this.prevSlot = itemSlot;
                AutoTotem.swapping = false;
                this.counter.reset();
                this.lastDelay = (long)(this.minDelay.getNum() + this.randomDelay.getNum() * Math.random());
            }
        }
        else {
            if (this.swapBack.isMode("None")) {
                return;
            }
            final String mode = this.swapBack.getMode();
            ain ain = null;
            switch (mode) {
                case "Gapple": {
                    ain = air.ar;
                    break;
                }
                case "Shield": {
                    ain = air.cR;
                    break;
                }
                default: {
                    ain = this.prevItem;
                    break;
                }
            }
            if (this.swapBack.isMode("Gapple") && AutoTotem.mc.h.bx.b.stream().anyMatch(aip -> aip.c() == air.ar)) {
                ain = air.cR;
            }
            if (this.swapBack.isMode("Shield") && AutoTotem.mc.h.bx.b.stream().anyMatch(aip2 -> aip2.c() == air.cR)) {
                ain = air.ar;
            }
            if (ain == null && this.swapBack.isMode("Prev Item")) {
                return;
            }
            if (AutoTotem.mc.h.cp().c() == ain) {
                return;
            }
            this.prevSlot = this.getItemSlot(ain);
            if (this.counter.hasReached((float)this.lastDelay) && this.prevSlot != -1) {
                AutoTotem.swapping = true;
                AutoTotem.mc.c.a(0, this.prevSlot, 0, afw.a, (aed)AutoTotem.mc.h);
                AutoTotem.mc.c.a(0, 0x32 ^ 0x1F, 0, afw.a, (aed)AutoTotem.mc.h);
                AutoTotem.mc.c.a(0, this.prevSlot, 0, afw.a, (aed)AutoTotem.mc.h);
                AutoTotem.swapping = false;
                this.lastDelay = (long)(this.minDelay.getNum() + this.randomDelay.getNum() * Math.random());
                this.prevItem = null;
                this.counter.reset();
            }
        }
    }
    
    public AutoTotem() {
        super("AutoTotem", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0431\u0435\u0440\u0451\u0442 \u0442\u043e\u0442\u0435\u043c \u0432 \u043b\u0435\u0432\u0443\u044e \u0440\u0443\u043a\u0443 \u043f\u0440\u0438 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0451\u043d\u043d\u043e\u043c \u0443\u0440\u043e\u0432\u043d\u0435 \u0437\u0434\u043e\u0440\u043e\u0432\u044c\u044f.", Category.COMBAT);
        this.go = 0;
        this.prevSlot = -1;
        this.lastDelay = 100L;
        AutoTotem.health = new OptionSlider(this, "Health", 10.0f, 1.0f, 20.0f, OptionSlider.SliderType.NULLINT);
        this.explosion = new OptionBoolean(this, "Explosion", true);
        this.minDelay = new OptionSlider(this, "Min Delay", 100.0f, 1.0f, 500.0f, OptionSlider.SliderType.MS);
        this.randomDelay = new OptionSlider(this, "Random Delay", 100.0f, 1.0f, 500.0f, OptionSlider.SliderType.MS);
        this.swapBack = new OptionMode(this, "SwapBack", "Prev Item", new String[] { "Prev Item", "Gapple", "Shield", "None" }, 0);
        this.addOptions(AutoTotem.health, this.explosion, this.swapBack, this.minDelay, this.randomDelay);
    }
}
