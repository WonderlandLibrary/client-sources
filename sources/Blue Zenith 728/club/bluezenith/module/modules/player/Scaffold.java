package club.bluezenith.module.modules.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.NoObf;
import club.bluezenith.module.modules.combat.Aura;
import club.bluezenith.module.modules.movement.Speed;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.client.MillisTimer;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.math.Range;
import club.bluezenith.util.math.Vec2d;
import club.bluezenith.util.player.MovementUtil;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.*;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

import static club.bluezenith.module.value.builders.AbstractBuilder.*;
import static net.minecraft.entity.Entity.strafeYaw;
import static net.minecraft.init.Blocks.*;

@SuppressWarnings({"unused"})
public class Scaffold extends Module {
	public Scaffold() {
		super("Scaffold", ModuleCategory.PLAYER, "BlockFly", "FeetPlace");
	}

	private final ModeValue rotationMode = createMode("Rotations")
			.index(1)
			.defaultOf("Normal")
			.range("Normal",
					"Static yaw",
					"Static yaw 2",
					"45deg yaw",
					"Hypixel Old",
					"Reduce",
					"Hypixel"
			).build();

	private final FloatValue searchRange = createFloat("Range")
			.index(2)
			.range(Range.of(1F, 5F))
			.defaultOf(3F)
			.increment(0.1F)
			.build();

	public final ListValue addons = createList("Addons")
			.index(3)
			.range(
					"Raytrace",
					"Silent rotations",
					"Keep rotations",
					"Rotation strafe",
					"Randomization",
					"Swing item",
					"Clamp facings",
					"Safe walk",
					"Keep Y",
					"Autoblock",
					"Sprint",
					"Blink"
			)
			.build();

	private final ModeValue safeWalkMode = createMode("Safe Walk")
			.index(4)
			.range("Ground, Always")
			.showIf(() -> addons.getOptionState("Safe walk"))
			.build();

	public final ModeValue sprintMode = createMode("Sprint")
			.index(5)
			.range("Normal, Boost, Hypixel Old")
			.showIf(() -> addons.getOptionState("Sprint"))
			.build();

	private final ModeValue sameYMode = createMode("Keep Y")
			.index(6)
			.range("Always, Speed Only, Moving Only")
			.showIf(() -> addons.getOptionState("Keep Y"))
			.build();

	private final ModeValue placeEvent = createMode("Place on")
			.index(7)
			.range("Pre, Post")
			.build();

	private final ModeValue blockCounter = createMode("Counter")
			.index(8)
			.range("Natasha, Simple")
			.build();

	private final ModeValue tower = createMode("Tower")
			.index(9)
			.range("Hypixel, None")
			.build();

	private final FloatValue yawFactor = createFloat("Yaw factor")
			.index(10)
			.range(Range.of(0F, 0.5F))
			.increment(0.01F)
			.showIf(() -> addons.getOptionState("Randomization"))
			.build();

	private final FloatValue pitchFactor = createFloat("Pitch factor")
			.index(11)
			.range(Range.of(0F, 0.5F))
			.increment(0.01F)
			.showIf(() -> addons.getOptionState("Randomization"))
			.build();

	private final FloatValue expandFactor = createFloat("Expand factor")
			.index(12)
			.range(Range.of(0F, 0.3F))
			.increment(0.01F)
			.showIf(() -> addons.getOptionState("Randomization"))
			.build();

	private final FloatValue timer = createFloat("Timer")
			.index(13)
			.range(Range.of(0.1F, 3F))
			.increment(0.05F)
			.build();

	private final BooleanValue towerTimer = createBoolean("Allow tower timer")
			.index(14)
			.defaultOf(false)
			.showIf(() -> timer.get() != 1.0F && !tower.is("None"))
			.build();

	public static float yawFakeLag = 0, pitchFakeLag = 0;

	private final MillisTimer rotationChangeTimer = new MillisTimer(),
							  bruh = new MillisTimer();

	private final LinkedBlockingQueue<Packet<?>> blinkPackets = new LinkedBlockingQueue<>();

	public EnumFacing nearestFace;

	public double distanceNearest, distanceCurrent;

	private double oldPlayerX, oldPlayerY, oldPlayerZ;

	private double nearestFaceZ, nearestFaceX, nearestFaceY, randomPlacePos = -1;

	private double prevRotationYaw, prevRotationPitch,
	  			   lastMotionX, lastMotionZ;


	private float oldPlayerYaw, oldPlayerPitch,
			      lastRandXZ;

	private int stackIndex = -1, prevStackIndex, blocksPlaced, offGroundTicks;

	private boolean setItemIndex, motionFlag, flag;

	public boolean isPlacing = false;

	private BlockPos nearestBlockPos;

	private final FontRenderer FR = FontUtil.SFLight42;

	private ItemStack currentStack = null;

	@NoObf
	@Listener
	public void onRender2D(Render2DEvent e) {
		if(currentStack == null || currentStack.getItem() == null) return;

		float x = e.resolution.getScaledWidth() / 2f - 10;
		float y = e.resolution.getScaledHeight() / 2f + 10;

		if(BlueZenith.isVirtueTheme) {
			final String count = String.valueOf(getStackCount());
			mc.fontRendererObj.drawString(count, (x + 10) - mc.fontRendererObj.getStringWidthF(count) / 2F, y, 220 << 8 | 20 | 255 << 24, true);
		} else {
			switch (blockCounter.get()) {
				case "Natasha":
					renderGarbageCounter(x, y);
					break;

				case "Simple":
					renderSimpleCounter(x, y);
					break;
			}
		}
	}

