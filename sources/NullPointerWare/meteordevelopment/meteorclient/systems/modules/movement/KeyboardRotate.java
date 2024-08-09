package meteordevelopment.meteorclient.systems.modules.movement;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public final class KeyboardRotate extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public KeyboardRotate() {
        super(Categories.Movement, "KeyoardRotate", "Rotates with the keyboard. No mouse needed!");
    }

    private final Setting<Integer> range = sgGeneral.add(new IntSetting.Builder()
        .name("height")
        .description("The Height you should be clipped")
        .defaultValue(10)
        .sliderMin(-200)
        .sliderMax(200)
        .build()
    );

    private final Setting<Boolean> randomize= sgGeneral.add(new BoolSetting.Builder()
        .name("randomize")
        .description("Randomize the movement to bipass acs.")
        .defaultValue(false)
        .build()
    );

    public final Setting<Boolean> noAir = sgGeneral.add(new BoolSetting.Builder()
        .name("no-in-air")
        .description("No clip if player is in air and nofall is on to prevent kicks...")
        .defaultValue(true)
        .build()
    );

    private long lastTime;

    @Override
    public void onActivate() {
        super.onActivate();
    }

    @EventHandler
    private void onTick(final Render3DEvent e) {
        float yaw = 0f;
        float pitch = 0f;

        //mc.keyboard.setRepeatEvents(true);

        float amount = (System.currentTimeMillis() - lastTime) / 10f;
        lastTime = System.currentTimeMillis();

        if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT))
            yaw -= amount;
        if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_RIGHT))
            yaw += amount;
        if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_UP))
            pitch -= amount;
        if (InputUtil.isKeyPressed(mc.getWindow().getHandle(), GLFW.GLFW_KEY_DOWN))
            pitch += amount;

        if (randomize.get()) {
            if (yaw == 0f && pitch != 0f) {
                yaw += -0.1 + Math.random() / 5f;
            } else {
                yaw *= 0.75f + Math.random() / 2f;
            }

            if (pitch == 0f && yaw != 0f) {
                pitch += -0.1 + Math.random() / 5f;
            } else {
                pitch *= 0.75f + Math.random() / 2f;
            }
        }


        mc.player.setYaw(mc.player.getYaw() + yaw);

        if (randomize.get()) {
            mc.player.setPitch(MathHelper.clamp(mc.player.getPitch() + pitch, -90f, 90f));
        } else {
            mc.player.setPitch(mc.player.getPitch() + pitch);
        }
    }
}
