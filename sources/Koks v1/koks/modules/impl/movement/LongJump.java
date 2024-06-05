package koks.modules.impl.movement;

import koks.event.Event;
import koks.event.impl.EventUpdate;
import koks.modules.Module;
import koks.utilities.MovementUtil;
import koks.utilities.value.values.ModeValue;
import koks.utilities.value.values.NumberValue;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 18:10
 */
public class LongJump extends Module {

    public ModeValue<String> mode = new ModeValue<>("LongJump Mode", "RedeSky", new String[]{"RedeSky"}, this);
    public NumberValue<Double> jumpFactor = new NumberValue<>("Jump Factor", 0.42D, 0.60D, 0.30D, this);
    public NumberValue<Double> timerSpeed = new NumberValue<>("Timer Speed", 1.00D, 2.00D, 0.50D, this);
    public NumberValue<Double> speed = new NumberValue<>("Speed", 0.50D, 1.00D, 0.40D, this);
    public NumberValue<Double> decreaseFalling = new NumberValue<>("Decrease FallDistance", 0.04D, 0.10D, 0.00D, this);

    public LongJump() {
        super("LongJump", "you jump a very long distance", Category.MOVEMENT);
        addValue(jumpFactor);
        addValue(timerSpeed);
        addValue(speed);
        addValue(decreaseFalling);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventUpdate) {
            setModuleInfo(mode.getSelectedMode());

            switch (mode.getSelectedMode()) {
                case "RedeSky":
                    MovementUtil movementUtil = new MovementUtil();
                    if (mc.thePlayer.onGround) {
                        mc.thePlayer.motionY = jumpFactor.getDefaultValue();
                    }
                    mc.thePlayer.motionY += decreaseFalling.getDefaultValue();
                    movementUtil.setSpeed(speed.getDefaultValue());
                    mc.timer.timerSpeed = timerSpeed.getDefaultValue();
                    break;
            }
        }
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1.0;
    }

}