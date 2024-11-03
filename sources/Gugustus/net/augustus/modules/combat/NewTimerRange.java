package net.augustus.modules.combat;

import com.sun.jdi.ShortValue;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventTick;
import net.augustus.events.EventUpdate;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.awt.*;
import java.util.ArrayList;

import static net.augustus.modules.combat.KillAura.target;

public class NewTimerRange extends Module {

    private Long balanceL = 0L;

    public final DoubleValue delay = new DoubleValue(112088,"Delay",this,100,0,3000,0);
    public final DoubleValue maxRange = new DoubleValue(112100, "FarStartRange", this, 3.8, 0.1, 10, 2);
    public final DoubleValue minRange = new DoubleValue(112101, "NearToStopDistance", this, 3.0, 0.1, 8, 2);
    public final DoubleValue firstTimer = new DoubleValue(112103, "TimerBoost", this, 10D, 1.1D, 35D, 1);
    public final DoubleValue lowt = new DoubleValue(112104, "LowTimer", this, 0.2D, 0.0D, 0.99D, 3);
    public final DoubleValue maxBalance = new DoubleValue(112109, "SlowTick", this, 10L, 0L, 10L, 0);
    public final DoubleValue TimerTcik = new DoubleValue(119120,"FastTick",this,10L, 0L, 10L, 0);
    public final BooleanValue onlyf = new BooleanValue(112106, "Only Move Forward", this, false);
    public final BooleanValue onlyog = new BooleanValue(112107, "Only on Ground", this, false);
    private final BooleanValue suffix = new BooleanValue(2717, "Suffix", this, false);
    public BooleanValue onWorld = new BooleanValue(14726, "DisableOnWorld", this, false);
    public NewTimerRange() {
        super("NewTimerRange", new Color(23, 233, 123), Categorys.COMBAT);
    }
    public boolean freezing;
    private final TimeHelper timeDelay = new TimeHelper();
    private long lastLagTime = 0;
    private long fastbalance = 0;
    @Override
public void onEnable() {
    balanceL = 0L;
    mc.timer.timerSpeed = 1f;
    lastLagTime=0;
    fastbalance = 0;
    }

    @Override
    public void onDisable() {
        balanceL = 0L;
        mc.timer.timerSpeed = 1f;
        lastLagTime = 0;
        fastbalance = 0;
    }

    @EventTarget
    public void onWorld(EventWorld eventWorld) {
        if (onWorld.getBoolean()) {
        	setToggled(false);
        }
    }

