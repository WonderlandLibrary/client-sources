/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.BaseLocale;
import com.ibm.icu.impl.locale.Extension;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.locale.LocaleExtensions;
import com.ibm.icu.impl.locale.LocaleSyntaxException;
import com.ibm.icu.impl.locale.StringTokenIterator;
import com.ibm.icu.impl.locale.UnicodeLocaleExtension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class InternalLocaleBuilder {
    private static final boolean JDKIMPL = false;
    private String _language = "";
    private String _script = "";
    private String _region = "";
    private String _variant = "";
    private static final CaseInsensitiveChar PRIVUSE_KEY;
    private HashMap<CaseInsensitiveChar, String> _extensions;
    private HashSet<CaseInsensitiveString> _uattributes;
    private HashMap<CaseInsensitiveString, String> _ukeywords;
    static final boolean $assertionsDisabled;

    public InternalLocaleBuilder setLanguage(String string) throws LocaleSyntaxException {
        if (string == null || string.length() == 0) {
            this._language = "";
        } else {
            if (!LanguageTag.isLanguage(string)) {
                throw new LocaleSyntaxException("Ill-formed language: " + string, 0);
            }
            this._language = string;
        }
        return this;
    }

    public InternalLocaleBuilder setScript(String string) throws LocaleSyntaxException {
        if (string == null || string.length() == 0) {
            this._script = "";
        } else {
            if (!LanguageTag.isScript(string)) {
                throw new LocaleSyntaxException("Ill-formed script: " + string, 0);
            }
            this._script = string;
        }
        return this;
    }

    public InternalLocaleBuilder setRegion(String string) throws LocaleSyntaxException {
        if (string == null || string.length() == 0) {
            this._region = "";
        } else {
            if (!LanguageTag.isRegion(string)) {
                throw new LocaleSyntaxException("Ill-formed region: " + string, 0);
            }
            this._region = string;
        }
        return this;
    }

    public InternalLocaleBuilder setVariant(String string) throws LocaleSyntaxException {
        if (string == null || string.length() == 0) {
            this._variant = "";
        } else {
            String string2 = string.replaceAll("-", "_");
            int n = this.checkVariants(string2, "_");
            if (n != -1) {
                throw new LocaleSyntaxException("Ill-formed variant: " + string, n);
            }
            this._variant = string2;
        }
        return this;
    }

    public InternalLocaleBuilder addUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (string == null || !UnicodeLocaleExtension.isAttribute(string)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + string);
        }
        if (this._uattributes == null) {
            this._uattributes = new HashSet(4);
        }
        this._uattributes.add(new CaseInsensitiveString(string));
        return this;
    }

    public InternalLocaleBuilder removeUnicodeLocaleAttribute(String string) throws LocaleSyntaxException {
        if (string == null || !UnicodeLocaleExtension.isAttribute(string)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale attribute: " + string);
        }
        if (this._uattributes != null) {
            this._uattributes.remove(new CaseInsensitiveString(string));
        }
        return this;
    }

    public InternalLocaleBuilder setUnicodeLocaleKeyword(String string, String string2) throws LocaleSyntaxException {
        if (!UnicodeLocaleExtension.isKey(string)) {
            throw new LocaleSyntaxException("Ill-formed Unicode locale keyword key: " + string);
        }
        CaseInsensitiveString caseInsensitiveString = new CaseInsensitiveString(string);
        if (string2 == null) {
            if (this._ukeywords != null) {
                this._ukeywords.remove(caseInsensitiveString);
            }
        } else {
            if (string2.length() != 0) {
                String string3 = string2.replaceAll("_", "-");
                StringTokenIterator stringTokenIterator = new StringTokenIterator(string3, "-");
                while (!stringTokenIterator.isDone()) {
                    String string4 = stringTokenIterator.current();
                    if (!UnicodeLocaleExtension.isTypeSubtag(string4)) {
                        throw new LocaleSyntaxException("Ill-formed Unicode locale keyword type: " + string2, stringTokenIterator.currentStart());
                    }
                    stringTokenIterator.next();
                }
            }
            if (this._ukeywords == null) {
                this._ukeywords = new HashMap(4);
            }
            this._ukeywords.put(caseInsensitiveString, string2);
        }
        return this;
    }

    public InternalLocaleBuilder setExtension(char c, String string) throws LocaleSyntaxException {
        boolean bl = LanguageTag.isPrivateusePrefixChar(c);
        if (!bl && !LanguageTag.isExtensionSingletonChar(c)) {
            throw new LocaleSyntaxException("Ill-formed extension key: " + c);
        }
        boolean bl2 = string == null || string.length() == 0;
        CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(c);
        if (bl2) {
            if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                if (this._uattributes != null) {
                    this._uattributes.clear();
                }
                if (this._ukeywords != null) {
                    this._ukeywords.clear();
                }
            } else if (this._extensions != null && this._extensions.containsKey(caseInsensitiveChar)) {
                this._extensions.remove(caseInsensitiveChar);
            }
        } else {
            String string2 = string.replaceAll("_", "-");
            StringTokenIterator stringTokenIterator = new StringTokenIterator(string2, "-");
            while (!stringTokenIterator.isDone()) {
                String string3 = stringTokenIterator.current();
                boolean bl3 = bl ? LanguageTag.isPrivateuseSubtag(string3) : LanguageTag.isExtensionSubtag(string3);
                if (!bl3) {
                    throw new LocaleSyntaxException("Ill-formed extension value: " + string3, stringTokenIterator.currentStart());
                }
                stringTokenIterator.next();
            }
            if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                this.setUnicodeLocaleExtension(string2);
            } else {
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(caseInsensitiveChar, string2);
            }
        }
        return this;
    }

    public InternalLocaleBuilder setExtensions(String string) throws LocaleSyntaxException {
        CharSequence charSequence;
        int n;
        String string2;
        if (string == null || string.length() == 0) {
            this.clearExtensions();
            return this;
        }
        string = string.replaceAll("_", "-");
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        ArrayList<String> arrayList = null;
        String string3 = null;
        int n2 = 0;
        while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSingleton(string2 = stringTokenIterator.current())) {
            n = stringTokenIterator.currentStart();
            charSequence = string2;
            StringBuilder stringBuilder = new StringBuilder((String)charSequence);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone() && LanguageTag.isExtensionSubtag(string2 = stringTokenIterator.current())) {
                stringBuilder.append("-").append(string2);
                n2 = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (n2 < n) {
                throw new LocaleSyntaxException("Incomplete extension '" + (String)charSequence + "'", n);
            }
            if (arrayList == null) {
                arrayList = new ArrayList<String>(4);
            }
            arrayList.add(stringBuilder.toString());
        }
        if (!stringTokenIterator.isDone() && LanguageTag.isPrivateusePrefix(string2 = stringTokenIterator.current())) {
            n = stringTokenIterator.currentStart();
            charSequence = new StringBuilder(string2);
            stringTokenIterator.next();
            while (!stringTokenIterator.isDone() && LanguageTag.isPrivateuseSubtag(string2 = stringTokenIterator.current())) {
                ((StringBuilder)charSequence).append("-").append(string2);
                n2 = stringTokenIterator.currentEnd();
                stringTokenIterator.next();
            }
            if (n2 <= n) {
                throw new LocaleSyntaxException("Incomplete privateuse:" + string.substring(n), n);
            }
            string3 = ((StringBuilder)charSequence).toString();
        }
        if (!stringTokenIterator.isDone()) {
            throw new LocaleSyntaxException("Ill-formed extension subtags:" + string.substring(stringTokenIterator.currentStart()), stringTokenIterator.currentStart());
        }
        return this.setExtensions(arrayList, string3);
    }

    private InternalLocaleBuilder setExtensions(List<String> list, String string) {
        this.clearExtensions();
        if (list != null && list.size() > 0) {
            HashSet hashSet = new HashSet(list.size());
            for (String string2 : list) {
                CaseInsensitiveChar caseInsensitiveChar = new CaseInsensitiveChar(string2.charAt(0));
                if (hashSet.contains(caseInsensitiveChar)) continue;
                if (UnicodeLocaleExtension.isSingletonChar(caseInsensitiveChar.value())) {
                    this.setUnicodeLocaleExtension(string2.substring(2));
                    continue;
                }
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(caseInsensitiveChar, string2.substring(2));
            }
        }
        if (string != null && string.length() > 0) {
            if (this._extensions == null) {
                this._extensions = new HashMap(1);
            }
            this._extensions.put(new CaseInsensitiveChar(string.charAt(0)), string.substring(2));
        }
        return this;
    }

    public InternalLocaleBuilder setLanguageTag(LanguageTag languageTag) {
        Object object;
        this.clear();
        if (languageTag.getExtlangs().size() > 0) {
            this._language = languageTag.getExtlangs().get(0);
        } else {
            object = languageTag.getLanguage();
            if (!((String)object).equals(LanguageTag.UNDETERMINED)) {
                this._language = object;
            }
        }
        this._script = languageTag.getScript();
        this._region = languageTag.getRegion();
        object = languageTag.getVariants();
        if (object.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder((String)object.get(0));
            for (int i = 1; i < object.size(); ++i) {
                stringBuilder.append("_").append((String)object.get(i));
            }
            this._variant = stringBuilder.toString();
        }
        this.setExtensions(languageTag.getExtensions(), languageTag.getPrivateuse());
        return this;
    }

    public InternalLocaleBuilder setLocale(BaseLocale baseLocale, LocaleExtensions localeExtensions) throws LocaleSyntaxException {
        Set<Character> set;
        int n;
        String string = baseLocale.getLanguage();
        String string2 = baseLocale.getScript();
        String string3 = baseLocale.getRegion();
        String string4 = baseLocale.getVariant();
        if (string.length() > 0 && !LanguageTag.isLanguage(string)) {
            throw new LocaleSyntaxException("Ill-formed language: " + string);
        }
        if (string2.length() > 0 && !LanguageTag.isScript(string2)) {
            throw new LocaleSyntaxException("Ill-formed script: " + string2);
        }
        if (string3.length() > 0 && !LanguageTag.isRegion(string3)) {
            throw new LocaleSyntaxException("Ill-formed region: " + string3);
        }
        if (string4.length() > 0 && (n = this.checkVariants(string4, "_")) != -1) {
            throw new LocaleSyntaxException("Ill-formed variant: " + string4, n);
        }
        this._language = string;
        this._script = string2;
        this._region = string3;
        this._variant = string4;
        this.clearExtensions();
        Set<Character> set2 = set = localeExtensions == null ? null : localeExtensions.getKeys();
        if (set != null) {
            for (Character c : set) {
                Extension extension = localeExtensions.getExtension(c);
                if (extension instanceof UnicodeLocaleExtension) {
                    UnicodeLocaleExtension unicodeLocaleExtension = (UnicodeLocaleExtension)extension;
                    for (String string5 : unicodeLocaleExtension.getUnicodeLocaleAttributes()) {
                        if (this._uattributes == null) {
                            this._uattributes = new HashSet(4);
                        }
                        this._uattributes.add(new CaseInsensitiveString(string5));
                    }
                    for (String string5 : unicodeLocaleExtension.getUnicodeLocaleKeys()) {
                        if (this._ukeywords == null) {
                            this._ukeywords = new HashMap(4);
                        }
                        this._ukeywords.put(new CaseInsensitiveString(string5), unicodeLocaleExtension.getUnicodeLocaleType(string5));
                    }
                    continue;
                }
                if (this._extensions == null) {
                    this._extensions = new HashMap(4);
                }
                this._extensions.put(new CaseInsensitiveChar(c.charValue()), extension.getValue());
            }
        }
        return this;
    }

    public InternalLocaleBuilder clear() {
        this._language = "";
        this._script = "";
        this._region = "";
        this._variant = "";
        this.clearExtensions();
        return this;
    }

    public InternalLocaleBuilder clearExtensions() {
        if (this._extensions != null) {
            this._extensions.clear();
        }
        if (this._uattributes != null) {
            this._uattributes.clear();
        }
        if (this._ukeywords != null) {
            this._ukeywords.clear();
        }
        return this;
    }

    public BaseLocale getBaseLocale() {
        String string;
        String string2 = this._language;
        String string3 = this._script;
        String string4 = this._region;
        String string5 = this._variant;
        if (this._extensions != null && (string = this._extensions.get(PRIVUSE_KEY)) != null) {
            StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
            boolean bl = false;
            int n = -1;
            while (!stringTokenIterator.isDone()) {
                if (bl) {
                    n = stringTokenIterator.currentStart();
                    break;
                }
                if (AsciiUtil.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
                    bl = true;
                }
                stringTokenIterator.next();
            }
            if (n != -1) {
                StringBuilder stringBuilder = new StringBuilder(string5);
                if (stringBuilder.length() != 0) {
                    stringBuilder.append("_");
                }
                stringBuilder.append(string.substring(n).replaceAll("-", "_"));
                string5 = stringBuilder.toString();
            }
        }
        return BaseLocale.getInstance(string2, string3, string4, string5);
    }

    public LocaleExtensions getLocaleExtensions() {
        if (!(this._extensions != null && this._extensions.size() != 0 || this._uattributes != null && this._uattributes.size() != 0 || this._ukeywords != null && this._ukeywords.size() != 0)) {
            return LocaleExtensions.EMPTY_EXTENSIONS;
        }
        return new LocaleExtensions(this._extensions, this._uattributes, this._ukeywords);
    }

    static String removePrivateuseVariant(String string) {
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        int n = -1;
        boolean bl = false;
        while (!stringTokenIterator.isDone()) {
            if (n != -1) {
                bl = true;
                break;
            }
            if (AsciiUtil.caseIgnoreMatch(stringTokenIterator.current(), "lvariant")) {
                n = stringTokenIterator.currentStart();
            }
            stringTokenIterator.next();
        }
        if (!bl) {
            return string;
        }
        if (!$assertionsDisabled && n != 0 && n <= 1) {
            throw new AssertionError();
        }
        return n == 0 ? null : string.substring(0, n - 1);
    }

    private int checkVariants(String string, String string2) {
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, string2);
        while (!stringTokenIterator.isDone()) {
            String string3 = stringTokenIterator.current();
            if (!LanguageTag.isVariant(string3)) {
                return stringTokenIterator.currentStart();
            }
            stringTokenIterator.next();
        }
        return 1;
    }

    private void setUnicodeLocaleExtension(String string) {
        if (this._uattributes != null) {
            this._uattributes.clear();
        }
        if (this._ukeywords != null) {
            this._ukeywords.clear();
        }
        StringTokenIterator stringTokenIterator = new StringTokenIterator(string, "-");
        while (!stringTokenIterator.isDone() && UnicodeLocaleExtension.isAttribute(stringTokenIterator.current())) {
            if (this._uattributes == null) {
                this._uattributes = new HashSet(4);
            }
            this._uattributes.add(new CaseInsensitiveString(stringTokenIterator.current()));
            stringTokenIterator.next();
        }
        CaseInsensitiveString caseInsensitiveString = null;
        int n = -1;
        int n2 = -1;
        while (!stringTokenIterator.isDone()) {
            String string2;
            if (caseInsensitiveString != null) {
                if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                    if (!$assertionsDisabled && n != -1 && n2 == -1) {
                        throw new AssertionError();
                    }
                    String string3 = string2 = n == -1 ? "" : string.substring(n, n2);
                    if (this._ukeywords == null) {
                        this._ukeywords = new HashMap(4);
                    }
                    this._ukeywords.put(caseInsensitiveString, string2);
                    CaseInsensitiveString caseInsensitiveString2 = new CaseInsensitiveString(stringTokenIterator.current());
                    caseInsensitiveString = this._ukeywords.containsKey(caseInsensitiveString2) ? null : caseInsensitiveString2;
                    n2 = -1;
                    n = -1;
                } else {
                    if (n == -1) {
                        n = stringTokenIterator.currentStart();
                    }
                    n2 = stringTokenIterator.currentEnd();
                }
            } else if (UnicodeLocaleExtension.isKey(stringTokenIterator.current())) {
                caseInsensitiveString = new CaseInsensitiveString(stringTokenIterator.current());
                if (this._ukeywords != null && this._ukeywords.containsKey(caseInsensitiveString)) {
                    caseInsensitiveString = null;
                }
            }
            if (!stringTokenIterator.hasNext()) {
                if (caseInsensitiveString == null) break;
                if (!$assertionsDisabled && n != -1 && n2 == -1) {
                    throw new AssertionError();
                }
                String string4 = string2 = n == -1 ? "" : string.substring(n, n2);
                if (this._ukeywords == null) {
                    this._ukeywords = new HashMap(4);
                }
                this._ukeywords.put(caseInsensitiveString, string2);
                break;
            }
            stringTokenIterator.next();
        }
    }

    static {
        $assertionsDisabled = !InternalLocaleBuilder.class.desiredAssertionStatus();
        PRIVUSE_KEY = new CaseInsensitiveChar("x".charAt(0));
    }

    static class CaseInsensitiveChar {
        private char _c;

        CaseInsensitiveChar(char c) {
            this._c = c;
        }

        public char value() {
            return this._c;
        }

        public int hashCode() {
            return AsciiUtil.toLower(this._c);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof CaseInsensitiveChar)) {
                return true;
            }
            return this._c == AsciiUtil.toLower(((CaseInsensitiveChar)object).value());
        }
    }

    static class CaseInsensitiveString {
        private String _s;

        CaseInsensitiveString(String string) {
            this._s = string;
        }

        public String value() {
            return this._s;
        }

        public int hashCode() {
            return AsciiUtil.toLowerString(this._s).hashCode();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof CaseInsensitiveString)) {
                return true;
            }
            return AsciiUtil.caseIgnoreMatch(this._s, ((CaseInsensitiveString)object).value());
        }
    }
}

