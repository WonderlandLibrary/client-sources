package best.actinium.module.impl.player;

import best.actinium.Actinium;
import best.actinium.event.EventType;
import best.actinium.event.api.Callback;
import best.actinium.event.impl.input.ClickEvent;
import best.actinium.event.impl.move.MotionEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.module.impl.movement.scaffold.ScaffoldWalkModule;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.io.PacketUtil;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.player.PlayerUtil;
import net.minecraft.block.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Timer;

import java.util.Arrays;
import java.util.List;

@ModuleInfo(
        name = "Inv Manager",
        description = "fucks u up lil bro.",
        category = ModuleCategory.PLAYER
)
public class InvManagerModule extends Module {
    private NumberProperty minDelay = new NumberProperty("Min Delay",this,1,200,1000,1);
    private NumberProperty maxDelay = new NumberProperty("Max Delay",this,1,200,1000,1);
    private NumberProperty startDelay = new NumberProperty("Start Delay",this,1,200,1000,1);
    private BooleanProperty invOpen = new BooleanProperty("Open Inventory",this,true);
    private NumberProperty swordSlot = new NumberProperty("Sword Slot",this,1,1,10,1);
    private NumberProperty pickSlot = new NumberProperty("Pick Slot",this,1,2,10,1);
    private NumberProperty axeSlot = new NumberProperty("Axe Slot",this,1,3,10,1);
    private NumberProperty shovelSlot = new NumberProperty("Shovel Slot",this,1,4,10,1);
    private NumberProperty blockSlot = new NumberProperty("Block Slot",this,1,5,10,1);
    private NumberProperty potionSlot = new NumberProperty("Potion Slot",this,1,6,10,1);
    private NumberProperty foodSlot = new NumberProperty("Food Slot",this,1,7,10,1);
    private final int INVENTORY_ROWS = 4, INVENTORY_COLUMNS = 9, ARMOR_SLOTS = 4;
    private final int INVENTORY_SLOTS = (INVENTORY_ROWS * INVENTORY_COLUMNS) + ARMOR_SLOTS;
    private final TimerUtil stopwatch = new TimerUtil(),start = new TimerUtil();
    private int chestTicks, attackTicks, placeTicks;
    private boolean moved, open;
    private long nextClick;

    //motion
    @Callback
    public void onMotion(ClickEvent event) {
        if(!start.finished(startDelay.getValue().longValue())){
            return;
        } else {
            start.reset();
        }

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

        if (invOpen.isEnabled() && !(mc.currentScreen instanceof GuiInventory)) {
            this.stopwatch.reset();
            return;
        }

        if (!this.stopwatch.finished(this.nextClick) || this.chestTicks < 10 || this.attackTicks < 10 || this.placeTicks < 10) {
            this.closeInventory();
            return;
        }

        this.moved = false;
        int helmet = -1,chestplate = -1,leggings = -1,boots = -1,sword = -1,pickaxe = -1,axe = -1,shovel = -1,block = -1,potion = -1,food = -1;

        for (int i = 0; i < INVENTORY_SLOTS; i++) {
            final ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);

            if (stack == null) {
                continue;
            }

            final Item item = stack.getItem();

            if (!useful(stack)) {
                this.throwItem(i);
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

            if (item instanceof ItemSword) {
                if (sword == -1 || getDamage(stack) > getDamage(mc.thePlayer.inventory.getStackInSlot(sword))) {
                    sword = i;
                }

                if (i != sword) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemPickaxe) {
                if (pickaxe == -1 || mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(pickaxe))) {
                    pickaxe = i;
                }

                if (i != pickaxe) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemAxe) {
                if (axe == -1 || mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(axe))) {
                    axe = i;
                }

                if (i != axe) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemSpade) {
                if (shovel == -1 || mineSpeed(stack) > mineSpeed(mc.thePlayer.inventory.getStackInSlot(shovel))) {
                    shovel = i;
                }

                if (i != shovel) {
                    this.throwItem(i);
                }
            }

            if (item instanceof ItemBlock) {
                if (block == -1) {
                    block = i;
                } else {
                    final ItemStack currentStack = mc.thePlayer.inventory.getStackInSlot(block);

                    if (currentStack != null && stack.stackSize > currentStack.stackSize) {
                        block = i;
                    }
                }
            }

