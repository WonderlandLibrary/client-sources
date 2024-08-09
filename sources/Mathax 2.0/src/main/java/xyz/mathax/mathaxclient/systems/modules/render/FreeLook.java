package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.Input;
import net.minecraft.client.option.Perspective;
import org.lwjgl.glfw.GLFW;
import xyz.mathax.mathaxclient.settings.*;

public class FreeLook extends Module {
    public float cameraYaw;
    public float cameraPitch;

    private Perspective prePerspective;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup arrowsSettings = settings.createGroup("Arrows");

    // General

    public final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Which entity to rotate.")
            .defaultValue(Mode.Player)
            .build()
    );

    public final Setting<Boolean> togglePerpectiveSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Toggle perspective")
            .description("Changes your perspective on toggle.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Double> sensitivitySetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Camera sensitivity")
            .description("How fast the camera moves in camera mode.")
            .defaultValue(8)
            .min(0)
            .sliderRange(0, 10)
            .build()
    );

    // Arrows

    public final Setting<Boolean> arrowsSetting = arrowsSettings.add(new BoolSetting.Builder()
            .name("Arrows Control Opposite")
            .description("Allows you to control the other entities rotation with the arrow keys.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> arrowSpeedSetting = arrowsSettings.add(new DoubleSetting.Builder()
            .name("Arrow speed")
            .description("Rotation speed with arrow keys.")
            .defaultValue(4)
            .min(0)
            .build()
    );

    public FreeLook(Category category) {
        super(category, "Free Look", "Allows more rotation options in third person.");
    }

    @Override
    public void onEnable() {
        cameraYaw = mc.player.getYaw();
        cameraPitch = mc.player.getPitch();
        prePerspective = mc.options.getPerspective();

        if (prePerspective != Perspective.THIRD_PERSON_BACK &&  togglePerpectiveSetting.get()) {
            mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }

    @Override
    public void onDisable() {
        if (mc.options.getPerspective() != prePerspective && togglePerpectiveSetting.get()) {
            mc.options.setPerspective(prePerspective);
        }
    }

    public boolean playerMode() {
        return isEnabled() && mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && modeSetting.get() == Mode.Player;
    }

    public boolean cameraMode() {
        return isEnabled() && modeSetting.get() == Mode.Camera;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (arrowsSetting.get()) {
            for (int i = 0; i < (arrowSpeedSetting.get() * 2); i++) {
                switch (modeSetting.get()) {
                    case Player -> {
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
                            cameraYaw -= 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
                            cameraYaw += 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
                            cameraPitch -= 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
                            cameraPitch += 0.5;
                        }
                    }
                    case Camera -> {
                        float yaw = mc.player.getYaw();
                        float pitch = mc.player.getPitch();

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) {
                            yaw -= 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) {
                            yaw += 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_UP)) {
                            pitch -= 0.5;
                        }

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
                            pitch += 0.5;
                        }

                        mc.player.setYaw(yaw);
                        mc.player.setPitch(pitch);
                    }
                }
            }
        }

        mc.player.setPitch(Utils.clamp(mc.player.getPitch(), -90, 90));
        cameraPitch = Utils.clamp(cameraPitch, -90, 90);
    }

    public enum Mode {
        Player("Player"),
        Camera("Camera");

        private final String name;

        Mode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
