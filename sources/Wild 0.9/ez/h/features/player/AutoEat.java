package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class AutoEat extends Feature
{
    int slotBefore;
    int eating;
    OptionSlider onHunger;
    int bestSlot;
    
    public AutoEat() {
        super("AutoEat", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0435\u0441\u0442.", Category.PLAYER);
        this.onHunger = new OptionSlider(this, "On Hunger", 18.0f, 1.0f, 20.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.onHunger);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        this.setSuffix(this.onHunger.getNum() + "");
        if (AutoEat.mc.h.bO.d) {
            return;
        }
        if (this.eating < (0x13 ^ 0x3A)) {
            ++this.eating;
            if (this.eating <= 1) {
                AutoEat.mc.h.bv.d = this.bestSlot;
            }
            AutoEat.mc.t.ad.i = true;
            if (this.eating >= (0xE7 ^ 0xC1)) {
                AutoEat.mc.t.ad.k();
                if (this.slotBefore != -1) {
                    AutoEat.mc.h.bv.d = this.slotBefore;
                }
                this.slotBefore = -1;
            }
            return;
        }
        float n = 0.0f;
        this.bestSlot = -1;
        for (int i = 0; i < 9; ++i) {
            final aip a = AutoEat.mc.h.bv.a(i);
            if (!a.isEmpty()) {
                float j = 0.0f;
                if (a.c() instanceof aij) {
                    j = ((aij)a.c()).i(a);
                }
                if (j > n) {
                    n = j;
                    this.bestSlot = i;
                }
            }
        }
        if (this.bestSlot == -1) {
            return;
        }
        if (AutoEat.mc.h.di().a() >= this.onHunger.getNum()) {
            return;
        }
        this.slotBefore = AutoEat.mc.h.bv.d;
        if (this.slotBefore == -1) {
            return;
        }
        this.eating = 0;
    }
    
    @Override
    public void onEnable() {
        this.slotBefore = -1;
        this.bestSlot = -1;
        this.eating = (0x30 ^ 0x19);
        super.onEnable();
    }
    
    boolean isEating() {
        return !AutoEat.mc.h.bO.d && this.eating < (0x9A ^ 0xB3);
    }
}
