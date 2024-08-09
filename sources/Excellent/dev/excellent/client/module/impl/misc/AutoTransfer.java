package dev.excellent.client.module.impl.misc;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.notification.NotificationType;
import dev.excellent.client.script.manager.ScriptBlueprints;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.impl.StringValue;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.TextFormatting;

@ModuleInfo(name = "Auto Transfer", description = "Переносит предметы с одной анархии на другую. (FunTime)", category = Category.MISC)
public class AutoTransfer extends Module {
    public static Singleton<AutoTransfer> singleton = Singleton.create(() -> Module.link(AutoTransfer.class));
    private final StringValue anarchy = new StringValue("Анархия", this, "101");
    private final NumberValue itemCount = new NumberValue("Кол-во предметов", this, 3, 1, 9, 1);
    private final StringValue sellCommand = new StringValue("Команда продажи", this, "/ah dsell 1000");
    private final ScriptBlueprints scripts = new ScriptBlueprints() {{
        createScript("seller-task");
        createScript("buy-auc-task");
        createScript("window-click-task");
    }};
    private int serverAnarchy = -1;

    @Override
    protected void onEnable() {
        super.onEnable();
        serverAnarchy = PlayerUtil.getAnarchy();

        if (serverAnarchy == -1) {
            excellent.getNotificationManager().register("Нужно быть на сервере анархии", NotificationType.INFO, 3000);
            toggle();
            return;
        }

        if (mc.player.ticksExisted < (20 * 25)) {
            excellent.getNotificationManager().register("Подождите " + Mathf.round((20 * 25 - mc.player.ticksExisted) / 20f, 1) + " секунд", NotificationType.INFO, 1500);
            toggle();
            return;
        }

        scripts.getScript("seller-task").ifPresent(script -> {
            script.getScriptConstructor().cleanup();
            script.getScriptConstructor().addStep(0, () -> mc.player.inventory.currentItem = 0);
            for (int i = 0; i < itemCount.getValue().intValue(); i++) {
                if (!mc.player.inventory.getStackInSlot(0).isEmpty()) {
                    ChatUtil.sendText(sellCommand.getValue());
                    mc.playerController.windowClick(mc.player.container.windowId, mc.player.inventory.mainInventory.size(), 0, ClickType.QUICK_MOVE, mc.player);
                    continue;
                }
                if (mc.player.inventory.getStackInSlot(0).isEmpty()) {
                    script.getScriptConstructor()
                            .addStep(0, () -> {
                                for (int slotIndex = mc.player.inventory.mainInventory.size() - 9; slotIndex < mc.player.inventory.mainInventory.size(); slotIndex++) {
                                    Slot slot = mc.player.container.getSlot(slotIndex + 9);
                                    if (slot.getStack().isEmpty()) continue;
                                    if (mc.player.inventory.getStackInSlot(0).isEmpty() && slot.slotNumber != 0) {
                                        mc.playerController.windowClick(mc.player.container.windowId, slot.slotNumber, 0, ClickType.PICKUP, mc.player);
                                        mc.playerController.windowClick(mc.player.container.windowId, mc.player.inventory.mainInventory.size(), 0, ClickType.PICKUP, mc.player);
                                    }
                                }
                                for (int slotIndex = 0; slotIndex < mc.player.inventory.mainInventory.size() - 9; slotIndex++) {
                                    Slot slot = mc.player.container.getSlot(slotIndex + 9);
                                    if (slot.getStack().isEmpty()) continue;
                                    if (mc.player.inventory.getStackInSlot(0).isEmpty() && slot.slotNumber != 0) {
                                        mc.playerController.windowClick(mc.player.container.windowId, slot.slotNumber, 0, ClickType.PICKUP, mc.player);
                                        mc.playerController.windowClick(mc.player.container.windowId, mc.player.inventory.mainInventory.size(), 0, ClickType.PICKUP, mc.player);
                                    }
                                }
                            }, () -> mc.player.inventory.getStackInSlot(0).isEmpty());
                    script.getScriptConstructor()
                            .addStep(0,
                                    () -> ChatUtil.sendText(sellCommand.getValue()),
                                    () -> !mc.player.inventory.getStackInSlot(0).isEmpty());
                }
            }
            script.getScriptConstructor()
                    .addStep(50, () -> ChatUtil.sendText("/anarchy" + anarchy.getValue()))
                    .addStep(0, () -> scripts.getScript("buy-auc-task").ifPresent(buyTask ->
                            buyTask.getScriptConstructor().cleanup()
                                    .addStep(0, () -> ChatUtil.sendText("/ah " + mc.session.getUsername()))
                                    .addStep(0, () -> {
                                        if (mc.currentScreen instanceof ContainerScreen<?> container) {
                                            String title = TextFormatting.getTextWithoutFormattingCodes(container.getTitle().getString()).toLowerCase();
                                            if (title.contains("аукционы") && title.contains(mc.session.getUsername().toLowerCase())) {
                                                if (container.getContainer() instanceof ChestContainer chest) {
                                                    scripts.getScript("window-click-task")
                                                            .ifPresent(buytask -> {
                                                                buytask.getScriptConstructor()
                                                                        .cleanup();
                                                                for (int i = 0; i < itemCount.getValue().intValue(); i++) {
                                                                    int index = i;
                                                                    buytask.getScriptConstructor()
                                                                            .addStep(50, () -> mc.playerController.windowClick(chest.windowId + index, 0, 0, ClickType.QUICK_MOVE, mc.player));
                                                                }
                                                                buytask.getScriptConstructor()
                                                                        .addStep(0, () -> {
                                                                            mc.player.closeScreen();
                                                                            toggle();
                                                                        });

                                                            });
                                                }
                                            }
                                        }
                                    }, () -> {
                                        if (mc.currentScreen instanceof ContainerScreen<?> container) {
                                            String title = TextFormatting.getTextWithoutFormattingCodes(container.getTitle().getString()).toLowerCase();
                                            if (title.contains("аукционы") && title.contains(mc.session.getUsername().toLowerCase())) {
                                                return container.getContainer() instanceof ChestContainer;
                                            }
                                        }
                                        return false;
                                    })), () -> isInAnarchy() && serverAnarchy != PlayerUtil.getAnarchy())
            ;
        });
    }

    @Override
    protected void onDisable() {
        super.onDisable();
    }

    private final Listener<UpdateEvent> update = event -> {
        scripts.updateAll();
    };

    private boolean isInAnarchy() {
        return PlayerUtil.getAnarchy() != -1;
    }
}
