package me.swezedcode.client.module.modules.Fight;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.timer.TimerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class TriggerBot extends Module {

	public TriggerBot() {
		super("TriggerBot", Keyboard.KEY_NONE, 0xFF, ModCategory.Fight);
		setDisplayName("Trigger Bot");
	}

	private boolean players = true;
	
	private boolean monsters;

	private TimerUtils timer = new TimerUtils();

	@EventListener
	public void onUpdate(EventPreMotionUpdates e) {
		Random r = new Random();
		for (Object o : mc.theWorld.loadedEntityList) {
			Entity en = (Entity) o;
			if ((this.mc.objectMouseOver != null) && (this.mc.objectMouseOver.entityHit != null)
					&& (attackChecks(this.mc.objectMouseOver.entityHit)) && (this.timer.hD(10 / randomDelay()))
					&& (this.mc.thePlayer.getDistanceToEntity(en) < 3.4)) {
				this.mc.thePlayer.swingItem();
				this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(this.mc.objectMouseOver.entityHit, C02PacketUseEntity.Action.ATTACK));

				this.timer.rt();
			}

		}
	}

	private boolean attackChecks(Entity e) {
		if (e == null) {
			return false;
		}
		if (e == this.mc.thePlayer) {
			return false;
		}
		if (e.getDistanceToEntity(this.mc.thePlayer) > 3.7) {
			return false;
		}
		if (!e.isEntityAlive()) {
			return false;
		}
		boolean isVehicle = ((e instanceof EntityMinecart)) || ((e instanceof EntityBoat));
		if ((e instanceof EntityPlayer)) {
			if (!this.players) {
				return false;
			}
			EntityPlayer player = (EntityPlayer) e;
			if (!this.players) {
				return false;

			}
			if (isVehicle) {
				return false;
			}
			if (((e instanceof EntityMob)) && (!this.monsters)) {
				return false;
			}
			if (((e instanceof EntitySlime)) && (!this.monsters)) {
				return false;
			}
			return true;
		}
		return false;
	}

	private int randomDelay() {
		Random randy = new Random();
		int randyInt = randy.nextInt(15);
		return randyInt;
	}
}