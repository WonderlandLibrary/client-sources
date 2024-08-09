/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICUCache;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.SimpleCache;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

@Deprecated
public class GenderInfo {
    private final ListGenderStyle style;
    private static GenderInfo neutral = new GenderInfo(ListGenderStyle.NEUTRAL);
    private static Cache genderInfoCache = new Cache(null);

    @Deprecated
    public static GenderInfo getInstance(ULocale uLocale) {
        return genderInfoCache.get(uLocale);
    }

    @Deprecated
    public static GenderInfo getInstance(Locale locale) {
        return GenderInfo.getInstance(ULocale.forLocale(locale));
    }

    @Deprecated
    public Gender getListGender(Gender ... genderArray) {
        return this.getListGender(Arrays.asList(genderArray));
    }

    @Deprecated
    public Gender getListGender(List<Gender> list) {
        if (list.size() == 0) {
            return Gender.OTHER;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        switch (this.style) {
            case NEUTRAL: {
                return Gender.OTHER;
            }
            case MIXED_NEUTRAL: {
                boolean bl = false;
                boolean bl2 = false;
                for (Gender gender : list) {
                    switch (gender) {
                        case FEMALE: {
                            if (bl2) {
                                return Gender.OTHER;
                            }
                            bl = true;
                            break;
                        }
                        case MALE: {
                            if (bl) {
                                return Gender.OTHER;
                            }
                            bl2 = true;
                            break;
                        }
                        case OTHER: {
                            return Gender.OTHER;
                        }
                    }
                }
                return bl2 ? Gender.MALE : Gender.FEMALE;
            }
            case MALE_TAINTS: {
                for (Gender gender : list) {
                    if (gender == Gender.FEMALE) continue;
                    return Gender.MALE;
                }
                return Gender.FEMALE;
            }
        }
        return Gender.OTHER;
    }

    @Deprecated
    public GenderInfo(ListGenderStyle listGenderStyle) {
        this.style = listGenderStyle;
    }

    static GenderInfo access$000() {
        return neutral;
    }

    private static class Cache {
        private final ICUCache<ULocale, GenderInfo> cache = new SimpleCache<ULocale, GenderInfo>();

        private Cache() {
        }

        public GenderInfo get(ULocale uLocale) {
            GenderInfo genderInfo = this.cache.get(uLocale);
            if (genderInfo == null) {
                genderInfo = Cache.load(uLocale);
                if (genderInfo == null) {
                    ULocale uLocale2 = uLocale.getFallback();
                    genderInfo = uLocale2 == null ? GenderInfo.access$000() : this.get(uLocale2);
                }
                this.cache.put(uLocale, genderInfo);
            }
            return genderInfo;
        }

        private static GenderInfo load(ULocale uLocale) {
            UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "genderList", ICUResourceBundle.ICU_DATA_CLASS_LOADER, true);
            UResourceBundle uResourceBundle2 = uResourceBundle.get("genderList");
            try {
                return new GenderInfo(ListGenderStyle.fromName(uResourceBundle2.getString(uLocale.toString())));
            } catch (MissingResourceException missingResourceException) {
                return null;
            }
        }

        Cache(1 var1_1) {
            this();
        }
    }

    @Deprecated
    public static enum ListGenderStyle {
        NEUTRAL,
        MIXED_NEUTRAL,
        MALE_TAINTS;

        private static Map<String, ListGenderStyle> fromNameMap;

        @Deprecated
        public static ListGenderStyle fromName(String string) {
            ListGenderStyle listGenderStyle = fromNameMap.get(string);
            if (listGenderStyle == null) {
                throw new IllegalArgumentException("Unknown gender style name: " + string);
            }
            return listGenderStyle;
        }

        static {
            fromNameMap = new HashMap<String, ListGenderStyle>(3);
            fromNameMap.put("neutral", NEUTRAL);
            fromNameMap.put("maleTaints", MALE_TAINTS);
            fromNameMap.put("mixedNeutral", MIXED_NEUTRAL);
        }
    }

    @Deprecated
    public static enum Gender {
        MALE,
        FEMALE,
        OTHER;

    }
}

