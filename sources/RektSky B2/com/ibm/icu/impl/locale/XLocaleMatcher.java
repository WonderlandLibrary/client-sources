package com.ibm.icu.impl.locale;

import com.ibm.icu.util.*;
import java.util.*;

public class XLocaleMatcher
{
    private static final XLikelySubtags.LSR UND;
    private static final ULocale UND_LOCALE;
    private final XLocaleDistance localeDistance;
    private final int thresholdDistance;
    private final int demotionPerAdditionalDesiredLocale;
    private final XLocaleDistance.DistanceOption distanceOption;
    private final Map<XLikelySubtags.LSR, Set<ULocale>> supportedLanguages;
    private final Set<ULocale> exactSupportedLocales;
    private final ULocale defaultLanguage;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public XLocaleMatcher(final String supportedLocales) {
        this(builder().setSupportedLocales(supportedLocales));
    }
    
    public XLocaleMatcher(final LocalePriorityList supportedLocales) {
        this(builder().setSupportedLocales(supportedLocales));
    }
    
    public XLocaleMatcher(final Set<ULocale> supportedLocales) {
        this(builder().setSupportedLocales(supportedLocales));
    }
    
    private XLocaleMatcher(final Builder builder) {
        this.localeDistance = ((builder.localeDistance == null) ? XLocaleDistance.getDefault() : builder.localeDistance);
        this.thresholdDistance = ((builder.thresholdDistance < 0) ? this.localeDistance.getDefaultScriptDistance() : builder.thresholdDistance);
        final Set<XLikelySubtags.LSR> paradigms = this.extractLsrSet(this.localeDistance.getParadigms());
        final XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> temp2 = this.extractLsrMap(builder.supportedLanguagesList, paradigms);
        this.supportedLanguages = temp2.asMap();
        this.exactSupportedLocales = XCldrStub.ImmutableSet.copyOf(temp2.values());
        this.defaultLanguage = ((builder.defaultLanguage != null) ? builder.defaultLanguage : (this.supportedLanguages.isEmpty() ? null : this.supportedLanguages.entrySet().iterator().next().getValue().iterator().next()));
        this.demotionPerAdditionalDesiredLocale = ((builder.demotionPerAdditionalDesiredLocale < 0) ? (this.localeDistance.getDefaultRegionDistance() + 1) : builder.demotionPerAdditionalDesiredLocale);
        this.distanceOption = builder.distanceOption;
    }
    
    private Set<XLikelySubtags.LSR> extractLsrSet(final Set<ULocale> languagePriorityList) {
        final Set<XLikelySubtags.LSR> result = new LinkedHashSet<XLikelySubtags.LSR>();
        for (final ULocale item : languagePriorityList) {
            final XLikelySubtags.LSR max = item.equals(XLocaleMatcher.UND_LOCALE) ? XLocaleMatcher.UND : XLikelySubtags.LSR.fromMaximalized(item);
            result.add(max);
        }
        return result;
    }
    
    private XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> extractLsrMap(final Set<ULocale> languagePriorityList, final Set<XLikelySubtags.LSR> priorities) {
        XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> builder = (XCldrStub.Multimap<XLikelySubtags.LSR, ULocale>)XCldrStub.LinkedHashMultimap.create();
        for (final ULocale item : languagePriorityList) {
            final XLikelySubtags.LSR max = item.equals(XLocaleMatcher.UND_LOCALE) ? XLocaleMatcher.UND : XLikelySubtags.LSR.fromMaximalized(item);
            builder.put(max, item);
        }
        if (builder.size() > 1 && priorities != null) {
            final XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> builder2 = (XCldrStub.Multimap<XLikelySubtags.LSR, ULocale>)XCldrStub.LinkedHashMultimap.create();
            boolean first = true;
            for (final Map.Entry<XLikelySubtags.LSR, Set<ULocale>> entry : builder.asMap().entrySet()) {
                final XLikelySubtags.LSR key = entry.getKey();
                if (first || priorities.contains(key)) {
                    builder2.putAll(key, entry.getValue());
                    first = false;
                }
            }
            builder2.putAll(builder);
            if (!builder2.equals(builder)) {
                throw new IllegalArgumentException();
            }
            builder = builder2;
        }
        return XCldrStub.ImmutableMultimap.copyOf(builder);
    }
    
    public ULocale getBestMatch(final ULocale ulocale) {
        return this.getBestMatch(ulocale, null);
    }
    
    public ULocale getBestMatch(final String languageList) {
        return this.getBestMatch(LocalePriorityList.add(languageList).build(), null);
    }
    
