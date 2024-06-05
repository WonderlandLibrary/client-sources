package zamorozka.modules.WORLD;

import static java.lang.Math.abs;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Spring;

import org.apache.commons.lang3.builder.EqualsExclude;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEventData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventMove;
import zamorozka.event.events.EventPacket;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventReceivePacket;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventRender3D;
import zamorozka.event.events.EventSafeWalk;
import zamorozka.event.events.EventSendPacket;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.RenderEvent3D;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.modules.TRAFFIC.Fly;
import zamorozka.modules.TRAFFIC.Jesus;
import zamorozka.modules.TRAFFIC.LongJump;
import zamorozka.modules.TRAFFIC.SpeedHack;
import zamorozka.modules.TRAFFIC.Sprint;
import zamorozka.modules.ZAMOROZKA.LagCheck;
import zamorozka.notification.Notification;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.AngleUtil;
import zamorozka.ui.BlockUtils;
import zamorozka.ui.CombatUtil;
import zamorozka.ui.GCDFix;
import zamorozka.ui.InventoryUtil;
import zamorozka.ui.KillauraUtil;
import zamorozka.ui.MathUtil;
import zamorozka.ui.MathUtils;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.RayTraceUtil;
import zamorozka.ui.Render3DEvent;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.RotUtils;
import zamorozka.ui.RotationHelper;
import zamorozka.ui.RotationSpoofer;
import zamorozka.ui.RotationUtilis;
import zamorozka.ui.RotationUtils;
import zamorozka.ui.RotationUtils2;
import zamorozka.ui.RotationsNew;
import zamorozka.ui.RotationsUtilis;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;
import zamorozka.ui.ZBlock;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;

public class Scaffold extends Module {

