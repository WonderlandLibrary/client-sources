package us.loki.legit.modules.impl.Other;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import us.loki.legit.modules.*;

public class FPSBoost extends Module {

	public FPSBoost() {
		super("FPSBoost(test)","FPSBoost(test)", Keyboard.KEY_NONE, Category.OTHER);
	}

	String[] lastServer = { "none" };

	@Override
	public void onUpdate() {
			mc.gameSettings.ofRain = 3;
			mc.gameSettings.clouds = false;
			mc.gameSettings.ofFastRender = true;
			mc.gameSettings.ofSmoothFps = true;
			mc.gameSettings.fancyGraphics = false;
			mc.gameSettings.ofVoidParticles = false;
			mc.gameSettings.renderDistanceChunks = 7;
			mc.gameSettings.lastServer = (lastServer[0]);
			mc.gameSettings.ofAnimatedExplosion = false;
			mc.gameSettings.ofDrippingWaterLava = false;
		if (mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
				(int) mc.thePlayer.posZ) == Blocks.ice && (mc.gameSettings.keyBindBack.pressed)
				|| mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
						(int) mc.thePlayer.posZ) == Blocks.packed_ice && (mc.gameSettings.keyBindBack.pressed)
				|| mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
						(int) mc.thePlayer.posZ) == Blocks.ice && (mc.gameSettings.keyBindLeft.pressed)
				|| mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
						(int) mc.thePlayer.posZ) == Blocks.packed_ice && (mc.gameSettings.keyBindLeft.pressed)
				|| mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
						(int) mc.thePlayer.posZ) == Blocks.ice && (mc.gameSettings.keyBindRight.pressed)
				|| mc.theWorld.getBlock((int) mc.thePlayer.posX, (int) mc.thePlayer.posY - 1,
						(int) mc.thePlayer.posZ) == Blocks.packed_ice && (mc.gameSettings.keyBindRight.pressed)) {
			mc.thePlayer.motionX *= 0.851;
			mc.thePlayer.motionZ *= 0.851;
		}
		super.onUpdate();
	}

}
