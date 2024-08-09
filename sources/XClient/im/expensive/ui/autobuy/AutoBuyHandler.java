package im.expensive.ui.autobuy;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import im.expensive.Expensive;
import im.expensive.events.EventPacket;
import im.expensive.events.EventUpdate;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.ShulkerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.server.SChatPacket;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class    AutoBuyHandler implements IMinecraft {
    public static boolean process;
    public CopyOnWriteArrayList<AutoBuy> items = new CopyOnWriteArrayList<>();
    public List<ItemStack> buyedList = new LinkedList<>();

    StopWatch leaveUpdate = new StopWatch();
    StopWatch refreshStopWatch = new StopWatch();
    StopWatch buyStopWatch = new StopWatch();

    int lastBalance;

    public static AutoBuyHandler instance;

    public AutoBuyHandler() {
        Expensive.getInstance().getEventBus().register(this);
        instance = this;
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        if (AutoBuyHandler.process) {
            if (mc.player == null || mc.world == null) return;

            if (e.getPacket() instanceof SChatPacket p) {
                String raw = p.getChatComponent().getString().toLowerCase(Locale.ROOT);
                if (raw.contains("этот товар уже купили!")) {
                    returnToAuction();
                }
                if (raw.contains("ваш баланс:")) {
                    String[] parts = raw.split("ваш баланс:")[1].trim().split("\\s");

                    try {
                        lastBalance = Integer.parseInt(parts[0].replaceAll("[^\\d]", ""));
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                    }
                    e.cancel();
                }

            }
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        if (process && mc.currentScreen instanceof ChestScreen chestScreen) {
            processBuy(chestScreen);
        }

    }

    private void processBuy(ChestScreen chestScreen) {
        Container container = chestScreen.getContainer();

        if (chestScreen.getTitle().getString().toLowerCase().contains("подозрительная цена!")) {
            mc.playerController.windowClick(container.windowId, 10, 0, ClickType.PICKUP, mc.player);
        }
        if (chestScreen.getTitle().getString().contains("Аукцион") || chestScreen.getTitle().getString().contains("Поиск:")) {
            auctionBotLogic(container, chestScreen);
        }
    }

    private void auctionBotLogic(Container container, ChestScreen chestScreen) {
        refreshAuction(container);
        for (Slot slot : container.inventorySlots) {
            processAuctionSlot(chestScreen, slot);
        }
    }

    private void processAuctionSlot(ChestScreen chestScreen, Slot slot) {
        Container container = chestScreen.getContainer();

        for (AutoBuy item : items) {
            int targetPrice = item.getPrice();
            int currentPrice = extractPriceFromStack(slot.getStack());

            boolean itemIsFound = currentPrice != -1 && currentPrice <= targetPrice && isItemWasFound(item, slot);

            if (buyedList.contains(slot.getStack()) || slot.slotNumber > 48) {
                continue;
            }

            if (itemIsFound) {
                if (!checkItem(item, slot.getStack())) {
                    continue;
                }

                String sellerName = extractPidorFromStack(slot.getStack());

                if (!sellerName.isEmpty()) {
                    if (!chestScreen.getTitle().getString().contains(sellerName)) {
                        leaveAuction(sellerName);
                        return;
                    }
                    if (buyStopWatch.isReached(100)) {
                        buyItem(container, slot);
                    }
                }
            } else if (chestScreen.getTitle().getString().contains("[1/1]")) {
                returnToAuction();
            }
        }
    }

    private void refreshAuction(Container container) {
        if (refreshStopWatch.isReached(400)) {
            silentClick(container, 49, ClickType.QUICK_MOVE);
            refreshStopWatch.reset();
        }
    }

    private void buyItem(Container container, Slot slot) {
        mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
        buyedList.add(slot.getStack());
        refreshStopWatch.reset();
        buyStopWatch.reset();
    }

    boolean isReset = false;

    private void leaveAuction(String sellerName) {
        if (leaveUpdate.isReached(400)) {
            mc.player.closeScreen();
            mc.player.sendChatMessage("/ah " + sellerName);
            leaveUpdate.reset();
        }
    }

    private void returnToAuction() {
        if (leaveUpdate.isReached(350)) {
            mc.player.closeScreen();
            mc.player.sendChatMessage("/ah");
            leaveUpdate.reset();
        }
    }

    private boolean checkItem(AutoBuy autoBuy, ItemStack stack) {
        boolean don = stack.getTag() != null && stack.getTag().contains("don-item");

        if ((autoBuy.isItems() || autoBuy.isDon()) && (Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock) && ShulkerUtil.getItemInShulker(stack, autoBuy).isEmpty()) {
            return false;
        }

        if (stack.getCount() < autoBuy.getCount()) {
            return false;
        }

        if (autoBuy.isFake() && !don) {
            return false;
        }
        if (!autoBuy.getEnchanments().isEmpty()) {
            for (Map.Entry<Enchantment, Integer> enchantmentEntry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                Enchantment enchantment = enchantmentEntry.getKey();
                Integer enchantmentValue = enchantmentEntry.getValue();
                if (enchantmentValue == null || autoBuy.getEnchanments().get(enchantment) == null) {
                    return false;
                }
                if (autoBuy.getEnchanments().get(enchantment) <= enchantmentValue) {
                    print(enchantmentValue + " " + autoBuy.getEnchanments().get(enchantment));
                    return true;
                }
            }
            return false;
        }

        return true;
    }


    private void silentClick(Container container, int slot, ClickType clickType) {
        short short1 = container.getNextTransactionID(mc.player.inventory);
        ItemStack itemstack = mc.player.inventory.getStackInSlot(slot);

        mc.player.connection.sendPacket(new CClickWindowPacket(mc.player.openContainer.windowId, slot, 0, clickType, itemstack, short1));
    }

    private boolean isItemWasFound(AutoBuy autoBuy, Slot slot) {
        return autoBuy.getItem() == slot.getStack().getItem();
    }

    private int extractPriceFromStack(ItemStack stack) {
        CompoundNBT tag = stack.getTag();

        if (tag != null && tag.contains("display", 10)) {
            CompoundNBT display = tag.getCompound("display");

            if (display.contains("Lore", 9)) {
                ListNBT lore = display.getList("Lore", 8);

                for (int j = 0; j < lore.size(); ++j) {
                    JsonObject object = JsonParser.parseString(lore.getString(j)).getAsJsonObject();

                    if (object.has("extra")) {
                        JsonArray array = object.getAsJsonArray("extra");

                        if (array.size() > 2) {
                            JsonObject title = array.get(1).getAsJsonObject();

                            if (title.get("text").getAsString().trim().toLowerCase().contains("ценa")) {
                                String line = array.get(2).getAsJsonObject().get("text").getAsString().trim().substring(1).replaceAll(" ", "");

                                return Integer.parseInt(line);
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }

    private String extractPidorFromStack(ItemStack stack) {
        CompoundNBT tag = stack.getTag();

        if (tag != null && tag.contains("display", 10)) {
            CompoundNBT display = tag.getCompound("display");

            if (display.contains("Lore", 9)) {
                ListNBT lore = display.getList("Lore", 8);

                for (int j = 0; j < lore.size(); ++j) {
                    JsonObject object = JsonParser.parseString(lore.getString(j)).getAsJsonObject();

                    if (object.has("extra")) {
                        JsonArray array = object.getAsJsonArray("extra");

                        if (array.size() > 2) {
                            JsonObject title = array.get(1).getAsJsonObject();

                            if (title.get("text").getAsString().trim().toLowerCase().startsWith("прoдaвeц")) {
                                return array.get(2).getAsJsonObject().get("text").getAsString().trim().replaceAll(" ", "");
                            }
                        }
                    }
                }
            }
        }

        return "";
    }
}