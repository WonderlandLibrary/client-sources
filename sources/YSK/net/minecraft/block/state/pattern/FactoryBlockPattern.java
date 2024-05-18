package net.minecraft.block.state.pattern;

import net.minecraft.block.state.*;
import java.lang.reflect.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public class FactoryBlockPattern
{
    private static final Joiner COMMA_JOIN;
    private int rowWidth;
    private static final String[] I;
    private final Map<Character, Predicate<BlockWorldState>> symbolMap;
    private int aisleHeight;
    private final List<String[]> depth;
    
    private Predicate<BlockWorldState>[][][] makePredicateArray() {
        this.checkMissingPredicates();
        final Class<Predicate> clazz = Predicate.class;
        final int[] array = new int["   ".length()];
        array["".length()] = this.depth.size();
        array[" ".length()] = this.aisleHeight;
        array["  ".length()] = this.rowWidth;
        final Predicate[][][] array2 = (Predicate[][][])Array.newInstance(clazz, array);
        int i = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (i < this.depth.size()) {
            int j = "".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
            while (j < this.aisleHeight) {
                int k = "".length();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
                while (k < this.rowWidth) {
                    array2[i][j][k] = this.symbolMap.get(this.depth.get(i)[j].charAt(k));
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        return (Predicate<BlockWorldState>[][][])array2;
    }
    
    static {
        I();
        COMMA_JOIN = Joiner.on(FactoryBlockPattern.I["".length()]);
    }
    
    public static FactoryBlockPattern start() {
        return new FactoryBlockPattern();
    }
    
    public FactoryBlockPattern aisle(final String... array) {
        if (ArrayUtils.isEmpty((Object[])array) || StringUtils.isEmpty((CharSequence)array["".length()])) {
            throw new IllegalArgumentException(FactoryBlockPattern.I[0xC3 ^ 0xC4]);
        }
        if (this.depth.isEmpty()) {
            this.aisleHeight = array.length;
            this.rowWidth = array["".length()].length();
        }
        if (array.length != this.aisleHeight) {
            throw new IllegalArgumentException(FactoryBlockPattern.I[" ".length()] + this.aisleHeight + FactoryBlockPattern.I["  ".length()] + array.length + FactoryBlockPattern.I["   ".length()]);
        }
        final int length = array.length;
        int i = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < length) {
            final String s = array[i];
            if (s.length() != this.rowWidth) {
                throw new IllegalArgumentException(FactoryBlockPattern.I[0x83 ^ 0x87] + this.rowWidth + FactoryBlockPattern.I[0xE ^ 0xB] + s.length() + FactoryBlockPattern.I[0x6 ^ 0x0]);
            }
            final char[] charArray;
            final int length2 = (charArray = s.toCharArray()).length;
            int j = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (j < length2) {
                final char c = charArray[j];
                if (!this.symbolMap.containsKey(c)) {
                    this.symbolMap.put(c, null);
                }
                ++j;
            }
            ++i;
        }
        this.depth.add(array);
        return this;
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private FactoryBlockPattern() {
        this.depth = (List<String[]>)Lists.newArrayList();
        (this.symbolMap = (Map<Character, Predicate<BlockWorldState>>)Maps.newHashMap()).put((char)(0x94 ^ 0xB4), (Predicate<BlockWorldState>)Predicates.alwaysTrue());
    }
    
    private void checkMissingPredicates() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Map.Entry<Character, Predicate<BlockWorldState>>> iterator = this.symbolMap.entrySet().iterator();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<Character, Predicate<BlockWorldState>> entry = iterator.next();
            if (entry.getValue() == null) {
                arrayList.add(entry.getKey());
            }
        }
        if (!arrayList.isEmpty()) {
            throw new IllegalStateException(FactoryBlockPattern.I[0xCF ^ 0xC7] + FactoryBlockPattern.COMMA_JOIN.join((Iterable)arrayList) + FactoryBlockPattern.I[0x88 ^ 0x81]);
        }
    }
    
    public BlockPattern build() {
        return new BlockPattern(this.makePredicateArray());
    }
    
    private static void I() {
        (I = new String[0xA0 ^ 0xAA])["".length()] = I("\\", "pLuBe");
        FactoryBlockPattern.I[" ".length()] = I("\u000b\u001a7\u0011(:\u0007#T*'\u0011+\u0011k9\u000b3\u001ck&\u0007.\u0013#:B(\u0012k", "NbGtK");
        FactoryBlockPattern.I["  ".length()] = I("XE1\u00195T\u00122\u001fa\u0013\f%\t/T\n=\ta\u0003\f'\u0004a\u0015E;\t(\u0013\r'L.\u0012E", "teSlA");
        FactoryBlockPattern.I["   ".length()] = I("h", "AklHt");
        FactoryBlockPattern.I[0x12 ^ 0x16] = I("\u0007,\"I\u0004%/v\u001b\n>0v\u0000\u000bi7>\fE.* \f\u000bi\"?\u001a\t,c7\u001b\u0000i7>\fE*,$\u001b\u0000*7v\u001e\f-7>IM,;&\f\u0006=&2I", "ICVie");
        FactoryBlockPattern.I[0xB1 ^ 0xB4] = I("|S\u001e\u00037>\u0017X\u0003,5S\u000f\u000568S", "PsxlB");
        FactoryBlockPattern.I[0x9B ^ 0x9D] = I("`", "Inifd");
        FactoryBlockPattern.I[0x1 ^ 0x6] = I("$\t\u0005\u001f\u001cA\u0014\u0014\u001f\u0011\u0004\u0016\u001bK\u0003\u000e\u0016U\n\f\u0012\b\u0010", "aduke");
        FactoryBlockPattern.I[0x6A ^ 0x62] = I("3\"\u00163-\u00001\u000727C6\u001c%d\u00008\u0012%%\u0000$\u0016%l\u0010yS", "cPsWD");
        FactoryBlockPattern.I[0xCE ^ 0xC7] = I("R\u0000\n\u001cI\u001f\b\u000b\n\u0000\u001c\u0006", "raxyi");
    }
    
    public FactoryBlockPattern where(final char c, final Predicate<BlockWorldState> predicate) {
        this.symbolMap.put(c, predicate);
        return this;
    }
}
