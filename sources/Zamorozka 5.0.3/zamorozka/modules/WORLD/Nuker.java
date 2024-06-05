package zamorozka.modules.WORLD;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPlaceBlock;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventTick;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.BlockUtil;
import zamorozka.ui.BlockUtilis;
import zamorozka.ui.BlockUtils2;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;

public class Nuker extends Module {

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("NukerArmSwing", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NukerPackets", this, 1, 1, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("HorizontalRadius", this, 6, 1, 30, true));
		Zamorozka.settingsManager.rSetting(new Setting("HeightRadius", this, 3, 1, 30, true));
	}

	private int posX, posY, posZ;

	private boolean isRunning;
	private Timer2 timer = new Timer2();

	public Nuker() {
		super("Nucker", 0, Category.WORLD);
	}

	@EventTarget
	public void onPreUpdate(EventPreMotionUpdates e) {
		this.isRunning = false;
		float radius = (float) Zamorozka.settingsManager.getSettingByName("HorizontalRadius").getValDouble();
		float height = (float) Zamorozka.settingsManager.getSettingByName("HeightRadius").getValDouble();
		for (int y = (int) height; y >= -height; --y) {
			for (int x = (int) -radius; x < radius; ++x) {
				for (int z = (int) -radius; z < radius; ++z) {
					this.posX = (int) Math.floor(this.mc.player.posX) + x;
					this.posY = (int) Math.floor(this.mc.player.posY) + y;
					this.posZ = (int) Math.floor(this.mc.player.posZ) + z;
					if (this.mc.player.getDistanceSq(this.mc.player.posX + (double) x, this.mc.player.posY + (double) y, this.mc.player.posZ + (double) z) <= 16.0D) {
						Block block = BlockUtil.getBlock(this.posX, this.posY, this.posZ);
						boolean blockChecks = timer.check(50L);
						Block selected = BlockUtil.getBlock(mc.objectMouseOver.getBlockPos());

						blockChecks = blockChecks && BlockUtil.canSeeBlock(this.posX + 0.5F, this.posY + 0.9f, this.posZ + 0.5F) && !(block instanceof BlockAir);
						blockChecks = blockChecks && (block.getBlockHardness(null, this.mc.world, BlockPos.ORIGIN) != -1.0F || this.mc.playerController.isInCreativeMode());
						if (blockChecks) {
							this.isRunning = true;

							float[] angles = mc.world.faceBlock(this.posX + 0.5F, this.posY + 0.9, this.posZ + 0.5F);
							if (mc.player != null) {
								float sens = getSensitivityMultiplier();
								float yawGCD = (Math.round(angles[0] / sens) * sens);
								float pitchGCD = (Math.round(angles[1] / sens) * sens);
								e.setYaw(yawGCD);
								e.setPitch(pitchGCD);
								mc.player.renderYawOffset = yawGCD;
								mc.player.rotationYawHead = yawGCD;
								mc.player.rotationPitchHead = pitchGCD;
							}
							return;
						}
					}
				}
			}
		}
	}

	private static float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

	@EventTarget
	public void onPostUpdate(EventPostMotionUpdates e) {
		Block block = BlockUtil.getBlock(this.posX, this.posY, this.posZ);
		if (this.isRunning) {
			if (Zamorozka.settingsManager.getSettingByName("NukerArmSwing").getValBoolean()) {
				this.mc.player.swingArm(EnumHand.MAIN_HAND);
			} else {
				mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
			}
			for (int i = 0; i < (int) Zamorozka.settingsManager.getSettingByName("NukerPackets").getValDouble(); i++) {
				mc.getConnection().sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, new BlockPos(this.posX, this.posY, this.posZ), BlockUtil.getFacing(new BlockPos(this.posX, this.posY, this.posZ))));
				mc.getConnection().sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, new BlockPos(this.posX, this.posY, this.posZ), BlockUtil.getFacing(new BlockPos(this.posX, this.posY, this.posZ))));
			}
			if (this.mc.playerController.curBlockDamageMP >= 1.0D)
				timer.reset();
		}
	}
}
