// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.BlinkUtil;
import io.github.nevalackin.radbus.Listen;


@ModuleInfo(
        name = "Blink",
        category = Category.PLAYER
)
public class Blink extends Module {


    private final BooleanValue outbound = new BooleanValue("Outbound", true);
    private final BooleanValue inbound = new BooleanValue("Inbound", false);
    private final NumberValue<Integer> delayValue = new NumberValue<>("Delay (ms)", 50, 0, 200, 1);

    private int delay;

    public Blink() {
        super();
        addSettings(outbound, inbound, delayValue);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        if (++delay > delayValue.getValue() / 50) {
            BlinkUtil.releasePackets();
            delay = 0;
        }
    }

    @Override
    public void onEnable() {
        BlinkUtil.enable(inbound.getValue(), outbound.getValue());
    }

    @Override
    public void onDisable() {
        BlinkUtil.disable();
    }


}
