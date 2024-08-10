// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.MovementUtil;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "Bobbing",
        category = Category.RENDER
)
public class Bobbing extends Module {

    public BooleanValue nobobvalue = new BooleanValue("NoBob", true);
    public BooleanValue custombobbing = new BooleanValue("Custom Bobbing", false);
    public NumberValue<Float> bobbingvalue = new NumberValue<>("Bobbing Amount", 0.03F, -0.5F,0.5f, 0.01F);

    public Bobbing() {
        addSettings(nobobvalue, custombobbing, bobbingvalue);
    }

    @Listen
    @SuppressWarnings("unused")
    public void onMotion (MotionEvent event) {
        if (custombobbing.getValue() && mc.thePlayer.onGround && !nobobvalue.getValue() && MovementUtil.isMoving()) {
            mc.thePlayer.cameraYaw = bobbingvalue.getValue();
        }

        if (nobobvalue.getValue() && MovementUtil.isMoving()) {
            mc.thePlayer.cameraYaw = 0.0f;
        }
    }

}
