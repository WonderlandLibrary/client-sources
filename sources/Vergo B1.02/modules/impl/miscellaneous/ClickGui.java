package xyz.vergoclient.modules.impl.miscellaneous;

import xyz.vergoclient.modules.Module;
import xyz.vergoclient.ui.click.GuiClickGui;

import java.util.Arrays;

public class ClickGui extends Module {

	public ClickGui() {
		super("Clickgui", Category.MISCELLANEOUS);
		
	}
	//public ModeSetting style = new ModeSetting("Style", "Vergo", "Flux", "Vergo");
	
	@Override
	public boolean isEnabled() {
		return mc.currentScreen instanceof GuiClickGui;
	}
	
	@Override
	public boolean isDisabled() {
		return !(mc.currentScreen instanceof GuiClickGui);
	}

	@Override
	public void loadSettings() {
		//style.modes.addAll(Arrays.asList("Vergo", "New"));

		//addSettings(style);
	}

	@Override
	public void onEnable() {
		//if(style.is("Vergo")) {
			if(mc.currentScreen instanceof GuiClickGui)
				mc.displayGuiScreen(null);
			else {
				mc.displayGuiScreen(GuiClickGui.getClickGui());
			}
		//} else if (style.is("New")) {
			//if(mc.currentScreen instanceof ClickGUI) {
			//	mc.displayGuiScreen(null);
			//} else {
			//	mc.displayGuiScreen(ClickGUI.getClickGui());
			//}
		//}
		silentToggle();
	}
	
}
