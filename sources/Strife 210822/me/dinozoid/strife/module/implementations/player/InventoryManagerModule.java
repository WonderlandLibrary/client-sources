package me.dinozoid.strife.module.implementations.player;

import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketInboundEvent;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.property.Property;
import me.dinozoid.strife.property.implementations.DoubleProperty;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.*;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.util.MovingObjectPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "InventoryManager", renderName = "InventoryManager", description = "Manages your inventory for you.", aliases = "InvManager", category = Category.PLAYER)
public final class InventoryManagerModule extends Module {

    private final DoubleProperty clickDelayProperty = new DoubleProperty("Click Delay", 150, 10, 300, 10, Property.Representation.MILLISECONDS);
    private final Property<Boolean> whileFightingProperty = new Property("While Fighting", false);
    private final Property<Boolean> archeryProperty = new Property("Archery", true);
    private final Property<Boolean> sortHotbarProperty = new Property("Sort Hotbar", true);
    private final Property<Boolean> sortToolsProperty = new Property("Sort Tools", true, sortHotbarProperty::value);
    private final Property<Boolean> spoofProperty = new Property("Spoof", true);
    private int bestSwordSlot;
    private int bestBowSlot;
    private boolean openInventory;
    private final int[] bestArmorPieces = new int[4];
    private final int[] bestToolSlots = new int[3];

    private final TimerUtil interactionsTimer = new TimerUtil();

    private final List<Integer> trash = new ArrayList<>();
    private final List<Integer> duplicateSwords = new ArrayList<>();
    private final List<Integer> gappleStackSlots = new ArrayList<>();

    @Override
    public void init() {
        super.init();
        addValueChangeListener(clickDelayProperty);
    }

    @EventHandler
    private final Listener<PacketInboundEvent> packetInboundListener = new Listener<>(event -> {
       if(openInventory) {
           if(event.packet() instanceof S2EPacketCloseWindow)
               event.cancel();
           if(event.packet() instanceof S2DPacketOpenWindow)
               close();
       }
    });

