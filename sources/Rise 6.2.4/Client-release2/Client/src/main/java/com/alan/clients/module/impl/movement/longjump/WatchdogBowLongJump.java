package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.component.impl.player.ItemDamageComponent;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.*;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.component.impl.player.PacketlessDamageComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.render.NotificationComponent;
import com.alan.clients.component.impl.render.PercentageComponent;
import com.alan.clients.component.impl.player.BlinkComponent;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.render.chat.Chat;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.DamageUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.vector.Vector2d;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.List;

public class WatchdogBowLongJump extends Mode<LongJump> {
    private final List<Packet<?>> packets = new ArrayList<>();
    private double speed;
    private boolean aBoolean;
    private boolean aBoolean2;
    private int anInt;
    private int anInt2;
    private double start;

    public WatchdogBowLongJump(String name, LongJump parent) {
        super(name, parent);
    }
    @EventLink
    public final Listener<PacketReceiveEvent> receive = event -> {
        Packet<?> packet = event.getPacket();

        if (packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) packet;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {

            }
        }
    };

    @EventLink
    public final Listener<PreUpdateEvent> preUpdate = event -> {
      //  PingSpoofComponent.spoof(2000, true, false, false, false, false);

        if (!aBoolean) return;

        if (mc.thePlayer.ticksSinceVelocity == 1) {
            MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() * (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.75 : 0.7) - Math.random() / 10000f);
            mc.thePlayer.jump();
        }

        if (mc.thePlayer.ticksSinceVelocity == 9) {
            MoveUtil.strafe((mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.8 : 0.7) - Math.random() / 10000f);

        }

        if (mc.thePlayer.ticksSinceVelocity <= 50 && mc.thePlayer.ticksSinceVelocity > 9) {
            mc.thePlayer.motionY += 0.028;

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                mc.thePlayer.motionX *= 1.038;
                mc.thePlayer.motionZ *= 1.038;
            } else {
                if (mc.thePlayer.ticksSinceVelocity == 12 || mc.thePlayer.ticksSinceVelocity == 13) {
                    mc.thePlayer.motionX *= 1.1;
                    mc.thePlayer.motionZ *= 1.1;
                }

                mc.thePlayer.motionX *= 1.019;
                mc.thePlayer.motionZ *= 1.019;
            }
        }

    };

    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {
        if (!aBoolean) {
            event.setCancelled(true);
            MoveUtil.stop();
        } else if (mc.thePlayer.ticksSinceVelocity <= 19) {
            MoveUtil.strafe();
        }
    };

    @Override
    public void onEnable() {
        ItemDamageComponent.damage(false);
        aBoolean = false;
    }

}
