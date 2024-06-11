package Hydro.module.modules.player;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;
import net.minecraft.item.ItemFood;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module {

	public FastEat() {
		super("FastEat", 0, true, Category.PLAYER, "Allows you to eat food faster");
		Client.instance.settingsManager.rSetting(new Setting("fasteatPackets", "Packets", this, 20, 5, 50, true));
	}
	
	
	
	@Override
	public void onUpdate() {
		if(!(mc.thePlayer.inventory.getCurrentItem() == null)) {
			if(!(mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemFood))
				return;
			if(!mc.thePlayer.isEating())
				return;
		}else
			return;
		
		for (int i = 0; i < Client.instance.settingsManager.getSettingByName("fasteatPackets").getValDouble(); i++) {
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
		}
	}

}
