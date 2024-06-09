/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.vecmath.Vector3d;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.opengl.GL11;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventRender3D;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.DrawUtils3D;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import cafe.kagu.kagu.utils.WorldUtils;
import cafe.kagu.kagu.utils.WorldUtils.PlaceOnInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.WorldSettings.GameType;

/**
 * @author lavaflowglow
 *
 */
public class ModScaffold extends Module {
	
	public ModScaffold() {
		super("Scaffold", Category.MOVEMENT);
		setSettings(rotationMode, c08Position, itemMode, swingMode, vec3Mode, rayTraceMissMode, maxBlockReach,
				delayBlockPlacements, delayPlacedBlockUpdateTicks, keepY, visuals3D, visuals2D, safewalk, ignoreWorldBorder, sprint, silentRotations,
				blockSwitchDelay, extend, extendDistance, tower, towerMode);
	}
	
	private ModeSetting rotationMode = new ModeSetting("Rotation Mode", "None", "None", "Lock", "Snap", "Hypixel", "Vulcan", "Negativity");
	private ModeSetting c08Position = new ModeSetting("C08 Position", "PRE", "PRE", "POST"); // PRE sends before the c03, this is default mc behaviour. POST sends after the c03, this isn't default behaviour but may bypass other anticheats
	private ModeSetting itemMode = new ModeSetting("Item Selection", "Server", "Server", "Synced", "Spoof");
	private ModeSetting swingMode = new ModeSetting("Swing Mode", "Server", "Server", "Synced", "No Swing");
	private ModeSetting vec3Mode = new ModeSetting("Vec3 Calculation", "Origin", "Origin", "Center", "Rand >=0 <=1", "Rand >=0.2 <=0.8", "Raytrace");
	private ModeSetting rayTraceMissMode = (ModeSetting) new ModeSetting("Vec3 Raytrace Miss Backup", "Origin", "Origin", "Center", "Rand >=0 <=1", "Rand >=0.2 <=0.8").setDependency(() -> vec3Mode.is("Raytrace"));
	
	private BooleanSetting keepY = new BooleanSetting("Keep Y", false);
	private BooleanSetting visuals3D = new BooleanSetting("3D Visuals", true);
	private BooleanSetting visuals2D = new BooleanSetting("2D Visuals", true);
	private BooleanSetting safewalk = new BooleanSetting("Safewalk", true);
	private BooleanSetting ignoreWorldBorder = new BooleanSetting("Ignore World Border", false);
	private BooleanSetting sprint = new BooleanSetting("Allow Sprint", false);
	private BooleanSetting silentRotations = new BooleanSetting("Silent Rotations", true);
	
	// Additional Switch settings
	private IntegerSetting blockSwitchDelay = new IntegerSetting("Block Switch Delay", 50, 0, 400, 10);
	
	private DoubleSetting maxBlockReach = new DoubleSetting("Max Block Reach", 3, 1, 6, 0.5);
	private IntegerSetting delayBlockPlacements = new IntegerSetting("Skip Block Placement Ticks", 0, 0, 6, 1);
	private IntegerSetting delayPlacedBlockUpdateTicks = new IntegerSetting("Delay Placed Block Update Ticks", 0, 0, 6, 1);
	
	// Extend
	private BooleanSetting extend = new BooleanSetting("Extend", false);
	private DoubleSetting extendDistance = (DoubleSetting) new DoubleSetting("Extend Distance", 1, 0.1, 6, 0.1).setDependency(extend::isEnabled);
	
	// Tower
	private BooleanSetting tower = new BooleanSetting("Tower", false);
	private ModeSetting towerMode = (ModeSetting) new ModeSetting("Tower Mode", "Vanilla", "Vanilla", "Verus", "Test").setDependency(tower::isEnabled);
	
	// Vars used in the scaffold
	private BlockPos placePos = null;
	private PlaceOnInfo placeOnInfo = null;
	private int keepYPosition = Integer.MAX_VALUE;
	private float[] rotations = new float[] {0, 0};
	private float[] lastRotations = new float[] {0, 0};
	
	// So rotations can control when to place
	private boolean canPlace = false;
	
