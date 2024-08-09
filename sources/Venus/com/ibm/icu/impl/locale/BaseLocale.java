/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.LocaleObjectCache;

public final class BaseLocale {
    private static final boolean JDKIMPL = false;
    public static final String SEP = "_";
    private static final Cache CACHE = new Cache();
    public static final BaseLocale ROOT = BaseLocale.getInstance("", "", "", "");
    private String _language = "";
    private String _script = "";
    private String _region = "";
    private String _variant = "";
    private volatile transient int _hash = 0;

    private BaseLocale(String string, String string2, String string3, String string4) {
        if (string != null) {
            this._language = AsciiUtil.toLowerString(string).intern();
        }
        if (string2 != null) {
            this._script = AsciiUtil.toTitleString(string2).intern();
        }
        if (string3 != null) {
            this._region = AsciiUtil.toUpperString(string3).intern();
        }
        if (string4 != null) {
            this._variant = AsciiUtil.toUpperString(string4).intern();
        }
    }

    public static BaseLocale getInstance(String string, String string2, String string3, String string4) {
        Key key = new Key(string, string2, string3, string4);
        BaseLocale baseLocale = (BaseLocale)CACHE.get(key);
        return baseLocale;
    }

    public String getLanguage() {
        return this._language;
    }

    public String getScript() {
        return this._script;
    }

    public String getRegion() {
        return this._region;
    }

    public String getVariant() {
        return this._variant;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof BaseLocale)) {
            return true;
        }
        BaseLocale baseLocale = (BaseLocale)object;
        return this.hashCode() == baseLocale.hashCode() && this._language.equals(baseLocale._language) && this._script.equals(baseLocale._script) && this._region.equals(baseLocale._region) && this._variant.equals(baseLocale._variant);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this._language.length() > 0) {
            stringBuilder.append("language=");
            stringBuilder.append(this._language);
        }
        if (this._script.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("script=");
            stringBuilder.append(this._script);
        }
        if (this._region.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("region=");
            stringBuilder.append(this._region);
        }
        if (this._variant.length() > 0) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append("variant=");
            stringBuilder.append(this._variant);
        }
        return stringBuilder.toString();
    }

    public int hashCode() {
        int n = this._hash;
        if (n == 0) {
            int n2;
            for (n2 = 0; n2 < this._language.length(); ++n2) {
                n = 31 * n + this._language.charAt(n2);
            }
            for (n2 = 0; n2 < this._script.length(); ++n2) {
                n = 31 * n + this._script.charAt(n2);
            }
            for (n2 = 0; n2 < this._region.length(); ++n2) {
                n = 31 * n + this._region.charAt(n2);
            }
            for (n2 = 0; n2 < this._variant.length(); ++n2) {
                n = 31 * n + this._variant.charAt(n2);
            }
            this._hash = n;
        }
        return n;
    }

    BaseLocale(String string, String string2, String string3, String string4, 1 var5_5) {
        this(string, string2, string3, string4);
    }

    private static class Cache
    extends LocaleObjectCache<Key, BaseLocale> {
        @Override
        protected Key normalizeKey(Key key) {
            return Key.normalize(key);
        }

        @Override
        protected BaseLocale createObject(Key key) {
            return new BaseLocale(Key.access$000(key), Key.access$100(key), Key.access$200(key), Key.access$300(key), null);
        }

        @Override
        protected Object normalizeKey(Object object) {
            return this.normalizeKey((Key)object);
        }

        @Override
        protected Object createObject(Object object) {
            return this.createObject((Key)object);
        }
    }

    private static class Key
    implements Comparable<Key> {
        private String _lang = "";
        private String _scrt = "";
        private String _regn = "";
        private String _vart = "";
        private volatile int _hash;

        public Key(String string, String string2, String string3, String string4) {
            if (string != null) {
                this._lang = string;
            }
            if (string2 != null) {
                this._scrt = string2;
            }
            if (string3 != null) {
                this._regn = string3;
            }
            if (string4 != null) {
                this._vart = string4;
            }
        }

        public boolean equals(Object object) {
            return this == object || object instanceof Key && AsciiUtil.caseIgnoreMatch(((Key)object)._lang, this._lang) && AsciiUtil.caseIgnoreMatch(((Key)object)._scrt, this._scrt) && AsciiUtil.caseIgnoreMatch(((Key)object)._regn, this._regn) && AsciiUtil.caseIgnoreMatch(((Key)object)._vart, this._vart);
        }

        @Override
        public int compareTo(Key key) {
            int n = AsciiUtil.caseIgnoreCompare(this._lang, key._lang);
            if (n == 0 && (n = AsciiUtil.caseIgnoreCompare(this._scrt, key._scrt)) == 0 && (n = AsciiUtil.caseIgnoreCompare(this._regn, key._regn)) == 0) {
                n = AsciiUtil.caseIgnoreCompare(this._vart, key._vart);
            }
            return n;
        }

        public int hashCode() {
            int n = this._hash;
            if (n == 0) {
                int n2;
                for (n2 = 0; n2 < this._lang.length(); ++n2) {
                    n = 31 * n + AsciiUtil.toLower(this._lang.charAt(n2));
                }
                for (n2 = 0; n2 < this._scrt.length(); ++n2) {
                    n = 31 * n + AsciiUtil.toLower(this._scrt.charAt(n2));
                }
                for (n2 = 0; n2 < this._regn.length(); ++n2) {
                    n = 31 * n + AsciiUtil.toLower(this._regn.charAt(n2));
                }
                for (n2 = 0; n2 < this._vart.length(); ++n2) {
                    n = 31 * n + AsciiUtil.toLower(this._vart.charAt(n2));
                }
                this._hash = n;
            }
            return n;
        }

        public static Key normalize(Key key) {
            String string = AsciiUtil.toLowerString(key._lang).intern();
            String string2 = AsciiUtil.toTitleString(key._scrt).intern();
            String string3 = AsciiUtil.toUpperString(key._regn).intern();
            String string4 = AsciiUtil.toUpperString(key._vart).intern();
            return new Key(string, string2, string3, string4);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Key)object);
        }

        static String access$000(Key key) {
            return key._lang;
        }

        static String access$100(Key key) {
            return key._scrt;
        }

        static String access$200(Key key) {
            return key._regn;
        }

        static String access$300(Key key) {
            return key._vart;
        }
    }
}

