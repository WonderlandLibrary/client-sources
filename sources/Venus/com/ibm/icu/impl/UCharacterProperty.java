/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.lang.UScript;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.CodePointTrie;
import com.ibm.icu.util.ICUException;
import com.ibm.icu.util.ICUUncheckedIOException;
import com.ibm.icu.util.VersionInfo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class UCharacterProperty {
    public static final UCharacterProperty INSTANCE;
    public Trie2_16 m_trie_;
    public VersionInfo m_unicodeVersion_;
    public static final char LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE_ = '\u0130';
    public static final char LATIN_SMALL_LETTER_DOTLESS_I_ = '\u0131';
    public static final char LATIN_SMALL_LETTER_I_ = 'i';
    public static final int TYPE_MASK = 31;
    public static final int SRC_NONE = 0;
    public static final int SRC_CHAR = 1;
    public static final int SRC_PROPSVEC = 2;
    public static final int SRC_NAMES = 3;
    public static final int SRC_CASE = 4;
    public static final int SRC_BIDI = 5;
    public static final int SRC_CHAR_AND_PROPSVEC = 6;
    public static final int SRC_CASE_AND_NORM = 7;
    public static final int SRC_NFC = 8;
    public static final int SRC_NFKC = 9;
    public static final int SRC_NFKC_CF = 10;
    public static final int SRC_NFC_CANON_ITER = 11;
    public static final int SRC_INPC = 12;
    public static final int SRC_INSC = 13;
    public static final int SRC_VO = 14;
    public static final int SRC_COUNT = 15;
    static final int MY_MASK = 30;
    private static final int GC_CN_MASK;
    private static final int GC_CC_MASK;
    private static final int GC_CS_MASK;
    private static final int GC_ZS_MASK;
    private static final int GC_ZL_MASK;
    private static final int GC_ZP_MASK;
    private static final int GC_Z_MASK;
    BinaryProperty[] binProps = new BinaryProperty[]{new BinaryProperty(this, 1, 256), new BinaryProperty(this, 1, 128), new BinaryProperty(this, 5){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UBiDiProps.INSTANCE.isBidiControl(n);
        }
    }, new BinaryProperty(this, 5){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UBiDiProps.INSTANCE.isMirrored(n);
        }
    }, new BinaryProperty(this, 1, 2), new BinaryProperty(this, 1, 524288), new BinaryProperty(this, 1, 0x100000), new BinaryProperty(this, 1, 1024), new BinaryProperty(this, 1, 2048), new BinaryProperty(this, 8){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            Normalizer2Impl normalizer2Impl = Norm2AllModes.getNFCInstance().impl;
            return normalizer2Impl.isCompNo(normalizer2Impl.getNorm16(n));
        }
    }, new BinaryProperty(this, 1, 0x4000000), new BinaryProperty(this, 1, 8192), new BinaryProperty(this, 1, 16384), new BinaryProperty(this, 1, 64), new BinaryProperty(this, 1, 4), new BinaryProperty(this, 1, 0x2000000), new BinaryProperty(this, 1, 0x1000000), new BinaryProperty(this, 1, 512), new BinaryProperty(this, 1, 32768), new BinaryProperty(this, 1, 65536), new BinaryProperty(this, 5){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UBiDiProps.INSTANCE.isJoinControl(n);
        }
    }, new BinaryProperty(this, 1, 0x200000), new CaseBinaryProperty(this, 22), new BinaryProperty(this, 1, 32), new BinaryProperty(this, 1, 4096), new BinaryProperty(this, 1, 8), new BinaryProperty(this, 1, 131072), new CaseBinaryProperty(this, 27), new BinaryProperty(this, 1, 16), new BinaryProperty(this, 1, 262144), new CaseBinaryProperty(this, 30), new BinaryProperty(this, 1, 1), new BinaryProperty(this, 1, 0x800000), new BinaryProperty(this, 1, 0x400000), new CaseBinaryProperty(this, 34), new BinaryProperty(this, 1, 0x8000000), new BinaryProperty(this, 1, 0x10000000), new NormInertBinaryProperty(this, 8, 37), new NormInertBinaryProperty(this, 9, 38), new NormInertBinaryProperty(this, 8, 39), new NormInertBinaryProperty(this, 9, 40), new BinaryProperty(this, 11){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return Norm2AllModes.getNFCInstance().impl.ensureCanonIterData().isCanonSegmentStarter(n);
        }
    }, new BinaryProperty(this, 1, 0x20000000), new BinaryProperty(this, 1, 0x40000000), new BinaryProperty(this, 6){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UCharacter.isUAlphabetic(n) || UCharacter.isDigit(n);
        }
    }, new BinaryProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            if (n <= 159) {
                return n == 9 || n == 32;
            }
            return UCharacter.getType(n) == 12;
        }
    }, new BinaryProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UCharacterProperty.access$100(n);
        }
    }, new BinaryProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return UCharacter.getType(n) == 12 || UCharacterProperty.access$100(n);
        }
    }, new BinaryProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            if (n <= 102 && n >= 65 && (n <= 70 || n >= 97) || n >= 65313 && n <= 65350 && (n <= 65318 || n >= 65345)) {
                return false;
            }
            return UCharacter.getType(n) == 9;
        }
    }, new CaseBinaryProperty(this, 49), new CaseBinaryProperty(this, 50), new CaseBinaryProperty(this, 51), new CaseBinaryProperty(this, 52), new CaseBinaryProperty(this, 53), new BinaryProperty(this, 7){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            String string = Norm2AllModes.getNFCInstance().impl.getDecomposition(n);
            if (string != null) {
                n = string.codePointAt(0);
                if (Character.charCount(n) != string.length()) {
                    n = -1;
                }
            } else if (n < 0) {
                return true;
            }
            if (n >= 0) {
                UCaseProps uCaseProps = UCaseProps.INSTANCE;
                UCaseProps.dummyStringBuilder.setLength(0);
                return uCaseProps.toFullFolding(n, UCaseProps.dummyStringBuilder, 0) >= 0;
            }
            String string2 = UCharacter.foldCase(string, true);
            return !string2.equals(string);
        }
    }, new CaseBinaryProperty(this, 55), new BinaryProperty(this, 10){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            Normalizer2Impl normalizer2Impl = Norm2AllModes.getNFKC_CFInstance().impl;
            String string = UTF16.valueOf(n);
            StringBuilder stringBuilder = new StringBuilder();
            Normalizer2Impl.ReorderingBuffer reorderingBuffer = new Normalizer2Impl.ReorderingBuffer(normalizer2Impl, stringBuilder, 5);
            normalizer2Impl.compose(string, 0, string.length(), false, true, reorderingBuffer);
            return !Normalizer2Impl.UTF16Plus.equal(stringBuilder, string);
        }
    }, new BinaryProperty(this, 2, 0x10000000), new BinaryProperty(this, 2, 0x20000000), new BinaryProperty(this, 2, 0x40000000), new BinaryProperty(this, 2, Integer.MIN_VALUE), new BinaryProperty(this, 2, 0x8000000), new BinaryProperty(this, 2){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        boolean contains(int n) {
            return 127462 <= n && n <= 127487;
        }
    }, new BinaryProperty(this, 1, Integer.MIN_VALUE), new BinaryProperty(this, 2, 0x4000000)};
    private static final int[] gcbToHst;
    IntProperty[] intProps = new IntProperty[]{new BiDiIntProperty(this){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty);
        }

        @Override
        int getValue(int n) {
            return UBiDiProps.INSTANCE.getClass(n);
        }
    }, new IntProperty(this, 0, 130816, 8), new CombiningClassIntProperty(this, 8){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return Normalizer2.getNFDInstance().getCombiningClass(n);
        }
    }, new IntProperty(this, 2, 31, 0), new IntProperty(this, 0, 917504, 17), new IntProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return this.this$0.getType(n);
        }

        @Override
        int getMaxValue(int n) {
            return 0;
        }
    }, new BiDiIntProperty(this){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty);
        }

        @Override
        int getValue(int n) {
            return UBiDiProps.INSTANCE.getJoiningGroup(n);
        }
    }, new BiDiIntProperty(this){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty);
        }

        @Override
        int getValue(int n) {
            return UBiDiProps.INSTANCE.getJoiningType(n);
        }
    }, new IntProperty(this, 2, 0x3F00000, 20), new IntProperty(this, 1){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return UCharacterProperty.access$300(UCharacterProperty.access$200(this.this$0.getProperty(n)));
        }

        @Override
        int getMaxValue(int n) {
            return 0;
        }
    }, new IntProperty(this, 2){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return UScript.getScript(n);
        }

        @Override
        int getMaxValue(int n) {
            int n2 = this.this$0.getMaxValues(0) & 0xF000FF;
            return UCharacterProperty.mergeScriptCodeOrIndex(n2);
        }
    }, new IntProperty(this, 2){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            int n2 = (this.this$0.getAdditional(n, 2) & 0x3E0) >>> 5;
            if (n2 < UCharacterProperty.access$400().length) {
                return UCharacterProperty.access$400()[n2];
            }
            return 1;
        }

        @Override
        int getMaxValue(int n) {
            return 0;
        }
    }, new NormQuickCheckIntProperty(this, 8, 4108, 1), new NormQuickCheckIntProperty(this, 9, 4109, 1), new NormQuickCheckIntProperty(this, 8, 4110, 2), new NormQuickCheckIntProperty(this, 9, 4111, 2), new CombiningClassIntProperty(this, 8){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return Norm2AllModes.getNFCInstance().impl.getFCD16(n) >> 8;
        }
    }, new CombiningClassIntProperty(this, 8){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            return Norm2AllModes.getNFCInstance().impl.getFCD16(n) & 0xFF;
        }
    }, new IntProperty(this, 2, 992, 5), new IntProperty(this, 2, 1015808, 15), new IntProperty(this, 2, 31744, 10), new BiDiIntProperty(this){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty);
        }

        @Override
        int getValue(int n) {
            return UBiDiProps.INSTANCE.getPairedBracketType(n);
        }
    }, new IntProperty(this, 12){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            CodePointTrie codePointTrie = LayoutProps.INSTANCE.inpcTrie;
            return codePointTrie != null ? codePointTrie.get(n) : 0;
        }

        @Override
        int getMaxValue(int n) {
            return LayoutProps.INSTANCE.maxInpcValue;
        }
    }, new IntProperty(this, 13){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            CodePointTrie codePointTrie = LayoutProps.INSTANCE.inscTrie;
            return codePointTrie != null ? codePointTrie.get(n) : 0;
        }

        @Override
        int getMaxValue(int n) {
            return LayoutProps.INSTANCE.maxInscValue;
        }
    }, new IntProperty(this, 14){
        final UCharacterProperty this$0;
        {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getValue(int n) {
            CodePointTrie codePointTrie = LayoutProps.INSTANCE.voTrie;
            return codePointTrie != null ? codePointTrie.get(n) : 0;
        }

        @Override
        int getMaxValue(int n) {
            return LayoutProps.INSTANCE.maxVoValue;
        }
    }};
    Trie2_16 m_additionalTrie_;
    int[] m_additionalVectors_;
    int m_additionalColumnsCount_;
    int m_maxBlockScriptValue_;
    int m_maxJTGValue_;
    public char[] m_scriptExtensions_;
    private static final String DATA_FILE_NAME_ = "uprops.icu";
    private static final int NUMERIC_TYPE_VALUE_SHIFT_ = 6;
    private static final int NTV_NONE_ = 0;
    private static final int NTV_DECIMAL_START_ = 1;
    private static final int NTV_DIGIT_START_ = 11;
    private static final int NTV_NUMERIC_START_ = 21;
    private static final int NTV_FRACTION_START_ = 176;
    private static final int NTV_LARGE_START_ = 480;
    private static final int NTV_BASE60_START_ = 768;
    private static final int NTV_FRACTION20_START_ = 804;
    private static final int NTV_FRACTION32_START_ = 828;
    private static final int NTV_RESERVED_START_ = 844;
    public static final int SCRIPT_X_MASK = 0xF000FF;
    public static final int SCRIPT_HIGH_MASK = 0x300000;
    public static final int SCRIPT_HIGH_SHIFT = 12;
    public static final int MAX_SCRIPT = 1023;
    private static final int EAST_ASIAN_MASK_ = 917504;
    private static final int EAST_ASIAN_SHIFT_ = 17;
    private static final int BLOCK_MASK_ = 130816;
    private static final int BLOCK_SHIFT_ = 8;
    public static final int SCRIPT_LOW_MASK = 255;
    public static final int SCRIPT_X_WITH_COMMON = 0x400000;
    public static final int SCRIPT_X_WITH_INHERITED = 0x800000;
    public static final int SCRIPT_X_WITH_OTHER = 0xC00000;
    private static final int WHITE_SPACE_PROPERTY_ = 0;
    private static final int DASH_PROPERTY_ = 1;
    private static final int HYPHEN_PROPERTY_ = 2;
    private static final int QUOTATION_MARK_PROPERTY_ = 3;
    private static final int TERMINAL_PUNCTUATION_PROPERTY_ = 4;
    private static final int MATH_PROPERTY_ = 5;
    private static final int HEX_DIGIT_PROPERTY_ = 6;
    private static final int ASCII_HEX_DIGIT_PROPERTY_ = 7;
    private static final int ALPHABETIC_PROPERTY_ = 8;
    private static final int IDEOGRAPHIC_PROPERTY_ = 9;
    private static final int DIACRITIC_PROPERTY_ = 10;
    private static final int EXTENDER_PROPERTY_ = 11;
    private static final int NONCHARACTER_CODE_POINT_PROPERTY_ = 12;
    private static final int GRAPHEME_EXTEND_PROPERTY_ = 13;
    private static final int GRAPHEME_LINK_PROPERTY_ = 14;
    private static final int IDS_BINARY_OPERATOR_PROPERTY_ = 15;
    private static final int IDS_TRINARY_OPERATOR_PROPERTY_ = 16;
    private static final int RADICAL_PROPERTY_ = 17;
    private static final int UNIFIED_IDEOGRAPH_PROPERTY_ = 18;
    private static final int DEFAULT_IGNORABLE_CODE_POINT_PROPERTY_ = 19;
    private static final int DEPRECATED_PROPERTY_ = 20;
    private static final int LOGICAL_ORDER_EXCEPTION_PROPERTY_ = 21;
    private static final int XID_START_PROPERTY_ = 22;
    private static final int XID_CONTINUE_PROPERTY_ = 23;
    private static final int ID_START_PROPERTY_ = 24;
    private static final int ID_CONTINUE_PROPERTY_ = 25;
    private static final int GRAPHEME_BASE_PROPERTY_ = 26;
    private static final int S_TERM_PROPERTY_ = 27;
    private static final int VARIATION_SELECTOR_PROPERTY_ = 28;
    private static final int PATTERN_SYNTAX = 29;
    private static final int PATTERN_WHITE_SPACE = 30;
    private static final int PREPENDED_CONCATENATION_MARK = 31;
    private static final int PROPS_2_EXTENDED_PICTOGRAPHIC = 26;
    private static final int PROPS_2_EMOJI_COMPONENT = 27;
    private static final int PROPS_2_EMOJI = 28;
    private static final int PROPS_2_EMOJI_PRESENTATION = 29;
    private static final int PROPS_2_EMOJI_MODIFIER = 30;
    private static final int PROPS_2_EMOJI_MODIFIER_BASE = 31;
    private static final int LB_MASK = 0x3F00000;
    private static final int LB_SHIFT = 20;
    private static final int SB_MASK = 1015808;
    private static final int SB_SHIFT = 15;
    private static final int WB_MASK = 31744;
    private static final int WB_SHIFT = 10;
    private static final int GCB_MASK = 992;
    private static final int GCB_SHIFT = 5;
    private static final int DECOMPOSITION_TYPE_MASK_ = 31;
    private static final int FIRST_NIBBLE_SHIFT_ = 4;
    private static final int LAST_NIBBLE_MASK_ = 15;
    private static final int AGE_SHIFT_ = 24;
    private static final int DATA_FORMAT = 1431335535;
    private static final int TAB = 9;
    private static final int CR = 13;
    private static final int U_A = 65;
    private static final int U_F = 70;
    private static final int U_Z = 90;
    private static final int U_a = 97;
    private static final int U_f = 102;
    private static final int U_z = 122;
    private static final int DEL = 127;
    private static final int NL = 133;
    private static final int NBSP = 160;
    private static final int CGJ = 847;
    private static final int FIGURESP = 8199;
    private static final int HAIRSP = 8202;
    private static final int RLM = 8207;
    private static final int NNBSP = 8239;
    private static final int WJ = 8288;
    private static final int INHSWAP = 8298;
    private static final int NOMDIG = 8303;
    private static final int U_FW_A = 65313;
    private static final int U_FW_F = 65318;
    private static final int U_FW_Z = 65338;
    private static final int U_FW_a = 65345;
    private static final int U_FW_f = 65350;
    private static final int U_FW_z = 65370;
    private static final int ZWNBSP = 65279;
    static final boolean $assertionsDisabled;

    public final int getProperty(int n) {
        return this.m_trie_.get(n);
    }

    public int getAdditional(int n, int n2) {
        if (!$assertionsDisabled && n2 < 0) {
            throw new AssertionError();
        }
        if (n2 >= this.m_additionalColumnsCount_) {
            return 1;
        }
        return this.m_additionalVectors_[this.m_additionalTrie_.get(n) + n2];
    }

    public VersionInfo getAge(int n) {
        int n2 = this.getAdditional(n, 0) >> 24;
        return VersionInfo.getInstance(n2 >> 4 & 0xF, n2 & 0xF, 0, 0);
    }

    private static final boolean isgraphPOSIX(int n) {
        return (UCharacterProperty.getMask(UCharacter.getType(n)) & (GC_CC_MASK | GC_CS_MASK | GC_CN_MASK | GC_Z_MASK)) == 0;
    }

    public boolean hasBinaryProperty(int n, int n2) {
        if (n2 < 0 || 65 <= n2) {
            return true;
        }
        return this.binProps[n2].contains(n);
    }

    public int getType(int n) {
        return this.getProperty(n) & 0x1F;
    }

    public int getIntPropertyValue(int n, int n2) {
        if (n2 < 4096) {
            if (0 <= n2 && n2 < 65) {
                return this.binProps[n2].contains(n) ? 1 : 0;
            }
        } else {
            if (n2 < 4121) {
                return this.intProps[n2 - 4096].getValue(n);
            }
            if (n2 == 8192) {
                return UCharacterProperty.getMask(this.getType(n));
            }
        }
        return 1;
    }

    public int getIntPropertyMaxValue(int n) {
        if (n < 4096) {
            if (0 <= n && n < 65) {
                return 0;
            }
        } else if (n < 4121) {
            return this.intProps[n - 4096].getMaxValue(n);
        }
        return 1;
    }

    final int getSource(int n) {
        if (n < 0) {
            return 1;
        }
        if (n < 65) {
            return this.binProps[n].getSource();
        }
        if (n < 4096) {
            return 1;
        }
        if (n < 4121) {
            return this.intProps[n - 4096].getSource();
        }
        if (n < 16384) {
            switch (n) {
                case 8192: 
                case 12288: {
                    return 0;
                }
            }
            return 1;
        }
        if (n < 16398) {
            switch (n) {
                case 16384: {
                    return 1;
                }
                case 16385: {
                    return 0;
                }
                case 16386: 
                case 16388: 
                case 16390: 
                case 16391: 
                case 16392: 
                case 16393: 
                case 16394: 
                case 16396: {
                    return 1;
                }
                case 16387: 
                case 16389: 
                case 16395: {
                    return 0;
                }
            }
            return 1;
        }
        switch (n) {
            case 28672: {
                return 1;
            }
        }
        return 1;
    }

    public int getMaxValues(int n) {
        switch (n) {
            case 0: {
                return this.m_maxBlockScriptValue_;
            }
            case 2: {
                return this.m_maxJTGValue_;
            }
        }
        return 1;
    }

    public static final int getMask(int n) {
        return 1 << n;
    }

    public static int getEuropeanDigit(int n) {
        if (n > 122 && n < 65313 || n < 65 || n > 90 && n < 97 || n > 65370 || n > 65338 && n < 65345) {
            return 1;
        }
        if (n <= 122) {
            return n + 10 - (n <= 90 ? 65 : 97);
        }
        if (n <= 65338) {
            return n + 10 - 65313;
        }
        return n + 10 - 65345;
    }

    public int digit(int n) {
        int n2 = UCharacterProperty.getNumericTypeValue(this.getProperty(n)) - 1;
        if (n2 <= 9) {
            return n2;
        }
        return 1;
    }

    public int getNumericValue(int n) {
        int n2 = UCharacterProperty.getNumericTypeValue(this.getProperty(n));
        if (n2 == 0) {
            return UCharacterProperty.getEuropeanDigit(n);
        }
        if (n2 < 11) {
            return n2 - 1;
        }
        if (n2 < 21) {
            return n2 - 11;
        }
        if (n2 < 176) {
            return n2 - 21;
        }
        if (n2 < 480) {
            return 1;
        }
        if (n2 < 768) {
            int n3 = (n2 >> 5) - 14;
            int n4 = (n2 & 0x1F) + 2;
            if (n4 < 9 || n4 == 9 && n3 <= 2) {
                int n5 = n3;
                do {
                    n5 *= 10;
                } while (--n4 > 0);
                return n5;
            }
            return 1;
        }
        if (n2 < 804) {
            int n6 = (n2 >> 2) - 191;
            int n7 = (n2 & 3) + 1;
            switch (n7) {
                case 4: {
                    n6 *= 12960000;
                    break;
                }
                case 3: {
                    n6 *= 216000;
                    break;
                }
                case 2: {
                    n6 *= 3600;
                    break;
                }
                case 1: {
                    n6 *= 60;
                    break;
                }
            }
            return n6;
        }
        if (n2 < 844) {
            return 1;
        }
        return 1;
    }

    public double getUnicodeNumericValue(int n) {
        int n2 = UCharacterProperty.getNumericTypeValue(this.getProperty(n));
        if (n2 == 0) {
            return -1.23456789E8;
        }
        if (n2 < 11) {
            return n2 - 1;
        }
        if (n2 < 21) {
            return n2 - 11;
        }
        if (n2 < 176) {
            return n2 - 21;
        }
        if (n2 < 480) {
            int n3 = (n2 >> 4) - 12;
            int n4 = (n2 & 0xF) + 1;
            return (double)n3 / (double)n4;
        }
        if (n2 < 768) {
            int n5;
            int n6 = (n2 >> 5) - 14;
            double d = n6;
            for (n5 = (n2 & 0x1F) + 2; n5 >= 4; n5 -= 4) {
                d *= 10000.0;
            }
            switch (n5) {
                case 3: {
                    d *= 1000.0;
                    break;
                }
                case 2: {
                    d *= 100.0;
                    break;
                }
                case 1: {
                    d *= 10.0;
                    break;
                }
            }
            return d;
        }
        if (n2 < 804) {
            int n7 = (n2 >> 2) - 191;
            int n8 = (n2 & 3) + 1;
            switch (n8) {
                case 4: {
                    n7 *= 12960000;
                    break;
                }
                case 3: {
                    n7 *= 216000;
                    break;
                }
                case 2: {
                    n7 *= 3600;
                    break;
                }
                case 1: {
                    n7 *= 60;
                    break;
                }
            }
            return n7;
        }
        if (n2 < 828) {
            int n9 = n2 - 804;
            int n10 = 2 * (n9 & 3) + 1;
            int n11 = 20 << (n9 >> 2);
            return (double)n10 / (double)n11;
        }
        if (n2 < 844) {
            int n12 = n2 - 828;
            int n13 = 2 * (n12 & 3) + 1;
            int n14 = 32 << (n12 >> 2);
            return (double)n13 / (double)n14;
        }
        return -1.23456789E8;
    }

    private static final int getNumericTypeValue(int n) {
        return n >> 6;
    }

    private static final int ntvGetType(int n) {
        return n == 0 ? 0 : (n < 11 ? 1 : (n < 21 ? 2 : 3));
    }

    public static final int mergeScriptCodeOrIndex(int n) {
        return (n & 0x300000) >> 12 | n & 0xFF;
    }

    private UCharacterProperty() throws IOException {
        int n;
        if (this.binProps.length != 65) {
            throw new ICUException("binProps.length!=UProperty.BINARY_LIMIT");
        }
        if (this.intProps.length != 25) {
            throw new ICUException("intProps.length!=(UProperty.INT_LIMIT-UProperty.INT_START)");
        }
        ByteBuffer byteBuffer = ICUBinary.getRequiredData(DATA_FILE_NAME_);
        this.m_unicodeVersion_ = ICUBinary.readHeaderAndDataVersion(byteBuffer, 1431335535, new IsAcceptable(null));
        int n2 = byteBuffer.getInt();
        byteBuffer.getInt();
        byteBuffer.getInt();
        int n3 = byteBuffer.getInt();
        int n4 = byteBuffer.getInt();
        this.m_additionalColumnsCount_ = byteBuffer.getInt();
        int n5 = byteBuffer.getInt();
        int n6 = byteBuffer.getInt();
        byteBuffer.getInt();
        byteBuffer.getInt();
        this.m_maxBlockScriptValue_ = byteBuffer.getInt();
        this.m_maxJTGValue_ = byteBuffer.getInt();
        ICUBinary.skipBytes(byteBuffer, 16);
        this.m_trie_ = Trie2_16.createFromSerialized(byteBuffer);
        int n7 = (n2 - 16) * 4;
        int n8 = this.m_trie_.getSerializedLength();
        if (n8 > n7) {
            throw new IOException("uprops.icu: not enough bytes for main trie");
        }
        ICUBinary.skipBytes(byteBuffer, n7 - n8);
        ICUBinary.skipBytes(byteBuffer, (n3 - n2) * 4);
        if (this.m_additionalColumnsCount_ > 0) {
            this.m_additionalTrie_ = Trie2_16.createFromSerialized(byteBuffer);
            n7 = (n4 - n3) * 4;
            n8 = this.m_additionalTrie_.getSerializedLength();
            if (n8 > n7) {
                throw new IOException("uprops.icu: not enough bytes for additional-properties trie");
            }
            ICUBinary.skipBytes(byteBuffer, n7 - n8);
            n = n5 - n4;
            this.m_additionalVectors_ = ICUBinary.getInts(byteBuffer, n, 0);
        }
        if ((n = (n6 - n5) * 2) > 0) {
            this.m_scriptExtensions_ = ICUBinary.getChars(byteBuffer, n, 0);
        }
    }

    public UnicodeSet addPropertyStarts(UnicodeSet unicodeSet) {
        for (Trie2.Range range : this.m_trie_) {
            if (range.leadSurrogate) break;
            unicodeSet.add(range.startCodePoint);
        }
        unicodeSet.add(9);
        unicodeSet.add(10);
        unicodeSet.add(14);
        unicodeSet.add(28);
        unicodeSet.add(32);
        unicodeSet.add(133);
        unicodeSet.add(134);
        unicodeSet.add(127);
        unicodeSet.add(8202);
        unicodeSet.add(8208);
        unicodeSet.add(8298);
        unicodeSet.add(8304);
        unicodeSet.add(65279);
        unicodeSet.add(65280);
        unicodeSet.add(160);
        unicodeSet.add(161);
        unicodeSet.add(8199);
        unicodeSet.add(8200);
        unicodeSet.add(8239);
        unicodeSet.add(8240);
        unicodeSet.add(12295);
        unicodeSet.add(12296);
        unicodeSet.add(19968);
        unicodeSet.add(19969);
        unicodeSet.add(20108);
        unicodeSet.add(20109);
        unicodeSet.add(19977);
        unicodeSet.add(19978);
        unicodeSet.add(22235);
        unicodeSet.add(22236);
        unicodeSet.add(20116);
        unicodeSet.add(20117);
        unicodeSet.add(20845);
        unicodeSet.add(20846);
        unicodeSet.add(19971);
        unicodeSet.add(19972);
        unicodeSet.add(20843);
        unicodeSet.add(20844);
        unicodeSet.add(20061);
        unicodeSet.add(20062);
        unicodeSet.add(97);
        unicodeSet.add(123);
        unicodeSet.add(65);
        unicodeSet.add(91);
        unicodeSet.add(65345);
        unicodeSet.add(65371);
        unicodeSet.add(65313);
        unicodeSet.add(65339);
        unicodeSet.add(103);
        unicodeSet.add(71);
        unicodeSet.add(65351);
        unicodeSet.add(65319);
        unicodeSet.add(8288);
        unicodeSet.add(65520);
        unicodeSet.add(65532);
        unicodeSet.add(917504);
        unicodeSet.add(921600);
        unicodeSet.add(847);
        unicodeSet.add(848);
        return unicodeSet;
    }

    public void upropsvec_addPropertyStarts(UnicodeSet unicodeSet) {
        if (this.m_additionalColumnsCount_ > 0) {
            for (Trie2.Range range : this.m_additionalTrie_) {
                if (range.leadSurrogate) break;
                unicodeSet.add(range.startCodePoint);
            }
        }
    }

    static UnicodeSet ulayout_addPropertyStarts(int n, UnicodeSet unicodeSet) {
        return LayoutProps.INSTANCE.addPropertyStarts(n, unicodeSet);
    }

    static boolean access$100(int n) {
        return UCharacterProperty.isgraphPOSIX(n);
    }

    static int access$200(int n) {
        return UCharacterProperty.getNumericTypeValue(n);
    }

    static int access$300(int n) {
        return UCharacterProperty.ntvGetType(n);
    }

    static int[] access$400() {
        return gcbToHst;
    }

    static {
        $assertionsDisabled = !UCharacterProperty.class.desiredAssertionStatus();
        GC_CN_MASK = UCharacterProperty.getMask(0);
        GC_CC_MASK = UCharacterProperty.getMask(15);
        GC_CS_MASK = UCharacterProperty.getMask(18);
        GC_ZS_MASK = UCharacterProperty.getMask(12);
        GC_ZL_MASK = UCharacterProperty.getMask(13);
        GC_ZP_MASK = UCharacterProperty.getMask(14);
        GC_Z_MASK = GC_ZS_MASK | GC_ZL_MASK | GC_ZP_MASK;
        gcbToHst = new int[]{0, 0, 0, 0, 1, 0, 4, 5, 3, 2};
        try {
            INSTANCE = new UCharacterProperty();
        } catch (IOException iOException) {
            throw new MissingResourceException(iOException.getMessage(), "", "");
        }
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 7;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }

    private class NormQuickCheckIntProperty
    extends IntProperty {
        int which;
        int max;
        final UCharacterProperty this$0;

        NormQuickCheckIntProperty(UCharacterProperty uCharacterProperty, int n, int n2, int n3) {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
            this.which = n2;
            this.max = n3;
        }

        @Override
        int getValue(int n) {
            return Norm2AllModes.getN2WithImpl(this.which - 4108).getQuickCheck(n);
        }

        @Override
        int getMaxValue(int n) {
            return this.max;
        }
    }

    private class CombiningClassIntProperty
    extends IntProperty {
        final UCharacterProperty this$0;

        CombiningClassIntProperty(UCharacterProperty uCharacterProperty, int n) {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
        }

        @Override
        int getMaxValue(int n) {
            return 0;
        }
    }

    private class BiDiIntProperty
    extends IntProperty {
        final UCharacterProperty this$0;

        BiDiIntProperty(UCharacterProperty uCharacterProperty) {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, 5);
        }

        @Override
        int getMaxValue(int n) {
            return UBiDiProps.INSTANCE.getMaxValue(n);
        }
    }

    private class IntProperty {
        int column;
        int mask;
        int shift;
        final UCharacterProperty this$0;

        IntProperty(UCharacterProperty uCharacterProperty, int n, int n2, int n3) {
            this.this$0 = uCharacterProperty;
            this.column = n;
            this.mask = n2;
            this.shift = n3;
        }

        IntProperty(UCharacterProperty uCharacterProperty, int n) {
            this.this$0 = uCharacterProperty;
            this.column = n;
            this.mask = 0;
        }

        final int getSource() {
            return this.mask == 0 ? this.column : 2;
        }

        int getValue(int n) {
            return (this.this$0.getAdditional(n, this.column) & this.mask) >>> this.shift;
        }

        int getMaxValue(int n) {
            return (this.this$0.getMaxValues(this.column) & this.mask) >>> this.shift;
        }
    }

    private class NormInertBinaryProperty
    extends BinaryProperty {
        int which;
        final UCharacterProperty this$0;

        NormInertBinaryProperty(UCharacterProperty uCharacterProperty, int n, int n2) {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, n);
            this.which = n2;
        }

        @Override
        boolean contains(int n) {
            return Norm2AllModes.getN2WithImpl(this.which - 37).isInert(n);
        }
    }

    private class CaseBinaryProperty
    extends BinaryProperty {
        int which;
        final UCharacterProperty this$0;

        CaseBinaryProperty(UCharacterProperty uCharacterProperty, int n) {
            this.this$0 = uCharacterProperty;
            super(uCharacterProperty, 4);
            this.which = n;
        }

        @Override
        boolean contains(int n) {
            return UCaseProps.INSTANCE.hasBinaryProperty(n, this.which);
        }
    }

    private class BinaryProperty {
        int column;
        int mask;
        final UCharacterProperty this$0;

        BinaryProperty(UCharacterProperty uCharacterProperty, int n, int n2) {
            this.this$0 = uCharacterProperty;
            this.column = n;
            this.mask = n2;
        }

        BinaryProperty(UCharacterProperty uCharacterProperty, int n) {
            this.this$0 = uCharacterProperty;
            this.column = n;
            this.mask = 0;
        }

        final int getSource() {
            return this.mask == 0 ? this.column : 2;
        }

        boolean contains(int n) {
            return (this.this$0.getAdditional(n, this.column) & this.mask) != 0;
        }
    }

    private static final class LayoutProps {
        private static final IsAcceptable IS_ACCEPTABLE;
        private static final int DATA_FORMAT = 1281456495;
        private static final int IX_INPC_TRIE_TOP = 1;
        private static final int IX_INSC_TRIE_TOP = 2;
        private static final int IX_VO_TRIE_TOP = 3;
        private static final int IX_MAX_VALUES = 9;
        private static final int MAX_INPC_SHIFT = 24;
        private static final int MAX_INSC_SHIFT = 16;
        private static final int MAX_VO_SHIFT = 8;
        static final LayoutProps INSTANCE;
        CodePointTrie inpcTrie = null;
        CodePointTrie inscTrie = null;
        CodePointTrie voTrie = null;
        int maxInpcValue = 0;
        int maxInscValue = 0;
        int maxVoValue = 0;
        static final boolean $assertionsDisabled;

        LayoutProps() {
            ByteBuffer byteBuffer = ICUBinary.getRequiredData("ulayout.icu");
            try {
                int n;
                ICUBinary.readHeaderAndDataVersion(byteBuffer, 1281456495, IS_ACCEPTABLE);
                int n2 = byteBuffer.position();
                int n3 = byteBuffer.getInt();
                if (n3 < 12) {
                    throw new ICUUncheckedIOException("Text layout properties data: not enough indexes");
                }
                int[] nArray = new int[n3];
                nArray[0] = n3;
                for (n = 1; n < n3; ++n) {
                    nArray[n] = byteBuffer.getInt();
                }
                int n4 = nArray[1];
                n = n3 * 4;
                int n5 = n4 - n;
                if (n5 >= 16) {
                    this.inpcTrie = CodePointTrie.fromBinary(null, null, byteBuffer);
                }
                int n6 = byteBuffer.position() - n2;
                if (!$assertionsDisabled && n4 < n6) {
                    throw new AssertionError();
                }
                ICUBinary.skipBytes(byteBuffer, n4 - n6);
                n = n4;
                n4 = nArray[2];
                n5 = n4 - n;
                if (n5 >= 16) {
                    this.inscTrie = CodePointTrie.fromBinary(null, null, byteBuffer);
                }
                n6 = byteBuffer.position() - n2;
                if (!$assertionsDisabled && n4 < n6) {
                    throw new AssertionError();
                }
                ICUBinary.skipBytes(byteBuffer, n4 - n6);
                n = n4;
                n4 = nArray[3];
                n5 = n4 - n;
                if (n5 >= 16) {
                    this.voTrie = CodePointTrie.fromBinary(null, null, byteBuffer);
                }
                n6 = byteBuffer.position() - n2;
                if (!$assertionsDisabled && n4 < n6) {
                    throw new AssertionError();
                }
                ICUBinary.skipBytes(byteBuffer, n4 - n6);
                int n7 = nArray[9];
                this.maxInpcValue = n7 >>> 24;
                this.maxInscValue = n7 >> 16 & 0xFF;
                this.maxVoValue = n7 >> 8 & 0xFF;
            } catch (IOException iOException) {
                throw new ICUUncheckedIOException(iOException);
            }
        }

        public UnicodeSet addPropertyStarts(int n, UnicodeSet unicodeSet) {
            CodePointTrie codePointTrie;
            switch (n) {
                case 12: {
                    codePointTrie = this.inpcTrie;
                    break;
                }
                case 13: {
                    codePointTrie = this.inscTrie;
                    break;
                }
                case 14: {
                    codePointTrie = this.voTrie;
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
            if (codePointTrie == null) {
                throw new MissingResourceException("no data for one of the text layout properties; src=" + n, "LayoutProps", "");
            }
            CodePointMap.Range range = new CodePointMap.Range();
            int n2 = 0;
            while (codePointTrie.getRange(n2, null, range)) {
                unicodeSet.add(n2);
                n2 = range.getEnd() + 1;
            }
            return unicodeSet;
        }

        static {
            $assertionsDisabled = !UCharacterProperty.class.desiredAssertionStatus();
            IS_ACCEPTABLE = new IsAcceptable(null);
            INSTANCE = new LayoutProps();
        }

        private static final class IsAcceptable
        implements ICUBinary.Authenticate {
            private IsAcceptable() {
            }

            @Override
            public boolean isDataVersionAcceptable(byte[] byArray) {
                return byArray[0] == 1;
            }

            IsAcceptable(1 var1_1) {
                this();
            }
        }
    }
}

