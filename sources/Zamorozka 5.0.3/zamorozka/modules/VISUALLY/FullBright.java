package zamorozka.modules.VISUALLY;

import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class FullBright extends Module {

	public FullBright() {
		super("FullBright", 0, Category.VISUALLY);
	}

	public void onUpdate() {
		if (this.getState()) {
			mc.gameSettings.gammaSetting = 10f;
		} else {
			mc.gameSettings.gammaSetting = 1f;
		}
	}

}
