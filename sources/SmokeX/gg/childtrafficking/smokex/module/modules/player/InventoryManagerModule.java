// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.module.modules.player;

import java.util.Arrays;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.client.gui.GuiScreen;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.combat.KillauraModule;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import gg.childtrafficking.smokex.utils.player.PlayerUtils;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import java.util.ArrayList;
import gg.childtrafficking.smokex.event.events.player.EventUpdate;
import gg.childtrafficking.smokex.event.events.network.EventReceivePacket;
import gg.childtrafficking.smokex.event.events.network.EventSendPacket;
import java.util.List;
import gg.childtrafficking.smokex.event.events.player.EventWindowClick;
import gg.childtrafficking.smokex.event.EventListener;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import gg.childtrafficking.smokex.property.properties.BooleanProperty;
import gg.childtrafficking.smokex.property.properties.NumberProperty;
import gg.childtrafficking.smokex.module.ModuleCategory;
import gg.childtrafficking.smokex.module.ModuleInfo;
import gg.childtrafficking.smokex.module.Module;

@ModuleInfo(name = "InventoryManager", renderName = "Inventory Manager", aliases = { "InvManager" }, category = ModuleCategory.PLAYER)
public final class InventoryManagerModule extends Module
{
    public final NumberProperty<Long> clickDelayProperty;
    public final BooleanProperty whileFightingProperty;
    public final BooleanProperty archeryProperty;
    public final BooleanProperty sortHotbarProperty;
    public final BooleanProperty sortToolsProperty;
    public final BooleanProperty spoofInventoryProperty;
    private final TimerUtil interactionsTimer;
    private final EventListener<EventWindowClick> onWindowClickEvent;
    private final int[] bestArmorPieces;
    private final List<Integer> trash;
    private final List<Integer> duplicateSwords;
    private final int[] bestToolSlots;
    private final List<Integer> gappleStackSlots;
    private int bestSwordSlot;
    private int bestBowSlot;
    private boolean openInventory;
    private final EventListener<EventSendPacket> onPacketSendEvent;
    private final EventListener<EventReceivePacket> onPacketReceiveEvent;
    public final EventListener<EventUpdate> updatePlayerEvent;
    
