package xyz.mathax.mathaxclient.systems.modules.world;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.packets.PacketEvent;
import xyz.mathax.mathaxclient.mixin.BlockHitResultAccessor;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.Direction;

public class BuildHeight extends Module {
    public BuildHeight(Category category) {
        super(category, "Build Height", "Allows you to interact with objects at the build limit.");
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerInteractBlockC2SPacket packet)) {
            return;
        }

        if (packet.getBlockHitResult().getPos().y >= 255 && packet.getBlockHitResult().getSide() == Direction.UP) {
            ((BlockHitResultAccessor) packet.getBlockHitResult()).setSide(Direction.DOWN);
        }
    }
}