	// Used for item selection
	private int currentItemSlot = 0;
	private TimerUtil switchDelayTimer = new TimerUtil();
	
	// Rotation vars
	private PlaceOnInfo lastPlaceOnInfo = null;
	private boolean placedBlock = false;
	private double[] hypixelPositions = new double[] {0, 0};
	
	// Tower vars
	private TimerUtil towerTimerUtil = new TimerUtil();
	
	// Blacklisted blocks to place with
	private static final Block[] BLACKLISTED_BLOCKS = new Block[] {Blocks.sand, Blocks.gravel, Blocks.cactus, Blocks.iron_bars};
	
	// Used to delay block placements
	private int blockPlacmentTicksSkipped = 0;
	private int placedBlockUpdateTicks = 0;
	
	@Override
	public void onEnable() {
		placePos = null;
		placeOnInfo = null;
		lastPlaceOnInfo = null;
		keepYPosition = (int)(mc.thePlayer.posY - 1);
		canPlace = false;
		currentItemSlot = mc.thePlayer.inventory.currentItem;
		EntityPlayerSP thePlayer = mc.thePlayer;
		rotations[0] = thePlayer.rotationYaw;
		rotations[1] = thePlayer.rotationPitch;
		lastRotations[0] = rotations[0];
		lastRotations[1] = rotations[1];
		placedBlock = false;
		blockPlacmentTicksSkipped = 0;
	}
	
