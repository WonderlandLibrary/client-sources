/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.math.BigDecimal;

public final class UniversalTimeScale {
    public static final int JAVA_TIME = 0;
    public static final int UNIX_TIME = 1;
    public static final int ICU4C_TIME = 2;
    public static final int WINDOWS_FILE_TIME = 3;
    public static final int DOTNET_DATE_TIME = 4;
    public static final int MAC_OLD_TIME = 5;
    public static final int MAC_TIME = 6;
    public static final int EXCEL_TIME = 7;
    public static final int DB2_TIME = 8;
    public static final int UNIX_MICROSECONDS_TIME = 9;
    @Deprecated
    public static final int MAX_SCALE = 10;
    public static final int UNITS_VALUE = 0;
    public static final int EPOCH_OFFSET_VALUE = 1;
    public static final int FROM_MIN_VALUE = 2;
    public static final int FROM_MAX_VALUE = 3;
    public static final int TO_MIN_VALUE = 4;
    public static final int TO_MAX_VALUE = 5;
    public static final int EPOCH_OFFSET_PLUS_1_VALUE = 6;
    @Deprecated
    public static final int EPOCH_OFFSET_MINUS_1_VALUE = 7;
    @Deprecated
    public static final int UNITS_ROUND_VALUE = 8;
    @Deprecated
    public static final int MIN_ROUND_VALUE = 9;
    @Deprecated
    public static final int MAX_ROUND_VALUE = 10;
    @Deprecated
    public static final int MAX_SCALE_VALUE = 11;
    private static final long ticks = 1L;
    private static final long microseconds = 10L;
    private static final long milliseconds = 10000L;
    private static final long seconds = 10000000L;
    private static final long minutes = 600000000L;
    private static final long hours = 36000000000L;
    private static final long days = 864000000000L;
    private static final TimeScaleData[] timeScaleTable = new TimeScaleData[]{new TimeScaleData(10000L, 621355968000000000L, -9223372036854774999L, 9223372036854774999L, -984472800485477L, 860201606885477L), new TimeScaleData(10000000L, 621355968000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -984472800485L, 860201606885L), new TimeScaleData(10000L, 621355968000000000L, -9223372036854774999L, 9223372036854774999L, -984472800485477L, 860201606885477L), new TimeScaleData(1L, 504911232000000000L, -8718460804854775808L, Long.MAX_VALUE, Long.MIN_VALUE, 8718460804854775807L), new TimeScaleData(1L, 0L, Long.MIN_VALUE, Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE), new TimeScaleData(10000000L, 600527520000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -982389955685L, 862284451685L), new TimeScaleData(10000000L, 631139040000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -985451107685L, 859223299685L), new TimeScaleData(864000000000L, 599265216000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -11368793L, 9981605L), new TimeScaleData(864000000000L, 599265216000000000L, Long.MIN_VALUE, Long.MAX_VALUE, -11368793L, 9981605L), new TimeScaleData(10L, 621355968000000000L, -9223372036854775804L, 0x7FFFFFFFFFFFFFFCL, -984472800485477580L, 860201606885477580L)};

    private UniversalTimeScale() {
    }

    public static long from(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.fromRangeCheck(l, n);
        return (l + timeScaleData.epochOffset) * timeScaleData.units;
    }

    public static BigDecimal bigDecimalFrom(double d, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(d));
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.add(bigDecimal3).multiply(bigDecimal2);
    }

    public static BigDecimal bigDecimalFrom(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal = new BigDecimal(l);
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.add(bigDecimal3).multiply(bigDecimal2);
    }

    public static BigDecimal bigDecimalFrom(BigDecimal bigDecimal, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.add(bigDecimal3).multiply(bigDecimal2);
    }

    public static long toLong(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.toRangeCheck(l, n);
        if (l < 0L) {
            if (l < timeScaleData.minRound) {
                return (l + timeScaleData.unitsRound) / timeScaleData.units - timeScaleData.epochOffsetP1;
            }
            return (l - timeScaleData.unitsRound) / timeScaleData.units - timeScaleData.epochOffset;
        }
        if (l > timeScaleData.maxRound) {
            return (l - timeScaleData.unitsRound) / timeScaleData.units - timeScaleData.epochOffsetM1;
        }
        return (l + timeScaleData.unitsRound) / timeScaleData.units - timeScaleData.epochOffset;
    }

    public static BigDecimal toBigDecimal(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal = new BigDecimal(l);
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.divide(bigDecimal2, 4).subtract(bigDecimal3);
    }

    public static BigDecimal toBigDecimal(BigDecimal bigDecimal, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.divide(bigDecimal2, 4).subtract(bigDecimal3);
    }

    private static TimeScaleData getTimeScaleData(int n) {
        if (n < 0 || n >= 10) {
            throw new IllegalArgumentException("scale out of range: " + n);
        }
        return timeScaleTable[n];
    }

    public static long getTimeScaleValue(int n, int n2) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        switch (n2) {
            case 0: {
                return timeScaleData.units;
            }
            case 1: {
                return timeScaleData.epochOffset;
            }
            case 2: {
                return timeScaleData.fromMin;
            }
            case 3: {
                return timeScaleData.fromMax;
            }
            case 4: {
                return timeScaleData.toMin;
            }
            case 5: {
                return timeScaleData.toMax;
            }
            case 6: {
                return timeScaleData.epochOffsetP1;
            }
            case 7: {
                return timeScaleData.epochOffsetM1;
            }
            case 8: {
                return timeScaleData.unitsRound;
            }
            case 9: {
                return timeScaleData.minRound;
            }
            case 10: {
                return timeScaleData.maxRound;
            }
        }
        throw new IllegalArgumentException("value out of range: " + n2);
    }

    private static TimeScaleData toRangeCheck(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        if (l >= timeScaleData.toMin && l <= timeScaleData.toMax) {
            return timeScaleData;
        }
        throw new IllegalArgumentException("universalTime out of range:" + l);
    }

    private static TimeScaleData fromRangeCheck(long l, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        if (l >= timeScaleData.fromMin && l <= timeScaleData.fromMax) {
            return timeScaleData;
        }
        throw new IllegalArgumentException("otherTime out of range:" + l);
    }

    @Deprecated
    public static BigDecimal toBigDecimalTrunc(BigDecimal bigDecimal, int n) {
        TimeScaleData timeScaleData = UniversalTimeScale.getTimeScaleData(n);
        BigDecimal bigDecimal2 = new BigDecimal(timeScaleData.units);
        BigDecimal bigDecimal3 = new BigDecimal(timeScaleData.epochOffset);
        return bigDecimal.divide(bigDecimal2, 1).subtract(bigDecimal3);
    }

    private static final class TimeScaleData {
        long units;
        long epochOffset;
        long fromMin;
        long fromMax;
        long toMin;
        long toMax;
        long epochOffsetP1;
        long epochOffsetM1;
        long unitsRound;
        long minRound;
        long maxRound;

        TimeScaleData(long l, long l2, long l3, long l4, long l5, long l6) {
            this.units = l;
            this.unitsRound = l / 2L;
            this.minRound = Long.MIN_VALUE + this.unitsRound;
            this.maxRound = Long.MAX_VALUE - this.unitsRound;
            this.epochOffset = l2 / l;
            if (l == 1L) {
                this.epochOffsetP1 = this.epochOffsetM1 = this.epochOffset;
            } else {
                this.epochOffsetP1 = this.epochOffset + 1L;
                this.epochOffsetM1 = this.epochOffset - 1L;
            }
            this.toMin = l3;
            this.toMax = l4;
            this.fromMin = l5;
            this.fromMax = l6;
        }
    }
}

