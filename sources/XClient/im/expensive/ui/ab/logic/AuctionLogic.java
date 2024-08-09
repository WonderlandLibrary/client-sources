package im.expensive.ui.ab.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import im.expensive.ui.ab.model.IItem;
import im.expensive.utils.client.IMinecraft;
import im.expensive.utils.math.StopWatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.play.client.CClickWindowPacket;

import java.util.List;
import java.util.Map;

public class AuctionLogic implements IMinecraft {
    private final ActivationLogic parent;
    private final Minecraft mc;
    final StopWatch refreshStopWatch = new StopWatch();
    final StopWatch buyStopWatch = new StopWatch();
    final StopWatch leaveAuctionStopWatch = new StopWatch();
    final StopWatch returnAuctionStopWatch = new StopWatch();

    boolean leave, returnAuc;

    public AuctionLogic(ActivationLogic parent) {
        this.parent = parent;
        this.mc = Minecraft.getInstance();
    }

    public void processActive() {
        if (mc.currentScreen instanceof ChestScreen chestScreen) {
            processBuy(chestScreen);
        }
    }

    public void processBuy(ChestScreen chestScreen) {
        Container container = chestScreen.getContainer();

        if (chestScreen.getTitle().getString().toLowerCase().contains("подозрительная цена!")) {
            mc.playerController.windowClick(container.windowId, 10, 0, ClickType.PICKUP, mc.player);
        }
        if (chestScreen.getTitle().getString().contains("Аукцион") || chestScreen.getTitle().getString().contains("Поиск:")) {
            auctionBotLogic(container, chestScreen);
        }
    }

    public void auctionBotLogic(Container container, ChestScreen chestScreen) {
        for (Slot slot : container.inventorySlots) {
            processAuctionSlot(chestScreen, slot);
        }
    }

    public void processAuctionSlot(ChestScreen chestScreen, Slot slot) {
        Container container = chestScreen.getContainer();
        if (parent.itemStorage == null) return;
        long currentTime = System.currentTimeMillis();

        List<IItem> items = parent.itemStorage.getItems();
        for (IItem item : items) {
            int targetPrice = item.getPrice();
            int currentPrice = extractPriceFromStack(slot.getStack());

            boolean itemIsFound = currentPrice != -1 && currentPrice <= targetPrice && isItemWasFound(item, slot);

            if (parent.itemList.contains(slot.getStack()) || slot.slotNumber > 48) {
                continue;
            }

            if (itemIsFound) {
                refreshStopWatch.reset();
                if (!checkItem(item, slot.getStack())) {
                    continue;
                }

                String sellerName = extractPidorFromStack(slot.getStack());

                if (!sellerName.isEmpty()) {
                    buyItem(container, slot, currentTime);
                }
            }
        }
        if (refreshStopWatch.isReached(500)) {
            refreshAuction(container, currentTime);
            refreshStopWatch.reset();
        }
    }

    protected void refreshAuction(Container container, long currentTime) {
        if (refreshStopWatch.isReached(500)) {
            silentClick(container, 49, ClickType.QUICK_MOVE);
            lastClickTime = currentTime;
            refreshStopWatch.reset();
        }
    }

    private long lastClickTime = System.currentTimeMillis();

    protected void buyItem(Container container, Slot slot, long currentTime) {
        if (currentTime - lastClickTime > 1000) {
            mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, mc.player);
            parent.itemList.add(slot.getStack());
            lastClickTime = currentTime;
        }
    }

    protected void returnToAuction() {
        if (returnAuctionStopWatch.isReached(350)) {
            mc.player.closeScreen();
            mc.player.sendChatMessage("/ah");
            returnAuctionStopWatch.reset();
        }
    }

    protected boolean checkItem(IItem item, ItemStack stack) {
        boolean don = stack.getTag() != null && stack.getTag().contains("don-item");

        if (stack.getCount() < item.getQuantity()) {
            return false;
        }

        if (!item.getEnchantments().isEmpty()) {
            for (Map.Entry<Enchantment, Integer> enchantmentEntry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
                Enchantment enchantment = enchantmentEntry.getKey();
                Integer enchantmentValue = enchantmentEntry.getValue();
                if (enchantmentValue == null || item.getEnchantments().get(enchantment) == null) {
                    return false;
                }
                if (item.getEnchantments().get(enchantment) <= enchantmentValue) {
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

    private boolean isItemWasFound(IItem item, Slot slot) {
        return item.getItem() == slot.getStack().getItem();
    }

    protected int extractPriceFromStack(ItemStack stack) {
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

    protected String extractPidorFromStack(ItemStack stack) {
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
