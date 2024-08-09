/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.ValidIdentifiers;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.KeyTypeData;
import com.ibm.icu.util.IllformedLocaleException;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class LocaleValidityChecker {
    private final Set<ValidIdentifiers.Datasubtype> datasubtypes;
    private final boolean allowsDeprecated;
    static Pattern SEPARATOR = Pattern.compile("[-_]");
    private static final Pattern VALID_X = Pattern.compile("[a-zA-Z0-9]{2,8}(-[a-zA-Z0-9]{2,8})*");
    static final Set<String> REORDERING_INCLUDE = new HashSet<String>(Arrays.asList("space", "punct", "symbol", "currency", "digit", "others", "zzzz"));
    static final Set<String> REORDERING_EXCLUDE = new HashSet<String>(Arrays.asList("zinh", "zyyy"));
    static final Set<ValidIdentifiers.Datasubtype> REGULAR_ONLY = EnumSet.of(ValidIdentifiers.Datasubtype.regular);

    public LocaleValidityChecker(Set<ValidIdentifiers.Datasubtype> set) {
        this.datasubtypes = EnumSet.copyOf(set);
        this.allowsDeprecated = set.contains((Object)ValidIdentifiers.Datasubtype.deprecated);
    }

    public LocaleValidityChecker(ValidIdentifiers.Datasubtype ... datasubtypeArray) {
        this.datasubtypes = EnumSet.copyOf(Arrays.asList(datasubtypeArray));
        this.allowsDeprecated = this.datasubtypes.contains((Object)ValidIdentifiers.Datasubtype.deprecated);
    }

    public Set<ValidIdentifiers.Datasubtype> getDatasubtypes() {
        return EnumSet.copyOf(this.datasubtypes);
    }

    public boolean isValid(ULocale uLocale, Where where) {
        where.set(null, null);
        String string = uLocale.getLanguage();
        String string2 = uLocale.getScript();
        String string3 = uLocale.getCountry();
        String string4 = uLocale.getVariant();
        Set<Character> set = uLocale.getExtensionKeys();
        if (!this.isValid(ValidIdentifiers.Datatype.language, string, where)) {
            if (string.equals("x")) {
                where.set(null, null);
                return false;
            }
            return true;
        }
        if (!this.isValid(ValidIdentifiers.Datatype.script, string2, where)) {
            return true;
        }
        if (!this.isValid(ValidIdentifiers.Datatype.region, string3, where)) {
            return true;
        }
        if (!string4.isEmpty()) {
            for (String string5 : SEPARATOR.split(string4)) {
                if (this.isValid(ValidIdentifiers.Datatype.variant, string5, where)) continue;
                return true;
            }
        }
        for (Character c : set) {
            try {
                ValidIdentifiers.Datatype datatype = ValidIdentifiers.Datatype.valueOf(c + "");
                switch (datatype) {
                    case x: {
                        return true;
                    }
                    case t: 
                    case u: {
                        if (this.isValidU(uLocale, datatype, uLocale.getExtension(c.charValue()), where)) break;
                        return false;
                    }
                }
            } catch (Exception exception) {
                return where.set(ValidIdentifiers.Datatype.illegal, c + "");
            }
        }
        return false;
    }

    private boolean isValidU(ULocale uLocale, ValidIdentifiers.Datatype datatype, String string, Where where) {
        String string2 = "";
        int n = 0;
        Enum enum_ = null;
        Enum enum_2 = null;
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<String> hashSet = new HashSet<String>();
        StringBuilder stringBuilder2 = datatype == ValidIdentifiers.Datatype.t ? new StringBuilder() : null;
        block14: for (String string3 : SEPARATOR.split(string)) {
            if (string3.length() == 2 && (stringBuilder2 == null || string3.charAt(1) <= '9')) {
                if (stringBuilder2 != null) {
                    if (stringBuilder2.length() != 0 && !this.isValidLocale(stringBuilder2.toString(), where)) {
                        return true;
                    }
                    stringBuilder2 = null;
                }
                if ((string2 = KeyTypeData.toBcpKey(string3)) == null) {
                    return where.set(datatype, string3);
                }
                if (!this.allowsDeprecated && KeyTypeData.isDeprecated(string2)) {
                    return where.set(datatype, string2);
                }
                enum_ = KeyTypeData.getValueType(string2);
                enum_2 = SpecialCase.get(string2);
                n = 0;
                continue;
            }
            if (stringBuilder2 != null) {
                if (stringBuilder2.length() != 0) {
                    stringBuilder2.append('-');
                }
                stringBuilder2.append(string3);
                continue;
            }
            ++n;
            switch (1.$SwitchMap$com$ibm$icu$impl$locale$KeyTypeData$ValueType[enum_.ordinal()]) {
                case 1: {
                    if (n <= 1) break;
                    return where.set(datatype, string2 + "-" + string3);
                }
                case 2: {
                    if (n == 1) {
                        stringBuilder.setLength(0);
                        stringBuilder.append(string3);
                        break;
                    }
                    stringBuilder.append('-').append(string3);
                    string3 = stringBuilder.toString();
                    break;
                }
                case 3: {
                    if (n != 1) break;
                    hashSet.clear();
                    break;
                }
            }
            switch (1.$SwitchMap$com$ibm$icu$impl$locale$LocaleValidityChecker$SpecialCase[enum_2.ordinal()]) {
                case 1: {
                    continue block14;
                }
                case 2: {
                    try {
                        if (Integer.parseInt(string3, 16) <= 0x10FFFF) continue block14;
                        return where.set(datatype, string2 + "-" + string3);
                    } catch (NumberFormatException numberFormatException) {
                        return where.set(datatype, string2 + "-" + string3);
                    }
                }
                case 3: {
                    boolean bl = hashSet.add(string3.equals("zzzz") ? "others" : string3);
                    if (bl && this.isScriptReorder(string3)) continue block14;
                    return where.set(datatype, string2 + "-" + string3);
                }
                case 4: {
                    if (this.isSubdivision(uLocale, string3)) continue block14;
                    return where.set(datatype, string2 + "-" + string3);
                }
                case 5: {
                    if (string3.length() < 6 || !string3.endsWith("zzzz")) {
                        return where.set(datatype, string3);
                    }
                    if (this.isValid(ValidIdentifiers.Datatype.region, string3.substring(0, string3.length() - 4), where)) continue block14;
                    return true;
                }
                default: {
                    Output<Boolean> output = new Output<Boolean>();
                    Output<Boolean> output2 = new Output<Boolean>();
                    String string4 = KeyTypeData.toBcpType(string2, string3, output, output2);
                    if (string4 == null) {
                        return where.set(datatype, string2 + "-" + string3);
                    }
                    if (this.allowsDeprecated || !KeyTypeData.isDeprecated(string2, string3)) continue block14;
                    return where.set(datatype, string2 + "-" + string3);
                }
            }
        }
        return stringBuilder2 != null && stringBuilder2.length() != 0 && !this.isValidLocale(stringBuilder2.toString(), where);
    }

    private boolean isSubdivision(ULocale uLocale, String string) {
        String string2;
        if (string.length() < 3) {
            return true;
        }
        String string3 = string.substring(0, string.charAt(0) <= '9' ? 3 : 2);
        if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.subdivision, this.datasubtypes, string3, string2 = string.substring(string3.length())) == null) {
            return true;
        }
        String string4 = uLocale.getCountry();
        if (string4.isEmpty()) {
            ULocale uLocale2 = ULocale.addLikelySubtags(uLocale);
            string4 = uLocale2.getCountry();
        }
        return !string3.equalsIgnoreCase(string4);
    }

    private boolean isScriptReorder(String string) {
        if (REORDERING_INCLUDE.contains(string = AsciiUtil.toLowerString(string))) {
            return false;
        }
        if (REORDERING_EXCLUDE.contains(string)) {
            return true;
        }
        return ValidIdentifiers.isValid(ValidIdentifiers.Datatype.script, REGULAR_ONLY, string) != null;
    }

    private boolean isValidLocale(String string, Where where) {
        try {
            ULocale uLocale = new ULocale.Builder().setLanguageTag(string).build();
            return this.isValid(uLocale, where);
        } catch (IllformedLocaleException illformedLocaleException) {
            int n = illformedLocaleException.getErrorIndex();
            String[] stringArray = SEPARATOR.split(string.substring(n));
            return where.set(ValidIdentifiers.Datatype.t, stringArray[0]);
        } catch (Exception exception) {
            return where.set(ValidIdentifiers.Datatype.t, exception.getMessage());
        }
    }

    private boolean isValid(ValidIdentifiers.Datatype datatype, String string, Where where) {
        if (string.isEmpty()) {
            return false;
        }
        if (datatype == ValidIdentifiers.Datatype.variant && "posix".equalsIgnoreCase(string)) {
            return false;
        }
        return ValidIdentifiers.isValid(datatype, this.datasubtypes, string) != null ? true : (where == null ? false : where.set(datatype, string));
    }

    static enum SpecialCase {
        normal,
        anything,
        reorder,
        codepoints,
        subdivision,
        rgKey;


        static SpecialCase get(String string) {
            if (string.equals("kr")) {
                return reorder;
            }
            if (string.equals("vt")) {
                return codepoints;
            }
            if (string.equals("sd")) {
                return subdivision;
            }
            if (string.equals("rg")) {
                return rgKey;
            }
            if (string.equals("x0")) {
                return anything;
            }
            return normal;
        }
    }

    public static class Where {
        public ValidIdentifiers.Datatype fieldFailure;
        public String codeFailure;

        public boolean set(ValidIdentifiers.Datatype datatype, String string) {
            this.fieldFailure = datatype;
            this.codeFailure = string;
            return true;
        }

        public String toString() {
            return this.fieldFailure == null ? "OK" : "{" + (Object)((Object)this.fieldFailure) + ", " + this.codeFailure + "}";
        }
    }
}

