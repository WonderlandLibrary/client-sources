package meteordevelopment.meteorclient.utils.player;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import meteordevelopment.meteorclient.mixininterface.IClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PickFromInventoryC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Objects;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public final class CrystalsInvUtils {


    public static void swap (SwapType type, int slot) {
        if (slot <= -1) return;
        if (mc.player == null || mc.interactionManager == null || mc.getNetworkHandler() == null) return;
        switch (type) {
            case Simple -> {
                mc.player.getInventory().selectedSlot = slot;
                ((IClientPlayerInteractionManager) mc.interactionManager).syncSelected();
            }

            case InventoryClick -> {
                ScreenHandler handler = mc.player.currentScreenHandler;
                Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
                stack.put(slot, handler.getSlot(slot).getStack());

                mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                        handler.getRevision(), PlayerInventory.MAIN_SIZE + mc.player.getInventory().selectedSlot,
                        slot, SlotActionType.SWAP, handler.getSlot(slot).getStack(), stack)
                );
            }

            case PickSwitch -> {
                Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PickFromInventoryC2SPacket(slot));
            }
        }
    }

    public static void swap (SwapType type, int slot, Runnable runnable) {
        if (slot <= -1) return;
        if (mc.player == null || mc.interactionManager == null || mc.getNetworkHandler() == null) return;
        switch (type) {
            case Simple -> {
                int oldslot = mc.player.getInventory().selectedSlot;
                mc.player.getInventory().selectedSlot = slot;
                ((IClientPlayerInteractionManager) mc.interactionManager).syncSelected();
                runnable.run();
                mc.player.getInventory().selectedSlot = oldslot;
                ((IClientPlayerInteractionManager) mc.interactionManager).syncSelected();
            }

            case InventoryClick -> {
                ScreenHandler handler = mc.player.currentScreenHandler;
                Int2ObjectArrayMap<ItemStack> stack = new Int2ObjectArrayMap<>();
                stack.put(slot, handler.getSlot(slot).getStack());

                mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                        handler.getRevision(), PlayerInventory.MAIN_SIZE + mc.player.getInventory().selectedSlot,
                        slot, SlotActionType.SWAP, handler.getSlot(slot).getStack(), stack)
                );
                runnable.run();
                mc.getNetworkHandler().sendPacket(new ClickSlotC2SPacket(handler.syncId,
                        handler.getRevision(), PlayerInventory.MAIN_SIZE + mc.player.getInventory().selectedSlot,
                        slot, SlotActionType.SWAP, handler.getSlot(slot).getStack(), stack)
                );
            }

            case PickSwitch -> {
                Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PickFromInventoryC2SPacket(slot));
                runnable.run();
                Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PickFromInventoryC2SPacket(slot));
            }
        }
    }
}
