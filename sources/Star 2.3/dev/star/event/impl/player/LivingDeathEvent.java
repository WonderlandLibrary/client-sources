package dev.star.event.impl.player;

import dev.star.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

@Getter
@AllArgsConstructor
public class LivingDeathEvent extends Event {

    private final EntityLivingBase entity;
    private final DamageSource source;

}
