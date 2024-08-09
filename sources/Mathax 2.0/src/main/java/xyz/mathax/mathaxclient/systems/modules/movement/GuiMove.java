package xyz.mathax.mathaxclient.systems.modules.movement;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.mixin.CreativeInventoryScreenAccessor;
import xyz.mathax.mathaxclient.mixin.KeyBindingAccessor;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.Input;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroups;

import static org.lwjgl.glfw.GLFW.*;

public class GuiMove extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Screens> screensSetting = generalSettings.add(new EnumSetting.Builder<Screens>()
            .name("GUIs")
            .description("Which GUIs to move in.")
            .defaultValue(Screens.Inventory)
            .build()
    );

    private final Setting<Boolean> arrowsRotateSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Arrows rotate")
            .description("Allow you to use your arrow keys to rotate while in GUIs.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> rotateSpeedSetting = generalSettings.add(new DoubleSetting.Builder()
            .name("Rotate speed")
            .description("Rotation speed while in GUIs.")
            .defaultValue(4)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Boolean> jumpSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Jump")
            .description("Allow you to jump while in GUIs.")
            .defaultValue(true)
            .onChanged(value -> {
                if (isEnabled() && !value) {
                    set(mc.options.jumpKey, false);
                }
            })
            .build()
    );

    private final Setting<Boolean> sneakSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Sneak")
            .description("Allow you to sneak while in GUIs.")
            .defaultValue(true)
            .onChanged(value -> {
                if (isEnabled() && !value) {
                    set(mc.options.sneakKey, false);
                }
            })
            .build()
    );

    private final Setting<Boolean> sprintSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Sprint")
            .description("Allow you to sprint while in GUIs.")
            .defaultValue(true)
            .onChanged(value -> {
                if (isEnabled() && !value) {
                    set(mc.options.sprintKey, false);
                }
            })
            .build()
    );

    public GuiMove(Category category) {
        super(category, "GUI Move", "Allows you to perform various actions while in GUIs.");
    }

    @Override
    public void onDisable() {
        set(mc.options.forwardKey, false);
        set(mc.options.backKey, false);
        set(mc.options.leftKey, false);
        set(mc.options.rightKey, false);

        if (jumpSetting.get()) {
            set(mc.options.jumpKey, false);
        }

        if (sneakSetting.get()) {
            set(mc.options.sneakKey, false);
        }

        if (sprintSetting.get()) {
            set(mc.options.sprintKey, false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (skip()) {
            return;
        }

        if (screensSetting.get() == Screens.GUI && !(mc.currentScreen instanceof WidgetScreen)) {
            return;
        }

        if (screensSetting.get() == Screens.Inventory && mc.currentScreen instanceof WidgetScreen) {
            return;
        }

        set(mc.options.forwardKey, Input.isPressed(mc.options.forwardKey));
        set(mc.options.backKey, Input.isPressed(mc.options.backKey));
        set(mc.options.leftKey, Input.isPressed(mc.options.leftKey));
        set(mc.options.rightKey, Input.isPressed(mc.options.rightKey));

        if (jumpSetting.get()) {
            set(mc.options.jumpKey, Input.isPressed(mc.options.jumpKey));
        }

        if (sneakSetting.get()) {
            set(mc.options.sneakKey, Input.isPressed(mc.options.sneakKey));
        }

        if (sprintSetting.get()) {
            set(mc.options.sprintKey, Input.isPressed(mc.options.sprintKey));
        }

        if (arrowsRotateSetting.get()) {
            float yaw = mc.player.getYaw();
            float pitch = mc.player.getPitch();
            for (int i = 0; i < (rotateSpeedSetting.get() * 2); i++) {
                if (Input.isKeyPressed(GLFW_KEY_LEFT)) {
                    yaw -= 0.5;
                }

                if (Input.isKeyPressed(GLFW_KEY_RIGHT)) {
                    yaw += 0.5;
                }

                if (Input.isKeyPressed(GLFW_KEY_UP)) {
                    pitch -= 0.5;
                }

                if (Input.isKeyPressed(GLFW_KEY_DOWN)) {
                    pitch += 0.5;
                }
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
        if (wasPressed != pressed && key.getCategory() == InputUtil.Type.KEYSYM) {
            MatHax.EVENT_BUS.post(KeyEvent.get(key.getCode(), 0, pressed ? KeyAction.Press : KeyAction.Release));
        }
    }

    public boolean skip() {
        return mc.currentScreen == null || (mc.currentScreen instanceof CreativeInventoryScreen && CreativeInventoryScreenAccessor.getSelectedTab() == ItemGroups.getSearchGroup()) || mc.currentScreen instanceof ChatScreen || mc.currentScreen instanceof SignEditScreen || mc.currentScreen instanceof AnvilScreen || mc.currentScreen instanceof AbstractCommandBlockScreen || mc.currentScreen instanceof StructureBlockScreen;
    }

    public enum Screens {
        GUI("GUI"),
        Inventory("Inventory"),
        Both("Both");

        private final String name;

        Screens(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
