package Focus.Beta.IMPL.Module.impl.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

import java.util.concurrent.ThreadLocalRandom;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.Module.impl.combat.Killaura;
import Focus.Beta.IMPL.set.Numbers;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.world.InventoryUtils;
import Focus.Beta.UTILS.world.ItemUtils;
import Focus.Beta.UTILS.world.Timer;

public class InvManager extends Module {

    private static Timer timer = new Timer();
    private Numbers<Double> delay = new Numbers<Double>("Delay", "Delay", 200.0, 1.0, 1000.0, 1.0);
    private Numbers<Double> swordSlot = new Numbers<Double>("Sword Slot", "Sword Slot", 1.0, 1.0, 9.0, 1.0);
    private final Option<Boolean> keepaxe = new Option<Boolean>("Keep Axe", "Keep Axe", false);
    private final Option<Boolean> keeppickaxe = new Option<Boolean>("Keep Pickaxe", "Keep Pickaxe", true);
    private final Option<Boolean> keepshovel = new Option<Boolean>("Keep Shovel", "Keep Shovel", true);
    private final Option<Boolean> clean = new Option<Boolean>("Clean", "Clean", true);
    private final Option<Boolean> cleanbad = new Option<Boolean>("Clean Bad", "Clean Bad", true);

    public InvManager() {
        super("Manager", new String[]{"InvManager", "InvCleaner"}, Type.MISC, "Manages inventory");
        addValues(delay, swordSlot, keepaxe, keeppickaxe, keepshovel, clean, cleanbad);
    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate event){
        double realdelay = delay.getValue();
        double delay = Math.max(20, realdelay + ThreadLocalRandom.current().nextDouble(-40, 40));

        if(Killaura.target != null){
            timer.reset();
            return;
        }

        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat) || mc.thePlayer.isUsingItem()) {
            timer.reset();
            return;
        }

        if (this.timer.hasReached(delay)) {
            this.invManager(delay);

            timer.reset();
        }
    }

    private void invManager(double delay) {
        int bestSword = -1;
        float bestDamage = 1F;

        for (int k = 0; k < mc.thePlayer.inventory.mainInventory.length; k++) {
            ItemStack item = mc.thePlayer.inventory.mainInventory[k];
            if (item != null) {
                if (item.getItem() instanceof ItemSword) {
                    ItemSword is = (ItemSword) item.getItem();
                    float damage = is.getDamageVsEntity();
                    damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, item) * 1.26F +
                            EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, item) * 0.01f;
                    if (damage > bestDamage) {
                        bestDamage = damage;
                        bestSword = k;
                    }
                }
            }
        }
        double swordSlot = this.swordSlot.getValue();
        if (bestSword != -1 && bestSword != swordSlot - 1) {
            for (int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); i++) {
                Slot s = mc.thePlayer.inventoryContainer.inventorySlots.get(i);
                if (s.getHasStack() && s.getStack() == mc.thePlayer.inventory.mainInventory[bestSword]) {
                    double slot = swordSlot - 1;
                    mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, s.slotNumber, (int) slot, 2, mc.thePlayer);
                    timer.reset();
                    return;
                }
            }
        }
        if (this.clean.getValue() && timer.hasReached(delay)) {
            for (int i = 9; i < 45; ++i) {
                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                    final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (this.shouldDrop(is, i) && timer.hasReached(delay)) {
                        this.drop(i);
                        this.timer.reset();
                        break;
                    }
                }
            }
        }
    }

    private void autoArmor(double delay) {
        int bestHelm = this.getBestHelmet();
        if (mc.thePlayer.inventory.armorItemInSlot(3) == null) {
            if (bestHelm != -1) {
                if (bestHelm < 9 && mc.thePlayer.inventory.getStackInSlot(bestHelm).getItem() instanceof ItemArmor) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestHelm));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bestHelm)));
                } else {
                    this.shiftClick(bestHelm);
                }
                timer.reset();
                return;
            }
        } else if (bestHelm != -1 && mc.thePlayer.inventory.armorItemInSlot(3) != mc.thePlayer.inventoryContainer.getSlot(bestHelm).getStack()){
            this.drop(5);
            timer.reset();
            return;
        }
        int bestChest = this.getBestChestplate();
        if (mc.thePlayer.inventory.armorItemInSlot(2) == null) {
            if (bestChest != -1) {
                if (bestChest < 9 && mc.thePlayer.inventory.getStackInSlot(bestChest).getItem() instanceof ItemArmor) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestChest));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bestChest)));
                } else {
                    this.shiftClick(bestChest);
                }
                timer.reset();
                return;
            }
        } else if (bestChest != -1 && mc.thePlayer.inventory.armorItemInSlot(2) != mc.thePlayer.inventoryContainer.getSlot(bestChest).getStack()){
            this.drop(6);
            timer.reset();
            return;
        }
        int bestLegs = this.getBestLeggings();
        if (mc.thePlayer.inventory.armorItemInSlot(1) == null) {
            if (bestLegs != -1) {
                if (bestLegs < 9 && mc.thePlayer.inventory.getStackInSlot(bestLegs).getItem() instanceof ItemArmor) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestLegs));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bestLegs)));
                } else {
                    this.shiftClick(bestLegs);
                    timer.reset();
                    return;
                }
            }
        } else if (bestLegs != -1 && mc.thePlayer.inventory.armorItemInSlot(1) != mc.thePlayer.inventoryContainer.getSlot(bestLegs).getStack()){
            this.drop(7);
            timer.reset();
            return;
        }
        int bestBoot = this.getBestBoots();
        if (mc.thePlayer.inventory.armorItemInSlot(0) == null) {
            if (bestBoot != -1) {
                if (bestBoot < 9 && mc.thePlayer.inventory.getStackInSlot(bestBoot).getItem() instanceof ItemArmor) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestBoot));
                    mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(bestBoot)));
                } else {
                    this.shiftClick(bestBoot);
                }
                timer.reset();
                return;
            }
        } else if (bestBoot != -1 && mc.thePlayer.inventory.armorItemInSlot(0) != mc.thePlayer.inventoryContainer.getSlot(bestBoot).getStack()){
            this.drop(8);
            timer.reset();
            return;
        }

        boolean dropped = false;

        for (int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is != null && is.getItem() instanceof ItemArmor && !dropped) {
                    dropped = true;
                    timer.reset();
                    this.drop(i);
                    return;
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    public void drop(final int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }

    public boolean shouldDrop(ItemStack is, int k) {
        int bestSword = ItemUtils.getSwordSlot();
        if (is.getItem() instanceof ItemSword) {
            if (bestSword != -1 && bestSword != k) {
                return true;
            }
        }
        int bestPick = ItemUtils.getPickaxeSlot();
        if (is.getItem() instanceof ItemPickaxe) {
            if (!this.keeppickaxe.getValue()) {
                return true;
            }
            if (bestPick != -1 && bestPick != k) {
                return true;
            }
        }

        int bestAxe = ItemUtils.getAxeSlot();
        if (is.getItem() instanceof ItemAxe) {
            if (!this.keepaxe.getValue()) {
                return true;
            }
            if (bestAxe != -1 && bestAxe != k) {
                return true;
            }
        }

        int bestShovel = ItemUtils.getShovelSlot();
        if (ItemUtils.isShovel(is.getItem())) {
            if (!this.keepshovel.getValue()) {
                return true;
            }
            if (bestShovel != -1 && bestShovel != k) {
                return true;
            }
        }
        if (this.cleanbad.getValue() && InventoryUtils.isBad(is)) {
            return true;
        }
        return false;
    }

    private int getBestHelmet() {
        int bestSword = -1;
        float bestValue = 0F;

        for (int k = 0; k < 36; k++) {
            if (mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(k).getStack();
                if (item != null) {
                    if (item.getItem() instanceof ItemArmor) {
                        ItemArmor ia = (ItemArmor) item.getItem();
                        float value = getValue(item, ia);
                        if (ia.armorType == 0 && value > bestValue) {
                            bestValue = value;
                            bestSword = k;
                        }
                    }
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
            if (item != null) {
                if (item.getItem() instanceof ItemArmor) {
                    ItemArmor ia = (ItemArmor) item.getItem();
                    float value = getValue(item, ia);
                    if (ia.armorType == 0 && value > bestValue) {
                        bestValue = value;
                        bestSword = k;
                    }
                }
            }
        }
        return bestSword;
    }

    private int getBestChestplate() {
        int bestSword = -1;
        float bestValue = 0F;

        for (int k = 0; k < 36; k++) {
            if (mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(k).getStack();
                if (item != null) {
                    if (item.getItem() instanceof ItemArmor) {
                        ItemArmor ia = (ItemArmor) item.getItem();
                        float value = getValue(item, ia);
                        if (ia.armorType == 1 && value > bestValue) {
                            bestValue = value;
                            bestSword = k;
                        }
                    }
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
            if (item != null) {
                if (item.getItem() instanceof ItemArmor) {
                    ItemArmor ia = (ItemArmor) item.getItem();
                    float value = getValue(item, ia);
                    if (ia.armorType == 1 && value > bestValue) {
                        bestValue = value;
                        bestSword = k;
                    }
                }
            }
        }
        return bestSword;
    }

    private int getBestLeggings() {
        int bestSword = -1;
        float bestValue = 0F;

        for (int k = 0; k < 36; k++) {
            if (mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(k).getStack();
                if (item != null) {
                    if (item.getItem() instanceof ItemArmor) {
                        ItemArmor ia = (ItemArmor) item.getItem();
                        float value = getValue(item, ia);
                        if (ia.armorType == 2 && value > bestValue) {
                            bestValue = value;
                            bestSword = k;
                        }
                    }
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
            if (item != null) {
                if (item.getItem() instanceof ItemArmor) {
                    ItemArmor ia = (ItemArmor) item.getItem();
                    float value = getValue(item, ia);
                    if (ia.armorType == 2 && value > bestValue) {
                        bestValue = value;
                        bestSword = k;
                    }
                }
            }
        }
        return bestSword;
    }

    private int getBestBoots() {
        int bestSword = -1;
        float bestValue = 0F;

        for (int k = 0; k < 36; k++) {
            if (mc.thePlayer.inventoryContainer.getSlot(k).getHasStack()) {
                ItemStack item = mc.thePlayer.inventoryContainer.getSlot(k).getStack();

                if (item != null) {
                    if (item.getItem() instanceof ItemArmor) {
                        ItemArmor ia = (ItemArmor) item.getItem();
                        float value = getValue(item, ia);
                        if (ia.armorType == 3 && value > bestValue) {
                            bestValue = value;
                            bestSword = k;
                        }
                    }
                }
            }
        }

        for (int k = 0; k < 9; k++) {
            ItemStack item = mc.thePlayer.inventory.getStackInSlot(k);
            if (item != null) {
                if (item.getItem() instanceof ItemArmor) {
                    ItemArmor ia = (ItemArmor) item.getItem();
                    float value = getValue(item, ia);
                    if (ia.armorType == 3 && value > bestValue) {
                        bestValue = value;
                        bestSword = k;
                    }
                }
            }
        }
        return bestSword;
    }

    private float getValue(ItemStack is, ItemArmor ia) {
        int type = 0;
        if (ia.armorType == 0) {type = 0;}
        if (ia.armorType == 3) {type = 1;}
        if (ia.armorType == 2) {type = 2;}
        if (ia.armorType == 1) {type = 3;}

        int render = 0;
        if (ia.renderIndex == 0) {render = 0;}
        if (ia.renderIndex == 1) {render = 1;}
        if (ia.renderIndex == 4) {render = 2;}
        if (ia.renderIndex == 2) {render = 3;}
        if (ia.renderIndex == 3) {render = 4;}

        float value = (type + 1) * (render + 1);
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is) * 2.5f;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, is) * 1.25f;
        value += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is) * 1f;

        return value;
    }

    public void shiftClick(int slot) {
        if (mc.thePlayer.inventoryContainer.getSlot(slot).getHasStack()) {
            Slot s = mc.thePlayer.inventoryContainer.getSlot(slot);
            if (s.getStack().getItem() instanceof ItemArmor) {
                Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
            }
        }
    }

}
