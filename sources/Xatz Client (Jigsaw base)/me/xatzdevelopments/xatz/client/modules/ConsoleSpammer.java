package me.xatzdevelopments.xatz.client.modules;

import me.xatzdevelopments.xatz.client.events.UpdateEvent;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.client.tools.MarisaTimer;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.util.BlockPos;

public class ConsoleSpammer extends Module {

	MarisaTimer time = new MarisaTimer();

	public ConsoleSpammer() {
		super("ConsoleSpammer", 0, Category.EXPLOITS, "Spams the console with a lot of shit.");
		
	}
	@Override
	public void onEnable(){
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
	
		super.onDisable();
	}
	
	@Override
	public void onUpdate(UpdateEvent event) {

		if (time.hasPassed(250)){
			mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete("//search d"+ (int) (Math.random() * 1000), new BlockPos(0, 0, 0)));
			time.reset();
		}
		
		
		
		
		super.onUpdate(event);
	}
	
}