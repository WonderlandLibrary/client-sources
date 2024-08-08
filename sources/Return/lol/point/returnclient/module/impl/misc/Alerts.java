package lol.point.returnclient.module.impl.misc;

import lol.point.returnclient.module.Category;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.module.ModuleInfo;
import lol.point.returnclient.settings.impl.BooleanSetting;

@ModuleInfo(
        name = "Alerts",
        description = "alerts you about other player activities",
        category = Category.MISC
)
public class Alerts extends Module {

    public final BooleanSetting swords = new BooleanSetting("Swords", true);
    public final BooleanSetting armor = new BooleanSetting("Armor", true);
    public final BooleanSetting bows = new BooleanSetting("Bows", true);
    public final BooleanSetting enderPearl = new BooleanSetting("Ender pearls", true);
    public final BooleanSetting speedEffects = new BooleanSetting("Speed effects", true);
    public final BooleanSetting jumpEffects = new BooleanSetting("Jump effects", true);
    public final BooleanSetting invisibilityEffects = new BooleanSetting("Invisibility effects", true);

    public Alerts() {
        addSettings(swords, armor, bows, enderPearl, speedEffects, jumpEffects, invisibilityEffects);
    }

}
