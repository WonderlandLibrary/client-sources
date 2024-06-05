package white.floor.features.impl.display;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.features.Category;
import white.floor.features.Feature;

import java.util.ArrayList;

public class Notifications extends Feature {
    public Notifications() {
        super("Notifications", "notify", 0, Category.DISPLAY);
        ArrayList<String> notifs = new ArrayList<String>();
        notifs.add("Compact");
        notifs.add("Intellij");
        Main.instance.settingsManager.rSetting(new Setting("Notification Mode", this, "Compact", notifs));
    }
}
