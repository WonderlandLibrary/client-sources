package com.ibm.icu.text;

import java.text.*;
import java.nio.*;
import java.io.*;
import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.impl.*;

public class RuleBasedBreakIterator extends BreakIterator
{
    private static final int START_STATE = 1;
    private static final int STOP_STATE = 0;
    private static final int RBBI_START = 0;
    private static final int RBBI_RUN = 1;
    private static final int RBBI_END = 2;
    private CharacterIterator fText;
    @Deprecated
    public RBBIDataWrapper fRData;
    private int fPosition;
    private int fRuleStatusIndex;
    private boolean fDone;
    private BreakCache fBreakCache;
    private int fDictionaryCharCount;
    private DictionaryCache fDictionaryCache;
    private static final String RBBI_DEBUG_ARG = "rbbi";
    private static final boolean TRACE;
    private static final UnhandledBreakEngine gUnhandledBreakEngine;
    private static final List<LanguageBreakEngine> gAllBreakEngines;
    private List<LanguageBreakEngine> fBreakEngines;
    @Deprecated
    public static final String fDebugEnv;
    private static final int kMaxLookaheads = 8;
    private LookAheadResults fLookAheadMatches;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    private RuleBasedBreakIterator() {
        this.fText = new StringCharacterIterator("");
        this.fBreakCache = new BreakCache();
        this.fDictionaryCache = new DictionaryCache();
        this.fLookAheadMatches = new LookAheadResults();
        this.fDictionaryCharCount = 0;
        synchronized (RuleBasedBreakIterator.gAllBreakEngines) {
            this.fBreakEngines = new ArrayList<LanguageBreakEngine>(RuleBasedBreakIterator.gAllBreakEngines);
        }
    }
    
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(final InputStream is) throws IOException {
        final RuleBasedBreakIterator This = new RuleBasedBreakIterator();
        This.fRData = RBBIDataWrapper.get(ICUBinary.getByteBufferFromInputStreamAndCloseStream(is));
        return This;
    }
    
    @Deprecated
    public static RuleBasedBreakIterator getInstanceFromCompiledRules(final ByteBuffer bytes) throws IOException {
        final RuleBasedBreakIterator This = new RuleBasedBreakIterator();
        This.fRData = RBBIDataWrapper.get(bytes);
        return This;
    }
    
    public RuleBasedBreakIterator(final String rules) {
        this();
        try {
            final ByteArrayOutputStream ruleOS = new ByteArrayOutputStream();
            compileRules(rules, ruleOS);
            this.fRData = RBBIDataWrapper.get(ByteBuffer.wrap(ruleOS.toByteArray()));
        }
        catch (IOException e) {
            final RuntimeException rte = new RuntimeException("RuleBasedBreakIterator rule compilation internal error: " + e.getMessage());
            throw rte;
        }
    }
    
    @Override
    public Object clone() {
        final RuleBasedBreakIterator result = (RuleBasedBreakIterator)super.clone();
        if (this.fText != null) {
            result.fText = (CharacterIterator)this.fText.clone();
        }
        synchronized (RuleBasedBreakIterator.gAllBreakEngines) {
            result.fBreakEngines = new ArrayList<LanguageBreakEngine>(RuleBasedBreakIterator.gAllBreakEngines);
        }
        result.fLookAheadMatches = new LookAheadResults();
        result.fBreakCache = result.new BreakCache(this.fBreakCache);
        result.fDictionaryCache = result.new DictionaryCache(this.fDictionaryCache);
        return result;
    }
    
    @Override
    public boolean equals(final Object that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }
        try {
            final RuleBasedBreakIterator other = (RuleBasedBreakIterator)that;
            return (this.fRData == other.fRData || (this.fRData != null && other.fRData != null)) && (this.fRData == null || other.fRData == null || this.fRData.fRuleSource.equals(other.fRData.fRuleSource)) && ((this.fText == null && other.fText == null) || (this.fText != null && other.fText != null && this.fText.equals(other.fText) && this.fPosition == other.fPosition));
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        String retStr = "";
        if (this.fRData != null) {
            retStr = this.fRData.fRuleSource;
        }
        return retStr;
    }
    
    @Override
    public int hashCode() {
        return this.fRData.fRuleSource.hashCode();
    }
    
    @Deprecated
    public void dump(PrintStream out) {
        if (out == null) {
            out = System.out;
        }
        this.fRData.dump(out);
    }
    
    public static void compileRules(final String rules, final OutputStream ruleBinary) throws IOException {
        RBBIRuleBuilder.compileRules(rules, ruleBinary);
    }
    
