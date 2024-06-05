package Ascii4UwUWareClient.Module.Modules.Move;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventMove;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Client;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Module.Modules.Combat.Killaura;
import Ascii4UwUWareClient.Util.MoveUtils;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.player.MovementUtil;
import Ascii4UwUWareClient.Util.player.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.optifine.MathUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Speed extends Module {

    public Mode <Enum> mode = new Mode ( "Mode", "Mode", SpeedMode.values (), SpeedMode.NCP );
    private final Numbers <Double> hypixelspeed = new Numbers <Double> ( "Speed", "Speed", 0.0118, 0.0001, 0.03, 0.0001 );


    public int stage;
    public double moveSpeed;
    private double movementSpeed;
    private double distance;
    private int ticksSinceJump;
    public double lastDist;
    private final TimerUtil timer = new TimerUtil ();
    private int counter;
    public static boolean strafeDirection;
    public boolean shouldslow;

    public int waitTicks, hops;
    private double speed;
    public double motion;
    public boolean doSlow;
    public  boolean boosted;

    public Speed() {
        super ( "Speed", new String[]{"Speed", "Bhop", "AutoJump"}, ModuleType.Move );
        addValues ( mode );
    }



    private boolean isInLiquid() {
        if (Minecraft.thePlayer == null) {
            return false;
        }
        for (int x = MathHelper.floor_double ( Minecraft.thePlayer.boundingBox.minX ); x < MathHelper
                .floor_double ( Minecraft.thePlayer.boundingBox.maxX ) + 1; x++) {
            for (int z = MathHelper.floor_double ( Minecraft.thePlayer.boundingBox.minZ ); z < MathHelper
                    .floor_double ( Minecraft.thePlayer.boundingBox.maxZ ) + 1; z++) {
                BlockPos pos = new BlockPos ( x, (int) Minecraft.thePlayer.boundingBox.minY, z );
                Block block = Minecraft.theWorld.getBlockState ( pos ).getBlock ();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    private boolean canZoom() {
        return Minecraft.thePlayer.moving () && Minecraft.thePlayer.onGround && !this.isInLiquid ();
    }

    public void setSpeed(final double speed) {
        Minecraft.thePlayer.motionX = -(Math.sin ( getDirection () ) * speed);
        Minecraft.thePlayer.motionZ = Math.cos ( getDirection () ) * speed;
    }

    public float getDirection() {
        float yaw = Minecraft.thePlayer.rotationYaw;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (Minecraft.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw *= 0.017453292f;
        return yaw;
    }

    @EventHandler
    public void onPreUpdate(EventPreUpdate event) {
        movementSpeed = 0.0;
        this.distance = 0.0;
        stage = 0;

        setSuffix ( mode.getModeAsString () );
        switch (mode.getModeAsString ()) {
            case "Mineplex":
                break;
            case "Redesky":

                if (mc.thePlayer.onGround && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0)) {
                    mc.thePlayer.jump ();
                    motion = 1.02995;
                    doSlow = true;
                } else {
                    if (doSlow) {
                        motion += 0.0015625;
                        doSlow = false;
                    } else {
                        motion *= 0.99585;
                    }
                    motion = Math.max ( 0.999998, motion );
                }
                mc.thePlayer.motionX *= motion;
                mc.thePlayer.motionZ *= motion;

         break;
            case "NCP":
                if (Minecraft.thePlayer.isMoving()) {
                    if (Killaura.target == null && (mc.gameSettings.keyBindForward.isKeyDown () || mc.gameSettings.keyBindLeft.isKeyDown () || mc.gameSettings.keyBindRight.isKeyDown ())) {
                        MoveUtils.strafe ();
                    }
                    if (Minecraft.thePlayer.onGround) {
                        Minecraft.thePlayer.jump ();
                    }
                }

        break;

            case "EpicGamer":
                if (MovementUtil.isMoving() && MovementUtil.isOnGround()){
                    Minecraft.thePlayer.jump();
                }
                break;
            case "Cubecraft":
                if (Minecraft.thePlayer.isMoving()) {
                    if (Killaura.target == null && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())) {
                        MoveUtils.strafe();
                    }
                    if (Minecraft.thePlayer.onGround) {
                        Minecraft.thePlayer.jump();
                        event.setY(event.getY() + 1.01346739E-7);
                    }
                } else {
                    Minecraft.thePlayer.motionX = 0D;
                    Minecraft.thePlayer.motionZ = 0D;
                }
                break;
            case "Verus":
                mc.timer.timerSpeed = 1.0f;
                if (Minecraft.thePlayer.isMoving () && Minecraft.thePlayer.onGround && this.timer.hasTimeElapsed(100L, true)) {
                    Minecraft.thePlayer.jump();
                }
                if (Minecraft.thePlayer.isSprinting()) {
                    if (Minecraft.thePlayer.onGround) {
                        if (Minecraft.thePlayer.moveForward > 0.0f) {
                           setSpeed( 0.19f );
                        } else {
                            setSpeed( 0.14f );
                        }
                    } else if (Minecraft.thePlayer.moveForward > 0.0f) {
                       setSpeed( 0.295f );
                    } else {
                        setSpeed( 0.29f );
                    }
                } else if (Minecraft.thePlayer.onGround) {
                    if (Minecraft.thePlayer.moveForward > 0.0f) {
                       setSpeed( 0.16f );
                    } else {
                        setSpeed( 0.14f );
                    }
                } else if (Minecraft.thePlayer.moveForward > 0.0f) {
                   setSpeed( 0.25f );
                } else {
                    setSpeed( 0.2f );
                }

                break;
            case"Minemen":
                if(Minecraft.thePlayer.ticksExisted % 3 == 0){
                    MoveUtils.setMotion(0.68);
                }else {
                    MoveUtils.setMotion(0.34);
                }
                break;
            case"AAC":
            if (!Minecraft.thePlayer.isOnLadder()) {
                final Minecraft mc2 =Speed.mc;
                if (!Minecraft.thePlayer.isEating ()) {
                    final Minecraft mc3 = Speed.mc;
                    if (!Minecraft.thePlayer.isInWater ()) {
                        final Minecraft mc4 = Speed.mc;
                        if (!Minecraft.thePlayer.isInLava ()) {
                            Speed.mc.gameSettings.keyBindJump.pressed = false;
                            final Minecraft mc5 = Speed.mc;
                            if (Minecraft.thePlayer.onGround) {
                                final Minecraft mc6 = Speed.mc;
                                Minecraft.thePlayer.jump ();
                                this.counter = 5;
                            } else if (this.counter < 7) {
                                if (this.counter == 1) {
                                    Speed.mc.timer.timerSpeed = 1.32352324f;
                                } else {
                                    Speed.mc.timer.timerSpeed = 1.0f;
                                }
                                ++this.counter;
                            } else {
                                this.counter = 0;
                            }
                        }
                    }
                }
            }
        break;

        }
    }

    @EventHandler
    public void onMove(EventMove e){
        switch (mode.getModeAsString()){
            case "Mineplex":
                double speed = 0;
                mc.timer.timerSpeed = 1.1f;
                stage++;
                if (Minecraft.thePlayer.isCollidedHorizontally) {
                    stage = 50;
                }
                if (Minecraft.thePlayer.onGround && (Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f)) {
                    Minecraft.thePlayer.jump();
                    EventMove.setY(Minecraft.thePlayer.motionY = 0.42);
                    stage = 0;
                    speed = 0.3;
                }
                if (!Minecraft.thePlayer.onGround) {
                    if (Minecraft.thePlayer.motionY > -1.623523528) {
                        Minecraft.thePlayer.motionY += 0.023452545343;

                    } else {
                        Minecraft.thePlayer.motionY += 0.01;
                    }
                    double slowdown1 = 0.006;
                    speed = (2 - (stage * slowdown1));
                    if (speed < 0) speed = 0;
                }
                this.setMoveSpeed(e, speed);
                break;

            case "Cubecraft":
            case "Verus":
            case "Craftplay":
                this.setMoveSpeed(e, MovementUtil.defaultSpeed());
                break;
            case "NCP":
                double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
                double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
                this.lastDist = Math.sqrt ( xDist * xDist + zDist * zDist );
                if (!canSpeed ())
                    return;
                if (Minecraft.thePlayer.isInWater ()) {
                    return;
                }
                mc.timer.timerSpeed = 1.0f;
                if (MathUtils.round ( Minecraft.thePlayer.posY - (double) ((int) Minecraft.thePlayer.posY), 3 ) == MathUtils.round ( 0.138, 3 )) {
                    Minecraft.thePlayer.motionY -= 0.08;
                    Minecraft.thePlayer.motionY -= 0.09316090325960147;
                    Minecraft.thePlayer.posY -= 0.09316090325960147;
                }
                if (this.stage == 1 && (Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f)) {
                    this.stage = 2;
                    this.moveSpeed = 1.38 * this.getBaseMoveSpeed () - 0.01;
                } else if (this.stage == 2) {
                    this.stage = 3;
                    Minecraft.thePlayer.motionY = 0.399399995803833;
                    Minecraft.thePlayer.motionY = 0.399399995803833;
                    this.moveSpeed *= 2.149;
                } else if (this.stage == 3) {
                    this.stage = 4;
                    double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed ());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    if (Minecraft.theWorld.getCollidingBoundingBoxes ( Minecraft.thePlayer,
                            Minecraft.thePlayer.boundingBox.offset ( 0.0, Minecraft.thePlayer.motionY, 0.0 ) ).size () > 0
                            || Minecraft.thePlayer.isCollidedVertically) {
                        this.stage = 1;
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                this.moveSpeed = Math.max ( this.moveSpeed, this.getBaseMoveSpeed () );
                MovementInput movementInput = Minecraft.thePlayer.movementInput;
                float forward = MovementInput.moveForward;
                float strafe = MovementInput.moveStrafe;
                float yaw = Minecraft.thePlayer.rotationYaw;
                if (forward == 0.0f && strafe == 0.0f) {
                    Minecraft.thePlayer.motionX = 0.0;
                    Minecraft.thePlayer.motionZ = 0.0;
                } else if (forward != 0.0f) {
                    if (strafe >= 1.0f) {
                        yaw += (float) (forward > 0.0f ? -45 : 45);
                        strafe = 0.0f;
                    } else if (strafe <= -1.0f) {
                        yaw += (float) (forward > 0.0f ? 45 : -45);
                        strafe = 0.0f;
                    }
                    if (forward > 0.0f) {
                        forward = 1.0f;
                    } else if (forward < 0.0f) {
                        forward = -1.0f;
                    }
                }
                double mx2 = Math.cos ( Math.toRadians ( yaw + 90.0f ) );
                double mz2 = Math.sin ( Math.toRadians ( yaw + 90.0f ) );
                double motionX = (double) forward * this.moveSpeed * mx2 + (double) strafe * this.moveSpeed * mz2;
                double motionZ = (double) forward * this.moveSpeed * mz2 - (double) strafe * this.moveSpeed * mx2;
                Minecraft.thePlayer.motionX = (double) forward * this.moveSpeed * mx2 + (double) strafe * this.moveSpeed * mz2;
                Minecraft.thePlayer.motionZ = (double) forward * this.moveSpeed * mz2 - (double) strafe * this.moveSpeed * mx2;


                break;


        }
    }

    @Override
    public void onEnable() {
        this.moveSpeed = MovementUtil.getBaseMoveSpeed() * (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? 1.0D : 1.34D);
        stage = 0;
        ticksSinceJump = 3;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        stage = 0;
        if (this.mode.getValue() == Speed.SpeedMode.AAC) {
            Minecraft.thePlayer.speedInAir = 0.02f;
            mc.timer.timerSpeed = 1f;
            Minecraft.thePlayer.motionX = 0.0;
            Minecraft.thePlayer.motionY = 0.0;
            Minecraft.thePlayer.motionZ = 0.0;
        }
        super.onDisable();
    }

    public boolean canSpeed() {
        if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.movementInput.jump) {
            return false;
        } else
            return true;
    }
    public void setMoveSpeed(EventMove event, double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        TargetStrafe targetStrafe = (TargetStrafe) Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class);
        double moveForward = Minecraft.thePlayer.movementInput.getForward();
        double moveStrafe = TargetStrafe.canStrafe() ? targetStrafe.strafeDirection : Minecraft.thePlayer.movementInput.getStrafe();
        if (TargetStrafe.canStrafe()) {
            if (Minecraft.thePlayer.getDistanceToEntity(Killaura.target) <= 3) moveForward = 0;
        }
        double yaw = TargetStrafe.canStrafe() ? RotationUtil.getRotations(Killaura.target)[0] : Minecraft.thePlayer.rotationYaw;
        if (moveForward == 0.0F && moveStrafe == 0.0F) {
            event.setX(0);
            event.setZ(0);
        }
        if (moveForward != 0 && moveStrafe != 0) {
            moveForward = moveForward * Math.sin(Math.PI / 4);
            moveStrafe = moveStrafe * Math.cos(Math.PI / 4);
        }
        event.setX(moveForward * moveSpeed * -Math.sin(Math.toRadians(yaw)) + (moveStrafe) * moveSpeed * Math.cos(Math.toRadians(yaw)));
        event.setZ(moveForward * moveSpeed * Math.cos(Math.toRadians(yaw)) - (moveStrafe) * moveSpeed * -Math.sin(Math.toRadians(yaw)));

    }


    public static void setSpeed(float d) {
        boolean isMovingStraight;
        double yaw = Minecraft.thePlayer.rotationYaw;
        boolean isMoving = Minecraft.thePlayer.moveForward != 0.0f || Minecraft.thePlayer.moveStrafing != 0.0f;
        boolean isMovingForward = Minecraft.thePlayer.moveForward > 0.0f;
        boolean isMovingBackward = Minecraft.thePlayer.moveForward < 0.0f;
        boolean isMovingRight = Minecraft.thePlayer.moveStrafing > 0.0f;
        boolean isMovingLeft = Minecraft.thePlayer.moveStrafing < 0.0f;
        boolean isMovingSideways = isMovingLeft || isMovingRight;
        boolean bl = isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            } else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            } else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            } else if (isMovingForward) {
                yaw -= 45.0;
            } else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            } else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            } else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            } else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            Minecraft.thePlayer.motionX = -Math.sin(yaw) * (double)d;
            Minecraft.thePlayer.motionZ = Math.cos(yaw) * (double)d;
        }
    }




    public double round(double var1, int var3) {
        if (var3 < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal var4 = new BigDecimal(var1);
            var4 = var4.setScale(var3, RoundingMode.HALF_UP);
            return var4.doubleValue();
        }
    }

    private void setMotion(EventMove em, double speed) {

        Minecraft mc = Minecraft.getMinecraft();
        TargetStrafe targetStrafe = (TargetStrafe) Client.instance.getModuleManager().getModuleByClass(TargetStrafe.class);
        double moveForward = Minecraft.thePlayer.movementInput.getForward();
        double moveStrafe = TargetStrafe.canStrafe() ? targetStrafe.strafeDirection : Minecraft.thePlayer.movementInput.getStrafe();
        if (TargetStrafe.canStrafe()) {
            if (Minecraft.thePlayer.getDistanceToEntity(Killaura.target) <= TargetStrafe.distance.getValue())
                moveForward = 0;
        }
        double yaw = TargetStrafe.canStrafe() ? RotationUtil.getRotations( Killaura.target)[0] : Minecraft.thePlayer.rotationYaw;
        if (moveForward == 0.0F && moveStrafe == 0.0F) {
            em.setX(0);
            em.setZ(0);
        }
        if (moveForward != 0 && moveStrafe != 0) {
            moveForward = moveForward * Math.sin(Math.PI / 4);
            moveStrafe = moveStrafe * Math.cos(Math.PI / 4);
        }
        em.setX(moveForward * moveSpeed * -Math.sin(Math.toRadians(yaw)) + (moveStrafe) * moveSpeed * Math.cos(Math.toRadians(yaw)));
        em.setZ(moveForward * moveSpeed * Math.cos(Math.toRadians(yaw)) - (moveStrafe) * moveSpeed * -Math.sin(Math.toRadians(yaw)));

    }

    double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return baseSpeed;
    }


    public enum SpeedMode{
        Mineplex, Redesky , NCP, EpicGamer, Cubecraft , Verus , Minemen , AAC
    }

}
