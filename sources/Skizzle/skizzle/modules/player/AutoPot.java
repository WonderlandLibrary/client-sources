/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.player;

import net.minecraft.item.ItemPotion;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class AutoPot
extends Module {
    public boolean isEating;
    public Timer timer = new Timer();
    public float pitch;
    public NumberSetting health;
    public Timer timer2 = new Timer();
    public int slot;

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion) {
            EventMotion Nigga2 = (EventMotion)Nigga;
            if (!Client.ghostMode) {
                for (int Nigga3 = 0; Nigga3 < 9; ++Nigga3) {
                    AutoPot Nigga4;
                    if (Nigga4.mc.thePlayer.inventory.getStackInSlot(Nigga3) == null || !(Nigga4.mc.thePlayer.inventory.getStackInSlot(Nigga3).getItem() instanceof ItemPotion)) continue;
                    ItemPotion Nigga5 = (ItemPotion)Nigga4.mc.thePlayer.inventory.getStackInSlot(Nigga3).getItem();
                    if (!ItemPotion.isSplash(Nigga4.mc.thePlayer.inventory.getStackInSlot(Nigga3).getMetadata())) continue;
                    String Nigga6 = "" + Nigga5.getEffects(Nigga4.mc.thePlayer.inventory.getStackInSlot(Nigga3)).get(0);
                    String Nigga7 = Nigga6.split(Qprot0.0("\u40b8\u718b"))[0];
                    if (!((double)Nigga4.mc.thePlayer.getHealth() < Nigga4.health.getValue() && (Nigga7.startsWith(Qprot0.0("\u40e4\u71c4\u7b9c\ue264\ub48d\uc540\u8c61\u2ca3\u128e\u1fcf\u4f66\uaf02\uc26f\u37d6\uc0ba\udce5\u42e0\uf69c\u5287")) && !Nigga4.mc.thePlayer.isPotionActive(10) || Nigga7.startsWith(Qprot0.0("\u40e4\u71c4\u7b9c\ue264\ub48d\uc540\u8c61\u2cb9\u128e\u1fc9\u4f6f")))) && (!Nigga7.startsWith(Qprot0.0("\u40e4\u71c4\u7b9c\ue264\ub48d\uc540\u8c61\u2cbc\u1284\u1fde\u4f66\uaf3f\uc27a\u37c1\uc0be\udcf5")) || Nigga4.mc.thePlayer.isPotionActive(1))) continue;
                    Nigga4.slot = Nigga4.mc.thePlayer.inventory.currentItem;
                    Nigga2.setPitch(Float.intBitsToFloat(1.03661248E9f ^ 0x7F63737E));
                    if (Nigga2.getPitch() != Float.intBitsToFloat(1.03382458E9f ^ 0x7F34E930) || !Nigga2.isPost() || !Nigga2.isPost()) continue;
                    Nigga4.timer2.reset();
                    ModuleManager.autoSword.overrideSwitch = true;
                    Nigga4.mc.thePlayer.inventory.currentItem = Nigga3;
                    Nigga4.isEating = true;
                    Nigga4.mc.rightClickMouse();
                    ModuleManager.autoSword.overrideSwitch = false;
                    Nigga4.mc.thePlayer.inventory.currentItem = Nigga4.slot;
                }
            }
        }
    }

    public static {
        throw throwable;
    }

    public AutoPot() {
        super(Qprot0.0("\u40d5\u71de\u7b9c\ua7eb\u6a3b\uc541\u8c3b"), 0, Module.Category.PLAYER);
        AutoPot Nigga;
        Nigga.health = new NumberSetting(Qprot0.0("\u40dc\u71ce\u7b89\ua7e8\u6a1f\uc546"), 10.0, 2.0, 20.0, 1.0);
        Nigga.addSettings(Nigga.health);
    }
}

