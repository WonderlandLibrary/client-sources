package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.HitSlowDownEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "KeepSprint",
        category = Category.GHOST
)
public class KeepSprint extends Module {

    private final NumberValue<Double> defensiveValue = new NumberValue<>("Defensive Motion", 0.6D, 0D, 1D, 0.05D);
    private final NumberValue<Double> offensiveValue = new NumberValue<>("Offensive Motion", 1D, 0D, 1D, 0.05D);
    private final NumberValue<Double> chanceValue = new NumberValue<>("Chance", 100D, 0D, 100D, 1D);


    public KeepSprint () {
        addSettings(defensiveValue, offensiveValue, chanceValue);
    }

    @Listen
    public void onKeepSprint(HitSlowDownEvent e) {
        if (chanceValue.getValue() != 100.0D) {
            if (Math.random() >= chanceValue.getValue() / 100.0D) {
                return;
            }
        }

        if (mc.thePlayer.hurtTime > 3) {
            e.setSlowDown(defensiveValue.getValue());
            e.setSprint(false);
        } else {
            e.setSlowDown(offensiveValue.getValue());
            e.setSprint(true);
        }
    }

}
