package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.api.IMode;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ModeSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.math.time.Stopwatch;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@ModuleMetaData(name = "Inventory Manager", description = "Manages items in your inventory", category = ModuleCategoryEnum.PLAYER)
public class InventoryManagerModule extends AbstractModule {
    private final Stopwatch timer = new Stopwatch();

    private final ModeSetting<InventoryModeEnum> mode = new ModeSetting<>("Mode", InventoryModeEnum.OPENINVENTORY);

    private final NumberSetting<Integer> minDelay = new NumberSetting<>("Min Delay", 100, 1, 500, 1);
    private final NumberSetting<Integer> maxDelay = new NumberSetting<>("Max Delay", 150, 1, 500, 1);
    private final BooleanSetting throwUselessItems = new BooleanSetting("Throw Useless Items", true);

    private final BooleanSetting equipBestGear = new BooleanSetting("Equip Best Gear", true);
    private final BooleanSetting whileStill = new BooleanSetting("While Still",true);

    private int ticksSinceChest;

    private boolean movedItem, inventoryOpen;

    public InventoryManagerModule() {
        this.registerSettings(mode, minDelay, maxDelay, throwUselessItems, equipBestGear,whileStill);
    }

    @EventHandler
    private final Listener<MotionEvent> updateEventListener = event -> {
        this.setSuffix(this.minDelay.getValue() + this.maxDelay.getValue() / 2 + "ms");

        if (event.getEventType() != EventTypeEnum.PRE)
            return;

        if (MovementUtil.isMoving() && this.whileStill.getValue())
            return;



        if (this.minDelay.getValue() > this.maxDelay.getValue()) {
            this.minDelay.setValue(this.maxDelay.getValue());
        }



        if (this.mc.currentScreen instanceof GuiChest) {
                this.ticksSinceChest = 0;
            } else {
                this.ticksSinceChest++;
            }

            if (this.ticksSinceChest <= 10) {
                return;
            }
            if (this.mc.thePlayer.isSwingInProgress) {
                return;
            }


            this.movedItem = false;


            final int delay = MathUtil.getRandomInt(this.minDelay.getValue(), this.maxDelay.getValue());

            /* Allows the mode of the manager to function */
                if (this.timer.hasReached(delay)) {
                    if (this.mode.getValue() == InventoryModeEnum.OPENINVENTORY) {
                        if (!(this.mc.currentScreen instanceof GuiInventory)) return;
                    }

                    final int INVENTORY_ROWS = 4;
                    final int INVENTORY_COLUMNS = 9;
                    final int ARMOR_SLOTS = 4;
                    final int INVENTORY_SLOTS = INVENTORY_ROWS * INVENTORY_COLUMNS + ARMOR_SLOTS;
                    if (this.throwUselessItems.getValue()) {
                        for (int i = 0; i < INVENTORY_SLOTS; ++i) {
                            final ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);

                            if (itemStack == null || itemStack.getItem() == null) continue;

                            if (!itemWhitelisted(itemStack)) {
                                throwItem(getSlotId(i));
                            }
                        }
                    }

                    int bestHelmet = -1;
                    int bestChestPlate = -1;
                    int bestLeggings = -1;
                    int bestBoots = -1;
                    int bestSword = -1;
                    int bestPickaxe = -1;
                    int bestAxe = -1;
                    int bestShovel = -1;
                    int bestBlock = -1;
                    int bestPotion = -1;

                    for (int i = 0; i < INVENTORY_SLOTS; ++i) {
                        final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

                        if (itemStack == null || itemStack.getItem() == null) continue;

                        final Item item = itemStack.getItem();

                        if (item instanceof ItemArmor) {
                            final ItemArmor armor = (ItemArmor) item;
                            final int damageReductionItem = getArmorDamageReduction(itemStack);

                            /* Helmet */
                            if (armor.armorType == 0) {
                                if (bestHelmet == -1 || damageReductionItem > getArmorDamageReduction(mc.thePlayer.inventory.getStackInSlot(bestHelmet))) {
                                    bestHelmet = i;
                                }
                            }

                            /* Chestplate */
                            if (armor.armorType == 1) {
                                if (bestChestPlate == -1 || damageReductionItem > getArmorDamageReduction(mc.thePlayer.inventory.getStackInSlot(bestChestPlate))) {
                                    bestChestPlate = i;
                                }
                            }

                            /* Leggings */
                            if (armor.armorType == 2) {
                                if (bestLeggings == -1 || damageReductionItem > getArmorDamageReduction(mc.thePlayer.inventory.getStackInSlot(bestLeggings))) {
                                    bestLeggings = i;
                                }
                            }

                            if (armor.armorType == 3) {
                                if (bestBoots == -1 || damageReductionItem > getArmorDamageReduction(mc.thePlayer.inventory.getStackInSlot(bestBoots))) {
                                    bestBoots = i;
                                }
                            }

                        }

                        if (item instanceof ItemSword) {
                            final float damage = getSwordDamage(itemStack);
                            if (bestSword == -1 || damage > getSwordDamage(mc.thePlayer.inventory.getStackInSlot(bestSword))) {
                                bestSword = i;
                            }
                        }

                        if (item instanceof ItemPickaxe) {
                            final float mineSpeed = getMineSpeed(itemStack);
                            if (bestPickaxe == -1 || mineSpeed > getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestPickaxe))) {
                                bestPickaxe = i;
                            }
                        }

