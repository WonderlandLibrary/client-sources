/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.locale;

import com.ibm.icu.impl.locale.AsciiUtil;
import com.ibm.icu.impl.locale.Extension;
import com.ibm.icu.impl.locale.InternalLocaleBuilder;
import com.ibm.icu.impl.locale.LanguageTag;
import com.ibm.icu.impl.locale.UnicodeLocaleExtension;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class LocaleExtensions {
    private SortedMap<Character, Extension> _map;
    private String _id;
    private static final SortedMap<Character, Extension> EMPTY_MAP;
    public static final LocaleExtensions EMPTY_EXTENSIONS;
    public static final LocaleExtensions CALENDAR_JAPANESE;
    public static final LocaleExtensions NUMBER_THAI;
    static final boolean $assertionsDisabled;

    private LocaleExtensions() {
    }

    /*
     * WARNING - void declaration
     */
    LocaleExtensions(Map<InternalLocaleBuilder.CaseInsensitiveChar, String> map, Set<InternalLocaleBuilder.CaseInsensitiveString> set, Map<InternalLocaleBuilder.CaseInsensitiveString, String> map2) {
        Object object;
        boolean bl;
        boolean bl2 = map != null && map.size() > 0;
        boolean bl3 = set != null && set.size() > 0;
        boolean bl4 = bl = map2 != null && map2.size() > 0;
        if (!(bl2 || bl3 || bl)) {
            this._map = EMPTY_MAP;
            this._id = "";
            return;
        }
        this._map = new TreeMap<Character, Extension>();
        if (bl2) {
            for (Map.Entry<InternalLocaleBuilder.CaseInsensitiveChar, String> object2 : map.entrySet()) {
                void var10_16;
                String string;
                char unicodeLocaleExtension = AsciiUtil.toLower(object2.getKey().value());
                String string2 = object2.getValue();
                if (LanguageTag.isPrivateusePrefixChar(unicodeLocaleExtension) && (string = InternalLocaleBuilder.removePrivateuseVariant(string2)) == null) continue;
                object = new Extension(unicodeLocaleExtension, AsciiUtil.toLowerString((String)var10_16));
                this._map.put(Character.valueOf(unicodeLocaleExtension), (Extension)object);
            }
        }
        if (bl3 || bl) {
            void var8_11;
            Object object4 = null;
            Object var8_9 = null;
            if (bl3) {
                object4 = new TreeSet();
                for (InternalLocaleBuilder.CaseInsensitiveString caseInsensitiveString : set) {
                    ((TreeSet)object4).add(AsciiUtil.toLowerString(caseInsensitiveString.value()));
                }
            }
            if (bl) {
                TreeMap<Object, String> treeMap = new TreeMap<Object, String>();
                for (Map.Entry<InternalLocaleBuilder.CaseInsensitiveString, String> entry : map2.entrySet()) {
                    object = AsciiUtil.toLowerString(entry.getKey().value());
                    String string = AsciiUtil.toLowerString(entry.getValue());
                    treeMap.put(object, string);
                }
            }
            UnicodeLocaleExtension unicodeLocaleExtension = new UnicodeLocaleExtension((SortedSet<String>)object4, (SortedMap<String, String>)var8_11);
            this._map.put(Character.valueOf('u'), unicodeLocaleExtension);
        }
        if (this._map.size() == 0) {
            this._map = EMPTY_MAP;
            this._id = "";
        } else {
            this._id = LocaleExtensions.toID(this._map);
        }
    }

    public Set<Character> getKeys() {
        return Collections.unmodifiableSet(this._map.keySet());
    }

    public Extension getExtension(Character c) {
        return (Extension)this._map.get(Character.valueOf(AsciiUtil.toLower(c.charValue())));
    }

    public String getExtensionValue(Character c) {
        Extension extension = (Extension)this._map.get(Character.valueOf(AsciiUtil.toLower(c.charValue())));
        if (extension == null) {
            return null;
        }
        return extension.getValue();
    }

    public Set<String> getUnicodeLocaleAttributes() {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        if (!$assertionsDisabled && !(extension instanceof UnicodeLocaleExtension)) {
            throw new AssertionError();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleAttributes();
    }

    public Set<String> getUnicodeLocaleKeys() {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return Collections.emptySet();
        }
        if (!$assertionsDisabled && !(extension instanceof UnicodeLocaleExtension)) {
            throw new AssertionError();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleKeys();
    }

    public String getUnicodeLocaleType(String string) {
        Extension extension = (Extension)this._map.get(Character.valueOf('u'));
        if (extension == null) {
            return null;
        }
        if (!$assertionsDisabled && !(extension instanceof UnicodeLocaleExtension)) {
            throw new AssertionError();
        }
        return ((UnicodeLocaleExtension)extension).getUnicodeLocaleType(AsciiUtil.toLowerString(string));
    }

    public boolean isEmpty() {
        return this._map.isEmpty();
    }

    public static boolean isValidKey(char c) {
        return LanguageTag.isExtensionSingletonChar(c) || LanguageTag.isPrivateusePrefixChar(c);
    }

    public static boolean isValidUnicodeLocaleKey(String string) {
        return UnicodeLocaleExtension.isKey(string);
    }

    private static String toID(SortedMap<Character, Extension> sortedMap) {
        StringBuilder stringBuilder = new StringBuilder();
        Extension extension = null;
        for (Map.Entry<Character, Extension> entry : sortedMap.entrySet()) {
            char c = entry.getKey().charValue();
            Extension extension2 = entry.getValue();
            if (LanguageTag.isPrivateusePrefixChar(c)) {
                extension = extension2;
                continue;
            }
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append(extension2);
        }
        if (extension != null) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("-");
            }
            stringBuilder.append(extension);
        }
        return stringBuilder.toString();
    }

    public String toString() {
        return this._id;
    }

    public String getID() {
        return this._id;
    }

    public int hashCode() {
        return this._id.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof LocaleExtensions)) {
            return true;
        }
        return this._id.equals(((LocaleExtensions)object)._id);
    }

    static {
        $assertionsDisabled = !LocaleExtensions.class.desiredAssertionStatus();
        EMPTY_MAP = Collections.unmodifiableSortedMap(new TreeMap());
        EMPTY_EXTENSIONS = new LocaleExtensions();
        LocaleExtensions.EMPTY_EXTENSIONS._id = "";
        LocaleExtensions.EMPTY_EXTENSIONS._map = EMPTY_MAP;
        CALENDAR_JAPANESE = new LocaleExtensions();
        LocaleExtensions.CALENDAR_JAPANESE._id = "u-ca-japanese";
        LocaleExtensions.CALENDAR_JAPANESE._map = new TreeMap<Character, Extension>();
        LocaleExtensions.CALENDAR_JAPANESE._map.put(Character.valueOf('u'), UnicodeLocaleExtension.CA_JAPANESE);
        NUMBER_THAI = new LocaleExtensions();
        LocaleExtensions.NUMBER_THAI._id = "u-nu-thai";
        LocaleExtensions.NUMBER_THAI._map = new TreeMap<Character, Extension>();
        LocaleExtensions.NUMBER_THAI._map.put(Character.valueOf('u'), UnicodeLocaleExtension.NU_THAI);
    }
}

