package wtf.diablo.client.module.impl.player;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import wtf.diablo.client.event.api.EventTypeEnum;
import wtf.diablo.client.event.impl.player.motion.MotionEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.NumberSetting;
import wtf.diablo.client.util.math.MathUtil;
import wtf.diablo.client.util.math.time.Stopwatch;
import wtf.diablo.client.util.mc.player.InventoryUtil;
import wtf.diablo.client.util.mc.player.movement.MovementUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@ModuleMetaData(
        name = "Inventory Manager",
        category = ModuleCategoryEnum.PLAYER
)
public final class InventoryManagerRecodeModule extends AbstractModule {
    private final NumberSetting<Integer> minDelay = new NumberSetting<>("Min Delay", 150, 1, 1000, 1);
    private final NumberSetting<Integer> maxDelay = new NumberSetting<>("Max Delay", 250, 1, 1000, 1);
    private final NumberSetting<Integer> swordSlotSetting = new NumberSetting<>("Sword Slot", 1, 1, 9, 1);
    private final NumberSetting<Integer> pickaxeSlotSetting = new NumberSetting<>("Pickaxe Slot", 2, 1, 9, 1);
    private final NumberSetting<Integer> axeSlotSetting = new NumberSetting<>("Axe Slot", 3, 1, 9, 1);
    private final NumberSetting<Integer> shovelSlotSetting = new NumberSetting<>("Shovel Slot", 4, 1, 9, 1);
    private final NumberSetting<Integer> blockSlotSetting = new NumberSetting<>("Block Slot", 5, 1, 9, 1);
    private final BooleanSetting throwUselessItems = new BooleanSetting("Throw Useless Items", true);

    private final BooleanSetting equipBestGear = new BooleanSetting("Equip Best Gear", true);
    private final BooleanSetting whileStill = new BooleanSetting("While Still", true);

    private final Stopwatch stopwatch = new Stopwatch();

    public InventoryManagerRecodeModule() {
        this.registerSettings(this.minDelay, this.maxDelay, this.swordSlotSetting, this.pickaxeSlotSetting, this.axeSlotSetting, this.shovelSlotSetting, this.blockSlotSetting, this.throwUselessItems, this.equipBestGear, this.whileStill);
    }

    private int delay;

    private final int INVENTORY_ROWS = 4;
    private final int INVENTORY_COLUMNS = 9;
    private final int ARMOR_SLOTS = 4;
    private final int INVENTORY_SLOTS = INVENTORY_ROWS * INVENTORY_COLUMNS + ARMOR_SLOTS;

    @EventHandler
    private final Listener<MotionEvent> motionEventListener = this::doInventoryManagement;

    @Override
    protected void onEnable() {
        super.onEnable();
        this.delay = this.generateDelay();
    }

    private void doInventoryManagement(final MotionEvent event) {
        if (!(this.mc.currentScreen instanceof GuiInventory)) {
            return;
        }

        if (event.getEventType() == EventTypeEnum.POST) {
            return;
        }

        if (MovementUtil.isMoving() && this.whileStill.getValue()) {
            return;
        }

        if (this.delay != this.generateDelay()) {
            this.delay = this.generateDelay();
        }

        if (this.stopwatch.hasReached(this.delay)) {
            this.stopwatch.reset();
            this.delay = this.generateDelay();

            this.equipBestArmor();

            this.doInventoryManagement(event);
        }
    }

