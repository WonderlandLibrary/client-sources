// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.ghost;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.util.MathHelper;

import java.util.Random;

@ModuleInfo(
        name = "Reach",
        category = Category.GHOST
)
public class Reach extends Module {

    public final NumberValue<Double> minReach = new NumberValue<>("Min Reach", 3.1D, 3D, 4D, 0.01D);
    public final NumberValue<Double> maxReach = new NumberValue<>("Max Reach", 3.1D, 3D, 4D, 0.01D);
    public final NumberValue<Double> chance = new NumberValue<>("Chance", 1D, 0D, 1D, 0.01D);

    public double combatReach = 3.0;

    public Reach() {
        super();
        addSettings(minReach, maxReach, chance);
    }

    @Listen
    public void onAttack(AttackEvent event) {
        double rnd = MathHelper.getRandomDoubleInRange(new Random(), 0, 1);
        if (rnd <= chance.getValue()) {
           combatReach =  MathHelper.getRandomDoubleInRange(new Random(), minReach.getValue(), maxReach.getValue());
        } else {
            combatReach = 3.0D;
        }
    }

}