	@Override
	public void onDisable() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (towerMode.getMode()) {
			
		}
		if (currentItemSlot != mc.thePlayer.inventory.currentItem)
			switch(itemMode.getMode()) {
				case "Server":
				case "Synced":{
					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
				}break;
			}
	}
	
	/**
	 * Used for the tower
	 */
	@EventHandler
	private Handler<EventPlayerUpdate> onUpdateTower = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		// Not towering
		if (!isTowering()) {
			switch (towerMode.getMode()) {
				
			}
			return;
		}
		
		MovementUtils.setMotion(0);
		
		// Towering
		switch (towerMode.getMode()) {
			case "Test":{
//				if (MovementUtils.isTrueOnGround()) {
//					thePlayer.jump();
//				}
//				else if (MovementUtils.isTrueOnGround(0.5)) {
//					thePlayer.motionY = 0.3584000015258789f - (Math.random() / 500);
//				}
				if (thePlayer.ticksExisted % 2 == 0)
					thePlayer.setPosition(thePlayer.lastTickPosX, thePlayer.lastTickPosY, thePlayer.lastTickPosZ);
				else
					thePlayer.offsetPosition(0, 0.5, 0);
//				if (MovementUtils.isTrueOnGround())
//					thePlayer.offsetPosition(0, 0.41999998688697815f, 0);
//				else if (MovementUtils.isTrueOnGround(0.2)) {
//					thePlayer.offsetPosition(0, 0.3399998688697815f, 0);
//				}
//				else if (MovementUtils.isTrueOnGround(0.41999998688697815f))
//					thePlayer.offsetPosition(0, 0.58000001311f, 0);
//				e.setPosX(thePlayer.posX);
//				e.setPosY(thePlayer.posY);
//				e.setPosZ(thePlayer.posZ);
//				thePlayer.jump();
//				thePlayer.motionY += Math.random() / 500f;
			}break;
		}
	};
	
	/**
	 * Whether or not the player should be towering
	 * @return true if the player is towering, otherwise false
	 */
	public boolean isTowering() {
		return !MovementUtils.isPlayerMoving() && mc.gameSettings.keyBindJump.isKeyDown() && tower.isEnabled();
	}
	
	/**
	 * Used for controlling sprint
	 */
	@EventHandler
	private Handler<EventTick> onTickSprint = e -> {
		if (e.isPost() || sprint.isEnabled())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		if (thePlayer.isSprinting())
			thePlayer.setSprinting(false);
	};
	
	/**
	 * Used for controlling sprint
	 */
	@EventHandler
	private Handler<EventPacketSend> onSendPacketSprint = e -> {
		if (e.isPost() || sprint.isEnabled() || !(e.getPacket() instanceof C0BPacketEntityAction))
			return;
		C0BPacketEntityAction c0b = (C0BPacketEntityAction)e.getPacket();
		if (c0b.getAction() != Action.START_SPRINTING)
			return;
		e.cancel();
	};
	
	/**
	 * Used for item selection
	 */
	@EventHandler
	private Handler<EventPacketSend> onSendPacketItem = e -> {
		if (e.isPost() || !(e.getPacket() instanceof C09PacketHeldItemChange) || itemMode.is("Spoof"))
			return;
		
		// Cancel the packet because the scaffold module controls the held item server side
		e.cancel();
	};
	
	/**
	 * Used for item selection
	 */
	@EventHandler
	private Handler<EventPacketReceive> onReceivePacketItem = e -> {
		if (e.isPost() || !(e.getPacket() instanceof S09PacketHeldItemChange))
			return;
		currentItemSlot = ((S09PacketHeldItemChange)e.getPacket()).getHeldItemHotbarIndex();
	};
	
	/**
	 * Used for item selection
	 */
	@EventHandler
	private Handler<EventTick> onTickItem = e -> {
		if (e.isPost() || !switchDelayTimer.hasTimeElapsed(blockSwitchDelay.getValue()))
			return;
		
		int largestBlocks = -1;
		int currentSlot = -1;
		InventoryPlayer inventory = mc.thePlayer.inventory;
		List<Block> blacklisted = Arrays.asList(BLACKLISTED_BLOCKS);
		for (int i = 0; i < 9; i++) {
			ItemStack item = inventory.getStackInSlot(i);
			if (item == null || item.getItem() == null || !(item.getItem() instanceof ItemBlock))
				continue;
			ItemBlock itemBlock = (ItemBlock)item.getItem();
			Block block = itemBlock.getBlock();
			if (!(block.canCollideCheck(block.getDefaultState(), false) && !block.doesBlockActivate()
					&& WorldUtils.additionalPlaceOnBlockCheck(block)))
				continue;
			if (item.getStackSize() <= largestBlocks)
				continue;
			if (blacklisted.contains(block))
				continue;
			currentSlot = i;
			largestBlocks = item.getStackSize();
		}
		
		if (largestBlocks == -1 || currentSlot == -1 || currentItemSlot == currentSlot) {
			return;
		}
		
		currentItemSlot = currentSlot;
		switch(itemMode.getMode()) {
			case "Server":{
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(currentSlot));
			}break;
			case "Synced":{
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(currentSlot));
				inventory.currentItem = currentSlot;
			}break;
			case "Spoof":{
				
			}break;
		}
		switchDelayTimer.reset();
		
	};
	
	@EventHandler
	private Handler<EventTick> onTickSafewalk = e -> {
		if (e.isPost())
			return;
		// Safewalk
		if (safewalk.isEnabled())
			SpoofUtils.setSpoofSneakMovement(true);
	};
	
	/**
	 * Used for block selection
	 */
	@EventHandler
	private Handler<EventCheatProcessTick> onCheatProcessTickBlock = e -> {
		if (e.isPost())
			return;
		
		// Vars
		EntityPlayerSP thePlayer = mc.thePlayer;
		boolean shouldKeepY = keepY.isEnabled() && thePlayer.posY > keepYPosition;
		double keepYPosition = this.keepYPosition;
		double[] playerPos = new double[] {thePlayer.posX, thePlayer.posY, thePlayer.posZ};
		
		// Return if the player is in spectator or adventure mode
		if (mc.playerController.getCurrentGameType() == GameType.SPECTATOR || mc.playerController.getCurrentGameType() == GameType.ADVENTURE) {
			placePos = null;
			placeOnInfo = null;
			return;
		}
		
		// Same y recalculation if on ground
		if (MovementUtils.isTrueOnGround()) {
			keepYPosition = (int)(mc.thePlayer.posY - 1);
		}
		
		// Default place pos
		placePos = new BlockPos(playerPos[0], shouldKeepY ? keepYPosition : (playerPos[1] - 1), playerPos[2]);
		
		// keep y catch incase you fall below the scaffold and land again
		if (!shouldKeepY && keepY.isEnabled() && MovementUtils.isTrueOnGround()) {
			keepYPosition = placePos.getY();
		}
		
		// Extend
		if (extend.isEnabled() && WorldUtils.isBlockSolid(placePos)) {
			for (double d = 0.1; d < extendDistance.getValue(); d += 0.1) {
				double[] pos = WorldUtils.extendPosition(d, playerPos[0], shouldKeepY ? keepYPosition : (playerPos[1] - 1), playerPos[2]);
				BlockPos newPos = new BlockPos(pos[0], pos[1], pos[2]);
				if (!WorldUtils.isBlockSolid(newPos)) {
					placePos = newPos;
					break;
				}
			}
		}
		
		// If the block we want to place is already solid then return, no point in doing further calculations
		if (WorldUtils.isBlockSolid(placePos)) {
			placePos = null;
			placeOnInfo = null;
			return;
		}
		
		// Find what block and placing we should place on
		PlaceOnInfo tempInfo = WorldUtils.getPlaceOn(placePos, maxBlockReach.getValue());
		if (tempInfo == null)
			return;
		BlockPos tempPos = tempInfo.getPlaceOn();
		EnumFacing tempFacing = tempInfo.getPlaceFacing();
		BlockPos currentPos = null;
		EnumFacing currentFacing = null;
		if (placeOnInfo != null) {
			currentPos = placeOnInfo.getPlaceOn();
			currentFacing = placeOnInfo.getPlaceFacing();
		}
		
		// If the block or facing is different then set the placeOnInfo, otherwise we do nothing because it makes my life easier when I do rotations
		if (placeOnInfo == null || !(currentFacing == tempFacing && currentPos.getX() == tempPos.getX() && currentPos.getY() == tempPos.getY() && currentPos.getZ() == tempPos.getZ())) {
			placeOnInfo = tempInfo;
		}
		
	};
	
	/**
	 * Used for 3d visuals
	 */
	@EventHandler
	private Handler<EventRender3D> onRender3D = e -> {
		if (visuals3D.isDisabled())
			return;
		
		BlockPos placePos = this.placePos;
		PlaceOnInfo placeOnInfo = this.placeOnInfo;
		
		GlStateManager.pushMatrix();
		GlStateManager.pushAttrib();
		GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
		GL11.glPolygonOffset(1.0f, -1099998.0f);
		
		if (placeOnInfo != null) {
			DrawUtils3D.drawColored3DWorldBox(placeOnInfo.getPlaceOn().getX(), placeOnInfo.getPlaceOn().getY(),
					placeOnInfo.getPlaceOn().getZ(), placeOnInfo.getPlaceOn().getX() + 1,
					placeOnInfo.getPlaceOn().getY() + 1, placeOnInfo.getPlaceOn().getZ() + 1, 0x400000ff);
		}
		
		if (placePos != null) {
			DrawUtils3D.drawColored3DWorldBox(placePos.getX(), placePos.getY(), placePos.getZ(), placePos.getX() + 1, placePos.getY() + 1, placePos.getZ() + 1, 0x90ffffff);
		}
		
		GlStateManager.popAttrib();
		GlStateManager.popMatrix();
		
	};
	
	/**
	 * Used for 2d visuals
	 */
	@EventHandler
	private Handler<EventRender2D> onRender2D = e -> {
		if (e.isPost() || visuals2D.isDisabled())
			return;
	};
	
	/**
	 * Used for rotations, always rotates on the pre event
	 */
	@EventHandler
	private Handler<EventPlayerUpdate> rotationPlayerUpdate = e -> {
		if (e.isPost())
			return;
		
		PlaceOnInfo placeOnInfo = this.placeOnInfo;
		
		switch (rotationMode.getMode()) {
			case "None":{
				canPlace = true;
			}return; // Returning cancels rotations
			case "Lock":{
				if (lastPlaceOnInfo == null && placeOnInfo == null)
					break;
				else if (lastPlaceOnInfo == null)
					lastPlaceOnInfo = placeOnInfo;
				BlockPos placeOn = lastPlaceOnInfo.getPlaceOn();
				lastRotations = rotations;
				rotations = RotationUtils.getRotations(new Vector3d(placeOn.getX() + 0.5, placeOn.getY() + 0.5, placeOn.getZ() + 0.5));
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, rotations);
				canPlace = true;
			}break;
			case "Snap":{
				if (placeOnInfo == null)
					break;
				if (placeOnInfo != lastPlaceOnInfo) {
					BlockPos placeOn = placeOnInfo.getPlaceOn();
					lastRotations = rotations;
					rotations = RotationUtils.getRotations(new Vector3d(placeOn.getX() + 0.5, placeOn.getY() + 0.5, placeOn.getZ() + 0.5));
					RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, rotations);
				}
				canPlace = true;
			}break;
			case "Hypixel":{
				if (lastPlaceOnInfo == null && placeOnInfo == null)
					break;
				else if (lastPlaceOnInfo == null)
					lastPlaceOnInfo = placeOnInfo;
				BlockPos placeOn = lastPlaceOnInfo.getPlaceOn();
//				lastRotations = rotations;
				rotations = RotationUtils.getRotations(new Vector3d(placeOn.getX() + 0.5, placeOn.getY(), placeOn.getZ() + 0.5));
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, rotations);
				if (!placedBlock) {
					rotations[0] = lastRotations[0];
					rotations[1] = lastRotations[1];
				}
				canPlace = (lastRotations[0] - rotations[0] < 20) && placeOnInfo != null;
				if (canPlace) {
					EntityPlayerSP thePlayer = mc.thePlayer;
					switch (placeOnInfo.getPlaceFacing()) {
						case NORTH: {
							canPlace = thePlayer.lastTickPosZ - placeOnInfo.getPlaceOn().getZ() < 0;
						}break;
						case EAST: {
							canPlace = (placeOnInfo.getPlaceOn().getX() + 1) - thePlayer.lastTickPosX < 0;
						}break;
						case SOUTH: {
							canPlace = (placeOnInfo.getPlaceOn().getZ() + 1) - thePlayer.lastTickPosZ < 0;
						}break;
						case WEST: {
							canPlace = thePlayer.lastTickPosX - placeOnInfo.getPlaceOn().getX() < 0;
						}break;
						default: {
							canPlace = true;
						}break;
					}
				}
				lastRotations = rotations;
			}break;
			case "Vulcan":{
				if (lastPlaceOnInfo == null && placeOnInfo == null)
					break;
				else if (lastPlaceOnInfo == null)
					lastPlaceOnInfo = placeOnInfo;
				EntityPlayerSP thePlayer = mc.thePlayer;
				if (placeOnInfo == null) {
					break;
				}else {
					Vector3d targetPos = new Vector3d(placeOnInfo.getPlaceOn().getX(), placeOnInfo.getPlaceOn().getY(), placeOnInfo.getPlaceOn().getZ());
					switch (placeOnInfo.getPlaceFacing()) {
						case EAST:{
							targetPos.setX(targetPos.getX() + 1);
						}break;
						case SOUTH:{
							targetPos.setZ(targetPos.getZ() + 1);
						}break;	
						default:break;
					}
					rotations[0] = RotationUtils.getRotations(targetPos)[0] + 180;
				}
				
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, rotations);
				lastRotations[0] = rotations[0];
				rotations[1] = 90;
				lastRotations[1] = 90;
				if (placeOnInfo == null || placeOnInfo.getPlaceFacing() == null || placeOnInfo.getPlaceOn() == null)
					break;
				switch (placeOnInfo.getPlaceFacing()) {
					case NORTH: {
						canPlace = thePlayer.lastTickPosZ - placeOnInfo.getPlaceOn().getZ() < 1;
					}break;
					case EAST: {
						canPlace = (placeOnInfo.getPlaceOn().getX() + 1) - thePlayer.lastTickPosX < 1;
					}break;
					case SOUTH: {
						canPlace = (placeOnInfo.getPlaceOn().getZ() + 1) - thePlayer.lastTickPosZ < 1;
					}break;
					case WEST: {
						canPlace = thePlayer.lastTickPosX - placeOnInfo.getPlaceOn().getX() < 1;
					}break;
					default: {
						canPlace = true;
					}break;
				}
				if (!canPlace)
					canPlace = EnumFacing.getHorizontal(MathHelper.floor_double((double)(lastRotations[0] * 4.0F / 360.0F) + 0.5D) & 3) != placeOnInfo.getPlaceFacing();
				if (!canPlace)
					ChatUtils.addChatMessage(placeOnInfo.getPlaceFacing());
			}break;
			case "Negativity":{
				rotations[0] = System.currentTimeMillis() % 360;
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, rotations);
				lastRotations[0] = rotations[0];
				rotations[1] = 90;
				lastRotations[1] = 90;
				canPlace = true;
			}break;
			
		}
		
		if (placeOnInfo != null)
			lastPlaceOnInfo = placeOnInfo;
		
		if (placedBlock)
			placedBlock = false;
		
		SpoofUtils.setSpoofedYaw(rotations[0]);
		SpoofUtils.setSpoofedPitch(rotations[1]);
		SpoofUtils.setSpoofedLastYaw(lastRotations[0]);
		SpoofUtils.setSpoofedLastPitch(lastRotations[1]);
		((EventPlayerUpdate)e).setRotationYaw(rotations[0]);
		((EventPlayerUpdate)e).setRotationPitch(rotations[1]);
		if (silentRotations.isDisabled()) {
			mc.thePlayer.rotationYaw = rotations[0];
			mc.thePlayer.rotationPitch = rotations[1];
		}
	};
	
	/**
	 * Used to send c08 packets, can be on the pre or the post
	 */
	@EventHandler
	private Handler<EventPlayerUpdate> c08PlayerUpdate = e -> {
		if (c08Position.is("PRE") ? e.isPost() : e.isPre())
			return;
		
		if (delayPlacedBlockUpdateTicks.getValue() > 0 && placedBlockUpdateTicks == 0) {
			placedBlock = true;
		}
		placedBlockUpdateTicks--;
		
		// Only attempt place if place pos and place on info is set, canPlace is set to true, and there is something the player can place with
		if (placePos == null || placeOnInfo == null || !canPlace)
			return;
		
		// Vars
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		BlockPos placeOn = placeOnInfo.getPlaceOn();
		EnumFacing placeOnFacing = placeOnInfo.getPlaceFacing();
		IBlockState placeOnState = theWorld.getBlockState(placeOn);
		float[] vec3 = getVec3(vec3Mode.getMode());
		
		// Get the item that the player is using to place the block
		ItemStack placeItem = thePlayer.inventory.getStackInSlot(currentItemSlot);
		
		// If the place item is null or isn't a block then return
		if (placeItem == null || !(placeItem.getItem() instanceof ItemBlock)) {
			return;
		}
		
		// World border check, modified from normal mc because I want to give the user the option to bypass this check
		if (!theWorld.getWorldBorder().contains(placeOn) && ignoreWorldBorder.isDisabled()){
			return;
        }
		
		// This is if you click blocks that show guis or do stuff, the scaffold targeting should avoid blocks like this but in the case it doesn't this should catch it
        if (placeOnState.getBlock().doesBlockActivate()) {
        	return;
        }
        
        // Delay block placement setting
        if (delayBlockPlacements.getValue() > 0 && blockPlacmentTicksSkipped <= delayBlockPlacements.getValue()) {
        	blockPlacmentTicksSkipped++;
        	return;
        }
        
        // If the block for some reason activates then do nothing, otherwise place the block
        if (!((!thePlayer.isSneaking() || thePlayer.getHeldItem() == null) && placeOnState.getBlock().onBlockActivated(theWorld, placeOn, placeOnState, thePlayer, placeOnFacing, vec3[0], vec3[1], vec3[2]))){
        	
        	// For the spoof item selection
        	if (itemMode.is("Spoof")) {
        		mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(currentItemSlot));
        	}
        	
        	// Place block
        	mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(placeOn, placeOnFacing.getIndex(), placeItem, vec3[0], vec3[1], vec3[2]));
        	
        	// For the spoof item selection
        	if (itemMode.is("Spoof")) {
        		mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(thePlayer.inventory.currentItem));
        	}
        	
        	// Update client side, we don't need to wait for the server to place the block since we just assume it does
        	if (mc.playerController.getCurrentGameType().isCreative()) {
				int i = placeItem.getMetadata();
				int j = placeItem.stackSize;
				placeItem.onItemUse(thePlayer, theWorld, placeOn, placeOnFacing, vec3[0], vec3[1], vec3[2]);
				placeItem.setItemDamage(i);
				placeItem.stackSize = j;
			} else {
				placeItem.onItemUse(thePlayer, theWorld, placeOn, placeOnFacing, vec3[0], vec3[1], vec3[2]);
			}
        	
        	// Swing item
        	switch (swingMode.getMode()) {
        		case "Server":{
        			mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
        		}break;
        		case "Synced":{
        			thePlayer.swingItem();
        		}break;
        		case "No Swing":{
        			
        		}break;
			}
        	
        	if (delayPlacedBlockUpdateTicks.getValue() == 0) {
        		placedBlock = true;
        	}else {
        		placedBlockUpdateTicks = delayPlacedBlockUpdateTicks.getValue();
        	}
        	
        	blockPlacmentTicksSkipped = 0;
        }
        
        // Tower again
        if (isTowering()) {
			switch (towerMode.getMode()) {
				case "Vanilla":{
					if (placedBlock) {
						thePlayer.offsetPosition(0, 1, 0);
						thePlayer.motionY = -0.0784000015258789;
					}
				}break;
				case "Verus":{
					if (placedBlock && MovementUtils.isTrueOnGround(2))
						thePlayer.motionY = 1;
				}break;
				case "Test":{
					
				}break;
			}
        }
        
	};
	
	/**
	 * @return The vec3 to use for the c08
	 */
	private float[] getVec3(String vec3Mode) {
		
		// "Origin", "Rand >=0 <=1", "Rand >=0.2 <=0.8", "Raytrace"
		switch (vec3Mode) {
			case "Origin":return new float[] {0, 0, 0};
			case "Center":return new float[] {0.5f, 0.5f, 0.5f};
			case "Rand >=0 <=1":return new float[] {RandomUtils.nextFloat(0, 1), RandomUtils.nextFloat(0, 1), RandomUtils.nextFloat(0, 1)};
			case "Rand >=0.2 <=0.8":return new float[] {RandomUtils.nextFloat(0.2f, 0.8f), RandomUtils.nextFloat(0.2f, 0.8f), RandomUtils.nextFloat(0.2f, 0.8f)};
			case "Raytrace":{
				// Taken from mc code, some var names changed to make them easier to understand
				float partialTicks = mc.getTimer().getRenderPartialTicks();
				double reach = maxBlockReach.getValue() + 1;
				Vec3 eyePos = mc.thePlayer.getPositionEyes(partialTicks);
	            Vec3 look = RotationUtils.getLook(rotations[0], rotations[1], lastRotations[0], lastRotations[1]);
	            Vec3 vec32 = eyePos.addVector(look.xCoord * reach, look.yCoord * reach, look.zCoord * reach);
	            
	            // Get the block collision box
	            BlockPos placeOn = placeOnInfo.getPlaceOn();
	            WorldClient theWorld = mc.theWorld;
	            IBlockState placeOnState = theWorld.getBlockState(placeOn);
	            AxisAlignedBB collisionBox = placeOnState.getBlock().getCollisionBoundingBox(theWorld, placeOn, placeOnState);
	            
	            // Do the raytrace
	            MovingObjectPosition rayTrace = null;
	            try {
	            	rayTrace = collisionBox.calculateIntercept(eyePos, vec32);
	            }catch(Exception e1) {
	            	
	            }
	            
	            // Process the results
	            if (rayTrace == null || rayTrace.typeOfHit == MovingObjectType.MISS) {
	            	return getVec3(rayTraceMissMode.getMode());
	            }
	            Vec3 results = rayTrace.hitVec.addVector(-placeOn.getX(), -placeOn.getY(), -placeOn.getZ());
	            return new float[] {(float) results.xCoord, (float) results.yCoord, (float) results.zCoord};
			}
		}
		
		return new float[] {0, 0, 0};
	}
	
	/**
	 * @return the sprint
	 */
	public BooleanSetting getSprint() {
		return sprint;
	}
	
}
