package com.ibm.icu.impl.locale;

import com.ibm.icu.text.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.util.*;

public class XLocaleDistance
{
    static final boolean PRINT_OVERRIDES = false;
    public static final int ABOVE_THRESHOLD = 100;
    @Deprecated
    public static final String ANY = "\ufffd";
    static final LocaleDisplayNames english;
    static final XCldrStub.Multimap<String, String> CONTAINER_TO_CONTAINED;
    static final XCldrStub.Multimap<String, String> CONTAINER_TO_CONTAINED_FINAL;
    private static final Set<String> ALL_FINAL_REGIONS;
    private final DistanceTable languageDesired2Supported;
    private final RegionMapper regionMapper;
    private final int defaultLanguageDistance;
    private final int defaultScriptDistance;
    private final int defaultRegionDistance;
    private static final XLocaleDistance DEFAULT;
    
    private static String fixAny(final String string) {
        return "*".equals(string) ? "\ufffd" : string;
    }
    
    private static List<Row.R4<String, String, Integer, Boolean>> xGetLanguageMatcherData() {
        final List<Row.R4<String, String, Integer, Boolean>> distanceList = new ArrayList<Row.R4<String, String, Integer, Boolean>>();
        final ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
        final ICUResourceBundle languageMatchingNew = suppData.findTopLevel("languageMatchingNew");
        final ICUResourceBundle written = (ICUResourceBundle)languageMatchingNew.get("written");
        final UResourceBundleIterator iter = written.getIterator();
        while (iter.hasNext()) {
            final ICUResourceBundle item = (ICUResourceBundle)iter.next();
            final boolean oneway = item.getSize() > 3 && "1".equals(item.getString(3));
            distanceList.add((Row.R4)Row.of(item.getString(0), item.getString(1), Integer.parseInt(item.getString(2)), oneway).freeze());
        }
        return Collections.unmodifiableList((List<? extends Row.R4<String, String, Integer, Boolean>>)distanceList);
    }
    
    private static Set<String> xGetParadigmLocales() {
        final ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
        final ICUResourceBundle languageMatchingInfo = suppData.findTopLevel("languageMatchingInfo");
        final ICUResourceBundle writtenParadigmLocales = (ICUResourceBundle)languageMatchingInfo.get("written").get("paradigmLocales");
        final HashSet<String> paradigmLocales = new HashSet<String>(Arrays.asList(writtenParadigmLocales.getStringArray()));
        return Collections.unmodifiableSet((Set<? extends String>)paradigmLocales);
    }
    
    private static Map<String, String> xGetMatchVariables() {
        final ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
        final ICUResourceBundle languageMatchingInfo = suppData.findTopLevel("languageMatchingInfo");
        final ICUResourceBundle writtenMatchVariables = (ICUResourceBundle)languageMatchingInfo.get("written").get("matchVariable");
        final HashMap<String, String> matchVariables = new HashMap<String, String>();
        final Enumeration<String> enumer = writtenMatchVariables.getKeys();
        while (enumer.hasMoreElements()) {
            final String key = enumer.nextElement();
            matchVariables.put(key, writtenMatchVariables.getString(key));
        }
        return Collections.unmodifiableMap((Map<? extends String, ? extends String>)matchVariables);
    }
    
    private static XCldrStub.Multimap<String, String> xGetContainment() {
        final XCldrStub.TreeMultimap<String, String> containment = XCldrStub.TreeMultimap.create();
        containment.putAll("001", "019", "002", "150", "142", "009").putAll("011", "BF", "BJ", "CI", "CV", "GH", "GM", "GN", "GW", "LR", "ML", "MR", "NE", "NG", "SH", "SL", "SN", "TG").putAll("013", "BZ", "CR", "GT", "HN", "MX", "NI", "PA", "SV").putAll("014", "BI", "DJ", "ER", "ET", "KE", "KM", "MG", "MU", "MW", "MZ", "RE", "RW", "SC", "SO", "SS", "TZ", "UG", "YT", "ZM", "ZW").putAll("142", "145", "143", "030", "034", "035").putAll("143", "TM", "TJ", "KG", "KZ", "UZ").putAll("145", "AE", "AM", "AZ", "BH", "CY", "GE", "IL", "IQ", "JO", "KW", "LB", "OM", "PS", "QA", "SA", "SY", "TR", "YE", "NT", "YD").putAll("015", "DZ", "EG", "EH", "LY", "MA", "SD", "TN", "EA", "IC").putAll("150", "154", "155", "151", "039").putAll("151", "BG", "BY", "CZ", "HU", "MD", "PL", "RO", "RU", "SK", "UA", "SU").putAll("154", "GG", "IM", "JE", "AX", "DK", "EE", "FI", "FO", "GB", "IE", "IS", "LT", "LV", "NO", "SE", "SJ").putAll("155", "AT", "BE", "CH", "DE", "FR", "LI", "LU", "MC", "NL", "DD", "FX").putAll("017", "AO", "CD", "CF", "CG", "CM", "GA", "GQ", "ST", "TD", "ZR").putAll("018", "BW", "LS", "NA", "SZ", "ZA").putAll("019", "021", "013", "029", "005", "003", "419").putAll("002", "015", "011", "017", "014", "018").putAll("021", "BM", "CA", "GL", "PM", "US").putAll("029", "AG", "AI", "AW", "BB", "BL", "BQ", "BS", "CU", "CW", "DM", "DO", "GD", "GP", "HT", "JM", "KN", "KY", "LC", "MF", "MQ", "MS", "PR", "SX", "TC", "TT", "VC", "VG", "VI", "AN").putAll("003", "021", "013", "029").putAll("030", "CN", "HK", "JP", "KP", "KR", "MN", "MO", "TW").putAll("035", "BN", "ID", "KH", "LA", "MM", "MY", "PH", "SG", "TH", "TL", "VN", "BU", "TP").putAll("039", "AD", "AL", "BA", "ES", "GI", "GR", "HR", "IT", "ME", "MK", "MT", "RS", "PT", "SI", "SM", "VA", "XK", "CS", "YU").putAll("419", "013", "029", "005").putAll("005", "AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PE", "PY", "SR", "UY", "VE").putAll("053", "AU", "NF", "NZ").putAll("054", "FJ", "NC", "PG", "SB", "VU").putAll("057", "FM", "GU", "KI", "MH", "MP", "NR", "PW").putAll("061", "AS", "CK", "NU", "PF", "PN", "TK", "TO", "TV", "WF", "WS").putAll("034", "AF", "BD", "BT", "IN", "IR", "LK", "MV", "NP", "PK").putAll("009", "053", "054", "057", "061", "QO").putAll("QO", "AQ", "BV", "CC", "CX", "GS", "HM", "IO", "TF", "UM", "AC", "CP", "DG", "TA");
        final XCldrStub.TreeMultimap<String, String> containmentResolved = XCldrStub.TreeMultimap.create();
        fill("001", containment, containmentResolved);
        return XCldrStub.ImmutableMultimap.copyOf(containmentResolved);
    }
    
