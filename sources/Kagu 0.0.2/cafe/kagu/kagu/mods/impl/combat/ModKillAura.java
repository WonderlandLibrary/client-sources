/**
 * 
 */
package cafe.kagu.kagu.mods.impl.combat;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.vecmath.Vector3d;

import org.apache.commons.lang3.RandomUtils;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModAntiBot;
import cafe.kagu.kagu.settings.SettingDependency;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.SpoofUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.CurvedLineHelper;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.PlayerUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * @author lavaflowglow
 *
 */
public class ModKillAura extends Module {

	public ModKillAura() {
		super("KillAura", Category.COMBAT);
		setSettings(rotationMode, vulcanMode, linearSpeed, curveRotationSpeed, curveYawDifferenceToHit, blockMode,
				preferredTargetMetrics, targetSelectionMode, swingMode, clickMode, hitRange, blockRange, hitChance,
				minAps, maxAps, slowOnBlock, blockSlowdown, silentRotations, movementMatchRotation, targetAll,
				targetPlayers, targetAnimals, targetMobs);
		Kagu.getEventBus().subscribe(new ApsMinMaxFixer(this));
	}
	
	private ModeSetting rotationMode = new ModeSetting("Rotation Mode", "Lock", "None", "Lock", "Lock+", "Linear", "Linear+", "Linear Humanized", "Quadratic Curve", "Cubic Curve");
	private BooleanSetting vulcanMode = new BooleanSetting("Vulcan Rotation Bypass", false);
	private DoubleSetting linearSpeed = new DoubleSetting("Linear Speed", 10, 0.5, 180, 0.5).setDependency(() -> rotationMode.is("Linear") || rotationMode.is("Linear+") || rotationMode.is("Linear Humanized"));
	private DoubleSetting curveRotationSpeed = new DoubleSetting("Rotation Speed", 0.1, 0.01, 1, 0.01).setDependency(() -> rotationMode.is("Quadratic Curve") || rotationMode.is("Cubic Curve"));
	private DoubleSetting curveYawDifferenceToHit = new DoubleSetting("Min Hit Yaw Difference", 20, 1, 180, 0.5).setDependency(() -> rotationMode.is("Quadratic Curve") || rotationMode.is("Cubic Curve"));
	
	private ModeSetting blockMode = new ModeSetting("Block Mode", "None", "None", "Fake", "Vanilla", "Hypixel", "Test");
	private ModeSetting preferredTargetMetrics = new ModeSetting("Preferred Target Metrics", "Distance", "Distance");
	private ModeSetting targetSelectionMode = new ModeSetting("Target Selection", "Instant", "Instant");
	private ModeSetting swingMode = new ModeSetting("Swing Mode", "Swing", "Swing", "Server Side", "C0A, Swing On Hit", "No Swing");
	private ModeSetting clickMode = new ModeSetting("Click Mode", "Random", "Random", "Gaussian");
	
	// Ranges
	private DoubleSetting hitRange = new DoubleSetting("Hit Range", 3, 1, 7, 0.1);
	private DoubleSetting blockRange = (DoubleSetting) new DoubleSetting("Block Range", 3, 1, 7, 0.1).setDependency((SettingDependency)() -> !blockMode.is("None"));
	
	// Hit chance
	private IntegerSetting hitChance = new IntegerSetting("Hit Chance", 100, 0, 100, 1);
	
	// APS settings
	private DoubleSetting minAps = new DoubleSetting("Min APS", 10, 0, 20, 0.1);
	private DoubleSetting maxAps = new DoubleSetting("Max APS", 10, 0.1, 20, 0.1);
	
	private BooleanSetting slowOnBlock = new BooleanSetting("Slow On Block", false).setDependency(() -> !blockMode.is("None") && !blockMode.is("Fake"));
	private DoubleSetting blockSlowdown = new DoubleSetting("Block Slowdown", 0.2, 0.0, 1.0, 0.05).setDependency(() -> slowOnBlock.isEnabled( )&& !slowOnBlock.isHidden());
	
	private BooleanSetting silentRotations = new BooleanSetting("Silent Rotations", true);
	private BooleanSetting movementMatchRotation = new BooleanSetting("Match Movement With Rotation", false);
	
	// Targets
	private BooleanSetting targetAll = new BooleanSetting("Target Everything", true);
	private BooleanSetting targetPlayers = new BooleanSetting("Target Players", true).setDependency(targetAll::isDisabled);
	private BooleanSetting targetAnimals = new BooleanSetting("Target Animals", false).setDependency(targetAll::isDisabled);
	private BooleanSetting targetMobs = new BooleanSetting("Target Mobs", false).setDependency(targetAll::isDisabled);
	
