/**
* 
*/
package cafe.kagu.kagu.mods.impl.visual;

import org.json.JSONObject;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventBus;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventSettingUpdate;
import cafe.kagu.kagu.managers.FileManager;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.ui.clickgui.GuiCsgoClickgui;
import cafe.kagu.kagu.ui.clickgui.GuiDropdownClickgui;
import cafe.kagu.kagu.utils.ClickGuiUtils;

/**
 * @author lavaflowglow
 *
 */
public class ModClickGui extends Module {

	public ModClickGui() {
		super("ClickGui", Category.VISUAL);

		// Load clickgui settings
		if (FileManager.CLICKGUI_OPTIONS.exists()) {
			try {
				JSONObject json = new JSONObject(FileManager.readStringFromFile(FileManager.CLICKGUI_OPTIONS));
				mode.setMode(json.getString("mode"));
				bgImage.setMode(json.getString("image"));
				bgImageAnimation.setMode(json.getString("bgAnimation"));
				bgImageScale.setValue(json.getDouble("bgScale"));
				if (json.getBoolean("bgFlip"))
					bgImageFlip.enable();
			} catch (Exception e) {

			}
		}

		// Save clickgui settings
		GuiDropdownClickgui.getInstance().saveClickguiOptions();

		// Register settings
		setSettings(mode, bgImage, bgImageAnimation, bgImageScale, bgImageFlip, resetClickGuiTabs);

		// Hook event handlers
		Kagu.getEventBus().subscribe(new DisabledFix());
	}

	private ModeSetting mode = new ModeSetting("Mode", "Dropdown", "CS:GO", "Dropdown");
	private ModeSetting bgImage = new ModeSetting("BG Image", "Fleur 1", "Fleur 1", "Fleur 2", "Distasteful",
			"Cheddar 1", "Cheddar 2", "Cheddar 3", "Sylveon 1", "Vaporeon 1", "Wolf O'Donnell", "Protogen 1",
			"Protogen 2", "Presto 1", "Presto 2", "Presto 3", "Neko Maid 1", "Astolfo 1", "Astolfo 2", "Astolfo 3", "Felix 1",
			"Felix 2", "Felix 3", "Miku 1", "Miku 2", "Peter Griffin 1", "Peter Griffin 2", "Yoshi 1", "Yoshi 2",
			"Crazy Frog 1", "Jeremy Clarkson", "Niko 1").setDependency(() -> mode.is("Dropdown"));
	private ModeSetting bgImageAnimation = new ModeSetting("BG Image Animation", "From Bottom", "None", "From Bottom",
			"From Side", "From Corner").setDependency(() -> mode.is("Dropdown"));
	private DoubleSetting bgImageScale = new DoubleSetting("BG Image Scale", 1, 0.1, 4, 0.1)
			.setDependency(() -> mode.is("Dropdown"));
	private BooleanSetting bgImageFlip = new BooleanSetting("Flip BG Image", false)
			.setDependency(() -> mode.is("Dropdown"));
	private BooleanSetting resetClickGuiTabs = new BooleanSetting("Reset Tabs", false)
			.setDependency(() -> mode.is("Dropdown"));

	/**
	 * @author DistastefulBannock
	 *
	 */
	private class DisabledFix {
		@EventHandler
		private Handler<EventSettingUpdate> onSettingUpdate = e -> {
			if (e.getSetting() == bgImage) {
				GuiDropdownClickgui.getInstance().resetBackgroundImage();
				GuiDropdownClickgui.getInstance().saveClickguiOptions();
			} else if (e.getSetting() == resetClickGuiTabs) {
				if (resetClickGuiTabs.isEnabled()) {
					GuiDropdownClickgui.getInstance().resetTabs();
					resetClickGuiTabs.toggle();
				}
			} else if (e.getSetting() == bgImageAnimation
					|| e.getSetting() == bgImageFlip) {
				GuiDropdownClickgui.getInstance().saveClickguiOptions();
			} else if (e.getSetting() != mode)
				return;
			switch (mode.getMode()) {
				case "CS:GO": {
					mc.displayGuiScreen(GuiCsgoClickgui.getInstance());
				}
					break;
				case "Dropdown": {
					mc.displayGuiScreen(GuiDropdownClickgui.getInstance());
				}
					break;
			}
			GuiDropdownClickgui.getInstance().saveClickguiOptions();
		};
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean isDisabled() {
		return true;
	}

	@Override
	public void toggle() {
		if (mc.getCurrentScreen() == null) {
			switch (mode.getMode()) {
				case "CS:GO": {
					mc.displayGuiScreen(GuiCsgoClickgui.getInstance());
				}
					break;
				case "Dropdown": {
					mc.displayGuiScreen(GuiDropdownClickgui.getInstance());
				}
					break;
			}
		} else if (ClickGuiUtils.isInClickGui()) {
			mc.displayGuiScreen(null);
		}
	}

	@Override
	public void enable() {

	}

	@Override
	public void disable() {

	}

	/**
	 * @return the mode
	 */
	public ModeSetting getMode() {
		return mode;
	}

	/**
	 * @return the bgImage
	 */
	public ModeSetting getBgImage() {
		return bgImage;
	}

	/**
	 * @return the bgImageScale
	 */
	public DoubleSetting getBgImageScale() {
		return bgImageScale;
	}

	/**
	 * @return the bgImageAnimation
	 */
	public ModeSetting getBgImageAnimation() {
		return bgImageAnimation;
	}

	/**
	 * @return the bgImageFlip
	 */
	public BooleanSetting getBgImageFlip() {
		return bgImageFlip;
	}

}
