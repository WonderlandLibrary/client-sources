package zamorozka.modules.PLAYER;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventEntityCollision;
import zamorozka.event.events.EventMove2;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventTick;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockUtils;

public class Phase extends Module {

	@Override
	public void setup() {
		Zamorozka.instance.settingsManager.rSetting(new Setting("PhaseStrength", this, 0.2, 0.01, 0.4, false));
	}

	public Phase() {
		super("Phase", 0, Category.WORLD);
	}

	@Override
	public void onDisable() {
		mc.player.stepHeight = 0.625f;
		super.onDisable();
	}

	@EventTarget
	public void onMotion(EventPreMotionUpdates event) {
		double delay = Zamorozka.settingsManager.getSettingByName("PhaseStrength").getValDouble();
		mc.player.stepHeight = 0;
		double mx = Math.cos(Math.toRadians(mc.player.rotationYaw + 90.0F));
		double mz = Math.sin(Math.toRadians(mc.player.rotationYaw + 90.0F));
		double x = (double) mc.player.movementInput.moveForward * delay * mx + (double) mc.player.movementInput.moveStrafe * delay * mz;
		double z = (double) mc.player.movementInput.moveForward * delay * mz - (double) mc.player.movementInput.moveStrafe * delay * mx;

		if (mc.player.isCollidedHorizontally && !mc.player.isOnLadder()) {
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX + x, mc.player.posY, mc.player.posZ + z, false));
			mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY + 3, mc.player.posZ, false));
			mc.player.setPosition(mc.player.posX + x, mc.player.posY, mc.player.posZ + z);
		}
	}
}