package com.ibm.icu.util;

import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.impl.*;
import java.io.*;

public class MeasureUnit implements Serializable
{
    private static final long serialVersionUID = -1839973855554750484L;
    private static final Map<String, Map<String, MeasureUnit>> cache;
    private static boolean cacheIsPopulated;
    @Deprecated
    protected final String type;
    @Deprecated
    protected final String subType;
    static final UnicodeSet ASCII;
    static final UnicodeSet ASCII_HYPHEN_DIGITS;
    private static Factory UNIT_FACTORY;
    static Factory CURRENCY_FACTORY;
    static Factory TIMEUNIT_FACTORY;
    static Factory NOUNIT_FACTORY;
    public static final MeasureUnit G_FORCE;
    public static final MeasureUnit METER_PER_SECOND_SQUARED;
    public static final MeasureUnit ARC_MINUTE;
    public static final MeasureUnit ARC_SECOND;
    public static final MeasureUnit DEGREE;
    public static final MeasureUnit RADIAN;
    public static final MeasureUnit REVOLUTION_ANGLE;
    public static final MeasureUnit ACRE;
    public static final MeasureUnit HECTARE;
    public static final MeasureUnit SQUARE_CENTIMETER;
    public static final MeasureUnit SQUARE_FOOT;
    public static final MeasureUnit SQUARE_INCH;
    public static final MeasureUnit SQUARE_KILOMETER;
    public static final MeasureUnit SQUARE_METER;
    public static final MeasureUnit SQUARE_MILE;
    public static final MeasureUnit SQUARE_YARD;
    public static final MeasureUnit KARAT;
    public static final MeasureUnit MILLIGRAM_PER_DECILITER;
    public static final MeasureUnit MILLIMOLE_PER_LITER;
    public static final MeasureUnit PART_PER_MILLION;
    public static final MeasureUnit LITER_PER_100KILOMETERS;
    public static final MeasureUnit LITER_PER_KILOMETER;
    public static final MeasureUnit MILE_PER_GALLON;
    public static final MeasureUnit MILE_PER_GALLON_IMPERIAL;
    public static final MeasureUnit BIT;
    public static final MeasureUnit BYTE;
    public static final MeasureUnit GIGABIT;
    public static final MeasureUnit GIGABYTE;
    public static final MeasureUnit KILOBIT;
    public static final MeasureUnit KILOBYTE;
    public static final MeasureUnit MEGABIT;
    public static final MeasureUnit MEGABYTE;
    public static final MeasureUnit TERABIT;
    public static final MeasureUnit TERABYTE;
    public static final MeasureUnit CENTURY;
    public static final TimeUnit DAY;
    public static final TimeUnit HOUR;
    public static final MeasureUnit MICROSECOND;
    public static final MeasureUnit MILLISECOND;
    public static final TimeUnit MINUTE;
    public static final TimeUnit MONTH;
    public static final MeasureUnit NANOSECOND;
    public static final TimeUnit SECOND;
    public static final TimeUnit WEEK;
    public static final TimeUnit YEAR;
    public static final MeasureUnit AMPERE;
    public static final MeasureUnit MILLIAMPERE;
    public static final MeasureUnit OHM;
    public static final MeasureUnit VOLT;
    public static final MeasureUnit CALORIE;
    public static final MeasureUnit FOODCALORIE;
    public static final MeasureUnit JOULE;
    public static final MeasureUnit KILOCALORIE;
    public static final MeasureUnit KILOJOULE;
    public static final MeasureUnit KILOWATT_HOUR;
    public static final MeasureUnit GIGAHERTZ;
    public static final MeasureUnit HERTZ;
    public static final MeasureUnit KILOHERTZ;
    public static final MeasureUnit MEGAHERTZ;
    public static final MeasureUnit ASTRONOMICAL_UNIT;
    public static final MeasureUnit CENTIMETER;
    public static final MeasureUnit DECIMETER;
    public static final MeasureUnit FATHOM;
    public static final MeasureUnit FOOT;
    public static final MeasureUnit FURLONG;
    public static final MeasureUnit INCH;
    public static final MeasureUnit KILOMETER;
    public static final MeasureUnit LIGHT_YEAR;
    public static final MeasureUnit METER;
    public static final MeasureUnit MICROMETER;
    public static final MeasureUnit MILE;
    public static final MeasureUnit MILE_SCANDINAVIAN;
    public static final MeasureUnit MILLIMETER;
    public static final MeasureUnit NANOMETER;
    public static final MeasureUnit NAUTICAL_MILE;
    public static final MeasureUnit PARSEC;
    public static final MeasureUnit PICOMETER;
    public static final MeasureUnit POINT;
    public static final MeasureUnit YARD;
    public static final MeasureUnit LUX;
    public static final MeasureUnit CARAT;
    public static final MeasureUnit GRAM;
    public static final MeasureUnit KILOGRAM;
    public static final MeasureUnit METRIC_TON;
    public static final MeasureUnit MICROGRAM;
    public static final MeasureUnit MILLIGRAM;
    public static final MeasureUnit OUNCE;
    public static final MeasureUnit OUNCE_TROY;
    public static final MeasureUnit POUND;
    public static final MeasureUnit STONE;
    public static final MeasureUnit TON;
    public static final MeasureUnit GIGAWATT;
    public static final MeasureUnit HORSEPOWER;
    public static final MeasureUnit KILOWATT;
    public static final MeasureUnit MEGAWATT;
    public static final MeasureUnit MILLIWATT;
    public static final MeasureUnit WATT;
    public static final MeasureUnit HECTOPASCAL;
    public static final MeasureUnit INCH_HG;
    public static final MeasureUnit MILLIBAR;
    public static final MeasureUnit MILLIMETER_OF_MERCURY;
    public static final MeasureUnit POUND_PER_SQUARE_INCH;
    public static final MeasureUnit KILOMETER_PER_HOUR;
    public static final MeasureUnit KNOT;
    public static final MeasureUnit METER_PER_SECOND;
    public static final MeasureUnit MILE_PER_HOUR;
    public static final MeasureUnit CELSIUS;
    public static final MeasureUnit FAHRENHEIT;
    public static final MeasureUnit GENERIC_TEMPERATURE;
    public static final MeasureUnit KELVIN;
    public static final MeasureUnit ACRE_FOOT;
    public static final MeasureUnit BUSHEL;
    public static final MeasureUnit CENTILITER;
    public static final MeasureUnit CUBIC_CENTIMETER;
    public static final MeasureUnit CUBIC_FOOT;
    public static final MeasureUnit CUBIC_INCH;
    public static final MeasureUnit CUBIC_KILOMETER;
    public static final MeasureUnit CUBIC_METER;
    public static final MeasureUnit CUBIC_MILE;
    public static final MeasureUnit CUBIC_YARD;
    public static final MeasureUnit CUP;
    public static final MeasureUnit CUP_METRIC;
    public static final MeasureUnit DECILITER;
    public static final MeasureUnit FLUID_OUNCE;
    public static final MeasureUnit GALLON;
    public static final MeasureUnit GALLON_IMPERIAL;
    public static final MeasureUnit HECTOLITER;
    public static final MeasureUnit LITER;
    public static final MeasureUnit MEGALITER;
    public static final MeasureUnit MILLILITER;
    public static final MeasureUnit PINT;
    public static final MeasureUnit PINT_METRIC;
    public static final MeasureUnit QUART;
    public static final MeasureUnit TABLESPOON;
    public static final MeasureUnit TEASPOON;
    private static HashMap<Pair<MeasureUnit, MeasureUnit>, MeasureUnit> unitPerUnitToSingleUnit;
    
