package zamorozka.modules.VISUALLY;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;

public class NoScoreBoard extends Module{

	@Override
	public void setup() {
		Zamorozka.settingsManager.rSetting(new Setting("Print", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("Remove", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("NoPoints", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NameBoardSpoof", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("NoScoreBoardRect", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ScorPositionY", this, 0, -180, 225, false));
		Zamorozka.settingsManager.rSetting(new Setting("ScorPositionX", this, 0, 0, 900, false));
		
	}
	
	public NoScoreBoard() {
		super("ScoreBoard", 0, Category.VISUALLY);
	}
}