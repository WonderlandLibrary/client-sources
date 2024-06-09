package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import net.minecraftforge.event.entity.living.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class HorseMod extends ModuleContainer
{
    public static final NumberValue jump;
    public static final NumberValue speed;
    public static BooleanValue jumpPower;
    
    public HorseMod() {
        super(Category.MOVEMENT, "HorseMod", false, -1, "Changes stats or jump power of a horse");
    }
    
    static {
        jump = ValueFactory.M("HorseMod", "Jump", "Horse Jump Attribute", 1.0, 0.01, 0.0, 1.0);
        speed = ValueFactory.M("HorseMod", "Speed", "Horse Speed Attribute", 0.3375, 0.001, 0.0, 0.3375);
        HorseMod.jumpPower = ValueFactory.M("HorseMod", "JumpPower", "Always jump with max power", true);
    }
    
    @Override
    public String M() {
        return new StringBuilder().insert(0, String.format("J:%.1f,", HorseMod.jump.k())).append(String.format("S:%.1f", HorseMod.speed.k())).toString();
    }
    
    @SubscribeEvent
    public void M(final LivingEvent$LivingUpdateEvent livingEvent$LivingUpdateEvent) {
        try {
            if (HorseMod.jumpPower.M()) {
                Wrapper.mc.player.horseJumpPower = 1.0f;
            }
            if (EntityUtils.m(livingEvent$LivingUpdateEvent.getEntity()) && (Ob.M() instanceof AbstractHorse || Wrapper.mc.player.getRidingEntity() instanceof EntityDonkey || Wrapper.mc.player.getRidingEntity() instanceof EntityMule)) {
                final AbstractHorse abstractHorse = (AbstractHorse)Ob.M();
                final IAttribute jump_STRENGTH = AbstractHorse.JUMP_STRENGTH;
                final IAttribute movement_SPEED = SharedMonsterAttributes.MOVEMENT_SPEED;
                ((EntityLivingBase)Ob.M()).getEntityAttribute(jump_STRENGTH).setBaseValue(HorseMod.jump.k());
                ((EntityLivingBase)Ob.M()).getEntityAttribute(movement_SPEED).setBaseValue(HorseMod.speed.k());
            }
        }
        catch (Exception ex) {}
    }
}