    @Deprecated
    protected MeasureUnit(final String type, final String subType) {
        this.type = type;
        this.subType = subType;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getSubtype() {
        return this.subType;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.type.hashCode() + this.subType.hashCode();
    }
    
    @Override
    public boolean equals(final Object rhs) {
        if (rhs == this) {
            return true;
        }
        if (!(rhs instanceof MeasureUnit)) {
            return false;
        }
        final MeasureUnit c = (MeasureUnit)rhs;
        return this.type.equals(c.type) && this.subType.equals(c.subType);
    }
    
    @Override
    public String toString() {
        return this.type + "-" + this.subType;
    }
    
    public static synchronized Set<String> getAvailableTypes() {
        populateCache();
        return Collections.unmodifiableSet((Set<? extends String>)MeasureUnit.cache.keySet());
    }
    
    public static synchronized Set<MeasureUnit> getAvailable(final String type) {
        populateCache();
        final Map<String, MeasureUnit> units = MeasureUnit.cache.get(type);
        return (units == null) ? Collections.emptySet() : Collections.unmodifiableSet((Set<? extends MeasureUnit>)new CollectionSet<MeasureUnit>(units.values()));
    }
    
    public static synchronized Set<MeasureUnit> getAvailable() {
        final Set<MeasureUnit> result = new HashSet<MeasureUnit>();
        for (final String type : new HashSet<String>(getAvailableTypes())) {
            for (final MeasureUnit unit : getAvailable(type)) {
                result.add(unit);
            }
        }
        return Collections.unmodifiableSet((Set<? extends MeasureUnit>)result);
    }
    
    @Deprecated
    public static MeasureUnit internalGetInstance(final String type, final String subType) {
        if (type == null || subType == null) {
            throw new NullPointerException("Type and subType must be non-null");
        }
        if (!"currency".equals(type) && (!MeasureUnit.ASCII.containsAll(type) || !MeasureUnit.ASCII_HYPHEN_DIGITS.containsAll(subType))) {
            throw new IllegalArgumentException("The type or subType are invalid.");
        }
        Factory factory;
        if ("currency".equals(type)) {
            factory = MeasureUnit.CURRENCY_FACTORY;
        }
        else if ("duration".equals(type)) {
            factory = MeasureUnit.TIMEUNIT_FACTORY;
        }
        else if ("none".equals(type)) {
            factory = MeasureUnit.NOUNIT_FACTORY;
        }
        else {
            factory = MeasureUnit.UNIT_FACTORY;
        }
        return addUnit(type, subType, factory);
    }
    
    @Deprecated
    public static MeasureUnit resolveUnitPerUnit(final MeasureUnit unit, final MeasureUnit perUnit) {
        return MeasureUnit.unitPerUnitToSingleUnit.get(Pair.of(unit, perUnit));
    }
    
    private static void populateCache() {
        if (MeasureUnit.cacheIsPopulated) {
            return;
        }
        MeasureUnit.cacheIsPopulated = true;
        final ICUResourceBundle rb1 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", "en");
        rb1.getAllItemsWithFallback("units", new MeasureUnitSink());
        final ICUResourceBundle rb2 = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "currencyNumericCodes", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        rb2.getAllItemsWithFallback("codeMap", new CurrencyNumericCodeSink());
    }
    
    @Deprecated
    protected static synchronized MeasureUnit addUnit(String type, final String unitName, final Factory factory) {
        Map<String, MeasureUnit> tmp = MeasureUnit.cache.get(type);
        if (tmp == null) {
            MeasureUnit.cache.put(type, tmp = new HashMap<String, MeasureUnit>());
        }
        else {
            type = tmp.entrySet().iterator().next().getValue().type;
        }
        MeasureUnit unit = tmp.get(unitName);
        if (unit == null) {
            tmp.put(unitName, unit = factory.create(type, unitName));
        }
        return unit;
    }
    
    private Object writeReplace() throws ObjectStreamException {
        return new MeasureUnitProxy(this.type, this.subType);
    }
    
    static {
        cache = new HashMap<String, Map<String, MeasureUnit>>();
        MeasureUnit.cacheIsPopulated = false;
        ASCII = new UnicodeSet(97, 122).freeze();
        ASCII_HYPHEN_DIGITS = new UnicodeSet(new int[] { 45, 45, 48, 57, 97, 122 }).freeze();
        MeasureUnit.UNIT_FACTORY = new Factory() {
            @Override
            public MeasureUnit create(final String type, final String subType) {
                return new MeasureUnit(type, subType);
            }
        };
        MeasureUnit.CURRENCY_FACTORY = new Factory() {
            @Override
            public MeasureUnit create(final String unusedType, final String subType) {
                return new Currency(subType);
            }
        };
        MeasureUnit.TIMEUNIT_FACTORY = new Factory() {
            @Override
            public MeasureUnit create(final String type, final String subType) {
                return new TimeUnit(type, subType);
            }
        };
        MeasureUnit.NOUNIT_FACTORY = new Factory() {
            @Override
            public MeasureUnit create(final String type, final String subType) {
                return new NoUnit(subType);
            }
        };
        G_FORCE = internalGetInstance("acceleration", "g-force");
        METER_PER_SECOND_SQUARED = internalGetInstance("acceleration", "meter-per-second-squared");
        ARC_MINUTE = internalGetInstance("angle", "arc-minute");
        ARC_SECOND = internalGetInstance("angle", "arc-second");
        DEGREE = internalGetInstance("angle", "degree");
        RADIAN = internalGetInstance("angle", "radian");
        REVOLUTION_ANGLE = internalGetInstance("angle", "revolution");
        ACRE = internalGetInstance("area", "acre");
        HECTARE = internalGetInstance("area", "hectare");
        SQUARE_CENTIMETER = internalGetInstance("area", "square-centimeter");
        SQUARE_FOOT = internalGetInstance("area", "square-foot");
        SQUARE_INCH = internalGetInstance("area", "square-inch");
        SQUARE_KILOMETER = internalGetInstance("area", "square-kilometer");
        SQUARE_METER = internalGetInstance("area", "square-meter");
        SQUARE_MILE = internalGetInstance("area", "square-mile");
        SQUARE_YARD = internalGetInstance("area", "square-yard");
        KARAT = internalGetInstance("concentr", "karat");
        MILLIGRAM_PER_DECILITER = internalGetInstance("concentr", "milligram-per-deciliter");
        MILLIMOLE_PER_LITER = internalGetInstance("concentr", "millimole-per-liter");
        PART_PER_MILLION = internalGetInstance("concentr", "part-per-million");
        LITER_PER_100KILOMETERS = internalGetInstance("consumption", "liter-per-100kilometers");
        LITER_PER_KILOMETER = internalGetInstance("consumption", "liter-per-kilometer");
        MILE_PER_GALLON = internalGetInstance("consumption", "mile-per-gallon");
        MILE_PER_GALLON_IMPERIAL = internalGetInstance("consumption", "mile-per-gallon-imperial");
        BIT = internalGetInstance("digital", "bit");
        BYTE = internalGetInstance("digital", "byte");
        GIGABIT = internalGetInstance("digital", "gigabit");
        GIGABYTE = internalGetInstance("digital", "gigabyte");
        KILOBIT = internalGetInstance("digital", "kilobit");
        KILOBYTE = internalGetInstance("digital", "kilobyte");
        MEGABIT = internalGetInstance("digital", "megabit");
        MEGABYTE = internalGetInstance("digital", "megabyte");
        TERABIT = internalGetInstance("digital", "terabit");
        TERABYTE = internalGetInstance("digital", "terabyte");
        CENTURY = internalGetInstance("duration", "century");
        DAY = (TimeUnit)internalGetInstance("duration", "day");
        HOUR = (TimeUnit)internalGetInstance("duration", "hour");
        MICROSECOND = internalGetInstance("duration", "microsecond");
        MILLISECOND = internalGetInstance("duration", "millisecond");
        MINUTE = (TimeUnit)internalGetInstance("duration", "minute");
        MONTH = (TimeUnit)internalGetInstance("duration", "month");
        NANOSECOND = internalGetInstance("duration", "nanosecond");
        SECOND = (TimeUnit)internalGetInstance("duration", "second");
        WEEK = (TimeUnit)internalGetInstance("duration", "week");
        YEAR = (TimeUnit)internalGetInstance("duration", "year");
        AMPERE = internalGetInstance("electric", "ampere");
        MILLIAMPERE = internalGetInstance("electric", "milliampere");
        OHM = internalGetInstance("electric", "ohm");
        VOLT = internalGetInstance("electric", "volt");
        CALORIE = internalGetInstance("energy", "calorie");
        FOODCALORIE = internalGetInstance("energy", "foodcalorie");
        JOULE = internalGetInstance("energy", "joule");
        KILOCALORIE = internalGetInstance("energy", "kilocalorie");
        KILOJOULE = internalGetInstance("energy", "kilojoule");
        KILOWATT_HOUR = internalGetInstance("energy", "kilowatt-hour");
        GIGAHERTZ = internalGetInstance("frequency", "gigahertz");
        HERTZ = internalGetInstance("frequency", "hertz");
        KILOHERTZ = internalGetInstance("frequency", "kilohertz");
        MEGAHERTZ = internalGetInstance("frequency", "megahertz");
        ASTRONOMICAL_UNIT = internalGetInstance("length", "astronomical-unit");
        CENTIMETER = internalGetInstance("length", "centimeter");
        DECIMETER = internalGetInstance("length", "decimeter");
        FATHOM = internalGetInstance("length", "fathom");
        FOOT = internalGetInstance("length", "foot");
        FURLONG = internalGetInstance("length", "furlong");
        INCH = internalGetInstance("length", "inch");
        KILOMETER = internalGetInstance("length", "kilometer");
        LIGHT_YEAR = internalGetInstance("length", "light-year");
        METER = internalGetInstance("length", "meter");
        MICROMETER = internalGetInstance("length", "micrometer");
        MILE = internalGetInstance("length", "mile");
        MILE_SCANDINAVIAN = internalGetInstance("length", "mile-scandinavian");
        MILLIMETER = internalGetInstance("length", "millimeter");
        NANOMETER = internalGetInstance("length", "nanometer");
        NAUTICAL_MILE = internalGetInstance("length", "nautical-mile");
        PARSEC = internalGetInstance("length", "parsec");
        PICOMETER = internalGetInstance("length", "picometer");
        POINT = internalGetInstance("length", "point");
        YARD = internalGetInstance("length", "yard");
        LUX = internalGetInstance("light", "lux");
        CARAT = internalGetInstance("mass", "carat");
        GRAM = internalGetInstance("mass", "gram");
        KILOGRAM = internalGetInstance("mass", "kilogram");
        METRIC_TON = internalGetInstance("mass", "metric-ton");
        MICROGRAM = internalGetInstance("mass", "microgram");
        MILLIGRAM = internalGetInstance("mass", "milligram");
        OUNCE = internalGetInstance("mass", "ounce");
        OUNCE_TROY = internalGetInstance("mass", "ounce-troy");
        POUND = internalGetInstance("mass", "pound");
        STONE = internalGetInstance("mass", "stone");
        TON = internalGetInstance("mass", "ton");
        GIGAWATT = internalGetInstance("power", "gigawatt");
        HORSEPOWER = internalGetInstance("power", "horsepower");
        KILOWATT = internalGetInstance("power", "kilowatt");
        MEGAWATT = internalGetInstance("power", "megawatt");
        MILLIWATT = internalGetInstance("power", "milliwatt");
        WATT = internalGetInstance("power", "watt");
        HECTOPASCAL = internalGetInstance("pressure", "hectopascal");
        INCH_HG = internalGetInstance("pressure", "inch-hg");
        MILLIBAR = internalGetInstance("pressure", "millibar");
        MILLIMETER_OF_MERCURY = internalGetInstance("pressure", "millimeter-of-mercury");
        POUND_PER_SQUARE_INCH = internalGetInstance("pressure", "pound-per-square-inch");
        KILOMETER_PER_HOUR = internalGetInstance("speed", "kilometer-per-hour");
        KNOT = internalGetInstance("speed", "knot");
        METER_PER_SECOND = internalGetInstance("speed", "meter-per-second");
        MILE_PER_HOUR = internalGetInstance("speed", "mile-per-hour");
        CELSIUS = internalGetInstance("temperature", "celsius");
        FAHRENHEIT = internalGetInstance("temperature", "fahrenheit");
        GENERIC_TEMPERATURE = internalGetInstance("temperature", "generic");
        KELVIN = internalGetInstance("temperature", "kelvin");
        ACRE_FOOT = internalGetInstance("volume", "acre-foot");
        BUSHEL = internalGetInstance("volume", "bushel");
        CENTILITER = internalGetInstance("volume", "centiliter");
        CUBIC_CENTIMETER = internalGetInstance("volume", "cubic-centimeter");
        CUBIC_FOOT = internalGetInstance("volume", "cubic-foot");
        CUBIC_INCH = internalGetInstance("volume", "cubic-inch");
        CUBIC_KILOMETER = internalGetInstance("volume", "cubic-kilometer");
        CUBIC_METER = internalGetInstance("volume", "cubic-meter");
        CUBIC_MILE = internalGetInstance("volume", "cubic-mile");
        CUBIC_YARD = internalGetInstance("volume", "cubic-yard");
        CUP = internalGetInstance("volume", "cup");
        CUP_METRIC = internalGetInstance("volume", "cup-metric");
        DECILITER = internalGetInstance("volume", "deciliter");
        FLUID_OUNCE = internalGetInstance("volume", "fluid-ounce");
        GALLON = internalGetInstance("volume", "gallon");
        GALLON_IMPERIAL = internalGetInstance("volume", "gallon-imperial");
        HECTOLITER = internalGetInstance("volume", "hectoliter");
        LITER = internalGetInstance("volume", "liter");
        MEGALITER = internalGetInstance("volume", "megaliter");
        MILLILITER = internalGetInstance("volume", "milliliter");
        PINT = internalGetInstance("volume", "pint");
        PINT_METRIC = internalGetInstance("volume", "pint-metric");
        QUART = internalGetInstance("volume", "quart");
        TABLESPOON = internalGetInstance("volume", "tablespoon");
        TEASPOON = internalGetInstance("volume", "teaspoon");
        (MeasureUnit.unitPerUnitToSingleUnit = new HashMap<Pair<MeasureUnit, MeasureUnit>, MeasureUnit>()).put(Pair.of(MeasureUnit.LITER, MeasureUnit.KILOMETER), MeasureUnit.LITER_PER_KILOMETER);
        MeasureUnit.unitPerUnitToSingleUnit.put(Pair.of(MeasureUnit.POUND, MeasureUnit.SQUARE_INCH), MeasureUnit.POUND_PER_SQUARE_INCH);
        MeasureUnit.unitPerUnitToSingleUnit.put((Pair<MeasureUnit, MeasureUnit>)Pair.of(MeasureUnit.MILE, MeasureUnit.HOUR), MeasureUnit.MILE_PER_HOUR);
        MeasureUnit.unitPerUnitToSingleUnit.put(Pair.of(MeasureUnit.MILLIGRAM, MeasureUnit.DECILITER), MeasureUnit.MILLIGRAM_PER_DECILITER);
        MeasureUnit.unitPerUnitToSingleUnit.put(Pair.of(MeasureUnit.MILE, MeasureUnit.GALLON_IMPERIAL), MeasureUnit.MILE_PER_GALLON_IMPERIAL);
        MeasureUnit.unitPerUnitToSingleUnit.put((Pair<MeasureUnit, MeasureUnit>)Pair.of(MeasureUnit.KILOMETER, MeasureUnit.HOUR), MeasureUnit.KILOMETER_PER_HOUR);
        MeasureUnit.unitPerUnitToSingleUnit.put(Pair.of(MeasureUnit.MILE, MeasureUnit.GALLON), MeasureUnit.MILE_PER_GALLON);
        MeasureUnit.unitPerUnitToSingleUnit.put((Pair<MeasureUnit, MeasureUnit>)Pair.of(MeasureUnit.METER, MeasureUnit.SECOND), MeasureUnit.METER_PER_SECOND);
    }
    
    private static final class MeasureUnitSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table unitTypesTable = value.getTable();
            for (int i2 = 0; unitTypesTable.getKeyAndValue(i2, key, value); ++i2) {
                if (!key.contentEquals("compound")) {
                    if (!key.contentEquals("coordinate")) {
                        final String unitType = key.toString();
                        final UResource.Table unitNamesTable = value.getTable();
                        for (int i3 = 0; unitNamesTable.getKeyAndValue(i3, key, value); ++i3) {
                            final String unitName = key.toString();
                            MeasureUnit.internalGetInstance(unitType, unitName);
                        }
                    }
                }
            }
        }
    }
    
    private static final class CurrencyNumericCodeSink extends UResource.Sink
    {
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table codesTable = value.getTable();
            for (int i1 = 0; codesTable.getKeyAndValue(i1, key, value); ++i1) {
                MeasureUnit.internalGetInstance("currency", key.toString());
            }
        }
    }
    
    static final class MeasureUnitProxy implements Externalizable
    {
        private static final long serialVersionUID = -3910681415330989598L;
        private String type;
        private String subType;
        
        public MeasureUnitProxy(final String type, final String subType) {
            this.type = type;
            this.subType = subType;
        }
        
        public MeasureUnitProxy() {
        }
        
        @Override
        public void writeExternal(final ObjectOutput out) throws IOException {
            out.writeByte(0);
            out.writeUTF(this.type);
            out.writeUTF(this.subType);
            out.writeShort(0);
        }
        
        @Override
        public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
            in.readByte();
            this.type = in.readUTF();
            this.subType = in.readUTF();
            final int extra = in.readShort();
            if (extra > 0) {
                final byte[] extraBytes = new byte[extra];
                in.read(extraBytes, 0, extra);
            }
        }
        
        private Object readResolve() throws ObjectStreamException {
            return MeasureUnit.internalGetInstance(this.type, this.subType);
        }
    }
    
    @Deprecated
    protected interface Factory
    {
        @Deprecated
        MeasureUnit create(final String p0, final String p1);
    }
}
