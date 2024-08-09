/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.SharedObject;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.VersionInfo;
import java.util.Map;

public final class CollationTailoring {
    public CollationData data;
    public SharedObject.Reference<CollationSettings> settings;
    private String rules;
    private UResourceBundle rulesResource;
    public ULocale actualLocale = ULocale.ROOT;
    public int version = 0;
    CollationData ownedData;
    Trie2_32 trie;
    UnicodeSet unsafeBackwardSet;
    public Map<Integer, Integer> maxExpansions;
    static final boolean $assertionsDisabled = !CollationTailoring.class.desiredAssertionStatus();

    CollationTailoring(SharedObject.Reference<CollationSettings> reference) {
        if (reference != null) {
            if (!$assertionsDisabled && reference.readOnly().reorderCodes.length != 0) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && reference.readOnly().reorderTable != null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && reference.readOnly().minHighNoReorder != 0L) {
                throw new AssertionError();
            }
            this.settings = reference.clone();
        } else {
            this.settings = new SharedObject.Reference<CollationSettings>(new CollationSettings());
        }
    }

    void ensureOwnedData() {
        if (this.ownedData == null) {
            Normalizer2Impl normalizer2Impl = Norm2AllModes.getNFCInstance().impl;
            this.ownedData = new CollationData(normalizer2Impl);
        }
        this.data = this.ownedData;
    }

    void setRules(String string) {
        if (!($assertionsDisabled || this.rules == null && this.rulesResource == null)) {
            throw new AssertionError();
        }
        this.rules = string;
    }

    void setRulesResource(UResourceBundle uResourceBundle) {
        if (!($assertionsDisabled || this.rules == null && this.rulesResource == null)) {
            throw new AssertionError();
        }
        this.rulesResource = uResourceBundle;
    }

    public String getRules() {
        if (this.rules != null) {
            return this.rules;
        }
        if (this.rulesResource != null) {
            return this.rulesResource.getString();
        }
        return "";
    }

    static VersionInfo makeBaseVersion(VersionInfo versionInfo) {
        return VersionInfo.getInstance(VersionInfo.UCOL_BUILDER_VERSION.getMajor(), (versionInfo.getMajor() << 3) + versionInfo.getMinor(), versionInfo.getMilli() << 6, 0);
    }

    void setVersion(int n, int n2) {
        int n3 = n2 >> 16 & 0xFF00;
        int n4 = n2 >> 16 & 0xFF;
        int n5 = n2 >> 8 & 0xFF;
        int n6 = n2 & 0xFF;
        this.version = VersionInfo.UCOL_BUILDER_VERSION.getMajor() << 24 | n & 0xFFC000 | n3 + (n3 >> 6) & 0x3F00 | (n4 << 3) + (n4 >> 5) + n5 + (n6 << 4) + (n6 >> 4) & 0xFF;
    }

    int getUCAVersion() {
        return this.version >> 12 & 0xFF0 | this.version >> 14 & 3;
    }
}

