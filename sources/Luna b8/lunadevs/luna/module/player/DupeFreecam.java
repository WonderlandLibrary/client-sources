package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.BoundingBoxEvent;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.events.PacketSendEvent;
import lunadevs.luna.events.PreMotionUpdatesEvent;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C03PacketPlayer;

//Coded by faith.

public class DupeFreecam extends Module {

	double nuumun;
	double nienmun;
	double nuhmun;
	
	public DupeFreecam() {
		super("DupeFreecam", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	public void onUpdate() {
		
	}
	
	public void onEnable() {
		Luna.addChatMessage("Use this for the /pv 1 duplication glitch.");
		  	nuumun = this.mc.thePlayer.posX;
		  	nienmun = this.mc.thePlayer.posY;
		    nuhmun = this.mc.thePlayer.posZ;
		    EntityOtherPlayerMP entity = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
		    entity.inventory = this.mc.thePlayer.inventory;
		    entity.inventoryContainer = this.mc.thePlayer.inventoryContainer;
		    entity.setPositionAndRotation(this.nuumun, this.mc.thePlayer.boundingBox.minY, this.nuhmun, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
		    entity.rotationYawHead = this.mc.thePlayer.rotationYawHead;
		    this.mc.theWorld.addEntityToWorld(-1, entity);
	}

	public void onDisable() {
		mc.thePlayer.setPosition(nuumun, nienmun, nuhmun);
		Luna.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Luna.getPlayer().posX,Luna.getPlayer().posY + 0.01D, Luna.getPlayer().posZ, Luna.getPlayer().onGround));
		mc.thePlayer.capabilities.isFlying = false;
		mc.thePlayer.noClip = false;
		mc.theWorld.removeEntityFromWorld(-1);
	}
	
	@EventTarget
	public void onPre(PreMotionUpdatesEvent event) {
		 this.mc.thePlayer.capabilities.isFlying = true;
		    this.mc.thePlayer.noClip = true;
		    this.mc.thePlayer.capabilities.setFlySpeed(0.1F);
		    event.setCancelled(true);
	}

	@EventTarget
	public void onPacketSend(PacketSendEvent event) {
	    if ((event.getPacket() instanceof C03PacketPlayer)) {
	    	event.setCancelled(true);
	    }
	}
	
	@EventTarget
	public void onBB(BoundingBoxEvent event) {
		event.setCancelled(true);
	}
	
	public String getValue() {
		return null;
	}

}
