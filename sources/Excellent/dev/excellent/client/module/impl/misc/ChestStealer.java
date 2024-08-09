package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.EntityRayTraceEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Chest Stealer", description = "Перемещает вещи с контейнеров в ваш инвентарь", category = Category.MISC)
public class ChestStealer extends Module {
    private final BooleanValue ignorePlayers = new BooleanValue("Игнорировать игроков", this, true);
    private final NumberValue stealDelay = new NumberValue("Задержка", this, 50, 0, 500, 1);
    private final BooleanValue random = new BooleanValue("Рандом", this, true);
    private final BooleanValue closeEmpty = new BooleanValue("Закрывать пустые", this, true);
    private final BooleanValue closeIsFull = new BooleanValue("Закр. если фулл инв", this, true);
    private final BooleanValue leaveAfterLoot = new BooleanValue("Ливать после лута", this, false);
    private final BooleanValue missSlots = new BooleanValue("Промахиватся", this, true);
    private final TimerUtil timerUtil = TimerUtil.create();

    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.openContainer instanceof ChestContainer container) {
            List<Integer> slotsForLoot = new ArrayList<>();
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (!container.getLowerChestInventory().getStackInSlot(index).isEmpty()) {
                    slotsForLoot.add(index);
                }
            }
            lootItems(slotsForLoot, container);
            if (closeEmpty.getValue() && container.getLowerChestInventory().isEmpty()) mc.player.closeScreen();
            if (leaveAfterLoot.getValue() && container.getLowerChestInventory().isEmpty() && !PlayerUtil.isPvp())
                mc.player.connection.getNetworkManager().closeChannel(ITextComponent.getTextComponentOrEmpty("Вы покинули сервер \n" + " причина: Сундук пуст!"));
            if (missSlots.getValue()) missSlots(container);
            if (closeIsFull.getValue() && mc.player.inventory.getFirstEmptyStack() == -1) mc.player.closeScreen();
        }
    };

    private final Listener<EntityRayTraceEvent> onRayTrace = event -> {
        if (ignorePlayers.getValue()) event.cancel();
    };

    private void missSlots(ChestContainer container) {
        int containerSize = container.getLowerChestInventory().getSizeInventory();

        for (int index = 0; index < containerSize; ++index) {
            if (container.getLowerChestInventory().getStackInSlot(index).isEmpty()) {
                if (ThreadLocalRandom.current().nextDouble() < 0.1 && mc.player.ticksExisted % 30 == 0) {
                    mc.playerController.windowClick(container.windowId, index, 0, ClickType.PICKUP, mc.player);
                    return;
                }
            }
        }

    }

    private void lootItems(List<Integer> slots, ChestContainer container) {
        if (random.getValue()) Collections.shuffle(slots, ThreadLocalRandom.current());
        for (int index : slots) {
            if (timerUtil.hasReached(stealDelay.getValue().longValue(), true)) {
                mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
            }
        }
    }
}
