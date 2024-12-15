package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.Client;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Criticals;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public final class BufferAbuseVelocity extends Mode<Velocity> {
    public BufferAbuseVelocity(String name, Velocity parent) {
        super(name, parent);
    }
    private final NumberValue horizontal = new NumberValue("Horizontal", this, 100, 0, 100, 1);
    private final NumberValue vertical = new NumberValue("Vertical", this, 100, 0, 100, 1);
    private final NumberValue buffer = new NumberValue("Buffer", this, 1, 1, 3, 1);
    public final BooleanValue air = new BooleanValue("Watchdog Mode / Only in Air", this, false);
    private int amount;

    private Speed speed = null;

    @EventLink
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress || event.isCancelled()) return;

        final Packet<?> p = event.getPacket();

        final double horizontal = this.horizontal.getValue().doubleValue();
        final double vertical = this.vertical.getValue().doubleValue();
        if (speed == null) {
            speed = getModule(Speed.class);

        }
        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;


            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {

                if (mc.thePlayer.onGround && getModule(Criticals.class).isEnabled()) {
                    if (wrapper.getMotionY() >= 0.4 && wrapper.getMotionY() <= 0.47) {
                        mc.thePlayer.motionY = (0.419875);
                    }
                }

                if (amount < buffer.getValue().intValue()) {
                    if(air.getValue()){
                        if(mc.thePlayer.offGroundTicks>1 && !(mc.thePlayer.offGroundTicks==11)) {
                            event.setCancelled();
                        } else if (!(speed.isEnabled()) && Client.INSTANCE.getModuleManager().get(Speed.class).mode.getValue().getName().equals("Watchdog")){
                            wrapper.motionX *= horizontal / 100;
                            wrapper.motionY *= vertical / 100;
                            wrapper.motionZ *= horizontal / 100;
                        //    ChatUtil.display("boost1");
                            event.setPacket(wrapper);

                        } else{
                          //  ChatUtil.display("boost2");
                          //  MoveUtil.strafe();
                        }
                    } else {
                        event.setCancelled();
                    }


                    amount++;
                    return;
                }

                wrapper.motionX *= horizontal / 100;
                wrapper.motionY *= vertical / 100;
                wrapper.motionZ *= horizontal / 100;

                event.setPacket(wrapper);
                amount = 0;
            }
        }

        if (p instanceof S27PacketExplosion) {
            final S27PacketExplosion wrapper = (S27PacketExplosion) p;

            if (amount < buffer.getValue().intValue()) {
                event.setCancelled();
                amount++;
                return;
            }

            wrapper.posX *= horizontal / 100;
            wrapper.posY *= vertical / 100;
            wrapper.posZ *= horizontal / 100;

            event.setPacket(wrapper);
            amount = 0;
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if(!(mc.thePlayer.offGroundTicks>1 && !(mc.thePlayer.offGroundTicks==11))) {
           if(mc.thePlayer.hurtTime == 10){
               MoveUtil.strafe();
               ChatUtil.display("strafed");
           }
        }

    };
}
