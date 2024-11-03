package dev.stephen.nexus.module.modules.ghost;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.Module;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.setting.impl.BooleanSetting;
import dev.stephen.nexus.module.setting.impl.RangeSetting;
import dev.stephen.nexus.utils.math.RandomUtil;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import dev.stephen.nexus.utils.timer.MillisTimer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AutoClicker extends Module {
    public static final RangeSetting attackSpeed = new RangeSetting("CPS", 0, 25, 10, 12, 0.1);
    public static final BooleanSetting dropCps = new BooleanSetting("Drop CPS", true);
    public static final BooleanSetting playSound = new BooleanSetting("Play Sound", false);
    public static final BooleanSetting oneDotEight = new BooleanSetting("1.8 Swing Order", false);

    public AutoClicker() {
        super("AutoClicker", "Clicks for you", 0, ModuleCategory.GHOST);
        this.addSettings(attackSpeed, dropCps, playSound, oneDotEight);
    }

    final MillisTimer timer = new MillisTimer();
    private long cps, prevCps, lastSound;

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = e -> {
        if (isNull()) {
            return;
        }

        if (!mc.options.attackKey.isPressed()) {
            return;
        }

        if (dropCps.getValue()) {
            if (mc.player.age % 12 == 0) {
                cps -= (long) RandomUtil.smartRandom(1, 3);
            }
        }

        if (cps == prevCps) cps -= (long) RandomUtil.smartRandom(1, 3);

        if (timer.hasElapsed(1000L / cps)) {
            if (playSound.getValue()) {
                if (System.currentTimeMillis() > lastSound) {
                    new Thread(() -> playSound("click")).start();
                    lastSound = System.currentTimeMillis() + 80;
                }
            }
            PlayerUtil.click(oneDotEight.getValue());
            prevCps = cps;
            randomize();
            timer.reset();
        }
    };

    public static void playSound(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(AutoClicker.class.getResource("/assets/nexus/sounds/" + name + ".wav")));
                clip.start();
                Thread.sleep(clip.getMicrosecondLength());
            } catch (Exception e) {
                System.out.println("Error with playing sound.");
            }
            if (clip != null) {
                clip.close();
            }
        });
    }

    private void randomize() {
        cps = (long) RandomUtil.smartRandom(attackSpeed.getValueMin(), attackSpeed.getValueMax()) + 6;
    }

    @Override
    public void onEnable() {
        randomize();
        prevCps = 0;
        lastSound = System.currentTimeMillis() + 80;
        super.onEnable();
    }
}