    private static Set<String> fill(final String region, final XCldrStub.TreeMultimap<String, String> containment, final XCldrStub.Multimap<String, String> toAddTo) {
        final Set<String> contained = containment.get(region);
        if (contained == null) {
            return Collections.emptySet();
        }
        toAddTo.putAll(region, contained);
        for (final String subregion : contained) {
            toAddTo.putAll(region, fill(subregion, containment, toAddTo));
        }
        return toAddTo.get(region);
    }
    
    public XLocaleDistance(final DistanceTable datadistancetable2, final RegionMapper regionMapper) {
        this.languageDesired2Supported = datadistancetable2;
        this.regionMapper = regionMapper;
        final StringDistanceNode languageNode = ((StringDistanceTable)this.languageDesired2Supported).subtables.get("\ufffd").get("\ufffd");
        this.defaultLanguageDistance = languageNode.distance;
        final StringDistanceNode scriptNode = ((StringDistanceTable)languageNode.distanceTable).subtables.get("\ufffd").get("\ufffd");
        this.defaultScriptDistance = scriptNode.distance;
        final DistanceNode regionNode = ((StringDistanceTable)scriptNode.distanceTable).subtables.get("\ufffd").get("\ufffd");
        this.defaultRegionDistance = regionNode.distance;
    }
    
    private static Map newMap() {
        return new TreeMap();
    }
    
    public int distance(final ULocale desired, final ULocale supported, final int threshold, final DistanceOption distanceOption) {
        final XLikelySubtags.LSR supportedLSR = XLikelySubtags.LSR.fromMaximalized(supported);
        final XLikelySubtags.LSR desiredLSR = XLikelySubtags.LSR.fromMaximalized(desired);
        return this.distanceRaw(desiredLSR, supportedLSR, threshold, distanceOption);
    }
    
    public int distanceRaw(final XLikelySubtags.LSR desired, final XLikelySubtags.LSR supported, final int threshold, final DistanceOption distanceOption) {
        return this.distanceRaw(desired.language, supported.language, desired.script, supported.script, desired.region, supported.region, threshold, distanceOption);
    }
    
    public int distanceRaw(final String desiredLang, final String supportedlang, final String desiredScript, final String supportedScript, final String desiredRegion, final String supportedRegion, final int threshold, final DistanceOption distanceOption) {
        final Output<DistanceTable> subtable = new Output<DistanceTable>();
        int distance = this.languageDesired2Supported.getDistance(desiredLang, supportedlang, subtable, true);
        final boolean scriptFirst = distanceOption == DistanceOption.SCRIPT_FIRST;
        if (scriptFirst) {
            distance >>= 2;
        }
        if (distance < 0) {
            distance = 0;
        }
        else if (distance >= threshold) {
            return 100;
        }
        int scriptDistance = subtable.value.getDistance(desiredScript, supportedScript, subtable, true);
        if (scriptFirst) {
            scriptDistance >>= 1;
        }
        distance += scriptDistance;
        if (distance >= threshold) {
            return 100;
        }
        if (desiredRegion.equals(supportedRegion)) {
            return distance;
        }
        final String desiredPartition = this.regionMapper.toId(desiredRegion);
        final String supportedPartition = this.regionMapper.toId(supportedRegion);
        Collection<String> desiredPartitions = desiredPartition.isEmpty() ? this.regionMapper.macroToPartitions.get(desiredRegion) : null;
        Collection<String> supportedPartitions = supportedPartition.isEmpty() ? this.regionMapper.macroToPartitions.get(supportedRegion) : null;
        int subdistance;
        if (desiredPartitions != null || supportedPartitions != null) {
            subdistance = 0;
            if (desiredPartitions == null) {
                desiredPartitions = Collections.singleton(desiredPartition);
            }
            if (supportedPartitions == null) {
                supportedPartitions = Collections.singleton(supportedPartition);
            }
            for (final String desiredPartition2 : desiredPartitions) {
                for (final String supportedPartition2 : supportedPartitions) {
                    final int tempSubdistance = subtable.value.getDistance(desiredPartition2, supportedPartition2, null, false);
                    if (subdistance < tempSubdistance) {
                        subdistance = tempSubdistance;
                    }
                }
            }
        }
        else {
            subdistance = subtable.value.getDistance(desiredPartition, supportedPartition, null, false);
        }
        distance += subdistance;
        return (distance >= threshold) ? 100 : distance;
    }
    
