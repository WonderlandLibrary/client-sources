package win.sightclient.module.combat;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.render.EventRender2D;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.movement.Scaffold;
import win.sightclient.module.player.AutoPot;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.notification.Notification;
import win.sightclient.utils.RotationUtils;
import win.sightclient.utils.TimerUtils;
import win.sightclient.utils.minecraft.CombatUtils;

public class Killaura extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Switch", "Single", "Priority"});
	private ModeSetting autoblockmode = new ModeSetting("AutoBlock", this, new String[] {"NCP", "Vanilla", "None"});
	private ModeSetting priority = new ModeSetting("Priority",  this, new String[] {"Crosshair", "Health", "Distance"});
	private ModeSetting rotationsmode = new ModeSetting("Rotations", this, new String[] {"Normal", "Smooth", "Legit", "None"});
	private ModeSetting hit = new ModeSetting("HitMode", this, new String[] {"Pre", "Post"});
	public NumberSetting range = new NumberSetting("Range", this, 4.2, 1, 6, false);
	private NumberSetting switchdelay = new NumberSetting("SwitchDelay", this, 350, 1, 1000, true);
	private NumberSetting apsSet = new NumberSetting("APS", this, 10, 1.5, 20, false);
	private NumberSetting hitchance = new NumberSetting("HitChance", this, 100, 1, 100, true);
	private BooleanSetting randomizeAps = new BooleanSetting("RandomAPS", this, true);
	private BooleanSetting thruwalls = new BooleanSetting("Walls", this, true);
	private BooleanSetting disable = new BooleanSetting("AutoDisable", this, true);
	private BooleanSetting noswing = new BooleanSetting("NoSwing", this, false);
	private BooleanSetting raytrace = new BooleanSetting("Raytrace", this, false);
	public ModeSetting thud = new ModeSetting("TargetHUD", this, new String[] {"Simple", "Advanced", "Novoline", "None"});

	private TimerUtils switchTimer = new TimerUtils();
	private TimerUtils attackTimer = new TimerUtils();
	public static EntityLivingBase target;
	private double aps;
	private boolean isBlocking;
	public static boolean isAttackTick = false;
	public static boolean canBlock;
	private float lastYaw;
	private float lastPitch;
	
	public Killaura() {
		super("Killaura", Category.COMBAT);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate) e;
			if (this.disable.getValue() && !mc.thePlayer.isEntityAlive()) {
				this.setToggled(false);
				Sight.instance.nm.send(new Notification("Killaura", "Killaura has automatically disabled."));
				return;
			}

			if (eu.isPre()) {
				this.setTarget(eu);
				this.isAttackTick = false;
			}

			if (this.target == null) {
				TargetHUD.setRunning(false);
				this.canBlock = false;
				crits.reset();
				return;
			}

			if (this.canBlock() && !autoblockmode.getValue().equalsIgnoreCase("Vanilla")) {
				mc.thePlayer.setItemInUse(mc.thePlayer.getCurrentEquippedItem(), 71626);
			}
			
			this.canBlock = this.canBlock();

			if (eu.isPre()) {
				this.rotate(eu);
			}
			if (this.canAttack() && ((hit.getValue().equals("Pre") && eu.isPre()) || (hit.getValue().equals("Post") && !eu.isPre()) )) {
				if (this.canBlock()) {
					this.unBlock();
				}
				if (noswing.getValue()) {
					mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
				} else {
					mc.thePlayer.swingItem();
				}
				if (ThreadLocalRandom.current().nextInt(100) <= hitchance.getValueInt() && raytrace()) {
					this.attack();
					this.isAttackTick = true;
				}
				this.attackTimer.reset();
				if (this.canBlock()) {
					this.block();
				}
			} else if (!this.isBlocking && eu.isPre() && this.canBlock()) {
				this.block();
			}
		} else if (e instanceof EventRender2D) {
			TargetHUD.onRender();
		}
	}
	
	public void setTarget(EventUpdate eu) {
		if (this.target == null || !this.isValid(this.target, eu)) {
			this.target = null;
		}

		if (this.mode.getValue().equalsIgnoreCase("Priority")) {
			this.target = this.getBest(eu);
		} else if (this.mode.getValue().equalsIgnoreCase("Switch")) {
			if (this.switchTimer.hasReached(this.switchdelay.getValue())) {
				ArrayList<EntityLivingBase> targets = this.getTargets(eu);
				EntityLivingBase oldTarget = this.target;
				this.target = null;
				if (targets.size() > 1 && oldTarget != null && targets.contains(oldTarget)) {
					int index = targets.indexOf(oldTarget);
					index++;
					if (index > targets.size() - 1) {
						index = 0;
					}
					this.target = targets.get(index);
					this.switchTimer.reset();
				} else if (targets.size() > 0) {
					this.target = targets.get(0);
					this.switchTimer.reset();
				}
			}
		} else {
			boolean valid = this.isValid(this.target, eu);
			if (this.target != null && valid) {
				return;
			}

			if ((this.target == null) || (this.target != null && !valid)) {
				this.target = this.getBest(eu);
			}
		}
	}

	private ArrayList<EntityLivingBase> getTargets(EventUpdate eu) {
		ArrayList<EntityLivingBase> ts = new ArrayList<EntityLivingBase>();

		for (int k = 0; k < mc.theWorld.loadedEntityList.size(); k++) {
			if (this.isValid(mc.theWorld.loadedEntityList.get(k), eu)) {
				ts.add((EntityLivingBase) mc.theWorld.loadedEntityList.get(k));
			}
		}

		return ts;
	}

	private EntityLivingBase getBest(EventUpdate eu) {
		EntityLivingBase target = null;

		if (this.priority.getValue().equalsIgnoreCase("Distance")) {
			double cDist = this.range.getValue();
			for (int k = 0; k < mc.theWorld.loadedEntityList.size(); k++) {
				if (this.isValid(mc.theWorld.loadedEntityList.get(k), eu)) {
					double dist = this.getDistance(eu, (EntityLivingBase) mc.theWorld.loadedEntityList.get(k));
					if (dist <= cDist) {
						cDist = dist;
						target = (EntityLivingBase) mc.theWorld.loadedEntityList.get(k);
					}
				}
			}
		}

		if (this.priority.getValue().equalsIgnoreCase("Health")) {
			double cHealth = 1000;
			for (int k = 0; k < mc.theWorld.loadedEntityList.size(); k++) {
				if (this.isValid(mc.theWorld.loadedEntityList.get(k), eu)) {
					float health = ((EntityLivingBase) mc.theWorld.loadedEntityList.get(k)).getHealth();
					if (health <= cHealth) {
						cHealth = health;
						target = (EntityLivingBase) mc.theWorld.loadedEntityList.get(k);
					}
				}
			}
		}

		if (this.priority.getValue().equalsIgnoreCase("Crosshair")) {
			double cFOV = 1000;
			for (int k = 0; k < mc.theWorld.loadedEntityList.size(); k++) {
				if (this.isValid(mc.theWorld.loadedEntityList.get(k), eu)) {
					float fov = Math.abs(RotationUtils.getYawToTarget(mc.theWorld.loadedEntityList.get(k)));
					if (fov <= cFOV) {
						cFOV = fov;
						target = (EntityLivingBase) mc.theWorld.loadedEntityList.get(k);
					}
				}
			}
		}
		return target;
	}

	private double getDistance(EventUpdate eu, EntityLivingBase target) {
		return mc.thePlayer.getDistanceToEntity(target);
	}

	private void rotate(EventUpdate eu) {
		if (Scaffold.isPlaceTick || AutoPot.isActivated()) {
			return;
		}
		float toSetYaw = eu.getYaw();
		float toSetPitch = eu.getPitch();
		float[] rots = RotationUtils.getRotations(target);
		if (rotationsmode.getValue().equalsIgnoreCase("Normal")) {
			toSetYaw = rots[0];
			toSetPitch = rots[1];
		} else if (rotationsmode.getValue().equalsIgnoreCase("Smooth")) {
			double speed = ThreadLocalRandom.current().nextDouble(2, 4);
			final float targetYaw = RotationUtils.getYawChange(eu.getLastYaw(),
					target.posX + ThreadLocalRandom.current().nextDouble(-1, 1) * 0.05,
					target.posZ + ThreadLocalRandom.current().nextDouble(-1, 1) * 0.05);
			final float yawFactor = (float) (targetYaw / speed);
			toSetYaw = eu.getLastYaw() + yawFactor;

			double pitchDistance = rots[1] - eu.getLastPitch();
			toSetPitch = (float) (eu.getLastPitch() + (pitchDistance / speed));
		} else if (rotationsmode.getValue().equalsIgnoreCase("Legit")) {
			double speed = ThreadLocalRandom.current().nextDouble(4, 8);
			final float targetYaw = RotationUtils.getYawChange(eu.getLastYaw(),
					target.posX + ThreadLocalRandom.current().nextDouble(-1, 1) * 0.05,
					target.posZ + ThreadLocalRandom.current().nextDouble(-1, 1) * 0.05);
			final float yawFactor = (float) (targetYaw / speed);
			toSetYaw = eu.getLastYaw() + yawFactor;

			double pitchDistance = rots[1] - RotationUtils.roundTo360(eu.getLastPitch());
			toSetPitch = (float) (EventUpdate.getLastPitch() + (pitchDistance / speed));
		}

		this.lastYaw = toSetYaw;
		this.lastPitch = toSetPitch;
		eu.setYaw(toSetYaw);
		eu.setPitch(toSetPitch);
	}
	
	@Override
	public void onEnable() {
		this.target = null;
		this.crits.reset();
		super.onEnable();
		this.isAttackTick = false;
	}

	@Override
	public void onDisable() {
		if (isBlocking && canBlock()) {
			unBlock();
		}
		this.isBlocking = false;
		this.target = null;
		this.canBlock = false;
		super.onDisable();
		this.isAttackTick = false;
	}

	public boolean canBlock() {
		return !this.autoblockmode.getValue().equalsIgnoreCase("None") && mc.thePlayer.getCurrentEquippedItem() != null
				&& mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword;
	}

	public boolean canAttack() {
		if (!this.attackTimer.hasReached(1000.0 / this.aps) || this.target == null) {
			return false;
		}

		return true;
	}
	
	public boolean raytrace() {
		if (raytrace.getValue()) {
			if (target == null) {
				return false;
			}
			float oldYaw = mc.thePlayer.rotationYaw;
			float oldPitch = mc.thePlayer.rotationPitch;
			mc.thePlayer.rotationYaw = this.lastYaw;
			mc.thePlayer.rotationPitch = this.lastPitch;
			
			Entity lookingAt = RotationUtils.getMouseOver(1.0F, mc.thePlayer);
			if (lookingAt != this.target) {
				mc.thePlayer.rotationYaw = oldYaw;
				mc.thePlayer.rotationPitch = oldPitch;
				return false;
			}
			mc.thePlayer.rotationYaw = oldYaw;
			mc.thePlayer.rotationPitch = oldPitch;
		}
		return true;
	}

	private boolean isValid(Entity target, EventUpdate eu) {
		if (target == null) {
			return false;
		}
		if (!CombatUtils.isValid(target, true)) {
			return false;
		}

		if (this.getDistance(eu, (EntityLivingBase) target) > range.getValue()) {
			return false;
		}

		if (!this.thruwalls.getValue() && !mc.thePlayer.canEntityBeSeen(target)) {
			return false;
		}

		return true;
	}

	private TimerUtils crits = new TimerUtils();

	private boolean attack() {
		if (target == null) {
			return false;
		}
		mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
		return true;
	}

	private void unBlock() {
		if (autoblockmode.getValue().equalsIgnoreCase("Vanilla")) {
			mc.playerController.onStoppedUsingItem(mc.thePlayer);
		} else if (autoblockmode.getValue().equalsIgnoreCase("NCP")) {
			mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.1F, -.59F, -.1F), EnumFacing.DOWN));
		}
	}

	private void block() {
		if (autoblockmode.getValue().equalsIgnoreCase("Vanilla")) {
			mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
		} else if (autoblockmode.getValue().equalsIgnoreCase("NCP")) {
			mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
		} else if (autoblockmode.getValue().equalsIgnoreCase("Redesky")) {
			mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
		}
	}

	@Override
	public void updateSettings() {
		aps = apsSet.getValue()
				+ (randomizeAps.getValue() ? ThreadLocalRandom.current().nextDouble(-1, 1) : 0);
		switchdelay.setVisible(mode.getValue().equalsIgnoreCase("Switch"));
		this.priority.setVisible(!this.mode.getValue().equalsIgnoreCase("Switch"));
		this.setSuffix(mode.getValue());
	}
}
