package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.*;
import java.util.regex.*;
import java.util.*;
import com.ibm.icu.util.*;

public class LocaleValidityChecker
{
    private final Set<ValidIdentifiers.Datasubtype> datasubtypes;
    private final boolean allowsDeprecated;
    static Pattern SEPARATOR;
    private static final Pattern VALID_X;
    static final Set<String> REORDERING_INCLUDE;
    static final Set<String> REORDERING_EXCLUDE;
    static final Set<ValidIdentifiers.Datasubtype> REGULAR_ONLY;
    
    public LocaleValidityChecker(final Set<ValidIdentifiers.Datasubtype> datasubtypes) {
        this.datasubtypes = EnumSet.copyOf(datasubtypes);
        this.allowsDeprecated = datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
    }
    
    public LocaleValidityChecker(final ValidIdentifiers.Datasubtype... datasubtypes) {
        this.datasubtypes = EnumSet.copyOf(Arrays.asList(datasubtypes));
        this.allowsDeprecated = this.datasubtypes.contains(ValidIdentifiers.Datasubtype.deprecated);
    }
    
    public Set<ValidIdentifiers.Datasubtype> getDatasubtypes() {
        return EnumSet.copyOf(this.datasubtypes);
    }
    
    public boolean isValid(final ULocale locale, final Where where) {
        where.set(null, null);
        final String language = locale.getLanguage();
        final String script = locale.getScript();
        final String region = locale.getCountry();
        final String variantString = locale.getVariant();
        final Set<Character> extensionKeys = locale.getExtensionKeys();
        if (!this.isValid(ValidIdentifiers.Datatype.language, language, where)) {
            if (language.equals("x")) {
                where.set(null, null);
                return true;
            }
            return false;
        }
        else {
            if (!this.isValid(ValidIdentifiers.Datatype.script, script, where)) {
                return false;
            }
            if (!this.isValid(ValidIdentifiers.Datatype.region, region, where)) {
                return false;
            }
            if (!variantString.isEmpty()) {
                for (final String variant : LocaleValidityChecker.SEPARATOR.split(variantString)) {
                    if (!this.isValid(ValidIdentifiers.Datatype.variant, variant, where)) {
                        return false;
                    }
                }
            }
            for (final Character c : extensionKeys) {
                try {
                    final ValidIdentifiers.Datatype datatype = ValidIdentifiers.Datatype.valueOf(c + "");
                    switch (datatype) {
                        case x: {
                            return true;
                        }
                        case t:
                        case u: {
                            if (!this.isValidU(locale, datatype, locale.getExtension(c), where)) {
                                return false;
                            }
                            continue;
                        }
                    }
                }
                catch (Exception e) {
                    return where.set(ValidIdentifiers.Datatype.illegal, c + "");
                }
            }
            return true;
        }
    }
    
