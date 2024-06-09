/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpNew.Hypixel;
import me.thekirkayt.client.module.modules.longjump.LongJumpNew.NCP;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@Module.Mod(displayName="LongJumpNew")
public class LongJumpNew
extends Module {
    public static final String Speed = null;
    public NCP ncp = new NCP("NCP", true, this);
    public Hypixel hypixel = new Hypixel("Hypixel", false, this);

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.ncp);
        OptionManager.getOptionList().add(this.hypixel);
        this.updateSuffix();
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.ncp.enable();
        this.hypixel.enable();
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        this.ncp.onMove(event);
        this.hypixel.onMove(event);
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.ncp.onUpdate(event);
        this.hypixel.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.ncp.getValue()).booleanValue()) {
            this.setSuffix("NCP");
        } else if (((Boolean)this.hypixel.getValue()).booleanValue()) {
            this.setSuffix("Hypixel");
        } else {
            this.setSuffix("None");
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

