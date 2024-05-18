package optfine;

import java.io.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.state.*;

public class ConnectedParser
{
    private static final String[] I;
    private String context;
    
    public RangeListInt parseRangeListInt(final String s) {
        if (s == null) {
            return null;
        }
        final RangeListInt rangeListInt = new RangeListInt();
        final String[] tokenize = Config.tokenize(s, ConnectedParser.I[0xAF ^ 0x81]);
        int i = "".length();
        "".length();
        if (1 >= 4) {
            throw null;
        }
        while (i < tokenize.length) {
            final RangeInt rangeInt = this.parseRangeInt(tokenize[i]);
            if (rangeInt == null) {
                return null;
            }
            rangeListInt.addRange(rangeInt);
            ++i;
        }
        return rangeListInt;
    }
    
    public MatchBlock[] parseMatchBlocks(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList list = new ArrayList();
        final String[] tokenize = Config.tokenize(s, ConnectedParser.I[" ".length()]);
        int i = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (i < tokenize.length) {
            final MatchBlock[] matchBlock = this.parseMatchBlock(tokenize[i]);
            if (matchBlock == null) {
                return null;
            }
            list.addAll(Arrays.asList(matchBlock));
            ++i;
        }
        return (MatchBlock[])list.toArray(new MatchBlock[list.size()]);
    }
    
    public boolean isFullBlockName(final String[] array) {
        if (array.length < "  ".length()) {
            return "".length() != 0;
        }
        final String s = array[" ".length()];
        int n;
        if (s.length() < " ".length()) {
            n = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this.startsWithDigit(s)) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (s.contains(ConnectedParser.I[0xB8 ^ 0xBD])) {
            n = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public static Comparable getPropertyValue(final String s, final Collection collection) {
        final Iterator<Comparable> iterator = collection.iterator();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Comparable next = iterator.next();
            if (String.valueOf(next).equals(s)) {
                return next;
            }
        }
        return null;
    }
    
    public ConnectedParser(final String context) {
        this.context = null;
        this.context = context;
    }
    
    private RangeInt parseRangeInt(final String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(0xEE ^ 0xC3) >= 0) {
            final String[] tokenize = Config.tokenize(s, ConnectedParser.I[0x6E ^ 0x41]);
            if (tokenize.length != "  ".length()) {
                this.warn(ConnectedParser.I[0x47 ^ 0x77] + s);
                return null;
            }
            final int int1 = Config.parseInt(tokenize["".length()], -" ".length());
            final int int2 = Config.parseInt(tokenize[" ".length()], -" ".length());
            if (int1 >= 0 && int2 >= 0) {
                return new RangeInt(int1, int2);
            }
            this.warn(ConnectedParser.I[0x4 ^ 0x35] + s);
            return null;
        }
        else {
            final int int3 = Config.parseInt(s, -" ".length());
            if (int3 < 0) {
                this.warn(ConnectedParser.I[0x46 ^ 0x74] + s);
                return null;
            }
            return new RangeInt(int3, int3);
        }
    }
    
    public String parseBasePath(final String s) {
        final int lastIndex = s.lastIndexOf(0xAF ^ 0x80);
        String substring;
        if (lastIndex < 0) {
            substring = ConnectedParser.I["".length()];
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            substring = s.substring("".length(), lastIndex);
        }
        return substring;
    }
    
    static {
        I();
    }
    
