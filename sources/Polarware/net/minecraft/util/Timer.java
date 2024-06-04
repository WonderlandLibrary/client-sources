package net.minecraft.util;



import com.polarware.Client;
import com.polarware.module.impl.combat.TickBaseModule;
import com.polarware.util.math.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import util.time.StopWatch;

public class Timer
{
    float ticksPerSecond;
    private double lastHRTime;
    public int elapsedTicks;
    public float renderPartialTicks;
    public float timerSpeed;
    public float elapsedPartialTicks;
    private long lastSyncSysClock;
    private long lastSyncHRClock;
    private long field_74285_i;
    private double timeSyncAdjustment;
    private final StopWatch timeHelper;
    private final StopWatch timeHelper2;

    public Timer(final float p_i1018_1_) {
        this.timerSpeed = 1.0f;
        this.timeSyncAdjustment = 1.0;
        this.timeHelper = new StopWatch();
        this.timeHelper2 = new StopWatch();
        this.ticksPerSecond = p_i1018_1_;
        this.lastSyncSysClock = Minecraft.getSystemTime();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }

    public void updateTimer() {
        final long systemTime = Minecraft.getSystemTime();
        final long syncSysClockDiff = systemTime - this.lastSyncSysClock;
        final long syncHRClock = System.nanoTime() / 1000000L;
        final double hrTime = syncHRClock / 1000.0;
        if (syncSysClockDiff <= 1000L && syncSysClockDiff >= 0L) {
            this.field_74285_i += syncSysClockDiff;
            if (this.field_74285_i > 1000L) {
                final long hrClockDiff = syncHRClock - this.lastSyncHRClock;
                final double d1 = this.field_74285_i / (double)hrClockDiff;
                this.timeSyncAdjustment += (d1 - this.timeSyncAdjustment) * 0.20000000298023224;
                this.lastSyncHRClock = syncHRClock;
                this.field_74285_i = 0L;
            }
            if (this.field_74285_i < 0L) {
                this.lastSyncHRClock = syncHRClock;
            }
        }
        else {
            this.lastHRTime = hrTime;
        }
        this.lastSyncSysClock = systemTime;
        double d2 = (hrTime - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = hrTime;
        d2 = MathHelper.clamp_double(d2, 0.0, 1.0);
        this.elapsedPartialTicks += (float)(d2 * this.timerSpeed * this.ticksPerSecond);
        if (Client.INSTANCE.getModuleManager().get(TickBaseModule.class).isEnabled() && Minecraft.getMinecraft().inGameHasFocus && Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            float elapsedPartialTicks = (float)(this.elapsedPartialTicks + d2 * this.timerSpeed * this.ticksPerSecond);
            float elapsedTicks = (float)(int)elapsedPartialTicks;
            elapsedPartialTicks -= elapsedTicks;
            if (elapsedTicks > 10.0f) {
                elapsedTicks = 10.0f;
            }
            final float renderPartialTicks = mc.timer.renderPartialTicks;
            //Killaura target null check btw
            if (this.timeHelper.finished(7000L) && Client.INSTANCE.getModuleManager().get(TickBaseModule.class).balanceCounter < 250L) {
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
                        TickBaseModule tickbase = Client.INSTANCE.getModuleManager().get(TickBaseModule.class);
                        if (positionEyes.distanceTo(new Vec3(bestX, bestY, bestZ)) <= tickbase.range.getValue().floatValue() &&
                                targetEyes.distanceTo(new Vec3(myBestX, myBestY, myBestZ)) > tickbase.range.getValue().floatValue() &&
                                currentPosEyes.distanceTo(new Vec3(currentX, currentY, currentZ)) > tickbase.range.getValue().floatValue()) {

                            this.elapsedPartialTicks = (float) MathUtil.getRandom(tickbase.elapsted.getValue().floatValue(),tickbase.elapsted.getSecondValue().floatValue());
                            if(tickbase.shouldTimer.getValue()) {
                                mc.timer.timerSpeed = (float) MathUtil.getRandom(tickbase.starttimer.getValue().floatValue(), tickbase.starttimer.getSecondValue().floatValue());
                            }
                            this.timeHelper.reset();
                            break;
                        } else if(tickbase.shouldTimer.getValue()) {
                            mc.timer.timerSpeed = (float) MathUtil.getRandom(tickbase.endtimer.getValue().floatValue(), tickbase.endtimer.getSecondValue().floatValue());
                        }
                    }
                }
            }
        }
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= this.elapsedTicks;
        if (this.elapsedTicks > 10) {
            this.elapsedTicks = 10;
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}

