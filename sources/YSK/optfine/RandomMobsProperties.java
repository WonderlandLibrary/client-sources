package optfine;

import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.world.biome.*;

public class RandomMobsProperties
{
    private static final String[] I;
    public RandomMobsRule[] rules;
    public String basePath;
    public ResourceLocation[] resourceLocations;
    public String name;
    
    public RandomMobsProperties(final Properties properties, final String s, final ResourceLocation resourceLocation) {
        this.name = null;
        this.basePath = null;
        this.resourceLocations = null;
        this.rules = null;
        final ConnectedParser connectedParser = new ConnectedParser(RandomMobsProperties.I[" ".length()]);
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.rules = this.parseRules(properties, resourceLocation, connectedParser);
    }
    
    private RangeListInt parseMinMaxHeight(final Properties properties, final int n) {
        final String property = properties.getProperty(RandomMobsProperties.I[0x46 ^ 0x40] + n);
        final String property2 = properties.getProperty(RandomMobsProperties.I[0x10 ^ 0x17] + n);
        if (property == null && property2 == null) {
            return null;
        }
        int n2 = "".length();
        if (property != null) {
            n2 = Config.parseInt(property, -" ".length());
            if (n2 < 0) {
                Config.warn(RandomMobsProperties.I[0x0 ^ 0x8] + property);
                return null;
            }
        }
        int int1 = 31 + 253 - 110 + 82;
        if (property2 != null) {
            int1 = Config.parseInt(property2, -" ".length());
            if (int1 < 0) {
                Config.warn(RandomMobsProperties.I[0xB6 ^ 0xBF] + property2);
                return null;
            }
        }
        if (int1 < 0) {
            Config.warn(RandomMobsProperties.I[0x35 ^ 0x3F] + property + RandomMobsProperties.I[0x5D ^ 0x56] + property2);
            return null;
        }
        final RangeListInt rangeListInt = new RangeListInt();
        rangeListInt.addRange(new RangeInt(n2, int1));
        return rangeListInt;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ResourceLocation getTextureLocation(final ResourceLocation resourceLocation, final EntityLiving entityLiving) {
        if (this.rules != null) {
            int i = "".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            while (i < this.rules.length) {
                final RandomMobsRule randomMobsRule = this.rules[i];
                if (randomMobsRule.matches(entityLiving)) {
                    return randomMobsRule.getTextureLocation(resourceLocation, entityLiving.randomMobsId);
                }
                ++i;
            }
        }
        if (this.resourceLocations != null) {
            return this.resourceLocations[entityLiving.randomMobsId % this.resourceLocations.length];
        }
        return resourceLocation;
    }
    
    public RandomMobsProperties(final String s, final ResourceLocation[] resourceLocations) {
        this.name = null;
        this.basePath = null;
        this.resourceLocations = null;
        this.rules = null;
        final ConnectedParser connectedParser = new ConnectedParser(RandomMobsProperties.I["".length()]);
        this.name = connectedParser.parseName(s);
        this.basePath = connectedParser.parseBasePath(s);
        this.resourceLocations = resourceLocations;
    }
    
    static {
        I();
    }
    
    private RandomMobsRule[] parseRules(final Properties properties, final ResourceLocation resourceLocation, final ConnectedParser connectedParser) {
        final ArrayList<RandomMobsRule> list = new ArrayList<RandomMobsRule>();
        final int size = properties.size();
        int i = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (i < size) {
            final int n = i + " ".length();
            final String property = properties.getProperty(RandomMobsProperties.I["  ".length()] + n);
            if (property != null) {
                final int[] intList = connectedParser.parseIntList(property);
                final int[] intList2 = connectedParser.parseIntList(properties.getProperty(RandomMobsProperties.I["   ".length()] + n));
                final BiomeGenBase[] biomes = connectedParser.parseBiomes(properties.getProperty(RandomMobsProperties.I[0x37 ^ 0x33] + n));
                RangeListInt rangeListInt = connectedParser.parseRangeListInt(properties.getProperty(RandomMobsProperties.I[0x1B ^ 0x1E] + n));
                if (rangeListInt == null) {
                    rangeListInt = this.parseMinMaxHeight(properties, n);
                }
                list.add(new RandomMobsRule(resourceLocation, intList, intList2, biomes, rangeListInt));
            }
            ++i;
        }
        return list.toArray(new RandomMobsRule[list.size()]);
    }
    
    private static void I() {
        (I = new String[0x32 ^ 0x3C])["".length()] = I("\u000b\u001b=&\u001d47< \u0001", "YzSBr");
        RandomMobsProperties.I[" ".length()] = I("\u0006\u00056\r,9)7\u000b0", "TdXiC");
        RandomMobsProperties.I["  ".length()] = I("2\u001f\u000f\u00016o", "AtfoE");
        RandomMobsProperties.I["   ".length()] = I("3$*%.02m", "DACBF");
        RandomMobsProperties.I[0x5B ^ 0x5F] = I(":\u000f,9-+H", "XfCTH");
        RandomMobsProperties.I[0xA3 ^ 0xA6] = I("\u0012\u0015.)\u001d\u000e\u0003i", "zpGNu");
        RandomMobsProperties.I[0x23 ^ 0x25] = I("/\u001c&\u0003&+\u0012 ?m", "BuHKC");
        RandomMobsProperties.I[0x2C ^ 0x2B] = I("\b6)!\u0004\f09\u001dO", "eWQia");
        RandomMobsProperties.I[0x20 ^ 0x28] = I("\u0007\u0007\u0012\u0006/'\rD\n* !\u0001\u000e$&\u001d^G", "NidgC");
        RandomMobsProperties.I[0xA2 ^ 0xAB] = I("3;> \n\u00131h,\u0007\u0002\u001d-(\u0001\u0012!ra", "zUHAf");
        RandomMobsProperties.I[0x3 ^ 0x9] = I("*\t\u00047.\n\u0003R;+\r/\u0017?%\u000b\u0013^v/\u0002\u001f:3+\u0004\u000f\u0006lb", "cgrVB");
        RandomMobsProperties.I[0x17 ^ 0x1C] = I("MG", "agKmN");
        RandomMobsProperties.I[0x78 ^ 0x74] = I("?&F'\u0006\u0018'\u0015t\u001e\u0001,\u0005=\u000b\u0018,\u0002nM", "qIfTm");
        RandomMobsProperties.I[0x4E ^ 0x43] = I("$+=\u0011\u001f\u0002+e\u000b\u0005\u0004n#\n\u001f\u001e*\u007fE", "pNEej");
    }
    
    public boolean isValid(final String s) {
        if (this.resourceLocations == null && this.rules == null) {
            Config.warn(RandomMobsProperties.I[0xB6 ^ 0xBA] + s);
            return "".length() != 0;
        }
        if (this.rules != null) {
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < this.rules.length) {
                if (!this.rules[i].isValid(s)) {
                    return "".length() != 0;
                }
                ++i;
            }
        }
        if (this.resourceLocations != null) {
            int j = "".length();
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (j < this.resourceLocations.length) {
                final ResourceLocation resourceLocation = this.resourceLocations[j];
                if (!Config.hasResource(resourceLocation)) {
                    Config.warn(RandomMobsProperties.I[0x9 ^ 0x4] + resourceLocation.getResourcePath());
                    return "".length() != 0;
                }
                ++j;
            }
        }
        return " ".length() != 0;
    }
}
