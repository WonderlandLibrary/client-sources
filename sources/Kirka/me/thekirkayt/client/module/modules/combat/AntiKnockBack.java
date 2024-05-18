/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.combat.AntiKnockBack.AAC;
import me.thekirkayt.client.module.modules.combat.AntiKnockBack.NCP;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketReceiveEvent;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;

@Module.Mod
public class AntiKnockBack
extends Module {
    public NCP ncp = new NCP("NCP", true, this);
    public AAC aac = new AAC("AAC", false, this);

    @Override
    public void preInitialize() {
        OptionManager.getOptionList().add(this.aac);
        OptionManager.getOptionList().add(this.ncp);
        this.updateSuffix();
        super.preInitialize();
    }

    @EventTarget
    private void onPacketReceive(PacketReceiveEvent event) {
        this.ncp.onPacketReceiveEvent(event);
    }

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        this.aac.onUpdate(event);
    }

    @EventTarget
    private void onTick(TickEvent event) {
        this.updateSuffix();
    }

    private void updateSuffix() {
        if (((Boolean)this.ncp.getValue()).booleanValue()) {
            this.setSuffix("NCP");
        } else {
            this.setSuffix("AAC");
        }
    }
}

