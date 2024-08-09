package mathax.client.systems.modules.render;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.game.ChangePerspectiveEvent;
import mathax.client.events.mathax.MouseScrollEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.misc.KeyBind;
import net.minecraft.client.option.Perspective;
import net.minecraft.item.Items;

public class CameraTweaks extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgScrolling = settings.createGroup("Scrolling");

    public double distance;

    // General

    private final Setting<Boolean> clip = sgGeneral.add(new BoolSetting.Builder()
        .name("clip")
        .description("Allows the camera to clip through blocks.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> cameraDistance = sgGeneral.add(new DoubleSetting.Builder()
        .name("camera-distance")
        .description("The distance the third person camera is from the player.")
        .defaultValue(4)
        .min(0)
        .onChanged(value -> distance = value)
        .build()
    );

    // Scrolling

    private final Setting<Boolean> scrollingEnabled = sgScrolling.add(new BoolSetting.Builder()
        .name("scrolling-enabled")
        .description("Allows you to scroll to change camera distance.")
        .defaultValue(false)
        .build()
    );

    private final Setting<Double> scrollSensitivity = sgScrolling.add(new DoubleSetting.Builder()
        .name("scroll-sensitivity")
        .description("Scroll sensitivity when changing the cameras distance. 0 to disable.")
        .visible(scrollingEnabled::get)
        .defaultValue(1)
        .min(0)
        .build()
    );

    private final Setting<KeyBind> scrollKeybind = sgScrolling.add(new KeyBindSetting.Builder()
        .name("scroll-keybind")
        .description("Makes it so a keybind needs to be pressed for scrolling to work.")
        .visible(scrollingEnabled::get)
        .defaultValue(KeyBind.none())
        .build()
    );

    public CameraTweaks() {
        super(Categories.Render, Items.COMMAND_BLOCK, "camera-tweaks", "Allows modification of the third person camera.");
    }

    @Override
    public boolean onActivate() {
        distance = cameraDistance.get();
        return false;
    }

    @EventHandler
    private void onPerspectiveChanged(ChangePerspectiveEvent event) {
        distance = cameraDistance.get();
    }
    //so many very important code comments

    @EventHandler
    private void onMouseScroll(MouseScrollEvent event) {
        if (mc.options.getPerspective() == Perspective.FIRST_PERSON || mc.currentScreen != null || !scrollingEnabled.get() || (scrollKeybind.get().isValid() && !scrollKeybind.get().isPressed())) return;

        if (scrollSensitivity.get() > 0) {
            distance -= event.value * 0.25 * (scrollSensitivity.get() * distance);

            event.cancel();
        }
    }

    public boolean clip() {
        return isActive() && clip.get();
    }

    public double getDistance() {
        return isActive() ? distance : 4;
    }
}
