/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.CurrencyData;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleFormatterImpl;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.UResource;
import com.ibm.icu.impl.number.DecimalQuantity;
import com.ibm.icu.impl.number.MicroProps;
import com.ibm.icu.impl.number.MicroPropsGenerator;
import com.ibm.icu.impl.number.Modifier;
import com.ibm.icu.impl.number.ModifierStore;
import com.ibm.icu.impl.number.RoundingUtils;
import com.ibm.icu.impl.number.SimpleModifier;
import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.PluralRules;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.MeasureUnit;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.EnumMap;
import java.util.Map;
import java.util.MissingResourceException;

public class LongNameHandler
implements MicroPropsGenerator,
ModifierStore {
    private static final int DNAM_INDEX = StandardPlural.COUNT;
    private static final int PER_INDEX = StandardPlural.COUNT + 1;
    private static final int ARRAY_LENGTH = StandardPlural.COUNT + 2;
    private final Map<StandardPlural, SimpleModifier> modifiers;
    private final PluralRules rules;
    private final MicroPropsGenerator parent;

    private static int getIndex(String string) {
        if (string.equals("dnam")) {
            return DNAM_INDEX;
        }
        if (string.equals("per")) {
            return PER_INDEX;
        }
        return StandardPlural.fromString(string).ordinal();
    }

    private static String getWithPlural(String[] stringArray, StandardPlural standardPlural) {
        String string = stringArray[standardPlural.ordinal()];
        if (string == null) {
            string = stringArray[StandardPlural.OTHER.ordinal()];
        }
        if (string == null) {
            throw new ICUException("Could not find data in 'other' plural variant");
        }
        return string;
    }

    private static void getMeasureData(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, String[] stringArray) {
        PluralTableSink pluralTableSink = new PluralTableSink(stringArray);
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/unit", uLocale);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            stringBuilder.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            stringBuilder.append("Short");
        }
        stringBuilder.append("/");
        stringBuilder.append(measureUnit.getType());
        stringBuilder.append("/");
        if (measureUnit.getSubtype().endsWith("-person")) {
            stringBuilder.append(measureUnit.getSubtype(), 0, measureUnit.getSubtype().length() - 7);
        } else {
            stringBuilder.append(measureUnit.getSubtype());
        }
        try {
            iCUResourceBundle.getAllItemsWithFallback(stringBuilder.toString(), pluralTableSink);
        } catch (MissingResourceException missingResourceException) {
            throw new IllegalArgumentException("No data for unit " + measureUnit + ", width " + (Object)((Object)unitWidth), missingResourceException);
        }
    }

    private static void getCurrencyLongNameData(ULocale uLocale, Currency currency, String[] stringArray) {
        Map<String, String> map = CurrencyData.provider.getInstance(uLocale, true).getUnitPatterns();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String string = entry.getKey();
            int n = LongNameHandler.getIndex(string);
            String string2 = currency.getName(uLocale, 2, string, null);
            String string3 = entry.getValue();
            stringArray[n] = string3 = string3.replace("{1}", string2);
        }
    }

    private static String getPerUnitFormat(ULocale uLocale, NumberFormatter.UnitWidth unitWidth) {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/unit", uLocale);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("units");
        if (unitWidth == NumberFormatter.UnitWidth.NARROW) {
            stringBuilder.append("Narrow");
        } else if (unitWidth == NumberFormatter.UnitWidth.SHORT) {
            stringBuilder.append("Short");
        }
        stringBuilder.append("/compound/per");
        try {
            return iCUResourceBundle.getStringWithFallback(stringBuilder.toString());
        } catch (MissingResourceException missingResourceException) {
            throw new IllegalArgumentException("Could not find x-per-y format for " + uLocale + ", width " + (Object)((Object)unitWidth));
        }
    }

    private LongNameHandler(Map<StandardPlural, SimpleModifier> map, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        this.modifiers = map;
        this.rules = pluralRules;
        this.parent = microPropsGenerator;
    }

    public static String getUnitDisplayName(ULocale uLocale, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth) {
        String[] stringArray = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData(uLocale, measureUnit, unitWidth, stringArray);
        return stringArray[DNAM_INDEX];
    }

    public static LongNameHandler forCurrencyLongNames(ULocale uLocale, Currency currency, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        String[] stringArray = new String[ARRAY_LENGTH];
        LongNameHandler.getCurrencyLongNameData(uLocale, currency, stringArray);
        EnumMap<StandardPlural, SimpleModifier> enumMap = new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class);
        LongNameHandler longNameHandler = new LongNameHandler(enumMap, pluralRules, microPropsGenerator);
        longNameHandler.simpleFormatsToModifiers(stringArray, NumberFormat.Field.CURRENCY);
        return longNameHandler;
    }

    public static LongNameHandler forMeasureUnit(ULocale uLocale, MeasureUnit stringArray, MeasureUnit measureUnit, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        Object object;
        if (measureUnit != null) {
            object = MeasureUnit.resolveUnitPerUnit((MeasureUnit)stringArray, measureUnit);
            if (object != null) {
                stringArray = object;
            } else {
                return LongNameHandler.forCompoundUnit(uLocale, (MeasureUnit)stringArray, measureUnit, unitWidth, pluralRules, microPropsGenerator);
            }
        }
        object = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData(uLocale, (MeasureUnit)stringArray, unitWidth, object);
        EnumMap<StandardPlural, SimpleModifier> enumMap = new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class);
        LongNameHandler longNameHandler = new LongNameHandler(enumMap, pluralRules, microPropsGenerator);
        longNameHandler.simpleFormatsToModifiers((String[])object, NumberFormat.Field.MEASURE_UNIT);
        return longNameHandler;
    }

    private static LongNameHandler forCompoundUnit(ULocale uLocale, MeasureUnit measureUnit, MeasureUnit measureUnit2, NumberFormatter.UnitWidth unitWidth, PluralRules pluralRules, MicroPropsGenerator microPropsGenerator) {
        Object object;
        Object object2;
        String string;
        String[] stringArray = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData(uLocale, measureUnit, unitWidth, stringArray);
        String[] stringArray2 = new String[ARRAY_LENGTH];
        LongNameHandler.getMeasureData(uLocale, measureUnit2, unitWidth, stringArray2);
        if (stringArray2[PER_INDEX] != null) {
            string = stringArray2[PER_INDEX];
        } else {
            object2 = LongNameHandler.getPerUnitFormat(uLocale, unitWidth);
            object = new StringBuilder();
            String string2 = SimpleFormatterImpl.compileToStringMinMaxArguments((CharSequence)object2, (StringBuilder)object, 2, 2);
            String string3 = LongNameHandler.getWithPlural(stringArray2, StandardPlural.ONE);
            String string4 = SimpleFormatterImpl.compileToStringMinMaxArguments(string3, (StringBuilder)object, 1, 1);
            String string5 = SimpleFormatterImpl.getTextWithNoArguments(string4).trim();
            string = SimpleFormatterImpl.formatCompiledPattern(string2, "{0}", string5);
        }
        object2 = new EnumMap(StandardPlural.class);
        object = new LongNameHandler((Map<StandardPlural, SimpleModifier>)object2, pluralRules, microPropsGenerator);
        super.multiSimpleFormatsToModifiers(stringArray, string, NumberFormat.Field.MEASURE_UNIT);
        return object;
    }

    private void simpleFormatsToModifiers(String[] stringArray, NumberFormat.Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String string = LongNameHandler.getWithPlural(stringArray, standardPlural);
            String string2 = SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = 0;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(string2, field, false, parameters));
        }
    }

    private void multiSimpleFormatsToModifiers(String[] stringArray, String string, NumberFormat.Field field) {
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = SimpleFormatterImpl.compileToStringMinMaxArguments(string, stringBuilder, 1, 1);
        for (StandardPlural standardPlural : StandardPlural.VALUES) {
            String string3 = LongNameHandler.getWithPlural(stringArray, standardPlural);
            String string4 = SimpleFormatterImpl.formatCompiledPattern(string2, string3);
            String string5 = SimpleFormatterImpl.compileToStringMinMaxArguments(string4, stringBuilder, 0, 1);
            Modifier.Parameters parameters = new Modifier.Parameters();
            parameters.obj = this;
            parameters.signum = 0;
            parameters.plural = standardPlural;
            this.modifiers.put(standardPlural, new SimpleModifier(string5, field, false, parameters));
        }
    }

    @Override
    public MicroProps processQuantity(DecimalQuantity decimalQuantity) {
        MicroProps microProps = this.parent.processQuantity(decimalQuantity);
        StandardPlural standardPlural = RoundingUtils.getPluralSafe(microProps.rounder, this.rules, decimalQuantity);
        microProps.modOuter = this.modifiers.get((Object)standardPlural);
        return microProps;
    }

    @Override
    public Modifier getModifier(int n, StandardPlural standardPlural) {
        return this.modifiers.get((Object)standardPlural);
    }

    static int access$000(String string) {
        return LongNameHandler.getIndex(string);
    }

    private static final class PluralTableSink
    extends UResource.Sink {
        String[] outArray;

        public PluralTableSink(String[] stringArray) {
            this.outArray = stringArray;
        }

        @Override
        public void put(UResource.Key key, UResource.Value value, boolean bl) {
            UResource.Table table = value.getTable();
            int n = 0;
            while (table.getKeyAndValue(n, key, value)) {
                int n2 = LongNameHandler.access$000(key.toString());
                if (this.outArray[n2] == null) {
                    String string;
                    this.outArray[n2] = string = value.getString();
                }
                ++n;
            }
        }
    }
}