	// Vars
	private EntityLivingBase lastTarget = null;
	private EntityLivingBase target = null;
	private double aps = minAps.getValue();
	private boolean blocking = false;
	private TimerUtil apsTimer = new TimerUtil();
	private float[] lastRotations = new float[] {0, 0};
	private boolean canHit = false, isSentAttackForAnimation = false;
	private boolean hypixelFlagged = false;
	private int spike = 3, lastTargetHurtTime = 0;
	private CurvedLineHelper curvedPointHelper = null;
	
	@Override
	public void onEnable() {
		EntityPlayerSP thePlayer = mc.thePlayer;
		lastRotations[0] = thePlayer.rotationYaw;
		lastRotations[1] = thePlayer.rotationPitch;
		spike = 3;
		lastTargetHurtTime = 0;
		isSentAttackForAnimation = false;
	}
	
	@Override
	public void onDisable() {
		stopBlocking();
		curvedPointHelper = null;
	}
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		
		// Rotations, target selection, blocking
		EntityLivingBase target = null;
		double distanceFromPlayer = 3621;
		float[] rotations = null;
		
		setInfo(new DecimalFormat("0.00").format(aps) + " APS", rotationMode.getMode());
		
		EntityLivingBase[] targets = getTargets();
		if (targets.length == 0) {
			stopBlocking();
			this.target = null;
			curvedPointHelper = null;
			lastTargetHurtTime = 0;
			return;
		}
		
		target = targets[0];
		this.target = target;
		
		if (target == null) {
			stopBlocking();
			curvedPointHelper = null;
			lastTargetHurtTime = 0;
			return;
		}
		
		if (target != lastTarget) {
			curvedPointHelper = null;
			lastTarget = target;
		}
		
		// So the swing animation if the mode is set correctly and target is hit
		if (swingMode.is("C0A, Swing On Hit") && isSentAttackForAnimation && target.hurtTime > lastTargetHurtTime) {
			mc.thePlayer.isSwingInProgress = true;
			isSentAttackForAnimation = false;
		}
		lastTargetHurtTime = target.hurtTime;
		
		distanceFromPlayer = getDistanceFromPlayerEyes(target);
		
		// Check if the target is within the block distance
		if (!blockMode.is("None")) {
			
			// Block or unblock
//			if (blockMode.is("Hypixel") && blocking && mc.thePlayer.ticksExisted % 10 == 0) {
//				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
//			}
//			if ((blockMode.is("Hypixel") && mc.thePlayer.ticksExisted % 10 == 0))
//				blocking = false;
			
			if (distanceFromPlayer <= blockRange.getValue())
				startBlocking();
			else
				stopBlocking();
			
		}
		
		// Get and set the rotations
		float[] rotsWithGcd = lastRotations;
		rotations = getRotations(target, e);
		RotationUtils.applyGCD(rotations, rotsWithGcd);
		e.setRotationYaw(rotations[0]);
		e.setRotationPitch(rotations[1]);
		if (!rotationMode.is("None")) {
			SpoofUtils.setSpoofedYaw(rotations[0]);
			SpoofUtils.setSpoofedPitch(rotations[1]);
		}
		if (movementMatchRotation.isEnabled())
			SpoofUtils.setSpoofedMovementYaw(rotations[0]);
		if (silentRotations.isDisabled()) {
			mc.thePlayer.rotationYaw = rotations[0];
			mc.thePlayer.rotationPitch = rotations[1];
		}
		
		// Attacking
		if (target == null || rotations == null)
			return;
		
		// Check if we're able to hit
		if (canHit && distanceFromPlayer <= hitRange.getValue() && apsTimer.hasTimeElapsed((long) (1000 / aps), true)) {
			
			// Swing
			switch (swingMode.getMode()) {
				case "Swing":{
					mc.thePlayer.swingItem();
				}break;
				case "Server Side":{
					mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
				}break;
				case "No Swing":break;
			}
			
			if (!blockMode.is("None") && !blockMode.is("Vanilla") && !blockMode.is("Hypixel")) {
				stopBlocking();
			}
			
			// Hit chance, bypasses percent checks
			if (hitChance.getValue() != 0 && RandomUtils.nextInt(0, 101) <= hitChance.getValue()) {
				mc.getNetHandler().getNetworkManager().sendPacket(new C02PacketUseEntity(target, Action.ATTACK));
//				Robot bot = null;
//				try {
//					bot = new Robot();
//				} catch (Exception failed) {
//					System.err.println("Failed instantiating Robot: " + failed);
//				}
//				int mask = InputEvent.BUTTON1_DOWN_MASK;
//				bot.mousePress(mask);
//				bot.mouseRelease(mask);
				isSentAttackForAnimation = true;
			}
			
			if (!blockMode.is("None") && !blockMode.is("Vanilla") && !blockMode.is("Hypixel")) {
				startBlocking();
			}
			
			if (hypixelFlagged)
				hypixelFlagged = false;
			
			setAps();
			
		}
		
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost())
			return;
		if (blockMode.is("Hypixel") && isBlocking() && e.getPacket() instanceof S08PacketPlayerPosLook) {
			stopBlocking();
//			startBlocking();
//			hypixelFlagged = true;
//			mc.thePlayer.onGround = false;
			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(false));
			startBlocking();
