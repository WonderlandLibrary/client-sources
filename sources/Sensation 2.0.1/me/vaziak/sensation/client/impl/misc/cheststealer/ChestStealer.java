package me.vaziak.sensation.client.impl.misc.cheststealer;

import net.minecraft.block.BlockChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.List;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.BooleanProperty;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.ItemsProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.anthony.ContainerUtil;
import me.vaziak.sensation.utils.anthony.ItemUtil;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;

public class ChestStealer extends Module {

    private BooleanProperty prop_ignoreCustomName = new BooleanProperty("Ignore Custom Chest", "Prevents you from stealing items from chests with a custom displayname.",
            null, true);
    private BooleanProperty instant = new BooleanProperty("Hypixel Instant", "Steals items very fast - instant hypixel chest steal ;)",
            null, true);

    private ItemsProperty prop_blacklistedItems = new ItemsProperty("BL Items", "Items that won't be stolen.",
            null, false, new ArrayList<>());

    private StringsProperty prop_blacklistedProps = new StringsProperty("BL Properties", "Properties that will prevent an item from being stolen.",
            null, true, false, new String[] {"Harmful Potion", "Worse Sword", "Duplicate Tool", "Worse Armor"}, new Boolean[] {false, false, false, false});

    private DoubleProperty prop_stealDelay = new DoubleProperty("Min Steal delay", "The time in between when each item is taken", () -> !instant.getValue(), 150, 0, 250, 10, "ms");


    private DoubleProperty prop_maxstealDelay = new DoubleProperty("Max Steal delay", "The time in between when each item is taken",
            () -> !instant.getValue(), 150, 0, 250, 10, "ms");

    private int fuckingdelay;
    private TimerUtil takeStopwatch;
    public boolean stealing;
    private final ArrayList<BlockPos> lootedChestPositions;

    private TimerUtil stopwatch;

    private boolean decreasing;

    private int lastIndex;

    private int exploitTime;

