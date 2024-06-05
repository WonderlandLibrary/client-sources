/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.hack.combat;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.TargetPickListener;
import de.dietrichpaul.clientbase.event.UpdateListener;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickCallback;
import de.dietrichpaul.clientbase.feature.engine.clicking.ClickSpoof;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.feature.hack.HackCategory;
import de.dietrichpaul.clientbase.feature.engine.rotation.impl.AimbotRotationSpoof;
import de.dietrichpaul.clientbase.property.impl.EnumProperty;
import de.dietrichpaul.clientbase.property.impl.FloatProperty;
import de.dietrichpaul.clientbase.property.impl.IntProperty;
import de.dietrichpaul.clientbase.util.math.MathUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

public class KillAuraHack extends Hack implements UpdateListener, TargetPickListener, ClickSpoof {
    private final AimbotRotationSpoof aimbot;

    private IntProperty maxCPSProperty = new IntProperty("MaxCPS", 20, 0, 20);
    private IntProperty minCPSProperty = new IntProperty("MinCPS", 20, 0, 20);
    private FloatProperty stdProperty = new FloatProperty("STD", 2, 0, 10);
    private FloatProperty meanProperty = new FloatProperty("Mean", 15, 0, 20);

    private IntProperty failRateProperty = new IntProperty("FailRate", 0, 0, 100);
    private EnumProperty<CPSMode> cpsModeProperty = new EnumProperty<>("CPSMode", CPSMode.CUSTOM,
            CPSMode.values(), CPSMode.class);
    private EnumProperty<SmartClickingMode> smartClickingProperty = new EnumProperty<>("SmartClicking",
            SmartClickingMode.NONE, SmartClickingMode.values(), SmartClickingMode.class);

    private double cps;
    private int delay;
    private double partialDelays;

    public KillAuraHack() {
        super("KillAura", HackCategory.COMBAT);
        addProperty(maxCPSProperty);
        addProperty(minCPSProperty);
        addProperty(stdProperty);
        addProperty(meanProperty);
        addProperty(cpsModeProperty);
        addProperty(failRateProperty);
        addProperty(smartClickingProperty);

        aimbot = new AimbotRotationSpoof(this, addPropertyGroup("Rotations"));
        ClientBase.INSTANCE.getRotationEngine().add(aimbot);
        ClientBase.INSTANCE.getClickEngine().add(this);
    }

    @Override
    protected void onEnable() {
        cps = MathHelper.lerp(Math.random(), minCPSProperty.getValue(), maxCPSProperty.getValue());
        ClientBase.INSTANCE.getEventDispatcher().subscribe(UpdateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().subscribe(TargetPickListener.class, this, this::getPriority);
    }

    @Override
    protected void onDisable() {
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(UpdateListener.class, this);
        ClientBase.INSTANCE.getEventDispatcher().unsubscribeInternal(TargetPickListener.class, this);
    }

    @Override
    public boolean canClick() {
        return aimbot.hasTarget();
    }

    @Override
    public int getPriority() {
        return aimbot.getPriority();
    }

    @Override
    public void click(ClickCallback callback) {
        if (delay > 0)
            return;


        HitResult crosshairTarget = mc.crosshairTarget;
        if (!(crosshairTarget instanceof EntityHitResult) && Math.random() * 100 > failRateProperty.getValue()) {
            return;
        }

        if (smartClickingProperty.getValue().click.test(aimbot)) {
            if (cpsModeProperty.getValue() == CPSMode.GAUSSIAN) {
                if (ThreadLocalRandom.current().nextGaussian() > 1) {
                    float randomValue = MathUtil.boxMuellerDistribution(ThreadLocalRandom.current(), 1, 20, meanProperty.getValue(), stdProperty.getValue());
                    cps = randomValue;
                }
                double delay = 20.0 / cps;
                this.delay = (int) Math.floor(delay + partialDelays);
                partialDelays += delay - this.delay;
            } else {
                double delay = 20.0 / cps;
                this.delay = (int) Math.floor(delay + partialDelays);
                partialDelays += delay - this.delay;
            }
            callback.left();
        }
    }

    @Override
    public void onUpdate() {
        if (cpsModeProperty.getValue() == CPSMode.CUSTOM) {
            if (ThreadLocalRandom.current().nextGaussian() > 0) {
                double newCps = MathHelper.lerp(Math.random(), minCPSProperty.getValue(), maxCPSProperty.getValue());
                if (Math.abs(newCps - cps) >= 0.5) {
                    partialDelays = 0;
                }
                cps = newCps;
            }
        }
        if (delay > 0)
            delay--;
    }

    @Override
    public void onPickTarget(TargetPickEvent event) {
        if (aimbot.hasTarget())
            event.setTarget(aimbot.getPrimaryTarget());
    }

    enum CPSMode {
        CUSTOM("Custom"), GAUSSIAN("Gaussian");

        private final String name;

        CPSMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    enum SmartClickingMode {
        NONE("None", bot -> true),
        DELAY("Delay", bot -> mc.player.getAttackCooldownProgress(0) >= 1);

        private final String name;
        private final Predicate<AimbotRotationSpoof> click;

        SmartClickingMode(String name, Predicate<AimbotRotationSpoof> click) {
            this.name = name;
            this.click = click;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
