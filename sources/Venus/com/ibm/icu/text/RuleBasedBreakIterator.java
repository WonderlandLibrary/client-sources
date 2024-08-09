/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteration;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.BurmeseBreakEngine;
import com.ibm.icu.text.CjkBreakEngine;
import com.ibm.icu.text.DictionaryBreakEngine;
import com.ibm.icu.text.KhmerBreakEngine;
import com.ibm.icu.text.LanguageBreakEngine;
import com.ibm.icu.text.LaoBreakEngine;
import com.ibm.icu.text.RBBIRuleBuilder;
import com.ibm.icu.text.ThaiBreakEngine;
import com.ibm.icu.text.UnhandledBreakEngine;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class RuleBasedBreakIterator
extends BreakIterator {
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final int RBBI_START = 0;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_END = 2;
    private CharacterIterator fText = new StringCharacterIterator("");
    @Deprecated
    public RBBIDataWrapper fRData;
    private int fPosition;
    private int fRuleStatusIndex;
    private boolean fDone;
    private BreakCache fBreakCache = new BreakCache(this);
    private int fDictionaryCharCount = 0;
    private DictionaryCache fDictionaryCache = new DictionaryCache(this);
    private static final String RBBI_DEBUG_ARG = "rbbi";
    private static final boolean TRACE;
    private static final UnhandledBreakEngine gUnhandledBreakEngine;
    private static final List<LanguageBreakEngine> gAllBreakEngines;
    private List<LanguageBreakEngine> fBreakEngines;
    @Deprecated
    public static final String fDebugEnv;
    private static final int kMaxLookaheads = 8;
    private LookAheadResults fLookAheadMatches = new LookAheadResults();
    static final boolean $assertionsDisabled;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private RuleBasedBreakIterator() {
        List<LanguageBreakEngine> list = gAllBreakEngines;
        synchronized (list) {
            this.fBreakEngines = new ArrayList<LanguageBreakEngine>(gAllBreakEngines);
        }
    }

    public static RuleBasedBreakIterator getInstanceFromCompiledRules(InputStream inputStream) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        ruleBasedBreakIterator.fRData = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
        return ruleBasedBreakIterator;
    }

    @Deprecated
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(ByteBuffer byteBuffer) throws IOException {
        RuleBasedBreakIterator ruleBasedBreakIterator = new RuleBasedBreakIterator();
        ruleBasedBreakIterator.fRData = RBBIDataWrapper.get(byteBuffer);
        return ruleBasedBreakIterator;
    }

    public RuleBasedBreakIterator(String string) {
        this();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            RuleBasedBreakIterator.compileRules(string, byteArrayOutputStream);
            this.fRData = RBBIDataWrapper.get(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
        } catch (IOException iOException) {
            RuntimeException runtimeException = new RuntimeException("RuleBasedBreakIterator rule compilation internal error: " + iOException.getMessage());
            throw runtimeException;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object clone() {
        RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)super.clone();
        if (this.fText != null) {
            ruleBasedBreakIterator.fText = (CharacterIterator)this.fText.clone();
        }
        List<LanguageBreakEngine> list = gAllBreakEngines;
        synchronized (list) {
            ruleBasedBreakIterator.fBreakEngines = new ArrayList<LanguageBreakEngine>(gAllBreakEngines);
        }
        ruleBasedBreakIterator.fLookAheadMatches = new LookAheadResults();
        RuleBasedBreakIterator ruleBasedBreakIterator2 = ruleBasedBreakIterator;
        ruleBasedBreakIterator2.getClass();
        ruleBasedBreakIterator.fBreakCache = new BreakCache(ruleBasedBreakIterator2, this.fBreakCache);
        RuleBasedBreakIterator ruleBasedBreakIterator3 = ruleBasedBreakIterator;
        ruleBasedBreakIterator3.getClass();
        ruleBasedBreakIterator.fDictionaryCache = new DictionaryCache(ruleBasedBreakIterator3, this.fDictionaryCache);
        return ruleBasedBreakIterator;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return true;
        }
        if (this == object) {
            return false;
        }
        try {
            RuleBasedBreakIterator ruleBasedBreakIterator = (RuleBasedBreakIterator)object;
            if (this.fRData != ruleBasedBreakIterator.fRData && (this.fRData == null || ruleBasedBreakIterator.fRData == null)) {
                return false;
            }
            if (this.fRData != null && ruleBasedBreakIterator.fRData != null && !this.fRData.fRuleSource.equals(ruleBasedBreakIterator.fRData.fRuleSource)) {
                return false;
            }
            if (this.fText == null && ruleBasedBreakIterator.fText == null) {
                return true;
            }
            if (this.fText == null || ruleBasedBreakIterator.fText == null || !this.fText.equals(ruleBasedBreakIterator.fText)) {
                return false;
            }
            return this.fPosition == ruleBasedBreakIterator.fPosition;
        } catch (ClassCastException classCastException) {
            return true;
        }
    }

    public String toString() {
        String string = "";
        if (this.fRData != null) {
            string = this.fRData.fRuleSource;
        }
        return string;
    }

    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }

    @Deprecated
    public void dump(PrintStream printStream) {
        if (printStream == null) {
            printStream = System.out;
        }
        this.fRData.dump(printStream);
    }

    public static void compileRules(String string, OutputStream outputStream) throws IOException {
        RBBIRuleBuilder.compileRules(string, outputStream);
    }

    @Override
    public int first() {
        if (this.fText == null) {
            return 1;
        }
        this.fText.first();
        int n = this.fText.getIndex();
        if (!this.fBreakCache.seek(n)) {
            this.fBreakCache.populateNear(n);
        }
        this.fBreakCache.current();
        if (!$assertionsDisabled && this.fPosition != n) {
            throw new AssertionError();
        }
        return this.fPosition;
    }

    @Override
    public int last() {
        if (this.fText == null) {
            return 1;
        }
        int n = this.fText.getEndIndex();
        boolean bl = this.isBoundary(n);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
        if (this.fPosition != n && !$assertionsDisabled && this.fPosition != n) {
            throw new AssertionError();
        }
        return n;
    }

    @Override
    public int next(int n) {
        int n2 = 0;
        if (n > 0) {
            while (n > 0 && n2 != -1) {
                n2 = this.next();
                --n;
            }
        } else if (n < 0) {
            while (n < 0 && n2 != -1) {
                n2 = this.previous();
                ++n;
            }
        } else {
            n2 = this.current();
        }
        return n2;
    }

    @Override
    public int next() {
        this.fBreakCache.next();
        return this.fDone ? -1 : this.fPosition;
    }

    @Override
    public int previous() {
        this.fBreakCache.previous();
        return this.fDone ? -1 : this.fPosition;
    }

    @Override
    public int following(int n) {
        if (n < this.fText.getBeginIndex()) {
            return this.first();
        }
        n = RuleBasedBreakIterator.CISetIndex32(this.fText, n);
        this.fBreakCache.following(n);
        return this.fDone ? -1 : this.fPosition;
    }

    @Override
    public int preceding(int n) {
        if (this.fText == null || n > this.fText.getEndIndex()) {
            return this.last();
        }
        if (n < this.fText.getBeginIndex()) {
            return this.first();
        }
        int n2 = n;
        this.fBreakCache.preceding(n2);
        return this.fDone ? -1 : this.fPosition;
    }

    protected static final void checkOffset(int n, CharacterIterator characterIterator) {
        if (n < characterIterator.getBeginIndex() || n > characterIterator.getEndIndex()) {
            throw new IllegalArgumentException("offset out of bounds");
        }
    }

    @Override
    public boolean isBoundary(int n) {
        RuleBasedBreakIterator.checkOffset(n, this.fText);
        int n2 = RuleBasedBreakIterator.CISetIndex32(this.fText, n);
        boolean bl = false;
        if (this.fBreakCache.seek(n2) || this.fBreakCache.populateNear(n2)) {
            boolean bl2 = bl = this.fBreakCache.current() == n;
        }
        if (!bl) {
            this.next();
        }
        return bl;
    }

    @Override
    public int current() {
        return this.fText != null ? this.fPosition : -1;
    }

    @Override
    public int getRuleStatus() {
        int n = this.fRuleStatusIndex + this.fRData.fStatusTable[this.fRuleStatusIndex];
        int n2 = this.fRData.fStatusTable[n];
        return n2;
    }

    @Override
    public int getRuleStatusVec(int[] nArray) {
        int n = this.fRData.fStatusTable[this.fRuleStatusIndex];
        if (nArray != null) {
            int n2 = Math.min(n, nArray.length);
            for (int i = 0; i < n2; ++i) {
                nArray[i] = this.fRData.fStatusTable[this.fRuleStatusIndex + i + 1];
            }
        }
        return n;
    }

    @Override
    public CharacterIterator getText() {
        return this.fText;
    }

    @Override
    public void setText(CharacterIterator characterIterator) {
        if (characterIterator != null) {
            this.fBreakCache.reset(characterIterator.getBeginIndex(), 0);
        } else {
            this.fBreakCache.reset();
        }
        this.fDictionaryCache.reset();
        this.fText = characterIterator;
        this.first();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private LanguageBreakEngine getLanguageBreakEngine(int n) {
        for (LanguageBreakEngine object : this.fBreakEngines) {
            if (!object.handles(n)) continue;
            return object;
        }
        List<LanguageBreakEngine> list = gAllBreakEngines;
        synchronized (list) {
            int n2;
            LanguageBreakEngine languageBreakEngine2;
            for (LanguageBreakEngine languageBreakEngine2 : gAllBreakEngines) {
                if (!languageBreakEngine2.handles(n)) continue;
                this.fBreakEngines.add(languageBreakEngine2);
                return languageBreakEngine2;
            }
            int n3 = UCharacter.getIntPropertyValue(n, 4106);
            if (n3 == 22 || n3 == 20) {
                n2 = 17;
            }
            try {
                switch (n2) {
                    case 38: {
                        languageBreakEngine2 = new ThaiBreakEngine();
                        break;
                    }
                    case 24: {
                        languageBreakEngine2 = new LaoBreakEngine();
                        break;
                    }
                    case 28: {
                        languageBreakEngine2 = new BurmeseBreakEngine();
                        break;
                    }
                    case 23: {
                        languageBreakEngine2 = new KhmerBreakEngine();
                        break;
                    }
                    case 17: {
                        languageBreakEngine2 = new CjkBreakEngine(false);
                        break;
                    }
                    case 18: {
                        languageBreakEngine2 = new CjkBreakEngine(true);
                        break;
                    }
                    default: {
                        gUnhandledBreakEngine.handleChar(n);
                        languageBreakEngine2 = gUnhandledBreakEngine;
                        break;
                    }
                }
            } catch (IOException iOException) {
                languageBreakEngine2 = null;
            }
            if (languageBreakEngine2 != null && languageBreakEngine2 != gUnhandledBreakEngine) {
                gAllBreakEngines.add(languageBreakEngine2);
                this.fBreakEngines.add(languageBreakEngine2);
            }
            return languageBreakEngine2;
        }
    }

    private int handleNext() {
        if (TRACE) {
            System.out.println("Handle Next   pos      char  state category");
        }
        this.fRuleStatusIndex = 0;
        this.fDictionaryCharCount = 0;
        CharacterIterator characterIterator = this.fText;
        Trie2 trie2 = this.fRData.fTrie;
        short[] sArray = this.fRData.fFTable.fTable;
        int n = this.fPosition;
        characterIterator.setIndex(n);
        int n2 = n;
        int n3 = characterIterator.current();
        if (n3 >= 55296 && (n3 = CharacterIteration.nextTrail32(characterIterator, n3)) == Integer.MAX_VALUE) {
            this.fDone = true;
            return 1;
        }
        int n4 = 1;
        int n5 = this.fRData.getRowIndex(n4);
        int n6 = 3;
        int n7 = this.fRData.fFTable.fFlags;
        int n8 = 1;
        if ((n7 & 2) != 0) {
            n6 = 2;
            n8 = 0;
            if (TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(n3, 10));
                System.out.println(RBBIDataWrapper.intToString(n4, 7) + RBBIDataWrapper.intToString(n6, 6));
            }
        }
        this.fLookAheadMatches.reset();
        while (n4 != 0) {
            int n9;
            short s;
            if (n3 == Integer.MAX_VALUE) {
                if (n8 == 2) break;
                n8 = 2;
                n6 = 1;
            } else if (n8 == 1) {
                n6 = (short)trie2.get(n3);
                if ((n6 & 0x4000) != 0) {
                    ++this.fDictionaryCharCount;
                    n6 = (short)(n6 & 0xFFFFBFFF);
                }
                if (TRACE) {
                    System.out.print("            " + RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                    System.out.print(RBBIDataWrapper.intToHexString(n3, 10));
                    System.out.println(RBBIDataWrapper.intToString(n4, 7) + RBBIDataWrapper.intToString(n6, 6));
                }
                if ((n3 = (int)characterIterator.next()) >= 55296) {
                    n3 = CharacterIteration.nextTrail32(characterIterator, n3);
                }
            } else {
                n8 = 1;
            }
            n4 = sArray[n5 + 4 + n6];
            n5 = this.fRData.getRowIndex(n4);
            if (sArray[n5 + 0] == -1) {
                n2 = characterIterator.getIndex();
                if (n3 >= 65536 && n3 <= 0x10FFFF) {
                    --n2;
                }
                this.fRuleStatusIndex = sArray[n5 + 2];
            }
            if ((s = sArray[n5 + 0]) > 0 && (n9 = this.fLookAheadMatches.getPosition(s)) >= 0) {
                this.fRuleStatusIndex = sArray[n5 + 2];
                this.fPosition = n9;
                return n9;
            }
            n9 = sArray[n5 + 1];
            if (n9 == 0) continue;
            int n10 = characterIterator.getIndex();
            if (n3 >= 65536 && n3 <= 0x10FFFF) {
                --n10;
            }
            this.fLookAheadMatches.setPosition(n9, n10);
        }
        if (n2 == n) {
            if (TRACE) {
                System.out.println("Iterator did not move. Advancing by 1.");
            }
            characterIterator.setIndex(n);
            CharacterIteration.next32(characterIterator);
            n2 = characterIterator.getIndex();
            this.fRuleStatusIndex = 0;
        }
        this.fPosition = n2;
        if (TRACE) {
            System.out.println("result = " + n2);
        }
        return n2;
    }

    private int handleSafePrevious(int n) {
        short s = 0;
        int n2 = 0;
        CharacterIterator characterIterator = this.fText;
        Trie2 trie2 = this.fRData.fTrie;
        short[] sArray = this.fRData.fRTable.fTable;
        RuleBasedBreakIterator.CISetIndex32(characterIterator, n);
        if (TRACE) {
            System.out.print("Handle Previous   pos   char  state category");
        }
        if (characterIterator.getIndex() == characterIterator.getBeginIndex()) {
            return 1;
        }
        int n3 = CharacterIteration.previous32(characterIterator);
        int n4 = 1;
        int n5 = this.fRData.getRowIndex(n4);
        while (n3 != Integer.MAX_VALUE) {
            s = (short)trie2.get(n3);
            s = (short)(s & 0xFFFFBFFF);
            if (TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(characterIterator.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(n3, 10));
                System.out.println(RBBIDataWrapper.intToString(n4, 7) + RBBIDataWrapper.intToString(s, 6));
            }
            if (!$assertionsDisabled && s >= this.fRData.fHeader.fCatCount) {
                throw new AssertionError();
            }
            n4 = sArray[n5 + 4 + s];
            n5 = this.fRData.getRowIndex(n4);
            if (n4 == 0) break;
            n3 = CharacterIteration.previous32(characterIterator);
        }
        n2 = characterIterator.getIndex();
        if (TRACE) {
            System.out.println("result = " + n2);
        }
        return n2;
    }

    private static int CISetIndex32(CharacterIterator characterIterator, int n) {
        if (n <= characterIterator.getBeginIndex()) {
            characterIterator.first();
        } else if (n >= characterIterator.getEndIndex()) {
            characterIterator.setIndex(characterIterator.getEndIndex());
        } else if (Character.isLowSurrogate(characterIterator.setIndex(n)) && !Character.isHighSurrogate(characterIterator.previous())) {
            characterIterator.next();
        }
        return characterIterator.getIndex();
    }

    static CharacterIterator access$000(RuleBasedBreakIterator ruleBasedBreakIterator) {
        return ruleBasedBreakIterator.fText;
    }

    static LanguageBreakEngine access$100(RuleBasedBreakIterator ruleBasedBreakIterator, int n) {
        return ruleBasedBreakIterator.getLanguageBreakEngine(n);
    }

    static boolean access$202(RuleBasedBreakIterator ruleBasedBreakIterator, boolean bl) {
        ruleBasedBreakIterator.fDone = bl;
        return ruleBasedBreakIterator.fDone;
    }

    static int access$302(RuleBasedBreakIterator ruleBasedBreakIterator, int n) {
        ruleBasedBreakIterator.fPosition = n;
        return ruleBasedBreakIterator.fPosition;
    }

    static int access$402(RuleBasedBreakIterator ruleBasedBreakIterator, int n) {
        ruleBasedBreakIterator.fRuleStatusIndex = n;
        return ruleBasedBreakIterator.fRuleStatusIndex;
    }

    static int access$500(RuleBasedBreakIterator ruleBasedBreakIterator, int n) {
        return ruleBasedBreakIterator.handleSafePrevious(n);
    }

    static int access$600(RuleBasedBreakIterator ruleBasedBreakIterator) {
        return ruleBasedBreakIterator.handleNext();
    }

    static int access$400(RuleBasedBreakIterator ruleBasedBreakIterator) {
        return ruleBasedBreakIterator.fRuleStatusIndex;
    }

    static DictionaryCache access$700(RuleBasedBreakIterator ruleBasedBreakIterator) {
        return ruleBasedBreakIterator.fDictionaryCache;
    }

    static int access$800(RuleBasedBreakIterator ruleBasedBreakIterator) {
        return ruleBasedBreakIterator.fDictionaryCharCount;
    }

    static {
        $assertionsDisabled = !RuleBasedBreakIterator.class.desiredAssertionStatus();
        TRACE = ICUDebug.enabled(RBBI_DEBUG_ARG) && ICUDebug.value(RBBI_DEBUG_ARG).indexOf("trace") >= 0;
        gUnhandledBreakEngine = new UnhandledBreakEngine();
        gAllBreakEngines = new ArrayList<LanguageBreakEngine>();
        gAllBreakEngines.add(gUnhandledBreakEngine);
        fDebugEnv = ICUDebug.enabled(RBBI_DEBUG_ARG) ? ICUDebug.value(RBBI_DEBUG_ARG) : null;
    }

    class BreakCache {
        static final boolean RetainCachePosition = false;
        static final boolean UpdateCachePosition = true;
        static final int CACHE_SIZE = 128;
        int fStartBufIdx;
        int fEndBufIdx;
        int fTextIdx;
        int fBufIdx;
        int[] fBoundaries;
        short[] fStatuses;
        DictionaryBreakEngine.DequeI fSideBuffer;
        static final boolean $assertionsDisabled = !RuleBasedBreakIterator.class.desiredAssertionStatus();
        final RuleBasedBreakIterator this$0;

        BreakCache(RuleBasedBreakIterator ruleBasedBreakIterator) {
            this.this$0 = ruleBasedBreakIterator;
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            this.reset();
        }

        void reset(int n, int n2) {
            this.fStartBufIdx = 0;
            this.fEndBufIdx = 0;
            this.fTextIdx = n;
            this.fBufIdx = 0;
            this.fBoundaries[0] = n;
            this.fStatuses[0] = (short)n2;
        }

        void reset() {
            this.reset(0, 0);
        }

        void next() {
            if (this.fBufIdx == this.fEndBufIdx) {
                RuleBasedBreakIterator.access$202(this.this$0, !this.populateFollowing());
                RuleBasedBreakIterator.access$302(this.this$0, this.fTextIdx);
                RuleBasedBreakIterator.access$402(this.this$0, this.fStatuses[this.fBufIdx]);
            } else {
                this.fBufIdx = this.modChunkSize(this.fBufIdx + 1);
                this.fTextIdx = RuleBasedBreakIterator.access$302(this.this$0, this.fBoundaries[this.fBufIdx]);
                RuleBasedBreakIterator.access$402(this.this$0, this.fStatuses[this.fBufIdx]);
            }
        }

        void previous() {
            int n = this.fBufIdx;
            if (this.fBufIdx == this.fStartBufIdx) {
                this.populatePreceding();
            } else {
                this.fBufIdx = this.modChunkSize(this.fBufIdx - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
            }
            RuleBasedBreakIterator.access$202(this.this$0, this.fBufIdx == n);
            RuleBasedBreakIterator.access$302(this.this$0, this.fTextIdx);
            RuleBasedBreakIterator.access$402(this.this$0, this.fStatuses[this.fBufIdx]);
        }

        void following(int n) {
            if (n == this.fTextIdx || this.seek(n) || this.populateNear(n)) {
                RuleBasedBreakIterator.access$202(this.this$0, false);
                this.next();
            }
        }

        void preceding(int n) {
            if (n == this.fTextIdx || this.seek(n) || this.populateNear(n)) {
                if (n == this.fTextIdx) {
                    this.previous();
                } else {
                    if (!$assertionsDisabled && n <= this.fTextIdx) {
                        throw new AssertionError();
                    }
                    this.current();
                }
            }
        }

        int current() {
            RuleBasedBreakIterator.access$302(this.this$0, this.fTextIdx);
            RuleBasedBreakIterator.access$402(this.this$0, this.fStatuses[this.fBufIdx]);
            RuleBasedBreakIterator.access$202(this.this$0, false);
            return this.fTextIdx;
        }

        boolean populateNear(int n) {
            if (!$assertionsDisabled && n >= this.fBoundaries[this.fStartBufIdx] && n <= this.fBoundaries[this.fEndBufIdx]) {
                throw new AssertionError();
            }
            if (n < this.fBoundaries[this.fStartBufIdx] - 15 || n > this.fBoundaries[this.fEndBufIdx] + 15) {
                int n2 = RuleBasedBreakIterator.access$000(this.this$0).getBeginIndex();
                int n3 = 0;
                if (n > n2 + 20) {
                    int n4 = RuleBasedBreakIterator.access$500(this.this$0, n);
                    if (n4 > n2) {
                        RuleBasedBreakIterator.access$302(this.this$0, n4);
                        n2 = RuleBasedBreakIterator.access$600(this.this$0);
                        if (n2 == n4 + 1 || n2 == n4 + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.access$000(this.this$0).setIndex(n4)) && Character.isLowSurrogate(RuleBasedBreakIterator.access$000(this.this$0).next())) {
                            n2 = RuleBasedBreakIterator.access$600(this.this$0);
                        }
                    }
                    n3 = RuleBasedBreakIterator.access$400(this.this$0);
                }
                this.reset(n2, n3);
            }
            if (this.fBoundaries[this.fEndBufIdx] < n) {
                while (this.fBoundaries[this.fEndBufIdx] < n) {
                    if (this.populateFollowing()) continue;
                    if (!$assertionsDisabled) {
                        throw new AssertionError();
                    }
                    return true;
                }
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx > n) {
                    this.previous();
                }
                return false;
            }
            if (this.fBoundaries[this.fStartBufIdx] > n) {
                while (this.fBoundaries[this.fStartBufIdx] > n) {
                    this.populatePreceding();
                }
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx < n) {
                    this.next();
                }
                if (this.fTextIdx > n) {
                    this.previous();
                }
                return false;
            }
            if (!$assertionsDisabled && this.fTextIdx != n) {
                throw new AssertionError();
            }
            return false;
        }

        boolean populateFollowing() {
            int n = this.fBoundaries[this.fEndBufIdx];
            short s = this.fStatuses[this.fEndBufIdx];
            int n2 = 0;
            int n3 = 0;
            if (RuleBasedBreakIterator.access$700(this.this$0).following(n)) {
                this.addFollowing(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fStatusIndex, false);
                return false;
            }
            RuleBasedBreakIterator.access$302(this.this$0, n);
            n2 = RuleBasedBreakIterator.access$600(this.this$0);
            if (n2 == -1) {
                return true;
            }
            n3 = RuleBasedBreakIterator.access$400(this.this$0);
            if (RuleBasedBreakIterator.access$800(this.this$0) > 0) {
                RuleBasedBreakIterator.access$700(this.this$0).populateDictionary(n, n2, s, n3);
                if (RuleBasedBreakIterator.access$700(this.this$0).following(n)) {
                    this.addFollowing(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fStatusIndex, false);
                    return false;
                }
            }
            this.addFollowing(n2, n3, false);
            for (int i = 0; i < 6 && (n2 = RuleBasedBreakIterator.access$600(this.this$0)) != -1 && RuleBasedBreakIterator.access$800(this.this$0) <= 0; ++i) {
                this.addFollowing(n2, RuleBasedBreakIterator.access$400(this.this$0), true);
            }
            return false;
        }

        boolean populatePreceding() {
            int n;
            int n2 = this.fBoundaries[this.fStartBufIdx];
            int n3 = RuleBasedBreakIterator.access$000(this.this$0).getBeginIndex();
            if (n2 == n3) {
                return true;
            }
            int n4 = n3;
            int n5 = 0;
            if (RuleBasedBreakIterator.access$700(this.this$0).preceding(n2)) {
                this.addPreceding(RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fBoundary, RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fStatusIndex, false);
                return false;
            }
            int n6 = n2;
            do {
                if ((n6 = (n6 -= 30) <= n3 ? n3 : RuleBasedBreakIterator.access$500(this.this$0, n6)) == -1 || n6 == n3) {
                    n4 = n3;
                    n5 = 0;
                    continue;
                }
                RuleBasedBreakIterator.access$302(this.this$0, n6);
                n4 = RuleBasedBreakIterator.access$600(this.this$0);
                if (n4 == n6 + 1 || n4 == n6 + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.access$000(this.this$0).setIndex(n6)) && Character.isLowSurrogate(RuleBasedBreakIterator.access$000(this.this$0).next())) {
                    n4 = RuleBasedBreakIterator.access$600(this.this$0);
                }
                n5 = RuleBasedBreakIterator.access$400(this.this$0);
            } while (n4 >= n2);
            this.fSideBuffer.removeAllElements();
            this.fSideBuffer.push(n4);
            this.fSideBuffer.push(n5);
            do {
                n = RuleBasedBreakIterator.access$302(this.this$0, n4);
                int n7 = n5;
                n4 = RuleBasedBreakIterator.access$600(this.this$0);
                n5 = RuleBasedBreakIterator.access$400(this.this$0);
                if (n4 == -1) break;
                boolean bl = false;
                if (RuleBasedBreakIterator.access$800(this.this$0) != 0) {
                    int n8 = n4;
                    RuleBasedBreakIterator.access$700(this.this$0).populateDictionary(n, n8, n7, n5);
                    while (RuleBasedBreakIterator.access$700(this.this$0).following(n)) {
                        n4 = RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fBoundary;
                        n5 = RuleBasedBreakIterator.access$700((RuleBasedBreakIterator)this.this$0).fStatusIndex;
                        bl = true;
                        if (!$assertionsDisabled && n4 <= n) {
                            throw new AssertionError();
                        }
                        if (n4 >= n2) break;
                        if (!$assertionsDisabled && n4 > n8) {
                            throw new AssertionError();
                        }
                        this.fSideBuffer.push(n4);
                        this.fSideBuffer.push(n5);
                        n = n4;
                    }
                    if (!$assertionsDisabled && n4 != n8 && n4 < n2) {
                        throw new AssertionError();
                    }
                }
                if (bl || n4 >= n2) continue;
                this.fSideBuffer.push(n4);
                this.fSideBuffer.push(n5);
            } while (n4 < n2);
            n = 0;
            if (!this.fSideBuffer.isEmpty()) {
                n5 = this.fSideBuffer.pop();
                n4 = this.fSideBuffer.pop();
                this.addPreceding(n4, n5, false);
                n = 1;
            }
            while (!this.fSideBuffer.isEmpty()) {
                n5 = this.fSideBuffer.pop();
                n4 = this.fSideBuffer.pop();
                if (this.addPreceding(n4, n5, true)) continue;
                break;
            }
            return n != 0;
        }

        void addFollowing(int n, int n2, boolean bl) {
            if (!$assertionsDisabled && n <= this.fBoundaries[this.fEndBufIdx]) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n2 > Short.MAX_VALUE) {
                throw new AssertionError();
            }
            int n3 = this.modChunkSize(this.fEndBufIdx + 1);
            if (n3 == this.fStartBufIdx) {
                this.fStartBufIdx = this.modChunkSize(this.fStartBufIdx + 6);
            }
            this.fBoundaries[n3] = n;
            this.fStatuses[n3] = (short)n2;
            this.fEndBufIdx = n3;
            if (bl) {
                this.fBufIdx = n3;
                this.fTextIdx = n;
            } else if (!$assertionsDisabled && n3 == this.fBufIdx) {
                throw new AssertionError();
            }
        }

        boolean addPreceding(int n, int n2, boolean bl) {
            if (!$assertionsDisabled && n >= this.fBoundaries[this.fStartBufIdx]) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n2 > Short.MAX_VALUE) {
                throw new AssertionError();
            }
            int n3 = this.modChunkSize(this.fStartBufIdx - 1);
            if (n3 == this.fEndBufIdx) {
                if (this.fBufIdx == this.fEndBufIdx && !bl) {
                    return true;
                }
                this.fEndBufIdx = this.modChunkSize(this.fEndBufIdx - 1);
            }
            this.fBoundaries[n3] = n;
            this.fStatuses[n3] = (short)n2;
            this.fStartBufIdx = n3;
            if (bl) {
                this.fBufIdx = n3;
                this.fTextIdx = n;
            }
            return false;
        }

        boolean seek(int n) {
            if (n < this.fBoundaries[this.fStartBufIdx] || n > this.fBoundaries[this.fEndBufIdx]) {
                return true;
            }
            if (n == this.fBoundaries[this.fStartBufIdx]) {
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return false;
            }
            if (n == this.fBoundaries[this.fEndBufIdx]) {
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return false;
            }
            int n2 = this.fStartBufIdx;
            int n3 = this.fEndBufIdx;
            while (n2 != n3) {
                int n4 = (n2 + n3 + (n2 > n3 ? 128 : 0)) / 2;
                if (this.fBoundaries[n4 = this.modChunkSize(n4)] > n) {
                    n3 = n4;
                    continue;
                }
                n2 = this.modChunkSize(n4 + 1);
            }
            if (!$assertionsDisabled && this.fBoundaries[n3] <= n) {
                throw new AssertionError();
            }
            this.fBufIdx = this.modChunkSize(n3 - 1);
            this.fTextIdx = this.fBoundaries[this.fBufIdx];
            if (!$assertionsDisabled && this.fTextIdx > n) {
                throw new AssertionError();
            }
            return false;
        }

        BreakCache(RuleBasedBreakIterator ruleBasedBreakIterator, BreakCache breakCache) {
            this.this$0 = ruleBasedBreakIterator;
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            this.fStartBufIdx = breakCache.fStartBufIdx;
            this.fEndBufIdx = breakCache.fEndBufIdx;
            this.fTextIdx = breakCache.fTextIdx;
            this.fBufIdx = breakCache.fBufIdx;
            this.fBoundaries = (int[])breakCache.fBoundaries.clone();
            this.fStatuses = (short[])breakCache.fStatuses.clone();
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
        }

        void dumpCache() {
            System.out.printf("fTextIdx:%d   fBufIdx:%d%n", this.fTextIdx, this.fBufIdx);
            int n = this.fStartBufIdx;
            while (true) {
                System.out.printf("%d  %d%n", n, this.fBoundaries[n]);
                if (n == this.fEndBufIdx) break;
                n = this.modChunkSize(n + 1);
            }
        }

        private final int modChunkSize(int n) {
            return n & 0x7F;
        }
    }

    class DictionaryCache {
        DictionaryBreakEngine.DequeI fBreaks;
        int fPositionInCache;
        int fStart;
        int fLimit;
        int fFirstRuleStatusIndex;
        int fOtherRuleStatusIndex;
        int fBoundary;
        int fStatusIndex;
        static final boolean $assertionsDisabled = !RuleBasedBreakIterator.class.desiredAssertionStatus();
        final RuleBasedBreakIterator this$0;

        void reset() {
            this.fPositionInCache = -1;
            this.fStart = 0;
            this.fLimit = 0;
            this.fFirstRuleStatusIndex = 0;
            this.fOtherRuleStatusIndex = 0;
            this.fBreaks.removeAllElements();
        }

        boolean following(int n) {
            if (n >= this.fLimit || n < this.fStart) {
                this.fPositionInCache = -1;
                return true;
            }
            int n2 = 0;
            if (this.fPositionInCache >= 0 && this.fPositionInCache < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == n) {
                ++this.fPositionInCache;
                if (this.fPositionInCache >= this.fBreaks.size()) {
                    this.fPositionInCache = -1;
                    return true;
                }
                n2 = this.fBreaks.elementAt(this.fPositionInCache);
                if (!$assertionsDisabled && n2 <= n) {
                    throw new AssertionError();
                }
                this.fBoundary = n2;
                this.fStatusIndex = this.fOtherRuleStatusIndex;
                return false;
            }
            this.fPositionInCache = 0;
            while (this.fPositionInCache < this.fBreaks.size()) {
                n2 = this.fBreaks.elementAt(this.fPositionInCache);
                if (n2 > n) {
                    this.fBoundary = n2;
                    this.fStatusIndex = this.fOtherRuleStatusIndex;
                    return false;
                }
                ++this.fPositionInCache;
            }
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            this.fPositionInCache = -1;
            return true;
        }

        boolean preceding(int n) {
            if (n <= this.fStart || n > this.fLimit) {
                this.fPositionInCache = -1;
                return true;
            }
            if (n == this.fLimit) {
                this.fPositionInCache = this.fBreaks.size() - 1;
                if (this.fPositionInCache >= 0 && !$assertionsDisabled && this.fBreaks.elementAt(this.fPositionInCache) != n) {
                    throw new AssertionError();
                }
            }
            if (this.fPositionInCache > 0 && this.fPositionInCache < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == n) {
                --this.fPositionInCache;
                int n2 = this.fBreaks.elementAt(this.fPositionInCache);
                if (!$assertionsDisabled && n2 >= n) {
                    throw new AssertionError();
                }
                this.fBoundary = n2;
                this.fStatusIndex = n2 == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                return false;
            }
            if (this.fPositionInCache == 0) {
                this.fPositionInCache = -1;
                return true;
            }
            this.fPositionInCache = this.fBreaks.size() - 1;
            while (this.fPositionInCache >= 0) {
                int n3 = this.fBreaks.elementAt(this.fPositionInCache);
                if (n3 < n) {
                    this.fBoundary = n3;
                    this.fStatusIndex = n3 == this.fStart ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex;
                    return false;
                }
                --this.fPositionInCache;
            }
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            this.fPositionInCache = -1;
            return true;
        }

        void populateDictionary(int n, int n2, int n3, int n4) {
            if (n2 - n <= 1) {
                return;
            }
            this.reset();
            this.fFirstRuleStatusIndex = n3;
            this.fOtherRuleStatusIndex = n4;
            int n5 = n;
            int n6 = n2;
            int n7 = 0;
            RuleBasedBreakIterator.access$000(this.this$0).setIndex(n5);
            int n8 = CharacterIteration.current32(RuleBasedBreakIterator.access$000(this.this$0));
            short s = (short)this.this$0.fRData.fTrie.get(n8);
            while (true) {
                int n9;
                if ((n9 = RuleBasedBreakIterator.access$000(this.this$0).getIndex()) < n6 && (s & 0x4000) == 0) {
                    n8 = CharacterIteration.next32(RuleBasedBreakIterator.access$000(this.this$0));
                    s = (short)this.this$0.fRData.fTrie.get(n8);
                    continue;
                }
                if (n9 >= n6) break;
                LanguageBreakEngine languageBreakEngine = RuleBasedBreakIterator.access$100(this.this$0, n8);
                if (languageBreakEngine != null) {
                    n7 += languageBreakEngine.findBreaks(RuleBasedBreakIterator.access$000(this.this$0), n5, n6, this.fBreaks);
                }
                n8 = CharacterIteration.current32(RuleBasedBreakIterator.access$000(this.this$0));
                s = (short)this.this$0.fRData.fTrie.get(n8);
            }
            if (n7 > 0) {
                if (!$assertionsDisabled && n7 != this.fBreaks.size()) {
                    throw new AssertionError();
                }
                if (n < this.fBreaks.elementAt(0)) {
                    this.fBreaks.offer(n);
                }
                if (n2 > this.fBreaks.peek()) {
                    this.fBreaks.push(n2);
                }
                this.fPositionInCache = 0;
                this.fStart = this.fBreaks.elementAt(0);
                this.fLimit = this.fBreaks.peek();
            }
        }

        DictionaryCache(RuleBasedBreakIterator ruleBasedBreakIterator) {
            this.this$0 = ruleBasedBreakIterator;
            this.fPositionInCache = -1;
            this.fBreaks = new DictionaryBreakEngine.DequeI();
        }

        DictionaryCache(RuleBasedBreakIterator ruleBasedBreakIterator, DictionaryCache dictionaryCache) {
            this.this$0 = ruleBasedBreakIterator;
            try {
                this.fBreaks = (DictionaryBreakEngine.DequeI)dictionaryCache.fBreaks.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new RuntimeException(cloneNotSupportedException);
            }
            this.fPositionInCache = dictionaryCache.fPositionInCache;
            this.fStart = dictionaryCache.fStart;
            this.fLimit = dictionaryCache.fLimit;
            this.fFirstRuleStatusIndex = dictionaryCache.fFirstRuleStatusIndex;
            this.fOtherRuleStatusIndex = dictionaryCache.fOtherRuleStatusIndex;
            this.fBoundary = dictionaryCache.fBoundary;
            this.fStatusIndex = dictionaryCache.fStatusIndex;
        }
    }

    private static class LookAheadResults {
        int fUsedSlotLimit = 0;
        int[] fPositions = new int[8];
        int[] fKeys = new int[8];
        static final boolean $assertionsDisabled = !RuleBasedBreakIterator.class.desiredAssertionStatus();

        LookAheadResults() {
        }

        int getPosition(int n) {
            for (int i = 0; i < this.fUsedSlotLimit; ++i) {
                if (this.fKeys[i] != n) continue;
                return this.fPositions[i];
            }
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            return 1;
        }

        void setPosition(int n, int n2) {
            int n3;
            for (n3 = 0; n3 < this.fUsedSlotLimit; ++n3) {
                if (this.fKeys[n3] != n) continue;
                this.fPositions[n3] = n2;
                return;
            }
            if (n3 >= 8) {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                n3 = 7;
            }
            this.fKeys[n3] = n;
            this.fPositions[n3] = n2;
            if (!$assertionsDisabled && this.fUsedSlotLimit != n3) {
                throw new AssertionError();
            }
            this.fUsedSlotLimit = n3 + 1;
        }

        void reset() {
            this.fUsedSlotLimit = 0;
        }
    }
}

