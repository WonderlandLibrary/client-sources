package com.alan.clients.module.impl.movement.step;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PostStrafeEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.StepEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.movement.Speed;
import com.alan.clients.module.impl.movement.Step;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.network.play.client.C03PacketPlayer;
import rip.vantage.commons.util.time.StopWatch;

import static com.alan.clients.util.player.MoveUtil.WALK_SPEED;

public class WatchdogStep extends Mode<Step> {
    private boolean step;

    public WatchdogStep(String name, Step parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
    };

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
        speed2 = false;
        ticks2 = 0;
    }


    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
if(mc.thePlayer.onGround){

        }
    };

    private double stepHeight;
    private int ticks;


    private long ticks2;
    private boolean speed2 = false;



    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {


        if (mc.thePlayer.onGround && !PlayerUtil.inLiquid() && !mc.gameSettings.keyBindJump.isKeyDown() ) {
            mc.thePlayer.stepHeight = 1;
        } else {
            mc.thePlayer.stepHeight = 0.6F;
        }


    };

    @EventLink
    public final Listener<StepEvent> onStep = event -> {

    //    MoveUtil.strafe(WALK_SPEED);
        final double height = event.getHeight();
        this.stepHeight = height;

        if (getModule(Speed.class).isEnabled() && height > 0.6F) {
          //  getModule(Speed.class).setEnabled(false);
            speed2 = true;
        }

        if (getModule(Scaffold.class).isEnabled()) {
            mc.thePlayer.stepHeight = 0.6F;

        } else{
            mc.thePlayer.stepHeight = 1;
        }

        if (!mc.thePlayer.onGround || PlayerUtil.inLiquid() || (PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ) instanceof BlockSlab) || (PlayerUtil.block(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ) instanceof BlockStairs)) {
            return;
        }








        if (height <= 0.6F) {
            return;
        }

        double[] values = new double[0];

        if (height == 1) {

            values = new double[]{0.42F, 0.75F, 1.0F};
        }

        ticks2 = System.currentTimeMillis() + 100;

        for (final double d : values) {
            MoveUtil.strafe(MoveUtil.getbaseMoveSpeed());
            mc.timer.timerSpeed = .25f;
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ, false));

            ticks = 0;

        }
    };

    @EventLink
    public final Listener<TickEvent> onTick = event -> {
        //  ChatUtil.display(ticks2);
        ticks++;

        if (ticks == 1) {
           mc.timer.timerSpeed = 1f;
        }

        if(speed2 && System.currentTimeMillis() > ticks2){
            getModule(Speed.class).setEnabled(true);
            speed2 = false;
            ticks2 = 0;
        }
    };



}

