package com.client.glowclient.modules.movement;

import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.entity.passive.*;
import com.client.glowclient.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class EntityControl extends ModuleContainer
{
    public double b;
    
    @Override
    public void E() {
        HookTranslator.v5 = false;
    }
    
    @Override
    public void D() {
        HookTranslator.v5 = true;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v5 = true;
        if (Wrapper.mc.player.getRidingEntity() != null && Ob.M() instanceof EntityPig) {
            if (Ob.M() instanceof EntityPig && Wrapper.mc.player.getRidingEntity().onGround && Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.getRidingEntity().motionY = 0.6;
            }
            EntityControl entityControl;
            if (Wrapper.mc.player.getRidingEntity().onGround) {
                entityControl = this;
                this.b = 0.1;
            }
            else {
                entityControl = this;
                this.b = 0.015;
            }
            u.M(entityControl.b, Wrapper.mc.player.getRidingEntity());
        }
    }
    
    public EntityControl() {
        final double b = 0.08;
        super(Category.MOVEMENT, "EntityControl", false, -1, "Allows the control of pigs, llamas, and horses");
        this.b = b;
    }
}
