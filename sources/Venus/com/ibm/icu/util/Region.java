/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.util.UResourceBundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Region
implements Comparable<Region> {
    private String id;
    private int code;
    private RegionType type;
    private Region containingRegion = null;
    private Set<Region> containedRegions = new TreeSet<Region>();
    private List<Region> preferredValues = null;
    private static boolean regionDataIsLoaded = false;
    private static Map<String, Region> regionIDMap = null;
    private static Map<Integer, Region> numericCodeMap = null;
    private static Map<String, Region> regionAliases = null;
    private static ArrayList<Region> regions = null;
    private static ArrayList<Set<Region>> availableRegions = null;
    private static final String UNKNOWN_REGION_ID = "ZZ";
    private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
    private static final String WORLD_ID = "001";

    private Region() {
    }

    /*
     * WARNING - void declaration
     */
    private static synchronized void loadRegionData() {
        void var19_38;
        void var19_36;
        int n2;
        Object object;
        Object object2;
        if (regionDataIsLoaded) {
            return;
        }
        regionAliases = new HashMap<String, Region>();
        regionIDMap = new HashMap<String, Region>();
        numericCodeMap = new HashMap<Integer, Region>();
        availableRegions = new ArrayList(RegionType.values().length);
        UResourceBundle uResourceBundle = null;
        UResourceBundle uResourceBundle2 = null;
        UResourceBundle uResourceBundle3 = null;
        UResourceBundle uResourceBundle4 = null;
        UResourceBundle uResourceBundle5 = null;
        UResourceBundle uResourceBundle6 = null;
        UResourceBundle uResourceBundle7 = null;
        UResourceBundle uResourceBundle8 = null;
        UResourceBundle uResourceBundle9 = null;
        UResourceBundle uResourceBundle10 = null;
        UResourceBundle uResourceBundle11 = null;
        UResourceBundle uResourceBundle12 = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        uResourceBundle = uResourceBundle12.get("alias");
        uResourceBundle2 = uResourceBundle.get("territory");
        UResourceBundle uResourceBundle13 = UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        uResourceBundle3 = uResourceBundle13.get("codeMappings");
        uResourceBundle4 = uResourceBundle13.get("idValidity");
        uResourceBundle5 = uResourceBundle4.get("region");
        uResourceBundle6 = uResourceBundle5.get("regular");
        uResourceBundle7 = uResourceBundle5.get("macroregion");
        uResourceBundle8 = uResourceBundle5.get("unknown");
        uResourceBundle10 = uResourceBundle13.get("territoryContainment");
        uResourceBundle9 = uResourceBundle10.get(WORLD_ID);
        uResourceBundle11 = uResourceBundle10.get("grouping");
        String[] stringArray = uResourceBundle9.getStringArray();
        List<String> list = Arrays.asList(stringArray);
        Enumeration<String> enumeration = uResourceBundle11.getKeys();
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<String> arrayList2 = new ArrayList<String>();
        arrayList2.addAll(Arrays.asList(uResourceBundle6.getStringArray()));
        arrayList2.addAll(Arrays.asList(uResourceBundle7.getStringArray()));
        arrayList2.add(uResourceBundle8.getString());
        for (String object32 : arrayList2) {
            int n = object32.indexOf("~");
            if (n > 0) {
                object2 = new StringBuilder(object32);
                char c = ((StringBuilder)object2).charAt(n + 1);
                ((StringBuilder)object2).setLength(n);
                for (char c2 = ((StringBuilder)object2).charAt(n - 1); c2 <= c; c2 = (char)(c2 + '\u0001')) {
                    object = ((StringBuilder)object2).toString();
                    arrayList.add((String)object);
                    ((StringBuilder)object2).setCharAt(n - 1, c2);
                }
                continue;
            }
            arrayList.add(object32);
        }
        regions = new ArrayList(arrayList.size());
        for (String string : arrayList) {
            Region region = new Region();
            region.id = string;
            region.type = RegionType.TERRITORY;
            regionIDMap.put(string, region);
            if (string.matches("[0-9]{3}")) {
                region.code = Integer.valueOf(string);
                numericCodeMap.put(region.code, region);
                region.type = RegionType.SUBCONTINENT;
            } else {
                region.code = -1;
            }
            regions.add(region);
        }
        for (n2 = 0; n2 < uResourceBundle2.getSize(); ++n2) {
            Region region;
            UResourceBundle uResourceBundle14 = uResourceBundle2.get(n2);
            String string = uResourceBundle14.getKey();
            object2 = uResourceBundle14.get("replacement").getString();
            if (regionIDMap.containsKey(object2) && !regionIDMap.containsKey(string)) {
                regionAliases.put(string, regionIDMap.get(object2));
                continue;
            }
            if (regionIDMap.containsKey(string)) {
                region = regionIDMap.get(string);
            } else {
                region = new Region();
                region.id = string;
                regionIDMap.put(string, region);
                if (string.matches("[0-9]{3}")) {
                    region.code = Integer.valueOf(string);
                    numericCodeMap.put(region.code, region);
                } else {
                    region.code = -1;
                }
                regions.add(region);
            }
            region.type = RegionType.DEPRECATED;
            List<String> list2 = Arrays.asList(((String)object2).split(" "));
            region.preferredValues = new ArrayList<Region>();
            for (String string2 : list2) {
                if (!regionIDMap.containsKey(string2)) continue;
                region.preferredValues.add(regionIDMap.get(string2));
            }
        }
        for (n2 = 0; n2 < uResourceBundle3.getSize(); ++n2) {
            UResourceBundle uResourceBundle15 = uResourceBundle3.get(n2);
            if (uResourceBundle15.getType() != 8) continue;
            String[] stringArray2 = uResourceBundle15.getStringArray();
            object2 = stringArray2[0];
            Integer n = Integer.valueOf(stringArray2[5]);
            String string = stringArray2[5];
            if (!regionIDMap.containsKey(object2)) continue;
            object = regionIDMap.get(object2);
            ((Region)object).code = n;
            numericCodeMap.put(((Region)object).code, (Region)object);
            regionAliases.put(string, (Region)object);
        }
        if (regionIDMap.containsKey(WORLD_ID)) {
            Region region = regionIDMap.get(WORLD_ID);
            region.type = RegionType.WORLD;
        }
        if (regionIDMap.containsKey(UNKNOWN_REGION_ID)) {
            Region region = regionIDMap.get(UNKNOWN_REGION_ID);
            region.type = RegionType.UNKNOWN;
        }
        for (String string : list) {
            if (!regionIDMap.containsKey(string)) continue;
            Region region = regionIDMap.get(string);
            region.type = RegionType.CONTINENT;
        }
        while (enumeration.hasMoreElements()) {
            String string = enumeration.nextElement();
            if (!regionIDMap.containsKey(string)) continue;
            Region region = regionIDMap.get(string);
            region.type = RegionType.GROUPING;
        }
        if (regionIDMap.containsKey(OUTLYING_OCEANIA_REGION_ID)) {
            Region region = regionIDMap.get(OUTLYING_OCEANIA_REGION_ID);
            region.type = RegionType.SUBCONTINENT;
        }
        boolean bl = false;
        while (var19_36 < uResourceBundle10.getSize()) {
            UResourceBundle uResourceBundle16 = uResourceBundle10.get((int)var19_36);
            object2 = uResourceBundle16.getKey();
            if (!(((String)object2).equals("containedGroupings") || ((String)object2).equals("deprecated") || ((String)object2).equals("grouping"))) {
                Region region = regionIDMap.get(object2);
                for (int i = 0; i < uResourceBundle16.getSize(); ++i) {
                    object = uResourceBundle16.getString(i);
                    Region region2 = regionIDMap.get(object);
                    if (region == null || region2 == null) continue;
                    region.containedRegions.add(region2);
                    if (region.getType() == RegionType.GROUPING) continue;
                    region2.containingRegion = region;
                }
            }
            ++var19_36;
        }
        boolean bl2 = false;
        while (var19_38 < RegionType.values().length) {
            availableRegions.add(new TreeSet());
            ++var19_38;
        }
        for (Region region : regions) {
            object2 = availableRegions.get(region.type.ordinal());
            object2.add(region);
            availableRegions.set(region.type.ordinal(), (Set<Region>)object2);
        }
        regionDataIsLoaded = true;
    }

    public static Region getInstance(String string) {
        if (string == null) {
            throw new NullPointerException();
        }
        Region.loadRegionData();
        Region region = regionIDMap.get(string);
        if (region == null) {
            region = regionAliases.get(string);
        }
        if (region == null) {
            throw new IllegalArgumentException("Unknown region id: " + string);
        }
        if (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) {
            region = region.preferredValues.get(0);
        }
        return region;
    }

    public static Region getInstance(int n) {
        Region.loadRegionData();
        Region region = numericCodeMap.get(n);
        if (region == null) {
            String string = "";
            if (n < 10) {
                string = "00";
            } else if (n < 100) {
                string = "0";
            }
            String string2 = string + Integer.toString(n);
            region = regionAliases.get(string2);
        }
        if (region == null) {
            throw new IllegalArgumentException("Unknown region code: " + n);
        }
        if (region.type == RegionType.DEPRECATED && region.preferredValues.size() == 1) {
            region = region.preferredValues.get(0);
        }
        return region;
    }

    public static Set<Region> getAvailable(RegionType regionType) {
        Region.loadRegionData();
        return Collections.unmodifiableSet(availableRegions.get(regionType.ordinal()));
    }

    public Region getContainingRegion() {
        Region.loadRegionData();
        return this.containingRegion;
    }

    public Region getContainingRegion(RegionType regionType) {
        Region.loadRegionData();
        if (this.containingRegion == null) {
            return null;
        }
        if (this.containingRegion.type.equals((Object)regionType)) {
            return this.containingRegion;
        }
        return this.containingRegion.getContainingRegion(regionType);
    }

    public Set<Region> getContainedRegions() {
        Region.loadRegionData();
        return Collections.unmodifiableSet(this.containedRegions);
    }

    public Set<Region> getContainedRegions(RegionType regionType) {
        Region.loadRegionData();
        TreeSet<Region> treeSet = new TreeSet<Region>();
        Set<Region> set = this.getContainedRegions();
        for (Region region : set) {
            if (region.getType() == regionType) {
                treeSet.add(region);
                continue;
            }
            treeSet.addAll(region.getContainedRegions(regionType));
        }
        return Collections.unmodifiableSet(treeSet);
    }

    public List<Region> getPreferredValues() {
        Region.loadRegionData();
        if (this.type == RegionType.DEPRECATED) {
            return Collections.unmodifiableList(this.preferredValues);
        }
        return null;
    }

    public boolean contains(Region region) {
        Region.loadRegionData();
        if (this.containedRegions.contains(region)) {
            return false;
        }
        for (Region region2 : this.containedRegions) {
            if (!region2.contains(region)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        return this.id;
    }

    public int getNumericCode() {
        return this.code;
    }

    public RegionType getType() {
        return this.type;
    }

    @Override
    public int compareTo(Region region) {
        return this.id.compareTo(region.id);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((Region)object);
    }

    public static enum RegionType {
        UNKNOWN,
        TERRITORY,
        WORLD,
        CONTINENT,
        SUBCONTINENT,
        GROUPING,
        DEPRECATED;

    }
}

