/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.text.ArabicShapingException;

public final class ArabicShaping {
    private final int options;
    private boolean isLogical;
    private boolean spacesRelativeToTextBeginEnd;
    private char tailChar;
    public static final int SEEN_TWOCELL_NEAR = 0x200000;
    public static final int SEEN_MASK = 0x700000;
    public static final int YEHHAMZA_TWOCELL_NEAR = 0x1000000;
    public static final int YEHHAMZA_MASK = 0x3800000;
    public static final int TASHKEEL_BEGIN = 262144;
    public static final int TASHKEEL_END = 393216;
    public static final int TASHKEEL_RESIZE = 524288;
    public static final int TASHKEEL_REPLACE_BY_TATWEEL = 786432;
    public static final int TASHKEEL_MASK = 917504;
    public static final int SPACES_RELATIVE_TO_TEXT_BEGIN_END = 0x4000000;
    public static final int SPACES_RELATIVE_TO_TEXT_MASK = 0x4000000;
    public static final int SHAPE_TAIL_NEW_UNICODE = 0x8000000;
    public static final int SHAPE_TAIL_TYPE_MASK = 0x8000000;
    public static final int LENGTH_GROW_SHRINK = 0;
    public static final int LAMALEF_RESIZE = 0;
    public static final int LENGTH_FIXED_SPACES_NEAR = 1;
    public static final int LAMALEF_NEAR = 1;
    public static final int LENGTH_FIXED_SPACES_AT_END = 2;
    public static final int LAMALEF_END = 2;
    public static final int LENGTH_FIXED_SPACES_AT_BEGINNING = 3;
    public static final int LAMALEF_BEGIN = 3;
    public static final int LAMALEF_AUTO = 65536;
    public static final int LENGTH_MASK = 65539;
    public static final int LAMALEF_MASK = 65539;
    public static final int TEXT_DIRECTION_LOGICAL = 0;
    public static final int TEXT_DIRECTION_VISUAL_RTL = 0;
    public static final int TEXT_DIRECTION_VISUAL_LTR = 4;
    public static final int TEXT_DIRECTION_MASK = 4;
    public static final int LETTERS_NOOP = 0;
    public static final int LETTERS_SHAPE = 8;
    public static final int LETTERS_UNSHAPE = 16;
    public static final int LETTERS_SHAPE_TASHKEEL_ISOLATED = 24;
    public static final int LETTERS_MASK = 24;
    public static final int DIGITS_NOOP = 0;
    public static final int DIGITS_EN2AN = 32;
    public static final int DIGITS_AN2EN = 64;
    public static final int DIGITS_EN2AN_INIT_LR = 96;
    public static final int DIGITS_EN2AN_INIT_AL = 128;
    public static final int DIGITS_MASK = 224;
    public static final int DIGIT_TYPE_AN = 0;
    public static final int DIGIT_TYPE_AN_EXTENDED = 256;
    public static final int DIGIT_TYPE_MASK = 256;
    private static final char HAMZAFE_CHAR = '\ufe80';
    private static final char HAMZA06_CHAR = '\u0621';
    private static final char YEH_HAMZA_CHAR = '\u0626';
    private static final char YEH_HAMZAFE_CHAR = '\ufe89';
    private static final char LAMALEF_SPACE_SUB = '\uffff';
    private static final char TASHKEEL_SPACE_SUB = '\ufffe';
    private static final char LAM_CHAR = '\u0644';
    private static final char SPACE_CHAR = ' ';
    private static final char SHADDA_CHAR = '\ufe7c';
    private static final char SHADDA06_CHAR = '\u0651';
    private static final char TATWEEL_CHAR = '\u0640';
    private static final char SHADDA_TATWEEL_CHAR = '\ufe7d';
    private static final char NEW_TAIL_CHAR = '\ufe73';
    private static final char OLD_TAIL_CHAR = '\u200b';
    private static final int SHAPE_MODE = 0;
    private static final int DESHAPE_MODE = 1;
    private static final int IRRELEVANT = 4;
    private static final int LAMTYPE = 16;
    private static final int ALEFTYPE = 32;
    private static final int LINKR = 1;
    private static final int LINKL = 2;
    private static final int LINK_MASK = 3;
    private static final int[] irrelevantPos = new int[]{0, 2, 4, 6, 8, 10, 12, 14};
    private static final int[] tailFamilyIsolatedFinal = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
    private static final int[] tashkeelMedial = new int[]{0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
    private static final char[] yehHamzaToYeh = new char[]{'\ufeef', '\ufef0'};
    private static final char[] convertNormalizedLamAlef = new char[]{'\u0622', '\u0623', '\u0625', '\u0627'};
    private static final int[] araLink = new int[]{4385, 4897, 5377, 5921, 6403, 7457, 7939, 8961, 9475, 10499, 11523, 12547, 13571, 14593, 15105, 15617, 16129, 16643, 17667, 18691, 19715, 20739, 21763, 22787, 23811, 0, 0, 0, 0, 0, 3, 24835, 25859, 26883, 27923, 28931, 29955, 30979, 32001, 32513, 33027, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 34049, 34561, 35073, 35585, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 33, 33, 0, 33, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 3, 3, 3, 3, 1, 1};
    private static final int[] presLink = new int[]{3, 3, 3, 0, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 32, 33, 32, 33, 0, 1, 32, 33, 0, 2, 3, 1, 32, 33, 0, 2, 3, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 16, 18, 19, 17, 0, 2, 3, 1, 0, 2, 3, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 2, 3, 1, 0, 1, 0, 1, 0, 1, 0, 1};
    private static int[] convertFEto06 = new int[]{1611, 1611, 1612, 1612, 1613, 1613, 1614, 1614, 1615, 1615, 1616, 1616, 1617, 1617, 1618, 1618, 1569, 1570, 1570, 1571, 1571, 1572, 1572, 1573, 1573, 1574, 1574, 1574, 1574, 1575, 1575, 1576, 1576, 1576, 1576, 1577, 1577, 1578, 1578, 1578, 1578, 1579, 1579, 1579, 1579, 1580, 1580, 1580, 1580, 1581, 1581, 1581, 1581, 1582, 1582, 1582, 1582, 1583, 1583, 1584, 1584, 1585, 1585, 1586, 1586, 1587, 1587, 1587, 1587, 1588, 1588, 1588, 1588, 1589, 1589, 1589, 1589, 1590, 1590, 1590, 1590, 1591, 1591, 1591, 1591, 1592, 1592, 1592, 1592, 1593, 1593, 1593, 1593, 1594, 1594, 1594, 1594, 1601, 1601, 1601, 1601, 1602, 1602, 1602, 1602, 1603, 1603, 1603, 1603, 1604, 1604, 1604, 1604, 1605, 1605, 1605, 1605, 1606, 1606, 1606, 1606, 1607, 1607, 1607, 1607, 1608, 1608, 1609, 1609, 1610, 1610, 1610, 1610, 1628, 1628, 1629, 1629, 1630, 1630, 1631, 1631};
    private static final int[][][] shapeTable = new int[][][]{new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 0, 3}, {0, 1, 0, 1}}, new int[][]{{0, 0, 2, 2}, {0, 0, 1, 2}, {0, 1, 1, 2}, {0, 1, 1, 3}}, new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 1, 0, 3}, {0, 1, 0, 3}}, new int[][]{{0, 0, 1, 2}, {0, 0, 1, 2}, {0, 1, 1, 2}, {0, 1, 1, 3}}};

    public int shape(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4) throws ArabicShapingException {
        if (cArray == null) {
            throw new IllegalArgumentException("source can not be null");
        }
        if (n < 0 || n2 < 0 || n + n2 > cArray.length) {
            throw new IllegalArgumentException("bad source start (" + n + ") or length (" + n2 + ") for buffer of length " + cArray.length);
        }
        if (cArray2 == null && n4 != 0) {
            throw new IllegalArgumentException("null dest requires destSize == 0");
        }
        if (n4 != 0 && (n3 < 0 || n4 < 0 || n3 + n4 > cArray2.length)) {
            throw new IllegalArgumentException("bad dest start (" + n3 + ") or size (" + n4 + ") for buffer of length " + cArray2.length);
        }
        if ((this.options & 0xE0000) != 0 && (this.options & 0xE0000) != 262144 && (this.options & 0xE0000) != 393216 && (this.options & 0xE0000) != 524288 && (this.options & 0xE0000) != 786432) {
            throw new IllegalArgumentException("Wrong Tashkeel argument");
        }
        if ((this.options & 0x10003) != 0 && (this.options & 0x10003) != 3 && (this.options & 0x10003) != 2 && (this.options & 0x10003) != 0 && (this.options & 0x10003) != 65536 && (this.options & 0x10003) != 1) {
            throw new IllegalArgumentException("Wrong Lam Alef argument");
        }
        if ((this.options & 0xE0000) != 0 && (this.options & 0x18) == 16) {
            throw new IllegalArgumentException("Tashkeel replacement should not be enabled in deshaping mode ");
        }
        return this.internalShape(cArray, n, n2, cArray2, n3, n4);
    }

    public void shape(char[] cArray, int n, int n2) throws ArabicShapingException {
        if ((this.options & 0x10003) == 0) {
            throw new ArabicShapingException("Cannot shape in place with length option resize.");
        }
        this.shape(cArray, n, n2, cArray, n, n2);
    }

    public String shape(String string) throws ArabicShapingException {
        char[] cArray;
        char[] cArray2 = cArray = string.toCharArray();
        if ((this.options & 0x10003) == 0 && (this.options & 0x18) == 16) {
            cArray2 = new char[cArray.length * 2];
        }
        int n = this.shape(cArray, 0, cArray.length, cArray2, 0, cArray2.length);
        return new String(cArray2, 0, n);
    }

    public ArabicShaping(int n) {
        this.options = n;
        if ((n & 0xE0) > 128) {
            throw new IllegalArgumentException("bad DIGITS options");
        }
        this.isLogical = (n & 4) == 0;
        this.spacesRelativeToTextBeginEnd = (n & 0x4000000) == 0x4000000;
        this.tailChar = (n & 0x8000000) == 0x8000000 ? (char)65139 : (char)8203;
    }

    public boolean equals(Object object) {
        return object != null && object.getClass() == ArabicShaping.class && this.options == ((ArabicShaping)object).options;
    }

    public int hashCode() {
        return this.options;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append('[');
        switch (this.options & 0x10003) {
            case 0: {
                stringBuilder.append("LamAlef resize");
                break;
            }
            case 1: {
                stringBuilder.append("LamAlef spaces at near");
                break;
            }
            case 3: {
                stringBuilder.append("LamAlef spaces at begin");
                break;
            }
            case 2: {
                stringBuilder.append("LamAlef spaces at end");
                break;
            }
            case 65536: {
                stringBuilder.append("lamAlef auto");
            }
        }
        switch (this.options & 4) {
            case 0: {
                stringBuilder.append(", logical");
                break;
            }
            case 4: {
                stringBuilder.append(", visual");
            }
        }
        switch (this.options & 0x18) {
            case 0: {
                stringBuilder.append(", no letter shaping");
                break;
            }
            case 8: {
                stringBuilder.append(", shape letters");
                break;
            }
            case 24: {
                stringBuilder.append(", shape letters tashkeel isolated");
                break;
            }
            case 16: {
                stringBuilder.append(", unshape letters");
            }
        }
        switch (this.options & 0x700000) {
            case 0x200000: {
                stringBuilder.append(", Seen at near");
            }
        }
        switch (this.options & 0x3800000) {
            case 0x1000000: {
                stringBuilder.append(", Yeh Hamza at near");
            }
        }
        switch (this.options & 0xE0000) {
            case 262144: {
                stringBuilder.append(", Tashkeel at begin");
                break;
            }
            case 393216: {
                stringBuilder.append(", Tashkeel at end");
                break;
            }
            case 786432: {
                stringBuilder.append(", Tashkeel replace with tatweel");
                break;
            }
            case 524288: {
                stringBuilder.append(", Tashkeel resize");
            }
        }
        switch (this.options & 0xE0) {
            case 0: {
                stringBuilder.append(", no digit shaping");
                break;
            }
            case 32: {
                stringBuilder.append(", shape digits to AN");
                break;
            }
            case 64: {
                stringBuilder.append(", shape digits to EN");
                break;
            }
            case 96: {
                stringBuilder.append(", shape digits to AN contextually: default EN");
                break;
            }
            case 128: {
                stringBuilder.append(", shape digits to AN contextually: default AL");
            }
        }
        switch (this.options & 0x100) {
            case 0: {
                stringBuilder.append(", standard Arabic-Indic digits");
                break;
            }
            case 256: {
                stringBuilder.append(", extended Arabic-Indic digits");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void shapeToArabicDigitsWithContext(char[] cArray, int n, int n2, char c, boolean bl) {
        UBiDiProps uBiDiProps = UBiDiProps.INSTANCE;
        c = (char)(c - 48);
        int n3 = n + n2;
        while (--n3 >= n) {
            char c2 = cArray[n3];
            switch (uBiDiProps.getClass(c2)) {
                case 0: 
                case 1: {
                    bl = false;
                    break;
                }
                case 13: {
                    bl = true;
                    break;
                }
                case 2: {
                    if (!bl || c2 > '9') break;
                    cArray[n3] = (char)(c2 + c);
                    break;
                }
            }
        }
    }

    private static void invertBuffer(char[] cArray, int n, int n2) {
        int n3 = n;
        for (int i = n + n2 - 1; n3 < i; ++n3, --i) {
            char c = cArray[n3];
            cArray[n3] = cArray[i];
            cArray[i] = c;
        }
    }

    private static char changeLamAlef(char c) {
        switch (c) {
            case '\u0622': {
                return '\u0001';
            }
            case '\u0623': {
                return '\u0000';
            }
            case '\u0625': {
                return '\u0001';
            }
            case '\u0627': {
                return '\u0000';
            }
        }
        return '\u0001';
    }

    private static int specialChar(char c) {
        if (c > '\u0621' && c < '\u0626' || c == '\u0627' || c > '\u062e' && c < '\u0633' || c > '\u0647' && c < '\u064a' || c == '\u0629') {
            return 0;
        }
        if (c >= '\u064b' && c <= '\u0652') {
            return 1;
        }
        if (c >= '\u0653' && c <= '\u0655' || c == '\u0670' || c >= '\ufe70' && c <= '\ufe7f') {
            return 0;
        }
        return 1;
    }

    private static int getLink(char c) {
        if (c >= '\u0622' && c <= '\u06d3') {
            return araLink[c - 1570];
        }
        if (c == '\u200d') {
            return 0;
        }
        if (c >= '\u206d' && c <= '\u206f') {
            return 1;
        }
        if (c >= '\ufe70' && c <= '\ufefc') {
            return presLink[c - 65136];
        }
        return 1;
    }

    private static int countSpacesLeft(char[] cArray, int n, int n2) {
        int n3 = n + n2;
        for (int i = n; i < n3; ++i) {
            if (cArray[i] == ' ') continue;
            return i - n;
        }
        return n2;
    }

    private static int countSpacesRight(char[] cArray, int n, int n2) {
        int n3 = n + n2;
        while (--n3 >= n) {
            if (cArray[n3] == ' ') continue;
            return n + n2 - 1 - n3;
        }
        return n2;
    }

    private static boolean isTashkeelChar(char c) {
        return c >= '\u064b' && c <= '\u0652';
    }

    private static int isSeenTailFamilyChar(char c) {
        if (c >= '\ufeb1' && c < '\ufebf') {
            return tailFamilyIsolatedFinal[c - 65201];
        }
        return 1;
    }

    private static int isSeenFamilyChar(char c) {
        if (c >= '\u0633' && c <= '\u0636') {
            return 0;
        }
        return 1;
    }

    private static boolean isTailChar(char c) {
        return c != '\u200b' && c != '\ufe73';
    }

    private static boolean isAlefMaksouraChar(char c) {
        return c == '\ufeef' || c == '\ufef0' || c == '\u0649';
    }

    private static boolean isYehHamzaChar(char c) {
        return c != '\ufe89' && c != '\ufe8a';
    }

    private static boolean isTashkeelCharFE(char c) {
        return c != '\ufe75' && c >= '\ufe70' && c <= '\ufe7f';
    }

    private static int isTashkeelOnTatweelChar(char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75' && c != '\ufe7d') {
            return tashkeelMedial[c - 65136];
        }
        if (c >= '\ufcf2' && c <= '\ufcf4' || c == '\ufe7d') {
            return 1;
        }
        return 1;
    }

    private static int isIsolatedTashkeelChar(char c) {
        if (c >= '\ufe70' && c <= '\ufe7f' && c != '\ufe73' && c != '\ufe75') {
            return 1 - tashkeelMedial[c - 65136];
        }
        if (c >= '\ufc5e' && c <= '\ufc63') {
            return 0;
        }
        return 1;
    }

    private static boolean isAlefChar(char c) {
        return c == '\u0622' || c == '\u0623' || c == '\u0625' || c == '\u0627';
    }

    private static boolean isLamAlefChar(char c) {
        return c >= '\ufef5' && c <= '\ufefc';
    }

    private static boolean isNormalizedLamAlefChar(char c) {
        return c >= '\u065c' && c <= '\u065f';
    }

    private int calculateSize(char[] cArray, int n, int n2) {
        int n3 = n2;
        switch (this.options & 0x18) {
            case 8: 
            case 24: {
                if (this.isLogical) {
                    int n4 = n + n2 - 1;
                    for (int i = n; i < n4; ++i) {
                        if ((cArray[i] != '\u0644' || !ArabicShaping.isAlefChar(cArray[i + 1])) && !ArabicShaping.isTashkeelCharFE(cArray[i])) continue;
                        --n3;
                    }
                } else {
                    int n5 = n + n2;
                    for (int i = n + 1; i < n5; ++i) {
                        if ((cArray[i] != '\u0644' || !ArabicShaping.isAlefChar(cArray[i - 1])) && !ArabicShaping.isTashkeelCharFE(cArray[i])) continue;
                        --n3;
                    }
                }
                break;
            }
            case 16: {
                int n6 = n + n2;
                for (int i = n; i < n6; ++i) {
                    if (!ArabicShaping.isLamAlefChar(cArray[i])) continue;
                    ++n3;
                }
                break;
            }
        }
        return n3;
    }

    private static int countSpaceSub(char[] cArray, int n, char c) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (cArray[i] != c) continue;
            ++n2;
        }
        return n2;
    }

    private static void shiftArray(char[] cArray, int n, int n2, char c) {
        int n3 = n2;
        int n4 = n2;
        while (--n4 >= n) {
            char c2 = cArray[n4];
            if (c2 == c || --n3 == n4) continue;
            cArray[n3] = c2;
        }
    }

    private static int flipArray(char[] cArray, int n, int n2, int n3) {
        if (n3 > n) {
            int n4 = n3;
            n3 = n;
            while (n4 < n2) {
                cArray[n3++] = cArray[n4++];
            }
        } else {
            n3 = n2;
        }
        return n3;
    }

    private static int handleTashkeelWithTatweel(char[] cArray, int n) {
        for (int i = 0; i < n; ++i) {
            if (ArabicShaping.isTashkeelOnTatweelChar(cArray[i]) == 1) {
                cArray[i] = 1600;
                continue;
            }
            if (ArabicShaping.isTashkeelOnTatweelChar(cArray[i]) == 2) {
                cArray[i] = 65149;
                continue;
            }
            if (ArabicShaping.isIsolatedTashkeelChar(cArray[i]) != 1 || cArray[i] == '\ufe7c') continue;
            cArray[i] = 32;
        }
        return n;
    }

    private int handleGeneratedSpaces(char[] cArray, int n, int n2) {
        block26: {
            boolean bl;
            boolean bl2;
            int n3;
            int n4;
            block25: {
                int n5;
                n4 = this.options & 0x10003;
                n3 = this.options & 0xE0000;
                bl2 = false;
                bl = false;
                if (!this.isLogical & !this.spacesRelativeToTextBeginEnd) {
                    switch (n4) {
                        case 3: {
                            n4 = 2;
                            break;
                        }
                        case 2: {
                            n4 = 3;
                            break;
                        }
                    }
                    switch (n3) {
                        case 262144: {
                            n3 = 393216;
                            break;
                        }
                        case 393216: {
                            n3 = 262144;
                            break;
                        }
                    }
                }
                if (n4 != 1) break block25;
                int n6 = n5 + n2;
                for (n5 = n; n5 < n6; ++n5) {
                    if (cArray[n5] != '\uffff') continue;
                    cArray[n5] = 32;
                }
                break block26;
            }
            int n7 = n + n2;
            int n8 = ArabicShaping.countSpaceSub(cArray, n2, '\uffff');
            int n9 = ArabicShaping.countSpaceSub(cArray, n2, '\ufffe');
            if (n4 == 2) {
                bl2 = true;
            }
            if (n3 == 393216) {
                bl = true;
            }
            if (bl2 && n4 == 2) {
                ArabicShaping.shiftArray(cArray, n, n7, '\uffff');
                while (n8 > n) {
                    cArray[--n8] = 32;
                }
            }
            if (bl && n3 == 393216) {
                ArabicShaping.shiftArray(cArray, n, n7, '\ufffe');
                while (n9 > n) {
                    cArray[--n9] = 32;
                }
            }
            bl2 = false;
            bl = false;
            if (n4 == 0) {
                bl2 = true;
            }
            if (n3 == 524288) {
                bl = true;
            }
            if (bl2 && n4 == 0) {
                ArabicShaping.shiftArray(cArray, n, n7, '\uffff');
                n8 = ArabicShaping.flipArray(cArray, n, n7, n8);
                n2 = n8 - n;
            }
            if (bl && n3 == 524288) {
                ArabicShaping.shiftArray(cArray, n, n7, '\ufffe');
                n9 = ArabicShaping.flipArray(cArray, n, n7, n9);
                n2 = n9 - n;
            }
            bl2 = false;
            bl = false;
            if (n4 == 3 || n4 == 65536) {
                bl2 = true;
            }
            if (n3 == 262144) {
                bl = true;
            }
            if (bl2 && (n4 == 3 || n4 == 65536)) {
                ArabicShaping.shiftArray(cArray, n, n7, '\uffff');
                n8 = ArabicShaping.flipArray(cArray, n, n7, n8);
                while (n8 < n7) {
                    cArray[n8++] = 32;
                }
            }
            if (!bl || n3 != 262144) break block26;
            ArabicShaping.shiftArray(cArray, n, n7, '\ufffe');
            n9 = ArabicShaping.flipArray(cArray, n, n7, n9);
            while (n9 < n7) {
                cArray[n9++] = 32;
            }
        }
        return n2;
    }

    private boolean expandCompositCharAtBegin(char[] cArray, int n, int n2, int n3) {
        boolean bl = false;
        if (n3 > ArabicShaping.countSpacesRight(cArray, n, n2)) {
            bl = true;
            return bl;
        }
        int n4 = n + n2 - n3;
        int n5 = n + n2;
        while (--n4 >= n) {
            char c = cArray[n4];
            if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                cArray[--n5] = 1604;
                cArray[--n5] = convertNormalizedLamAlef[c - 1628];
                continue;
            }
            cArray[--n5] = c;
        }
        return bl;
    }

    private boolean expandCompositCharAtEnd(char[] cArray, int n, int n2, int n3) {
        boolean bl = false;
        if (n3 > ArabicShaping.countSpacesLeft(cArray, n, n2)) {
            bl = true;
            return bl;
        }
        int n4 = n;
        int n5 = n + n2;
        for (int i = n + n3; i < n5; ++i) {
            char c = cArray[i];
            if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                cArray[n4++] = convertNormalizedLamAlef[c - 1628];
                cArray[n4++] = 1604;
                continue;
            }
            cArray[n4++] = c;
        }
        return bl;
    }

    private boolean expandCompositCharAtNear(char[] cArray, int n, int n2, int n3, int n4, int n5) {
        boolean bl = false;
        if (ArabicShaping.isNormalizedLamAlefChar(cArray[n])) {
            bl = true;
            return bl;
        }
        int n6 = n + n2;
        while (--n6 >= n) {
            char c = cArray[n6];
            if (n5 == 1 && ArabicShaping.isNormalizedLamAlefChar(c)) {
                if (n6 > n && cArray[n6 - 1] == ' ') {
                    cArray[n6] = 1604;
                    cArray[--n6] = convertNormalizedLamAlef[c - 1628];
                    continue;
                }
                bl = true;
                return bl;
            }
            if (n4 == 1 && ArabicShaping.isSeenTailFamilyChar(c) == 1) {
                if (n6 > n && cArray[n6 - 1] == ' ') {
                    cArray[n6 - 1] = this.tailChar;
                    continue;
                }
                bl = true;
                return bl;
            }
            if (n3 != 1 || !ArabicShaping.isYehHamzaChar(c)) continue;
            if (n6 > n && cArray[n6 - 1] == ' ') {
                cArray[n6] = yehHamzaToYeh[c - 65161];
                cArray[n6 - 1] = 65152;
                continue;
            }
            bl = true;
            return bl;
        }
        return true;
    }

    private int expandCompositChar(char[] cArray, int n, int n2, int n3, int n4) throws ArabicShapingException {
        int n5 = this.options & 0x10003;
        int n6 = this.options & 0x700000;
        int n7 = this.options & 0x3800000;
        boolean bl = false;
        if (!this.isLogical && !this.spacesRelativeToTextBeginEnd) {
            switch (n5) {
                case 3: {
                    n5 = 2;
                    break;
                }
                case 2: {
                    n5 = 3;
                    break;
                }
            }
        }
        if (n4 == 1) {
            if (n5 == 65536) {
                if (this.isLogical) {
                    bl = this.expandCompositCharAtEnd(cArray, n, n2, n3);
                    if (bl) {
                        bl = this.expandCompositCharAtBegin(cArray, n, n2, n3);
                    }
                    if (bl) {
                        bl = this.expandCompositCharAtNear(cArray, n, n2, 0, 0, 1);
                    }
                    if (bl) {
                        throw new ArabicShapingException("No spacefor lamalef");
                    }
                } else {
                    bl = this.expandCompositCharAtBegin(cArray, n, n2, n3);
                    if (bl) {
                        bl = this.expandCompositCharAtEnd(cArray, n, n2, n3);
                    }
                    if (bl) {
                        bl = this.expandCompositCharAtNear(cArray, n, n2, 0, 0, 1);
                    }
                    if (bl) {
                        throw new ArabicShapingException("No spacefor lamalef");
                    }
                }
            } else if (n5 == 2) {
                bl = this.expandCompositCharAtEnd(cArray, n, n2, n3);
                if (bl) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            } else if (n5 == 3) {
                bl = this.expandCompositCharAtBegin(cArray, n, n2, n3);
                if (bl) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            } else if (n5 == 1) {
                bl = this.expandCompositCharAtNear(cArray, n, n2, 0, 0, 1);
                if (bl) {
                    throw new ArabicShapingException("No spacefor lamalef");
                }
            } else if (n5 == 0) {
                int n8 = n + n2;
                int n9 = n8 + n3;
                while (--n8 >= n) {
                    char c = cArray[n8];
                    if (ArabicShaping.isNormalizedLamAlefChar(c)) {
                        cArray[--n9] = 1604;
                        cArray[--n9] = convertNormalizedLamAlef[c - 1628];
                        continue;
                    }
                    cArray[--n9] = c;
                }
                n2 += n3;
            }
        } else {
            if (n6 == 0x200000 && (bl = this.expandCompositCharAtNear(cArray, n, n2, 0, 1, 0))) {
                throw new ArabicShapingException("No space for Seen tail expansion");
            }
            if (n7 == 0x1000000 && (bl = this.expandCompositCharAtNear(cArray, n, n2, 1, 0, 0))) {
                throw new ArabicShapingException("No space for YehHamza expansion");
            }
        }
        return n2;
    }

    private int normalize(char[] cArray, int n, int n2) {
        int n3;
        int n4 = 0;
        int n5 = n3 + n2;
        for (n3 = n; n3 < n5; ++n3) {
            char c = cArray[n3];
            if (c < '\ufe70' || c > '\ufefc') continue;
            if (ArabicShaping.isLamAlefChar(c)) {
                ++n4;
            }
            cArray[n3] = (char)convertFEto06[c - 65136];
        }
        return n4;
    }

    private int deshapeNormalize(char[] cArray, int n, int n2) {
        int n3;
        int n4 = 0;
        boolean bl = false;
        boolean bl2 = false;
        bl = (this.options & 0x3800000) == 0x1000000;
        bl2 = (this.options & 0x700000) == 0x200000;
        int n5 = n3 + n2;
        for (n3 = n; n3 < n5; ++n3) {
            char c = cArray[n3];
            if (bl && (c == '\u0621' || c == '\ufe80') && n3 < n2 - 1 && ArabicShaping.isAlefMaksouraChar(cArray[n3 + 1])) {
                cArray[n3] = 32;
                cArray[n3 + 1] = 1574;
                continue;
            }
            if (bl2 && ArabicShaping.isTailChar(c) && n3 < n2 - 1 && ArabicShaping.isSeenTailFamilyChar(cArray[n3 + 1]) == 1) {
                cArray[n3] = 32;
                continue;
            }
            if (c < '\ufe70' || c > '\ufefc') continue;
            if (ArabicShaping.isLamAlefChar(c)) {
                ++n4;
            }
            cArray[n3] = (char)convertFEto06[c - 65136];
        }
        return n4;
    }

    private int shapeUnicode(char[] cArray, int n, int n2, int n3, int n4) throws ArabicShapingException {
        int n5 = this.normalize(cArray, n, n2);
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n6 = n + n2 - 1;
        int n7 = ArabicShaping.getLink(cArray[n6]);
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = n6;
        int n12 = -2;
        int n13 = 0;
        while (n6 >= 0) {
            if ((n7 & 0xFF00) != 0 || ArabicShaping.isTashkeelChar(cArray[n6])) {
                char c;
                n13 = n6 - 1;
                n12 = -2;
                while (n12 < 0) {
                    if (n13 == -1) {
                        n8 = 0;
                        n12 = Integer.MAX_VALUE;
                        continue;
                    }
                    n8 = ArabicShaping.getLink(cArray[n13]);
                    if ((n8 & 4) == 0) {
                        n12 = n13;
                        continue;
                    }
                    --n13;
                }
                if ((n7 & 0x20) > 0 && (n10 & 0x10) > 0) {
                    bl = true;
                    c = ArabicShaping.changeLamAlef(cArray[n6]);
                    if (c != '\u0000') {
                        cArray[n6] = 65535;
                        cArray[n11] = c;
                        n6 = n11;
                    }
                    n10 = n9;
                    n7 = ArabicShaping.getLink(c);
                }
                if (n6 > 0 && cArray[n6 - 1] == ' ') {
                    if (ArabicShaping.isSeenFamilyChar(cArray[n6]) == 1) {
                        bl2 = true;
                    } else if (cArray[n6] == '\u0626') {
                        bl3 = true;
                    }
                } else if (n6 == 0) {
                    if (ArabicShaping.isSeenFamilyChar(cArray[n6]) == 1) {
                        bl2 = true;
                    } else if (cArray[n6] == '\u0626') {
                        bl3 = true;
                    }
                }
                c = ArabicShaping.specialChar(cArray[n6]);
                int n14 = shapeTable[n8 & 3][n10 & 3][n7 & 3];
                if (c == '\u0001') {
                    n14 &= 1;
                } else if (c == '\u0002') {
                    n14 = n4 == 0 && (n10 & 2) != 0 && (n8 & 1) != 0 && cArray[n6] != '\u064c' && cArray[n6] != '\u064d' && ((n8 & 0x20) != 32 || (n10 & 0x10) != 16) ? 1 : (n4 == 2 && cArray[n6] == '\u0651' ? 1 : 0);
                }
                if (c == '\u0002') {
                    if (n4 == 2 && cArray[n6] != '\u0651') {
                        cArray[n6] = 65534;
                        bl4 = true;
                    } else {
                        cArray[n6] = (char)(65136 + irrelevantPos[cArray[n6] - 1611] + n14);
                    }
                } else {
                    cArray[n6] = (char)(65136 + (n7 >> 8) + n14);
                }
            }
            if ((n7 & 4) == 0) {
                n9 = n10;
                n10 = n7;
                n11 = n6;
            }
            if (--n6 == n12) {
                n7 = n8;
                n12 = -2;
                continue;
            }
            if (n6 == -1) continue;
            n7 = ArabicShaping.getLink(cArray[n6]);
        }
        n3 = n2;
        if (bl || bl4) {
            n3 = this.handleGeneratedSpaces(cArray, n, n2);
        }
        if (bl2 || bl3) {
            n3 = this.expandCompositChar(cArray, n, n3, n5, 0);
        }
        return n3;
    }

    private int deShapeUnicode(char[] cArray, int n, int n2, int n3) throws ArabicShapingException {
        int n4 = this.deshapeNormalize(cArray, n, n2);
        n3 = n4 != 0 ? this.expandCompositChar(cArray, n, n2, n4, 1) : n2;
        return n3;
    }

    private int internalShape(char[] cArray, int n, int n2, char[] cArray2, int n3, int n4) throws ArabicShapingException {
        if (n2 == 0) {
            return 1;
        }
        if (n4 == 0) {
            if ((this.options & 0x18) != 0 && (this.options & 0x10003) == 0) {
                return this.calculateSize(cArray, n, n2);
            }
            return n2;
        }
        char[] cArray3 = new char[n2 * 2];
        System.arraycopy(cArray, n, cArray3, 0, n2);
        if (this.isLogical) {
            ArabicShaping.invertBuffer(cArray3, 0, n2);
        }
        int n5 = n2;
        switch (this.options & 0x18) {
            case 24: {
                n5 = this.shapeUnicode(cArray3, 0, n2, n4, 1);
                break;
            }
            case 8: {
                if ((this.options & 0xE0000) != 0 && (this.options & 0xE0000) != 786432) {
                    n5 = this.shapeUnicode(cArray3, 0, n2, n4, 2);
                    break;
                }
                n5 = this.shapeUnicode(cArray3, 0, n2, n4, 0);
                if ((this.options & 0xE0000) != 786432) break;
                n5 = ArabicShaping.handleTashkeelWithTatweel(cArray3, n2);
                break;
            }
            case 16: {
                n5 = this.deShapeUnicode(cArray3, 0, n2, n4);
                break;
            }
        }
        if (n5 > n4) {
            throw new ArabicShapingException("not enough room for result data");
        }
        if ((this.options & 0xE0) != 0) {
            char c = '0';
            switch (this.options & 0x100) {
                case 0: {
                    c = '\u0660';
                    break;
                }
                case 256: {
                    c = '\u06f0';
                    break;
                }
            }
            switch (this.options & 0xE0) {
                case 32: {
                    int n6 = c - 48;
                    for (int i = 0; i < n5; ++i) {
                        char c2 = cArray3[i];
                        if (c2 > '9' || c2 < '0') continue;
                        int n7 = i;
                        cArray3[n7] = (char)(cArray3[n7] + n6);
                    }
                    break;
                }
                case 64: {
                    char c3 = (char)(c + 9);
                    int n8 = 48 - c;
                    for (int i = 0; i < n5; ++i) {
                        char c4 = cArray3[i];
                        if (c4 > c3 || c4 < c) continue;
                        int n9 = i;
                        cArray3[n9] = (char)(cArray3[n9] + n8);
                    }
                    break;
                }
                case 96: {
                    this.shapeToArabicDigitsWithContext(cArray3, 0, n5, c, false);
                    break;
                }
                case 128: {
                    this.shapeToArabicDigitsWithContext(cArray3, 0, n5, c, true);
                    break;
                }
            }
        }
        if (this.isLogical) {
            ArabicShaping.invertBuffer(cArray3, 0, n5);
        }
        System.arraycopy(cArray3, 0, cArray2, n3, n5);
        return n5;
    }
}

