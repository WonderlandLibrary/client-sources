package xyz.mathax.mathaxclient.systems.modules.misc;

import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.entity.player.FinishUsingItemEvent;
import xyz.mathax.mathaxclient.events.entity.player.StoppedUsingItemEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseButtonEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.BoolSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import xyz.mathax.mathaxclient.utils.player.FindItemResult;
import xyz.mathax.mathaxclient.utils.player.InvUtils;

public class MiddleClickExtra extends Module {
    private boolean isUsing;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<Mode> modeSetting = generalSettings.add(new EnumSetting.Builder<Mode>()
            .name("Mode")
            .description("Which item to use when you middle click.")
            .defaultValue(Mode.Pearl)
            .build()
    );

    private final Setting<Boolean> notifySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Notify")
            .description("Notifies you when you do not have the specified item in your hotbar.")
            .defaultValue(true)
            .build()
    );

    public MiddleClickExtra(Category category) {
        super(category, "Middle Click Extra", "Lets you use items when you middle click.");
    }

    @Override
    public void onDisable() {
        stopIfUsing();
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action != KeyAction.Press || event.button != GLFW.GLFW_MOUSE_BUTTON_MIDDLE || mc.currentScreen != null) {
            return;
        }

        FindItemResult result = InvUtils.findInHotbar(modeSetting.get().item);
        if (!result.found()) {
            if (notifySetting.get()) {
                warning("Unable to find specified item.");
            }

            return;
        }

        InvUtils.swap(result.slot(), true);

        switch (modeSetting.get().type) {
            case Immediate -> {
                mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
                InvUtils.swapBack();
            }
            case Longer_Single_Click -> mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            case Longer -> {
                mc.options.useKey.setPressed(true);
                isUsing = true;
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (isUsing) {
            boolean pressed = true;
            if (mc.player.getMainHandStack().getItem() instanceof BowItem) {
                pressed = BowItem.getPullProgress(mc.player.getItemUseTime()) < 1;
            }

            mc.options.useKey.setPressed(pressed);
        }
    }

    @EventHandler
    private void onFinishUsingItem(FinishUsingItemEvent event) {
        stopIfUsing();
    }

    @EventHandler
    private void onStoppedUsingItem(StoppedUsingItemEvent event) {
        stopIfUsing();
    }

    private void stopIfUsing() {
        if (isUsing) {
            mc.options.useKey.setPressed(false);
            InvUtils.swapBack();
            isUsing = false;
        }
    }

    private enum Type {
        Immediate,
        Longer_Single_Click,
        Longer;
    }

    public enum Mode {
        Pearl("Pearl", Items.ENDER_PEARL, Type.Immediate),
        Rocket("Rocket", Items.FIREWORK_ROCKET, Type.Immediate),
        Rod("Rod", Items.FISHING_ROD, Type.Longer_Single_Click),
        Bow("Bow", Items.BOW, Type.Longer),
        Gap("Gap", Items.GOLDEN_APPLE, Type.Longer),
        EGap("EGap", Items.ENCHANTED_GOLDEN_APPLE, Type.Longer),
        Chorus("Chorus", Items.CHORUS_FRUIT, Type.Longer),
        XP("XP", Items.EXPERIENCE_BOTTLE, Type.Immediate);

        private final String name;

        private final Item item;

        private final Type type;

        Mode(String name, Item item, Type type) {
            this.name = name;
            this.item = item;
            this.type = type;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}