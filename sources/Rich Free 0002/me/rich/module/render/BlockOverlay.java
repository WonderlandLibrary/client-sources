package me.rich.module.render;

import me.rich.Main;
import me.rich.event.EventTarget;
import me.rich.event.events.Event3D;
import me.rich.helpers.render.RenderHelper;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;

public class BlockOverlay extends Feature {

	public BlockOverlay() {
		super("BlockOverlay", 0, Category.RENDER);
	}

    @EventTarget
    public void onRender(final Event3D event){
        if(mc.objectMouseOver.getBlockPos() != null && mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock() != Blocks.AIR && mc.world.getBlockState(mc.objectMouseOver.getBlockPos()).getBlock().isFullBlock(mc.world.getBlockState(mc.objectMouseOver.getBlockPos()))){
            double[] cords = {mc.objectMouseOver.getBlockPos().getX() - mc.getRenderManager().renderPosX, mc.objectMouseOver.getBlockPos().getY() - mc.getRenderManager().renderPosY, mc.objectMouseOver.getBlockPos().getZ() - mc.getRenderManager().renderPosZ};
            RenderHelper.drawOutline(new AxisAlignedBB(cords[0], cords[1] + 1, cords[2], cords[0] + 1, cords[1] + 1, cords[2] + 1), 1.7f, Main.getClientColor());
        }
    }
    @Override
    public void onEnable() {
        super.onEnable();
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
    }

    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        super.onDisable();
    }
}
