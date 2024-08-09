package wtf.resolute.moduled.impl.player;

import com.google.common.eventbus.Subscribe;
import wtf.resolute.evented.EventUpdate;
import wtf.resolute.evented.interfaces.Event;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import wtf.resolute.utiled.math.StopWatch;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import wtf.resolute.utiled.math.TimerUtil;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ModuleAnontion(name = "ChestStealer", type = Categories.Player,server = "")
public class ChestStealer extends Module {

    final SliderSetting delay = new SliderSetting("Задержка", 100.0f, 0.0f, 1000.0f, 1.0f);
    public static ModeSetting cheststealermode = new ModeSetting("Mode", "Все что есть", "Все что есть", "Норм Ресурсы");
    public ChestStealer() {
        addSettings(delay,cheststealermode);
    }

    final StopWatch stopWatch = new StopWatch();
    private final TimerUtil timerUtil = new TimerUtil();
    final List<Item> ingotItemList = List.of(Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.NETHERITE_INGOT,
            Items.DIAMOND,
            Items.NETHERITE_SCRAP);

    @Subscribe
    public void onUpdate(EventUpdate e) {
        switch (cheststealermode.get()) {
            case "Норм Ресурсы" -> {
                if (mc.player.openContainer instanceof ChestContainer container) {
                    IInventory lowerChestInventory = container.getLowerChestInventory();
                    for (int index = 0; index < lowerChestInventory.getSizeInventory(); ++index) {
                        ItemStack stack = lowerChestInventory.getStackInSlot(index);
                        if (!shouldMoveItem(container, index)) {
                            continue;
                        }
                        if (isContainerEmpty(stack)) {
                            continue;
                        }
                        if (delay.get() == 0.0f) {
                            moveItem(container, index, lowerChestInventory.getSizeInventory());
                        } else {
                            if (stopWatch.isReached(delay.get().longValue())) {
                                mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                                stopWatch.reset();
                            }
                        }
                    }
                }
            }
            case "Все что есть" -> {
                if (e instanceof EventUpdate) {
                    if (mc.player.openContainer instanceof ChestContainer) {
                        ChestContainer container = (ChestContainer) mc.player.openContainer;
                        for (int index = 0; index < container.inventorySlots.size(); ++index) {
                            if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0)
                                    && timerUtil.hasTimeElapsed(delay.get().longValue())) {
                                mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                                timerUtil.reset();
                                continue;
                            }

                            if (container.getLowerChestInventory().isEmpty()) {
                                mc.player.closeScreen();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean shouldMoveItem(ChestContainer container, int index) {
        ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(index);
        return itemStack.getItem() != Item.getItemById(0);
    }

    private void moveItem(ChestContainer container, int index, int multi) {
        for (int i = 0; i < multi; i++) {
            mc.playerController.windowClick(container.windowId, index + i, 0, ClickType.QUICK_MOVE, mc.player);
        }
    }

    public boolean isWhiteListItem(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return (itemStack.isFood()
                || itemStack.isEnchanted()
                || ingotItemList.contains(item)
                || item == Items.PLAYER_HEAD
                || item instanceof ArmorItem
                || item instanceof EnderPearlItem
                || item instanceof SwordItem
                || item instanceof ToolItem
                || item instanceof PotionItem
                || item instanceof ArrowItem
                || item instanceof SkullItem
                || item.getGroup() == ItemGroup.COMBAT
        );
    }

    private boolean isContainerEmpty(ItemStack stack) {
        return !isWhiteListItem(stack);
    }

}
