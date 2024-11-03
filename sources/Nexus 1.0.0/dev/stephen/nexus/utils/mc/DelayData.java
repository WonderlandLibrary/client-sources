package dev.stephen.nexus.utils.mc;

import lombok.Getter;
import net.minecraft.network.packet.Packet;

@Getter
public class DelayData {
    private final Packet<?> packet;
    private final long delay;

    public DelayData(Packet<?> packet, long delay) {
        this.packet = packet;
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "DelayData{" +
                "packet=" + packet +
                ", delay=" + delay +
                '}';
    }
}