    public static XLocaleDistance getDefault() {
        return XLocaleDistance.DEFAULT;
    }
    
    private static void printMatchXml(final List<String> desired, final List<String> supported, final Integer distance, final Boolean oneway) {
    }
    
    private static String fixedName(final List<String> match) {
        final List<String> alt = new ArrayList<String>(match);
        final int size = alt.size();
        assert size >= 1 && size <= 3;
        final StringBuilder result = new StringBuilder();
        if (size >= 3) {
            final String region = alt.get(2);
            if (region.equals("*") || region.startsWith("$")) {
                result.append(region);
            }
            else {
                result.append(XLocaleDistance.english.regionDisplayName(region));
            }
        }
        if (size >= 2) {
            final String script = alt.get(1);
            if (script.equals("*")) {
                result.insert(0, script);
            }
            else {
                result.insert(0, XLocaleDistance.english.scriptDisplayName(script));
            }
        }
        if (size >= 1) {
            final String language = alt.get(0);
            if (language.equals("*")) {
                result.insert(0, language);
            }
            else {
                result.insert(0, XLocaleDistance.english.languageDisplayName(language));
            }
        }
        return XCldrStub.CollectionUtilities.join(alt, "; ");
    }
    
    public static void add(final StringDistanceTable languageDesired2Supported, final List<String> desired, final List<String> supported, final int percentage) {
        final int size = desired.size();
        if (size != supported.size() || size < 1 || size > 3) {
            throw new IllegalArgumentException();
        }
        final String desiredLang = fixAny(desired.get(0));
        final String supportedLang = fixAny(supported.get(0));
        if (size == 1) {
            languageDesired2Supported.addSubtable(desiredLang, supportedLang, percentage);
        }
        else {
            final String desiredScript = fixAny(desired.get(1));
            final String supportedScript = fixAny(supported.get(1));
            if (size == 2) {
                languageDesired2Supported.addSubtables(desiredLang, supportedLang, desiredScript, supportedScript, percentage);
            }
            else {
                final String desiredRegion = fixAny(desired.get(2));
                final String supportedRegion = fixAny(supported.get(2));
                languageDesired2Supported.addSubtables(desiredLang, supportedLang, desiredScript, supportedScript, desiredRegion, supportedRegion, percentage);
            }
        }
    }
    
    @Override
    public String toString() {
        return this.toString(false);
    }
    
    public String toString(final boolean abbreviate) {
        return this.regionMapper + "\n" + this.languageDesired2Supported.toString(abbreviate);
    }
    
    static Set<String> getContainingMacrosFor(final Collection<String> input, final Set<String> output) {
        output.clear();
        for (final Map.Entry<String, Set<String>> entry : XLocaleDistance.CONTAINER_TO_CONTAINED.asMap().entrySet()) {
            if (input.containsAll(entry.getValue())) {
                output.add(entry.getKey());
            }
        }
        return output;
    }
    
    public static <K, V> XCldrStub.Multimap<K, V> invertMap(final Map<V, K> map) {
        return XCldrStub.Multimaps.invertFrom((Map<Object, Object>)XCldrStub.Multimaps.forMap((Map<V, K>)map), (XCldrStub.Multimap<K, V>)XCldrStub.LinkedHashMultimap.create());
    }
    
    public Set<ULocale> getParadigms() {
        return this.regionMapper.paradigms;
    }
    
    public int getDefaultLanguageDistance() {
        return this.defaultLanguageDistance;
    }
    
    public int getDefaultScriptDistance() {
        return this.defaultScriptDistance;
    }
    
    public int getDefaultRegionDistance() {
        return this.defaultRegionDistance;
    }
    
    @Deprecated
    public StringDistanceTable internalGetDistanceTable() {
        return (StringDistanceTable)this.languageDesired2Supported;
    }
    
    public static void main(final String[] args) {
        final DistanceTable table = getDefault().languageDesired2Supported;
        final DistanceTable compactedTable = table.compact();
        if (!table.equals(compactedTable)) {
            throw new IllegalArgumentException("Compaction isn't equal");
        }
    }
    
