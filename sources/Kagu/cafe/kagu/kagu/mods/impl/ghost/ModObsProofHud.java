/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRenderObs;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;

/**
 * @author DistastefulBannock
 *
 */
public class ModObsProofHud extends Module {
	
	public ModObsProofHud() {
		super("ObsProofHud", Category.GHOST);
	}
	
	@Override
	public void onEnable() {
		Kagu.getModuleManager().getModule(ModObsProofUi.class).enable();
		if (Kagu.getModuleManager().getModule(ModObsProofUi.class).isDisabled()) {
			toggle();
			return;
		}
	}
	
	private final Color KINDA_BLACK = new Color(0, 0, 0, 0x80);
	
	@EventHandler
	private Handler<EventRenderObs> onRenderObs = e -> {
		if (e.isPost())
			return;
		Graphics2D graphics2d = e.getGraphics();
		Rectangle bounds = graphics2d.getDeviceConfiguration().getBounds();
		
		// Watermark
		graphics2d.setStroke(new BasicStroke(2));
		graphics2d.setFont(FontUtils.AWT_STRATUM2_MEDIUM_26);
		graphics2d.setColor(KINDA_BLACK);
		graphics2d.drawString(Kagu.getName() + " v" + Kagu.getVersion(), 3, 23);
		graphics2d.setColor(Color.WHITE);
		graphics2d.drawString(Kagu.getName() + " v" + Kagu.getVersion(), 1, 21);
		
		// Arraylist
		String separator = " - ";
		List<Module> mods = new ArrayList<Module>(Kagu.getModuleManager().getModules());
		Font moduleFr = FontUtils.AWT_SAN_FRANCISCO_REGULAR_20;
		FontRenderContext moduleFrRenderContext = new FontRenderContext(new AffineTransform(), true, false);
		FontRenderContext infoFrRenderContext = new FontRenderContext(new AffineTransform(), true, false);
		Font infoFr = FontUtils.AWT_SAN_FRANCISCO_THIN_20;
		double rightPad = 2;
		double topPad = 0.5;
		double index = 0;
		
		// Only show modules that are far enough in the arraylist animation to display
		mods = mods.stream().filter(mod -> mod.getArraylistAnimation() > 0.01).collect(Collectors.toList());
		mods.sort(Comparator.comparingDouble(module -> moduleFr.getStringBounds(module.getName(), moduleFrRenderContext).getWidth() + infoFr.getStringBounds(module.getInfoAsString(separator), infoFrRenderContext).getWidth()));
		Collections.reverse(mods);
		
		// Draw arraylist
		for (Module mod : mods) {
			
			// Info length shit
			String info = mod.getInfoAsString(separator);
			double infoLength = infoFr.getStringBounds(info, infoFrRenderContext).getWidth();
			
		}
		
	};
	
}
