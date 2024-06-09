/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpApple2.modes.Guardian;
import me.thekirkayt.client.module.modules.longjump.LongJumpApple2.modes.Normal;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod(category=Module.Category.motion)
public class LongJumpApple2
extends Module {
    private /* synthetic */ double moveSpeed;
    private /* synthetic */ double lastDist;
    public static /* synthetic */ int stage;
    @Option.Op(increment=1.0, min=4.0, max=24.0)
    private /* synthetic */ double boost;
    private /* synthetic */ Normal normal;
    private /* synthetic */ Guardian guardian;

    public /* synthetic */ LongJumpApple2() {
        this.boost = 4.0;
        this.normal = new Normal("Normal", true, this);
        this.guardian = new Guardian("Guardian", false, this);
    }

    public /* synthetic */ void init() {
        OptionManager.getOptionList().add(this.normal);
        OptionManager.getOptionList().add(this.guardian);
        this.updateSuffix();
        super.init();
    }

    @Override
    public /* synthetic */ void enable() {
        stage = 0;
        super.enable();
    }

    @EventTarget
    public /* synthetic */ void onTick(TickEvent event) {
        this.updateSuffix();
    }

    @EventTarget
    private /* synthetic */ void onMove(MoveEvent event) {
        this.normal.onMove(event);
    }

    @EventTarget
    private /* synthetic */ void onUpdate(UpdateEvent event) {
        this.normal.onUpdate(event);
        this.guardian.onUpdate(event);
    }

    public /* synthetic */ void updateSuffix() {
        if (((Boolean)this.normal.getValue()).booleanValue()) {
            this.setSuffix("Normal");
        } else if (((Boolean)this.guardian.getValue()).booleanValue()) {
            this.setSuffix("Guardian");
        }
    }
}

