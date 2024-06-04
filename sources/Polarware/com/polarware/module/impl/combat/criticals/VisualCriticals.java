package com.polarware.module.impl.combat.criticals;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.module.impl.combat.KillAuraModule;
import com.polarware.value.Mode;
import net.minecraft.util.EnumParticleTypes;

public final class VisualCriticals extends Mode<CriticalsModule> {

    public VisualCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(mc.thePlayer.isSwingInProgress && KillAuraModule.target != null) {
            this.mc.effectRenderer.emitParticleAtEntity(KillAuraModule.target, EnumParticleTypes.CRIT);
        }
    };
}
