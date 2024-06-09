package ooo.cpacket.ruby.util;

public class TimeUnits
{
    public static final long SECOND = 1000L;
    public static final long MINUTE = 60000L;
    public static final long HOUR = 3600000L;
    public static final long DAY = 86400000L;
    public static final long WEEK = 604800000L;
    
    public long toMilliseconds(final double amount, final long convertFrom) {
        return convertFrom * (long)Math.floor(amount);
    }
    
    public double fromMilliseconds(final long milliseconds, final long convertTo) {
        return milliseconds / convertTo;
    }
}
