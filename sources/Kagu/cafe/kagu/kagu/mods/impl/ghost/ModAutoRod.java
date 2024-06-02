/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.vecmath.Vector3d;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.player.ModAntiBot;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MathUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.util.ChatComponentText;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

/**
 * @author DistastefulBannock
 *
 */
public class ModAutoRod extends Module {
	
	public ModAutoRod() {
		super("AutoRod", Category.GHOST);
		setSettings(labelSetting, maxDistance, cancelRodDistance, mode, handleHookContact, dontRodInAir);
	}
	
	private LabelSetting labelSetting = new LabelSetting("Switchs to and uses rod");
	private DoubleSetting maxDistance = new DoubleSetting("Max Distance", 6, 5, 12, 0.1);
	private DoubleSetting cancelRodDistance = new DoubleSetting("Panic Cancel Rod Distance", 3.2, 0, 7, 0.1);
	private ModeSetting mode = new ModeSetting("Mode", "Legit", "Legit");
	private ModeSetting handleHookContact = new ModeSetting("Handle Hook Contact", "Switch Slots", "Switch Slots", "Retract");
	private BooleanSetting dontRodInAir = new BooleanSetting("Don't Rod While in Air", true);
	
	private EntityLivingBase target = null;
	private boolean isRodding = false, retractNextTick = false;
	private int lastSlot = -1, cooldownTicks = 0;
	
	@Override
	public void onEnable() {
		target = null;
		isRodding = false;
		lastSlot = -1;
		cooldownTicks = 0;
		retractNextTick = false;
	}
	
