package com.client.glowclient.modules.server;

import com.client.glowclient.events.*;
import net.minecraft.entity.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class MappingBot extends ModuleContainer
{
    public static nB direction;
    public static nB mode;
    public static NumberValue loopVal;
    
    static {
        MappingBot.direction = ValueFactory.M("MappingBot", "Direction", "Direction of MappingBot", "N", "N", "S", "E", "W");
        MappingBot.mode = ValueFactory.M("MappingBot", "Mode", "Mode of MappingBot", "UnderBedrock", "UnderBedrock");
        final String s = "MappingBot";
        final String s2 = "LoopVal";
        final String s3 = "Looped Value";
        final double n = 0.0;
        MappingBot.loopVal = ValueFactory.M(s, s2, s3, n, n, n, 0.0);
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        final double n = 128.0;
        final double n2 = MappingBot.loopVal.k() * n;
        if (MappingBot.direction.e().equals("N")) {
            MappingBot.B.player.rotationYaw = 180.0f;
            if (MappingBot.B.player.posZ < -n - n2) {
                MappingBot.direction.M("W");
            }
        }
        if (MappingBot.direction.e().equals("W")) {
            MappingBot.B.player.rotationYaw = 90.0f;
            if (MappingBot.B.player.posX < -n - n2) {
                MappingBot.direction.M("S");
            }
        }
        if (MappingBot.direction.e().equals("S")) {
            MappingBot.B.player.rotationYaw = 0.0f;
            if (MappingBot.B.player.posZ > n + n2) {
                MappingBot.direction.M("E");
            }
        }
        if (MappingBot.direction.e().equals("E")) {
            MappingBot.B.player.rotationYaw = -90.0f;
            if (MappingBot.B.player.posX > n + n2) {
                MappingBot.loopVal.M(MappingBot.loopVal.k() + 1.0);
                qd.D(new StringBuilder().insert(0, "This loop should take an estimated: ").append(String.format("%.3f", 128.0 * (MappingBot.loopVal.k() + 1.0) * 2.0 / u.M((Entity)MappingBot.B.player) * 4.0 / 60.0)).append(" minutes.").toString());
                MappingBot.direction.M("N");
            }
        }
        if (MappingBot.mode.e().equals("UnderBedrock") && Wrapper.mc.player.getRidingEntity() != null) {
            if (!this.G.hasBeenSet()) {
                this.G.reset();
            }
            if (this.G.delay(0.0) && Wrapper.mc.player.posY < -3.0) {
                Wrapper.mc.player.getRidingEntity().motionY = 0.7;
            }
            if (this.G.delay(50.0)) {
                Wrapper.mc.player.getRidingEntity().motionY = -0.033;
            }
            if (this.G.delay(1250.0)) {
                this.G.reset();
            }
            if (Wrapper.mc.player.getRidingEntity().posY < -8.0) {
                Wrapper.mc.player.getRidingEntity().motionY = 2.0;
            }
        }
    }
    
    @Override
    public void E() {
    }
    
    @Override
    public void D() {
    }
    
    public MappingBot() {
        super(Category.SERVER, "MappingBot", false, -1, "Recommended to start north");
    }
}
