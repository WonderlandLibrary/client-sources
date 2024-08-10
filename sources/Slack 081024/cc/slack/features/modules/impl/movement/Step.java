// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.movement.steps.IStep;
import cc.slack.features.modules.impl.movement.steps.impl.*;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "Step",
        category = Category.MOVEMENT
)
public class Step extends Module {

    private final ModeValue<IStep> mode = new ModeValue<>(new IStep[]{new VanillaStep(), new NCPStep(), new VerusStep(), new VulcanStep(), new TestStep()});
    private final NumberValue<Float> timerSpeed = new NumberValue<>("Timer", 1f, 0f, 2f, 0.05f);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});


    public Step() {
        super();
        addSettings(mode, timerSpeed, displayMode);
    }


    @Override
    public void onEnable() {
        mode.getValue().onEnable();
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        mode.getValue().onDisable();
    }

    @Listen
    public void onPacket(PacketEvent event) {
        mode.getValue().onPacket(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {
        mode.getValue().onMotion(event);
    }

    @Listen
    public void onMove(MoveEvent event) {
        mode.getValue().onMove(event);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.onGround) {
            mc.timer.timerSpeed = timerSpeed.getValue();
            mode.getValue().onUpdate(event);
        } else {
            mc.thePlayer.stepHeight = 0.5f;
            mc.timer.timerSpeed = 1f;
        }
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return mode.getValue().toString();
        }
        return null;
    }
}
