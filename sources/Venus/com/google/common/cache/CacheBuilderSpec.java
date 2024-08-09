/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.cache;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LocalCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

@GwtIncompatible
public final class CacheBuilderSpec {
    private static final Splitter KEYS_SPLITTER = Splitter.on(',').trimResults();
    private static final Splitter KEY_VALUE_SPLITTER = Splitter.on('=').trimResults();
    private static final ImmutableMap<String, ValueParser> VALUE_PARSERS = ImmutableMap.builder().put("initialCapacity", new InitialCapacityParser()).put("maximumSize", (InitialCapacityParser)((Object)new MaximumSizeParser())).put("maximumWeight", (InitialCapacityParser)((Object)new MaximumWeightParser())).put("concurrencyLevel", (InitialCapacityParser)((Object)new ConcurrencyLevelParser())).put("weakKeys", (InitialCapacityParser)((Object)new KeyStrengthParser(LocalCache.Strength.WEAK))).put("softValues", (InitialCapacityParser)((Object)new ValueStrengthParser(LocalCache.Strength.SOFT))).put("weakValues", (InitialCapacityParser)((Object)new ValueStrengthParser(LocalCache.Strength.WEAK))).put("recordStats", (InitialCapacityParser)((Object)new RecordStatsParser())).put("expireAfterAccess", (InitialCapacityParser)((Object)new AccessDurationParser())).put("expireAfterWrite", (InitialCapacityParser)((Object)new WriteDurationParser())).put("refreshAfterWrite", (InitialCapacityParser)((Object)new RefreshDurationParser())).put("refreshInterval", (InitialCapacityParser)((Object)new RefreshDurationParser())).build();
    @VisibleForTesting
    Integer initialCapacity;
    @VisibleForTesting
    Long maximumSize;
    @VisibleForTesting
    Long maximumWeight;
    @VisibleForTesting
    Integer concurrencyLevel;
    @VisibleForTesting
    LocalCache.Strength keyStrength;
    @VisibleForTesting
    LocalCache.Strength valueStrength;
    @VisibleForTesting
    Boolean recordStats;
    @VisibleForTesting
    long writeExpirationDuration;
    @VisibleForTesting
    TimeUnit writeExpirationTimeUnit;
    @VisibleForTesting
    long accessExpirationDuration;
    @VisibleForTesting
    TimeUnit accessExpirationTimeUnit;
    @VisibleForTesting
    long refreshDuration;
    @VisibleForTesting
    TimeUnit refreshTimeUnit;
    private final String specification;

    private CacheBuilderSpec(String string) {
        this.specification = string;
    }

    public static CacheBuilderSpec parse(String string) {
        CacheBuilderSpec cacheBuilderSpec = new CacheBuilderSpec(string);
        if (!string.isEmpty()) {
            for (String string2 : KEYS_SPLITTER.split(string)) {
                ImmutableList<String> immutableList = ImmutableList.copyOf(KEY_VALUE_SPLITTER.split(string2));
                Preconditions.checkArgument(!immutableList.isEmpty(), "blank key-value pair");
                Preconditions.checkArgument(immutableList.size() <= 2, "key-value pair %s with more than one equals sign", (Object)string2);
                String string3 = (String)immutableList.get(0);
                ValueParser valueParser = VALUE_PARSERS.get(string3);
                Preconditions.checkArgument(valueParser != null, "unknown key %s", (Object)string3);
                String string4 = immutableList.size() == 1 ? null : (String)immutableList.get(1);
                valueParser.parse(cacheBuilderSpec, string3, string4);
            }
        }
        return cacheBuilderSpec;
    }

    public static CacheBuilderSpec disableCaching() {
        return CacheBuilderSpec.parse("maximumSize=0");
    }

