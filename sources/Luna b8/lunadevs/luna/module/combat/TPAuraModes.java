package lunadevs.luna.module.combat;

import java.util.Random;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import lunadevs.luna.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

public class TPAuraModes extends Module {

	public static boolean active;
	@Option.Op(name = "Teleport")
	public static boolean Teleport = false;
	@Option.Op(name = "Packet")
	public static boolean Packet = false;

	public TPAuraModes() {
		super("TPAura", 0, Category.COMBAT, true);
	}

	public static String modname;
	private Random random = new Random();

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Teleport == true) {
			teleport();
			if (this.Teleport == true) {
				this.Teleport = false;

			}
			modname = "Teleport";
		}else if (this.Packet == true) {
			packet();
			 mc.thePlayer.swingItem();
			if (this.Teleport == true) {
				this.Teleport = false;

			}
			modname = "Packet";
			

		super.onUpdate();
	}}

	private void teleport() {
		if (this.Teleport == true) {
			this.modname = "Teleport";
			EntityPlayer en = EntityUtils.getClosestEntity1();
		    if ((en == null) || 
		      (Minecraft.thePlayer.getDistanceToEntity(en) > 10.0 && !Minecraft.thePlayer.isInvisible()))
		    {
		      EntityUtils.lookChanged = false;
		      return;
		    }
		    EntityUtils.lookChanged = true;
		    {
		      Minecraft.thePlayer.setPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
		        en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D);
		      }
		      EntityUtils.faceEntity(en);
		      mc.thePlayer.swingItem();
		      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, 
		        C02PacketUseEntity.Action.ATTACK));
		}
	}
	
	private void packet() {
		if (this.Packet == true) {
			this.modname = "Packet";
			EntityPlayer en = EntityUtils.getClosestEntity1();
		    if ((en == null) || 
		      (Minecraft.thePlayer.getDistanceToEntity(en) > 10.0 && !Minecraft.thePlayer.isInvisible()))
		    {
		      EntityUtils.lookChanged = false;
		      return;
		    }
		    EntityUtils.lookChanged = true;
		    {
		      zCore.p().sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(en.posX + this.random.nextInt(3) * 2 - 2.0D, 
		  	        en.posY, en.posZ + this.random.nextInt(3) * 2 - 2.0D, true));
		      }
		      EntityUtils.faceEntity(en);
		      mc.thePlayer.swingItem();
		      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(en, 
		        C02PacketUseEntity.Action.ATTACK));
		}
	}
			
	@Override
	public void onDisable() {
		super.onDisable();
		mc.thePlayer.stepHeight = 0.6f;
		active = false;
	}

	@Override
	public void onEnable() {
		active = true;
		super.onEnable();
	}

	@Override
	public String getValue() {

		return modname;
	}

}
