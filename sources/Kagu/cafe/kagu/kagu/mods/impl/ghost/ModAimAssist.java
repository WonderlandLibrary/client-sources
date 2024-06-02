/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.vecmath.Vector3d;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.eventBus.impl.EventMouseDeltasUpdate;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModAntiAim;
import cafe.kagu.kagu.mods.impl.player.ModAntiBot;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.CurvedLineHelper;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

/**
 * @author DistastefulBannock
 *
 */
public class ModAimAssist extends Module {
	
	public ModAimAssist() {
		super("AimAssist", Category.GHOST);
		setSettings(mode, targets, raytraceCheck, passiveSlowdown, activeSpeed, fov, range);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Passive", "Passive", "Lock");
	private ModeSetting targets = new ModeSetting("Targets", "Players", "Players", "Entities");
	private ModeSetting raytraceCheck = new ModeSetting("Raytrace", "Off", "Off", "Simple", "Advanced");
	private DoubleSetting passiveSlowdown = new DoubleSetting("Slowdown", 0.55, 0, 1, 0.01).setDependency(() -> mode.is("Passive"));
	private DoubleSetting activeSpeed = new DoubleSetting("Speed", 3, 0.1, 10, 0.05).setDependency(() -> mode.is("Active"));
	private DoubleSetting fov = new DoubleSetting("FOV", 180, 1, 180, 1);
	private DoubleSetting range = new DoubleSetting("Range", 12, 2, 12, 0.05);
	
	private EntityLivingBase target = null;
	
	@Override
	public void onEnable() {
		target = null;
	}
	
	@Override
	public void onDisable() {
		target = null;
	}
	
	@EventHandler
	private Handler<EventMouseDeltasUpdate> onMouseDeltasUpdate = e -> {
		if (e.isPost() || target == null)
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		EntityLivingBase target = this.target;
		
		// Calculate the rotations
		double collisionBorderSize = target.getCollisionBorderSize();
		AxisAlignedBB boundingBox = target.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
		float[] rots = RotationUtils.getRotations(new Vector3d(target.posX, target.posY + ((boundingBox.maxY - boundingBox.minY) / 2), target.posZ));
		RotationUtils.makeRotationValuesLoopCorrectly(new float[] {thePlayer.rotationYaw}, rots);
		rots[0] -= thePlayer.rotationYaw;
		rots[1] = thePlayer.rotationPitch - rots[1];
		
		switch (mode.getMode()) {
			case "Passive":{
				float[] lockMouseMovements = RotationUtils.getMouseDeltasForRotationOffset(rots);
				double slowdown = passiveSlowdown.getValue();
				
				// Yaw correction
				if (MathUtils.getRange(0, lockMouseMovements[0])
						<
					MathUtils.getRange(e.getDeltaX(), lockMouseMovements[0])) {
					e.setDeltaX((int)(e.getDeltaX() * slowdown));
				}
				
				// Pitch correction
				if (MathUtils.getRange(0, lockMouseMovements[1])
						<
					MathUtils.getRange(e.getDeltaY(), lockMouseMovements[1])) {
					e.setDeltaY((int)(e.getDeltaY() * slowdown));
				}
				
			}break;
			case "Lock":{
				float[] mouseMovements = RotationUtils.getMouseDeltasForRotationOffset(rots);
				e.setDeltaX((int)mouseMovements[0]);
				e.setDeltaY((int)mouseMovements[1]);
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;

		// When a target is found but then goes out of fov, the cheat continues to use them as the target.
		// This checks for that and resets the target if needed
		if (target != null){
			float[] rots = RotationUtils.getRotations(new Vector3d(this.target.posX, this.target.posY, this.target.posZ));
			RotationUtils.makeRotationValuesLoopCorrectly(new float[] {thePlayer.rotationYaw, thePlayer.rotationPitch}, rots);
			if (MathUtils.getDistance2D(thePlayer.rotationYaw, thePlayer.rotationPitch, rots[0], rots[1]) > fov.getValue())
				this.target = null;
		}

		if (target == null || (!raytraceCheck.is("Off") && (raytraceCheck.is("Simple") ? !mc.thePlayer.canEntityBeSeen(target) : !mc.thePlayer.raytraceEntity(target))) 
				|| thePlayer.getDistanceToEntity(target) > range.getValue() || mc.theWorld.getEntityByID(target.getEntityId()) == null) {
			EntityLivingBase[] targets = getTargets();
			if (targets == null || targets.length == 0) {
				target = null;
				return;
			}
			this.target = targets[0];
		}
	};
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (e.isPost() || e.isCanceled() || !(e.getPacket() instanceof C02PacketUseEntity))
			return;
		C02PacketUseEntity c02 = (C02PacketUseEntity)e.getPacket();
		if (c02.getAction() != Action.ATTACK)
			return;
		Entity entity = mc.theWorld.getEntityByID(c02.getEntityId());
		if (entity == null || !(entity instanceof EntityLivingBase))
			return;
		this.target = (EntityLivingBase) entity;
	};
	
	/**
	 * Gets an array of valid targets
	 * @return An array of valid targets
	 */
	private EntityLivingBase[] getTargets() {
		ArrayList<Entity> potentialTargets = new ArrayList<>(mc.theWorld.loadedEntityList);
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		// Remove non living and out of range entities
		ModAntiBot modAntiBot = Kagu.getModuleManager().getModule(ModAntiBot.class);
		potentialTargets = potentialTargets.stream()
				.filter(ent -> (targets.is("Players") ? ent instanceof EntityPlayer : ent instanceof EntityLivingBase) 
						&& thePlayer.getDistanceToEntity(ent) <= range.getValue()
						&& ent != mc.thePlayer
						&& (raytraceCheck.is("Off") || (raytraceCheck.is("Simple") ? mc.thePlayer.canEntityBeSeen(ent) : mc.thePlayer.raytraceEntity(ent)))
						&& (((EntityLivingBase) ent).getMaxHealth() <= 0 || ((EntityLivingBase) ent).getHealth() > 0)
						&& (!(ent instanceof EntityPlayer) || (modAntiBot.isEnabled()
								? !modAntiBot.isBot((EntityPlayer) ent)
								: true)))
				.collect(Collectors.toCollection(ArrayList::new));
		
		// Sort them based on the fov distance
		potentialTargets.sort(Comparator.comparingDouble(ent -> {
			float[] rots = RotationUtils.getRotations(new Vector3d(ent.posX, ent.posY, ent.posZ));
			RotationUtils.makeRotationValuesLoopCorrectly(new float[] {thePlayer.rotationYaw, thePlayer.rotationPitch}, rots);
			return MathUtils.getDistance2D(thePlayer.rotationYaw, thePlayer.rotationPitch, rots[0], rots[1]);
		}));
		
		// Remove out of fov targets
		potentialTargets = potentialTargets.stream().filter(ent -> {
			float[] rots = RotationUtils.getRotations(new Vector3d(ent.posX, ent.posY, ent.posZ));
			RotationUtils.makeRotationValuesLoopCorrectly(new float[] {thePlayer.rotationYaw, thePlayer.rotationPitch}, rots);
			return MathUtils.getDistance2D(thePlayer.rotationYaw, thePlayer.rotationPitch, rots[0], rots[1]) <= fov.getValue();
		}).collect(Collectors.toCollection(ArrayList::new));
		
		// Return
		EntityLivingBase[] targets = new EntityLivingBase[0];
		targets = potentialTargets.toArray(targets);
		return potentialTargets.toArray(targets);
	}
	
}
