package zamorozka.modules.TRAFFIC;

import java.util.ArrayList;

import de.Hero.settings.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventPostMotionUpdates;
import zamorozka.event.events.EventPreMotionUpdates;
import zamorozka.event.events.EventUpdate;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class AutoSneak extends Module {

	public AutoSneak() {
		super("AutoSneak", 0, Category.TRAFFIC);
	}
	
	@EventTarget
	public void onPre(EventPreMotionUpdates event) {
		if(!mc.player.isSneaking() || !mc.gameSettings.keyBindSneak.pressed) {
		 mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
		}
	}
	
	@EventTarget
	public void onPost(EventPostMotionUpdates event) {
		if(!mc.player.isSneaking() || !mc.gameSettings.keyBindSneak.pressed) {
		 mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
		}
	}
	
    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
        super.onDisable();
    }
}