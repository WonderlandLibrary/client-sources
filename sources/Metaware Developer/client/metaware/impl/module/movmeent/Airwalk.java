package client.metaware.impl.module.movmeent;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.impl.event.impl.player.PlayerBoundingShitEvent;
import net.minecraft.util.AxisAlignedBB;

@ModuleInfo(renderName = "Airwalk", name = "Airwalk", category = Category.MOVEMENT)
public class Airwalk extends Module {

    @EventHandler
    private Listener<PlayerBoundingShitEvent> eventUpdate = event -> {
        if (this.mc.thePlayer.isSneaking())
            return;
        if (event.getBlock() instanceof net.minecraft.block.BlockAir && event.getY() < this.mc.thePlayer.posY)
            event.setBoundingBox(AxisAlignedBB.fromBounds(event.getX(), event.getY(), event.getZ(), event.getX() + 10.0D, this.mc.thePlayer.posY, event.getZ() + 10.0D));
    };
}
