package xyz.mathax.mathaxclient.systems.modules.player;

import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.DoubleSetting;
import xyz.mathax.mathaxclient.settings.EnumSetting;
import xyz.mathax.mathaxclient.settings.Setting;
import xyz.mathax.mathaxclient.settings.SettingGroup;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;

public class Rotation extends Module {
    private final SettingGroup yawSettings = settings.createGroup("Yaw");
    private final SettingGroup pitchSettings = settings.createGroup("Pitch");

    // Yaw

    private final Setting<LockMode> yawLockModeSetting = yawSettings.add(new EnumSetting.Builder<LockMode>()
            .name("Lock mode")
            .description("The way in which your yaw is locked.")
            .defaultValue(LockMode.Simple)
            .build()
    );

    private final Setting<Double> yawAngleSetting = yawSettings.add(new DoubleSetting.Builder()
            .name("Angle")
            .description("Yaw angle in degrees.")
            .defaultValue(0)
            .sliderMax(360)
            .range(0, 360)
            .build()
    );

    // Pitch

    private final Setting<LockMode> pitchLockMode = pitchSettings.add(new EnumSetting.Builder<LockMode>()
            .name("Lock-mode")
            .description("The way in which your pitch is locked.")
            .defaultValue(LockMode.Simple)
            .build()
    );

    private final Setting<Double> pitchAngleSetting = pitchSettings.add(new DoubleSetting.Builder()
            .name("Angle")
            .description("Pitch angle in degrees.")
            .defaultValue(0)
            .range(-90, 90)
            .sliderRange(-90, 90)
            .build()
    );

    public Rotation(Category category) {
        super(category, "Rotation", "Changes/locks your yaw and pitch.");
    }

    @Override
    public void onEnable() {
        onTick(null);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        switch (yawLockModeSetting.get()) {
            case Simple -> setYawAngle(yawAngleSetting.get().floatValue());
            case Smart  -> setYawAngle(getSmartYawDirection());
        }

        switch (pitchLockMode.get()) {
            case Simple -> mc.player.setPitch(pitchAngleSetting.get().floatValue());
            case Smart  -> mc.player.setPitch(getSmartPitchDirection());
        }
    }

    private float getSmartYawDirection() {
        return Math.round((mc.player.getYaw() + 1f) / 45f) * 45f;
    }

    private float getSmartPitchDirection() {
        return Math.round((mc.player.getPitch() + 1f) / 30f) * 30f;
    }

    private void setYawAngle(float yawAngle) {
        mc.player.setYaw(yawAngle);
        mc.player.headYaw = yawAngle;
        mc.player.bodyYaw = yawAngle;
    }

    public enum LockMode {
        Smart("Smart"),
        Simple("Simple"),
        None("None");

        private final String name;

        LockMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}