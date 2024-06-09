package ooo.cpacket.ruby.module.attack;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.misc.GodMode;
import ooo.cpacket.ruby.util.CombatUtil;
import ooo.cpacket.ruby.util.EntityHelper;
import ooo.cpacket.ruby.util.Timer;

public class KillAura extends Module {

	private Timer timer = new Timer();

	public static EntityLivingBase XD = null;

	public KillAura(String name, int key, Category category) {
		super(name, key, category);
		this.addNumberOption("APS", 6, 1, 20, true);
		this.addNumberOption("Reach", 3.4, 1, 6, false);
		this.addBool("Villagers", false);
		this.addBool("Players", true);
		this.addBool("Autoblock", true);
	}

	@EventImpl
	public void onPreMotionUpdate(EventMotionUpdate event) {
		EntityLivingBase e = XD = this.getTarget();
		boolean ab = this.getBool("Autoblock");
		if (CombatUtil.canBlock() && ab && mc.thePlayer.getHeldItem() != null
				&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
			if (event.getState() == State.PRE && mc.thePlayer.getHeldItem() != null
					&& mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				mc.thePlayer.setItemInUse(mc.thePlayer.getHeldItem(), 155);
				mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
				if (mc.thePlayer.ticksExisted % 1 == 0) mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
						C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
			}
			if (event.getState() == State.POST) {
				
			}
		}
		if (e == null)
			return;
		long APS = (long) this.getDouble("APS");
		if (APS > 4 && Ruby.getRuby.getModuleManager().getModule(GodMode.class).isEnabled()) {
			APS = 4;
		}
		float f[] = EntityHelper.getEntityRotations(mc.thePlayer, e);
		event.setYaw(f[0]);
		mc.thePlayer.rotationYawHead = event.getYaw();
		event.setPitch(f[1]);
		mc.thePlayer.rotationPitchHead = event.getPitch();
		if (event.getState() == State.POST) {
			if (this.timer.hasReached(1000L / APS)) {
				mc.thePlayer.swingItem();
				mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
				this.timer.reset();
			}
		}
	}

	@Override
	public void onEnable() {
		long APS = (long) this.getDouble("APS");
		if (APS > 4 && Ruby.getRuby.getModuleManager().getModule(GodMode.class).isEnabled()) {
			Ruby.getRuby.chat("APS Limited to 4 for compatibility with godmode.");
		}
	}

	@Override
	public void onDisable() {
		XD = null;
	}

	// TODO improve stuff for aura
	public EntityLivingBase getTarget() {
		for (Entity o : (List<Entity>) mc.theWorld.loadedEntityList) {
			if (o instanceof EntityPlayer && !o.isDead && o != mc.thePlayer && (mc.thePlayer
					.getDistanceToEntity(o) <= (mc.thePlayer.canEntityBeSeen(o) ? this.getDouble("Reach") : 3.1))) {
				if (Ruby.getRuby.getModuleManager().getModule(AntiBot.class).isEnabled()
						&& !AntiBot.isEntityValidTarget(o)) {
					continue;
				}
				return (EntityPlayer) o;
			}
			if (o instanceof EntityVillager && this.getBool("Villagers") && !o.isDead
					&& (mc.thePlayer.getDistanceToEntity(o) <= this.getDouble("Reach")))
				return (EntityVillager) o;
		}
		return null;
	}

}
