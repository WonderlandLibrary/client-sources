package ru.FecuritySQ.module.передвижение;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.utils.MoveUtil;

import java.util.Random;

public class WaterSpeed extends Module {
    public WaterSpeed() {
        super(Category.Передвижение, GLFW.GLFW_KEY_K);
        setEnabled(true);
    }

    @Override
    public void event(Event e) {
        if (e instanceof EventUpdate && isEnabled()) {
            if (mc.player.isInWater() && MoveUtil.isMoving()) {
                if (mc.gameSettings.keyBindJump.isKeyDown() && mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.getMotion().y = 0;
                }

                mc.player.setSwimming(true);

                float moveSpeed = 2.0f + new Random().nextFloat() * 1.0f;
                moveSpeed /= 100.0f;

                double moveX = mc.player.getForward().x * moveSpeed;
                double moveZ = mc.player.getForward().z * moveSpeed;

                mc.player.addVelocity(moveX, 0, moveZ);
            }
        }
    }
}
