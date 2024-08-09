/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.RBBINode;
import com.ibm.icu.text.RBBIRuleBuilder;
import com.ibm.icu.text.RBBIRuleParseTable;
import com.ibm.icu.text.RBBISymbolTable;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeSet;
import java.text.ParsePosition;
import java.util.HashMap;

class RBBIRuleScanner {
    private static final int kStackSize = 100;
    RBBIRuleBuilder fRB;
    int fScanIndex;
    int fNextIndex;
    boolean fQuoteMode;
    int fLineNum;
    int fCharNum;
    int fLastChar;
    RBBIRuleChar fC = new RBBIRuleChar();
    short[] fStack = new short[100];
    int fStackPtr;
    RBBINode[] fNodeStack = new RBBINode[100];
    int fNodeStackPtr;
    boolean fReverseRule;
    boolean fLookAheadRule;
    boolean fNoChainInRule;
    RBBISymbolTable fSymbolTable;
    HashMap<String, RBBISetTableEl> fSetTable = new HashMap();
    UnicodeSet[] fRuleSets = new UnicodeSet[10];
    int fRuleNum;
    int fOptionStart;
    private static String gRuleSet_rule_char_pattern = "[^[\\p{Z}\\u0020-\\u007f]-[\\p{L}]-[\\p{N}]]";
    private static String gRuleSet_name_char_pattern = "[_\\p{L}\\p{N}]";
    private static String gRuleSet_digit_char_pattern = "[0-9]";
    private static String gRuleSet_name_start_char_pattern = "[_\\p{L}]";
    private static String gRuleSet_white_space_pattern = "[\\p{Pattern_White_Space}]";
    private static String kAny = "any";
    static final int chNEL = 133;
    static final int chLS = 8232;

    RBBIRuleScanner(RBBIRuleBuilder rBBIRuleBuilder) {
        this.fRB = rBBIRuleBuilder;
        this.fLineNum = 1;
        this.fRuleSets[3] = new UnicodeSet(gRuleSet_rule_char_pattern);
        this.fRuleSets[4] = new UnicodeSet(gRuleSet_white_space_pattern);
        this.fRuleSets[1] = new UnicodeSet(gRuleSet_name_char_pattern);
        this.fRuleSets[2] = new UnicodeSet(gRuleSet_name_start_char_pattern);
        this.fRuleSets[0] = new UnicodeSet(gRuleSet_digit_char_pattern);
        this.fSymbolTable = new RBBISymbolTable(this);
    }

