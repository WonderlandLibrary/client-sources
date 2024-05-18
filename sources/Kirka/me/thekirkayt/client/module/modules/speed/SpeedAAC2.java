/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.AAC Bunny Hop;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.AAC Ground Strafe;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.AAC Strafe Hop;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.Vanilla;
import me.thekirkayt.client.module.modules.speed.SpeedAAC2.YPort (Old AAC);
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Module.Mod
public class SpeedAAC2
extends Module {
    public AAC Strafe Hop latest = new AAC Strafe Hop("AAC Strafe Hop", true, this);
    public YPort (Old AAC) oldNCP = new AAC Ground Strafe("AAC Ground Strafe", false, this);
    public AAC Bunny Hop bhop = new AAC Bunny Hop("AAC Bunny Hop", false, this);
    private Vanilla vanilla = new Vanilla("Vanilla", false, this);
    private AAC Ground Strafe yport = new YPort (Old AAC)("YPort (Old AAC)", false, this);
    @Option.Op(min=0.2, max=10.0, increment=0.05, name="Vanilla Speed")
    public double speed = 0.5;

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.latest);
        OptionManager.getOptionList().add(this.bhop);
        OptionManager.getOptionList().add(this.oldNCP);
        OptionManager.getOptionList().add(this.vanilla);
        OptionManager.getOptionList().add(this.yport);
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.latest.enable();
        this.oldNCP.enable();
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        this.latest.onMove(event);
        this.vanilla.onMove(event);
        ((AAC Ground Strafe)((Object)this.oldNCP)).onMove(event);
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.latest.onUpdate(event);
        this.oldNCP.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.bhop.onTick(event);
        ((YPort (Old AAC))((Object)this.yport)).onTick(event);
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.latest.getValue()).booleanValue()) {
            this.setSuffix("AAC Strafe Hop");
        } else if (((Boolean)this.bhop.getValue()).booleanValue()) {
            this.setSuffix("AAC Bunny Hop");
        } else if (((Boolean)this.vanilla.getValue()).booleanValue()) {
            this.setSuffix("Vanilla");
        } else if (((Boolean)this.oldNCP.getValue()).booleanValue()) {
            this.setSuffix("AAC Ground Strafe");
        } else {
            this.setSuffix("YPort (Old AAC)");
        }
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            int amplifier = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
        return baseSpeed;
    }
}

