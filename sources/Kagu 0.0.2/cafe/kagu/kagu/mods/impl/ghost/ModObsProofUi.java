/**
 * 
 */
package cafe.kagu.kagu.mods.impl.ghost;

import java.awt.Color;
import java.awt.Graphics2D;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventRenderObs;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.LabelSetting;
import cafe.kagu.kagu.ui.ghost.GhostUi;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.OSUtil;

/**
 * @author DistastefulBannock
 *
 */
public class ModObsProofUi extends Module {
	
	public ModObsProofUi() {
		super("ObsProofUI", Category.GHOST);
		setSettings(windowsOnlyLabel, offsetX, offsetY, offsetCalibrationRect);
	}
	
	private LabelSetting windowsOnlyLabel = new LabelSetting("Windows only feature").setDependency(() -> !OSUtil.isWindows());
	private IntegerSetting offsetX = new IntegerSetting("Offset X", 8, -20, 20, 1).setDependency(OSUtil::isWindows);
	private IntegerSetting offsetY = new IntegerSetting("Offset Y", 31, -40, 40, 1).setDependency(OSUtil::isWindows);
	private BooleanSetting offsetCalibrationRect = new BooleanSetting("Offset Calibration Rect", false).setDependency(OSUtil::isWindows);
	
	@Override
	public void onEnable() {
		if (!OSUtil.isWindows()) {
			ChatUtils.addChatMessage("This is a windows only feature, sorry " + System.getProperty("os.name").toLowerCase() + " user");
			toggle();
			return;
		}
		ChatUtils.addChatMessage("Use game capture to record the game, other means of recording (like display capture) will pick up the ui");
	}
	
	private static final Color COLOR = new Color(214, 140, 255, 100);
	@EventHandler
	private Handler<EventRenderObs> onRenderObs = e -> {
		if (e.isPost() || offsetCalibrationRect.isDisabled())
			return;
		Graphics2D graphics2d = e.getGraphics();
		GhostUi ghostUi = e.getGhostUi();
		graphics2d.setColor(COLOR);
		graphics2d.fillRect(-100, -100, ghostUi.getWidth() + 200, ghostUi.getHeight() + 200);
	};
	
	/**
	 * @return the offsetX
	 */
	public IntegerSetting getOffsetX() {
		return offsetX;
	}
	
	/**
	 * @return the offsetY
	 */
	public IntegerSetting getOffsetY() {
		return offsetY;
	}
	
}
