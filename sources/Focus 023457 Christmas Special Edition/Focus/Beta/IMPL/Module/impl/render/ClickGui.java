
package Focus.Beta.IMPL.Module.impl.render;

import Focus.Beta.API.GUI.betaui.BetaClickUI;
import Focus.Beta.API.GUI.clickgui.ClickUi;
import Focus.Beta.API.GUI.vapelite.VapeClickGUI;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.set.Mode;

public class ClickGui extends Module{
	
	public static final Mode<Enum> mode = new Mode("Modes", "Modes", Modes.values(), Modes.Focus);

	public ClickGui() {
		super("ClickUI", new String[0], Type.RENDER, "No");
		this.addValues(mode);
	}
	
	@Override
	public void onEnable() {
		switch(mode.getModeAsString()) {
		case "Focus":
			this.mc.displayGuiScreen(new ClickUi());
			this.setEnabled(false);
			break;
			case "Beta":
				this.mc.displayGuiScreen(new BetaClickUI());
				this.setEnabled(false);
				break;
		}
	}

	enum Modes{
		Focus
	}
}
