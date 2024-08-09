package mathax.client.systems.modules.movement;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.mixin.PlayerMoveC2SPacketAccessor;
import mathax.client.mixin.VehicleMoveC2SPacketAccessor;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;

public class RoboWalk extends Module {
    public RoboWalk() {
        super(Categories.Movement, Items.IRON_BLOCK, "robo-walk", "Bypasses LiveOverflow movement check.");
    }

    private double smooth(double d) {
        double temp = (double) Math.round(d * 100) / 100;
        return Math.nextAfter(temp, temp + Math.signum(d));
    }

    @EventHandler
    private void onPacketSend(PacketEvent.Send event) {
        if (!mc.player.isRiding()) {
            if (event.packet instanceof PlayerMoveC2SPacket packet) {
                if (!packet.changesPosition()) return;

                double x = smooth(packet.getX(0));
                double z = smooth(packet.getZ(0));

                ((PlayerMoveC2SPacketAccessor) packet).setX(x);
                ((PlayerMoveC2SPacketAccessor) packet).setZ(z);
            } else if (event.packet instanceof VehicleMoveC2SPacket packet) {
                double x = smooth(packet.getX());
                double z = smooth(packet.getZ());

                ((VehicleMoveC2SPacketAccessor) packet).setX(x);
                ((VehicleMoveC2SPacketAccessor) packet).setZ(z);
            }
        }
    }
}
