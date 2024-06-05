package xyz.vergoclient.modules.impl.player;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventUpdate;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.ModeSetting;
import xyz.vergoclient.util.main.MovementUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module implements OnEventInterface {

	public NoFall() {
		super("NoFall", Category.PLAYER);
	}

	public ModeSetting mode = new ModeSetting("Mode", "Hypixel", "Hypixel");

	@Override
	public void loadSettings() {
		mode.modes.clear();
		mode.modes.addAll(Arrays.asList("Hypixel"));
		addSettings(mode);
	}


	@Override
	public void onEnable() {
		if(mode.is("Hypixel")) {
			setInfo("Hypixel");
		}
		//ChatUtils.addChatMessage("Module Detected. Proceed with caution.");
	}

	@Override
	public void onEvent(Event e) {
		if (mode.is("Hypixel")) {
			onNoFallHypixelEvent(e);
		}
	}


	private void onNoFallHypixelEvent(Event e) {

		if (e instanceof EventUpdate && e.isPre()) {

			if (!(mc.thePlayer.fallDistance > 2.69) || !e.isPre()) return;
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
			mc.thePlayer.fallDistance = 0.0f;
		}
	}

	private boolean isOverVoid() {

		boolean isOverVoid = true;
		BlockPos block = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1, mc.thePlayer.posZ);

		for (double i = mc.thePlayer.posY + 1; i > 0; i -= 0.5) {

			if (isOverVoid) {

				try {
					if (mc.theWorld.getBlockState(block).getBlock() != Blocks.air) {

						isOverVoid = false;
						break;

					}
				} catch (Exception e) {

				}

			}

			block = block.add(0, -1, 0);

		}

		for (double i = 0; i < 10; i += 0.1) {
			if (MovementUtils.isOnGround(i) && isOverVoid) {
				isOverVoid = false;
				break;
			}
		}

		return isOverVoid;
	}
}
