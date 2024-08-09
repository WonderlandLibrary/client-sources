package xyz.mathax.mathaxclient.systems.modules.player;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.world.PlaySoundEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;

public class AutoFish extends Module {
    private boolean ticksEnabled;
    private int ticksToRightClick;
    private int ticksData;

    private int autoCastTimer;
    private boolean autoCastEnabled;

    private int autoCastCheckTimer;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup splashRangeDetectionSettings = settings.createGroup("Splash Detection");

    // General

    private final Setting<Boolean> autoCastSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Auto cast")
            .description("Automatically casts when not fishing.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> ticksAutoCastSetting = generalSettings.add(new IntSetting.Builder()
            .name("Ticks auto cast")
            .description("The amount of ticks to wait before recasting automatically.")
            .defaultValue(10)
            .min(0)
            .sliderRange(0, 60)
            .build()
    );

    private final Setting<Integer> ticksCatchSetting = generalSettings.add(new IntSetting.Builder()
            .name("Catch delay")
            .description("The amount of ticks to wait before catching the fish.")
            .defaultValue(6)
            .min(0)
            .sliderRange(0, 60)
            .build()
    );

    private final Setting<Integer> ticksThrowSetting = generalSettings.add(new IntSetting.Builder()
            .name("Throw delay")
            .description("The amount of ticks to wait before throwing the bobber.")
            .defaultValue(14)
            .min(0)
            .sliderRange(0, 60)
            .build()
    );

    // Splash Detection

    private final Setting<Boolean> splashDetectionRangeEnabledSetting = splashRangeDetectionSettings.add(new BoolSetting.Builder()
            .name("Splash detection range enabled")
            .description("Allows you to use multiple accounts next to each other.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Double> splashDetectionRangeSetting = splashRangeDetectionSettings.add(new DoubleSetting.Builder()
            .name("Splash detection range")
            .description("The detection range of a splash. Lower values will not work when the TPS is low.")
            .defaultValue(10)
            .min(0)
            .range(0, 20)
            .build()
    );

    public AutoFish(Category category) {
        super(category, "Auto Fish", "Automatically fishes for you.");
    }

    @Override
    public void onEnable() {
        ticksEnabled = false;
        autoCastEnabled = false;

        autoCastCheckTimer = 0;
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        SoundInstance sound = event.sound;
        FishingBobberEntity fishingBobber = mc.player.fishHook;
        if (sound.getId().getPath().equals("entity.fishing_bobber.splash")) {
            if (!splashDetectionRangeEnabledSetting.get() || Utils.distance(fishingBobber.getX(), fishingBobber.getY(), fishingBobber.getZ(), sound.getX(), sound.getY(), sound.getZ()) <= splashDetectionRangeSetting.get()) {
                ticksEnabled = true;
                ticksToRightClick = ticksCatchSetting.get();
                ticksData = 0;
            }
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (autoCastCheckTimer <= 0) {
            autoCastCheckTimer = 30;

            if (autoCastSetting.get() && !ticksEnabled && !autoCastEnabled && mc.player.fishHook == null && mc.player.getMainHandStack().getItem() instanceof FishingRodItem) {
                autoCastTimer = 0;
                autoCastEnabled = true;
            }
        } else {
            autoCastCheckTimer--;
        }

        if (autoCastEnabled) {
            autoCastTimer++;

            if (autoCastTimer > ticksAutoCastSetting.get()) {
                autoCastEnabled = false;
                Utils.rightClick();
            }
        }

        if (ticksEnabled && ticksToRightClick <= 0) {
            if (ticksData == 0) {
                Utils.rightClick();
                ticksToRightClick = ticksThrowSetting.get();
                ticksData = 1;
            }
            else if (ticksData == 1) {
                Utils.rightClick();
                ticksEnabled = false;
            }
        }

        ticksToRightClick--;
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (mc.options.useKey.isPressed()) {
            ticksEnabled = false;
        }
    }
}