                        if (item instanceof ItemAxe) {
                            final float mineSpeed = getMineSpeed(itemStack);
                            if (bestAxe == -1 || mineSpeed > getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestAxe))) {
                                bestAxe = i;
                            }
                        }

                        if (item instanceof ItemSpade) {
                            final float mineSpeed = getMineSpeed(itemStack);
                            if (bestShovel == -1 || mineSpeed > getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestShovel))) {
                                bestShovel = i;
                            }
                        }

                        if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().isFullCube()) {
                            final float amountOfBlocks = itemStack.getStackSize();
                            if (bestBlock == -1 || amountOfBlocks > mc.thePlayer.inventory.getStackInSlot(bestBlock).getStackSize()) {
                                bestBlock = i;
                            }
                        }

                        if (item instanceof ItemPotion) {
                            final ItemPotion itemPotion = (ItemPotion) item;
                            if (bestPotion == -1 && ItemPotion.isSplash(itemStack.getMetadata()) && itemPotion.getEffects(itemStack.getMetadata()) != null) {
                                final int potionID = itemPotion.getEffects(itemStack.getMetadata()).get(0).getPotionID();
                                boolean isPotionActive = false;

                                for (final PotionEffect potion : mc.thePlayer.getActivePotionEffects()) {
                                    if (potion.getPotionID() == potionID && potion.getDuration() > 0) {
                                        isPotionActive = true;
                                        break;
                                    }
                                }

                                final Collection<Integer> whitelistedPotionsID = Arrays.asList(1, 5, 8, 14, 12, 16);

                                if (!isPotionActive && (whitelistedPotionsID.contains(potionID) || (potionID == 10 || potionID == 6)))
                                    bestPotion = i;
                            }
                        }
                    }

                    if (this.equipBestGear.getValue()) {
                        if (bestHelmet != -1) equipArmor(getSlotId(bestHelmet));
                        if (bestChestPlate != -1) equipArmor(getSlotId(bestChestPlate));
                        if (bestLeggings != -1) equipArmor(getSlotId(bestLeggings));
                        if (bestBoots != -1) equipArmor(getSlotId(bestBoots));

                        if (bestSword != -1) {
                            final int swordSlot = 1;
                            this.moveItem(getSlotId(bestSword), getSlotId(swordSlot - 37));
                        }

                        if (bestPickaxe != -1) {
                            final int pickaxeSlot = 2;
                            this.moveItem(getSlotId(bestPickaxe), getSlotId(pickaxeSlot - 37));
                        }

                        if (bestAxe != -1) {
                            final int axeSlot = 3;
                            this.moveItem(getSlotId(bestAxe), getSlotId(axeSlot - 37));
                        }

                        if (bestShovel != -1) {
                            final int shovelSlot = 4;
                            this.moveItem(getSlotId(bestShovel), getSlotId(shovelSlot - 37));
                        }

                        if (bestBlock != -1) {
                            final int blockSlot = 5;
                            this.moveItem(getSlotId(bestBlock), getSlotId(blockSlot - 37));
                        }
                        if (bestPotion != -1) {
                            final int movePotionTo = 6;
                            if (mc.thePlayer.inventory.getStackInSlot((movePotionTo - 1)) == null || !(mc.thePlayer.inventory.getStackInSlot((movePotionTo - 1)).getItem() instanceof ItemPotion)) {
                                this.moveItem(getSlotId(bestPotion), getSlotId(movePotionTo - 37));
                            }
                        }
                    }

                    if (this.throwUselessItems.getValue()) {
                        for (int i = 0; i < INVENTORY_SLOTS; ++i) {
                            final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(i);

                            if (itemStack == null || itemStack.getItem() == null) continue;

                            final Item item = itemStack.getItem();

                            if (item instanceof ItemArmor) {
                                final ItemArmor armor = (ItemArmor) item;

                                if ((armor.armorType == 0 && bestHelmet != -1 && i != bestHelmet) || (armor.armorType == 1 && bestChestPlate != -1 && i != bestChestPlate) || (armor.armorType == 2 && bestLeggings != -1 && i != bestLeggings) || (armor.armorType == 3 && bestBoots != -1 && i != bestBoots)) {
                                    this.throwItem(getSlotId(i));
                                }

                            }

                            if (item instanceof ItemSword) {
                                if (bestSword != -1 && i != bestSword) {
                                    this.throwItem(getSlotId(i));
                                }
                            }


                            if (item instanceof ItemPickaxe) {
                                if (bestPickaxe != -1 && i != bestPickaxe) {
                                    this.throwItem(getSlotId(i));
                                }
                            }


                            if (item instanceof ItemAxe) {
                                if (bestAxe != -1 && i != bestAxe) {
                                    this.throwItem(getSlotId(i));
                                }
                            }


                            if (item instanceof ItemSpade) {
                                if (bestShovel != -1 && i != bestShovel) {
                                    this.throwItem(getSlotId(i));
                                }
                            }
                        }
                    }

                    if (this.mode.getValue() == InventoryModeEnum.PACKET) {
                        if (!this.movedItem) {
                            closeInventoryIfNecessary();
                        }
                    }

                if (this.mode.getValue() != InventoryModeEnum.OPENINVENTORY) {
                    if (this.movedItem) {
                        event.setCancelled(true);
                    }
                }

                this.timer.reset();
            }
    };


    private float getSwordDamage(final ItemStack itemStack) {
        final ItemSword sword = (ItemSword) itemStack.getItem();
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
        return (float) (sword.getDamageVsEntity() + level * 1.25);
    }

    private int getArmorDamageReduction(final ItemStack itemStack) {
        return ((ItemArmor) itemStack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
    }

    private void closeInventoryIfNecessary() {
        if (this.inventoryOpen) {
            this.inventoryOpen = false;
            this.mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
        }
    }

    private void throwItem(final int slot) {
        if (!movedItem) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
            movedItem = true;
        }
    }

    private void moveItem(final int slot, final int newSlot) {
        if (slot != newSlot + 36 && !this.movedItem) {
            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, newSlot, 2, this.mc.thePlayer);
            this.movedItem = true;
        }
    }

    private void equipArmor(final int slot) {
        if (slot > 8 && !movedItem) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
            movedItem = true;
        }
    }

    public int getSlotId(final int slot) {
        if (slot >= 36) return 8 - (slot - 36);
        if (slot < 9) return slot + 36;
        return slot;
    }

    private boolean itemWhitelisted(final ItemStack itemStack) {
        final Collection<Item> whitelistedCollection = Arrays.asList(Items.ender_pearl, Items.iron_ingot, Items.gold_ingot, Items.redstone, Items.diamond, Items.emerald, Items.quartz, Items.bow, Items.arrow);

        final Item item = itemStack.getItem();
        final String itemName = itemStack.getDisplayName();

        if (itemName.contains("Right Click") || itemName.contains("Click to Use") || itemName.contains("Players Finder"))
            return true;

        final int[] whitelistedPotionsID = new int[]{
                6, 1, 5, 8, 14, 12, 10, 16
        };

        if (item instanceof ItemPotion) {
            final int potionID = getPotionId(itemStack);
            return Objects.equals(whitelistedPotionsID[0], potionID);
        }

        return (item instanceof ItemBlock
                && !(((ItemBlock) item).getBlock() instanceof BlockTNT)
                && !(((ItemBlock) item).getBlock() instanceof BlockFalling))
                || item instanceof ItemAnvilBlock
                || item instanceof ItemSword
                || item instanceof ItemArmor
                || item instanceof ItemTool
                || item instanceof ItemFood
                || whitelistedCollection.contains(item)
                && !item.equals(Items.spider_eye);
    }

    private int getPotionId(final ItemStack potion) {
        final Item item = potion.getItem();

        if (item instanceof ItemPotion) {
            final ItemPotion p = (ItemPotion) item;
            return p.getEffects(potion.getMetadata()).get(0).getPotionID();
        }
        return 0;
    }

    private float getMineSpeed(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);

        //Percentage of Efficiency per Level
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
                return ((ItemPickaxe) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
            } else if (item instanceof ItemSpade) {
                return ((ItemSpade) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
            } else if (item instanceof ItemAxe) {
                return ((ItemAxe) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
            }
        return 0;
    }

    private enum InventoryModeEnum implements IMode {
        OPENINVENTORY("Open Inventory"),
        PACKET("Packet"),
        PACKETSPAM("Packet Spam");

        private final String name;

        InventoryModeEnum(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
