package net.augustus.modules.world;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.sun.javafx.geom.Vec2d;

import net.augustus.Augustus;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventJump;
import net.augustus.events.EventMove;
import net.augustus.events.EventPostMouseOver;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventSaveWalk;
import net.augustus.events.EventSendPacket;
import net.augustus.events.EventSilentMove;
import net.augustus.events.EventSwingItemClientSide;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.RayTraceUtil;
import net.augustus.utils.RenderUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class BlockFly extends Module {
	public final DoubleValue yawSpeed = new DoubleValue(2, "YawSpeed", this, 60.0, 0.0, 180.0, 0);
	public final DoubleValue pitchSpeed = new DoubleValue(3, "PitchSpeed", this, 60.0, 0.0, 180.0, 0);
	public final DoubleValue expand = new DoubleValue(136, "Expand", this, 0.0, 0.0, 3.0, 0);
	public final BooleanValue snap = new BooleanValue(52789782, "Snap", this, false);
	public final BooleanValue resetRotation = new BooleanValue(52789782, "ResetRotation", this, false);

	public final BooleanValue dynamicFov = new BooleanValue(2643, "NoDynamicFov", this, false);
	public final BooleanValue rayCast = new BooleanValue(6, "RayCast", this, false);
	public final BooleanValue playerYaw = new BooleanValue(9, "PlayerYaw", this, false);
	public final BooleanValue blockSafe = new BooleanValue(25, "BlockSafe", this, false);
	public final BooleanValue moonWalk = new BooleanValue(21, "MoonWalk", this, false);
	public final BooleanValue godBridge = new BooleanValue(6234, "GodBridge", this, false);
	public final BooleanValue telly = new BooleanValue(534789, "Telly(Test)", this, false);
	public final BooleanValue latestRotate = new BooleanValue(7, "LatestRotate", this, false);
	public final BooleanValue latestPlace = new BooleanValue(24, "LatestPlace", this, false);
	public final BooleanValue predict = new BooleanValue(9, "Predict", this, false);
	public final DoubleValue backupTicks = new DoubleValue(8, "Backup", this, 1.0, 0.0, 3.0, 0);
	public final BooleanValue adStrafe = new BooleanValue(13, "AdStrafe", this, true);
	public final BooleanValue adStrafeLegit = new BooleanValue(19, "AdStrafeLegit", this, true);
	public final BooleanValue sprint = new BooleanValue(4, "Sprint", this, false);
	public final BooleanValue moveFix = new BooleanValue(5, "MoveFix", this, false);
	public final BooleanValue flyTest = new BooleanValue(42069, "FlyTest", this, false);
	public final BooleanValue spamClick = new BooleanValue(20, "SpamClick", this, false);
	public final BooleanValue rightClickMouse = new BooleanValue(854930, "RightClickMouse", this, false);
	public final DoubleValue spamClickDelay = new DoubleValue(22, "ClickDelay", this, 0.0, 0.0, 200.0, 0);
	public final BooleanValue intaveHit = new BooleanValue(23, "IntaveHit", this, false);
	public final BooleanValue rotateToBlock = new BooleanValue(10, "Rotate", this, true);
	public final BooleanValue correctSide = new BooleanValue(12, "CorrectSide", this, true);
	public final BooleanValue sameY = new BooleanValue(18, "SameY", this, false);
	public final BooleanValue autoJump = new BooleanValue(18, "AutoJump", this, false);
	public final BooleanValue esp = new BooleanValue(26, "ESP", this, true);
	public final StringValue towerMode = new StringValue(1234, "TowerMode", this, "None",
			new String[] { "None", "Vanilla", "LowHop", "FastJump", "NCP", "AAC", "Grim", "Hycraft" });
	public final StringValue rotationMode = new StringValue(45645445, "RotationMode", this, "Normal", new String[] { "Normal", "GodBridge"});
	public final BooleanValue towerMove = new BooleanValue(25, "TowerMove", this, false);
	public final BooleanValue noSwing = new BooleanValue(25, "NoSwing", this, false);
	public final BooleanValue safewalk = new BooleanValue(7622, "SafeWalk", this, false);
	public final BooleanValue startSneak = new BooleanValue(29, "StartSneak", this, false);
	public final BooleanValue sneak = new BooleanValue(13, "Sneak", this, false);
	public final BooleanValue sneakOnPlace = new BooleanValue(17, "SneakOnPlace", this, false);
	public final DoubleValue sneakTicks = new DoubleValue(27, "SneakTicks", this, 1.0, 1.0, 10.0, 0);
	public final DoubleValue sneakBlocks = new DoubleValue(28, "SneakBlocksF", this, 1.0, 1.0, 15.0, 0);
	public final DoubleValue sneakBlocksDiagonal = new DoubleValue(31, "SneakBlocksD", this, 1.0, 1.0, 15.0, 0);
	public final BooleanValue sneakDelayBool = new BooleanValue(30, "SneakDelay", this, false);
	public final DoubleValue sneakDelay = new DoubleValue(16, "SneakDelay", this, 1000.0, 0.0, 4000.0, 0);
	public final DoubleValue timerSpeed = new DoubleValue(15, "Timer", this, 1.0, 0.1, 4.0, 2);
	public final BooleanValue switchItemSneak = new BooleanValue(290, "SneakWhenSwitchItem", this, false);
	public final DoubleValue switchItemSneakDelay = new DoubleValue(325431, "SneakSwitchItemDelay", this, 1000.0, 0.0, 4000.0, 0);
	public StringValue silentMode = new StringValue(1, "SilentMode", this, "Spoof",
			new String[] { "Switch", "Spoof", "None" });
	public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);

	private final RotationUtil rotationUtil = new RotationUtil();
	private final TimeHelper startTimeHelper = new TimeHelper();
	private final TimeHelper sneakTimeHelper = new TimeHelper();
	private final TimeHelper hitTimeHelper = new TimeHelper();
	private final TimeHelper switchsneakTimeHelper = new TimeHelper();
	private final TimeHelper tellyTimeHelper = new TimeHelper();
	private final ArrayList<Vec3> lastPositions = new ArrayList<>();
	private final HashMap<float[], MovingObjectPosition> map = new HashMap<>();
	public ItemStack lastStack = null;
	
	@EventTarget
	public void onWorld(EventWorld eventWorld) {
		if (onWorld.getBoolean()) {
			setToggled(false);
		}
	}

	public float[] rots = new float[2];
	public float[] lastRots = new float[2];
	private int slotID;
	private BlockPos b;
	private Vec3 aimPos;
	private long lastTime;
	private int blockCounter = 0;
	private final HashMap<S23PacketBlockChange, Long> blockpackets = new HashMap<>();
	private Block playerBlock;
	private double[] xyz = new double[3];
	private MovingObjectPosition objectPosition = null;
	private int sneakCounter = 4;
	private int isSneakingTicks = 0;
	private int randomDelay = 0;
	private boolean dynamic;
	public static boolean cracked = true;
	private final TimeHelper placeTimer = new TimeHelper();
	private final int placeDelay = 0;
	private int offGroundTicks;
	private final HashMap<S32PacketConfirmTransaction, Long> transactions = new HashMap<S32PacketConfirmTransaction, Long>();
	public TimeHelper timer = new TimeHelper();
	public float[] keepRots = new float[2], keepLastRots = new float[2];
	public int jumps = 0;
	public boolean jump = false;

	public BlockFly() {
		super("Scaffold", new Color(182, 194, 207), Categorys.WORLD);
	}

	@Override
	public void onEnable() {
		super.onEnable();
		dynamic = mc.gameSettings.ofDynamicFov;
		if (dynamicFov.getBoolean()) {
			mc.gameSettings.ofDynamicFov = false;
		}
		this.sneakCounter = 4;
		this.blockCounter = 0;
		if (mc.thePlayer != null) {
			mc.getTimer().timerSpeed = 1.0F;
			this.rots[0] = mc.thePlayer.rotationYaw;
			this.rots[1] = mc.thePlayer.rotationPitch;
			this.lastRots[0] = mc.thePlayer.prevRotationYaw;
			this.lastRots[1] = mc.thePlayer.prevRotationPitch;
			this.b = null;
			this.startTimeHelper.reset();
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.keyBindSneak.pressed = false;
		for (Map.Entry<S23PacketBlockChange, Long> set : blockpackets.entrySet()) {
			mc.thePlayer.sendQueue.handleBlockChange(set.getKey());
		}
		for (Map.Entry<S32PacketConfirmTransaction, Long> set : transactions.entrySet()) {
			mc.thePlayer.sendQueue.handleConfirmTransaction(set.getKey());
		}
		transactions.clear();
		blockpackets.clear();
		if (dynamicFov.getBoolean()) {
			mc.gameSettings.ofDynamicFov = dynamic;
		}
		mc.getTimer().timerSpeed = 1.0F;
		this.rots = this.lastRots;
		if (autoJump.getBoolean() && mc.gameSettings.keyBindJump.pressed) {
			mc.gameSettings.keyBindJump.pressed = false;
		}
	}

	private void checkS23Packets() {
		try {
			S23PacketBlockChange toremove = null;
			for (Map.Entry<S23PacketBlockChange, Long> set : blockpackets.entrySet()) {
				if (set.getValue() + 1000 < System.currentTimeMillis()) {
					mc.thePlayer.sendQueue.handleBlockChange(set.getKey());
					toremove = set.getKey();
				}
			}
			if (toremove != null) {
				blockpackets.remove(toremove);
			}
		} catch (Exception var2) {
			var2.printStackTrace();
			// System.err.println("Error SBlink");
		}
	}

	private void checkS32Packets() {
		try {
			S32PacketConfirmTransaction toremove = null;
			for (Map.Entry<S32PacketConfirmTransaction, Long> set : transactions.entrySet()) {
				if (set.getValue() + 1000 < System.currentTimeMillis()) {
					// mc.theWorld.invalidateRegionAndSetBlock(set.getKey().getBlockPosition(),
					// set.getKey().getBlockState());
					mc.thePlayer.sendQueue.handleConfirmTransaction(set.getKey());
					toremove = set.getKey();
				}
			}
			if (toremove != null) {
				transactions.remove(toremove);
			}
		} catch (Exception var2) {
			var2.printStackTrace();
			// System.err.println("Error SBlink");
		}
	}

	@EventTarget
	public void onSafewalk(EventSaveWalk eventSaveWalk) {
		if (safewalk.getBoolean()) {
			Block blockUnder1 = mc.theWorld
					.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ))
					.getBlock();
			Block blockUnder2 = mc.theWorld
					.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ))
					.getBlock();
			if (blockUnder1 instanceof BlockAir && blockUnder2 instanceof BlockAir) {
				eventSaveWalk.setSaveWalk(true);
			}
		}
	}

	@EventTarget
	public void onPacketRecv(EventReadPacket eventReadPacket) {
		Packet packet = eventReadPacket.getPacket();
		if (this.flyTest.getBoolean()) {
			if (packet instanceof S23PacketBlockChange) {
				blockpackets.put((S23PacketBlockChange) packet, System.currentTimeMillis());
				eventReadPacket.setCanceled(true);
			}
			if (packet instanceof S32PacketConfirmTransaction) {
				transactions.put((S32PacketConfirmTransaction) packet, System.currentTimeMillis());
				eventReadPacket.setCanceled(true);
			}
			// checkS32Packets();
			checkS23Packets();
		}
	}
	
	@EventTarget
	public void onPacketSend(EventSendPacket eventSendPacket) {
		Packet packet = eventSendPacket.getPacket();
		if(this.flyTest.getBoolean()) {
			if(packet instanceof C08PacketPlayerBlockPlacement) {
				eventSendPacket.setCanceled(true);
			}
		}
	}

	public void tower() {
		switch (towerMode.getSelected()) {
		case "Vanilla": {
			mc.thePlayer.motionY = 0.5F;
			break;
		}
		case "LowHop": {
			if(mc.thePlayer.motionY == -0.078375) {
			       mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[Gugustus]" + "Flag"));
			}
			if (mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.37F;
			}
//	       mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[Gugustus]" + mc.thePlayer.motionY));
			break;
		}
		case "Grim": {
			if(MoveUtil.isMoving())
				return;
			if(mc.thePlayer.motionY == -0.078375) {
			       mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[Gugustus]" + "Flag"));
			}
			if (mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.37F;
			       mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[Gugustus]" + "Tower"));
			}
//	       mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("[Gugustus]" + mc.thePlayer.motionY));
			break;
		}
		case "FastJump": {
			if (mc.thePlayer.motionY < 0) {
				mc.thePlayer.motionY = mc.thePlayer.getJumpUpwardsMotion();
				if (mc.thePlayer.isPotionActive(Potion.jump)) {
					mc.thePlayer.motionY += (float) (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1)
							* 0.1F;
				}
			}
			break;
		}
		case "NCP": {
			/*
			 * if (mc.thePlayer.posY % true <= 0.00153598D) {
			 * mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY),
			 * mc.thePlayer.posY); mc.thePlayer.motionY = 0.41998D; break; }
			 */
			if (mc.thePlayer.posY % 1 <= 0.00153598) {
				mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
				mc.thePlayer.motionY = 0.41998;
			} else if (mc.thePlayer.posY % 1 < 0.1 && offGroundTicks != 0) {
				mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
			}
			break;
		}
		case "AAC": {
			if (mc.thePlayer.posY % 1 <= 0.005) {
				mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
				mc.thePlayer.motionY = 0.41998;
			}
			break;
		}
		case "Hycraft": {
			if(mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.37F;
			}else {
			}
		}
		}
	}

	public boolean isTower2() {
		return mc.gameSettings.keyBindJump.pressed
				&& ((!mc.gameSettings.keyBindLeft.pressed && !mc.gameSettings.keyBindRight.pressed
						&& !mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindBack.pressed)
						|| towerMove.getBoolean());
	}

	@EventTarget
	public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
		if (mc.thePlayer.onGround) {
			offGroundTicks = 0;
		} else {
			offGroundTicks++;
		}
		this.objectPosition = null;
		if (this.shouldScaffold()) {
			if(this.switchItemSneak.getBoolean()) {
				if(mc.thePlayer.inventory.getStackInSlot(slotID) != lastStack) {
					if(!switchsneakTimeHelper.reached((long) this.switchItemSneakDelay.getValue())) {
						mc.gameSettings.keyBindSneak.pressed = true;
					}else {
						mc.gameSettings.keyBindSneak.pressed = false;
						switchsneakTimeHelper.reset();
					}
				}
				lastStack = mc.thePlayer.inventory.getStackInSlot(slotID);
			}
			if (!sameY.getBoolean() && isTower2()) {
				tower();
			}
			if (sprint.getBoolean()) {
//				mc.gameSettings.keyBindSprint.pressed = true;
				mc.thePlayer.setSprinting(true);
			}
			if (telly.getBoolean()) {
				if(offGroundTicks > 2) {
					if(tellyTimeHelper.reached(500)) {
						sameY.setBoolean(true);
						tellyTimeHelper.reset();
						return;
					}
				}else {
					sameY.setBoolean(false);
				}
			}
			this.b = this.getBlockPos();
			if (this.playerYaw.getBoolean() && this.rayCast.getBoolean()) {
				if (this.b != null) {
					if (this.lastPositions.size() > 20) {
						this.lastPositions.remove(0);
					}

					Vec3 playerPosition = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
					this.lastPositions.add(playerPosition);
					this.lastRots[0] = this.rots[0];
					this.lastRots[1] = this.rots[1];
					this.rots = this.getPlayerYawRotation();
					if (mc.thePlayer.hurtResistantTime > 0 && this.blockSafe.getBoolean()) {
						this.rots = this.getRayCastRots();
					}

					if (this.objectPosition != null) {
						this.aimPos = this.objectPosition.hitVec;
					}
				} else {
					this.lastRots[0] = this.rots[0];
					this.lastRots[1] = this.rots[1];
				}
			} else if (!this.rayCast.getBoolean()) {
				Vec3 pos = this.getAimPosBasic();
				this.aimPos = pos;
				if (this.rotateToBlock.getBoolean()) {
					if (this.correctSide.getBoolean() || this.rotationMode.getSelected().equals("OnlyFacing")) {
						if (this.b != null && this.shouldBuild() && pos != null) {
							float yawSpeed = MathHelper.clamp_float(
									(float) (this.yawSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)),
									0.0F, 180.0F);
							float pitchSpeed = MathHelper.clamp_float(
									(float) (this.pitchSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)),
									0.0F, 180.0F);
							this.lastRots[0] = this.rots[0];
							this.lastRots[1] = this.rots[1];
							this.rots = RotationUtil.positionRotation(pos.xCoord, pos.yCoord, pos.zCoord, this.lastRots,
									yawSpeed, pitchSpeed, false);
						} else {
							this.lastRots[0] = this.rots[0];
							this.lastRots[1] = this.rots[1];
						}
					} else if (this.b != null && this.shouldBuild()) {
						MovingObjectPosition objectPosition = mc.thePlayer.customRayTrace(4.5, 1.0F, this.rots[0],
								this.rots[1]);
						if (objectPosition != null
								&& objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
								&& objectPosition.getBlockPos().equalsBlockPos(this.b)) {
							this.lastRots[0] = this.rots[0];
							this.lastRots[1] = this.rots[1];
						} else {
							float yawSpeed = MathHelper.clamp_float(
									(float) (this.yawSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)),
									0.0F, 180.0F);
							float pitchSpeed = MathHelper.clamp_float(
									(float) (this.pitchSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)),
									0.0F, 180.0F);
							this.lastRots[0] = this.rots[0];
							this.lastRots[1] = this.rots[1];
							this.rots = RotationUtil.positionRotation((double) this.b.getX() + 0.5,
									(double) this.b.getY() + 0.5, (double) this.b.getZ() + 0.5, this.lastRots, yawSpeed,
									pitchSpeed, false);
							this.aimPos = new Vec3((double) this.b.getX() + 0.5, this.b.getY() + 1,
									(double) this.b.getZ() + 0.5);
						}
					} else {
						this.lastRots[0] = this.rots[0];
						this.lastRots[1] = this.rots[1];
					}
				} else {
					this.rots = new float[] { Augustus.getInstance().getYawPitchHelper().realYaw,
							Augustus.getInstance().getYawPitchHelper().realPitch };
					this.lastRots = new float[] { Augustus.getInstance().getYawPitchHelper().realLastYaw,
							Augustus.getInstance().getYawPitchHelper().realLastPitch };
					this.aimPos = new Vec3((double) this.b.getX() + 0.5, (double) this.b.getY() + 0.5,
							(double) this.b.getZ() + 0.5);
				}
			} else if (this.rayCast.getBoolean() && !this.playerYaw.getBoolean()) {
				if (this.b != null) {
					if (this.lastPositions.size() > 20) {
						this.lastPositions.remove(0);
					}

					Vec3 playerPosition = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
					this.lastPositions.add(playerPosition);
					this.lastRots[0] = this.rots[0];
					this.lastRots[1] = this.rots[1];
					this.rots = this.getRayCastRots();
					if (this.objectPosition != null) {
						this.aimPos = this.objectPosition.hitVec;
					}
				} else {
					this.lastRots[0] = this.rots[0];
					this.lastRots[1] = this.rots[1];
				}
			}
			if (autoJump.getBoolean()) {
				if (MoveUtil.isMoving()) {
					if (mc.thePlayer.onGround) {
						mc.gameSettings.keyBindJump.pressed = true;
					}
				} else {
					mc.gameSettings.keyBindJump.pressed = false;
				}
			}
			if (this.shouldBuild() || !snap.getBoolean()) {
				this.setRotation();
			} else if (resetRotation.getBoolean()) {
				this.rots = this.lastRots;
				this.rots[0] = mc.thePlayer.rotationYaw;
				this.rots[1] = mc.thePlayer.rotationPitch;
				this.lastRots[0] = mc.thePlayer.prevRotationYaw;
				this.lastRots[1] = mc.thePlayer.prevRotationPitch;
				this.startTimeHelper.reset();
			}
			if (snap.getBoolean() && shouldBuild() && resetRotation.getBoolean() && shouldSneak()) {
				mc.thePlayer.movementInput.sneak = true;
			} else {
				if (snap.getBoolean() && resetRotation.getBoolean()) {
					mc.thePlayer.movementInput.sneak = false;
				}
			}
		}
		if(shouldBuild() && (rots[0] == lastRots[0])) {
			System.out.println("kept");
			this.keepRots = rots;
			this.keepLastRots = lastRots;
		}
		this.playerBlock = mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ)).getBlock();
	}

	@EventTarget
	public void onEventClick(EventClick eventClick) {
		eventClick.setCanceled(true);
		if (this.shouldScaffold() && this.b != null) {
			ItemStack itemStack = this.getItemStack();
			ItemStack lastItem = mc.thePlayer.inventory.getCurrentItem();
			int slot = mc.thePlayer.inventory.currentItem;
			if (!this.rayCast.getBoolean()) {
				boolean flag = this.hitTimeHelper.reached(this.randomDelay);
				if (flag) {
					this.hitTimeHelper.reset();
				}

				if (this.shouldBuild()) {
					EnumFacing enumFacing = this.getPlaceSide(this.b);
					if (enumFacing != null) {
						if (this.aimPos == null) {
							this.aimPos = new Vec3((double) this.b.getX() + 0.5, (double) this.b.getY() + 0.5,
									(double) this.b.getZ() + 0.5);
						}

						if (this.silentMode.getSelected().equals("Switch")) {
							mc.thePlayer.inventory.setCurrentItem(itemStack.getItem(), 0, false, false);
						}

						if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack, this.b,
								enumFacing, this.aimPos)) {
							if(this.rightClickMouse.getBoolean()) {
								mc.rightClickMouse();
							}
							mc.thePlayer.swingItem();
							this.sneakCounter = 0;
							++this.blockCounter;
							flag = false;
						}
					}
				}

				if (flag && itemStack != null && this.spamClick.getBoolean()
						&& mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, itemStack)) {
					mc.entityRenderer.itemRenderer.resetEquippedProgress2();
				}
			} else {
				MovingObjectPosition objectPosition = mc.objectMouseOver;
				if (this.objectPosition != null) {
					objectPosition = this.objectPosition;
				}

				if (objectPosition != null) {
					boolean flag = this.hitTimeHelper.reached(this.randomDelay);
					if (flag) {
						this.hitTimeHelper.reset();
					}

					switch (objectPosition.typeOfHit) {
					case ENTITY:
						if (mc.playerController.func_178894_a(mc.thePlayer, objectPosition.entityHit, objectPosition)) {
							flag = false;
						} else if (mc.playerController.interactWithEntitySendPacket(mc.thePlayer,
								objectPosition.entityHit)) {
							flag = false;
						}
						break;
					case BLOCK:
						if (objectPosition.getBlockPos().equalsBlockPos(this.b)) {
							if (objectPosition.sideHit == EnumFacing.UP) {
								if (!this.sameY.getBoolean()
										|| mc.gameSettings.keyBindJump.isKeyDown() && Mouse.isButtonDown(1)) {
									if (this.silentMode.getSelected().equals("Switch")) {
										mc.thePlayer.inventory.setCurrentItem(itemStack.getItem(), 0, false, false);
									}

									if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack,
											objectPosition.getBlockPos(), objectPosition.sideHit,
											objectPosition.hitVec)) {
										if(this.rightClickMouse.getBoolean()) {
											mc.rightClickMouse();
										}
										mc.thePlayer.swingItem();
										System.out.println("Placed");
										this.sneakCounter = 0;
										++this.blockCounter;
										flag = false;
									}
								}
							} else {
								if (this.silentMode.getSelected().equals("Switch")) {
									mc.thePlayer.inventory.setCurrentItem(itemStack.getItem(), 0, false, false);
								}

								if ((this.shouldBuild() || !this.latestPlace.getBoolean()
										|| this.latestPlace.getBoolean() && !this.latestRotate.getBoolean())
										&& mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack,
												objectPosition.getBlockPos(), objectPosition.sideHit,
												objectPosition.hitVec)) {
									if(this.rightClickMouse.getBoolean()) {
										mc.rightClickMouse();
									}
									mc.thePlayer.swingItem();
									System.out.println("Placed");
									this.sneakCounter = 0;
									++this.blockCounter;
									flag = false;
								}
							}
						} else if (this.isNearbyBlockPos(objectPosition.getBlockPos())
								&& objectPosition.sideHit != EnumFacing.UP) {
							if (this.silentMode.getSelected().equals("Switch")) {
								mc.thePlayer.inventory.setCurrentItem(itemStack.getItem(), 0, false, false);
							}

							if ((this.shouldBuild() || !this.latestPlace.getBoolean()
									|| this.latestPlace.getBoolean() && !this.latestRotate.getBoolean())
									&& mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, itemStack,
											objectPosition.getBlockPos(), objectPosition.sideHit,
											objectPosition.hitVec)) {
								if(this.rightClickMouse.getBoolean()) {
									mc.rightClickMouse();
								}
								mc.thePlayer.swingItem();
								System.out.println("Placed");
								this.sneakCounter = 0;
								++this.blockCounter;
								flag = false;
							}
						}
					}

					if (flag && itemStack != null && this.spamClick.getBoolean()
							&& mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, itemStack)) {
						mc.entityRenderer.itemRenderer.resetEquippedProgress2();
					}
				}
			}

			if (itemStack != null && itemStack.stackSize == 0) {
				mc.thePlayer.inventory.mainInventory[this.slotID] = null;
			}

			if (this.silentMode.getSelected().equals("Switch")) {
				if (lastItem != null) {
					mc.thePlayer.inventory.setCurrentItem(lastItem.getItem(), 0, false, false);
				} else {
					mc.thePlayer.inventory.currentItem = slot;
				}
			}
		}

		mc.sendClickBlockToController(false);
		this.setRandomDelay();
	}

	private void setRandomDelay() {
		if (this.intaveHit.getBoolean()) {
			this.randomDelay = 50;
		} else if (this.spamClickDelay.getValue() == 0.0) {
			this.randomDelay = 0;
		} else {
			SecureRandom secureRandom = new SecureRandom();
			this.randomDelay = (int) (this.spamClickDelay.getValue() + (double) secureRandom.nextInt(60));
		}
	}

	@EventTarget
	public void onEventMove(EventMove eventMove) {
		if (!this.moveFix.getBoolean()) {
			eventMove.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
		}
	}

	@EventTarget
	public void onEventJump(EventJump eventJump) {
		if (!this.moveFix.getBoolean()) {
			eventJump.setYaw(Augustus.getInstance().getYawPitchHelper().realYaw);
		}
	}

	@EventTarget
	public void onEventSaveWalk(EventSaveWalk eventSaveWalk) {
		if (this.sneak.getBoolean() && this.sneakOnPlace.getBoolean()) {
			eventSaveWalk.setDisableSneak(true);
		}
	}

	@EventTarget
	public void onEventSilentMove(EventSilentMove eventSilentMove) {
		if (this.moveFix.getBoolean()) {
			eventSilentMove.setSilent(true);
		}

		if (this.startSneak.getBoolean() && (!this.startTimeHelper.reached(200L)
				|| mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0 && mc.thePlayer.onGround)) {
			mc.thePlayer.movementInput.sneak = true;
		}

		Block playerBlock = mc.theWorld
				.getBlockState(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ)).getBlock();
		if (this.sneak.getBoolean() && !mc.gameSettings.keyBindJump.isKeyDown()) {
			if (!this.sneakOnPlace.getBoolean()) {
				if (this.sneakDelayBool.getBoolean()) {
					if (this.sneakTimeHelper.reached((long) this.sneakDelay.getValue()) && mc.thePlayer.onGround
							&& this.shouldSneak()) {
						this.isSneakingTicks = 0;
						this.sneakTimeHelper.reset();
					}
				} else if (this.buildForward()) {
					if ((double) this.blockCounter >= this.sneakBlocks.getValue() && mc.thePlayer.onGround
							&& this.shouldSneak()) {
						this.isSneakingTicks = 0;
					}
				} else if ((double) this.blockCounter >= this.sneakBlocksDiagonal.getValue() && mc.thePlayer.onGround
						&& this.shouldSneak()) {
					this.isSneakingTicks = 0;
				}

				if ((double) this.isSneakingTicks < this.sneakTicks.getValue()) {
					this.blockCounter = 0;
					mc.thePlayer.movementInput.sneak = true;
					this.sneakTimeHelper.reset();
					++this.isSneakingTicks;
				}
			} else if (!mc.thePlayer.movementInput.jump) {
				playerBlock = mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX - mc.thePlayer.motionX,
						mc.thePlayer.posY - 0.5, mc.thePlayer.posZ - mc.thePlayer.motionZ)).getBlock();
				int random = RandomUtil.nextInt(2, 3);
				if (this.sneakCounter == 1 || this.sneakCounter <= random) {
					mc.thePlayer.movementInput.sneak = true;
					if (this.sneakCounter == random) {
						this.sneakCounter = 10;
					}
				}
			}
		}

		++this.sneakCounter;
		if (!this.isTower()) {
			if (this.rayCast.getBoolean() && this.adStrafe.getBoolean() && this.b != null
					&& (playerBlock.getMaterial() == Material.air || this.adStrafeLegit.getBoolean())
					&& this.shouldScaffold() && !mc.gameSettings.keyBindJump.isKeyDown() && MoveUtil.isMoving()
					&& this.buildForward() && (!this.moonWalk.getBoolean() || !this.playerYaw.getBoolean())) {
				if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.EAST) {
					if ((double) this.b.getZ() + 0.5 > mc.thePlayer.posZ) {
						this.ad1();
					} else {
						this.ad2();
					}
				} else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.WEST) {
					if ((double) this.b.getZ() + 0.5 < mc.thePlayer.posZ) {
						this.ad1();
					} else {
						this.ad2();
					}
				} else if (mc.thePlayer.getHorizontalFacing(this.rots[0]) == EnumFacing.SOUTH) {
					if ((double) this.b.getX() + 0.5 < mc.thePlayer.posX) {
						this.ad1();
					} else {
						this.ad2();
					}
				} else if ((double) this.b.getX() + 0.5 > mc.thePlayer.posX) {
					this.ad1();
				} else {
					this.ad2();
				}
			}

			if (this.moonWalk.getBoolean() && this.playerYaw.getBoolean() && this.b != null && this.rayCast.getBoolean()
					&& this.buildForward() && MoveUtil.isMoving()) {
				if (mc.thePlayer.getHorizontalFacing(this.rots[0] - 18.6F) == EnumFacing.EAST) {
					if ((double) this.b.getZ() + 0.5 > mc.thePlayer.posZ) {
						mc.thePlayer.movementInput.moveStrafe = 1.0F;
					}
				} else if (mc.thePlayer.getHorizontalFacing(this.rots[0] - 18.6F) == EnumFacing.WEST) {
					if ((double) this.b.getZ() + 0.5 < mc.thePlayer.posZ) {
						mc.thePlayer.movementInput.moveStrafe = 1.0F;
					}
				} else if (mc.thePlayer.getHorizontalFacing(this.rots[0] - 18.6F) == EnumFacing.SOUTH) {
					if ((double) this.b.getX() + 0.5 < mc.thePlayer.posX) {
						mc.thePlayer.movementInput.moveStrafe = 1.0F;
					}
				} else if ((double) this.b.getX() + 0.5 > mc.thePlayer.posX) {
					mc.thePlayer.movementInput.moveStrafe = 1.0F;
				}
			}
		}
	}

	@EventTarget
	public void onEventPreMotion(EventPreMotion eventPreMotion) {
		mc.getTimer().timerSpeed = (float) this.timerSpeed.getValue();
	}

	@EventTarget
	public void onEventPostMouseOver(EventPostMouseOver eventPostMouseOver) {
		if (this.objectPosition != null) {
			mc.objectMouseOver = this.objectPosition;
		}
	}

	@EventTarget
	public void onEventSwingItemClientSide(EventSwingItemClientSide eventSwingItemClientSide) {
		if (this.noSwing.getBoolean()) {
			eventSwingItemClientSide.cancel = true;
		}
	}

	private void ad1() {
		if (mc.thePlayer.movementInput.moveForward != 0.0F) {
			mc.thePlayer.movementInput.moveStrafe = mc.thePlayer.movementInput.moveForward > 0.0F ? 1.0F : -1.0F;
		} else if (mc.thePlayer.movementInput.moveStrafe != 0.0F) {
			mc.thePlayer.movementInput.moveForward = mc.thePlayer.movementInput.moveStrafe > 0.0F ? -1.0F : 1.0F;
		}
	}

	private void ad2() {
		if (mc.thePlayer.movementInput.moveForward != 0.0F) {
			mc.thePlayer.movementInput.moveStrafe = mc.thePlayer.movementInput.moveForward > 0.0F ? -1.0F : 1.0F;
		} else if (mc.thePlayer.movementInput.moveStrafe != 0.0F) {
			mc.thePlayer.movementInput.moveForward = mc.thePlayer.movementInput.moveStrafe > 0.0F ? 1.0F : -1.0F;
		}
	}

	private float[] getRayCastRots() {
		float yawSpeed = MathHelper.clamp_float(
				(float) (this.yawSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)), 0.0F, 180.0F);
		float pitchSpeed = MathHelper.clamp_float(
				(float) (this.pitchSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)), 0.0F, 180.0F);
		if (this.isTower()) {
			Vec3 pos = this.getAimPosBasic();
			MovingObjectPosition objectPosition = mc.objectMouseOver;
			if (objectPosition != null) {
				if (objectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
						&& objectPosition.getBlockPos().equalsBlockPos(this.b)
						&& objectPosition.sideHit == EnumFacing.UP) {
					return this.rots;
				}

				if (pos != null) {
					return RotationUtil.positionRotation(pos.xCoord, pos.yCoord, pos.zCoord, this.lastRots, yawSpeed,
							pitchSpeed, false);
				}

				return this.rots;
			}

			if (pos != null) {
				return RotationUtil.positionRotation(pos.xCoord, pos.yCoord, pos.zCoord, this.lastRots, yawSpeed,
						pitchSpeed, false);
			}
		}

		float yaw = this.rots[0];
		float[] rotations = new float[] { yaw, this.rots[1] };
		if ((mc.thePlayer.motionX != 0.0 || mc.thePlayer.motionZ != 0.0 || !mc.thePlayer.onGround)
				&& this.startTimeHelper.reached(200L)) {
			if (mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0 && mc.thePlayer.onGround) {
				this.startTimeHelper.reset();
			}
		} else {
			float pitch = this.rotationUtil.rotateToPitch(yawSpeed, this.rots[1], 80.34F);
			yaw = this.rotationUtil.rotateToYaw(pitchSpeed, this.rots[0],
					Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F);
			RotationUtil.mouseSens(yaw, pitch, this.rots[0], this.rots[1]);
			rotations = new float[] { yaw, pitch };
		}

		if (this.shouldBuild()) {
			ArrayList<float[]> rots = new ArrayList<>();
			float difference = 0.1F;
			float currentX = this.rots[0];
			float currentY = this.rots[1];

			for (float dist = 0.0F; dist < 180.0F; dist += difference) {
				float maxX = this.rots[0] + dist;
				float minX = this.rots[0] - dist;
				float maxY = Math.min(this.rots[1] + dist, 90.0F);

				float minY;
				for (minY = Math.max(this.rots[1] - dist, -90.0F); currentY < maxY; currentY += difference) {
					float[] f = RotationUtil.mouseSens(currentX, currentY, this.rots[0], this.rots[1]);
					if (this.canPlace(f)) {
						rots.add(f);
					}
				}

				for (; currentX <= maxX; currentX += difference) {
					float[] f = RotationUtil.mouseSens(currentX, currentY, this.rots[0], this.rots[1]);
					if (this.canPlace(f)) {
						rots.add(f);
					}
				}

				for (; currentY >= minY; currentY -= difference) {
					float[] f = RotationUtil.mouseSens(currentX, currentY, this.rots[0], this.rots[1]);
					if (this.canPlace(f)) {
						rots.add(f);
					}
				}

				for (; currentX >= minX; currentX -= difference) {
					float[] f = RotationUtil.mouseSens(currentX, currentY, this.rots[0], this.rots[1]);
					if (this.canPlace(f)) {
						rots.add(f);
					}
				}

				if (dist > 5.0F && dist <= 10.0F) {
					difference = 0.3F;
				}

				if (dist > 10.0F) {
					difference = (float) ((double) difference + (double) (dist / 500.0F) + 0.01);
				}
			}

			rots.sort(Comparator.comparingDouble(this::distanceToLastRots));
			if (!rots.isEmpty()) {
				rotations = rots.get(0);
				this.objectPosition = this.map.get(rotations);
			}
		}

		return rotations;
	}

	private boolean canPlace(float[] yawPitch) {
		BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.5, mc.thePlayer.posZ);
		MovingObjectPosition m4 = RayTraceUtil.rayCast(1.0F, new float[] { yawPitch[0], yawPitch[1] });
		if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && BlockUtil.isValidBock(m4.getBlockPos())
				&& m4.getBlockPos().equalsBlockPos(this.b) && m4.sideHit != EnumFacing.DOWN
				&& m4.sideHit != EnumFacing.UP && m4.getBlockPos().getY() <= b.getY()) {
			this.map.put(yawPitch, m4);
			return true;
		} else {
			return false;
		}
	}

	private double distanceToLastRots(float[] predictRots) {
		float diff1 = Math.abs(predictRots[0] - this.rots[0]);
		float diff2 = Math.abs(predictRots[1] - this.rots[1]);
		return diff1 * diff1 + diff1 * diff1 + diff2 * diff2;
	}

	private boolean buildForward() {
		float realYaw = this.moonWalk.getBoolean()
				? MathHelper.wrapAngleTo180_float(Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F)
				: MathHelper.wrapAngleTo180_float(this.rots[0]);
		if ((double) realYaw > 77.5 && (double) realYaw < 102.5) {
			return true;
		} else if (!((double) realYaw > 167.5) && !(realYaw < -167.0F)) {
			if ((double) realYaw < -77.5 && (double) realYaw > -102.5) {
				return true;
			} else {
				return (double) realYaw > -12.5 && (double) realYaw < 12.5;
			}
		} else {
			return true;
		}
	}

	private boolean prediction() {
		Vec3 predictedPosition = this.getPredictedPosition(1);
		BlockPos blockPos = this.getPredictedBlockPos();
		if (blockPos != null && predictedPosition != null) {
			double maX = (double) blockPos.getX() + 1.285;
			double miX = (double) blockPos.getX() - 0.285;
			double maZ = (double) blockPos.getZ() + 1.285;
			double miZ = (double) blockPos.getZ() - 0.285;
			return predictedPosition.xCoord > maX || predictedPosition.xCoord < miX || predictedPosition.zCoord > maZ
					|| predictedPosition.zCoord < miZ;
		} else {
			return false;
		}
	}

	private Vec3 getPredictedPosition(int predictTicks) {
		Vec3 playerPosition = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		Vec3 vec3 = null;
		if (!this.lastPositions.isEmpty() && this.lastPositions.size() > 10
				&& this.lastPositions.size() > this.lastPositions.size() - predictTicks - 1) {
			vec3 = playerPosition
					.add(playerPosition.subtract(this.lastPositions.get(this.lastPositions.size() - predictTicks - 1)));
		}

		return vec3;
	}

	private BlockPos getPredictedBlockPos() {
		ArrayList<Float> pitchs = new ArrayList<>();

		for (float i = Math.max(this.rots[1] - 30.0F, -90.0F); i < Math.min(this.rots[1] + 20.0F, 90.0F); i += 0.05F) {
			float[] f = RotationUtil.mouseSens(this.rots[0], i, this.lastRots[0], this.lastRots[1]);
			MovingObjectPosition m4 = mc.thePlayer.customRayTrace(4.5, 2.0F, this.rots[0], f[1]);
			if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && BlockUtil.isValidBock(m4.getBlockPos())
					&& this.isNearbyBlockPos(m4.getBlockPos()) && m4.sideHit != EnumFacing.DOWN
					&& m4.sideHit != EnumFacing.UP) {
				pitchs.add(f[1]);
			}
		}

		float[] rotations = new float[2];
		if (!pitchs.isEmpty()) {
			pitchs.sort(Comparator.comparingDouble(this::distanceToLastPitch));
			if (!pitchs.isEmpty()) {
				rotations[1] = pitchs.get(0);
				rotations[0] = this.rots[0];
			}

			MovingObjectPosition movingObjectPosition = mc.thePlayer.customRayTrace(4.5, 2.0F, rotations[0],
					rotations[1]);
			EnumFacing enumFacing = movingObjectPosition.sideHit;
			BlockPos blockPos = movingObjectPosition.getBlockPos();
			if (enumFacing == EnumFacing.EAST) {
				return blockPos.add(1, 0, 0);
			}

			if (enumFacing == EnumFacing.WEST) {
				return blockPos.add(-1, 0, 0);
			}

			if (enumFacing == EnumFacing.NORTH) {
				return blockPos.add(0, 0, -1);
			}

			if (enumFacing == EnumFacing.SOUTH) {
				return blockPos.add(0, 0, 1);
			}
		}

		return null;
	}

	private float[] getPlayerYawRotation() {
		boolean moonWalk = this.moonWalk.getBoolean() && this.buildForward();
		boolean godBridge = this.godBridge.getBoolean() || this.rotationMode.getSelected().equals("GodBridge");
		float yaw = this.rots[0];
		float yawSpeed = MathHelper.clamp_float(
				(float) (this.yawSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)), 0.0F, 180.0F);
		float pitchSpeed = MathHelper.clamp_float(
				(float) (this.pitchSpeed.getValue() + (double) RandomUtil.nextFloat(0.0F, 15.0F)), 0.0F, 180.0F);
		if (this.isTower()) {
			MovingObjectPosition objectPosition = mc.objectMouseOver;
			if (objectPosition != null) {
				float pitch = this.rotationUtil.rotateToPitch(yawSpeed, this.rots, 90.0F);
				yaw = this.rotationUtil.rotateToYaw(pitchSpeed, this.rots,
						moonWalk ? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 18.5F
								: godBridge ? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 45
										: Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F);
				return new float[] { yaw, pitch };
			}
		}

		float[] rotations = new float[] { yaw, this.rots[1] };
		if ((mc.thePlayer.motionX != 0.0 || mc.thePlayer.motionZ != 0.0 || !mc.thePlayer.onGround)
				&& this.startTimeHelper.reached(200L)) {
			if (mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0 && mc.thePlayer.onGround) {
				this.startTimeHelper.reset();
			}
		} else {
			float pitch = this.rotationUtil.rotateToPitch(pitchSpeed, this.rots, moonWalk ? 80.0F : 80.34F);
			yaw = this.rotationUtil.rotateToYaw(yawSpeed, this.rots,
					moonWalk ? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 18.5F
							: godBridge ? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 45
									: Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F);
			rotations = new float[] { yaw, pitch };
		}

		float realYaw = mc.thePlayer.rotationYaw;
		if (mc.gameSettings.keyBindBack.pressed) {
			realYaw += 180.0F;
			if (mc.gameSettings.keyBindLeft.pressed) {
				realYaw += 45.0F;
			} else if (mc.gameSettings.keyBindRight.pressed) {
				realYaw -= 45.0F;
			}
		} else if (mc.gameSettings.keyBindForward.pressed) {
			if (mc.gameSettings.keyBindLeft.pressed) {
				realYaw -= 45.0F;
			} else if (mc.gameSettings.keyBindRight.pressed) {
				realYaw += 45.0F;
			}
		} else if (mc.gameSettings.keyBindRight.pressed) {
			realYaw += 90.0F;
		} else if (mc.gameSettings.keyBindLeft.pressed) {
			realYaw -= 90.0F;
		}

		yaw = this.rotationUtil.rotateToYaw(yawSpeed, this.rots[0], moonWalk
				? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 18.5F
				: godBridge ? Augustus.getInstance().getYawPitchHelper().realYaw - 180.0F + 45 : realYaw - 180.0F);
		rotations[0] = yaw;
		if (this.shouldBuild()) {
			MovingObjectPosition m1 = RayTraceUtil.rayCast(1.0F, new float[] { rotations[0], rotations[1] });
			if (m1.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && BlockUtil.isValidBock(m1.getBlockPos())
					&& this.isNearbyBlockPos(m1.getBlockPos()) && m1.sideHit != EnumFacing.DOWN
					&& m1.sideHit != EnumFacing.UP) {
				this.objectPosition = m1;
				return rotations;
			}

			HashMap<Float, MovingObjectPosition> hashMap = new HashMap<>();
			ArrayList<Float> pitchs = new ArrayList<>();

			for (float i = Math.max(this.rots[1] - 30.0F, -90.0F); i < Math.min(this.rots[1] + 20.0F,
					90.0F); i += 0.05F) {
				float[] f = RotationUtil.mouseSens(yaw, i, this.rots[0], this.rots[1]);
				MovingObjectPosition m4 = RayTraceUtil.rayCast(1.0F, new float[] { yaw, f[1] });
				if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
						&& BlockUtil.isValidBock(m4.getBlockPos()) && this.isNearbyBlockPos(m4.getBlockPos())
						&& m4.sideHit != EnumFacing.DOWN && m4.sideHit != EnumFacing.UP) {
					hashMap.put(f[1], m4);
					pitchs.add(f[1]);
				}
			}

			if (!pitchs.isEmpty()) {
				pitchs.sort(Comparator.comparingDouble(this::distanceToLastPitch));
				if (!pitchs.isEmpty()) {
					rotations[1] = pitchs.get(0);
					this.objectPosition = hashMap.get(rotations[1]);
				}
			} else {
				if (!this.blockSafe.getBoolean()) {
					return rotations;
				}

				int add = 1;

				for (int yawLoops = 0; yawLoops < 180; ++yawLoops) {
					float yaw1 = yaw + (float) (yawLoops * add);
					float yaw2 = yaw - (float) (yawLoops * add);
					float pitch = this.rots[1];

					for (int pitchLoops = 0; pitchLoops < 25; ++pitchLoops) {
						float pitch1 = MathHelper.clamp_float(pitch + (float) (pitchLoops * add), -90.0F, 90.0F);
						float pitch2 = MathHelper.clamp_float(pitch - (float) (pitchLoops * add), -90.0F, 90.0F);
						float[] ffff = RotationUtil.mouseSens(yaw2, pitch2, this.rots[0], this.rots[1]);
						MovingObjectPosition m7 = RayTraceUtil.rayCast(1.0F, ffff);
						if (m7.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
								&& BlockUtil.isValidBock(m7.getBlockPos()) && this.isNearbyBlockPos(m7.getBlockPos())
								&& m7.sideHit != EnumFacing.DOWN && m7.sideHit != EnumFacing.UP) {
							this.objectPosition = m7;
							return ffff;
						}

						float[] fff = RotationUtil.mouseSens(yaw2, pitch1, this.rots[0], this.rots[1]);
						MovingObjectPosition m6 = RayTraceUtil.rayCast(1.0F, fff);
						if (m6.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
								&& BlockUtil.isValidBock(m6.getBlockPos()) && this.isNearbyBlockPos(m6.getBlockPos())
								&& m6.sideHit != EnumFacing.DOWN && m6.sideHit != EnumFacing.UP) {
							this.objectPosition = m6;
							return fff;
						}

						float[] ff = RotationUtil.mouseSens(yaw1, pitch2, this.rots[0], this.rots[1]);
						MovingObjectPosition m5 = RayTraceUtil.rayCast(1.0F, ff);
						if (m5.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
								&& BlockUtil.isValidBock(m5.getBlockPos()) && this.isNearbyBlockPos(m5.getBlockPos())
								&& m5.sideHit != EnumFacing.DOWN && m5.sideHit != EnumFacing.UP) {
							this.objectPosition = m5;
							return ff;
						}

						float[] f = RotationUtil.mouseSens(yaw1, pitch1, this.rots[0], this.rots[1]);
						MovingObjectPosition m4 = RayTraceUtil.rayCast(1.0F, f);
						if (m4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
								&& BlockUtil.isValidBock(m4.getBlockPos()) && this.isNearbyBlockPos(m4.getBlockPos())
								&& m4.sideHit != EnumFacing.DOWN && m4.sideHit != EnumFacing.UP) {
							this.objectPosition = m4;
							return f;
						}
					}
				}
			}
		}
	return rotations;
	}

	private double distanceToLastPitch(float pitch) {
		return Math.abs(pitch - this.rots[1]);
	}

	private double distanceToLastPitch(float[] pitch) {
		return Math.abs(pitch[0] - this.rots[1]);
	}

	private boolean isNearbyBlockPos(BlockPos blockPos) {
		if (!mc.thePlayer.onGround) {
			return blockPos.equalsBlockPos(this.b);
		} else {
			for (int x = this.b.getX() - 1; x <= this.b.getX() + 1; ++x) {
				for (int z = this.b.getZ() - 1; z <= this.b.getZ() + 1; ++z) {
					if (blockPos.equalsBlockPos(new BlockPos(x, this.b.getY(), z))) {
						return true;
					}
				}
			}

			return false;
		}
	}

	private Vec3 getAimPosBasic() {
		if (this.b == null) {
			return null;
		} else {
			EnumFacing enumFacing = this.getPlaceSide(this.b);
			Block block = mc.theWorld.getBlockState(this.b).getBlock();
			double add = 0.01F;
			Vec3 min = null;
			Vec3 max = null;
			if (enumFacing != null) {
				if (enumFacing == EnumFacing.UP) {
					min = new Vec3((double) this.b.getX() + add, (double) this.b.getY() + block.getBlockBoundsMaxY(),
							(double) this.b.getZ() + add);
					max = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX() - add,
							(double) this.b.getY() + block.getBlockBoundsMaxY(),
							(double) this.b.getZ() + block.getBlockBoundsMaxZ() - add);
				} else if (enumFacing == EnumFacing.WEST) {
					min = new Vec3(this.b.getX(), (double) this.b.getY() + add, (double) this.b.getZ() + add);
					max = new Vec3(this.b.getX(), (double) this.b.getY() + block.getBlockBoundsMaxY() - add,
							(double) this.b.getZ() + block.getBlockBoundsMaxZ() - add);
				} else if (enumFacing == EnumFacing.EAST) {
					min = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX(), (double) this.b.getY() + add,
							(double) this.b.getZ() + add);
					max = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX(),
							(double) this.b.getY() + block.getBlockBoundsMaxY() - add,
							(double) this.b.getZ() + block.getBlockBoundsMaxZ() - add);
				} else if (enumFacing == EnumFacing.SOUTH) {
					min = new Vec3((double) this.b.getX() + add, (double) this.b.getY() + add,
							(double) this.b.getZ() + block.getBlockBoundsMaxZ());
					max = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX() - add,
							(double) this.b.getY() + block.getBlockBoundsMaxY() - add,
							(double) this.b.getZ() + block.getBlockBoundsMaxZ());
				} else if (enumFacing == EnumFacing.NORTH) {
					min = new Vec3((double) this.b.getX() + add, (double) this.b.getY() + add, this.b.getZ());
					max = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX() - add,
							(double) this.b.getY() + block.getBlockBoundsMaxY() - add, this.b.getZ());
				} else if (enumFacing == EnumFacing.DOWN) {
					min = new Vec3((double) this.b.getX() + add, this.b.getY(), (double) this.b.getZ() + add);
					max = new Vec3((double) this.b.getX() + block.getBlockBoundsMaxX() - add, this.b.getY(),
							(double) this.b.getZ() + block.getBlockBoundsMaxZ() - add);
				}
			}

			return min != null ? this.getBestHit(min, max) : null;
		}
	}

	private EnumFacing getPlaceSide(BlockPos blockPos) {
		ArrayList<Vec3> positions = new ArrayList<>();
		HashMap<Vec3, EnumFacing> hashMap = new HashMap<>();
		double[] ichVercrafteDasAllesNichtMehr = teleportForward(expand.getValue());
		BlockPos playerPos = new BlockPos(mc.thePlayer.posX + ichVercrafteDasAllesNichtMehr[0], mc.thePlayer.posY,
				mc.thePlayer.posZ + ichVercrafteDasAllesNichtMehr[1]);
		if (BlockUtil.isAirBlock(blockPos.add(0, 1, 0)) && !blockPos.add(0, 1, 0).equalsBlockPos(playerPos)
				&& !mc.thePlayer.onGround) {
			BlockPos bp = blockPos.add(0, 1, 0);
			Vec3 vec3 = this.getBestHitFeet(bp);
			positions.add(vec3);
			hashMap.put(vec3, EnumFacing.UP);
		}

		if (BlockUtil.isAirBlock(blockPos.add(1, 0, 0)) && !blockPos.add(1, 0, 0).equalsBlockPos(playerPos)) {
			BlockPos bp = blockPos.add(1, 0, 0);
			Vec3 vec3 = this.getBestHitFeet(bp);
			positions.add(vec3);
			hashMap.put(vec3, EnumFacing.EAST);
		}

		if (BlockUtil.isAirBlock(blockPos.add(-1, 0, 0)) && !blockPos.add(-1, 0, 0).equalsBlockPos(playerPos)) {
			BlockPos bp = blockPos.add(-1, 0, 0);
			Vec3 vec3 = this.getBestHitFeet(bp);
			positions.add(vec3);
			hashMap.put(vec3, EnumFacing.WEST);
		}

		if (BlockUtil.isAirBlock(blockPos.add(0, 0, 1)) && !blockPos.add(0, 0, 1).equalsBlockPos(playerPos)) {
			BlockPos bp = blockPos.add(0, 0, 1);
			Vec3 vec3 = this.getBestHitFeet(bp);
			positions.add(vec3);
			hashMap.put(vec3, EnumFacing.SOUTH);
		}

		if (BlockUtil.isAirBlock(blockPos.add(0, 0, -1)) && !blockPos.add(0, 0, -1).equalsBlockPos(playerPos)) {
			BlockPos bp = blockPos.add(0, 0, -1);
			Vec3 vec3 = this.getBestHitFeet(bp);
			positions.add(vec3);
			hashMap.put(vec3, EnumFacing.NORTH);
		}

		positions.sort(Comparator
				.comparingDouble(vec3x -> mc.thePlayer.getDistance(vec3x.xCoord, vec3x.yCoord, vec3x.zCoord)));
		if (!positions.isEmpty()) {
			Vec3 vec3 = this.getBestHitFeet(this.b);
			if (mc.thePlayer.getDistance(vec3.xCoord, vec3.yCoord, vec3.zCoord) >= mc.thePlayer
					.getDistance(positions.get(0).xCoord, positions.get(0).yCoord, positions.get(0).zCoord)) {
				return hashMap.get(positions.get(0));
			}
		}

		return null;
	}

	private Vec3 getBestHitFeet(BlockPos blockPos) {
		Block block = mc.theWorld.getBlockState(blockPos).getBlock();
		double ex = MathHelper.clamp_double(mc.thePlayer.posX, blockPos.getX(),
				(double) blockPos.getX() + block.getBlockBoundsMaxX());
		double ey = MathHelper.clamp_double(mc.thePlayer.posY, blockPos.getY(),
				(double) blockPos.getY() + block.getBlockBoundsMaxY());
		double ez = MathHelper.clamp_double(mc.thePlayer.posZ, blockPos.getZ(),
				(double) blockPos.getZ() + block.getBlockBoundsMaxZ());
		return new Vec3(ex, ey, ez);
	}

	private Vec3 getBestHit(Vec3 min, Vec3 max) {
		Vec3 positionEyes = mc.thePlayer.getPositionEyes(1.0F);
		double x = MathHelper.clamp_double(mc.thePlayer.posX, min.xCoord, max.xCoord);
		double y = MathHelper.clamp_double(mc.thePlayer.posY, min.yCoord, max.yCoord);
		double z = MathHelper.clamp_double(mc.thePlayer.posZ, min.zCoord, max.zCoord);
		return new Vec3(x, y, z);
	}

	private ItemStack getItemStack() {
		ItemStack itemStack = mc.thePlayer.getCurrentEquippedItem();
		if (!this.silentMode.getSelected().equals("None")) {
			for (int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
				ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
				if (stack != null && stack.getItem() instanceof ItemBlock && stack.stackSize > 0
						&& Augustus.getInstance().getBlockUtil().isValidStack(stack)) {
					this.slotID = i - 36;
					break;
				}
			}

			itemStack = mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack();
		} else {
			this.slotID = mc.thePlayer.inventory.currentItem;
		}

		return itemStack;
	}

	public static double[] teleportForward(final double speed) {
		final float forward = 1.0F;
		final float side = 0;
		final float yaw = mc.thePlayer.prevRotationYaw
				+ (mc.thePlayer.rotationYaw - mc.thePlayer.prevRotationYaw) * mc.timer.renderPartialTicks;
		final double sin = Math.sin(Math.toRadians(yaw + 90.0F));
		final double cos = Math.cos(Math.toRadians(yaw + 90.0F));
		final double posX = forward * speed * cos + side * speed * sin;
		final double posZ = forward * speed * sin - side * speed * cos;
		return new double[] { posX, posZ };
	}

	private BlockPos getBlockPos() {
		if (expand.getValue() == 0) {
			BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0, mc.thePlayer.posZ);
			ArrayList<Vec3> positions = new ArrayList<>();
			HashMap<Vec3, BlockPos> hashMap = new HashMap<>();

			for (int x = playerPos.getX() - 5; x <= playerPos.getX() + 5; ++x) {
				for (int y = playerPos.getY() - 1; y <= playerPos.getY(); ++y) {
					for (int z = playerPos.getZ() - 5; z <= playerPos.getZ() + 5; ++z) {
						if (BlockUtil.isValidBock(new BlockPos(x, y, z))) {
							BlockPos blockPos = new BlockPos(x, y, z);
							Block block = mc.theWorld.getBlockState(blockPos).getBlock();
							double ex = MathHelper.clamp_double(mc.thePlayer.posX, blockPos.getX(),
									(double) blockPos.getX() + block.getBlockBoundsMaxX());
							double ey = MathHelper.clamp_double(mc.thePlayer.posY, blockPos.getY(),
									(double) blockPos.getY() + block.getBlockBoundsMaxY());
							double ez = MathHelper.clamp_double(mc.thePlayer.posZ, blockPos.getZ(),
									(double) blockPos.getZ() + block.getBlockBoundsMaxZ());
							Vec3 vec3 = new Vec3(ex, ey, ez);
							positions.add(vec3);
							hashMap.put(vec3, blockPos);
						}
					}
				}
			}

			if (!positions.isEmpty()) {
				positions.sort(Comparator.comparingDouble(this::getBestBlock));
				return this.isTower() && (double) hashMap.get(positions.get(0)).getY() != mc.thePlayer.posY - 1.5
						? new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.5, mc.thePlayer.posZ)
						: hashMap.get(positions.get(0));
			}
		} else {
			double[] forward = teleportForward(expand.getValue());
//         return new BlockPos(mc.thePlayer.posX + forward[0], mc.thePlayer.posY - 1.0, mc.thePlayer.posZ + forward[1]);
			BlockPos playerPos = new BlockPos(mc.thePlayer.posX + forward[0], mc.thePlayer.posY - 1.0,
					mc.thePlayer.posZ + forward[1]);
			ArrayList<Vec3> positions = new ArrayList<>();
			HashMap<Vec3, BlockPos> hashMap = new HashMap<>();

			for (int x = (int) (playerPos.getX() - (expand.getValue() + 5)); x <= playerPos.getX() + expand.getValue()
					+ 5; ++x) {
				for (int y = playerPos.getY() - 1; y <= playerPos.getY(); ++y) {
					for (int z = (int) (playerPos.getZ() - (expand.getValue() + 5)); z <= playerPos.getZ()
							+ expand.getValue() + 5; ++z) {
						if (BlockUtil.isValidBock(new BlockPos(x, y, z))) {
							BlockPos blockPos = new BlockPos(x, y, z);
							Block block = mc.theWorld.getBlockState(blockPos).getBlock();
							double ex = MathHelper.clamp_double(mc.thePlayer.posX, blockPos.getX(),
									(double) blockPos.getX() + block.getBlockBoundsMaxX());
							double ey = MathHelper.clamp_double(mc.thePlayer.posY, blockPos.getY(),
									(double) blockPos.getY() + block.getBlockBoundsMaxY());
							double ez = MathHelper.clamp_double(mc.thePlayer.posZ, blockPos.getZ(),
									(double) blockPos.getZ() + block.getBlockBoundsMaxZ());
							Vec3 vec3 = new Vec3(ex, ey, ez);
							positions.add(vec3);
							hashMap.put(vec3, blockPos);
						}
					}
				}
			}

			if (!positions.isEmpty()) {
				positions.sort(Comparator.comparingDouble(this::getBestBlock));
				return this.isTower() && (double) hashMap.get(positions.get(0)).getY() != mc.thePlayer.posY - 1.5
						? new BlockPos(mc.thePlayer.posX + forward[0], mc.thePlayer.posY - 1.5,
								mc.thePlayer.posZ + forward[1])
						: hashMap.get(positions.get(0));
			}
		}
		return null;
	}

	private boolean shouldScaffold() {
		return mc.currentScreen == null;
	}

	private boolean shouldBuild() {
		if (this.latestRotate.getBoolean() && this.rayCast.getBoolean()) {
			double add1 = 1.282;
			double add2 = 0.282;
			double x = mc.thePlayer.posX;
			double z = mc.thePlayer.posZ;
			x += (mc.thePlayer.posX - this.xyz[0]) * this.backupTicks.getValue();
			z += (mc.thePlayer.posZ - this.xyz[2]) * this.backupTicks.getValue();
			this.xyz = new double[] { mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ };
			double maX = (double) this.b.getX() + add1;
			double miX = (double) this.b.getX() - add2;
			double maZ = (double) this.b.getZ() + add1;
			double miZ = (double) this.b.getZ() - add2;
			return x > maX || x < miX || z > maZ || z < miZ
					|| this.predict.getBoolean() && this.rayCast.getBoolean() && this.prediction()/* && yes */;
		} else {
			double[] ichVercrafteDasAllesNichtMehr = teleportForward(expand.getValue());
			BlockPos playerPos = new BlockPos(mc.thePlayer.posX + ichVercrafteDasAllesNichtMehr[0],
					mc.thePlayer.posY - 0.5, mc.thePlayer.posZ + ichVercrafteDasAllesNichtMehr[1]);
			return mc.theWorld.isAirBlock(playerPos)/* && yes */;
		}
	}

	private boolean shouldSneak() {
		if (this.latestRotate.getBoolean() && this.rayCast.getBoolean()) {
			double add1 = 1.15;
			double add2 = 0.15;
			double x = mc.thePlayer.posX;
			double z = mc.thePlayer.posZ;
			x += (mc.thePlayer.posX - this.xyz[0]) * this.backupTicks.getValue();
			z += (mc.thePlayer.posZ - this.xyz[2]) * this.backupTicks.getValue();
			this.xyz = new double[] { mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ };
			double maX = (double) this.b.getX() + add1;
			double miX = (double) this.b.getX() - add2;
			double maZ = (double) this.b.getZ() + add1;
			double miZ = (double) this.b.getZ() - add2;
			return x > maX || x < miX || z > maZ || z < miZ
					|| this.predict.getBoolean() && this.rayCast.getBoolean() && this.prediction();
		} else {
			Block playerBlock = mc.theWorld
					.getBlockState(new BlockPos(mc.thePlayer.posX + mc.thePlayer.posX - this.xyz[0],
							mc.thePlayer.posY - 0.5, mc.thePlayer.posZ + mc.thePlayer.posZ - this.xyz[2]))
					.getBlock();
			this.xyz = new double[] { mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ };
			return playerBlock instanceof BlockAir;
		}
	}

	private double getBestBlock(Vec3 vec3) {
		double[] forward = teleportForward(expand.getValue());
		return mc.thePlayer.getDistanceSq(vec3.xCoord + forward[0], vec3.yCoord, vec3.zCoord + forward[1]);
	}

	private double[] getAdvancedDiagonalExpandXZ(BlockPos blockPos) {
		double[] xz = new double[2];
		Vec2d difference = new Vec2d((double) blockPos.getX() - mc.thePlayer.posX,
				(double) blockPos.getZ() - mc.thePlayer.posZ);
		if (difference.x > -1.0 && difference.x < 0.0 && difference.y < -1.0) {
			xz[0] = difference.x * -1.0;
			xz[1] = 1.0;
		}

		if (difference.y < 0.0 && difference.y > -1.0 && difference.x < -1.0) {
			xz[0] = 1.0;
			xz[1] = difference.y * -1.0;
		}

		if (difference.x > -1.0 && difference.x < 0.0 && difference.y > 0.0) {
			xz[0] = difference.x * -1.0;
			xz[1] = 0.0;
		}

		if (difference.y < 0.0 && difference.y > -1.0 && difference.x > 0.0) {
			xz[0] = 0.0;
			xz[1] = difference.y * -1.0;
		}

		if (difference.x >= 0.0 && difference.y < -1.0) {
			xz[1] = 1.0;
		}

		if (difference.y >= 0.0 & difference.x < -1.0) {
			xz[0] = 1.0;
		}

		if (difference.x >= 0.0 && difference.y > 0.0) {
		}

		if (difference.y <= -1.0 && difference.x < -1.0) {
			xz[0] = 1.0;
			xz[1] = 1.0;
		}

		return xz;
	}

	public int getSlotID() {
		return this.slotID;
	}

	public float[] rot, lastRot;
	
	private void setRotation() {
		if (mc.currentScreen == null) {
			if(telly.getBoolean()) {
				if(offGroundTicks > 4) {
					return;
				}
			}
			mc.thePlayer.rotationYaw = this.rots[0];
			mc.thePlayer.rotationPitch = this.rots[1];
			mc.thePlayer.prevRotationYaw = this.lastRots[0];
			mc.thePlayer.prevRotationPitch = this.lastRots[1];
		}
	}

	private boolean isTower() {
		return mc.gameSettings.keyBindJump.isKeyDown() && mc.thePlayer.motionX == 0.0 && mc.thePlayer.motionZ == 0.0;
	}

	@EventTarget
	public void onEventRender3D(EventRender3D eventRender3D) {
		if (this.esp.getBoolean()) {
			GL11.glEnable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(2848);
			GL11.glDisable(2929);
			GL11.glDisable(3553);
			GlStateManager.disableCull();
			GL11.glDepthMask(false);
			float red = 1.0F;
			float green = .0F;
			float blue = 1.0F;
			float lineWidth = 0.0F;
			if (this.b != null) {
				if (mc.thePlayer.getDistance(this.b.getX(), this.b.getY(), this.b.getZ()) > 1.0) {
					double d0 = 1.0 - mc.thePlayer.getDistance(this.b.getX(), this.b.getY(), this.b.getZ()) / 20.0;
					if (d0 < 0.3) {
						d0 = 0.3;
					}

					lineWidth = (float) ((double) lineWidth * d0);
				}

				RenderUtil.drawBlockESP(this.b, red, green, blue, 0.3137255F, 1.0F, lineWidth);
			}

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDepthMask(true);
			GlStateManager.enableCull();
			GL11.glEnable(3553);
			GL11.glEnable(2929);
			GL11.glDisable(3042);
			GL11.glBlendFunc(770, 771);
			GL11.glDisable(2848);
		}
	}
}
