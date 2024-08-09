package im.expensive.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.IPacket;

@Getter
@Setter
@AllArgsConstructor
public class EventPacket extends CancelEvent {

    private IPacket<?> packet;
    private Type type;
    public boolean isSend() {
        return type == Type.SEND;
    }
    public boolean isReceive() {
        return type == Type.RECEIVE;
    }
    public enum Type {
        RECEIVE, SEND
    }
}
