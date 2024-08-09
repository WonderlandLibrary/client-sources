package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.mixininterface.IAbstractFurnaceScreenHandler;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.ItemListSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.player.InvUtils;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.AbstractFurnaceScreenHandler;

import java.util.List;
import java.util.Map;

public class AutoSmelter extends Module {
    private Map<Item, Integer> fuelTimeMap;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<List<Item>> fuelItemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Fuel items")
            .description("Items to use as fuel")
            .defaultValue(
                    Items.COAL,
                    Items.CHARCOAL
            )
            .filter(this::fuelItemFilter)
            .bypassFilterWhenSavingAndLoading()
            .build()
    );

    private final Setting<List<Item>> smeltableItemsSetting = generalSettings.add(new ItemListSetting.Builder()
            .name("Smeltable items")
            .description("Items to smelt")
            .defaultValue(
                    Items.IRON_ORE,
                    Items.GOLD_ORE,
                    Items.COPPER_ORE,
                    Items.RAW_IRON,
                    Items.RAW_COPPER,
                    Items.RAW_GOLD
            )
            .filter(this::smeltableItemFilter)
            .bypassFilterWhenSavingAndLoading()
            .build()
    );

    private final Setting<Boolean> disableWhenOutOfItemsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Disable when out of items")
            .description("Disable the module when you run out of items")
            .defaultValue(true)
            .build()
    );

    public AutoSmelter(Category category) {
        super(category, "Auto Smelter", "Automatically smelts items from your inventory");
    }

    private boolean fuelItemFilter(Item item) {
        if (!Utils.canUpdate() && fuelTimeMap == null) {
            return false;
        }

        if (fuelTimeMap == null) {
            fuelTimeMap = AbstractFurnaceBlockEntity.createFuelTimeMap();
        }

        return fuelTimeMap.containsKey(item);
    }

    private boolean smeltableItemFilter(Item item) {
        return mc.world != null && mc.world.getRecipeManager().getFirstMatch(RecipeType.SMELTING, new SimpleInventory(item.getDefaultStack()), mc.world).isPresent();
    }

    public void tick(AbstractFurnaceScreenHandler handler) {
        if (mc.player.age % 10 == 0) {
            return;
        }

        checkFuel(handler);

        takeResults(handler);

        insertItems(handler);
    }

    private void insertItems(AbstractFurnaceScreenHandler handler) {
        ItemStack inputItemStack = handler.slots.get(0).getStack();
        if (!inputItemStack.isEmpty()) {
            return;
        }

        int slot = -1;
        for (int i = 3; i < handler.slots.size(); i++) {
            ItemStack item = handler.slots.get(i).getStack();
            if (!((IAbstractFurnaceScreenHandler) handler).isItemSmeltable(item)) {
                continue;
            }

            if (!smeltableItemsSetting.get().contains(item.getItem())) {
                continue;
            }

            if (!smeltableItemFilter(item.getItem())) {
                continue;
            }

            slot = i;
            break;
        }

        if (disableWhenOutOfItemsSetting.get() && slot == -1) {
            error("You do not have any items in your inventory that can be smelted, disabling...");
            forceToggle(false);
            return;
        }

        InvUtils.move().fromId(slot).toId(0);
    }

    private void checkFuel(AbstractFurnaceScreenHandler handler) {
        ItemStack fuelStack = handler.slots.get(1).getStack();
        if (handler.getFuelProgress() > 0) {
            return;
        }

        if (!fuelStack.isEmpty()) {
            return;
        }

        int slot = -1;
        for (int i = 3; i < handler.slots.size(); i++) {
            ItemStack item = handler.slots.get(i).getStack();
            if (!fuelItemsSetting.get().contains(item.getItem())) {
                continue;
            }

            if (!fuelItemFilter(item.getItem())) {
                continue;
            }

            slot = i;
            break;
        }

        if (disableWhenOutOfItemsSetting.get() && slot == -1) {
            error("You do not have any fuel in your inventory, disabling...");
            forceToggle(false);
            return;
        }

        InvUtils.move().fromId(slot).toId(1);
    }

    private void takeResults(AbstractFurnaceScreenHandler handler) {
        ItemStack resultStack = handler.slots.get(2).getStack();
        if (resultStack.isEmpty()) {
            return;
        }

        InvUtils.quickMove().slotId(2);

        if (!resultStack.isEmpty()) {
            error("Your inventory is full, disabling...");
            forceToggle(false);
        }
    }
}