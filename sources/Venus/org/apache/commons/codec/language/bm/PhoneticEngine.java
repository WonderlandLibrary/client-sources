/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.language.bm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.codec.language.bm.Rule;
import org.apache.commons.codec.language.bm.RuleType;

public class PhoneticEngine {
    private static final Map<NameType, Set<String>> NAME_PREFIXES = new EnumMap<NameType, Set<String>>(NameType.class);
    private static final int DEFAULT_MAX_PHONEMES = 20;
    private final Lang lang;
    private final NameType nameType;
    private final RuleType ruleType;
    private final boolean concat;
    private final int maxPhonemes;

    private static String join(Iterable<String> iterable, String string) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator2 = iterable.iterator();
        if (iterator2.hasNext()) {
            stringBuilder.append(iterator2.next());
        }
        while (iterator2.hasNext()) {
            stringBuilder.append(string).append(iterator2.next());
        }
        return stringBuilder.toString();
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean bl) {
        this(nameType, ruleType, bl, 20);
    }

    public PhoneticEngine(NameType nameType, RuleType ruleType, boolean bl, int n) {
        if (ruleType == RuleType.RULES) {
            throw new IllegalArgumentException("ruleType must not be " + (Object)((Object)RuleType.RULES));
        }
        this.nameType = nameType;
        this.ruleType = ruleType;
        this.concat = bl;
        this.lang = Lang.instance(nameType);
        this.maxPhonemes = n;
    }

    private PhonemeBuilder applyFinalRules(PhonemeBuilder phonemeBuilder, Map<String, List<Rule>> map) {
        if (map == null) {
            throw new NullPointerException("finalRules can not be null");
        }
        if (map.isEmpty()) {
            return phonemeBuilder;
        }
        TreeMap<Rule.Phoneme, Rule.Phoneme> treeMap = new TreeMap<Rule.Phoneme, Rule.Phoneme>(Rule.Phoneme.COMPARATOR);
        for (Rule.Phoneme phoneme : phonemeBuilder.getPhonemes()) {
            PhonemeBuilder phonemeBuilder2 = PhonemeBuilder.empty(phoneme.getLanguages());
            String string = phoneme.getPhonemeText().toString();
            int n = 0;
            while (n < string.length()) {
                RulesApplication rulesApplication = new RulesApplication(map, string, phonemeBuilder2, n, this.maxPhonemes).invoke();
                boolean bl = rulesApplication.isFound();
                phonemeBuilder2 = rulesApplication.getPhonemeBuilder();
                if (!bl) {
                    phonemeBuilder2.append(string.subSequence(n, n + 1));
                }
                n = rulesApplication.getI();
            }
            for (Rule.Phoneme phoneme2 : phonemeBuilder2.getPhonemes()) {
                if (treeMap.containsKey(phoneme2)) {
                    Rule.Phoneme phoneme3 = (Rule.Phoneme)treeMap.remove(phoneme2);
                    Rule.Phoneme phoneme4 = phoneme3.mergeWithLanguage(phoneme2.getLanguages());
                    treeMap.put(phoneme4, phoneme4);
                    continue;
                }
                treeMap.put(phoneme2, phoneme2);
            }
        }
        return new PhonemeBuilder(treeMap.keySet(), null);
    }

    public String encode(String string) {
        Languages.LanguageSet languageSet = this.lang.guessLanguages(string);
        return this.encode(string, languageSet);
    }

    public String encode(String string, Languages.LanguageSet languageSet) {
        Object object3;
        Object object2;
        Map<String, List<Rule>> map = Rule.getInstanceMap(this.nameType, RuleType.RULES, languageSet);
        Map<String, List<Rule>> map2 = Rule.getInstanceMap(this.nameType, this.ruleType, "common");
        Map<String, List<Rule>> map3 = Rule.getInstanceMap(this.nameType, this.ruleType, languageSet);
        string = string.toLowerCase(Locale.ENGLISH).replace('-', ' ').trim();
        if (this.nameType == NameType.GENERIC) {
            if (string.length() >= 2 && string.substring(0, 2).equals("d'")) {
                String string2 = string.substring(2);
                String string3 = "d" + string2;
                return "(" + this.encode(string2) + ")-(" + this.encode(string3) + ")";
            }
            for (String object42 : NAME_PREFIXES.get((Object)this.nameType)) {
                if (!string.startsWith(object42 + " ")) continue;
                String object3 = string.substring(object42.length() + 1);
                String object22 = object42 + object3;
                return "(" + this.encode(object3) + ")-(" + this.encode(object22) + ")";
            }
        }
        List<String> list = Arrays.asList(string.split("\\s+"));
        ArrayList<String> arrayList = new ArrayList<String>();
        switch (1.$SwitchMap$org$apache$commons$codec$language$bm$NameType[this.nameType.ordinal()]) {
            case 1: {
                object2 = list.iterator();
                while (object2.hasNext()) {
                    String n = (String)object2.next();
                    object3 = n.split("'");
                    String string2 = object3[((String[])object3).length - 1];
                    arrayList.add(string2);
                }
                arrayList.removeAll((Collection)NAME_PREFIXES.get((Object)this.nameType));
                break;
            }
            case 2: {
                arrayList.addAll(list);
                arrayList.removeAll((Collection)NAME_PREFIXES.get((Object)this.nameType));
                break;
            }
            case 3: {
                arrayList.addAll(list);
                break;
            }
            default: {
                throw new IllegalStateException("Unreachable case: " + (Object)((Object)this.nameType));
            }
        }
        if (this.concat) {
            string = PhoneticEngine.join(arrayList, " ");
        } else if (arrayList.size() == 1) {
            string = (String)list.iterator().next();
        } else {
            object2 = new StringBuilder();
            for (Object object3 : arrayList) {
                ((StringBuilder)object2).append("-").append(this.encode((String)object3));
            }
            return ((StringBuilder)object2).substring(1);
        }
        object2 = PhonemeBuilder.empty(languageSet);
        int n = 0;
        while (n < string.length()) {
            object3 = new RulesApplication(map, string, (PhonemeBuilder)object2, n, this.maxPhonemes).invoke();
            n = ((RulesApplication)object3).getI();
            object2 = ((RulesApplication)object3).getPhonemeBuilder();
        }
        object2 = this.applyFinalRules((PhonemeBuilder)object2, map2);
        object2 = this.applyFinalRules((PhonemeBuilder)object2, map3);
        return ((PhonemeBuilder)object2).makeString();
    }

    public Lang getLang() {
        return this.lang;
    }

    public NameType getNameType() {
        return this.nameType;
    }

    public RuleType getRuleType() {
        return this.ruleType;
    }

    public boolean isConcat() {
        return this.concat;
    }

    public int getMaxPhonemes() {
        return this.maxPhonemes;
    }

    static {
        NAME_PREFIXES.put(NameType.ASHKENAZI, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("bar", "ben", "da", "de", "van", "von"))));
        NAME_PREFIXES.put(NameType.SEPHARDIC, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("al", "el", "da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
        NAME_PREFIXES.put(NameType.GENERIC, Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("da", "dal", "de", "del", "dela", "de la", "della", "des", "di", "do", "dos", "du", "van", "von"))));
    }

    private static final class RulesApplication {
        private final Map<String, List<Rule>> finalRules;
        private final CharSequence input;
        private final PhonemeBuilder phonemeBuilder;
        private int i;
        private final int maxPhonemes;
        private boolean found;

        public RulesApplication(Map<String, List<Rule>> map, CharSequence charSequence, PhonemeBuilder phonemeBuilder, int n, int n2) {
            if (map == null) {
                throw new NullPointerException("The finalRules argument must not be null");
            }
            this.finalRules = map;
            this.phonemeBuilder = phonemeBuilder;
            this.input = charSequence;
            this.i = n;
            this.maxPhonemes = n2;
        }

        public int getI() {
            return this.i;
        }

        public PhonemeBuilder getPhonemeBuilder() {
            return this.phonemeBuilder;
        }

        public RulesApplication invoke() {
            this.found = false;
            int n = 1;
            List<Rule> list = this.finalRules.get(this.input.subSequence(this.i, this.i + n));
            if (list != null) {
                for (Rule rule : list) {
                    String string = rule.getPattern();
                    n = string.length();
                    if (!rule.patternAndContextMatches(this.input, this.i)) continue;
                    this.phonemeBuilder.apply(rule.getPhoneme(), this.maxPhonemes);
                    this.found = true;
                    break;
                }
            }
            if (!this.found) {
                n = 1;
            }
            this.i += n;
            return this;
        }

        public boolean isFound() {
            return this.found;
        }
    }

    static final class PhonemeBuilder {
        private final Set<Rule.Phoneme> phonemes;

        public static PhonemeBuilder empty(Languages.LanguageSet languageSet) {
            return new PhonemeBuilder(new Rule.Phoneme("", languageSet));
        }

        private PhonemeBuilder(Rule.Phoneme phoneme) {
            this.phonemes = new LinkedHashSet<Rule.Phoneme>();
            this.phonemes.add(phoneme);
        }

        private PhonemeBuilder(Set<Rule.Phoneme> set) {
            this.phonemes = set;
        }

        public void append(CharSequence charSequence) {
            for (Rule.Phoneme phoneme : this.phonemes) {
                phoneme.append(charSequence);
            }
        }

        public void apply(Rule.PhonemeExpr phonemeExpr, int n) {
            LinkedHashSet<Rule.Phoneme> linkedHashSet = new LinkedHashSet<Rule.Phoneme>(n);
            block0: for (Rule.Phoneme phoneme : this.phonemes) {
                for (Rule.Phoneme phoneme2 : phonemeExpr.getPhonemes()) {
                    Languages.LanguageSet languageSet = phoneme.getLanguages().restrictTo(phoneme2.getLanguages());
                    if (languageSet.isEmpty()) continue;
                    Rule.Phoneme phoneme3 = new Rule.Phoneme(phoneme, phoneme2, languageSet);
                    if (linkedHashSet.size() >= n) continue;
                    linkedHashSet.add(phoneme3);
                    if (linkedHashSet.size() < n) continue;
                    break block0;
                }
            }
            this.phonemes.clear();
            this.phonemes.addAll(linkedHashSet);
        }

        public Set<Rule.Phoneme> getPhonemes() {
            return this.phonemes;
        }

        public String makeString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (Rule.Phoneme phoneme : this.phonemes) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append("|");
                }
                stringBuilder.append(phoneme.getPhonemeText());
            }
            return stringBuilder.toString();
        }

        PhonemeBuilder(Set set, 1 var2_2) {
            this(set);
        }
    }
}

