/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Date;
import java.util.TimeZone;

public class CalendarAstronomer {
    public static final double SIDEREAL_DAY = 23.93446960027;
    public static final double SOLAR_DAY = 24.065709816;
    public static final double SYNODIC_MONTH = 29.530588853;
    public static final double SIDEREAL_MONTH = 27.32166;
    public static final double TROPICAL_YEAR = 365.242191;
    public static final double SIDEREAL_YEAR = 365.25636;
    public static final int SECOND_MS = 1000;
    public static final int MINUTE_MS = 60000;
    public static final int HOUR_MS = 3600000;
    public static final long DAY_MS = 86400000L;
    public static final long JULIAN_EPOCH_MS = -210866760000000L;
    static final long EPOCH_2000_MS = 946598400000L;
    private static final double PI = Math.PI;
    private static final double PI2 = Math.PI * 2;
    private static final double RAD_HOUR = 3.819718634205488;
    private static final double DEG_RAD = Math.PI / 180;
    private static final double RAD_DEG = 57.29577951308232;
    static final double JD_EPOCH = 2447891.5;
    static final double SUN_ETA_G = 4.87650757829735;
    static final double SUN_OMEGA_G = 4.935239984568769;
    static final double SUN_E = 0.016713;
    public static final SolarLongitude VERNAL_EQUINOX = new SolarLongitude(0.0);
    public static final SolarLongitude SUMMER_SOLSTICE = new SolarLongitude(1.5707963267948966);
    public static final SolarLongitude AUTUMN_EQUINOX = new SolarLongitude(Math.PI);
    public static final SolarLongitude WINTER_SOLSTICE = new SolarLongitude(4.71238898038469);
    static final double moonL0 = 5.556284436750021;
    static final double moonP0 = 0.6342598060246725;
    static final double moonN0 = 5.559050068029439;
    static final double moonI = 0.08980357792017056;
    static final double moonE = 0.0549;
    static final double moonA = 384401.0;
    static final double moonT0 = 0.009042550854582622;
    static final double moonPi = 0.016592845198710092;
    public static final MoonAge NEW_MOON = new MoonAge(0.0);
    public static final MoonAge FIRST_QUARTER = new MoonAge(1.5707963267948966);
    public static final MoonAge FULL_MOON = new MoonAge(Math.PI);
    public static final MoonAge LAST_QUARTER = new MoonAge(4.71238898038469);
    private long time;
    private double fLongitude = 0.0;
    private double fLatitude = 0.0;
    private long fGmtOffset = 0L;
    private static final double INVALID = Double.MIN_VALUE;
    private transient double julianDay = Double.MIN_VALUE;
    private transient double julianCentury = Double.MIN_VALUE;
    private transient double sunLongitude = Double.MIN_VALUE;
    private transient double meanAnomalySun = Double.MIN_VALUE;
    private transient double moonLongitude = Double.MIN_VALUE;
    private transient double moonEclipLong = Double.MIN_VALUE;
    private transient double eclipObliquity = Double.MIN_VALUE;
    private transient double siderealT0 = Double.MIN_VALUE;
    private transient double siderealTime = Double.MIN_VALUE;
    private transient Equatorial moonPosition = null;

    public CalendarAstronomer() {
        this(System.currentTimeMillis());
    }

    public CalendarAstronomer(Date date) {
        this(date.getTime());
    }

    public CalendarAstronomer(long l) {
        this.time = l;
    }

    public CalendarAstronomer(double d, double d2) {
        this();
        this.fLongitude = CalendarAstronomer.normPI(d * (Math.PI / 180));
        this.fLatitude = CalendarAstronomer.normPI(d2 * (Math.PI / 180));
        this.fGmtOffset = (long)(this.fLongitude * 24.0 * 3600000.0 / (Math.PI * 2));
    }

    public void setTime(long l) {
        this.time = l;
        this.clearCache();
    }

    public void setDate(Date date) {
        this.setTime(date.getTime());
    }

    public void setJulianDay(double d) {
        this.time = (long)(d * 8.64E7) + -210866760000000L;
        this.clearCache();
        this.julianDay = d;
    }

    public long getTime() {
        return this.time;
    }

    public Date getDate() {
        return new Date(this.time);
    }

    public double getJulianDay() {
        if (this.julianDay == Double.MIN_VALUE) {
            this.julianDay = (double)(this.time - -210866760000000L) / 8.64E7;
        }
        return this.julianDay;
    }

    public double getJulianCentury() {
        if (this.julianCentury == Double.MIN_VALUE) {
            this.julianCentury = (this.getJulianDay() - 2415020.0) / 36525.0;
        }
        return this.julianCentury;
    }

