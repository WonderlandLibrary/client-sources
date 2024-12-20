package com.alan.clients.module.impl.render;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.constants.ConstantsManager;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MathHelper;

// EntityRenderer.java 634
@ModuleInfo(aliases = {"module.render.hurtcamera.name"}, description = "module.render.hurtcamera.description", category = Category.RENDER)
public final class HurtCamera extends Module {

    public final NumberValue intensity = new NumberValue("Intensity", this, 1, 0, 1, 0.1);

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = ((S12PacketEntityVelocity) packet);

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                final double velocityX = wrapper.motionX / 8000.0D;
                final double velocityZ = wrapper.motionZ / 8000.0D;

                mc.thePlayer.attackedAtYaw = (float) (MathHelper.atan2(velocityX, velocityZ) * 180.0D / ConstantsManager.a - (double) mc.thePlayer.rotationYaw);
            }
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.attackedAtYaw = 0;
    }
}