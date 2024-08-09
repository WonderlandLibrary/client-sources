/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.duration.impl;

import com.ibm.icu.impl.duration.impl.RecordReader;
import com.ibm.icu.impl.duration.impl.RecordWriter;
import java.util.ArrayList;

public class DataRecord {
    byte pl;
    String[][] pluralNames;
    byte[] genders;
    String[] singularNames;
    String[] halfNames;
    String[] numberNames;
    String[] mediumNames;
    String[] shortNames;
    String[] measures;
    String[] rqdSuffixes;
    String[] optSuffixes;
    String[] halves;
    byte[] halfPlacements;
    byte[] halfSupport;
    String fifteenMinutes;
    String fiveMinutes;
    boolean requiresDigitSeparator;
    String digitPrefix;
    String countSep;
    String shortUnitSep;
    String[] unitSep;
    boolean[] unitSepRequiresDP;
    boolean[] requiresSkipMarker;
    byte numberSystem;
    char zero;
    char decimalSep;
    boolean omitSingularCount;
    boolean omitDualCount;
    byte zeroHandling;
    byte decimalHandling;
    byte fractionHandling;
    String skippedUnitMarker;
    boolean allowZero;
    boolean weeksAloneOnly;
    byte useMilliseconds;
    ScopeData[] scopeData;

    public static DataRecord read(String string, RecordReader recordReader) {
        if (recordReader.open("DataRecord")) {
            DataRecord dataRecord = new DataRecord();
            dataRecord.pl = recordReader.namedIndex("pl", EPluralization.names);
            dataRecord.pluralNames = recordReader.stringTable("pluralName");
            dataRecord.genders = recordReader.namedIndexArray("gender", EGender.names);
            dataRecord.singularNames = recordReader.stringArray("singularName");
            dataRecord.halfNames = recordReader.stringArray("halfName");
            dataRecord.numberNames = recordReader.stringArray("numberName");
            dataRecord.mediumNames = recordReader.stringArray("mediumName");
            dataRecord.shortNames = recordReader.stringArray("shortName");
            dataRecord.measures = recordReader.stringArray("measure");
            dataRecord.rqdSuffixes = recordReader.stringArray("rqdSuffix");
            dataRecord.optSuffixes = recordReader.stringArray("optSuffix");
            dataRecord.halves = recordReader.stringArray("halves");
            dataRecord.halfPlacements = recordReader.namedIndexArray("halfPlacement", EHalfPlacement.names);
            dataRecord.halfSupport = recordReader.namedIndexArray("halfSupport", EHalfSupport.names);
            dataRecord.fifteenMinutes = recordReader.string("fifteenMinutes");
            dataRecord.fiveMinutes = recordReader.string("fiveMinutes");
            dataRecord.requiresDigitSeparator = recordReader.bool("requiresDigitSeparator");
            dataRecord.digitPrefix = recordReader.string("digitPrefix");
            dataRecord.countSep = recordReader.string("countSep");
            dataRecord.shortUnitSep = recordReader.string("shortUnitSep");
            dataRecord.unitSep = recordReader.stringArray("unitSep");
            dataRecord.unitSepRequiresDP = recordReader.boolArray("unitSepRequiresDP");
            dataRecord.requiresSkipMarker = recordReader.boolArray("requiresSkipMarker");
            dataRecord.numberSystem = recordReader.namedIndex("numberSystem", ENumberSystem.names);
            dataRecord.zero = recordReader.character("zero");
            dataRecord.decimalSep = recordReader.character("decimalSep");
            dataRecord.omitSingularCount = recordReader.bool("omitSingularCount");
            dataRecord.omitDualCount = recordReader.bool("omitDualCount");
            dataRecord.zeroHandling = recordReader.namedIndex("zeroHandling", EZeroHandling.names);
            dataRecord.decimalHandling = recordReader.namedIndex("decimalHandling", EDecimalHandling.names);
            dataRecord.fractionHandling = recordReader.namedIndex("fractionHandling", EFractionHandling.names);
            dataRecord.skippedUnitMarker = recordReader.string("skippedUnitMarker");
            dataRecord.allowZero = recordReader.bool("allowZero");
            dataRecord.weeksAloneOnly = recordReader.bool("weeksAloneOnly");
            dataRecord.useMilliseconds = recordReader.namedIndex("useMilliseconds", EMilliSupport.names);
            if (recordReader.open("ScopeDataList")) {
                ScopeData scopeData;
                ArrayList<ScopeData> arrayList = new ArrayList<ScopeData>();
                while (null != (scopeData = ScopeData.read(recordReader))) {
                    arrayList.add(scopeData);
                }
                if (recordReader.close()) {
                    dataRecord.scopeData = arrayList.toArray(new ScopeData[arrayList.size()]);
                }
            }
            if (recordReader.close()) {
                return dataRecord;
            }
        } else {
            throw new InternalError("did not find DataRecord while reading " + string);
        }
        throw new InternalError("null data read while reading " + string);
    }

