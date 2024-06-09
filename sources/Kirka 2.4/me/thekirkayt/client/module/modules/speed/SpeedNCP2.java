/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Bunny Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Ground (Latest NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Ground (Old NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Long Bunny Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Low Hop (Latest NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Low Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.Speed;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Module.Mod
public class SpeedNCP2
extends Module {
    public Ground (Latest NCP) Ground (Latest NCP) = new Ground (Latest NCP)("Ground (Latest NCP)", true, this);
    public Ground (Old NCP) Ground (Old NCP) = new Ground (Old NCP)("Ground (Old NCP)", false, this);
    private Low Hop (Latest NCP) Low Hop (Latest NCP) = new Low Hop (Latest NCP)("Low Hop (Latest NCP)", false, this);
    private Low Hop (Old NCP) Low Hop (Old NCP) = new Low Hop (Old NCP)("Low Hop (Old NCP)", false, this);
    private Bunny Hop (Old NCP) Bunny Hop (Old NCP) = new Bunny Hop (Old NCP)("Bunny Hop (Old NCP)", false, this);
    public Long Bunny Hop (Old NCP) Long Bunny Hop (Old NCP) = new Long Bunny Hop (Old NCP)("Long Bunny Hop (Old NCP)", false, this);
    private Speed Speed = new Speed("Speed", false, this);
    @Option.Op(min=0.2, max=10.0, increment=0.05)
    public double speed = 0.5;

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.Ground (Latest NCP));
        OptionManager.getOptionList().add(this.Ground (Old NCP));
        OptionManager.getOptionList().add(this.Long Bunny Hop (Old NCP));
        OptionManager.getOptionList().add(this.Low Hop (Latest NCP));
        OptionManager.getOptionList().add(this.Low Hop (Old NCP));
        OptionManager.getOptionList().add(this.Bunny Hop (Old NCP));
        OptionManager.getOptionList().add(this.Speed);
        this.updateSuffix();
        super.preInitialize();
    }

    @Override
    public void enable() {
        this.Ground (Latest NCP).enable();
        this.Ground (Old NCP).enable();
        this.Low Hop (Latest NCP).enable();
        this.Low Hop (Old NCP).enable();
        this.Bunny Hop (Old NCP).enable();
        this.Speed.enable();
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        this.Ground (Latest NCP).onMove(event);
        this.Ground (Old NCP).onMove(event);
        this.Long Bunny Hop (Old NCP).onMove(event);
        this.Low Hop (Latest NCP).onMove(event);
        this.Low Hop (Old NCP).onMove(event);
        this.Bunny Hop (Old NCP).onMove(event);
        this.Speed.onMove(event);
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.Ground (Latest NCP).onUpdate(event);
        this.Ground (Old NCP).onUpdate(event);
        this.Long Bunny Hop (Old NCP).onUpdate(event);
        this.Low Hop (Latest NCP).onUpdate(event);
        this.Low Hop (Old NCP).onUpdate(event);
        this.Bunny Hop (Old NCP).onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.Ground (Latest NCP).getValue()).booleanValue()) {
            this.setSuffix("Ground (Latest NCP)");
        } else if (((Boolean)this.Ground (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Ground (Old NCP)");
        } else if (((Boolean)this.Long Bunny Hop (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Long Bunny Hop (Old NCP)");
        } else if (((Boolean)this.Low Hop (Latest NCP).getValue()).booleanValue()) {
            this.setSuffix("Low Hop (Latest NCP)");
        } else if (((Boolean)this.Speed.getValue()).booleanValue()) {
            this.setSuffix("Speed");
        } else if (((Boolean)this.Bunny Hop (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Bunny Hop (Old NCP)");
        } else {
            this.setSuffix("Low Hop (Old NCP)");
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