	@Override
	public void onDisable() {
		target = null;
		isRodding = false;
		lastSlot = -1;
		cooldownTicks = 0;
		retractNextTick = false;
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		// Set target
		if (target == null || thePlayer.getDistanceToEntity(target) > maxDistance.getValue()) {
			EntityLivingBase[] targets = getTargets();
			if (targets == null || targets.length == 0) {
				target = null;
				if (lastSlot != -1) {
					thePlayer.inventory.currentItem = lastSlot;
					lastSlot = -1;
				}
				retractNextTick = false;
				isRodding = false;
				return;
			}
			this.target = targets[0];
		}
		
		EntityLivingBase target = this.target;
		InventoryPlayer inventory = thePlayer.inventory;
		
		if (target == null) {
			if (lastSlot != -1) {
				thePlayer.inventory.currentItem = lastSlot;
				lastSlot = -1;
			}
			retractNextTick = false;
			isRodding = false;
			return;
		}
		
		// Panic cancel rod if needed
		if (thePlayer.getDistanceToEntity(target) <= cancelRodDistance.getValue()) {
			if (lastSlot != -1) {
				inventory.currentItem = lastSlot;
			}
			isRodding = false;
			retractNextTick = false;
			return;
		}
		
		// Retract if needed
		if (retractNextTick || thePlayer.hurtResistantTime == 19) {
			if (lastSlot != -1) {
				thePlayer.inventory.currentItem = lastSlot;
				lastSlot = -1;
			}
			retractNextTick = false;
			isRodding = false;
			cooldownTicks = 2;
			return;
		}
		
		// Cooldown
		if (cooldownTicks-- > 0) {
			return;
		}
		
		// Hold rod if not held
		for (int i = 0; i < 9; i++) {
			if (inventory.getCurrentItem() != null && inventory.getCurrentItem().getItem() instanceof ItemFishingRod)
				break;
			else if (inventory.getStackInSlot(i) != null && inventory.getStackInSlot(i).getItem() instanceof ItemFishingRod) {
				lastSlot = inventory.currentItem;
				inventory.currentItem = i;
				return;
			}
		}
		// Last check to make sure
		if (!(inventory.getCurrentItem() != null && inventory.getCurrentItem().getItem() instanceof ItemFishingRod)) {
			isRodding = false;
			retractNextTick = true;
			return;
		}
		
		// Handle when hit player
		if (isRodding && thePlayer.fishEntity != null && thePlayer.fishEntity.caughtEntity != null) {
			switch (handleHookContact.getMode()) {
				case "Switch Slots":{
					if (lastSlot != -1) {
						inventory.currentItem = lastSlot;
						lastSlot = -1;
					}else {
						inventory.currentItem = inventory.currentItem - 1 < 0 ? 1 : inventory.currentItem - 1;
					}
				}
				case "Retract":{
					if (mc.gameSettings.keyBindUseItem.getPressTime() <= 0)
						mc.gameSettings.keyBindUseItem.setPressTime(1);
					retractNextTick = true;
				}break;
			}
			isRodding = false;
			cooldownTicks = 2;
			return;
		}
		
		// Handle when miss player
		if (isRodding && thePlayer.fishEntity != null && thePlayer.fishEntity.caughtEntity == null) {
			if (thePlayer.getDistanceToEntity(target) + 1.75 < thePlayer.getDistanceToEntity(thePlayer.fishEntity)
					|| thePlayer.fishEntity.isCollidedHorizontally || thePlayer.fishEntity.isCollidedVertically) {
				retractNextTick = true;
				isRodding = false;
				cooldownTicks = 3;
				return;
			}
		}
		else if (isRodding && (thePlayer.fishEntity == null || thePlayer.fishEntity.isCollidedHorizontally || thePlayer.fishEntity.isCollidedVertically)) {
			retractNextTick = true;
			cooldownTicks = 3;
		}
		
		// Throw rod if held (handled in minecraft class)
		if (!isRodding && (dontRodInAir.isDisabled() || MovementUtils.isTrueOnGround())) {
			isRodding = true;
			if (mc.gameSettings.keyBindUseItem.getPressTime() <= 0)
				mc.gameSettings.keyBindUseItem.setPressTime(1);
		}
	};
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (e.getPacket() instanceof C09PacketHeldItemChange && e.isPre() && isRodding) {
			C09PacketHeldItemChange c09 = (C09PacketHeldItemChange)e.getPacket();
			if (mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFishingRod && mc.thePlayer.inventory.currentItem == c09.getSlotId())
				return;
			if (isRodding)
				retractNextTick = true;
			lastSlot = c09.getSlotId();
		}
		
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
		potentialTargets = (ArrayList<Entity>) potentialTargets.stream()
				.filter(ent -> ent instanceof EntityLivingBase && thePlayer.getDistanceToEntity(ent) <= maxDistance.getValue()
						&& ent != mc.thePlayer
						&& (((EntityLivingBase) ent).getMaxHealth() <= 0 || ((EntityLivingBase) ent).getHealth() > 0)
						&& (!(ent instanceof EntityPlayer) || (modAntiBot.isEnabled()
								? !modAntiBot.isBot((EntityPlayer) ent)
								: true)))
				.collect(Collectors.toList());
		
		// Remove targets that don't fit the required target settings
		potentialTargets = (ArrayList<Entity>) potentialTargets
				.stream()
				.filter(ent -> ent instanceof EntityPlayer
				).collect(Collectors.toList());
		
		// Sort them based on the fov distance
		potentialTargets.sort(Comparator.comparingDouble(ent -> {
			float[] rots = RotationUtils.getRotations(new Vector3d(ent.posX, ent.posY, ent.posZ));
			RotationUtils.makeRotationValuesLoopCorrectly(new float[] {thePlayer.rotationYaw, thePlayer.rotationPitch}, rots);
			return MathUtils.getDistance2D(thePlayer.rotationYaw, thePlayer.rotationPitch, rots[0], rots[1]);
		}));
		
		EntityLivingBase[] targets = new EntityLivingBase[0];
		targets = potentialTargets.toArray(targets);
		return potentialTargets.toArray(targets);
	}
	
	/**
	 * @return the target
	 */
	public EntityLivingBase getTarget() {
		return target;
	}
	
	/**
	 * @return the isRodding
	 */
	public boolean isRodding() {
		return isRodding;
	}
	
}
