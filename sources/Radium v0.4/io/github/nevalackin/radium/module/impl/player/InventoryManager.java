package io.github.nevalackin.radium.module.impl.player;

import io.github.nevalackin.radium.event.impl.packet.PacketReceiveEvent;
import io.github.nevalackin.radium.event.impl.packet.PacketSendEvent;
import io.github.nevalackin.radium.event.impl.player.UpdatePositionEvent;
import io.github.nevalackin.radium.event.impl.player.WindowClickEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.module.impl.combat.KillAura;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.EnumProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.InventoryUtils;
import io.github.nevalackin.radium.utils.ServerUtils;
import io.github.nevalackin.radium.utils.TimerUtil;
import io.github.nevalackin.radium.utils.Wrapper;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(label = "Inventory Manager", category = ModuleCategory.SELF)
public final class InventoryManager extends Module {

    private final EnumProperty<ClickDelayMode> clickDelayModeProperty = new EnumProperty<>("Click Delay Mode",
            ClickDelayMode.DELAY);
    private final DoubleProperty clickDelayProperty = new DoubleProperty("Click Delay", 150,
            () -> clickDelayModeProperty.getValue() == ClickDelayMode.DELAY,
            10, 300, 10, Representation.MILLISECONDS);
    private final Property<Boolean> cancelWhenFighting = new Property<>("Cancel When Fighting", true);
    private final TimerUtil interactionsTimer = new TimerUtil();
    private final ArmorPiece[] bestArmorPieces = new ArmorPiece[4];
    private final List<Integer> trash = new ArrayList<>();
    private final int[] bestToolSlots = new int[3];
    private int bestSwordSlot;
    private int bestBowSlot;
    private int waterBucketSlot;
    private final List<Integer> gappleStackSlots = new ArrayList<>();
    private boolean openInventory;

    @Override
    public void onEnable() {
        openInventory = Wrapper.getCurrentScreen() instanceof GuiInventory;
        clear();
        interactionsTimer.reset();
    }

    @Override
    public void onDisable() {
        if (openInventory)
            Wrapper.getPlayer().closeScreen();
    }

    private void clear() {
        trash.clear();
        bestBowSlot = -1;
        bestSwordSlot = -1;
        waterBucketSlot = -1;
        gappleStackSlots.clear();
        Arrays.fill(bestArmorPieces, null);
        Arrays.fill(bestToolSlots, -1);
    }

    @Listener
    public void onWindowClickEvent(WindowClickEvent event) {
        interactionsTimer.reset();
    }

    @Listener
    public void onPacketSendEvent(PacketSendEvent event) {
        if (openInventory) {
            if (event.getPacket() instanceof C16PacketClientStatus) {
                C16PacketClientStatus packet = (C16PacketClientStatus) event.getPacket();
                if (packet.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT)
                    event.setCancelled();
            } else if (event.getPacket() instanceof C0DPacketCloseWindow) {
                openInventory = false;
            }
        }
    }

    @Listener
    public void onPacketReceiveEvent(PacketReceiveEvent event) {
        if (event.getPacket() instanceof S2EPacketCloseWindow ||
                event.getPacket() instanceof S2DPacketOpenWindow)
            openInventory = false;
    }

