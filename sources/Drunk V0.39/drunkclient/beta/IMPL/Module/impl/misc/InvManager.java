/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.Module.impl.combat.Killaura;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.world.InventoryUtils;
import drunkclient.beta.UTILS.world.ItemUtils;
import drunkclient.beta.UTILS.world.Timer;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class InvManager
extends Module {
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
        this.addValues(this.delay, this.swordSlot, this.keepaxe, this.keeppickaxe, this.keepshovel, this.clean, this.cleanbad);
    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate event) {
        block6: {
            double delay;
            block5: {
                double realdelay = (Double)this.delay.getValue();
                delay = Math.max(20.0, realdelay + ThreadLocalRandom.current().nextDouble(-40.0, 40.0));
                if (Killaura.target != null) {
                    timer.reset();
                    return;
                }
                if (InvManager.mc.currentScreen == null || InvManager.mc.currentScreen instanceof GuiInventory) break block5;
                if (!(InvManager.mc.currentScreen instanceof GuiChat)) break block6;
            }
            if (!Minecraft.thePlayer.isUsingItem()) {
                if (!timer.hasReached(delay)) return;
                this.invManager(delay);
                timer.reset();
                return;
            }
        }
        timer.reset();
    }

    private void invManager(double delay) {
        int bestSword = -1;
        float bestDamage = 1.0f;
        for (int k = 0; k < Minecraft.thePlayer.inventory.mainInventory.length; ++k) {
            ItemStack item = Minecraft.thePlayer.inventory.mainInventory[k];
            if (item == null || !(item.getItem() instanceof ItemSword)) continue;
            ItemSword is = (ItemSword)item.getItem();
            float damage = is.getDamageVsEntity();
            if (!((damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, item) * 1.26f + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, item) * 0.01f) > bestDamage)) continue;
            bestDamage = damage;
            bestSword = k;
        }
        double swordSlot = (Double)this.swordSlot.getValue();
        if (bestSword != -1 && (double)bestSword != swordSlot - 1.0) {
            for (int i = 0; i < Minecraft.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
                Slot s = Minecraft.thePlayer.inventoryContainer.inventorySlots.get(i);
                if (!s.getHasStack()) continue;
                if (s.getStack() != Minecraft.thePlayer.inventory.mainInventory[bestSword]) continue;
                double slot = swordSlot - 1.0;
                InvManager.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, s.slotNumber, (int)slot, 2, Minecraft.thePlayer);
                timer.reset();
                return;
            }
        }
        if ((Boolean)this.clean.getValue() == false) return;
        if (!timer.hasReached(delay)) return;
        int i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (this.shouldDrop(is, i) && timer.hasReached(delay)) {
                    this.drop(i);
                    timer.reset();
                    return;
                }
            }
            ++i;
        }
    }

    /*
     * Unable to fully structure code
     */
    private void autoArmor(double delay) {
        block25: {
            block24: {
                block23: {
                    block22: {
                        block21: {
                            block20: {
                                block19: {
                                    bestHelm = this.getBestHelmet();
                                    if (Minecraft.thePlayer.inventory.armorItemInSlot(3) != null) break block19;
                                    if (bestHelm == -1) break block20;
                                    if (bestHelm >= 9) ** GOTO lbl-1000
                                    if (Minecraft.thePlayer.inventory.getStackInSlot(bestHelm).getItem() instanceof ItemArmor) {
                                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestHelm));
                                        InvManager.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getStackInSlot(bestHelm)));
                                    } else lbl-1000:
                                    // 2 sources

                                    {
                                        this.shiftClick(bestHelm);
                                    }
                                    InvManager.timer.reset();
                                    return;
                                }
                                if (bestHelm != -1) {
                                    if (Minecraft.thePlayer.inventory.armorItemInSlot(3) != Minecraft.thePlayer.inventoryContainer.getSlot(bestHelm).getStack()) {
                                        this.drop(5);
                                        InvManager.timer.reset();
                                        return;
                                    }
                                }
                            }
                            bestChest = this.getBestChestplate();
                            if (Minecraft.thePlayer.inventory.armorItemInSlot(2) != null) break block21;
                            if (bestChest == -1) break block22;
                            if (bestChest >= 9) ** GOTO lbl-1000
                            if (Minecraft.thePlayer.inventory.getStackInSlot(bestChest).getItem() instanceof ItemArmor) {
                                Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestChest));
                                InvManager.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getStackInSlot(bestChest)));
                            } else lbl-1000:
                            // 2 sources

                            {
                                this.shiftClick(bestChest);
                            }
                            InvManager.timer.reset();
                            return;
                        }
                        if (bestChest != -1) {
                            if (Minecraft.thePlayer.inventory.armorItemInSlot(2) != Minecraft.thePlayer.inventoryContainer.getSlot(bestChest).getStack()) {
                                this.drop(6);
                                InvManager.timer.reset();
                                return;
                            }
                        }
                    }
                    bestLegs = this.getBestLeggings();
                    if (Minecraft.thePlayer.inventory.armorItemInSlot(1) != null) ** GOTO lbl64
                    if (bestLegs == -1) break block23;
                    if (bestLegs >= 9) ** GOTO lbl-1000
                    if (Minecraft.thePlayer.inventory.getStackInSlot(bestLegs).getItem() instanceof ItemArmor) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestLegs));
                        InvManager.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getStackInSlot(bestLegs)));
                    } else lbl-1000:
                    // 2 sources

                    {
                        this.shiftClick(bestLegs);
                        InvManager.timer.reset();
                        return;
lbl64:
                        // 1 sources

                        if (bestLegs != -1) {
                            if (Minecraft.thePlayer.inventory.armorItemInSlot(1) != Minecraft.thePlayer.inventoryContainer.getSlot(bestLegs).getStack()) {
                                this.drop(7);
                                InvManager.timer.reset();
                                return;
                            }
                        }
                    }
                }
                bestBoot = this.getBestBoots();
                if (Minecraft.thePlayer.inventory.armorItemInSlot(0) != null) break block24;
                if (bestBoot == -1) break block25;
                if (bestBoot >= 9) ** GOTO lbl-1000
                if (Minecraft.thePlayer.inventory.getStackInSlot(bestBoot).getItem() instanceof ItemArmor) {
                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(bestBoot));
                    InvManager.mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getStackInSlot(bestBoot)));
                } else lbl-1000:
                // 2 sources

                {
                    this.shiftClick(bestBoot);
                }
                InvManager.timer.reset();
                return;
            }
            if (bestBoot != -1) {
                if (Minecraft.thePlayer.inventory.armorItemInSlot(0) != Minecraft.thePlayer.inventoryContainer.getSlot(bestBoot).getStack()) {
                    this.drop(8);
                    InvManager.timer.reset();
                    return;
                }
            }
        }
        dropped = false;
        i = 9;
        while (i < 45) {
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (is != null && is.getItem() instanceof ItemArmor && !dropped) {
                    dropped = true;
                    InvManager.timer.reset();
                    this.drop(i);
                    return;
                }
            }
            ++i;
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        timer.reset();
    }

    public void drop(int slot) {
        InvManager.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 1, 4, Minecraft.thePlayer);
    }

    public boolean shouldDrop(ItemStack is, int k) {
        int bestSword = ItemUtils.getSwordSlot();
        if (is.getItem() instanceof ItemSword && bestSword != -1 && bestSword != k) {
            return true;
        }
        int bestPick = ItemUtils.getPickaxeSlot();
        if (is.getItem() instanceof ItemPickaxe) {
            if (!((Boolean)this.keeppickaxe.getValue()).booleanValue()) {
                return true;
            }
            if (bestPick != -1 && bestPick != k) {
                return true;
            }
        }
        int bestAxe = ItemUtils.getAxeSlot();
        if (is.getItem() instanceof ItemAxe) {
            if (!((Boolean)this.keepaxe.getValue()).booleanValue()) {
                return true;
            }
            if (bestAxe != -1 && bestAxe != k) {
                return true;
            }
        }
        int bestShovel = ItemUtils.getShovelSlot();
        if (ItemUtils.isShovel(is.getItem())) {
            if (!((Boolean)this.keepshovel.getValue()).booleanValue()) {
                return true;
            }
            if (bestShovel != -1 && bestShovel != k) {
                return true;
            }
        }
        if ((Boolean)this.cleanbad.getValue() == false) return false;
        if (!InventoryUtils.isBad(is)) return false;
        return true;
    }

    private int getBestHelmet() {
        float value;
        ItemArmor ia;
        ItemStack item;
        int k;
        int bestSword = -1;
        float bestValue = 0.0f;
        for (k = 0; k < 36; ++k) {
            if (!Minecraft.thePlayer.inventoryContainer.getSlot(k).getHasStack()) continue;
            item = Minecraft.thePlayer.inventoryContainer.getSlot(k).getStack();
            if (item == null || !(item.getItem() instanceof ItemArmor)) continue;
            ia = (ItemArmor)item.getItem();
            value = this.getValue(item, ia);
            if (ia.armorType != 0 || !(value > bestValue)) continue;
            bestValue = value;
            bestSword = k;
        }
        k = 0;
        while (k < 9) {
            item = Minecraft.thePlayer.inventory.getStackInSlot(k);
            if (item != null && item.getItem() instanceof ItemArmor) {
                ia = (ItemArmor)item.getItem();
                value = this.getValue(item, ia);
                if (ia.armorType == 0 && value > bestValue) {
                    bestValue = value;
                    bestSword = k;
                }
            }
            ++k;
        }
        return bestSword;
    }

    private int getBestChestplate() {
        float value;
        ItemArmor ia;
        ItemStack item;
        int k;
        int bestSword = -1;
        float bestValue = 0.0f;
        for (k = 0; k < 36; ++k) {
            if (!Minecraft.thePlayer.inventoryContainer.getSlot(k).getHasStack()) continue;
            item = Minecraft.thePlayer.inventoryContainer.getSlot(k).getStack();
            if (item == null || !(item.getItem() instanceof ItemArmor)) continue;
            ia = (ItemArmor)item.getItem();
            value = this.getValue(item, ia);
            if (ia.armorType != 1 || !(value > bestValue)) continue;
            bestValue = value;
            bestSword = k;
        }
        k = 0;
        while (k < 9) {
            item = Minecraft.thePlayer.inventory.getStackInSlot(k);
            if (item != null && item.getItem() instanceof ItemArmor) {
                ia = (ItemArmor)item.getItem();
                value = this.getValue(item, ia);
                if (ia.armorType == 1 && value > bestValue) {
                    bestValue = value;
                    bestSword = k;
                }
            }
            ++k;
        }
        return bestSword;
    }

    private int getBestLeggings() {
        float value;
        ItemArmor ia;
        ItemStack item;
        int k;
        int bestSword = -1;
        float bestValue = 0.0f;
        for (k = 0; k < 36; ++k) {
            if (!Minecraft.thePlayer.inventoryContainer.getSlot(k).getHasStack()) continue;
            item = Minecraft.thePlayer.inventoryContainer.getSlot(k).getStack();
            if (item == null || !(item.getItem() instanceof ItemArmor)) continue;
            ia = (ItemArmor)item.getItem();
            value = this.getValue(item, ia);
            if (ia.armorType != 2 || !(value > bestValue)) continue;
            bestValue = value;
            bestSword = k;
        }
        k = 0;
        while (k < 9) {
            item = Minecraft.thePlayer.inventory.getStackInSlot(k);
            if (item != null && item.getItem() instanceof ItemArmor) {
                ia = (ItemArmor)item.getItem();
                value = this.getValue(item, ia);
                if (ia.armorType == 2 && value > bestValue) {
                    bestValue = value;
                    bestSword = k;
                }
            }
            ++k;
        }
        return bestSword;
    }

    private int getBestBoots() {
        float value;
        ItemArmor ia;
        ItemStack item;
        int k;
        int bestSword = -1;
        float bestValue = 0.0f;
        for (k = 0; k < 36; ++k) {
            if (!Minecraft.thePlayer.inventoryContainer.getSlot(k).getHasStack()) continue;
            item = Minecraft.thePlayer.inventoryContainer.getSlot(k).getStack();
            if (item == null || !(item.getItem() instanceof ItemArmor)) continue;
            ia = (ItemArmor)item.getItem();
            value = this.getValue(item, ia);
            if (ia.armorType != 3 || !(value > bestValue)) continue;
            bestValue = value;
            bestSword = k;
        }
        k = 0;
        while (k < 9) {
            item = Minecraft.thePlayer.inventory.getStackInSlot(k);
            if (item != null && item.getItem() instanceof ItemArmor) {
                ia = (ItemArmor)item.getItem();
                value = this.getValue(item, ia);
                if (ia.armorType == 3 && value > bestValue) {
                    bestValue = value;
                    bestSword = k;
                }
            }
            ++k;
        }
        return bestSword;
    }

    private float getValue(ItemStack is, ItemArmor ia) {
        int type = 0;
        if (ia.armorType == 0) {
            type = 0;
        }
        if (ia.armorType == 3) {
            type = 1;
        }
        if (ia.armorType == 2) {
            type = 2;
        }
        if (ia.armorType == 1) {
            type = 3;
        }
        int render = 0;
        if (ia.renderIndex == 0) {
            render = 0;
        }
        if (ia.renderIndex == 1) {
            render = 1;
        }
        if (ia.renderIndex == 4) {
            render = 2;
        }
        if (ia.renderIndex == 2) {
            render = 3;
        }
        if (ia.renderIndex == 3) {
            render = 4;
        }
        float value = (type + 1) * (render + 1);
        value += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, is) * 2.5f;
        value += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, is) * 1.25f;
        return value += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, is) * 1.0f;
    }

    public void shiftClick(int slot) {
        if (!Minecraft.thePlayer.inventoryContainer.getSlot(slot).getHasStack()) return;
        Slot s = Minecraft.thePlayer.inventoryContainer.getSlot(slot);
        if (!(s.getStack().getItem() instanceof ItemArmor)) return;
        PlayerControllerMP playerControllerMP = Minecraft.getMinecraft().playerController;
        Minecraft.getMinecraft();
        int n = Minecraft.thePlayer.inventoryContainer.windowId;
        Minecraft.getMinecraft();
        playerControllerMP.windowClick(n, slot, 0, 1, Minecraft.thePlayer);
    }
}

