/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.combat.AutoArmor;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.SlotUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ArmorItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import java.util.*;
import java.util.function.Consumer;

import static net.minecraft.entity.damage.DamageTypes.GENERIC;

public final class InvManager extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public InvManager() {
        super(Categories.Misc, "InvManager", "Manages your inventory for you");
    }

    private final Setting<Boolean> inInv = sgGeneral.add(new BoolSetting.Builder()
        .name("only-in-inventory")
        .description("Does shit only in inventory")
        .defaultValue(true)
        .build()
    );

    int swordSlot = 0;
    int axeSlot = 2;
    int shovelSlot = 3;
    int PickaxeSlot = 1;
    int ArmorSlot = 5;
    int blockSlot = 7;
    int foodSlot = 1;
    private final int helmet = 3;
    private final int chestplate = 2;
    private final int leggings = 1;
    private final int boots = 0;
    private final int[] armorPieces = new int[]{helmet, chestplate, leggings, boots};



    // TODO: Order Hotbar (settings for where the items shall be)
    //  SUBTODO: Find desired items (ret: Int or FindItemResult)
    //  SUBTODO: After that, move item to desired slot

    // TODO: throw out shitty items we don't need (setting for how many blocks should be in the inventory?)
    //  SUBTODO: Get best items in inventory (ret: Int)
    //  SUBTODO: Check if best items are in use (ret: bool)
    //  SUBTODO: If so, throw out all other results (add a delay for that)
    //  SUBTODO: If not in use, replace currently used items with best items and throw rest out

    // TODO: put certain items in offhand, depending on settings
    //  SUBTODO: Setting for shit in offhand
    //  SUBTODO: Get item supposed to be in offhand, maybe assign values for blocks?
    //  SUBTODO: Switch item, when we have it

    // TODO: place blocks if sword in hand && hunger full / no food in offhand
    //  SUBTODO: get hunger level, if not full, return
    //  SUBTODO: Place block where pointing


    @Override
    public void onActivate() {
        super.onActivate();
    }

    // TODO: Order Hotbar (settings for where the items shall be)
    //  SUBTODO: Find desired items
    //  SUBTODO: After that, move item to desired slot

    private int getBestPickaxeSlot() {
        int slotA = mc.player.getInventory().selectedSlot;
        double damageA = 0;
        double currentDamageA;

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof PickaxeItem pickaxeItem) {
                currentDamageA =  pickaxeItem.getMaterial().getMiningSpeedMultiplier() + pickaxeItem.getMaterial().getDurability();
                // currentDamageA = pickaxeItem.getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);

                if (currentDamageA > damageA) {
                    damageA = currentDamageA;
                    slotA = i;
                }
            }
        }

        return slotA;
    }

    private void processNonBestPickaxeSlots(Consumer<Integer> slotProcessor) {
        ItemStack bestPickaxe = mc.player.getInventory().getStack(getBestPickaxeSlot());

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof PickaxeItem pickaxeItem && i != getBestPickaxeSlot()) {
                slotProcessor.accept(i);
            }
        }
    }

    private int getBestShovelSlot() {
        int slotA = mc.player.getInventory().selectedSlot;
        double damageA = 0;
        double currentDamageA;

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof ShovelItem shovelItem) {
                currentDamageA = shovelItem.getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);

                if (currentDamageA > damageA) {
                    damageA = currentDamageA;
                    slotA = i;
                }
            }
        }

        return slotA;
    }

    private void processNonBestShovelSlots(Consumer<Integer> slotProcessor) {
        ItemStack bestShovel = mc.player.getInventory().getStack(getBestShovelSlot());

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof ShovelItem shovelItem && i != getBestShovelSlot()) {
                slotProcessor.accept(i);
            }
        }
    }

    private int getBestAxeSlot() {
        int slotA = mc.player.getInventory().selectedSlot;
        double damageA = 0;
        double currentDamageA;

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof AxeItem axeItem) {
                currentDamageA = axeItem.getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);

                if (currentDamageA > damageA) {
                    damageA = currentDamageA;
                    slotA = i;
                }
            }
        }

        return slotA;
    }

    private void processNonBestAxeSlots(Consumer<Integer> slotProcessor) {
        ItemStack bestAxe = mc.player.getInventory().getStack(getBestAxeSlot());

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof AxeItem axeItem && i != getBestAxeSlot()) {
                slotProcessor.accept(i);
            }
        }
    }

    private int getBestSwordSlot() {
        int slotS = mc.player.getInventory().selectedSlot;
        double damageS = 0;
        double currentDamageS;

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof SwordItem swordItem) {
                currentDamageS = swordItem.getMaterial().getAttackDamage() + EnchantmentHelper.getAttackDamage(stack, EntityGroup.DEFAULT);

                if (currentDamageS > damageS) {
                    damageS = currentDamageS;
                    slotS = i;
                }
            }
        }

        return slotS;
    }

    private void processNonBestSwordSlots(Consumer<Integer> slotProcessor) {
        ItemStack bestSword = mc.player.getInventory().getStack(getBestSwordSlot());

        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof SwordItem swordItem && i != getBestSwordSlot()) {
                slotProcessor.accept(i);
            }
        }
    }

    @EventHandler
    private void onTick(final TickEvent.Pre e) {
        if (mc.player == null || mc.world == null) return;
        if (inInv.get() && mc.currentScreen == null) return;

        int bestShovelSlot = getBestShovelSlot();
        int bestSwordSlot = getBestSwordSlot();
        int bestPickaxeSlot = getBestPickaxeSlot();
        int bestAxeSlot = getBestAxeSlot();

        if (bestSwordSlot == swordSlot){
            return;
        }else {
            InvUtils.move().from(bestSwordSlot).to(swordSlot);
            processNonBestSwordSlots(slot -> {
                InvUtils.drop().slot(slot);
            });
        }

        if (bestAxeSlot == axeSlot){
            return;
        }else {
            InvUtils.move().from(bestAxeSlot).to(axeSlot);
            processNonBestAxeSlots(slot -> {
                InvUtils.drop().slot(slot);
            });
        }

        if (bestShovelSlot == shovelSlot){
            return;
        }else {
            InvUtils.move().from(bestShovelSlot).to(shovelSlot);
            processNonBestShovelSlots(slot -> {
                InvUtils.drop().slot(slot);
            });
        }

        if (bestPickaxeSlot == PickaxeSlot){
            return;
        }else {
            InvUtils.move().from(bestPickaxeSlot).to(PickaxeSlot);
            processNonBestPickaxeSlots(slot -> {
                InvUtils.drop().slot(slot);
            });
        }
    }

    private List<Item> findDesiredItems() {
        // Implement logic to find and return the desired items
        // For example, iterate through the inventory and check against settings
        // ...
        Inventory inv = mc.player.getInventory();

        return new ArrayList<>(); // Placeholder, replace with actual list
    }

    private int getDesiredSlot(Item item) {
        // Implement logic to determine the desired slot for the item
        // ...

        int desiredSlot = 0;

        return desiredSlot; // Placeholder, replace with actual slot
    }

    private void moveItemToSlot(Item item, int slot) {
        // Implement logic to move the item to the desired slot
        // ...
    }


    private boolean canPlaceBlock() {

        Item item = mc.player.getMainHandStack().getItem();

        return mc.player.getInventory().getMainHandStack().isEmpty() || isWeapon(item) || (mc.player.getOffHandStack().isFood() && !mc.player.getHungerManager().isNotFull());
    }

    private boolean isWeapon(final Item item) {

        HitResult coolBlock = mc.player.raycast(4.5, mc.getTickDelta(), false);

        BlockPos blockPos = BlockPos.ofFloored(coolBlock.getPos());

        Block block = mc.world.getBlockState(blockPos).getBlock();

        return item instanceof SwordItem || item instanceof AxeItem || item instanceof PickaxeItem || item instanceof HoeItem || (item instanceof ShovelItem && !block.equals(Blocks.GRASS_BLOCK));
    }

        // Other TODO methods and their corresponding private methods...


}
