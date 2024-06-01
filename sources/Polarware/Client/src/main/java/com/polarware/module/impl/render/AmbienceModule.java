package com.polarware.module.impl.render;


import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.network.PacketReceiveEvent;
import com.polarware.event.impl.render.Render3DEvent;
import com.polarware.value.impl.ColorValue;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.NumberValue;
import com.polarware.value.impl.SubMode;
import lombok.Getter;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.*;

@Getter
@ModuleInfo(name = "module.render.ambience.name", description = "module.render.ambience.description", category = Category.RENDER)
public final class AmbienceModule extends Module {

    private final NumberValue time = new NumberValue("Time", this, 0, 0, 22999, 1);
    private final NumberValue speed = new NumberValue("Time Speed", this, 0, 0, 20, 1);



    @Override
    protected void onDisable() {
        mc.theWorld.setRainStrength(0);
        mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
        mc.theWorld.getWorldInfo().setRainTime(0);
        mc.theWorld.getWorldInfo().setThunderTime(0);
        mc.theWorld.getWorldInfo().setRaining(false);
        mc.theWorld.getWorldInfo().setThundering(false);
    }

    @EventLink()
    public final Listener<Render3DEvent> onRender3D = event -> {

        mc.theWorld.setWorldTime((time.getValue().intValue() + (System.currentTimeMillis() * speed.getValue().intValue())));
    };

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.ticksExisted % 20 == 0) {
            mc.theWorld.setRainStrength(0);
            mc.theWorld.getWorldInfo().setCleanWeatherTime(Integer.MAX_VALUE);
            mc.theWorld.getWorldInfo().setRainTime(0);
            mc.theWorld.getWorldInfo().setThunderTime(0);
            mc.theWorld.getWorldInfo().setRaining(false);
            mc.theWorld.getWorldInfo().setThundering(false);
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {

        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }

        else if (event.getPacket() instanceof S2BPacketChangeGameState) {
            S2BPacketChangeGameState s2b = (S2BPacketChangeGameState) event.getPacket();

            if (s2b.getGameState() == 1 || s2b.getGameState() == 2) {
                event.setCancelled(true);
            }
        }
    };

    public float getFloatTemperature(BlockPos blockPos, BiomeGenBase biomeGenBase) {
        if (this.isEnabled()) {

        }

        return biomeGenBase.getFloatTemperature(blockPos);
    }

    public boolean skipRainParticles() {
        return false;
    }
}