    public static Comparable parseValue(final String s, final Class clazz) {
        Serializable s2;
        if (clazz == String.class) {
            s2 = s;
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else if (clazz == Boolean.class) {
            s2 = Boolean.valueOf(s);
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            double doubleValue;
            if (clazz == Float.class) {
                doubleValue = Float.valueOf(s);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            else if (clazz == Double.class) {
                doubleValue = Double.valueOf(s);
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                long longValue;
                if (clazz == Integer.class) {
                    longValue = Integer.valueOf(s);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    Long value;
                    if (clazz == Long.class) {
                        value = Long.valueOf(s);
                        "".length();
                        if (3 == 2) {
                            throw null;
                        }
                    }
                    else {
                        value = null;
                    }
                    longValue = value;
                }
                doubleValue = longValue;
            }
            s2 = doubleValue;
        }
        return (Comparable)s2;
    }
    
    public int[] parseIntList(final String s) {
        if (s == null) {
            return null;
        }
        final ArrayList<Integer> list = new ArrayList<Integer>();
        final String[] tokenize = Config.tokenize(s, ConnectedParser.I[0x2 ^ 0x15]);
        int i = "".length();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            if (s2.contains(ConnectedParser.I[0x93 ^ 0x8B])) {
                final String[] tokenize2 = Config.tokenize(s2, ConnectedParser.I[0x48 ^ 0x51]);
                if (tokenize2.length != "  ".length()) {
                    this.warn(ConnectedParser.I[0x46 ^ 0x5C] + s2 + ConnectedParser.I[0x3C ^ 0x27] + s);
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                }
                else {
                    final int int1 = Config.parseInt(tokenize2["".length()], -" ".length());
                    final int int2 = Config.parseInt(tokenize2[" ".length()], -" ".length());
                    if (int1 >= 0 && int2 >= 0 && int1 <= int2) {
                        int j = int1;
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (j <= int2) {
                            list.add(j);
                            ++j;
                        }
                        "".length();
                        if (2 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        this.warn(ConnectedParser.I[0xAA ^ 0xB6] + s2 + ConnectedParser.I[0x46 ^ 0x5B] + s);
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                    }
                }
            }
            else {
                final int int3 = Config.parseInt(s2, -" ".length());
                if (int3 < 0) {
                    this.warn(ConnectedParser.I[0x31 ^ 0x2F] + s2 + ConnectedParser.I[0x14 ^ 0xB] + s);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
                else {
                    list.add(int3);
                }
            }
            ++i;
        }
        final int[] array = new int[list.size()];
        int k = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (k < array.length) {
            array[k] = (int)list.get(k);
            ++k;
        }
        return array;
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public int[] parseBlockMetadatas(final Block block, final String[] array) {
        if (array.length <= 0) {
            return null;
        }
        final String s = array["".length()];
        if (this.startsWithDigit(s)) {
            return this.parseIntList(s);
        }
        final Collection<IProperty> propertyNames = block.getDefaultState().getPropertyNames();
        final HashMap<IProperty, List<?>> hashMap = (HashMap<IProperty, List<?>>)new HashMap<Object, List<?>>();
        int i = "".length();
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i < array.length) {
            final String s2 = array[i];
            if (s2.length() > 0) {
                final String[] tokenize = Config.tokenize(s2, ConnectedParser.I[0x65 ^ 0x6C]);
                if (tokenize.length != "  ".length()) {
                    this.warn(ConnectedParser.I[0x2B ^ 0x21] + s2);
                    return null;
                }
                final String s3 = tokenize["".length()];
                final String s4 = tokenize[" ".length()];
                final IProperty property = ConnectedProperties.getProperty(s3, propertyNames);
                if (property == null) {
                    this.warn(ConnectedParser.I[0xF ^ 0x4] + s3 + ConnectedParser.I[0x9D ^ 0x91] + block);
                    return null;
                }
                List<?> list = hashMap.get(s3);
                if (list == null) {
                    list = new ArrayList<Object>();
                    hashMap.put(property, list);
                }
                final String[] tokenize2 = Config.tokenize(s4, ConnectedParser.I[0x7A ^ 0x77]);
                int j = "".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                while (j < tokenize2.length) {
                    final String s5 = tokenize2[j];
                    final Comparable propertyValue = parsePropertyValue(property, s5);
                    if (propertyValue == null) {
                        this.warn(ConnectedParser.I[0x15 ^ 0x1B] + s5 + ConnectedParser.I[0x7E ^ 0x71] + s3 + ConnectedParser.I[0xAC ^ 0xBC] + block);
                        return null;
                    }
                    list.add(propertyValue);
                    ++j;
                }
            }
            ++i;
        }
        if (hashMap.isEmpty()) {
            return null;
        }
        final ArrayList<Integer> list2 = new ArrayList<Integer>();
        int k = "".length();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (k < (0x2F ^ 0x3F)) {
            if (this.matchState(block.getStateFromMeta(k), hashMap)) {
                list2.add(k);
            }
            ++k;
        }
        if (list2.size() == (0x2B ^ 0x3B)) {
            return null;
        }
        final int[] array2 = new int[list2.size()];
        int l = "".length();
        "".length();
        if (4 < 0) {
            throw null;
        }
        while (l < array2.length) {
            array2[l] = (int)list2.get(l);
            ++l;
        }
        return array2;
    }
    
    public int parseInt(final String s, final int n) {
        if (s == null) {
            return n;
        }
        final int int1 = Config.parseInt(s, -" ".length());
        if (int1 < 0) {
            this.warn(ConnectedParser.I[0x28 ^ 0x3E] + s);
            return n;
        }
        return int1;
    }
    
    public String parseName(final String s) {
        String s2 = s;
        final int lastIndex = s.lastIndexOf(0x15 ^ 0x3A);
        if (lastIndex >= 0) {
            s2 = s.substring(lastIndex + " ".length());
        }
        final int lastIndex2 = s2.lastIndexOf(0x29 ^ 0x7);
        if (lastIndex2 >= 0) {
            s2 = s2.substring("".length(), lastIndex2);
        }
        return s2;
    }
    
    public boolean startsWithDigit(final String s) {
        if (s == null) {
            return "".length() != 0;
        }
        if (s.length() < " ".length()) {
            return "".length() != 0;
        }
        return Character.isDigit(s.charAt("".length()));
    }
    
    public void dbg(final String s) {
        Config.dbg(this.context + ConnectedParser.I[0x81 ^ 0xAD] + s);
    }
    
    public static Comparable parsePropertyValue(final IProperty property, final String s) {
        Comparable comparable = parseValue(s, property.getValueClass());
        if (comparable == null) {
            comparable = getPropertyValue(s, property.getAllowedValues());
        }
        return comparable;
    }
    
    public boolean[] parseFaces(final String s, final boolean[] array) {
        if (s == null) {
            return array;
        }
        final EnumSet<EnumFacing> all = EnumSet.allOf(EnumFacing.class);
        final String[] tokenize = Config.tokenize(s, ConnectedParser.I[0x6B ^ 0x4B]);
        int i = "".length();
        "".length();
        if (4 < 2) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            if (s2.equals(ConnectedParser.I[0xE6 ^ 0xC7])) {
                all.add(EnumFacing.NORTH);
                all.add(EnumFacing.SOUTH);
                all.add(EnumFacing.WEST);
                all.add(EnumFacing.EAST);
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else if (s2.equals(ConnectedParser.I[0x85 ^ 0xA7])) {
                all.addAll((Collection<?>)Arrays.asList(EnumFacing.VALUES));
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                final EnumFacing face = this.parseFace(s2);
                if (face != null) {
                    all.add(face);
                }
            }
            ++i;
        }
        final boolean[] array2 = new boolean[EnumFacing.VALUES.length];
        int j = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (j < array2.length) {
            array2[j] = all.contains(EnumFacing.VALUES[j]);
            ++j;
        }
        return array2;
    }
    
    public Block[] parseBlockPart(final String s, final String s2) {
        if (this.startsWithDigit(s2)) {
            final int[] intList = this.parseIntList(s2);
            if (intList == null) {
                return null;
            }
            final Block[] array = new Block[intList.length];
            int i = "".length();
            "".length();
            if (2 == 4) {
                throw null;
            }
            while (i < intList.length) {
                final int n = intList[i];
                final Block blockById = Block.getBlockById(n);
                if (blockById == null) {
                    this.warn(ConnectedParser.I[0x5F ^ 0x59] + n);
                    return null;
                }
                array[i] = blockById;
                ++i;
            }
            return array;
        }
        else {
            final String string = String.valueOf(s) + ConnectedParser.I[0x2C ^ 0x2B] + s2;
            final Block blockFromName = Block.getBlockFromName(string);
            if (blockFromName == null) {
                this.warn(ConnectedParser.I[0xAC ^ 0xA4] + string);
                return null;
            }
            final Block[] array2 = new Block[" ".length()];
            array2["".length()] = blockFromName;
            return array2;
        }
    }
    
    public int parseInt(final String s) {
        if (s == null) {
            return -" ".length();
        }
        final int int1 = Config.parseInt(s, -" ".length());
        if (int1 < 0) {
            this.warn(ConnectedParser.I[0x41 ^ 0x54] + s);
        }
        return int1;
    }
    
    private static void I() {
        (I = new String[0x53 ^ 0x60])["".length()] = I("", "UcMMM");
        ConnectedParser.I[" ".length()] = I("k", "KItPV");
        ConnectedParser.I["  ".length()] = I("Q", "kGZBk");
        ConnectedParser.I["   ".length()] = I("\u000f\u00018\t\u0012\u0010\t0\u0018", "bhVlq");
        ConnectedParser.I[0x7A ^ 0x7E] = I(",;\u001c\u0013\u001033\u0014\u0002", "ARrvs");
        ConnectedParser.I[0x73 ^ 0x76] = I("N", "sbkyn");
        ConnectedParser.I[0x5E ^ 0x58] = I("3\u0003\u001c!8Q\u0001\u001c6s\u0017\u0000\u0006,7Q\t\u001c0s\u0018\u000bIb", "qosBS");
        ConnectedParser.I[0xAA ^ 0xAD] = I("s", "IjirY");
        ConnectedParser.I[0x69 ^ 0x61] = I("1\n.9\nS\b..A\u0015\t44\u0005S\u0000.(A\u001d\u0007,?[S", "sfAZa");
        ConnectedParser.I[0x6C ^ 0x65] = I("m", "PczJA");
        ConnectedParser.I[0x2C ^ 0x26] = I(".\u001b\u0003\u00186\u000e\u0011U\u001b6\b\u0016\u001eY*\u0015\u001a\u0005\u001c(\u0013\fOY", "guuyZ");
        ConnectedParser.I[0x3B ^ 0x30] = I("\u001c7\u0017$\u0006>1\u0001t\r#1X2\f9+\u001cnC", "LExTc");
        ConnectedParser.I[0xB6 ^ 0xBA] = I("fV\u0011?\u0017)\u001dIs", "JvsSx");
        ConnectedParser.I[0xC ^ 0x1] = I("t", "XTqFJ");
        ConnectedParser.I[0xCD ^ 0xC3] = I("\u0001=.(&#;8x50#4=c? 5x%>:/<yq", "QOAXC");
        ConnectedParser.I[0x86 ^ 0x89] = I("^W\u0004\u0011:\u0002\u0012\u0006\u0017,HW", "rwtcU");
        ConnectedParser.I[0x6 ^ 0x16] = I("iB,\u0016\u000b&\ttZ", "EbNzd");
        ConnectedParser.I[0xBC ^ 0xAD] = I("l", "LufYe");
        ConnectedParser.I[0x63 ^ 0x71] = I("/\b\u0019!\u0000M\u000f\u00198E\u000b\u000e\u0003\"\u0001WA", "mavLe");
        ConnectedParser.I[0x87 ^ 0x94] = I("X", "xILAH");
        ConnectedParser.I[0x34 ^ 0x20] = I("", "DpLvC");
        ConnectedParser.I[0xBD ^ 0xA8] = I(",7\u00120:\f=D?#\b;\u0001#lE", "eYdQV");
        ConnectedParser.I[0xD2 ^ 0xC4] = I("\u0002+\u0014\n\u0006\"!B\u0005\u001f&'\u0007\u0019Pk", "KEbkj");
        ConnectedParser.I[0x26 ^ 0x31] = I("jF", "JjDsx");
        ConnectedParser.I[0x4D ^ 0x55] = I("|", "QboRM");
        ConnectedParser.I[0x90 ^ 0x89] = I("^", "serUU");
        ConnectedParser.I[0x8C ^ 0x96] = I("\u0000)$55 #r=7=\" \"8%}r", "IGRTY");
        ConnectedParser.I[0x7F ^ 0x64] = I("dA\u0011\u0001\f&A\u0016\b\u001b;\b\b\u000eSh", "Hafii");
        ConnectedParser.I[0xB3 ^ 0xAF] = I("\u00007/(\b =y \n=<+?\u0005%cy", "IYYId");
        ConnectedParser.I[0x88 ^ 0x95] = I("cd \u0005\u001f!d'\f\b<-9\n@o", "ODWmz");
        ConnectedParser.I[0x9F ^ 0x81] = I("!\u001d\u0013/:\u0001\u0017E #\u0005\u0011\u0000<lH", "hseNV");
        ConnectedParser.I[0x8B ^ 0x94] = I("Ha\u0006,\u0003\na\u0001%\u0014\u0017(\u001f#\\D", "dAqDf");
        ConnectedParser.I[0xBB ^ 0x9B] = I("DI", "deZrL");
        ConnectedParser.I[0x3E ^ 0x1F] = I("\u0006-\u001d\u0001\u0006", "uDydu");
        ConnectedParser.I[0x96 ^ 0xB4] = I("\"\u00066", "CjZqx");
        ConnectedParser.I[0x81 ^ 0xA2] = I("7\u0002\u00119-8", "UmeMB");
        ConnectedParser.I[0x42 ^ 0x66] = I("=?\u001d/", "YPjAL");
        ConnectedParser.I[0x6F ^ 0x4A] = I("\r\u001c\u0016", "ysfLE");
        ConnectedParser.I[0x49 ^ 0x6F] = I("'2", "RBwqV");
        ConnectedParser.I[0x20 ^ 0x7] = I("*\f#:9", "DcQNQ");
        ConnectedParser.I[0x3C ^ 0x14] = I(" +&$\u0002", "SDSPj");
        ConnectedParser.I[0x67 ^ 0x4E] = I("\u0010\u0000\u001f ", "ualTG");
        ConnectedParser.I[0x9B ^ 0xB1] = I(">\u0012\u0015\u0002", "IwfvS");
        ConnectedParser.I[0x89 ^ 0xA2] = I("\u0013\n!=\u00151\nj5\u001b%\u0001ps", "FdJSz");
        ConnectedParser.I[0x4C ^ 0x60] = I("tO", "NoZpk");
        ConnectedParser.I[0xB8 ^ 0x95] = I("ua", "OAeCH");
        ConnectedParser.I[0x4F ^ 0x61] = I("WD", "whqfm");
        ConnectedParser.I[0x52 ^ 0x7D] = I("y", "TVGxZ");
        ConnectedParser.I[0x20 ^ 0x10] = I(".\u0005 \u0017\u0003\u000e\u000fv\u0004\u000e\t\f3LO", "gkVvo");
        ConnectedParser.I[0x89 ^ 0xB8] = I("\u0001\r\u0004)$!\u0007R:)&\u0004\u0017rh", "HcrHH");
        ConnectedParser.I[0xF3 ^ 0xC1] = I("<?1,\b\u001c5g$\n\u00014 (\u0016Oq", "uQGMd");
    }
    
    public EnumFacing parseFace(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        if (lowerCase.equals(ConnectedParser.I[0x5C ^ 0x7F]) || lowerCase.equals(ConnectedParser.I[0x1E ^ 0x3A])) {
            return EnumFacing.DOWN;
        }
        if (lowerCase.equals(ConnectedParser.I[0x42 ^ 0x67]) || lowerCase.equals(ConnectedParser.I[0x13 ^ 0x35])) {
            return EnumFacing.UP;
        }
        if (lowerCase.equals(ConnectedParser.I[0x58 ^ 0x7F])) {
            return EnumFacing.NORTH;
        }
        if (lowerCase.equals(ConnectedParser.I[0x6E ^ 0x46])) {
            return EnumFacing.SOUTH;
        }
        if (lowerCase.equals(ConnectedParser.I[0x89 ^ 0xA0])) {
            return EnumFacing.EAST;
        }
        if (lowerCase.equals(ConnectedParser.I[0x29 ^ 0x3])) {
            return EnumFacing.WEST;
        }
        Config.warn(ConnectedParser.I[0x86 ^ 0xAD] + lowerCase);
        return null;
    }
    
    public void warn(final String s) {
        Config.warn(this.context + ConnectedParser.I[0x9C ^ 0xB1] + s);
    }
    
    public BiomeGenBase findBiome(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        final BiomeGenBase[] biomeGenArray = BiomeGenBase.getBiomeGenArray();
        int i = "".length();
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (i < biomeGenArray.length) {
            final BiomeGenBase biomeGenBase = biomeGenArray[i];
            if (biomeGenBase != null && biomeGenBase.biomeName.replace(ConnectedParser.I[0x55 ^ 0x46], ConnectedParser.I[0x3D ^ 0x29]).toLowerCase().equals(lowerCase)) {
                return biomeGenBase;
            }
            ++i;
        }
        return null;
    }
    
    public boolean matchState(final IBlockState blockState, final Map map) {
        final Iterator<Map.Entry<K, List>> iterator = map.entrySet().iterator();
        "".length();
        if (-1 == 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<K, List> next = iterator.next();
            final IProperty<Comparable> property = ((Map.Entry<IProperty, List>)next).getKey();
            final List list = ((Map.Entry<IProperty, List>)next).getValue();
            final Comparable value = blockState.getValue(property);
            if (value == null) {
                return "".length() != 0;
            }
            if (!list.contains(value)) {
                return "".length() != 0;
            }
        }
        return " ".length() != 0;
    }
    
    public BiomeGenBase[] parseBiomes(final String s) {
        if (s == null) {
            return null;
        }
        final String[] tokenize = Config.tokenize(s, ConnectedParser.I[0xA4 ^ 0xB5]);
        final ArrayList<BiomeGenBase> list = new ArrayList<BiomeGenBase>();
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < tokenize.length) {
            final String s2 = tokenize[i];
            final BiomeGenBase biome = this.findBiome(s2);
            if (biome == null) {
                this.warn(ConnectedParser.I[0x7 ^ 0x15] + s2);
                "".length();
                if (4 < 1) {
                    throw null;
                }
            }
            else {
                list.add(biome);
            }
            ++i;
        }
        return list.toArray(new BiomeGenBase[list.size()]);
    }
    
    public MatchBlock[] parseMatchBlock(String trim) {
        if (trim == null) {
            return null;
        }
        trim = trim.trim();
        if (trim.length() <= 0) {
            return null;
        }
        final String[] tokenize = Config.tokenize(trim, ConnectedParser.I["  ".length()]);
        final String s = ConnectedParser.I["   ".length()];
        "".length();
        String s2;
        int n;
        if (tokenize.length > " ".length() && this.isFullBlockName(tokenize)) {
            s2 = tokenize["".length()];
            n = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            s2 = ConnectedParser.I[0x5D ^ 0x59];
            n = "".length();
        }
        final String s3 = tokenize[n];
        final String[] array = Arrays.copyOfRange(tokenize, n + " ".length(), tokenize.length);
        final Block[] blockPart = this.parseBlockPart(s2, s3);
        final MatchBlock[] array2 = new MatchBlock[blockPart.length];
        int i = "".length();
        "".length();
        if (4 == -1) {
            throw null;
        }
        while (i < blockPart.length) {
            final Block block = blockPart[i];
            array2[i] = new MatchBlock(Block.getIdFromBlock(block), this.parseBlockMetadatas(block, array));
            ++i;
        }
        return array2;
    }
}