    public void write(RecordWriter recordWriter) {
        recordWriter.open("DataRecord");
        recordWriter.namedIndex("pl", EPluralization.names, this.pl);
        recordWriter.stringTable("pluralName", this.pluralNames);
        recordWriter.namedIndexArray("gender", EGender.names, this.genders);
        recordWriter.stringArray("singularName", this.singularNames);
        recordWriter.stringArray("halfName", this.halfNames);
        recordWriter.stringArray("numberName", this.numberNames);
        recordWriter.stringArray("mediumName", this.mediumNames);
        recordWriter.stringArray("shortName", this.shortNames);
        recordWriter.stringArray("measure", this.measures);
        recordWriter.stringArray("rqdSuffix", this.rqdSuffixes);
        recordWriter.stringArray("optSuffix", this.optSuffixes);
        recordWriter.stringArray("halves", this.halves);
        recordWriter.namedIndexArray("halfPlacement", EHalfPlacement.names, this.halfPlacements);
        recordWriter.namedIndexArray("halfSupport", EHalfSupport.names, this.halfSupport);
        recordWriter.string("fifteenMinutes", this.fifteenMinutes);
        recordWriter.string("fiveMinutes", this.fiveMinutes);
        recordWriter.bool("requiresDigitSeparator", this.requiresDigitSeparator);
        recordWriter.string("digitPrefix", this.digitPrefix);
        recordWriter.string("countSep", this.countSep);
        recordWriter.string("shortUnitSep", this.shortUnitSep);
        recordWriter.stringArray("unitSep", this.unitSep);
        recordWriter.boolArray("unitSepRequiresDP", this.unitSepRequiresDP);
        recordWriter.boolArray("requiresSkipMarker", this.requiresSkipMarker);
        recordWriter.namedIndex("numberSystem", ENumberSystem.names, this.numberSystem);
        recordWriter.character("zero", this.zero);
        recordWriter.character("decimalSep", this.decimalSep);
        recordWriter.bool("omitSingularCount", this.omitSingularCount);
        recordWriter.bool("omitDualCount", this.omitDualCount);
        recordWriter.namedIndex("zeroHandling", EZeroHandling.names, this.zeroHandling);
        recordWriter.namedIndex("decimalHandling", EDecimalHandling.names, this.decimalHandling);
        recordWriter.namedIndex("fractionHandling", EFractionHandling.names, this.fractionHandling);
        recordWriter.string("skippedUnitMarker", this.skippedUnitMarker);
        recordWriter.bool("allowZero", this.allowZero);
        recordWriter.bool("weeksAloneOnly", this.weeksAloneOnly);
        recordWriter.namedIndex("useMilliseconds", EMilliSupport.names, this.useMilliseconds);
        if (this.scopeData != null) {
            recordWriter.open("ScopeDataList");
            for (int i = 0; i < this.scopeData.length; ++i) {
                this.scopeData[i].write(recordWriter);
            }
            recordWriter.close();
        }
        recordWriter.close();
    }

    public static interface EGender {
        public static final byte M = 0;
        public static final byte F = 1;
        public static final byte N = 2;
        public static final String[] names = new String[]{"M", "F", "N"};
    }

    public static interface ESeparatorVariant {
        public static final byte NONE = 0;
        public static final byte SHORT = 1;
        public static final byte FULL = 2;
        public static final String[] names = new String[]{"NONE", "SHORT", "FULL"};
    }

    public static interface EMilliSupport {
        public static final byte YES = 0;
        public static final byte NO = 1;
        public static final byte WITH_SECONDS = 2;
        public static final String[] names = new String[]{"YES", "NO", "WITH_SECONDS"};
    }

    public static interface EHalfSupport {
        public static final byte YES = 0;
        public static final byte NO = 1;
        public static final byte ONE_PLUS = 2;
        public static final String[] names = new String[]{"YES", "NO", "ONE_PLUS"};
    }

