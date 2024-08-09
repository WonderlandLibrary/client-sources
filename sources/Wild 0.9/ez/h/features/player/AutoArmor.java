package ez.h.features.player;

import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class AutoArmor extends Feature
{
    OptionSlider delay;
    
    boolean shouldEquip(final bib bib) {
        final boolean b = bib.m instanceof bmg;
        final boolean hasReached = this.counter.hasReached(this.delay.getNum());
        return !bib.h.z() && hasReached && !b;
    }
    
    int getArmorValue(final aip aip) {
        if (!aip.isEmpty() && aip.c() instanceof agv) {
            return ((agv)aip.c()).d + alm.a(alo.a, aip);
        }
        return -1;
    }
    
    @EventTarget
    public void onMotion(final EventLivingUpdate eventLivingUpdate) {
        if (this.counter.hasReached(this.delay.getNum()) && AutoArmor.mc.m == null && this.shouldEquip(AutoArmor.mc)) {
            this.equip(AutoArmor.mc);
            this.counter.reset();
        }
    }
    
    public AutoArmor() {
        super("AutoArmor", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043e\u0434\u0435\u0432\u0430\u0435\u0442 \u0431\u0440\u043e\u043d\u044e \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435.", Category.PLAYER);
        this.delay = new OptionSlider(this, "Delay", 250.0f, 1.0f, 2000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.delay);
    }
    
    void equip(final bib bib) {
        final int[] array = { -1, -1, -1, -1 };
        final int[] array2 = { -1, -1, -1, -1 };
        for (int i = 0; i < 4; ++i) {
            final aip g = bib.h.bv.g(i);
            if (g.isEmpty() && g.c() instanceof agv) {
                array2[i] = this.getArmorValue(g);
            }
        }
        for (int j = 9; j < (0x12 ^ 0x3F); ++j) {
            final aip d = bib.h.bx.a(j).d();
            if (!d.isEmpty() && d.c() instanceof agv) {
                if (d.getCount() <= 1) {
                    final int n = ((agv)d.c()).c.ordinal() - 2;
                    if (n != 2 || !bib.h.bv.g(n).c().equals(air.cS)) {
                        final int armorValue = this.getArmorValue(d);
                        if (armorValue > array2[n]) {
                            array[n] = j;
                            array2[n] = armorValue;
                        }
                    }
                }
            }
        }
        for (int k = 0; k < 4; ++k) {
            final int n2 = array[k];
            if (n2 != -1) {
                final aip g2 = bib.h.bv.g(k);
                if ((!g2.isEmpty() || bib.h.bv.k() != -1) && this.getArmorValue(g2) < array2[k]) {
                    bib.c.a(0, 8 - k, 0, afw.b, (aed)bib.h);
                    bib.c.a(0, n2, 0, afw.b, (aed)bib.h);
                    break;
                }
            }
        }
    }
}