//			mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
			ChatUtils.addChatMessage("Flagged");
		}
	};
	
	/**
	 * Gets the rotations to look at the target
	 * @param target The target
	 * @param eventPlayerUpdate The event player update
	 * @return A float array containing yaw and pitch, may also contain separate data
	 */
	private float[] getRotations(EntityLivingBase target, EventPlayerUpdate eventPlayerUpdate) {
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		if (vulcanMode.isEnabled() && thePlayer.ticksExisted % 2 == 0)
			return lastRotations;
		Vector3d playerEyePos = new Vector3d(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ);
		Vector3d targetEyePos = new Vector3d(target.posX, target.posY + target.getEyeHeight(), target.posZ);
		float hitboxSize = target.getCollisionBorderSize();
		AxisAlignedBB targetHitbox = target.getEntityBoundingBox().expand(hitboxSize, hitboxSize, hitboxSize);
		
		switch (rotationMode.getMode()) {
			case "None":{
				canHit = true;
				return new float[] {thePlayer.rotationYaw, thePlayer.rotationPitch};
			} // break; not needed because we return before that point
			case "Lock":{
				canHit = true;
				float[] result = RotationUtils.getRotations(playerEyePos, targetEyePos);
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, result);
				return lastRotations = result;
			} // break; not needed because we return before that point
			case "Lock+":{
				canHit = true;
				float[] result = RotationUtils.getRotations(playerEyePos, PlayerUtils.getClosestPointInBoundingBox(playerEyePos, targetHitbox));
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, result);
				return lastRotations = result;
			} // break; not needed because we return before that point
			case "Linear":
			case "Linear+":
			case "Linear Humanized":{
				
				if (rotationMode.is("Linear Humanized")) {
					
					// Funny tracking
					if (target.ticksExisted > 1) {
						targetEyePos.setX(target.posX - (target.lastTickPosX - target.posX) * RandomUtils.nextDouble(0.5, 1.5));
						targetEyePos.setZ(target.posZ - (target.lastTickPosZ - target.posZ) * RandomUtils.nextDouble(0.5, 1.5));
					}
					
					// Funny Y
					double lookDown = (double)(target.ticksExisted % 40) / 40;
					if (target.ticksExisted % 80 >= 40) {
						lookDown = 1 - (double)((target.ticksExisted % 80) - 40) / 40;
					}
					if (thePlayer.getDistanceToEntity(target) < 0.6) {
						lookDown *= 0.25;
					}
					targetEyePos.setY(targetEyePos.getY() - target.getEyeHeight() / 2 * lookDown);
				}
				
				float[] targetRotations = RotationUtils.getRotations(playerEyePos,
						rotationMode.is("Linear+") || rotationMode.is("Linear Humanized")
								? PlayerUtils.getClosestPointInBoundingBox(playerEyePos, targetHitbox)
								: targetEyePos);
				float[] currentRotations = lastRotations;
				float[] finalRotations = currentRotations;
				
				// Unfuck yaw because it loops back to -180 after it passes 180, without this there's an issue where the character does 360s where the two limits loop back to each other
				RotationUtils.makeRotationValuesLoopCorrectly(currentRotations, targetRotations);
				
				// Do lerp
				double move = linearSpeed.getValue();
				
				if (rotationMode.is("Linear Humanized")) {
					int maxSpike = 5;
					if (spike <= thePlayer.ticksExisted || spike - thePlayer.ticksExisted > maxSpike) {
						spike = thePlayer.ticksExisted + RandomUtils.nextInt(0, maxSpike + 2) - 2;
						move *= RandomUtils.nextDouble(1.3, 1.6);
					}
				}
				
				double amountMoved = move / Math.abs(currentRotations[0] - targetRotations[0]);
				double pitchMove = Math.abs(currentRotations[1] - targetRotations[1]) * amountMoved;
				boolean yawOnPoint = false;
				boolean pitchOnPoint = false;
				
				// Yaw
				if (move < Math.abs(currentRotations[0] - targetRotations[0])) {
					finalRotations[0] += move * (targetRotations[0] - currentRotations[0] < 0 ? -1 : 1);
				}else {
					finalRotations[0] = targetRotations[0];
					yawOnPoint = true;
				}
				
				// Pitch
				if (pitchMove < Math.abs(currentRotations[1] - targetRotations[1])) {
					finalRotations[1] += pitchMove * (targetRotations[1] - currentRotations[1] < 0 ? -1 : 1);
				}else {
					finalRotations[1] = targetRotations[1];
					pitchOnPoint = true;
				}
				
				canHit = yawOnPoint && pitchOnPoint;
				return lastRotations = finalRotations;
			} // break; not needed because we return before that point
			case "Quadratic Curve":
			case "Cubic Curve":{
				float[] targetRots = RotationUtils.getRotations(playerEyePos, targetEyePos);
				RotationUtils.makeRotationValuesLoopCorrectly(lastRotations, targetRots);
				
				if (curvedPointHelper == null || curvedPointHelper.getProgress() >= 1) {
					curvedPointHelper = new CurvedLineHelper(lastRotations, targetRots);
					if (rotationMode.is("Cubic Curve")) {
						curvedPointHelper.createCubicCurve();
					}else {
						curvedPointHelper.createQuadraticCurve();
					}
				}else {
					curvedPointHelper.setEnd(targetRots);
					curvedPointHelper.setStart(lastRotations);
					curvedPointHelper.addProgress((float) curveRotationSpeed.getValue() + (float)ThreadLocalRandom.current().nextGaussian() * 0.1f);
				}
				
				float[] finalRots = curvedPointHelper.getCurrentPoint();
				canHit = MathUtils.getRange(finalRots[0], lastRotations[0]) <= curveYawDifferenceToHit.getValue();
				return lastRotations = finalRots;
			} // break; not needed because we return before that point
		}
		
		return new float[] {0, 0};
	}
	
	/**
	 * Gets an array of valid targets
	 * @return An array of valid targets
	 */
	private EntityLivingBase[] getTargets() {
		
		ArrayList<Entity> potentialTargets = new ArrayList<>(mc.theWorld.loadedEntityList);
		
		// Remove non living and out of range entities
		ModAntiBot modAntiBot = Kagu.getModuleManager().getModule(ModAntiBot.class);
		potentialTargets = (ArrayList<Entity>) potentialTargets
				.stream()
				.filter(ent -> 
						ent instanceof EntityLivingBase && 
						getDistanceFromPlayerEyes((EntityLivingBase)ent) <= Math.max(hitRange.getValue(), blockRange.getValue()) && 
						ent != mc.thePlayer
						&& (!(ent instanceof EntityPlayer) || (modAntiBot.isEnabled() ? !modAntiBot.isBot((EntityPlayer)ent) : true)))
				.collect(Collectors.toList());
		
		// Remove targets that don't fit the required target settings
		if (targetAll.isDisabled()) {
			potentialTargets = (ArrayList<Entity>) potentialTargets
					.stream()
					.filter(ent -> {
						if (targetPlayers.isEnabled() && ent instanceof EntityPlayer);
						else if (targetAnimals.isEnabled() && (ent instanceof EntityAnimal || ent instanceof EntityWaterMob));
						else if (targetMobs.isEnabled() && ent instanceof EntityMob);
						else return false;
						
						return true;
					}
					).collect(Collectors.toList());
		}
		
		// Sort them based on the config that the user has
		switch (preferredTargetMetrics.getMode()) {
			case "Distance":{
				potentialTargets.sort(Comparator.comparingDouble(ent -> getDistanceFromPlayerEyes((EntityLivingBase)ent)));
			}break;
		}
		
		// Cum
		EntityLivingBase tmpTarget = potentialTargets.isEmpty() ? null : (EntityLivingBase) potentialTargets.get(0);
		potentialTargets = (ArrayList<Entity>) potentialTargets
				.stream()
				.filter(ent -> 
						(((EntityLivingBase)ent).getMaxHealth() <= 0 || ((EntityLivingBase)ent).getHealth() > 0)
						)
				.collect(Collectors.toList());
		if (swingMode.is("C0A, Swing On Hit") && isSentAttackForAnimation && tmpTarget != null) {
			if (potentialTargets.isEmpty() || potentialTargets.get(0) != tmpTarget) {
				mc.thePlayer.isSwingInProgress = true;
				isSentAttackForAnimation = false;
			}
		}
		
		// Now that the targets are filtered and sorted we can use the users preferred
		// selection mode to make a decision on who the target should be
		EntityLivingBase[] targets = new EntityLivingBase[0];
		switch (targetSelectionMode.getMode()) {
			case "Instant":{
				targets = potentialTargets.toArray(targets);
			}break;
		}
		
		return targets;
	}
	
	/**
	 * Starts blocking
	 */
	private void startBlocking() {
		SpoofUtils.setSpoofBlocking(true);
		if (slowOnBlock.isEnabled() && mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
			if (MovementUtils.isTrueOnGround()) {
		        mc.thePlayer.motionX *= blockSlowdown.getValue();
		        mc.thePlayer.motionZ *= blockSlowdown.getValue();
			}
		}
		if (blocking)
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		if (!(thePlayer.inventory.getCurrentItem() != null && thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword)) {
			blocking = true;
			return;
		}
		
		switch (blockMode.getMode()) {
			case "Vanilla":{
				mc.getNetHandler().getNetworkManager().sendPacket(
						new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, thePlayer.getHeldItem(), 0, 0, 0));
			}break;
			case "Hypixel":{
				if (hypixelFlagged) {
					blocking = false;
					return;
				}
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(
						new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, thePlayer.getHeldItem(), 0, 0, 0));
				mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
			}break;
		}
		
		blocking = true;
	}
	
	/**
	 * Stops blocking
	 */
	private void stopBlocking() {
		if (!blocking)
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		if (!(thePlayer.inventory.getCurrentItem() != null && thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword)) {
			blocking = false;
			return;
		}
		
		switch (blockMode.getMode()) {
			case "Vanilla":{
				mc.getNetHandler().getNetworkManager()
						.sendPacket(new C07PacketPlayerDigging(
								net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
								BlockPos.ORIGIN, EnumFacing.DOWN));
			}break;
			case "Hypixel":{
				mc.getNetHandler().getNetworkManager()
						.sendPacketNoEvent(new C07PacketPlayerDigging(
								net.minecraft.network.play.client.C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,
								BlockPos.ORIGIN, EnumFacing.DOWN));
			}break;
		}
		
		blocking = false;
	}
	
	/**
	 * Sets the aps
	 */
	private void setAps() {
		aps = RandomUtils.nextDouble(minAps.getValue(), maxAps.getValue());
		
		switch (clickMode.getMode()) {
			case "Gaussian":{
				aps = Math.abs(ThreadLocalRandom.current().nextGaussian()) * (maxAps.getValue() - minAps.getValue()) + minAps.getValue();
			}break;
		}
		
	}
	
	/**
	 * Gets the distance to the player's eyes
	 * @param entityLivingBase The entity to check the distance for
	 * @return
	 */
	private double getDistanceFromPlayerEyes(EntityLivingBase entityLivingBase) {
		return entityLivingBase.getDistanceToEntity(mc.thePlayer);
	}
	
	private static class ApsMinMaxFixer{
		
		public ApsMinMaxFixer(ModKillAura modKillAura) {
			this.modKillAura = modKillAura;
		}
		
		private ModKillAura modKillAura;
		
		/**
		 * Used to make sure the min aps isn't greater than the max aps and vice versa
		 */
		@EventHandler
		private Handler<EventSettingUpdate> onSettingUpdate = e -> {
			
			if (e.getSetting() == modKillAura.minAps) {
				if (modKillAura.minAps.getValue() > modKillAura.maxAps.getValue())
					modKillAura.maxAps.setValue(modKillAura.minAps.getValue());
				if (modKillAura.aps < modKillAura.minAps.getValue())
					modKillAura.setAps();
			}
			else if (e.getSetting() == modKillAura.maxAps) {
				if (modKillAura.maxAps.getValue() < modKillAura.minAps.getValue())
					modKillAura.minAps.setValue(modKillAura.maxAps.getValue());
				if (modKillAura.aps > modKillAura.maxAps.getValue())
					modKillAura.setAps();
			}
			
		};
		
	}
	
	/**
	 * @return the target
	 */
	public EntityLivingBase getTarget() {
		if (isDisabled()) {
			target = null;
		}
		return target;
	}
	
	/**
	 * @return the blocking
	 */
	public boolean isBlocking() {
		return blocking;
	}
	
}
