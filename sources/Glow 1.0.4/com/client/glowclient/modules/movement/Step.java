package com.client.glowclient.modules.movement;

import net.minecraft.entity.player.*;
import com.client.glowclient.modules.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import com.google.common.collect.*;
import com.client.glowclient.*;
import java.util.*;

public class Step extends ModuleContainer
{
    public static final NumberValue height;
    private CPacketPlayer G;
    public static final float d = 0.6f;
    public static BooleanValue useTimer;
    public static BooleanValue entityStep;
    public static BooleanValue infinite;
    private final float b = 50.0f;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!Wrapper.mc.player.isElytraFlying()) {
            final EntityPlayer entityPlayer = (EntityPlayer)eventUpdate.getEntityLiving();
            final double n = 20.0 / me.M().M().M();
            if (entityPlayer.onGround) {
                Step step;
                if (!Step.infinite.M()) {
                    step = this;
                    entityPlayer.stepHeight = (float)Step.height.k();
                }
                else {
                    entityPlayer.stepHeight = 256.0f;
                    step = this;
                }
                if (step.k() && !ModuleManager.M("Timer").k() && ModuleManager.M("Timer") != null) {
                    this.M(50.0f);
                }
                else {
                    this.M(50.0f / (float)Timer.speed.k());
                }
                if (Timer.tPSSync.M() && ModuleManager.M("Timer").k()) {
                    this.M(50.0f * (float)n);
                }
            }
            else {
                entityPlayer.stepHeight = 0.6f;
            }
            if (Wrapper.mc.player.getRidingEntity() != null && Step.entityStep.M()) {
                if (!Step.infinite.M()) {
                    if (Wrapper.mc.player.getRidingEntity().onGround) {
                        Wrapper.mc.player.getRidingEntity().stepHeight = (float)Step.height.k();
                        return;
                    }
                    Wrapper.mc.player.getRidingEntity().stepHeight = 1.0f;
                }
                else {
                    if (Wrapper.mc.player.getRidingEntity().onGround) {
                        Wrapper.mc.player.getRidingEntity().stepHeight = 256.0f;
                        return;
                    }
                    Wrapper.mc.player.getRidingEntity().stepHeight = 1.0f;
                }
            }
        }
    }
    
    static {
        Step.useTimer = ValueFactory.M("Step", "UseTimer", "Uses Timer", true);
        Step.entityStep = ValueFactory.M("Step", "EntityStep", "Steps with rideable entities", true);
        Step.infinite = ValueFactory.M("Step", "Infinite", "Unlimited step height", false);
        final String s = "Step";
        final String s2 = "Height";
        final String s3 = "step height";
        final double n = 1.0;
        height = ValueFactory.M(s, s2, s3, n, n, 0.0, 5.0);
    }
    
    public Step() {
        final CPacketPlayer g = null;
        final float b = 50.0f;
        super(Category.MOVEMENT, "Step", false, -1, "Step up blocks without jumping");
        this.G = g;
    }
    
    public void M(final float tickLength) {
        try {
            Wrapper.mc.timer.tickLength = tickLength;
        }
        catch (Exception ex) {}
    }
    
    @Override
    public String M() {
        if (!Step.infinite.M()) {
            return String.format("%.0f", Step.height.k());
        }
        return "Infinite";
    }
    
    @Override
    public void E() {
        if (Wrapper.mc.player != null) {
            Wrapper.mc.player.stepHeight = 0.6f;
            this.M(50.0f);
            if (Wrapper.mc.player.getRidingEntity() != null && Step.entityStep.M()) {
                Wrapper.mc.player.getRidingEntity().stepHeight = 1.0f;
            }
        }
    }
    
    @SubscribeEvent
    public void M(final EventClientPacket eventClientPacket) {
        if ((eventClientPacket.getPacket() instanceof CPacketPlayer$Position || eventClientPacket.getPacket() instanceof CPacketPlayer$PositionRotation) && !Wrapper.mc.player.isElytraFlying()) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)eventClientPacket.getPacket();
            final double n;
            if (this.G != null && !pc.M((Packet)eventClientPacket.getPacket()) && (n = cPacketPlayer.getY(0.0) - this.G.getY(0.0)) > 0.6000000238418579 && n <= 1.2491870787) {
                final ArrayList<Object> arrayList = Lists.newArrayList();
                Step step = null;
                Label_0144: {
                    if (Wrapper.mc.player.onGround && !Wrapper.mc.player.isElytraFlying()) {
                        if (Step.useTimer.M()) {
                            step = this;
                            this.M(166.66666f);
                            break Label_0144;
                        }
                        this.M(50.0f);
                    }
                    step = this;
                }
                final double x = step.G.getX(0.0);
                final double y = this.G.getY(0.0);
                final double z = this.G.getZ(0.0);
                arrayList.add(new CPacketPlayer$Position(x, y + 0.4199999869, z, true));
                arrayList.add(new CPacketPlayer$Position(x, y + 0.7531999805, z, true));
                arrayList.add(new CPacketPlayer$Position(cPacketPlayer.getX(0.0), cPacketPlayer.getY(0.0), cPacketPlayer.getZ(0.0), cPacketPlayer.isOnGround()));
                Object iterator2;
                final Iterator<Object> iterator = (Iterator<Object>)(iterator2 = arrayList.iterator());
                while (((Iterator)iterator2).hasNext()) {
                    final Packet packet = iterator.next();
                    iterator2 = iterator;
                    pc.M(packet);
                    Ob.M().sendPacket(packet);
                }
                eventClientPacket.setCanceled(true);
            }
            this.G = (CPacketPlayer)eventClientPacket.getPacket();
        }
    }
}
