package com.client.glowclient.modules.movement;

import net.minecraftforge.event.entity.living.*;
import com.client.glowclient.utils.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class HighJump extends ModuleContainer
{
    @SubscribeEvent
    public void M(final LivingEvent$LivingUpdateEvent livingEvent$LivingUpdateEvent) {
        MinecraftHelper.getPlayer().addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 25, 4));
    }
    
    @Override
    public void E() {
        MinecraftHelper.getPlayer().removePotionEffect(MobEffects.JUMP_BOOST);
    }
    
    public HighJump() {
        super(Category.MOVEMENT, "HighJump", false, -1, "Gives jump boost effect");
    }
}
