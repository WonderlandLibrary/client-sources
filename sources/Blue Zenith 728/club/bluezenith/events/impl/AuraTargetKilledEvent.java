package club.bluezenith.events.impl;

import club.bluezenith.events.Event;
import net.minecraft.entity.EntityLivingBase;

public class AuraTargetKilledEvent extends Event {
    public final EntityLivingBase target;

    public AuraTargetKilledEvent(EntityLivingBase target) {
        this.target = target;
    }
}
