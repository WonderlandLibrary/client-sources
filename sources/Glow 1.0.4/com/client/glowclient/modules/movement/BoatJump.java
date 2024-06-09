package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;

public class BoatJump extends ModuleContainer
{
    public static final NumberValue power;
    
    public BoatJump() {
        super(Category.MOVEMENT, "BoatJump", false, -1, "Jump with boats");
    }
    
    @SubscribeEvent
    public void M(final TickEvent$ClientTickEvent tickEvent$ClientTickEvent) {
        if (Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.player.getRidingEntity() instanceof EntityBoat && Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && (Wrapper.mc.player.getRidingEntity().onGround || Wrapper.mc.player.getRidingEntity().isInWater())) {
            Wrapper.mc.player.getRidingEntity().motionY = BoatJump.power.k();
        }
    }
    
    static {
        power = ValueFactory.M("BoatJump", "Power", "jump strength", 0.35, 0.05, 0.0, 1.0);
    }
}
