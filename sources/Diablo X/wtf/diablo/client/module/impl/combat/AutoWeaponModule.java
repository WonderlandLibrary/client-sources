package wtf.diablo.client.module.impl.combat;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;

@ModuleMetaData(
        name = "Auto Weapon",
        description = "Automatically equips the best weapon in your hotbar when attacking.",
        category = ModuleCategoryEnum.COMBAT
)
public final class AutoWeaponModule extends AbstractModule {
    @EventHandler
    private final Listener<SendPacketEvent> packetListener = e -> {
        if (e.getPacket() instanceof C02PacketUseEntity) {
            final int bestWeapon = this.getBestWeaponFromHotBar();

            if (bestWeapon != -1) {
                mc.thePlayer.inventory.currentItem = bestWeapon;
            }
        }
    };

    private int getBestWeaponFromHotBar() {
        int bestSword = -1;
        float bestDamage = 0.0F;

        for (int i = 0; i < 9; i++) {
            if (mc.thePlayer.inventory.mainInventory[i] != null) {
                float damage = 0.0F;
                if (mc.thePlayer.inventory.mainInventory[i].getItem() instanceof ItemSword) {
                    damage = ((ItemSword) mc.thePlayer.inventory.mainInventory[i].getItem()).getDamageVsEntity();
                    damage += (float) (0.25 * EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, mc.thePlayer.inventory.mainInventory[i]));
                }

                if (damage > bestDamage) {
                    bestDamage = damage;
                    bestSword = i;
                }
            }
        }
        return bestSword;
    }
}
