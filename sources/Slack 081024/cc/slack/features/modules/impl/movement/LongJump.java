package cc.slack.features.modules.impl.movement;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.CollideEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.features.modules.impl.movement.longjumps.ILongJump;
import cc.slack.features.modules.impl.movement.longjumps.impl.verus.VerusBowLJ;
import cc.slack.features.modules.impl.movement.longjumps.impl.vulcan.VulcanLJ;
import io.github.nevalackin.radbus.Listen;

@ModuleInfo(
        name = "LongJump",
        category = Category.MOVEMENT
)
public class LongJump extends Module {

    public final ModeValue<ILongJump> mode = new ModeValue<>(new ILongJump[]{

            // Vanilla


            // Verus
            new VerusBowLJ(),

            // Vulcan
            new VulcanLJ()
            // Others

    });

    public final NumberValue<Double> speedValue = new NumberValue<>("Speed", 5.0D,1.0D,9.0D,0.5D);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    public LongJump() {
        super();
        addSettings(mode, speedValue, displayMode);
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
    public void onMove(MoveEvent event) {
        mode.getValue().onMove(event);
    }

    @Listen
    public void onUpdate(UpdateEvent event) {
        mode.getValue().onUpdate(event);
    }

    @Listen
    public void onPacket(PacketEvent event) {
        mode.getValue().onPacket(event);
    }

    @Listen
    public void onCollide(CollideEvent event) {
        mode.getValue().onCollide(event);
    }

    @Listen
    public void onMotion(MotionEvent event) {

        mode.getValue().onMotion(event);
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
