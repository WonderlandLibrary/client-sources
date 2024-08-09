package im.expensive.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.math.StopWatch;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE)
@FunctionRegister(name = "ChestStealer", type = Category.PVE)
public class ChestStealer extends Function {
    private final ModeSetting mode = new ModeSetting("Мод", "FunTime", "FunTime", "HolyWorld");
    private final BooleanSetting chestClose = new BooleanSetting("Закрывать при полном", true);
    private final SliderSetting stealDelay = new SliderSetting("Задержка", 100, 0, 200, 1);
    private final BooleanSetting filterLootToggle = new BooleanSetting("Фильтр лута", false).setVisible(() -> mode.is("HolyWorld"));
    private final ModeListSetting filterLoot = new ModeListSetting("Лут",
            new BooleanSetting("Руды", true),
            new BooleanSetting("Головы", false),
            new BooleanSetting("Незеритовый слиток", false),
            new BooleanSetting("Зачарованная книга", false),
            new BooleanSetting("Тотемы", false),
            new BooleanSetting("Зелья", false)
    ).setVisible(() -> mode.is("FunTime") && filterLootToggle.get());
    private final SliderSetting itemLimit = new SliderSetting("Лимит кол", 12, 1, 25, 1).setVisible(() -> mode.is("HolyWorld"));
    private final SliderSetting missPercent = new SliderSetting("Миссать", 50, 0, 100, 1).setVisible(() -> mode.is("FunTime"));
    private final StopWatch timerUtil = new StopWatch();

    public ChestStealer() {
        addSettings(mode, chestClose, stealDelay, filterLootToggle, filterLoot, itemLimit, missPercent);
    }

    private boolean filterItem(Item item) {
        if (!filterLootToggle.get()) {
            return true;
        }

        boolean filterOres = filterLoot.get(0).get();
        boolean filterHeads = filterLoot.get(1).get();
        boolean filterNetherite = filterLoot.get(2).get();
        boolean filterEnchantedBooks = filterLoot.get(3).get();
        boolean filterTotems = filterLoot.get(4).get();
        boolean filterPotions = filterLoot.get(5).get();

        if (filterOres && (
                item == Items.DIAMOND_ORE ||
                        item == Items.EMERALD_ORE ||
                        item == Items.IRON_ORE ||
                        item == Items.GOLD_ORE ||
                        item == Items.COAL_ORE
        )) {
            return true;
        }

        if (filterHeads && item == Items.PLAYER_HEAD) {
            return true;
        }

        if (filterNetherite && item == Items.NETHERITE_INGOT) {
            return true;
        }

        if (filterEnchantedBooks && item == Items.ENCHANTED_BOOK) {
            return true;
        }

        if (filterTotems && item == Items.TOTEM_OF_UNDYING) {
            return true;
        }

        if (filterPotions && (
                item == Items.POTION ||
                        item == Items.SPLASH_POTION
        )) {
            return true;
        }

        return false;
    }

    @Subscribe
    public void onEvent(final EventUpdate event) {
        if (mode.is("FunTime")) {
            if (mc.player.openContainer instanceof ChestContainer) {
                ChestContainer container = (ChestContainer) mc.player.openContainer;
                IInventory inventory = container.getLowerChestInventory();
                List<Integer> validSlots = new ArrayList<>();

                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    if (inventory.getStackInSlot(i).getItem() != Item.getItemById(0)
                            && inventory.getStackInSlot(i).getCount() <= itemLimit.get()
                            && filterItem(inventory.getStackInSlot(i).getItem())) {
                        validSlots.add(i);
                    }
                }

                if (!validSlots.isEmpty() && timerUtil.isReached(Math.round(stealDelay.get()))) {
                    int randomIndex = new Random().nextInt(validSlots.size());
                    int slotToSteal = validSlots.get(randomIndex);

                    if (new Random().nextInt(100) >= missPercent.get()) {
                        mc.playerController.windowClick(container.windowId, slotToSteal, 0, ClickType.QUICK_MOVE, mc.player);
                    }

                    timerUtil.reset();
                }

                if (inventory.isEmpty() && chestClose.get()) {
                    mc.player.closeScreen();
                }
            }
        }
    }
}