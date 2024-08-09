package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.*;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.*;

@FunctionRegister(name = "LongJump", type = Category.MOVEMENT, desc = "Позволяет вам далеко прыгать")
public class LongJump extends Function {
    public static LongJump get;
    boolean placed;
    int counter;
    public static boolean isFallDamage;
    private int ticks;
    private double packetMotionY;
    private float speed;
    public static boolean doSpeed;


    public ModeSetting mod = new ModeSetting("Мод", "Matrix Flag", "Matrix Flag");
    private final SliderSetting horizontal = new SliderSetting("По горизонтали", 0.1f, 0f, 20f, 0.1f);
    private final SliderSetting vertical = new SliderSetting("По вертикали", 0.1f, 0f, 20f, 0.1f).setVisible(() -> mod.is("StormHVH"));
    private final timerHelper timerHelper = new timerHelper();
    public TimerUtils timer = new TimerUtils();
    public TimerUtils timer2 = new TimerUtils();
    public LongJump() {
        addSettings(mod, horizontal, vertical);
    }



    void flagHop() {
        mc.player.motion.y = 0.4229;
        MoveUtils.setMotion(3.953);
    }
    void flagHop1() {
        mc.player.motion.y = 0.4229;
        MoveUtils.setMotion(horizontal.get());
    }
    StopWatch stopWatch = new StopWatch();
    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mod.is("Matrix Flag")) {
            if (mc.player.getMotion().y != -0.0784000015258789) {
                this.timer2.reset();
            }
            if (!MoveUtils.isMoving()) {
                this.timer2.setTime(this.timer2.getLastMS() + 50L);
            }
            if (this.timer2.hasTimeElapsed(100L)) {
                mc.player.setMotion(mc.player.getMotion().x, 0.4229f, mc.player.getMotion().z);
                MoveUtils.setSpeed(1.953f);
                //Entity.motiony = 1.0;
            }
        } else {
            doSpeed = false;
            isFallDamage = false;
        }
        if (this.mod.is("StormHVH 2")) {
            if (mc.player.motion.y != -0.0784000015258789) {
                this.timerHelper.reset();
            }

            if (!MoveUtils.isMoving()) {
                this.timerHelper.setTime(this.timerHelper.getCurrentMS() + 50L);
            }

            if (this.timerHelper.hasReached(100.0)) {
                this.flagHop1();
                mc.player.motion.y = 1.0;
            }
        } else {
            doSpeed = false;
            isFallDamage = false;
        }
        if(mod.is("StormHVH")){
            if(mc.player.isOnGround())
                mc.player.jump();
            else {
                updatePlayerMotion();
                mc.player.motion.y = vertical.get();
                mc.timer.timerSpeed = 0.2f;
            }
        }
    }
    /*
    @Subscribe
    public void onPacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPlayerPositionLookPacket look) {
            if (this.mod.is("FlagBoost")) {
                mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ());
                mc.player.connection.sendPacket(new CConfirmTeleportPacket(look.getTeleportId()));
                this.flagHop();
                event.cancel();
            }
        }

        if (!isFallDamage) {
            if (event.getRecivePacket() instanceof SEntityVelocityPacket && ((SEntityVelocityPacket) event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                this.packetMotionY = (double) ((SEntityVelocityPacket) event.getPacket()).getMotionY() / 8000.0;
            }
            if (event.getPacket() instanceof SEntityStatusPacket) {
                SEntityStatusPacket sPacketEntityStatus = (SEntityStatusPacket) event.getPacket();
                if (sPacketEntityStatus.getOpCode() == 2 && sPacketEntityStatus.getEntity(mc.world) == mc.player) {
                    doSpeed = true;
                }
            }
        }

    }*/
    @Subscribe
    public void onMoving(MovingEvent e) {
    }
    private void updatePlayerMotion() {
        double motionX = mc.player.getMotion().x;
        double motionZ = mc.player.getMotion().z;

        MoveUtils.setMotion(horizontal.get());
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        counter = 0;
        placed = false;
        return false;
    }
    @Override
    public void onDisable() {
        super.onDisable();
        mc.timer.timerSpeed = 0.998f;
        mc.player.abilities.isFlying = false;
    }
}