package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.component.impl.player.BlinkComponent;
import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.DamageUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public class SparkyLongJump extends Mode<LongJump> {
    public SparkyLongJump(String name, LongJump parent) {
        super(name, parent);
    }
    public Vec3 position = new Vec3(0, 0, 0);
    public int ticksSince  = 0;

    @Override
    public void onEnable() {
        DamageUtil.damagePlayer(DamageUtil.DamageType.POSITION, 3.42F, 1, false, false);
        mc.timer.timerSpeed = 0.2F;
    }

    public final BooleanValue LessFall = new BooleanValue("Less Fall Damage", this, true);
    private final NumberValue height = new NumberValue("Height", this, 2, 0, 2.8, 0.1);

    @EventLink
    public final Listener<StrafeEvent> onStrafe = event -> {
ticksSince++;

        if (MoveUtil.isMoving() && !mc.thePlayer.onGround) {


        }

        if (!mc.thePlayer.onGround && !(FallDistanceComponent.distance > 1)) {





        } else{

        }

        if (mc.thePlayer.onGround && mc.thePlayer.hurtTime > 0) {
            event.setSpeed(9.2);
            mc.thePlayer.motionY = height.getValue().doubleValue();
        } else if (mc.thePlayer.hurtTime > 3) {
            event.setSpeed(.6);
        }
        else{

        }




    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        if (mc.thePlayer.onGround){
            BlinkComponent.blinking = false;

        }

        if (!mc.thePlayer.onGround && !(FallDistanceComponent.distance > 1)) {
            BlinkComponent.blinking = true;
        } else{
            BlinkComponent.blinking = false;
        }

        if (!mc.thePlayer.onGround && LessFall.getValue()) {

            if (FallDistanceComponent.distance > 10) {

                PacketUtil.send(new C03PacketPlayer(true));

                mc.timer.timerSpeed = 0.5f;
                FallDistanceComponent.distance = 0;
            }

        }
    };
    
}

