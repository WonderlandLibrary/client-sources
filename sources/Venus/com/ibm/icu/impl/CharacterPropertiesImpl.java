/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.UBiDiProps;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.impl.UCharacterProperty;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;

public final class CharacterPropertiesImpl {
    private static final int NUM_INCLUSIONS = 40;
    private static final UnicodeSet[] inclusions;
    static final boolean $assertionsDisabled;

    public static synchronized void clear() {
        for (int i = 0; i < inclusions.length; ++i) {
            CharacterPropertiesImpl.inclusions[i] = null;
        }
    }

    private static UnicodeSet getInclusionsForSource(int n) {
        if (inclusions[n] == null) {
            UnicodeSet unicodeSet = new UnicodeSet();
            switch (n) {
                case 1: {
                    UCharacterProperty.INSTANCE.addPropertyStarts(unicodeSet);
                    break;
                }
                case 2: {
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(unicodeSet);
                    break;
                }
                case 6: {
                    UCharacterProperty.INSTANCE.addPropertyStarts(unicodeSet);
                    UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(unicodeSet);
                    break;
                }
                case 7: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts(unicodeSet);
                    UCaseProps.INSTANCE.addPropertyStarts(unicodeSet);
                    break;
                }
                case 8: {
                    Norm2AllModes.getNFCInstance().impl.addPropertyStarts(unicodeSet);
                    break;
                }
                case 9: {
                    Norm2AllModes.getNFKCInstance().impl.addPropertyStarts(unicodeSet);
                    break;
                }
                case 10: {
                    Norm2AllModes.getNFKC_CFInstance().impl.addPropertyStarts(unicodeSet);
                    break;
                }
                case 11: {
                    Norm2AllModes.getNFCInstance().impl.addCanonIterPropertyStarts(unicodeSet);
                    break;
                }
                case 4: {
                    UCaseProps.INSTANCE.addPropertyStarts(unicodeSet);
                    break;
                }
                case 5: {
                    UBiDiProps.INSTANCE.addPropertyStarts(unicodeSet);
                    break;
                }
                case 12: 
                case 13: 
                case 14: {
                    UCharacterProperty.ulayout_addPropertyStarts(n, unicodeSet);
                    break;
                }
                default: {
                    throw new IllegalStateException("getInclusions(unknown src " + n + ")");
                }
            }
            CharacterPropertiesImpl.inclusions[n] = unicodeSet.compact();
        }
        return inclusions[n];
    }

    private static UnicodeSet getIntPropInclusions(int n) {
        if (!($assertionsDisabled || 4096 <= n && n < 4121)) {
            throw new AssertionError();
        }
        int n2 = 15 + n - 4096;
        if (inclusions[n2] != null) {
            return inclusions[n2];
        }
        int n3 = UCharacterProperty.INSTANCE.getSource(n);
        UnicodeSet unicodeSet = CharacterPropertiesImpl.getInclusionsForSource(n3);
        UnicodeSet unicodeSet2 = new UnicodeSet(0, 0);
        int n4 = unicodeSet.getRangeCount();
        int n5 = 0;
        for (int i = 0; i < n4; ++i) {
            int n6 = unicodeSet.getRangeEnd(i);
            for (int j = unicodeSet.getRangeStart(i); j <= n6; ++j) {
                int n7 = UCharacter.getIntPropertyValue(j, n);
                if (n7 == n5) continue;
                unicodeSet2.add(j);
                n5 = n7;
            }
        }
        CharacterPropertiesImpl.inclusions[n2] = unicodeSet2.compact();
        return CharacterPropertiesImpl.inclusions[n2];
    }

    public static synchronized UnicodeSet getInclusionsForProperty(int n) {
        if (4096 <= n && n < 4121) {
            return CharacterPropertiesImpl.getIntPropInclusions(n);
        }
        int n2 = UCharacterProperty.INSTANCE.getSource(n);
        return CharacterPropertiesImpl.getInclusionsForSource(n2);
    }

    static {
        $assertionsDisabled = !CharacterPropertiesImpl.class.desiredAssertionStatus();
        inclusions = new UnicodeSet[40];
    }
}

