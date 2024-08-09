/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.RBBINode;
import com.ibm.icu.text.RBBIRuleScanner;
import com.ibm.icu.text.SymbolTable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeMatcher;
import com.ibm.icu.text.UnicodeSet;
import java.text.ParsePosition;
import java.util.HashMap;

class RBBISymbolTable
implements SymbolTable {
    HashMap<String, RBBISymbolTableEntry> fHashTable;
    RBBIRuleScanner fRuleScanner;
    String ffffString;
    UnicodeSet fCachedSetLookup;

    RBBISymbolTable(RBBIRuleScanner rBBIRuleScanner) {
        this.fRuleScanner = rBBIRuleScanner;
        this.fHashTable = new HashMap();
        this.ffffString = "\uffff";
    }

    @Override
    public char[] lookup(String string) {
        String string2;
        RBBISymbolTableEntry rBBISymbolTableEntry = this.fHashTable.get(string);
        if (rBBISymbolTableEntry == null) {
            return null;
        }
        RBBINode rBBINode = rBBISymbolTableEntry.val;
        while (rBBINode.fLeftChild.fType == 2) {
            rBBINode = rBBINode.fLeftChild;
        }
        RBBINode rBBINode2 = rBBINode.fLeftChild;
        if (rBBINode2.fType == 0) {
            RBBINode rBBINode3 = rBBINode2.fLeftChild;
            this.fCachedSetLookup = rBBINode3.fInputSet;
            string2 = this.ffffString;
        } else {
            this.fRuleScanner.error(66063);
            string2 = rBBINode2.fText;
            this.fCachedSetLookup = null;
        }
        return string2.toCharArray();
    }

    @Override
    public UnicodeMatcher lookupMatcher(int n) {
        UnicodeSet unicodeSet = null;
        if (n == 65535) {
            unicodeSet = this.fCachedSetLookup;
            this.fCachedSetLookup = null;
        }
        return unicodeSet;
    }

    @Override
    public String parseReference(String string, ParsePosition parsePosition, int n) {
        int n2;
        int n3;
        int n4;
        String string2 = "";
        for (n3 = n2 = parsePosition.getIndex(); n3 < n; n3 += UTF16.getCharCount(n4)) {
            n4 = UTF16.charAt(string, n3);
            if (n3 == n2 && !UCharacter.isUnicodeIdentifierStart(n4) || !UCharacter.isUnicodeIdentifierPart(n4)) break;
        }
        if (n3 == n2) {
            return string2;
        }
        parsePosition.setIndex(n3);
        string2 = string.substring(n2, n3);
        return string2;
    }

    RBBINode lookupNode(String string) {
        RBBINode rBBINode = null;
        RBBISymbolTableEntry rBBISymbolTableEntry = this.fHashTable.get(string);
        if (rBBISymbolTableEntry != null) {
            rBBINode = rBBISymbolTableEntry.val;
        }
        return rBBINode;
    }

    void addEntry(String string, RBBINode rBBINode) {
        RBBISymbolTableEntry rBBISymbolTableEntry = this.fHashTable.get(string);
        if (rBBISymbolTableEntry != null) {
            this.fRuleScanner.error(66055);
            return;
        }
        rBBISymbolTableEntry = new RBBISymbolTableEntry();
        rBBISymbolTableEntry.key = string;
        rBBISymbolTableEntry.val = rBBINode;
        this.fHashTable.put(rBBISymbolTableEntry.key, rBBISymbolTableEntry);
    }

    void rbbiSymtablePrint() {
        RBBISymbolTableEntry rBBISymbolTableEntry;
        int n;
        System.out.print("Variable Definitions\nName               Node Val     String Val\n----------------------------------------------------------------------\n");
        RBBISymbolTableEntry[] rBBISymbolTableEntryArray = this.fHashTable.values().toArray(new RBBISymbolTableEntry[0]);
        for (n = 0; n < rBBISymbolTableEntryArray.length; ++n) {
            rBBISymbolTableEntry = rBBISymbolTableEntryArray[n];
            System.out.print("  " + rBBISymbolTableEntry.key + "  ");
            System.out.print("  " + rBBISymbolTableEntry.val + "  ");
            System.out.print(rBBISymbolTableEntry.val.fLeftChild.fText);
            System.out.print("\n");
        }
        System.out.println("\nParsed Variable Definitions\n");
        for (n = 0; n < rBBISymbolTableEntryArray.length; ++n) {
            rBBISymbolTableEntry = rBBISymbolTableEntryArray[n];
            System.out.print(rBBISymbolTableEntry.key);
            rBBISymbolTableEntry.val.fLeftChild.printTree(false);
            System.out.print("\n");
        }
    }

    static class RBBISymbolTableEntry {
        String key;
        RBBINode val;

        RBBISymbolTableEntry() {
        }
    }
}

