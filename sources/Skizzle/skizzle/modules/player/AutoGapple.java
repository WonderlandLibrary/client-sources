/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAppleGold;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class AutoGapple
extends Module {
    public boolean isEating;
    public NumberSetting health;
    public Timer timer2;
    public Timer timer = new Timer();

    @Override
    public void onEvent(Event Nigga) {
        int Nigga2;
        AutoGapple Nigga3;
        if (Nigga instanceof EventUpdate && Nigga.isPre() && !Client.ghostMode && (double)Nigga3.mc.thePlayer.getHealth() < Nigga3.health.getValue()) {
            for (Nigga2 = 0; Nigga2 < 9; ++Nigga2) {
                if (Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga2) == null || !(Nigga3.mc.thePlayer.inventory.getStackInSlot(Nigga2).getItem() instanceof ItemAppleGold)) continue;
                Nigga3.mc.thePlayer.inventory.currentItem = Nigga2;
                if (Nigga3.isEating || !Nigga3.timer2.hasTimeElapsed((long)-869826570 ^ 0xFFFFFFFFCC277EDAL, true)) continue;
                ModuleManager.autoSword.overrideSwitch = true;
                KeyBinding.setKeyBindState(Nigga3.mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                Nigga3.isEating = true;
                Nigga3.timer.reset();
            }
        }
        Nigga2 = 300;
        if (!ModuleManager.fastEat.isEnabled()) {
            Nigga2 = 2000;
        }
        if (Nigga3.timer.hasTimeElapsed(Nigga2, true) && Nigga3.isEating) {
            Nigga3.isEating = false;
            KeyBinding.setKeyBindState(Nigga3.mc.gameSettings.keyBindUseItem.getKeyCode(), false);
            ModuleManager.autoSword.overrideSwitch = false;
        }
    }

    public AutoGapple() {
        super(Qprot0.0("\uee55\u71de\ud51c\ua7eb\uc1ac\u6bcf\u8c3f\u8221\u570e\u6ac4"), 0, Module.Category.PLAYER);
        AutoGapple Nigga;
        Nigga.timer2 = new Timer();
        Nigga.health = new NumberSetting(Qprot0.0("\uee5c\u71ce\ud509\ua7e8\uc19f\u6bc6"), 10.0, 2.0, 20.0, 1.0);
        Nigga.addSettings(Nigga.health);
    }

    public static {
        throw throwable;
    }
}

