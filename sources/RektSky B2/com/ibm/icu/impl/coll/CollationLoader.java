package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import java.util.*;
import com.ibm.icu.util.*;
import java.io.*;
import java.nio.*;

public final class CollationLoader
{
    private static volatile String rootRules;
    
    private CollationLoader() {
    }
    
    private static void loadRootRules() {
        if (CollationLoader.rootRules != null) {
            return;
        }
        synchronized (CollationLoader.class) {
            if (CollationLoader.rootRules == null) {
                final UResourceBundle rootBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/coll", ULocale.ROOT);
                CollationLoader.rootRules = rootBundle.getString("UCARules");
            }
        }
    }
    
    public static String getRootRules() {
        loadRootRules();
        return CollationLoader.rootRules;
    }
    
    static String loadRules(final ULocale locale, final String collationType) {
        final UResourceBundle bundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/coll", locale);
        final UResourceBundle data = ((ICUResourceBundle)bundle).getWithFallback("collations/" + ASCII.toLowerCase(collationType));
        final String rules = data.getString("Sequence");
        return rules;
    }
    
    private static final UResourceBundle findWithFallback(final UResourceBundle table, final String entryName) {
        return ((ICUResourceBundle)table).findWithFallback(entryName);
    }
    
    public static CollationTailoring loadTailoring(final ULocale locale, final Output<ULocale> outValidLocale) {
        final CollationTailoring root = CollationRoot.getRoot();
        final String localeName = locale.getName();
        if (localeName.length() == 0 || localeName.equals("root")) {
            outValidLocale.value = ULocale.ROOT;
            return root;
        }
        UResourceBundle bundle = null;
        try {
            bundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/coll", locale, ICUResourceBundle.OpenType.LOCALE_ROOT);
        }
        catch (MissingResourceException e2) {
            outValidLocale.value = ULocale.ROOT;
            return root;
        }
        ULocale validLocale = bundle.getULocale();
        final String validLocaleName = validLocale.getName();
        if (validLocaleName.length() == 0 || validLocaleName.equals("root")) {
            validLocale = ULocale.ROOT;
        }
        outValidLocale.value = validLocale;
        UResourceBundle collations;
        try {
            collations = bundle.get("collations");
            if (collations == null) {
                return root;
            }
        }
        catch (MissingResourceException ignored) {
            return root;
        }
        String type = locale.getKeywordValue("collation");
        String defaultType = "standard";
        String defT = ((ICUResourceBundle)collations).findStringWithFallback("default");
        if (defT != null) {
            defaultType = defT;
        }
        if (type == null || type.equals("default")) {
            type = defaultType;
        }
        else {
            type = ASCII.toLowerCase(type);
        }
        UResourceBundle data = findWithFallback(collations, type);
        if (data == null && type.length() > 6 && type.startsWith("search")) {
            type = "search";
            data = findWithFallback(collations, type);
        }
        if (data == null && !type.equals(defaultType)) {
            type = defaultType;
            data = findWithFallback(collations, type);
        }
        if (data == null && !type.equals("standard")) {
            type = "standard";
            data = findWithFallback(collations, type);
        }
        if (data == null) {
            return root;
        }
        ULocale actualLocale = data.getULocale();
        final String actualLocaleName = actualLocale.getName();
        if (actualLocaleName.length() == 0 || actualLocaleName.equals("root")) {
            actualLocale = ULocale.ROOT;
            if (type.equals("standard")) {
                return root;
            }
        }
        final CollationTailoring t = new CollationTailoring(root.settings);
        t.actualLocale = actualLocale;
        final UResourceBundle binary = data.get("%%CollationBin");
        final ByteBuffer inBytes = binary.getBinary();
        try {
            CollationDataReader.read(root, inBytes, t);
        }
        catch (IOException e) {
            throw new ICUUncheckedIOException("Failed to load collation tailoring data for locale:" + actualLocale + " type:" + type, e);
        }
        try {
            t.setRulesResource(data.get("Sequence"));
        }
        catch (MissingResourceException ex) {}
        if (!type.equals(defaultType)) {
            outValidLocale.value = validLocale.setKeywordValue("collation", type);
        }
        if (!actualLocale.equals(validLocale)) {
            final UResourceBundle actualBundle = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/coll", actualLocale);
            defT = ((ICUResourceBundle)actualBundle).findStringWithFallback("collations/default");
            if (defT != null) {
                defaultType = defT;
            }
        }
        if (!type.equals(defaultType)) {
            t.actualLocale = t.actualLocale.setKeywordValue("collation", type);
        }
        return t;
    }
    
    static {
        CollationLoader.rootRules = null;
    }
    
    private static final class ASCII
    {
        static String toLowerCase(final String s) {
            for (int i = 0; i < s.length(); ++i) {
                char c = s.charAt(i);
                if ('A' <= c && c <= 'Z') {
                    final StringBuilder sb = new StringBuilder(s.length());
                    sb.append(s, 0, i).append((char)(c + ' '));
                    while (++i < s.length()) {
                        c = s.charAt(i);
                        if ('A' <= c && c <= 'Z') {
                            c += ' ';
                        }
                        sb.append(c);
                    }
                    return sb.toString();
                }
            }
            return s;
        }
    }
}
