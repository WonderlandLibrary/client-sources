package mathax.client.systems.modules.ghost;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.item.Items;

import java.util.Random;

public class AutoClicker extends Module {
    private int timer;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // General

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
        .name("mode")
        .description("The method of clicking.")
        .defaultValue(Mode.Press)
        .build()
    );

    private final Setting<Button> button = sgGeneral.add(new EnumSetting.Builder<Button>()
        .name("button")
        .description("Which button to press.")
        .defaultValue(Button.Attack)
        .build()
    );
    private final Setting<Double> jitterAttack = sgGeneral.add(new DoubleSetting.Builder()
        .name("Jitter Attack")
        .description("Gives you grandma hands when attacking.")
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );
    private final Setting<Double> jitterUse = sgGeneral.add(new DoubleSetting.Builder()
        .name("Jitter Use")
        .description("Gives you grandma hands when using an item.")
        .defaultValue(1)
        .min(0)
        .sliderRange(0, 10)
        .build()
    );
    private final Setting<Integer> minDelay = sgGeneral.add(new IntSetting.Builder()
        .name("Minimum Click Delay")
        .description("The shortest delay between clicks.")
        .defaultValue(2)
        .min(0)
        .sliderRange(0, 60)
        .build()
    );
    private final Setting<Integer> maxDelay = sgGeneral.add(new IntSetting.Builder()
        .name("Maximum Click Delay")
        .description("The longest delay between clicks.")
        .defaultValue(4)
        .min(0)
        .sliderRange(0, 60)
        .build()
    );
    public AutoClicker() {
        super(Categories.Ghost, Items.STONE_BUTTON, "auto-clicker", "Automatically clicks.");
    }

    @Override
    public boolean onActivate() {
        timer = 0;
        mc.options.attackKey.setPressed(false);
        mc.options.useKey.setPressed(false);
        return false;
    }

    @Override
    public void onDeactivate() {
        mc.options.attackKey.setPressed(false);
        mc.options.useKey.setPressed(false);
    }
    @EventHandler
    private void onTick(TickEvent.Post event) {
        switch (mode.get()) {
            case Hold:
                switch (button.get()) {
                    case Attack -> {
                        mc.options.attackKey.setPressed(true);
                        if (jitterAttack.get() > 0) applyJitter();
                    }
                    case Use -> {
                        mc.options.useKey.setPressed(true);
                        if (jitterUse.get() > 0) applyJitter();
                    }
                }
                break;
            case Press:
                timer++;
                int min = minDelay.get();
                int max = maxDelay.get();
                if (min > max) {
                    min = 0;
                    max = 0;
                }
                Random random = new Random();
                int randomDelay = random.nextInt(max - min + 1) + min;
                if (!(randomDelay > timer)) {
                    switch (button.get()) {
                        case Attack -> {
                            Utils.leftClick();
                            applyJitter();
                        }
                        case Use -> {
                            Utils.rightClick();
                            applyJitter();
                        }
                    }
                    timer = 0;
                }
                break;
        }
    }

    private void applyJitter() {
        if (jitterUse.get() > 0 || jitterAttack.get() > 0) {
            Random random = new Random();
            switch (button.get()) {
                case Use:
                    double yawJitterU = jitterUse.get() * (random.nextDouble() * 2 - 1);
                    double pitchJitterU = jitterUse.get() * (random.nextDouble() * 2 - 1);
                    double newYawU = mc.player.getYaw() + yawJitterU;
                    double newPitchU = mc.player.getPitch() + pitchJitterU;
                    if (newYawU > 180) newYawU -= 360;
                    if (newYawU < -180) newYawU += 360;
                    if (newPitchU > 90) newPitchU = 90;
                    if (newPitchU < -90) newPitchU = -90;
                    mc.player.setYaw((float)newYawU);
                    mc.player.setPitch((float)newPitchU);

                case Attack:
                    double yawJitterA = jitterAttack.get() * (random.nextDouble() * 2 - 1);
                    double pitchJitterA = jitterAttack.get() * (random.nextDouble() * 2 - 1);
                    double newYawA = mc.player.getYaw() + yawJitterA;
                    double newPitchA = mc.player.getPitch() + pitchJitterA;
                    if (newYawA > 180) newYawA -= 360;
                    if (newYawA < -180) newYawA += 360;
                    if (newPitchA > 90) newPitchA = 90;
                    if (newPitchA < -90) newPitchA = -90;
                    mc.player.setYaw((float)newYawA);
                    mc.player.setPitch((float)newPitchA);
            }


        }
    }
    public enum Mode {
        Hold("Hold"),
        Press("Auto Click");

        private final String title;

        Mode(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }

    public enum Button {
        Use("Use"),
        Attack("Attack");

        private final String title;

        Button(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
