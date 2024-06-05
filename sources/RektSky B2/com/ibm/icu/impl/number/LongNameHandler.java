package com.ibm.icu.impl.number;

import com.ibm.icu.number.*;
import com.ibm.icu.util.*;
import java.util.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;

public class LongNameHandler implements MicroPropsGenerator
{
    private static final int DNAM_INDEX;
    private static final int PER_INDEX;
    private static final int ARRAY_LENGTH;
    private final Map<StandardPlural, SimpleModifier> modifiers;
    private final PluralRules rules;
    private final MicroPropsGenerator parent;
    
    private static int getIndex(final String pluralKeyword) {
        if (pluralKeyword.equals("dnam")) {
            return LongNameHandler.DNAM_INDEX;
        }
        if (pluralKeyword.equals("per")) {
            return LongNameHandler.PER_INDEX;
        }
        return StandardPlural.fromString(pluralKeyword).ordinal();
    }
    
    private static String getWithPlural(final String[] strings, final StandardPlural plural) {
        String result = strings[plural.ordinal()];
        if (result == null) {
            result = strings[StandardPlural.OTHER.ordinal()];
        }
        if (result == null) {
            throw new ICUException("Could not find data in 'other' plural variant");
        }
        return result;
    }
    
    private static void getMeasureData(final ULocale locale, final MeasureUnit unit, final NumberFormatter.UnitWidth width, final String[] outArray) {
        final PluralTableSink sink = new PluralTableSink(outArray);
        final ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", locale);
        final StringBuilder key = new StringBuilder();
        key.append("units");
        if (width == NumberFormatter.UnitWidth.NARROW) {
            key.append("Narrow");
        }
        else if (width == NumberFormatter.UnitWidth.SHORT) {
            key.append("Short");
        }
        key.append("/");
        key.append(unit.getType());
        key.append("/");
        key.append(unit.getSubtype());
        try {
            resource.getAllItemsWithFallback(key.toString(), sink);
        }
        catch (MissingResourceException e) {
            throw new IllegalArgumentException("No data for unit " + unit + ", width " + width, e);
        }
    }
    
    private static void getCurrencyLongNameData(final ULocale locale, final Currency currency, final String[] outArray) {
        final Map<String, String> data = CurrencyData.provider.getInstance(locale, true).getUnitPatterns();
        for (final Map.Entry<String, String> e : data.entrySet()) {
            final String pluralKeyword = e.getKey();
            final int index = getIndex(pluralKeyword);
            final String longName = currency.getName(locale, 2, pluralKeyword, null);
            String simpleFormat = e.getValue();
            simpleFormat = simpleFormat.replace("{1}", longName);
            outArray[index] = simpleFormat;
        }
    }
    
    private static String getPerUnitFormat(final ULocale locale, final NumberFormatter.UnitWidth width) {
        final ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/unit", locale);
        final StringBuilder key = new StringBuilder();
        key.append("units");
        if (width == NumberFormatter.UnitWidth.NARROW) {
            key.append("Narrow");
        }
        else if (width == NumberFormatter.UnitWidth.SHORT) {
            key.append("Short");
        }
        key.append("/compound/per");
        try {
            return resource.getStringWithFallback(key.toString());
        }
        catch (MissingResourceException e) {
            throw new IllegalArgumentException("Could not find x-per-y format for " + locale + ", width " + width);
        }
    }
    
    private LongNameHandler(final Map<StandardPlural, SimpleModifier> modifiers, final PluralRules rules, final MicroPropsGenerator parent) {
        this.modifiers = modifiers;
        this.rules = rules;
        this.parent = parent;
    }
    
    public static String getUnitDisplayName(final ULocale locale, final MeasureUnit unit, final NumberFormatter.UnitWidth width) {
        final String[] measureData = new String[LongNameHandler.ARRAY_LENGTH];
        getMeasureData(locale, unit, width, measureData);
        return measureData[LongNameHandler.DNAM_INDEX];
    }
    
