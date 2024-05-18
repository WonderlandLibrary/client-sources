/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.AAC;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.AAC2;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.EnvyLongJump;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.Rewi;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod
public class LongJumpBypass2
extends Module {
    public static int stage;
    public AAC2 aac2 = new AAC2("AAC 3.0.5 | AAC 3.0.4", false, this);
    public EnvyLongJump envylongjump = new EnvyLongJump("EnvyLongJump", true, this);
    public AAC aac = new AAC("AAC 3.1.0 | 3.1.5", false, this);
    public Rewi rewinside = new Rewi("Rewi", false, this);

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.aac2);
        OptionManager.getOptionList().add(this.envylongjump);
        OptionManager.getOptionList().add(this.aac);
        OptionManager.getOptionList().add(this.rewinside);
    }

    @Override
    public void enable() {
        this.envylongjump.enable();
        this.aac2.enable();
        super.enable();
    }

    @EventTarget
    public void tick(TickEvent event) {
        this.updateSuffix();
        this.rewinside.onTick(event);
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        this.envylongjump.onUpdate(event);
        this.aac2.onUpdate(event);
    }

    @EventTarget
    public void onMove(MoveEvent event) {
        this.envylongjump.onMove(event);
        this.aac2.onMove(event);
        this.aac.onMove(event);
    }

    @Override
    public void disable() {
        this.envylongjump.disable();
        this.aac2.disable();
        this.aac.disable();
        this.rewinside.disable();
        super.disable();
    }

    private void updateSuffix() {
        if (((Boolean)this.aac2.getValue()).booleanValue()) {
            this.setSuffix("AAC (3.0.4) | (3.0.5)");
        } else if (((Boolean)this.envylongjump.getValue()).booleanValue()) {
            this.setSuffix("EnvyLongJump");
        } else if (((Boolean)this.aac.getValue()).booleanValue()) {
            this.setSuffix("AAC (3.1.0) | (3.1.5)");
        } else if (((Boolean)this.rewinside.getValue()).booleanValue()) {
            this.setSuffix("Rewi");
        }
    }
}

