package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.LClickEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.PacketEvent;
import io.github.liticane.clients.feature.event.impl.other.StrafeEvent;
import io.github.liticane.clients.feature.event.impl.other.TickEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import io.github.liticane.clients.util.player.RotationUtils;
import io.github.liticane.clients.util.player.ScafUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjglx.input.Keyboard;

import java.util.Random;

@Module.Info(name = "ScaffoldWalk", category = Module.Category.MOVEMENT)
public class ScaffoldWalk extends Module {
    //todo:recode this scaffold and scaf util
    private ScafUtil.BlockData blockdata, lastblockdata;
    public StringProperty scaffoldmode = new StringProperty("Scaffold Mode", this, "Normal", "Normal", "Extend");
    public NumberProperty extenda = new NumberProperty("Extend Amount", this, 1, 0, 6, 1,() -> scaffoldmode.is("Extend"));
    public BooleanProperty sprint = new BooleanProperty("Sprint",this,false);
    public StringProperty sprintmode = new StringProperty("Sprint Mode", this, "Normal", "Normal", "Watchdog");
    public BooleanProperty movefix = new BooleanProperty("Move Fix",this,false);
    public  BooleanProperty y = new BooleanProperty("Y",this,false);
    public BooleanProperty autojump = new BooleanProperty("Auto Jump",this,false);
    public BooleanProperty tower = new BooleanProperty("Tower",this,false);
    public BooleanProperty hypixel = new BooleanProperty("Hypixel",this,false);
    public BooleanProperty sneak = new BooleanProperty("Sneak",this,false);
    public NumberProperty startmin = new NumberProperty("Start Min", this, 50, 1, 400, 1,() -> sneak.isToggled());
    public NumberProperty startmax = new NumberProperty("Start Max", this, 50, 1, 400, 1,() -> sneak.isToggled());
    public NumberProperty endmin = new NumberProperty("Min Min", this, 50, 1, 400, 1,() -> sneak.isToggled());
    public NumberProperty endmax = new NumberProperty("Min Max", this, 50, 1, 400, 1,() -> sneak.isToggled());
    public BooleanProperty rr = new BooleanProperty("Rotations",this,false);
    public NumberProperty bruteforce = new NumberProperty("Brute Force Amount", this, 0.1, 0, 3, 0.1,() -> rr.isToggled());
    public BooleanProperty swing = new BooleanProperty("Swing",this,false);
    public double Ylevel;
    private float yaw, pitch, lastYaw, lastPitch;
    private float rotations[];
    private final TimerUtil timerend = new TimerUtil();
    private final TimerUtil timestart = new TimerUtil();
    private boolean sneaking;

