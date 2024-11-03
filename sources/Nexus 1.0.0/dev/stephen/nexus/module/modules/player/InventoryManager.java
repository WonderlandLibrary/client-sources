package dev.stephen.nexus.module.modules.player;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.render.EventRender3D;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.NumberSetting;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.InventoryUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import dev.stephen.nexus.utils.timer.MillisTimer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager extends Module {
    public static final NumberSetting startDelayValue = new NumberSetting("Start Delay", 0, 1000, 100, 10);
    public static final RangeSetting takeDelayValue = new RangeSetting("Take Delay", 0, 1000, 150, 200, 10);
    public static final BooleanSetting autoArmor = new BooleanSetting("Auto Armor", true);
    public static final BooleanSetting throwSword = new BooleanSetting("Throw Sword", false);
    public static final NumberSetting swordSlot = new NumberSetting("Sword Slot", 1, 9, 1, 1);
    public static final BooleanSetting throwBlock = new BooleanSetting("Throw Blocks", false);
    public static final NumberSetting blockSlot = new NumberSetting("Block Slot", 1, 9, 4, 1);
    public static final BooleanSetting throwTools = new BooleanSetting("Throw Tools", false);
    public static final NumberSetting pickSlot = new NumberSetting("Pickaxe Slot", 1, 9, 2, 1);
    public static final NumberSetting axeSlot = new NumberSetting("Axe Slot", 1, 9, 3, 1);
    public static final NumberSetting shovelSlot = new NumberSetting("Shovel Slot", 1, 9, 9, 1);
    public static final NumberSetting hoeSlot = new NumberSetting("Hoe Slot", 1, 9, 8, 1);
    public static final BooleanSetting throwBow = new BooleanSetting("Throw Bow", false);
    public static final NumberSetting bowSlot = new NumberSetting("Bow Slot", 1, 9, 5, 1);
    public static final BooleanSetting throwFishingRod = new BooleanSetting("Throw Fishing Rod", false);
    public static final NumberSetting fishingRodSlot = new NumberSetting("Fishing Rod Slot", 1, 9, 7, 1);
    public static final BooleanSetting throwEnderPearl = new BooleanSetting("Throw Ender Pearl", false);
    public static final NumberSetting enderPearlSlot = new NumberSetting("Ender Pearl Slot", 1, 9, 8, 1);
    public static final BooleanSetting throwFood = new BooleanSetting("Throw Food", false);
    public static final NumberSetting foodSlot = new NumberSetting("Food Slot", 1, 9, 6, 1);

    public InventoryManager() {
        super("InventoryManager", "Sorts your inventory", 0, ModuleCategory.PLAYER);
        this.addSettings(startDelayValue, takeDelayValue, throwSword, swordSlot, throwBlock, blockSlot, throwTools, pickSlot, axeSlot, shovelSlot, hoeSlot, throwBow, bowSlot, throwFishingRod, fishingRodSlot, throwEnderPearl, enderPearlSlot, throwFood, foodSlot);

        fishingRodSlot.addDependency(throwFishingRod, false);
        enderPearlSlot.addDependency(throwEnderPearl, false);
        shovelSlot.addDependency(throwTools, false);
        swordSlot.addDependency(throwSword, false);
        blockSlot.addDependency(throwBlock, false);
        pickSlot.addDependency(throwTools, false);
        axeSlot.addDependency(throwTools, false);
        hoeSlot.addDependency(throwTools, false);
        bowSlot.addDependency(throwBow, false);
    }

    private final MillisTimer startDelay = new MillisTimer();
    private final MillisTimer takeDelay = new MillisTimer();

    @EventLink
    public final Listener<EventRender3D> eventRender3DListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }

        if (mc.currentScreen instanceof InventoryScreen) {
            if (!startDelay.hasElapsed((long) startDelayValue.getValue())) {
                takeDelay.reset();
                return;
            }

            List<Slot> itemSlots = mc.player.playerScreenHandler.slots.stream().filter(Slot::hasStack).filter(slot -> slot.id > 4).toList();
            List<Slot> unnecessarySlots = new ArrayList<>();

            Slot bestSwordSlot = null;
            Slot bestBlockSlot = null;
            Slot[] bestArmorSlots = new Slot[4];
            Slot[] bestToolSlots = new Slot[10];
            Slot bestBowSlot = null;
            Slot bestEnderPearlSlot = null;
            Slot bestFishingRodSlot = null;
            Slot bestFoodSlot = null;

            for (Slot slot : itemSlots) {
                Item item = slot.getStack().getItem();

                if (item instanceof SwordItem) {
                    if (bestSwordSlot == null || InventoryUtils.getSwordDamage(slot) > InventoryUtils.getSwordDamage(bestSwordSlot)) {
                        unnecessarySlots.add(bestSwordSlot);
                        bestSwordSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof ArmorItem) {
                    int index = ((ArmorItem) item).getSlotType().getEntitySlotId();
                    Slot bestArmorSlot = bestArmorSlots[index];

                    if (bestArmorSlot == null || InventoryUtils.getProtection(slot) > InventoryUtils.getProtection(bestArmorSlot)) {
                        unnecessarySlots.add(bestArmorSlot);
                        bestArmorSlots[index] = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof ToolItem) {
                    int index = -1;
                    if (item instanceof ShovelItem) index = shovelSlot.getValueInt() - 1;
                    if (item instanceof PickaxeItem) index = pickSlot.getValueInt() - 1;
                    if (item instanceof AxeItem) index = axeSlot.getValueInt() - 1;
                    if (item instanceof HoeItem) index = hoeSlot.getValueInt() - 1;

                    if (index != -1) {
                        Slot bestToolSlot = bestToolSlots[index];

                        if (bestToolSlot == null || InventoryUtils.getDestroySpeed(slot) > InventoryUtils.getDestroySpeed(bestToolSlot)) {
                            unnecessarySlots.add(bestToolSlot);
                            bestToolSlots[index] = slot;
                        } else {
                            unnecessarySlots.add(slot);
                        }
                    }
                } else if (item instanceof BlockItem && InventoryUtils.isBlockPlaceable(item.getDefaultStack())) {
                    if (bestBlockSlot == null) {
                        bestBlockSlot = slot;
                    } else {
                        if (bestBlockSlot.getStack().getCount() < slot.getStack().getCount()) {
                            unnecessarySlots.add(bestBlockSlot);
                            bestBlockSlot = slot;
                        } else if (bestBlockSlot.getStack().getCount() == slot.getStack().getCount()) {
                            unnecessarySlots.add(slot);
                        }
                    }
                } else if (item instanceof BowItem) {
                    if (bestBowSlot == null || InventoryUtils.getBowDamage(slot) > InventoryUtils.getBowDamage(bestBowSlot)) {
                        unnecessarySlots.add(bestBowSlot);
                        bestBowSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof EnderPearlItem) {
                    if (bestEnderPearlSlot == null || slot.getStack().getCount() > bestEnderPearlSlot.getStack().getCount()) {
                        unnecessarySlots.add(bestEnderPearlSlot);
                        bestEnderPearlSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item instanceof FishingRodItem) {
                    if (bestFishingRodSlot == null) {
                        bestFishingRodSlot = slot;
                    } else {
                        unnecessarySlots.add(slot);
                    }
                } else if (item.getComponents().contains(DataComponentTypes.FOOD)) {
                    FoodComponent foodComponent = item.getComponents().get(DataComponentTypes.FOOD);

                    if (foodComponent != null) {
                        int foodPoints = foodComponent.nutrition();
                        int stackSize = slot.getStack().getCount();

                        double foodValue = foodPoints * stackSize;

                        if (bestFoodSlot == null) {
                            bestFoodSlot = slot;
                        } else {
                            FoodComponent bestFoodComponent = bestFoodSlot.getStack().getItem().getComponents().get(DataComponentTypes.FOOD);
                            if (bestFoodComponent != null) {
                                int bestFoodPoints = bestFoodComponent.nutrition();
                                int bestStackSize = bestFoodSlot.getStack().getCount();
                                double bestFoodValue = bestFoodPoints * bestStackSize;

                                if (foodValue > bestFoodValue) {
                                    unnecessarySlots.add(bestFoodSlot);
                                    bestFoodSlot = slot;
                                } else {
                                    unnecessarySlots.add(slot);
                                }
                            }
                        }
                    }
                } else if (!(item instanceof ArrowItem && !throwBow.getValue())) {
                    unnecessarySlots.add(slot);
                }
            }

            for (Slot slot : unnecessarySlots) {
                if (slot != null) {
                    click(slot, 1, SlotActionType.THROW);
                }
            }

            if (autoArmor.getValue()) {
                for (int i = 0; i < bestArmorSlots.length; i++) {
                    Slot slot = bestArmorSlots[i];
                    if (slot != null) {
                        if (!isArmorEquipped(i, slot)) {
                            click(slot, 0, SlotActionType.QUICK_MOVE);
                        }
                    }
                }
            }

            if (!throwSword.getValue()) {
                if (bestSwordSlot != null && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
                    if (bestSwordSlot.id != 36) {
                        click(bestSwordSlot, swordSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestSwordSlot != null && bestSwordSlot.getStack().getItem() instanceof SwordItem) {
                    click(bestSwordSlot, 1, SlotActionType.THROW);
                }
            }

            if (!throwBlock.getValue()) {
                if (bestBlockSlot != null && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
                    if (bestBlockSlot.id != 36) {
                        click(bestBlockSlot, blockSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestBlockSlot != null && bestBlockSlot.getStack().getItem() instanceof BlockItem) {
                    click(bestBlockSlot, 1, SlotActionType.THROW);
                }
            }

            if (!throwTools.getValue()) {
                for (int i = 0; i < bestToolSlots.length; i++) {
                    Slot slot = bestToolSlots[i];
                    if (slot != null && slot.id != 37 + i) {
                        click(slot, i, SlotActionType.SWAP);
                    }
                }
            } else {
                for (int i = 0; i < bestToolSlots.length; i++) {
                    Slot slot = bestToolSlots[i];
                    if (slot != null && slot.id != 37 + i) {
                        click(slot, 1, SlotActionType.THROW);
                    }
                }
            }

            if (!throwBow.getValue()) {
                if (bestBowSlot != null && bestBowSlot.getStack().getItem() instanceof BowItem) {
                    if (bestBowSlot.id != 36) {
                        click(bestBowSlot, bowSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestBowSlot != null && bestBowSlot.getStack().getItem() instanceof BowItem) {
                    click(bestBowSlot, 1, SlotActionType.THROW);
                }
            }

            if (!throwEnderPearl.getValue()) {
                if (bestEnderPearlSlot != null && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
                    if (bestEnderPearlSlot.id != 36) {
                        click(bestEnderPearlSlot, enderPearlSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestEnderPearlSlot != null && bestEnderPearlSlot.getStack().getItem() instanceof EnderPearlItem) {
                    click(bestEnderPearlSlot, 1, SlotActionType.THROW);
                }
            }

            if (!throwFishingRod.getValue()) {
                if (bestFishingRodSlot != null && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
                    if (bestFishingRodSlot.id != 36) {
                        click(bestFishingRodSlot, fishingRodSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestFishingRodSlot != null && bestFishingRodSlot.getStack().getItem() instanceof FishingRodItem) {
                    click(bestFishingRodSlot, 1, SlotActionType.THROW);
                }
            }

            if (!throwFood.getValue()) {
                if (bestFoodSlot != null && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
                    if (bestFoodSlot.id != 36) {
                        click(bestFoodSlot, foodSlot.getValueInt() - 1, SlotActionType.SWAP);
                    }
                }
            } else {
                if (bestFoodSlot != null && bestFoodSlot.getStack().getItem().getComponents().contains(DataComponentTypes.FOOD)) {
                    click(bestFoodSlot, 1, SlotActionType.THROW);
                }
            }
        } else {
            takeDelay.reset();
            startDelay.reset();
        }
    };

    private boolean isArmorEquipped(int index, Slot slot) {
        List<ItemStack> armorItems = new ArrayList<>();
        for (ItemStack armor : mc.player.getArmorItems()) {
            armorItems.add(armor);
        }

        if (index >= 0 && index < armorItems.size()) {
            return armorItems.get(index).getItem() == slot.getStack().getItem();
        }
        return false;
    }

    private void click(Slot slot, int button, SlotActionType type) {
        if (mc.currentScreen instanceof InventoryScreen) {
            if (takeDelay.hasElapsed((long) RandomUtil.randomBetween(takeDelayValue.getValueMin(), takeDelayValue.getValueMax()))) {
                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot.id, button, type, mc.player);
                takeDelay.reset();
            }
        }
    }
}
