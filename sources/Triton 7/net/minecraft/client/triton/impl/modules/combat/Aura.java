package net.minecraft.client.triton.impl.modules.combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.triton.impl.modules.combat.aura.Switch;
import net.minecraft.client.triton.impl.modules.combat.aura.Tick;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.friend.FriendManager;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.LiquidUtils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

@Mod(displayName = "Aura")
public class Aura extends Module {
	private Switch switchMode;
	public Tick tick;
	@Op(min = 100.0, max = 400.0, increment = 25.0, name = "Switch Speed")
	public float speed = 244;
	@Op(min = 1.0, max = 7.0, increment = 0.1, name = "Range")
	public double range;
	@Op(min = 0.0, max = 180.0, increment = 5.0, name = "Degrees")
	public double degrees;
	@Op(name = "Ticks Existed", min = 0.0, max = 80.0, increment = 1.0)
	public double ticksExisted;
	@Op(name = "Prioritize Enemy")
	public boolean enemyPriority;
	@Op(name = "Players")
	private boolean players;
	@Op(name = "Creatures")
	private boolean animals;
	public boolean noslowdown = true;
	@Op(name = "Criticals")
	public boolean criticals;
	@Op(name = "Auto Block")
	public boolean autoblock;
	@Op(name = "Armor Check")
	private boolean armorCheck;
	@Op(name = "Armor Breaker")
	public boolean dura;

	public Aura() {
		this.setProperties("Aura", "Aura", Module.Category.Combat, 0, "", true);
		this.switchMode = new Switch("Switch", true, this);
		this.tick = new Tick("Tick", false, this);
		this.range = 4.2;
		this.degrees = 90.0;
		this.ticksExisted = 10.0;
		this.players = true;
	}

	@Override
	public void postInitialize() {
		OptionManager.getOptionList().add(this.switchMode);
		OptionManager.getOptionList().add(this.tick);
		this.updateSuffix();
		super.postInitialize();
	}

	@EventTarget
	private void onUpdate(UpdateEvent event) {
		this.switchMode.onUpdate(event);
		this.tick.onUpdate(event);
	}

	@EventTarget
	private void onTick(final TickEvent event) {
		this.updateSuffix();
	}

	private void updateSuffix() {
		if (this.switchMode.getValue()) {
			this.setSuffix("Switch");
		} else {
			this.setSuffix("Tick");
		}
	}

	public boolean isEntityValid(final Entity entity) {
		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLiving = (EntityLivingBase) entity;
			if (!ClientUtils.player().isEntityAlive() || !entityLiving.isEntityAlive()
					|| entityLiving.getDistanceToEntity(ClientUtils
							.player()) > (ClientUtils.player().canEntityBeSeen(entityLiving) ? this.range : 3.0)) {
				return false;
			}
			if (entityLiving.ticksExisted < this.ticksExisted) {
				return false;
			}
			if (this.players && entityLiving instanceof EntityPlayer) {
				final EntityPlayer entityPlayer = (EntityPlayer) entityLiving;
				return !FriendManager.isFriend(entityPlayer.getName())
						&& (!this.armorCheck || this.hasArmor(entityPlayer));
			}
			if (this.animals && (entityLiving instanceof EntityAnimal || entityLiving instanceof EntitySquid
					|| entityLiving instanceof EntityMob || entityLiving instanceof EntityGhast)) {
				return true;
			}
		}
		return false;
	}

	private boolean hasArmor(final EntityPlayer player) {
		final ItemStack boots = player.inventory.armorInventory[0];
		final ItemStack pants = player.inventory.armorInventory[1];
		final ItemStack chest = player.inventory.armorInventory[2];
		final ItemStack head = player.inventory.armorInventory[3];
		return boots != null || pants != null || chest != null || head != null;
	}

	public void attack(final EntityLivingBase entity) {
		this.attack(entity, this.criticals);
	}

	public void attack(final EntityLivingBase entity, final boolean crit) {
		this.swingItem();
		if (crit) {
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX,
					ClientUtils.player().posY + 0.05000000074505806D, ClientUtils.player().posZ, false));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX,
					ClientUtils.player().posY, ClientUtils.player().posZ, false));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX,
					ClientUtils.player().posY + 0.012511000037193298D, ClientUtils.player().posZ, false));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.player().posX,
					ClientUtils.player().posY, ClientUtils.player().posZ, false));
		} else {
			ClientUtils.packet(new C03PacketPlayer(true));
		}
		ClientUtils.packet(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
	}

	public static boolean isOnSameTeam(final EntityPlayer entity, final EntityPlayer entity2) {
		return entity.getDisplayName().getFormattedText().contains("§" + getTeamFromName(entity))
				&& entity2.getDisplayName().getFormattedText().contains("§" + getTeamFromName(entity2));
	}

	public static String getTeamFromName(final Entity e) {
		final Matcher m = Pattern.compile("§(.).*§r").matcher(e.getDisplayName().getFormattedText());
		if (m.find()) {
			return m.group(1);
		}
		return "f";
	}

	public void swingItem() {
		ClientUtils.player().swingItem();
	}
}
