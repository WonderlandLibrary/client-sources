/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.Trie2Writable;
import com.ibm.icu.impl.Trie2_16;
import com.ibm.icu.text.RBBINode;
import com.ibm.icu.text.RBBIRuleBuilder;
import com.ibm.icu.text.UnicodeSet;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

class RBBISetBuilder {
    RBBIRuleBuilder fRB;
    RangeDescriptor fRangeList;
    Trie2Writable fTrie;
    Trie2_16 fFrozenTrie;
    int fGroupCount;
    boolean fSawBOF;
    static final int DICT_BIT = 16384;
    static final boolean $assertionsDisabled = !RBBISetBuilder.class.desiredAssertionStatus();

    RBBISetBuilder(RBBIRuleBuilder rBBIRuleBuilder) {
        this.fRB = rBBIRuleBuilder;
    }

    void buildRanges() {
        RangeDescriptor rangeDescriptor;
        Object object;
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
            this.printSets();
        }
        this.fRangeList = new RangeDescriptor();
        this.fRangeList.fStartChar = 0;
        this.fRangeList.fEndChar = 0x10FFFF;
        for (RBBINode object22 : this.fRB.fUSetNodes) {
            object = object22.fInputSet;
            int n = ((UnicodeSet)object).getRangeCount();
            int n2 = 0;
            rangeDescriptor = this.fRangeList;
            while (n2 < n) {
                int unicodeSet = ((UnicodeSet)object).getRangeStart(n2);
                int n3 = ((UnicodeSet)object).getRangeEnd(n2);
                while (rangeDescriptor.fEndChar < unicodeSet) {
                    rangeDescriptor = rangeDescriptor.fNext;
                }
                if (rangeDescriptor.fStartChar < unicodeSet) {
                    rangeDescriptor.split(unicodeSet);
                    continue;
                }
                if (rangeDescriptor.fEndChar > n3) {
                    rangeDescriptor.split(n3 + 1);
                }
                if (rangeDescriptor.fIncludesSets.indexOf(object22) == -1) {
                    rangeDescriptor.fIncludesSets.add(object22);
                }
                if (n3 == rangeDescriptor.fEndChar) {
                    ++n2;
                }
                rangeDescriptor = rangeDescriptor.fNext;
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
            this.printRanges();
        }
        rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            Object object3 = this.fRangeList;
            while (object3 != rangeDescriptor) {
                if (rangeDescriptor.fIncludesSets.equals(((RangeDescriptor)object3).fIncludesSets)) {
                    rangeDescriptor.fNum = ((RangeDescriptor)object3).fNum;
                    break;
                }
                object3 = ((RangeDescriptor)object3).fNext;
            }
            if (rangeDescriptor.fNum == 0) {
                ++this.fGroupCount;
                rangeDescriptor.fNum = this.fGroupCount + 2;
                rangeDescriptor.setDictionaryFlag();
                this.addValToSets(rangeDescriptor.fIncludesSets, this.fGroupCount + 2);
            }
            rangeDescriptor = rangeDescriptor.fNext;
        }
        String string = "eof";
        object = "bof";
        for (RBBINode rBBINode : this.fRB.fUSetNodes) {
            UnicodeSet unicodeSet = rBBINode.fInputSet;
            if (unicodeSet.contains(string)) {
                this.addValToSet(rBBINode, 1);
            }
            if (!unicodeSet.contains((CharSequence)object)) continue;
            this.addValToSet(rBBINode, 2);
            this.fSawBOF = true;
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
            this.printRangeGroups();
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("esets") >= 0) {
            this.printSets();
        }
    }

    void buildTrie() {
        this.fTrie = new Trie2Writable(0, 0);
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            this.fTrie.setRange(rangeDescriptor.fStartChar, rangeDescriptor.fEndChar, rangeDescriptor.fNum, false);
            rangeDescriptor = rangeDescriptor.fNext;
        }
    }

    void mergeCategories(RBBIRuleBuilder.IntPair intPair) {
        if (!$assertionsDisabled && intPair.first < 1) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && intPair.second <= intPair.first) {
            throw new AssertionError();
        }
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            int n = rangeDescriptor.fNum & 0xFFFFBFFF;
            int n2 = rangeDescriptor.fNum & 0x4000;
            if (n == intPair.second) {
                rangeDescriptor.fNum = intPair.first | n2;
            } else if (n > intPair.second) {
                --rangeDescriptor.fNum;
            }
            rangeDescriptor = rangeDescriptor.fNext;
        }
        --this.fGroupCount;
    }

    int getTrieSize() {
        if (this.fFrozenTrie == null) {
            this.fFrozenTrie = this.fTrie.toTrie2_16();
            this.fTrie = null;
        }
        return this.fFrozenTrie.getSerializedLength();
    }

    void serializeTrie(OutputStream outputStream) throws IOException {
        if (this.fFrozenTrie == null) {
            this.fFrozenTrie = this.fTrie.toTrie2_16();
            this.fTrie = null;
        }
        this.fFrozenTrie.serialize(outputStream);
    }

    void addValToSets(List<RBBINode> list, int n) {
        for (RBBINode rBBINode : list) {
            this.addValToSet(rBBINode, n);
        }
    }

    void addValToSet(RBBINode rBBINode, int n) {
        RBBINode rBBINode2 = new RBBINode(3);
        rBBINode2.fVal = n;
        if (rBBINode.fLeftChild == null) {
            rBBINode.fLeftChild = rBBINode2;
            rBBINode2.fParent = rBBINode;
        } else {
            RBBINode rBBINode3 = new RBBINode(9);
            rBBINode3.fLeftChild = rBBINode.fLeftChild;
            rBBINode3.fRightChild = rBBINode2;
            rBBINode3.fLeftChild.fParent = rBBINode3;
            rBBINode3.fRightChild.fParent = rBBINode3;
            rBBINode.fLeftChild = rBBINode3;
            rBBINode3.fParent = rBBINode;
        }
    }

    int getNumCharCategories() {
        return this.fGroupCount + 3;
    }

    boolean sawBOF() {
        return this.fSawBOF;
    }

    int getFirstChar(int n) {
        int n2 = -1;
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            if (rangeDescriptor.fNum == n) {
                n2 = rangeDescriptor.fStartChar;
                break;
            }
            rangeDescriptor = rangeDescriptor.fNext;
        }
        return n2;
    }

    void printRanges() {
        System.out.print("\n\n Nonoverlapping Ranges ...\n");
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            System.out.print(" " + rangeDescriptor.fNum + "   " + rangeDescriptor.fStartChar + "-" + rangeDescriptor.fEndChar);
            for (int i = 0; i < rangeDescriptor.fIncludesSets.size(); ++i) {
                RBBINode rBBINode;
                RBBINode rBBINode2 = rangeDescriptor.fIncludesSets.get(i);
                String string = "anon";
                RBBINode rBBINode3 = rBBINode2.fParent;
                if (rBBINode3 != null && (rBBINode = rBBINode3.fParent) != null && rBBINode.fType == 2) {
                    string = rBBINode.fText;
                }
                System.out.print(string);
                System.out.print("  ");
            }
            System.out.println("");
            rangeDescriptor = rangeDescriptor.fNext;
        }
    }

    void printRangeGroups() {
        int n = 0;
        System.out.print("\nRanges grouped by Unicode Set Membership...\n");
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            int n2 = rangeDescriptor.fNum & 0xBFFF;
            if (n2 > n) {
                int n3;
                n = n2;
                if (n2 < 10) {
                    System.out.print(" ");
                }
                System.out.print(n2 + " ");
                if ((rangeDescriptor.fNum & 0x4000) != 0) {
                    System.out.print(" <DICT> ");
                }
                for (n3 = 0; n3 < rangeDescriptor.fIncludesSets.size(); ++n3) {
                    RBBINode rBBINode;
                    RBBINode rBBINode2 = rangeDescriptor.fIncludesSets.get(n3);
                    String string = "anon";
                    RBBINode rBBINode3 = rBBINode2.fParent;
                    if (rBBINode3 != null && (rBBINode = rBBINode3.fParent) != null && rBBINode.fType == 2) {
                        string = rBBINode.fText;
                    }
                    System.out.print(string);
                    System.out.print(" ");
                }
                n3 = 0;
                RangeDescriptor rangeDescriptor2 = rangeDescriptor;
                while (rangeDescriptor2 != null) {
                    if (rangeDescriptor2.fNum == rangeDescriptor.fNum) {
                        if (n3++ % 5 == 0) {
                            System.out.print("\n    ");
                        }
                        RBBINode.printHex(rangeDescriptor2.fStartChar, -1);
                        System.out.print("-");
                        RBBINode.printHex(rangeDescriptor2.fEndChar, 0);
                    }
                    rangeDescriptor2 = rangeDescriptor2.fNext;
                }
                System.out.print("\n");
            }
            rangeDescriptor = rangeDescriptor.fNext;
        }
        System.out.print("\n");
    }

    void printSets() {
        System.out.print("\n\nUnicode Sets List\n------------------\n");
        for (int i = 0; i < this.fRB.fUSetNodes.size(); ++i) {
            RBBINode rBBINode;
            RBBINode rBBINode2 = this.fRB.fUSetNodes.get(i);
            RBBINode.printInt(2, i);
            String string = "anonymous";
            RBBINode rBBINode3 = rBBINode2.fParent;
            if (rBBINode3 != null && (rBBINode = rBBINode3.fParent) != null && rBBINode.fType == 2) {
                string = rBBINode.fText;
            }
            System.out.print("  " + string);
            System.out.print("   ");
            System.out.print(rBBINode2.fText);
            System.out.print("\n");
            if (rBBINode2.fLeftChild == null) continue;
            rBBINode2.fLeftChild.printTree(false);
        }
        System.out.print("\n");
    }

    static class RangeDescriptor {
        int fStartChar;
        int fEndChar;
        int fNum;
        List<RBBINode> fIncludesSets;
        RangeDescriptor fNext;

        RangeDescriptor() {
            this.fIncludesSets = new ArrayList<RBBINode>();
        }

        RangeDescriptor(RangeDescriptor rangeDescriptor) {
            this.fStartChar = rangeDescriptor.fStartChar;
            this.fEndChar = rangeDescriptor.fEndChar;
            this.fNum = rangeDescriptor.fNum;
            this.fIncludesSets = new ArrayList<RBBINode>(rangeDescriptor.fIncludesSets);
        }

        void split(int n) {
            Assert.assrt(n > this.fStartChar && n <= this.fEndChar);
            RangeDescriptor rangeDescriptor = new RangeDescriptor(this);
            rangeDescriptor.fStartChar = n;
            this.fEndChar = n - 1;
            rangeDescriptor.fNext = this.fNext;
            this.fNext = rangeDescriptor;
        }

        void setDictionaryFlag() {
            for (int i = 0; i < this.fIncludesSets.size(); ++i) {
                RBBINode rBBINode;
                RBBINode rBBINode2 = this.fIncludesSets.get(i);
                String string = "";
                RBBINode rBBINode3 = rBBINode2.fParent;
                if (rBBINode3 != null && (rBBINode = rBBINode3.fParent) != null && rBBINode.fType == 2) {
                    string = rBBINode.fText;
                }
                if (!string.equals("dictionary")) continue;
                this.fNum |= 0x4000;
                break;
            }
        }
    }
}

