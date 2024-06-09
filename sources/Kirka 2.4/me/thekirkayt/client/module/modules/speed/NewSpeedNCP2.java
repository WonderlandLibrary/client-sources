/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Boost LongJump;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Bunny Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Ground (Latest NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Ground (Old NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Long Bunny Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.LongJump;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Low Hop (Latest NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Low Hop (Old NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Mini YPort (Latest NCP);
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.Speed;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.YPort (Latest NCP);
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
public class NewSpeedNCP2
extends Module {
    public Ground (Latest NCP) Ground (Latest NCP) = new Ground (Latest NCP)("Ground (Latest NCP)", true, this);
    public Ground (Old NCP) Ground (Old NCP) = new Ground (Old NCP)("Ground (Old NCP)", false, this);
    private Low Hop (Latest NCP) Low Hop (Latest NCP) = new Low Hop (Latest NCP)("Low Hop (Latest NCP)", false, this);
    private Low Hop (Old NCP) Low Hop (Old NCP) = new Low Hop (Old NCP)("Low Hop (Old NCP)", false, this);
    private Bunny Hop (Old NCP) Bunny Hop (Old NCP) = new Bunny Hop (Old NCP)("Bunny Hop (Old NCP)", false, this);
    private Long Bunny Hop (Old NCP) Long Bunny Hop (Old NCP) = new Long Bunny Hop (Old NCP)("Long Bunny Hop (Old NCP)", false, this);
    private Mini YPort (Latest NCP) Mini YPort (Latest NCP) = new Mini YPort (Latest NCP)("Mini YPort (Latest NCP)", false, this);
    private YPort (Latest NCP) YPort (Latest NCP) = new YPort (Latest NCP)("YPort (Latest NCP)", false, this);
    private Boost LongJump Boost LongJump = new Boost LongJump("Boost LongJump", false, this);
    public LongJump LongJump = new LongJump("LongJump", false, this);
    private Speed Speed = new Speed("Speed", false, this);
    @Option.Op(min=0.2, max=10.0, increment=0.05)
    public double speed = 0.5;
    @Option.Op(min=2.0, max=4.5, increment=0.25)
    public static double boost;

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.Ground (Latest NCP));
        OptionManager.getOptionList().add(this.Ground (Old NCP));
        OptionManager.getOptionList().add(this.Low Hop (Latest NCP));
        OptionManager.getOptionList().add(this.Low Hop (Old NCP));
        OptionManager.getOptionList().add(this.Bunny Hop (Old NCP));
        OptionManager.getOptionList().add(this.Long Bunny Hop (Old NCP));
        OptionManager.getOptionList().add(this.LongJump);
        OptionManager.getOptionList().add(this.Mini YPort (Latest NCP));
        OptionManager.getOptionList().add(this.YPort (Latest NCP));
        OptionManager.getOptionList().add(this.Boost LongJump);
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
        this.Long Bunny Hop (Old NCP).enable();
        this.Mini YPort (Latest NCP).enable();
        this.YPort (Latest NCP).enable();
        this.Boost LongJump.enable();
        this.Speed.enable();
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent moveEvent) {
        this.Ground (Latest NCP).onMove(moveEvent);
        this.Ground (Old NCP).onMove(moveEvent);
        this.Low Hop (Latest NCP).onMove(moveEvent);
        this.Low Hop (Old NCP).onMove(moveEvent);
        this.Bunny Hop (Old NCP).onMove(moveEvent);
        this.Long Bunny Hop (Old NCP).onMove(moveEvent);
        this.LongJump.onMove(moveEvent);
        this.Mini YPort (Latest NCP).onMove(moveEvent);
        this.YPort (Latest NCP).onMove(moveEvent);
        this.Boost LongJump.onMove(moveEvent);
        this.Speed.onMove(moveEvent);
    }

    @EventTarget
    private void onUpdate(UpdateEvent updateEvent) {
        this.Ground (Latest NCP).onUpdate(updateEvent);
        this.Ground (Old NCP).onUpdate(updateEvent);
        this.Low Hop (Latest NCP).onUpdate(updateEvent);
        this.Low Hop (Old NCP).onUpdate(updateEvent);
        this.Bunny Hop (Old NCP).onUpdate(updateEvent);
        this.Long Bunny Hop (Old NCP).onUpdate(updateEvent);
        this.LongJump.onUpdate(updateEvent);
        this.Mini YPort (Latest NCP).onUpdate(updateEvent);
        this.YPort (Latest NCP).onUpdate(updateEvent);
        this.Boost LongJump.onUpdate(updateEvent);
    }

    @EventTarget
    private void onTick(TickEvent tickEvent) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.Ground (Latest NCP).getValue()).booleanValue()) {
            this.setSuffix("Ground (Latest NCP)");
        } else if (((Boolean)this.Ground (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Ground (Old NCP)");
        } else if (((Boolean)this.Low Hop (Latest NCP).getValue()).booleanValue()) {
            this.setSuffix("Low Hop (Latest NCP)");
        } else if (((Boolean)this.Low Hop (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Low Hop (Old NCP)");
        } else if (((Boolean)this.Bunny Hop (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Bunny Hop (Old NCP)");
        } else if (((Boolean)this.Long Bunny Hop (Old NCP).getValue()).booleanValue()) {
            this.setSuffix("Long Bunny Hop (Old NCP)");
        } else if (((Boolean)this.LongJump.getValue()).booleanValue()) {
            this.setSuffix("LongJump");
        } else if (((Boolean)this.Mini YPort (Latest NCP).getValue()).booleanValue()) {
            this.setSuffix("Mini YPort (Latest NCP)");
        } else if (((Boolean)this.Speed.getValue()).booleanValue()) {
            this.setSuffix("Speed");
        } else if (((Boolean)this.Boost LongJump.getValue()).booleanValue()) {
            this.setSuffix("Boost LongJump");
        } else {
            this.setSuffix("YPort (Latest NCP)");
        }
    }

    public static double getBaseMoveSpeed() {
        double d = 0.2873;
        if (ClientUtils.player().isPotionActive(Potion.moveSpeed)) {
            int n = ClientUtils.player().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            d *= 1.0 + 0.2 * (double)(n + 1);
        }
        return d;
    }
}

