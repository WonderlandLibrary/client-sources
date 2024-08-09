/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.ab.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.ui.ab.logic.ActivationLogic;
import mpp.venusfr.ui.ab.model.IItem;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.math.StopWatch;
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
import net.minecraft.network.play.client.CClickWindowPacket;

public class AuctionLogic
implements IMinecraft {
    private final ActivationLogic parent;
    private final Minecraft mc;
    final StopWatch refreshStopWatch = new StopWatch();
    final StopWatch buyStopWatch = new StopWatch();
    final StopWatch leaveAuctionStopWatch = new StopWatch();
    final StopWatch returnAuctionStopWatch = new StopWatch();
    boolean leave;
    boolean returnAuc;
    private long lastClickTime = System.currentTimeMillis();

    public AuctionLogic(ActivationLogic activationLogic) {
        this.parent = activationLogic;
        this.mc = Minecraft.getInstance();
    }

    public void processActive() {
        Screen screen = this.mc.currentScreen;
        if (screen instanceof ChestScreen) {
            ChestScreen chestScreen = (ChestScreen)screen;
            this.processBuy(chestScreen);
        }
    }

    public void processBuy(ChestScreen chestScreen) {
        Object t = chestScreen.getContainer();
        if (chestScreen.getTitle().getString().toLowerCase().contains("\u043f\u043e\u0434\u043e\u0437\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u0430\u044f \u0446\u0435\u043d\u0430!")) {
            this.mc.playerController.windowClick(((Container)t).windowId, 10, 0, ClickType.PICKUP, this.mc.player);
        }
        if (chestScreen.getTitle().getString().contains("\u0410\u0443\u043a\u0446\u0438\u043e\u043d") || chestScreen.getTitle().getString().contains("\u041f\u043e\u0438\u0441\u043a:")) {
            this.auctionBotLogic((Container)t, chestScreen);
        }
    }

    public void auctionBotLogic(Container container, ChestScreen chestScreen) {
        for (Slot slot : container.inventorySlots) {
            this.processAuctionSlot(chestScreen, slot);
        }
    }

    public void processAuctionSlot(ChestScreen chestScreen, Slot slot) {
        Object t = chestScreen.getContainer();
        if (this.parent.itemStorage == null) {
            return;
        }
        long l = System.currentTimeMillis();
        CopyOnWriteArrayList<IItem> copyOnWriteArrayList = this.parent.itemStorage.getItems();
        for (IItem iItem : copyOnWriteArrayList) {
            String string;
            boolean bl;
            int n = iItem.getPrice();
            int n2 = this.extractPriceFromStack(slot.getStack());
            boolean bl2 = bl = n2 != -1 && n2 <= n && this.isItemWasFound(iItem, slot);
            if (this.parent.itemList.contains(slot.getStack()) || slot.slotNumber > 48 || !bl) continue;
            this.refreshStopWatch.reset();
            if (!this.checkItem(iItem, slot.getStack()) || (string = this.extractPidorFromStack(slot.getStack())).isEmpty()) continue;
            this.buyItem((Container)t, slot, l);
        }
        if (this.refreshStopWatch.isReached(500L)) {
            this.refreshAuction((Container)t, l);
            this.refreshStopWatch.reset();
        }
    }

    protected void refreshAuction(Container container, long l) {
        if (this.refreshStopWatch.isReached(500L)) {
            this.silentClick(container, 49, ClickType.QUICK_MOVE);
            this.lastClickTime = l;
            this.refreshStopWatch.reset();
        }
    }

    protected void buyItem(Container container, Slot slot, long l) {
        if (l - this.lastClickTime > 1000L) {
            this.mc.playerController.windowClick(container.windowId, slot.slotNumber, 0, ClickType.QUICK_MOVE, this.mc.player);
            this.parent.itemList.add(slot.getStack());
            this.lastClickTime = l;
        }
    }

    protected void returnToAuction() {
        if (this.returnAuctionStopWatch.isReached(350L)) {
            this.mc.player.closeScreen();
            this.mc.player.sendChatMessage("/ah");
            this.returnAuctionStopWatch.reset();
        }
    }

    protected boolean checkItem(IItem iItem, ItemStack itemStack) {
        boolean bl;
        boolean bl2 = bl = itemStack.getTag() != null && itemStack.getTag().contains("don-item");
        if (itemStack.getCount() < iItem.getQuantity()) {
            return true;
        }
        if (!iItem.getEnchantments().isEmpty()) {
            for (Map.Entry<Enchantment, Integer> entry : EnchantmentHelper.getEnchantments(itemStack).entrySet()) {
                Enchantment enchantment = entry.getKey();
                Integer n = entry.getValue();
                if (n == null || iItem.getEnchantments().get(enchantment) == null) {
                    return true;
                }
                if (iItem.getEnchantments().get(enchantment) > n) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private void silentClick(Container container, int n, ClickType clickType) {
        short s = container.getNextTransactionID(this.mc.player.inventory);
        ItemStack itemStack = this.mc.player.inventory.getStackInSlot(n);
        this.mc.player.connection.sendPacket(new CClickWindowPacket(this.mc.player.openContainer.windowId, n, 0, clickType, itemStack, s));
    }

    private boolean isItemWasFound(IItem iItem, Slot slot) {
        return iItem.getItem() == slot.getStack().getItem();
    }

    protected int extractPriceFromStack(ItemStack itemStack) {
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

    protected String extractPidorFromStack(ItemStack itemStack) {
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

