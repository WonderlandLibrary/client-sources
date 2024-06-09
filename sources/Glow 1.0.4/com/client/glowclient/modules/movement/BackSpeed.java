package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class BackSpeed extends ModuleContainer
{
    @Override
    public boolean A() {
        return false;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!Wrapper.mc.player.isElytraFlying() && Wrapper.mc.gameSettings.keyBindBack.isKeyDown()) {
            if (Wrapper.mc.player.moveForward > 0.0f && !Wrapper.mc.player.collidedHorizontally) {
                Wrapper.mc.player.setSprinting(true);
            }
            if (Wrapper.mc.player.onGround) {
                final EntityPlayerSP player = Wrapper.mc.player;
                player.motionX *= 1.192;
                final EntityPlayerSP player2 = Wrapper.mc.player;
                player2.motionZ *= 1.192;
            }
            final double sqrt = Math.sqrt(Math.pow(Wrapper.mc.player.motionX, 2.0) + Math.pow(Wrapper.mc.player.motionZ, 2.0));
            final double n = 0.6600000262260437;
            if (sqrt > n) {
                Wrapper.mc.player.motionX = Wrapper.mc.player.motionX / sqrt * n;
                Wrapper.mc.player.motionZ = Wrapper.mc.player.motionZ / sqrt * n;
            }
        }
    }
    
    public BackSpeed() {
        super(Category.MOVEMENT, "BackSpeed", false, -1, "Go sprint speed backwards");
    }
}
