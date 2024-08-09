package fun.ellant.functions.impl.player;


import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.StringSetting;
import fun.ellant.utils.client.ClientUtil;
import fun.ellant.utils.math.StopWatch;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

// Доделать
@FunctionRegister(name = "AutoTransfer", type = Category.PLAYER, desc = "Переносит предметы на другую анку")
public class AutoTransfer extends Function {

    private final StringSetting anarchyNumberSetting = new StringSetting("Анархия", "", "Введите номер анархии \nдля переноса вещей", true);
    private final StringSetting itemsCountSetting = new StringSetting("Количество предметов", "", "Укажите количество предметов для продажи", true);
    private final StringSetting sellPriceSetting = new StringSetting("Цена", "", "Укажите цену (от 10$)", true);

    private final StopWatch stopWatch = new StopWatch(), changeServerStopWatch = new StopWatch();
    private boolean allItemsToSell = false;
    private boolean connectedToServer = false;
    private final List<Item> playerItems = new ArrayList<>();
    private int sellCount = 0;
    private boolean isReadyToSell;

    public AutoTransfer() {
        addSettings(anarchyNumberSetting, itemsCountSetting, sellPriceSetting);
    }

    @Subscribe
    private void onPacket(EventPacket packetEvent) {
        if (!ClientUtil.isConnectedToServer("funtime")) return;

        if (packetEvent.getPacket() instanceof SChatPacket chatPacket) {
            String chatMessage = chatPacket.getChatComponent().getString().toLowerCase(Locale.ROOT);
            if (chatMessage.contains("освободите хранилище") && !playerItems.isEmpty()) allItemsToSell = true;
            if (chatMessage.contains("вы уже подключены")) connectedToServer = true;
            if (chatMessage.contains("выставлен на продажу")) {
                sellCount++;
            }
        }
    }

    List<ItemStack> stacks = new LinkedList<>();

    @Subscribe
    private void onUpdate(EventUpdate updateEvent) {
        if (mc.player.ticksExisted < 500 && !isReadyToSell) {
            int ticksRemaining = 500 - mc.player.ticksExisted;
            int secondsRemaining = ticksRemaining / 20;
            print("Подождите ещё " + TextFormatting.RED + secondsRemaining + TextFormatting.RESET + " секунд(ы), прежде чем использовать");
            toggle();
            return;
        }

        if (mc.ingameGUI.getTabList().header != null) {
            String serverHeader = TextFormatting.getTextWithoutFormattingCodes(mc.ingameGUI.getTabList().header.getString());
            if (serverHeader != null && serverHeader.contains(anarchyNumberSetting.get())) connectedToServer = true;
        }

        int itemCountToSell = Integer.parseInt(itemsCountSetting.get());

        if (itemCountToSell > 9) {
            print("Кол-во предметов не должно превышать 9!");
            toggle();
            return;
        }

        int sellPrice = Integer.parseInt(sellPriceSetting.get());

        if (!isReadyToSell) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.AIR) {
                    continue;
                }
                if (stopWatch.isReached(100)) {
                    mc.player.inventory.currentItem = i;
                    mc.player.sendChatMessage("/ah dsell " + sellPrice);
                    playerItems.add(mc.player.inventory.getStackInSlot(i).getItem());
                    stopWatch.reset();
                }
            }
        }

        if (sellCount >= itemCountToSell || allItemsToSell) {
            isReadyToSell = true;
            int anarchyNumber = Integer.parseInt(anarchyNumberSetting.get());

            if (!connectedToServer) {
                if (changeServerStopWatch.isReached(100)) {
                    mc.player.sendChatMessage("/an" + anarchyNumber);
                    changeServerStopWatch.reset();
                }
                return;
            }

            if (mc.player.openContainer instanceof ChestContainer container) {
                IInventory lowerChestInventory = container.getLowerChestInventory();

                for (int index = 0; index < lowerChestInventory.getSizeInventory(); ++index) {
                    if (stopWatch.isReached(200) && lowerChestInventory.getStackInSlot(index).getItem() != Items.AIR) {
                        if (playerItems.contains(lowerChestInventory.getStackInSlot(index).getItem())) {
                            mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                            stopWatch.reset();
                        } else {
                            resetAndToggle();
                            toggle();
                        }
                    }
                }
            } else {
                if (stopWatch.isReached(500)) {
                    mc.player.sendChatMessage("/ah " + mc.player.getNameClear());
                    stopWatch.reset();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        resetAndToggle();
        super.onDisable();
    }

    private void resetAndToggle() {
        allItemsToSell = false;
        connectedToServer = false;
        playerItems.clear();
        isReadyToSell = false;
        sellCount = 0;
    }
}