    @EventTarget
    public void onEvenTick(EventRender3D e) {
        if(mc.timer.timerSpeed >1){
            fastbalance ++;
            if(fastbalance >TimerTcik.getValue()){
                mc.timer.timerSpeed = 1f;
                timeDelay.reset();
            }
        }

        if (shouldStop()) {
            if(mc.timer.timerSpeed !=1f) {
                mc.timer.timerSpeed = 1f;
            }
        }
        final float renderPartialTicks = mc.getTimer().renderPartialTicks;
        for(
                final Entity entity :mc.theWorld.loadedEntityList)
            if(entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase) entity;
                final float width = entity.width / 2.0f;
                final float height = entity.height;
                final double posXNow = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * renderPartialTicks;
                final double posYNow = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * renderPartialTicks;
                final double posZNow = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * renderPartialTicks;
                final double posX2 = entity.posX;
                final double posY2 = entity.posY;
                final double posZ2 = entity.posZ;
                final AxisAlignedBB possibleBoundingBox = new AxisAlignedBB(posX2 - width, posY2, posZ2 - width, posX2 + width, posY2 + height, posZ2 + width);
                final Vec3 positionEyes = mc.thePlayer.getPositionEyes(3.0f);
                final double bestX = MathHelper.clamp_double(positionEyes.xCoord, possibleBoundingBox.minX, possibleBoundingBox.maxX);
                final double bestY = MathHelper.clamp_double(positionEyes.yCoord, possibleBoundingBox.minY, possibleBoundingBox.maxY);
                final double bestZ = MathHelper.clamp_double(positionEyes.zCoord, possibleBoundingBox.minZ, possibleBoundingBox.maxZ);
                final AxisAlignedBB boundingBoxNow = new AxisAlignedBB(posXNow - width, posYNow, posZNow - width, posXNow + width, posYNow + height, posZNow + width);
                final double currentX = MathHelper.clamp_double(positionEyes.xCoord, boundingBoxNow.minX, boundingBoxNow.maxX);
                final double currentY = MathHelper.clamp_double(positionEyes.yCoord, boundingBoxNow.minY, boundingBoxNow.maxY);
                final double currentZ = MathHelper.clamp_double(positionEyes.zCoord, boundingBoxNow.minZ, boundingBoxNow.maxZ);
                final Vec3 currentPosEyes = mc.thePlayer.getPositionEyes(1.0f);
                final Vec3 targetEyes = player.getPositionEyes(1.0f);
                final Vec3 myPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                final double diffX = mc.thePlayer.prevPosX - mc.thePlayer.posX;
                final double diffZ = mc.thePlayer.prevPosZ - mc.thePlayer.posZ;
                final Vec3 myPosBest = myPos.addVector(-diffX * 2.0, 0.0, -diffZ * 2.0);
                final Vec3 myPosBestLast = myPos.addVector(-diffX, 0.0, -diffZ);
                final double myPosForTargetX = myPosBestLast.xCoord + (myPosBest.xCoord - myPosBestLast.xCoord) / 3.0;
                final double myPosForTargetY = myPosBestLast.yCoord + (myPosBest.yCoord - myPosBestLast.yCoord) / 3.0;
                final double myPosForTargetZ = myPosBestLast.zCoord + (myPosBest.zCoord - myPosBestLast.zCoord) / 3.0;
                final float myWidth = player.width / 2.0f;
                final AxisAlignedBB myBB = new AxisAlignedBB(myPosForTargetX - myWidth, myPosForTargetY, myPosForTargetZ - myWidth, myPosForTargetX + myWidth, myPosForTargetY + height, myPosForTargetZ + myWidth);
                final double myBestX = MathHelper.clamp_double(targetEyes.xCoord, myBB.minX, myBB.maxX);
                final double myBestY = MathHelper.clamp_double(targetEyes.yCoord, myBB.minY, myBB.maxY);
                final double myBestZ = MathHelper.clamp_double(targetEyes.zCoord, myBB.minZ, myBB.maxZ);

                if (shouldStop())
                    return;

                if (timeDelay.reached((long) delay.getValue())) {
                    if (mc.thePlayer.hurtTime == 0 && currentPosEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) <= maxRange.getValue() && targetEyes.distanceTo(new Vec3(myBestX, myBestY, myBestZ)) <= maxRange.getValue() && positionEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) >= minRange.getValue() && MoveUtil.isMoving()) {
                        if (balanceL < maxBalance.getValue()) {
                            mc.timer.timerSpeed = (float) lowt.getValue();
                            if (System.currentTimeMillis() - lastLagTime >= 50) {
                                balanceL++;
                                lastLagTime = System.currentTimeMillis();
                            }
                            return;
                        }

                        for (int i = 0; i < TimerTcik.getValue(); i++) {
                            mc.thePlayer.onUpdate();
                            mc.timer.timerSpeed = (float) firstTimer.getValue();
                        }
                        balanceL = 0L;
                    }
                }
            }
    }


    private boolean shouldStop() {
        boolean stop = false;

        if (!mc.gameSettings.keyBindForward.pressed && onlyf.getBoolean()) {
            stop = true;
            return stop;
        }

        if (onlyog.getBoolean() && !mc.thePlayer.onGround) {
            stop = true;
        }

        if (mc.thePlayer.getDistanceToEntity(target) <= minRange.getValue()){
            stop = true;
        }

        if (mc.thePlayer.hurtTime!=0){
            stop = true;
        }

        return stop;
    }

}
