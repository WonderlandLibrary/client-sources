package me.protocol_client.modules;

import java.util.ArrayList;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class EnderAura extends Module{
	
	int mode = 0;
	private static ArrayList<EntityPlayer> entities;
	EntityPlayer attacked;
	static float yaw;
	static float pitch;
	static float posx;
	static float posy;
	static float posz;
	int timer;
	public EnderAura() {
		super("Ender Aura", "enderaura", 0, Category.COMBAT, new String[]{"dsdfsdfsdfsdghgh"});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public void onUpdate(){
		timer++;
		if(timer > 5){
			timer = 0;
		}
	}
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
			for(Object o: mc.theWorld.loadedEntityList){
				if(o instanceof EntityPlayer){
					EntityPlayer entity = (EntityPlayer)o;
					if(!(entity instanceof EntityPlayerSP) && Protocol.getAura().getCurrent().isAttackable(entity) && Protocol.getAura().getCurrent().isInAttackRange(entity)){
						double mx = Math.cos(Math.toRadians(entity.rotationYaw + 90.0F));
					    double mz = Math.sin(Math.toRadians(entity.rotationYaw + 90.0F));
					    double x = 1.0F * 1.1 * mx + 0.0F * 1.1 * mz;
					    double z = 1.0F * 1.1 * mz - 0.0F * 1.1 * mx;
						for(int i = 0; i < 5; i++){
							if(entity.onGround)
						    this.mc.thePlayer.setPosition(entity.posX - x, entity.posY, entity.posZ - z);
						    }
							Wrapper.getPlayer().swingItem();
							Wrapper.mc().playerController.attackEntity(Wrapper.getPlayer(), entity);
						}
						}
						
			}
		}
}
