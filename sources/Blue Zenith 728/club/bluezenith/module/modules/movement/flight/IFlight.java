package club.bluezenith.module.modules.movement.flight;

import club.bluezenith.events.impl.CollisionEvent;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.modules.movement.Flight;
import net.minecraft.client.Minecraft;

public interface IFlight {

    Minecraft mc = Minecraft.getMinecraft();
    void onCollision(CollisionEvent event, Flight flight);
    void onPlayerUpdate(UpdatePlayerEvent event, Flight flight);
    void onMoveEvent(MoveEvent event, Flight flight);
    default void onPacket(PacketEvent event, Flight flight){}
    void onEnable(Flight flight);
    void onDisable(Flight flight);
}
