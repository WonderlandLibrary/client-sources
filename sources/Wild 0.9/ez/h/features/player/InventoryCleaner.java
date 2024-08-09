package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import java.io.*;
import ez.h.event.*;

public class InventoryCleaner extends Feature
{
    OptionBoolean potion;
    OptionBoolean sword;
    OptionSlider delay;
    OptionBoolean armor;
    OptionBoolean axe;
    OptionBoolean blocks;
    OptionBoolean food;
    OptionBoolean tools;
    OptionBoolean shield;
    
    public InventoryCleaner() {
        super("InventoryCleaner", "\u041e\u0447\u0438\u0449\u0430\u0435\u0442 \u0432\u0430\u0448 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u044c.", Category.PLAYER);
        this.sword = new OptionBoolean(this, "Sword", true);
        this.blocks = new OptionBoolean(this, "Blocks", true);
        this.tools = new OptionBoolean(this, "Tools", true);
        this.axe = new OptionBoolean(this, "Axe", true);
        this.food = new OptionBoolean(this, "Food", true);
        this.armor = new OptionBoolean(this, "Armor", true);
        this.potion = new OptionBoolean(this, "Potion", true);
        this.shield = new OptionBoolean(this, "Shield", true);
        this.delay = new OptionSlider(this, "Delay", 1.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.addOptions(this.sword, this.axe, this.tools, this.blocks, this.food, this.armor, this.potion, this.shield, this.delay);
    }
    
    @EventTarget
    public void e(final EventMotion eventMotion) throws IOException {
        if (InventoryCleaner.mc.h.bx.b.size() == 0) {
            return;
        }
        if (InventoryCleaner.mc.m != null) {
            return;
        }
        if (this.counter.hasReached(this.delay.getNum())) {
            for (int i = 0; i < InventoryCleaner.mc.h.bx.b.size(); ++i) {
                final ain c = InventoryCleaner.mc.h.bx.a(i).d().c();
                if ((!(c instanceof aij) || !this.food.enabled) && (!(c instanceof agy) || !this.axe.enabled) && (!(c instanceof ahb) || !this.blocks.enabled) && (!(c instanceof ajy) || !this.sword.enabled) && (!(c instanceof ahq) || !this.tools.enabled) && (!(c instanceof agv) || !this.armor.enabled) && ((!(c instanceof ajd) && c != air.bI) || !this.potion.enabled) && (!(c instanceof ajm) || !this.shield.enabled) && !(c instanceof aie)) {
                    if (!c.a().contains("\u0420\u0443\u043d\u0430")) {
                        InventoryCleaner.mc.h.d.a((ht)new lf(InventoryCleaner.mc.h.bx.d, i, 1, afw.e, InventoryCleaner.mc.h.bx.a(i).d(), (short)1));
                        this.counter.reset();
                    }
                }
            }
        }
    }
}
