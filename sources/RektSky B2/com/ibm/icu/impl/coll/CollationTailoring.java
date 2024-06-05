package com.ibm.icu.impl.coll;

import com.ibm.icu.text.*;
import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;

public final class CollationTailoring
{
    public CollationData data;
    public SharedObject.Reference<CollationSettings> settings;
    private String rules;
    private UResourceBundle rulesResource;
    public ULocale actualLocale;
    public int version;
    CollationData ownedData;
    Trie2_32 trie;
    UnicodeSet unsafeBackwardSet;
    public Map<Integer, Integer> maxExpansions;
    
    CollationTailoring(final SharedObject.Reference<CollationSettings> baseSettings) {
        this.actualLocale = ULocale.ROOT;
        this.version = 0;
        if (baseSettings != null) {
            assert baseSettings.readOnly().reorderCodes.length == 0;
            assert baseSettings.readOnly().reorderTable == null;
            assert baseSettings.readOnly().minHighNoReorder == 0L;
            this.settings = baseSettings.clone();
        }
        else {
            this.settings = new SharedObject.Reference<CollationSettings>(new CollationSettings());
        }
    }
    
    void ensureOwnedData() {
        if (this.ownedData == null) {
            final Normalizer2Impl nfcImpl = Norm2AllModes.getNFCInstance().impl;
            this.ownedData = new CollationData(nfcImpl);
        }
        this.data = this.ownedData;
    }
    
    void setRules(final String r) {
        assert this.rules == null && this.rulesResource == null;
        this.rules = r;
    }
    
    void setRulesResource(final UResourceBundle res) {
        assert this.rules == null && this.rulesResource == null;
        this.rulesResource = res;
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
    
    static VersionInfo makeBaseVersion(final VersionInfo ucaVersion) {
        return VersionInfo.getInstance(VersionInfo.UCOL_BUILDER_VERSION.getMajor(), (ucaVersion.getMajor() << 3) + ucaVersion.getMinor(), ucaVersion.getMilli() << 6, 0);
    }
    
    void setVersion(final int baseVersion, final int rulesVersion) {
        final int r = rulesVersion >> 16 & 0xFF00;
        final int s = rulesVersion >> 16 & 0xFF;
        final int t = rulesVersion >> 8 & 0xFF;
        final int q = rulesVersion & 0xFF;
        this.version = (VersionInfo.UCOL_BUILDER_VERSION.getMajor() << 24 | (baseVersion & 0xFFC000) | (r + (r >> 6) & 0x3F00) | ((s << 3) + (s >> 5) + t + (q << 4) + (q >> 4) & 0xFF));
    }
    
    int getUCAVersion() {
        return (this.version >> 12 & 0xFF0) | (this.version >> 14 & 0x3);
    }
}
