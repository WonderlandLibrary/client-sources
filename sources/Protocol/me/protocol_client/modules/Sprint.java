package me.protocol_client.modules;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.Value;
import net.minecraft.entity.EntityLivingBase;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Sprint extends Module{

	public Sprint() 
	{
		super("Sprint", "sprint", Keyboard.KEY_V, Category.MOVEMENT, new String[]{"sprint"});
	}
	public final Value<Boolean> multi = new Value<>("sprint_multi", true);
	@EventTarget
	public void onEvent(EventPreMotionUpdates event){
		if(multi.getValue()){
			setDisplayName("Sprint [Multi]");
			if(((Wrapper.getPlayer().moveForward != 0) || (Wrapper.getPlayer().moveStrafing != 0)) && !Wrapper.getPlayer().isSneaking() && !Wrapper.getPlayer().isCollidedHorizontally){
				Wrapper.getPlayer().setSprinting(true);
			}
			return;
		}
		setDisplayName("Sprint [Normal]");
		if((Wrapper.getPlayer().moveForward > 0) && !Wrapper.getPlayer().isSneaking() && !Wrapper.getPlayer().isCollidedHorizontally){ //|| Wrapper.getPlayer().moveStrafing != 0) && !Wrapper.getPlayer().isCollidedHorizontally){
			Wrapper.getPlayer().setSprinting(true);
		}
	}
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
}