    public static interface EFractionHandling {
        public static final byte FPLURAL = 0;
        public static final byte FSINGULAR_PLURAL = 1;
        public static final byte FSINGULAR_PLURAL_ANDAHALF = 2;
        public static final byte FPAUCAL = 3;
        public static final String[] names = new String[]{"FPLURAL", "FSINGULAR_PLURAL", "FSINGULAR_PLURAL_ANDAHALF", "FPAUCAL"};
    }

    public static interface EDecimalHandling {
        public static final byte DPLURAL = 0;
        public static final byte DSINGULAR = 1;
        public static final byte DSINGULAR_SUBONE = 2;
        public static final byte DPAUCAL = 3;
        public static final String[] names = new String[]{"DPLURAL", "DSINGULAR", "DSINGULAR_SUBONE", "DPAUCAL"};
    }

    public static interface EZeroHandling {
        public static final byte ZPLURAL = 0;
        public static final byte ZSINGULAR = 1;
        public static final String[] names = new String[]{"ZPLURAL", "ZSINGULAR"};
    }

    public static interface ENumberSystem {
        public static final byte DEFAULT = 0;
        public static final byte CHINESE_TRADITIONAL = 1;
        public static final byte CHINESE_SIMPLIFIED = 2;
        public static final byte KOREAN = 3;
        public static final String[] names = new String[]{"DEFAULT", "CHINESE_TRADITIONAL", "CHINESE_SIMPLIFIED", "KOREAN"};
    }

    public static interface EHalfPlacement {
        public static final byte PREFIX = 0;
        public static final byte AFTER_FIRST = 1;
        public static final byte LAST = 2;
        public static final String[] names = new String[]{"PREFIX", "AFTER_FIRST", "LAST"};
    }

    public static interface EPluralization {
        public static final byte NONE = 0;
        public static final byte PLURAL = 1;
        public static final byte DUAL = 2;
        public static final byte PAUCAL = 3;
        public static final byte HEBREW = 4;
        public static final byte ARABIC = 5;
        public static final String[] names = new String[]{"NONE", "PLURAL", "DUAL", "PAUCAL", "HEBREW", "ARABIC"};
    }

    public static interface ECountVariant {
        public static final byte INTEGER = 0;
        public static final byte INTEGER_CUSTOM = 1;
        public static final byte HALF_FRACTION = 2;
        public static final byte DECIMAL1 = 3;
        public static final byte DECIMAL2 = 4;
        public static final byte DECIMAL3 = 5;
        public static final String[] names = new String[]{"INTEGER", "INTEGER_CUSTOM", "HALF_FRACTION", "DECIMAL1", "DECIMAL2", "DECIMAL3"};
    }

    public static interface EUnitVariant {
        public static final byte PLURALIZED = 0;
        public static final byte MEDIUM = 1;
        public static final byte SHORT = 2;
        public static final String[] names = new String[]{"PLURALIZED", "MEDIUM", "SHORT"};
    }

    public static interface ETimeDirection {
        public static final byte NODIRECTION = 0;
        public static final byte PAST = 1;
        public static final byte FUTURE = 2;
        public static final String[] names = new String[]{"NODIRECTION", "PAST", "FUTURE"};
    }

    public static interface ETimeLimit {
        public static final byte NOLIMIT = 0;
        public static final byte LT = 1;
        public static final byte MT = 2;
        public static final String[] names = new String[]{"NOLIMIT", "LT", "MT"};
    }

    public static class ScopeData {
        String prefix;
        boolean requiresDigitPrefix;
        String suffix;

        public void write(RecordWriter recordWriter) {
            recordWriter.open("ScopeData");
            recordWriter.string("prefix", this.prefix);
            recordWriter.bool("requiresDigitPrefix", this.requiresDigitPrefix);
            recordWriter.string("suffix", this.suffix);
            recordWriter.close();
        }

        public static ScopeData read(RecordReader recordReader) {
            if (recordReader.open("ScopeData")) {
                ScopeData scopeData = new ScopeData();
                scopeData.prefix = recordReader.string("prefix");
                scopeData.requiresDigitPrefix = recordReader.bool("requiresDigitPrefix");
                scopeData.suffix = recordReader.string("suffix");
                if (recordReader.close()) {
                    return scopeData;
                }
            }
            return null;
        }
    }
}

