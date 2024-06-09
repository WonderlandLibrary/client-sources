package ooo.cpacket.ruby.module.attack;

import java.util.ArrayList;
import java.util.List;

import me.sopt.pathstuff.GotoAI;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockWeb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.BlockPos;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.manager.FriendManager;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.misc.GodMode;
import ooo.cpacket.ruby.util.Timer;

public class TPAura extends Module {

	private Timer timer = new Timer();
	private Timer timer2 = new Timer();
	public static EntityLivingBase XD = null;
	private GotoAI rolf;

	public TPAura(String name, int key, Category category) {
		super(name, key, category);
		this.addNumberOption("APS", 6, 1, 20, true);
		this.addNumberOption("Reach", 30, 30, 420, false);
		this.addBool("Players", true);
		this.addBool("Autoblock", true);
	}

	@EventImpl
	public void onPreMotionUpdate(EventMotionUpdate event) {
		List<EntityLivingBase> xd = this.getTargets();
		long APS = (long) this.getDouble("APS");
		for (EntityLivingBase e : xd) {
			if (e == null)
				return;
			if (APS > 4 && Ruby.getRuby.getModuleManager().getModule(GodMode.class).isEnabled()) {
				APS = 4;
			}
			if (this.timer.hasReached(1000L / APS)) {
				this.rolf = new GotoAI(e);
//				mc.thePlayer.sendQueue.addToSendQueue(new C04PacketPlayerPosition(mc.thePlayer.posX,
//						mc.thePlayer.posY + 15.4922, mc.thePlayer.posZ, true));
				this.rolf.update("infiniteaura");
				this.rolf = null;
			}
		}
		if (this.timer.hasReached(1000L / APS)) {
			this.timer.reset();
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
	public List<EntityLivingBase> getTargets() {
		List<EntityLivingBase> list = new ArrayList<EntityLivingBase>();
		for (Entity o : (List<Entity>) mc.theWorld.loadedEntityList) {
			if (list.size() > 240)
				continue;
			if (o instanceof EntityPlayer && !o.isDead && o != mc.thePlayer && (mc.thePlayer
					.getDistanceToEntity(o) <= this.getDouble("Reach"))) {
				if (Ruby.getRuby.getModuleManager().getModule(AntiBot.class).isEnabled()
						&& !AntiBot.isEntityValidTarget(o)) {
					continue;
				}
				if (FriendManager.isFriend(o.getName()))
					continue;
				if (o.posY > mc.thePlayer.posY + 12) {
					continue;
				}
				if (o.posY < mc.thePlayer.posY - 0.2) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY + 1, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY + 2, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY + 3, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY + 0.1, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				if (mc.theWorld.getBlockState(new BlockPos(o.posX, o.posY - 1, o.posZ)).getBlock() instanceof BlockWeb) {
					continue;
				}
				list.add((EntityPlayer) o);
			}
		}
		return list;
	}

}
