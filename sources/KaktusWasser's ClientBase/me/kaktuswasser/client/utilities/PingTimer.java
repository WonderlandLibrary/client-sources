// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

public final class PingTimer
{
    private static float startTime;
    private static float endTime;
    private static float time;
    
    private static void update() {
        PingTimer.startTime = System.nanoTime();
        PingTimer.time = (PingTimer.startTime - PingTimer.endTime) / 1.0E9f;
    }
    
    public static float getTime() {
        update();
        return PingTimer.time;
    }
    
    public static void reset() {
        PingTimer.endTime = System.nanoTime();
        update();
    }
}
