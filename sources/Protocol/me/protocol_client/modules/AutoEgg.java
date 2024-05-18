package me.protocol_client.modules;

import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPrePlayerUpdate;

public class AutoEgg extends Module{

	public AutoEgg() {
		super("Auto Egg", "autoegg", 0, Category.PLAYER, new String[]{"autoegg", "egg"});
	}
	public final ClampedValue<Float>	amount	= new ClampedValue<>("autoegg_amount", 20f, 2f, 500f);
	public void onEnable(){
		EventManager.register(this);
	}
	public void onDisable(){
		EventManager.unregister(this);
	}
	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event){
		if(Wrapper.getPlayer().getHeldItem() != null && Wrapper.getPlayer().getHeldItem().getItem() instanceof ItemMonsterPlacer && Wrapper.mc().gameSettings.keyBindUseItem.pressed){
			for(int i = 0; i < amount.getValue(); i++){
				Wrapper.mc().rightClickDelayTimer = 0;
				Wrapper.mc().rightClickMouse();
			}
			Wrapper.mc().rightClickDelayTimer = 4;
			Wrapper.mc().gameSettings.keyBindUseItem.pressed = false;
		}
	}
	public void runCmd(String s){
		try{
			float nigger = Float.parseFloat(s);
			this.amount.setValue(nigger);
		}catch(Exception e){
			Wrapper.invalidCommand("Auto Egg");
			Wrapper.tellPlayer("§7Please Use an integer. -autoegg <amount>");
		}
	}
}
