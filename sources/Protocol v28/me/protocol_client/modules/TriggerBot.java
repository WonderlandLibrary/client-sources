package me.protocol_client.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class TriggerBot extends Module{

	public TriggerBot() {
		super("Triggerbot", "triggerbot", 0, Category.COMBAT, new String[]{"dsdfsdfsdfsdghgh"});
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	public int ticks;
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		ticks++;
		if(ticks >= (20 / Protocol.auraModule.delay.getValue())){
		if(Minecraft.getMinecraft().objectMouseOver != null && Protocol.getAura().getCurrent().isAttackable((EntityLivingBase) Minecraft.getMinecraft().objectMouseOver.entityHit)){
					Wrapper.getPlayer().swingItem();
					Wrapper.mc().playerController.attackEntity(Wrapper.getPlayer(),  Minecraft.getMinecraft().objectMouseOver.entityHit);
				}
		ticks = 0;
		}
}
}