    private boolean isValidU(final ULocale locale, final ValidIdentifiers.Datatype datatype, final String extensionString, final Where where) {
        String key = "";
        int typeCount = 0;
        KeyTypeData.ValueType valueType = null;
        SpecialCase specialCase = null;
        final StringBuilder prefix = new StringBuilder();
        final Set<String> seen = new HashSet<String>();
        StringBuilder tBuffer = (datatype == ValidIdentifiers.Datatype.t) ? new StringBuilder() : null;
        for (String subtag : LocaleValidityChecker.SEPARATOR.split(extensionString)) {
            if (subtag.length() == 2 && (tBuffer == null || subtag.charAt(1) <= '9')) {
                if (tBuffer != null) {
                    if (tBuffer.length() != 0 && !this.isValidLocale(tBuffer.toString(), where)) {
                        return false;
                    }
                    tBuffer = null;
                }
                key = KeyTypeData.toBcpKey(subtag);
                if (key == null) {
                    return where.set(datatype, subtag);
                }
                if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key)) {
                    return where.set(datatype, key);
                }
                valueType = KeyTypeData.getValueType(key);
                specialCase = SpecialCase.get(key);
                typeCount = 0;
            }
            else if (tBuffer != null) {
                if (tBuffer.length() != 0) {
                    tBuffer.append('-');
                }
                tBuffer.append(subtag);
            }
            else {
                ++typeCount;
                switch (valueType) {
                    case single: {
                        if (typeCount > 1) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                        break;
                    }
                    case incremental: {
                        if (typeCount == 1) {
                            prefix.setLength(0);
                            prefix.append(subtag);
                            break;
                        }
                        prefix.append('-').append(subtag);
                        subtag = prefix.toString();
                        break;
                    }
                    case multiple: {
                        if (typeCount == 1) {
                            seen.clear();
                            break;
                        }
                        break;
                    }
                }
                switch (specialCase) {
                    case anything: {
                        break;
                    }
                    case codepoints: {
                        try {
                            if (Integer.parseInt(subtag, 16) > 1114111) {
                                return where.set(datatype, key + "-" + subtag);
                            }
                            break;
                        }
                        catch (NumberFormatException e) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                    }
                    case reorder: {
                        final boolean newlyAdded = seen.add(subtag.equals("zzzz") ? "others" : subtag);
                        if (!newlyAdded || !this.isScriptReorder(subtag)) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                        break;
                    }
                    case subdivision: {
                        if (!this.isSubdivision(locale, subtag)) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                        break;
                    }
                    case rgKey: {
                        if (subtag.length() < 6 || !subtag.endsWith("zzzz")) {
                            return where.set(datatype, subtag);
                        }
                        if (!this.isValid(ValidIdentifiers.Datatype.region, subtag.substring(0, subtag.length() - 4), where)) {
                            return false;
                        }
                        break;
                    }
                    default: {
                        final Output<Boolean> isKnownKey = new Output<Boolean>();
                        final Output<Boolean> isSpecialType = new Output<Boolean>();
                        final String type = KeyTypeData.toBcpType(key, subtag, isKnownKey, isSpecialType);
                        if (type == null) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                        if (!this.allowsDeprecated && KeyTypeData.isDeprecated(key, subtag)) {
                            return where.set(datatype, key + "-" + subtag);
                        }
                        break;
                    }
                }
            }
        }
        return tBuffer == null || tBuffer.length() == 0 || this.isValidLocale(tBuffer.toString(), where);
    }
    
    private boolean isSubdivision(final ULocale locale, final String subtag) {
        if (subtag.length() < 3) {
            return false;
        }
        final String region = subtag.substring(0, (subtag.charAt(0) <= '9') ? 3 : 2);
        final String subdivision = subtag.substring(region.length());
        if (ValidIdentifiers.isValid(ValidIdentifiers.Datatype.subdivision, this.datasubtypes, region, subdivision) == null) {
            return false;
        }
        String localeRegion = locale.getCountry();
        if (localeRegion.isEmpty()) {
            final ULocale max = ULocale.addLikelySubtags(locale);
            localeRegion = max.getCountry();
        }
        return region.equalsIgnoreCase(localeRegion);
    }
    
    private boolean isScriptReorder(String subtag) {
        subtag = AsciiUtil.toLowerString(subtag);
        return LocaleValidityChecker.REORDERING_INCLUDE.contains(subtag) || (!LocaleValidityChecker.REORDERING_EXCLUDE.contains(subtag) && ValidIdentifiers.isValid(ValidIdentifiers.Datatype.script, LocaleValidityChecker.REGULAR_ONLY, subtag) != null);
    }
    
    private boolean isValidLocale(final String extensionString, final Where where) {
        try {
            final ULocale locale = new ULocale.Builder().setLanguageTag(extensionString).build();
            return this.isValid(locale, where);
        }
        catch (IllformedLocaleException e) {
            final int startIndex = e.getErrorIndex();
            final String[] list = LocaleValidityChecker.SEPARATOR.split(extensionString.substring(startIndex));
            return where.set(ValidIdentifiers.Datatype.t, list[0]);
        }
        catch (Exception e2) {
            return where.set(ValidIdentifiers.Datatype.t, e2.getMessage());
        }
    }
    
    private boolean isValid(final ValidIdentifiers.Datatype datatype, final String code, final Where where) {
        return code.isEmpty() || (datatype == ValidIdentifiers.Datatype.variant && "posix".equalsIgnoreCase(code)) || ValidIdentifiers.isValid(datatype, this.datasubtypes, code) != null || (where != null && where.set(datatype, code));
    }
    
    static {
        LocaleValidityChecker.SEPARATOR = Pattern.compile("[-_]");
        VALID_X = Pattern.compile("[a-zA-Z0-9]{2,8}(-[a-zA-Z0-9]{2,8})*");
        REORDERING_INCLUDE = new HashSet<String>(Arrays.asList("space", "punct", "symbol", "currency", "digit", "others", "zzzz"));
        REORDERING_EXCLUDE = new HashSet<String>(Arrays.asList("zinh", "zyyy"));
        REGULAR_ONLY = EnumSet.of(ValidIdentifiers.Datasubtype.regular);
    }
    
    public static class Where
    {
        public ValidIdentifiers.Datatype fieldFailure;
        public String codeFailure;
        
        public boolean set(final ValidIdentifiers.Datatype datatype, final String code) {
            this.fieldFailure = datatype;
            this.codeFailure = code;
            return false;
        }
        
        @Override
        public String toString() {
            return (this.fieldFailure == null) ? "OK" : ("{" + this.fieldFailure + ", " + this.codeFailure + "}");
        }
    }
    
    enum SpecialCase
    {
        normal, 
        anything, 
        reorder, 
        codepoints, 
        subdivision, 
        rgKey;
        
        static SpecialCase get(final String key) {
            if (key.equals("kr")) {
                return SpecialCase.reorder;
            }
            if (key.equals("vt")) {
                return SpecialCase.codepoints;
            }
            if (key.equals("sd")) {
                return SpecialCase.subdivision;
            }
            if (key.equals("rg")) {
                return SpecialCase.rgKey;
            }
            if (key.equals("x0")) {
                return SpecialCase.anything;
            }
            return SpecialCase.normal;
        }
    }
}
