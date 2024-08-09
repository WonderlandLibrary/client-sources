/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class DaitchMokotoffSoundex
implements StringEncoder {
    private static final String COMMENT = "//";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String MULTILINE_COMMENT_END = "*/";
    private static final String MULTILINE_COMMENT_START = "/*";
    private static final String RESOURCE_FILE = "org/apache/commons/codec/language/dmrules.txt";
    private static final int MAX_LENGTH = 6;
    private static final Map<Character, List<Rule>> RULES = new HashMap<Character, List<Rule>>();
    private static final Map<Character, Character> FOLDINGS = new HashMap<Character, Character>();
    private final boolean folding;

    private static void parseRules(Scanner scanner, String string, Map<Character, List<Rule>> map, Map<Character, Character> map2) {
        int n = 0;
        boolean bl = false;
        while (scanner.hasNextLine()) {
            String string2;
            String string3;
            String[] stringArray;
            String string4;
            ++n;
            String string5 = string4 = scanner.nextLine();
            if (bl) {
                if (!string5.endsWith(MULTILINE_COMMENT_END)) continue;
                bl = false;
                continue;
            }
            if (string5.startsWith(MULTILINE_COMMENT_START)) {
                bl = true;
                continue;
            }
            int n2 = string5.indexOf(COMMENT);
            if (n2 >= 0) {
                string5 = string5.substring(0, n2);
            }
            if ((string5 = string5.trim()).length() == 0) continue;
            if (string5.contains("=")) {
                stringArray = string5.split("=");
                if (stringArray.length != 2) {
                    throw new IllegalArgumentException("Malformed folding statement split into " + stringArray.length + " parts: " + string4 + " in " + string);
                }
                string3 = stringArray[0];
                string2 = stringArray[5];
                if (string3.length() != 1 || string2.length() != 1) {
                    throw new IllegalArgumentException("Malformed folding statement - patterns are not single characters: " + string4 + " in " + string);
                }
                map2.put(Character.valueOf(string3.charAt(0)), Character.valueOf(string2.charAt(0)));
                continue;
            }
            stringArray = string5.split("\\s+");
            if (stringArray.length != 4) {
                throw new IllegalArgumentException("Malformed rule statement split into " + stringArray.length + " parts: " + string4 + " in " + string);
            }
            try {
                string3 = DaitchMokotoffSoundex.stripQuotes(stringArray[0]);
                string2 = DaitchMokotoffSoundex.stripQuotes(stringArray[5]);
                String string6 = DaitchMokotoffSoundex.stripQuotes(stringArray[5]);
                String string7 = DaitchMokotoffSoundex.stripQuotes(stringArray[5]);
                Rule rule = new Rule(string3, string2, string6, string7);
                char c = Rule.access$000(rule).charAt(0);
                List<Rule> list = map.get(Character.valueOf(c));
                if (list == null) {
                    list = new ArrayList<Rule>();
                    map.put(Character.valueOf(c), list);
                }
                list.add(rule);
            } catch (IllegalArgumentException illegalArgumentException) {
                throw new IllegalStateException("Problem parsing line '" + n + "' in " + string, illegalArgumentException);
            }
        }
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

    public DaitchMokotoffSoundex() {
        this(true);
    }

    public DaitchMokotoffSoundex(boolean bl) {
        this.folding = bl;
    }

    private String cleanup(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isWhitespace(c)) continue;
            c = Character.toLowerCase(c);
            if (this.folding && FOLDINGS.containsKey(Character.valueOf(c))) {
                c = FOLDINGS.get(Character.valueOf(c)).charValue();
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    public Object encode(Object object) throws EncoderException {
        if (!(object instanceof String)) {
            throw new EncoderException("Parameter supplied to DaitchMokotoffSoundex encode is not of type java.lang.String");
        }
        return this.encode((String)object);
    }

    @Override
    public String encode(String string) {
        if (string == null) {
            return null;
        }
        return this.soundex(string, false)[0];
    }

    public String soundex(String string) {
        String[] stringArray = this.soundex(string, true);
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        for (String string2 : stringArray) {
            stringBuilder.append(string2);
            if (++n >= stringArray.length) continue;
            stringBuilder.append('|');
        }
        return stringBuilder.toString();
    }

    private String[] soundex(String string, boolean bl) {
        int n;
        if (string == null) {
            return null;
        }
        String string2 = this.cleanup(string);
        LinkedHashSet<Branch> linkedHashSet = new LinkedHashSet<Branch>();
        linkedHashSet.add(new Branch(null));
        int n2 = 0;
        for (int i = 0; i < string2.length(); ++i) {
            n = string2.charAt(i);
            if (Character.isWhitespace((char)n)) continue;
            String string3 = string2.substring(i);
            List<Rule> list = RULES.get(Character.valueOf((char)n));
            if (list == null) continue;
            List list2 = bl ? new ArrayList() : Collections.EMPTY_LIST;
            for (Rule rule : list) {
                if (!rule.matches(string3)) continue;
                if (bl) {
                    list2.clear();
                }
                String[] stringArray = rule.getReplacements(string3, n2 == 0);
                boolean bl2 = stringArray.length > 1 && bl;
                block2: for (Branch branch : linkedHashSet) {
                    for (String string4 : stringArray) {
                        Branch branch2 = bl2 ? branch.createBranch() : branch;
                        boolean bl3 = n2 == 109 && n == 110 || n2 == 110 && n == 109;
                        branch2.processNextReplacement(string4, bl3);
                        if (!bl) continue block2;
                        list2.add(branch2);
                    }
                }
                if (bl) {
                    linkedHashSet.clear();
                    linkedHashSet.addAll(list2);
                }
                i += rule.getPatternLength() - 1;
                break;
            }
            n2 = n;
        }
        String[] stringArray = new String[linkedHashSet.size()];
        n = 0;
        for (Branch branch : linkedHashSet) {
            branch.finish();
            stringArray[n++] = branch.toString();
        }
        return stringArray;
    }

    static {
        InputStream inputStream = DaitchMokotoffSoundex.class.getClassLoader().getResourceAsStream(RESOURCE_FILE);
        if (inputStream == null) {
            throw new IllegalArgumentException("Unable to load resource: org/apache/commons/codec/language/dmrules.txt");
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        try {
            DaitchMokotoffSoundex.parseRules(scanner, RESOURCE_FILE, RULES, FOLDINGS);
        } finally {
            scanner.close();
        }
        for (Map.Entry<Character, List<Rule>> entry : RULES.entrySet()) {
            List<Rule> list = entry.getValue();
            Collections.sort(list, new Comparator<Rule>(){

                @Override
                public int compare(Rule rule, Rule rule2) {
                    return rule2.getPatternLength() - rule.getPatternLength();
                }

                @Override
                public int compare(Object object, Object object2) {
                    return this.compare((Rule)object, (Rule)object2);
                }
            });
        }
    }

    private static final class Rule {
        private final String pattern;
        private final String[] replacementAtStart;
        private final String[] replacementBeforeVowel;
        private final String[] replacementDefault;

        protected Rule(String string, String string2, String string3, String string4) {
            this.pattern = string;
            this.replacementAtStart = string2.split("\\|");
            this.replacementBeforeVowel = string3.split("\\|");
            this.replacementDefault = string4.split("\\|");
        }

        public int getPatternLength() {
            return this.pattern.length();
        }

        public String[] getReplacements(String string, boolean bl) {
            boolean bl2;
            if (bl) {
                return this.replacementAtStart;
            }
            int n = this.getPatternLength();
            boolean bl3 = bl2 = n < string.length() ? this.isVowel(string.charAt(n)) : false;
            if (bl2) {
                return this.replacementBeforeVowel;
            }
            return this.replacementDefault;
        }

        private boolean isVowel(char c) {
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
        }

        public boolean matches(String string) {
            return string.startsWith(this.pattern);
        }

        public String toString() {
            return String.format("%s=(%s,%s,%s)", this.pattern, Arrays.asList(this.replacementAtStart), Arrays.asList(this.replacementBeforeVowel), Arrays.asList(this.replacementDefault));
        }

        static String access$000(Rule rule) {
            return rule.pattern;
        }
    }

    private static final class Branch {
        private final StringBuilder builder = new StringBuilder();
        private String cachedString = null;
        private String lastReplacement = null;

        private Branch() {
        }

        public Branch createBranch() {
            Branch branch = new Branch();
            branch.builder.append(this.toString());
            branch.lastReplacement = this.lastReplacement;
            return branch;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Branch)) {
                return true;
            }
            return this.toString().equals(((Branch)object).toString());
        }

        public void finish() {
            while (this.builder.length() < 6) {
                this.builder.append('0');
                this.cachedString = null;
            }
        }

        public int hashCode() {
            return this.toString().hashCode();
        }

        public void processNextReplacement(String string, boolean bl) {
            boolean bl2;
            boolean bl3 = bl2 = this.lastReplacement == null || !this.lastReplacement.endsWith(string) || bl;
            if (bl2 && this.builder.length() < 6) {
                this.builder.append(string);
                if (this.builder.length() > 6) {
                    this.builder.delete(6, this.builder.length());
                }
                this.cachedString = null;
            }
            this.lastReplacement = string;
        }

        public String toString() {
            if (this.cachedString == null) {
                this.cachedString = this.builder.toString();
            }
            return this.cachedString;
        }

        Branch(1 var1_1) {
            this();
        }
    }
}

