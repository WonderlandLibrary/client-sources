/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ClassLoaderUtil;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.coll.BOCSU;
import com.ibm.icu.impl.coll.CollationCompare;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFastLatin;
import com.ibm.icu.impl.coll.CollationKeys;
import com.ibm.icu.impl.coll.CollationLoader;
import com.ibm.icu.impl.coll.CollationRoot;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.CollationTailoring;
import com.ibm.icu.impl.coll.ContractionsAndExpansions;
import com.ibm.icu.impl.coll.FCDUTF16CollationIterator;
import com.ibm.icu.impl.coll.SharedObject;
import com.ibm.icu.impl.coll.TailoredSet;
import com.ibm.icu.impl.coll.UTF16CollationIterator;
import com.ibm.icu.text.CollationElementIterator;
import com.ibm.icu.text.CollationKey;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.RawCollationKey;
import com.ibm.icu.text.UCharacterIterator;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.VersionInfo;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class RuleBasedCollator
extends Collator {
    private Lock frozenLock;
    private CollationBuffer collationBuffer;
    CollationData data;
    SharedObject.Reference<CollationSettings> settings;
    CollationTailoring tailoring;
    private ULocale validLocale;
    private boolean actualLocaleIsSameAsValid;
    static final boolean $assertionsDisabled = !RuleBasedCollator.class.desiredAssertionStatus();

    public RuleBasedCollator(String string) throws Exception {
        if (string == null) {
            throw new IllegalArgumentException("Collation rules can not be null");
        }
        this.validLocale = ULocale.ROOT;
        this.internalBuildTailoring(string);
    }

    private final void internalBuildTailoring(String string) throws Exception {
        CollationTailoring collationTailoring;
        CollationTailoring collationTailoring2 = CollationRoot.getRoot();
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader(this.getClass());
        try {
            Class<?> clazz = classLoader.loadClass("com.ibm.icu.impl.coll.CollationBuilder");
            Object obj = clazz.getConstructor(CollationTailoring.class).newInstance(collationTailoring2);
            Method method = clazz.getMethod("parseAndBuild", String.class);
            collationTailoring = (CollationTailoring)method.invoke(obj, string);
        } catch (InvocationTargetException invocationTargetException) {
            throw (Exception)invocationTargetException.getTargetException();
        }
        collationTailoring.actualLocale = null;
        this.adoptTailoring(collationTailoring);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this.isFrozen()) {
            return this;
        }
        return this.cloneAsThawed();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final void initMaxExpansions() {
        CollationTailoring collationTailoring = this.tailoring;
        synchronized (collationTailoring) {
            if (this.tailoring.maxExpansions == null) {
                this.tailoring.maxExpansions = CollationElementIterator.computeMaxExpansions(this.tailoring.data);
            }
        }
    }

    public CollationElementIterator getCollationElementIterator(String string) {
        this.initMaxExpansions();
        return new CollationElementIterator(string, this);
    }

    public CollationElementIterator getCollationElementIterator(CharacterIterator characterIterator) {
        this.initMaxExpansions();
        CharacterIterator characterIterator2 = (CharacterIterator)characterIterator.clone();
        return new CollationElementIterator(characterIterator2, this);
    }

    public CollationElementIterator getCollationElementIterator(UCharacterIterator uCharacterIterator) {
        this.initMaxExpansions();
        return new CollationElementIterator(uCharacterIterator, this);
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
                this.collationBuffer = new CollationBuffer(this.data, null);
            }
        }
        return this;
    }

    @Override
    public RuleBasedCollator cloneAsThawed() {
        try {
            RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)super.clone();
            ruleBasedCollator.settings = this.settings.clone();
            ruleBasedCollator.collationBuffer = null;
            ruleBasedCollator.frozenLock = null;
            return ruleBasedCollator;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
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
    public void setHiraganaQuaternary(boolean bl) {
        this.checkNotFrozen();
    }

    @Deprecated
    public void setHiraganaQuaternaryDefault() {
        this.checkNotFrozen();
    }

    public void setUpperCaseFirst(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isUpperCaseFirst()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setCaseFirst(bl ? 768 : 0);
        this.setFastLatinOptions(collationSettings);
    }

    public void setLowerCaseFirst(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isLowerCaseFirst()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setCaseFirst(bl ? 512 : 0);
        this.setFastLatinOptions(collationSettings);
    }

    public final void setCaseFirstDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setCaseFirstDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setAlternateHandlingDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setAlternateHandlingDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setCaseLevelDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(1024, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setDecompositionDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(1, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setFrenchCollationDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(2048, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setStrengthDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setStrengthDefault(collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setNumericCollationDefault() {
        this.checkNotFrozen();
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings) {
            return;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        collationSettings2.setFlagDefault(2, collationSettings.options);
        this.setFastLatinOptions(collationSettings2);
    }

    public void setFrenchCollation(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isFrenchCollation()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(2048, bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setAlternateHandlingShifted(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isAlternateHandlingShifted()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setAlternateHandlingShifted(bl);
        this.setFastLatinOptions(collationSettings);
    }

    public void setCaseLevel(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.isCaseLevel()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(1024, bl);
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public void setDecomposition(int n) {
        boolean bl;
        this.checkNotFrozen();
        switch (n) {
            case 16: {
                bl = false;
                break;
            }
            case 17: {
                bl = true;
                break;
            }
            default: {
                throw new IllegalArgumentException("Wrong decomposition mode.");
            }
        }
        if (bl == this.settings.readOnly().getFlag(0)) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(1, bl);
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public void setStrength(int n) {
        this.checkNotFrozen();
        if (n == this.getStrength()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setStrength(n);
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public RuleBasedCollator setMaxVariable(int n) {
        int n2;
        if (n == -1) {
            n2 = -1;
        } else if (4096 <= n && n <= 4099) {
            n2 = n - 4096;
        } else {
            throw new IllegalArgumentException("illegal max variable group " + n);
        }
        int n3 = this.settings.readOnly().getMaxVariable();
        if (n2 == n3) {
            return this;
        }
        CollationSettings collationSettings = this.getDefaultSettings();
        if (this.settings.readOnly() == collationSettings && n2 < 0) {
            return this;
        }
        CollationSettings collationSettings2 = this.getOwnedSettings();
        if (n == -1) {
            n = 4096 + collationSettings.getMaxVariable();
        }
        long l = this.data.getLastPrimaryForGroup(n);
        if (!$assertionsDisabled && l == 0L) {
            throw new AssertionError();
        }
        collationSettings2.setMaxVariable(n2, collationSettings.options);
        collationSettings2.variableTop = l;
        this.setFastLatinOptions(collationSettings2);
        return this;
    }

    @Override
    public int getMaxVariable() {
        return 4096 + this.settings.readOnly().getMaxVariable();
    }

    @Override
    @Deprecated
    public int setVariableTop(String string) {
        long l;
        long l2;
        this.checkNotFrozen();
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Variable top argument string can not be null or zero in length.");
        }
        boolean bl = this.settings.readOnly().isNumeric();
        if (this.settings.readOnly().dontCheckFCD()) {
            UTF16CollationIterator uTF16CollationIterator = new UTF16CollationIterator(this.data, bl, string, 0);
            l2 = uTF16CollationIterator.nextCE();
            l = uTF16CollationIterator.nextCE();
        } else {
            FCDUTF16CollationIterator fCDUTF16CollationIterator = new FCDUTF16CollationIterator(this.data, bl, string, 0);
            l2 = fCDUTF16CollationIterator.nextCE();
            l = fCDUTF16CollationIterator.nextCE();
        }
        if (l2 == 0x101000100L || l != 0x101000100L) {
            throw new IllegalArgumentException("Variable top argument string must map to exactly one collation element");
        }
        this.internalSetVariableTop(l2 >>> 32);
        return (int)this.settings.readOnly().variableTop;
    }

    @Override
    @Deprecated
    public void setVariableTop(int n) {
        this.checkNotFrozen();
        this.internalSetVariableTop((long)n & 0xFFFFFFFFL);
    }

    private void internalSetVariableTop(long l) {
        if (l != this.settings.readOnly().variableTop) {
            int n = this.data.getGroupForPrimary(l);
            if (n < 4096 || 4099 < n) {
                throw new IllegalArgumentException("The variable top must be a primary weight in the space/punctuation/symbols/currency symbols range");
            }
            long l2 = this.data.getLastPrimaryForGroup(n);
            if (!($assertionsDisabled || l2 != 0L && l2 >= l)) {
                throw new AssertionError();
            }
            l = l2;
            if (l != this.settings.readOnly().variableTop) {
                CollationSettings collationSettings = this.getOwnedSettings();
                collationSettings.setMaxVariable(n - 4096, this.getDefaultSettings().options);
                collationSettings.variableTop = l;
                this.setFastLatinOptions(collationSettings);
            }
        }
    }

    public void setNumericCollation(boolean bl) {
        this.checkNotFrozen();
        if (bl == this.getNumericCollation()) {
            return;
        }
        CollationSettings collationSettings = this.getOwnedSettings();
        collationSettings.setFlag(2, bl);
        this.setFastLatinOptions(collationSettings);
    }

    @Override
    public void setReorderCodes(int ... nArray) {
        int n;
        this.checkNotFrozen();
        int n2 = n = nArray != null ? nArray.length : 0;
        if (n == 1 && nArray[0] == 103) {
            n = 0;
        }
        if (n == 0 ? this.settings.readOnly().reorderCodes.length == 0 : Arrays.equals(nArray, this.settings.readOnly().reorderCodes)) {
            return;
        }
        CollationSettings collationSettings = this.getDefaultSettings();
        if (n == 1 && nArray[0] == -1) {
            if (this.settings.readOnly() != collationSettings) {
                CollationSettings collationSettings2 = this.getOwnedSettings();
                collationSettings2.copyReorderingFrom(collationSettings);
                this.setFastLatinOptions(collationSettings2);
            }
            return;
        }
        CollationSettings collationSettings3 = this.getOwnedSettings();
        if (n == 0) {
            collationSettings3.resetReordering();
        } else {
            collationSettings3.setReordering(this.data, (int[])nArray.clone());
        }
        this.setFastLatinOptions(collationSettings3);
    }

    private void setFastLatinOptions(CollationSettings collationSettings) {
        collationSettings.fastLatinOptions = CollationFastLatin.getOptions(this.data, collationSettings, collationSettings.fastLatinPrimaries);
    }

    public String getRules() {
        return this.tailoring.getRules();
    }

    public String getRules(boolean bl) {
        if (!bl) {
            return this.tailoring.getRules();
        }
        return CollationLoader.getRootRules() + this.tailoring.getRules();
    }

    @Override
    public UnicodeSet getTailoredSet() {
        UnicodeSet unicodeSet = new UnicodeSet();
        if (this.data.base != null) {
            new TailoredSet(unicodeSet).forData(this.data);
        }
        return unicodeSet;
    }

    public void getContractionsAndExpansions(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, boolean bl) throws Exception {
        if (unicodeSet != null) {
            unicodeSet.clear();
        }
        if (unicodeSet2 != null) {
            unicodeSet2.clear();
        }
        new ContractionsAndExpansions(unicodeSet, unicodeSet2, null, bl).forData(this.data);
    }

    @Deprecated
    void internalAddContractions(int n, UnicodeSet unicodeSet) {
        new ContractionsAndExpansions(unicodeSet, null, null, false).forCodePoint(this.data, n);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CollationKey getCollationKey(String string) {
        if (string == null) {
            return null;
        }
        CollationBuffer collationBuffer = null;
        try {
            collationBuffer = this.getCollationBuffer();
            CollationKey collationKey = this.getCollationKey(string, collationBuffer);
            return collationKey;
        } finally {
            this.releaseCollationBuffer(collationBuffer);
        }
    }

    private CollationKey getCollationKey(String string, CollationBuffer collationBuffer) {
        collationBuffer.rawCollationKey = this.getRawCollationKey(string, collationBuffer.rawCollationKey, collationBuffer);
        return new CollationKey(string, collationBuffer.rawCollationKey);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public RawCollationKey getRawCollationKey(String string, RawCollationKey rawCollationKey) {
        if (string == null) {
            return null;
        }
        CollationBuffer collationBuffer = null;
        try {
            collationBuffer = this.getCollationBuffer();
            RawCollationKey rawCollationKey2 = this.getRawCollationKey(string, rawCollationKey, collationBuffer);
            return rawCollationKey2;
        } finally {
            this.releaseCollationBuffer(collationBuffer);
        }
    }

    private RawCollationKey getRawCollationKey(CharSequence charSequence, RawCollationKey rawCollationKey, CollationBuffer collationBuffer) {
        if (rawCollationKey == null) {
            rawCollationKey = new RawCollationKey(this.simpleKeyLengthEstimate(charSequence));
        } else if (rawCollationKey.bytes == null) {
            rawCollationKey.bytes = new byte[this.simpleKeyLengthEstimate(charSequence)];
        }
        CollationKeyByteSink collationKeyByteSink = new CollationKeyByteSink(rawCollationKey);
        this.writeSortKey(charSequence, collationKeyByteSink, collationBuffer);
        rawCollationKey.size = collationKeyByteSink.NumberOfBytesAppended();
        return rawCollationKey;
    }

    private int simpleKeyLengthEstimate(CharSequence charSequence) {
        return 2 * charSequence.length() + 10;
    }

    private void writeSortKey(CharSequence charSequence, CollationKeyByteSink collationKeyByteSink, CollationBuffer collationBuffer) {
        boolean bl = this.settings.readOnly().isNumeric();
        if (this.settings.readOnly().dontCheckFCD()) {
            collationBuffer.leftUTF16CollIter.setText(bl, charSequence, 1);
            CollationKeys.writeSortKeyUpToQuaternary(collationBuffer.leftUTF16CollIter, this.data.compressibleBytes, this.settings.readOnly(), collationKeyByteSink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        } else {
            collationBuffer.leftFCDUTF16Iter.setText(bl, charSequence, 1);
            CollationKeys.writeSortKeyUpToQuaternary(collationBuffer.leftFCDUTF16Iter, this.data.compressibleBytes, this.settings.readOnly(), collationKeyByteSink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
        }
        if (this.settings.readOnly().getStrength() == 15) {
            this.writeIdenticalLevel(charSequence, collationKeyByteSink);
        }
        collationKeyByteSink.Append(0);
    }

    private void writeIdenticalLevel(CharSequence charSequence, CollationKeyByteSink collationKeyByteSink) {
        int n = this.data.nfcImpl.decompose(charSequence, 0, charSequence.length(), null);
        collationKeyByteSink.Append(1);
        CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).size = collationKeyByteSink.NumberOfBytesAppended();
        int n2 = 0;
        if (n != 0) {
            n2 = BOCSU.writeIdenticalLevelRun(n2, charSequence, 0, n, CollationKeyByteSink.access$100(collationKeyByteSink));
        }
        if (n < charSequence.length()) {
            int n3 = charSequence.length() - n;
            StringBuilder stringBuilder = new StringBuilder();
            this.data.nfcImpl.decompose(charSequence, n, charSequence.length(), stringBuilder, n3);
            BOCSU.writeIdenticalLevelRun(n2, stringBuilder, 0, stringBuilder.length(), CollationKeyByteSink.access$100(collationKeyByteSink));
        }
        collationKeyByteSink.setBufferAndAppended(CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).bytes, CollationKeyByteSink.access$100((CollationKeyByteSink)collationKeyByteSink).size);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Deprecated
    public long[] internalGetCEs(CharSequence charSequence) {
        CollationBuffer collationBuffer = null;
        try {
            UTF16CollationIterator uTF16CollationIterator;
            collationBuffer = this.getCollationBuffer();
            boolean bl = this.settings.readOnly().isNumeric();
            if (this.settings.readOnly().dontCheckFCD()) {
                collationBuffer.leftUTF16CollIter.setText(bl, charSequence, 1);
                uTF16CollationIterator = collationBuffer.leftUTF16CollIter;
            } else {
                collationBuffer.leftFCDUTF16Iter.setText(bl, charSequence, 1);
                uTF16CollationIterator = collationBuffer.leftFCDUTF16Iter;
            }
            int n = uTF16CollationIterator.fetchCEs() - 1;
            if (!($assertionsDisabled || n >= 0 && uTF16CollationIterator.getCE(n) == 0x101000100L)) {
                throw new AssertionError();
            }
            long[] lArray = new long[n];
            System.arraycopy(uTF16CollationIterator.getCEs(), 0, lArray, 0, n);
            long[] lArray2 = lArray;
            return lArray2;
        } finally {
            this.releaseCollationBuffer(collationBuffer);
        }
    }

    @Override
    public int getStrength() {
        return this.settings.readOnly().getStrength();
    }

    @Override
    public int getDecomposition() {
        return (this.settings.readOnly().options & 1) != 0 ? 17 : 16;
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
        return (this.settings.readOnly().options & 0x400) != 0;
    }

    public boolean isFrenchCollation() {
        return (this.settings.readOnly().options & 0x800) != 0;
    }

    @Deprecated
    public boolean isHiraganaQuaternary() {
        return true;
    }

    @Override
    public int getVariableTop() {
        return (int)this.settings.readOnly().variableTop;
    }

    public boolean getNumericCollation() {
        return (this.settings.readOnly().options & 2) != 0;
    }

    @Override
    public int[] getReorderCodes() {
        return (int[])this.settings.readOnly().reorderCodes.clone();
    }

    @Override
    public boolean equals(Object object) {
        UnicodeSet unicodeSet;
        boolean bl;
        if (this == object) {
            return false;
        }
        if (!super.equals(object)) {
            return true;
        }
        RuleBasedCollator ruleBasedCollator = (RuleBasedCollator)object;
        if (!this.settings.readOnly().equals(ruleBasedCollator.settings.readOnly())) {
            return true;
        }
        if (this.data == ruleBasedCollator.data) {
            return false;
        }
        boolean bl2 = this.data.base == null;
        boolean bl3 = bl = ruleBasedCollator.data.base == null;
        if (!$assertionsDisabled && bl2 && bl) {
            throw new AssertionError();
        }
        if (bl2 != bl) {
            return true;
        }
        String string = this.tailoring.getRules();
        String string2 = ruleBasedCollator.tailoring.getRules();
        if ((bl2 || string.length() != 0) && (bl || string2.length() != 0) && string.equals(string2)) {
            return false;
        }
        UnicodeSet unicodeSet2 = this.getTailoredSet();
        return !unicodeSet2.equals(unicodeSet = ruleBasedCollator.getTailoredSet());
    }

    @Override
    public int hashCode() {
        int n = this.settings.readOnly().hashCode();
        if (this.data.base == null) {
            return n;
        }
        UnicodeSet unicodeSet = this.getTailoredSet();
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            n ^= this.data.getCE32(unicodeSetIterator.codepoint);
        }
        return n;
    }

    @Override
    public int compare(String string, String string2) {
        return this.doCompare(string, string2);
    }

    private static final int compareNFDIter(Normalizer2Impl normalizer2Impl, NFDIterator nFDIterator, NFDIterator nFDIterator2) {
        block3: {
            while (true) {
                int n;
                int n2;
                if ((n2 = nFDIterator.nextCodePoint()) == (n = nFDIterator2.nextCodePoint())) {
                    if (n2 >= 0) continue;
                    break block3;
                }
                if ((n2 = n2 < 0 ? -2 : (n2 == 65534 ? -1 : nFDIterator.nextDecomposedCodePoint(normalizer2Impl, n2))) < (n = n < 0 ? -2 : (n == 65534 ? -1 : nFDIterator2.nextDecomposedCodePoint(normalizer2Impl, n)))) {
                    return 1;
                }
                if (n2 > n) break;
            }
            return 0;
        }
        return 1;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Deprecated
    protected int doCompare(CharSequence charSequence, CharSequence charSequence2) {
        CollationBuffer collationBuffer;
        int n;
        int n2;
        if (charSequence == charSequence2) {
            return 1;
        }
        int n3 = 0;
        while (true) {
            if (n3 == charSequence.length()) {
                if (n3 != charSequence2.length()) break;
                return 1;
            }
            if (n3 == charSequence2.length() || charSequence.charAt(n3) != charSequence2.charAt(n3)) break;
            ++n3;
        }
        CollationSettings collationSettings = this.settings.readOnly();
        boolean bl = collationSettings.isNumeric();
        if (n3 > 0 && (n3 != charSequence.length() && this.data.isUnsafeBackward(charSequence.charAt(n3), bl) || n3 != charSequence2.length() && this.data.isUnsafeBackward(charSequence2.charAt(n3), bl))) {
            while (--n3 > 0 && this.data.isUnsafeBackward(charSequence.charAt(n3), bl)) {
            }
        }
        if ((n2 = !((n = collationSettings.fastLatinOptions) < 0 || n3 != charSequence.length() && charSequence.charAt(n3) > '\u017f' || n3 != charSequence2.length() && charSequence2.charAt(n3) > '\u017f') ? CollationFastLatin.compareUTF16(this.data.fastLatinTable, collationSettings.fastLatinPrimaries, n, charSequence, charSequence2, n3) : -2) == -2) {
            collationBuffer = null;
            try {
                collationBuffer = this.getCollationBuffer();
                if (collationSettings.dontCheckFCD()) {
                    collationBuffer.leftUTF16CollIter.setText(bl, charSequence, n3);
                    collationBuffer.rightUTF16CollIter.setText(bl, charSequence2, n3);
                    n2 = CollationCompare.compareUpToQuaternary(collationBuffer.leftUTF16CollIter, collationBuffer.rightUTF16CollIter, collationSettings);
                } else {
                    collationBuffer.leftFCDUTF16Iter.setText(bl, charSequence, n3);
                    collationBuffer.rightFCDUTF16Iter.setText(bl, charSequence2, n3);
                    n2 = CollationCompare.compareUpToQuaternary(collationBuffer.leftFCDUTF16Iter, collationBuffer.rightFCDUTF16Iter, collationSettings);
                }
            } finally {
                this.releaseCollationBuffer(collationBuffer);
            }
        }
        if (n2 != 0 || collationSettings.getStrength() < 15) {
            return n2;
        }
        collationBuffer = null;
        try {
            collationBuffer = this.getCollationBuffer();
            Normalizer2Impl normalizer2Impl = this.data.nfcImpl;
            if (collationSettings.dontCheckFCD()) {
                collationBuffer.leftUTF16NFDIter.setText(charSequence, n3);
                collationBuffer.rightUTF16NFDIter.setText(charSequence2, n3);
                int n4 = RuleBasedCollator.compareNFDIter(normalizer2Impl, collationBuffer.leftUTF16NFDIter, collationBuffer.rightUTF16NFDIter);
                return n4;
            }
            collationBuffer.leftFCDUTF16NFDIter.setText(normalizer2Impl, charSequence, n3);
            collationBuffer.rightFCDUTF16NFDIter.setText(normalizer2Impl, charSequence2, n3);
            int n5 = RuleBasedCollator.compareNFDIter(normalizer2Impl, collationBuffer.leftFCDUTF16NFDIter, collationBuffer.rightFCDUTF16NFDIter);
            return n5;
        } finally {
            this.releaseCollationBuffer(collationBuffer);
        }
    }

    RuleBasedCollator(CollationTailoring collationTailoring, ULocale uLocale) {
        this.data = collationTailoring.data;
        this.settings = collationTailoring.settings.clone();
        this.tailoring = collationTailoring;
        this.validLocale = uLocale;
        this.actualLocaleIsSameAsValid = false;
    }

    private void adoptTailoring(CollationTailoring collationTailoring) {
        if (!($assertionsDisabled || this.settings == null && this.data == null && this.tailoring == null)) {
            throw new AssertionError();
        }
        this.data = collationTailoring.data;
        this.settings = collationTailoring.settings.clone();
        this.tailoring = collationTailoring;
        this.validLocale = collationTailoring.actualLocale;
        this.actualLocaleIsSameAsValid = false;
    }

    final boolean isUnsafe(int n) {
        return this.data.isUnsafeBackward(n, this.settings.readOnly().isNumeric());
    }

    @Override
    public VersionInfo getVersion() {
        int n = this.tailoring.version;
        int n2 = VersionInfo.UCOL_RUNTIME_VERSION.getMajor();
        return VersionInfo.getInstance((n >>> 24) + (n2 << 4) + (n2 >> 4), n >> 16 & 0xFF, n >> 8 & 0xFF, n & 0xFF);
    }

    @Override
    public VersionInfo getUCAVersion() {
        VersionInfo versionInfo = this.getVersion();
        return VersionInfo.getInstance(versionInfo.getMinor() >> 3, versionInfo.getMinor() & 7, versionInfo.getMilli() >> 6, 0);
    }

    private final CollationBuffer getCollationBuffer() {
        if (this.isFrozen()) {
            this.frozenLock.lock();
        } else if (this.collationBuffer == null) {
            this.collationBuffer = new CollationBuffer(this.data, null);
        }
        return this.collationBuffer;
    }

    private final void releaseCollationBuffer(CollationBuffer collationBuffer) {
        if (this.isFrozen()) {
            this.frozenLock.unlock();
        }
    }

    @Override
    public ULocale getLocale(ULocale.Type type) {
        if (type == ULocale.ACTUAL_LOCALE) {
            return this.actualLocaleIsSameAsValid ? this.validLocale : this.tailoring.actualLocale;
        }
        if (type == ULocale.VALID_LOCALE) {
            return this.validLocale;
        }
        throw new IllegalArgumentException("unknown ULocale.Type " + type);
    }

    @Override
    void setLocale(ULocale uLocale, ULocale uLocale2) {
        if (!$assertionsDisabled && uLocale == null != (uLocale2 == null)) {
            throw new AssertionError();
        }
        if (Objects.equals(uLocale2, this.tailoring.actualLocale)) {
            this.actualLocaleIsSameAsValid = false;
        } else {
            if (!$assertionsDisabled && !Objects.equals(uLocale2, uLocale)) {
                throw new AssertionError();
            }
            this.actualLocaleIsSameAsValid = true;
        }
        this.validLocale = uLocale;
    }

    @Override
    public Collator cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Collator setMaxVariable(int n) {
        return this.setMaxVariable(n);
    }

    @Override
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    public Object freeze() {
        return this.freeze();
    }

    private static final class CollationBuffer {
        UTF16CollationIterator leftUTF16CollIter;
        UTF16CollationIterator rightUTF16CollIter;
        FCDUTF16CollationIterator leftFCDUTF16Iter;
        FCDUTF16CollationIterator rightFCDUTF16Iter;
        UTF16NFDIterator leftUTF16NFDIter;
        UTF16NFDIterator rightUTF16NFDIter;
        FCDUTF16NFDIterator leftFCDUTF16NFDIter;
        FCDUTF16NFDIterator rightFCDUTF16NFDIter;
        RawCollationKey rawCollationKey;

        private CollationBuffer(CollationData collationData) {
            this.leftUTF16CollIter = new UTF16CollationIterator(collationData);
            this.rightUTF16CollIter = new UTF16CollationIterator(collationData);
            this.leftFCDUTF16Iter = new FCDUTF16CollationIterator(collationData);
            this.rightFCDUTF16Iter = new FCDUTF16CollationIterator(collationData);
            this.leftUTF16NFDIter = new UTF16NFDIterator();
            this.rightUTF16NFDIter = new UTF16NFDIterator();
            this.leftFCDUTF16NFDIter = new FCDUTF16NFDIterator();
            this.rightFCDUTF16NFDIter = new FCDUTF16NFDIterator();
        }

        CollationBuffer(CollationData collationData, 1 var2_2) {
            this(collationData);
        }
    }

    private static final class FCDUTF16NFDIterator
    extends UTF16NFDIterator {
        private StringBuilder str;

        FCDUTF16NFDIterator() {
        }

        void setText(Normalizer2Impl normalizer2Impl, CharSequence charSequence, int n) {
            this.reset();
            int n2 = normalizer2Impl.makeFCD(charSequence, n, charSequence.length(), null);
            if (n2 == charSequence.length()) {
                this.s = charSequence;
                this.pos = n;
            } else {
                if (this.str == null) {
                    this.str = new StringBuilder();
                } else {
                    this.str.setLength(0);
                }
                this.str.append(charSequence, n, n2);
                Normalizer2Impl.ReorderingBuffer reorderingBuffer = new Normalizer2Impl.ReorderingBuffer(normalizer2Impl, this.str, charSequence.length() - n);
                normalizer2Impl.makeFCD(charSequence, n2, charSequence.length(), reorderingBuffer);
                this.s = this.str;
                this.pos = 0;
            }
        }
    }

    private static class UTF16NFDIterator
    extends NFDIterator {
        protected CharSequence s;
        protected int pos;

        UTF16NFDIterator() {
        }

        void setText(CharSequence charSequence, int n) {
            this.reset();
            this.s = charSequence;
            this.pos = n;
        }

        @Override
        protected int nextRawCodePoint() {
            if (this.pos == this.s.length()) {
                return 1;
            }
            int n = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(n);
            return n;
        }
    }

    private static abstract class NFDIterator {
        private String decomp;
        private int index;

        NFDIterator() {
        }

        final void reset() {
            this.index = -1;
        }

        final int nextCodePoint() {
            if (this.index >= 0) {
                if (this.index == this.decomp.length()) {
                    this.index = -1;
                } else {
                    int n = Character.codePointAt(this.decomp, this.index);
                    this.index += Character.charCount(n);
                    return n;
                }
            }
            return this.nextRawCodePoint();
        }

        final int nextDecomposedCodePoint(Normalizer2Impl normalizer2Impl, int n) {
            if (this.index >= 0) {
                return n;
            }
            this.decomp = normalizer2Impl.getDecomposition(n);
            if (this.decomp == null) {
                return n;
            }
            n = Character.codePointAt(this.decomp, 0);
            this.index = Character.charCount(n);
            return n;
        }

        protected abstract int nextRawCodePoint();
    }

    private static final class CollationKeyByteSink
    extends CollationKeys.SortKeyByteSink {
        private RawCollationKey key_;

        CollationKeyByteSink(RawCollationKey rawCollationKey) {
            super(rawCollationKey.bytes);
            this.key_ = rawCollationKey;
        }

        @Override
        protected void AppendBeyondCapacity(byte[] byArray, int n, int n2, int n3) {
            if (this.Resize(n2, n3)) {
                System.arraycopy(byArray, n, this.buffer_, n3, n2);
            }
        }

        @Override
        protected boolean Resize(int n, int n2) {
            int n3 = 2 * this.buffer_.length;
            int n4 = n2 + 2 * n;
            if (n3 < n4) {
                n3 = n4;
            }
            if (n3 < 200) {
                n3 = 200;
            }
            byte[] byArray = new byte[n3];
            System.arraycopy(this.buffer_, 0, byArray, 0, n2);
            this.key_.bytes = byArray;
            this.buffer_ = byArray;
            return false;
        }

        static RawCollationKey access$100(CollationKeyByteSink collationKeyByteSink) {
            return collationKeyByteSink.key_;
        }
    }
}

