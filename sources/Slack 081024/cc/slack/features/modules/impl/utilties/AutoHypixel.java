package cc.slack.features.modules.impl.utilties;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
        name = "AutoHypixel",
        category = Category.UTILITIES
)
public class AutoHypixel extends Module {

    // GG
    private final BooleanValue autoggValue = new BooleanValue("AutoGG", false);
    private final NumberValue<Long> autoggDelayValue = new NumberValue<>("AutoGG Delay", 1000L, 250L, 0L, 5000L);

    // AutoPlay
    private final BooleanValue autoplayValue = new BooleanValue("AutoPlay", false);
    private final ModeValue<String> autoplayMode = new ModeValue<>("Game", new String[]{"SkyWars", "BedWars"});
    private final ModeValue<String> skywarsMode = new ModeValue<>("SkyWars", new String[]{"Solo", "Doubles"});
    private final ModeValue<String> skywarsTypeMode = new ModeValue<>("SkyWars Type", new String[]{"Normal", "Insane"});
    private final ModeValue<String> bedwarsMode = new ModeValue<>("BedWars", new String[]{"Solo", "Doubles", "3v3v3v3", "4v4v4v4"});
    private final NumberValue<Long> autoplayDelayValue = new NumberValue<>("AutoPlay Delay", 1000L, 250L, 0L, 5000L);

    // Useless stuff
    private final BooleanValue detectPlayerBan = new BooleanValue("Auto Quit on Ban", false);
    private final BooleanValue detectPartyPlayerBan = new BooleanValue("Auto Leave Party on Ban", false);



    public AutoHypixel() {
        addSettings();
    }
}