    public double getGreenwichSidereal() {
        if (this.siderealTime == Double.MIN_VALUE) {
            double d = CalendarAstronomer.normalize((double)this.time / 3600000.0, 24.0);
            this.siderealTime = CalendarAstronomer.normalize(this.getSiderealOffset() + d * 1.002737909, 24.0);
        }
        return this.siderealTime;
    }

    private double getSiderealOffset() {
        if (this.siderealT0 == Double.MIN_VALUE) {
            double d = Math.floor(this.getJulianDay() - 0.5) + 0.5;
            double d2 = d - 2451545.0;
            double d3 = d2 / 36525.0;
            this.siderealT0 = CalendarAstronomer.normalize(6.697374558 + 2400.051336 * d3 + 2.5862E-5 * d3 * d3, 24.0);
        }
        return this.siderealT0;
    }

    public double getLocalSidereal() {
        return CalendarAstronomer.normalize(this.getGreenwichSidereal() + (double)this.fGmtOffset / 3600000.0, 24.0);
    }

    private long lstToUT(double d) {
        double d2 = CalendarAstronomer.normalize((d - this.getSiderealOffset()) * 0.9972695663, 24.0);
        long l = 86400000L * ((this.time + this.fGmtOffset) / 86400000L) - this.fGmtOffset;
        return l + (long)(d2 * 3600000.0);
    }

    public final Equatorial eclipticToEquatorial(Ecliptic ecliptic) {
        return this.eclipticToEquatorial(ecliptic.longitude, ecliptic.latitude);
    }

    public final Equatorial eclipticToEquatorial(double d, double d2) {
        double d3 = this.eclipticObliquity();
        double d4 = Math.sin(d3);
        double d5 = Math.cos(d3);
        double d6 = Math.sin(d);
        double d7 = Math.cos(d);
        double d8 = Math.sin(d2);
        double d9 = Math.cos(d2);
        double d10 = Math.tan(d2);
        return new Equatorial(Math.atan2(d6 * d5 - d10 * d4, d7), Math.asin(d8 * d5 + d9 * d4 * d6));
    }

    public final Equatorial eclipticToEquatorial(double d) {
        return this.eclipticToEquatorial(d, 0.0);
    }

    public Horizon eclipticToHorizon(double d) {
        Equatorial equatorial = this.eclipticToEquatorial(d);
        double d2 = this.getLocalSidereal() * Math.PI / 12.0 - equatorial.ascension;
        double d3 = Math.sin(d2);
        double d4 = Math.cos(d2);
        double d5 = Math.sin(equatorial.declination);
        double d6 = Math.cos(equatorial.declination);
        double d7 = Math.sin(this.fLatitude);
        double d8 = Math.cos(this.fLatitude);
        double d9 = Math.asin(d5 * d7 + d6 * d8 * d4);
        double d10 = Math.atan2(-d6 * d8 * d3, d5 - d7 * Math.sin(d9));
        return new Horizon(d10, d9);
    }

    public double getSunLongitude() {
        if (this.sunLongitude == Double.MIN_VALUE) {
            double[] dArray = this.getSunLongitude(this.getJulianDay());
            this.sunLongitude = dArray[0];
            this.meanAnomalySun = dArray[1];
        }
        return this.sunLongitude;
    }

    double[] getSunLongitude(double d) {
        double d2 = d - 2447891.5;
        double d3 = CalendarAstronomer.norm2PI(0.017202791632524146 * d2);
        double d4 = CalendarAstronomer.norm2PI(d3 + 4.87650757829735 - 4.935239984568769);
        return new double[]{CalendarAstronomer.norm2PI(this.trueAnomaly(d4, 0.016713) + 4.935239984568769), d4};
    }

    public Equatorial getSunPosition() {
        return this.eclipticToEquatorial(this.getSunLongitude(), 0.0);
    }

    public long getSunTime(double d, boolean bl) {
        return this.timeOfAngle(new AngleFunc(this){
            final CalendarAstronomer this$0;
            {
                this.this$0 = calendarAstronomer;
            }

            @Override
            public double eval() {
                return this.this$0.getSunLongitude();
            }
        }, d, 365.242191, 60000L, bl);
    }

    public long getSunTime(SolarLongitude solarLongitude, boolean bl) {
        return this.getSunTime(solarLongitude.value, bl);
    }

    public long getSunRiseSet(boolean bl) {
        long l = this.time;
        long l2 = (this.time + this.fGmtOffset) / 86400000L * 86400000L - this.fGmtOffset + 43200000L;
        this.setTime(l2 + (bl ? -6L : 6L) * 3600000L);
        long l3 = this.riseOrSet(new CoordFunc(this){
            final CalendarAstronomer this$0;
            {
                this.this$0 = calendarAstronomer;
            }

            @Override
            public Equatorial eval() {
                return this.this$0.getSunPosition();
            }
        }, bl, 0.009302604913129777, 0.009890199094634533, 5000L);
        this.setTime(l);
        return l3;
    }

