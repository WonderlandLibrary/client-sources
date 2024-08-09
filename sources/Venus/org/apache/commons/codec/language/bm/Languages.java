/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.codec.language.bm.NameType;

public class Languages {
    public static final String ANY = "any";
    private static final Map<NameType, Languages> LANGUAGES = new EnumMap<NameType, Languages>(NameType.class);
    private final Set<String> languages;
    public static final LanguageSet NO_LANGUAGES;
    public static final LanguageSet ANY_LANGUAGE;

    public static Languages getInstance(NameType nameType) {
        return LANGUAGES.get((Object)nameType);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Languages getInstance(String string) {
        HashSet<String> hashSet = new HashSet<String>();
        InputStream inputStream = Languages.class.getClassLoader().getResourceAsStream(string);
        if (inputStream == null) {
            throw new IllegalArgumentException("Unable to resolve required resource: " + string);
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        try {
            boolean bl = false;
            while (scanner.hasNextLine()) {
                String string2 = scanner.nextLine().trim();
                if (bl) {
                    if (!string2.endsWith("*/")) continue;
                    bl = false;
                    continue;
                }
                if (string2.startsWith("/*")) {
                    bl = true;
                    continue;
                }
                if (string2.length() <= 0) continue;
                hashSet.add(string2);
            }
        } finally {
            scanner.close();
        }
        return new Languages(Collections.unmodifiableSet(hashSet));
    }

    private static String langResourceName(NameType nameType) {
        return String.format("org/apache/commons/codec/language/bm/%s_languages.txt", nameType.getName());
    }

    private Languages(Set<String> set) {
        this.languages = set;
    }

    public Set<String> getLanguages() {
        return this.languages;
    }

    static {
        for (NameType nameType : NameType.values()) {
            LANGUAGES.put(nameType, Languages.getInstance(Languages.langResourceName(nameType)));
        }
        NO_LANGUAGES = new LanguageSet(){

            @Override
            public boolean contains(String string) {
                return true;
            }

            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the empty language set.");
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }

            @Override
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return this;
            }

            @Override
            public LanguageSet merge(LanguageSet languageSet) {
                return languageSet;
            }

            public String toString() {
                return "NO_LANGUAGES";
            }
        };
        ANY_LANGUAGE = new LanguageSet(){

            @Override
            public boolean contains(String string) {
                return false;
            }

            @Override
            public String getAny() {
                throw new NoSuchElementException("Can't fetch any language from the any language set.");
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public boolean isSingleton() {
                return true;
            }

            @Override
            public LanguageSet restrictTo(LanguageSet languageSet) {
                return languageSet;
            }

            @Override
            public LanguageSet merge(LanguageSet languageSet) {
                return languageSet;
            }

            public String toString() {
                return "ANY_LANGUAGE";
            }
        };
    }

    public static final class SomeLanguages
    extends LanguageSet {
        private final Set<String> languages;

        private SomeLanguages(Set<String> set) {
            this.languages = Collections.unmodifiableSet(set);
        }

        @Override
        public boolean contains(String string) {
            return this.languages.contains(string);
        }

        @Override
        public String getAny() {
            return this.languages.iterator().next();
        }

        public Set<String> getLanguages() {
            return this.languages;
        }

        @Override
        public boolean isEmpty() {
            return this.languages.isEmpty();
        }

        @Override
        public boolean isSingleton() {
            return this.languages.size() == 1;
        }

        @Override
        public LanguageSet restrictTo(LanguageSet languageSet) {
            if (languageSet == NO_LANGUAGES) {
                return languageSet;
            }
            if (languageSet == ANY_LANGUAGE) {
                return this;
            }
            SomeLanguages someLanguages = (SomeLanguages)languageSet;
            HashSet<String> hashSet = new HashSet<String>(Math.min(this.languages.size(), someLanguages.languages.size()));
            for (String string : this.languages) {
                if (!someLanguages.languages.contains(string)) continue;
                hashSet.add(string);
            }
            return SomeLanguages.from(hashSet);
        }

        @Override
        public LanguageSet merge(LanguageSet languageSet) {
            if (languageSet == NO_LANGUAGES) {
                return this;
            }
            if (languageSet == ANY_LANGUAGE) {
                return languageSet;
            }
            SomeLanguages someLanguages = (SomeLanguages)languageSet;
            HashSet<String> hashSet = new HashSet<String>(this.languages);
            for (String string : someLanguages.languages) {
                hashSet.add(string);
            }
            return SomeLanguages.from(hashSet);
        }

        public String toString() {
            return "Languages(" + this.languages.toString() + ")";
        }

        SomeLanguages(Set set, 1 var2_2) {
            this(set);
        }
    }

    public static abstract class LanguageSet {
        public static LanguageSet from(Set<String> set) {
            return set.isEmpty() ? NO_LANGUAGES : new SomeLanguages(set, null);
        }

        public abstract boolean contains(String var1);

        public abstract String getAny();

        public abstract boolean isEmpty();

        public abstract boolean isSingleton();

        public abstract LanguageSet restrictTo(LanguageSet var1);

        abstract LanguageSet merge(LanguageSet var1);
    }
}