    static {
        english = LocaleDisplayNames.getInstance(ULocale.ENGLISH);
        CONTAINER_TO_CONTAINED = xGetContainment();
        final XCldrStub.Multimap<String, String> containerToFinalContainedBuilder = (XCldrStub.Multimap<String, String>)XCldrStub.TreeMultimap.create();
        for (final Map.Entry<String, Set<String>> entry : XLocaleDistance.CONTAINER_TO_CONTAINED.asMap().entrySet()) {
            final String container = entry.getKey();
            for (final String contained : entry.getValue()) {
                if (XLocaleDistance.CONTAINER_TO_CONTAINED.get(contained) == null) {
                    containerToFinalContainedBuilder.put(container, contained);
                }
            }
        }
        CONTAINER_TO_CONTAINED_FINAL = XCldrStub.ImmutableMultimap.copyOf(containerToFinalContainedBuilder);
        ALL_FINAL_REGIONS = XCldrStub.ImmutableSet.copyOf(XLocaleDistance.CONTAINER_TO_CONTAINED_FINAL.get("001"));
        final String[][] variableOverrides = { { "$enUS", "AS+GU+MH+MP+PR+UM+US+VI" }, { "$cnsar", "HK+MO" }, { "$americas", "019" }, { "$maghreb", "MA+DZ+TN+LY+MR+EH" } };
        final String[] paradigmRegions = { "en", "en-GB", "es", "es-419", "pt-BR", "pt-PT" };
        final String[][] regionRuleOverrides = { { "ar_*_$maghreb", "ar_*_$maghreb", "96" }, { "ar_*_$!maghreb", "ar_*_$!maghreb", "96" }, { "ar_*_*", "ar_*_*", "95" }, { "en_*_$enUS", "en_*_$enUS", "96" }, { "en_*_$!enUS", "en_*_$!enUS", "96" }, { "en_*_*", "en_*_*", "95" }, { "es_*_$americas", "es_*_$americas", "96" }, { "es_*_$!americas", "es_*_$!americas", "96" }, { "es_*_*", "es_*_*", "95" }, { "pt_*_$americas", "pt_*_$americas", "96" }, { "pt_*_$!americas", "pt_*_$!americas", "96" }, { "pt_*_*", "pt_*_*", "95" }, { "zh_Hant_$cnsar", "zh_Hant_$cnsar", "96" }, { "zh_Hant_$!cnsar", "zh_Hant_$!cnsar", "96" }, { "zh_Hant_*", "zh_Hant_*", "95" }, { "*_*_*", "*_*_*", "96" } };
        final RegionMapper.Builder rmb = new RegionMapper.Builder().addParadigms(paradigmRegions);
        for (final String[] variableRule : variableOverrides) {
            rmb.add(variableRule[0], variableRule[1]);
        }
        final StringDistanceTable defaultDistanceTable = new StringDistanceTable();
        final RegionMapper defaultRegionMapper = rmb.build();
        final XCldrStub.Splitter bar = XCldrStub.Splitter.on('_');
        final List<Row.R4<List<String>, List<String>, Integer, Boolean>>[] sorted = (List<Row.R4<List<String>, List<String>, Integer, Boolean>>[])new ArrayList[] { new ArrayList(), new ArrayList(), new ArrayList() };
        for (final Row.R4<String, String, Integer, Boolean> info : xGetLanguageMatcherData()) {
            final String desiredRaw = info.get0();
            final String supportedRaw = info.get1();
            final List<String> desired = bar.splitToList(desiredRaw);
            final List<String> supported = bar.splitToList(supportedRaw);
            final Boolean oneway = info.get3();
            final int distance = desiredRaw.equals("*_*") ? 50 : info.get2();
            final int size = desired.size();
            if (size == 3) {
                continue;
            }
            sorted[size - 1].add(Row.of(desired, supported, distance, oneway));
        }
        for (final List<Row.R4<List<String>, List<String>, Integer, Boolean>> item1 : sorted) {
            for (final Row.R4<List<String>, List<String>, Integer, Boolean> item2 : item1) {
                final List<String> desired2 = item2.get0();
                final List<String> supported2 = item2.get1();
                final Integer distance2 = item2.get2();
                final Boolean oneway2 = item2.get3();
                add(defaultDistanceTable, desired2, supported2, distance2);
                if (oneway2 != Boolean.TRUE && !desired2.equals(supported2)) {
                    add(defaultDistanceTable, supported2, desired2, distance2);
                }
                printMatchXml(desired2, supported2, distance2, oneway2);
            }
        }
        for (final String[] rule : regionRuleOverrides) {
            final List<String> desiredBase = new ArrayList<String>(bar.splitToList(rule[0]));
            final List<String> supportedBase = new ArrayList<String>(bar.splitToList(rule[1]));
            final Integer distance3 = 100 - Integer.parseInt(rule[2]);
            printMatchXml(desiredBase, supportedBase, distance3, false);
            final Collection<String> desiredRegions = defaultRegionMapper.getIdsFromVariable(desiredBase.get(2));
            if (desiredRegions.isEmpty()) {
                throw new IllegalArgumentException("Bad region variable: " + desiredBase.get(2));
            }
            final Collection<String> supportedRegions = defaultRegionMapper.getIdsFromVariable(supportedBase.get(2));
            if (supportedRegions.isEmpty()) {
                throw new IllegalArgumentException("Bad region variable: " + supportedBase.get(2));
            }
            for (final String desiredRegion2 : desiredRegions) {
                desiredBase.set(2, desiredRegion2.toString());
                for (final String supportedRegion2 : supportedRegions) {
                    supportedBase.set(2, supportedRegion2.toString());
                    add(defaultDistanceTable, desiredBase, supportedBase, distance3);
                    add(defaultDistanceTable, supportedBase, desiredBase, distance3);
                }
            }
        }
        DEFAULT = new XLocaleDistance(defaultDistanceTable.compact(), defaultRegionMapper);
    }
    
    @Deprecated
    public abstract static class DistanceTable
    {
        abstract int getDistance(final String p0, final String p1, final Output<DistanceTable> p2, final boolean p3);
        
        abstract Set<String> getCloser(final int p0);
        
        abstract String toString(final boolean p0);
        
        public DistanceTable compact() {
            return this;
        }
        
        public DistanceNode getInternalNode(final String any, final String any2) {
            return null;
        }
        
        public Map<String, Set<String>> getInternalMatches() {
            return null;
        }
        
        public boolean isEmpty() {
            return true;
        }
    }
    
    @Deprecated
    public static class DistanceNode
    {
        final int distance;
        
        public DistanceNode(final int distance) {
            this.distance = distance;
        }
        
        public DistanceTable getDistanceTable() {
            return null;
        }
        
        @Override
        public boolean equals(final Object obj) {
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.distance == ((DistanceNode)obj).distance);
        }
        
        @Override
        public int hashCode() {
            return this.distance;
        }
        
