package club.bluezenith.module.modules.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.AttackEvent;
import club.bluezenith.events.impl.SpawnPlayerEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.client.ServerUtils;
import club.bluezenith.util.math.MathUtil;
import net.minecraft.block.BlockClay;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class InvManager extends Module {
    public InvManager() {
        super("InvManager", ModuleCategory.PLAYER, "InvCleaner", "InventoryManager", "InventoryCleaner");
    }

    public static boolean canSort = true;
    private final BooleanValue cleaner = new BooleanValue("Cleaner", true, true, null).setIndex(1);
    private final BooleanValue onlyWhenOpened = new BooleanValue("Only when opened", true, true, null).setIndex(2);
    private final BooleanValue disableOnRespawn = new BooleanValue("Disable on respawn", false).setIndex(3);
    private final BooleanValue hypixelLobbyCheck = new BooleanValue("Lobby check", true).showIf(() -> ServerUtils.hypixel).setIndex(4);
    private final IntegerValue minDelay = new IntegerValue("Min delay", 50, 0, 500, 10, true, null).setIndex(5);
    private final IntegerValue maxDelay = new IntegerValue("Max delay", 50, 0, 500, 10, true, null).setIndex(6);
    private final IntegerValue maxBlocks = new IntegerValue("Max blocks", 128, 0, 256, 16, true, null).setIndex(7);

    private final ModeValue slot1 = new ModeValue("Slot 1", "Sword", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(7);
    private final ModeValue slot2 = new ModeValue("Slot 2", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(8);
    private final ModeValue slot3 = new ModeValue("Slot 3", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(9);
    private final ModeValue slot4 = new ModeValue("Slot 4", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(10);
    private final ModeValue slot5 = new ModeValue("Slot 5", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(11);
    private final ModeValue slot6 = new ModeValue("Slot 6", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(12);
    private final ModeValue slot7 = new ModeValue("Slot 7", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(13);
    private final ModeValue slot8 = new ModeValue("Slot 8", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(14);
    private final ModeValue slot9 = new ModeValue("Slot 9", "Ignore", true, null, "Sword", "Axe", "Pickaxe", "Shovel", "Gapple", "Block", "Food", "Epearl", "Bow", "Ignore").setIndex(15);
    private final BooleanValue stopInCombat = new BooleanValue("Stop in combat", false, true, null).setIndex(16);
    private final IntegerValue combatCooldown = new IntegerValue("Combat cooldown", 1000, 1, 3000, 50).showIf(stopInCombat::get).setIndex(17);
    private final MillisTimer combatTimer = new MillisTimer();

    public static boolean isCleanerDone = false;

    private final ModeValue[] slots = {slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9};

    private final MillisTimer delay = new MillisTimer();
    private long unfunnyVariable = 0;

    @Listener
    public void onUpdate(UpdatePlayerEvent e) {
        if(mc.thePlayer == null || !canSort) return;

        if(ServerUtils.hypixel && hypixelLobbyCheck.get()) {
            boolean shouldReturn = false;
            final ItemStack compass = mc.thePlayer.inventory.getStackInSlot(0);
            final ItemStack head = mc.thePlayer.inventory.getStackInSlot(1);
            if(compass != null && compass.getItem() == Items.compass && head != null && head.getItem() instanceof ItemSkull) {
                shouldReturn = true; //main lobby check
            }
            final ItemStack bed = mc.thePlayer.inventory.getStackInSlot(8);
            if(bed != null && bed.getItem() instanceof ItemBed) {
                shouldReturn = true; //blitz, sw, etc check
            }
            if(shouldReturn) return;
        }

        if (player.hurtTime == 9) {
            combatTimer.reset();
        }

        if (stopInCombat.get() && !combatTimer.hasTimeReached(combatCooldown.get())) {
            return;
        }

        final InventoryPlayer inventory = mc.thePlayer.inventory;
        final Container invcon = mc.thePlayer.inventoryContainer;

        if(!onlyWhenOpened.get()) {
            isCleanerDone = clean(invcon);

            if (isCleanerDone) {
                sort(invcon, inventory);
            }

        } else if((mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiContainerCreative)) {
            isCleanerDone = clean(invcon);

            if (isCleanerDone) {
                sort(invcon, inventory);
            }
        }
    }

    private boolean clean(Container invcon) {
        final List<ItemStack> invStacks = invcon.getInventory();

        final ArrayList<ItemStack> helmets = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> chestplates = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> leggings = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> boots = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> swords = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> pickaxes = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> axes = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> shovels = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> bows = new ArrayList<ItemStack>(36);
        final ArrayList<ItemStack> blocks = new ArrayList<ItemStack>(36);


        addItems(invStacks, helmets, chestplates, leggings, boots, swords, pickaxes, axes, shovels, blocks);

        int currentBlockAmount = 0;

        for (int i = invStacks.size() - 1; i >= 0; i--) {
            ItemStack itemStack = invStacks.get(i);

            if (itemStack == null || !(itemStack.getItem() instanceof ItemBlock) || itemStack.getItem() instanceof ItemAnvilBlock || Scaffold.badBlocks.contains(((ItemBlock) itemStack.getItem()).getBlock())) continue;

            currentBlockAmount += itemStack.stackSize;

            if (currentBlockAmount <= maxBlocks.get()) {
                invStacks.set(i, null);
            }
        }

        ItemStack bestSword = sortSword(swords);

        ItemStack bestPickaxe;
        if (!pickaxes.isEmpty()) {
            bestPickaxe = getBestTool(pickaxes, "pickaxe");
        } else bestPickaxe = null;

        ItemStack bestAxe;
        if (!axes.isEmpty()) {
            bestAxe = getBestTool(axes, "axe");
        } else bestAxe = null;

        ItemStack bestShovel;
        if (!shovels.isEmpty()) {
            bestShovel = getBestTool(shovels, "shovel");
        } else bestShovel = null;

        ItemStack bestHelmet;
        if (!helmets.isEmpty()) {
            bestHelmet = getBestArmorPiece(helmets);
        } else bestHelmet = null;

        ItemStack bestChestplate;
        if (!chestplates.isEmpty()) {
            bestChestplate = getBestArmorPiece(chestplates);
        } else bestChestplate = null;

        ItemStack bestLeggings;
        if (!leggings.isEmpty()) {
            bestLeggings = getBestArmorPiece(leggings);
        } else bestLeggings= null;

        ItemStack bestBoots;
        if (!boots.isEmpty()) {
            bestBoots = getBestArmorPiece(boots);
        } else bestBoots = null;

        for (int i = invStacks.size() - 1; i >= 0; i--) {
            ItemStack itemStack = invStacks.get(i);
            if (itemStack == null) continue;

            if (itemStack == bestHelmet || itemStack == bestBoots || itemStack == bestChestplate || itemStack == bestLeggings || itemStack == bestSword || itemStack == bestPickaxe || itemStack == bestAxe || itemStack == bestShovel)
            {
                invStacks.set(i, null);
            }
        }
        boolean isCleanerDone = true;

        isCleanerDone = doTheCleaning(invcon, invStacks, isCleanerDone);
        return isCleanerDone;
    }

    private boolean doTheCleaning(Container invcon, List<ItemStack> invStacks, boolean isCleanerDone) {
        for (int i = 0; i < invStacks.size(); i++) {

            ItemStack itemStack = invStacks.get(i);
            if(itemStack == null || itemStack.stackSize == 0) continue;

            Item item = itemStack.getItem();

            if(
                    delay.hasTimeReached(unfunnyVariable)
                            && !(item instanceof ItemPotion)
                            && !(item instanceof ItemBow)
                            && !(item.equals(Items.arrow))
                            && !(item instanceof ItemFood)
                            && !(item instanceof ItemEnderPearl)
            ) {
                mc.playerController.windowClick(invcon.windowId, i, 1, 4, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                isCleanerDone = false;
                break;
            }
        }
        return isCleanerDone;
    }

    private ItemStack sortSword(ArrayList<ItemStack> swords) {
        ItemStack bestSword;
        if (!swords.isEmpty()) {
            bestSword = swords.get(0);
            for (ItemStack sword : swords) {
                ItemSword itemSword = (ItemSword) sword.getItem();
                ItemSword bestItemSword = (ItemSword) bestSword.getItem();

                float swordDamage = itemSword.getDamageVsEntity() + 4.0f;
                float bestSwordDamage = bestItemSword.getDamageVsEntity() + 4.0f;
                float swordDurability = itemSword.getMaxDamage();
                float bestSwordDurability = bestItemSword.getMaxDamage();
                NBTTagList swordEnchants = sword.getEnchantmentTagList();
                NBTTagList bestSwordEnchants = bestSword.getEnchantmentTagList();

                if (swordEnchants != null) {
                    for (int i = 0; i < swordEnchants.tagCount(); i++) {
                        NBTTagCompound sus = swordEnchants.getCompoundTagAt(i);

                        int id = sus.getInteger("id");
                        int lvl = sus.getInteger("lvl");

                        Enchantment ench = Enchantment.getEnchantmentById(id);
                        if (ench instanceof EnchantmentDamage) {
                            EnchantmentDamage newench = (EnchantmentDamage) ench;

                            swordDamage += newench.calcDamageByCreature(lvl, EnumCreatureAttribute.ARTHROPOD);
                        }
                        else if (ench instanceof EnchantmentDurability) {
                            swordDurability *= (lvl + 1);
                        }
                    }
                }
                if (bestSwordEnchants != null) {
                    for (int i = 0; i < bestSwordEnchants.tagCount(); i++) {
                        NBTTagCompound sus = bestSwordEnchants.getCompoundTagAt(i);

                        int id = sus.getInteger("id");
                        int lvl = sus.getInteger("lvl");

                        Enchantment ench = Enchantment.getEnchantmentById(id);
                        if (ench instanceof EnchantmentDamage) {
                            EnchantmentDamage newench = (EnchantmentDamage) ench;

                            bestSwordDamage += newench.calcDamageByCreature(lvl, EnumCreatureAttribute.ARTHROPOD);
                        }
                        else if (ench instanceof EnchantmentDurability) {
                            bestSwordDurability *= (lvl + 1);
                        }
                    }
                }

                // final Multimap<String, AttributeModifier> attributes = itemSword.getItemAttributeModifiers();

                if (swordDamage > bestSwordDamage) {
                    bestSword = sword;
                    continue;
                }

                if (swordDamage == bestSwordDamage && swordDurability > bestSwordDurability) {
                    bestSword = sword;
                    continue;
                }
            }
        } else bestSword = null;
        return bestSword;
    }

    private void addItems(List<ItemStack> invStacks, ArrayList<ItemStack> helmets, ArrayList<ItemStack> chestplates, ArrayList<ItemStack> leggings, ArrayList<ItemStack> boots, ArrayList<ItemStack> swords, ArrayList<ItemStack> pickaxes, ArrayList<ItemStack> axes, ArrayList<ItemStack> shovels, ArrayList<ItemStack> blocks) {
        for (ItemStack stack : invStacks) {
            if (stack == null || stack.stackSize == 0) continue;

            final Item item = stack.getItem();

            if (item instanceof ItemBlock) {
                blocks.add(stack);
                continue;
            }
            if (item instanceof ItemSword) {
                swords.add(stack);
                continue;
            }
            if (item instanceof ItemPickaxe) {
                pickaxes.add(stack);
                continue;
            }
            if (item instanceof ItemAxe) {
                axes.add(stack);
                continue;
            }
            if (item instanceof ItemSpade) {
                shovels.add(stack);
                continue;
            }
            if (item instanceof ItemArmor) {
                final ItemArmor sus = (ItemArmor) stack.getItem();

                switch (sus.armorType) {
                    case 0:
                        helmets.add(stack);
                        break;
                    case 1:
                        chestplates.add(stack);
                        break;
                    case 2:
                        leggings.add(stack);
                        break;
                    case 3:
                        boots.add(stack);
                        break;
                }
                continue;
            }

        }
    }

    private void sort(Container invcon, InventoryPlayer inv) {
        final List<ItemStack> invStacks = invcon.getInventory();

        for (int i = 0, slotsLength = slots.length; i < slotsLength; i++) {
            ModeValue slotMode = slots[i];
            String slotItemName = slotMode.get();
            final ItemStack itemStack1 = invStacks.get(45 - 9 + i);

            switch (slotItemName) {
                case "Sword":
                    if (caseSword(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Gapple":
                    if (caseGapple(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Axe":
                    if (caseAxe(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Pickaxe":
                    if (casePickaxe(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Shovel":
                    if (caseShovel(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Epearl":
                    if (caseEpearl(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Block":
                    if (caseBlock(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Food":
                    if (caseFood(invcon, invStacks, i, itemStack1)) return;
                    break;
                case "Bow":
                    if (caseBow(invcon, invStacks, i, itemStack1)) return;
                    break;
            }

        }

    }

    private boolean caseBow(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemBow)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemBow) {
                    if (j >= 36 && slots[j - 36].get().equals("Bow")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseFood(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemFood)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemFood && !(itemStack.getItem() instanceof ItemAppleGold)) {
                    if (j >= 36 && slots[j - 36].get().equals("Food")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseBlock(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemBlock)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemBlock) {
                    if (j >= 36 && slots[j - 36].get().equals("Block")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseEpearl(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemEnderPearl)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemEnderPearl) {
                    if (j >= 36 && slots[j - 36].get().equals("Epearl")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseShovel(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemSpade)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemSpade) {
                    if (j >= 36 && slots[j - 36].get().equals("Shovel")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean casePickaxe(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemPickaxe)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemPickaxe) {
                    if (j >= 36 && slots[j - 36].get().equals("Pickaxe")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseAxe(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemAxe)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemAxe) {
                    if (j >= 36 && slots[j - 36].get().equals("Axe")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseGapple(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemAppleGold)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);
                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemAppleGold) {
                    if (j >= 36 && slots[j - 36].get().equals("Gapple")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private boolean caseSword(Container invcon, List<ItemStack> invStacks, int i, ItemStack itemStack1) {
        if (itemStack1 == null || !(itemStack1.getItem() instanceof ItemSword)) {
            ItemStack itemToMove = null;
            int itemSlot = 0;

            for (int j = 0, invStacksSize = invStacks.size(); j < invStacksSize; j++) {
                ItemStack itemStack = invStacks.get(j);

                if(itemStack == null || j == 45 - 9 + i) continue;

                if(itemStack.getItem() instanceof ItemSword) {
                    if (j >= 36 && slots[j - 36].get().equals("Sword")) {
                        continue;
                    }
                    itemToMove = itemStack;
                    itemSlot = j;
                    break;
                }
            }

            if (itemToMove != null && delay.hasTimeReached(unfunnyVariable)) {
                mc.playerController.windowClick(invcon.windowId, itemSlot, i, 2, mc.thePlayer);
                unfunnyVariable = MathUtil.getRandomInt(minDelay.get(), maxDelay.get());
                delay.reset();
                return true;
            }
        }
        return false;
    }

    private ItemStack getBestArmorPiece(ArrayList<ItemStack> armorPieces) {

        ItemStack bestArmorPiece = armorPieces.get(0);

        if (armorPieces.size() == 1) {
            return bestArmorPiece;
        }

        for (ItemStack armorPiece : armorPieces) {

            final int helmetDamageReduce = ((ItemArmor) armorPiece.getItem()).damageReduceAmount;
            final int bestHelmetDamageReduce = ((ItemArmor)bestArmorPiece.getItem()).damageReduceAmount;

            final int helmetMaxDamage = ((ItemArmor) armorPiece.getItem()).getMaxDamage();
            final int bestHelmetMaxDamage = ((ItemArmor)bestArmorPiece.getItem()).getMaxDamage();

            if (helmetDamageReduce > bestHelmetDamageReduce) {
                bestArmorPiece = armorPiece;
                continue;
            }

//            if (helmetDamageReduce == bestHelmetDamageReduce && helmetMaxDamage > bestHelmetMaxDamage) {
//                bestArmorPiece = armorPiece;
//                continue;
//            }
        }

        return bestArmorPiece;
    }

    private ItemStack getBestTool(ArrayList<ItemStack> tools, String toolType) {
        ItemStack bestTool = tools.get(0);

        if (tools.size() == 1) {
            return bestTool;
        }

        for (ItemStack tool : tools) {
            ItemTool itemTool = (ItemTool) tool.getItem();
            ItemTool bestItemTool = (ItemTool) bestTool.getItem();

            switch(toolType) {
                case "pickaxe":
                    if (itemTool.getStrVsBlock(tool, new BlockStone()) > bestItemTool.getStrVsBlock(bestTool, new BlockStone())) {
                        bestTool = tool;
                    }
                    break;
                case "axe":
                    if (itemTool.getStrVsBlock(tool, new BlockPlanks()) > bestItemTool.getStrVsBlock(bestTool, new BlockPlanks())) {
                        bestTool = tool;
                    }
                    break;
                case "shovel":
                    if (itemTool.getStrVsBlock(tool, new BlockClay()) > bestItemTool.getStrVsBlock(bestTool, new BlockClay())) {
                        bestTool = tool;
                    }
                    break;
            }
        }
        return bestTool;
    }

    @Listener
    public void onRespawn(SpawnPlayerEvent event) {
        if(disableOnRespawn.get()) {
            this.toggle();
            BlueZenith.getBlueZenith().getNotificationPublisher().postWarning(displayName, "Disabled due to a respawn", 2500);
        }
    }

    @Listener
    public void onAttack(AttackEvent event) {
        if(stopInCombat.get() && event.isPost()) {
            combatTimer.reset();
        }
    }

    @Override
    public String getTag() {
        return valueOf(minDelay.get());
    }
}