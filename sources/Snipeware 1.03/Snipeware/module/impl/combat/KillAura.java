package Snipeware.module.impl.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.network.PacketThreadUtil;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.render.EventRender3D;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.module.impl.movement.Speed;
import Snipeware.module.impl.world.Scaffold;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.RotationUtils;
import Snipeware.util.visual.RenderUtil;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import font.Vec2f;
import io.netty.util.NetUtil;
import me.tojatta.api.utilities.angle.Angle;
import me.tojatta.api.utilities.angle.AngleUtility;
import me.tojatta.api.utilities.vector.impl.Vector3;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class KillAura extends Module {
	
	public EnumValue<RotationsMode> rotationsMode = new EnumValue<>("Rotations Mode", RotationsMode.Normal);
	
	public EnumValue<Mode> killauraMode = new EnumValue<>("KillAura Mode", Mode.Switch);
	
	public EnumValue<BlockMode> blockMode = new EnumValue<>("Autoblock Mode", BlockMode.NCP);
	
	public EnumValue<SortingMode> sortingMode = new EnumValue<>("Sorting Mode", SortingMode.Health);

	public EnumValue<EventMode> eventMode = new EnumValue<>("Attack Event", EventMode.Post);
	
	public NumberValue<Integer> maxCPS = new NumberValue<>("Maximum CPS", 13, 1, 20, 1);
	public NumberValue<Integer> minCPS = new NumberValue<>("Minimum CPS", 9, 1, 20, 1);
	
	public NumberValue<Float> range = new NumberValue<>("Attack Range", 4.2f, 1.0f, 6.0f, 0.2f);


	public BooleanValue teams = new BooleanValue("Teams", true);
	public BooleanValue animals = new BooleanValue("Animals", true);
	public BooleanValue players = new BooleanValue("Players", true);
	public BooleanValue passives = new BooleanValue("Passives", true);
	public BooleanValue autoblock = new BooleanValue("Autoblock", true);
	public BooleanValue monsters = new BooleanValue("Monsters", true);
	public BooleanValue particles = new BooleanValue("Particles", false);
	public BooleanValue invisibles = new BooleanValue("Invisibles", true);
	public BooleanValue lockview = new BooleanValue("Lock View", false);
	public BooleanValue noSwing = new BooleanValue("No Swing", false);
	public BooleanValue autism = new BooleanValue("Autism", true);
	
	public static float rangee;
	
	
	public List<EntityLivingBase> targets = new ArrayList<>();
	
	private List<Packet> packets = new ArrayList<>();
	
	public EntityLivingBase target;
	
	public int index;
	
	public int tick;
	
	private int AutismYaw;
	private int AutismPitch;
	
	private boolean shouldminus;
	
	public boolean rar = false;
	
	public TimeHelper attackTimer = new TimeHelper();
	public boolean block;
	private final TimeHelper RotationTimer2 = new TimeHelper();
	private int smothYaw;
	private final TimeHelper packetsTimer = new TimeHelper();
	private final TimeHelper RotationTimer = new TimeHelper();
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_K, ModuleCategory.COMBAT);
		addValues(killauraMode, sortingMode, blockMode, rotationsMode ,eventMode, maxCPS, minCPS, range, autoblock ,players, teams, monsters, animals, passives, invisibles, noSwing, autism, lockview, particles);
	}
	
	public void onEnable() {
		super.onEnable();
		RotationTimer.reset();
		target = null;
		attackTimer.reset();
		tick = 100;
		rar = false;
	}
	
	public void onDisable() {
		super.onDisable();
		if(block){
			unblock();
			block = false;
		}
		target = null;
		attackTimer.reset();
		rar = false;
	}
	
	private enum Mode {
		Switch, Single, Multi;
	}

	private enum EventMode{
		Pre, Post;
	}
	
	private enum SortingMode {
		Health, Range, Angle, FOV;
	}
	
	public enum RotationsMode {
		Normal, Smooth, AAC, NCP, None;
	}
	
	private enum BlockMode {
		Watchdog, NCP, Vanilla, Fake;
	}
	
	public boolean hasSword() {
		return mc.thePlayer.getHeldItem().getItem() instanceof ItemSword && mc.thePlayer.getHeldItem() != null;
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(killauraMode.getValueAsString());
		
		rangee = range.getValue();
		
		final Scaffold scaffold = (Scaffold) Client.INSTANCE.getModuleManager().getModule("scaffold");
		if (scaffold != null) {
			if (scaffold.isEnabled()) 
				return;
		}
		getTargets();
		targetsSort();
		
		++tick;
		if(block){
			unblock();
			block = false;
		}
		

        if (index >= targets.size())
            index = 0;



        target = !targets.isEmpty() && index < targets.size() ? targets.get(index) : null;
        
        switch (event.getType()) {
		case PRE:
			
			boolean shouldAutism = mc.thePlayer.getDistanceToEntity(target) < 3.5f && autism.isEnabled();
			float pitch = 0;
			float yaw = 0;
			
			final AngleUtility angleUtil = new AngleUtility(10, 190, 10, 10);
			
			Vector3<Double> enemyCoords = new Vector3<>(target.posX, target.posY, target.posZ);
			Vector3<Double> myCoords = new Vector3<>(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
			
			Angle dstAngle = angleUtil.calculateAngle(enemyCoords, myCoords);
			
			Angle srcAngle = new Angle(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
			
			Angle smoothedAngle = angleUtil.smoothAngle(dstAngle, srcAngle);
			
			switch (rotationsMode.getValue()) {
				case AAC: {
					if(RotationTimer2.isDelayComplete(2)) {
						pitch = (float) (RotationUtils.getNeededRotations(target)[1]);
						yaw = (float) (RotationUtils.getNeededRotations(target)[0]);
               			if(smothYaw <= yaw) {
               				smothYaw += 5;
               			}
               			if(smothYaw >= yaw) {
               				smothYaw -= 5;
               			}
               			RotationTimer.reset();
               		}
					
				
					break;
				}
				case Normal: {
					rar = false;
					pitch = RotationUtils.getNeededRotations(target)[1];
					yaw = RotationUtils.getNeededRotations(target)[0];
					break;
				}
				case NCP: { 
					break;
				}
				case Smooth: {
					yaw = dstAngle.getYaw();
					pitch = dstAngle.getPitch();
					break;
				}
				case None: {
					rar = true;
					break;
				}
	
			}

			if(block && target == null){
				unblock();
				block = false;
			}
			
		
			if(RotationTimer.isDelayComplete(5)) {
				AutismYaw += 20;
				if(!shouldminus) {
					AutismPitch += 15;
				}else {
					AutismPitch -= 15;
				}
				if(AutismPitch >= 85) {
					shouldminus = true;
				}else if(AutismPitch <= 5){
					shouldminus = false;
				}
				RotationTimer.reset();
			}
			
		if(!rar) {	
			if (lockview.isEnabled()) {
				if(rotationsMode.getValueAsString() == "AAC") {
					mc.thePlayer.rotationYaw = smothYaw;
					mc.thePlayer.rotationPitch = pitch;
				}else {
					mc.thePlayer.rotationYaw = yaw;
					mc.thePlayer.rotationPitch = shouldAutism ? AutismPitch : pitch;
				}
			} else {
				if(rotationsMode.getValueAsString() == "AAC") {
					event.setYaw(smothYaw);
					event.setPitch(pitch);
				}else {
					event.setYaw(shouldAutism ? AutismYaw : yaw);
					event.setPitch(shouldAutism ? AutismPitch : pitch);
				}
	
			
			}
			
		}


			if(eventMode.getValue() == EventMode.Pre){
				boolean illegal = minCPS.getValue() > maxCPS.getValue();
				boolean equalto = minCPS.getValue() == maxCPS.getValue() || maxCPS.getValue() == minCPS.getValue();
				int delay = equalto ? maxCPS.getValue() : illegal ? ThreadLocalRandom.current().nextInt(maxCPS.getValue(), minCPS.getValue()) : ThreadLocalRandom.current().nextInt(minCPS.getValue(), maxCPS.getValue());
				if (attackTimer.isDelayComplete(1000 / delay)) {
					if (isValid(target)) {
				
						if (noSwing.isEnabled()) {
							mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0APacketAnimation());
						}
						else {
							mc.thePlayer.swingItem();
						}
						mc.thePlayer.onEnchantmentCritical(target);
						mc.thePlayer.onCriticalHit(target);
						tick = 0;
						mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
						/*
							float pines = mc.thePlayer.getDistanceToEntity(target) - 3.0f;
							if(pines > 0) {
							Logger.print(String.valueOf(pines));
							}
						 */
						attackTimer.reset();
					}
				}
			}



			if (autoblock.isEnabled() && target != null && hasSword()) {
				switch (blockMode.getValue()) {
				case Watchdog:{
					block = true;
					break;
				}
					case NCP: {
						//mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), mc.thePlayer.getHeldItem().getMaxItemUseDuration());
						break;
					}
					case Vanilla: {
						mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
						break;
					}case Fake:{
						block = true;
						break;
					}
				}
			}
			break;
		case POST:
			boolean illegal = minCPS.getValue() > maxCPS.getValue();
			boolean equalto = minCPS.getValue() == maxCPS.getValue() || maxCPS.getValue() == minCPS.getValue();
			int delay = equalto ? maxCPS.getValue() : illegal ? ThreadLocalRandom.current().nextInt(maxCPS.getValue(), minCPS.getValue()) : ThreadLocalRandom.current().nextInt(minCPS.getValue(), maxCPS.getValue());
			if (attackTimer.isDelayComplete(1000 / delay) && eventMode.getValue() == EventMode.Post) {
				if (isValid(target)) {

					if (noSwing.isEnabled()) {
						mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C0APacketAnimation());
					}
					else {
						mc.thePlayer.swingItem();
					}
					mc.thePlayer.onEnchantmentCritical(target);
					mc.thePlayer.onCriticalHit(target);
					tick = 0;
					mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, Action.ATTACK));
				/*
						float pines = mc.thePlayer.getDistanceToEntity(target) - 3.0f;
						if(pines > 0) {
						Logger.print(String.valueOf(pines));
						}
						 */
					attackTimer.reset();
				
				}
			}

			block();
        }
	}

	private void getTargets() {
		targets.clear();
		for (final Entity ent : mc.theWorld.getLoadedEntityList()) {
			if (ent instanceof EntityLivingBase) {
				final EntityLivingBase entLiving = (EntityLivingBase) ent;
				if (isValid(entLiving)) {
					targets.add(entLiving);
				}
			}
		}
    }
	
	public boolean isValid(EntityLivingBase entLiving) {
		return PlayerUtil.isValid(entLiving, players.isEnabled(), monsters.isEnabled(), animals.isEnabled(), teams.isEnabled(), invisibles.isEnabled(), passives.isEnabled(), range.getValue());
	}

	private void targetsSort() {
		switch (sortingMode.getValue()) {
		case Angle:
			targets.sort((o1, o2) -> {
				float[] rot1 = RotationUtils.getNeededRotations(o1);
				float[] rot2 = RotationUtils.getNeededRotations(o2);
				return (int) (mc.thePlayer.rotationYaw - rot1[0] - (mc.thePlayer.rotationYaw - rot2[0]));
			});
			break;
		case Health:
			this.targets.sort(Comparator.comparingDouble(EntityLivingBase::getHealth));
			break;
		case Range:
			targets.sort((o1, o2) -> (int) (o1.getDistanceToEntity(mc.thePlayer) - o2.getDistanceToEntity(mc.thePlayer)));
			break;
		case FOV:
			targets.sort(Comparator.comparingDouble(o -> RotationUtils.getDistanceBetweenAngles(mc.thePlayer.rotationPitch, RotationUtils.getNeededRotations(o)[0])));
			break;
		}
	}
	
	private void block() {
		switch (blockMode.getValue()) {
		case Watchdog:{
			if (!block && autoblock.isEnabled() && target != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				block = true;
				   mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.INTERACT));
                   mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
			break;
			}
		}
			case NCP: {
				if (!block && autoblock.isEnabled() && target != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
					block = true;
					mc.thePlayer.sendQueue.addToSendQueue(
							new C02PacketUseEntity(this.target,
									this.target.getPositionVector()));
					mc.thePlayer.sendQueue.addToSendQueue(
							new C02PacketUseEntity(this.target,
									C02PacketUseEntity.Action.INTERACT));
					mc.getNetHandler().addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
				}
				break;
			}
			case Vanilla: {
				break;
			}
			case Fake:{
				if (!block && autoblock.isEnabled() && target != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				block = true;
				}
				break;
			}
		}
	}

	private void unblock() {
		switch (blockMode.getValue()) {
		case Watchdog:{
			if (block && autoblock.isEnabled() && target != null) {
				block = false;
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
			}
		}
		
			case NCP: {
				if (block && autoblock.isEnabled() && target != null) {
					block = false;
					mc.getNetHandler().addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
				}
				break;
			}
			case Vanilla: {
				break;
			}
			case Fake:{
				if (block && autoblock.isEnabled() && target != null) {
				block = true;
				}
				break;
			}
		}
	}
	

	 
}