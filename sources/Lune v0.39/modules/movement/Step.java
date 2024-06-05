package me.superskidder.lune.modules.movement;

import me.superskidder.lune.events.EventStep;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventCategory;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.utils.timer.Timer;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Arrays;
import java.util.List;

public class Step extends Mod {
    private Num<Double> height;
    private Bool<Boolean> ncp;
    boolean resetTimer;
    private TimerUtil time = new TimerUtil();
    public static Timer lastStep = new Timer();
    private Num<Double> DELAY;
    private Mode<Enum<?>> mode;

    public Step() {
        super("Step", ModCategory.Movement,"Warning your feet");
        this.addValues(DELAY = new Num<>("Delay", 0.5, 0.0, 2.0));
        this.addValues(height = new Num<>("Height", 1.0, 1.0, 10.0));
    }


    @Override
    public void onEnabled() {
        this.resetTimer = false;
    }

    @Override
    public void onDisable() {
        if (this.mc.thePlayer != null) {
            this.mc.thePlayer.stepHeight = 0.5f;
        }
        this.mc.timer.timerSpeed = 1.0f;
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (this.mc.timer.timerSpeed < 1.0f && this.mc.thePlayer.onGround) {
            this.mc.timer.timerSpeed = 1.0f;
        }
    }

    @EventTarget
    public void onStep(EventStep event) {
        if (ModuleManager.getModByClass(Fly.class).getState()
                || ModuleManager.getModByClass(Scaffold.class).getState()) {
            return;
        }
        if (!mc.thePlayer.isInLiquid()) {
            if (this.resetTimer) {
                boolean resetTimer;
                if (!this.resetTimer) {
                    resetTimer = true;
                } else {
                    resetTimer = false;
                }
                this.resetTimer = resetTimer;
                this.mc.timer.timerSpeed = 1.0f;
            }
            if (event.getEventType() == EventCategory.PRE) {
                if (!this.mc.thePlayer.onGround || !this.time.isDelayComplete(this.DELAY.getValue().longValue())) {
                    event.setHeight(this.mc.thePlayer.stepHeight = 0.5f);
                    return;
                }
                this.mc.thePlayer.stepHeight = this.height.getValue().floatValue();
                event.setHeight(this.height.getValue().floatValue());
            } else if (event.getHeight() > 0.5) {
                final double n = this.mc.thePlayer.getEntityBoundingBox().minY - this.mc.thePlayer.posY;
                if (n >= 0.625) {
                    final float n2 = 0.6f;
                    float n3;
                    if (n >= 1.0) {
                        n3 = Math.abs(1.0f - (float) n) * 0.33f;
                    } else {
                        n3 = 0.0f;
                    }
                    mc.timer.timerSpeed = n2 - n3;
                    if (this.mc.timer.timerSpeed <= 0.05f) {
                        this.mc.timer.timerSpeed = 0.05f;
                    }
                    this.resetTimer = true;
                    this.ncpStep(n);
                    this.time.reset();
                }
            }
        }
    }

    void ncpStep(final double n) {
        final List<Double> list = Arrays.asList(0.42, 0.333, 0.248, 0.083, -0.078);
        final double posX = this.mc.thePlayer.posX;
        final double posZ = this.mc.thePlayer.posZ;
        double posY = this.mc.thePlayer.posY;
        if (n < 1.1) {
            double n2 = 0.42;
            double n3 = 0.75;
            if (n != 1.0) {
                n2 *= n;
                n3 *= n;
                if (n2 > 0.425) {
                    n2 = 0.425;
                }
                if (n3 > 0.78) {
                    n3 = 0.78;
                }
                if (n3 < 0.49) {
                    n3 = 0.49;
                }
            }
            if (n2 == 0.42) {
                n2 = 0.41999998688698;
            }
            this.mc.thePlayer.sendQueue
                    .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + n2, posZ, false));
            if (posY + n3 < posY + n) {
                this.mc.thePlayer.sendQueue
                        .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + n3, posZ, false));
            }
            return;
        }
        if (n < 1.6) {
            int i = 0;
            while (i < list.size()) {
                posY += list.get(i);
                this.mc.thePlayer.sendQueue
                        .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, posZ, false));
                ++i;
            }
        } else if (n < 2.1) {
            final double[] array = {0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
            final int length = array.length;
            int j = 0;
            while (j < length) {
                this.mc.thePlayer.sendQueue.addToSendQueue(
                        new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array[j], posZ, false));
                ++j;
            }
        } else {
            final double[] array2 = {0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
            final int length2 = array2.length;
            int k = 0;
            while (k < length2) {
                this.mc.thePlayer.sendQueue.addToSendQueue(
                        new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + array2[k], posZ, false));
                ++k;
            }
        }
    }
}
