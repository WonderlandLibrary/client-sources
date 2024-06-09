package com.client.glowclient.modules.render;

import com.client.glowclient.modules.*;
import net.minecraft.world.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import net.minecraft.network.play.server.*;

public class NoWeather extends ModuleContainer
{
    private float A;
    private boolean B;
    private float b;
    
    public NoWeather() {
        final float b = 0.0f;
        final float a = 0.0f;
        final boolean b2 = false;
        super(Category.RENDER, "NoWeather", false, -1, "Removes weather");
        this.B = b2;
        this.A = a;
        this.b = b;
    }
    
    private void M(final boolean b, final float n, final float n2) {
        this.B = b;
        this.M(n, n2);
    }
    
    private void M(final World world) {
        if (world != null) {
            this.M(world.getWorldInfo().isRaining(), world.rainingStrength, world.prevRainingStrength);
            return;
        }
        final boolean b = false;
        final float n = 1.0f;
        this.M(b, n, n);
    }
    
    @Override
    public void E() {
        this.a();
    }
    
    @Override
    public void D() {
        this.M((World)Wrapper.mc.world);
    }
    
    public void a() {
        if (Wrapper.mc.world != null) {
            Wrapper.mc.world.getWorldInfo().setRaining(this.B);
            Wrapper.mc.world.rainingStrength = this.A;
            Wrapper.mc.world.prevRainingStrength = this.b;
        }
    }
    
    @SubscribeEvent
    public void M(final TickEvent$ClientTickEvent tickEvent$ClientTickEvent) {
        this.d();
    }
    
    private void M(final float a, final float b) {
        this.A = a;
        this.b = b;
    }
    
    private void d() {
        if (Wrapper.mc.world != null) {
            Wrapper.mc.world.getWorldInfo().setRaining(false);
            Wrapper.mc.world.setRainStrength(0.0f);
        }
    }
    
    @SubscribeEvent
    public void M(final EventWorld eventWorld) {
        this.M(eventWorld.getWorld());
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        if (eventServerPacket.getPacket() instanceof SPacketChangeGameState) {
            final int gameState = ((SPacketChangeGameState)eventServerPacket.getPacket()).getGameState();
            final float value = ((SPacketChangeGameState)eventServerPacket.getPacket()).getValue();
            final boolean b;
            switch (gameState) {
                case 1: {
                    b = true;
                    while (false) {}
                    final boolean b2 = true;
                    final float n = 0.0f;
                    this.M(b2, n, n);
                    break;
                }
                case 2: {
                    final boolean b3 = false;
                    final float n2 = 1.0f;
                    this.M(b3, n2, n2);
                    break;
                }
                case 7: {
                    final float n3 = value;
                    this.M(n3, n3);
                    break;
                }
            }
            if (b) {
                final boolean canceled = true;
                this.d();
                eventServerPacket.setCanceled(canceled);
            }
        }
    }
    
    @Override
    public boolean A() {
        return false;
    }
}
