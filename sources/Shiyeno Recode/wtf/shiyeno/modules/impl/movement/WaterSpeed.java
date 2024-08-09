package wtf.shiyeno.modules.impl.movement;

import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.*;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.util.movement.MoveUtil;

@FunctionAnnotation(name = "WaterSpeed", type = Type.Movement)
public class WaterSpeed extends Function {


    public SliderSetting speed = new SliderSetting("Скорость", 0.41f, 0.1f, 0.5f, 0.01f);
    public SliderSetting motionY = new SliderSetting("Скорость по Y", 0.0f, 0.0f, 0.1f, 0.01f);

    public WaterSpeed() {
        addSettings(speed, motionY);
    }

    private float currentValue;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTravel move) {
            if (mc.player.collidedVertically || mc.player.collidedHorizontally) {
                return;
            }
            if (mc.player.isSwimming()) {
                float speed = this.speed.getValue().floatValue() / 10F;
                if (mc.gameSettings.keyBindJump.pressed) {
                    mc.player.motion.y += motionY.getValue().floatValue();
                }
                if (mc.gameSettings.keyBindSneak.pressed) {
                    mc.player.motion.y -= motionY.getValue().floatValue();
                }

                MoveUtil.setMotion(MoveUtil.getMotion());
                move.speed += speed;
            }
        }
    }

    public float calculateNewValue(float value, float increment) {
        return value * Math.min((currentValue += increment) / 100.0f, 1.0f);
    }

}
