package com.client.glowclient.modules.movement;

import com.client.glowclient.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class Spider extends ModuleContainer
{
    public static NumberValue speed;
    public static nB mode;
    
    @Override
    public String M() {
        return Spider.mode.e();
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.player != null) {
            if (Spider.mode.e().equals("Entity") && Wrapper.mc.player.getRidingEntity() != null && Wrapper.mc.player.getRidingEntity().collidedHorizontally) {
                Wrapper.mc.player.getRidingEntity().motionY = Spider.speed.k();
            }
            if (Spider.mode.e().equals("Player") && Wrapper.mc.player.collidedHorizontally) {
                Wrapper.mc.player.motionY = Spider.speed.k();
            }
        }
    }
    
    static {
        Spider.mode = ValueFactory.M("Spider", "Mode", "Mode of Spider", "Player", "Player", "Entity");
        final String s = "Spider";
        final String s2 = "Speed";
        final String s3 = "Speed of Spider";
        final double n = 0.05;
        final double n2 = 0.1;
        Spider.speed = ValueFactory.M(s, s2, s3, n2, n, n2, 1.0);
    }
    
    public Spider() {
        super(Category.MOVEMENT, "Spider", false, -1, "Go up walls");
    }
}
