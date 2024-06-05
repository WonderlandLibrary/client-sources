package com.ibm.icu.text;

import java.lang.reflect.*;
import java.text.*;
import java.util.concurrent.locks.*;
import java.util.*;
import com.ibm.icu.impl.coll.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;

public final class RuleBasedCollator extends Collator
{
    private Lock frozenLock;
    private CollationBuffer collationBuffer;
    CollationData data;
    SharedObject.Reference<CollationSettings> settings;
    CollationTailoring tailoring;
    private ULocale validLocale;
    private boolean actualLocaleIsSameAsValid;
    
    public RuleBasedCollator(final String rules) throws Exception {
        if (rules == null) {
            throw new IllegalArgumentException("Collation rules can not be null");
        }
        this.validLocale = ULocale.ROOT;
        this.internalBuildTailoring(rules);
    }
    
    private final void internalBuildTailoring(final String rules) throws Exception {
        final CollationTailoring base = CollationRoot.getRoot();
        final ClassLoader classLoader = ClassLoaderUtil.getClassLoader(this.getClass());
        CollationTailoring t;
        try {
            final Class<?> builderClass = classLoader.loadClass("com.ibm.icu.impl.coll.CollationBuilder");
            final Object builder = builderClass.getConstructor(CollationTailoring.class).newInstance(base);
            final Method parseAndBuild = builderClass.getMethod("parseAndBuild", String.class);
            t = (CollationTailoring)parseAndBuild.invoke(builder, rules);
        }
        catch (InvocationTargetException e) {
            throw (Exception)e.getTargetException();
        }
        t.actualLocale = null;
        this.adoptTailoring(t);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }
    
    private final void initMaxExpansions() {
        synchronized (this.tailoring) {
            if (this.tailoring.maxExpansions == null) {
                this.tailoring.maxExpansions = CollationElementIterator.computeMaxExpansions(this.tailoring.data);
            }
        }
    }
    
    public CollationElementIterator getCollationElementIterator(final String source) {
        this.initMaxExpansions();
        return new CollationElementIterator(source, this);
    }
    
    public CollationElementIterator getCollationElementIterator(final CharacterIterator source) {
        this.initMaxExpansions();
        final CharacterIterator newsource = (CharacterIterator)source.clone();
        return new CollationElementIterator(newsource, this);
    }
    
    public CollationElementIterator getCollationElementIterator(final UCharacterIterator source) {
        this.initMaxExpansions();
        return new CollationElementIterator(source, this);
    }
    
    @Override
    public boolean isFrozen() {
        return this.frozenLock != null;
    }
    
    @Override
    public Collator freeze() {
        if (!this.isFrozen()) {
            this.frozenLock = new ReentrantLock();
            if (this.collationBuffer == null) {
                this.collationBuffer = new CollationBuffer(this.data);
            }
        }
        return this;
    }
    
