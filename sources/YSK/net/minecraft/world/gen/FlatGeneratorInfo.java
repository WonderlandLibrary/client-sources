package net.minecraft.world.gen;

import net.minecraft.world.biome.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

public class FlatGeneratorInfo
{
    private int biomeToUse;
    private static final String[] I;
    private final List<FlatLayerInfo> flatLayers;
    private final Map<String, Map<String, String>> worldFeatures;
    
    public static FlatGeneratorInfo getDefaultFlatGenerator() {
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        flatGeneratorInfo.setBiome(BiomeGenBase.plains.biomeID);
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(" ".length(), Blocks.bedrock));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo("  ".length(), Blocks.dirt));
        flatGeneratorInfo.getFlatLayers().add(new FlatLayerInfo(" ".length(), Blocks.grass));
        flatGeneratorInfo.func_82645_d();
        flatGeneratorInfo.getWorldFeatures().put(FlatGeneratorInfo.I[0x6C ^ 0x7B], Maps.newHashMap());
        return flatGeneratorInfo;
    }
    
    private static List<FlatLayerInfo> func_180716_a(final int n, final String s) {
        if (s == null || s.length() < " ".length()) {
            return null;
        }
        final ArrayList arrayList = Lists.newArrayList();
        final String[] split = s.split(FlatGeneratorInfo.I[0x21 ^ 0x2E]);
        int length = "".length();
        final String[] array;
        final int length2 = (array = split).length;
        int i = "".length();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (i < length2) {
            final FlatLayerInfo func_180715_a = func_180715_a(n, array[i], length);
            if (func_180715_a == null) {
                return null;
            }
            arrayList.add(func_180715_a);
            length += func_180715_a.getLayerCount();
            ++i;
        }
        return (List<FlatLayerInfo>)arrayList;
    }
    
    public Map<String, Map<String, String>> getWorldFeatures() {
        return this.worldFeatures;
    }
    
    public static FlatGeneratorInfo createFlatGeneratorFromString(final String s) {
        if (s == null) {
            return getDefaultFlatGenerator();
        }
        final String[] split = s.split(FlatGeneratorInfo.I[0x2F ^ 0x3F], -" ".length());
        int n;
        if (split.length == " ".length()) {
            n = "".length();
            "".length();
            if (1 == 4) {
                throw null;
            }
        }
        else {
            n = MathHelper.parseIntWithDefault(split["".length()], "".length());
        }
        final int n2 = n;
        if (n2 < 0 || n2 > "   ".length()) {
            return getDefaultFlatGenerator();
        }
        final FlatGeneratorInfo flatGeneratorInfo = new FlatGeneratorInfo();
        int n3;
        if (split.length == " ".length()) {
            n3 = "".length();
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n3 = " ".length();
        }
        int n4 = n3;
        final List<FlatLayerInfo> func_180716_a = func_180716_a(n2, split[n4++]);
        if (func_180716_a != null && !func_180716_a.isEmpty()) {
            flatGeneratorInfo.getFlatLayers().addAll(func_180716_a);
            flatGeneratorInfo.func_82645_d();
            int biome = BiomeGenBase.plains.biomeID;
            if (n2 > 0 && split.length > n4) {
                biome = MathHelper.parseIntWithDefault(split[n4++], biome);
            }
            flatGeneratorInfo.setBiome(biome);
            if (n2 > 0 && split.length > n4) {
                final String[] split2;
                final int length = (split2 = split[n4++].toLowerCase().split(FlatGeneratorInfo.I[0x47 ^ 0x56])).length;
                int i = "".length();
                "".length();
                if (-1 == 2) {
                    throw null;
                }
                while (i < length) {
                    final String[] split3 = split2[i].split(FlatGeneratorInfo.I[0x65 ^ 0x77], "  ".length());
                    final HashMap hashMap = Maps.newHashMap();
                    if (split3["".length()].length() > 0) {
                        flatGeneratorInfo.getWorldFeatures().put(split3["".length()], hashMap);
                        if (split3.length > " ".length() && split3[" ".length()].endsWith(FlatGeneratorInfo.I[0x20 ^ 0x33]) && split3[" ".length()].length() > " ".length()) {
                            final String[] split4 = split3[" ".length()].substring("".length(), split3[" ".length()].length() - " ".length()).split(FlatGeneratorInfo.I[0x7E ^ 0x6A]);
                            int j = "".length();
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                            while (j < split4.length) {
                                final String[] split5 = split4[j].split(FlatGeneratorInfo.I[0xA6 ^ 0xB3], "  ".length());
                                if (split5.length == "  ".length()) {
                                    hashMap.put(split5["".length()], split5[" ".length()]);
                                }
                                ++j;
                            }
                        }
                    }
                    ++i;
                }
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                flatGeneratorInfo.getWorldFeatures().put(FlatGeneratorInfo.I[0x68 ^ 0x7E], Maps.newHashMap());
            }
            return flatGeneratorInfo;
        }
        return getDefaultFlatGenerator();
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("   ".length());
        sb.append(FlatGeneratorInfo.I["".length()]);
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < this.flatLayers.size()) {
            if (i > 0) {
                sb.append(FlatGeneratorInfo.I[" ".length()]);
            }
            sb.append(this.flatLayers.get(i).toString());
            ++i;
        }
        sb.append(FlatGeneratorInfo.I["  ".length()]);
        sb.append(this.biomeToUse);
        if (!this.worldFeatures.isEmpty()) {
            sb.append(FlatGeneratorInfo.I["   ".length()]);
            int length = "".length();
            final Iterator<Map.Entry<String, Map<String, String>>> iterator = this.worldFeatures.entrySet().iterator();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (iterator.hasNext()) {
                final Map.Entry<String, Map<String, String>> entry = iterator.next();
                if (length++ > 0) {
                    sb.append(FlatGeneratorInfo.I[0x4C ^ 0x48]);
                }
                sb.append(entry.getKey().toLowerCase());
                final Map<String, String> map = entry.getValue();
                if (!map.isEmpty()) {
                    sb.append(FlatGeneratorInfo.I[0x85 ^ 0x80]);
                    int length2 = "".length();
                    final Iterator<Map.Entry<String, String>> iterator2 = map.entrySet().iterator();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (iterator2.hasNext()) {
                        final Map.Entry<String, String> entry2 = iterator2.next();
                        if (length2++ > 0) {
                            sb.append(FlatGeneratorInfo.I[0x6E ^ 0x68]);
                        }
                        sb.append(entry2.getKey());
                        sb.append(FlatGeneratorInfo.I[0x5D ^ 0x5A]);
                        sb.append(entry2.getValue());
                    }
                    sb.append(FlatGeneratorInfo.I[0xB6 ^ 0xBE]);
                }
            }
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            sb.append(FlatGeneratorInfo.I[0x24 ^ 0x2D]);
        }
        return sb.toString();
    }
    
    private static FlatLayerInfo func_180715_a(final int n, final String s, final int minY) {
        String[] array;
        if (n >= "   ".length()) {
            array = s.split(FlatGeneratorInfo.I[0xA4 ^ 0xAE], "  ".length());
            "".length();
            if (0 == 2) {
                throw null;
            }
        }
        else {
            array = s.split(FlatGeneratorInfo.I[0x83 ^ 0x88], "  ".length());
        }
        final String[] array2 = array;
        int n2 = " ".length();
        int n3 = "".length();
        if (array2.length == "  ".length()) {
            try {
                n2 = Integer.parseInt(array2["".length()]);
                if (minY + n2 >= 180 + 34 - 138 + 180) {
                    n2 = 37 + 172 - 43 + 90 - minY;
                }
                if (n2 < 0) {
                    n2 = "".length();
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
            }
            catch (Throwable t) {
                return null;
            }
        }
        Block block;
        try {
            final String s2 = array2[array2.length - " ".length()];
            if (n < "   ".length()) {
                final String[] split = s2.split(FlatGeneratorInfo.I[0x30 ^ 0x3C], "  ".length());
                if (split.length > " ".length()) {
                    n3 = Integer.parseInt(split[" ".length()]);
                }
                block = Block.getBlockById(Integer.parseInt(split["".length()]));
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                final String[] split2 = s2.split(FlatGeneratorInfo.I[0x5C ^ 0x51], "   ".length());
                Block blockFromName;
                if (split2.length > " ".length()) {
                    blockFromName = Block.getBlockFromName(String.valueOf(split2["".length()]) + FlatGeneratorInfo.I[0xAC ^ 0xA2] + split2[" ".length()]);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    blockFromName = null;
                }
                block = blockFromName;
                if (block != null) {
                    int n4;
                    if (split2.length > "  ".length()) {
                        n4 = Integer.parseInt(split2["  ".length()]);
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n4 = "".length();
                    }
                    n3 = n4;
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    block = Block.getBlockFromName(split2["".length()]);
                    if (block != null) {
                        int n5;
                        if (split2.length > " ".length()) {
                            n5 = Integer.parseInt(split2[" ".length()]);
                            "".length();
                            if (0 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n5 = "".length();
                        }
                        n3 = n5;
                    }
                }
                if (block == null) {
                    return null;
                }
            }
            if (block == Blocks.air) {
                n3 = "".length();
            }
            if (n3 < 0 || n3 > (0x28 ^ 0x27)) {
                n3 = "".length();
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
        }
        catch (Throwable t2) {
            return null;
        }
        final FlatLayerInfo flatLayerInfo = new FlatLayerInfo(n, n2, block, n3);
        flatLayerInfo.setMinY(minY);
        return flatLayerInfo;
    }
    
    public void setBiome(final int biomeToUse) {
        this.biomeToUse = biomeToUse;
    }
    
    private static void I() {
        (I = new String[0xBE ^ 0xA6])["".length()] = I("T", "oDPcX");
        FlatGeneratorInfo.I[" ".length()] = I("O", "cUMfB");
        FlatGeneratorInfo.I["  ".length()] = I("v", "MdHcd");
        FlatGeneratorInfo.I["   ".length()] = I("T", "oGYJW");
        FlatGeneratorInfo.I[0x9 ^ 0xD] = I("y", "UfNRq");
        FlatGeneratorInfo.I[0xC7 ^ 0xC2] = I("]", "uYrCE");
        FlatGeneratorInfo.I[0x8D ^ 0x8B] = I("q", "QyNjT");
        FlatGeneratorInfo.I[0xC1 ^ 0xC6] = I("{", "FQIpO");
        FlatGeneratorInfo.I[0xB8 ^ 0xB0] = I("G", "nTQVG");
        FlatGeneratorInfo.I[0x38 ^ 0x31] = I("H", "svICh");
        FlatGeneratorInfo.I[0x9 ^ 0x3] = I("\u0019x", "ERqsv");
        FlatGeneratorInfo.I[0xAC ^ 0xA7] = I("3", "KPrTw");
        FlatGeneratorInfo.I[0xBD ^ 0xB1] = I("r", "HnHcT");
        FlatGeneratorInfo.I[0x21 ^ 0x2C] = I("W", "mjXxv");
        FlatGeneratorInfo.I[0x6F ^ 0x61] = I("K", "qovxo");
        FlatGeneratorInfo.I[0x5D ^ 0x52] = I("F", "jWLlx");
        FlatGeneratorInfo.I[0xB7 ^ 0xA7] = I("o", "TTxXo");
        FlatGeneratorInfo.I[0x9A ^ 0x8B] = I("f", "JsxYL");
        FlatGeneratorInfo.I[0x5E ^ 0x4C] = I("8M", "deUPa");
        FlatGeneratorInfo.I[0x20 ^ 0x33] = I("A", "hxVmK");
        FlatGeneratorInfo.I[0x3D ^ 0x29] = I("B", "brPSh");
        FlatGeneratorInfo.I[0x8C ^ 0x99] = I("^", "ctFwN");
        FlatGeneratorInfo.I[0x7D ^ 0x6B] = I("$\u000b\u001a\u001a\u00175\u0007", "Rbvvv");
        FlatGeneratorInfo.I[0x55 ^ 0x42] = I("\u0017\u000f\t?\u0017\u0006\u0003", "afeSv");
    }
    
    public void func_82645_d() {
        int length = "".length();
        final Iterator<FlatLayerInfo> iterator = this.flatLayers.iterator();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final FlatLayerInfo flatLayerInfo = iterator.next();
            flatLayerInfo.setMinY(length);
            length += flatLayerInfo.getLayerCount();
        }
    }
    
    static {
        I();
    }
    
    public FlatGeneratorInfo() {
        this.flatLayers = (List<FlatLayerInfo>)Lists.newArrayList();
        this.worldFeatures = (Map<String, Map<String, String>>)Maps.newHashMap();
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int getBiome() {
        return this.biomeToUse;
    }
    
    public List<FlatLayerInfo> getFlatLayers() {
        return this.flatLayers;
    }
}
