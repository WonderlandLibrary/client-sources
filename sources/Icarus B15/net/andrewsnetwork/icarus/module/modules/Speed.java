// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import java.math.RoundingMode;
import java.math.BigDecimal;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraft.client.entity.EntityPlayerSP;
import net.andrewsnetwork.icarus.event.events.MotionFlying;
import net.minecraft.item.ItemFood;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.potion.Potion;
import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.andrewsnetwork.icarus.utilities.BlockHelper;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.event.events.PlayerMovement;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.andrewsnetwork.icarus.event.events.EveryTick;
import net.andrewsnetwork.icarus.event.events.SetBack;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.ModeValue;
import net.andrewsnetwork.icarus.module.Module;

public class Speed extends Module
{
    public final ModeValue speedMode;
    private boolean boostCollided;
    private double boostSpeed;
    private float ground;
    public boolean canStep;
    public boolean nextTick;
    public boolean jumped;
    public boolean nextPacket;
    private int motionDelay;
    private int ticks;
    private int packets;
    private double hDistance;
    private double yDistance;
    private double prevX;
    private double prevY;
    private double prevZ;
    private double curX;
    private double curY;
    private double curZ;
    private double lastHDistance;
    public double yOffset;
    private int jumpTicks;
    private int value;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private final TimeHelper flagTime;
    
    public Speed() {
        super("Speed", -6684775, 47, Category.MOVEMENT);
        this.speedMode = new ModeValue("speed_Speed Mode", "speedmode", "bhop_latest", new String[] { "new", "boost", "other", "old", "frames", "bhop", "bhop_latest" }, this);
        this.value = 0;
        this.stage = 1;
        this.moveSpeed = 0.2872;
        this.flagTime = new TimeHelper();
    }
    
