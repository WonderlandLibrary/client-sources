package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.input.Input;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class FreeLook extends Module {
    public float cameraYaw;
    public float cameraPitch;

    private Perspective prePers;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgArrows = settings.createGroup("Arrows");

    // General

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("Which entity to rotate.")
        .defaultValue(Mode.Player)
        .build()
    );

    public final Setting<Boolean> togglePerpective = sgGeneral.add(new BoolSetting.Builder()
        .name("toggle-perspective")
        .description("Changes your perspective on toggle.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Double> sensitivity = sgGeneral.add(new DoubleSetting.Builder()
        .name("camera-sensitivity")
        .description("How fast the camera moves in camera mode.")
        .defaultValue(8)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );

    // Arrows

    public final Setting<Boolean> arrows = sgArrows.add(new BoolSetting.Builder()
        .name("arrows-control-opposite")
        .description("Allows you to control the other entities rotation with the arrow keys.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> arrowSpeed = sgArrows.add(new DoubleSetting.Builder()
        .name("arrow-speed")
        .description("Rotation speed with arrow keys.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 5)
        .build()
    );

    public FreeLook() {
        super(Categories.Minigame, Items.PLAYER_HEAD, "free-look", "Allows more rotation options in third person.");
    }

    @Override
    public boolean onActivate() { //imagine looking
        cameraYaw = mc.player.getYaw();
        cameraPitch = mc.player.getPitch();
        prePers = mc.options.getPerspective();

        if (prePers != Perspective.THIRD_PERSON_BACK &&  togglePerpective.get()) mc.options.setPerspective(Perspective.THIRD_PERSON_BACK);
        return false;
    }

    @Override
    public void onDeactivate() {
        if (mc.options.getPerspective() != prePers && togglePerpective.get()) mc.options.setPerspective(prePers);
    }

    public boolean playerMode() {
        return isActive() && mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && mode.get() == Mode.Player;
    }

    public boolean cameraMode() {
        return isActive() && mc.options.getPerspective() == Perspective.THIRD_PERSON_BACK && mode.get() == Mode.Camera;
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (arrows.get()) {
            for (int i = 0; i < (arrowSpeed.get() * 2); i++) {
                switch (mode.get()) {
                    case Player -> {
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) cameraYaw -= 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) cameraYaw += 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_UP)) cameraPitch -= 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) cameraPitch += 0.5;
                    }
                    case Camera -> {
                        float yaw = mc.player.getYaw();
                        float pitch = mc.player.getPitch();

                        if (Input.isKeyPressed(GLFW.GLFW_KEY_LEFT)) yaw -= 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_RIGHT)) yaw += 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_UP)) pitch -= 0.5;
                        if (Input.isKeyPressed(GLFW.GLFW_KEY_DOWN)) pitch += 0.5;

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

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
