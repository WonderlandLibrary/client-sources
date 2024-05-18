package net.minecraft.util;

import net.minecraft.client.*;

public class Timer
{
    private long lastSyncSysClock;
    public float timerSpeed;
    private double lastHRTime;
    private long lastSyncHRClock;
    private double timeSyncAdjustment;
    public float elapsedPartialTicks;
    private long field_74285_i;
    public float renderPartialTicks;
    public int elapsedTicks;
    float ticksPerSecond;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public Timer(final float ticksPerSecond) {
        this.timerSpeed = 1.0f;
        this.timeSyncAdjustment = 1.0;
        this.ticksPerSecond = ticksPerSecond;
        this.lastSyncSysClock = Minecraft.getSystemTime();
        this.lastSyncHRClock = System.nanoTime() / 1000000L;
    }
    
    public void updateTimer() {
        final long systemTime = Minecraft.getSystemTime();
        final long n = systemTime - this.lastSyncSysClock;
        final long n2 = System.nanoTime() / 1000000L;
        final double n3 = n2 / 1000.0;
        if (n <= 1000L && n >= 0L) {
            this.field_74285_i += n;
            if (this.field_74285_i > 1000L) {
                this.timeSyncAdjustment += (this.field_74285_i / (n2 - this.lastSyncHRClock) - this.timeSyncAdjustment) * 0.20000000298023224;
                this.lastSyncHRClock = n2;
                this.field_74285_i = 0L;
            }
            if (this.field_74285_i < 0L) {
                this.lastSyncHRClock = n2;
                "".length();
                if (3 == 4) {
                    throw null;
                }
            }
        }
        else {
            this.lastHRTime = n3;
        }
        this.lastSyncSysClock = systemTime;
        final double n4 = (n3 - this.lastHRTime) * this.timeSyncAdjustment;
        this.lastHRTime = n3;
        this.elapsedPartialTicks += (float)(MathHelper.clamp_double(n4, 0.0, 1.0) * this.timerSpeed * this.ticksPerSecond);
        this.elapsedTicks = (int)this.elapsedPartialTicks;
        this.elapsedPartialTicks -= this.elapsedTicks;
        if (this.elapsedTicks > (0x34 ^ 0x3E)) {
            this.elapsedTicks = (0x2 ^ 0x8);
        }
        this.renderPartialTicks = this.elapsedPartialTicks;
    }
}
