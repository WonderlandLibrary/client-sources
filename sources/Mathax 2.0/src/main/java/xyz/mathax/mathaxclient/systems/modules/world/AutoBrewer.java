package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.settings.PotionSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.misc.MyPotion;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.screen.BrewingStandScreenHandler;

public class AutoBrewer extends Module {
    private boolean first;

    private int ingredientI;
    private int timer;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<MyPotion> potionSetting = generalSettings.add(new PotionSetting.Builder()
            .name("Potion")
            .description("The type of potion to brew.")
            .defaultValue(MyPotion.Strength)
            .build()
    );

    public AutoBrewer(Category category) {
        super(category, "Auto Brewer", "Automatically brews the specified potion.");
    }

    @Override
    public void onEnable() {
        first = false;
    }

    public void onBrewingStandClose() {
        first = false;
    }

    public void tick(BrewingStandScreenHandler handler) {
        timer++;

        if (!first) {
            first = true;

            ingredientI = -2;
            timer = 0;
        }

        if (handler.getBrewTime() != 0 || timer < 5) {
            return;
        }

        if (ingredientI == -2) {
            if (takePotions(handler)) {
                return;
            }

            ingredientI++;
            timer = 0;
        } else if (ingredientI == -1) {
            if (insertWaterBottles(handler)) {
                return;
            }

            ingredientI++;
            timer = 0;
        } else if (ingredientI < potionSetting.get().ingredients.length) {
            if (checkFuel(handler)) {
                return;
            }

            if (insertIngredient(handler, potionSetting.get().ingredients[ingredientI])) {
                return;
            }

            ingredientI++;
            timer = 0;
        } else {
            ingredientI = -2;
            timer = 0;
        }
    }

    private boolean insertIngredient(BrewingStandScreenHandler handler, Item ingredient) {
        int slot = -1;
        for (int slotI = 5; slotI < handler.slots.size(); slotI++) {
            if (handler.slots.get(slotI).getStack().getItem() == ingredient) {
                slot = slotI;
                break;
            }
        }

        if (slot == -1) {
            error("You do not have any %s left in your inventory, disabling...", ingredient.getName().getString());
            forceToggle(false);
            return true;
        }

        moveOneItem(handler, slot, 3);

        return false;
    }

    private boolean checkFuel(BrewingStandScreenHandler handler) {
        if (handler.getFuel() == 0) {
            int slot = -1;
            for (int slotI = 5; slotI < handler.slots.size(); slotI++) {
                if (handler.slots.get(slotI).getStack().getItem() == Items.BLAZE_POWDER) {
                    slot = slotI;
                    break;
                }
            }

            if (slot == -1) {
                error("You do not have a sufficient amount of blaze powder to use as fuel for the brew, disabling...");
                forceToggle(false);
                return true;
            }

            moveOneItem(handler, slot, 4);
        }

        return false;
    }

    private void moveOneItem(BrewingStandScreenHandler handler, int from, int to) {
        InvUtils.move().fromId(from).toId(to);
    }

    private boolean insertWaterBottles(BrewingStandScreenHandler handler) {
        for (int i = 0; i < 3; i++) {
            int slot = -1;
            for (int slotI = 5; slotI < handler.slots.size(); slotI++) {
                if (handler.slots.get(slotI).getStack().getItem() == Items.POTION) {
                    Potion potion = PotionUtil.getPotion(handler.slots.get(slotI).getStack());
                    if (potion == Potions.WATER) {
                        slot = slotI;
                        break;
                    }
                }
            }

            if (slot == -1) {
                error("You do not have a sufficient amount of water bottles to complete this brew, disabling...");
                forceToggle(false);
                return true;
            }

            InvUtils.move().fromId(slot).toId(i);
        }

        return false;
    }

    private boolean takePotions(BrewingStandScreenHandler handler) {
        for (int i = 0; i < 3; i++) {
            InvUtils.quickMove().slotId(i);

            if (!handler.slots.get(i).getStack().isEmpty()) {
                error("You do not have a sufficient amount of inventory space, disabling...");
                toggle();
                return true;
            }
        }

        return false;
    }
}