package io.github.liticane.clients.feature.module.impl.movement;

import io.github.liticane.clients.feature.event.api.EventListener;
import io.github.liticane.clients.feature.event.api.annotations.SubscribeEvent;
import io.github.liticane.clients.feature.event.impl.motion.PreMotionEvent;
import io.github.liticane.clients.feature.event.impl.other.MoveEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.misc.TimerUtil;
import io.github.liticane.clients.util.player.MoveUtil;
import io.github.liticane.clients.util.player.PlayerUtil;
import net.minecraft.potion.Potion;
import org.apache.commons.lang3.RandomUtils;
import org.lwjglx.input.Keyboard;

@Module.Info(name = "Speed", category = Module.Category.MOVEMENT)
public class Speed extends Module {

    public StringProperty mode = new StringProperty("Mode", this, "Strafe", "Strafe","Custom", "Watchdog","Verus","Verus 2","Verus 3");
    public NumberProperty bruh = new NumberProperty("Bruh", this, 1, 0.5, 8, 0.5);
    private final TimerUtil timer = new TimerUtil();
    private int stage;
    private double lastY;

    @Override
    protected void onEnable() {
        this.stage = 0;
        super.onEnable();
    }

    @Override
    protected void onDisable() {
        timer.reset();
        mc.settings.keyBindJump.pressed = Keyboard.isKeyDown(mc.settings.keyBindJump.getKeyCode());
        if(mode.is("Verus 3")) {
            mc.player.setPosition(mc.player.posX, lastY, mc.player.posZ);
        }
        super.onDisable();
    }
    @SubscribeEvent
    private final EventListener<MoveEvent> moveent = event -> {
        switch (mode.getMode()) {
            case "Verus 2":
            if (mc.player.onGround) {
                event.setY(0.42F);
                MoveUtil.strafe(0.69F + MoveUtil.speedPotionAmp(0.1));
                mc.player.motionY = 0F;
            } else {
                MoveUtil.strafe(0.41F + MoveUtil.speedPotionAmp(0.055));
            }
            mc.player.setSprinting(true);
            break;
            case "Verus 3":
                if (!mc.settings.keyBindJump.pressed && MoveUtil.isMoving()) {
                    if (stage >= 7) {
                        float add = mc.player.isPotionActive(Potion.moveSpeed) ? 0.12f : -0.05f;
                        MoveUtil.strafe(event, MoveUtil.getSpeed(event) + ((0.25f + add)));
                        //RandomUtils.nextFloat(0.010f, 0.02f)
                        if(MoveUtil.hasAppliedSpeedII(mc.player)) {
                            //RandomUtils.nextFloat(0.012f, 0.02f)
                            MoveUtil.strafe(event, MoveUtil.getSpeed(event));
                        } else {
                            MoveUtil.strafe(event, MoveUtil.getSpeed(event) - RandomUtils.nextFloat(0.012f, 0.02f));
                        }
                    }
                }
                if(!MoveUtil.isMoving()) {
                    stage = 0;
                }
                break;
        }


    };

    @SubscribeEvent
    private final EventListener<PreMotionEvent> preMotionEventEventListener = event -> {
        setSuffix(mode.getMode());
        if(!mode.is("Verus 2") || !mode.is("Watchdog")) {
            mc.settings.keyBindJump.pressed = true;
        }

        switch (mode.getMode()) {
            case "Strafe":
                MoveUtil.strafe(MoveUtil.baseSpeed());
                break;
            case "Watchdog":
                if(mc.player.onGround) {
                    mc.player.jump();
                    MoveUtil.strafe(MoveUtil.baseSpeed());
                }
                break;
            case "Custom":
                MoveUtil.strafe(bruh.getValue());
                break;
            case "Verus":
                if(mc.player.isSprinting()) {
                    if (!MoveUtil.hasAppliedSpeedII(mc.player)) {
                        if (!mc.player.onGround) {
                            if (!mc.player.isPotionActive(Potion.moveSpeed)) {
                                MoveUtil.strafe(0.37f);
                                //0.36 might be the max value
                            } else {
                                MoveUtil.strafe(0.43f);
                            }
                        }
                    } else {
                        MoveUtil.strafe(0.49f);
                        //0.48
                    }
                    if (!MoveUtil.hasAppliedSpeedII(mc.player)) {
                        if (mc.player.onGround) {
                            if (!mc.player.isPotionActive(Potion.moveSpeed)) {
                                MoveUtil.strafe(0.55f);
                                //0.50
                            } else {
                                if (mc.settings.keyBindForward.isPressed()) {
                                    MoveUtil.strafe(0.63f);
                                } else {
                                    //  MoveUtil.strafe(MoveUtil.getBaseMoveSpeed() - 0.2f);
                                }
                            }
                        }
                    } else {
                        if (mc.player.onGround) {
                            MoveUtil.strafe(0.73f);
                            //0.80 kindo flags
                            //0.79 kindo flags :skull:
                            //0.78 might flag
                            //0.77 kindo flags
                            //0.76 wtf
                            //0.75 f
                            //0.73 safe
                            //0.72
                            //0.70
                            //might be able to go a bit more
                        }
                    }
                } else{
                    MoveUtil.strafe(MoveUtil.baseSpeed());
                }
                break;
            case "Verus 3":
                mc.settings.keyBindJump.pressed = false;
                if (PlayerUtil.isMathGround()) {
                    event.setOnGround(true);

                    switch (this.stage++) {
                        case 0:
                            MoveUtil.strafe(MoveUtil.baseSpeed());
                            break;
                        case 2:
                        case 1: {
                            event.setOnGround(false);
                            event.setPosY(event.getPosY() + 0.419999986886978);
                            break;
                        }

                        case 4:
                        case 3: {
                            event.setOnGround(false);
                            event.setPosY(event.getPosY() + 0.341599985361099);
                            break;
                        }

                        case 6:
                        case 5: {
                            event.setOnGround(false);
                            event.setPosY(event.getPosY() + 0.186367980844497);
                            break;
                        }
                    }

                    this.stage++;
                    lastY = event.getPosY();

                    if (stage >= 24) {
                        stage = 0;
                        MoveUtil.strafe(MoveUtil.baseSpeed());
                    }
                } else if (!(mc.player.posY == Math.round(mc.player.posY))) {
                    stage = 0;
                    MoveUtil.strafe(MoveUtil.baseSpeed());
                }
                break;

        }
    };

}