    @Listener
    public void onUpdatePositionEvent(UpdatePositionEvent event) {
        if (event.isPre()) {
            if (cancelWhenFighting.getValue() && KillAura.getInstance().getTarget() != null) {
                interactionsTimer.reset();
                return;
            }

            GuiScreen currentScreen = Wrapper.getCurrentScreen();
            if (currentScreen == null || currentScreen instanceof GuiInventory) {
                long clickDelay = 250L; // Fallback value
                switch (clickDelayModeProperty.getValue()) {
                    case DELAY:
                        clickDelay = clickDelayProperty.getValue().longValue();
                        break;
                    case DYNAMIC:
                        if (!Wrapper.getMinecraft().isSingleplayer())
                            clickDelay = ServerUtils.getPingToCurrentServer();
                        break;
                }

                if (!interactionsTimer.hasElapsed(clickDelay))
                    return;

                clear();

                Wrapper.forEachInventorySlot(InventoryUtils.INCLUDE_ARMOR_BEGIN, InventoryUtils.END, (slot, stack) -> {
                    if (stack.getItem() instanceof ItemSword) { // Find best sword
                        if (InventoryUtils.isBestSword(stack)) {
                            if (slot != bestSwordSlot)
                                bestSwordSlot = slot;
                        } else if (!trash.contains(slot))
                            trash.add(slot);
                    } else if (stack.getItem() instanceof ItemTool) { // Find best tools
                        if (InventoryUtils.isBestTool(stack)) {
                            int toolType = InventoryUtils.getToolType(stack);
                            if (toolType != -1 && slot != bestToolSlots[toolType])
                                bestToolSlots[toolType] = slot;
                        } else if (!trash.contains(slot))
                            trash.add(slot);
                    } else if (stack.getItem() instanceof ItemArmor) { // Find best armor
                        if (InventoryUtils.isBestArmor(stack)) {
                            ItemArmor armor = (ItemArmor) stack.getItem();

                            ArmorPiece piece = bestArmorPieces[armor.armorType];

                            if (piece == null || slot != piece.getSlot())
                                bestArmorPieces[armor.armorType] =
                                        new ArmorPiece(slot, InventoryUtils.getDamageReduction(stack));
                        } else if (!trash.contains(slot))
                            trash.add(slot);
                    } else if (stack.getItem() instanceof ItemBow) {
                        if (InventoryUtils.isBestBow(stack)) {
                            if (slot != bestBowSlot)
                                bestBowSlot = slot;
                        } else if (!trash.contains(slot))
                            trash.add(slot);
                    } else if (stack.getItem() instanceof ItemAppleGold) {
                        gappleStackSlots.add(slot);
                    } else if (!InventoryUtils.isValid(stack) && !trash.contains(slot))
                        trash.add(slot);
                });

                // Equip armor
                if (interactionsTimer.hasElapsed(clickDelay)) {
                    int slotToClick = -1;

                    for (int i = 0; i < bestArmorPieces.length; i++) {
                        ArmorPiece piece = bestArmorPieces[i];

                        if (piece != null) {
                            int armorPieceSlot = i + 5;
                            ItemStack stack = Wrapper.getStackInSlot(armorPieceSlot);
                            if (stack != null)
                                continue;

                            slotToClick = piece.getSlot();
                            break;
                        }
                    }

                    if (slotToClick != -1) {
                        open();
                        InventoryUtils.windowClick(slotToClick, 0, InventoryUtils.ClickType.SHIFT_CLICK);
                        close();
                        return;
                    }
                }

                // Throw out trash
                if (interactionsTimer.hasElapsed(clickDelay) && !trash.isEmpty()) {
                    open();
                    int slot = trash.remove(0);
                    InventoryUtils.windowClick(slot, 1, InventoryUtils.ClickType.DROP_ITEM);
                    if (trash.isEmpty())
                        close();
                    return;
                }

                int currentSlot = 36;

                // Put best items in slots
                if (interactionsTimer.hasElapsed(clickDelay) && bestSwordSlot != -1) {
                    if (bestSwordSlot != currentSlot) {
                        putSwordInSlot(currentSlot);
                        return;
                    }
                    currentSlot++;
                }


                if (interactionsTimer.hasElapsed(clickDelay) && bestBowSlot != -1) {
                    if (bestBowSlot != currentSlot) {
                        putBowInSlot(currentSlot);
                        return;
                    }
                    currentSlot++;
                }

                if (interactionsTimer.hasElapsed(clickDelay) && !gappleStackSlots.isEmpty()) {
                    gappleStackSlots.sort((slot1, slot2) -> Wrapper.getStackInSlot(slot2).stackSize - Wrapper.getStackInSlot(slot1).stackSize);
                    int bestGappleSlot = gappleStackSlots.get(0);
                    if (bestGappleSlot != currentSlot) {
                        putGappleInSlot(currentSlot, bestGappleSlot);
                        this.gappleStackSlots.set(0, currentSlot);
                        return;
                    }
                    currentSlot++;
                }

                int[] toolSlots = {currentSlot, currentSlot + 1, currentSlot + 2};

                for (int toolSlot : bestToolSlots) {
                    int type = InventoryUtils.getToolType(Wrapper.getStackInSlot(toolSlot));
                    if (toolSlot != -1) {
                        if (toolSlot != toolSlots[type]) {
                            putToolsInSlot(type, toolSlots);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void putSwordInSlot(int swordSlot) {
        open();
        InventoryUtils.windowClick(Wrapper.getPlayer().inventoryContainer.windowId,
                bestSwordSlot,
                swordSlot - 36,
                InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        bestSwordSlot = swordSlot;
        close();
    }

    private void putBowInSlot(int bowSlot) {
        open();
        InventoryUtils.windowClick(Wrapper.getPlayer().inventoryContainer.windowId,
                bestBowSlot,
                bowSlot - 36,
                InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        bestBowSlot = bowSlot;
        close();
    }

    private void putGappleInSlot(int gappleSlot, int slotIn) {
        open();
        InventoryUtils.windowClick(Wrapper.getPlayer().inventoryContainer.windowId,
                slotIn,
                gappleSlot - 36,
                InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        close();
    }

    private void putToolsInSlot(int tool, int[] toolSlots) {
        open();
        int toolSlot = toolSlots[tool];
        InventoryUtils.windowClick(Wrapper.getPlayer().inventoryContainer.windowId,
                bestToolSlots[tool],
                toolSlot - 36,
                InventoryUtils.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        bestToolSlots[tool] = toolSlot;
        close();
    }

    private void open() {
        if (!openInventory) {
            interactionsTimer.reset();
            InventoryUtils.openInventory();
            openInventory = true;
        }
    }

    private void close() {
        if (openInventory) {
            InventoryUtils.closeInventory();
            openInventory = false;
        }
    }

    private enum ClickDelayMode {
        DYNAMIC, DELAY
    }

    private static class ArmorPiece {
        private final int slot;
        private final double reduction;

        public ArmorPiece(int slot, double reduction) {
            this.slot = slot;
            this.reduction = reduction;
        }

        public int getSlot() {
            return slot;
        }

        public double getReduction() {
            return reduction;
        }
    }
}