	private void renderSimpleCounter(float x, float y) {
		RenderHelper.enableGUIStandardItemLighting();
		mc.getRenderItem().renderItemAndEffectIntoGUI(currentStack, (int) x - 10, (int) y - 1);
		//mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, mc.thePlayer.getHeldItem(), e.resolution.getScaledWidth() / 2 - 10, e.resolution.getScaledHeight() - 65);
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		int stack = getStackCount();
		FR.drawString(String.valueOf(stack), x + 8, y + (7 - FR.FONT_HEIGHT / 2f) - 1, Color.WHITE.getRGB(), true);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.enableBlend();
		RenderHelper.disableStandardItemLighting();
	}

	private static int getStackCount() {
		int stack = 0;
		for (int i = 0; i < 9; i++) {
			final ItemStack stack1 = mc.thePlayer.inventory.mainInventory[i];
			if(stack1 == null || stack1.getItem() == null || !(stack1.getItem() instanceof ItemBlock)) continue;
			boolean continueNext = false;
			for (Block badBlock : badBlocks) {
				if(continueNext) continue;
				if(stack1.getItem().getUnlocalizedName().equals(badBlock.getUnlocalizedName())) continueNext = true;
			}
			if(continueNext) continue;
			stack += stack1.stackSize;
		}
		return stack;
	}

	private void renderGarbageCounter(float x, float y) {
		RenderHelper.enableGUIStandardItemLighting();
		final float division = FR.FONT_HEIGHT / 2f;
		int stack = getStackCount();
		final float l = FR.getStringWidthF(String.valueOf(stack)) - 2.5f;
		RenderUtil.rect(x + 16 + l, y - division + 1, x - 12.5, y - division + 2, ColorUtil.rainbow(1, 1));
		RenderUtil.rect(x + 16 + l, y + division + 10, x - 12.5, y - division + 2,  new Color(0, 0, 0, 165));
		mc.getRenderItem().renderItemAndEffectIntoGUI(currentStack, (int) x - 10, (int) y - 1);
		//mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, mc.thePlayer.getHeldItem(), e.resolution.getScaledWidth() / 2 - 10, e.resolution.getScaledHeight() - 65);
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		FR.drawString(String.valueOf(stack), x + 8, y + (7 - FR.FONT_HEIGHT / 2f) - 1, Color.WHITE.getRGB(), true);
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.enableBlend();
		RenderHelper.disableStandardItemLighting();
	}

