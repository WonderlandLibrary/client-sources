package mathax.client.systems.modules.world;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.packets.PacketEvent;
import mathax.client.mixin.BlockHitResultAccessor;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.Direction;

public class BuildHeight extends Module {
    public BuildHeight() {
        super(Categories.World, Items.SCAFFOLDING, "build-height", "Allows you to interact with objects at the build limit.");
    }
    //build height is just 255 or 320??? why is this needed :/
    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerInteractBlockC2SPacket packet)) return;
        if (packet.getBlockHitResult().getPos().y >= 255 && packet.getBlockHitResult().getSide() == Direction.UP) ((BlockHitResultAccessor) packet.getBlockHitResult()).setSide(Direction.DOWN);
    }
}