    @Override
    public RuleBasedCollator cloneAsThawed() {
        try {
            final RuleBasedCollator result = (RuleBasedCollator)super.clone();
            result.settings = this.settings.clone();
            result.collationBuffer = null;
            result.frozenLock = null;
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
    
    private void checkNotFrozen() {
        if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify frozen RuleBasedCollator");
        }
    }
    
    private final CollationSettings getOwnedSettings() {
        return this.settings.copyOnWrite();
    }
    
    private final CollationSettings getDefaultSettings() {
        return this.tailoring.settings.readOnly();
    }
    
    @Deprecated
    public void setHiraganaQuaternary(final boolean flag) {
        this.checkNotFrozen();
    }
    
    @Deprecated
    public void setHiraganaQuaternaryDefault() {
        this.checkNotFrozen();
    }
    
    public void setUpperCaseFirst(final boolean upperfirst) {
        this.checkNotFrozen();
        if (upperfirst == this.isUpperCaseFirst()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setCaseFirst(upperfirst ? 768 : 0);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setLowerCaseFirst(final boolean lowerfirst) {
        this.checkNotFrozen();
        if (lowerfirst == this.isLowerCaseFirst()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setCaseFirst(lowerfirst ? 512 : 0);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public final void setCaseFirstDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setCaseFirstDefault(defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setAlternateHandlingDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setAlternateHandlingDefault(defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setCaseLevelDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlagDefault(1024, defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setDecompositionDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlagDefault(1, defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setFrenchCollationDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlagDefault(2048, defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setStrengthDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setStrengthDefault(defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setNumericCollationDefault() {
        this.checkNotFrozen();
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlagDefault(2, defaultSettings.options);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setFrenchCollation(final boolean flag) {
        this.checkNotFrozen();
        if (flag == this.isFrenchCollation()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlag(2048, flag);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setAlternateHandlingShifted(final boolean shifted) {
        this.checkNotFrozen();
        if (shifted == this.isAlternateHandlingShifted()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setAlternateHandlingShifted(shifted);
        this.setFastLatinOptions(ownedSettings);
    }
    
    public void setCaseLevel(final boolean flag) {
        this.checkNotFrozen();
        if (flag == this.isCaseLevel()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlag(1024, flag);
        this.setFastLatinOptions(ownedSettings);
    }
    
    @Override
    public void setDecomposition(final int decomposition) {
        this.checkNotFrozen();
        boolean flag = false;
        switch (decomposition) {
            case 16: {
                flag = false;
                break;
            }
            case 17: {
                flag = true;
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong decomposition mode.");
            }
        }
        if (flag == this.settings.readOnly().getFlag(1)) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlag(1, flag);
        this.setFastLatinOptions(ownedSettings);
    }
    
    @Override
    public void setStrength(final int newStrength) {
        this.checkNotFrozen();
        if (newStrength == this.getStrength()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setStrength(newStrength);
        this.setFastLatinOptions(ownedSettings);
    }
    
    @Override
    public RuleBasedCollator setMaxVariable(int group) {
        int value;
        if (group == -1) {
            value = -1;
        }
        else {
            if (4096 > group || group > 4099) {
                throw new IllegalArgumentException("illegal max variable group " + group);
            }
            value = group - 4096;
        }
        final int oldValue = this.settings.readOnly().getMaxVariable();
        if (value == oldValue) {
            return this;
        }
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == defaultSettings && value < 0) {
            return this;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        if (group == -1) {
            group = 4096 + defaultSettings.getMaxVariable();
        }
        final long varTop = this.data.getLastPrimaryForGroup(group);
        assert varTop != 0L;
        ownedSettings.setMaxVariable(value, defaultSettings.options);
        ownedSettings.variableTop = varTop;
        this.setFastLatinOptions(ownedSettings);
        return this;
    }
    
    @Override
    public int getMaxVariable() {
        return 4096 + this.settings.readOnly().getMaxVariable();
    }
    
    @Deprecated
    @Override
    public int setVariableTop(final String varTop) {
        this.checkNotFrozen();
        if (varTop == null || varTop.length() == 0) {
            throw new IllegalArgumentException("Variable top argument string can not be null or zero in length.");
        }
        final boolean numeric = this.settings.readOnly().isNumeric();
        long ce1;
        long ce2;
        if (this.settings.readOnly().dontCheckFCD()) {
            final UTF16CollationIterator ci = new UTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci.nextCE();
            ce2 = ci.nextCE();
        }
        else {
            final FCDUTF16CollationIterator ci2 = new FCDUTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci2.nextCE();
            ce2 = ci2.nextCE();
        }
        if (ce1 == 4311744768L || ce2 != 4311744768L) {
            throw new IllegalArgumentException("Variable top argument string must map to exactly one collation element");
        }
        this.internalSetVariableTop(ce1 >>> 32);
        return (int)this.settings.readOnly().variableTop;
    }
    
    @Deprecated
    @Override
    public void setVariableTop(final int varTop) {
        this.checkNotFrozen();
        this.internalSetVariableTop((long)varTop & 0xFFFFFFFFL);
    }
    
    private void internalSetVariableTop(long varTop) {
        if (varTop != this.settings.readOnly().variableTop) {
            final int group = this.data.getGroupForPrimary(varTop);
            if (group < 4096 || 4099 < group) {
                throw new IllegalArgumentException("The variable top must be a primary weight in the space/punctuation/symbols/currency symbols range");
            }
            final long v = this.data.getLastPrimaryForGroup(group);
            assert v != 0L && v >= varTop;
            varTop = v;
            if (varTop != this.settings.readOnly().variableTop) {
                final CollationSettings ownedSettings = this.getOwnedSettings();
                ownedSettings.setMaxVariable(group - 4096, this.getDefaultSettings().options);
                ownedSettings.variableTop = varTop;
                this.setFastLatinOptions(ownedSettings);
            }
        }
    }
    
    public void setNumericCollation(final boolean flag) {
        this.checkNotFrozen();
        if (flag == this.getNumericCollation()) {
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        ownedSettings.setFlag(2, flag);
        this.setFastLatinOptions(ownedSettings);
    }
    
    @Override
    public void setReorderCodes(final int... order) {
        this.checkNotFrozen();
        int length = (order != null) ? order.length : 0;
        if (length == 1 && order[0] == 103) {
            length = 0;
        }
        Label_0075: {
            if (length == 0) {
                if (this.settings.readOnly().reorderCodes.length != 0) {
                    break Label_0075;
                }
            }
            else if (!Arrays.equals(order, this.settings.readOnly().reorderCodes)) {
                break Label_0075;
            }
            return;
        }
        final CollationSettings defaultSettings = this.getDefaultSettings();
        if (length == 1 && order[0] == -1) {
            if (this.settings.readOnly() != defaultSettings) {
                final CollationSettings ownedSettings = this.getOwnedSettings();
                ownedSettings.copyReorderingFrom(defaultSettings);
                this.setFastLatinOptions(ownedSettings);
            }
            return;
        }
        final CollationSettings ownedSettings = this.getOwnedSettings();
        if (length == 0) {
            ownedSettings.resetReordering();
        }
        else {
            ownedSettings.setReordering(this.data, order.clone());
        }
        this.setFastLatinOptions(ownedSettings);
    }
    
    private void setFastLatinOptions(final CollationSettings ownedSettings) {
        ownedSettings.fastLatinOptions = CollationFastLatin.getOptions(this.data, ownedSettings, ownedSettings.fastLatinPrimaries);
    }
    
    public String getRules() {
        return this.tailoring.getRules();
    }
    
    public String getRules(final boolean fullrules) {
        if (!fullrules) {
            return this.tailoring.getRules();
        }
        return CollationLoader.getRootRules() + this.tailoring.getRules();
    }
    
    @Override
    public UnicodeSet getTailoredSet() {
        final UnicodeSet tailored = new UnicodeSet();
        if (this.data.base != null) {
            new TailoredSet(tailored).forData(this.data);
        }
        return tailored;
    }
    
    public void getContractionsAndExpansions(final UnicodeSet contractions, final UnicodeSet expansions, final boolean addPrefixes) throws Exception {
        if (contractions != null) {
            contractions.clear();
        }
        if (expansions != null) {
            expansions.clear();
        }
        new ContractionsAndExpansions(contractions, expansions, null, addPrefixes).forData(this.data);
    }
    
    @Deprecated
    void internalAddContractions(final int c, final UnicodeSet set) {
        new ContractionsAndExpansions(set, null, null, false).forCodePoint(this.data, c);
    }
    
    @Override
    public CollationKey getCollationKey(final String source) {
        if (source == null) {
            return null;
        }
        CollationBuffer buffer = null;
        try {
            buffer = this.getCollationBuffer();
            return this.getCollationKey(source, buffer);
        }
        finally {
            this.releaseCollationBuffer(buffer);
        }
    }
    
    private CollationKey getCollationKey(final String source, final CollationBuffer buffer) {
        buffer.rawCollationKey = this.getRawCollationKey(source, buffer.rawCollationKey, buffer);
        return new CollationKey(source, buffer.rawCollationKey);
    }
    
    @Override
    public RawCollationKey getRawCollationKey(final String source, final RawCollationKey key) {
        if (source == null) {
            return null;
        }
        CollationBuffer buffer = null;
        try {
            buffer = this.getCollationBuffer();
            return this.getRawCollationKey(source, key, buffer);
        }
        finally {
            this.releaseCollationBuffer(buffer);
        }
    }
    
    private RawCollationKey getRawCollationKey(final CharSequence source, RawCollationKey key, final CollationBuffer buffer) {
        if (key == null) {
            key = new RawCollationKey(this.simpleKeyLengthEstimate(source));
        }
        else if (key.bytes == null) {
            key.bytes = new byte[this.simpleKeyLengthEstimate(source)];
        }
        final CollationKeyByteSink sink = new CollationKeyByteSink(key);
        this.writeSortKey(source, sink, buffer);
        key.size = sink.NumberOfBytesAppended();
        return key;
    }
    
    private int simpleKeyLengthEstimate(final CharSequence source) {
        return 2 * source.length() + 10;
    }
    
    private void writeSortKey(final CharSequence s, final CollationKeyByteSink sink, final CollationBuffer buffer) {
        final boolean numeric = this.settings.readOnly().isNumeric();
        if (this.settings.readOnly().dontCheckFCD()) {
            buffer.leftUTF16CollIter.setText(numeric, s, 0);
            CollationKeys.writeSortKeyUpToQuaternary(buffer.leftUTF16CollIter, this.data.compressibleBytes, this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        }
        else {
            buffer.leftFCDUTF16Iter.setText(numeric, s, 0);
            CollationKeys.writeSortKeyUpToQuaternary(buffer.leftFCDUTF16Iter, this.data.compressibleBytes, this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        }
        if (this.settings.readOnly().getStrength() == 15) {
            this.writeIdenticalLevel(s, sink);
        }
        sink.Append(0);
    }
    
    private void writeIdenticalLevel(final CharSequence s, final CollationKeyByteSink sink) {
        final int nfdQCYesLimit = this.data.nfcImpl.decompose(s, 0, s.length(), null);
        sink.Append(1);
        sink.key_.size = sink.NumberOfBytesAppended();
        int prev = 0;
        if (nfdQCYesLimit != 0) {
            prev = BOCSU.writeIdenticalLevelRun(prev, s, 0, nfdQCYesLimit, sink.key_);
        }
        if (nfdQCYesLimit < s.length()) {
            final int destLengthEstimate = s.length() - nfdQCYesLimit;
            final StringBuilder nfd = new StringBuilder();
            this.data.nfcImpl.decompose(s, nfdQCYesLimit, s.length(), nfd, destLengthEstimate);
            BOCSU.writeIdenticalLevelRun(prev, nfd, 0, nfd.length(), sink.key_);
        }
        sink.setBufferAndAppended(sink.key_.bytes, sink.key_.size);
    }
    
    @Deprecated
    public long[] internalGetCEs(final CharSequence str) {
        CollationBuffer buffer = null;
        try {
            buffer = this.getCollationBuffer();
            final boolean numeric = this.settings.readOnly().isNumeric();
            CollationIterator iter;
            if (this.settings.readOnly().dontCheckFCD()) {
                buffer.leftUTF16CollIter.setText(numeric, str, 0);
                iter = buffer.leftUTF16CollIter;
            }
            else {
                buffer.leftFCDUTF16Iter.setText(numeric, str, 0);
                iter = buffer.leftFCDUTF16Iter;
            }
            final int length = iter.fetchCEs() - 1;
            assert length >= 0 && iter.getCE(length) == 4311744768L;
            final long[] ces = new long[length];
            System.arraycopy(iter.getCEs(), 0, ces, 0, length);
            return ces;
        }
        finally {
            this.releaseCollationBuffer(buffer);
        }
    }
    
    @Override
    public int getStrength() {
        return this.settings.readOnly().getStrength();
    }
    
    @Override
    public int getDecomposition() {
        return ((this.settings.readOnly().options & 0x1) != 0x0) ? 17 : 16;
    }
    
    public boolean isUpperCaseFirst() {
        return this.settings.readOnly().getCaseFirst() == 768;
    }
    
    public boolean isLowerCaseFirst() {
        return this.settings.readOnly().getCaseFirst() == 512;
    }
    
    public boolean isAlternateHandlingShifted() {
        return this.settings.readOnly().getAlternateHandling();
    }
    
    public boolean isCaseLevel() {
        return (this.settings.readOnly().options & 0x400) != 0x0;
    }
    
    public boolean isFrenchCollation() {
        return (this.settings.readOnly().options & 0x800) != 0x0;
    }
    
    @Deprecated
    public boolean isHiraganaQuaternary() {
        return false;
    }
    
    @Override
    public int getVariableTop() {
        return (int)this.settings.readOnly().variableTop;
    }
    
    public boolean getNumericCollation() {
        return (this.settings.readOnly().options & 0x2) != 0x0;
    }
    
    @Override
    public int[] getReorderCodes() {
        return this.settings.readOnly().reorderCodes.clone();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final RuleBasedCollator o = (RuleBasedCollator)obj;
        if (!this.settings.readOnly().equals(o.settings.readOnly())) {
            return false;
        }
        if (this.data == o.data) {
            return true;
        }
        final boolean thisIsRoot = this.data.base == null;
        final boolean otherIsRoot = o.data.base == null;
        assert !otherIsRoot;
        if (thisIsRoot != otherIsRoot) {
            return false;
        }
        final String theseRules = this.tailoring.getRules();
        final String otherRules = o.tailoring.getRules();
        if ((thisIsRoot || theseRules.length() != 0) && (otherIsRoot || otherRules.length() != 0) && theseRules.equals(otherRules)) {
            return true;
        }
        final UnicodeSet thisTailored = this.getTailoredSet();
        final UnicodeSet otherTailored = o.getTailoredSet();
        return thisTailored.equals(otherTailored);
    }
    
    @Override
    public int hashCode() {
        int h = this.settings.readOnly().hashCode();
        if (this.data.base == null) {
            return h;
        }
        final UnicodeSet set = this.getTailoredSet();
        final UnicodeSetIterator iter = new UnicodeSetIterator(set);
        while (iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING) {
            h ^= this.data.getCE32(iter.codepoint);
        }
        return h;
    }
    
    @Override
    public int compare(final String source, final String target) {
        return this.doCompare(source, target);
    }
    
    private static final int compareNFDIter(final Normalizer2Impl nfcImpl, final NFDIterator left, final NFDIterator right) {
        while (true) {
            int leftCp = left.nextCodePoint();
            int rightCp = right.nextCodePoint();
            if (leftCp == rightCp) {
                if (leftCp < 0) {
                    return 0;
                }
                continue;
            }
            else {
                if (leftCp < 0) {
                    leftCp = -2;
                }
                else if (leftCp == 65534) {
                    leftCp = -1;
                }
                else {
                    leftCp = left.nextDecomposedCodePoint(nfcImpl, leftCp);
                }
                if (rightCp < 0) {
                    rightCp = -2;
                }
                else if (rightCp == 65534) {
                    rightCp = -1;
                }
                else {
                    rightCp = right.nextDecomposedCodePoint(nfcImpl, rightCp);
                }
                if (leftCp < rightCp) {
                    return -1;
                }
                if (leftCp > rightCp) {
                    return 1;
                }
                continue;
            }
        }
    }
    
    @Deprecated
    @Override
    protected int doCompare(final CharSequence left, final CharSequence right) {
        if (left == right) {
            return 0;
        }
        int equalPrefixLength = 0;
        while (true) {
            while (equalPrefixLength != left.length()) {
                if (equalPrefixLength != right.length()) {
                    if (left.charAt(equalPrefixLength) == right.charAt(equalPrefixLength)) {
                        ++equalPrefixLength;
                        continue;
                    }
                }
                final CollationSettings roSettings = this.settings.readOnly();
                final boolean numeric = roSettings.isNumeric();
                if (equalPrefixLength > 0 && ((equalPrefixLength != left.length() && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric)) || (equalPrefixLength != right.length() && this.data.isUnsafeBackward(right.charAt(equalPrefixLength), numeric)))) {
                    while (--equalPrefixLength > 0 && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric)) {}
                }
                final int fastLatinOptions = roSettings.fastLatinOptions;
                int result;
                if (fastLatinOptions >= 0 && (equalPrefixLength == left.length() || left.charAt(equalPrefixLength) <= '\u017f') && (equalPrefixLength == right.length() || right.charAt(equalPrefixLength) <= '\u017f')) {
                    result = CollationFastLatin.compareUTF16(this.data.fastLatinTable, roSettings.fastLatinPrimaries, fastLatinOptions, left, right, equalPrefixLength);
                }
                else {
                    result = -2;
                }
                if (result == -2) {
                    CollationBuffer buffer = null;
                    try {
                        buffer = this.getCollationBuffer();
                        if (roSettings.dontCheckFCD()) {
                            buffer.leftUTF16CollIter.setText(numeric, left, equalPrefixLength);
                            buffer.rightUTF16CollIter.setText(numeric, right, equalPrefixLength);
                            result = CollationCompare.compareUpToQuaternary(buffer.leftUTF16CollIter, buffer.rightUTF16CollIter, roSettings);
                        }
                        else {
                            buffer.leftFCDUTF16Iter.setText(numeric, left, equalPrefixLength);
                            buffer.rightFCDUTF16Iter.setText(numeric, right, equalPrefixLength);
                            result = CollationCompare.compareUpToQuaternary(buffer.leftFCDUTF16Iter, buffer.rightFCDUTF16Iter, roSettings);
                        }
                    }
                    finally {
                        this.releaseCollationBuffer(buffer);
                    }
                }
                if (result != 0 || roSettings.getStrength() < 15) {
                    return result;
                }
                CollationBuffer buffer = null;
                try {
                    buffer = this.getCollationBuffer();
                    final Normalizer2Impl nfcImpl = this.data.nfcImpl;
                    if (roSettings.dontCheckFCD()) {
                        buffer.leftUTF16NFDIter.setText(left, equalPrefixLength);
                        buffer.rightUTF16NFDIter.setText(right, equalPrefixLength);
                        return compareNFDIter(nfcImpl, buffer.leftUTF16NFDIter, buffer.rightUTF16NFDIter);
                    }
                    buffer.leftFCDUTF16NFDIter.setText(nfcImpl, left, equalPrefixLength);
                    buffer.rightFCDUTF16NFDIter.setText(nfcImpl, right, equalPrefixLength);
                    return compareNFDIter(nfcImpl, buffer.leftFCDUTF16NFDIter, buffer.rightFCDUTF16NFDIter);
                }
                finally {
                    this.releaseCollationBuffer(buffer);
                }
            }
            if (equalPrefixLength == right.length()) {
                return 0;
            }
            continue;
        }
    }
    
    RuleBasedCollator(final CollationTailoring t, final ULocale vl) {
        this.data = t.data;
        this.settings = t.settings.clone();
        this.tailoring = t;
        this.validLocale = vl;
        this.actualLocaleIsSameAsValid = false;
    }
    
    private void adoptTailoring(final CollationTailoring t) {
        assert this.settings == null && this.data == null && this.tailoring == null;
        this.data = t.data;
        this.settings = t.settings.clone();
        this.tailoring = t;
        this.validLocale = t.actualLocale;
        this.actualLocaleIsSameAsValid = false;
    }
    
    final boolean isUnsafe(final int c) {
        return this.data.isUnsafeBackward(c, this.settings.readOnly().isNumeric());
    }
    
    @Override
    public VersionInfo getVersion() {
        final int version = this.tailoring.version;
        final int rtVersion = VersionInfo.UCOL_RUNTIME_VERSION.getMajor();
        return VersionInfo.getInstance((version >>> 24) + (rtVersion << 4) + (rtVersion >> 4), version >> 16 & 0xFF, version >> 8 & 0xFF, version & 0xFF);
    }
    
    @Override
    public VersionInfo getUCAVersion() {
        final VersionInfo v = this.getVersion();
        return VersionInfo.getInstance(v.getMinor() >> 3, v.getMinor() & 0x7, v.getMilli() >> 6, 0);
    }
    
    private final CollationBuffer getCollationBuffer() {
        if (this.isFrozen()) {
            this.frozenLock.lock();
        }
        else if (this.collationBuffer == null) {
            this.collationBuffer = new CollationBuffer(this.data);
        }
        return this.collationBuffer;
    }
    
    private final void releaseCollationBuffer(final CollationBuffer buffer) {
        if (this.isFrozen()) {
            this.frozenLock.unlock();
        }
    }
    
    @Override
    public ULocale getLocale(final ULocale.Type type) {
        if (type == ULocale.ACTUAL_LOCALE) {
            return this.actualLocaleIsSameAsValid ? this.validLocale : this.tailoring.actualLocale;
        }
        if (type == ULocale.VALID_LOCALE) {
            return this.validLocale;
        }
        throw new IllegalArgumentException("unknown ULocale.Type " + type);
    }
    
    @Override
    void setLocale(final ULocale valid, final ULocale actual) {
        assert valid == null == (actual == null);
        if (Utility.objectEquals(actual, this.tailoring.actualLocale)) {
            this.actualLocaleIsSameAsValid = false;
        }
        else {
            assert Utility.objectEquals(actual, valid);
            this.actualLocaleIsSameAsValid = true;
        }
        this.validLocale = valid;
    }
    
    private static final class CollationKeyByteSink extends CollationKeys.SortKeyByteSink
    {
        private RawCollationKey key_;
        
        CollationKeyByteSink(final RawCollationKey key) {
            super(key.bytes);
            this.key_ = key;
        }
        
        @Override
        protected void AppendBeyondCapacity(final byte[] bytes, final int start, final int n, final int length) {
            if (this.Resize(n, length)) {
                System.arraycopy(bytes, start, this.buffer_, length, n);
            }
        }
        
        @Override
        protected boolean Resize(final int appendCapacity, final int length) {
            int newCapacity = 2 * this.buffer_.length;
            final int altCapacity = length + 2 * appendCapacity;
            if (newCapacity < altCapacity) {
                newCapacity = altCapacity;
            }
            if (newCapacity < 200) {
                newCapacity = 200;
            }
            final byte[] newBytes = new byte[newCapacity];
            System.arraycopy(this.buffer_, 0, newBytes, 0, length);
            final RawCollationKey key_ = this.key_;
            final byte[] array = newBytes;
            key_.bytes = array;
            this.buffer_ = array;
            return true;
        }
    }
    
    private abstract static class NFDIterator
    {
        private String decomp;
        private int index;
        
        NFDIterator() {
        }
        
        final void reset() {
            this.index = -1;
        }
        
        final int nextCodePoint() {
            if (this.index >= 0) {
                if (this.index != this.decomp.length()) {
                    final int c = Character.codePointAt(this.decomp, this.index);
                    this.index += Character.charCount(c);
                    return c;
                }
                this.index = -1;
            }
            return this.nextRawCodePoint();
        }
        
        final int nextDecomposedCodePoint(final Normalizer2Impl nfcImpl, int c) {
            if (this.index >= 0) {
                return c;
            }
            this.decomp = nfcImpl.getDecomposition(c);
            if (this.decomp == null) {
                return c;
            }
            c = Character.codePointAt(this.decomp, 0);
            this.index = Character.charCount(c);
            return c;
        }
        
        protected abstract int nextRawCodePoint();
    }
    
    private static class UTF16NFDIterator extends NFDIterator
    {
        protected CharSequence s;
        protected int pos;
        
        UTF16NFDIterator() {
        }
        
        void setText(final CharSequence seq, final int start) {
            this.reset();
            this.s = seq;
            this.pos = start;
        }
        
        @Override
        protected int nextRawCodePoint() {
            if (this.pos == this.s.length()) {
                return -1;
            }
            final int c = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(c);
            return c;
        }
    }
    
    private static final class FCDUTF16NFDIterator extends UTF16NFDIterator
    {
        private StringBuilder str;
        
        FCDUTF16NFDIterator() {
        }
        
        void setText(final Normalizer2Impl nfcImpl, final CharSequence seq, final int start) {
            this.reset();
            final int spanLimit = nfcImpl.makeFCD(seq, start, seq.length(), null);
            if (spanLimit == seq.length()) {
                this.s = seq;
                this.pos = start;
            }
            else {
                if (this.str == null) {
                    this.str = new StringBuilder();
                }
                else {
                    this.str.setLength(0);
                }
                this.str.append(seq, start, spanLimit);
                final Normalizer2Impl.ReorderingBuffer buffer = new Normalizer2Impl.ReorderingBuffer(nfcImpl, this.str, seq.length() - start);
                nfcImpl.makeFCD(seq, spanLimit, seq.length(), buffer);
                this.s = this.str;
                this.pos = 0;
            }
        }
    }
    
    private static final class CollationBuffer
    {
        UTF16CollationIterator leftUTF16CollIter;
        UTF16CollationIterator rightUTF16CollIter;
        FCDUTF16CollationIterator leftFCDUTF16Iter;
        FCDUTF16CollationIterator rightFCDUTF16Iter;
        UTF16NFDIterator leftUTF16NFDIter;
        UTF16NFDIterator rightUTF16NFDIter;
        FCDUTF16NFDIterator leftFCDUTF16NFDIter;
        FCDUTF16NFDIterator rightFCDUTF16NFDIter;
        RawCollationKey rawCollationKey;
        
        private CollationBuffer(final CollationData data) {
            this.leftUTF16CollIter = new UTF16CollationIterator(data);
            this.rightUTF16CollIter = new UTF16CollationIterator(data);
            this.leftFCDUTF16Iter = new FCDUTF16CollationIterator(data);
            this.rightFCDUTF16Iter = new FCDUTF16CollationIterator(data);
            this.leftUTF16NFDIter = new UTF16NFDIterator();
            this.rightUTF16NFDIter = new UTF16NFDIterator();
            this.leftFCDUTF16NFDIter = new FCDUTF16NFDIterator();
            this.rightFCDUTF16NFDIter = new FCDUTF16NFDIterator();
        }
    }
}
