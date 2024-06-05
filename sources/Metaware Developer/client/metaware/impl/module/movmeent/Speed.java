package client.metaware.impl.module.movmeent;

import client.metaware.api.event.painfulniggerrapist.Listener;
import client.metaware.api.event.painfulniggerrapist.annotations.EventHandler;
import client.metaware.api.module.api.Category;
import client.metaware.api.module.api.Module;
import client.metaware.api.module.api.ModuleInfo;
import client.metaware.api.properties.property.Property;
import client.metaware.api.properties.property.impl.DoubleProperty;
import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.PlayerJumpEvent;
import client.metaware.impl.event.impl.player.PlayerStrafeEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.utils.util.other.FrictionUtil;
import client.metaware.impl.utils.util.other.MathUtils;
import client.metaware.impl.utils.util.other.PlayerUtil;
import client.metaware.impl.utils.util.player.ACType;
import client.metaware.impl.utils.util.player.MovementUtils;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Speed", renderName = "Speed", category = Category.MOVEMENT, keybind = Keyboard.KEY_F)
public class Speed extends Module {

    public EnumProperty<Mode> mode = new EnumProperty<>("Mode", Mode.Verus);
    public Property<Boolean> hasTimer = new Property<>("Timer", false, () -> mode.getValue() == Mode.Watchdog);
    public DoubleProperty timerSpeed = new DoubleProperty("Timer Speed", 1.2, 1, 10, 0.05, () -> hasTimer.getValue() && mode.getValue() == Mode.Watchdog);
    public double movementSpeed;
    public int stage;
    public boolean doSlow;

    public enum Mode{
        Verus, Verus2, Watchdog, Funcrafto
    }

    @Override
    public void onEnable() {
        super.onEnable();
        movementSpeed = 0;
        stage = 0;
        doSlow = false;
    }

    @EventHandler
    private final Listener<PlayerStrafeEvent> eventListener1 = event -> {
        switch (mode.getValue()) {
        }
    };

    @EventHandler
    private final Listener<UpdatePlayerEvent> eventListener2 = event -> {
        if(mode.getValue() == Mode.Watchdog){

        }
    };

    @EventHandler
    private final Listener<MovePlayerEvent> eventListener = event -> {
        setSuffix(mode.getValue().toString());

        switch (mode.getValue()) {
            case Watchdog:{
                double baseSpeed = MovementUtils.getSpeed();

                if(mc.thePlayer.isMoving()) {
                    if(hasTimer.getValue() && !mc.thePlayer.onGround)
                        mc.timer.timerSpeed = (float) (timerSpeed.getValue() + Math.random() / 500);


                    mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
                    if (mc.thePlayer.isMovingOnGround()) {
                        event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(0.42F));
                        stage = 0;
                    }

                    switch (stage) {
                        case 0: {
                            movementSpeed = baseSpeed * 2.135;
                            break;
                        }
                        case 1: {
                            movementSpeed *= 0.575;
                            break;
                        }
                        case 4: {
                            movementSpeed = baseSpeed * 1.2;
                            break;
                        }
                        default: {
                            if(!mc.thePlayer.onGround) event.setY(mc.thePlayer.motionY += 0.0025 + Math.random() / 500);
                            movementSpeed = FrictionUtil.applyCustomFriction((float)movementSpeed, 98.5f);
                            break;
                        }
                    }
                    this.stage++;
                    MovementUtils.setSpeed(event, Math.max(baseSpeed, movementSpeed));
                }
                break;
            }
            case Funcrafto: {
               MovementUtils.setSpeed(event, mc.thePlayer.ticksExisted % 2 != 0 ? 0 : 1.2);
                break;
            }
            case Verus:{
                mc.thePlayer.motionX *= mc.thePlayer.motionZ *= 0;
                if(mc.thePlayer.isMoving()){
                    double baseSpeed = MovementUtils.getSpeed();

                    if(mc.thePlayer.onGround){
                        event.setY(mc.thePlayer.motionY = 0.42F);
                        mc.thePlayer.setPosition(mc.thePlayer.posX + MovementUtils.yawPos(0.3)[0], mc.thePlayer.posY, mc.thePlayer.posZ + MovementUtils.yawPos(0.3)[1]);
                        doSlow = true;
                    }else if(doSlow){
                        mc.thePlayer.setPosition(mc.thePlayer.posX + MovementUtils.yawPos(1)[0], mc.thePlayer.posY, mc.thePlayer.posZ + MovementUtils.yawPos(1)[1]);
                        doSlow = false;
                    }else{
                        mc.thePlayer.setPosition(mc.thePlayer.posX + MovementUtils.yawPos(0.2)[0], mc.thePlayer.posY, mc.thePlayer.posZ + MovementUtils.yawPos(0.2)[1]);
                    }
                }
//                if(mc.thePlayer.isMoving()) {
//                    if (mc.thePlayer.onGround) {
//                        event.setY(mc.thePlayer.motionY = .42F);
//                        movementSpeed = 0.612;
//                        stage = 1;
//                    } else if(mc.thePlayer.fallDistance < 1.2){
//                        movementSpeed = 0.36;
//                    }
//                    MovementUtils.setSpeed(event, movementSpeed - 1.0E-4);
//                    MovementUtils.setSpeed(event, MovementUtils.getBaseSpeed(ACType.VERUS) - 0.24 + movementSpeed);
//                }
                break;
            }
            case Verus2:{
//                if(mc.thePlayer.isMoving()){
//                    if(mc.thePlayer.onGround){
//                        event.setY(mc.thePlayer.motionY = .4F);
//                        MovementUtils.setSpeed(event, 0.6);
//                    }else{
//                        MovementUtils.setSpeed(event, 0.4);
//                    }
//                }
                break;
            }
        }
    };

    private double getAACSpeed(int stage, int jumps) {
        double value = 0.29;
        double firstvalue = 0.3019;
        double thirdvalue = 0.0286 - (double) stage / 1000;
        if (stage == 0) {
            //JUMP
            value = 0.497;
            if (jumps >= 2) {
                value += 0.1069;
            }
            if (jumps >= 3) {
                value += 0.046;
            }
        } else if (stage == 1) {
            value = 0.3031;
            if (jumps >= 2) {
                value += 0.0642;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 2) {
            value = 0.302;
            if (jumps >= 2) {
                value += 0.0629;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 3) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0607;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 4) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0584;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 5) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0561;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 6) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0539;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 7) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0517;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 8) {
            value = firstvalue;
            if (MovementUtils.isOnGround(0.05))
                value -= 0.002;

            if (jumps >= 2) {
                value += 0.0496;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 9) {
            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0475;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 10) {

            value = firstvalue;
            if (jumps >= 2) {
                value += 0.0455;
            }
            if (jumps >= 3) {
                value += thirdvalue;
            }
        } else if (stage == 11) {

            value = 0.3;
            if (jumps >= 2) {
                value += 0.045;
            }
            if (jumps >= 3) {
                value += 0.018;
            }

        } else if (stage == 12) {
            value = 0.301;
            if (jumps <= 2)
                stage = 0;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 13) {
            value = 0.298;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        } else if (stage == 14) {

            value = 0.297;
            if (jumps >= 2) {
                value += 0.042;
            }
            if (jumps >= 3) {
                value += thirdvalue + 0.001;
            }
        }
        if (mc.thePlayer.moveForward <= 0) {
            value -= 0.06;
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            value -= 0.1;
            stage = 0;
        }
        return value;
    }

    @EventHandler
    private final Listener<PlayerJumpEvent> playerJumpEventListener = event -> {
      event.setCancelled(mode.getValue() == Mode.Watchdog);
    };

}
