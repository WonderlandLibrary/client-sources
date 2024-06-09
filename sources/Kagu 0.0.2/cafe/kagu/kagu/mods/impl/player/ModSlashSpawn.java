/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import net.minecraft.network.play.client.C01PacketChatMessage;

/**
 * @author DistastefulBannock
 *
 */
public class ModSlashSpawn extends Module {
	
	public ModSlashSpawn() {
		super("/Spawn", Category.PLAYER);
		setSettings(labelSetting);
	}
	
	private LabelSetting labelSetting = new LabelSetting("Types /spawn on enable");
	
	@Override
	public void onEnable() {
		mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C01PacketChatMessage("/spawn"));
		toggle();
	}
	
}
