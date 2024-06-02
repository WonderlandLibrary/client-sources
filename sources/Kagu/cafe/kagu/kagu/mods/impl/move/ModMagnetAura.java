/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.combat.ModKillAura;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author DistastefulBannock
 *
 */
public class ModMagnetAura extends Module {
	
	public ModMagnetAura() {
		super("MagnetAura", Category.MOVEMENT);
		setSettings(labelSetting);
	}
	
	private LabelSetting labelSetting = new LabelSetting("Sets your player position to your ka target position");
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		ModKillAura modKillAura = Kagu.getModuleManager().getModule(ModKillAura.class);
		if (modKillAura.isEnabled() && modKillAura.getTarget() != null) {
			EntityLivingBase target = modKillAura.getTarget();
			mc.thePlayer.setPosition(target.posX + (target.posX - target.lastTickPosX), target.posY + (target.posY - target.lastTickPosY), target.posZ + (target.posZ - target.lastTickPosZ));
		}
	};
	
}
