package me.travis.wurstplus.util;

import java.time.temporal.Temporal;
import java.time.Duration;
import java.time.Instant;

public class TimeUtils
{
    public TimeUtils() {
        super();
    }
    
    public static boolean hasTimePassed(final Instant start, final Instant current, final int seconds) {
        return Duration.between(start, current).getSeconds() >= seconds;
    }
}
