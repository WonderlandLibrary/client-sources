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
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.DamageSource;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MotionUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Aliases;
import digital.rbq.module.annotations.Category;
import digital.rbq.module.option.impl.DoubleOption;
import digital.rbq.utils.InventoryUtils;
import digital.rbq.utils.Stopwatch;

@Label(value="Inventory Manager")
@Category(value=ModuleCategory.PLAYER)
@Aliases(value={"inventorymanager", "invmanager"})
public final class InventoryManagerMod
extends Module {
    public static final Stopwatch INV_STOPWATCH = new Stopwatch();
    private List<Integer> allSwords = new ArrayList<Integer>();
    private List<Integer>[] allArmors = new List[4];
    private List<Integer> trash = new ArrayList<Integer>();
    private boolean cleaning;
    private int[] bestArmorSlot;
    private int bestSwordSlot;
    private final DoubleOption swordSlot = new DoubleOption("Sword Slot", 1.0, 1.0, 9.0, 1.0);
    private final DoubleOption delay = new DoubleOption("Delay", 250.0, 0.0, 1000.0, 50.0);

    public InventoryManagerMod() {
        this.addOptions(this.swordSlot, this.delay);
    }

    @Listener(value=MotionUpdateEvent.class)
    public void onEvent(MotionUpdateEvent event) {
        if ((InventoryManagerMod.mc.currentScreen == null || InventoryManagerMod.mc.currentScreen instanceof GuiInventory) && event.isPre()) {
            this.collectItems();
            this.collectBestArmor();
            this.collectTrash();
            int trashSize = this.trash.size();
            boolean trashPresent = trashSize > 0;
            EntityPlayerSP player = InventoryManagerMod.mc.thePlayer;
            int windowId = player.openContainer.windowId;
            int bestSwordSlot = this.bestSwordSlot;
            if (trashPresent) {
                if (!this.cleaning) {
                    this.cleaning = true;
                    player.sendQueue.addToSendQueueSilent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                }
                for (int i = 0; i < trashSize; ++i) {
                    int slot = this.trash.get(i);
                    if (this.checkDelay()) break;
                    InventoryManagerMod.mc.playerController.windowClick(windowId, slot < 9 ? slot + 36 : slot, 1, 4, player);
                    INV_STOPWATCH.reset();
                }
                if (this.cleaning) {
                    player.sendQueue.addToSendQueueSilent(new C0DPacketCloseWindow(windowId));
                    this.cleaning = false;
                }
            }
            if (bestSwordSlot != -1 && !this.checkDelay()) {
                InventoryManagerMod.mc.playerController.windowClick(windowId, bestSwordSlot < 9 ? bestSwordSlot + 36 : bestSwordSlot, ((Double)this.swordSlot.getValue()).intValue() - 1, 2, player);
                INV_STOPWATCH.reset();
            }
        }
    }

    private boolean checkDelay() {
        return !INV_STOPWATCH.elapsed(((Double)this.delay.getValue()).longValue());
    }

    public void collectItems() {
        this.bestSwordSlot = -1;
        this.allSwords.clear();
        float bestSwordDamage = -1.0f;
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = InventoryManagerMod.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemSword)) continue;
            float damageLevel = InventoryUtils.getDamageLevel(itemStack);
            this.allSwords.add(i);
            if (!(bestSwordDamage < damageLevel)) continue;
            bestSwordDamage = damageLevel;
            this.bestSwordSlot = i;
        }
    }

    private void collectBestArmor() {
        ItemArmor armor;
        ItemStack itemStack;
        int i;
        int[] bestArmorDamageReducement = new int[4];
        this.bestArmorSlot = new int[4];
        Arrays.fill(bestArmorDamageReducement, -1);
        Arrays.fill(this.bestArmorSlot, -1);
        for (i = 0; i < this.bestArmorSlot.length; ++i) {
            int currentProtectionLevel;
            itemStack = InventoryManagerMod.mc.thePlayer.inventory.armorItemInSlot(i);
            this.allArmors[i] = new ArrayList<Integer>();
            if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemArmor)) continue;
            armor = (ItemArmor)itemStack.getItem();
            bestArmorDamageReducement[i] = currentProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
        }
        for (i = 0; i < 36; ++i) {
            itemStack = InventoryManagerMod.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null || !(itemStack.getItem() instanceof ItemArmor)) continue;
            armor = (ItemArmor)itemStack.getItem();
            int armorType = 3 - armor.armorType;
            this.allArmors[armorType].add(i);
            int slotProtectionLevel = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            if (bestArmorDamageReducement[armorType] >= slotProtectionLevel) continue;
            bestArmorDamageReducement[armorType] = slotProtectionLevel;
            this.bestArmorSlot[armorType] = i;
        }
    }

    private void collectTrash() {
        int i;
        this.trash.clear();
        for (i = 0; i < 36; ++i) {
            ItemStack itemStack = InventoryManagerMod.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack == null || itemStack.getItem() == null || InventoryUtils.isValidItem(itemStack)) continue;
            this.trash.add(i);
        }
        for (i = 0; i < this.allArmors.length; ++i) {
            List<Integer> armorItem = this.allArmors[i];
            if (armorItem == null) continue;
            List<Integer> integers = this.trash;
            int armorItemSize = armorItem.size();
            for (int i1 = 0; i1 < armorItemSize; ++i1) {
                Integer slot = armorItem.get(i1);
                if (slot == this.bestArmorSlot[i]) continue;
                integers.add(slot);
            }
        }
        List<Integer> integers = this.trash;
        int allSwordsSize = this.allSwords.size();
        for (int i2 = 0; i2 < allSwordsSize; ++i2) {
            Integer slot = this.allSwords.get(i2);
            if (slot == this.bestSwordSlot) continue;
            integers.add(slot);
        }
    }
}