	public static final List<Block> invalidBlocks = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, Blocks.WATER, Blocks.LAVA,
			Blocks.FLOWING_WATER, Blocks.FLOWING_LAVA, Blocks.SAND, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE,
			Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, Blocks.STONE_SLAB, Blocks.WOODEN_SLAB, Blocks.STONE_SLAB2, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.YELLOW_FLOWER,
			Blocks.RED_FLOWER, Blocks.ANVIL, Blocks.GLASS_PANE, Blocks.STAINED_GLASS_PANE, Blocks.IRON_BARS, Blocks.CACTUS, Blocks.LADDER, Blocks.WEB, Blocks.PUMPKIN);
	private final List<Block> validBlocks = Arrays.asList(Blocks.AIR, Blocks.WATER, Blocks.FLOWING_WATER, Blocks.LAVA, Blocks.FLOWING_LAVA);
	private final BlockPos[] blockPositions = new BlockPos[] { new BlockPos(-1, 0, 0), new BlockPos(1, 0, 0), new BlockPos(0, 0, -1), new BlockPos(0, 0, 1), new BlockPos(0, 0, 0) };
	private final EnumFacing[] facings = new EnumFacing[] { EnumFacing.EAST, EnumFacing.WEST, EnumFacing.SOUTH, EnumFacing.NORTH };
	private final Timerr towertimer = new Timerr();
	private float[] angles = new float[2];
	public static BlockData data;
	private int slot;
	public static boolean isSneaking;
	TimerHelper timer;
    private static double dif = 0.5D;

	private Timer2 time = new Timer2();

	private double speed;

	private int item;

	private boolean canPlace;
	private double pitch;
	Object localObject;
	private int itspoof;

	@Override
	public void setup() {
		ArrayList<String> towermode = new ArrayList<>();
		towermode.add("Intave");
		towermode.add("MatrixOLD");
		towermode.add("MatrixOLD2");
		towermode.add("MatrixLatest");
		towermode.add("Under");
		Zamorozka.instance.settingsManager.rSetting(new Setting("BlockRotation Mode", this, "MatrixLatest", towermode));
		ArrayList<String> motion = new ArrayList<>();
		motion.add("Zitter");
		motion.add("MoonWalk");
		motion.add("SlowForward");
		Zamorozka.instance.settingsManager.rSetting(new Setting("MotionJitter Mode", this, "Zitter", motion));
		Zamorozka.settingsManager.rSetting(new Setting("BlockPlaceChance", this, 100, 0, 100, true));
		Zamorozka.settingsManager.rSetting(new Setting("PlaceDelay", this, 0, 0, 500, true));
		Zamorozka.settingsManager.rSetting(new Setting("PlaceDelayRandom", this, 250, 0, 1000, true));
		Zamorozka.settingsManager.rSetting(new Setting("RotationsHide", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("VaildCheck", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ScaffTower", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ScaffPacketSneak", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ScaffSwingHand", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("AutoMotionStop", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SprintOFF", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BlockSafe", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BlockRayTrace", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BlockTraceReduce", this, 0.9, 0.4, 1.5, false));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixTimer", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixTimerValue", this, 1, 0.1, 1, true));
		Zamorozka.settingsManager.rSetting(new Setting("MotionJitter", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("MotionJitterPower", this, 0.008, 0.0001, 0.1, false));
		Zamorozka.settingsManager.rSetting(new Setting("MotionJitterSlow", this, 0.2, 0.05, 1, false));
		Zamorozka.settingsManager.rSetting(new Setting("SlowdownMotion", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("SlowdownMotionValue", this, 0.01, 0.0, 12.0, true));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixPitchValue", this, 82.5, 1, 90, true));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixYawRandom", this, 1.5, 0.1, 5, false));
		Zamorozka.settingsManager.rSetting(new Setting("MatrixPitchRandom", this, 1.5, 0.1, 5, false));
	}

	public Scaffold() {
		super("Scaffold", 0, Category.WORLD);
	}

	@Override
	public void onEnable() {
		String mod = Zamorozka.settingsManager.getSettingByName("BlockRotation Mode").getValString();
		String modeput = Character.toUpperCase(mod.charAt(0)) + mod.substring(1);
		this.setDisplayName("Scaffold " + ChatFormatting.WHITE + modeput);
		NotificationPublisher.queue("Module", "Scaffold was Enabled!", NotificationType.INFO);
		if (!InventoryUtil.doesHotbarHaveBlock()) {
			NotificationPublisher.queue("Scaffold", "You have no blocks in your hotbar!", NotificationType.INFO);
		}
		this.angles[0] = 999.0F;
		this.angles[1] = 999.0F;
		this.towertimer.reset();
		if (Zamorozka.settingsManager.getSettingByName("AutoMotionStop").getValBoolean()) {
			mc.player.motionX *= -1.15;
			mc.player.motionZ *= -1.15;
		}
		this.slot = mc.player.inventory.currentItem;
		this.data = null;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		NotificationPublisher.queue("Module", "Scaffold was Disabled!", NotificationType.INFO);
		isSneaking = false;
		this.data = null;
		mc.timer.timerSpeed = 1f;

		super.onDisable();
	}

	@EventTarget
	public void onUpdate(EventPreMotionUpdates eventUpdate) {
		this.item = findBlock(mc.player.inventoryContainer);
		if (InventoryUtil.doesHotbarHaveBlock()) {
			if (Zamorozka.settingsManager.getSettingByName("ScaffJump").getValBoolean()) {
				if (!(Zamorozka.settingsManager.getSettingByName("ScaffJump").getValBoolean() && mc.gameSettings.keyBindJump.isKeyDown())) {
					if (mc.player.onGround) {
						mc.gameSettings.keyBindJump.pressed = false;
						mc.player.jump();
					}
				}
			}
			if (Zamorozka.settingsManager.getSettingByName("AutoMotionStop").getValBoolean()) {
				if (mc.player.isSprinting() && mc.gameSettings.keyBindForward.isKeyDown() && Zamorozka.settingsManager.getSettingByName("ScaffJump").getValBoolean() && Zamorozka.settingsManager.getSettingByName("SprintOFF").getValBoolean()) {
					mc.player.motionX *= -1.15;
					mc.player.motionZ *= -1.15;
				}
			}
			mc.player.motionX /= 1.05F;
			mc.player.motionZ /= 1.05F;
		}

		String mod = Zamorozka.settingsManager.getSettingByName("BlockRotation Mode").getValString();
		String modeput = Character.toUpperCase(mod.charAt(0)) + mod.substring(1);
		double yDif = 1.0D;
		double posY;
		for (posY = mc.player.posY - 1.0D; posY > 0.0D; posY--) {
			BlockPos blockPos = isSneaking ? new BlockPos(this.mc.player).add(0.0D, -1D, 0.0D).down() : new BlockPos(this.mc.player).add(0.0D, -1D, 0.0D);
			BlockData newData = getBlockData2(blockPos);

			if (newData != null) {
				yDif = mc.player.posY - posY;
				if (yDif <= 7.0D) {
					data = newData;
				}
			}
		}

		/*
		 * if
		 * (Zamorozka.settingsManager.getSettingByName("ScaffLegitSneak").getValBoolean(
		 * )) { BlockPos blockPos = new BlockPos(mc.player.posX, mc.player.posY - 1,
		 * mc.player.posZ); if (this.mc.player.fallDistance <= 2.0F) { if
		 * (this.mc.world.getBlockState(blockPos).getBlock() != Blocks.AIR) { if
		 * (this.mc.gameSettings.keyBindSneak.pressed = false) {
		 * 
		 * } } else {
		 * 
		 * if (this.mc.gameSettings.keyBindSneak.pressed = true) {
		 * 
		 * } } } }
		 */

		if (Zamorozka.settingsManager.getSettingByName("SlowdownMotion").getValBoolean()) {
			double speed2 = Zamorozka.settingsManager.getSettingByName("SlowdownMotionValue").getValDouble();
			double forward = mc.player.movementInput.moveForward;
			double strafe = mc.player.movementInput.moveStrafe;
			float YAW = mc.player.rotationYaw;
			double a = (forward * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f)) + strafe * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f)));
			double b = (forward * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f)) - strafe * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f)));
			double c = Math.abs((a * b));
			double slow = 1 - c * 5;
			speed2 *= slow;
			if (speed2 < 0.05)
				speed2 = 0.05;
			speed2 += AngleUtil.randomFloat(0.01f, -0.01f);
			double more = AngleUtil.randomFloat(0.01f, -0.01f);
			speed2 *= more;
			MovementUtilis.setMotion(speed2);
		}

		if (Zamorozka.settingsManager.getSettingByName("MotionJitter").getValBoolean()) {
			String mo = Zamorozka.settingsManager.getSettingByName("MotionJitter Mode").getValString();
			if (mo.equalsIgnoreCase("Zitter")) {
				float jj = (float) Zamorozka.settingsManager.getSettingByName("MotionJitterPower").getValDouble();
				double jitter = AngleUtil.randomFloat(jj, -jj);
				mc.player.motionX *= mc.player.isPotionActive(MobEffects.SPEED) && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() ? Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble() / 1.37
						: Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble();
				mc.player.motionZ *= mc.player.isPotionActive(MobEffects.SPEED) && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() ? Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble() / 1.37
						: Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble();
				mc.player.motionX += jitter;
				mc.player.motionZ -= jitter;
			}

			if (mo.equalsIgnoreCase("SlowForward")) {
				if (mc.gameSettings.keyBindForward.isKeyDown()) {
					double dir = Math.toRadians(mc.player.rotationYaw);
					mc.player.motionX += -Math.sin(dir) * -0.05;
					mc.player.motionZ += Math.cos(dir) * -0.05;
				}
			}

			if (mo.equalsIgnoreCase("MoonWalk")) {
				mc.player.motionX *= mc.player.isPotionActive(MobEffects.SPEED) && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() ? Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble() / 1.37
						: Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble();
				mc.player.motionZ *= mc.player.isPotionActive(MobEffects.SPEED) && mc.player.onGround && !mc.gameSettings.keyBindJump.isKeyDown() ? Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble() / 1.37
						: Zamorozka.settingsManager.getSettingByName("MotionJitterSlow").getValDouble();
				if (mc.player.ticksExisted % 7 == 0) {
					double dir = Math.toRadians(mc.player.rotationYaw - 90);
					mc.player.motionX += -Math.sin(dir) * 0.03;
					mc.player.motionZ += Math.cos(dir) * 0.03;
				}
				if (mc.player.ticksExisted % 11 == 0) {
					double dir = Math.toRadians(mc.player.rotationYaw + 90);
					mc.player.motionX += -Math.sin(dir) * 0.003;
					mc.player.motionZ += Math.cos(dir) * 0.003;
					double dir1 = Math.toRadians(mc.player.rotationYaw + 90);
					mc.player.setPosition(mc.player.posX += -Math.sin(dir1) * 0.072, mc.player.posY, mc.player.posZ);
					mc.player.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ += Math.cos(dir1) * 0.072);
				}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("SprintOFF").getValBoolean() && getState()) {
			mc.player.setSprinting(false);
		}

		if (data != null && slot != -1) {
			int last;

			BlockPos pos = data.pos;

			EnumFacing facing = data.face;
			Block block = mc.world.getBlockState(pos.offset(data.face)).getBlock();
			Vec3d hitVec = getVec3(data);
			if (mod.equalsIgnoreCase("MatrixOLD")) {
				float zz = (float) Zamorozka.settingsManager.getSettingByName("MatrixPitchValue").getValDouble();
				float sens = getSensitivityMultiplier2();
				float pitchGCD = (Math.round(zz / sens) * sens);
				eventUpdate.setPitch(pitchGCD);
				if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
					mc.player.rotationPitchHead = (pitchGCD);
				}
				if (this.mc.gameSettings.keyBindForward.pressed) {
					eventUpdate.setYaw(Math.round(this.mc.player.rotationYaw >= 180.0f ? this.mc.player.rotationYaw - 180.0f : this.mc.player.rotationYaw + 180.0f / sens) * sens);
					if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
						mc.player.renderYawOffset = this.mc.player.rotationYaw >= 180.0f ? this.mc.player.rotationYaw - 180.0f : this.mc.player.rotationYaw + 180.0f;
						mc.player.rotationYawHead = this.mc.player.rotationYaw >= 180.0f ? this.mc.player.rotationYaw - 180.0f : this.mc.player.rotationYaw + 180.0f;
					}
				} else if (this.mc.gameSettings.keyBindBack.pressed) {
					eventUpdate.setYaw(Math.round(this.mc.player.rotationYaw / sens) * sens);
					if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
						mc.player.renderYawOffset = this.mc.player.rotationYaw;
						mc.player.rotationYawHead = this.mc.player.rotationYaw;
					}
				} else if (this.mc.gameSettings.keyBindLeft.pressed) {
					eventUpdate.setYaw(Math.round(this.mc.player.rotationYaw + 90.0f / sens) * sens);
					if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
						mc.player.renderYawOffset = this.mc.player.rotationYaw + 90.0f;
						mc.player.rotationYawHead = this.mc.player.rotationYaw + 90.0f;
					}
				} else if (this.mc.gameSettings.keyBindRight.pressed) {
					eventUpdate.setYaw(Math.round(this.mc.player.rotationYaw - 90.0f / sens) * sens);
					if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
						mc.player.renderYawOffset = this.mc.player.rotationYaw - 90.0f;
						mc.player.rotationYawHead = this.mc.player.rotationYaw - 90.0f;
					}
				}

			}

			if (mod.equalsIgnoreCase("MatrixOLD2")) {
				float yaw = RotUtils.getRotationsNeededBlock(pos.getX(), pos.getY(), pos.getZ())[0] + MathUtil.getRandomInRange(1, -2);
				float yawGCD = GCDFix.getFixedRotation(yaw);
				float zz = (float) Zamorozka.settingsManager.getSettingByName("MatrixPitchValue").getValDouble();
				float zzGCD = GCDFix.getFixedRotation(zz);
				eventUpdate.setPitch(zzGCD);
				eventUpdate.setYaw(yawGCD);
				if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
					mc.player.rotationPitchHead = zzGCD;
					mc.player.renderYawOffset = yawGCD;
					mc.player.rotationYawHead = yawGCD;
				}
			}

			if (mod.equalsIgnoreCase("MatrixLatest")) {
				float yawRand = (float) Zamorozka.settingsManager.getSettingByName("MatrixYawRandom").getValDouble();
				float pitchRand = (float) Zamorozka.settingsManager.getSettingByName("MatrixPitchRandom").getValDouble();
				float[] rots = RotationUtils.getNeededFacing(hitVec);
				float yawGCD = (rots[0] + (float) MiscUtils.getDoubleRandom(-yawRand, yawRand));
				float pitchGCD = (rots[1] + (float) MiscUtils.getDoubleRandom(-pitchRand, pitchRand));
				eventUpdate.setYaw(yawGCD);
				eventUpdate.setPitch(pitchGCD);
				if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
					mc.player.rotationYawHead = yawGCD;
					mc.player.renderYawOffset = yawGCD;
					mc.player.rotationPitchHead = pitchGCD;
				}
			}

			if (mod.equalsIgnoreCase("Intave")) {
				float x = getSensitivityMultiplier2();
				float[] rot = RotUtils.getIntaveRots(pos, facing);
				float f = getSensitivityMultiplier2();
				eventUpdate.setPitch(82.500114F);
				eventUpdate.setYaw((float) (rot[0] + MiscUtils.getDoubleRandom(-0.1D, 0.1D)));
				if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
					mc.player.rotationPitchHead = 82.500114F;
					mc.player.rotationYawHead = (float) (rot[0] + MiscUtils.getDoubleRandom(-0.1D, 0.1D));
					mc.player.renderYawOffset = (float) (rot[0] + MiscUtils.getDoubleRandom(-0.1D, 0.1D));
				}
			}

			if (mod.equalsIgnoreCase("Under")) {
				eventUpdate.setPitch(89.44F);
				if (!Zamorozka.settingsManager.getSettingByName("RotationsHide").getValBoolean()) {
					mc.player.rotationPitchHead = 89.44F;
				}
			}

			if (!this.validBlocks.contains(block) || isBlockUnder(yDif)) {
				return;
			}
			if (mc.gameSettings.keyBindJump.isKeyDown() && Zamorozka.settingsManager.getSettingByName("ScaffTower").getValBoolean()) {
				if (mc.player.onGround) {
					mc.player.jump();
				}
			}
		}
	}

	@EventTarget
	public void onSafeWalk(EventSafeWalk event) {
		if (Zamorozka.settingsManager.getSettingByName("BlockSafe").getValBoolean() && !isSneaking) {
			event.setCancelled(mc.player.onGround);
		}
		localObject = new BlockPos(mc.player.posX, mc.player.posY - 0.5D, mc.player.posZ);
		if (InventoryUtil.doesHotbarHaveBlock()) {
			int slot = -1;
			int blockCount = 0;
			for (int i = 0; i < 9; i++) {
				ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
				if (itemStack != null) {
					int stackSize = itemStack.stackSize;

					if (isValidItem(itemStack.getItem())) {
						slot = i;
					}
				}
			}
			if (data != null && slot != -1) {
				int last;
				BlockPos pos = data.pos;
				EnumFacing facing = data.face;
				Block block = mc.world.getBlockState(pos.offset(data.face)).getBlock();
				Vec3d hitVec = getVec3(data);
				float chance3 = (float) Zamorozka.settingsManager.getSettingByName("BlockPlaceChance").getValDouble();
				float delay1 = (float) Zamorozka.settingsManager.getSettingByName("PlaceDelay").getValDouble();
				float delay2 = (float) Zamorozka.settingsManager.getSettingByName("PlaceDelayRandom").getValDouble();
				if (Zamorozka.settingsManager.getSettingByName("MatrixTimer").getValBoolean()) {
					float fl = (float) Zamorozka.settingsManager.getSettingByName("MatrixTimerValue").getValDouble();
					mc.timer.timerSpeed = fl;
				}

				if (itspoof != -1) {
					ItemSpoof();
				}
				if (time.check((float) (delay1 + AngleUtil.randomFloat(0, delay2)))) {
					boolean look = RotationSpoofer.isLookingAt1(hitVec);
					if (!look && (Zamorozka.settingsManager.getSettingByName("BlockRayTrace").getValBoolean()))
						return;
					if (!Zamorozka.settingsManager.getSettingByName("ScaffJump").getValBoolean() && Zamorozka.settingsManager.getSettingByName("ScaffPacketSneak").getValBoolean()) {
						if (allowPlacing()) {
							if (getState()) {
								mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.START_SNEAKING));
							}
						}
					}

					/*
					 * last = mc.player.inventory.currentItem; mc.player.inventory.currentItem =
					 * slot;
					 */

					if (Zamorozka.settingsManager.getSettingByName("VaildCheck").getValBoolean()) {
						if (!(validateDataRange(data) && validateReplaceable(data))) {
							return;
						}
					}
					if (AngleUtil.randomFloat(0.0f, 100.0f) <= chance3) {
						mc.playerController.processRightClickBlock(mc.player, mc.world, pos, data.face, hitVec, EnumHand.MAIN_HAND);
						if (Zamorozka.settingsManager.getSettingByName("ScaffSwingHand").getValBoolean()) {
							mc.player.swingArm(EnumHand.MAIN_HAND);
						} else {
							mc.getConnection().sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
						}
					}

					// mc.getConnection().sendPacket(new CPacketHeldItemChange(last));
					// mc.playerController.updateController();

					if (!Zamorozka.settingsManager.getSettingByName("ScaffJump").getValBoolean() && Zamorozka.settingsManager.getSettingByName("ScaffPacketSneak").getValBoolean()) {
						if (allowPlacing()) {
							if (getState()) {
								mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, Action.STOP_SNEAKING));
							}
						}
					}
					time.reset();
					// mc.player.inventory.currentItem = last;
				}
			}
		}
	}
	
	public boolean rayTrace(float yaw, float pitch) {
		Vec3d vec3 = mc.player.getPositionEyes(1.0f);
		Vec3d vec31 = RotationHelper.getVectorForRotation2(yaw, pitch);
		Vec3d vec32 = vec3.addVector(vec31.xCoord * 5, vec31.yCoord * 5, vec31.zCoord * 5);

		RayTraceResult result = mc.world.rayTraceBlocks(vec3, vec32, false);

		return result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && ((BlockPos) blockPositions[0]).equals(result.getBlockPos());
	}

	private boolean validateDataRange(BlockData data) {
		Vec3d hitVec = getVec3(data);
		EntityPlayerSP entity = mc.player;
		double x = (hitVec.xCoord - entity.posX);
		double y = (hitVec.yCoord - (entity.posY + entity.getEyeHeight()));
		double z = (hitVec.zCoord - entity.posZ);
		return Math.sqrt(x * x + y * y + z * z) <= 4.0D;
	}

	private boolean validateReplaceable(BlockData data) {
		BlockPos pos = data.pos.offset(data.face);
		World world = mc.world;
		return world.getBlockState(pos).getBlock().isReplaceable(world, pos);
	}

	private boolean allowPlacing() {
		// double d = 0.024D;
		BlockPos localBlockPos1 = new BlockPos(mc.player.posX - 0.024D, mc.player.posY - 0.5D, mc.player.posZ - 0.024D);
		BlockPos localBlockPos2 = new BlockPos(mc.player.posX - 0.024D, mc.player.posY - 0.5D, mc.player.posZ + 0.024D);
		BlockPos localBlockPos3 = new BlockPos(mc.player.posX + 0.024D, mc.player.posY - 0.5D, mc.player.posZ + 0.024D);
		BlockPos localBlockPos4 = new BlockPos(mc.player.posX + 0.024D, mc.player.posY - 0.5D, mc.player.posZ - 0.024D);
		return (mc.world.getBlockState(localBlockPos1).getBlock() == Blocks.AIR) && (mc.world.getBlockState(localBlockPos2).getBlock() == Blocks.AIR) && (mc.world.getBlockState(localBlockPos3).getBlock() == Blocks.AIR)
				&& (mc.world.getBlockState(localBlockPos4).getBlock() == Blocks.AIR);
	}

	private boolean allowRotation() {
		double d = 0.1D;
		BlockPos localBlockPos1 = new BlockPos(mc.player.posX - 0.1D, mc.player.posY - 0.5D, mc.player.posZ - 0.1D);
		BlockPos localBlockPos2 = new BlockPos(mc.player.posX - 0.1D, mc.player.posY - 0.5D, mc.player.posZ + 0.1D);
		BlockPos localBlockPos3 = new BlockPos(mc.player.posX + 0.1D, mc.player.posY - 0.5D, mc.player.posZ + 0.1D);
		BlockPos localBlockPos4 = new BlockPos(mc.player.posX + 0.1D, mc.player.posY - 0.5D, mc.player.posZ - 0.1D);
		return (mc.world.getBlockState(localBlockPos1).getBlock() == Blocks.AIR) && (mc.world.getBlockState(localBlockPos2).getBlock() == Blocks.AIR) && (mc.world.getBlockState(localBlockPos3).getBlock() == Blocks.AIR)
				&& (mc.world.getBlockState(localBlockPos4).getBlock() == Blocks.AIR);
	}

	static float getSensitivityMultiplier2() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

	private void ItemSpoof() {
		if (itspoof != -1) {
			if (itspoof != mc.player.inventory.currentItem) {

			}
		}
		ItemStack is = new ItemStack(Item.getItemById(261));
		try {
			for (int i = 36; i < 45; i++) {
				int slot1 = i - 36;

				if (!mc.player.inventoryContainer.canAddItemToSlot(mc.player.inventoryContainer.getSlot(i), is, true) && mc.player.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock
						&& mc.player.inventoryContainer.getSlot(i).getStack() != null) {
					if (isValidItem(mc.player.inventoryContainer.getSlot(i).getStack().getItem()) && mc.player.inventoryContainer.getSlot(i).getStack().stackSize != 0) {
						if (mc.player.inventory.currentItem != slot1) {
							if (itspoof != slot1) {

								CPacketHeldItemChange p = new CPacketHeldItemChange(slot1);
								itspoof = slot1;
								mc.getConnection().sendPacket(p);
								mc.player.inventory.currentItem = slot1;
								mc.playerController.updateController();
							} else {
								mc.player.inventory.currentItem = slot1;
								mc.playerController.updateController();
							}
						} else {
							mc.player.inventory.currentItem = slot1;
							mc.playerController.updateController();

						}
						break;

					}
				}
			}
		} catch (Exception e) {
		}
	}

	float to1801(float ang) {
		float value = ang % 360.0f;

		if (value >= 180.0f) {
			value -= 360.0f;
		}

		if (value < -180.0f) {
			value += 360.0f;
		}

		return value;
	}

	@EventTarget
	public void onRender(EventRender3D event) {
		if (data != null && slot != -1) {
			double x = mc.player.posX;
			double y = mc.player.posY;
			double z = mc.player.posZ;
			double yaw = mc.player.rotationYaw * 0.017453292;
			BlockPos below = new BlockPos(x - Math.sin(yaw), y - 1, z + Math.cos(yaw));
			Color color = new Color(255, 73, 73, 255);

			if (this.getBlockCount() >= 64 && 128 > this.getBlockCount()) {
				color = new Color(255, 231, 77, 255);
			} else if (this.getBlockCount() >= 128) {
				color = new Color(79, 255, 79, 255);
			}
			RenderUtils2.blockEsp(below, color, 1, 1);
		}
	}

	@EventTarget
	public void onRender2D(EventRender2D render) {
		float width = render.getResolution().getScaledWidth();
		float height = render.getResolution().getScaledHeight();
		int color = new Color(255, 73, 73, 255).getRGB();

		if (this.getBlockCount() >= 64 && 128 > this.getBlockCount()) {
			color = new Color(255, 231, 77, 255).getRGB();
		} else if (this.getBlockCount() >= 128) {
			color = new Color(79, 255, 79, 255).getRGB();
		}

		GlStateManager.enableBlend();
		CFontRenderer font = Fonts.comfortaa20;
		RenderingUtils.drawOutlinedString(this.getBlockCount() + " ", font, width / 2F + 10, height / 2 - font.getStringHeight(this.getBlockCount() + " \247fBlocks") / 2F, color);
	}

	@EventTarget
	public void onPacket(EventPacket event) {
		Packet<?> p = event.getPacket();
		if (event.isIncoming()) {
			if (p instanceof SPacketPlayerPosLook && mc.player != null && mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
				mc.player.onGround = false;
				mc.player.motionX *= 0;
				mc.player.motionZ *= 0;
				mc.player.jumpMovementFactor = 0;
				if (Zamorozka.moduleManager.getModule(Scaffold.class).getState() && Zamorozka.moduleManager.getModule(LagCheck.class).getState()) {
					Zamorozka.moduleManager.getModule(Scaffold.class).toggle();
					NotificationPublisher.queue("LagBack", "Scaffold was lagback!", NotificationType.WARNING);
					isEnabled = false;
					ModuleManager.getModule(Scaffold.class).setState(false);
					isEnabled = false;
					ModuleManager.getModule(Scaffold.class).setState(false);
				}
			}
		}
	}

	private int getBlockCount() {
		int blockCount = 0;

		for (int i = 0; i < 45; ++i) {
			if (!mc.player.inventoryContainer.getSlot(i).getHasStack()) {
				continue;
			}

			ItemStack is = mc.player.inventoryContainer.getSlot(i).getStack();
			Item item = is.getItem();

			if (!isValidItem(item)) {
				continue;
			}
			blockCount += is.stackSize;
		}
		return blockCount;
	}

	private boolean isBlockUnder(double yOffset) {
		return !this.validBlocks.contains(mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - yOffset, mc.player.posZ)).getBlock());
	}

	private boolean isValidItem(Item item) {
		if (item instanceof ItemBlock) {
			ItemBlock iBlock = (ItemBlock) item;
			Block block = iBlock.getBlock();
			return !this.invalidBlocks.contains(block);
		}
		return false;
	}

	// BlockData
	public BlockData getBlockData(BlockPos pos, int i) {
		return (this.mc.world.getBlockState(pos.add(0, 0, i)).getBlock() != Blocks.AIR) ? new BlockData(pos.add(0, 0, i), EnumFacing.NORTH)
				: ((this.mc.world.getBlockState(pos.add(0, 0, -i)).getBlock() != Blocks.AIR) ? new BlockData(pos.add(0, 0, -i), EnumFacing.SOUTH)
						: ((this.mc.world.getBlockState(pos.add(i, 0, 0)).getBlock() != Blocks.AIR) ? new BlockData(pos.add(i, 0, 0), EnumFacing.WEST)
								: ((this.mc.world.getBlockState(pos.add(-i, 0, 0)).getBlock() != Blocks.AIR) ? new BlockData(pos.add(-i, 0, 0), EnumFacing.EAST)
										: ((this.mc.world.getBlockState(pos.add(0, -i, 0)).getBlock() != Blocks.AIR) ? new BlockData(pos.add(0, -i, 0), EnumFacing.UP) : null))));
	}

	public final int findBlock(Container paramContainer) {
		for (int i = 0; i < 9; i++) {
			ItemStack localItemStack = paramContainer.getSlot(i | 0x24).getStack();
			if ((localItemStack != null) && ((localItemStack.getItem() instanceof ItemBlock))) {
				return i;
			}
		}
		return -1;
	}

	private int findBlockValue(Container paramContainer) {
		int i = 0;
		for (int j = 0; j < 9; j++) {
			ItemStack localItemStack = paramContainer.getSlot(j | 0x24).getStack();
			if ((localItemStack != null) && ((localItemStack.getItem() instanceof ItemBlock))) {
				i |= localItemStack.stackSize;
			}
		}
		return i;
	}

	public BlockData getBlockData2(BlockPos pos) {
		BlockData blockData = null;
		int i = 0;
		while (blockData == null) {
			if (i >= 2) {
				break;
			}
			if (!this.isBlockPosAir(pos.add(0, 0, 1))) {
				blockData = new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(0, 0, -1))) {
				blockData = new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 0, 0))) {
				blockData = new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 0, 0))) {
				blockData = new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
				break;
			}
			if (Zamorozka.settingsManager.getSettingByName("ScaffTower").getValBoolean() && mc.gameSettings.keyBindJump.isKeyDown()) {
				if (!this.isBlockPosAir(pos.add(0, -1, 0))) {
					blockData = new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
					break;
				}
			}

			if (!this.isBlockPosAir(pos.add(0, 1, 0)) && isSneaking) {
				blockData = new BlockData(pos.add(0, 1, 0), EnumFacing.DOWN);
				break;
			}
			if (!this.isBlockPosAir(pos.add(0, 1, 1)) && isSneaking) {
				blockData = new BlockData(pos.add(0, 1, 1), EnumFacing.DOWN);
				break;
			}
			if (!this.isBlockPosAir(pos.add(0, 1, -1)) && isSneaking) {
				blockData = new BlockData(pos.add(0, 1, -1), EnumFacing.DOWN);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 1, 0)) && isSneaking) {
				blockData = new BlockData(pos.add(1, 1, 0), EnumFacing.DOWN);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 1, 0)) && isSneaking) {
				blockData = new BlockData(pos.add(-1, 1, 0), EnumFacing.DOWN);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 0, 1))) {
				blockData = new BlockData(pos.add(1, 0, 1), EnumFacing.NORTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 0, -1))) {
				blockData = new BlockData(pos.add(-1, 0, -1), EnumFacing.SOUTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 0, 1))) {
				blockData = new BlockData(pos.add(1, 0, 1), EnumFacing.WEST);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 0, -1))) {
				blockData = new BlockData(pos.add(-1, 0, -1), EnumFacing.EAST);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 0, 1))) {
				blockData = new BlockData(pos.add(-1, 0, 1), EnumFacing.NORTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 0, -1))) {
				blockData = new BlockData(pos.add(1, 0, -1), EnumFacing.SOUTH);
				break;
			}
			if (!this.isBlockPosAir(pos.add(1, 0, -1))) {
				blockData = new BlockData(pos.add(1, 0, -1), EnumFacing.WEST);
				break;
			}
			if (!this.isBlockPosAir(pos.add(-1, 0, 1))) {
				blockData = new BlockData(pos.add(-1, 0, 1), EnumFacing.EAST);
				break;
			}
			pos = pos.down();
			++i;
		}
		return blockData;
	}

	private Vec3d getVec3(BlockData data) {
		BlockPos pos = data.pos;
		EnumFacing face = data.face;
		double x = pos.getX() + 0.5D;
		double y = pos.getY() + 0.5D;
		double z = pos.getZ() + 0.5D;
		if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
			x += 0.3;
			z += 0.3;
		} else {
			y += 0.5;
		}
		if (face == EnumFacing.WEST || face == EnumFacing.EAST)
			z += 0.15;
		if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH)
			x += 0.15;
		return new Vec3d(x, y, z);
	}

	public Vec3d getPositionByFace(BlockPos position, EnumFacing facing) {
		Vec3d offset = new Vec3d((double) facing.getDirectionVec().getX() / 2.0, (double) facing.getDirectionVec().getY() / 2.0, (double) facing.getDirectionVec().getZ() / 2.0);
		Vec3d point = new Vec3d((double) position.getX() + 0.5, (double) position.getY() + 0.5, (double) position.getZ() + 0.5);
		return point.add(offset);
	}

	public boolean isBlockPosAir(final BlockPos blockPos) {
		return this.getBlockByPos(blockPos) == Blocks.AIR || this.getBlockByPos(blockPos) instanceof BlockLiquid;
	}

	public Block getBlockByPos(final BlockPos blockPos) {
		return mc.world.getBlockState(blockPos).getBlock();
	}

	private double randomNumber(double max, double min) {
		return Math.random() * (max - min) + min;
	}

	private static class BlockData {
		public final BlockPos pos;
		public final EnumFacing face;

		private BlockData(BlockPos pos, EnumFacing face) {
			this.pos = pos;
			this.face = face;
		}
	}
}