            if (item instanceof ItemPotion) {
                if (potion == -1) {
                    potion = i;
                } else {
                    final ItemStack currentStack = mc.thePlayer.inventory.getStackInSlot(potion);

                    if (currentStack == null) {
                        continue;
                    }

                    final ItemPotion currentItemPotion = (ItemPotion) currentStack.getItem();
                    final ItemPotion itemPotion = (ItemPotion) item;

                    boolean foundCurrent = false;

                    for (final PotionEffect e : mc.thePlayer.getActivePotionEffects()) {
                        if (e.getPotionID() == currentItemPotion.getEffects(currentStack).get(0).getPotionID() && e.getDuration() > 0) {
                            foundCurrent = true;
                            break;
                        }
                    }

                    boolean found = false;

                    for (final PotionEffect e : mc.thePlayer.getActivePotionEffects()) {
                        if (e.getPotionID() == itemPotion.getEffects(stack).get(0).getPotionID() && e.getDuration() > 0) {
                            found = true;
                            break;
                        }
                    }

                    if (itemPotion.getEffects(stack) != null && currentItemPotion.getEffects(currentStack) != null) {
                        if ((PlayerUtil.potionRank(itemPotion.getEffects(stack).get(0).getPotionID()) > PlayerUtil.potionRank(currentItemPotion.getEffects(currentStack).get(0).getPotionID()) || foundCurrent) && !found) {
                            potion = i;
                        }
                    }
                }
            }

            if (item instanceof ItemFood) {
                if (food == -1) {
                    food = i;
                } else {
                    final ItemStack currentStack = mc.thePlayer.inventory.getStackInSlot(food);

                    if (currentStack == null) {
                        continue;
                    }

                    final ItemFood currentItemFood = (ItemFood) currentStack.getItem();
                    final ItemFood itemFood = (ItemFood) item;

                    if (itemFood.getSaturationModifier(stack) > currentItemFood.getSaturationModifier(currentStack)) {
                        food = i;
                    }
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

        if (sword != -1 && sword != this.swordSlot.getValue().intValue() - 1) {
            this.moveItem(sword, this.swordSlot.getValue().intValue() - 37);
        }

        if (pickaxe != -1 && pickaxe != this.pickSlot.getValue().intValue() - 1) {
            this.moveItem(pickaxe, this.pickSlot.getValue().intValue() - 37);
        }

        if (axe != -1 && axe != this.axeSlot.getValue().intValue() - 1) {
            this.moveItem(axe, this.axeSlot.getValue().intValue() - 37);
        }

        if (shovel != -1 && shovel != this.shovelSlot.getValue().intValue() - 1) {
            this.moveItem(shovel, this.shovelSlot.getValue().intValue() - 37);
        }

        if (block != -1 && block != this.blockSlot.getValue().intValue() - 1 && !Actinium.INSTANCE.getModuleManager().get(ScaffoldWalkModule.class).isEnabled()) {
            this.moveItem(block, this.blockSlot.getValue().intValue() - 37);
        }

        if (potion != -1 && potion != this.potionSlot.getValue().intValue() - 1) {
            this.moveItem(potion, this.potionSlot.getValue().intValue() - 37);
        }

        if (food != -1 && food != this.foodSlot.getValue().intValue() - 1) {
            this.moveItem(food, this.foodSlot.getValue().intValue() - 37);
        }

        if (this.canOpenInventory() && !this.moved) {
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
        return !(mc.currentScreen instanceof GuiInventory);
    }

    private void throwItem(final int slot) {
        if ((!this.moved || this.nextClick <= 0)) {

            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 1, 4, mc.thePlayer);

            this.nextClick = Math.round(RandomUtil.getAdvancedRandom(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }

    private void moveItem(final int slot, final int destination) {
        if ((!this.moved || this.nextClick <= 0)) {

            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), this.slot(destination), 2, mc.thePlayer);

            this.nextClick = Math.round(RandomUtil.getAdvancedRandom(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }

    private void equipItem(final int slot) {
        if ((!this.moved || this.nextClick <= 0)) {

            if (this.canOpenInventory()) {
                this.openInventory();
            }

            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, this.slot(slot), 0, 1, mc.thePlayer);

            this.nextClick = Math.round(RandomUtil.getAdvancedRandom(this.minDelay.getValue().intValue(), this.maxDelay.getValue().intValue()));
            this.stopwatch.reset();
            this.moved = true;
        }
    }

    private float getDamage(final ItemStack stack) {
        final ItemSword sword = (ItemSword) stack.getItem();
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
        return (float) (sword.getDamageVsEntity() + level * 1.25);
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

    private final List<Item> WHITELISTED_ITEMS = Arrays.asList(Items.fishing_rod, Items.water_bucket, Items.bucket, Items.arrow, Items.bow, Items.snowball, Items.egg, Items.ender_pearl);

    private boolean useful(final ItemStack stack) {
        final Item item = stack.getItem();

        if (item instanceof ItemPotion) {
            final ItemPotion potion = (ItemPotion) item;
            return PlayerUtil.gud(potion.getEffects(stack).get(0).getPotionID());
        }

        if (item instanceof ItemBlock) {
            final Block block = ((ItemBlock) item).getBlock();
            if (block instanceof BlockGlass || block instanceof BlockStainedGlass || (block.isFullBlock() && !(block instanceof BlockTNT || block instanceof BlockSlime || block instanceof BlockFalling))) {
                return true;
            }
        }

        return item instanceof ItemSword ||
                item instanceof ItemTool ||
                item instanceof ItemArmor ||
                item instanceof ItemFood ||
                WHITELISTED_ITEMS.contains(item);
    }
}
