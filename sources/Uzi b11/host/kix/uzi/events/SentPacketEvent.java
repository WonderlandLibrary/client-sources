package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

/**
 * Created by myche on 3/6/2017.
 */
public class SentPacketEvent extends EventCancellable {

    private Packet packet;

    public SentPacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(final Packet packet) {
        this.packet = packet;
    }

}
