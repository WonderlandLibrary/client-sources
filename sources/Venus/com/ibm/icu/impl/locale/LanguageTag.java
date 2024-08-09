/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.Extension;
import com.ibm.icu.impl.locale.LocaleExtensions;
import com.ibm.icu.impl.locale.ParseStatus;
import com.ibm.icu.impl.locale.StringTokenIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageTag {
    private static final boolean JDKIMPL = false;
    public static final String SEP = "-";
    public static final String PRIVATEUSE = "x";
    public static String UNDETERMINED;
    public static final String PRIVUSE_VARIANT_PREFIX = "lvariant";
    private String _language = "";
    private String _script = "";
    private String _region = "";
    private String _privateuse = "";
    private List<String> _extlangs = Collections.emptyList();
    private List<String> _variants = Collections.emptyList();
    private List<String> _extensions = Collections.emptyList();
    private static final Map<AsciiUtil.CaseInsensitiveKey, String[]> GRANDFATHERED;
    static final boolean $assertionsDisabled;

    private LanguageTag() {
    }

    public static LanguageTag parse(String string, ParseStatus parseStatus) {
        StringTokenIterator stringTokenIterator;
        if (parseStatus == null) {
            parseStatus = new ParseStatus();
        } else {
            parseStatus.reset();
        }
        boolean bl = false;
        String[] stringArray = GRANDFATHERED.get(new AsciiUtil.CaseInsensitiveKey(string));
        int n = 2;
        while (stringArray == null && (n = string.indexOf(45, n + 1)) != -1) {
            stringArray = GRANDFATHERED.get(new AsciiUtil.CaseInsensitiveKey(string.substring(0, n)));
        }
        if (stringArray != null) {
            stringTokenIterator = stringArray[0].length() == string.length() ? new StringTokenIterator(stringArray[5], SEP) : new StringTokenIterator(stringArray[5] + string.substring(n), SEP);
            bl = true;
        } else {
            stringTokenIterator = new StringTokenIterator(string, SEP);
        }
        LanguageTag languageTag = new LanguageTag();
        if (languageTag.parseLanguage(stringTokenIterator, parseStatus)) {
            if (languageTag._language.length() <= 3) {
                languageTag.parseExtlangs(stringTokenIterator, parseStatus);
            }
            languageTag.parseScript(stringTokenIterator, parseStatus);
            languageTag.parseRegion(stringTokenIterator, parseStatus);
            languageTag.parseVariants(stringTokenIterator, parseStatus);
            languageTag.parseExtensions(stringTokenIterator, parseStatus);
        }
        languageTag.parsePrivateuse(stringTokenIterator, parseStatus);
        if (bl) {
            if (!$assertionsDisabled && !stringTokenIterator.isDone()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && parseStatus.isError()) {
                throw new AssertionError();
            }
            parseStatus._parseLength = string.length();
        } else if (!stringTokenIterator.isDone() && !parseStatus.isError()) {
            String string2 = stringTokenIterator.current();
            parseStatus._errorIndex = stringTokenIterator.currentStart();
            parseStatus._errorMsg = string2.length() == 0 ? "Empty subtag" : "Invalid subtag: " + string2;
        }
        return languageTag;
    }

    private boolean parseLanguage(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        String string = stringTokenIterator.current();
        if (LanguageTag.isLanguage(string)) {
            bl = true;
            this._language = string;
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
        }
        return bl;
    }

    private boolean parseExtlangs(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        String string;
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        while (!stringTokenIterator.isDone() && LanguageTag.isExtlang(string = stringTokenIterator.current())) {
            bl = true;
            if (this._extlangs.isEmpty()) {
                this._extlangs = new ArrayList<String>(3);
            }
            this._extlangs.add(string);
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
            if (this._extlangs.size() != 3) continue;
            break;
        }
        return bl;
    }

    private boolean parseScript(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        String string = stringTokenIterator.current();
        if (LanguageTag.isScript(string)) {
            bl = true;
            this._script = string;
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
        }
        return bl;
    }

    private boolean parseRegion(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        String string = stringTokenIterator.current();
        if (LanguageTag.isRegion(string)) {
            bl = true;
            this._region = string;
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
        }
        return bl;
    }

    private boolean parseVariants(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        String string;
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        while (!stringTokenIterator.isDone() && LanguageTag.isVariant(string = stringTokenIterator.current())) {
            bl = true;
            if (this._variants.isEmpty()) {
                this._variants = new ArrayList<String>(3);
            }
            if (!this._variants.contains(string = string.toUpperCase())) {
                this._variants.add(string);
            }
            parseStatus._parseLength = stringTokenIterator.currentEnd();
            stringTokenIterator.next();
        }
        return bl;
    }

    private boolean parseExtensions(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        String string;
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSingleton(string = stringTokenIterator.current())) {
            int n = stringTokenIterator.currentStart();
            String string2 = string.toLowerCase();
            StringBuilder stringBuilder = new StringBuilder(string2);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSubtag(string = stringTokenIterator.current())) {
                stringBuilder.append(SEP).append(string);
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (parseStatus._parseLength <= n) {
                parseStatus._errorIndex = n;
                parseStatus._errorMsg = "Incomplete extension '" + string2 + "'";
                break;
            }
            if (this._extensions.size() == 0) {
                this._extensions = new ArrayList<String>(4);
            }
            boolean bl2 = false;
            for (String string3 : this._extensions) {
                bl2 |= string3.charAt(0) == stringBuilder.charAt(0);
            }
            if (!bl2) {
                this._extensions.add(stringBuilder.toString());
            }
            bl = true;
        }
        return bl;
    }

    private boolean parsePrivateuse(StringTokenIterator stringTokenIterator, ParseStatus parseStatus) {
        if (stringTokenIterator.isDone() || parseStatus.isError()) {
            return true;
        }
        boolean bl = false;
        String string = stringTokenIterator.current();
        if (LanguageTag.isPrivateusePrefix(string)) {
            int n = stringTokenIterator.currentStart();
            StringBuilder stringBuilder = new StringBuilder(string);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone() && LanguageTag.isPrivateuseSubtag(string = stringTokenIterator.current())) {
                stringBuilder.append(SEP).append(string);
                parseStatus._parseLength = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (parseStatus._parseLength <= n) {
                parseStatus._errorIndex = n;
                parseStatus._errorMsg = "Incomplete privateuse";
            } else {
                this._privateuse = stringBuilder.toString();
                bl = true;
            }
        }
        return bl;
    }

    public static LanguageTag parseLocale(BaseLocale baseLocale, LocaleExtensions localeExtensions) {
        Object object;
        Object object2;
        Object object3;
        ArrayList<String> arrayList;
        LanguageTag languageTag = new LanguageTag();
        String string = baseLocale.getLanguage();
        String string2 = baseLocale.getScript();
        String string3 = baseLocale.getRegion();
        String string4 = baseLocale.getVariant();
        boolean bl = false;
        String string5 = null;
        if (string.length() > 0 && LanguageTag.isLanguage(string)) {
            if (string.equals("iw")) {
                string = "he";
            } else if (string.equals("ji")) {
                string = "yi";
            } else if (string.equals("in")) {
                string = "id";
            }
            languageTag._language = string;
        }
        if (string2.length() > 0 && LanguageTag.isScript(string2)) {
            languageTag._script = LanguageTag.canonicalizeScript(string2);
            bl = true;
        }
        if (string3.length() > 0 && LanguageTag.isRegion(string3)) {
            languageTag._region = LanguageTag.canonicalizeRegion(string3);
            bl = true;
        }
        if (string4.length() > 0) {
            arrayList = null;
            object3 = new StringTokenIterator(string4, "_");
            while (!((StringTokenIterator)object3).isDone() && LanguageTag.isVariant((String)(object2 = ((StringTokenIterator)object3).current()))) {
                if (arrayList == null) {
                    arrayList = new ArrayList<String>();
                }
                arrayList.add(LanguageTag.canonicalizeVariant((String)object2));
                ((StringTokenIterator)object3).next();
            }
            if (arrayList != null) {
                languageTag._variants = arrayList;
                bl = true;
            }
            if (!((StringTokenIterator)object3).isDone()) {
                object2 = new StringBuilder();
                while (!((StringTokenIterator)object3).isDone() && LanguageTag.isPrivateuseSubtag((String)(object = ((StringTokenIterator)object3).current()))) {
                    if (((StringBuilder)object2).length() > 0) {
                        ((StringBuilder)object2).append(SEP);
                    }
                    object = AsciiUtil.toLowerString((String)object);
                    ((StringBuilder)object2).append((String)object);
                    ((StringTokenIterator)object3).next();
                }
                if (((StringBuilder)object2).length() > 0) {
                    string5 = ((StringBuilder)object2).toString();
                }
            }
        }
        arrayList = null;
        object3 = null;
        object2 = localeExtensions.getKeys();
        object = object2.iterator();
        while (object.hasNext()) {
            Character c = object.next();
            Extension extension = localeExtensions.getExtension(c);
            if (LanguageTag.isPrivateusePrefixChar(c.charValue())) {
                object3 = extension.getValue();
                continue;
            }
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            arrayList.add(c.toString() + SEP + extension.getValue());
        }
        if (arrayList != null) {
            languageTag._extensions = arrayList;
            bl = true;
        }
        if (string5 != null) {
            object3 = object3 == null ? "lvariant-" + string5 : (String)object3 + SEP + PRIVUSE_VARIANT_PREFIX + SEP + string5.replace("_", SEP);
        }
        if (object3 != null) {
            languageTag._privateuse = object3;
        }
        if (languageTag._language.length() == 0 && (bl || object3 == null)) {
            languageTag._language = UNDETERMINED;
        }
        return languageTag;
    }

    public String getLanguage() {
        return this._language;
    }

    public List<String> getExtlangs() {
        return Collections.unmodifiableList(this._extlangs);
    }

    public String getScript() {
        return this._script;
    }

    public String getRegion() {
        return this._region;
    }

    public List<String> getVariants() {
        return Collections.unmodifiableList(this._variants);
    }

    public List<String> getExtensions() {
        return Collections.unmodifiableList(this._extensions);
    }

    public String getPrivateuse() {
        return this._privateuse;
    }

    public static boolean isLanguage(String string) {
        return string.length() >= 2 && string.length() <= 8 && AsciiUtil.isAlphaString(string);
    }

    public static boolean isExtlang(String string) {
        return string.length() == 3 && AsciiUtil.isAlphaString(string);
    }

    public static boolean isScript(String string) {
        return string.length() == 4 && AsciiUtil.isAlphaString(string);
    }

    public static boolean isRegion(String string) {
        return string.length() == 2 && AsciiUtil.isAlphaString(string) || string.length() == 3 && AsciiUtil.isNumericString(string);
    }

    public static boolean isVariant(String string) {
        int n = string.length();
        if (n >= 5 && n <= 8) {
            return AsciiUtil.isAlphaNumericString(string);
        }
        if (n == 4) {
            return AsciiUtil.isNumeric(string.charAt(0)) && AsciiUtil.isAlphaNumeric(string.charAt(1)) && AsciiUtil.isAlphaNumeric(string.charAt(2)) && AsciiUtil.isAlphaNumeric(string.charAt(3));
        }
        return true;
    }

    public static boolean isExtensionSingleton(String string) {
        return string.length() == 1 && AsciiUtil.isAlphaNumericString(string) && !AsciiUtil.caseIgnoreMatch(PRIVATEUSE, string);
    }

    public static boolean isExtensionSingletonChar(char c) {
        return LanguageTag.isExtensionSingleton(String.valueOf(c));
    }

    public static boolean isExtensionSubtag(String string) {
        return string.length() >= 2 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
    }

    public static boolean isPrivateusePrefix(String string) {
        return string.length() == 1 && AsciiUtil.caseIgnoreMatch(PRIVATEUSE, string);
    }

    public static boolean isPrivateusePrefixChar(char c) {
        return AsciiUtil.caseIgnoreMatch(PRIVATEUSE, String.valueOf(c));
    }

    public static boolean isPrivateuseSubtag(String string) {
        return string.length() >= 1 && string.length() <= 8 && AsciiUtil.isAlphaNumericString(string);
    }

    public static String canonicalizeLanguage(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtlang(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeScript(String string) {
        return AsciiUtil.toTitleString(string);
    }

    public static String canonicalizeRegion(String string) {
        return AsciiUtil.toUpperString(string);
    }

    public static String canonicalizeVariant(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtension(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtensionSingleton(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizeExtensionSubtag(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizePrivateuse(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public static String canonicalizePrivateuseSubtag(String string) {
        return AsciiUtil.toLowerString(string);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this._language.length() > 0) {
            stringBuilder.append(this._language);
            for (String string : this._extlangs) {
                stringBuilder.append(SEP).append(string);
            }
            if (this._script.length() > 0) {
                stringBuilder.append(SEP).append(this._script);
            }
            if (this._region.length() > 0) {
                stringBuilder.append(SEP).append(this._region);
            }
            for (String string : this._variants) {
                stringBuilder.append(SEP).append(string);
            }
            for (String string : this._extensions) {
                stringBuilder.append(SEP).append(string);
            }
        }
        if (this._privateuse.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(SEP);
            }
            stringBuilder.append(this._privateuse);
        }
        return stringBuilder.toString();
    }

    static {
        String[][] stringArrayArray;
        $assertionsDisabled = !LanguageTag.class.desiredAssertionStatus();
        UNDETERMINED = "und";
        GRANDFATHERED = new HashMap<AsciiUtil.CaseInsensitiveKey, String[]>();
        for (String[] stringArray : stringArrayArray = new String[][]{{"art-lojban", "jbo"}, {"cel-gaulish", "xtg-x-cel-gaulish"}, {"en-GB-oed", "en-GB-x-oed"}, {"i-ami", "ami"}, {"i-bnn", "bnn"}, {"i-default", "en-x-i-default"}, {"i-enochian", "und-x-i-enochian"}, {"i-hak", "hak"}, {"i-klingon", "tlh"}, {"i-lux", "lb"}, {"i-mingo", "see-x-i-mingo"}, {"i-navajo", "nv"}, {"i-pwn", "pwn"}, {"i-tao", "tao"}, {"i-tay", "tay"}, {"i-tsu", "tsu"}, {"no-bok", "nb"}, {"no-nyn", "nn"}, {"sgn-BE-FR", "sfb"}, {"sgn-BE-NL", "vgt"}, {"sgn-CH-DE", "sgg"}, {"zh-guoyu", "cmn"}, {"zh-hakka", "hak"}, {"zh-min", "nan-x-zh-min"}, {"zh-min-nan", "nan"}, {"zh-xiang", "hsn"}}) {
            GRANDFATHERED.put(new AsciiUtil.CaseInsensitiveKey(stringArray[0]), stringArray);
        }
    }
}