	@Listener
	public void onPacket(PacketEvent ev) {
		// nigga tf
		// what's wrong
		int stackIndex = getCurrentBlock();
		if(ev.packet instanceof C09PacketHeldItemChange) {
			if(((C09PacketHeldItemChange) ev.packet).getSlotId() != stackIndex) {
				if(stackIndex != -1) {
					((C09PacketHeldItemChange) ev.packet).slotId = stackIndex;
				}
			}
		}
		if(ev.packet instanceof C08PacketPlayerBlockPlacement && addons.getOptionState("Clamp facings")) {
			final C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) ev.packet;

			packet.facingZ = (float)(nearestFaceZ - nearestBlockPos.getZ());
			packet.facingX = (float)(nearestFaceX - nearestBlockPos.getX());
			packet.facingY = (float)(nearestFaceY - nearestBlockPos.getY());

			if(packet.facingX >= 1F) {
				packet.facingX = 0.95f;
			}
			if(packet.facingY >= 1F) {
				packet.facingY = 0.95F;
			}
			if(packet.facingZ >= 1F) {
				packet.facingZ = 0.95F;
			}
		}
		if (addons.getOptionState("Blink")) {
			if (!isPlacing) {
				if (!blinkPackets.isEmpty())
				sendBlinkPackets();
			}
			else if (ev.direction == EnumPacketDirection.CLIENTBOUND && mc.thePlayer != null && mc.thePlayer.ticksExisted > 10 && ev.packet instanceof C03PacketPlayer) {
				blinkPackets.add(ev.packet);
				ev.cancel();
			}
		}
	}
	@Override
	public void onDisable() {
		if(currentStack != null) {
			changeItemToPrev();
		}
		if (addons.getOptionState("Blink")) {
			sendBlinkPackets();
		}
		mc.timer.timerSpeed = 1;
	}

	@Override
	public void onEnable() {
		blocksPlaced = offGroundTicks = 0;

		if (addons.getOptionState("Keep Y")) {
			BlueZenith.getBlueZenith().getModuleManager().getModule(Speed.class).setState(false);
		}

		if(player != null) {
			oldPlayerPitch = MathHelper.wrapAngleTo180_float(player.rotationPitch);
			oldPlayerYaw = MathHelper.wrapAngleTo180_float(player.rotationYaw);
		}
		BlueZenith.getBlueZenith().getModuleManager().getModule(Aura.class).setState(false);
	}

	private void sendBlinkPackets() {
		while (!blinkPackets.isEmpty()) {
			try {
				PacketUtil.sendSilent(blinkPackets.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Listener
	public void pleasePullBeforeVomiting(MoveEvent e){
		if (e.isPost()) return;
		if(addons.getOptionState("Safe walk"))
			e.safeWalkState = safeWalkMode.is("Always") ? 2 : 1;
	}

	@Listener
	public void onUpdatePlayer(UpdatePlayerEvent e) {
		stackIndex = getCurrentBlock();

		// strafeYaw = mc.thePlayer.rotationYaw;
		if(stackIndex == -1) {
			BlueZenith.getBlueZenith().getNotificationPublisher().postError(displayName, "No blocks found in your hotbar.", 2500);
			this.setState(false);
			return;
		}
		doAutoBlock();
		
		final float r = searchRange.get();
		final boolean itemCheck = currentStack != null
				&& currentStack.getItem() != null
				&& currentStack.getItem() instanceof ItemBlock
				&& ( !addons.getOptionState("Autoblock")
				    || mc.thePlayer.inventory.currentItem == stackIndex
		);
		final boolean isPlayerMovingY = oldPlayerY != mc.thePlayer.posY;
		// ClientUtils.debug(itemCheck); orange cluck broke scaffold
		final boolean isPlayerMovingXZ = true;

		if ((isPlayerMovingY || isPlayerMovingXZ) && itemCheck) {

			if(player.onGround)
				offGroundTicks = 0;
			else if(e.isPre())
				offGroundTicks++;


			/*if(e.isPre() && player.onGround)
				player.jump(0.37);*/
			/*if (*/tryToPlaceABlock(e, r, isPlayerMovingXZ);/*) return;*/

		} else if (currentStack == null || currentStack.stackSize <= 0) {
			changeItemToPrev();
			if (addons.getOptionState("Keep rotations")) {
				setKeepRotations(e);
			} else {
				strafeYaw = mc.thePlayer.rotationYaw;
				yawFakeLag = mc.thePlayer.rotationYaw;
				pitchFakeLag = mc.thePlayer.rotationPitch;
			}
		}
		else {
			if (addons.getOptionState("Keep rotations")) {
				setKeepRotations(e);
			} else {
				strafeYaw = mc.thePlayer.rotationYaw;
				yawFakeLag = mc.thePlayer.rotationYaw;
				pitchFakeLag = mc.thePlayer.rotationPitch;
			}
		}
		oldPlayerX = mc.thePlayer.posX;
		oldPlayerY = mc.thePlayer.posY;
		oldPlayerZ = mc.thePlayer.posZ;
	}

	@Listener
	public void onMove(MoveEvent e) {
		if (e.isPost() || !mc.thePlayer.onGround) return;
		if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			mc.thePlayer.motionX *= getCastedModule(Speed.class).getState() ? 0.9 : 0.7;
			mc.thePlayer.motionZ *= getCastedModule(Speed.class).getState() ? 0.9 : 0.7;
		}
		if (sprintMode.is("Hypixel Old")) {
			if (isPlacing) {
				PacketUtil.sendSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
				lastMotionX = mc.thePlayer.motionX;
				lastMotionZ = mc.thePlayer.motionZ;
				mc.thePlayer.motionX *= 0.3;
				mc.thePlayer.motionZ *= 0.3;
				motionFlag = true;
			} else if (motionFlag) {
				//PacketUtil.sendSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
				mc.thePlayer.motionX = lastMotionX * 0.65;
				mc.thePlayer.motionZ = lastMotionZ * 0.65;
				motionFlag = false;
			}
		}
	}

	private void changeItemToPrev() {
		if(addons.getOptionState("Autoblock") && setItemIndex) {
			mc.thePlayer.inventory.currentItem = prevStackIndex;
			setItemIndex = false;

		} /*else if(autoBlockMode.get().equals("Silent") && setItemIndex) {
			mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
			setItemIndex = false;
		}*/
	}

	private void doAutoBlock() {
		if(stackIndex != -1 || stackIndex != mc.thePlayer.inventory.currentItem){
			if(stackIndex != mc.thePlayer.inventory.currentItem) setItemIndex = false;
			if(!setItemIndex){
				/*if(autoBlockMode.get().equals("Silent")) {
					prevStackIndex = mc.thePlayer.inventory.currentItem;
					mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(stackIndex));
				}
				else */if(addons.getOptionState("Autoblock") && stackIndex <= 8 && stackIndex >= 0){
					prevStackIndex = mc.thePlayer.inventory.currentItem;
					mc.thePlayer.inventory.currentItem = stackIndex;
				} else if(addons.getOptionState("Autoblock")) {
					setState(false);
					BlueZenith.getBlueZenith().getNotificationPublisher().postError(displayName, "No blocks found in your hotbar.", 2500);
				}
				setItemIndex = true;
			}
			currentStack = !addons.getOptionState("Autoblock") ? mc.thePlayer.getHeldItem() : mc.thePlayer.inventory.mainInventory[stackIndex];
		}else{
			currentStack = null;
		}
	}

	private boolean tryToPlaceABlock(UpdatePlayerEvent e, float r, boolean isPlayerMovingXZ) {
		final ArrayList<BlockPos> le_fullBlockPoses = getAllBlockPoses(r);

		if (le_fullBlockPoses.isEmpty()) {
			yawFakeLag = mc.thePlayer.rotationYaw;
			pitchFakeLag = mc.thePlayer.rotationPitch;
			return true;
		}

		nearestBlockPos = getNearestBlockPos(le_fullBlockPoses);
		final Block nearestBlock = mc.theWorld.getBlockState(nearestBlockPos).getBlock();
		final double diffX = mc.thePlayer.posX - (nearestBlockPos.getX() + 0.5);
		final double diffZ = mc.thePlayer.posZ - (nearestBlockPos.getZ() + 0.5);

		nearestFaceZ = nearestBlockPos.getZ() + 0.5;
		nearestFaceX = nearestBlockPos.getX() + 0.5;
		nearestFaceY = nearestBlockPos.getY() + 0.5;

		if (randomPlacePos == -1) {
			randomPlacePos = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(0.05f, expandFactor.get()) : 0;
		}

		if (e.isPre() && this.tower.is("Hypixel")) {
			if (mc.gameSettings.keyBindJump.pressed) {
				if(towerTimer.isVisible() && !towerTimer.get())
					mc.timer.timerSpeed = 1;
				if (this.blocksPlaced % 3 == 0) {
					PacketUtil.sendSilent(
							new C08PacketPlayerBlockPlacement(
									new BlockPos(player.posX, player.posY, player.posZ).down(),
									EnumFacing.UP.getIndex(),
									null,
									0.0F,
									(float) (Math.random() / 5.0),
									0.0F
							)
					);
				}
				mc.thePlayer.setSprinting(false);
				if (mc.thePlayer.posY % 1.0 <= 0.00153598) {
					mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
					mc.thePlayer.motionY = (double) 0.42f - randomY() / 2.0;
				} else if (mc.thePlayer.posY % 1.0 < 0.1 && this.offGroundTicks != 0) {
					mc.thePlayer.setPosition(mc.thePlayer.posX, Math.floor(mc.thePlayer.posY), mc.thePlayer.posZ);
				}
				e.y += Math.random() / 10000.0;
			}
		}

		if(!this.tower.is("Hypixel") || !mc.gameSettings.keyBindJump.pressed || (!towerTimer.isVisible() || towerTimer.get()))
			mc.timer.timerSpeed = timer.get();

		if (Math.max(Math.abs(diffX), Math.abs(diffZ)) > 0.5 + randomPlacePos) {
			final int distanceCompare = Double.compare(Math.abs(diffX), Math.abs(diffZ));

			float randRotHorizontal = 0;
			float randRotVertical = 0;

			if (rotationMode.is("Hypixel")) {
				if (lastRandXZ + 0.025f > 0.3f || lastRandXZ - 0.025f < -0.3f) {
					flag = !flag;
				}

				if (flag) {
					randRotHorizontal = lastRandXZ + 0.025f/* + ClientUtils.getRandomFloat(-0.01f, 0.01f)*/;
					lastRandXZ = randRotHorizontal;
				} else {
					randRotHorizontal = lastRandXZ - 0.025f /*+ ClientUtils.getRandomFloat(-0.01f, 0.01f)*/;
					lastRandXZ = randRotHorizontal;
				}
				randRotVertical = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-pitchFactor.get(), pitchFactor.get()) : 0;
			}
			else {
				randRotHorizontal = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-yawFactor.get(), yawFactor.get()) : 0;
				randRotVertical = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-pitchFactor.get(), pitchFactor.get()) : 0;
			}

			setFaceCoords(diffX, diffZ, distanceCompare, randRotHorizontal, randRotVertical);
			randomPlacePos = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(0.05f, expandFactor.get()) : 0;

			isPlacing = true;
		} else if (((!addons.getOptionState("Keep Y") || sameYMode.is("Speed Only") && !getCastedModule(Speed.class).getState() || sameYMode.is("Moving Only") && !MovementUtil.areMovementKeysPressed()) || (mc.gameSettings.keyBindJump.pressed && !isPlayerMovingXZ)) && mc.thePlayer.posY - nearestBlockPos.getY() >= 2) {
			//final float randRotHorizontal = randomization.get() ? ClientUtils.getRandomFloat(-RR_Horizontal.get(), RR_Horizontal.get()) : 0;
			float randRotHorizontal = 0;
			float randRotVertical = 0;

			if (rotationMode.is("Hypixel")) {
				if (lastRandXZ + 0.025f > 0.3f || lastRandXZ - 0.025f < -0.3f) {
					flag = !flag;
				}

				if (flag) {
					randRotHorizontal = lastRandXZ + 0.025f/* + ClientUtils.getRandomFloat(-0.01f, 0.01f)*/;
					lastRandXZ = randRotHorizontal;
				} else {
					randRotHorizontal = lastRandXZ - 0.025f/* + ClientUtils.getRandomFloat(-0.01f, 0.01f)*/;
					lastRandXZ = randRotHorizontal;
				}
				randRotVertical = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-pitchFactor.get(), pitchFactor.get()) : 0;
			}
			else {
				randRotHorizontal = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-yawFactor.get(), yawFactor.get()) : 0;
				randRotVertical = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-pitchFactor.get(), pitchFactor.get()) : 0;
			}

			nearestFace = EnumFacing.UP;
			nearestFaceZ = nearestBlockPos.getZ() + 0.5 + randRotHorizontal;
			nearestFaceX = nearestBlockPos.getX() + 0.5 + randRotHorizontal;
			nearestFaceY = nearestBlockPos.getY() + 1;
			isPlacing = true;

		} else {
			if (addons.getOptionState("Keep rotations")) {
				setKeepRotations(e);
			} else {
				strafeYaw = mc.thePlayer.rotationYaw;
				yawFakeLag = mc.thePlayer.rotationYaw;
				pitchFakeLag = mc.thePlayer.rotationPitch;
			}

			nearestFace = null;
			isPlacing = false;
		}

		BlockPos bruh = null;

		if (nearestFace == null) return true;
		bruh = getBlockPlacePos(bruh);

		if (nearestFace != null && mc.theWorld.canBlockBePlaced(nearestBlock, bruh, false, nearestFace, mc.thePlayer, mc.thePlayer.getHeldItem())) {
			rotate(e, nearestFaceZ, nearestFaceX, nearestFaceY, nearestBlock, nearestBlockPos);
		}


		le_fullBlockPoses.clear();
		return false;
	}

	public BlockPos getBlockPlacePos(BlockPos bruh) {
		switch (nearestFace) {
			case NORTH:
				bruh = new BlockPos(nearestBlockPos.getX(), nearestBlockPos.getY(), nearestBlockPos.getZ() - 1); // 0
				break;
			case SOUTH:
				bruh = new BlockPos(nearestBlockPos.getX(), nearestBlockPos.getY(), nearestBlockPos.getZ() + 1); // -180
				break;
			case WEST:
				bruh = new BlockPos(nearestBlockPos.getX() - 1, nearestBlockPos.getY(), nearestBlockPos.getZ()); // -90 yaw
				break;
			case EAST:
				bruh = new BlockPos(nearestBlockPos.getX() + 1, nearestBlockPos.getY(), nearestBlockPos.getZ()); // 90 yaw
				break;
			case UP:
				bruh = new BlockPos(nearestBlockPos.getX(), nearestBlockPos.getY() + 1, nearestBlockPos.getZ()); // northeast 45 northwest -45 southwest -135 southeast 135
		}
		return bruh;
	}

	private void setKeepRotations(UpdatePlayerEvent e) {
		switch (rotationMode.get()) {
			case "Static yaw":
				e.yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 180);
				break;
			case "Static yaw 2":
				e.yaw = (float) weirdshit(oldPlayerYaw);
				break;
			case "Hypixel Old":
				e.yaw = mc.thePlayer.rotationYaw + (float) prevRotationYaw;
				break;
			default:
				e.yaw = MathHelper.wrapAngleTo180_float(oldPlayerYaw);
				break;
		}
		yawFakeLag = e.yaw;
		pitchFakeLag = oldPlayerPitch;

		if (addons.getOptionState("Rotation strafe"))
			strafeYaw = e.yaw - 180;
		else
			strafeYaw = mc.thePlayer.rotationYaw;

		e.pitch = oldPlayerPitch;
	}

	private void setFaceCoords(double diffX, double diffZ, int distanceCompare, float randRotHorizontal, float randRotVertical) {
		if(distanceCompare < 0) {
			if(diffZ <= 0) {
				nearestFace = EnumFacing.NORTH;
				nearestFaceZ = nearestBlockPos.getZ();
				nearestFaceX = nearestBlockPos.getX() + 0.5 + randRotHorizontal;
				nearestFaceY = nearestBlockPos.getY() + 0.5 + randRotVertical;
			} else {
				nearestFace = EnumFacing.SOUTH;
				nearestFaceZ = nearestBlockPos.getZ() + 1.0;
				nearestFaceX = nearestBlockPos.getX() + 0.5 + randRotHorizontal;
				nearestFaceY = nearestBlockPos.getY() + 0.5 + randRotVertical;
			}
			return;
		}
		if(diffX <= 0) {
			nearestFace = EnumFacing.WEST;
			nearestFaceZ = nearestBlockPos.getZ() + 0.5 + randRotHorizontal;
			nearestFaceX = nearestBlockPos.getX();
			nearestFaceY = nearestBlockPos.getY() + randRotVertical;
		} else {
			nearestFaceZ = nearestBlockPos.getZ() + 0.5 + randRotHorizontal;
			nearestFaceX = nearestBlockPos.getX() + 1.0;
			nearestFaceY = nearestBlockPos.getY() + randRotVertical;
			nearestFace = EnumFacing.EAST;
		}
	}

	private void rotate(UpdatePlayerEvent e, double nearestFaceZ, double nearestFaceX, double nearestFaceY, Block nearestBlock, BlockPos nearestBlockPos) {

		final Vec3 eyesPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
		final Vec3 facePos = new Vec3(nearestFaceX, nearestFaceY, nearestFaceZ);

		final double faceDiffX = facePos.xCoord - eyesPos.xCoord;
		final double faceDiffY = facePos.yCoord - eyesPos.yCoord;
		final double faceDiffZ = facePos.zCoord - eyesPos.zCoord;

		double rotationYaw = 0;
		double rotationPitch = 0;

		switch (rotationMode.get()) {
			case "Hypixel":
			case "Normal":
				rotationYaw = MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(faceDiffZ, faceDiffX)) - 90F);
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				break;
			case "Static yaw":
				rotationYaw = MathHelper.wrapAngleTo180_double(mc.thePlayer.rotationYaw - 180);
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				break;
			case "Static yaw 2":
				rotationYaw = weirdshit(oldPlayerYaw);
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				break;
			case "45deg yaw":
				rotationYaw = get45degYaw(faceDiffX, faceDiffZ, nearestFace);
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				break;
			case "Hypixel Old":
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				float normalYaw = (float) MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(faceDiffZ, faceDiffX)) - 90F);
				rotationYaw = getHanabiYaw(nearestFace, rotationPitch, eyesPos, normalYaw);
				break;
			case "Reduce":
				rotationYaw = mc.thePlayer.rotationYaw - (mc.thePlayer.rotationYaw % 360) + MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(faceDiffZ, faceDiffX)) - 90F);
				rotationPitch = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY, Math.sqrt(faceDiffX * faceDiffX + faceDiffZ * faceDiffZ)))));
				Vec2d vec2 = getReduceRotation(rotationPitch, rotationYaw, eyesPos, faceDiffX, faceDiffY, faceDiffZ);
				rotationYaw = vec2.y;
				rotationPitch = vec2.x;
				break;
		}

		float yawSpeed = (float) (rotationYaw - oldPlayerYaw);
		float yawSpeedMinus360 = (float) ((rotationYaw - 360) - oldPlayerYaw);
		float yawSpeedPlus360 = (float) ((rotationYaw + 360) - oldPlayerYaw);

		float pitchSpeed = (float) (rotationPitch - oldPlayerPitch);

		strafeYaw = addons.getOptionState("Rotation strafe") ? MathHelper.wrapAngleTo180_float((float) rotationYaw - 180) : mc.thePlayer.rotationYaw;


		double newyaw = 0;
		double newpitch = 0;
		final float rand = MathUtil.getRandomFloat(-0.5f, 0.5f);
		if (rotationMode.is("Hypixel")) {
			if (Math.abs(yawSpeedMinus360) < Math.abs(yawSpeed) && Math.abs(yawSpeedMinus360) < Math.abs(yawSpeedPlus360)) {
				if (Math.abs(yawSpeedMinus360) < 39 + rand) {
					newyaw = rotationYaw;
				} else {
					newyaw = oldPlayerYaw + ((yawSpeedMinus360 / Math.abs(yawSpeedMinus360)) * (39 + rand));
					//ClientUtils.fancyMessage("toofas");
				}
			} else if (Math.abs(yawSpeed) < Math.abs(yawSpeedMinus360) && Math.abs(yawSpeed) < Math.abs(yawSpeedPlus360)) {
				if (Math.abs(yawSpeed) < 39 + rand) {
					newyaw = rotationYaw;
				} else {
					newyaw = oldPlayerYaw + ((yawSpeed / Math.abs(yawSpeed)) * (39 + rand));
					//ClientUtils.fancyMessage("toofas");
				}
			} else if (Math.abs(yawSpeedPlus360) < Math.abs(yawSpeed) && Math.abs(yawSpeedPlus360) < Math.abs(yawSpeedMinus360)) {
				if (Math.abs(yawSpeedPlus360) < 39 + rand) {
					newyaw = rotationYaw;
				} else {
					newyaw = oldPlayerYaw + ((yawSpeedPlus360 / Math.abs(yawSpeedPlus360)) * (39 + rand));
					//ClientUtils.fancyMessage("toofas");
				}
			}

			if (Math.abs(pitchSpeed) < 39 + rand) {
				newpitch = rotationPitch;
			} else {
				newpitch = oldPlayerPitch + ((pitchSpeed / Math.abs(pitchSpeed)) * (39 + rand));
				//ClientUtils.fancyMessage("toofas");
			}
		}
		else {
			newyaw = rotationYaw;
			newpitch = rotationPitch;
		}

		if (e.isPre()) {
			if (addons.getOptionState("Silent rotations")) {
				e.yaw = (float) newyaw;
				e.pitch = (float) newpitch;
				//ClientUtils.fancyMessage(MathUtil.round(e.yaw, 2));
			} else {
				mc.thePlayer.rotationYaw = (float) newyaw;
				mc.thePlayer.rotationPitch = (float) newpitch;
			}
		}
		yawFakeLag = (float) newyaw;
		pitchFakeLag = (float) newpitch;

		oldPlayerYaw = (float) newyaw;
		oldPlayerPitch = (float) newpitch;

		Vec3 vec31 = mc.thePlayer.getVectorForRotation((float) newpitch, (float) newyaw);
		Vec3 vec32 = eyesPos.addVector(vec31.xCoord * 5F, vec31.yCoord * 5F, vec31.zCoord * 5F);
		final MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(eyesPos, vec32, false, false, true);
		//final MovingObjectPosition raytraceResult = nearestBlock.collisionRayTrace(mc.theWorld, nearestBlockPos, eyesPos, vec32);

		if (!(newyaw == rotationYaw) || !(newpitch == rotationPitch)) return;
		if (!addons.getOptionState("Raytrace") || (rayTraceResult != null && rayTraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && rayTraceResult.sideHit == nearestFace)) {
			//PacketUtil.sendSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
			if (e.isPre() && tower.is("Hypixel")) {
				if(mc.gameSettings.keyBindJump.pressed/* && player.posY - nearestBlockPos.getY() < 2.015 */&& nearestFace == EnumFacing.UP) {
					mc.thePlayer.motionY = 0.39;
				}
			}


			if (e.isPost() && placeEvent.is("Post") || placeEvent.is("Pre")) {
				if (addons.getOptionState("Swing item"))
					mc.thePlayer.swingItem();
				else PacketUtil.sendSilent(new C0APacketAnimation());
				//PacketUtil.sendSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));

				blocksPlaced++;
				mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), nearestBlockPos, nearestFace, rayTraceResult.hitVec);
			}
			//mc.rightClickMouse();
			//PacketUtil.sendSilent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
		}
	}

	private Vec2d getReduceRotation(double rotationPitch, double rotationYaw, Vec3 eyesPos, double faceDiffX, double faceDiffY, double faceDiffZ) {
		Vec3 vec31 = mc.thePlayer.getVectorForRotation((float) prevRotationPitch, (float) prevRotationYaw);
		Vec3 vec32 = eyesPos.addVector(vec31.xCoord * 5F, vec31.yCoord * 5F, vec31.zCoord * 5F);
		MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(eyesPos, vec32, false, false, true);

		final boolean hit1check = rayTraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && rayTraceResult.sideHit == nearestFace;

		if (hit1check) {
			return new Vec2d(prevRotationPitch, prevRotationYaw);
		}
		else if (rotationChangeTimer.hasTimeReached(200)){

			MovingObjectPosition raytraceResult1 = null;
			double rotationYaw1 = 0;
			double rotationPitch1 = 0;
			int i = 0;

			while (raytraceResult1 == null
					|| raytraceResult1.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK
					&& raytraceResult1.sideHit != nearestFace
					&& i < 10)
			{
				double nearestFaceX1 = 0;
				double nearestFaceY1 = 0;
				double nearestFaceZ1 = 0;

				final float randRotHorizontal1 = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-yawFactor.get(), yawFactor.get()) : 0;
				final float randRotVertical1 = addons.getOptionState("Randomization") ? MathUtil.getRandomFloat(-pitchFactor.get(), pitchFactor.get()) : 0;

				switch (nearestFace) {
					case NORTH:
						nearestFaceZ1 = nearestBlockPos.getZ();
						nearestFaceX1 = nearestBlockPos.getX() + 0.5 + randRotHorizontal1;
						nearestFaceY1 = nearestBlockPos.getY() + 0.5 + randRotVertical1;
						break;
					case SOUTH:
						nearestFaceZ1 = nearestBlockPos.getZ() + 1.0;
						nearestFaceX1 = nearestBlockPos.getX() + 0.5 + randRotHorizontal1;
						nearestFaceY1 = nearestBlockPos.getY() + 0.5 + randRotVertical1;
						break;
					case WEST:
						nearestFaceZ1 = nearestBlockPos.getZ() + 0.5 + randRotHorizontal1;
						nearestFaceX1 = nearestBlockPos.getX();
						nearestFaceY1 = nearestBlockPos.getY() + randRotVertical1;
						break;
					case EAST:
						nearestFaceZ1 = nearestBlockPos.getZ() + 0.5 + randRotHorizontal1;
						nearestFaceX1 = nearestBlockPos.getX() + 1.0;
						nearestFaceY1 = nearestBlockPos.getY() + randRotVertical1;
						break;
					case UP:
						nearestFaceZ1 = nearestBlockPos.getZ() + 0.5 + randRotHorizontal1;
						nearestFaceX1 = nearestBlockPos.getX() + 0.5 + randRotHorizontal1;
						nearestFaceY1 = nearestBlockPos.getY() + 1;
						break;
				}

				final double faceDiffX1 = nearestFaceX1 - eyesPos.xCoord;
				final double faceDiffY1 = nearestFaceY1 - eyesPos.yCoord;
				final double faceDiffZ1 = nearestFaceZ1 - eyesPos.zCoord;

				final Vec3 facePos = new Vec3(nearestFaceX1, nearestFaceY1, nearestFaceZ1);

				rotationYaw1 = mc.thePlayer.rotationYaw - (mc.thePlayer.rotationYaw % 360) + MathHelper.wrapAngleTo180_double(Math.toDegrees(Math.atan2(faceDiffZ1, faceDiffX1)) - 90F);
				rotationPitch1 = MathHelper.wrapAngleTo180_double((-Math.toDegrees(Math.atan2(faceDiffY1, Math.sqrt(faceDiffX1 * faceDiffX1 + faceDiffZ1 * faceDiffZ1)))));

				Vec3 vec311 = mc.thePlayer.getVectorForRotation((float) rotationPitch1, (float) rotationYaw1);
				Vec3 vec321 = eyesPos.addVector(vec311.xCoord * 5F, vec311.yCoord * 5F, vec311.zCoord * 5F);
				raytraceResult1 = mc.theWorld.rayTraceBlocks(eyesPos, vec321, false, false, true);
				i++;
			}

			//ClientUtils.fancyMessage(i);
			prevRotationPitch = rotationPitch1;
			prevRotationYaw = rotationYaw1;
			rotationChangeTimer.reset();
			return new Vec2d(rotationPitch1, rotationYaw1);
		}
		return new Vec2d(prevRotationPitch, prevRotationYaw);
	}

	private double getHanabiYaw(EnumFacing nearestFace, double rotationPitch, Vec3 eyesPos, float rotationYaw) {
		Vec3 vec31 = mc.thePlayer.getVectorForRotation((float) rotationPitch, MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 135));
		Vec3 vec32 = eyesPos.addVector(vec31.xCoord * 5F, vec31.yCoord * 5F, vec31.zCoord * 5F);
		MovingObjectPosition rayTraceResult = mc.theWorld.rayTraceBlocks(eyesPos, vec32, false, false, true);

		vec31 = mc.thePlayer.getVectorForRotation((float) rotationPitch, MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 225));
		vec32 = eyesPos.addVector(vec31.xCoord * 5F, vec31.yCoord * 5F, vec31.zCoord * 5F);
		MovingObjectPosition rayTraceResult2 = mc.theWorld.rayTraceBlocks(eyesPos, vec32, false, false, true);

		final boolean hit1check = rayTraceResult.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && rayTraceResult.sideHit == nearestFace;
		final boolean hit2check = rayTraceResult2.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && rayTraceResult2.sideHit == nearestFace;

		boolean timeReached = rotationChangeTimer.hasTimeReached(200);
		if (hit1check && hit2check)
		{
			float diff1 = Math.abs(mc.thePlayer.rotationYaw - 135 - rotationYaw);
			float diff2 = Math.abs(mc.thePlayer.rotationYaw + 135 - rotationYaw);
			int comparison = Float.compare(diff1, diff2);

			if (comparison >= 0 && (timeReached || prevRotationYaw == -135)) {
				if (timeReached) rotationChangeTimer.reset();
				prevRotationYaw = -135;
				return mc.thePlayer.rotationYaw - 135;
			}
			else if (comparison < 0 && (timeReached || prevRotationYaw == 135)){
				if (timeReached) rotationChangeTimer.reset();
				prevRotationYaw = 135;
				return mc.thePlayer.rotationYaw + 135;
			}
		}

		if (hit1check && (timeReached || prevRotationYaw == -135)) {
			if (timeReached) rotationChangeTimer.reset();
			prevRotationYaw = -135;
			return mc.thePlayer.rotationYaw - 135;
		}

		if (hit2check && (timeReached || prevRotationYaw == 135)) {
			if (timeReached) rotationChangeTimer.reset();
			prevRotationYaw = 135;
			return mc.thePlayer.rotationYaw + 135;
		}

		return mc.thePlayer.rotationYaw + prevRotationYaw;
	}

	private double get45degYaw(double faceDiffX, double faceDiffZ, EnumFacing nearestFace) { // northeast 45 northwest -45 southwest -135 southeast 135
		switch (nearestFace) {
			case NORTH:
				if (Math.abs(faceDiffX) > 0.25) {
					if (faceDiffX < 0) {
						return 45;
					}
					else {
						return -45;
					}
				}
				else {
					return 0;
				}
			case SOUTH:
				if (Math.abs(faceDiffX) > 0.25) {
					if (faceDiffX < 0) {
						return 135;
					}
					else {
						return -135;
					}
				}
				else {
					return -180;
				}
			case WEST:
				if (Math.abs(faceDiffZ) > 0.25) {
					if (faceDiffZ < 0) {
						return -135;
					}
					else {
						return -45;
					}
				}
				else {
					return -90;
				}
			case EAST:
				if (Math.abs(faceDiffZ) > 0.25) {
					if (faceDiffZ < 0) {
						return 135;
					}
					else {
						return 45;
					}
				}
				else {
					return 90;
				}
			case UP:
				break;
		}
		return 0;
	}

	private double weirdshit(double rotationYaw) {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) && Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 45 + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_S)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 135 + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 135 + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_W)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 45 + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 90 + 180);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			rotationYaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw - 90 + 180);
		}
		else {
			return MathHelper.wrapAngleTo180_double(rotationYaw);
		}
		return rotationYaw;
	}

	private BlockPos getNearestBlockPos(ArrayList<BlockPos> le_fullBlockPoses) {
		BlockPos nearestBlockPos = null;
		for (BlockPos blockPos : le_fullBlockPoses) {
			final Block block = mc.theWorld.getBlockState(blockPos).getBlock();

			final double playerCurrentXdiff = Math.abs(mc.thePlayer.posX - (blockPos.getX() + 0.5));
			final double playerCurrentYdiff = Math.abs(mc.thePlayer.posY - (blockPos.getY() + 0.5));
			final double playerCurrentZdiff = Math.abs(mc.thePlayer.posZ - (blockPos.getZ() + 0.5));

			distanceCurrent = (playerCurrentXdiff * playerCurrentXdiff) + (playerCurrentYdiff * playerCurrentYdiff) + (playerCurrentZdiff * playerCurrentZdiff);

			if (distanceNearest > distanceCurrent || nearestBlockPos == null) {
				nearestBlockPos = blockPos;
				distanceNearest = distanceCurrent;
			}

		}
		return nearestBlockPos;
	}

	public static final List<Block> badBlocks = Lists.newArrayList(
			enchanting_table, ladder, Blocks.torch, Blocks.web, Blocks.beacon, Blocks.glass, Blocks.stained_glass, Blocks.stained_glass_pane,
			Blocks.ender_chest, Blocks.chest, Blocks.glass_pane, ladder, Blocks.cactus, Blocks.glass, Blocks.red_mushroom,
			Blocks.brown_mushroom, Blocks.tallgrass, Blocks.yellow_flower, Blocks.red_flower, Blocks.carpet,
			Blocks.sapling, Blocks.double_plant, Blocks.tnt, Blocks.trapped_chest, Blocks.slime_block, Blocks.stone_stairs,
			Blocks.sandstone_stairs, Blocks.oak_stairs, Blocks.acacia_stairs, Blocks.jungle_stairs, Blocks.brick_stairs,
			Blocks.spruce_stairs, Blocks.red_sandstone_stairs, Blocks.sand, Blocks.gravel, Blocks.rail,
			Blocks.golden_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.detector_rail, Blocks.end_portal_frame, Blocks.iron_bars, Blocks.wooden_pressure_plate,
			Blocks.stone_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.stone_slab, stone_slab2, Blocks.wooden_slab
	);

	private ArrayList<BlockPos> removeAir(ArrayList<BlockPos> le_blockPoses) {
		ArrayList<BlockPos> le_fullBlockPoses = new ArrayList<>();
		for (final BlockPos block_pos : le_blockPoses) {
			final Block block = mc.theWorld.getBlockState(block_pos).getBlock();
			if(block.isFullBlock() && !badBlocks.contains(block) && !block.getUnlocalizedName().contains("stairs") && !block.getUnlocalizedName().contains("slab")) le_fullBlockPoses.add(block_pos);
		}
		return le_fullBlockPoses;
	}

	private ArrayList<BlockPos> getAllBlockPoses(float r) {
		ArrayList<BlockPos> le_blockPoses = new ArrayList<>();
		for (double x = (mc.thePlayer.posX - r); x < (mc.thePlayer.posX + r); x++) {
			for (double y = (mc.thePlayer.posY - r); y < mc.thePlayer.posY; y++) {
				for (double z = (mc.thePlayer.posZ - r); z < (mc.thePlayer.posZ + r); z++) {
					BlockPos pos = new BlockPos(x, y, z);
					if(mc.theWorld.getBlockState(pos).getBlock().isFullBlock())
						le_blockPoses.add(pos);
				}
			}
		}
		return le_blockPoses;
	}

	private int getCurrentBlock() {
		if(mc.thePlayer == null) return -1;
		for (int z = 0; z < mc.thePlayer.inventory.mainInventory.length ; z++) {
			ItemStack i = mc.thePlayer.inventory.mainInventory[z];
			if(i != null && i.getItem() != null && i.getItem() instanceof ItemBlock && !badBlocks.contains(((ItemBlock) i.getItem()).getBlock())){
				return z;
			}
		}
		return -1;
	}

	private static double randomDouble(double min, double max) {
		if (min == max) {
			return min;
		}
		if (min > max) {
			double tempDouble = min;
			min = max;
			max = tempDouble;
		}
		return ThreadLocalRandom.current().nextDouble(min, max);
	}

	private static double randomY() {
		SecureRandom secureRandom = new SecureRandom();
		double generated = secureRandom.nextDouble() * (1.0 / (double)System.currentTimeMillis());
		int n2 = 0;
		while ((double)n2 < randomDouble(randomDouble(4, 6), randomDouble(8, 20))) {
			generated *= 1.0 / (double)System.currentTimeMillis();
			++n2;
		}
		return generated;
	}
}