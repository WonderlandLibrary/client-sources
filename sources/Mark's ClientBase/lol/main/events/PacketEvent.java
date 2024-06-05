package lol.main.events;

import lol.base.addons.EventAddon;
import lol.base.radbus.Type;
import lombok.AllArgsConstructor;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;

@AllArgsConstructor
public class PacketEvent extends EventAddon {
    public Packet<?> packet;
    public INetHandler iNetHandler;
    public Type type;
}