    @EventHandler
    private final Listener<PacketOutboundEvent> packetOutboundListener = new Listener<>(event -> {
        if(openInventory) {
            if(event.packet() instanceof C16PacketClientStatus) {
                C16PacketClientStatus status = (C16PacketClientStatus) event.packet();
                if(status.getStatus() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT && !(mc.currentScreen instanceof GuiChest))
                    event.cancel();
            }
            if(event.packet() instanceof C0DPacketCloseWindow)
                event.cancel();
        }
    });

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
       if(event.state() == EventState.PRE) {
           if(mc.currentScreen instanceof GuiInventory) {
               if(!interactionsTimer.hasElapsed(clickDelayProperty.value().longValue())) return;
               reset();
               boolean foundSword = false;
               boolean foundBow = false;
               boolean foundGapple = false;
               // Find and save slots
               for(int slot = PlayerUtil.INCLUDE_ARMOR_BEGIN; slot < PlayerUtil.END; slot++) {
                   final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
                   if(stack != null) {
                        // find best swords (trash duplicates)
                        if(stack.getItem() instanceof ItemSword && PlayerUtil.isBestSword(stack)) {
                            if(foundSword)
                                duplicateSwords.add(slot);
                            else if(slot != bestSwordSlot) {
                                foundSword = true;
                                bestSwordSlot = slot;
                            }
                        // find best bows (trash duplicates)
                        } else if(stack.getItem() instanceof ItemBow && PlayerUtil.isBestBow(stack) && archeryProperty.value()) {
                            if(slot != bestBowSlot)
                                bestBowSlot = slot;
                        // find best tools
                        } else if(stack.getItem() instanceof ItemTool && PlayerUtil.isBestTool(stack)) {
                            final int toolType = PlayerUtil.getToolType(stack);
                            if (toolType != -1 && slot != bestToolSlots[toolType])
                                bestToolSlots[toolType] = slot;
                        // find best armor
                        } else if(stack.getItem() instanceof ItemArmor && PlayerUtil.isBestArmor(stack)) {
                            final ItemArmor armor = (ItemArmor) stack.getItem();
                            final int bestSlot = bestArmorPieces[armor.armorType];
                            if(bestSlot == -1 || slot != bestSlot)
                                bestArmorPieces[armor.armorType] = slot;
                        } else if(stack.getItem() instanceof ItemAppleGold) {
                            if(!foundGapple) {
                                if(stack.stackSize == 64) {
                                    foundGapple = true;
                                    gappleStackSlots.add(slot);
                                }
                            } else trash.add(slot);
                        } else if(!trash.contains(slot) && !isValidStack(stack))
                            trash.add(slot);
                   }
               }

               for (int i = 0; i < bestArmorPieces.length; i++) {
                   final int piece = bestArmorPieces[i];
                   if (piece != -1) {
                       final int armorPieceSlot = i + PlayerUtil.INCLUDE_ARMOR_BEGIN;
                       final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(armorPieceSlot).getStack();
                       if (stack != null)
                           continue;
                       open();
                       PlayerUtil.windowClick(piece, 0, PlayerUtil.ClickType.SHIFT_CLICK);
                       close();
                       return;
                   }
               }

               // increment when slot is sorted
               int currentSlot = PlayerUtil.ONLY_HOT_BAR_BEGIN;

               if (!whileFightingProperty.value() && mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && mc.objectMouseOver.entityHit.hurtResistantTime >= 10) {
                   interactionsTimer.reset();
                   return;
               }

               // purge duplicate swords
               if(purgeList(duplicateSwords)) return;

               // sort hotbar
               if (sortHotbarProperty.value()) {
                   // get best sword slot and swap it to 36 (0 in hotbar)
                   if(bestSwordSlot != -1) {
                       if(bestSwordSlot != currentSlot) {
                           putItemInSlot(ItemType.SWORD, currentSlot);
                           return;
                       }
                       currentSlot++;
                   }
               }

               // purge trash
               if (purgeList(trash)) return;

               // sort rest of hotbar
               if (sortHotbarProperty.value()) {
                   // get best bow slot and swap it to 37 (1 in hotbar)
                   if (bestBowSlot != -1) {
                       if (bestBowSlot != currentSlot) {
                           putItemInSlot(ItemType.BOW, currentSlot);
                           return;
                       }
                       currentSlot++;
                   }
                   if (!gappleStackSlots.isEmpty()) {
                       open();
                       gappleStackSlots.sort((s1, s2) -> mc.thePlayer.inventoryContainer.getSlot(s2).getStack().stackSize - mc.thePlayer.inventoryContainer.getSlot(s2).getStack().stackSize);
                       // get the biggest gapple slot
                       int bestSlot = gappleStackSlots.get(0);
                       // set the gapple slot to the currentSlot
                        if(bestSlot != currentSlot) {
                           PlayerUtil.windowClick(bestSlot, currentSlot - PlayerUtil.ONLY_HOT_BAR_BEGIN, PlayerUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                            return;
                        }
                       currentSlot++;
                   }
               }

               // sort tools
               if (sortToolsProperty.value()) {
                   // next 3 slots after last incremented slot
                   final int[] toolSlots = {currentSlot, currentSlot + 1, currentSlot + 2};
                   for(int bestSlot : bestToolSlots) {
                       if(bestSlot != -1) {
                           int type = PlayerUtil.getToolType(mc.thePlayer.inventoryContainer.getSlot(bestSlot).getStack());
                           if(type != -1) {
                               if(bestSlot != toolSlots[type]) {
                                   putToolsInSlot(type, toolSlots);
                                   return;
                               }
                           }
                       }
                   }
               }
           }
       }
    });

    private void reset() {
        trash.clear();
        bestBowSlot = -1;
        bestSwordSlot = -1;
        duplicateSwords.clear();
        gappleStackSlots.clear();
        Arrays.fill(bestArmorPieces, -1);
        Arrays.fill(bestToolSlots, -1);
    }

    private void open() {
        if (!openInventory) {
            interactionsTimer.reset();
            if (spoofProperty.value())
                PlayerUtil.openInventory();
            openInventory = true;
        }
    }

    private void close() {
        if (openInventory) {
            if (spoofProperty.value())
                PlayerUtil.closeInventory();
            openInventory = false;
        }
    }

    private boolean isValidStack(ItemStack stack) {
        if (stack.getItem() instanceof ItemBlock && PlayerUtil.isGoodBlockStack(stack))
            return true;
        else if (archeryProperty.value() && stack.getItem().getUnlocalizedName().equals("item.arrow"))
            return true;
        else if (stack.getItem() instanceof ItemEnderPearl)
            return true;
        else if (stack.getItem() instanceof ItemPotion && PlayerUtil.isBuffPotion(stack))
            return true;
        else return stack.getItem() instanceof ItemFood && PlayerUtil.isGoodFood(stack);
    }

    private boolean purgeList(List<Integer> listOfSlots) {
        if (!listOfSlots.isEmpty()) {
            open();
            int slot = listOfSlots.remove(0);
            PlayerUtil.windowClick(slot, 1, PlayerUtil.ClickType.DROP_ITEM);
            if (listOfSlots.isEmpty())
                close();
            return true;
        }
        return false;
    }

    private enum ItemType {
        SWORD, BOW
    }

    private void putItemInSlot(ItemType type, int slot) {
        open();
        switch (type) {
            case SWORD:
                PlayerUtil.windowClick(bestSwordSlot, slot - PlayerUtil.ONLY_HOT_BAR_BEGIN, PlayerUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                break;
            case BOW:
                PlayerUtil.windowClick(bestBowSlot, slot - PlayerUtil.ONLY_HOT_BAR_BEGIN, PlayerUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
                break;
        }
        close();
    }

    private void putToolsInSlot(int tool, int[] toolSlots) {
        open();
        int toolSlot = toolSlots[tool];
        PlayerUtil.windowClick(bestToolSlots[tool], toolSlot - PlayerUtil.ONLY_HOT_BAR_BEGIN, PlayerUtil.ClickType.SWAP_WITH_HOT_BAR_SLOT);
        bestToolSlots[tool] = toolSlot;
        close();
    }

    public DoubleProperty clickDelay() {
        return clickDelayProperty;
    }

    public static InventoryManagerModule instance() {
        return StrifeClient.INSTANCE.moduleRepository().moduleBy(InventoryManagerModule.class);
    }

}
