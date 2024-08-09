/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BidiClassifier;
import com.ibm.icu.text.BidiLine;
import com.ibm.icu.text.BidiRun;
import com.ibm.icu.text.BidiWriter;
import com.ibm.icu.text.UTF16;
import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

public class Bidi {
    public static final byte LEVEL_DEFAULT_LTR = 126;
    public static final byte LEVEL_DEFAULT_RTL = 127;
    public static final byte MAX_EXPLICIT_LEVEL = 125;
    public static final byte LEVEL_OVERRIDE = -128;
    public static final int MAP_NOWHERE = -1;
    public static final byte LTR = 0;
    public static final byte RTL = 1;
    public static final byte MIXED = 2;
    public static final byte NEUTRAL = 3;
    public static final short KEEP_BASE_COMBINING = 1;
    public static final short DO_MIRRORING = 2;
    public static final short INSERT_LRM_FOR_NUMERIC = 4;
    public static final short REMOVE_BIDI_CONTROLS = 8;
    public static final short OUTPUT_REVERSE = 16;
    public static final short REORDER_DEFAULT = 0;
    public static final short REORDER_NUMBERS_SPECIAL = 1;
    public static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
    public static final short REORDER_RUNS_ONLY = 3;
    public static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
    public static final short REORDER_INVERSE_LIKE_DIRECT = 5;
    public static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
    static final short REORDER_COUNT = 7;
    static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
    public static final int OPTION_DEFAULT = 0;
    public static final int OPTION_INSERT_MARKS = 1;
    public static final int OPTION_REMOVE_CONTROLS = 2;
    public static final int OPTION_STREAMING = 4;
    static final byte L = 0;
    static final byte R = 1;
    static final byte EN = 2;
    static final byte ES = 3;
    static final byte ET = 4;
    static final byte AN = 5;
    static final byte CS = 6;
    static final byte B = 7;
    static final byte S = 8;
    static final byte WS = 9;
    static final byte ON = 10;
    static final byte LRE = 11;
    static final byte LRO = 12;
    static final byte AL = 13;
    static final byte RLE = 14;
    static final byte RLO = 15;
    static final byte PDF = 16;
    static final byte NSM = 17;
    static final byte BN = 18;
    static final byte FSI = 19;
    static final byte LRI = 20;
    static final byte RLI = 21;
    static final byte PDI = 22;
    static final byte ENL = 23;
    static final byte ENR = 24;
    @Deprecated
    public static final int CLASS_DEFAULT = 23;
    static final int SIMPLE_PARAS_COUNT = 10;
    static final int SIMPLE_OPENINGS_COUNT = 20;
    private static final char CR = '\r';
    private static final char LF = '\n';
    static final int LRM_BEFORE = 1;
    static final int LRM_AFTER = 2;
    static final int RLM_BEFORE = 4;
    static final int RLM_AFTER = 8;
    static final byte FOUND_L = (byte)Bidi.DirPropFlag((byte)0);
    static final byte FOUND_R = (byte)Bidi.DirPropFlag((byte)1);
    static final int ISOLATE = 256;
    Bidi paraBidi;
    final UBiDiProps bdp;
    char[] text;
    int originalLength;
    int length;
    int resultLength;
    boolean mayAllocateText;
    boolean mayAllocateRuns;
    byte[] dirPropsMemory = new byte[1];
    byte[] levelsMemory = new byte[1];
    byte[] dirProps;
    byte[] levels;
    boolean isInverse;
    int reorderingMode;
    int reorderingOptions;
    boolean orderParagraphsLTR;
    byte paraLevel;
    byte defaultParaLevel;
    String prologue;
    String epilogue;
    ImpTabPair impTabPair;
    byte direction;
    int flags;
    int lastArabicPos;
    int trailingWSStart;
    int paraCount;
    int[] paras_limit = new int[10];
    byte[] paras_level = new byte[10];
    int runCount;
    BidiRun[] runsMemory = new BidiRun[0];
    BidiRun[] runs;
    BidiRun[] simpleRuns = new BidiRun[]{new BidiRun()};
    Isolate[] isolates;
    int isolateCount;
    int[] logicalToVisualRunsMap;
    boolean isGoodLogicalToVisualRunsMap;
    BidiClassifier customClassifier = null;
    InsertPoints insertPoints = new InsertPoints();
    int controlCount;
    static final int DirPropFlagMultiRuns = Bidi.DirPropFlag((byte)31);
    static final int[] DirPropFlagLR = new int[]{Bidi.DirPropFlag((byte)0), Bidi.DirPropFlag((byte)1)};
    static final int[] DirPropFlagE = new int[]{Bidi.DirPropFlag((byte)11), Bidi.DirPropFlag((byte)14)};
    static final int[] DirPropFlagO = new int[]{Bidi.DirPropFlag((byte)12), Bidi.DirPropFlag((byte)15)};
    static final int MASK_LTR = Bidi.DirPropFlag((byte)0) | Bidi.DirPropFlag((byte)2) | Bidi.DirPropFlag((byte)23) | Bidi.DirPropFlag((byte)24) | Bidi.DirPropFlag((byte)5) | Bidi.DirPropFlag((byte)11) | Bidi.DirPropFlag((byte)12) | Bidi.DirPropFlag((byte)20);
    static final int MASK_RTL = Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13) | Bidi.DirPropFlag((byte)14) | Bidi.DirPropFlag((byte)15) | Bidi.DirPropFlag((byte)21);
    static final int MASK_R_AL = Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13);
    static final int MASK_STRONG_EN_AN = Bidi.DirPropFlag((byte)0) | Bidi.DirPropFlag((byte)1) | Bidi.DirPropFlag((byte)13) | Bidi.DirPropFlag((byte)2) | Bidi.DirPropFlag((byte)5);
    static final int MASK_EXPLICIT = Bidi.DirPropFlag((byte)11) | Bidi.DirPropFlag((byte)12) | Bidi.DirPropFlag((byte)14) | Bidi.DirPropFlag((byte)15) | Bidi.DirPropFlag((byte)16);
    static final int MASK_BN_EXPLICIT = Bidi.DirPropFlag((byte)18) | MASK_EXPLICIT;
    static final int MASK_ISO = Bidi.DirPropFlag((byte)20) | Bidi.DirPropFlag((byte)21) | Bidi.DirPropFlag((byte)19) | Bidi.DirPropFlag((byte)22);
    static final int MASK_B_S = Bidi.DirPropFlag((byte)7) | Bidi.DirPropFlag((byte)8);
    static final int MASK_WS = MASK_B_S | Bidi.DirPropFlag((byte)9) | MASK_BN_EXPLICIT | MASK_ISO;
    static final int MASK_POSSIBLE_N = Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlag((byte)6) | Bidi.DirPropFlag((byte)3) | Bidi.DirPropFlag((byte)4) | MASK_WS;
    static final int MASK_EMBEDDING = Bidi.DirPropFlag((byte)17) | MASK_POSSIBLE_N;
    static final int NOT_SEEKING_STRONG = 0;
    static final int SEEKING_STRONG_FOR_PARA = 1;
    static final int SEEKING_STRONG_FOR_FSI = 2;
    static final int LOOKING_FOR_PDI = 3;
    private static final int IMPTABPROPS_COLUMNS = 16;
    private static final int IMPTABPROPS_RES = 15;
    private static final short[] groupProp = new short[]{0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10, 4, 4, 4, 4, 13, 14};
    private static final short _L = 0;
    private static final short _R = 1;
    private static final short _EN = 2;
    private static final short _AN = 3;
    private static final short _ON = 4;
    private static final short _S = 5;
    private static final short _B = 6;
    private static final short[][] impTabProps = new short[][]{{1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 18, 21, 4}, {1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 50, 53, 0}, {33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 50, 53, 1}, {33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 50, 53, 1}, {33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 18, 21, 2}, {33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 50, 53, 3}, {33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 18, 21, 3}, {33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 50, 53, 4}, {33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 50, 53, 4}, {33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 18, 21, 4}, {97, 98, 4, 101, 135, 111, 113, 135, 142, 135, 10, 135, 99, 18, 21, 2}, {33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 18, 21, 2}, {97, 98, 100, 5, 135, 111, 113, 135, 142, 135, 12, 135, 99, 114, 117, 3}, {97, 98, 6, 6, 136, 112, 113, 136, 136, 136, 13, 136, 99, 18, 21, 3}, {33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 146, 149, 4}, {33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 50, 53, 5}, {33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 50, 53, 5}, {33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 50, 53, 6}, {33, 34, 18, 37, 39, 47, 49, 83, 20, 83, 18, 18, 35, 18, 21, 0}, {97, 98, 18, 101, 135, 111, 113, 135, 142, 135, 19, 135, 99, 18, 21, 0}, {33, 34, 18, 37, 39, 47, 49, 39, 20, 39, 20, 20, 35, 18, 21, 0}, {33, 34, 21, 37, 39, 47, 49, 86, 23, 86, 21, 21, 35, 18, 21, 3}, {97, 98, 21, 101, 135, 111, 113, 135, 142, 135, 22, 135, 99, 18, 21, 3}, {33, 34, 21, 37, 39, 47, 49, 39, 23, 39, 23, 23, 35, 18, 21, 3}};
    private static final int IMPTABLEVELS_COLUMNS = 8;
    private static final int IMPTABLEVELS_RES = 7;
    private static final byte[][] impTabL_DEFAULT = new byte[][]{{0, 1, 0, 2, 0, 0, 0, 0}, {0, 1, 3, 3, 20, 20, 0, 1}, {0, 1, 0, 2, 21, 21, 0, 2}, {0, 1, 3, 3, 20, 20, 0, 2}, {0, 33, 51, 51, 4, 4, 0, 0}, {0, 33, 0, 50, 5, 5, 0, 0}};
    private static final byte[][] impTabR_DEFAULT = new byte[][]{{1, 0, 2, 2, 0, 0, 0, 0}, {1, 0, 1, 3, 20, 20, 0, 1}, {1, 0, 2, 2, 0, 0, 0, 1}, {1, 0, 1, 3, 5, 5, 0, 1}, {33, 0, 33, 3, 4, 4, 0, 0}, {1, 0, 1, 3, 5, 5, 0, 0}};
    private static final short[] impAct0 = new short[]{0, 1, 2, 3, 4};
    private static final ImpTabPair impTab_DEFAULT = new ImpTabPair(impTabL_DEFAULT, impTabR_DEFAULT, impAct0, impAct0);
    private static final byte[][] impTabL_NUMBERS_SPECIAL = new byte[][]{{0, 2, 17, 17, 0, 0, 0, 0}, {0, 66, 1, 1, 0, 0, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 1}, {0, 34, 52, 52, 3, 3, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 2}};
    private static final ImpTabPair impTab_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_DEFAULT, impAct0, impAct0);
    private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R = new byte[][]{{0, 3, 17, 17, 0, 0, 0, 0}, {32, 3, 1, 1, 2, 32, 32, 2}, {32, 3, 1, 1, 2, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 1}, {32, 3, 5, 5, 4, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 2}};
    private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R = new byte[][]{{2, 0, 1, 1, 0, 0, 0, 0}, {2, 0, 1, 1, 0, 0, 0, 1}, {2, 0, 20, 20, 19, 0, 0, 1}, {34, 0, 4, 4, 3, 0, 0, 0}, {34, 0, 4, 4, 3, 0, 0, 1}};
    private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair(impTabL_GROUP_NUMBERS_WITH_R, impTabR_GROUP_NUMBERS_WITH_R, impAct0, impAct0);
    private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L = new byte[][]{{0, 1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 20, 20, 0, 1}, {0, 1, 0, 0, 21, 21, 0, 2}, {0, 1, 0, 0, 20, 20, 0, 2}, {32, 1, 32, 32, 4, 4, 32, 1}, {32, 1, 32, 32, 5, 5, 32, 1}};
    private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L = new byte[][]{{1, 0, 1, 1, 0, 0, 0, 0}, {1, 0, 1, 1, 20, 20, 0, 1}, {1, 0, 1, 1, 0, 0, 0, 1}, {1, 0, 1, 1, 5, 5, 0, 1}, {33, 0, 33, 33, 4, 4, 0, 0}, {1, 0, 1, 1, 5, 5, 0, 0}};
    private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair(impTabL_INVERSE_NUMBERS_AS_L, impTabR_INVERSE_NUMBERS_AS_L, impAct0, impAct0);
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT = new byte[][]{{1, 0, 2, 2, 0, 0, 0, 0}, {1, 0, 1, 2, 19, 19, 0, 1}, {1, 0, 2, 2, 0, 0, 0, 1}, {33, 48, 6, 4, 3, 3, 48, 0}, {33, 48, 6, 4, 5, 5, 48, 3}, {33, 48, 6, 4, 5, 5, 48, 2}, {33, 48, 6, 4, 3, 3, 48, 1}};
    private static final short[] impAct1 = new short[]{0, 1, 13, 14};
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
    private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{{0, 99, 0, 1, 0, 0, 0, 0}, {0, 99, 0, 1, 18, 48, 0, 4}, {32, 99, 32, 1, 2, 48, 32, 3}, {0, 99, 85, 86, 20, 48, 0, 3}, {48, 67, 85, 86, 4, 48, 48, 3}, {48, 67, 5, 86, 20, 48, 48, 4}, {48, 67, 85, 6, 20, 48, 48, 4}};
    private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{{19, 0, 1, 1, 0, 0, 0, 0}, {35, 0, 1, 1, 2, 64, 0, 1}, {35, 0, 1, 1, 2, 64, 0, 0}, {3, 0, 3, 54, 20, 64, 0, 1}, {83, 64, 5, 54, 4, 64, 64, 0}, {83, 64, 5, 54, 4, 64, 64, 1}, {83, 64, 6, 6, 4, 64, 64, 3}};
    private static final short[] impAct2 = new short[]{0, 1, 2, 5, 6, 7, 8};
    private static final short[] impAct3 = new short[]{0, 1, 9, 10, 11, 12};
    private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
    private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new byte[][]{{0, 98, 1, 1, 0, 0, 0, 0}, {0, 98, 1, 1, 0, 48, 0, 4}, {0, 98, 84, 84, 19, 48, 0, 3}, {48, 66, 84, 84, 3, 48, 48, 3}, {48, 66, 4, 4, 19, 48, 48, 4}};
    private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
    static final int FIRSTALLOC = 10;
    public static final int DIRECTION_LEFT_TO_RIGHT = 0;
    public static final int DIRECTION_RIGHT_TO_LEFT = 1;
    public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
    public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;

    static int DirPropFlag(byte by) {
        return 1 << by;
    }

    boolean testDirPropFlagAt(int n, int n2) {
        return (Bidi.DirPropFlag(this.dirProps[n2]) & n) != 0;
    }

    static final int DirPropFlagLR(byte by) {
        return DirPropFlagLR[by & 1];
    }

    static final int DirPropFlagE(byte by) {
        return DirPropFlagE[by & 1];
    }

    static final int DirPropFlagO(byte by) {
        return DirPropFlagO[by & 1];
    }

    static final byte DirFromStrong(byte by) {
        return by == 0 ? (byte)0 : 1;
    }

    static final byte NoOverride(byte by) {
        return (byte)(by & 0x7F);
    }

    static byte GetLRFromLevel(byte by) {
        return (byte)(by & 1);
    }

    static boolean IsDefaultLevel(byte by) {
        return (by & 0x7E) == 126;
    }

    static boolean IsBidiControlChar(int n) {
        return (n & 0xFFFFFFFC) == 8204 || n >= 8234 && n <= 8238 || n >= 8294 && n <= 8297;
    }

    void verifyValidPara() {
        if (this != this.paraBidi) {
            throw new IllegalStateException();
        }
    }

    void verifyValidParaOrLine() {
        Bidi bidi = this.paraBidi;
        if (this == bidi) {
            return;
        }
        if (bidi == null || bidi != bidi.paraBidi) {
            throw new IllegalStateException();
        }
    }

    void verifyRange(int n, int n2, int n3) {
        if (n < n2 || n >= n3) {
            throw new IllegalArgumentException("Value " + n + " is out of range " + n2 + " to " + n3);
        }
    }

    public Bidi() {
        this(0, 0);
    }

    public Bidi(int n, int n2) {
        if (n < 0 || n2 < 0) {
            throw new IllegalArgumentException();
        }
        this.bdp = UBiDiProps.INSTANCE;
        if (n > 0) {
            this.getInitialDirPropsMemory(n);
            this.getInitialLevelsMemory(n);
        } else {
            this.mayAllocateText = true;
        }
        if (n2 > 0) {
            if (n2 > 1) {
                this.getInitialRunsMemory(n2);
            }
        } else {
            this.mayAllocateRuns = true;
        }
    }

    private Object getMemory(String string, Object object, Class<?> clazz, boolean bl, int n) {
        int n2 = Array.getLength(object);
        if (n == n2) {
            return object;
        }
        if (!bl) {
            if (n <= n2) {
                return object;
            }
            throw new OutOfMemoryError("Failed to allocate memory for " + string);
        }
        try {
            return Array.newInstance(clazz, n);
        } catch (Exception exception) {
            throw new OutOfMemoryError("Failed to allocate memory for " + string);
        }
    }

    private void getDirPropsMemory(boolean bl, int n) {
        Object object = this.getMemory("DirProps", this.dirPropsMemory, Byte.TYPE, bl, n);
        this.dirPropsMemory = (byte[])object;
    }

    void getDirPropsMemory(int n) {
        this.getDirPropsMemory(this.mayAllocateText, n);
    }

    private void getLevelsMemory(boolean bl, int n) {
        Object object = this.getMemory("Levels", this.levelsMemory, Byte.TYPE, bl, n);
        this.levelsMemory = (byte[])object;
    }

    void getLevelsMemory(int n) {
        this.getLevelsMemory(this.mayAllocateText, n);
    }

    private void getRunsMemory(boolean bl, int n) {
        Object object = this.getMemory("Runs", this.runsMemory, BidiRun.class, bl, n);
        this.runsMemory = (BidiRun[])object;
    }

    void getRunsMemory(int n) {
        this.getRunsMemory(this.mayAllocateRuns, n);
    }

    private void getInitialDirPropsMemory(int n) {
        this.getDirPropsMemory(true, n);
    }

    private void getInitialLevelsMemory(int n) {
        this.getLevelsMemory(true, n);
    }

    private void getInitialRunsMemory(int n) {
        this.getRunsMemory(true, n);
    }

    public void setInverse(boolean bl) {
        this.isInverse = bl;
        this.reorderingMode = bl ? 4 : 0;
    }

    public boolean isInverse() {
        return this.isInverse;
    }

    public void setReorderingMode(int n) {
        if (n < 0 || n >= 7) {
            return;
        }
        this.reorderingMode = n;
        this.isInverse = n == 4;
    }

    public int getReorderingMode() {
        return this.reorderingMode;
    }

    public void setReorderingOptions(int n) {
        this.reorderingOptions = (n & 2) != 0 ? n & 0xFFFFFFFE : n;
    }

    public int getReorderingOptions() {
        return this.reorderingOptions;
    }

    public static byte getBaseDirection(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return 0;
        }
        int n = charSequence.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = UCharacter.codePointAt(charSequence, n2);
            byte by = UCharacter.getDirectionality(n3);
            if (by == 0) {
                return 1;
            }
            if (by == 1 || by == 13) {
                return 0;
            }
            n2 = UCharacter.offsetByCodePoints(charSequence, n2, 1);
        }
        return 0;
    }

    private byte firstL_R_AL() {
        int n;
        int n2 = 10;
        for (int i = 0; i < this.prologue.length(); i += Character.charCount(n)) {
            n = this.prologue.codePointAt(i);
            int n3 = this.getCustomizedClass(n);
            if (n2 != 10) continue;
            if (n3 != 0 && n3 != 1 && n3 != 13) continue;
            n2 = n3;
        }
        return (byte)n2;
    }

    private void checkParaCount() {
        int n = this.paraCount;
        if (n <= this.paras_level.length) {
            return;
        }
        int n2 = this.paras_level.length;
        int[] nArray = this.paras_limit;
        byte[] byArray = this.paras_level;
        try {
            this.paras_limit = new int[n * 2];
            this.paras_level = new byte[n * 2];
        } catch (Exception exception) {
            throw new OutOfMemoryError("Failed to allocate memory for paras");
        }
        System.arraycopy(nArray, 0, this.paras_limit, 0, n2);
        System.arraycopy(byArray, 0, this.paras_level, 0, n2);
    }

    private void getDirProps() {
        int n;
        byte by;
        int n2 = 0;
        this.flags = 0;
        byte by2 = 0;
        boolean bl = Bidi.IsDefaultLevel(this.paraLevel);
        boolean bl2 = bl && (this.reorderingMode == 5 || this.reorderingMode == 6);
        this.lastArabicPos = -1;
        int n3 = 0;
        boolean bl3 = (this.reorderingOptions & 2) != 0;
        int n4 = 10;
        int[] nArray = new int[126];
        byte[] byArray = new byte[126];
        int n5 = -1;
        if ((this.reorderingOptions & 4) != 0) {
            this.length = 0;
        }
        by2 = (byte)(this.paraLevel & 1);
        if (bl) {
            this.paras_level[0] = by2;
            n4 = by2;
            if (this.prologue != null && (by = this.firstL_R_AL()) != 10) {
                this.paras_level[0] = by == 0 ? (byte)0 : 1;
                n = 0;
            } else {
                n = 1;
            }
        } else {
            this.paras_level[0] = this.paraLevel;
            n = 0;
        }
        n2 = 0;
        while (n2 < this.originalLength) {
            int n6 = n2;
            int n7 = UTF16.charAt(this.text, 0, this.originalLength, n2);
            int n8 = (n2 += UTF16.getCharCount(n7)) - 1;
            by = (byte)this.getCustomizedClass(n7);
            this.flags |= Bidi.DirPropFlag(by);
            this.dirProps[n8] = by;
            if (n8 > n6) {
                this.flags |= Bidi.DirPropFlag((byte)18);
                do {
                    this.dirProps[--n8] = 18;
                } while (n8 > n6);
            }
            if (bl3 && Bidi.IsBidiControlChar(n7)) {
                ++n3;
            }
            if (by == 0) {
                if (n == 1) {
                    this.paras_level[this.paraCount - 1] = 0;
                    n = 0;
                } else if (n == 2) {
                    if (n5 <= 125) {
                        this.flags |= Bidi.DirPropFlag((byte)20);
                    }
                    n = 3;
                }
                n4 = 0;
                continue;
            }
            if (by == 1 || by == 13) {
                if (n == 1) {
                    this.paras_level[this.paraCount - 1] = 1;
                    n = 0;
                } else if (n == 2) {
                    if (n5 <= 125) {
                        this.dirProps[nArray[n5]] = 21;
                        this.flags |= Bidi.DirPropFlag((byte)21);
                    }
                    n = 3;
                }
                n4 = 1;
                if (by != 13) continue;
                this.lastArabicPos = n2 - 1;
                continue;
            }
            if (by >= 19 && by <= 21) {
                if (++n5 <= 125) {
                    nArray[n5] = n2 - 1;
                    byArray[n5] = n;
                }
                if (by == 19) {
                    this.dirProps[n2 - 1] = 20;
                    n = 2;
                    continue;
                }
                n = 3;
                continue;
            }
            if (by == 22) {
                if (n == 2 && n5 <= 125) {
                    this.flags |= Bidi.DirPropFlag((byte)20);
                }
                if (n5 < 0) continue;
                if (n5 <= 125) {
                    n = byArray[n5];
                }
                --n5;
                continue;
            }
            if (by != 7 || n2 < this.originalLength && n7 == 13 && this.text[n2] == '\n') continue;
            this.paras_limit[this.paraCount - 1] = n2;
            if (bl2 && n4 == 1) {
                this.paras_level[this.paraCount - 1] = 1;
            }
            if ((this.reorderingOptions & 4) != 0) {
                this.length = n2;
                this.controlCount = n3;
            }
            if (n2 >= this.originalLength) continue;
            ++this.paraCount;
            this.checkParaCount();
            if (bl) {
                this.paras_level[this.paraCount - 1] = by2;
                n = 1;
                n4 = by2;
            } else {
                this.paras_level[this.paraCount - 1] = this.paraLevel;
                n = 0;
            }
            n5 = -1;
        }
        if (n5 > 125) {
            n5 = 125;
            n = 2;
        }
        while (n5 >= 0) {
            if (n == 2) {
                this.flags |= Bidi.DirPropFlag((byte)20);
                break;
            }
            n = byArray[n5];
            --n5;
        }
        if ((this.reorderingOptions & 4) != 0) {
            if (this.length < this.originalLength) {
                --this.paraCount;
            }
        } else {
            this.paras_limit[this.paraCount - 1] = this.originalLength;
            this.controlCount = n3;
        }
        if (bl2 && n4 == 1) {
            this.paras_level[this.paraCount - 1] = 1;
        }
        if (bl) {
            this.paraLevel = this.paras_level[0];
        }
        for (n2 = 0; n2 < this.paraCount; ++n2) {
            this.flags |= Bidi.DirPropFlagLR(this.paras_level[n2]);
        }
        if (this.orderParagraphsLTR && (this.flags & Bidi.DirPropFlag((byte)7)) != 0) {
            this.flags |= Bidi.DirPropFlag((byte)0);
        }
    }

    byte GetParaLevelAt(int n) {
        int n2;
        if (this.defaultParaLevel == 0 || n < this.paras_limit[0]) {
            return this.paraLevel;
        }
        for (n2 = 1; n2 < this.paraCount && n >= this.paras_limit[n2]; ++n2) {
        }
        if (n2 >= this.paraCount) {
            n2 = this.paraCount - 1;
        }
        return this.paras_level[n2];
    }

    private void bracketInit(BracketData bracketData) {
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0] = new IsoRun();
        bracketData.isoRuns[0].start = 0;
        bracketData.isoRuns[0].limit = 0;
        bracketData.isoRuns[0].level = this.GetParaLevelAt(0);
        bracketData.isoRuns[0].lastBase = bracketData.isoRuns[0].contextDir = (byte)(this.GetParaLevelAt(0) & 1);
        bracketData.isoRuns[0].lastStrong = bracketData.isoRuns[0].contextDir;
        bracketData.isoRuns[0].contextPos = 0;
        bracketData.openings = new Opening[20];
        bracketData.isNumbersSpecial = this.reorderingMode == 1 || this.reorderingMode == 6;
    }

    private void bracketProcessB(BracketData bracketData, byte by) {
        bracketData.isoRunLast = 0;
        bracketData.isoRuns[0].limit = 0;
        bracketData.isoRuns[0].level = by;
        bracketData.isoRuns[0].lastBase = bracketData.isoRuns[0].contextDir = (byte)(by & 1);
        bracketData.isoRuns[0].lastStrong = bracketData.isoRuns[0].contextDir;
        bracketData.isoRuns[0].contextPos = 0;
    }

    private void bracketProcessBoundary(BracketData bracketData, int n, byte by, byte by2) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        if ((Bidi.DirPropFlag(this.dirProps[n]) & MASK_ISO) != 0) {
            return;
        }
        if (Bidi.NoOverride(by2) > Bidi.NoOverride(by)) {
            by = by2;
        }
        isoRun.limit = isoRun.start;
        isoRun.level = by2;
        isoRun.lastBase = isoRun.contextDir = (byte)(by & 1);
        isoRun.lastStrong = isoRun.contextDir;
        isoRun.contextPos = n;
    }

    private void bracketProcessLRI_RLI(BracketData bracketData, byte by) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        isoRun.lastBase = (byte)10;
        short s = isoRun.limit;
        ++bracketData.isoRunLast;
        isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        if (isoRun == null) {
            isoRun = bracketData.isoRuns[bracketData.isoRunLast] = new IsoRun();
        }
        isoRun.start = isoRun.limit = s;
        isoRun.level = by;
        isoRun.lastBase = isoRun.contextDir = (byte)(by & 1);
        isoRun.lastStrong = isoRun.contextDir;
        isoRun.contextPos = 0;
    }

    private void bracketProcessPDI(BracketData bracketData) {
        --bracketData.isoRunLast;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        isoRun.lastBase = (byte)10;
    }

    private void bracketAddOpening(BracketData bracketData, char c, int n) {
        Opening opening;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        if (isoRun.limit >= bracketData.openings.length) {
            int n2;
            Opening[] openingArray = bracketData.openings;
            try {
                n2 = bracketData.openings.length;
                bracketData.openings = new Opening[n2 * 2];
            } catch (Exception exception) {
                throw new OutOfMemoryError("Failed to allocate memory for openings");
            }
            System.arraycopy(openingArray, 0, bracketData.openings, 0, n2);
        }
        if ((opening = bracketData.openings[isoRun.limit]) == null) {
            opening = bracketData.openings[isoRun.limit] = new Opening();
        }
        opening.position = n;
        opening.match = c;
        opening.contextDir = isoRun.contextDir;
        opening.contextPos = isoRun.contextPos;
        opening.flags = 0;
        isoRun.limit = (short)(isoRun.limit + 1);
    }

    private void fixN0c(BracketData bracketData, int n, int n2, byte by) {
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        for (int i = n + 1; i < isoRun.limit; ++i) {
            Opening opening = bracketData.openings[i];
            if (opening.match >= 0) continue;
            if (n2 < opening.contextPos) break;
            if (n2 >= opening.position) continue;
            if (by == opening.contextDir) break;
            int n3 = opening.position;
            this.dirProps[n3] = by;
            int n4 = -opening.match;
            this.dirProps[n4] = by;
            opening.match = 0;
            this.fixN0c(bracketData, i, n3, by);
            this.fixN0c(bracketData, i, n4, by);
        }
    }

    private byte bracketProcessClosing(BracketData bracketData, int n, int n2) {
        byte by;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        Opening opening = bracketData.openings[n];
        byte by2 = (byte)(isoRun.level & 1);
        boolean bl = true;
        if (by2 == 0 && (opening.flags & FOUND_L) > 0 || by2 == 1 && (opening.flags & FOUND_R) > 0) {
            by = by2;
        } else if ((opening.flags & (FOUND_L | FOUND_R)) != 0) {
            boolean bl2 = bl = n == isoRun.start;
            by = by2 != opening.contextDir ? opening.contextDir : by2;
        } else {
            isoRun.limit = (short)n;
            return 1;
        }
        this.dirProps[opening.position] = by;
        this.dirProps[n2] = by;
        this.fixN0c(bracketData, n, opening.position, by);
        if (bl) {
            isoRun.limit = (short)n;
            while (isoRun.limit > isoRun.start && bracketData.openings[isoRun.limit - 1].position == opening.position) {
                isoRun.limit = (short)(isoRun.limit - 1);
            }
        } else {
            opening.match = -n2;
            int n3 = n - 1;
            while (n3 >= isoRun.start && bracketData.openings[n3].position == opening.position) {
                bracketData.openings[n3--].match = 0;
            }
            for (n3 = n + 1; n3 < isoRun.limit; ++n3) {
                Opening opening2 = bracketData.openings[n3];
                if (opening2.position < n2) {
                    if (opening2.match <= 0) continue;
                    opening2.match = 0;
                    continue;
                }
                break;
            }
        }
        return by;
    }

    private void bracketProcessChar(BracketData bracketData, int n) {
        byte by;
        char c;
        byte by2;
        int n2;
        IsoRun isoRun = bracketData.isoRuns[bracketData.isoRunLast];
        byte by3 = this.dirProps[n];
        if (by3 == 10) {
            n2 = this.text[n];
            for (int i = isoRun.limit - 1; i >= isoRun.start; --i) {
                if (bracketData.openings[i].match != n2) continue;
                by2 = this.bracketProcessClosing(bracketData, i, n);
                if (by2 == 10) {
                    n2 = 0;
                    break;
                }
                isoRun.lastBase = (byte)10;
                isoRun.contextDir = by2;
                isoRun.contextPos = n;
                byte by4 = this.levels[n];
                if ((by4 & 0xFFFFFF80) != 0) {
                    isoRun.lastStrong = by2 = (byte)(by4 & 1);
                    short s = (short)Bidi.DirPropFlag(by2);
                    for (int j = isoRun.start; j < i; ++j) {
                        bracketData.openings[j].flags = (short)(bracketData.openings[j].flags | s);
                    }
                    int n3 = n;
                    this.levels[n3] = (byte)(this.levels[n3] & 0x7F);
                }
                int n4 = bracketData.openings[i].position;
                this.levels[n4] = (byte)(this.levels[n4] & 0x7F);
                return;
            }
            if ((c = n2 != 0 ? (char)UCharacter.getBidiPairedBracket(n2) : (char)'\u0000') != n2 && UCharacter.getIntPropertyValue(n2, 4117) == 1) {
                if (c == '\u232a') {
                    this.bracketAddOpening(bracketData, '\u3009', n);
                } else if (c == '\u3009') {
                    this.bracketAddOpening(bracketData, '\u232a', n);
                }
                this.bracketAddOpening(bracketData, c, n);
            }
        }
        if (((by = this.levels[n]) & 0xFFFFFF80) != 0) {
            by2 = (byte)(by & 1);
            if (by3 != 8 && by3 != 9 && by3 != 10) {
                this.dirProps[n] = by2;
            }
            isoRun.lastBase = by2;
            isoRun.lastStrong = by2;
            isoRun.contextDir = by2;
            isoRun.contextPos = n;
        } else if (by3 <= 1 || by3 == 13) {
            by2 = Bidi.DirFromStrong(by3);
            isoRun.lastBase = by3;
            isoRun.lastStrong = by3;
            isoRun.contextDir = by2;
            isoRun.contextPos = n;
        } else if (by3 == 2) {
            isoRun.lastBase = (byte)2;
            if (isoRun.lastStrong == 0) {
                by2 = 0;
                if (!bracketData.isNumbersSpecial) {
                    this.dirProps[n] = 23;
                }
                isoRun.contextDir = 0;
                isoRun.contextPos = n;
            } else {
                by2 = 1;
                this.dirProps[n] = isoRun.lastStrong == 13 ? 5 : 24;
                isoRun.contextDir = 1;
                isoRun.contextPos = n;
            }
        } else if (by3 == 5) {
            by2 = 1;
            isoRun.lastBase = (byte)5;
            isoRun.contextDir = 1;
            isoRun.contextPos = n;
        } else if (by3 == 17) {
            by2 = isoRun.lastBase;
            if (by2 == 10) {
                this.dirProps[n] = by2;
            }
        } else {
            by2 = by3;
            isoRun.lastBase = by3;
        }
        if (by2 <= 1 || by2 == 13) {
            c = (char)Bidi.DirPropFlag(Bidi.DirFromStrong(by2));
            for (n2 = isoRun.start; n2 < isoRun.limit; ++n2) {
                if (n <= bracketData.openings[n2].position) continue;
                bracketData.openings[n2].flags = (short)(bracketData.openings[n2].flags | c);
            }
        }
    }

    private byte directionFromFlags() {
        if ((this.flags & MASK_RTL) == 0 && ((this.flags & Bidi.DirPropFlag((byte)5)) == 0 || (this.flags & MASK_POSSIBLE_N) == 0)) {
            return 1;
        }
        if ((this.flags & MASK_LTR) == 0) {
            return 0;
        }
        return 1;
    }

    private byte resolveExplicitLevels() {
        int n = 0;
        byte by = this.GetParaLevelAt(0);
        this.isolateCount = 0;
        byte by2 = this.directionFromFlags();
        if (by2 != 2) {
            return by2;
        }
        if (this.reorderingMode > 1) {
            for (int i = 0; i < this.paraCount; ++i) {
                int n2 = i == 0 ? 0 : this.paras_limit[i - 1];
                int n3 = this.paras_limit[i];
                by = this.paras_level[i];
                for (n = n2; n < n3; ++n) {
                    this.levels[n] = by;
                }
            }
            return by2;
        }
        if ((this.flags & (MASK_EXPLICIT | MASK_ISO)) == 0) {
            BracketData bracketData = new BracketData();
            this.bracketInit(bracketData);
            for (int i = 0; i < this.paraCount; ++i) {
                int n4 = i == 0 ? 0 : this.paras_limit[i - 1];
                int n5 = this.paras_limit[i];
                by = this.paras_level[i];
                for (n = n4; n < n5; ++n) {
                    this.levels[n] = by;
                    byte by3 = this.dirProps[n];
                    if (by3 == 18) continue;
                    if (by3 == 7) {
                        if (n + 1 >= this.length || this.text[n] == '\r' && this.text[n + 1] == '\n') continue;
                        this.bracketProcessB(bracketData, by);
                        continue;
                    }
                    this.bracketProcessChar(bracketData, n);
                }
            }
            return by2;
        }
        byte by4 = by;
        byte by5 = by;
        int n6 = 0;
        short[] sArray = new short[127];
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        BracketData bracketData = new BracketData();
        this.bracketInit(bracketData);
        sArray[0] = by;
        this.flags = 0;
        block12: for (n = 0; n < this.length; ++n) {
            byte by6 = this.dirProps[n];
            switch (by6) {
                case 11: 
                case 12: 
                case 14: 
                case 15: {
                    this.flags |= Bidi.DirPropFlag((byte)18);
                    this.levels[n] = by5;
                    byte by7 = by6 == 11 || by6 == 12 ? (byte)(by4 + 2 & 0x7E) : (byte)(Bidi.NoOverride(by4) + 1 | 1);
                    if (by7 <= 125 && n8 == 0 && n9 == 0) {
                        n6 = n;
                        by4 = by7;
                        if (by6 == 12 || by6 == 15) {
                            by4 = (byte)(by4 | 0xFFFFFF80);
                        }
                        sArray[++n7] = by4;
                        continue block12;
                    }
                    if (n8 != 0) continue block12;
                    ++n9;
                    continue block12;
                }
                case 16: {
                    this.flags |= Bidi.DirPropFlag((byte)18);
                    this.levels[n] = by5;
                    if (n8 > 0) continue block12;
                    if (n9 > 0) {
                        --n9;
                        continue block12;
                    }
                    if (n7 <= 0 || sArray[n7] >= 256) continue block12;
                    n6 = n;
                    by4 = (byte)sArray[--n7];
                    continue block12;
                }
                case 20: 
                case 21: {
                    this.flags |= Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlagLR(by4);
                    this.levels[n] = Bidi.NoOverride(by4);
                    if (Bidi.NoOverride(by4) != Bidi.NoOverride(by5)) {
                        this.bracketProcessBoundary(bracketData, n6, by5, by4);
                        this.flags |= DirPropFlagMultiRuns;
                    }
                    by5 = by4;
                    byte by7 = by6 == 20 ? (byte)(by4 + 2 & 0x7E) : (byte)(Bidi.NoOverride(by4) + 1 | 1);
                    if (by7 <= 125 && n8 == 0 && n9 == 0) {
                        this.flags |= Bidi.DirPropFlag(by6);
                        n6 = n;
                        if (++n10 > this.isolateCount) {
                            this.isolateCount = n10;
                        }
                        by4 = by7;
                        sArray[++n7] = (short)(by4 + 256);
                        this.bracketProcessLRI_RLI(bracketData, by4);
                        continue block12;
                    }
                    this.dirProps[n] = 9;
                    ++n8;
                    continue block12;
                }
                case 22: {
                    if (Bidi.NoOverride(by4) != Bidi.NoOverride(by5)) {
                        this.bracketProcessBoundary(bracketData, n6, by5, by4);
                        this.flags |= DirPropFlagMultiRuns;
                    }
                    if (n8 > 0) {
                        --n8;
                        this.dirProps[n] = 9;
                    } else if (n10 > 0) {
                        this.flags |= Bidi.DirPropFlag((byte)22);
                        n6 = n;
                        n9 = 0;
                        while (sArray[n7] < 256) {
                            --n7;
                        }
                        --n7;
                        --n10;
                        this.bracketProcessPDI(bracketData);
                    } else {
                        this.dirProps[n] = 9;
                    }
                    by4 = (byte)(sArray[n7] & 0xFFFFFEFF);
                    this.flags |= Bidi.DirPropFlag((byte)10) | Bidi.DirPropFlagLR(by4);
                    by5 = by4;
                    this.levels[n] = Bidi.NoOverride(by4);
                    continue block12;
                }
                case 7: {
                    this.flags |= Bidi.DirPropFlag((byte)7);
                    this.levels[n] = this.GetParaLevelAt(n);
                    if (n + 1 >= this.length || this.text[n] == '\r' && this.text[n + 1] == '\n') continue block12;
                    n8 = 0;
                    n9 = 0;
                    n10 = 0;
                    n7 = 0;
                    by5 = by4 = this.GetParaLevelAt(n + 1);
                    sArray[0] = by4;
                    this.bracketProcessB(bracketData, by4);
                    continue block12;
                }
                case 18: {
                    this.levels[n] = by5;
                    this.flags |= Bidi.DirPropFlag((byte)18);
                    continue block12;
                }
                default: {
                    if (Bidi.NoOverride(by4) != Bidi.NoOverride(by5)) {
                        this.bracketProcessBoundary(bracketData, n6, by5, by4);
                        this.flags |= DirPropFlagMultiRuns;
                        this.flags = (by4 & 0xFFFFFF80) != 0 ? (this.flags |= Bidi.DirPropFlagO(by4)) : (this.flags |= Bidi.DirPropFlagE(by4));
                    }
                    by5 = by4;
                    this.levels[n] = by4;
                    this.bracketProcessChar(bracketData, n);
                    this.flags |= Bidi.DirPropFlag(this.dirProps[n]);
                }
            }
        }
        if ((this.flags & MASK_EMBEDDING) != 0) {
            this.flags |= Bidi.DirPropFlagLR(this.paraLevel);
        }
        if (this.orderParagraphsLTR && (this.flags & Bidi.DirPropFlag((byte)7)) != 0) {
            this.flags |= Bidi.DirPropFlag((byte)0);
        }
        by2 = this.directionFromFlags();
        return by2;
    }

    private byte checkExplicitLevels() {
        int n = 0;
        this.flags = 0;
        this.isolateCount = 0;
        int n2 = 0;
        int n3 = this.paras_limit[0];
        byte by = this.paraLevel;
        for (int i = 0; i < this.length; ++i) {
            byte by2 = this.levels[i];
            byte by3 = this.dirProps[i];
            if (by3 == 20 || by3 == 21) {
                if (++n > this.isolateCount) {
                    this.isolateCount = n;
                }
            } else if (by3 == 22) {
                --n;
            } else if (by3 == 7) {
                n = 0;
            }
            if (this.defaultParaLevel != 0 && i == n3 && n2 + 1 < this.paraCount) {
                by = this.paras_level[++n2];
                n3 = this.paras_limit[n2];
            }
            int n4 = by2 & 0xFFFFFF80;
            if ((by2 = (byte)(by2 & 0x7F)) < by || 125 < by2) {
                if (by2 == 0) {
                    if (by3 != 7) {
                        by2 = by;
                        this.levels[i] = (byte)(by2 | n4);
                    }
                } else {
                    throw new IllegalArgumentException("level " + by2 + " out of bounds at " + i);
                }
            }
            if (n4 != 0) {
                this.flags |= Bidi.DirPropFlagO(by2);
                continue;
            }
            this.flags |= Bidi.DirPropFlagE(by2) | Bidi.DirPropFlag(by3);
        }
        if ((this.flags & MASK_EMBEDDING) != 0) {
            this.flags |= Bidi.DirPropFlagLR(this.paraLevel);
        }
        return this.directionFromFlags();
    }

    private static short GetStateProps(short s) {
        return (short)(s & 0x1F);
    }

    private static short GetActionProps(short s) {
        return (short)(s >> 5);
    }

    private static short GetState(byte by) {
        return (short)(by & 0xF);
    }

    private static short GetAction(byte by) {
        return (short)(by >> 4);
    }

    private void addPoint(int n, int n2) {
        Point point = new Point();
        int n3 = this.insertPoints.points.length;
        if (n3 == 0) {
            this.insertPoints.points = new Point[10];
            n3 = 10;
        }
        if (this.insertPoints.size >= n3) {
            Point[] pointArray = this.insertPoints.points;
            this.insertPoints.points = new Point[n3 * 2];
            System.arraycopy(pointArray, 0, this.insertPoints.points, 0, n3);
        }
        point.pos = n;
        point.flag = n2;
        this.insertPoints.points[this.insertPoints.size] = point;
        ++this.insertPoints.size;
    }

    private void setLevelsOutsideIsolates(int n, int n2, byte by) {
        int n3 = 0;
        for (int i = n; i < n2; ++i) {
            byte by2 = this.dirProps[i];
            if (by2 == 22) {
                --n3;
            }
            if (n3 == 0) {
                this.levels[i] = by;
            }
            if (by2 != 20 && by2 != 21) continue;
            ++n3;
        }
    }

    private void processPropertySeq(LevState levState, short s, int n, int n2) {
        int n3;
        byte by;
        byte[][] byArray = levState.impTab;
        short[] sArray = levState.impAct;
        int n4 = n;
        short s2 = levState.state;
        byte by2 = byArray[s2][s];
        levState.state = Bidi.GetState(by2);
        short s3 = sArray[Bidi.GetAction(by2)];
        byte by3 = byArray[levState.state][7];
        if (s3 != 0) {
            switch (s3) {
                case 1: {
                    levState.startON = n4;
                    break;
                }
                case 2: {
                    n = levState.startON;
                    break;
                }
                case 3: {
                    by = (byte)(levState.runLevel + 1);
                    this.setLevelsOutsideIsolates(levState.startON, n4, by);
                    break;
                }
                case 4: {
                    by = (byte)(levState.runLevel + 2);
                    this.setLevelsOutsideIsolates(levState.startON, n4, by);
                    break;
                }
                case 5: {
                    if (levState.startL2EN >= 0) {
                        this.addPoint(levState.startL2EN, 1);
                    }
                    levState.startL2EN = -1;
                    if (this.insertPoints.points.length == 0 || this.insertPoints.size <= this.insertPoints.confirmed) {
                        levState.lastStrongRTL = -1;
                        by = byArray[s2][7];
                        if ((by & 1) != 0 && levState.startON > 0) {
                            n = levState.startON;
                        }
                        if (s != 5) break;
                        this.addPoint(n4, 1);
                        this.insertPoints.confirmed = this.insertPoints.size;
                        break;
                    }
                    for (n3 = levState.lastStrongRTL + 1; n3 < n4; ++n3) {
                        this.levels[n3] = (byte)(this.levels[n3] - 2 & 0xFFFFFFFE);
                    }
                    this.insertPoints.confirmed = this.insertPoints.size;
                    levState.lastStrongRTL = -1;
                    if (s != 5) break;
                    this.addPoint(n4, 1);
                    this.insertPoints.confirmed = this.insertPoints.size;
                    break;
                }
                case 6: {
                    if (this.insertPoints.points.length > 0) {
                        this.insertPoints.size = this.insertPoints.confirmed;
                    }
                    levState.startON = -1;
                    levState.startL2EN = -1;
                    levState.lastStrongRTL = n2 - 1;
                    break;
                }
                case 7: {
                    if (s == 3 && this.dirProps[n4] == 5 && this.reorderingMode != 6) {
                        if (levState.startL2EN == -1) {
                            levState.lastStrongRTL = n2 - 1;
                            break;
                        }
                        if (levState.startL2EN >= 0) {
                            this.addPoint(levState.startL2EN, 1);
                            levState.startL2EN = -2;
                        }
                        this.addPoint(n4, 1);
                        break;
                    }
                    if (levState.startL2EN != -1) break;
                    levState.startL2EN = n4;
                    break;
                }
                case 8: {
                    levState.lastStrongRTL = n2 - 1;
                    levState.startON = -1;
                    break;
                }
                case 9: {
                    for (n3 = n4 - 1; n3 >= 0 && (this.levels[n3] & 1) == 0; --n3) {
                    }
                    if (n3 >= 0) {
                        this.addPoint(n3, 4);
                        this.insertPoints.confirmed = this.insertPoints.size;
                    }
                    levState.startON = n4;
                    break;
                }
                case 10: {
                    this.addPoint(n4, 1);
                    this.addPoint(n4, 2);
                    break;
                }
                case 11: {
                    this.insertPoints.size = this.insertPoints.confirmed;
                    if (s != 5) break;
                    this.addPoint(n4, 4);
                    this.insertPoints.confirmed = this.insertPoints.size;
                    break;
                }
                case 12: {
                    by = (byte)(levState.runLevel + by3);
                    for (n3 = levState.startON; n3 < n4; ++n3) {
                        if (this.levels[n3] >= by) continue;
                        this.levels[n3] = by;
                    }
                    this.insertPoints.confirmed = this.insertPoints.size;
                    levState.startON = n4;
                    break;
                }
                case 13: {
                    by = levState.runLevel;
                    for (n3 = n4 - 1; n3 >= levState.startON; --n3) {
                        if (this.levels[n3] == by + 3) {
                            while (this.levels[n3] == by + 3) {
                                int n5 = n3--;
                                this.levels[n5] = (byte)(this.levels[n5] - 2);
                            }
                            while (this.levels[n3] == by) {
                                --n3;
                            }
                        }
                        this.levels[n3] = this.levels[n3] == by + 2 ? by : (byte)(by + 1);
                    }
                    break;
                }
                case 14: {
                    by = (byte)(levState.runLevel + 1);
                    for (n3 = n4 - 1; n3 >= levState.startON; --n3) {
                        if (this.levels[n3] <= by) continue;
                        int n6 = n3;
                        this.levels[n6] = (byte)(this.levels[n6] - 2);
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException("Internal ICU error in processPropertySeq");
                }
            }
        }
        if (by3 != 0 || n < n4) {
            by = (byte)(levState.runLevel + by3);
            if (n >= levState.runStart) {
                for (n3 = n; n3 < n2; ++n3) {
                    this.levels[n3] = by;
                }
            } else {
                this.setLevelsOutsideIsolates(n, n2, by);
            }
        }
    }

    private byte lastL_R_AL() {
        int n;
        for (int i = this.prologue.length(); i > 0; i -= Character.charCount(n)) {
            n = this.prologue.codePointBefore(i);
            byte by = (byte)this.getCustomizedClass(n);
            if (by == 0) {
                return 1;
            }
            if (by != 1 && by != 13) continue;
            return 0;
        }
        return 1;
    }

    private byte firstL_R_AL_EN_AN() {
        int n;
        for (int i = 0; i < this.epilogue.length(); i += Character.charCount(n)) {
            n = this.epilogue.codePointAt(i);
            byte by = (byte)this.getCustomizedClass(n);
            if (by == 0) {
                return 1;
            }
            if (by == 1 || by == 13) {
                return 0;
            }
            if (by != 2) continue;
            return 1;
        }
        return 1;
    }

    private void resolveImplicitLevels(int n, int n2, short s, short s2) {
        byte by;
        int n3;
        short s3;
        int n4;
        int n5;
        LevState levState = new LevState(null);
        int n6 = 1;
        int n7 = -1;
        boolean bl = n < this.lastArabicPos && (this.GetParaLevelAt(n) & 1) > 0 && (this.reorderingMode == 5 || this.reorderingMode == 6);
        levState.startL2EN = -1;
        levState.lastStrongRTL = -1;
        levState.runStart = n;
        levState.runLevel = this.levels[n];
        levState.impTab = this.impTabPair.imptab[levState.runLevel & 1];
        levState.impAct = this.impTabPair.impact[levState.runLevel & 1];
        if (n == 0 && this.prologue != null && (n5 = this.lastL_R_AL()) != 4) {
            s = (short)n5;
        }
        if (this.dirProps[n] == 22) {
            levState.startON = this.isolates[this.isolateCount].startON;
            n4 = this.isolates[this.isolateCount].start1;
            s3 = this.isolates[this.isolateCount].stateImp;
            levState.state = this.isolates[this.isolateCount].state;
            --this.isolateCount;
        } else {
            levState.startON = -1;
            n4 = n;
            s3 = this.dirProps[n] == 17 ? (short)(1 + s) : (short)0;
            levState.state = 0;
            this.processPropertySeq(levState, s, n, n);
        }
        int n8 = n;
        block6: for (n3 = n; n3 <= n2; ++n3) {
            short s4;
            if (n3 >= n2) {
                for (n5 = n2 - 1; n5 > n && (Bidi.DirPropFlag(this.dirProps[n5]) & MASK_BN_EXPLICIT) != 0; --n5) {
                }
                by = this.dirProps[n5];
                if (by == 20 || by == 21) break;
                s4 = s2;
            } else {
                n5 = this.dirProps[n3];
                if (n5 == 7) {
                    this.isolateCount = -1;
                }
                if (bl) {
                    if (n5 == 13) {
                        n5 = 1;
                    } else if (n5 == 2) {
                        if (n7 <= n3) {
                            n6 = 1;
                            n7 = n2;
                            for (int i = n3 + 1; i < n2; ++i) {
                                byte by2 = this.dirProps[i];
                                if (by2 != 0 && by2 != 1 && by2 != 13) continue;
                                n6 = by2;
                                n7 = i;
                                break;
                            }
                        }
                        if (n6 == 13) {
                            n5 = 5;
                        }
                    }
                }
                s4 = groupProp[n5];
            }
            short s5 = s3;
            short s6 = impTabProps[s5][s4];
            s3 = Bidi.GetStateProps(s6);
            short s7 = Bidi.GetActionProps(s6);
            if (n3 == n2 && s7 == 0) {
                s7 = 1;
            }
            if (s7 == 0) continue;
            short s8 = impTabProps[s5][15];
            switch (s7) {
                case 1: {
                    this.processPropertySeq(levState, s8, n4, n3);
                    n4 = n3;
                    continue block6;
                }
                case 2: {
                    n8 = n3;
                    continue block6;
                }
                case 3: {
                    this.processPropertySeq(levState, s8, n4, n8);
                    this.processPropertySeq(levState, (short)4, n8, n3);
                    n4 = n3;
                    continue block6;
                }
                case 4: {
                    this.processPropertySeq(levState, s8, n4, n8);
                    n4 = n8;
                    n8 = n3;
                    continue block6;
                }
                default: {
                    throw new IllegalStateException("Internal ICU error in resolveImplicitLevels");
                }
            }
        }
        if (n2 == this.length && this.epilogue != null && (n5 = (int)this.firstL_R_AL_EN_AN()) != 4) {
            s2 = (short)n5;
        }
        for (n3 = n2 - 1; n3 > n && (Bidi.DirPropFlag(this.dirProps[n3]) & MASK_BN_EXPLICIT) != 0; --n3) {
        }
        by = this.dirProps[n3];
        if ((by == 20 || by == 21) && n2 < this.length) {
            ++this.isolateCount;
            if (this.isolates[this.isolateCount] == null) {
                this.isolates[this.isolateCount] = new Isolate();
            }
            this.isolates[this.isolateCount].stateImp = s3;
            this.isolates[this.isolateCount].state = levState.state;
            this.isolates[this.isolateCount].start1 = n4;
            this.isolates[this.isolateCount].startON = levState.startON;
        } else {
            this.processPropertySeq(levState, s2, n2, n2);
        }
    }

    private void adjustWSLevels() {
        if ((this.flags & MASK_WS) != 0) {
            int n = this.trailingWSStart;
            block0: while (n > 0) {
                int n2;
                while (n > 0 && ((n2 = Bidi.DirPropFlag(this.dirProps[--n])) & MASK_WS) != 0) {
                    if (this.orderParagraphsLTR && (n2 & Bidi.DirPropFlag((byte)7)) != 0) {
                        this.levels[n] = 0;
                        continue;
                    }
                    this.levels[n] = this.GetParaLevelAt(n);
                }
                while (n > 0) {
                    if (((n2 = Bidi.DirPropFlag(this.dirProps[--n])) & MASK_BN_EXPLICIT) != 0) {
                        this.levels[n] = this.levels[n + 1];
                        continue;
                    }
                    if (this.orderParagraphsLTR && (n2 & Bidi.DirPropFlag((byte)7)) != 0) {
                        this.levels[n] = 0;
                        continue block0;
                    }
                    if ((n2 & MASK_B_S) == 0) continue;
                    this.levels[n] = this.GetParaLevelAt(n);
                    continue block0;
                }
            }
        }
    }

    public void setContext(String string, String string2) {
        this.prologue = string != null && string.length() > 0 ? string : null;
        this.epilogue = string2 != null && string2.length() > 0 ? string2 : null;
    }

    private void setParaSuccess() {
        this.prologue = null;
        this.epilogue = null;
        this.paraBidi = this;
    }

    int Bidi_Min(int n, int n2) {
        return n < n2 ? n : n2;
    }

    int Bidi_Abs(int n) {
        return n >= 0 ? n : -n;
    }

    void setParaRunsOnly(char[] cArray, byte by) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        this.reorderingMode = 0;
        int n6 = cArray.length;
        if (n6 == 0) {
            this.setPara(cArray, by, null);
            this.reorderingMode = 3;
            return;
        }
        int n7 = this.reorderingOptions;
        if ((n7 & 1) > 0) {
            this.reorderingOptions &= 0xFFFFFFFE;
            this.reorderingOptions |= 2;
        }
        by = (byte)(by & 1);
        this.setPara(cArray, by, null);
        byte[] byArray = new byte[this.length];
        System.arraycopy(this.getLevels(), 0, byArray, 0, this.length);
        int n8 = this.trailingWSStart;
        String string = this.writeReordered(2);
        int[] nArray = this.getVisualMap();
        this.reorderingOptions = n7;
        int n9 = this.length;
        byte by2 = this.direction;
        this.reorderingMode = 5;
        by = (byte)(by ^ 1);
        this.setPara(string, by, null);
        BidiLine.getRuns(this);
        int n10 = 0;
        int n11 = this.runCount;
        int n12 = 0;
        int n13 = 0;
        while (n13 < n11) {
            n5 = this.runs[n13].limit - n12;
            if (n5 >= 2) {
                n4 = this.runs[n13].start;
                for (n3 = n4 + 1; n3 < n4 + n5; ++n3) {
                    n2 = nArray[n3];
                    n = nArray[n3 - 1];
                    if (this.Bidi_Abs(n2 - n) == 1 && byArray[n2] == byArray[n]) continue;
                    ++n10;
                }
            }
            ++n13;
            n12 += n5;
        }
        if (n10 > 0) {
            this.getRunsMemory(n11 + n10);
            if (this.runCount == 1) {
                this.runsMemory[0] = this.runs[0];
            } else {
                System.arraycopy(this.runs, 0, this.runsMemory, 0, this.runCount);
            }
            this.runs = this.runsMemory;
            this.runCount += n10;
            for (n13 = n11; n13 < this.runCount; ++n13) {
                if (this.runs[n13] != null) continue;
                this.runs[n13] = new BidiRun(0, 0, 0);
            }
        }
        for (n13 = n11 - 1; n13 >= 0; --n13) {
            int n14;
            int n15;
            int n16;
            int n17;
            int n18 = n13 + n10;
            n5 = n13 == 0 ? this.runs[0].limit : this.runs[n13].limit - this.runs[n13 - 1].limit;
            n4 = this.runs[n13].start;
            int n19 = this.runs[n13].level & 1;
            if (n5 < 2) {
                if (n10 > 0) {
                    this.runs[n18].copyFrom(this.runs[n13]);
                }
                this.runs[n18].start = n17 = nArray[n4];
                this.runs[n18].level = (byte)(byArray[n17] ^ n19);
                continue;
            }
            if (n19 > 0) {
                n16 = n4;
                n15 = n4 + n5 - 1;
                n14 = 1;
            } else {
                n16 = n4 + n5 - 1;
                n15 = n4;
                n14 = -1;
            }
            for (n3 = n16; n3 != n15; n3 += n14) {
                int n20;
                n2 = nArray[n3];
                n = nArray[n3 + n14];
                if (this.Bidi_Abs(n2 - n) == 1 && byArray[n2] == byArray[n]) continue;
                this.runs[n18].start = n17 = this.Bidi_Min(nArray[n16], n2);
                this.runs[n18].level = (byte)(byArray[n17] ^ n19);
                this.runs[n18].limit = this.runs[n13].limit;
                this.runs[n13].limit -= this.Bidi_Abs(n3 - n16) + 1;
                this.runs[n18].insertRemove = n20 = this.runs[n13].insertRemove & 0xA;
                this.runs[n13].insertRemove &= ~n20;
                n16 = n3 + n14;
                --n10;
                --n18;
            }
            if (n10 > 0) {
                this.runs[n18].copyFrom(this.runs[n13]);
            }
            this.runs[n18].start = n17 = this.Bidi_Min(nArray[n16], nArray[n15]);
            this.runs[n18].level = (byte)(byArray[n17] ^ n19);
        }
        this.paraLevel = (byte)(this.paraLevel ^ 1);
        this.text = cArray;
        this.length = n9;
        this.originalLength = n6;
        this.direction = by2;
        this.levels = byArray;
        this.trailingWSStart = n8;
        if (this.runCount > 1) {
            this.direction = (byte)2;
        }
        this.reorderingMode = 3;
    }

    public void setPara(String string, byte by, byte[] byArray) {
        if (string == null) {
            this.setPara(new char[0], by, byArray);
        } else {
            this.setPara(string.toCharArray(), by, byArray);
        }
    }

    public void setPara(char[] cArray, byte by, byte[] byArray) {
        short s;
        short s2;
        short s3;
        byte by2;
        byte by3;
        short s4;
        if (by < 126) {
            this.verifyRange(by, 0, 126);
        }
        if (cArray == null) {
            cArray = new char[]{};
        }
        if (this.reorderingMode == 3) {
            this.setParaRunsOnly(cArray, by);
            return;
        }
        this.paraBidi = null;
        this.text = cArray;
        this.originalLength = this.resultLength = this.text.length;
        this.length = this.resultLength;
        this.paraLevel = by;
        this.direction = (byte)(by & 1);
        this.paraCount = 1;
        this.dirProps = new byte[0];
        this.levels = new byte[0];
        this.runs = new BidiRun[0];
        this.isGoodLogicalToVisualRunsMap = false;
        this.insertPoints.size = 0;
        this.insertPoints.confirmed = 0;
        byte by4 = this.defaultParaLevel = Bidi.IsDefaultLevel(by) ? by : (byte)0;
        if (this.length == 0) {
            if (Bidi.IsDefaultLevel(by)) {
                this.paraLevel = (byte)(this.paraLevel & 1);
                this.defaultParaLevel = 0;
            }
            this.flags = Bidi.DirPropFlagLR(by);
            this.runCount = 0;
            this.paraCount = 0;
            this.setParaSuccess();
            return;
        }
        this.runCount = -1;
        this.getDirPropsMemory(this.length);
        this.dirProps = this.dirPropsMemory;
        this.getDirProps();
        this.trailingWSStart = this.length;
        if (byArray == null) {
            this.getLevelsMemory(this.length);
            this.levels = this.levelsMemory;
            this.direction = this.resolveExplicitLevels();
        } else {
            this.levels = byArray;
            this.direction = this.checkExplicitLevels();
        }
        if (this.isolateCount > 0 && (this.isolates == null || this.isolates.length < this.isolateCount)) {
            this.isolates = new Isolate[this.isolateCount + 3];
        }
        this.isolateCount = -1;
        switch (this.direction) {
            case 0: {
                this.trailingWSStart = 0;
                break;
            }
            case 1: {
                this.trailingWSStart = 0;
                break;
            }
            default: {
                switch (this.reorderingMode) {
                    case 0: {
                        this.impTabPair = impTab_DEFAULT;
                        break;
                    }
                    case 1: {
                        this.impTabPair = impTab_NUMBERS_SPECIAL;
                        break;
                    }
                    case 2: {
                        this.impTabPair = impTab_GROUP_NUMBERS_WITH_R;
                        break;
                    }
                    case 3: {
                        throw new InternalError("Internal ICU error in setPara");
                    }
                    case 4: {
                        this.impTabPair = impTab_INVERSE_NUMBERS_AS_L;
                        break;
                    }
                    case 5: {
                        if ((this.reorderingOptions & 1) != 0) {
                            this.impTabPair = impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
                            break;
                        }
                        this.impTabPair = impTab_INVERSE_LIKE_DIRECT;
                        break;
                    }
                    case 6: {
                        this.impTabPair = (this.reorderingOptions & 1) != 0 ? impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS : impTab_INVERSE_FOR_NUMBERS_SPECIAL;
                    }
                }
                if (byArray == null && this.paraCount <= 1 && (this.flags & DirPropFlagMultiRuns) == 0) {
                    this.resolveImplicitLevels(0, this.length, Bidi.GetLRFromLevel(this.GetParaLevelAt(0)), Bidi.GetLRFromLevel(this.GetParaLevelAt(this.length - 1)));
                } else {
                    s4 = 0;
                    by3 = this.GetParaLevelAt(0);
                    s3 = by3 < (by2 = this.levels[0]) ? (short)Bidi.GetLRFromLevel(by2) : (short)Bidi.GetLRFromLevel(by3);
                    do {
                        s2 = s4;
                        by3 = by2;
                        s = s2 > 0 && this.dirProps[s2 - 1] == 7 ? (short)Bidi.GetLRFromLevel(this.GetParaLevelAt(s2)) : (short)s3;
                        while (++s4 < this.length && (this.levels[s4] == by3 || (Bidi.DirPropFlag(this.dirProps[s4]) & MASK_BN_EXPLICIT) != 0)) {
                        }
                        by2 = s4 < this.length ? this.levels[s4] : this.GetParaLevelAt(this.length - 1);
                        s3 = Bidi.NoOverride(by3) < Bidi.NoOverride(by2) ? (short)Bidi.GetLRFromLevel(by2) : (short)Bidi.GetLRFromLevel(by3);
                        if ((by3 & 0xFFFFFF80) == 0) {
                            this.resolveImplicitLevels(s2, s4, s, s3);
                            continue;
                        }
                        do {
                            short s5 = s2++;
                            this.levels[s5] = (byte)(this.levels[s5] & 0x7F);
                        } while (s2 < s4);
                    } while (s4 < this.length);
                }
                this.adjustWSLevels();
            }
        }
        if (this.defaultParaLevel > 0 && (this.reorderingOptions & 1) != 0 && (this.reorderingMode == 5 || this.reorderingMode == 6)) {
            block16: for (s = 0; s < this.paraCount; ++s) {
                s4 = this.paras_limit[s] - 1;
                by3 = this.paras_level[s];
                if (by3 == 0) continue;
                s2 = s == 0 ? (short)0 : this.paras_limit[s - 1];
                for (s3 = s4; s3 >= s2; --s3) {
                    by2 = this.dirProps[s3];
                    if (by2 == 0) {
                        if (s3 < s4) {
                            while (this.dirProps[s4] == 7) {
                                --s4;
                            }
                        }
                        this.addPoint(s4, 4);
                        continue block16;
                    }
                    if ((Bidi.DirPropFlag(by2) & MASK_R_AL) != 0) continue block16;
                }
            }
        }
        this.resultLength = (this.reorderingOptions & 2) != 0 ? (this.resultLength -= this.controlCount) : (this.resultLength += this.insertPoints.size);
        this.setParaSuccess();
    }

    public void setPara(AttributedCharacterIterator attributedCharacterIterator) {
        Serializable serializable;
        Boolean bl = (Boolean)attributedCharacterIterator.getAttribute(TextAttribute.RUN_DIRECTION);
        byte by = bl == null ? (byte)126 : (bl.equals(TextAttribute.RUN_DIRECTION_LTR) ? (byte)0 : 1);
        byte[] byArray = null;
        int n = attributedCharacterIterator.getEndIndex() - attributedCharacterIterator.getBeginIndex();
        byte[] byArray2 = new byte[n];
        char[] cArray = new char[n];
        int n2 = 0;
        char c = attributedCharacterIterator.first();
        while (c != '\uffff') {
            byte by2;
            cArray[n2] = c;
            serializable = (Integer)attributedCharacterIterator.getAttribute(TextAttribute.BIDI_EMBEDDING);
            if (serializable != null && (by2 = ((Integer)serializable).byteValue()) != 0) {
                if (by2 < 0) {
                    byArray = byArray2;
                    byArray2[n2] = (byte)(0 - by2 | 0xFFFFFF80);
                } else {
                    byArray = byArray2;
                    byArray2[n2] = by2;
                }
            }
            c = attributedCharacterIterator.next();
            ++n2;
        }
        serializable = (NumericShaper)attributedCharacterIterator.getAttribute(TextAttribute.NUMERIC_SHAPING);
        if (serializable != null) {
            ((NumericShaper)serializable).shape(cArray, 0, n);
        }
        this.setPara(cArray, by, byArray);
    }

    public void orderParagraphsLTR(boolean bl) {
        this.orderParagraphsLTR = bl;
    }

    public boolean isOrderParagraphsLTR() {
        return this.orderParagraphsLTR;
    }

    public byte getDirection() {
        this.verifyValidParaOrLine();
        return this.direction;
    }

    public String getTextAsString() {
        this.verifyValidParaOrLine();
        return new String(this.text);
    }

    public char[] getText() {
        this.verifyValidParaOrLine();
        return this.text;
    }

    public int getLength() {
        this.verifyValidParaOrLine();
        return this.originalLength;
    }

    public int getProcessedLength() {
        this.verifyValidParaOrLine();
        return this.length;
    }

    public int getResultLength() {
        this.verifyValidParaOrLine();
        return this.resultLength;
    }

    public byte getParaLevel() {
        this.verifyValidParaOrLine();
        return this.paraLevel;
    }

    public int countParagraphs() {
        this.verifyValidParaOrLine();
        return this.paraCount;
    }

    public BidiRun getParagraphByIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.paraCount);
        Bidi bidi = this.paraBidi;
        int n2 = n == 0 ? 0 : bidi.paras_limit[n - 1];
        BidiRun bidiRun = new BidiRun();
        bidiRun.start = n2;
        bidiRun.limit = bidi.paras_limit[n];
        bidiRun.level = this.GetParaLevelAt(n2);
        return bidiRun;
    }

    public BidiRun getParagraph(int n) {
        this.verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        this.verifyRange(n, 0, bidi.length);
        int n2 = 0;
        while (n >= bidi.paras_limit[n2]) {
            ++n2;
        }
        return this.getParagraphByIndex(n2);
    }

    public int getParagraphIndex(int n) {
        this.verifyValidParaOrLine();
        Bidi bidi = this.paraBidi;
        this.verifyRange(n, 0, bidi.length);
        int n2 = 0;
        while (n >= bidi.paras_limit[n2]) {
            ++n2;
        }
        return n2;
    }

    public void setCustomClassifier(BidiClassifier bidiClassifier) {
        this.customClassifier = bidiClassifier;
    }

    public BidiClassifier getCustomClassifier() {
        return this.customClassifier;
    }

    public int getCustomizedClass(int n) {
        int n2;
        if (this.customClassifier == null || (n2 = this.customClassifier.classify(n)) == 23) {
            n2 = this.bdp.getClass(n);
        }
        if (n2 >= 23) {
            n2 = 10;
        }
        return n2;
    }

    public Bidi setLine(int n, int n2) {
        this.verifyValidPara();
        this.verifyRange(n, 0, n2);
        this.verifyRange(n2, 0, this.length + 1);
        if (this.getParagraphIndex(n) != this.getParagraphIndex(n2 - 1)) {
            throw new IllegalArgumentException();
        }
        return BidiLine.setLine(this, n, n2);
    }

    public byte getLevelAt(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getLevelAt(this, n);
    }

    public byte[] getLevels() {
        this.verifyValidParaOrLine();
        if (this.length <= 0) {
            return new byte[0];
        }
        return BidiLine.getLevels(this);
    }

    public BidiRun getLogicalRun(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getLogicalRun(this, n);
    }

    public int countRuns() {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        return this.runCount;
    }

    public BidiRun getVisualRun(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        return BidiLine.getVisualRun(this, n);
    }

    public int getVisualIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.length);
        return BidiLine.getVisualIndex(this, n);
    }

    public int getLogicalIndex(int n) {
        this.verifyValidParaOrLine();
        this.verifyRange(n, 0, this.resultLength);
        if (this.insertPoints.size == 0 && this.controlCount == 0) {
            if (this.direction == 0) {
                return n;
            }
            if (this.direction == 1) {
                return this.length - n - 1;
            }
        }
        BidiLine.getRuns(this);
        return BidiLine.getLogicalIndex(this, n);
    }

    public int[] getLogicalMap() {
        this.countRuns();
        if (this.length <= 0) {
            return new int[0];
        }
        return BidiLine.getLogicalMap(this);
    }

    public int[] getVisualMap() {
        this.countRuns();
        if (this.resultLength <= 0) {
            return new int[0];
        }
        return BidiLine.getVisualMap(this);
    }

    public static int[] reorderLogical(byte[] byArray) {
        return BidiLine.reorderLogical(byArray);
    }

    public static int[] reorderVisual(byte[] byArray) {
        return BidiLine.reorderVisual(byArray);
    }

    public static int[] invertMap(int[] nArray) {
        if (nArray == null) {
            return null;
        }
        return BidiLine.invertMap(nArray);
    }

    public Bidi(String string, int n) {
        this(string.toCharArray(), 0, null, 0, string.length(), n);
    }

    public Bidi(AttributedCharacterIterator attributedCharacterIterator) {
        this();
        this.setPara(attributedCharacterIterator);
    }

    public Bidi(char[] cArray, int n, byte[] byArray, int n2, int n3, int n4) {
        this();
        byte[] byArray2;
        byte by;
        switch (n4) {
            default: {
                by = 0;
                break;
            }
            case 1: {
                by = 1;
                break;
            }
            case 126: {
                by = 126;
                break;
            }
            case 127: {
                by = 127;
            }
        }
        if (byArray == null) {
            byArray2 = null;
        } else {
            byArray2 = new byte[n3];
            for (int i = 0; i < n3; ++i) {
                byte by2 = byArray[i + n2];
                if (by2 < 0) {
                    by2 = (byte)(-by2 | 0xFFFFFF80);
                }
                byArray2[i] = by2;
            }
        }
        if (n == 0 && n3 == cArray.length) {
            this.setPara(cArray, by, byArray2);
        } else {
            char[] cArray2 = new char[n3];
            System.arraycopy(cArray, n, cArray2, 0, n3);
            this.setPara(cArray2, by, byArray2);
        }
    }

    public Bidi createLineBidi(int n, int n2) {
        return this.setLine(n, n2);
    }

    public boolean isMixed() {
        return !this.isLeftToRight() && !this.isRightToLeft();
    }

    public boolean isLeftToRight() {
        return this.getDirection() == 0 && (this.paraLevel & 1) == 0;
    }

    public boolean isRightToLeft() {
        return this.getDirection() == 1 && (this.paraLevel & 1) == 1;
    }

    public boolean baseIsLeftToRight() {
        return this.getParaLevel() == 0;
    }

    public int getBaseLevel() {
        return this.getParaLevel();
    }

    public int getRunCount() {
        return this.countRuns();
    }

    void getLogicalToVisualRunsMap() {
        int n;
        if (this.isGoodLogicalToVisualRunsMap) {
            return;
        }
        int n2 = this.countRuns();
        if (this.logicalToVisualRunsMap == null || this.logicalToVisualRunsMap.length < n2) {
            this.logicalToVisualRunsMap = new int[n2];
        }
        long[] lArray = new long[n2];
        for (n = 0; n < n2; ++n) {
            lArray[n] = ((long)this.runs[n].start << 32) + (long)n;
        }
        Arrays.sort(lArray);
        for (n = 0; n < n2; ++n) {
            this.logicalToVisualRunsMap[n] = (int)(lArray[n] & 0xFFFFFFFFFFFFFFFFL);
        }
        this.isGoodLogicalToVisualRunsMap = true;
    }

    public int getRunLevel(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[n]].level;
    }

    public int getRunStart(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        return this.runs[this.logicalToVisualRunsMap[n]].start;
    }

    public int getRunLimit(int n) {
        this.verifyValidParaOrLine();
        BidiLine.getRuns(this);
        this.verifyRange(n, 0, this.runCount);
        this.getLogicalToVisualRunsMap();
        int n2 = this.logicalToVisualRunsMap[n];
        int n3 = n2 == 0 ? this.runs[n2].limit : this.runs[n2].limit - this.runs[n2 - 1].limit;
        return this.runs[n2].start + n3;
    }

    public static boolean requiresBidi(char[] cArray, int n, int n2) {
        int n3 = 57378;
        for (int i = n; i < n2; ++i) {
            if ((1 << UCharacter.getDirection(cArray[i]) & 0xE022) == 0) continue;
            return false;
        }
        return true;
    }

    public static void reorderVisually(byte[] byArray, int n, Object[] objectArray, int n2, int n3) {
        byte[] byArray2 = new byte[n3];
        System.arraycopy(byArray, n, byArray2, 0, n3);
        int[] nArray = Bidi.reorderVisual(byArray2);
        Object[] objectArray2 = new Object[n3];
        System.arraycopy(objectArray, n2, objectArray2, 0, n3);
        for (int i = 0; i < n3; ++i) {
            objectArray[n2 + i] = objectArray2[nArray[i]];
        }
    }

    public String writeReordered(int n) {
        this.verifyValidParaOrLine();
        if (this.length == 0) {
            return "";
        }
        return BidiWriter.writeReordered(this, n);
    }

    public static String writeReverse(String string, int n) {
        if (string == null) {
            throw new IllegalArgumentException();
        }
        if (string.length() > 0) {
            return BidiWriter.writeReverse(string, n);
        }
        return "";
    }

    private static class LevState {
        byte[][] impTab;
        short[] impAct;
        int startON;
        int startL2EN;
        int lastStrongRTL;
        int runStart;
        short state;
        byte runLevel;

        private LevState() {
        }

        LevState(1 var1_1) {
            this();
        }
    }

    private static class ImpTabPair {
        byte[][][] imptab;
        short[][] impact;

        ImpTabPair(byte[][] byArray, byte[][] byArray2, short[] sArray, short[] sArray2) {
            this.imptab = new byte[][][]{byArray, byArray2};
            this.impact = new short[][]{sArray, sArray2};
        }
    }

    static class Isolate {
        int startON;
        int start1;
        short stateImp;
        short state;

        Isolate() {
        }
    }

    static class BracketData {
        Opening[] openings = new Opening[20];
        int isoRunLast;
        IsoRun[] isoRuns = new IsoRun[127];
        boolean isNumbersSpecial;

        BracketData() {
        }
    }

    static class IsoRun {
        int contextPos;
        short start;
        short limit;
        byte level;
        byte lastStrong;
        byte lastBase;
        byte contextDir;

        IsoRun() {
        }
    }

    static class Opening {
        int position;
        int match;
        int contextPos;
        short flags;
        byte contextDir;

        Opening() {
        }
    }

    static class InsertPoints {
        int size;
        int confirmed;
        Point[] points = new Point[0];

        InsertPoints() {
        }
    }

    static class Point {
        int pos;
        int flag;

        Point() {
        }
    }
}

