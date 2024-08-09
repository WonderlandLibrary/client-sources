package src.Wiksi.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeListSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.math.StopWatch;
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
@FunctionRegister(name = "ChestStealer", type = Category.Player)
public class ChestStealer extends Function {
    private final ModeSetting mode = new ModeSetting("Мод", "Обычный", "Обычный");
    private final BooleanSetting chestClose = new BooleanSetting("Закрывать при полном", true);
    private final SliderSetting stealDelay = new SliderSetting("Задержка", 100, 0, 1000, 1);
    private final BooleanSetting filterLootToggle = new BooleanSetting("Фильтр лута", false).setVisible(() -> mode.is("Обычный"));
    private final SliderSetting itemLimit = new SliderSetting("Лимит кол", 12, 1, 64, 1).setVisible(() -> mode.is("Обычный"));
    private final SliderSetting missPercent = new SliderSetting("Миссать", 50, 0, 100, 1).setVisible(() -> mode.is("Обычный"));
    private final StopWatch timerUtil = new StopWatch();

    public ChestStealer() {
        addSettings(mode, chestClose, stealDelay, filterLootToggle, itemLimit, missPercent);
    }

    private boolean filterItem(Item item) {
        if (!filterLootToggle.get()) {
            return true;
        }
        return false;
    }

    @Subscribe
    public void onEvent(final EventUpdate event) {
        if (mode.is("Обычный")) {
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