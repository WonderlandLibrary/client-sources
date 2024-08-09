package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.ChangePerspectiveEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseScrollEvent;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.input.KeyBind;
import net.minecraft.client.option.Perspective;
import xyz.mathax.mathaxclient.settings.*;

public class CameraTweaks extends Module {
    public double distance;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup scrollingSettings = settings.createGroup("Scrolling");

    // General

    private final Setting<Boolean> clipSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Clip")
            .description("Allows the camera to clip through blocks.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> cameraDistanceSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Camera distance")
            .description("The distance the third person camera is from the player.")
            .defaultValue(4)
            .min(0)
            .onChanged(value -> distance = value)
            .build()
    );

    // Scrolling

    private final Setting<Boolean> scrollingEnabledSetting = scrollingSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Allows you to scroll to change camera distance.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> scrollSensitivitySetting = scrollingSettings.add(new DoubleSetting.Builder()
            .name("Scroll sensitivity")
            .description("Scroll sensitivity when changing the cameras distance. 0 to disable.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    private final Setting<KeyBind> scrollKeybindSetting = scrollingSettings.add(new KeyBindSetting.Builder()
            .name("Scroll keybind")
            .description("Make it so a keybind needs to be pressed for scrolling to work.")
            .defaultValue(KeyBind.none())
            .build()
    );

    public CameraTweaks(Category category) {
        super(category, "Camera Tweaks", "Allows modification of the third person camera.");
    }

    @Override
    public void onEnable() {
        distance = cameraDistanceSetting.get();
    }

    @EventHandler
    private void onPerspectiveChanged(ChangePerspectiveEvent event) {
        distance = cameraDistanceSetting.get();
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent event) {
        if (mc.options.getPerspective() == Perspective.FIRST_PERSON || mc.currentScreen != null || !scrollingEnabledSetting.get() || (scrollKeybindSetting.get().isValid() && !scrollKeybindSetting.get().isPressed())) {
            return;
        }

        if (scrollSensitivitySetting.get() > 0) {
            distance -= event.value * 0.25 * (scrollSensitivitySetting.get() * distance);

            event.cancel();
        }
    }

    public boolean clip() {
        return isEnabled() && clipSetting.get();
    }

    public double getDistance() {
        return isEnabled() ? distance : 4;
    }
}
