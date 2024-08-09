package xyz.mathax.mathaxclient.systems.modules.render;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.render.ArmRenderEvent;
import xyz.mathax.mathaxclient.events.render.HeldItemRendererEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.player.Rotations;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RotationAxis;

public class HandView extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup mainHandSettings = settings.createGroup("Main Hand");
    private final SettingGroup offHandSettings = settings.createGroup("Off Hand");
    private final SettingGroup armSettings = settings.createGroup("Arm");
    private final SettingGroup swingSettings = settings.createGroup("Swing");

    // General

    private final Setting<Boolean> followRotationsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Follow rotations")
            .description("Make your hands follow your serverside rotations.")
            .defaultValue(false)
            .build()
    );

    // Scale

    // Main Hand

    private final Setting<Double> scaleXMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand scale X")
            .description("The X scale of your main hand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleYMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand scale Y")
            .description("The Y scale of your main hand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleZMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand scale Z")
            .description("The Z scale of your main hand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    // Offhand

    private final Setting<Double> scaleXOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand scale X")
            .description("The X scale of your offhand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleYOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand scale Y")
            .description("The Y scale of your offhand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleZOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand scale Z")
            .description("The Z scale of your offhand.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    // Arm

    private final Setting<Double> scaleXArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm scale X")
            .description("The X scale of your arm.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleYArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm scale Y")
            .description("The Y scale of your arm.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    private final Setting<Double> scaleZArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm scale Z")
            .description("The Z scale of your arm.")
            .defaultValue(1)
            .min(0)
            .sliderRange(0, 5)
            .build()
    );

    // Position

    // Main Hand

    private final Setting<Double> posXMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand position X")
            .description("The X position offset of your main hand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posYMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand position Y")
            .description("The Y position offset of your main hand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posZMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand position Z")
            .description("The Z position offset of your main hand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    // Offhand

    private final Setting<Double> posXOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand position X")
            .description("The X position offset of your offhand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posYOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand position Y")
            .description("The Y position offset of your offhand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posZOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand position Z")
            .description("The Z position offset of your offhand.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    // Arm

    private final Setting<Double> posXArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm position X")
            .description("The X position offset of your arm.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posYArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm position Y")
            .description("The Y position offset of your arm.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    private final Setting<Double> posZArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm position Z")
            .description("The Z position offset of your arm.")
            .defaultValue(0)
            .sliderRange(-3, 3)
            .build()
    );

    // Rotation

    // Main Hand

    private final Setting<Double> rotationXMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand rotation X")
            .description("The X orientation of your main hand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationYMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand rotation Y")
            .description("The Y orientation of your main hand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationZMainSetting = mainHandSettings.add(new DoubleSetting.Builder()
            .name("Main hand rotationZ")
            .description("The Z orientation of your main hand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    // Offhand

    private final Setting<Double> rotationXOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand rotation X")
            .description("The X orientation of your offhand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationYOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand rotation Y")
            .description("The Y orientation of your offhand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationZOffSetting = offHandSettings.add(new DoubleSetting.Builder()
            .name("Offhand rotation Z")
            .description("The Z orientation of your offhand.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    // Arm

    private final Setting<Double> rotationXArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm rotation X")
            .description("The X orientation of your arm.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationYArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm rotation Y")
            .description("The Y orientation of your arm.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    private final Setting<Double> rotationZArmSetting = armSettings.add(new DoubleSetting.Builder()
            .name("Arm rotation Z")
            .description("The Z orientation of your arm.")
            .defaultValue(0)
            .sliderRange(-180, 180)
            .build()
    );

    // Swing

    public final Setting<SwingMode> swingModeSetting = swingSettings.add(new EnumSetting.Builder<SwingMode>()
            .name("Mode")
            .description("Modifies your client & server side hand swinging.")
            .defaultValue(SwingMode.None)
            .build()
    );

    public final Setting<Double> mainSwingSetting = swingSettings.add(new DoubleSetting.Builder()
            .name("Main progress")
            .description("The swing progress of your main hand.")
            .defaultValue(0)
            .range(0, 1)
            .sliderRange(0, 1)
            .build()
    );

    public final Setting<Double> offSwingSetting = swingSettings.add(new DoubleSetting.Builder()
            .name("Offhand progress")
            .description("The swing progress of your offhand.")
            .defaultValue(0)
            .range(0, 1)
            .sliderRange(0, 1)
            .build()
    );

    public final Setting<Integer> swingSpeedSetting = swingSettings.add(new IntSetting.Builder()
            .name("Swing speed")
            .description("The swing speed of your hands. (higher = slower swing)")
            .defaultValue(6)
            .range(0, 20)
            .sliderRange(0, 20)
            .build()
    );

    public HandView(Category category) {
        super(category, "Hand View", "Alters the way items are rendered in your hands.");
    }

    @EventHandler
    private void onHeldItemRender(HeldItemRendererEvent event) {
        if (!isEnabled()) {
            return;
        }

        if (event.hand == Hand.MAIN_HAND) {
            event.matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotationXMainSetting.get().floatValue()));
            event.matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationYMainSetting.get().floatValue()));
            event.matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotationZMainSetting.get().floatValue()));
            event.matrixStack.scale(scaleXMainSetting.get().floatValue(), scaleYMainSetting.get().floatValue(), scaleZMainSetting.get().floatValue());
            event.matrixStack.translate(posXMainSetting.get().floatValue(), posYMainSetting.get().floatValue(), posZMainSetting.get().floatValue());
        } else {
            event.matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotationXOffSetting.get().floatValue()));
            event.matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationYOffSetting.get().floatValue()));
            event.matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotationZOffSetting.get().floatValue()));
            event.matrixStack.scale(scaleXOffSetting.get().floatValue(), scaleYOffSetting.get().floatValue(), scaleZOffSetting.get().floatValue());
            event.matrixStack.translate(posXOffSetting.get().floatValue(), posYOffSetting.get().floatValue(), posZOffSetting.get().floatValue());
        }

        if (Rotations.rotating && followRotationsSetting.get()) {
            applyServerRotations(event.matrixStack);
        }
    }

    @EventHandler
    private void onRenderArm(ArmRenderEvent event) {
        if (!isEnabled()) {
            return;
        }

        event.matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(rotationXArmSetting.get().floatValue()));
        event.matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationYArmSetting.get().floatValue()));
        event.matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotationZArmSetting.get().floatValue()));
        event.matrixStack.scale(scaleXArmSetting.get().floatValue(), scaleYArmSetting.get().floatValue(), scaleZArmSetting.get().floatValue());
        event.matrixStack.translate(posXArmSetting.get().floatValue(), posYArmSetting.get().floatValue(), posZArmSetting.get().floatValue());
    }

    private void applyServerRotations(MatrixStack matrixStack) {
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(mc.player.getPitch() - Rotations.serverPitch));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(mc.player.getYaw() - Rotations.serverYaw));
    }

    public enum SwingMode {
        Offhand("Offhand"),
        Mainhand("Mainhand"),
        None("None");

        private final String name;

        SwingMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}