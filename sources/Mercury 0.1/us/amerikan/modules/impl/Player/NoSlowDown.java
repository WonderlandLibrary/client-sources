/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Player;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import us.amerikan.amerikan;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;

public class NoSlowDown
extends Module {
    public NoSlowDown() {
        super("NoSlowDown", "NoSlowDown", 0, Category.PLAYER);
        ArrayList<String> options = new ArrayList<String>();
        options.add("AAC Fast");
        options.add("AAC Bypass");
        options.add("Cubecraft");
        amerikan.setmgr.rSetting(new Setting("Bypass-Mode", this, "AAC Fast", options));
    }
}