    public static LongNameHandler forCurrencyLongNames(final ULocale locale, final Currency currency, final PluralRules rules, final MicroPropsGenerator parent) {
        final String[] simpleFormats = new String[LongNameHandler.ARRAY_LENGTH];
        getCurrencyLongNameData(locale, currency, simpleFormats);
        final Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class);
        simpleFormatsToModifiers(simpleFormats, null, modifiers);
        return new LongNameHandler(modifiers, rules, parent);
    }
    
    public static LongNameHandler forMeasureUnit(final ULocale locale, MeasureUnit unit, final MeasureUnit perUnit, final NumberFormatter.UnitWidth width, final PluralRules rules, final MicroPropsGenerator parent) {
        if (perUnit != null) {
            final MeasureUnit simplified = MeasureUnit.resolveUnitPerUnit(unit, perUnit);
            if (simplified == null) {
                return forCompoundUnit(locale, unit, perUnit, width, rules, parent);
            }
            unit = simplified;
        }
        final String[] simpleFormats = new String[LongNameHandler.ARRAY_LENGTH];
        getMeasureData(locale, unit, width, simpleFormats);
        final Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class);
        simpleFormatsToModifiers(simpleFormats, null, modifiers);
        return new LongNameHandler(modifiers, rules, parent);
    }
    
    private static LongNameHandler forCompoundUnit(final ULocale locale, final MeasureUnit unit, final MeasureUnit perUnit, final NumberFormatter.UnitWidth width, final PluralRules rules, final MicroPropsGenerator parent) {
        final String[] primaryData = new String[LongNameHandler.ARRAY_LENGTH];
        getMeasureData(locale, unit, width, primaryData);
        final String[] secondaryData = new String[LongNameHandler.ARRAY_LENGTH];
        getMeasureData(locale, perUnit, width, secondaryData);
        String perUnitFormat;
        if (secondaryData[LongNameHandler.PER_INDEX] != null) {
            perUnitFormat = secondaryData[LongNameHandler.PER_INDEX];
        }
        else {
            final String rawPerUnitFormat = getPerUnitFormat(locale, width);
            final StringBuilder sb = new StringBuilder();
            final String compiled = SimpleFormatterImpl.compileToStringMinMaxArguments(rawPerUnitFormat, sb, 2, 2);
            final String secondaryFormat = getWithPlural(secondaryData, StandardPlural.ONE);
            final String secondaryCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(secondaryFormat, sb, 1, 1);
            final String secondaryString = SimpleFormatterImpl.getTextWithNoArguments(secondaryCompiled).trim();
            perUnitFormat = SimpleFormatterImpl.formatCompiledPattern(compiled, "{0}", secondaryString);
        }
        final Map<StandardPlural, SimpleModifier> modifiers = new EnumMap<StandardPlural, SimpleModifier>(StandardPlural.class);
        multiSimpleFormatsToModifiers(primaryData, perUnitFormat, null, modifiers);
        return new LongNameHandler(modifiers, rules, parent);
    }
    
    private static void simpleFormatsToModifiers(final String[] simpleFormats, final NumberFormat.Field field, final Map<StandardPlural, SimpleModifier> output) {
        final StringBuilder sb = new StringBuilder();
        for (final StandardPlural plural : StandardPlural.VALUES) {
            final String simpleFormat = getWithPlural(simpleFormats, plural);
            final String compiled = SimpleFormatterImpl.compileToStringMinMaxArguments(simpleFormat, sb, 0, 1);
            output.put(plural, new SimpleModifier(compiled, field, false));
        }
    }
    
    private static void multiSimpleFormatsToModifiers(final String[] leadFormats, final String trailFormat, final NumberFormat.Field field, final Map<StandardPlural, SimpleModifier> output) {
        final StringBuilder sb = new StringBuilder();
        final String trailCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(trailFormat, sb, 1, 1);
        for (final StandardPlural plural : StandardPlural.VALUES) {
            final String leadFormat = getWithPlural(leadFormats, plural);
            final String compoundFormat = SimpleFormatterImpl.formatCompiledPattern(trailCompiled, leadFormat);
            final String compoundCompiled = SimpleFormatterImpl.compileToStringMinMaxArguments(compoundFormat, sb, 0, 1);
            output.put(plural, new SimpleModifier(compoundCompiled, field, false));
        }
    }
    
    @Override
    public MicroProps processQuantity(final DecimalQuantity quantity) {
        final MicroProps micros = this.parent.processQuantity(quantity);
        final DecimalQuantity copy = quantity.createCopy();
        micros.rounder.apply(copy);
        micros.modOuter = this.modifiers.get(copy.getStandardPlural(this.rules));
        return micros;
    }
    
    static {
        DNAM_INDEX = StandardPlural.COUNT;
        PER_INDEX = StandardPlural.COUNT + 1;
        ARRAY_LENGTH = StandardPlural.COUNT + 2;
    }
    
    private static final class PluralTableSink extends UResource.Sink
    {
        String[] outArray;
        
        public PluralTableSink(final String[] outArray) {
            this.outArray = outArray;
        }
        
        @Override
        public void put(final UResource.Key key, final UResource.Value value, final boolean noFallback) {
            final UResource.Table pluralsTable = value.getTable();
            for (int i = 0; pluralsTable.getKeyAndValue(i, key, value); ++i) {
                final int index = getIndex(key.toString());
                if (this.outArray[index] == null) {
                    final String formatString = value.getString();
                    this.outArray[index] = formatString;
                }
            }
        }
    }
}
