/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.Normalizer;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class CanonicalIterator {
    private static boolean PROGRESS = false;
    private static boolean SKIP_ZEROS = true;
    private final Normalizer2 nfd;
    private final Normalizer2Impl nfcImpl;
    private String source;
    private boolean done;
    private String[][] pieces;
    private int[] current;
    private transient StringBuilder buffer = new StringBuilder();
    private static final Set<String> SET_WITH_NULL_STRING = new HashSet<String>();

    public CanonicalIterator(String string) {
        Norm2AllModes norm2AllModes = Norm2AllModes.getNFCInstance();
        this.nfd = norm2AllModes.decomp;
        this.nfcImpl = norm2AllModes.impl.ensureCanonIterData();
        this.setSource(string);
    }

    public String getSource() {
        return this.source;
    }

    public void reset() {
        this.done = false;
        for (int i = 0; i < this.current.length; ++i) {
            this.current[i] = 0;
        }
    }

    public String next() {
        if (this.done) {
            return null;
        }
        this.buffer.setLength(0);
        for (int i = 0; i < this.pieces.length; ++i) {
            this.buffer.append(this.pieces[i][this.current[i]]);
        }
        String string = this.buffer.toString();
        int n = this.current.length - 1;
        while (true) {
            if (n < 0) {
                this.done = true;
                break;
            }
            int n2 = n;
            this.current[n2] = this.current[n2] + 1;
            if (this.current[n] < this.pieces[n].length) break;
            this.current[n] = 0;
            --n;
        }
        return string;
    }

    public void setSource(String string) {
        int n;
        int n2;
        this.source = this.nfd.normalize(string);
        this.done = false;
        if (string.length() == 0) {
            this.pieces = new String[1][];
            this.current = new int[1];
            this.pieces[0] = new String[]{""};
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        int n3 = 0;
        for (n = UTF16.findOffsetFromCodePoint(this.source, 1); n < this.source.length(); n += Character.charCount(n2)) {
            n2 = this.source.codePointAt(n);
            if (!this.nfcImpl.isCanonSegmentStarter(n2)) continue;
            arrayList.add(this.source.substring(n3, n));
            n3 = n;
        }
        arrayList.add(this.source.substring(n3, n));
        this.pieces = new String[arrayList.size()][];
        this.current = new int[arrayList.size()];
        for (n = 0; n < this.pieces.length; ++n) {
            if (PROGRESS) {
                System.out.println("SEGMENT");
            }
            this.pieces[n] = this.getEquivalents((String)arrayList.get(n));
        }
    }

    @Deprecated
    public static void permute(String string, boolean bl, Set<String> set) {
        int n;
        if (string.length() <= 2 && UTF16.countCodePoint(string) <= 1) {
            set.add(string);
            return;
        }
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < string.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(string, i);
            if (bl && i != 0 && UCharacter.getCombiningClass(n) == 0) continue;
            hashSet.clear();
            CanonicalIterator.permute(string.substring(0, i) + string.substring(i + UTF16.getCharCount(n)), bl, hashSet);
            String string2 = UTF16.valueOf(string, i);
            for (String string3 : hashSet) {
                String string4 = string2 + string3;
                set.add(string4);
            }
        }
    }

    private String[] getEquivalents(String string) {
        HashSet<String> hashSet = new HashSet<String>();
        Set<String> set = this.getEquivalents2(string);
        HashSet<String> hashSet2 = new HashSet<String>();
        for (String stringArray2 : set) {
            hashSet2.clear();
            CanonicalIterator.permute(stringArray2, SKIP_ZEROS, hashSet2);
            for (String string2 : hashSet2) {
                if (Normalizer.compare(string2, string, 0) == 0) {
                    if (PROGRESS) {
                        System.out.println("Adding Permutation: " + Utility.hex(string2));
                    }
                    hashSet.add(string2);
                    continue;
                }
                if (!PROGRESS) continue;
                System.out.println("-Skipping Permutation: " + Utility.hex(string2));
            }
        }
        String[] stringArray = new String[hashSet.size()];
        hashSet.toArray(stringArray);
        return stringArray;
    }

    private Set<String> getEquivalents2(String string) {
        int n;
        HashSet<String> hashSet = new HashSet<String>();
        if (PROGRESS) {
            System.out.println("Adding: " + Utility.hex(string));
        }
        hashSet.add(string);
        StringBuffer stringBuffer = new StringBuffer();
        UnicodeSet unicodeSet = new UnicodeSet();
        for (int i = 0; i < string.length(); i += Character.charCount(n)) {
            n = string.codePointAt(i);
            if (!this.nfcImpl.getCanonStartSet(n, unicodeSet)) continue;
            UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
            while (unicodeSetIterator.next()) {
                int n2 = unicodeSetIterator.codepoint;
                Set<String> set = this.extract(n2, string, i, stringBuffer);
                if (set == null) continue;
                String string2 = string.substring(0, i);
                string2 = string2 + UTF16.valueOf(n2);
                for (String string3 : set) {
                    hashSet.add(string2 + string3);
                }
            }
        }
        return hashSet;
    }

    private Set<String> extract(int n, String string, int n2, StringBuffer stringBuffer) {
        int n3;
        String string2;
        if (PROGRESS) {
            System.out.println(" extract: " + Utility.hex(UTF16.valueOf(n)) + ", " + Utility.hex(string.substring(n2)));
        }
        if ((string2 = this.nfcImpl.getDecomposition(n)) == null) {
            string2 = UTF16.valueOf(n);
        }
        boolean bl = false;
        int n4 = 0;
        int n5 = UTF16.charAt(string2, 0);
        n4 += UTF16.getCharCount(n5);
        stringBuffer.setLength(0);
        for (int i = n2; i < string.length(); i += UTF16.getCharCount(n3)) {
            n3 = UTF16.charAt(string, i);
            if (n3 == n5) {
                if (PROGRESS) {
                    System.out.println("  matches: " + Utility.hex(UTF16.valueOf(n3)));
                }
                if (n4 == string2.length()) {
                    stringBuffer.append(string.substring(i + UTF16.getCharCount(n3)));
                    bl = true;
                    break;
                }
                n5 = UTF16.charAt(string2, n4);
                n4 += UTF16.getCharCount(n5);
                continue;
            }
            if (PROGRESS) {
                System.out.println("  buffer: " + Utility.hex(UTF16.valueOf(n3)));
            }
            UTF16.append(stringBuffer, n3);
        }
        if (!bl) {
            return null;
        }
        if (PROGRESS) {
            System.out.println("Matches");
        }
        if (stringBuffer.length() == 0) {
            return SET_WITH_NULL_STRING;
        }
        String string3 = stringBuffer.toString();
        if (0 != Normalizer.compare(UTF16.valueOf(n) + string3, string.substring(n2), 0)) {
            return null;
        }
        return this.getEquivalents2(string3);
    }

    static {
        SET_WITH_NULL_STRING.add("");
    }
}

