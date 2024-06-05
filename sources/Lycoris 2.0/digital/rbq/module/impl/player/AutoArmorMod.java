/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.DamageSource;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.impl.player.InventoryManagerMod;
import digital.rbq.module.option.impl.DoubleOption;

@Label(value="Auto Armor")
@Category(value=ModuleCategory.PLAYER)
@Aliases(value={"autoarmor"})
public final class AutoArmorMod
extends Module {
    private List<Integer>[] allArmors = new List[4];
    private boolean equipping;
    private int[] bestArmorSlot;
    private final DoubleOption delay = new DoubleOption("Delay", 250.0, 0.0, 1000.0, 50.0);

    public AutoArmorMod() {
        this.addOptions(this.delay);
    }

    @Listener(value=MotionUpdateEvent.class)
    public void onEvent(MotionUpdateEvent event) {
        if ((AutoArmorMod.mc.currentScreen == null || AutoArmorMod.mc.currentScreen instanceof GuiInventory) && event.isPre()) {
            this.collectBestArmor();
            EntityPlayerSP player = AutoArmorMod.mc.thePlayer;
            int inventoryId = player.inventoryContainer.windowId;
            for (int i = 0; i < 4; ++i) {
                int slot;
                if (this.bestArmorSlot[i] == -1) continue;
                if (!this.equipping) {
                    this.equipping = true;
                    player.sendQueue.addToSendQueueSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                }
                int bestSlot = this.bestArmorSlot[i];
                ItemStack oldArmor = AutoArmorMod.mc.thePlayer.inventory.armorItemInSlot(i);
                if (this.checkDelay()) {
                    return;
                }
                if (oldArmor != null) {
                    AutoArmorMod.mc.playerController.windowClick(inventoryId, 8 - i, 0, 4, player);
                    InventoryManagerMod.INV_STOPWATCH.reset();
                }
                int n = slot = bestSlot < 9 ? bestSlot + 36 : bestSlot;
                if (this.checkDelay()) {
                    return;
                }
                AutoArmorMod.mc.playerController.windowClick(inventoryId, slot, 0, 1, player);
                InventoryManagerMod.INV_STOPWATCH.reset();
                if (!this.equipping) continue;
                player.sendQueue.addToSendQueueSilent(new C0DPacketCloseWindow(inventoryId));
                this.equipping = false;
            }
        }
    }

    private boolean checkDelay() {
        return !InventoryManagerMod.INV_STOPWATCH.elapsed(((Double)this.delay.getValue()).longValue());
    }

    private void collectBestArmor() {
        ItemArmor armor;
        ItemStack itemStack;
        int i;
        int[] bestArmorDamageReduction = new int[4];
        this.bestArmorSlot = new int[4];
        Arrays.fill(bestArmorDamageReduction, -1);
        Arrays.fill(this.bestArmorSlot, -1);
        for (i = 0; i < this.bestArmorSlot.length; ++i) {
            int currentProtectionLevel;
            itemStack = AutoArmorMod.mc.thePlayer.inventory.armorItemInSlot(i);
            this.allArmors[i] = new ArrayList<Integer>();
            if (itemStack == null || itemStack.getItem() == null) continue;
            armor = (ItemArmor)itemStack.getItem();
            bestArmorDamageReduction[i] = currentProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
        }
        for (i = 0; i < 36; ++i) {
            itemStack = AutoArmorMod.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemArmor)) continue;
            armor = (ItemArmor)itemStack.getItem();
            int armorType = 3 - armor.armorType;
            this.allArmors[armorType].add(i);
            int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            if (bestArmorDamageReduction[armorType] >= slotProtectionLevel) continue;
            bestArmorDamageReduction[armorType] = slotProtectionLevel;
            this.bestArmorSlot[armorType] = i;
        }
    }
}

