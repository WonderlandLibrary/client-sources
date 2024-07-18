package com.alan.clients.util.packet;

import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.script.api.NetworkAPI;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.player.SlotUtil;
import lombok.experimental.UtilityClass;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S2FPacketSetSlot;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public final class PacketUtil implements Accessor {

    public void send(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public void sendNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToSendQueueUnregistered(packet);
    }

    public void queue(final Packet<?> packet) {
        if (packet == null) {
            System.out.println("Packet is null");
            return;
        }

        if (isClientPacket(packet)) {
            mc.getNetHandler().addToSendQueue(packet);
        } else {
            mc.getNetHandler().addToReceiveQueue(packet);
        }
    }

    public void queueNoEvent(final Packet<?> packet) {
        if (isClientPacket(packet)) {
            mc.getNetHandler().addToSendQueueUnregistered(packet);
        } else {
            mc.getNetHandler().addToReceiveQueueUnregistered(packet);
        }
    }

    public void receive(final Packet<?> packet) {
        mc.getNetHandler().addToReceiveQueue(packet);
    }

    public void receiveNoEvent(final Packet<?> packet) {
        mc.getNetHandler().addToReceiveQueueUnregistered(packet);
    }

    public static boolean isServerPacket(final Packet<?> packet) {
        return !isClientPacket(packet);
    }

    public static boolean isClientPacket(final Packet<?> packet) {
        return Arrays.stream(NetworkAPI.serverbound).anyMatch(clazz -> clazz == packet.getClass());
    }

    public void correctBlockCount(PacketReceiveEvent event) {
        if (mc.thePlayer.isDead || true) return;

        final Packet<?> packet = event.getPacket();

        if (packet instanceof S2FPacketSetSlot) {
            final S2FPacketSetSlot wrapper = ((S2FPacketSetSlot) packet);

            if (wrapper.stack() == null) {
                event.setCancelled();
            } else {
                try {
                    int slot = wrapper.slotID() - 36;
                    if (slot < 0) return;
                    final ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(slot);
                    final Item item = wrapper.stack().getItem();

                    if ((itemStack == null && wrapper.stack().stackSize <= 6 && item instanceof ItemBlock && !SlotUtil.blacklist.contains(((ItemBlock) item).getBlock())) ||
                            itemStack != null && Math.abs(Objects.requireNonNull(itemStack).stackSize - wrapper.stack().stackSize) <= 6 ||
                            wrapper.stack() == null) {
                        event.setCancelled();
                    }
                } catch (ArrayIndexOutOfBoundsException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public class TimedPacket {
        private final Packet<?> packet;
        private final long time;

        public TimedPacket(final Packet<?> packet, final long time) {
            this.packet = packet;
            this.time = time;
        }

        public TimedPacket(final Packet<?> packet) {
            this.packet = packet;
            this.time = System.currentTimeMillis();
        }

        public Packet<?> getPacket() {
            return packet;
        }

        public long getTime() {
            return time;
        }
    }
}
