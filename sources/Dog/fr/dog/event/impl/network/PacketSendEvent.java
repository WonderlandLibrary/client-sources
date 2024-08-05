package fr.dog.event.impl.network;

import fr.dog.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.network.Packet;

@Getter
@Setter
@AllArgsConstructor
public class PacketSendEvent extends CancellableEvent {

    public Packet<?> packet;

}
