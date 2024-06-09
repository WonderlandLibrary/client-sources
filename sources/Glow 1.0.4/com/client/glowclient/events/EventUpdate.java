package com.client.glowclient.events;

import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.*;

public class EventUpdate extends LivingEvent
{
    public EventUpdate(final EntityLivingBase entityLivingBase) {
        super(entityLivingBase);
    }
}
