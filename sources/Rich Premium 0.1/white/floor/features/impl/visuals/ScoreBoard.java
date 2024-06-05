package white.floor.features.impl.visuals;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;

public class ScoreBoard extends Feature {
    public ScoreBoard() {
        super("Scoreboard", "blbobl.", 0, Category.VISUALS);
        Main.settingsManager.rSetting(new Setting("Position Y", this, 100, 1, 200, true));
        Main.settingsManager.rSetting(new Setting("Remove", this, true));
        Main.settingsManager.rSetting(new Setting("Remove Points", this, true));
    }
}
