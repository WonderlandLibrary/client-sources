package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.SelectorDetectionComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.InventoryMove;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.ItemUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.BoundsNumberValue;
import com.alan.clients.value.impl.NumberValue;
import lombok.Getter;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import rip.vantage.commons.util.time.StopWatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class ItemStackWithNumber {
    private final ItemStack itemStack;
    private final int number;

    public ItemStackWithNumber(ItemStack itemStack, int number) {
        this.itemStack = itemStack;
        this.number = number;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getNumber() {
        return number;
    }

    public int getStackSize() {
        return itemStack.stackSize;
    }
}

@ModuleInfo(aliases = {"module.player.manager.name", "Manager"}, description = "module.player.manager.description", category = Category.PLAYER)
public class Manager extends Module {

    private final BoundsNumberValue delay = new BoundsNumberValue("Delay", this, 100, 150, 0, 500, 50);

    private final BooleanValue legit = new BooleanValue("Legit", this, false);
    private final BooleanValue dropCustomItems = new BooleanValue("Drop Custom Items", this, false);
    private final BooleanValue useCustomItems = new BooleanValue("Use Custom Items", this, false);
    private final BooleanValue prioritizeSplashPotions = new BooleanValue("Prioritize Splash Potions", this, false);
    private final NumberValue blockLimit = new NumberValue("Block Limit", this, 512, 0, 512, 4);
    private final NumberValue arrowLimit = new NumberValue("Arrow Limit", this, 128, 0, 512, 4);
    private final NumberValue bucketLimit = new NumberValue("Bucket Limit", this, 1, 0, 4, 1);
    private final NumberValue snowballEggLimit = new NumberValue("Snowball/Egg Limit", this, 16, 0, 64, 1);
    private final NumberValue enderPearlLimit = new NumberValue("Ender Pearl Limit", this, 16, 0, 64, 1);

    private final NumberValue swordSlot = new NumberValue("Sword Slot", this, 1, 0, 9, 1);
    private final NumberValue pickaxeSlot = new NumberValue("Pickaxe Slot", this, 2, 0, 9, 1);
    private final NumberValue axeSlot = new NumberValue("Axe Slot", this, 3, 0, 9, 1);
    private final NumberValue shovelSlot = new NumberValue("Shovel Slot", this, 4, 0, 9, 1);
    private final BoundsNumberValue blockSlot = new BoundsNumberValue("Block Slot", this, 5, 5, 0, 9, 1);
    private final BoundsNumberValue potionSlot = new BoundsNumberValue("Potion Slot", this, 6, 6, 0, 9, 1);
    private final NumberValue bowSlot = new NumberValue("Bow Slot", this, 7, 0, 9, 1);
    private final NumberValue rodSlot = new NumberValue("Rod Slot", this, 8, 0, 9, 1);
    private final BoundsNumberValue foodSlot = new BoundsNumberValue("Food Slot", this, 9, 9, 0, 9, 1);

    private final int INVENTORY_ROWS = 4, INVENTORY_COLUMNS = 9, ARMOR_SLOTS = 4;
    private final int INVENTORY_SLOTS = (INVENTORY_ROWS * INVENTORY_COLUMNS) + ARMOR_SLOTS;

    private final StopWatch stopwatch = new StopWatch();
    private int chestTicks, attackTicks, placeTicks;
    @Getter
    private boolean moved, open;
    private long nextClick;

    @EventLink(value = Priorities.LOW)
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (mc.thePlayer.ticksExisted <= 40) {
            return;
        }

        if (mc.currentScreen instanceof GuiChest) {
            this.chestTicks = 0;
        } else {
            this.chestTicks++;
        }

        this.attackTicks++;
        this.placeTicks++;

        // Calls stopwatch.reset() to simulate opening an inventory, checks for an open inventory to be legit.
        if (legit.getValue() && !(mc.currentScreen instanceof GuiInventory)) {
            this.stopwatch.reset();
            return;
        }

        if (!this.stopwatch.finished(this.nextClick) || this.chestTicks < 10 || this.attackTicks < 10 || this.placeTicks < 10) {
            this.closeInventory();
            return;
        }

        if (!this.getModule(InventoryMove.class).isEnabled() && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }

        this.moved = false;

        int helmet = -1;
        int chestplate = -1;
        int leggings = -1;
        int boots = -1;

        int sword = -1;
        int pickaxe = -1;
        int axe = -1;
        int shovel = -1;
        List<ItemStackWithNumber> blockStacks = new ArrayList<>();
        List<ItemStackWithNumber> potionStacks = new ArrayList<>();
        int bow = -1;
        int rod = -1;
        List<ItemStackWithNumber> foodStacks = new ArrayList<>();

        int totalBlocks = 0, totalArrows = 0, totalBuckets = 0, totalSnowballsEggs = 0, totalEnderPearls = 0;

        List<Integer> blockSlots = new ArrayList<>(), arrowSlots = new ArrayList<>(), bucketSlots = new ArrayList<>(), snowballEggSlots = new ArrayList<>(), enderPearlSlots = new ArrayList<>();

        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack == null) {
                continue;
            }

            final Item item = stack.getItem();

            if (!ItemUtil.useful(stack)) {
                this.throwItem(i);
            }

            if (item == Items.arrow) {
                totalArrows += stack.stackSize;
                arrowSlots.add(i);
            } else if (item == Items.bucket || item == Items.water_bucket) {
                totalBuckets += stack.stackSize;
                bucketSlots.add(i);
            } else if (item == Items.snowball || item == Items.egg) {
                totalSnowballsEggs += stack.stackSize;
                snowballEggSlots.add(i);
            } else if (item == Items.ender_pearl) {
                totalEnderPearls += stack.stackSize;
                enderPearlSlots.add(i);
            }

            if (item instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) item;
                final int reduction = this.armorReduction(stack);
                switch (armor.armorType) {
                    case 0:
                        if (helmet == -1 || reduction > armorReduction(mc.thePlayer.inventory.getStackInSlot(helmet))) {
                            helmet = i;
                        }
                        break;

                    case 1:
                        if (chestplate == -1 || reduction > armorReduction(mc.thePlayer.inventory.getStackInSlot(chestplate))) {
                            chestplate = i;
                        }
                        break;

                    case 2:
                        if (leggings == -1 || reduction > armorReduction(mc.thePlayer.inventory.getStackInSlot(leggings))) {
                            leggings = i;
                        }
                        break;

                    case 3:
                        if (boots == -1 || reduction > armorReduction(mc.thePlayer.inventory.getStackInSlot(boots))) {
                            boots = i;
                        }
                        break;
                }
            }

            if (item instanceof ItemSword && this.swordSlot.getValue().intValue() != 0) {
                if (sword == -1) {
                    sword = i;
                } else if (damage(stack) > damage(mc.thePlayer.inventory.getStackInSlot(sword))) {
                    sword = i;
                }

                if (i != sword) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemPickaxe) {
                if (pickaxe == -1) {
                    pickaxe = i;
                } else if (mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(pickaxe))) {
                    pickaxe = i;
                }
                if (i != pickaxe) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemAxe) {
                if (axe == -1) {
                    axe = i;
                } else if (mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(axe))) {
                    axe = i;
                }

                if (i != axe) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemSpade) {
                if (shovel == -1) {
                    shovel = i;
                } else if (mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(shovel))) {
                    shovel = i;
                }

                if (i != shovel) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemBlock) {
                totalBlocks += stack.stackSize;
                blockSlots.add(i);
                blockStacks.add(new ItemStackWithNumber(stack, i));
            }

            if (item instanceof ItemPotion) {
                potionStacks.add(new ItemStackWithNumber(stack, i));
            }

            if (item instanceof ItemBow) {
                if (bow == -1) {
                    bow = i;
                } else if (power(stack) > power(mc.thePlayer.inventory.getStackInSlot(bow))) {
                    bow = i;
                }

                if (i != bow) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemFood && this.foodSlot.getValue().intValue() != 0) {
                foodStacks.add(new ItemStackWithNumber(stack, i));
            }

            if (item instanceof ItemFishingRod) {
                if (rod == -1) {
                    rod = i;
                }

                if (i != rod) {
                    this.throwItem(i);
                }
            }
        }

        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack == null) {
                continue;
            }

            final Item item = stack.getItem();

            if (item instanceof ItemArmor) {
                final ItemArmor armor = (ItemArmor) item;

                switch (armor.armorType) {
                    case 0:
                        if (i != helmet) {
                            this.throwItem(i);
                        }
                        break;

                    case 1:
                        if (i != chestplate) {
                            this.throwItem(i);
                        }
                        break;

                    case 2:
                        if (i != leggings) {
                            this.throwItem(i);
                        }
                        break;

                    case 3:
                        if (i != boots) {
                            this.throwItem(i);
                        }
                        break;
                }
            }
        }

        if (helmet != -1 && helmet != 39) {
            this.equipItem(helmet);
        }

        if (chestplate != -1 && chestplate != 38) {
            this.equipItem(chestplate);
        }

        if (leggings != -1 && leggings != 37) {
            this.equipItem(leggings);
        }

        if (boots != -1 && boots != 36) {
            this.equipItem(boots);
        }

        if (this.blockSlot.getValue().intValue() != 0 && this.blockSlot.getValue().intValue() < this.blockSlot.getSecondValue().intValue()) {
            int items = this.blockSlot.getSecondValue().intValue() - this.blockSlot.getValue().intValue() + 1;
            blockStacks.sort(Comparator.comparingInt(ItemStackWithNumber::getStackSize).reversed());

            for (int i = 0; i < items; i++) {
                if (blockStacks.size() <= i) {
                    break;
                }
                this.moveItem(blockStacks.get(i).getNumber(), this.blockSlot.getValue().intValue() - 37 + i);
            }
        }

        if (totalBlocks > blockLimit.getValue().intValue()) {
            int excessBlocks = totalBlocks - blockLimit.getValue().intValue();

            for (int slot : blockSlots.reversed()) {
                if (excessBlocks <= 0) {
                    break;
                }
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);

                int stackSize = stack.stackSize;

                if (excessBlocks >= stackSize) {
                    this.throwItem(slot);
                    excessBlocks -= stackSize;
                } else {
                    this.throwItem(slot, stackSize - excessBlocks);
                    excessBlocks = 0;
                }
            }
        }

        if (totalArrows > arrowLimit.getValue().intValue()) {
            int excessArrows = totalArrows - arrowLimit.getValue().intValue();
            for (int slot : arrowSlots) {
                if (excessArrows <= 0) {
                    break;
                }
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                int stackSize = stack.stackSize;
                if (excessArrows >= stackSize) {
                    this.throwItem(slot);
                    excessArrows -= stackSize;
                } else {
                    this.throwItem(slot, stackSize - excessArrows);
                    excessArrows = 0;
                }
            }
        }

        if (totalBuckets > bucketLimit.getValue().intValue()) {
            int excessBuckets = totalBuckets - bucketLimit.getValue().intValue();
            for (int slot : bucketSlots) {
                if (excessBuckets <= 0) {
                    break;
                }
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                int stackSize = stack.stackSize;
                if (excessBuckets >= stackSize) {
                    this.throwItem(slot);
                    excessBuckets -= stackSize;
                } else {
                    this.throwItem(slot, stackSize - excessBuckets);
                    excessBuckets = 0;
                }
            }
        }

        if (totalSnowballsEggs > snowballEggLimit.getValue().intValue()) {
            int excessSnowballsEggs = totalSnowballsEggs - snowballEggLimit.getValue().intValue();
            for (int slot : snowballEggSlots) {
                if (excessSnowballsEggs <= 0) {
                    break;
                }
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                int stackSize = stack.stackSize;
                if (excessSnowballsEggs >= stackSize) {
                    this.throwItem(slot);
                    excessSnowballsEggs -= stackSize;
                } else {
                    this.throwItem(slot, stackSize - excessSnowballsEggs);
                    excessSnowballsEggs = 0;
                }
            }
        }

        if (totalEnderPearls > enderPearlLimit.getValue().intValue()) {
            int excessEnderPearls = totalEnderPearls - enderPearlLimit.getValue().intValue();
            for (int slot : enderPearlSlots) {
                if (excessEnderPearls <= 0) {
                    break;
                }
                ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slot);
                int stackSize = stack.stackSize;
                if (excessEnderPearls >= stackSize) {
                    this.throwItem(slot);
                    excessEnderPearls -= stackSize;
                } else {
                    this.throwItem(slot, stackSize - excessEnderPearls);
                    excessEnderPearls = 0;
                }
            }
        }
        if (sword != -1 && sword != this.swordSlot.getValue().intValue() - 1 && this.swordSlot.getValue().intValue() != 0) {
            this.moveItem(sword, this.swordSlot.getValue().intValue() - 37);
        }

        if (pickaxe != -1 && pickaxe != this.pickaxeSlot.getValue().intValue() - 1 && this.pickaxeSlot.getValue().intValue() != 0) {
            this.moveItem(pickaxe, this.pickaxeSlot.getValue().intValue() - 37);
        }

        if (axe != -1 && axe != this.axeSlot.getValue().intValue() - 1 && this.axeSlot.getValue().intValue() != 0) {
            this.moveItem(axe, this.axeSlot.getValue().intValue() - 37);
        }

        if (shovel != -1 && shovel != this.shovelSlot.getValue().intValue() - 1 && this.shovelSlot.getValue().intValue() != 0) {
            this.moveItem(shovel, this.shovelSlot.getValue().intValue() - 37);
        }

        if (bow != -1 && bow != this.bowSlot.getValue().intValue() - 1 && this.bowSlot.getValue().intValue() != 0) {
            this.moveItem(bow, this.bowSlot.getValue().intValue() - 37);
        }

        if (rod != -1 && rod != this.rodSlot.getValue().intValue() - 1 && this.rodSlot.getValue().intValue() != 0 && (bow == -1 && this.rodSlot.getValue().intValue() == this.bowSlot.getValue().intValue())) {
            this.moveItem(rod, this.rodSlot.getValue().intValue() - 37);
        }

        if (this.potionSlot.getValue().intValue() < this.potionSlot.getSecondValue().intValue()) {
            int items = this.potionSlot.getSecondValue().intValue() - this.potionSlot.getValue().intValue() + 1;
            potionStacks.sort((a, b) -> {
                ItemPotion potionA = (ItemPotion) a.getItemStack().getItem();
                ItemPotion potionB = (ItemPotion) b.getItemStack().getItem();

                if (prioritizeSplashPotions.getValue()) {
                    boolean isSplashA = ItemPotion.isSplash(a.getItemStack().getMetadata());
                    boolean isSplashB = ItemPotion.isSplash(b.getItemStack().getMetadata());

                    if (isSplashA && !isSplashB) {
                        return -1;
                    } else if (!isSplashA && isSplashB) {
                        return 1;
                    }
                }

                return Integer.compare(
                        PlayerUtil.potionRanking(potionB.getEffects(b.getItemStack()).get(0).getPotionID()),
                        PlayerUtil.potionRanking(potionA.getEffects(a.getItemStack()).get(0).getPotionID())
                );
            });

            for (int i = 0; i < items; i++) {
                if (potionStacks.size() <= i) {
                    break;
                }
                this.moveItem(potionStacks.get(i).getNumber(), this.potionSlot.getValue().intValue() - 37 + i);
            }
        }

        if (this.foodSlot.getValue().intValue() < this.foodSlot.getSecondValue().intValue()) {
            int items = this.foodSlot.getSecondValue().intValue() - this.foodSlot.getValue().intValue() + 1;
            foodStacks.sort((a, b) -> {
                ItemFood foodA = (ItemFood) a.getItemStack().getItem();
                ItemFood foodB = (ItemFood) b.getItemStack().getItem();
                return Float.compare(foodB.getSaturationModifier(b.getItemStack()), foodA.getSaturationModifier(a.getItemStack()));
            });

            for (int i = 0; i < items; i++) {
                if (foodStacks.size() <= i) {
                    break;
                }
                this.moveItem(foodStacks.get(i).getNumber(), this.foodSlot.getValue().intValue() - 37 + i);
            }
        }


        if (this.canOpenInventory() && !this.moved) {
            this.closeInventory();
        }
    };

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        this.attackTicks = 0;
    };

    @Override
    public void onDisable() {
        if (this.canOpenInventory()) {
            this.closeInventory();
        }
    }

    private void openInventory() {
        if (!this.open) {
            PacketUtil.send(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            this.open = true;
        }
    }

    private void closeInventory() {
        if (this.open) {
            PacketUtil.send(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
            this.open = false;
        }
    }

    private boolean canOpenInventory() {
        return this.getModule(InventoryMove.class).isEnabled() && !(mc.currentScreen instanceof GuiInventory);
    }

    private void throwItem(final int slot) {
        if ((!this.moved || this.nextClick <= 0) && !SelectorDetectionComponent.selector(slot, !dropCustomItems.getValue())) {
            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 1, 4, mc.thePlayer);

            this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }
    private void throwItem(final int slot, final int amountLeft) {
        if ((!this.moved || this.nextClick <= 0) && !SelectorDetectionComponent.selector(slot, !dropCustomItems.getValue())) {
            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 0, 0, mc.thePlayer);

            System.out.println(amountLeft);

            for (int i = 0; i < amountLeft; i++) {
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 1, 0, mc.thePlayer);
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);

            this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }
    private void moveItem(final int slot, final int destination) {
        if ((!this.moved || this.nextClick <= 0) && !SelectorDetectionComponent.selector(slot, !useCustomItems.getValue())) {

            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), this.slot(destination), 2, mc.thePlayer);

            this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }

    private void equipItem(final int slot) {
        if ((!this.moved || this.nextClick <= 0) && !SelectorDetectionComponent.selector(slot, !useCustomItems.getValue())) {

            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 0, 1, mc.thePlayer);

            this.nextClick = Math.round(MathUtil.getRandom(this.delay.getValue().intValue(), this.delay.getSecondValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }

    private float damage(final ItemStack stack) {
        final ItemSword sword = (ItemSword) stack.getItem();
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        return (float) (sword.getDamageVsEntity() + level * 1.25);
    }

    private float power(final ItemStack stack) {
        final ItemBow bow = (ItemBow) stack.getItem();
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        return (float) level;
    }

    private float mineSpeed(final ItemStack stack) {
        final Item item = stack.getItem();
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);

        switch (level) {
            case 1:
                level = 30;
                break;

            case 2:
                level = 69;
                break;

            case 3:
                level = 120;
                break;

            case 4:
                level = 186;
                break;

            case 5:
                level = 271;
                break;

            default:
                level = 0;
                break;
        }

        if (item instanceof ItemPickaxe) {
            final ItemPickaxe pickaxe = (ItemPickaxe) item;
            return pickaxe.getToolMaterial().getEfficiencyOnProperMaterial() + level;
        } else if (item instanceof ItemSpade) {
            final ItemSpade shovel = (ItemSpade) item;
            return shovel.getToolMaterial().getEfficiencyOnProperMaterial() + level;
        } else if (item instanceof ItemAxe) {
            final ItemAxe axe = (ItemAxe) item;
            return axe.getToolMaterial().getEfficiencyOnProperMaterial() + level;
        }

        return 0;
    }

    private int armorReduction(final ItemStack stack) {
        final ItemArmor armor = (ItemArmor) stack.getItem();
        return armor.damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{stack}, DamageSource.generic);
    }

    private int slot(final int slot) {
        if (slot >= 36) {
            return 8 - (slot - 36);
        }

        if (slot < 9) {
            return slot + 36;
        }

        return slot;
    }

    @EventLink
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();
//s
            if (!(packet.getStack() != null
                    && packet.getStack().getItem() == Items.water_bucket)) {
                this.placeTicks = 0;
            }
        }
    };
}
