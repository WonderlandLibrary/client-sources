package net.minecraft.util;

import best.actinium.Actinium;
import best.actinium.module.impl.combat.TickBaseModule;
import best.actinium.util.io.TimerUtil;
import best.actinium.util.math.MathUtil;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
/**
 * @author Nyghtfull
 * @since 1/11/2024
 */
public class Timer
{
    float ticksPerSecond;
    private double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float timerSpeed = 1.0F;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private long lastSyncHRClock;
    private long counter;
    private double timeSyncAdjustment = 1.0D;
    private final TimerUtil timeHelper = new TimerUtil(), timerUtil = new TimerUtil(), timerUtil2 = new TimerUtil(),
    timerUtil3 = new TimerUtil();
    private boolean shouldSkipTicks = false;

    public Timer(float tps)
    {
        this.ticksPerSecond = tps;
        this.lastSyncSysClock = Minecraft.getSystemTime();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }

    public void updateTimer()
    {
        long i = Minecraft.getSystemTime();
        long j = i - this.lastSyncSysClock;
        long k = System.nanoTime() / 1000000L;
        double d0 = (double)k / 1000.0D;

        if (j <= 1000L && j >= 0L)
        {
            this.counter += j;

            if (this.counter > 1000L)
            {
                long l = k - this.lastSyncHRClock;
                double d1 = (double)this.counter / (double)l;
                this.timeSyncAdjustment += (d1 - this.timeSyncAdjustment) * 0.20000000298023224D;
                this.lastSyncHRClock = k;
                this.counter = 0L;
            }

            if (this.counter < 0L)
            {
                this.lastSyncHRClock = k;
            }
        }
        else
        {
            this.lastHRTime = d0;
        }

        this.lastSyncSysClock = i;
        double d2 = (d0 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = d0 ;
        d2 = MathHelper.clamp_double(d2, 0.0, 1.0);
        this.elapsedPartialTicks += (float)(d2 * this.timerSpeed * this.ticksPerSecond);
        if (Actinium.INSTANCE.getModuleManager().get(TickBaseModule.class).isEnabled() && Minecraft.getMinecraft().inGameHasFocus && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            float elapsedPartialTicks = (float)(this.elapsedPartialTicks + d2 * this.timerSpeed * this.ticksPerSecond);
            float elapsedTicks = (float)(int)elapsedPartialTicks;
            elapsedPartialTicks -= elapsedTicks;
            if (elapsedTicks > 10.0f) {
                elapsedTicks = 10.0f;
            }
            TickBaseModule tickbase = Actinium.INSTANCE.getModuleManager().get(TickBaseModule.class);
            final float renderPartialTicks = mc.timer.renderPartialTicks;
            //Killaura target null check btw
            //7000L that the delay between timerange so cool down
            if (this.timeHelper.finished(tickbase.coolDown.getValue().longValue()) && Actinium.INSTANCE.getModuleManager().get(TickBaseModule.class).balanceCounter < 250L) {
                for (final Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityOtherPlayerMP) {
                        final EntityOtherPlayerMP player = (EntityOtherPlayerMP)entity;
                        final float width = player.width / 2.0f;
                        final float height = player.height;
                        final double posXNow = player.lastTickPosX + (player.posX - player.lastTickPosX) * renderPartialTicks;
                        final double posYNow = player.lastTickPosY + (player.posY - player.lastTickPosY) * renderPartialTicks;
                        final double posZNow = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * renderPartialTicks;
                        final double posX2 = player.otherPlayerMPX;
                        final double posY2 = player.otherPlayerMPY;
                        final double posZ2 = player.otherPlayerMPZ;
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
                        if (positionEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) <= tickbase.minRange.getValue().floatValue() &&
                                targetEyes.distanceTo(new Vec3(myBestX, myBestY, myBestZ)) > tickbase.minRange.getValue().floatValue() &&
                                currentPosEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) > tickbase.minRange.getValue().floatValue()) {

                            if(tickbase.shouldTimer.isEnabled()) {
                                mc.timer.timerSpeed = RandomUtil.getShitRandom(tickbase.minStarTimer.getValue().floatValue(), tickbase.maxStarTimer.getValue().floatValue());
                            }

                            if(!tickbase.shouldLastLonger.isEnabled()) {
                                this.elapsedPartialTicks = RandomUtil.getShitRandom(tickbase.minElapsted.getValue().floatValue(),tickbase.maxElapsted.getValue().floatValue());
                            }
                            this.timeHelper.reset();
                            this.timerUtil.reset();
                            this.timerUtil2.reset();
                            this.timerUtil3.reset();
                            break;
                        }
                    }
                }
            }

            if(tickbase.shouldLastLonger.isEnabled() && !timerUtil3.hasTimeElapsed(tickbase.skipTimeAmount.getValue().longValue())) {
                this.elapsedPartialTicks = RandomUtil.getShitRandom(tickbase.minElapsted.getValue().floatValue(),tickbase.maxElapsted.getValue().floatValue());
            }

            //maybe once mmc dont use timer
            //ok idk why i got banned on mcc with it when i freez the game prob i have to high
            //I FINALLY DID IT MAKE it unoption reset timer after or something ms and value
            //boost value
            if(timerUtil.hasTimeElapsed(tickbase.boostTime.getValue().floatValue())) {
              //  ChatUtil.display("Boost");
                mc.timer.timerSpeed = RandomUtil.getShitRandom(tickbase.minEndTimer.getValue().floatValue(), tickbase.maxEndTimer.getValue().floatValue());
            }
            //reset value
            if(timerUtil2.hasTimeElapsed(tickbase.resetTime.getValue().floatValue())) {
               // ChatUtil.display("Final reset");
                mc.timer.timerSpeed = 1;
            }
        //    ChatUtil.display(timeHelper.getTime());
            //  mc.timer.timerSpeed = RandomUtil.getShitRandom(tickbase.minEndTimer.getValue().floatValue(), tickbase.maxEndTimer.getValue().floatValue());
        }
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}
