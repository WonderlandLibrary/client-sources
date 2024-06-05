package zamorozka.modules.WORLD;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerDigging.Action;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventBlockBreaking;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MathUtils;
import zamorozka.ui.RenderUtil;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;

public class Fucker extends Module {

	private int xPos;
	private int yPos;
	private int zPos;
	private static int radius = 4;
	private EnumFacing side = EnumFacing.NORTH;

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("SendPosPacket", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ArmHandSwing", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("MoreDiggingPackets", this, 8, 1, 50, true));
	}

	public Fucker() {
		super("BedFucker", 0, Category.WORLD);
	}

	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		int pc = (int) Zamorozka.settingsManager.getSettingByName("MoreDiggingPackets").getValDouble();
		for (int x = -radius; x < radius; x++) {
			for (int y = radius; y > -radius; y--) {
				for (int z = -radius; z < radius; z++) {
					this.xPos = (int) mc.player.posX + x;
					this.yPos = (int) mc.player.posY + y;
					this.zPos = (int) mc.player.posZ + z;
					BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
					Block block = mc.world.getBlockState(blockPos).getBlock();

					if (Block.getIdFromBlock(block) != 26)
						continue;
					if (!(block == null && blockPos == null)) {
						float[] angles = mc.world.faceBlock(blockPos.getX() + 0.5, blockPos.getY() + 0.5,
								blockPos.getZ() + 0.5);
						float sens = getSensitivityMultiplier();
						float yaw = angles[0] + MathUtils.getRandomInRange(1, -3);
						float pitch = angles[1] + MathUtils.getRandomInRange(1, -3);
						float yawGCD = (Math.round(yaw / sens) * sens);
						float pitchGCD = (Math.round(pitch / sens) * sens);
						event.setYaw(yawGCD);
						event.setPitch(pitchGCD);
						mc.player.renderYawOffset = yawGCD;
						mc.player.rotationYawHead = yawGCD;
						mc.player.rotationPitchHead = pitchGCD;
						for (int i = 0; i < pc; i++) {
							mc.playerController.onPlayerDamageBlock(blockPos, side);
							mc.player.connection
									.sendPacket(new CPacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, side));
							mc.player.connection
									.sendPacket(new CPacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, side));
							if (Zamorozka.settingsManager.getSettingByName("SendPosPacket").getValBoolean()) {
								mc.player.setPositionAndUpdate(blockPos.getX() + 1, blockPos.getY() - 1,
										blockPos.getZ() + 0.5);
							}
							if (Zamorozka.settingsManager.getSettingByName("ArmHandSwing").getValBoolean()) {
								mc.player.swingArm(EnumHand.MAIN_HAND);
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onRender() {
		int playerX = (int) this.mc.player.posX;
		int playerZ = (int) this.mc.player.posZ;
		int playerY = (int) this.mc.player.posY;
		int range = (int) 4;
		for (int y = playerY - range; y <= playerY + range; y++) {
			for (int x = playerX - range; x <= playerX + range; x++) {
				for (int z = playerZ - range; z <= playerZ + range; z++) {
					BlockPos pos = new BlockPos(x, y, z);
					if (this.mc.world.getBlockState(pos).getBlock() == Blocks.BED) {
						if (pos != null && mc.world.getBlockState(pos).getBlock() != Blocks.AIR && !nullCheck()) {
							RenderUtils2.blockEspBox(pos, 255, 0, 0);
						}
					}
				}
			}
		}
		super.onRender();
	}

	private static float getSensitivityMultiplier() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

}