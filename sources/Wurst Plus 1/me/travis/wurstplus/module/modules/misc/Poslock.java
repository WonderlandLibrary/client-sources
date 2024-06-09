package me.travis.wurstplus.module.modules.misc;

import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.setting.Setting;
import me.travis.wurstplus.setting.Settings;
import me.travis.wurstplus.command.Command;

import net.minecraft.util.math.MathHelper;

@Module.Info(name = "PosLock", category = Module.Category.MISC)
public class Poslock extends Module {

    private Setting<Direction> direction = register(Settings.e("Direction", Direction.North));

    @Override
    public void onUpdate() {
        if (this.isDisabled()) {
            return;
        }
        if (direction.getValue() == Direction.North) {
            mc.player.rotationYaw = MathHelper.clamp(180, -180, 180);
        }
        else if (direction.getValue() == Direction.South) {
            mc.player.rotationYaw = MathHelper.clamp(0, -180, 180);
        }
        else if (direction.getValue() == Direction.West) {
            mc.player.rotationYaw = MathHelper.clamp(90, -180, 180);
        }
        else if (direction.getValue() == Direction.East) {
            mc.player.rotationYaw = -90;
        }
        else {
            Command.sendChatMessage("what");
        }
    }

    public static enum Direction {
        North, South, West, East
    }

}
