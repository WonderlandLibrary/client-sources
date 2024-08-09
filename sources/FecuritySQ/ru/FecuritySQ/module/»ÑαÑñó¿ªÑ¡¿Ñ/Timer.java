package ru.FecuritySQ.module.передвижение;

import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionNumric;

public class Timer extends Module {

    public static float violation = 0.0f;
    private static double prevPosX;
    private static double prevPosY;
    private static double prevPosZ;

    private static float yaw;
    private static float pitch;

    public static final OptionNumric timerSpeed = new OptionNumric("Скорость", 1.8f, 1.1f, 5.0f, 0.1f);
    public static final OptionBoolean smart = new OptionBoolean("Умный", true);
    public static final OptionNumric decreaseRate = new OptionNumric("Скорость убывания", 1.0f, 0.5f, 3.0f, 0.1f);
    public static final OptionBoolean indicator = new OptionBoolean("Индикатор", true);
    public static final OptionNumric addOnTheMove = new OptionNumric("Добавлять в движении", 0.2f, 0.2f, 1.0f, 0.05f);
    public Timer() {
        super(Category.Передвижение, GLFW.GLFW_KEY_K);
    addOption(timerSpeed);
    addOption(smart);
    addOption(indicator);
    addOption(addOnTheMove);
    addOption(decreaseRate);
    }
    @Override
    public void event(Event e) {
        if (e instanceof EventUpdate && isEnabled()) {
            mc.timer.timerSpeed = timerSpeed.get();
            if (100.0f - Timer.getViolation() < 14)
                toggle();
            if (!smart.get() || mc.timer.timerSpeed <= 1.0f)
                return;

            if (violation < 90f / timerSpeed.get()) {
                violation += decreaseRate.get();
                violation = MathHelper.clamp(violation, 0.0f, 100f / timerSpeed.get());
            } else
                toggle();

        }
    }
    public static float getViolation() {
        return violation;
    }

    public void updateTimer(float rotationYaw, float rotationPitch, double posX, double posY, double posZ) { // -> EntityPlayerSP updateWalkingPlayer()
        violation = notMoving() ? (float)(violation - (decreaseRate.get() + 0.4)) : violation - (addOnTheMove.get() / 10.0f);
        violation = (float) MathHelper.clamp(violation, 0.0, Math.floor(100f / mc.timer.timerSpeed));
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        yaw = rotationYaw;
        pitch = rotationPitch;
    }

    public static boolean notMoving() {
        return prevPosX == mc.player.getPosX()
                && prevPosY == mc.player.getPosY()
                && prevPosZ == mc.player.getPosZ()
                && yaw == mc.player.rotationYaw
                && pitch == mc.player.rotationPitch;
    }
}
