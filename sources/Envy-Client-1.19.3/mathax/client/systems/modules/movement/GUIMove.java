package mathax.client.systems.modules.movement;

import mathax.client.MatHax;
import mathax.client.eventbus.EventHandler;
import mathax.client.events.mathax.KeyEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.gui.WidgetScreen;
import mathax.client.mixin.CreativeInventoryScreenAccessor;
import mathax.client.mixin.KeyBindingAccessor;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.input.Input;
import mathax.client.utils.misc.input.KeyAction;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;

import static org.lwjgl.glfw.GLFW.*;

public class GUIMove extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Screens> screens = sgGeneral.add(new EnumSetting.Builder<Screens>()
        .name("guis")
        .description("Which GUIs to move in.")
        .defaultValue(Screens.Inventory)
        .build()
    );

    private final Setting<Boolean> jump = sgGeneral.add(new BoolSetting.Builder()
        .name("jump")
        .description("Allows you to jump while in GUIs.")
        .defaultValue(true)
        .onChanged(aBoolean -> {
            if (isActive() && !aBoolean) set(mc.options.jumpKey, false);
        })
        .build()
    );

    private final Setting<Boolean> sneak = sgGeneral.add(new BoolSetting.Builder()
        .name("sneak")
        .description("Allows you to sneak while in GUIs.")
        .defaultValue(true)
        .onChanged(aBoolean -> {
            if (isActive() && !aBoolean) set(mc.options.sneakKey, false);
        })
        .build()
    );

    private final Setting<Boolean> sprint = sgGeneral.add(new BoolSetting.Builder()
        .name("sprint")
        .description("Allows you to sprint while in GUIs.")
        .defaultValue(true)
        .onChanged(aBoolean -> {
            if (isActive() && !aBoolean) set(mc.options.sprintKey, false);
        })
        .build()
    );

    private final Setting<Boolean> arrowsRotate = sgGeneral.add(new BoolSetting.Builder()
        .name("arrows-rotate")
        .description("Allows you to use your arrow keys to rotate while in GUIs.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> rotateSpeed = sgGeneral.add(new DoubleSetting.Builder()
        .name("rotate-speed")
        .description("Rotation speed while in GUIs.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );

    public GUIMove() {
        super(Categories.Ghost, Items.DIAMOND_BOOTS, "gui-move", "Allows you to perform various actions while in GUIs.");
    }

    @Override
    public void onDeactivate() {
        set(mc.options.forwardKey, false);
        set(mc.options.backKey, false);
        set(mc.options.leftKey, false);
        set(mc.options.rightKey, false);

        if (jump.get()) set(mc.options.jumpKey, false);
        if (sneak.get()) set(mc.options.sneakKey, false);
        if (sprint.get()) set(mc.options.sprintKey, false);
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (skip()) return;
        if (screens.get() == Screens.GUI && !(mc.currentScreen instanceof WidgetScreen)) return;
        if (screens.get() == Screens.Inventory && mc.currentScreen instanceof WidgetScreen) return;

        set(mc.options.forwardKey, Input.isPressed(mc.options.forwardKey));
        set(mc.options.backKey, Input.isPressed(mc.options.backKey));
        set(mc.options.leftKey, Input.isPressed(mc.options.leftKey));
        set(mc.options.rightKey, Input.isPressed(mc.options.rightKey));

        if (jump.get()) set(mc.options.jumpKey, Input.isPressed(mc.options.jumpKey));
        if (sneak.get()) set(mc.options.sneakKey, Input.isPressed(mc.options.sneakKey));
        if (sprint.get()) set(mc.options.sprintKey, Input.isPressed(mc.options.sprintKey));

        if (arrowsRotate.get()) {
            float yaw = mc.player.getYaw();
            float pitch = mc.player.getPitch();

            for (int i = 0; i < (rotateSpeed.get() * 2); i++) {
                if (Input.isKeyPressed(GLFW_KEY_LEFT)) yaw -= 0.5;
                if (Input.isKeyPressed(GLFW_KEY_RIGHT)) yaw += 0.5;
                if (Input.isKeyPressed(GLFW_KEY_UP)) pitch -= 0.5;
                if (Input.isKeyPressed(GLFW_KEY_DOWN)) pitch += 0.5;
            }

            pitch = Utils.clamp(pitch, -90, 90);

            mc.player.setYaw(yaw);
            mc.player.setPitch(pitch);
        }
    }

    private void set(KeyBinding bind, boolean pressed) {
        boolean wasPressed = bind.isPressed();
        bind.setPressed(pressed);

        InputUtil.Key key = ((KeyBindingAccessor) bind).getKey();
        if (wasPressed != pressed && key.getCategory() == InputUtil.Type.KEYSYM) MatHax.EVENT_BUS.post(KeyEvent.get(key.getCode(), 0, pressed ? KeyAction.Press : KeyAction.Release));
    }

    public boolean skip() {
        return mc.currentScreen == null || (mc.currentScreen instanceof CreativeInventoryScreen && CreativeInventoryScreenAccessor.getSelectedTab() == ItemGroups.getSearchGroup()) || mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof SignEditScreen || mc.currentScreen instanceof AnvilScreen || mc.currentScreen instanceof AbstractCommandBlockScreen || mc.currentScreen instanceof StructureBlockScreen;
    }

    public enum Screens {
        GUI("GUI"),
        Inventory("Inventory"),
        Both("Both");

        private final String title;

        Screens(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
