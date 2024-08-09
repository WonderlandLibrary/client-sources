package src.Wiksi.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.player.MoveUtils;
import net.minecraft.client.entity.player.ClientPlayerEntity;

@FunctionRegister(name = "DragonFly", type = Category.Movement)
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