package win.sightclient.module.movement.step;

import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.player.EventStep;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.utils.TimerUtils;

public class FakeJumpStep extends ModuleMode {

	private TimerUtils timer;
	
	public FakeJumpStep(Module parent, TimerUtils timer) {
		super(parent);
		this.timer = timer;
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventStep) {
			EventStep es = (EventStep)e;
			try {
				if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(0, mc.thePlayer.stepHeight, 0)).size() != 0 || Sight.instance.mm.isEnabled("Speed"))
			         return;
			} catch (Exception e2) {
				return;
			}
			
			if (!es.isPre()) {
				if (mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212 + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ)).getBlock().getMaterial() == Material.air) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688698, mc.thePlayer.posZ, false));
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212, mc.thePlayer.posZ, false));
				}
				timer.reset();
			} else {
				es.stepHeight = 1;
			}
		}
	}
}
