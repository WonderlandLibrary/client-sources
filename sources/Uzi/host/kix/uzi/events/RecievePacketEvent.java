package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

/**
 * Created by myche on 2/4/2017.
 */
public class RecievePacketEvent extends EventCancellable{

    private Packet packet;

    public Packet getPacket() {
        return this.packet;
    }

    public void setPacket(final Packet packet) {
        this.packet = packet;
    }

    public RecievePacketEvent(final Packet packet) {
        this.packet = packet;
    }

}