    @Override
    public void onEnabled() {
        this.stage = 1;
        this.moveSpeed = ((Speed.mc.thePlayer == null) ? 0.2873 : this.getBaseMoveSpeed());
        super.onEnabled();
        this.motionDelay = 0;
        this.packets = 0;
        this.ticks = 0;
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
        if (e instanceof SetBack) {
            if (this.speedMode.getStringValue().equals("frames")) {
                this.value = 3;
            }
            if (this.speedMode.getStringValue().equals("bhop") || this.speedMode.getStringValue().equals("bhop_latest")) {
                this.flagTime.reset();
            }
        }
        if (e instanceof EveryTick) {
            this.setTag("Speed §7" + RenderHelper.getPrettyName(this.speedMode.getStringValue(), "_"));
        }
        if (e instanceof PlayerMovement) {
            final PlayerMovement event = (PlayerMovement)e;
            if (this.speedMode.getStringValue().equals("bhop_latest")) {
                event.y = -1.0;
            }
            if ((this.speedMode.getStringValue().equals("bhop") || this.speedMode.getStringValue().equals("bhop_latest")) && !Icarus.getModuleManager().getModuleByName("freecam").isEnabled() && this.flagTime.hasReached(750L) && !BlockHelper.isInLiquid() && !BlockHelper.isOnLiquid()) {
                if (Speed.mc.thePlayer.isCollidedVertically) {
                    this.canStep = true;
                }
                else {
                    this.canStep = false;
                }
                if (round(Speed.mc.thePlayer.posY - (int)Speed.mc.thePlayer.posY, 3) == round(0.943, 3)) {
                    if (this.speedMode.getStringValue().equals("bhop_latest")) {
                        final PlayerMovement playerMovement = event;
                        playerMovement.y -= 0.11;
                        final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
                        thePlayer.posY -= 0.11316090325960147;
                    }
                    else if (!this.speedMode.getStringValue().equals("bhop_latest")) {
                        final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
                        thePlayer2.motionY -= 0.15;
                        final PlayerMovement playerMovement2 = event;
                        playerMovement2.y -= 0.09;
                        final EntityPlayerSP thePlayer3 = Speed.mc.thePlayer;
                        thePlayer3.posY -= 0.09;
                    }
                }
                if (this.stage == 1 && (Speed.mc.thePlayer.moveForward != 0.0f || Speed.mc.thePlayer.moveStrafing != 0.0f)) {
                    this.stage = 2;
                    this.moveSpeed = (this.speedMode.getStringValue().equals("bhop_latest") ? 2.35 : 2.05) * this.getBaseMoveSpeed() - 0.01;
                }
                else if (this.stage == 2) {
                    this.stage = 3;
                    if (!this.speedMode.getStringValue().equals("bhop_latest")) {
                        Speed.mc.thePlayer.motionY = 0.4;
                    }
                    event.y = 0.4;
                    this.moveSpeed *= (this.speedMode.getStringValue().equals("bhop_latest") ? 2.146 : 2.149);
                }
                else if (this.stage == 3) {
                    this.stage = 4;
                    final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                }
                else {
                    if (Speed.mc.theWorld.getCollidingBoundingBoxes(Speed.mc.thePlayer, Speed.mc.thePlayer.boundingBox.offset(0.0, Speed.mc.thePlayer.motionY, 0.0)).size() > 0 || Speed.mc.thePlayer.isCollidedVertically) {
                        this.stage = 1;
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
                final MovementInput movementInput = Speed.mc.thePlayer.movementInput;
                float forward = movementInput.moveForward;
                float strafe = movementInput.moveStrafe;
                float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
                if (forward == 0.0f && strafe == 0.0f) {
                    event.x = 0.0;
                    event.z = 0.0;
                }
                else if (forward != 0.0f) {
                    if (strafe >= 1.0f) {
                        yaw += ((forward > 0.0f) ? -45 : 45);
                        strafe = 0.0f;
                    }
                    else if (strafe <= -1.0f) {
                        yaw += ((forward > 0.0f) ? 45 : -45);
                        strafe = 0.0f;
                    }
                    if (forward > 0.0f) {
                        forward = 1.0f;
                    }
                    else if (forward < 0.0f) {
                        forward = -1.0f;
                    }
                }
                final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
                final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
                event.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
                event.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
            }
        }
        if (e instanceof PreMotion) {
            final PreMotion event2 = (PreMotion)e;
            final boolean speedy = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed);
            final double xDist = Speed.mc.thePlayer.posX - Speed.mc.thePlayer.prevPosX;
            final double zDist = Speed.mc.thePlayer.posZ - Speed.mc.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
            if (this.speedMode.getStringValue().equals("frames") && !BlockHelper.isInLiquid() && !BlockHelper.isOnLiquid()) {
                final KeyBinding keyBindJump = Speed.mc.gameSettings.keyBindJump;
                KeyBinding.setKeyBindState(Speed.mc.gameSettings.keyBindJump.getKeyCode(), true);
                if (Speed.mc.thePlayer.onGround) {
                    if (this.jumpTicks < 10) {
                        this.value = 3;
                    }
                    this.jumpTicks = 0;
                    ++this.value;
                    if (this.value == 1) {
                        this.hDistance = 0.415;
                    }
                    if (this.value >= 2) {
                        this.hDistance = 0.07;
                        this.value = 0;
                    }
                    final EntityPlayerSP thePlayer4 = Speed.mc.thePlayer;
                    thePlayer4.motionX *= 0.13;
                    final EntityPlayerSP thePlayer5 = Speed.mc.thePlayer;
                    thePlayer5.motionZ *= 0.13;
                    this.lastHDistance = 0.0;
                }
                else {
                    ++this.jumpTicks;
                }
                if (this.jumpTicks > 2) {
                    Speed.mc.timer.timerSpeed = 1.15f;
                }
                else {
                    Speed.mc.timer.timerSpeed = 0.9f;
                }
                final double[] increaseVals = { 1.0, 1.956, 0.5876, 0.98, 0.982, 0.983, 0.9847, 0.9859, 0.9869, 0.988, 0.9889, 0.989 };
                if (this.jumpTicks > increaseVals.length - 1) {
                    this.jumpTicks = increaseVals.length - 1;
                }
                this.hDistance *= increaseVals[this.jumpTicks];
                this.lastHDistance += Math.abs(Speed.mc.thePlayer.rotationYaw - this.yDistance) / 700.0;
                double strafeValue = 1.0;
                strafeValue += this.lastHDistance;
                if (Speed.mc.thePlayer.moveStrafing != 0.0f) {
                    if (Speed.mc.thePlayer.moveForward > 0.0f) {
                        strafeValue = 1.2;
                    }
                    else {
                        strafeValue = 1.3;
                    }
                }
                if (Speed.mc.thePlayer.moveForward < 0.0f) {
                    strafeValue = 1.3;
                }
                this.hDistance /= strafeValue;
                ((EntityPlayerSP) mc.thePlayer).setPlayerSpeed(this.hDistance);
                this.prevX = Speed.mc.thePlayer.posX;
                this.yDistance = Speed.mc.thePlayer.rotationYaw;
            }
            if (this.speedMode.getStringValue().equals("new")) {
                this.canStep = false;
                final double offset = (Speed.mc.thePlayer.rotationYaw + 90.0f + ((Speed.mc.thePlayer.moveForward > 0.0f) ? ((Speed.mc.thePlayer.moveStrafing > 0.0f) ? -45 : ((Speed.mc.thePlayer.moveStrafing < 0.0f) ? 45 : 0)) : ((Speed.mc.thePlayer.moveForward < 0.0f) ? (180 + ((Speed.mc.thePlayer.moveStrafing > 0.0f) ? 45 : ((Speed.mc.thePlayer.moveStrafing < 0.0f) ? -45 : 0))) : ((Speed.mc.thePlayer.moveStrafing > 0.0f) ? -90 : ((Speed.mc.thePlayer.moveStrafing < 0.0f) ? 90 : 0))))) * 3.141592653589793 / 180.0;
                final double x = Math.cos(offset) * 0.25;
                final double z = Math.sin(offset) * 0.25;
                if (this.shouldSpeedUp()) {
                    final EntityPlayerSP thePlayer6 = Speed.mc.thePlayer;
                    thePlayer6.motionX += x;
                    Speed.mc.thePlayer.motionY = 0.017500000074505806;
                    final EntityPlayerSP thePlayer7 = Speed.mc.thePlayer;
                    thePlayer7.motionZ += z;
                    if (Speed.mc.thePlayer.movementInput.moveStrafe != 0.0f) {
                        final EntityPlayerSP thePlayer8 = Speed.mc.thePlayer;
                        thePlayer8.motionX *= 0.9750000238418579;
                        final EntityPlayerSP thePlayer9 = Speed.mc.thePlayer;
                        thePlayer9.motionZ *= 0.9750000238418579;
                    }
                    Speed.mc.timer.timerSpeed = 1.05f;
                    this.jumped = true;
                }
                else {
                    Speed.mc.timer.timerSpeed = 1.0f;
                }
                if (this.jumped && !Speed.mc.thePlayer.onGround && !Speed.mc.thePlayer.isOnLadder()) {
                    Speed.mc.thePlayer.motionY = -0.10000000149011612;
                    this.jumped = false;
                }
            }
            if (this.speedMode.getStringValue().equals("other")) {
                final double yDifference = Speed.mc.thePlayer.posY - Speed.mc.thePlayer.lastTickPosY;
                final boolean groundCheck = Speed.mc.thePlayer.onGround && yDifference == 0.0;
                final boolean strafe2 = Speed.mc.thePlayer.moveStrafing != 0.0f;
                double speed = 2.433;
                final double slow = 1.5;
                if (!Speed.mc.thePlayer.isSprinting()) {
                    speed += 0.4;
                }
                if (strafe2) {
                    speed -= 0.03999999910593033;
                }
                if (Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    final PotionEffect effect = Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed);
                    switch (effect.getAmplifier()) {
                        case 0: {
                            speed -= 0.2975;
                            break;
                        }
                        case 1: {
                            speed -= 0.5575;
                            break;
                        }
                        case 2: {
                            speed -= 0.7858;
                            break;
                        }
                        case 3: {
                            speed -= 0.7858;
                            break;
                        }
                    }
                }
                if (this.shouldSpeedUp()) {
                    final boolean nextTick = !this.nextTick;
                    this.nextTick = nextTick;
                    if (nextTick) {
                        final EntityPlayerSP thePlayer10 = Speed.mc.thePlayer;
                        thePlayer10.motionX *= speed;
                        final EntityPlayerSP thePlayer11 = Speed.mc.thePlayer;
                        thePlayer11.motionZ *= speed;
                        this.canStep = false;
                    }
                    else {
                        final EntityPlayerSP thePlayer12 = Speed.mc.thePlayer;
                        thePlayer12.motionX /= slow;
                        final EntityPlayerSP thePlayer13 = Speed.mc.thePlayer;
                        thePlayer13.motionZ /= slow;
                        this.canStep = true;
                    }
                }
                else if (this.nextTick) {
                    final EntityPlayerSP thePlayer14 = Speed.mc.thePlayer;
                    thePlayer14.motionX /= speed;
                    final EntityPlayerSP thePlayer15 = Speed.mc.thePlayer;
                    thePlayer15.motionZ /= speed;
                    this.canStep = true;
                    this.nextTick = false;
                }
            }
            if (this.speedMode.getStringValue().equals("old")) {
                final boolean using = Speed.mc.thePlayer.isUsingItem() && Speed.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood;
                double speed2 = 3.15;
                final double slow2 = 1.458;
                this.canStep = false;
                if (this.shouldSpeedUp()) {
                    if (Speed.mc.thePlayer.onGround && this.ground < 1.0f) {
                        this.ground += 0.2f;
                    }
                    if (!Speed.mc.thePlayer.onGround) {
                        this.ground = 0.0f;
                    }
                    if (this.ground == 1.0f && this.shouldSpeedUp()) {
                        if (Speed.mc.thePlayer.moveStrafing != 0.0f) {
                            speed2 -= 0.1;
                        }
                        if (Speed.mc.thePlayer.isInWater()) {
                            speed2 -= 0.1;
                        }
                        ++this.motionDelay;
                        if (this.motionDelay == 1) {
                            final EntityPlayerSP thePlayer16 = Speed.mc.thePlayer;
                            thePlayer16.motionX *= speed2;
                            final EntityPlayerSP thePlayer17 = Speed.mc.thePlayer;
                            thePlayer17.motionZ *= speed2;
                            this.canStep = false;
                        }
                        else if (this.motionDelay == 2) {
                            final EntityPlayerSP thePlayer18 = Speed.mc.thePlayer;
                            thePlayer18.motionX /= slow2;
                            final EntityPlayerSP thePlayer19 = Speed.mc.thePlayer;
                            thePlayer19.motionZ /= slow2;
                            this.canStep = true;
                        }
                        else if (this.motionDelay >= 4) {
                            this.motionDelay = 0;
                        }
                    }
                }
                else if (this.motionDelay == 1) {
                    this.canStep = true;
                    Speed.mc.timer.timerSpeed = 1.0f;
                    final EntityPlayerSP thePlayer20 = Speed.mc.thePlayer;
                    thePlayer20.motionX /= slow2 + 0.3;
                    final EntityPlayerSP thePlayer21 = Speed.mc.thePlayer;
                    thePlayer21.motionZ /= slow2 + 0.3;
                    this.motionDelay = 0;
                    return;
                }
            }
            if (this.speedMode.getStringValue().equals("boost")) {
                if (!Speed.mc.thePlayer.onGround) {
                    this.ticks = -5;
                }
                if (this.shouldSpeedUp()) {
                    if (!Speed.mc.thePlayer.isCollidedVertically) {
                        this.boostSpeed = 0.29316;
                        Speed.mc.timer.timerSpeed = 1.0f;
                    }
                    else {
                        switch (this.ticks) {
                            case 0: {
                                Speed.mc.timer.timerSpeed = 1.0f;
                                break;
                            }
                            case 1: {
                                this.boostSpeed *= 2.1499999;
                                Speed.mc.timer.timerSpeed = 1.0f;
                                break;
                            }
                            case 2: {
                                final double offset2 = Speed.mc.thePlayer.posX - Speed.mc.thePlayer.prevPosX;
                                final double x2 = Speed.mc.thePlayer.posZ - Speed.mc.thePlayer.prevPosZ;
                                final double z2 = Math.sqrt(offset2 * offset2 + x2 * x2);
                                final double difference2 = 0.656 * (z2 - 0.299);
                                this.boostSpeed = z2 - difference2;
                                if (!this.boostCollided) {
                                    event2.setY(event2.getY() + 0.017);
                                }
                                Speed.mc.timer.timerSpeed = 1.175f;
                                break;
                            }
                        }
                        if (this.ticks >= 2) {
                            this.ticks = 0;
                        }
                    }
                }
                else {
                    this.boostSpeed = 0.29316;
                    Speed.mc.timer.timerSpeed = 1.0f;
                    this.ticks = -5;
                }
                ++this.ticks;
            }
        }
        else if (e instanceof MotionFlying) {
            final MotionFlying event3 = (MotionFlying)e;
            if (this.shouldSpeedUp() && this.speedMode.getStringValue().equals("boost")) {
                event3.setCancelled(true);
                if (Speed.mc.thePlayer.isCollidedVertically) {
                    float yaw2 = Speed.mc.thePlayer.rotationYaw;
                    if (event3.getForward() != 0.0f) {
                        if (event3.getStrafe() >= 0.98) {
                            yaw2 += ((event3.getForward() > 0.0f) ? -45 : 45);
                            event3.setStrafe(0.0f);
                        }
                        else if (event3.getStrafe() <= -0.98) {
                            yaw2 += ((event3.getForward() > 0.0f) ? 45 : -45);
                            event3.setStrafe(0.0f);
                        }
                        if (event3.getForward() == 0.98) {
                            event3.setForward(1.0f);
                        }
                        else if (event3.getForward() == -0.98) {
                            event3.setForward(-1.0f);
                        }
                    }
                    double mx2 = Math.cos(Math.toRadians(yaw2 + 90.0f));
                    double mz2 = Math.sin(Math.toRadians(yaw2 + 90.0f));
                    Speed.mc.thePlayer.motionX = event3.getForward() * this.boostSpeed * mx2 + event3.getStrafe() * this.boostSpeed * mz2;
                    Speed.mc.thePlayer.motionZ = event3.getForward() * this.boostSpeed * mz2 - event3.getStrafe() * this.boostSpeed * mx2;
                    if (Speed.mc.theWorld.getCollidingBoundingBoxes(Speed.mc.thePlayer, Speed.mc.thePlayer.getEntityBoundingBox().offset(Speed.mc.thePlayer.motionX * 1.25, 0.0, Speed.mc.thePlayer.motionZ * 1.25)).size() <= 0 && Speed.mc.theWorld.getCollidingBoundingBoxes(Speed.mc.thePlayer, Speed.mc.thePlayer.getEntityBoundingBox().offset(Speed.mc.thePlayer.motionX, -1.0, Speed.mc.thePlayer.motionZ)).size() > 0) {
                        this.boostCollided = false;
                    }
                    else {
                        this.boostCollided = true;
                        this.ticks = -2;
                        this.boostSpeed = 0.29316;
                        yaw2 = Speed.mc.thePlayer.rotationYaw;
                        if (event3.getForward() != 0.0f) {
                            if (event3.getStrafe() >= 0.98) {
                                yaw2 += ((event3.getForward() > 0.0f) ? -45 : 45);
                                event3.setStrafe(0.0f);
                            }
                            else if (event3.getStrafe() <= -0.98) {
                                yaw2 += ((event3.getForward() > 0.0f) ? 45 : -45);
                                event3.setStrafe(0.0f);
                            }
                            if (event3.getForward() == 0.98) {
                                event3.setForward(1.0f);
                            }
                            else if (event3.getForward() == -0.98) {
                                event3.setForward(-1.0f);
                            }
                        }
                        mx2 = Math.cos(Math.toRadians(yaw2 + 90.0f));
                        mz2 = Math.sin(Math.toRadians(yaw2 + 90.0f));
                        Speed.mc.thePlayer.motionX = event3.getForward() * this.boostSpeed * mx2 + event3.getStrafe() * this.boostSpeed * mz2;
                        Speed.mc.thePlayer.motionZ = event3.getForward() * this.boostSpeed * mz2 - event3.getStrafe() * this.boostSpeed * mx2;
                    }
                    if (event3.getForward() == 0.0f && Speed.mc.thePlayer.onGround && event3.getStrafe() == 0.0f) {
                        Speed.mc.thePlayer.motionX = 0.0;
                        Speed.mc.thePlayer.motionZ = 0.0;
                    }
                }
            }
        }
    }
    
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2872;
        if (Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public boolean shouldSpeedUp() {
        if (Speed.mc.thePlayer == null) {
            return false;
        }
        final boolean moving = Speed.mc.thePlayer.movementInput.moveForward != 0.0f || Speed.mc.thePlayer.movementInput.moveStrafe != 0.0f;
        return !Speed.mc.thePlayer.isInWater() && !BlockHelper.isInLiquid() && !BlockHelper.isOnLiquid() && !Speed.mc.thePlayer.isCollidedHorizontally && !BlockHelper.isOnIce() && !BlockHelper.isOnLadder() && !Speed.mc.thePlayer.isSneaking() && Speed.mc.thePlayer.onGround && moving;
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        if (Speed.mc.thePlayer != null) {
            if (this.speedMode.getStringValue().equals("frames")) {
                final KeyBinding keyBindJump = Speed.mc.gameSettings.keyBindJump;
                KeyBinding.setKeyBindState(Speed.mc.gameSettings.keyBindJump.getKeyCode(), false);
            }
            Speed.mc.timer.timerSpeed = 1.0f;
        }
    }
}
