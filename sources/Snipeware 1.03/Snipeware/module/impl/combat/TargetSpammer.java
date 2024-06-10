package Snipeware.module.impl.combat;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.module.Module.ModuleCategory;
import net.minecraft.network.play.server.S02PacketChat;

public class TargetSpammer extends Module {

		public TargetSpammer() {
		super("TargetSpammer", 0, ModuleCategory.COMBAT);
	
	}
	
		@Handler
		public void onMotionUpdate(final EventMotionUpdate event) {
			
			
			
			mc.thePlayer.sendChatMessage("/tell" +Client.getInstance().getInstance().getModuleManager().getModule(KillAura.class));
			
		}

}