    private void equipBestArmor() {
        int bestHelmet = -1;
        int bestChestPlate = -1;
        int bestLeggings = -1;
        int bestBoots = -1;

        this.throwUseless();

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
            if (this.equipBestGear.getValue()) {
                if (bestHelmet != -1) {
                    equipArmor(InventoryUtil.getSlotId(bestHelmet));
                }
                if (bestChestPlate != -1) {
                    equipArmor(InventoryUtil.getSlotId(bestChestPlate));
                }
                if (bestLeggings != -1) {
                    equipArmor(InventoryUtil.getSlotId(bestLeggings));
                }
                if (bestBoots != -1) {
                    equipArmor(InventoryUtil.getSlotId(bestBoots));
                }

            }
        }
    }

    private void throwUseless() {
        if (this.throwUselessItems.getValue()) {
            for (int i = 0; i < INVENTORY_SLOTS; ++i) {
                final ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);

                if (itemStack == null || itemStack.getItem() == null) continue;

                if (!itemWhitelisted(itemStack)) {
                    throwItem(InventoryUtil.getSlotId(i));
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
                final float damage = InventoryUtil.getSwordDamage(itemStack);
                if (bestSword == -1 || damage > InventoryUtil.getSwordDamage(mc.thePlayer.inventory.getStackInSlot(bestSword))) {
                    bestSword = i;
                }
            }

            if (item instanceof ItemPickaxe) {
                final float mineSpeed = InventoryUtil.getMineSpeed(itemStack);
                if (bestPickaxe == -1 || mineSpeed > InventoryUtil.getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestPickaxe))) {
                    bestPickaxe = i;
                }
            }

            if (item instanceof ItemAxe) {
                final float mineSpeed = InventoryUtil.getMineSpeed(itemStack);
                if (bestAxe == -1 || mineSpeed > InventoryUtil.getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestAxe))) {
                    bestAxe = i;
                }
            }

            if (item instanceof ItemSpade) {
                final float mineSpeed = InventoryUtil.getMineSpeed(itemStack);
                if (bestShovel == -1 || mineSpeed > InventoryUtil.getMineSpeed(mc.thePlayer.inventory.getStackInSlot(bestShovel))) {
                    bestShovel = i;
                }
            }

            if (item instanceof ItemBlock && ((ItemBlock) item).getBlock().isFullCube()) {
                final float amountOfBlocks = itemStack.getStackSize();
                if (bestBlock == -1 || amountOfBlocks > mc.thePlayer.inventory.getStackInSlot(bestBlock).getStackSize()) {
                    bestBlock = i;
                }
            }

            mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("random.click"), 1.0F));

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

            if (bestSword != -1) {
                final int swordSlot = swordSlotSetting.getValue();
                this.moveItem(InventoryUtil.getSlotId(bestSword), InventoryUtil.getSlotId(swordSlot - 37));
            }

            if (bestPickaxe != -1) {
                final int pickaxeSlot = pickaxeSlotSetting.getValue();
                this.moveItem(InventoryUtil.getSlotId(bestPickaxe), InventoryUtil.getSlotId(pickaxeSlot - 37));
            }

            if (bestAxe != -1) {
                final int axeSlot = axeSlotSetting.getValue();
                this.moveItem(InventoryUtil.getSlotId(bestAxe), InventoryUtil.getSlotId(axeSlot - 37));
            }

            if (bestShovel != -1) {
                final int shovelSlot = shovelSlotSetting.getValue();
                this.moveItem(InventoryUtil.getSlotId(bestShovel), InventoryUtil.getSlotId(shovelSlot - 37));
            }

            if (bestBlock != -1) {
                final int blockSlot = blockSlotSetting.getValue();
                this.moveItem(InventoryUtil.getSlotId(bestBlock), InventoryUtil.getSlotId(blockSlot - 37));
            }

            if (bestPotion != -1) {
                final int movePotionTo = 6;
                if (mc.thePlayer.inventory.getStackInSlot((movePotionTo - 1)) == null || !(mc.thePlayer.inventory.getStackInSlot((movePotionTo - 1)).getItem() instanceof ItemPotion)) {
                    this.moveItem(InventoryUtil.getSlotId(bestPotion), InventoryUtil.getSlotId(movePotionTo - 37));
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
                        this.throwItem(InventoryUtil.getSlotId(i));
                    }

                }

                if (item instanceof ItemSword) {
                    if (bestSword != -1 && i != bestSword) {
                        this.throwItem(InventoryUtil.getSlotId(i));
                    }
                }


                if (item instanceof ItemPickaxe) {
                    if (bestPickaxe != -1 && i != bestPickaxe) {
                        this.throwItem(InventoryUtil.getSlotId(i));
                    }
                }


                if (item instanceof ItemAxe) {
                    if (bestAxe != -1 && i != bestAxe) {
                        this.throwItem(InventoryUtil.getSlotId(i));
                    }
                }


                if (item instanceof ItemSpade) {
                    if (bestShovel != -1 && i != bestShovel) {
                        this.throwItem(InventoryUtil.getSlotId(i));
                    }
                }
            }
        }
    }

    private void throwItem(final int slot) {
        mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
    }

    private void moveItem(final int slot, final int newSlot) {
        if (slot != newSlot + 36) {
            this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, newSlot, 2, this.mc.thePlayer);
        }
    }

    private void equipArmor(final int slot) {
        if (slot > 8) {
            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 0, 1, mc.thePlayer);
        }
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

    private int getArmorDamageReduction(final ItemStack itemStack) {
        return ((ItemArmor) itemStack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
    }

    private int generateDelay() {
        return MathUtil.getRandomInt(this.minDelay.getValue(), this.maxDelay.getValue());
    }
}