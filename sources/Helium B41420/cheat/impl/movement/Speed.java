package rip.helium.cheat.impl.movement;

import java.util.*;
import me.hippo.systems.lwjeb.annotation.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Timer;
import rip.helium.cheat.*;
import rip.helium.cheat.impl.combat.aura.Aura;
import rip.helium.event.minecraft.*;
import rip.helium.utils.*;
import rip.helium.utils.property.abs.*;
import rip.helium.utils.property.impl.*;

public class Speed extends Cheat {
    private double moveSpeed;
    private boolean slowDownHop;
    private double less;
    private double stair;
    private StringsProperty prop_mode;
    private double speed;
    private int stage;
    private double stage1;
    public static double yeet;
    private double lastDist;
    private boolean doSlow;
    private boolean setZero;


    public Speed() {
        super("Speed", "Makes you move faster.", CheatCategory.MOVEMENT);
        // change modes
        //premium speed modes:
        DoubleProperty speed = new DoubleProperty("Speed", "epic", null, 1.1, 0.1, 2.0, 0.1, null);
        this.prop_mode = new StringsProperty("Mode", "change the mode.", null, false, true, new String[]{"Hypixel", "Faithful", "Vanilla", "Mineplex", "MineplexSlow", "MineplexLow", "MineplexYPort"}, new Boolean[]{true, false, false, false, false, false, false});
        //free speed modes: this.prop_mode = new StringsProperty("Mode", "change the mode.", null, false, true, new String[] { "Watchdog", "Vanilla" }, new Boolean[] { true, false, false });
        this.registerProperties(this.prop_mode, speed);
    }

    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }

    public void onEnable() {
    }

    @Collect
    public void onPlayerUpdate(final PlayerUpdateEvent e) {
        this.setMode(this.prop_mode.getSelectedStrings().get(0));
    }

    /*/
    Start new hypixel
     */

    @Collect
    public void onMove(PlayerMoveEvent moveEvent) {
        if (prop_mode.getValue().get("Hypixel")) {
            if (mc.thePlayer.isMoving() && !PlayerUtils.isInLiquid()) {

                this.stage++;
            }
        }
    }

    @Collect
    public void onUpdate(PlayerUpdateEvent event) {
        if (event.isPre()) {
            double x = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
            double z = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;

            this.lastDist = Math.sqrt(x * x + z * z);

            if (prop_mode.getValue().get("Hypixel")) {
                if (mc.thePlayer.isMoving()) {
                    if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
                        event.setPosY(event.getPosY() + 0.0007435);
                    }
                }
            }
        }
    }


    /*/   public static double getBaseMoveSpeed() {
           double baseSpeed = 0.2873;
        
           if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
               final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
               baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
           }
           return baseSpeed;
       }/*/




    @Collect
    public void fmc(final PlayerMoveEvent e) {
        if (this.prop_mode.getValue().get("Faithful")) {

            switch (this.stage) {
                case 0: {
                    ++this.stage;
                    this.lastDist = 0.0;
                    break;
                }
                case 2: {
                    this.lastDist = 0.0;
                    double motionY = 0.5669;
                    if ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) && this.mc.thePlayer.onGround) {
                        if (this.mc.thePlayer.isPotionActive(Potion.jump)) {
                            motionY += (this.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
                        }
                        e.setY(this.mc.thePlayer.motionY = motionY);
                        this.moveSpeed *= (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 2.1499 : 2.1699 + 0.7);
                        break;
                    }
                    break;
                }
                case 3: {
                    this.moveSpeed = this.lastDist - (this.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (this.mc.thePlayer.isPotionActive(Potion.jump) ? 0.52 : 0.870) : 0.8315) * (this.lastDist - SpeedUtils.getBaseMoveSpeed() + 0.79999999999);
                    break;
                }
                default: {
                    if ((this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) && this.stage > 0) {
                        this.stage = ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / (this.mc.thePlayer.isPotionActive(Potion.jump) ? 163.0 : 163.0);
                    break;
                }
            }
            this.moveSpeed = Math.max(this.moveSpeed, SpeedUtils.getBaseMoveSpeed());
            if (mc.thePlayer.movementInput.moveForward == 0.0 && mc.thePlayer.movementInput.moveStrafe == 0.0) {
                e.setX(0.0);
                e.setZ(0.0);
            }
            if (mc.thePlayer.movementInput.moveForward != 0.0 && mc.thePlayer.movementInput.moveStrafe != 0.0) {
                mc.thePlayer.movementInput.moveForward *= Math.sin(0.805699694312);
                mc.thePlayer.movementInput.moveStrafe *= Math.cos(0.805699694312);
            }
            e.setX((mc.thePlayer.movementInput.moveForward * this.moveSpeed * -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw)) + mc.thePlayer.movementInput.moveStrafe * this.moveSpeed * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw))) * 0.99);
            e.setZ((mc.thePlayer.movementInput.moveForward * this.moveSpeed * Math.cos(Math.toRadians(mc.thePlayer.rotationYaw)) - mc.thePlayer.movementInput.moveStrafe * this.moveSpeed * -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw))) * 0.99);
            ++this.stage;
        }
    }
   
   @Collect
   public void mpspeed(PlayerMoveEvent event) {
	   if (this.prop_mode.getValue().get("Mineplex")) {
           boolean doSlow;
           this.mc.timer.timerSpeed = 1.0f;
           if (!this.mc.thePlayer.isMoving()) {
               this.setZero = true;
           }
           if (this.mc.thePlayer.onGround) {
               final double d = 0.42;
               event.setY(this.mc.thePlayer.motionY = d);
               this.speed = SpeedUtils.getBaseMoveSpeed() * 2.876;
               this.doSlow = true;
               final Timer timer = this.mc.timer;
               timer.timerSpeed *= 2.149;
               SpeedUtils.setMoveSpeed(event, 0.0);
           }
           else {
               if (this.doSlow) {
                   this.speed -= 0.2 * (this.speed - SpeedUtils.getBaseMoveSpeed());
                   this.doSlow = false;
               }
               this.speed -= this.speed / 159.0;
               if (!TargetStrafe.doStrafeAtSpeed(event, speed)) {
                   SpeedUtils.setMoveSpeed(event, this.speed = Math.max(this.speed, SpeedUtils.getBaseMoveSpeed()));
               }
           }
	   }
   }

    @Collect
    public void mp2speed(PlayerMoveEvent event) {
        if (this.prop_mode.getValue().get("MineplexSlow")) {
            boolean doSlow;
            this.mc.timer.timerSpeed = 1.0f;
            if (!this.mc.thePlayer.isMoving()) {
                this.setZero = true;
            }
            if (this.mc.thePlayer.onGround) {
                final double d = 0.42;
                event.setY(this.mc.thePlayer.motionY = d);
                this.speed = SpeedUtils.getBaseMoveSpeed() * 2.156;
                this.doSlow = true;
                final Timer timer = this.mc.timer;
                timer.timerSpeed *= 2.149;
                SpeedUtils.setMoveSpeed(event, 0.0);
            }
            else {
                if (this.doSlow) {
                    this.speed -= 0.2 * (this.speed - SpeedUtils.getBaseMoveSpeed());
                    this.doSlow = false;
                }
                this.speed -= this.speed / 149.0;
                if (!TargetStrafe.doStrafeAtSpeed(event, speed)) {
                    SpeedUtils.setMoveSpeed(event, this.speed = Math.max(this.speed, SpeedUtils.getBaseMoveSpeed()));
                }
            }
        }
    }

    @Collect
    public void mp3speed(PlayerMoveEvent event) {
        if (this.prop_mode.getValue().get("MineplexLow")) {
            boolean doSlow;
            this.mc.timer.timerSpeed = 1.0f;
            if (!this.mc.thePlayer.isMoving()) {
                this.setZero = true;
            }
            if (this.mc.thePlayer.onGround) {
                final double d = 0.35;
                event.setY(this.mc.thePlayer.motionY = d);
                this.speed = SpeedUtils.getBaseMoveSpeed() * 2.356;
                this.doSlow = true;
                final Timer timer = this.mc.timer;
                timer.timerSpeed *= 2.149;
                SpeedUtils.setMoveSpeed(event, 0.0);
            }
            else {
                if (this.doSlow) {
                    this.speed -= 0.2 * (this.speed - SpeedUtils.getBaseMoveSpeed());
                    this.doSlow = false;
                }
                this.speed -= this.speed / 149.0;
                if (!TargetStrafe.doStrafeAtSpeed(event, speed)) {
                    SpeedUtils.setMoveSpeed(event, this.speed = Math.max(this.speed, SpeedUtils.getBaseMoveSpeed()));
                }
            }
        }
    }

    @Collect
    public void mp4speed(PlayerMoveEvent event) {
        if (this.prop_mode.getValue().get("MineplexYPort")) {
            boolean doSlow;
            this.mc.timer.timerSpeed = 1.0f;
            if (!this.mc.thePlayer.isMoving()) {
                this.setZero = true;
            }
            if (this.mc.thePlayer.onGround) {
                final double d = 0.30;
                event.setY(this.mc.thePlayer.motionY = d);
                this.speed = SpeedUtils.getBaseMoveSpeed() * 2.356;
                this.doSlow = true;
                final Timer timer = this.mc.timer;
                timer.timerSpeed *= 2.149;
                SpeedUtils.setMoveSpeed(event, 0.0);
            }
            else {
                if (this.doSlow) {
                    this.speed -= 0.2 * (this.speed - SpeedUtils.getBaseMoveSpeed());
                    this.doSlow = false;
                }
                this.speed -= this.speed / 149.0;
                if (!TargetStrafe.doStrafeAtSpeed(event, speed)) {
                    SpeedUtils.setMoveSpeed(event, this.speed = Math.max(this.speed, SpeedUtils.getBaseMoveSpeed()));
                }
            }
        }
    }

    
    @Collect
    public void onground(final PlayerMoveEvent e) {
        if (this.prop_mode.getValue().get("Vanilla") && this.mc.thePlayer.isMoving()) {
            if (!TargetStrafe.doStrafeAtSpeed(e, speed)) {
                mc.thePlayer.setMoveSpeedAris(e, 0.8);
            }
        }
    }
    
    @Collect
    public void onground(final PlayerUpdateEvent e) {
        if (this.prop_mode.getValue().get("Vanilla")) {
            e.setOnGround(true);
        }
    }
    
        public boolean isIce() {
            double x = mc.thePlayer.posX;
            double z = mc.thePlayer.posZ;

            BlockPos underPos = new BlockPos(x, mc.thePlayer.posY - 1, z);
            Block block = mc.theWorld.getBlockState(underPos).getBlock();


            return (block instanceof BlockIce);
        }

    
    private boolean isPosSolid(BlockPos pos) {
        Block block = mc.theWorld.getBlockState(pos).getBlock();
        return (block.getMaterial().isSolid() || !block.isTranslucent() || block.isSolidFullCube() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
    }
    
    private double getCurrentSpeed(final int stage) {
        double speed = this.mc.thePlayer.getBaseMoveSpeed() + 0.028 * this.mc.thePlayer.getSpeedEffect() + this.mc.thePlayer.getSpeedEffect() / 15.0;
        final double initSpeed = 0.4145 + this.mc.thePlayer.getSpeedEffect() / 12.5;
        final double decrease = stage / 500.0 * 1.87;
        if (stage == 0) {
            speed = 0.642 + (this.mc.thePlayer.getSpeedEffect() + 0.028 * this.mc.thePlayer.getSpeedEffect()) * 0.134;
        }
        else if (stage == 1) {
            speed = initSpeed;
        }
        else if (stage >= 2) {
            speed = initSpeed - decrease;
        }
        return Math.max(speed, this.slowDownHop ? speed : (this.mc.thePlayer.getBaseMoveSpeed() + 0.028 * this.mc.thePlayer.getSpeedEffect()));
    }
}
