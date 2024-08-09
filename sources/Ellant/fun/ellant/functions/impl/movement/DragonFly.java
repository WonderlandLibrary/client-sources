package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.MoveUtils;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@FunctionRegister(name = "DragonFly", type = Category.MOVEMENT, desc = "Ускоряет тебя в полёте")
public class DragonFly extends Function {
    private final BooleanSetting fly = new BooleanSetting("Включить", true);
    private final SliderSetting speed = new SliderSetting("Скорость", 1.15f, 0f, 1.5f, 0.05f);
    public DragonFly() {
        addSettings(fly,speed);
    }
    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (e instanceof EventUpdate) {
            dragonFly();
        }
    }
    private void dragonFly() {
        if (fly.get() && mc.player.abilities.isFlying) {
            mc.player.motion.y = 0.0;
            ClientPlayerEntity player = mc.player;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                player.motion.y += 1.150;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                player.motion.y -= 1.150;
            }
            if (MoveUtils.isMoving()) {
                MoveUtils.setMotion(speed.get() - 0.2f);
            }
        }
    }

}