        @Override
        public String toString() {
            return "\ndistance: " + this.distance;
        }
    }
    
    static class IdMakerFull<T> implements IdMapper<T, Integer>
    {
        private final Map<T, Integer> objectToInt;
        private final List<T> intToObject;
        final String name;
        
        IdMakerFull(final String name) {
            this.objectToInt = new HashMap<T, Integer>();
            this.intToObject = new ArrayList<T>();
            this.name = name;
        }
        
        IdMakerFull() {
            this("unnamed");
        }
        
        IdMakerFull(final String name, final T zeroValue) {
            this(name);
            this.add(zeroValue);
        }
        
        public Integer add(final T source) {
            final Integer result = this.objectToInt.get(source);
            if (result == null) {
                final Integer newResult = this.intToObject.size();
                this.objectToInt.put(source, newResult);
                this.intToObject.add(source);
                return newResult;
            }
            return result;
        }
        
        @Override
        public Integer toId(final T source) {
            return this.objectToInt.get(source);
        }
        
        public T fromId(final int id) {
            return this.intToObject.get(id);
        }
        
        public T intern(final T source) {
            return this.fromId(this.add(source));
        }
        
        public int size() {
            return this.intToObject.size();
        }
        
        public Integer getOldAndAdd(final T source) {
            final Integer result = this.objectToInt.get(source);
            if (result == null) {
                final Integer newResult = this.intToObject.size();
                this.objectToInt.put(source, newResult);
                this.intToObject.add(source);
            }
            return result;
        }
        
        @Override
        public String toString() {
            return this.size() + ": " + this.intToObject;
        }
        
        @Override
        public boolean equals(final Object obj) {
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.intToObject.equals(((IdMakerFull)obj).intToObject));
        }
        
        @Override
        public int hashCode() {
            return this.intToObject.hashCode();
        }
    }
    
    static class StringDistanceNode extends DistanceNode
    {
        final DistanceTable distanceTable;
        
        public StringDistanceNode(final int distance, final DistanceTable distanceTable) {
            super(distance);
            this.distanceTable = distanceTable;
        }
        
        @Override
        public boolean equals(final Object obj) {
            final StringDistanceNode other;
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.distance == (other = (StringDistanceNode)obj).distance && Utility.equals(this.distanceTable, other.distanceTable) && super.equals(other));
        }
        
        @Override
        public int hashCode() {
            return this.distance ^ Utility.hashCode(this.distanceTable);
        }
        
        StringDistanceNode(final int distance) {
            this(distance, new StringDistanceTable());
        }
        
        public void addSubtables(final String desiredSub, final String supportedSub, final CopyIfEmpty r) {
            ((StringDistanceTable)this.distanceTable).addSubtables(desiredSub, supportedSub, r);
        }
        
        @Override
        public String toString() {
            return "distance: " + this.distance + "\n" + this.distanceTable;
        }
        
        public void copyTables(final StringDistanceTable value) {
            if (value != null) {
                ((StringDistanceTable)this.distanceTable).copy(value);
            }
        }
        
        @Override
        public DistanceTable getDistanceTable() {
            return this.distanceTable;
        }
    }
    
    @Deprecated
    public static class StringDistanceTable extends DistanceTable
    {
        final Map<String, Map<String, DistanceNode>> subtables;
        
        StringDistanceTable(final Map<String, Map<String, DistanceNode>> tables) {
            this.subtables = tables;
        }
        
        StringDistanceTable() {
            this(newMap());
        }
        
        @Override
        public boolean isEmpty() {
            return this.subtables.isEmpty();
        }
        
        @Override
        public boolean equals(final Object obj) {
            return this == obj || (obj != null && obj.getClass() == this.getClass() && this.subtables.equals(((StringDistanceTable)obj).subtables));
        }
        
        @Override
        public int hashCode() {
            return this.subtables.hashCode();
        }
        
        public int getDistance(final String desired, final String supported, final Output<DistanceTable> distanceTable, final boolean starEquals) {
            boolean star = false;
            Map<String, DistanceNode> sub2 = this.subtables.get(desired);
            if (sub2 == null) {
                sub2 = this.subtables.get("\ufffd");
                star = true;
            }
            DistanceNode value = sub2.get(supported);
            if (value == null) {
                value = sub2.get("\ufffd");
                if (value == null && !star) {
                    sub2 = this.subtables.get("\ufffd");
                    value = sub2.get(supported);
                    if (value == null) {
                        value = sub2.get("\ufffd");
                    }
                }
                star = true;
            }
            if (distanceTable != null) {
                distanceTable.value = ((StringDistanceNode)value).distanceTable;
            }
            return (starEquals && star && desired.equals(supported)) ? 0 : value.distance;
        }
        
        public void copy(final StringDistanceTable other) {
            for (final Map.Entry<String, Map<String, DistanceNode>> e1 : other.subtables.entrySet()) {
                for (final Map.Entry<String, DistanceNode> e2 : e1.getValue().entrySet()) {
                    final DistanceNode value = e2.getValue();
                    this.addSubtable(e1.getKey(), e2.getKey(), value.distance);
                }
            }
        }
        
        DistanceNode addSubtable(final String desired, final String supported, final int distance) {
            Map<String, DistanceNode> sub2 = this.subtables.get(desired);
            if (sub2 == null) {
                this.subtables.put(desired, sub2 = (Map<String, DistanceNode>)newMap());
            }
            final DistanceNode oldNode = sub2.get(supported);
            if (oldNode != null) {
                return oldNode;
            }
            final StringDistanceNode newNode = new StringDistanceNode(distance);
            sub2.put(supported, newNode);
            return newNode;
        }
        
        private DistanceNode getNode(final String desired, final String supported) {
            final Map<String, DistanceNode> sub2 = this.subtables.get(desired);
            if (sub2 == null) {
                return null;
            }
            return sub2.get(supported);
        }
        
        public void addSubtables(final String desired, final String supported, final XCldrStub.Predicate<DistanceNode> action) {
            DistanceNode node = this.getNode(desired, supported);
            if (node == null) {
                final Output<DistanceTable> node2 = new Output<DistanceTable>();
                final int distance = this.getDistance(desired, supported, node2, true);
                node = this.addSubtable(desired, supported, distance);
                if (node2.value != null) {
                    ((StringDistanceNode)node).copyTables((StringDistanceTable)node2.value);
                }
            }
            action.test(node);
        }
        
        public void addSubtables(final String desiredLang, final String supportedLang, final String desiredScript, final String supportedScript, final int percentage) {
            boolean haveKeys = false;
            for (final Map.Entry<String, Map<String, DistanceNode>> e1 : this.subtables.entrySet()) {
                final String key1 = e1.getKey();
                final boolean desiredIsKey = desiredLang.equals(key1);
                if (desiredIsKey || desiredLang.equals("\ufffd")) {
                    for (final Map.Entry<String, DistanceNode> e2 : e1.getValue().entrySet()) {
                        final String key2 = e2.getKey();
                        final boolean supportedIsKey = supportedLang.equals(key2);
                        haveKeys |= (desiredIsKey && supportedIsKey);
                        if (supportedIsKey || supportedLang.equals("\ufffd")) {
                            final DistanceNode value = e2.getValue();
                            ((StringDistanceTable)value.getDistanceTable()).addSubtable(desiredScript, supportedScript, percentage);
                        }
                    }
                }
            }
            final StringDistanceTable dt = new StringDistanceTable();
            dt.addSubtable(desiredScript, supportedScript, percentage);
            final CopyIfEmpty r = new CopyIfEmpty(dt);
            this.addSubtables(desiredLang, supportedLang, r);
        }
        
        public void addSubtables(final String desiredLang, final String supportedLang, final String desiredScript, final String supportedScript, final String desiredRegion, final String supportedRegion, final int percentage) {
            boolean haveKeys = false;
            for (final Map.Entry<String, Map<String, DistanceNode>> e1 : this.subtables.entrySet()) {
                final String key1 = e1.getKey();
                final boolean desiredIsKey = desiredLang.equals(key1);
                if (desiredIsKey || desiredLang.equals("\ufffd")) {
                    for (final Map.Entry<String, DistanceNode> e2 : e1.getValue().entrySet()) {
                        final String key2 = e2.getKey();
                        final boolean supportedIsKey = supportedLang.equals(key2);
                        haveKeys |= (desiredIsKey && supportedIsKey);
                        if (supportedIsKey || supportedLang.equals("\ufffd")) {
                            final StringDistanceNode value = e2.getValue();
                            ((StringDistanceTable)value.distanceTable).addSubtables(desiredScript, supportedScript, desiredRegion, supportedRegion, percentage);
                        }
                    }
                }
            }
            final StringDistanceTable dt = new StringDistanceTable();
            dt.addSubtable(desiredRegion, supportedRegion, percentage);
            final AddSub r = new AddSub(desiredScript, supportedScript, dt);
            this.addSubtables(desiredLang, supportedLang, r);
        }
        
        @Override
        public String toString() {
            return this.toString(false);
        }
        
        public String toString(final boolean abbreviate) {
            return this.toString(abbreviate, "", new IdMakerFull<Object>("interner"), new StringBuilder()).toString();
        }
        
        public StringBuilder toString(final boolean abbreviate, final String indent, final IdMakerFull<Object> intern, final StringBuilder buffer) {
            String indent2 = indent.isEmpty() ? "" : "\t";
            Integer id = abbreviate ? intern.getOldAndAdd(this.subtables) : null;
            if (id != null) {
                buffer.append(indent2).append('#').append(id).append('\n');
            }
            else {
                for (final Map.Entry<String, Map<String, DistanceNode>> e1 : this.subtables.entrySet()) {
                    final Map<String, DistanceNode> subsubtable = e1.getValue();
                    buffer.append(indent2).append(e1.getKey());
                    String indent3 = "\t";
                    id = (abbreviate ? intern.getOldAndAdd(subsubtable) : null);
                    if (id != null) {
                        buffer.append(indent3).append('#').append(id).append('\n');
                    }
                    else {
                        for (final Map.Entry<String, DistanceNode> e2 : subsubtable.entrySet()) {
                            final DistanceNode value = e2.getValue();
                            buffer.append(indent3).append(e2.getKey());
                            id = (abbreviate ? intern.getOldAndAdd(value) : null);
                            if (id != null) {
                                buffer.append('\t').append('#').append(id).append('\n');
                            }
                            else {
                                buffer.append('\t').append(value.distance);
                                final DistanceTable distanceTable = value.getDistanceTable();
                                if (distanceTable != null) {
                                    id = (abbreviate ? intern.getOldAndAdd(distanceTable) : null);
                                    if (id != null) {
                                        buffer.append('\t').append('#').append(id).append('\n');
                                    }
                                    else {
                                        ((StringDistanceTable)distanceTable).toString(abbreviate, indent + "\t\t\t", intern, buffer);
                                    }
                                }
                                else {
                                    buffer.append('\n');
                                }
                            }
                            indent3 = indent + '\t';
                        }
                    }
                    indent2 = indent;
                }
            }
            return buffer;
        }
        
        @Override
        public StringDistanceTable compact() {
            return new CompactAndImmutablizer().compact(this);
        }
        
        public Set<String> getCloser(final int threshold) {
            final Set<String> result = new HashSet<String>();
            for (final Map.Entry<String, Map<String, DistanceNode>> e1 : this.subtables.entrySet()) {
                final String desired = e1.getKey();
                for (final Map.Entry<String, DistanceNode> e2 : e1.getValue().entrySet()) {
                    if (e2.getValue().distance < threshold) {
                        result.add(desired);
                        break;
                    }
                }
            }
            return result;
        }
        
        public Integer getInternalDistance(final String a, final String b) {
            final Map<String, DistanceNode> subsub = this.subtables.get(a);
            if (subsub == null) {
                return null;
            }
            final DistanceNode dnode = subsub.get(b);
            return (dnode == null) ? null : Integer.valueOf(dnode.distance);
        }
        
        @Override
        public DistanceNode getInternalNode(final String a, final String b) {
            final Map<String, DistanceNode> subsub = this.subtables.get(a);
            if (subsub == null) {
                return null;
            }
            return subsub.get(b);
        }
        
        @Override
        public Map<String, Set<String>> getInternalMatches() {
            final Map<String, Set<String>> result = new LinkedHashMap<String, Set<String>>();
            for (final Map.Entry<String, Map<String, DistanceNode>> entry : this.subtables.entrySet()) {
                result.put(entry.getKey(), new LinkedHashSet<String>(entry.getValue().keySet()));
            }
            return result;
        }
    }
    
    static class CopyIfEmpty implements XCldrStub.Predicate<DistanceNode>
    {
        private final StringDistanceTable toCopy;
        
        CopyIfEmpty(final StringDistanceTable resetIfNotNull) {
            this.toCopy = resetIfNotNull;
        }
        
        @Override
        public boolean test(final DistanceNode node) {
            final StringDistanceTable subtables = (StringDistanceTable)node.getDistanceTable();
            if (subtables.subtables.isEmpty()) {
                subtables.copy(this.toCopy);
            }
            return true;
        }
    }
    
    static class AddSub implements XCldrStub.Predicate<DistanceNode>
    {
        private final String desiredSub;
        private final String supportedSub;
        private final CopyIfEmpty r;
        
        AddSub(final String desiredSub, final String supportedSub, final StringDistanceTable distanceTableToCopy) {
            this.r = new CopyIfEmpty(distanceTableToCopy);
            this.desiredSub = desiredSub;
            this.supportedSub = supportedSub;
        }
        
        @Override
        public boolean test(final DistanceNode node) {
            if (node == null) {
                throw new IllegalArgumentException("bad structure");
            }
            ((StringDistanceNode)node).addSubtables(this.desiredSub, this.supportedSub, this.r);
            return true;
        }
    }
    
    public enum DistanceOption
    {
        NORMAL, 
        SCRIPT_FIRST;
    }
    
    static class RegionMapper implements IdMapper<String, String>
    {
        final XCldrStub.Multimap<String, String> variableToPartition;
        final Map<String, String> regionToPartition;
        final XCldrStub.Multimap<String, String> macroToPartitions;
        final Set<ULocale> paradigms;
        
        private RegionMapper(final XCldrStub.Multimap<String, String> variableToPartitionIn, final Map<String, String> regionToPartitionIn, final XCldrStub.Multimap<String, String> macroToPartitionsIn, final Set<ULocale> paradigmsIn) {
            this.variableToPartition = XCldrStub.ImmutableMultimap.copyOf(variableToPartitionIn);
            this.regionToPartition = XCldrStub.ImmutableMap.copyOf(regionToPartitionIn);
            this.macroToPartitions = XCldrStub.ImmutableMultimap.copyOf(macroToPartitionsIn);
            this.paradigms = XCldrStub.ImmutableSet.copyOf(paradigmsIn);
        }
        
        @Override
        public String toId(final String region) {
            final String result = this.regionToPartition.get(region);
            return (result == null) ? "" : result;
        }
        
        public Collection<String> getIdsFromVariable(final String variable) {
            if (variable.equals("*")) {
                return Collections.singleton("*");
            }
            final Collection<String> result = this.variableToPartition.get(variable);
            if (result == null || result.isEmpty()) {
                throw new IllegalArgumentException("Variable not defined: " + variable);
            }
            return result;
        }
        
        public Set<String> regions() {
            return this.regionToPartition.keySet();
        }
        
        public Set<String> variables() {
            return this.variableToPartition.keySet();
        }
        
        @Override
        public String toString() {
            final XCldrStub.TreeMultimap<String, String> partitionToVariables = XCldrStub.Multimaps.invertFrom(this.variableToPartition, XCldrStub.TreeMultimap.create());
            final XCldrStub.TreeMultimap<String, String> partitionToRegions = XCldrStub.TreeMultimap.create();
            for (final Map.Entry<String, String> e : this.regionToPartition.entrySet()) {
                partitionToRegions.put(e.getValue(), e.getKey());
            }
            final StringBuilder buffer = new StringBuilder();
            buffer.append("Partition \u27a0 Variables \u27a0 Regions (final)");
            for (final Map.Entry<String, Set<String>> e2 : partitionToVariables.asMap().entrySet()) {
                buffer.append('\n');
                buffer.append(e2.getKey() + "\t" + e2.getValue() + "\t" + partitionToRegions.get(e2.getKey()));
            }
            buffer.append("\nMacro \u27a0 Partitions");
            for (final Map.Entry<String, Set<String>> e2 : this.macroToPartitions.asMap().entrySet()) {
                buffer.append('\n');
                buffer.append(e2.getKey() + "\t" + e2.getValue());
            }
            return buffer.toString();
        }
        
        static class Builder
        {
            private final XCldrStub.Multimap<String, String> regionToRawPartition;
            private final RegionSet regionSet;
            private final Set<ULocale> paradigms;
            
            Builder() {
                this.regionToRawPartition = (XCldrStub.Multimap<String, String>)XCldrStub.TreeMultimap.create();
                this.regionSet = new RegionSet();
                this.paradigms = new LinkedHashSet<ULocale>();
            }
            
            void add(final String variable, final String barString) {
                final Set<String> tempRegions = this.regionSet.parseSet(barString);
                for (final String region : tempRegions) {
                    this.regionToRawPartition.put(region, variable);
                }
                final Set<String> inverse = this.regionSet.inverse();
                final String inverseVariable = "$!" + variable.substring(1);
                for (final String region2 : inverse) {
                    this.regionToRawPartition.put(region2, inverseVariable);
                }
            }
            
            public Builder addParadigms(final String... paradigmRegions) {
                for (final String paradigm : paradigmRegions) {
                    this.paradigms.add(new ULocale(paradigm));
                }
                return this;
            }
            
            RegionMapper build() {
                final IdMakerFull<Collection<String>> id = new IdMakerFull<Collection<String>>("partition");
                final XCldrStub.Multimap<String, String> variableToPartitions = (XCldrStub.Multimap<String, String>)XCldrStub.TreeMultimap.create();
                final Map<String, String> regionToPartition = new TreeMap<String, String>();
                final XCldrStub.Multimap<String, String> partitionToRegions = (XCldrStub.Multimap<String, String>)XCldrStub.TreeMultimap.create();
                for (final Map.Entry<String, Set<String>> e : this.regionToRawPartition.asMap().entrySet()) {
                    final String region = e.getKey();
                    final Collection<String> rawPartition = e.getValue();
                    final String partition = String.valueOf((char)(945 + id.add(rawPartition)));
                    regionToPartition.put(region, partition);
                    partitionToRegions.put(partition, region);
                    for (final String variable : rawPartition) {
                        variableToPartitions.put(variable, partition);
                    }
                }
                final XCldrStub.Multimap<String, String> macroToPartitions = (XCldrStub.Multimap<String, String>)XCldrStub.TreeMultimap.create();
                for (final Map.Entry<String, Set<String>> e2 : XLocaleDistance.CONTAINER_TO_CONTAINED.asMap().entrySet()) {
                    final String macro = e2.getKey();
                    for (final Map.Entry<String, Set<String>> e3 : partitionToRegions.asMap().entrySet()) {
                        final String partition2 = e3.getKey();
                        if (!Collections.disjoint(e2.getValue(), e3.getValue())) {
                            macroToPartitions.put(macro, partition2);
                        }
                    }
                }
                return new RegionMapper((XCldrStub.Multimap)variableToPartitions, (Map)regionToPartition, (XCldrStub.Multimap)macroToPartitions, (Set)this.paradigms);
            }
        }
    }
    
    private static class RegionSet
    {
        private final Set<String> tempRegions;
        private Operation operation;
        
        private RegionSet() {
            this.tempRegions = new TreeSet<String>();
            this.operation = null;
        }
        
        private Set<String> parseSet(final String barString) {
            this.operation = Operation.add;
            int last = 0;
            this.tempRegions.clear();
            int i;
            for (i = 0; i < barString.length(); ++i) {
                final char c = barString.charAt(i);
                switch (c) {
                    case '+': {
                        this.add(barString, last, i);
                        last = i + 1;
                        this.operation = Operation.add;
                        break;
                    }
                    case '-': {
                        this.add(barString, last, i);
                        last = i + 1;
                        this.operation = Operation.remove;
                        break;
                    }
                }
            }
            this.add(barString, last, i);
            return this.tempRegions;
        }
        
        private Set<String> inverse() {
            final TreeSet<String> result = new TreeSet<String>(XLocaleDistance.ALL_FINAL_REGIONS);
            result.removeAll(this.tempRegions);
            return result;
        }
        
        private void add(final String barString, final int last, final int i) {
            if (i > last) {
                final String region = barString.substring(last, i);
                this.changeSet(this.operation, region);
            }
        }
        
        private void changeSet(final Operation operation, final String region) {
            final Collection<String> contained = XLocaleDistance.CONTAINER_TO_CONTAINED_FINAL.get(region);
            if (contained != null && !contained.isEmpty()) {
                if (Operation.add == operation) {
                    this.tempRegions.addAll(contained);
                }
                else {
                    this.tempRegions.removeAll(contained);
                }
            }
            else if (Operation.add == operation) {
                this.tempRegions.add(region);
            }
            else {
                this.tempRegions.remove(region);
            }
        }
        
        private enum Operation
        {
            add, 
            remove;
        }
    }
    
    static class CompactAndImmutablizer extends IdMakerFull<Object>
    {
        StringDistanceTable compact(final StringDistanceTable item) {
            if (((IdMakerFull<StringDistanceTable>)this).toId(item) != null) {
                return ((IdMakerFull<StringDistanceTable>)this).intern(item);
            }
            return new StringDistanceTable(this.compact(item.subtables, 0));
        }
        
         <K, T> Map<K, T> compact(final Map<K, T> item, final int level) {
            if (((IdMakerFull<Map<K, T>>)this).toId(item) != null) {
                return ((IdMakerFull<Map<K, T>>)this).intern(item);
            }
            final Map<K, T> copy = new LinkedHashMap<K, T>();
            for (final Map.Entry<K, T> entry : item.entrySet()) {
                final T value = entry.getValue();
                if (value instanceof Map) {
                    copy.put(entry.getKey(), (T)this.compact((Map<Object, Object>)value, level + 1));
                }
                else {
                    copy.put(entry.getKey(), (T)this.compact((DistanceNode)value));
                }
            }
            return XCldrStub.ImmutableMap.copyOf(copy);
        }
        
        DistanceNode compact(final DistanceNode item) {
            if (((IdMakerFull<DistanceNode>)this).toId(item) != null) {
                return ((IdMakerFull<DistanceNode>)this).intern(item);
            }
            final DistanceTable distanceTable = item.getDistanceTable();
            if (distanceTable == null || distanceTable.isEmpty()) {
                return new DistanceNode(item.distance);
            }
            return new StringDistanceNode(item.distance, this.compact((StringDistanceTable)((StringDistanceNode)item).distanceTable));
        }
    }
    
    private interface IdMapper<K, V>
    {
        V toId(final K p0);
    }
}
