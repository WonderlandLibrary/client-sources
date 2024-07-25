package club.bluezenith.util.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.reverse;

public class MathUtil {
    public static double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        if(Double.isInfinite(value) || Double.isNaN(value)) return 0;
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static double round(double value) {
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
    }

    public static float randomFloat(float min, float max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

    public static String convertMSToTimeString(long time, boolean letters) {

        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        reverse(units);

        final StringBuilder builder = new StringBuilder();

        for (TimeUnit unit : units) {

            long diff = unit.convert(time, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            time = time - diffInMilliesForUnit;

            if(diff > 0) {
                switch (unit) {
                    case DAYS: builder.append(diff).append(letters ? "d " : ":"); break;
                    case HOURS: builder.append(diff).append(letters ? "h " : ":"); break;
                    case MINUTES: builder.append(diff).append(letters ? "m " : ":"); break;
                    case SECONDS: builder.append(diff).append(letters ? "s " : ":"); break;
                }
            }
        }
        final String result = builder.toString();
        return result.length() > 0 ? result.substring(0, result.length() - 1) : result;
    }

    public static String convertMillisToAltPlaytime(long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    public static String convertSecondsToHMmSs(long seconds) {
        long s = seconds % 60;
        long m = (seconds / 60) % 60;
        return String.format("%01d:%02d", m,s);
    }

    public static int getRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * (max - min) + min);
    }

    public static long getRandomLong(long min, long max) {
        return (long) (Math.random() * (max - min) + min);
    }
}
