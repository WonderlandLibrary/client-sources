package ooo.cpacket.ruby.module.move;

import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class Jesus extends Module {

	public Jesus(String name, int key, Category category) {
		super(name, key, category);
	}
	
	@EventImpl
	public void onUpdate(EventMotionUpdate event) {
		BlockPos underPlayer = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.03, mc.thePlayer.posZ);
		if (mc.theWorld.getBlockState(underPlayer).getBlock() instanceof BlockLiquid) {
			if (mc.thePlayer.ticksExisted % 2 == 0) {
				event.setPosY(event.getPosY() - 0.03);
			}
			event.setOnGround(false);
			if (mc.thePlayer.isInWater()) {
				mc.thePlayer.motionY += 0.0315;
			}
			if (mc.thePlayer.isSneaking()) {
				mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0325, mc.thePlayer.posZ);
			}
		}
	}
	
	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		
	}

}
