/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language.bm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.RuleType;

public class Rule {
    public static final RPattern ALL_STRINGS_RMATCHER = new RPattern(){

        @Override
        public boolean isMatch(CharSequence charSequence) {
            return false;
        }
    };
    public static final String ALL = "ALL";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String HASH_INCLUDE = "#include";
    private static final Map<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>> RULES = new EnumMap<NameType, Map<RuleType, Map<String, Map<String, List<Rule>>>>>(NameType.class);
    private final RPattern lContext;
    private final String pattern;
    private final PhonemeExpr phoneme;
    private final RPattern rContext;

    private static boolean contains(CharSequence charSequence, char c) {
        for (int i = 0; i < charSequence.length(); ++i) {
            if (charSequence.charAt(i) != c) continue;
            return false;
        }
        return true;
    }

    private static String createResourceName(NameType nameType, RuleType ruleType, String string) {
        return String.format("org/apache/commons/codec/language/bm/%s_%s_%s.txt", nameType.getName(), ruleType.getName(), string);
    }

    private static Scanner createScanner(NameType nameType, RuleType ruleType, String string) {
        String string2 = Rule.createResourceName(nameType, ruleType, string);
        InputStream inputStream = Languages.class.getClassLoader().getResourceAsStream(string2);
        if (inputStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + string2);
        }
        return new Scanner(inputStream, "UTF-8");
    }

    private static Scanner createScanner(String string) {
        String string2 = String.format("org/apache/commons/codec/language/bm/%s.txt", string);
        InputStream inputStream = Languages.class.getClassLoader().getResourceAsStream(string2);
        if (inputStream == null) {
            throw new IllegalArgumentException("Unable to load resource: " + string2);
        }
        return new Scanner(inputStream, "UTF-8");
    }

    private static boolean endsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return true;
        }
        int n = charSequence.length() - 1;
        for (int i = charSequence2.length() - 1; i >= 0; --i) {
            if (charSequence.charAt(n) != charSequence2.charAt(i)) {
                return true;
            }
            --n;
        }
        return false;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        Map<String, List<Rule>> map = Rule.getInstanceMap(nameType, ruleType, languageSet);
        ArrayList<Rule> arrayList = new ArrayList<Rule>();
        for (List<Rule> list : map.values()) {
            arrayList.addAll(list);
        }
        return arrayList;
    }

    public static List<Rule> getInstance(NameType nameType, RuleType ruleType, String string) {
        return Rule.getInstance(nameType, ruleType, Languages.LanguageSet.from(new HashSet<String>(Arrays.asList(string))));
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, Languages.LanguageSet languageSet) {
        return languageSet.isSingleton() ? Rule.getInstanceMap(nameType, ruleType, languageSet.getAny()) : Rule.getInstanceMap(nameType, ruleType, "any");
    }

    public static Map<String, List<Rule>> getInstanceMap(NameType nameType, RuleType ruleType, String string) {
        Map<String, List<Rule>> map = RULES.get((Object)nameType).get((Object)ruleType).get(string);
        if (map == null) {
            throw new IllegalArgumentException(String.format("No rules found for %s, %s, %s.", nameType.getName(), ruleType.getName(), string));
        }
        return map;
    }

    private static Phoneme parsePhoneme(String string) {
        int n = string.indexOf("[");
        if (n >= 0) {
            if (!string.endsWith("]")) {
                throw new IllegalArgumentException("Phoneme expression contains a '[' but does not end in ']'");
            }
            String string2 = string.substring(0, n);
            String string3 = string.substring(n + 1, string.length() - 1);
            HashSet<String> hashSet = new HashSet<String>(Arrays.asList(string3.split("[+]")));
            return new Phoneme(string2, Languages.LanguageSet.from(hashSet));
        }
        return new Phoneme(string, Languages.ANY_LANGUAGE);
    }

    private static PhonemeExpr parsePhonemeExpr(String string) {
        if (string.startsWith("(")) {
            if (!string.endsWith(")")) {
                throw new IllegalArgumentException("Phoneme starts with '(' so must end with ')'");
            }
            ArrayList<Phoneme> arrayList = new ArrayList<Phoneme>();
            String string2 = string.substring(1, string.length() - 1);
            for (String string3 : string2.split("[|]")) {
                arrayList.add(Rule.parsePhoneme(string3));
            }
            if (string2.startsWith("|") || string2.endsWith("|")) {
                arrayList.add(new Phoneme("", Languages.ANY_LANGUAGE));
            }
            return new PhonemeList(arrayList);
        }
        return Rule.parsePhoneme(string);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static Map<String, List<Rule>> parseRules(Scanner scanner, String string) {
        HashMap<String, List<Rule>> hashMap = new HashMap<String, List<Rule>>();
        int n = 0;
        boolean bl = false;
        while (scanner.hasNextLine()) {
            Object object;
            Object object2;
            String string2;
            ++n;
            String string3 = string2 = scanner.nextLine();
            if (bl) {
                if (!string3.endsWith("*/")) continue;
                bl = false;
                continue;
            }
            if (string3.startsWith("/*")) {
                bl = true;
                continue;
            }
            int n2 = string3.indexOf("//");
            if (n2 >= 0) {
                string3 = string3.substring(0, n2);
            }
            if ((string3 = string3.trim()).length() == 0) continue;
            if (string3.startsWith(HASH_INCLUDE)) {
                object2 = string3.substring(8).trim();
                if (((String)object2).contains(" ")) {
                    throw new IllegalArgumentException("Malformed import statement '" + string2 + "' in " + string);
                }
                object = Rule.createScanner((String)object2);
                try {
                    hashMap.putAll(Rule.parseRules((Scanner)object, string + "->" + (String)object2));
                    continue;
                } finally {
                    ((Scanner)object).close();
                    continue;
                }
            }
            object2 = string3.split("\\s+");
            if (((String[])object2).length != 4) {
                throw new IllegalArgumentException("Malformed rule statement split into " + ((Object)object2).length + " parts: " + string2 + " in " + string);
            }
            try {
                object = Rule.stripQuotes((String)object2[0]);
                String string4 = Rule.stripQuotes((String)object2[5]);
                String string5 = Rule.stripQuotes((String)object2[5]);
                PhonemeExpr phonemeExpr = Rule.parsePhonemeExpr(Rule.stripQuotes((String)object2[5]));
                int n3 = n;
                Rule rule = new Rule((String)object, string4, string5, phonemeExpr, n3, string, (String)object, string4, string5){
                    private final int myLine;
                    private final String loc;
                    final int val$cLine;
                    final String val$location;
                    final String val$pat;
                    final String val$lCon;
                    final String val$rCon;
                    {
                        this.val$cLine = n;
                        this.val$location = string4;
                        this.val$pat = string5;
                        this.val$lCon = string6;
                        this.val$rCon = string7;
                        super(string, string2, string3, phonemeExpr);
                        this.myLine = this.val$cLine;
                        this.loc = this.val$location;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Rule");
                        stringBuilder.append("{line=").append(this.myLine);
                        stringBuilder.append(", loc='").append(this.loc).append('\'');
                        stringBuilder.append(", pat='").append(this.val$pat).append('\'');
                        stringBuilder.append(", lcon='").append(this.val$lCon).append('\'');
                        stringBuilder.append(", rcon='").append(this.val$rCon).append('\'');
                        stringBuilder.append('}');
                        return stringBuilder.toString();
                    }
                };
                String string6 = rule.pattern.substring(0, 1);
                ArrayList<2> arrayList = (ArrayList<2>)hashMap.get(string6);
                if (arrayList == null) {
                    arrayList = new ArrayList<2>();
                    hashMap.put(string6, arrayList);
                }
                arrayList.add(rule);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IllegalStateException("Problem parsing line '" + n + "' in " + string, illegalArgumentException);
            }
        }
        return hashMap;
    }

    private static RPattern pattern(String string) {
        boolean bl;
        boolean bl2 = string.startsWith("^");
        String string2 = string.substring(bl2 ? 1 : 0, (bl = string.endsWith("$")) ? string.length() - 1 : string.length());
        boolean bl3 = string2.contains("[");
        if (!bl3) {
            if (bl2 && bl) {
                if (string2.length() == 0) {
                    return new RPattern(){

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            return charSequence.length() == 0;
                        }
                    };
                }
                return new RPattern(string2){
                    final String val$content;
                    {
                        this.val$content = string;
                    }

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        return charSequence.equals(this.val$content);
                    }
                };
            }
            if ((bl2 || bl) && string2.length() == 0) {
                return ALL_STRINGS_RMATCHER;
            }
            if (bl2) {
                return new RPattern(string2){
                    final String val$content;
                    {
                        this.val$content = string;
                    }

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.access$100(charSequence, this.val$content);
                    }
                };
            }
            if (bl) {
                return new RPattern(string2){
                    final String val$content;
                    {
                        this.val$content = string;
                    }

                    @Override
                    public boolean isMatch(CharSequence charSequence) {
                        return Rule.access$200(charSequence, this.val$content);
                    }
                };
            }
        } else {
            String string3;
            boolean bl4 = string2.startsWith("[");
            boolean bl5 = string2.endsWith("]");
            if (bl4 && bl5 && !(string3 = string2.substring(1, string2.length() - 1)).contains("[")) {
                boolean bl6;
                boolean bl7 = string3.startsWith("^");
                if (bl7) {
                    string3 = string3.substring(1);
                }
                String string4 = string3;
                boolean bl8 = bl6 = !bl7;
                if (bl2 && bl) {
                    return new RPattern(string4, bl6){
                        final String val$bContent;
                        final boolean val$shouldMatch;
                        {
                            this.val$bContent = string;
                            this.val$shouldMatch = bl;
                        }

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            return charSequence.length() == 1 && Rule.access$300(this.val$bContent, charSequence.charAt(0)) == this.val$shouldMatch;
                        }
                    };
                }
                if (bl2) {
                    return new RPattern(string4, bl6){
                        final String val$bContent;
                        final boolean val$shouldMatch;
                        {
                            this.val$bContent = string;
                            this.val$shouldMatch = bl;
                        }

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            return charSequence.length() > 0 && Rule.access$300(this.val$bContent, charSequence.charAt(0)) == this.val$shouldMatch;
                        }
                    };
                }
                if (bl) {
                    return new RPattern(string4, bl6){
                        final String val$bContent;
                        final boolean val$shouldMatch;
                        {
                            this.val$bContent = string;
                            this.val$shouldMatch = bl;
                        }

                        @Override
                        public boolean isMatch(CharSequence charSequence) {
                            return charSequence.length() > 0 && Rule.access$300(this.val$bContent, charSequence.charAt(charSequence.length() - 1)) == this.val$shouldMatch;
                        }
                    };
                }
            }
        }
        return new RPattern(string){
            Pattern pattern;
            final String val$regex;
            {
                this.val$regex = string;
                this.pattern = Pattern.compile(this.val$regex);
            }

            @Override
            public boolean isMatch(CharSequence charSequence) {
                Matcher matcher = this.pattern.matcher(charSequence);
                return matcher.find();
            }
        };
    }

    private static boolean startsWith(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence2.length() > charSequence.length()) {
            return true;
        }
        for (int i = 0; i < charSequence2.length(); ++i) {
            if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
            return true;
        }
        return false;
    }

    private static String stripQuotes(String string) {
        if (string.startsWith(DOUBLE_QUOTE)) {
            string = string.substring(1);
        }
        if (string.endsWith(DOUBLE_QUOTE)) {
            string = string.substring(0, string.length() - 1);
        }
        return string;
    }

    public Rule(String string, String string2, String string3, PhonemeExpr phonemeExpr) {
        this.pattern = string;
        this.lContext = Rule.pattern(string2 + "$");
        this.rContext = Rule.pattern("^" + string3);
        this.phoneme = phonemeExpr;
    }

    public RPattern getLContext() {
        return this.lContext;
    }

    public String getPattern() {
        return this.pattern;
    }

    public PhonemeExpr getPhoneme() {
        return this.phoneme;
    }

    public RPattern getRContext() {
        return this.rContext;
    }

    public boolean patternAndContextMatches(CharSequence charSequence, int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Can not match pattern at negative indexes");
        }
        int n2 = this.pattern.length();
        int n3 = n + n2;
        if (n3 > charSequence.length()) {
            return true;
        }
        if (!charSequence.subSequence(n, n3).equals(this.pattern)) {
            return true;
        }
        if (!this.rContext.isMatch(charSequence.subSequence(n3, charSequence.length()))) {
            return true;
        }
        return this.lContext.isMatch(charSequence.subSequence(0, n));
    }

    static boolean access$100(CharSequence charSequence, CharSequence charSequence2) {
        return Rule.startsWith(charSequence, charSequence2);
    }

    static boolean access$200(CharSequence charSequence, CharSequence charSequence2) {
        return Rule.endsWith(charSequence, charSequence2);
    }

    static boolean access$300(CharSequence charSequence, char c) {
        return Rule.contains(charSequence, c);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static {
        for (NameType nameType : NameType.values()) {
            EnumMap enumMap = new EnumMap(RuleType.class);
            for (RuleType ruleType : RuleType.values()) {
                HashMap<String, Map<String, List<Rule>>> hashMap = new HashMap<String, Map<String, List<Rule>>>();
                Languages languages = Languages.getInstance(nameType);
                for (String string : languages.getLanguages()) {
                    Scanner scanner = Rule.createScanner(nameType, ruleType, string);
                    try {
                        hashMap.put(string, Rule.parseRules(scanner, Rule.createResourceName(nameType, ruleType, string)));
                    } catch (IllegalStateException illegalStateException) {
                        throw new IllegalStateException("Problem processing " + Rule.createResourceName(nameType, ruleType, string), illegalStateException);
                    } finally {
                        scanner.close();
                    }
                }
                if (!ruleType.equals((Object)RuleType.RULES)) {
                    Scanner scanner = Rule.createScanner(nameType, ruleType, "common");
                    try {
                        hashMap.put("common", Rule.parseRules(scanner, Rule.createResourceName(nameType, ruleType, "common")));
                    } finally {
                        scanner.close();
                    }
                }
                enumMap.put(ruleType, Collections.unmodifiableMap(hashMap));
            }
            RULES.put(nameType, Collections.unmodifiableMap(enumMap));
        }
    }

    public static interface RPattern {
        public boolean isMatch(CharSequence var1);
    }

    public static final class PhonemeList
    implements PhonemeExpr {
        private final List<Phoneme> phonemes;

        public PhonemeList(List<Phoneme> list) {
            this.phonemes = list;
        }

        public List<Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public Iterable getPhonemes() {
            return this.getPhonemes();
        }
    }

    public static interface PhonemeExpr {
        public Iterable<Phoneme> getPhonemes();
    }

    public static final class Phoneme
    implements PhonemeExpr {
        public static final Comparator<Phoneme> COMPARATOR = new Comparator<Phoneme>(){

            @Override
            public int compare(Phoneme phoneme, Phoneme phoneme2) {
                for (int i = 0; i < Phoneme.access$000(phoneme).length(); ++i) {
                    if (i >= Phoneme.access$000(phoneme2).length()) {
                        return 0;
                    }
                    int n = Phoneme.access$000(phoneme).charAt(i) - Phoneme.access$000(phoneme2).charAt(i);
                    if (n == 0) continue;
                    return n;
                }
                if (Phoneme.access$000(phoneme).length() < Phoneme.access$000(phoneme2).length()) {
                    return 1;
                }
                return 1;
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Phoneme)object, (Phoneme)object2);
            }
        };
        private final StringBuilder phonemeText;
        private final Languages.LanguageSet languages;

        public Phoneme(CharSequence charSequence, Languages.LanguageSet languageSet) {
            this.phonemeText = new StringBuilder(charSequence);
            this.languages = languageSet;
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2) {
            this(phoneme.phonemeText, phoneme.languages);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }

        public Phoneme(Phoneme phoneme, Phoneme phoneme2, Languages.LanguageSet languageSet) {
            this(phoneme.phonemeText, languageSet);
            this.phonemeText.append((CharSequence)phoneme2.phonemeText);
        }

        public Phoneme append(CharSequence charSequence) {
            this.phonemeText.append(charSequence);
            return this;
        }

        public Languages.LanguageSet getLanguages() {
            return this.languages;
        }

        @Override
        public Iterable<Phoneme> getPhonemes() {
            return Collections.singleton(this);
        }

        public CharSequence getPhonemeText() {
            return this.phonemeText;
        }

        @Deprecated
        public Phoneme join(Phoneme phoneme) {
            return new Phoneme(this.phonemeText.toString() + phoneme.phonemeText.toString(), this.languages.restrictTo(phoneme.languages));
        }

        public Phoneme mergeWithLanguage(Languages.LanguageSet languageSet) {
            return new Phoneme(this.phonemeText.toString(), this.languages.merge(languageSet));
        }

        public String toString() {
            return this.phonemeText.toString() + "[" + this.languages + "]";
        }

        static StringBuilder access$000(Phoneme phoneme) {
            return phoneme.phonemeText;
        }
    }
}