    public ChestStealer() {
        super("Chest Stealer", Category.MISC);
        registerValue(prop_ignoreCustomName, instant, prop_blacklistedItems, prop_blacklistedProps, prop_stealDelay, prop_maxstealDelay);
        takeStopwatch = new TimerUtil();
        lootedChestPositions = new ArrayList<>();

        stopwatch = new TimerUtil();
    }
    public void onEnable() {
        fuckingdelay = prop_stealDelay.getValue().intValue();
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        if (playerUpdateEvent.isPre()) {
            if (mc.currentScreen instanceof GuiChest) {
                GuiChest chest = (GuiChest) mc.currentScreen;
                if (chest.lowerChestInventory.getName().equals("Enchant Item")) {
                    ItemStack stack = chest.lowerChestInventory.getStackInSlot(33);
                    boolean contains = false;
                    for (String s : stack.getTooltip(mc.thePlayer, true)) {
                        if (s.contains("Blessing V")) {
                            contains = true;
                            break;
                        }
                    }

                    if (stopwatch.hasPassed(500)) {
                        if (!contains) {
                            InventoryPlayer inventoryplayer = mc.thePlayer.inventory;
                            if ((inventoryplayer.getItemStack() != null
                                    && inventoryplayer.getItemStack().getDisplayName().toLowerCase().contains("book"))

                                    || (chest.lowerChestInventory.getStackInSlot(13) != null
                                    && chest.lowerChestInventory.getStackInSlot(13).getDisplayName().toLowerCase().contains("book")));

                            mc.playerController.windowClick(chest.inventorySlots.windowId, 13, 0, 0, mc.thePlayer);
                        } else {
                            mc.playerController.windowClick(chest.inventorySlots.windowId, 33, 0, 0, mc.thePlayer);
                        }
                        stopwatch.reset();
                    }

                    return;
                }

                BlockPos pos = mc.objectMouseOver.getBlockPos();
                if (pos != null && mc.theWorld.getBlockState(pos).getBlock() instanceof BlockChest) {
                    lootedChestPositions.add(pos);
                    BlockChest blockChest = (BlockChest) mc.theWorld.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock();
                    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL.facings()) {
                        if (mc.theWorld.getBlockState(pos.offset(enumfacing)).getBlock() == blockChest) {
                            lootedChestPositions.add(pos.offset(enumfacing));
                        }
                    }
                }

                if (prop_ignoreCustomName.getValue()) {
                    if (!(chest.lowerChestInventory.getName().contains("Chest") || chest.lowerChestInventory.getName().contains("LOW"))) {
                        return;
                    }
                    
                }

                boolean noMoreItems = true;
                for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); index++) {
                    ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                    if (stack == null)
                        continue;

                    if (prop_blacklistedItems.getValue().contains(stack.getItem()))
                        continue;

                    if (prop_blacklistedProps.getValue().get("Harmful Potion")) {
                        if (stack.getItem() instanceof ItemPotion) {
                            if (isPotionNegative(stack))
                                continue;
                        }
                    }

                    if (prop_blacklistedProps.getValue().get("Worse Sword")) {
                        if (stack.getItem() instanceof ItemSword) {
                            boolean shouldContinue = false;
                            for (int i = 0; i < 44; i++) {
                                if (i == index) continue;
                                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                                    ItemStack stackAtIndex = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                                    if (stackAtIndex.getItem() instanceof ItemSword) {
                                        if (ItemUtil.compareDamage(stack, stackAtIndex) == stackAtIndex) {
                                            shouldContinue = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (shouldContinue)
                                continue;
                        }
                    }

                    if (prop_blacklistedProps.getValue().get("Duplicate Tool")) {
                        if (stack.getItem() instanceof ItemAxe || stack.getItem() instanceof ItemBow || stack.getItem() instanceof ItemFishingRod || stack.getItem() instanceof ItemPickaxe || Item.getIdFromItem(stack.getItem()) == 346) {
                            boolean shouldContinue = false;
                            for (int i = 44; i > 0; i--) {
                                if (i == index) continue;
                                if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                                    ItemStack stackAtIndex = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                                    if ((stackAtIndex.getItem() instanceof ItemSword || stackAtIndex.getItem() instanceof ItemAxe || stackAtIndex.getItem() instanceof ItemBow || stackAtIndex.getItem() instanceof ItemFishingRod || stackAtIndex.getItem() instanceof ItemAxe || stackAtIndex.getItem() instanceof ItemPickaxe || Item.getIdFromItem(stackAtIndex.getItem()) == 346)) {
                                        if (Item.getIdFromItem(stackAtIndex.getItem()) == Item.getIdFromItem(stack.getItem())) {
                                            shouldContinue = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (shouldContinue)
                                continue;
                        }
                    }

                    if (prop_blacklistedProps.getValue().get("Worse Armor")) {
                        if (stack.getItem() instanceof ItemArmor) {
                            int equipmentType = ContainerUtil.getArmorItemsEquipSlot(stack, true);
                            if (equipmentType != -1) {
                                if (mc.thePlayer.getEquipmentInSlot(equipmentType) != null) {
                                    if (ItemUtil.compareProtection(stack, mc.thePlayer.getEquipmentInSlot(equipmentType)) == mc.thePlayer.getEquipmentInSlot(equipmentType)) {
                                        continue;
                                    }
                                } else {
                                    boolean shouldContinue = false;
                                    for (int i = 44; i > 0; i--) {
                                        if (i == index) continue;
                                        if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                                            ItemStack stackAtIndex = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                                            if (stackAtIndex.getItem() instanceof ItemArmor) {
                                                if (ContainerUtil.getArmorItemsEquipSlot(stackAtIndex, true) == equipmentType) {
                                                    if (ItemUtil.compareProtection(stack, stackAtIndex) == stackAtIndex) {
                                                        shouldContinue = true;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (shouldContinue)
                                        continue;
                                }
                            }
                        }
                    }

                    noMoreItems = false;

                    if (fuckingdelay <= prop_stealDelay.getValue().intValue()) {
                        fuckingdelay = prop_stealDelay.getValue().intValue();
                        decreasing = false;
                    }
                    if (takeStopwatch.hasPassed(instant.getValue() ? MathUtils.getRandomInRange(56, 65) : Math.abs(index - lastIndex) <= 3 ? fuckingdelay - Math.abs(index - lastIndex) : fuckingdelay + 4)) {
                        mc.playerController.windowClick(chest.inventorySlots.windowId, index, 0, 1, mc.thePlayer);
                        takeStopwatch.reset();
                        stealing = true;
                        fuckingdelay += decreasing ? Math.abs(fuckingdelay - prop_stealDelay.getValue().intValue()) < 10 ? -5 : -2 : 5;
                        lastIndex = index;
                        if (instant.getValue()) {
                        	if (exploitTime > 4) {
                                exploitTime = 0;
                                mc.playerController.windowClick(((ContainerChest) mc.thePlayer.openContainer).windowId, (int) MathUtils.getRandomInRange(11, 17), 0, 1, mc.thePlayer);
                            }
                            exploitTime++;
                        }
                        if (fuckingdelay >= (prop_maxstealDelay.getValue().intValue())) {
                            decreasing = true;
                        }
                        return;
                    }
                }

                if (noMoreItems) {
                    if (takeStopwatch.hasPassed(80)) {
                        mc.thePlayer.closeScreen();
                        takeStopwatch.reset();
                        stealing = false;
                    }
                }
            }
        }
    }

    public ArrayList<BlockPos> getLootedChestPositions() {
        return lootedChestPositions;
    }


    public static boolean isPotionNegative(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemPotion) {
            ItemPotion potion = (ItemPotion) itemStack.getItem();

            List<PotionEffect> potionEffectList = potion.getEffects(itemStack);

            return potionEffectList.stream()
                    .map(potionEffect -> Potion.potionTypes[potionEffect.getPotionID()])
                    .anyMatch(Potion::isBadEffect);
        }
        return false;
    }
}