    public InventoryManagerModule() {
        this.clickDelayProperty = new NumberProperty<Long>("Click Delay", 150L, 10L, 300L, 10L);
        this.whileFightingProperty = new BooleanProperty("While Fighting", true);
        this.archeryProperty = new BooleanProperty("Archery", false);
        this.sortHotbarProperty = new BooleanProperty("Sort Hotbar", true);
        this.sortToolsProperty = new BooleanProperty("Sort Tools", false);
        this.spoofInventoryProperty = new BooleanProperty("Spoof", false);
        this.interactionsTimer = new TimerUtil();
        this.onWindowClickEvent = (event -> this.interactionsTimer.reset());
        this.bestArmorPieces = new int[4];
        this.trash = new ArrayList<Integer>();
        this.duplicateSwords = new ArrayList<Integer>();
        this.bestToolSlots = new int[3];
        this.gappleStackSlots = new ArrayList<Integer>();
        this.onPacketSendEvent = (event -> {
            if (this.openInventory) {
                if (event.getPacket() instanceof C16PacketClientStatus) {
                    final C16PacketClientStatus packet = (C16PacketClientStatus)event.getPacket();
                    if (packet.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT) {
                        event.cancel();
                    }
                }
                else if (event.getPacket() instanceof C0DPacketCloseWindow) {
                    event.cancel();
                }
            }
            return;
        });
        this.onPacketReceiveEvent = (event -> {
            if (this.openInventory) {
                if (event.getPacket() instanceof S2DPacketOpenWindow) {
                    this.close();
                }
                else if (event.getPacket() instanceof S2EPacketCloseWindow) {
                    event.cancel();
                }
            }
            return;
        });
        this.updatePlayerEvent = (event -> {
            if (event.isPre() && !PlayerUtils.isInLobby()) {
                final GuiScreen currentScreen = this.mc.currentScreen;
                if (currentScreen == null || currentScreen instanceof GuiChest || currentScreen instanceof GuiInventory) {
                    final long clickDelay = this.clickDelayProperty.getValue();
                    if (!(!this.interactionsTimer.hasElapsed((double)clickDelay))) {
                        this.clear();
                        boolean foundSword = false;
                        for (int slot = 5; slot < 45; ++slot) {
                            final ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                            if (stack != null) {
                                if (stack.getItem() instanceof ItemSword && PlayerUtils.isBestSword(stack)) {
                                    if (foundSword) {
                                        this.duplicateSwords.add(slot);
                                    }
                                    else if (slot != this.bestSwordSlot) {
                                        foundSword = true;
                                        this.bestSwordSlot = slot;
                                    }
                                }
                                else if (stack.getItem() instanceof ItemTool && PlayerUtils.isBestTool(stack)) {
                                    final int toolType = PlayerUtils.getToolType(stack);
                                    if (toolType != -1 && slot != this.bestToolSlots[toolType]) {
                                        this.bestToolSlots[toolType] = slot;
                                    }
                                }
                                else if (stack.getItem() instanceof ItemArmor && PlayerUtils.isBestArmor(stack)) {
                                    final ItemArmor armor = (ItemArmor)stack.getItem();
                                    final int pieceSlot = this.bestArmorPieces[armor.armorType];
                                    if (pieceSlot == -1 || slot != pieceSlot) {
                                        this.bestArmorPieces[armor.armorType] = slot;
                                    }
                                }
                                else if (stack.getItem() instanceof ItemBow && this.archeryProperty.getValue() && PlayerUtils.isBestBow(stack)) {
                                    if (slot != this.bestBowSlot) {
                                        this.bestBowSlot = slot;
                                    }
                                }
                                else if (stack.getItem() instanceof ItemAppleGold) {
                                    this.gappleStackSlots.add(slot);
                                }
                                else if (!this.trash.contains(slot) && !this.isValidStack(stack)) {
                                    this.trash.add(slot);
                                }
                            }
                        }
                        for (int i = 0; i < this.bestArmorPieces.length; ++i) {
                            final int piece = this.bestArmorPieces[i];
                            if (piece != -1) {
                                final int armorPieceSlot = i + 5;
                                final ItemStack stack2 = this.mc.thePlayer.inventoryContainer.getSlot(armorPieceSlot).getStack();
                                if (stack2 == null) {
                                    this.open();
                                    PlayerUtils.windowClick(piece, 0, PlayerUtils.ClickType.SHIFT_CLICK);
                                    this.close();
                                    return;
                                }
                            }
                        }
                        if (!this.whileFightingProperty.getValue() && ModuleManager.getInstance(KillauraModule.class).getTarget() != null) {
                            this.interactionsTimer.reset();
                        }
                        else if (!this.purgeList(this.duplicateSwords)) {
                            int currentSlot = 36;
                            if (this.bestSwordSlot != -1) {
                                if (this.bestSwordSlot != currentSlot) {
                                    this.putSwordInSlot(currentSlot);
                                    return;
                                }
                                else {
                                    ++currentSlot;
                                }
                            }
                            if (!this.purgeList(this.trash)) {
                                if (this.sortHotbarProperty.getValue()) {
                                    if (this.bestBowSlot != -1) {
                                        if (this.bestBowSlot != currentSlot) {
                                            this.putBowInSlot(currentSlot);
                                            return;
                                        }
                                        else {
                                            ++currentSlot;
                                        }
                                    }
                                    if (!this.gappleStackSlots.isEmpty()) {
                                        this.gappleStackSlots.sort((slot1, slot2) -> this.mc.thePlayer.inventoryContainer.getSlot(slot2).getStack().stackSize - this.mc.thePlayer.inventoryContainer.getSlot(slot1).getStack().stackSize);
                                        final int bestGappleSlot = this.gappleStackSlots.get(0);
                                        if (bestGappleSlot != currentSlot) {
                                            this.putGappleInSlot(currentSlot, bestGappleSlot);
                                            this.gappleStackSlots.set(0, currentSlot);
                                            return;
                                        }
                                        else {
                                            ++currentSlot;
                                        }
                                    }
                                    if (this.sortToolsProperty.getValue()) {
                                        final int[] toolSlots = { currentSlot, currentSlot + 1, currentSlot + 2 };
                                        final int[] bestToolSlots = this.bestToolSlots;
                                        int j = 0;
                                        for (int length = bestToolSlots.length; j < length; ++j) {
                                            final int toolSlot = bestToolSlots[j];
                                            if (toolSlot != -1) {
                                                final int type = PlayerUtils.getToolType(this.mc.thePlayer.inventoryContainer.getSlot(toolSlot).getStack());
                                                if (type != -1 && toolSlot != toolSlots[type]) {
                                                    this.putToolsInSlot(type, toolSlots);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
    private void putSwordInSlot(final int swordSlot) {
        this.open();
        PlayerUtils.windowClick(this.mc.thePlayer.inventoryContainer.windowId, this.bestSwordSlot, swordSlot - 36, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        this.bestSwordSlot = swordSlot;
        this.close();
    }
    
    private void putBowInSlot(final int bowSlot) {
        this.open();
        PlayerUtils.windowClick(this.mc.thePlayer.inventoryContainer.windowId, this.bestBowSlot, bowSlot - 36, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        this.bestBowSlot = bowSlot;
        this.close();
    }
    
    private void putGappleInSlot(final int gappleSlot, final int slotIn) {
        this.open();
        PlayerUtils.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slotIn, gappleSlot - 36, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        this.close();
    }
    
    private void putToolsInSlot(final int tool, final int[] toolSlots) {
        this.open();
        final int toolSlot = toolSlots[tool];
        PlayerUtils.windowClick(this.mc.thePlayer.inventoryContainer.windowId, this.bestToolSlots[tool], toolSlot - 36, PlayerUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        this.bestToolSlots[tool] = toolSlot;
        this.close();
    }
    
    private boolean isValidStack(final ItemStack stack) {
        return (stack.getItem() instanceof ItemBlock && PlayerUtils.isGoodBlockStack(stack)) || (this.archeryProperty.getValue() && stack.getItem().getUnlocalizedName().equals("item.arrow")) || stack.getItem() instanceof ItemEnderPearl || (stack.getItem() instanceof ItemPotion && PlayerUtils.isBuffPotion(stack)) || (stack.getItem() instanceof ItemFood && PlayerUtils.isGoodFood(stack));
    }
    
    private boolean purgeList(final List<Integer> listOfSlots) {
        if (!listOfSlots.isEmpty()) {
            this.open();
            final int slot = listOfSlots.remove(0);
            PlayerUtils.windowClick(slot, 1, PlayerUtils.ClickType.DROP_ITEM);
            if (listOfSlots.isEmpty()) {
                this.close();
            }
            return true;
        }
        return false;
    }
    
    private void clear() {
        this.trash.clear();
        this.bestBowSlot = -1;
        this.bestSwordSlot = -1;
        this.gappleStackSlots.clear();
        Arrays.fill(this.bestArmorPieces, -1);
        Arrays.fill(this.bestToolSlots, -1);
        this.duplicateSwords.clear();
    }
    
    private void open() {
        if (!this.openInventory) {
            this.interactionsTimer.reset();
            if (this.spoofInventoryProperty.getValue()) {
                PlayerUtils.openInventory();
            }
            this.openInventory = true;
        }
    }
    
    private void close() {
        if (this.openInventory) {
            if (this.spoofInventoryProperty.getValue()) {
                PlayerUtils.closeInventory();
            }
            this.openInventory = false;
        }
    }
}