    CacheBuilder<Object, Object> toCacheBuilder() {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (this.initialCapacity != null) {
            cacheBuilder.initialCapacity(this.initialCapacity);
        }
        if (this.maximumSize != null) {
            cacheBuilder.maximumSize(this.maximumSize);
        }
        if (this.maximumWeight != null) {
            cacheBuilder.maximumWeight(this.maximumWeight);
        }
        if (this.concurrencyLevel != null) {
            cacheBuilder.concurrencyLevel(this.concurrencyLevel);
        }
        if (this.keyStrength != null) {
            switch (1.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.keyStrength.ordinal()]) {
                case 1: {
                    cacheBuilder.weakKeys();
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        if (this.valueStrength != null) {
            switch (1.$SwitchMap$com$google$common$cache$LocalCache$Strength[this.valueStrength.ordinal()]) {
                case 2: {
                    cacheBuilder.softValues();
                    break;
                }
                case 1: {
                    cacheBuilder.weakValues();
                    break;
                }
                default: {
                    throw new AssertionError();
                }
            }
        }
        if (this.recordStats != null && this.recordStats.booleanValue()) {
            cacheBuilder.recordStats();
        }
        if (this.writeExpirationTimeUnit != null) {
            cacheBuilder.expireAfterWrite(this.writeExpirationDuration, this.writeExpirationTimeUnit);
        }
        if (this.accessExpirationTimeUnit != null) {
            cacheBuilder.expireAfterAccess(this.accessExpirationDuration, this.accessExpirationTimeUnit);
        }
        if (this.refreshTimeUnit != null) {
            cacheBuilder.refreshAfterWrite(this.refreshDuration, this.refreshTimeUnit);
        }
        return cacheBuilder;
    }

    public String toParsableString() {
        return this.specification;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).addValue(this.toParsableString()).toString();
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.initialCapacity, this.maximumSize, this.maximumWeight, this.concurrencyLevel, this.keyStrength, this.valueStrength, this.recordStats, CacheBuilderSpec.durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), CacheBuilderSpec.durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), CacheBuilderSpec.durationInNanos(this.refreshDuration, this.refreshTimeUnit)});
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof CacheBuilderSpec)) {
            return true;
        }
        CacheBuilderSpec cacheBuilderSpec = (CacheBuilderSpec)object;
        return Objects.equal(this.initialCapacity, cacheBuilderSpec.initialCapacity) && Objects.equal(this.maximumSize, cacheBuilderSpec.maximumSize) && Objects.equal(this.maximumWeight, cacheBuilderSpec.maximumWeight) && Objects.equal(this.concurrencyLevel, cacheBuilderSpec.concurrencyLevel) && Objects.equal((Object)this.keyStrength, (Object)cacheBuilderSpec.keyStrength) && Objects.equal((Object)this.valueStrength, (Object)cacheBuilderSpec.valueStrength) && Objects.equal(this.recordStats, cacheBuilderSpec.recordStats) && Objects.equal(CacheBuilderSpec.durationInNanos(this.writeExpirationDuration, this.writeExpirationTimeUnit), CacheBuilderSpec.durationInNanos(cacheBuilderSpec.writeExpirationDuration, cacheBuilderSpec.writeExpirationTimeUnit)) && Objects.equal(CacheBuilderSpec.durationInNanos(this.accessExpirationDuration, this.accessExpirationTimeUnit), CacheBuilderSpec.durationInNanos(cacheBuilderSpec.accessExpirationDuration, cacheBuilderSpec.accessExpirationTimeUnit)) && Objects.equal(CacheBuilderSpec.durationInNanos(this.refreshDuration, this.refreshTimeUnit), CacheBuilderSpec.durationInNanos(cacheBuilderSpec.refreshDuration, cacheBuilderSpec.refreshTimeUnit));
    }

    @Nullable
    private static Long durationInNanos(long l, @Nullable TimeUnit timeUnit) {
        return timeUnit == null ? null : Long.valueOf(timeUnit.toNanos(l));
    }

    private static String format(String string, Object ... objectArray) {
        return String.format(Locale.ROOT, string, objectArray);
    }

    static String access$000(String string, Object[] objectArray) {
        return CacheBuilderSpec.format(string, objectArray);
    }

    static class RefreshDurationParser
    extends DurationParser {
        RefreshDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.refreshTimeUnit == null, "refreshAfterWrite already set");
            cacheBuilderSpec.refreshDuration = l;
            cacheBuilderSpec.refreshTimeUnit = timeUnit;
        }
    }

    static class WriteDurationParser
    extends DurationParser {
        WriteDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.writeExpirationTimeUnit == null, "expireAfterWrite already set");
            cacheBuilderSpec.writeExpirationDuration = l;
            cacheBuilderSpec.writeExpirationTimeUnit = timeUnit;
        }
    }

    static class AccessDurationParser
    extends DurationParser {
        AccessDurationParser() {
        }

        @Override
        protected void parseDuration(CacheBuilderSpec cacheBuilderSpec, long l, TimeUnit timeUnit) {
            Preconditions.checkArgument(cacheBuilderSpec.accessExpirationTimeUnit == null, "expireAfterAccess already set");
            cacheBuilderSpec.accessExpirationDuration = l;
            cacheBuilderSpec.accessExpirationTimeUnit = timeUnit;
        }
    }

    static abstract class DurationParser
    implements ValueParser {
        DurationParser() {
        }

        protected abstract void parseDuration(CacheBuilderSpec var1, long var2, TimeUnit var4);

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, String string2) {
            Preconditions.checkArgument(string2 != null && !string2.isEmpty(), "value of key %s omitted", (Object)string);
            try {
                TimeUnit timeUnit;
                char c = string2.charAt(string2.length() - 1);
                switch (c) {
                    case 'd': {
                        timeUnit = TimeUnit.DAYS;
                        break;
                    }
                    case 'h': {
                        timeUnit = TimeUnit.HOURS;
                        break;
                    }
                    case 'm': {
                        timeUnit = TimeUnit.MINUTES;
                        break;
                    }
                    case 's': {
                        timeUnit = TimeUnit.SECONDS;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s invalid format.  was %s, must end with one of [dDhHmMsS]", new Object[]{string, string2}));
                    }
                }
                long l = Long.parseLong(string2.substring(0, string2.length() - 1));
                this.parseDuration(cacheBuilderSpec, l, timeUnit);
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s value set to %s, must be integer", new Object[]{string, string2}));
            }
        }
    }

    static class RecordStatsParser
    implements ValueParser {
        RecordStatsParser() {
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, @Nullable String string2) {
            Preconditions.checkArgument(string2 == null, "recordStats does not take values");
            Preconditions.checkArgument(cacheBuilderSpec.recordStats == null, "recordStats already set");
            cacheBuilderSpec.recordStats = true;
        }
    }

    static class ValueStrengthParser
    implements ValueParser {
        private final LocalCache.Strength strength;

        public ValueStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, @Nullable String string2) {
            Preconditions.checkArgument(string2 == null, "key %s does not take values", (Object)string);
            Preconditions.checkArgument(cacheBuilderSpec.valueStrength == null, "%s was already set to %s", (Object)string, (Object)cacheBuilderSpec.valueStrength);
            cacheBuilderSpec.valueStrength = this.strength;
        }
    }

    static class KeyStrengthParser
    implements ValueParser {
        private final LocalCache.Strength strength;

        public KeyStrengthParser(LocalCache.Strength strength) {
            this.strength = strength;
        }

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, @Nullable String string2) {
            Preconditions.checkArgument(string2 == null, "key %s does not take values", (Object)string);
            Preconditions.checkArgument(cacheBuilderSpec.keyStrength == null, "%s was already set to %s", (Object)string, (Object)cacheBuilderSpec.keyStrength);
            cacheBuilderSpec.keyStrength = this.strength;
        }
    }

    static class ConcurrencyLevelParser
    extends IntegerParser {
        ConcurrencyLevelParser() {
        }

        @Override
        protected void parseInteger(CacheBuilderSpec cacheBuilderSpec, int n) {
            Preconditions.checkArgument(cacheBuilderSpec.concurrencyLevel == null, "concurrency level was already set to ", (Object)cacheBuilderSpec.concurrencyLevel);
            cacheBuilderSpec.concurrencyLevel = n;
        }
    }

    static class MaximumWeightParser
    extends LongParser {
        MaximumWeightParser() {
        }

        @Override
        protected void parseLong(CacheBuilderSpec cacheBuilderSpec, long l) {
            Preconditions.checkArgument(cacheBuilderSpec.maximumWeight == null, "maximum weight was already set to ", (Object)cacheBuilderSpec.maximumWeight);
            Preconditions.checkArgument(cacheBuilderSpec.maximumSize == null, "maximum size was already set to ", (Object)cacheBuilderSpec.maximumSize);
            cacheBuilderSpec.maximumWeight = l;
        }
    }

    static class MaximumSizeParser
    extends LongParser {
        MaximumSizeParser() {
        }

        @Override
        protected void parseLong(CacheBuilderSpec cacheBuilderSpec, long l) {
            Preconditions.checkArgument(cacheBuilderSpec.maximumSize == null, "maximum size was already set to ", (Object)cacheBuilderSpec.maximumSize);
            Preconditions.checkArgument(cacheBuilderSpec.maximumWeight == null, "maximum weight was already set to ", (Object)cacheBuilderSpec.maximumWeight);
            cacheBuilderSpec.maximumSize = l;
        }
    }

    static class InitialCapacityParser
    extends IntegerParser {
        InitialCapacityParser() {
        }

        @Override
        protected void parseInteger(CacheBuilderSpec cacheBuilderSpec, int n) {
            Preconditions.checkArgument(cacheBuilderSpec.initialCapacity == null, "initial capacity was already set to ", (Object)cacheBuilderSpec.initialCapacity);
            cacheBuilderSpec.initialCapacity = n;
        }
    }

    static abstract class LongParser
    implements ValueParser {
        LongParser() {
        }

        protected abstract void parseLong(CacheBuilderSpec var1, long var2);

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, String string2) {
            Preconditions.checkArgument(string2 != null && !string2.isEmpty(), "value of key %s omitted", (Object)string);
            try {
                this.parseLong(cacheBuilderSpec, Long.parseLong(string2));
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s value set to %s, must be integer", new Object[]{string, string2}), numberFormatException);
            }
        }
    }

    static abstract class IntegerParser
    implements ValueParser {
        IntegerParser() {
        }

        protected abstract void parseInteger(CacheBuilderSpec var1, int var2);

        @Override
        public void parse(CacheBuilderSpec cacheBuilderSpec, String string, String string2) {
            Preconditions.checkArgument(string2 != null && !string2.isEmpty(), "value of key %s omitted", (Object)string);
            try {
                this.parseInteger(cacheBuilderSpec, Integer.parseInt(string2));
            } catch (NumberFormatException numberFormatException) {
                throw new IllegalArgumentException(CacheBuilderSpec.access$000("key %s value set to %s, must be integer", new Object[]{string, string2}), numberFormatException);
            }
        }
    }

    private static interface ValueParser {
        public void parse(CacheBuilderSpec var1, String var2, @Nullable String var3);
    }
}