    @Override
    public int first() {
        if (this.fText == null) {
            return -1;
        }
        this.fText.first();
        final int start = this.fText.getIndex();
        if (!this.fBreakCache.seek(start)) {
            this.fBreakCache.populateNear(start);
        }
        this.fBreakCache.current();
        assert this.fPosition == start;
        return this.fPosition;
    }
    
    @Override
    public int last() {
        if (this.fText == null) {
            return -1;
        }
        final int endPos = this.fText.getEndIndex();
        final boolean endShouldBeBoundary = this.isBoundary(endPos);
        assert endShouldBeBoundary;
        if (this.fPosition != endPos && !RuleBasedBreakIterator.$assertionsDisabled && this.fPosition != endPos) {
            throw new AssertionError();
        }
        return endPos;
    }
    
    @Override
    public int next(int n) {
        int result = 0;
        if (n > 0) {
            while (n > 0 && result != -1) {
                result = this.next();
                --n;
            }
        }
        else if (n < 0) {
            while (n < 0 && result != -1) {
                result = this.previous();
                ++n;
            }
        }
        else {
            result = this.current();
        }
        return result;
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
    public int following(int startPos) {
        if (startPos < this.fText.getBeginIndex()) {
            return this.first();
        }
        startPos = CISetIndex32(this.fText, startPos);
        this.fBreakCache.following(startPos);
        return this.fDone ? -1 : this.fPosition;
    }
    
    @Override
    public int preceding(final int offset) {
        if (this.fText == null || offset > this.fText.getEndIndex()) {
            return this.last();
        }
        if (offset < this.fText.getBeginIndex()) {
            return this.first();
        }
        final int adjustedOffset = offset;
        this.fBreakCache.preceding(adjustedOffset);
        return this.fDone ? -1 : this.fPosition;
    }
    
    protected static final void checkOffset(final int offset, final CharacterIterator text) {
        if (offset < text.getBeginIndex() || offset > text.getEndIndex()) {
            throw new IllegalArgumentException("offset out of bounds");
        }
    }
    
    @Override
    public boolean isBoundary(final int offset) {
        checkOffset(offset, this.fText);
        final int adjustedOffset = CISetIndex32(this.fText, offset);
        boolean result = false;
        if (this.fBreakCache.seek(adjustedOffset) || this.fBreakCache.populateNear(adjustedOffset)) {
            result = (this.fBreakCache.current() == offset);
        }
        if (!result) {
            this.next();
        }
        return result;
    }
    
    @Override
    public int current() {
        return (this.fText != null) ? this.fPosition : -1;
    }
    
    @Override
    public int getRuleStatus() {
        final int idx = this.fRuleStatusIndex + this.fRData.fStatusTable[this.fRuleStatusIndex];
        final int tagVal = this.fRData.fStatusTable[idx];
        return tagVal;
    }
    
    @Override
    public int getRuleStatusVec(final int[] fillInArray) {
        final int numStatusVals = this.fRData.fStatusTable[this.fRuleStatusIndex];
        if (fillInArray != null) {
            for (int numToCopy = Math.min(numStatusVals, fillInArray.length), i = 0; i < numToCopy; ++i) {
                fillInArray[i] = this.fRData.fStatusTable[this.fRuleStatusIndex + i + 1];
            }
        }
        return numStatusVals;
    }
    
    @Override
    public CharacterIterator getText() {
        return this.fText;
    }
    
    @Override
    public void setText(final CharacterIterator newText) {
        if (newText != null) {
            this.fBreakCache.reset(newText.getBeginIndex(), 0);
        }
        else {
            this.fBreakCache.reset();
        }
        this.fDictionaryCache.reset();
        this.fText = newText;
        this.first();
    }
    
    private LanguageBreakEngine getLanguageBreakEngine(final int c) {
        for (final LanguageBreakEngine candidate : this.fBreakEngines) {
            if (candidate.handles(c)) {
                return candidate;
            }
        }
        synchronized (RuleBasedBreakIterator.gAllBreakEngines) {
            for (final LanguageBreakEngine candidate2 : RuleBasedBreakIterator.gAllBreakEngines) {
                if (candidate2.handles(c)) {
                    this.fBreakEngines.add(candidate2);
                    return candidate2;
                }
            }
            int script = UCharacter.getIntPropertyValue(c, 4106);
            if (script == 22 || script == 20) {
                script = 17;
            }
            LanguageBreakEngine eng = null;
            try {
                switch (script) {
                    case 38: {
                        eng = new ThaiBreakEngine();
                        break;
                    }
                    case 24: {
                        eng = new LaoBreakEngine();
                        break;
                    }
                    case 28: {
                        eng = new BurmeseBreakEngine();
                        break;
                    }
                    case 23: {
                        eng = new KhmerBreakEngine();
                        break;
                    }
                    case 17: {
                        eng = new CjkBreakEngine(false);
                        break;
                    }
                    case 18: {
                        eng = new CjkBreakEngine(true);
                        break;
                    }
                    default: {
                        RuleBasedBreakIterator.gUnhandledBreakEngine.handleChar(c);
                        eng = RuleBasedBreakIterator.gUnhandledBreakEngine;
                        break;
                    }
                }
            }
            catch (IOException e) {
                eng = null;
            }
            if (eng != null && eng != RuleBasedBreakIterator.gUnhandledBreakEngine) {
                RuleBasedBreakIterator.gAllBreakEngines.add(eng);
                this.fBreakEngines.add(eng);
            }
            return eng;
        }
    }
    
    private int handleNext() {
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("Handle Next   pos      char  state category");
        }
        this.fRuleStatusIndex = 0;
        this.fDictionaryCharCount = 0;
        final CharacterIterator text = this.fText;
        final Trie2 trie = this.fRData.fTrie;
        final short[] stateTable = this.fRData.fFTable.fTable;
        final int initialPosition = this.fPosition;
        text.setIndex(initialPosition);
        int result = initialPosition;
        int c = text.current();
        if (c >= 55296) {
            c = CharacterIteration.nextTrail32(text, c);
            if (c == Integer.MAX_VALUE) {
                this.fDone = true;
                return -1;
            }
        }
        int state = 1;
        int row = this.fRData.getRowIndex(state);
        short category = 3;
        final int flagsState = this.fRData.fFTable.fFlags;
        int mode = 1;
        if ((flagsState & 0x2) != 0x0) {
            category = 2;
            mode = 0;
            if (RuleBasedBreakIterator.TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(text.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(c, 10));
                System.out.println(RBBIDataWrapper.intToString(state, 7) + RBBIDataWrapper.intToString(category, 6));
            }
        }
        this.fLookAheadMatches.reset();
        while (state != 0) {
            if (c == Integer.MAX_VALUE) {
                if (mode == 2) {
                    break;
                }
                mode = 2;
                category = 1;
            }
            else if (mode == 1) {
                category = (short)trie.get(c);
                if ((category & 0x4000) != 0x0) {
                    ++this.fDictionaryCharCount;
                    category &= 0xFFFFBFFF;
                }
                if (RuleBasedBreakIterator.TRACE) {
                    System.out.print("            " + RBBIDataWrapper.intToString(text.getIndex(), 5));
                    System.out.print(RBBIDataWrapper.intToHexString(c, 10));
                    System.out.println(RBBIDataWrapper.intToString(state, 7) + RBBIDataWrapper.intToString(category, 6));
                }
                c = text.next();
                if (c >= 55296) {
                    c = CharacterIteration.nextTrail32(text, c);
                }
            }
            else {
                mode = 1;
            }
            state = stateTable[row + 4 + category];
            row = this.fRData.getRowIndex(state);
            if (stateTable[row + 0] == -1) {
                result = text.getIndex();
                if (c >= 65536 && c <= 1114111) {
                    --result;
                }
                this.fRuleStatusIndex = stateTable[row + 2];
            }
            final int completedRule = stateTable[row + 0];
            if (completedRule > 0) {
                final int lookaheadResult = this.fLookAheadMatches.getPosition(completedRule);
                if (lookaheadResult >= 0) {
                    this.fRuleStatusIndex = stateTable[row + 2];
                    return this.fPosition = lookaheadResult;
                }
            }
            final int rule = stateTable[row + 1];
            if (rule != 0) {
                int pos = text.getIndex();
                if (c >= 65536 && c <= 1114111) {
                    --pos;
                }
                this.fLookAheadMatches.setPosition(rule, pos);
            }
        }
        if (result == initialPosition) {
            if (RuleBasedBreakIterator.TRACE) {
                System.out.println("Iterator did not move. Advancing by 1.");
            }
            text.setIndex(initialPosition);
            CharacterIteration.next32(text);
            result = text.getIndex();
            this.fRuleStatusIndex = 0;
        }
        this.fPosition = result;
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("result = " + result);
        }
        return result;
    }
    
    private int handleSafePrevious(final int fromPosition) {
        short category = 0;
        int result = 0;
        final CharacterIterator text = this.fText;
        final Trie2 trie = this.fRData.fTrie;
        final short[] stateTable = this.fRData.fRTable.fTable;
        CISetIndex32(text, fromPosition);
        if (RuleBasedBreakIterator.TRACE) {
            System.out.print("Handle Previous   pos   char  state category");
        }
        if (text.getIndex() == text.getBeginIndex()) {
            return -1;
        }
        int c = CharacterIteration.previous32(text);
        int state = 1;
        int row = this.fRData.getRowIndex(state);
        while (c != Integer.MAX_VALUE) {
            category = (short)trie.get(c);
            category &= 0xFFFFBFFF;
            if (RuleBasedBreakIterator.TRACE) {
                System.out.print("            " + RBBIDataWrapper.intToString(text.getIndex(), 5));
                System.out.print(RBBIDataWrapper.intToHexString(c, 10));
                System.out.println(RBBIDataWrapper.intToString(state, 7) + RBBIDataWrapper.intToString(category, 6));
            }
            assert category < this.fRData.fHeader.fCatCount;
            state = stateTable[row + 4 + category];
            row = this.fRData.getRowIndex(state);
            if (state == 0) {
                break;
            }
            c = CharacterIteration.previous32(text);
        }
        result = text.getIndex();
        if (RuleBasedBreakIterator.TRACE) {
            System.out.println("result = " + result);
        }
        return result;
    }
    
    private static int CISetIndex32(final CharacterIterator ci, final int index) {
        if (index <= ci.getBeginIndex()) {
            ci.first();
        }
        else if (index >= ci.getEndIndex()) {
            ci.setIndex(ci.getEndIndex());
        }
        else if (Character.isLowSurrogate(ci.setIndex(index)) && !Character.isHighSurrogate(ci.previous())) {
            ci.next();
        }
        return ci.getIndex();
    }
    
    static {
        TRACE = (ICUDebug.enabled("rbbi") && ICUDebug.value("rbbi").indexOf("trace") >= 0);
        gUnhandledBreakEngine = new UnhandledBreakEngine();
        (gAllBreakEngines = new ArrayList<LanguageBreakEngine>()).add(RuleBasedBreakIterator.gUnhandledBreakEngine);
        fDebugEnv = (ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null);
    }
    
    private static class LookAheadResults
    {
        int fUsedSlotLimit;
        int[] fPositions;
        int[] fKeys;
        
        LookAheadResults() {
            this.fUsedSlotLimit = 0;
            this.fPositions = new int[8];
            this.fKeys = new int[8];
        }
        
        int getPosition(final int key) {
            for (int i = 0; i < this.fUsedSlotLimit; ++i) {
                if (this.fKeys[i] == key) {
                    return this.fPositions[i];
                }
            }
            assert false;
            return -1;
        }
        
        void setPosition(final int key, final int position) {
            int i;
            for (i = 0; i < this.fUsedSlotLimit; ++i) {
                if (this.fKeys[i] == key) {
                    this.fPositions[i] = position;
                    return;
                }
            }
            if (i >= 8) {
                assert false;
                i = 7;
            }
            this.fKeys[i] = key;
            this.fPositions[i] = position;
            assert this.fUsedSlotLimit == i;
            this.fUsedSlotLimit = i + 1;
        }
        
        void reset() {
            this.fUsedSlotLimit = 0;
        }
    }
    
    class DictionaryCache
    {
        DictionaryBreakEngine.DequeI fBreaks;
        int fPositionInCache;
        int fStart;
        int fLimit;
        int fFirstRuleStatusIndex;
        int fOtherRuleStatusIndex;
        int fBoundary;
        int fStatusIndex;
        static final /* synthetic */ boolean $assertionsDisabled;
        
        void reset() {
            this.fPositionInCache = -1;
            this.fStart = 0;
            this.fLimit = 0;
            this.fFirstRuleStatusIndex = 0;
            this.fOtherRuleStatusIndex = 0;
            this.fBreaks.removeAllElements();
        }
        
        boolean following(final int fromPos) {
            if (fromPos >= this.fLimit || fromPos < this.fStart) {
                this.fPositionInCache = -1;
                return false;
            }
            int r = 0;
            if (this.fPositionInCache >= 0 && this.fPositionInCache < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == fromPos) {
                ++this.fPositionInCache;
                if (this.fPositionInCache >= this.fBreaks.size()) {
                    this.fPositionInCache = -1;
                    return false;
                }
                r = this.fBreaks.elementAt(this.fPositionInCache);
                assert r > fromPos;
                this.fBoundary = r;
                this.fStatusIndex = this.fOtherRuleStatusIndex;
                return true;
            }
            else {
                this.fPositionInCache = 0;
                while (this.fPositionInCache < this.fBreaks.size()) {
                    r = this.fBreaks.elementAt(this.fPositionInCache);
                    if (r > fromPos) {
                        this.fBoundary = r;
                        this.fStatusIndex = this.fOtherRuleStatusIndex;
                        return true;
                    }
                    ++this.fPositionInCache;
                }
                assert false;
                this.fPositionInCache = -1;
                return false;
            }
        }
        
        boolean preceding(final int fromPos) {
            if (fromPos <= this.fStart || fromPos > this.fLimit) {
                this.fPositionInCache = -1;
                return false;
            }
            if (fromPos == this.fLimit) {
                this.fPositionInCache = this.fBreaks.size() - 1;
                if (this.fPositionInCache >= 0 && !DictionaryCache.$assertionsDisabled && this.fBreaks.elementAt(this.fPositionInCache) != fromPos) {
                    throw new AssertionError();
                }
            }
            if (this.fPositionInCache > 0 && this.fPositionInCache < this.fBreaks.size() && this.fBreaks.elementAt(this.fPositionInCache) == fromPos) {
                --this.fPositionInCache;
                final int r = this.fBreaks.elementAt(this.fPositionInCache);
                assert r < fromPos;
                this.fStatusIndex = (((this.fBoundary = r) == this.fStart) ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex);
                return true;
            }
            else {
                if (this.fPositionInCache == 0) {
                    this.fPositionInCache = -1;
                    return false;
                }
                this.fPositionInCache = this.fBreaks.size() - 1;
                while (this.fPositionInCache >= 0) {
                    final int r = this.fBreaks.elementAt(this.fPositionInCache);
                    if (r < fromPos) {
                        this.fBoundary = r;
                        this.fStatusIndex = ((r == this.fStart) ? this.fFirstRuleStatusIndex : this.fOtherRuleStatusIndex);
                        return true;
                    }
                    --this.fPositionInCache;
                }
                assert false;
                this.fPositionInCache = -1;
                return false;
            }
        }
        
        void populateDictionary(final int startPos, final int endPos, final int firstRuleStatus, final int otherRuleStatus) {
            if (endPos - startPos <= 1) {
                return;
            }
            this.reset();
            this.fFirstRuleStatusIndex = firstRuleStatus;
            this.fOtherRuleStatusIndex = otherRuleStatus;
            final int rangeStart = startPos;
            final int rangeEnd = endPos;
            int foundBreakCount = 0;
            RuleBasedBreakIterator.this.fText.setIndex(rangeStart);
            int c = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
            int category = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(c);
            while (true) {
                final int current;
                if ((current = RuleBasedBreakIterator.this.fText.getIndex()) < rangeEnd && (category & 0x4000) == 0x0) {
                    c = CharacterIteration.next32(RuleBasedBreakIterator.this.fText);
                    category = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(c);
                }
                else {
                    if (current >= rangeEnd) {
                        break;
                    }
                    final LanguageBreakEngine lbe = RuleBasedBreakIterator.this.getLanguageBreakEngine(c);
                    if (lbe != null) {
                        foundBreakCount += lbe.findBreaks(RuleBasedBreakIterator.this.fText, rangeStart, rangeEnd, this.fBreaks);
                    }
                    c = CharacterIteration.current32(RuleBasedBreakIterator.this.fText);
                    category = (short)RuleBasedBreakIterator.this.fRData.fTrie.get(c);
                }
            }
            if (foundBreakCount > 0) {
                assert foundBreakCount == this.fBreaks.size();
                if (startPos < this.fBreaks.elementAt(0)) {
                    this.fBreaks.offer(startPos);
                }
                if (endPos > this.fBreaks.peek()) {
                    this.fBreaks.push(endPos);
                }
                this.fPositionInCache = 0;
                this.fStart = this.fBreaks.elementAt(0);
                this.fLimit = this.fBreaks.peek();
            }
        }
        
        DictionaryCache() {
            this.fPositionInCache = -1;
            this.fBreaks = new DictionaryBreakEngine.DequeI();
        }
        
        DictionaryCache(final DictionaryCache src) {
            try {
                this.fBreaks = (DictionaryBreakEngine.DequeI)src.fBreaks.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            this.fPositionInCache = src.fPositionInCache;
            this.fStart = src.fStart;
            this.fLimit = src.fLimit;
            this.fFirstRuleStatusIndex = src.fFirstRuleStatusIndex;
            this.fOtherRuleStatusIndex = src.fOtherRuleStatusIndex;
            this.fBoundary = src.fBoundary;
            this.fStatusIndex = src.fStatusIndex;
        }
    }
    
    class BreakCache
    {
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
        
        BreakCache() {
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            this.reset();
        }
        
        void reset(final int pos, final int ruleStatus) {
            this.fStartBufIdx = 0;
            this.fEndBufIdx = 0;
            this.fTextIdx = pos;
            this.fBufIdx = 0;
            this.fBoundaries[0] = pos;
            this.fStatuses[0] = (short)ruleStatus;
        }
        
        void reset() {
            this.reset(0, 0);
        }
        
        void next() {
            if (this.fBufIdx == this.fEndBufIdx) {
                RuleBasedBreakIterator.this.fDone = !this.populateFollowing();
                RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
                RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            }
            else {
                this.fBufIdx = this.modChunkSize(this.fBufIdx + 1);
                this.fTextIdx = (RuleBasedBreakIterator.this.fPosition = this.fBoundaries[this.fBufIdx]);
                RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            }
        }
        
        void previous() {
            final int initialBufIdx = this.fBufIdx;
            if (this.fBufIdx == this.fStartBufIdx) {
                this.populatePreceding();
            }
            else {
                this.fBufIdx = this.modChunkSize(this.fBufIdx - 1);
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
            }
            RuleBasedBreakIterator.this.fDone = (this.fBufIdx == initialBufIdx);
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
        }
        
        void following(final int startPos) {
            if (startPos == this.fTextIdx || this.seek(startPos) || this.populateNear(startPos)) {
                RuleBasedBreakIterator.this.fDone = false;
                this.next();
            }
        }
        
        void preceding(final int startPos) {
            if (startPos == this.fTextIdx || this.seek(startPos) || this.populateNear(startPos)) {
                if (startPos == this.fTextIdx) {
                    this.previous();
                }
                else {
                    assert startPos > this.fTextIdx;
                    this.current();
                }
            }
        }
        
        int current() {
            RuleBasedBreakIterator.this.fPosition = this.fTextIdx;
            RuleBasedBreakIterator.this.fRuleStatusIndex = this.fStatuses[this.fBufIdx];
            RuleBasedBreakIterator.this.fDone = false;
            return this.fTextIdx;
        }
        
        boolean populateNear(final int position) {
            assert position > this.fBoundaries[this.fEndBufIdx];
            if (position < this.fBoundaries[this.fStartBufIdx] - 15 || position > this.fBoundaries[this.fEndBufIdx] + 15) {
                int aBoundary = RuleBasedBreakIterator.this.fText.getBeginIndex();
                int ruleStatusIndex = 0;
                if (position > aBoundary + 20) {
                    final int backupPos = RuleBasedBreakIterator.this.handleSafePrevious(position);
                    if (backupPos > aBoundary) {
                        RuleBasedBreakIterator.this.fPosition = backupPos;
                        aBoundary = RuleBasedBreakIterator.this.handleNext();
                        if (aBoundary == backupPos + 1 || (aBoundary == backupPos + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(backupPos)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                            aBoundary = RuleBasedBreakIterator.this.handleNext();
                        }
                    }
                    ruleStatusIndex = RuleBasedBreakIterator.this.fRuleStatusIndex;
                }
                this.reset(aBoundary, ruleStatusIndex);
            }
            if (this.fBoundaries[this.fEndBufIdx] < position) {
                while (this.fBoundaries[this.fEndBufIdx] < position) {
                    if (!this.populateFollowing()) {
                        assert false;
                        return false;
                    }
                }
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx > position) {
                    this.previous();
                }
                return true;
            }
            if (this.fBoundaries[this.fStartBufIdx] > position) {
                while (this.fBoundaries[this.fStartBufIdx] > position) {
                    this.populatePreceding();
                }
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                while (this.fTextIdx < position) {
                    this.next();
                }
                if (this.fTextIdx > position) {
                    this.previous();
                }
                return true;
            }
            assert this.fTextIdx == position;
            return true;
        }
        
        boolean populateFollowing() {
            final int fromPosition = this.fBoundaries[this.fEndBufIdx];
            final int fromRuleStatusIdx = this.fStatuses[this.fEndBufIdx];
            int pos = 0;
            int ruleStatusIdx = 0;
            if (RuleBasedBreakIterator.this.fDictionaryCache.following(fromPosition)) {
                this.addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            RuleBasedBreakIterator.this.fPosition = fromPosition;
            pos = RuleBasedBreakIterator.this.handleNext();
            if (pos == -1) {
                return false;
            }
            ruleStatusIdx = RuleBasedBreakIterator.this.fRuleStatusIndex;
            if (RuleBasedBreakIterator.this.fDictionaryCharCount > 0) {
                RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(fromPosition, pos, fromRuleStatusIdx, ruleStatusIdx);
                if (RuleBasedBreakIterator.this.fDictionaryCache.following(fromPosition)) {
                    this.addFollowing(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                    return true;
                }
            }
            this.addFollowing(pos, ruleStatusIdx, true);
            for (int count = 0; count < 6; ++count) {
                pos = RuleBasedBreakIterator.this.handleNext();
                if (pos == -1) {
                    break;
                }
                if (RuleBasedBreakIterator.this.fDictionaryCharCount > 0) {
                    break;
                }
                this.addFollowing(pos, RuleBasedBreakIterator.this.fRuleStatusIndex, false);
            }
            return true;
        }
        
        boolean populatePreceding() {
            final int textBegin = RuleBasedBreakIterator.this.fText.getBeginIndex();
            final int fromPosition = this.fBoundaries[this.fStartBufIdx];
            if (fromPosition == textBegin) {
                return false;
            }
            int position = textBegin;
            int positionStatusIdx = 0;
            if (RuleBasedBreakIterator.this.fDictionaryCache.preceding(fromPosition)) {
                this.addPreceding(RuleBasedBreakIterator.this.fDictionaryCache.fBoundary, RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex, true);
                return true;
            }
            int backupPosition = fromPosition;
            do {
                backupPosition -= 30;
                if (backupPosition <= textBegin) {
                    backupPosition = textBegin;
                }
                else {
                    backupPosition = RuleBasedBreakIterator.this.handleSafePrevious(backupPosition);
                }
                if (backupPosition == -1 || backupPosition == textBegin) {
                    position = textBegin;
                    positionStatusIdx = 0;
                }
                else {
                    RuleBasedBreakIterator.this.fPosition = backupPosition;
                    position = RuleBasedBreakIterator.this.handleNext();
                    if (position == backupPosition + 1 || (position == backupPosition + 2 && Character.isHighSurrogate(RuleBasedBreakIterator.this.fText.setIndex(backupPosition)) && Character.isLowSurrogate(RuleBasedBreakIterator.this.fText.next()))) {
                        position = RuleBasedBreakIterator.this.handleNext();
                    }
                    positionStatusIdx = RuleBasedBreakIterator.this.fRuleStatusIndex;
                }
            } while (position >= fromPosition);
            this.fSideBuffer.removeAllElements();
            this.fSideBuffer.push(position);
            this.fSideBuffer.push(positionStatusIdx);
            do {
                int prevPosition = RuleBasedBreakIterator.this.fPosition = position;
                final int prevStatusIdx = positionStatusIdx;
                position = RuleBasedBreakIterator.this.handleNext();
                positionStatusIdx = RuleBasedBreakIterator.this.fRuleStatusIndex;
                if (position == -1) {
                    break;
                }
                boolean segmentHandledByDictionary = false;
                if (RuleBasedBreakIterator.this.fDictionaryCharCount != 0) {
                    final int dictSegEndPosition = position;
                    RuleBasedBreakIterator.this.fDictionaryCache.populateDictionary(prevPosition, dictSegEndPosition, prevStatusIdx, positionStatusIdx);
                    while (RuleBasedBreakIterator.this.fDictionaryCache.following(prevPosition)) {
                        position = RuleBasedBreakIterator.this.fDictionaryCache.fBoundary;
                        positionStatusIdx = RuleBasedBreakIterator.this.fDictionaryCache.fStatusIndex;
                        segmentHandledByDictionary = true;
                        assert position > prevPosition;
                        if (position >= fromPosition) {
                            break;
                        }
                        assert position <= dictSegEndPosition;
                        this.fSideBuffer.push(position);
                        this.fSideBuffer.push(positionStatusIdx);
                        prevPosition = position;
                    }
                    assert position >= fromPosition;
                }
                if (segmentHandledByDictionary || position >= fromPosition) {
                    continue;
                }
                this.fSideBuffer.push(position);
                this.fSideBuffer.push(positionStatusIdx);
            } while (position < fromPosition);
            boolean success = false;
            if (!this.fSideBuffer.isEmpty()) {
                positionStatusIdx = this.fSideBuffer.pop();
                position = this.fSideBuffer.pop();
                this.addPreceding(position, positionStatusIdx, true);
                success = true;
            }
            while (!this.fSideBuffer.isEmpty()) {
                positionStatusIdx = this.fSideBuffer.pop();
                position = this.fSideBuffer.pop();
                if (!this.addPreceding(position, positionStatusIdx, false)) {
                    break;
                }
            }
            return success;
        }
        
        void addFollowing(final int position, final int ruleStatusIdx, final boolean update) {
            assert position > this.fBoundaries[this.fEndBufIdx];
            assert ruleStatusIdx <= 32767;
            final int nextIdx = this.modChunkSize(this.fEndBufIdx + 1);
            if (nextIdx == this.fStartBufIdx) {
                this.fStartBufIdx = this.modChunkSize(this.fStartBufIdx + 6);
            }
            this.fBoundaries[nextIdx] = position;
            this.fStatuses[nextIdx] = (short)ruleStatusIdx;
            this.fEndBufIdx = nextIdx;
            if (update) {
                this.fBufIdx = nextIdx;
                this.fTextIdx = position;
            }
            else {
                assert nextIdx != this.fBufIdx;
            }
        }
        
        boolean addPreceding(final int position, final int ruleStatusIdx, final boolean update) {
            assert position < this.fBoundaries[this.fStartBufIdx];
            assert ruleStatusIdx <= 32767;
            final int nextIdx = this.modChunkSize(this.fStartBufIdx - 1);
            if (nextIdx == this.fEndBufIdx) {
                if (this.fBufIdx == this.fEndBufIdx && !update) {
                    return false;
                }
                this.fEndBufIdx = this.modChunkSize(this.fEndBufIdx - 1);
            }
            this.fBoundaries[nextIdx] = position;
            this.fStatuses[nextIdx] = (short)ruleStatusIdx;
            this.fStartBufIdx = nextIdx;
            if (update) {
                this.fBufIdx = nextIdx;
                this.fTextIdx = position;
            }
            return true;
        }
        
        boolean seek(final int pos) {
            if (pos < this.fBoundaries[this.fStartBufIdx] || pos > this.fBoundaries[this.fEndBufIdx]) {
                return false;
            }
            if (pos == this.fBoundaries[this.fStartBufIdx]) {
                this.fBufIdx = this.fStartBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            }
            if (pos == this.fBoundaries[this.fEndBufIdx]) {
                this.fBufIdx = this.fEndBufIdx;
                this.fTextIdx = this.fBoundaries[this.fBufIdx];
                return true;
            }
            int min = this.fStartBufIdx;
            int max = this.fEndBufIdx;
            while (min != max) {
                int probe = (min + max + ((min > max) ? 128 : 0)) / 2;
                probe = this.modChunkSize(probe);
                if (this.fBoundaries[probe] > pos) {
                    max = probe;
                }
                else {
                    min = this.modChunkSize(probe + 1);
                }
            }
            assert this.fBoundaries[max] > pos;
            this.fBufIdx = this.modChunkSize(max - 1);
            this.fTextIdx = this.fBoundaries[this.fBufIdx];
            assert this.fTextIdx <= pos;
            return true;
        }
        
        BreakCache(final BreakCache src) {
            this.fBoundaries = new int[128];
            this.fStatuses = new short[128];
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
            this.fStartBufIdx = src.fStartBufIdx;
            this.fEndBufIdx = src.fEndBufIdx;
            this.fTextIdx = src.fTextIdx;
            this.fBufIdx = src.fBufIdx;
            this.fBoundaries = src.fBoundaries.clone();
            this.fStatuses = src.fStatuses.clone();
            this.fSideBuffer = new DictionaryBreakEngine.DequeI();
        }
        
        void dumpCache() {
            System.out.printf("fTextIdx:%d   fBufIdx:%d%n", this.fTextIdx, this.fBufIdx);
            int i = this.fStartBufIdx;
            while (true) {
                System.out.printf("%d  %d%n", i, this.fBoundaries[i]);
                if (i == this.fEndBufIdx) {
                    break;
                }
                i = this.modChunkSize(i + 1);
            }
        }
        
        private final int modChunkSize(final int index) {
            return index & 0x7F;
        }
    }
}