    boolean doParseActions(int n) {
        RBBINode rBBINode = null;
        boolean bl = true;
        switch (n) {
            case 11: {
                this.pushNewNode(7);
                ++this.fRuleNum;
                break;
            }
            case 14: {
                this.fNoChainInRule = true;
                break;
            }
            case 9: {
                this.fixOpStack(4);
                RBBINode rBBINode2 = this.fNodeStack[this.fNodeStackPtr--];
                RBBINode rBBINode3 = this.pushNewNode(9);
                rBBINode3.fLeftChild = rBBINode2;
                rBBINode2.fParent = rBBINode3;
                break;
            }
            case 7: {
                this.fixOpStack(4);
                RBBINode rBBINode4 = this.fNodeStack[this.fNodeStackPtr--];
                RBBINode rBBINode5 = this.pushNewNode(8);
                rBBINode5.fLeftChild = rBBINode4;
                rBBINode4.fParent = rBBINode5;
                break;
            }
            case 12: {
                this.pushNewNode(15);
                break;
            }
            case 10: {
                this.fixOpStack(2);
                break;
            }
            case 13: {
                break;
            }
            case 23: {
                rBBINode = this.fNodeStack[this.fNodeStackPtr - 1];
                rBBINode.fFirstPos = this.fNextIndex;
                this.pushNewNode(7);
                break;
            }
            case 3: {
                this.fixOpStack(1);
                RBBINode rBBINode6 = this.fNodeStack[this.fNodeStackPtr - 2];
                RBBINode rBBINode7 = this.fNodeStack[this.fNodeStackPtr - 1];
                RBBINode rBBINode8 = this.fNodeStack[this.fNodeStackPtr];
                rBBINode8.fFirstPos = rBBINode6.fFirstPos;
                rBBINode8.fLastPos = this.fScanIndex;
                rBBINode8.fText = this.fRB.fRules.substring(rBBINode8.fFirstPos, rBBINode8.fLastPos);
                rBBINode7.fLeftChild = rBBINode8;
                rBBINode8.fParent = rBBINode7;
                this.fSymbolTable.addEntry(rBBINode7.fText, rBBINode7);
                this.fNodeStackPtr -= 3;
                break;
            }
            case 4: {
                int n2;
                RBBINode rBBINode9;
                this.fixOpStack(1);
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rtree") >= 0) {
                    this.printNodeStack("end of rule");
                }
                Assert.assrt(this.fNodeStackPtr == 1);
                RBBINode rBBINode10 = this.fNodeStack[this.fNodeStackPtr];
                if (this.fLookAheadRule) {
                    RBBINode rBBINode11 = this.pushNewNode(6);
                    rBBINode9 = this.pushNewNode(8);
                    this.fNodeStackPtr -= 2;
                    rBBINode9.fLeftChild = rBBINode10;
                    rBBINode9.fRightChild = rBBINode11;
                    this.fNodeStack[this.fNodeStackPtr] = rBBINode9;
                    rBBINode11.fVal = this.fRuleNum;
                    rBBINode11.fLookAheadEnd = true;
                    rBBINode10 = rBBINode9;
                }
                rBBINode10.fRuleRoot = true;
                if (this.fRB.fChainRules && !this.fNoChainInRule) {
                    rBBINode10.fChainIn = true;
                }
                int n3 = n2 = this.fReverseRule ? 3 : this.fRB.fDefaultTree;
                if (this.fRB.fTreeRoots[n2] != null) {
                    rBBINode10 = this.fNodeStack[this.fNodeStackPtr];
                    rBBINode9 = this.fRB.fTreeRoots[n2];
                    RBBINode rBBINode12 = this.pushNewNode(9);
                    rBBINode12.fLeftChild = rBBINode9;
                    rBBINode9.fParent = rBBINode12;
                    rBBINode12.fRightChild = rBBINode10;
                    rBBINode10.fParent = rBBINode12;
                    this.fRB.fTreeRoots[n2] = rBBINode12;
                } else {
                    this.fRB.fTreeRoots[n2] = this.fNodeStack[this.fNodeStackPtr];
                }
                this.fReverseRule = false;
                this.fLookAheadRule = false;
                this.fNoChainInRule = false;
                this.fNodeStackPtr = 0;
                break;
            }
            case 19: {
                this.error(66052);
                bl = false;
                break;
            }
            case 32: {
                this.error(66052);
                break;
            }
            case 29: {
                RBBINode rBBINode13 = this.fNodeStack[this.fNodeStackPtr--];
                RBBINode rBBINode14 = this.pushNewNode(11);
                rBBINode14.fLeftChild = rBBINode13;
                rBBINode13.fParent = rBBINode14;
                break;
            }
            case 30: {
                RBBINode rBBINode15 = this.fNodeStack[this.fNodeStackPtr--];
                RBBINode rBBINode16 = this.pushNewNode(12);
                rBBINode16.fLeftChild = rBBINode15;
                rBBINode15.fParent = rBBINode16;
                break;
            }
            case 31: {
                RBBINode rBBINode17 = this.fNodeStack[this.fNodeStackPtr--];
                RBBINode rBBINode18 = this.pushNewNode(10);
                rBBINode18.fLeftChild = rBBINode17;
                rBBINode17.fParent = rBBINode18;
                break;
            }
            case 18: {
                rBBINode = this.pushNewNode(0);
                String string = String.valueOf((char)this.fC.fChar);
                this.findSetFor(string, rBBINode, null);
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                break;
            }
            case 2: {
                rBBINode = this.pushNewNode(0);
                this.findSetFor(kAny, rBBINode, null);
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                break;
            }
            case 22: {
                rBBINode = this.pushNewNode(4);
                rBBINode.fVal = this.fRuleNum;
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                this.fLookAheadRule = true;
                break;
            }
            case 24: {
                rBBINode = this.pushNewNode(5);
                rBBINode.fVal = 0;
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                break;
            }
            case 26: {
                rBBINode = this.fNodeStack[this.fNodeStackPtr];
                int n4 = UCharacter.digit((char)this.fC.fChar, 10);
                rBBINode.fVal = rBBINode.fVal * 10 + n4;
                break;
            }
            case 28: {
                rBBINode = this.fNodeStack[this.fNodeStackPtr];
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                break;
            }
            case 27: {
                this.error(66062);
                bl = false;
                break;
            }
            case 16: {
                this.fOptionStart = this.fScanIndex;
                break;
            }
            case 15: {
                String string = this.fRB.fRules.substring(this.fOptionStart, this.fScanIndex);
                if (string.equals("chain")) {
                    this.fRB.fChainRules = true;
                    break;
                }
                if (string.equals("LBCMNoChain")) {
                    this.fRB.fLBCMNoChain = true;
                    break;
                }
                if (string.equals("forward")) {
                    this.fRB.fDefaultTree = 0;
                    break;
                }
                if (string.equals("reverse")) {
                    this.fRB.fDefaultTree = 1;
                    break;
                }
                if (string.equals("safe_forward")) {
                    this.fRB.fDefaultTree = 2;
                    break;
                }
                if (string.equals("safe_reverse")) {
                    this.fRB.fDefaultTree = 3;
                    break;
                }
                if (string.equals("lookAheadHardBreak")) {
                    this.fRB.fLookAheadHardBreak = true;
                    break;
                }
                if (string.equals("quoted_literals_only")) {
                    this.fRuleSets[3].clear();
                    break;
                }
                if (string.equals("unquoted_literals")) {
                    this.fRuleSets[3].applyPattern(gRuleSet_rule_char_pattern);
                    break;
                }
                this.error(66061);
                break;
            }
            case 17: {
                this.fReverseRule = true;
                break;
            }
            case 25: {
                rBBINode = this.pushNewNode(2);
                rBBINode.fFirstPos = this.fScanIndex;
                break;
            }
            case 5: {
                rBBINode = this.fNodeStack[this.fNodeStackPtr];
                if (rBBINode == null || rBBINode.fType != 2) {
                    this.error(66049);
                    break;
                }
                rBBINode.fLastPos = this.fScanIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos + 1, rBBINode.fLastPos);
                rBBINode.fLeftChild = this.fSymbolTable.lookupNode(rBBINode.fText);
                break;
            }
            case 1: {
                rBBINode = this.fNodeStack[this.fNodeStackPtr];
                if (rBBINode.fLeftChild != null) break;
                this.error(66058);
                bl = false;
                break;
            }
            case 8: {
                break;
            }
            case 20: {
                this.error(66054);
                bl = false;
                break;
            }
            case 6: {
                bl = false;
                break;
            }
            case 21: {
                this.scanSet();
                break;
            }
            default: {
                this.error(66049);
                bl = false;
            }
        }
        return bl;
    }

    void error(int n) {
        String string = "Error " + n + " at line " + this.fLineNum + " column " + this.fCharNum;
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException(string);
        throw illegalArgumentException;
    }

    void fixOpStack(int n) {
        RBBINode rBBINode;
        while (true) {
            rBBINode = this.fNodeStack[this.fNodeStackPtr - 1];
            if (rBBINode.fPrecedence == 0) {
                System.out.print("RBBIRuleScanner.fixOpStack, bad operator node");
                this.error(66049);
                return;
            }
            if (rBBINode.fPrecedence < n || rBBINode.fPrecedence <= 2) break;
            rBBINode.fRightChild = this.fNodeStack[this.fNodeStackPtr];
            this.fNodeStack[this.fNodeStackPtr].fParent = rBBINode;
            --this.fNodeStackPtr;
        }
        if (n <= 2) {
            if (rBBINode.fPrecedence != n) {
                this.error(66056);
            }
            this.fNodeStack[this.fNodeStackPtr - 1] = this.fNodeStack[this.fNodeStackPtr];
            --this.fNodeStackPtr;
        }
    }

    void findSetFor(String string, RBBINode rBBINode, UnicodeSet unicodeSet) {
        RBBISetTableEl rBBISetTableEl = this.fSetTable.get(string);
        if (rBBISetTableEl != null) {
            rBBINode.fLeftChild = rBBISetTableEl.val;
            Assert.assrt(rBBINode.fLeftChild.fType == 1);
            return;
        }
        if (unicodeSet == null) {
            if (string.equals(kAny)) {
                unicodeSet = new UnicodeSet(0, 0x10FFFF);
            } else {
                int n = UTF16.charAt(string, 0);
                unicodeSet = new UnicodeSet(n, n);
            }
        }
        RBBINode rBBINode2 = new RBBINode(1);
        rBBINode2.fInputSet = unicodeSet;
        rBBINode2.fParent = rBBINode;
        rBBINode.fLeftChild = rBBINode2;
        rBBINode2.fText = string;
        this.fRB.fUSetNodes.add(rBBINode2);
        rBBISetTableEl = new RBBISetTableEl();
        rBBISetTableEl.key = string;
        rBBISetTableEl.val = rBBINode2;
        this.fSetTable.put(rBBISetTableEl.key, rBBISetTableEl);
    }

    static String stripRules(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        boolean bl = false;
        int n2 = 0;
        while (n2 < n) {
            int n3 = string.codePointAt(n2);
            boolean bl2 = UCharacter.hasBinaryProperty(n3, 43);
            if (!bl || !bl2) {
                stringBuilder.appendCodePoint(n3);
                bl = bl2;
            }
            n2 = string.offsetByCodePoints(n2, 1);
        }
        return stringBuilder.toString();
    }

    int nextCharLL() {
        if (this.fNextIndex >= this.fRB.fRules.length()) {
            return 1;
        }
        int n = UTF16.charAt(this.fRB.fRules, this.fNextIndex);
        this.fNextIndex = UTF16.moveCodePointOffset(this.fRB.fRules, this.fNextIndex, 1);
        if (n == 13 || n == 133 || n == 8232 || n == 10 && this.fLastChar != 13) {
            ++this.fLineNum;
            this.fCharNum = 0;
            if (this.fQuoteMode) {
                this.error(66057);
                this.fQuoteMode = false;
            }
        } else if (n != 10) {
            ++this.fCharNum;
        }
        this.fLastChar = n;
        return n;
    }

    void nextChar(RBBIRuleChar rBBIRuleChar) {
        this.fScanIndex = this.fNextIndex;
        rBBIRuleChar.fChar = this.nextCharLL();
        rBBIRuleChar.fEscaped = false;
        if (rBBIRuleChar.fChar == 39) {
            if (UTF16.charAt(this.fRB.fRules, this.fNextIndex) == 39) {
                rBBIRuleChar.fChar = this.nextCharLL();
                rBBIRuleChar.fEscaped = true;
            } else {
                this.fQuoteMode = !this.fQuoteMode;
                rBBIRuleChar.fChar = this.fQuoteMode ? 40 : 41;
                rBBIRuleChar.fEscaped = false;
                return;
            }
        }
        if (this.fQuoteMode) {
            rBBIRuleChar.fEscaped = true;
        } else {
            if (rBBIRuleChar.fChar == 35) {
                int n = this.fScanIndex;
                do {
                    rBBIRuleChar.fChar = this.nextCharLL();
                } while (rBBIRuleChar.fChar != -1 && rBBIRuleChar.fChar != 13 && rBBIRuleChar.fChar != 10 && rBBIRuleChar.fChar != 133 && rBBIRuleChar.fChar != 8232);
                for (int i = n; i < this.fNextIndex - 1; ++i) {
                    this.fRB.fStrippedRules.setCharAt(i, ' ');
                }
            }
            if (rBBIRuleChar.fChar == -1) {
                return;
            }
            if (rBBIRuleChar.fChar == 92) {
                rBBIRuleChar.fEscaped = true;
                int[] nArray = new int[]{this.fNextIndex};
                rBBIRuleChar.fChar = Utility.unescapeAt(this.fRB.fRules, nArray);
                if (nArray[0] == this.fNextIndex) {
                    this.error(66050);
                }
                this.fCharNum += nArray[0] - this.fNextIndex;
                this.fNextIndex = nArray[0];
            }
        }
    }

    void parse() {
        int n = 1;
        this.nextChar(this.fC);
        while (n != 0) {
            RBBIRuleParseTable.RBBIRuleTableElement rBBIRuleTableElement = RBBIRuleParseTable.gRuleParseStateTable[n];
            if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                System.out.println("char, line, col = ('" + (char)this.fC.fChar + "', " + this.fLineNum + ", " + this.fCharNum + "    state = " + rBBIRuleTableElement.fStateName);
            }
            int n2 = n;
            while (true) {
                UnicodeSet unicodeSet;
                rBBIRuleTableElement = RBBIRuleParseTable.gRuleParseStateTable[n2];
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                    System.out.print(".");
                }
                if (rBBIRuleTableElement.fCharClass < 127 && !this.fC.fEscaped && rBBIRuleTableElement.fCharClass == this.fC.fChar || rBBIRuleTableElement.fCharClass == 255 || rBBIRuleTableElement.fCharClass == 254 && this.fC.fEscaped || rBBIRuleTableElement.fCharClass == 253 && this.fC.fEscaped && (this.fC.fChar == 80 || this.fC.fChar == 112) || rBBIRuleTableElement.fCharClass == 252 && this.fC.fChar == -1 || rBBIRuleTableElement.fCharClass >= 128 && rBBIRuleTableElement.fCharClass < 240 && !this.fC.fEscaped && this.fC.fChar != -1 && (unicodeSet = this.fRuleSets[rBBIRuleTableElement.fCharClass - 128]).contains(this.fC.fChar)) break;
                ++n2;
            }
            if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                System.out.println("");
            }
            if (!this.doParseActions(rBBIRuleTableElement.fAction)) break;
            if (rBBIRuleTableElement.fPushState != 0) {
                ++this.fStackPtr;
                if (this.fStackPtr >= 100) {
                    System.out.println("RBBIRuleScanner.parse() - state stack overflow.");
                    this.error(66049);
                }
                this.fStack[this.fStackPtr] = rBBIRuleTableElement.fPushState;
            }
            if (rBBIRuleTableElement.fNextChar) {
                this.nextChar(this.fC);
            }
            if (rBBIRuleTableElement.fNextState != 255) {
                n = rBBIRuleTableElement.fNextState;
                continue;
            }
            n = this.fStack[this.fStackPtr];
            --this.fStackPtr;
            if (this.fStackPtr >= 0) continue;
            System.out.println("RBBIRuleScanner.parse() - state stack underflow.");
            this.error(66049);
        }
        if (this.fRB.fTreeRoots[0] == null) {
            this.error(66052);
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("symbols") >= 0) {
            this.fSymbolTable.rbbiSymtablePrint();
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ptree") >= 0) {
            System.out.println("Completed Forward Rules Parse Tree...");
            this.fRB.fTreeRoots[0].printTree(false);
            System.out.println("\nCompleted Reverse Rules Parse Tree...");
            this.fRB.fTreeRoots[1].printTree(false);
            System.out.println("\nCompleted Safe Point Forward Rules Parse Tree...");
            if (this.fRB.fTreeRoots[2] == null) {
                System.out.println("  -- null -- ");
            } else {
                this.fRB.fTreeRoots[2].printTree(false);
            }
            System.out.println("\nCompleted Safe Point Reverse Rules Parse Tree...");
            if (this.fRB.fTreeRoots[3] == null) {
                System.out.println("  -- null -- ");
            } else {
                this.fRB.fTreeRoots[3].printTree(false);
            }
        }
    }

    void printNodeStack(String string) {
        System.out.println(string + ".  Dumping node stack...\n");
        for (int i = this.fNodeStackPtr; i > 0; --i) {
            this.fNodeStack[i].printTree(false);
        }
    }

    RBBINode pushNewNode(int n) {
        ++this.fNodeStackPtr;
        if (this.fNodeStackPtr >= 100) {
            System.out.println("RBBIRuleScanner.pushNewNode - stack overflow.");
            this.error(66049);
        }
        this.fNodeStack[this.fNodeStackPtr] = new RBBINode(n);
        return this.fNodeStack[this.fNodeStackPtr];
    }

    void scanSet() {
        UnicodeSet unicodeSet = null;
        ParsePosition parsePosition = new ParsePosition(this.fScanIndex);
        int n = this.fScanIndex;
        try {
            unicodeSet = new UnicodeSet(this.fRB.fRules, parsePosition, this.fSymbolTable, 1);
        } catch (Exception exception) {
            this.error(66063);
        }
        if (unicodeSet.isEmpty()) {
            this.error(66060);
        }
        int n2 = parsePosition.getIndex();
        while (this.fNextIndex < n2) {
            this.nextCharLL();
        }
        RBBINode rBBINode = this.pushNewNode(0);
        rBBINode.fFirstPos = n;
        rBBINode.fLastPos = this.fNextIndex;
        rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
        this.findSetFor(rBBINode.fText, rBBINode, unicodeSet);
    }

    static class RBBISetTableEl {
        String key;
        RBBINode val;

        RBBISetTableEl() {
        }
    }

    static class RBBIRuleChar {
        int fChar;
        boolean fEscaped;

        RBBIRuleChar() {
        }
    }
}

