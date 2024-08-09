/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.ui.autobuy.AutoBuy;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.ShulkerUtil;
import mpp.venusfr.venusfr;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
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
        venusfr.getInstance().getEventBus().register(this);
        instance = this;
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        if (process) {
            if (AutoBuyHandler.mc.player == null || AutoBuyHandler.mc.world == null) {
                return;
            }
            IPacket<?> iPacket = eventPacket.getPacket();
            if (iPacket instanceof SChatPacket) {
                SChatPacket sChatPacket = (SChatPacket)iPacket;
                if (((String)((Object)(iPacket = sChatPacket.getChatComponent().getString().toLowerCase(Locale.ROOT)))).contains("\u044d\u0442\u043e\u0442 \u0442\u043e\u0432\u0430\u0440 \u0443\u0436\u0435 \u043a\u0443\u043f\u0438\u043b\u0438!")) {
                    this.returnToAuction();
                }
                if (((String)((Object)iPacket)).contains("\u0432\u0430\u0448 \u0431\u0430\u043b\u0430\u043d\u0441:")) {
                    String[] stringArray = ((String)((Object)iPacket)).split("\u0432\u0430\u0448 \u0431\u0430\u043b\u0430\u043d\u0441:")[1].trim().split("\\s");
                    try {
                        this.lastBalance = Integer.parseInt(stringArray[0].replaceAll("[^\\d]", ""));
                    } catch (NumberFormatException numberFormatException) {
                        numberFormatException.printStackTrace();
                    }
                    eventPacket.cancel();
                }
            }
        }
    }

    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        Screen screen;
        if (process && (screen = AutoBuyHandler.mc.currentScreen) instanceof ChestScreen) {
            ChestScreen chestScreen = (ChestScreen)screen;
            this.processBuy(chestScreen);
        }
    }

    private void processBuy(ChestScreen chestScreen) {
        Object t = chestScreen.getContainer();
        if (chestScreen.getTitle().getString().toLowerCase().contains("\u043f\u043e\u0434\u043e\u0437\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u0430\u044f \u0446\u0435\u043d\u0430!")) {
            AutoBuyHandler.mc.playerController.windowClick(((Container)t).windowId, 10, 0, ClickType.PICKUP, AutoBuyHandler.mc.player);
        }
        if (chestScreen.getTitle().getString().contains("\u0410\u0443\u043a\u0446\u0438\u043e\u043d") || chestScreen.getTitle().getString().contains("\u041f\u043e\u0438\u0441\u043a:")) {
            this.auctionBotLogic((Container)t, chestScreen);
        }
    }

    private void auctionBotLogic(Container container, ChestScreen chestScreen) {
        this.refreshAuction(container);
        for (Slot slot : container.inventorySlots) {
            this.processAuctionSlot(chestScreen, slot);
        }
    }

    private void processAuctionSlot(ChestScreen chestScreen, Slot slot) {
        Object t = chestScreen.getContainer();
        for (AutoBuy autoBuy : this.items) {
            boolean bl;
            int n = autoBuy.getPrice();
            int n2 = this.extractPriceFromStack(slot.getStack());
            boolean bl2 = bl = n2 != -1 && n2 <= n && this.isItemWasFound(autoBuy, slot);
            if (this.buyedList.contains(slot.getStack()) || slot.slotNumber > 48) continue;
            if (bl) {
                String string;
                if (!this.checkItem(autoBuy, slot.getStack()) || (string = this.extractPidorFromStack(slot.getStack())).isEmpty()) continue;
                if (!chestScreen.getTitle().getString().contains(string)) {
                    this.leaveAuction(string);
                    return;
                }
                if (!this.buyStopWatch.isReached(100L)) continue;
                this.buyItem((Container)t, slot);
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
        AutoBuyHandler.mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, AutoBuyHandler.mc.player);
        this.buyedList.add(slot.getStack());
        this.refreshStopWatch.reset();
        this.buyStopWatch.reset();
    }

    private void leaveAuction(String string) {
        if (this.leaveUpdate.isReached(400L)) {
            AutoBuyHandler.mc.player.closeScreen();
            AutoBuyHandler.mc.player.sendChatMessage("/ah " + string);
            this.leaveUpdate.reset();
        }
    }

    private void returnToAuction() {
        if (this.leaveUpdate.isReached(350L)) {
            AutoBuyHandler.mc.player.closeScreen();
            AutoBuyHandler.mc.player.sendChatMessage("/ah");
            this.leaveUpdate.reset();
        }
    }

    private boolean checkItem(AutoBuy autoBuy, ItemStack itemStack) {
        boolean bl;
        boolean bl2 = bl = itemStack.getTag() != null && itemStack.getTag().contains("don-item");
        if ((autoBuy.isItems() || autoBuy.isDon()) && Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock && ShulkerUtil.getItemInShulker(itemStack, autoBuy).isEmpty()) {
            return true;
        }
        if (itemStack.getCount() < autoBuy.getCount()) {
            return true;
        }
        if (autoBuy.isFake() && !bl) {
            return true;
        }
        if (!autoBuy.getEnchanments().isEmpty()) {
            for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(itemStack).entrySet()) {
                Enchantment enchantment = entry.getKey();
                Integer n = entry.getValue();
                if (n == null || autoBuy.getEnchanments().get(enchantment) == null) {
                    return true;
                }
                if (autoBuy.getEnchanments().get(enchantment) > n) continue;
                this.print(n + " " + autoBuy.getEnchanments().get(enchantment));
                return false;
            }
            return true;
        }
        return false;
    }

    private void silentClick(Container container, int n, ClickType clickType) {
        short s = container.getNextTransactionID(AutoBuyHandler.mc.player.inventory);
        ItemStack itemStack = AutoBuyHandler.mc.player.inventory.getStackInSlot(n);
        AutoBuyHandler.mc.player.connection.sendPacket(new CClickWindowPacket(AutoBuyHandler.mc.player.openContainer.windowId, n, 0, clickType, itemStack, s));
    }

    private boolean isItemWasFound(AutoBuy autoBuy, Slot slot) {
        return autoBuy.getItem() == slot.getStack().getItem();
    }

    private int extractPriceFromStack(ItemStack itemStack) {
        CompoundNBT compoundNBT;
        CompoundNBT compoundNBT2 = itemStack.getTag();
        if (compoundNBT2 != null && compoundNBT2.contains("display", 1) && (compoundNBT = compoundNBT2.getCompound("display")).contains("Lore", 0)) {
            ListNBT listNBT = compoundNBT.getList("Lore", 8);
            for (int i = 0; i < listNBT.size(); ++i) {
                JsonObject jsonObject;
                JsonArray jsonArray;
                JsonObject jsonObject2 = JsonParser.parseString(listNBT.getString(i)).getAsJsonObject();
                if (!jsonObject2.has("extra") || (jsonArray = jsonObject2.getAsJsonArray("extra")).size() <= 2 || !(jsonObject = jsonArray.get(1).getAsJsonObject()).get("text").getAsString().trim().toLowerCase().contains("\u0446\u0435\u043da")) continue;
                String string = jsonArray.get(2).getAsJsonObject().get("text").getAsString().trim().substring(1).replaceAll(" ", "");
                return Integer.parseInt(string);
            }
        }
        return 1;
    }

    private String extractPidorFromStack(ItemStack itemStack) {
        CompoundNBT compoundNBT;
        CompoundNBT compoundNBT2 = itemStack.getTag();
        if (compoundNBT2 != null && compoundNBT2.contains("display", 1) && (compoundNBT = compoundNBT2.getCompound("display")).contains("Lore", 0)) {
            ListNBT listNBT = compoundNBT.getList("Lore", 8);
            for (int i = 0; i < listNBT.size(); ++i) {
                JsonObject jsonObject;
                JsonArray jsonArray;
                JsonObject jsonObject2 = JsonParser.parseString(listNBT.getString(i)).getAsJsonObject();
                if (!jsonObject2.has("extra") || (jsonArray = jsonObject2.getAsJsonArray("extra")).size() <= 2 || !(jsonObject = jsonArray.get(1).getAsJsonObject()).get("text").getAsString().trim().toLowerCase().startsWith("\u043f\u0440o\u0434a\u0432e\u0446")) continue;
                return jsonArray.get(2).getAsJsonObject().get("text").getAsString().trim().replaceAll(" ", "");
            }
        }
        return "";
    }
}

