/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.commons.lang3.StringUtils;

public class LocaleUtils {
    private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap<String, List<Locale>>();
    private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap<String, List<Locale>>();

    public static Locale toLocale(String string) {
        if (string == null) {
            return null;
        }
        if (string.isEmpty()) {
            return new Locale("", "");
        }
        if (string.contains("#")) {
            throw new IllegalArgumentException("Invalid locale format: " + string);
        }
        int n = string.length();
        if (n < 2) {
            throw new IllegalArgumentException("Invalid locale format: " + string);
        }
        char c = string.charAt(0);
        if (c == '_') {
            if (n < 3) {
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            char c2 = string.charAt(1);
            char c3 = string.charAt(2);
            if (!Character.isUpperCase(c2) || !Character.isUpperCase(c3)) {
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            if (n == 3) {
                return new Locale("", string.substring(1, 3));
            }
            if (n < 5) {
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            if (string.charAt(3) != '_') {
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            return new Locale("", string.substring(1, 3), string.substring(4));
        }
        String[] stringArray = string.split("_", -1);
        int n2 = stringArray.length - 1;
        switch (n2) {
            case 0: {
                if (StringUtils.isAllLowerCase(string) && (n == 2 || n == 3)) {
                    return new Locale(string);
                }
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            case 1: {
                if (StringUtils.isAllLowerCase(stringArray[0]) && (stringArray[0].length() == 2 || stringArray[0].length() == 3) && stringArray[5].length() == 2 && StringUtils.isAllUpperCase(stringArray[5])) {
                    return new Locale(stringArray[0], stringArray[5]);
                }
                throw new IllegalArgumentException("Invalid locale format: " + string);
            }
            case 2: {
                if (!StringUtils.isAllLowerCase(stringArray[0]) || stringArray[0].length() != 2 && stringArray[0].length() != 3 || stringArray[5].length() != 0 && (stringArray[5].length() != 2 || !StringUtils.isAllUpperCase(stringArray[5])) || stringArray[5].length() <= 0) break;
                return new Locale(stringArray[0], stringArray[5], stringArray[5]);
            }
        }
        throw new IllegalArgumentException("Invalid locale format: " + string);
    }

    public static List<Locale> localeLookupList(Locale locale) {
        return LocaleUtils.localeLookupList(locale, locale);
    }

    public static List<Locale> localeLookupList(Locale locale, Locale locale2) {
        ArrayList<Locale> arrayList = new ArrayList<Locale>(4);
        if (locale != null) {
            arrayList.add(locale);
            if (locale.getVariant().length() > 0) {
                arrayList.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (locale.getCountry().length() > 0) {
                arrayList.add(new Locale(locale.getLanguage(), ""));
            }
            if (!arrayList.contains(locale2)) {
                arrayList.add(locale2);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static List<Locale> availableLocaleList() {
        return SyncAvoid.access$000();
    }

    public static Set<Locale> availableLocaleSet() {
        return SyncAvoid.access$100();
    }

    public static boolean isAvailableLocale(Locale locale) {
        return LocaleUtils.availableLocaleList().contains(locale);
    }

    public static List<Locale> languagesByCountry(String string) {
        if (string == null) {
            return Collections.emptyList();
        }
        List<Locale> list = (ArrayList)cLanguagesByCountry.get(string);
        if (list == null) {
            list = new ArrayList();
            List<Locale> list2 = LocaleUtils.availableLocaleList();
            for (int i = 0; i < list2.size(); ++i) {
                Locale locale = list2.get(i);
                if (!string.equals(locale.getCountry()) || !locale.getVariant().isEmpty()) continue;
                list.add(locale);
            }
            list = Collections.unmodifiableList(list);
            cLanguagesByCountry.putIfAbsent(string, list);
            list = (List)cLanguagesByCountry.get(string);
        }
        return list;
    }

    public static List<Locale> countriesByLanguage(String string) {
        if (string == null) {
            return Collections.emptyList();
        }
        List<Locale> list = (ArrayList)cCountriesByLanguage.get(string);
        if (list == null) {
            list = new ArrayList();
            List<Locale> list2 = LocaleUtils.availableLocaleList();
            for (int i = 0; i < list2.size(); ++i) {
                Locale locale = list2.get(i);
                if (!string.equals(locale.getLanguage()) || locale.getCountry().length() == 0 || !locale.getVariant().isEmpty()) continue;
                list.add(locale);
            }
            list = Collections.unmodifiableList(list);
            cCountriesByLanguage.putIfAbsent(string, list);
            list = (List)cCountriesByLanguage.get(string);
        }
        return list;
    }

    static class SyncAvoid {
        private static final List<Locale> AVAILABLE_LOCALE_LIST;
        private static final Set<Locale> AVAILABLE_LOCALE_SET;

        SyncAvoid() {
        }

        static List access$000() {
            return AVAILABLE_LOCALE_LIST;
        }

        static Set access$100() {
            return AVAILABLE_LOCALE_SET;
        }

        static {
            ArrayList<Locale> arrayList = new ArrayList<Locale>(Arrays.asList(Locale.getAvailableLocales()));
            AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(arrayList);
            AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet<Locale>(arrayList));
        }
    }
}

