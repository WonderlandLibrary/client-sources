/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class AutoArmour
extends Module {
    public Timer timer = new Timer();
    public NumberSetting delay = new NumberSetting(Qprot0.0("\u69aa\u71ce\u52ee\ua7e5\u4344"), 120.0, 0.0, 2000.0, 10.0);

    public boolean isChestInventory() {
        AutoArmour Nigga;
        return Nigga.mc.thePlayer.openContainer != null && Nigga.mc.thePlayer.openContainer instanceof ContainerChest;
    }

    public static {
        throw throwable;
    }

    public AutoArmour() {
        super(Qprot0.0("\u69af\u71de\u52f6\ua7eb\u437c\uec26\u8c22\u05d4\u5710"), 0, Module.Category.COMBAT);
        AutoArmour Nigga;
        Nigga.addSettings(Nigga.delay);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventUpdate) {
            AutoArmour Nigga2;
            try {
                Nigga2.setSuffix(String.valueOf(Nigga2.delay.getValue()));
                if (!Client.ghostMode && !Nigga2.isChestInventory()) {
                    for (int Nigga3 = 0; Nigga3 < 36; ++Nigga3) {
                        ItemStack Nigga4 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3);
                        if (Nigga4 == null || !(Nigga4.getItem() instanceof ItemArmor)) continue;
                        ItemArmor Nigga5 = (ItemArmor)Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3).getItem();
                        int Nigga6 = 0;
                        int Nigga7 = 0;
                        int Nigga8 = 0;
                        if (Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType) != null) {
                            ItemArmor Nigga9 = (ItemArmor)Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType).getItem();
                            ItemStack Nigga10 = Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType);
                            Nigga6 = Nigga9.getArmorMaterial().getDamageReductionAmount(Nigga5.armorType);
                            Nigga6 = AutoArmour.checkProtection(Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType)) + Nigga6;
                            Nigga7 = Nigga10.getItemDamage();
                            Nigga8 = Nigga5.getArmorMaterial().getDamageReductionAmount(Nigga5.armorType);
                            Nigga8 = AutoArmour.checkProtection(Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga3)) + Nigga8;
                        }
                        if (Nigga2.getFreeSlot() != -1 && Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType) != null && (Nigga8 > Nigga6 || Nigga8 == Nigga6 && Nigga4.getItemDamage() < Nigga7 && Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true))) {
                            if (Nigga3 < 9) {
                                Nigga3 += 36;
                            }
                            Nigga2.mc.playerController.windowClick(Nigga2.mc.thePlayer.inventoryContainer.windowId, 5 + Nigga5.armorType, 0, 4, Nigga2.mc.thePlayer);
                            Nigga2.mc.playerController.windowClick(Nigga2.mc.thePlayer.inventoryContainer.windowId, Nigga3, 0, 1, Nigga2.mc.thePlayer);
                        }
                        if (Nigga2.mc.thePlayer.inventory.getStackInSlot(39 - Nigga5.armorType) != null || !Nigga2.timer.hasTimeElapsed((long)Nigga2.delay.getValue(), true)) continue;
                        if (Nigga3 < 9) {
                            Nigga3 += 36;
                        }
                        Nigga2.mc.playerController.windowClick(Nigga2.mc.thePlayer.inventoryContainer.windowId, Nigga3, 0, 1, Nigga2.mc.thePlayer);
                    }
                }
            }
            catch (Exception Nigga11) {
                System.out.println(Qprot0.0("\u6999\u71df\u52e4\ue22d\u8dd6\uec26\u8c3a\u05d3\u129d"));
                Nigga2.mc.thePlayer.messagePlayer(Qprot0.0("\u699c\u71ce\u52f2\ue262\u8dc6\uec20\u8c6f\u05cf\u1283\u2697\u660a\uaf4c\ueb14\u37cb\uf9ad\uf59f\u42e1\udffc\u52c9\u9286\u9043\u01c3\u79d1\uadff\u4a7f\ud48c\u2f5e\ubcb4\u07dd\u075c\u8821\u8855\ue43a"));
                Nigga11.printStackTrace();
            }
        }
    }

    public int getFreeSlot() {
        for (int Nigga = 35; Nigga > 0; --Nigga) {
            AutoArmour Nigga2;
            ItemStack Nigga3 = Nigga2.mc.thePlayer.inventory.getStackInSlot(Nigga);
            if (Nigga3 != null) continue;
            return Nigga;
        }
        return -1;
    }

    public static int checkProtection(ItemStack Nigga) {
        return EnchantmentHelper.getEnchantmentLevel(0, Nigga);
    }
}