    public Equatorial getMoonPosition() {
        if (this.moonPosition == null) {
            double d = this.getSunLongitude();
            double d2 = this.getJulianDay() - 2447891.5;
            double d3 = CalendarAstronomer.norm2PI(0.22997150421858628 * d2 + 5.556284436750021);
            double d4 = CalendarAstronomer.norm2PI(d3 - 0.001944368345221015 * d2 - 0.6342598060246725);
            double d5 = 0.022233749341155764 * Math.sin(2.0 * (d3 - d) - d4);
            double d6 = 0.003242821750205464 * Math.sin(this.meanAnomalySun);
            double d7 = 0.00645771823237902 * Math.sin(this.meanAnomalySun);
            double d8 = 0.10975677534091541 * Math.sin(d4 += d5 - d6 - d7);
            double d9 = 0.0037350045992678655 * Math.sin(2.0 * d4);
            this.moonLongitude = d3 + d5 + d8 - d6 + d9;
            double d10 = 0.011489502465878671 * Math.sin(2.0 * (this.moonLongitude - d));
            this.moonLongitude += d10;
            double d11 = CalendarAstronomer.norm2PI(5.559050068029439 - 9.242199067718253E-4 * d2);
            double d12 = Math.sin(this.moonLongitude - (d11 -= 0.0027925268031909274 * Math.sin(this.meanAnomalySun)));
            double d13 = Math.cos(this.moonLongitude - d11);
            this.moonEclipLong = Math.atan2(d12 * Math.cos(0.08980357792017056), d13) + d11;
            double d14 = Math.asin(d12 * Math.sin(0.08980357792017056));
            this.moonPosition = this.eclipticToEquatorial(this.moonEclipLong, d14);
        }
        return this.moonPosition;
    }

    public double getMoonAge() {
        this.getMoonPosition();
        return CalendarAstronomer.norm2PI(this.moonEclipLong - this.sunLongitude);
    }

    public double getMoonPhase() {
        return 0.5 * (1.0 - Math.cos(this.getMoonAge()));
    }

    public long getMoonTime(double d, boolean bl) {
        return this.timeOfAngle(new AngleFunc(this){
            final CalendarAstronomer this$0;
            {
                this.this$0 = calendarAstronomer;
            }

            @Override
            public double eval() {
                return this.this$0.getMoonAge();
            }
        }, d, 29.530588853, 60000L, bl);
    }

    public long getMoonTime(MoonAge moonAge, boolean bl) {
        return this.getMoonTime(moonAge.value, bl);
    }

    public long getMoonRiseSet(boolean bl) {
        return this.riseOrSet(new CoordFunc(this){
            final CalendarAstronomer this$0;
            {
                this.this$0 = calendarAstronomer;
            }

            @Override
            public Equatorial eval() {
                return this.this$0.getMoonPosition();
            }
        }, bl, 0.009302604913129777, 0.009890199094634533, 60000L);
    }

    private long timeOfAngle(AngleFunc angleFunc, double d, double d2, long l, boolean bl) {
        double d3;
        double d4 = angleFunc.eval();
        double d5 = CalendarAstronomer.norm2PI(d - d4);
        double d6 = d3 = (d5 + (bl ? 0.0 : Math.PI * -2)) * (d2 * 8.64E7) / (Math.PI * 2);
        long l2 = this.time;
        this.setTime(this.time + (long)d3);
        do {
            double d7 = angleFunc.eval();
            double d8 = Math.abs(d3 / CalendarAstronomer.normPI(d7 - d4));
            d3 = CalendarAstronomer.normPI(d - d7) * d8;
            if (Math.abs(d3) > Math.abs(d6)) {
                long l3 = (long)(d2 * 8.64E7 / 8.0);
                this.setTime(l2 + (bl ? l3 : -l3));
                return this.timeOfAngle(angleFunc, d, d2, l, bl);
            }
            d6 = d3;
            d4 = d7;
            this.setTime(this.time + (long)d3);
        } while (Math.abs(d3) > (double)l);
        return this.time;
    }

    private long riseOrSet(CoordFunc coordFunc, boolean bl, double d, double d2, long l) {
        double d3;
        double d4;
        Equatorial equatorial = null;
        double d5 = Math.tan(this.fLatitude);
        long l2 = Long.MAX_VALUE;
        int n = 0;
        do {
            equatorial = coordFunc.eval();
            d4 = Math.acos(-d5 * Math.tan(equatorial.declination));
            d3 = ((bl ? Math.PI * 2 - d4 : d4) + equatorial.ascension) * 24.0 / (Math.PI * 2);
            long l3 = this.lstToUT(d3);
            l2 = l3 - this.time;
            this.setTime(l3);
        } while (++n < 5 && Math.abs(l2) > l);
        d4 = Math.cos(equatorial.declination);
        d3 = Math.acos(Math.sin(this.fLatitude) / d4);
        double d6 = d / 2.0 + d2;
        double d7 = Math.asin(Math.sin(d6) / Math.sin(d3));
        long l4 = (long)(240.0 * d7 * 57.29577951308232 / d4 * 1000.0);
        return this.time + (bl ? -l4 : l4);
    }