    public ULocale getBestMatch(final ULocale... locales) {
        return this.getBestMatch(new LinkedHashSet<ULocale>(Arrays.asList(locales)), null);
    }
    
    public ULocale getBestMatch(final Set<ULocale> desiredLanguages) {
        return this.getBestMatch(desiredLanguages, null);
    }
    
    public ULocale getBestMatch(final LocalePriorityList desiredLanguages) {
        return this.getBestMatch(desiredLanguages, null);
    }
    
    public ULocale getBestMatch(final LocalePriorityList desiredLanguages, final Output<ULocale> outputBestDesired) {
        return this.getBestMatch(asSet(desiredLanguages), outputBestDesired);
    }
    
    private static Set<ULocale> asSet(final LocalePriorityList languageList) {
        final Set<ULocale> temp = new LinkedHashSet<ULocale>();
        for (final ULocale locale : languageList) {
            temp.add(locale);
        }
        return temp;
    }
    
    public ULocale getBestMatch(final Set<ULocale> desiredLanguages, final Output<ULocale> outputBestDesired) {
        if (desiredLanguages.size() == 1) {
            return this.getBestMatch(desiredLanguages.iterator().next(), outputBestDesired);
        }
        final XCldrStub.Multimap<XLikelySubtags.LSR, ULocale> desiredLSRs = this.extractLsrMap(desiredLanguages, null);
        int bestDistance = Integer.MAX_VALUE;
        ULocale bestDesiredLocale = null;
        Collection<ULocale> bestSupportedLocales = null;
        int delta = 0;
    Label_0308:
        for (final Map.Entry<XLikelySubtags.LSR, ULocale> desiredLsrAndLocale : desiredLSRs.entries()) {
            final ULocale desiredLocale = desiredLsrAndLocale.getValue();
            final XLikelySubtags.LSR desiredLSR = desiredLsrAndLocale.getKey();
            if (delta < bestDistance) {
                if (this.exactSupportedLocales.contains(desiredLocale)) {
                    if (outputBestDesired != null) {
                        outputBestDesired.value = desiredLocale;
                    }
                    return desiredLocale;
                }
                final Collection<ULocale> found = this.supportedLanguages.get(desiredLSR);
                if (found != null) {
                    if (outputBestDesired != null) {
                        outputBestDesired.value = desiredLocale;
                    }
                    return found.iterator().next();
                }
            }
            for (final Map.Entry<XLikelySubtags.LSR, Set<ULocale>> supportedLsrAndLocale : this.supportedLanguages.entrySet()) {
                final int distance = delta + this.localeDistance.distanceRaw(desiredLSR, supportedLsrAndLocale.getKey(), this.thresholdDistance, this.distanceOption);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    bestDesiredLocale = desiredLocale;
                    bestSupportedLocales = supportedLsrAndLocale.getValue();
                    if (distance == 0) {
                        break Label_0308;
                    }
                    continue;
                }
            }
            delta += this.demotionPerAdditionalDesiredLocale;
        }
        if (bestDistance >= this.thresholdDistance) {
            if (outputBestDesired != null) {
                outputBestDesired.value = null;
            }
            return this.defaultLanguage;
        }
        if (outputBestDesired != null) {
            outputBestDesired.value = bestDesiredLocale;
        }
        if (bestSupportedLocales.contains(bestDesiredLocale)) {
            return bestDesiredLocale;
        }
        return bestSupportedLocales.iterator().next();
    }
    
    public ULocale getBestMatch(final ULocale desiredLocale, final Output<ULocale> outputBestDesired) {
        int bestDistance = Integer.MAX_VALUE;
        ULocale bestDesiredLocale = null;
        Collection<ULocale> bestSupportedLocales = null;
        final XLikelySubtags.LSR desiredLSR = desiredLocale.equals(XLocaleMatcher.UND_LOCALE) ? XLocaleMatcher.UND : XLikelySubtags.LSR.fromMaximalized(desiredLocale);
        if (this.exactSupportedLocales.contains(desiredLocale)) {
            if (outputBestDesired != null) {
                outputBestDesired.value = desiredLocale;
            }
            return desiredLocale;
        }
        if (this.distanceOption == XLocaleDistance.DistanceOption.NORMAL) {
            final Collection<ULocale> found = this.supportedLanguages.get(desiredLSR);
            if (found != null) {
                if (outputBestDesired != null) {
                    outputBestDesired.value = desiredLocale;
                }
                return found.iterator().next();
            }
        }
        for (final Map.Entry<XLikelySubtags.LSR, Set<ULocale>> supportedLsrAndLocale : this.supportedLanguages.entrySet()) {
            final int distance = this.localeDistance.distanceRaw(desiredLSR, supportedLsrAndLocale.getKey(), this.thresholdDistance, this.distanceOption);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestDesiredLocale = desiredLocale;
                bestSupportedLocales = supportedLsrAndLocale.getValue();
                if (distance == 0) {
                    break;
                }
                continue;
            }
        }
        if (bestDistance >= this.thresholdDistance) {
            if (outputBestDesired != null) {
                outputBestDesired.value = null;
            }
            return this.defaultLanguage;
        }
        if (outputBestDesired != null) {
            outputBestDesired.value = bestDesiredLocale;
        }
        if (bestSupportedLocales.contains(bestDesiredLocale)) {
            return bestDesiredLocale;
        }
        return bestSupportedLocales.iterator().next();
    }
    
    public static ULocale combine(ULocale bestSupported, final ULocale bestDesired) {
        if (!bestSupported.equals(bestDesired) && bestDesired != null) {
            final ULocale.Builder b = new ULocale.Builder().setLocale(bestSupported);
            final String region = bestDesired.getCountry();
            if (!region.isEmpty()) {
                b.setRegion(region);
            }
            final String variants = bestDesired.getVariant();
            if (!variants.isEmpty()) {
                b.setVariant(variants);
            }
            for (final char extensionKey : bestDesired.getExtensionKeys()) {
                b.setExtension(extensionKey, bestDesired.getExtension(extensionKey));
            }
            bestSupported = b.build();
        }
        return bestSupported;
    }
    
    public int distance(final ULocale desired, final ULocale supported) {
        return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(desired), XLikelySubtags.LSR.fromMaximalized(supported), this.thresholdDistance, this.distanceOption);
    }
    
    public int distance(final String desiredLanguage, final String supportedLanguage) {
        return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(new ULocale(desiredLanguage)), XLikelySubtags.LSR.fromMaximalized(new ULocale(supportedLanguage)), this.thresholdDistance, this.distanceOption);
    }
    
    @Override
    public String toString() {
        return this.exactSupportedLocales.toString();
    }
    
    public double match(final ULocale desired, final ULocale supported) {
        return (100 - this.distance(desired, supported)) / 100.0;
    }
    
    @Deprecated
    public double match(final ULocale desired, final ULocale desiredMax, final ULocale supported, final ULocale supportedMax) {
        return this.match(desired, supported);
    }
    
    public ULocale canonicalize(final ULocale ulocale) {
        return null;
    }
    
    public int getThresholdDistance() {
        return this.thresholdDistance;
    }
    
    static {
        UND = new XLikelySubtags.LSR("und", "", "");
        UND_LOCALE = new ULocale("und");
    }
    
    public static class Builder
    {
        private Set<ULocale> supportedLanguagesList;
        private int thresholdDistance;
        private int demotionPerAdditionalDesiredLocale;
        private ULocale defaultLanguage;
        private XLocaleDistance localeDistance;
        private XLocaleDistance.DistanceOption distanceOption;
        
        public Builder() {
            this.thresholdDistance = -1;
            this.demotionPerAdditionalDesiredLocale = -1;
        }
        
        public Builder setSupportedLocales(final String languagePriorityList) {
            this.supportedLanguagesList = asSet(LocalePriorityList.add(languagePriorityList).build());
            return this;
        }
        
        public Builder setSupportedLocales(final LocalePriorityList languagePriorityList) {
            this.supportedLanguagesList = asSet(languagePriorityList);
            return this;
        }
        
        public Builder setSupportedLocales(final Set<ULocale> languagePriorityList) {
            this.supportedLanguagesList = languagePriorityList;
            return this;
        }
        
        public Builder setThresholdDistance(final int thresholdDistance) {
            this.thresholdDistance = thresholdDistance;
            return this;
        }
        
        public Builder setDemotionPerAdditionalDesiredLocale(final int demotionPerAdditionalDesiredLocale) {
            this.demotionPerAdditionalDesiredLocale = demotionPerAdditionalDesiredLocale;
            return this;
        }
        
        public Builder setLocaleDistance(final XLocaleDistance localeDistance) {
            this.localeDistance = localeDistance;
            return this;
        }
        
        public Builder setDefaultLanguage(final ULocale defaultLanguage) {
            this.defaultLanguage = defaultLanguage;
            return this;
        }
        
        public Builder setDistanceOption(final XLocaleDistance.DistanceOption distanceOption) {
            this.distanceOption = distanceOption;
            return this;
        }
        
        public XLocaleMatcher build() {
            return new XLocaleMatcher(this, null);
        }
    }
}
