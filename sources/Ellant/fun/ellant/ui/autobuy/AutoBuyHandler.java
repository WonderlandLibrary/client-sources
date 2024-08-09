/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.autobuy;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fun.ellant.Ellant;
import fun.ellant.events.EventPacket;
import fun.ellant.events.EventUpdate;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.ShulkerUtil;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CClickWindowPacket;
import net.minecraft.network.play.server.SChatPacket;

public class AutoBuyHandler
implements IMinecraft {
    public static boolean process;
    public CopyOnWriteArrayList<AutoBuy> items = new CopyOnWriteArrayList();
    public List<ItemStack> buyedList = new LinkedList<ItemStack>();
    StopWatch leaveUpdate = new StopWatch();
    StopWatch refreshStopWatch = new StopWatch();
    StopWatch buyStopWatch = new StopWatch();
    int lastBalance;
    public static AutoBuyHandler instance;
    boolean isReset = false;

    public AutoBuyHandler() {
        Ellant.getInstance().getEventBus().register(this);
        instance = this;
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        if (process) {
            if (Minecraft.player == null || AutoBuyHandler.mc.world == null) {
                return;
            }
            IPacket<?> var3 = e.getPacket();
            if (var3 instanceof SChatPacket) {
                SChatPacket p = (SChatPacket)var3;
                String raw = p.getChatComponent().getString().toLowerCase(Locale.ROOT);
                if (raw.contains("\u044d\u0442\u043e\u0442 \u0442\u043e\u0432\u0430\u0440 \u0443\u0436\u0435 \u043a\u0443\u043f\u0438\u043b\u0438!")) {
                    this.returnToAuction();
                }
                if (raw.contains("\u0432\u0430\u0448 \u0431\u0430\u043b\u0430\u043d\u0441:")) {
                    String[] parts = raw.split("\u0432\u0430\u0448 \u0431\u0430\u043b\u0430\u043d\u0441:")[1].trim().split("\\s");
                    try {
                        this.lastBalance = Integer.parseInt(parts[0].replaceAll("[^\\d]", ""));
                    } catch (NumberFormatException var6) {
                        NumberFormatException ex = var6;
                        ex.printStackTrace();
                    }
                    e.cancel();
                }
            }
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate e) {
        Screen var3;
        if (process && (var3 = AutoBuyHandler.mc.currentScreen) instanceof ChestScreen) {
            ChestScreen chestScreen = (ChestScreen)var3;
            this.processBuy(chestScreen);
        }
    }

    private void processBuy(ChestScreen chestScreen) {
        Object container = chestScreen.getContainer();
        if (chestScreen.getTitle().getString().toLowerCase().contains("\u043f\u043e\u0434\u043e\u0437\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u0430\u044f \u0446\u0435\u043d\u0430!")) {
            AutoBuyHandler.mc.playerController.windowClick(((Container)container).windowId, 10, 0, ClickType.PICKUP, Minecraft.player);
        }
        if (chestScreen.getTitle().getString().contains("\u0410\u0443\u043a\u0446\u0438\u043e\u043d") || chestScreen.getTitle().getString().contains("\u041f\u043e\u0438\u0441\u043a:")) {
            this.auctionBotLogic((Container)container, chestScreen);
        }
    }

    private void auctionBotLogic(Container container, ChestScreen chestScreen) {
        this.refreshAuction(container);
        for (Slot slot : container.inventorySlots) {
            this.processAuctionSlot(chestScreen, slot);
        }
    }

    private void processAuctionSlot(ChestScreen chestScreen, Slot slot) {
        Object container = chestScreen.getContainer();
        for (AutoBuy item : this.items) {
            boolean itemIsFound;
            int targetPrice = item.getPrice();
            int currentPrice = this.extractPriceFromStack(slot.getStack());
            boolean bl = itemIsFound = currentPrice != -1 && currentPrice <= targetPrice && this.isItemWasFound(item, slot);
            if (this.buyedList.contains(slot.getStack()) || slot.slotNumber > 48) continue;
            if (itemIsFound) {
                String sellerName;
                if (!this.checkItem(item, slot.getStack()) || (sellerName = this.extractPidorFromStack(slot.getStack())).isEmpty()) continue;
                if (!chestScreen.getTitle().getString().contains(sellerName)) {
                    this.leaveAuction(sellerName);
                    return;
                }
                if (!this.buyStopWatch.isReached(1L)) continue;
                this.buyItem((Container)container, slot);
                continue;
            }
            if (!chestScreen.getTitle().getString().contains("[1/1]")) continue;
            this.returnToAuction();
        }
    }

    private void refreshAuction(Container container) {
        if (this.refreshStopWatch.isReached(400L)) {
            this.silentClick(container, 49, ClickType.QUICK_MOVE);
            this.refreshStopWatch.reset();
        }
    }

    private void buyItem(Container container, Slot slot) {
        AutoBuyHandler.mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, Minecraft.player);
        this.buyedList.add(slot.getStack());
        this.refreshStopWatch.reset();
        this.buyStopWatch.reset();
    }

    private void leaveAuction(String sellerName) {
        if (this.leaveUpdate.isReached(400L)) {
            Minecraft.player.closeScreen();
            Minecraft.player.sendChatMessage("/ah " + sellerName);
            this.leaveUpdate.reset();
        }
    }

    private void returnToAuction() {
        if (this.leaveUpdate.isReached(350L)) {
            Minecraft.player.closeScreen();
            Minecraft.player.sendChatMessage("/ah");
            this.leaveUpdate.reset();
        }
    }

    private boolean checkItem(AutoBuy autoBuy, ItemStack stack) {
        Integer enchantmentValue;
        Enchantment enchantment;
        boolean don;
        boolean bl = don = stack.getTag() != null && stack.getTag().contains("don-item");
        if (autoBuy.isDon() && Block.getBlockFromItem(stack.getItem()) instanceof ShulkerBoxBlock && ShulkerUtil.getItemInShulker(stack, autoBuy).isEmpty()) {
            return false;
        }
        if (stack.getCount() < autoBuy.getCount()) {
            return false;
        }
        if (autoBuy.isFake() && !don) {
            return false;
        }
        if (autoBuy.getEnchanments().isEmpty()) {
            return true;
        }
        Iterator<Map.Entry<Enchantment, Integer>> var4 = EnchantmentHelper.getEnchantments(stack).entrySet().iterator();
        do {
            if (!var4.hasNext()) {
                return false;
            }
            Map.Entry<Enchantment, Integer> enchantmentEntry = var4.next();
            enchantment = enchantmentEntry.getKey();
            enchantmentValue = enchantmentEntry.getValue();
            if (enchantmentValue != null && autoBuy.getEnchanments().get(enchantment) != null) continue;
            return false;
        } while (autoBuy.getEnchanments().get(enchantment) > enchantmentValue);
        this.print(enchantmentValue + " " + autoBuy.getEnchanments().get(enchantment));
        return true;
    }

    private void silentClick(Container container, int slot, ClickType clickType) {
        short short1 = container.getNextTransactionID(Minecraft.player.inventory);
        ItemStack itemstack = Minecraft.player.inventory.getStackInSlot(slot);
        Minecraft.player.connection.sendPacket(new CClickWindowPacket(Minecraft.player.openContainer.windowId, slot, 0, clickType, itemstack, short1));
    }

    private boolean isItemWasFound(AutoBuy autoBuy, Slot slot) {
        return autoBuy.getItem() == slot.getStack().getItem();
    }

    private int extractPriceFromStack(ItemStack stack) {
        CompoundNBT display;
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("display", 10) && (display = tag.getCompound("display")).contains("Lore", 9)) {
            ListNBT lore = display.getList("Lore", 8);
            for (int j = 0; j < lore.size(); ++j) {
                JsonObject title;
                JsonArray array;
                JsonObject object = JsonParser.parseString(lore.getString(j)).getAsJsonObject();
                if (!object.has("extra") || (array = object.getAsJsonArray("extra")).size() <= 2 || !(title = array.get(1).getAsJsonObject()).get("text").getAsString().trim().toLowerCase().contains("\u0446\u0435\u043da")) continue;
                String line = array.get(2).getAsJsonObject().get("text").getAsString().trim().substring(1).replaceAll(" ", "");
                return Integer.parseInt(line);
            }
        }
        return -1;
    }

    private String extractPidorFromStack(ItemStack stack) {
        CompoundNBT display;
        CompoundNBT tag = stack.getTag();
        if (tag != null && tag.contains("display", 10) && (display = tag.getCompound("display")).contains("Lore", 9)) {
            ListNBT lore = display.getList("Lore", 8);
            for (int j = 0; j < lore.size(); ++j) {
                JsonObject title;
                JsonArray array;
                JsonObject object = JsonParser.parseString(lore.getString(j)).getAsJsonObject();
                if (!object.has("extra") || (array = object.getAsJsonArray("extra")).size() <= 2 || !(title = array.get(1).getAsJsonObject()).get("text").getAsString().trim().toLowerCase().startsWith("\u043f\u0440o\u0434a\u0432e\u0446")) continue;
                return array.get(2).getAsJsonObject().get("text").getAsString().trim().replaceAll(" ", "");
            }
        }
        return "";
    }
}