    @Override
    protected void onEnable() {
        Ylevel = mc.player.getPositionVector().yCoord;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        if(autojump.isToggled()) {
            mc.settings.keyBindJump.pressed = Keyboard.isKeyDown(mc.settings.keyBindJump.getKeyCode());
        }
        super.onDisable();
    }
    @SubscribeEvent
    private final EventListener<LClickEvent> LCLickEvent = e -> {
        place();
    };

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = e -> {
        this.setSuffix(scaffoldmode.getMode());
        rotate(e);
        sneak();
        if(hypixel.isToggled() && mc.player.onGround) {
            //0.09
            MoveUtil.strafe(0.11f);
        }
    };
    @SubscribeEvent
    private final EventListener<TickEvent> onTick = e -> {
        if(lastblockdata != null) {
            calculateRotations();
        }
    };
    private void sneak() {
        if(!sneak.isToggled()) {
            return;
        }
        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ)).getBlock() instanceof BlockAir
                && mc.player.onGround) {
            if(timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(),(float)startmin.getValue(),(float)startmax.getValue()))) {
                sneaking = true;
                timestart.reset();
                mc.settings.keyBindSneak.pressed = true;
            }
        } else {
            if (sneaking) {
                if (timestart.hasTimeElapsed(MathHelper.randomFloatClamp(new Random(), (float)endmin.getValue(), (float)endmax.getValue()))) {
                    // if(mc.player.ticksExisted % 2 == 0) {
                    mc.settings.keyBindSneak.pressed = false;
                    timerend.reset();
                    sneaking = false;
                }
            }
        }
    }
    private void calculateRotations() {
        lastYaw = yaw;
        lastPitch = pitch;
        float[] rotations = new float[] {0, 0};
        if(lastblockdata != null) {
            rotations = RotationUtils.getSmoothRotations(lastblockdata.getPosition(),lastblockdata.getFacing(),false,(float)bruteforce.getValue());
        } else  {
            rotations = new float[] { mc.player.rotationYaw - 180, 90};
        }
        yaw = rotations[0];
        pitch = rotations[1];


        float[] fixedRotations = RotationUtils.getFixedRotations(
                new float[] { yaw, pitch },
                new float[] { lastYaw, lastPitch }
        );

        yaw = fixedRotations[0];
        pitch = fixedRotations[1];

    }
    private void rotate(PreMotionEvent e) {
        if(rr.isToggled()) {
            e.setYaw(yaw);
            e.setPitch(pitch);
            //maybe to much
            RotationUtils.setVS(yaw, pitch,lastYaw,20);
        }
    }
    private void place() {
        if(!sprint.isToggled() && mc.player.isSprinting()) {
          mc.player.setSprinting(false);
        }
        blockdata = ScafUtil.placedata();
        if (blockdata != null) {
            lastblockdata = ScafUtil.placedata();
        }else{
            return;
        }
        int slot = ScafUtil.getBlockSlot();
        if(slot == -1) return;
        mc.player.inventory.currentItem = slot;
        if (blockdata == null) return;
        mc.controller.onPlayerRightClick(mc.player, mc.world, mc.player.inventory.getStackInSlot(slot), lastblockdata.position, lastblockdata.facing, ScafUtil.getHypixelVec3(lastblockdata));
        if(swing.isToggled()){
            mc.player.swing();
        } else {
            mc.player.connection.send(new C0APacketAnimation());
        }
        //if(mc.settings.keyBindJump.isPressed() || mc.settings.keyBindJump.isKeyDown() && !MoveUtil.isMoving()) {
        //            Ylevel = mc.player.getPositionVector().yCoord;
        //        } else {
        //            Ylevel = Y3;
        //        }

        if(autojump.isToggled()) {
            mc.settings.keyBindJump.pressed = true;
        }
        blockdata = null;
    }
    @SubscribeEvent
    private final EventListener<StrafeEvent> onpacketeve = event -> {
        if(tower.isToggled() && mc.settings.keyBindJump.isKeyDown()) {
            // mc.player.motionY = 0.75f;
            if (!MoveUtil.isMoving() && mc.player == null) {
                return;
            }

            if (mc.player.onGround && mc.player.ticksExisted % 2 == 0) {
                mc.player.motionY = 0.42F;
                //1.2
                mc.player.motionX *= 0.7;
                mc.player.motionZ *= 0.7;
            }
        }
    };
    @SubscribeEvent
    private final EventListener<PacketEvent> onpacketo = event -> {
        if(tower.isToggled() && mc.player!= null) {
            final Packet<?> packet = event.getPacket();
            if (mc.player.motionY > -0.0114000015258789 && !mc.player.isPotionActive(Potion.jump) && packet instanceof C08PacketPlayerBlockPlacement && MoveUtil.isMoving()) {
                final C08PacketPlayerBlockPlacement wrapper = ((C08PacketPlayerBlockPlacement) packet);

                if (wrapper.getPosition().equals(new BlockPos(mc.player.posX, mc.player.posY - 1.4, mc.player.posZ))) {
                    mc.player.motionY = -0.0114000015258789;
                }
            }
        }
        switch (sprintmode.getMode()) {
            case "Watchdog":
                if(mc.player.onGround) {
                    if(event.getPacket() instanceof C03PacketPlayer) {
                        event.setCancelled(true);
                    }
                }
                break;
        }
    };

}