    private static final double normalize(double d, double d2) {
        return d - d2 * Math.floor(d / d2);
    }

    private static final double norm2PI(double d) {
        return CalendarAstronomer.normalize(d, Math.PI * 2);
    }

    private static final double normPI(double d) {
        return CalendarAstronomer.normalize(d + Math.PI, Math.PI * 2) - Math.PI;
    }

    private double trueAnomaly(double d, double d2) {
        double d3;
        double d4 = d;
        do {
            d3 = d4 - d2 * Math.sin(d4) - d;
            d4 -= d3 / (1.0 - d2 * Math.cos(d4));
        } while (Math.abs(d3) > 1.0E-5);
        return 2.0 * Math.atan(Math.tan(d4 / 2.0) * Math.sqrt((1.0 + d2) / (1.0 - d2)));
    }

    private double eclipticObliquity() {
        if (this.eclipObliquity == Double.MIN_VALUE) {
            double d = 2451545.0;
            double d2 = (this.getJulianDay() - 2451545.0) / 36525.0;
            this.eclipObliquity = 23.439292 - 0.013004166666666666 * d2 - 1.6666666666666665E-7 * d2 * d2 + 5.027777777777778E-7 * d2 * d2 * d2;
            this.eclipObliquity *= Math.PI / 180;
        }
        return this.eclipObliquity;
    }

    private void clearCache() {
        this.julianDay = Double.MIN_VALUE;
        this.julianCentury = Double.MIN_VALUE;
        this.sunLongitude = Double.MIN_VALUE;
        this.meanAnomalySun = Double.MIN_VALUE;
        this.moonLongitude = Double.MIN_VALUE;
        this.moonEclipLong = Double.MIN_VALUE;
        this.eclipObliquity = Double.MIN_VALUE;
        this.siderealTime = Double.MIN_VALUE;
        this.siderealT0 = Double.MIN_VALUE;
        this.moonPosition = null;
    }

    public String local(long l) {
        return new Date(l - (long)TimeZone.getDefault().getRawOffset()).toString();
    }

    private static String radToHms(double d) {
        int n = (int)(d * 3.819718634205488);
        int n2 = (int)((d * 3.819718634205488 - (double)n) * 60.0);
        int n3 = (int)((d * 3.819718634205488 - (double)n - (double)n2 / 60.0) * 3600.0);
        return Integer.toString(n) + "h" + n2 + "m" + n3 + "s";
    }

    private static String radToDms(double d) {
        int n = (int)(d * 57.29577951308232);
        int n2 = (int)((d * 57.29577951308232 - (double)n) * 60.0);
        int n3 = (int)((d * 57.29577951308232 - (double)n - (double)n2 / 60.0) * 3600.0);
        return Integer.toString(n) + "\u00b0" + n2 + "'" + n3 + "\"";
    }

    static String access$000(double d) {
        return CalendarAstronomer.radToHms(d);
    }

    static String access$100(double d) {
        return CalendarAstronomer.radToDms(d);
    }

    public static final class Horizon {
        public final double altitude;
        public final double azimuth;

        public Horizon(double d, double d2) {
            this.altitude = d;
            this.azimuth = d2;
        }

        public String toString() {
            return Double.toString(this.altitude * 57.29577951308232) + "," + this.azimuth * 57.29577951308232;
        }
    }

    public static final class Equatorial {
        public final double ascension;
        public final double declination;

        public Equatorial(double d, double d2) {
            this.ascension = d;
            this.declination = d2;
        }

        public String toString() {
            return Double.toString(this.ascension * 57.29577951308232) + "," + this.declination * 57.29577951308232;
        }

        public String toHmsString() {
            return CalendarAstronomer.access$000(this.ascension) + "," + CalendarAstronomer.access$100(this.declination);
        }
    }

    public static final class Ecliptic {
        public final double latitude;
        public final double longitude;

        public Ecliptic(double d, double d2) {
            this.latitude = d;
            this.longitude = d2;
        }

        public String toString() {
            return Double.toString(this.longitude * 57.29577951308232) + "," + this.latitude * 57.29577951308232;
        }
    }

    private static interface CoordFunc {
        public Equatorial eval();
    }

    private static interface AngleFunc {
        public double eval();
    }

    private static class MoonAge {
        double value;

        MoonAge(double d) {
            this.value = d;
        }
    }

    private static class SolarLongitude {
        double value;

        SolarLongitude(double d) {
            this.value = d;
        }
    }
}

