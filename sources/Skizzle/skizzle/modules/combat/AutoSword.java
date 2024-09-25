/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import skizzle.events.Event;
import skizzle.events.listeners.EventAttack;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class AutoSword
extends Module {
    public NumberSetting delay;
    public Timer timer = new Timer();
    public boolean overrideSwitch = false;

    public int getFreeSlot() {
        for (int Nigga = 35; Nigga > 0; --Nigga) {
            AutoSword Nigga2;
            ItemStack Nigga3 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
            if (Nigga3 != null) continue;
            return Nigga;
        }
        return -1;
    }

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventAttack) {
            AutoSword Nigga2;
            EventAttack cfr_ignored_0 = (EventAttack)Nigga;
            try {
                for (int Nigga3 = 0; Nigga3 < 9; ++Nigga3) {
                    ItemStack Nigga4 = Nigga2.mc.thePlayer.inventory.getCurrentItem();
                    ItemStack Nigga5 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3);
                    if (Nigga5 != null && Nigga4 != null && Nigga5.getItem() instanceof ItemSword && Nigga4.getItem() instanceof ItemSword) {
                        float Nigga6;
                        ItemSword Nigga7 = (ItemSword)Nigga4.getItem();
                        ItemSword Nigga8 = (ItemSword)Nigga5.getItem();
                        float Nigga9 = Nigga2.getDamageAmount(Nigga5) + Nigga8.damageAmount;
                        if (Nigga9 > (Nigga6 = Nigga2.getDamageAmount(Nigga4) + Nigga7.damageAmount) && !Nigga2.overrideSwitch) {
                            Nigga2.mc.thePlayer.inventory.currentItem = Nigga3;
                        }
                    }
                    if (Nigga4 != null && Nigga4.getItem() instanceof ItemSword || Nigga5 == null || !(Nigga5.getItem() instanceof ItemSword) || Nigga2.overrideSwitch) continue;
                    Nigga2.mc.thePlayer.inventory.currentItem = Nigga3;
                }
            }
            catch (Exception Nigga10) {
                System.out.println(Qprot0.0("\uc428\u71df\uff75\ue22d\u3045\u4197\u8c3a\ua842\u129d"));
                Nigga2.mc.thePlayer.messagePlayer(Qprot0.0("\uc42d\u71ce\uff63\ue262\u3055\u4191\u8c6f\ua85e\u1283\u9b04\ucbbb\uaf4c\u4685\u37cb\u443e\u582e\u42e1\u726d\u52c9\u2f15\u3df2\u01c3\ud440\uadff\uf7ec\u793d\u2f5e\u1125\u07dd\ubacf\u2590\u8855\u49ac"));
                Nigga10.printStackTrace();
            }
        }
    }

    public float getDamageAmount(ItemStack Nigga) {
        float Nigga2 = EnchantmentHelper.getEnchantmentLevel(16, Nigga) * 2;
        return Nigga2;
    }

    public AutoSword() {
        super(Qprot0.0("\uc41e\u71de\uff67\ua7eb\uf7ff\u4192\u8c20\ua858\u5706"), 0, Module.Category.COMBAT);
        AutoSword Nigga;
        Nigga.delay = new NumberSetting(Qprot0.0("\uc41b\u71ce\uff7f\ua7e5\uf7d5"), 0.0, 0.0, 1.0, 0.0);
        Nigga.addSettings(Nigga.delay);
    }
}

