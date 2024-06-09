/**
 * 
 */
package cafe.kagu.kagu.mods.impl.visual;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventEntityRender;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import net.minecraft.client.renderer.GlStateManager;

/**
 * @author DistastefulBannock
 *
 */
public class ModWideMen extends Module {
	
	public ModWideMen() {
		super("WideMen", Category.VISUAL);
		setSettings(label, wideness);
	}
	
	private LabelSetting label = new LabelSetting("Wide Men Walking");
	private DoubleSetting wideness = new DoubleSetting("Wideness", 2, 1.1, 4, 0.1);
	
	@EventHandler
	private Handler<EventEntityRender> onEntityRender = e -> {
		if (e.getEntity() != mc.thePlayer)
			return;
		if (e.isPre()) {
			GlStateManager.pushMatrix();
			float yaw = mc.getRenderViewEntity().rotationYaw;
			GlStateManager.rotate(yaw, 0, -1, 0);
			GlStateManager.scale(wideness.getValue(), 1, 1);
			GlStateManager.rotate(yaw, 0, 1, 0);
		}else {
			GlStateManager.popMatrix();
		}
	};
	
}
