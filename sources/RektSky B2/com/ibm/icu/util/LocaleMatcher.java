package com.ibm.icu.util;

import com.ibm.icu.impl.locale.*;
import java.util.regex.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class LocaleMatcher
{
    @Deprecated
    public static final boolean DEBUG = false;
    private static final ULocale UNKNOWN_LOCALE;
    private static final double DEFAULT_THRESHOLD = 0.5;
    private final ULocale defaultLanguage;
    private final double threshold;
    Set<Row.R3<ULocale, ULocale, Double>> localeToMaxLocaleAndWeight;
    Map<String, Set<Row.R3<ULocale, ULocale, Double>>> desiredLanguageToPossibleLocalesToMaxLocaleToData;
    LanguageMatcherData matcherData;
    LocalePriorityList languagePriorityList;
    private static final LanguageMatcherData defaultWritten;
    private static HashMap<String, String> canonicalMap;
    transient XLocaleMatcher xLocaleMatcher;
    transient ULocale xDefaultLanguage;
    transient boolean xFavorScript;
    
    public LocaleMatcher(final LocalePriorityList languagePriorityList) {
        this(languagePriorityList, LocaleMatcher.defaultWritten);
    }
    
    public LocaleMatcher(final String languagePriorityListString) {
        this(LocalePriorityList.add(languagePriorityListString).build());
    }
    
    @Deprecated
    public LocaleMatcher(final LocalePriorityList languagePriorityList, final LanguageMatcherData matcherData) {
        this(languagePriorityList, matcherData, 0.5);
    }
    
    @Deprecated
    public LocaleMatcher(final LocalePriorityList languagePriorityList, final LanguageMatcherData matcherData, final double threshold) {
        this.localeToMaxLocaleAndWeight = new LinkedHashSet<Row.R3<ULocale, ULocale, Double>>();
        this.desiredLanguageToPossibleLocalesToMaxLocaleToData = new LinkedHashMap<String, Set<Row.R3<ULocale, ULocale, Double>>>();
        this.xLocaleMatcher = null;
        this.xDefaultLanguage = null;
        this.xFavorScript = false;
        this.matcherData = ((matcherData == null) ? LocaleMatcher.defaultWritten : matcherData.freeze());
        this.languagePriorityList = languagePriorityList;
        for (final ULocale language : languagePriorityList) {
            this.add(language, languagePriorityList.getWeight(language));
        }
        this.processMapping();
        final Iterator<ULocale> it = languagePriorityList.iterator();
        this.defaultLanguage = (it.hasNext() ? it.next() : null);
        this.threshold = threshold;
    }
    
    public double match(final ULocale desired, final ULocale desiredMax, final ULocale supported, final ULocale supportedMax) {
        return this.matcherData.match(desired, desiredMax, supported, supportedMax);
    }
    
    public ULocale canonicalize(final ULocale ulocale) {
        final String lang = ulocale.getLanguage();
        final String lang2 = LocaleMatcher.canonicalMap.get(lang);
        final String script = ulocale.getScript();
        final String script2 = LocaleMatcher.canonicalMap.get(script);
        final String region = ulocale.getCountry();
        final String region2 = LocaleMatcher.canonicalMap.get(region);
        if (lang2 != null || script2 != null || region2 != null) {
            return new ULocale((lang2 == null) ? lang : lang2, (script2 == null) ? script : script2, (region2 == null) ? region : region2);
        }
        return ulocale;
    }
    
    public ULocale getBestMatch(final LocalePriorityList languageList) {
        double bestWeight = 0.0;
        ULocale bestTableMatch = null;
        double penalty = 0.0;
        final OutputDouble matchWeight = new OutputDouble();
        for (final ULocale language : languageList) {
            final ULocale matchLocale = this.getBestMatchInternal(language, matchWeight);
            final double weight = matchWeight.value * languageList.getWeight(language) - penalty;
            if (weight > bestWeight) {
                bestWeight = weight;
                bestTableMatch = matchLocale;
            }
            penalty += 0.07000001;
        }
        if (bestWeight < this.threshold) {
            bestTableMatch = this.defaultLanguage;
        }
        return bestTableMatch;
    }
    
    public ULocale getBestMatch(final String languageList) {
        return this.getBestMatch(LocalePriorityList.add(languageList).build());
    }
    
    public ULocale getBestMatch(final ULocale ulocale) {
        return this.getBestMatchInternal(ulocale, null);
    }
    
    @Deprecated
    public ULocale getBestMatch(final ULocale... ulocales) {
        return this.getBestMatch(LocalePriorityList.add(ulocales).build());
    }
    
    @Override
    public String toString() {
        return "{" + this.defaultLanguage + ", " + this.localeToMaxLocaleAndWeight + "}";
    }
    
    private ULocale getBestMatchInternal(ULocale languageCode, final OutputDouble outputWeight) {
        languageCode = this.canonicalize(languageCode);
        final ULocale maximized = this.addLikelySubtags(languageCode);
        double bestWeight = 0.0;
        ULocale bestTableMatch = null;
        final String baseLanguage = maximized.getLanguage();
        final Set<Row.R3<ULocale, ULocale, Double>> searchTable = this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(baseLanguage);
        if (searchTable != null) {
            for (final Row.R3<ULocale, ULocale, Double> tableKeyValue : searchTable) {
                final ULocale tableKey = tableKeyValue.get0();
                final ULocale maxLocale = tableKeyValue.get1();
                final Double matchedWeight = tableKeyValue.get2();
                final double match = this.match(languageCode, maximized, tableKey, maxLocale);
                final double weight = match * matchedWeight;
                if (weight > bestWeight) {
                    bestWeight = weight;
                    bestTableMatch = tableKey;
                    if (weight > 0.999) {
                        break;
                    }
                    continue;
                }
            }
        }
        if (bestWeight < this.threshold) {
            bestTableMatch = this.defaultLanguage;
        }
        if (outputWeight != null) {
            outputWeight.value = bestWeight;
        }
        return bestTableMatch;
    }
    
    private void add(ULocale language, final Double weight) {
        language = this.canonicalize(language);
        final Row.R3<ULocale, ULocale, Double> row = Row.of(language, this.addLikelySubtags(language), weight);
        row.freeze();
        this.localeToMaxLocaleAndWeight.add(row);
    }
    
    private void processMapping() {
        for (final Map.Entry<String, Set<String>> desiredToMatchingLanguages : this.matcherData.matchingLanguages().keyValuesSet()) {
            final String desired = desiredToMatchingLanguages.getKey();
            final Set<String> supported = desiredToMatchingLanguages.getValue();
            for (final Row.R3<ULocale, ULocale, Double> localeToMaxAndWeight : this.localeToMaxLocaleAndWeight) {
                final ULocale key = localeToMaxAndWeight.get0();
                final String lang = key.getLanguage();
                if (supported.contains(lang)) {
                    this.addFiltered(desired, localeToMaxAndWeight);
                }
            }
        }
        for (final Row.R3<ULocale, ULocale, Double> localeToMaxAndWeight2 : this.localeToMaxLocaleAndWeight) {
            final ULocale key2 = localeToMaxAndWeight2.get0();
            final String lang2 = key2.getLanguage();
            this.addFiltered(lang2, localeToMaxAndWeight2);
        }
    }
    
    private void addFiltered(final String desired, final Row.R3<ULocale, ULocale, Double> localeToMaxAndWeight) {
        Set<Row.R3<ULocale, ULocale, Double>> map = this.desiredLanguageToPossibleLocalesToMaxLocaleToData.get(desired);
        if (map == null) {
            this.desiredLanguageToPossibleLocalesToMaxLocaleToData.put(desired, map = new LinkedHashSet<Row.R3<ULocale, ULocale, Double>>());
        }
        map.add(localeToMaxAndWeight);
    }
    
    private ULocale addLikelySubtags(final ULocale languageCode) {
        if (languageCode.equals(LocaleMatcher.UNKNOWN_LOCALE)) {
            return LocaleMatcher.UNKNOWN_LOCALE;
        }
        final ULocale result = ULocale.addLikelySubtags(languageCode);
        if (result == null || result.equals(languageCode)) {
            final String language = languageCode.getLanguage();
            final String script = languageCode.getScript();
            final String region = languageCode.getCountry();
            return new ULocale(((language.length() == 0) ? "und" : language) + "_" + ((script.length() == 0) ? "Zzzz" : script) + "_" + ((region.length() == 0) ? "ZZ" : region));
        }
        return result;
    }
    
    @Deprecated
    public static ICUResourceBundle getICUSupplementalData() {
        final ICUResourceBundle suppData = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        return suppData;
    }
    
    @Deprecated
    public static double match(final ULocale a, final ULocale b) {
        final LocaleMatcher matcher = new LocaleMatcher("");
        return matcher.match(a, matcher.addLikelySubtags(a), b, matcher.addLikelySubtags(b));
    }
    
    @Deprecated
    public int distance(final ULocale desired, final ULocale supported) {
        return this.getLocaleMatcher().distance(desired, supported);
    }
    
    private synchronized XLocaleMatcher getLocaleMatcher() {
        if (this.xLocaleMatcher == null) {
            final XLocaleMatcher.Builder builder = XLocaleMatcher.builder();
            builder.setSupportedLocales(this.languagePriorityList);
            if (this.xDefaultLanguage != null) {
                builder.setDefaultLanguage(this.xDefaultLanguage);
            }
            if (this.xFavorScript) {
                builder.setDistanceOption(XLocaleDistance.DistanceOption.SCRIPT_FIRST);
            }
            this.xLocaleMatcher = builder.build();
        }
        return this.xLocaleMatcher;
    }
    
    @Deprecated
    public ULocale getBestMatch(final LinkedHashSet<ULocale> desiredLanguages, final Output<ULocale> outputBestDesired) {
        return this.getLocaleMatcher().getBestMatch(desiredLanguages, outputBestDesired);
    }
    
    @Deprecated
    public synchronized LocaleMatcher setDefaultLanguage(final ULocale defaultLanguage) {
        this.xDefaultLanguage = defaultLanguage;
        this.xLocaleMatcher = null;
        return this;
    }
    
    @Deprecated
    public synchronized LocaleMatcher setFavorScript(final boolean favorScript) {
        this.xFavorScript = favorScript;
        this.xLocaleMatcher = null;
        return this;
    }
    
    static {
        UNKNOWN_LOCALE = new ULocale("und");
        (LocaleMatcher.canonicalMap = new HashMap<String, String>()).put("iw", "he");
        LocaleMatcher.canonicalMap.put("mo", "ro");
        LocaleMatcher.canonicalMap.put("tl", "fil");
        final ICUResourceBundle suppData = getICUSupplementalData();
        final ICUResourceBundle languageMatching = suppData.findTopLevel("languageMatching");
        final ICUResourceBundle written = (ICUResourceBundle)languageMatching.get("written");
        defaultWritten = new LanguageMatcherData();
        final UResourceBundleIterator iter = written.getIterator();
        while (iter.hasNext()) {
            final ICUResourceBundle item = (ICUResourceBundle)iter.next();
            final boolean oneway = item.getSize() > 3 && "1".equals(item.getString(3));
            LocaleMatcher.defaultWritten.addDistance(item.getString(0), item.getString(1), Integer.parseInt(item.getString(2)), oneway);
        }
        LocaleMatcher.defaultWritten.freeze();
    }
    
    @Deprecated
    private static class OutputDouble
    {
        double value;
    }
    
    private static class LocalePatternMatcher
    {
        private String lang;
        private String script;
        private String region;
        private Level level;
        static Pattern pattern;
        
        public LocalePatternMatcher(final String toMatch) {
            final Matcher matcher = LocalePatternMatcher.pattern.matcher(toMatch);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Bad pattern: " + toMatch);
            }
            this.lang = matcher.group(1);
            this.script = matcher.group(2);
            this.region = matcher.group(3);
            this.level = ((this.region != null) ? Level.region : ((this.script != null) ? Level.script : Level.language));
            if (this.lang.equals("*")) {
                this.lang = null;
            }
            if (this.script != null && this.script.equals("*")) {
                this.script = null;
            }
            if (this.region != null && this.region.equals("*")) {
                this.region = null;
            }
        }
        
        boolean matches(final ULocale ulocale) {
            return (this.lang == null || this.lang.equals(ulocale.getLanguage())) && (this.script == null || this.script.equals(ulocale.getScript())) && (this.region == null || this.region.equals(ulocale.getCountry()));
        }
        
        public Level getLevel() {
            return this.level;
        }
        
        public String getLanguage() {
            return (this.lang == null) ? "*" : this.lang;
        }
        
        public String getScript() {
            return (this.script == null) ? "*" : this.script;
        }
        
        public String getRegion() {
            return (this.region == null) ? "*" : this.region;
        }
        
        @Override
        public String toString() {
            String result = this.getLanguage();
            if (this.level != Level.language) {
                result = result + "-" + this.getScript();
                if (this.level != Level.script) {
                    result = result + "-" + this.getRegion();
                }
            }
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof LocalePatternMatcher)) {
                return false;
            }
            final LocalePatternMatcher other = (LocalePatternMatcher)obj;
            return Utility.objectEquals(this.level, other.level) && Utility.objectEquals(this.lang, other.lang) && Utility.objectEquals(this.script, other.script) && Utility.objectEquals(this.region, other.region);
        }
        
        @Override
        public int hashCode() {
            return this.level.ordinal() ^ ((this.lang == null) ? 0 : this.lang.hashCode()) ^ ((this.script == null) ? 0 : this.script.hashCode()) ^ ((this.region == null) ? 0 : this.region.hashCode());
        }
        
        static {
            LocalePatternMatcher.pattern = Pattern.compile("([a-z]{1,8}|\\*)(?:[_-]([A-Z][a-z]{3}|\\*))?(?:[_-]([A-Z]{2}|[0-9]{3}|\\*))?");
        }
    }
    
    enum Level
    {
        language(0.99), 
        script(0.2), 
        region(0.04);
        
        final double worst;
        
        private Level(final double d) {
            this.worst = d;
        }
    }
    
    private static class ScoreData implements Freezable<ScoreData>
    {
        private static final double maxUnequal_changeD_sameS = 0.5;
        private static final double maxUnequal_changeEqual = 0.75;
        LinkedHashSet<Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>> scores;
        final Level level;
        private volatile boolean frozen;
        
        public ScoreData(final Level level) {
            this.scores = new LinkedHashSet<Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>>();
            this.frozen = false;
            this.level = level;
        }
        
        void addDataToScores(final String desired, final String supported, final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> data) {
            final boolean added = this.scores.add(data);
            if (!added) {
                throw new ICUException("trying to add duplicate data: " + data);
            }
        }
        
        double getScore(final ULocale dMax, final String desiredRaw, final String desiredMax, final ULocale sMax, final String supportedRaw, final String supportedMax) {
            double distance = 0.0;
            if (!desiredMax.equals(supportedMax)) {
                distance = this.getRawScore(dMax, sMax);
            }
            else if (!desiredRaw.equals(supportedRaw)) {
                distance += 0.001;
            }
            return distance;
        }
        
        private double getRawScore(final ULocale desiredLocale, final ULocale supportedLocale) {
            for (final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> datum : this.scores) {
                if (datum.get0().matches(desiredLocale) && datum.get1().matches(supportedLocale)) {
                    return datum.get2();
                }
            }
            return this.level.worst;
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder().append(this.level);
            for (final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> score : this.scores) {
                result.append("\n\t\t").append(score);
            }
            return result.toString();
        }
        
        @Override
        public ScoreData cloneAsThawed() {
            try {
                final ScoreData result = (ScoreData)this.clone();
                result.scores = (LinkedHashSet<Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double>>)result.scores.clone();
                result.frozen = false;
                return result;
            }
            catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException(e);
            }
        }
        
        @Override
        public ScoreData freeze() {
            return this;
        }
        
        @Override
        public boolean isFrozen() {
            return this.frozen;
        }
        
        public Relation<String, String> getMatchingLanguages() {
            final Relation<String, String> desiredToSupported = Relation.of(new LinkedHashMap<String, Set<String>>(), HashSet.class);
            for (final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> item : this.scores) {
                final LocalePatternMatcher desired = item.get0();
                final LocalePatternMatcher supported = item.get1();
                if (desired.lang != null && supported.lang != null) {
                    desiredToSupported.put(desired.lang, supported.lang);
                }
            }
            desiredToSupported.freeze();
            return desiredToSupported;
        }
    }
    
    @Deprecated
    public static class LanguageMatcherData implements Freezable<LanguageMatcherData>
    {
        private ScoreData languageScores;
        private ScoreData scriptScores;
        private ScoreData regionScores;
        private Relation<String, String> matchingLanguages;
        private volatile boolean frozen;
        
        @Deprecated
        public LanguageMatcherData() {
            this.languageScores = new ScoreData(Level.language);
            this.scriptScores = new ScoreData(Level.script);
            this.regionScores = new ScoreData(Level.region);
            this.frozen = false;
        }
        
        @Deprecated
        public Relation<String, String> matchingLanguages() {
            return this.matchingLanguages;
        }
        
        @Deprecated
        @Override
        public String toString() {
            return this.languageScores + "\n\t" + this.scriptScores + "\n\t" + this.regionScores;
        }
        
        @Deprecated
        public double match(final ULocale a, final ULocale aMax, final ULocale b, final ULocale bMax) {
            double diff = 0.0;
            diff += this.languageScores.getScore(aMax, a.getLanguage(), aMax.getLanguage(), bMax, b.getLanguage(), bMax.getLanguage());
            if (diff > 0.999) {
                return 0.0;
            }
            diff += this.scriptScores.getScore(aMax, a.getScript(), aMax.getScript(), bMax, b.getScript(), bMax.getScript());
            diff += this.regionScores.getScore(aMax, a.getCountry(), aMax.getCountry(), bMax, b.getCountry(), bMax.getCountry());
            if (!a.getVariant().equals(b.getVariant())) {
                diff += 0.01;
            }
            if (diff < 0.0) {
                diff = 0.0;
            }
            else if (diff > 1.0) {
                diff = 1.0;
            }
            return 1.0 - diff;
        }
        
        @Deprecated
        public LanguageMatcherData addDistance(final String desired, final String supported, final int percent, final String comment) {
            return this.addDistance(desired, supported, percent, false, comment);
        }
        
        @Deprecated
        public LanguageMatcherData addDistance(final String desired, final String supported, final int percent, final boolean oneway) {
            return this.addDistance(desired, supported, percent, oneway, null);
        }
        
        private LanguageMatcherData addDistance(final String desired, final String supported, final int percent, final boolean oneway, final String comment) {
            final double score = 1.0 - percent / 100.0;
            final LocalePatternMatcher desiredMatcher = new LocalePatternMatcher(desired);
            final Level desiredLen = desiredMatcher.getLevel();
            final LocalePatternMatcher supportedMatcher = new LocalePatternMatcher(supported);
            final Level supportedLen = supportedMatcher.getLevel();
            if (desiredLen != supportedLen) {
                throw new IllegalArgumentException("Lengths unequal: " + desired + ", " + supported);
            }
            final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> data = Row.of(desiredMatcher, supportedMatcher, score);
            final Row.R3<LocalePatternMatcher, LocalePatternMatcher, Double> data2 = oneway ? null : Row.of(supportedMatcher, desiredMatcher, score);
            final boolean desiredEqualsSupported = desiredMatcher.equals(supportedMatcher);
            switch (desiredLen) {
                case language: {
                    final String dlanguage = desiredMatcher.getLanguage();
                    final String slanguage = supportedMatcher.getLanguage();
                    this.languageScores.addDataToScores(dlanguage, slanguage, data);
                    if (!oneway && !desiredEqualsSupported) {
                        this.languageScores.addDataToScores(slanguage, dlanguage, data2);
                        break;
                    }
                    break;
                }
                case script: {
                    final String dscript = desiredMatcher.getScript();
                    final String sscript = supportedMatcher.getScript();
                    this.scriptScores.addDataToScores(dscript, sscript, data);
                    if (!oneway && !desiredEqualsSupported) {
                        this.scriptScores.addDataToScores(sscript, dscript, data2);
                        break;
                    }
                    break;
                }
                case region: {
                    final String dregion = desiredMatcher.getRegion();
                    final String sregion = supportedMatcher.getRegion();
                    this.regionScores.addDataToScores(dregion, sregion, data);
                    if (!oneway && !desiredEqualsSupported) {
                        this.regionScores.addDataToScores(sregion, dregion, data2);
                        break;
                    }
                    break;
                }
            }
            return this;
        }
        
        @Deprecated
        @Override
        public LanguageMatcherData cloneAsThawed() {
            try {
                final LanguageMatcherData result = (LanguageMatcherData)this.clone();
                result.languageScores = this.languageScores.cloneAsThawed();
                result.scriptScores = this.scriptScores.cloneAsThawed();
                result.regionScores = this.regionScores.cloneAsThawed();
                result.frozen = false;
                return result;
            }
            catch (CloneNotSupportedException e) {
                throw new ICUCloneNotSupportedException(e);
            }
        }
        
        @Deprecated
        @Override
        public LanguageMatcherData freeze() {
            this.languageScores.freeze();
            this.regionScores.freeze();
            this.scriptScores.freeze();
            this.matchingLanguages = this.languageScores.getMatchingLanguages();
            this.frozen = true;
            return this;
        }
        
        @Deprecated
        @Override
        public boolean isFrozen() {
            return this.frozen;
        }
    }
}
