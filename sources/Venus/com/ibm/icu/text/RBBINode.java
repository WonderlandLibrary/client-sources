/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.text.UnicodeSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RBBINode {
    static final int setRef = 0;
    static final int uset = 1;
    static final int varRef = 2;
    static final int leafChar = 3;
    static final int lookAhead = 4;
    static final int tag = 5;
    static final int endMark = 6;
    static final int opStart = 7;
    static final int opCat = 8;
    static final int opOr = 9;
    static final int opStar = 10;
    static final int opPlus = 11;
    static final int opQuestion = 12;
    static final int opBreak = 13;
    static final int opReverse = 14;
    static final int opLParen = 15;
    static final int nodeTypeLimit = 16;
    static final String[] nodeTypeNames = new String[]{"setRef", "uset", "varRef", "leafChar", "lookAhead", "tag", "endMark", "opStart", "opCat", "opOr", "opStar", "opPlus", "opQuestion", "opBreak", "opReverse", "opLParen"};
    static final int precZero = 0;
    static final int precStart = 1;
    static final int precLParen = 2;
    static final int precOpOr = 3;
    static final int precOpCat = 4;
    int fType;
    RBBINode fParent;
    RBBINode fLeftChild;
    RBBINode fRightChild;
    UnicodeSet fInputSet;
    int fPrecedence = 0;
    String fText;
    int fFirstPos;
    int fLastPos;
    boolean fNullable;
    int fVal;
    boolean fLookAheadEnd;
    boolean fRuleRoot;
    boolean fChainIn;
    Set<RBBINode> fFirstPosSet;
    Set<RBBINode> fLastPosSet;
    Set<RBBINode> fFollowPos;
    int fSerialNum;
    static int gLastSerial;

    RBBINode(int n) {
        Assert.assrt(n < 16);
        this.fSerialNum = ++gLastSerial;
        this.fType = n;
        this.fFirstPosSet = new HashSet<RBBINode>();
        this.fLastPosSet = new HashSet<RBBINode>();
        this.fFollowPos = new HashSet<RBBINode>();
        this.fPrecedence = n == 8 ? 4 : (n == 9 ? 3 : (n == 7 ? 1 : (n == 15 ? 2 : 0)));
    }

    RBBINode(RBBINode rBBINode) {
        this.fSerialNum = ++gLastSerial;
        this.fType = rBBINode.fType;
        this.fInputSet = rBBINode.fInputSet;
        this.fPrecedence = rBBINode.fPrecedence;
        this.fText = rBBINode.fText;
        this.fFirstPos = rBBINode.fFirstPos;
        this.fLastPos = rBBINode.fLastPos;
        this.fNullable = rBBINode.fNullable;
        this.fVal = rBBINode.fVal;
        this.fRuleRoot = false;
        this.fChainIn = rBBINode.fChainIn;
        this.fFirstPosSet = new HashSet<RBBINode>(rBBINode.fFirstPosSet);
        this.fLastPosSet = new HashSet<RBBINode>(rBBINode.fLastPosSet);
        this.fFollowPos = new HashSet<RBBINode>(rBBINode.fFollowPos);
    }

    RBBINode cloneTree() {
        RBBINode rBBINode;
        if (this.fType == 2) {
            rBBINode = this.fLeftChild.cloneTree();
        } else if (this.fType == 1) {
            rBBINode = this;
        } else {
            rBBINode = new RBBINode(this);
            if (this.fLeftChild != null) {
                rBBINode.fLeftChild = this.fLeftChild.cloneTree();
                rBBINode.fLeftChild.fParent = rBBINode;
            }
            if (this.fRightChild != null) {
                rBBINode.fRightChild = this.fRightChild.cloneTree();
                rBBINode.fRightChild.fParent = rBBINode;
            }
        }
        return rBBINode;
    }

    RBBINode flattenVariables() {
        if (this.fType == 2) {
            RBBINode rBBINode = this.fLeftChild.cloneTree();
            rBBINode.fRuleRoot = this.fRuleRoot;
            rBBINode.fChainIn = this.fChainIn;
            return rBBINode;
        }
        if (this.fLeftChild != null) {
            this.fLeftChild = this.fLeftChild.flattenVariables();
            this.fLeftChild.fParent = this;
        }
        if (this.fRightChild != null) {
            this.fRightChild = this.fRightChild.flattenVariables();
            this.fRightChild.fParent = this;
        }
        return this;
    }

    void flattenSets() {
        RBBINode rBBINode;
        RBBINode rBBINode2;
        RBBINode rBBINode3;
        Assert.assrt(this.fType != 0);
        if (this.fLeftChild != null) {
            if (this.fLeftChild.fType == 0) {
                rBBINode3 = this.fLeftChild;
                rBBINode2 = rBBINode3.fLeftChild;
                rBBINode = rBBINode2.fLeftChild;
                this.fLeftChild = rBBINode.cloneTree();
                this.fLeftChild.fParent = this;
            } else {
                this.fLeftChild.flattenSets();
            }
        }
        if (this.fRightChild != null) {
            if (this.fRightChild.fType == 0) {
                rBBINode3 = this.fRightChild;
                rBBINode2 = rBBINode3.fLeftChild;
                rBBINode = rBBINode2.fLeftChild;
                this.fRightChild = rBBINode.cloneTree();
                this.fRightChild.fParent = this;
            } else {
                this.fRightChild.flattenSets();
            }
        }
    }

    void findNodes(List<RBBINode> list, int n) {
        if (this.fType == n) {
            list.add(this);
        }
        if (this.fLeftChild != null) {
            this.fLeftChild.findNodes(list, n);
        }
        if (this.fRightChild != null) {
            this.fRightChild.findNodes(list, n);
        }
    }

    static void printNode(RBBINode rBBINode) {
        if (rBBINode == null) {
            System.out.print(" -- null --\n");
        } else {
            RBBINode.printInt(rBBINode.fSerialNum, 10);
            RBBINode.printString(nodeTypeNames[rBBINode.fType], 11);
            RBBINode.printInt(rBBINode.fParent == null ? 0 : rBBINode.fParent.fSerialNum, 11);
            RBBINode.printInt(rBBINode.fLeftChild == null ? 0 : rBBINode.fLeftChild.fSerialNum, 11);
            RBBINode.printInt(rBBINode.fRightChild == null ? 0 : rBBINode.fRightChild.fSerialNum, 12);
            RBBINode.printInt(rBBINode.fFirstPos, 12);
            RBBINode.printInt(rBBINode.fVal, 7);
            if (rBBINode.fType == 2) {
                System.out.print(" " + rBBINode.fText);
            }
        }
        System.out.println("");
    }

    static void printString(String string, int n) {
        int n2;
        for (n2 = n; n2 < 0; ++n2) {
            System.out.print(' ');
        }
        for (n2 = string.length(); n2 < n; ++n2) {
            System.out.print(' ');
        }
        System.out.print(string);
    }

    static void printInt(int n, int n2) {
        String string = Integer.toString(n);
        RBBINode.printString(string, Math.max(n2, string.length() + 1));
    }

    static void printHex(int n, int n2) {
        String string = Integer.toString(n, 16);
        String string2 = "00000".substring(0, Math.max(0, 5 - string.length()));
        string = string2 + string;
        RBBINode.printString(string, n2);
    }

    void printTree(boolean bl) {
        if (bl) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("    Serial       type     Parent  LeftChild  RightChild    position  value");
        }
        RBBINode.printNode(this);
        if (this.fType != 2) {
            if (this.fLeftChild != null) {
                this.fLeftChild.printTree(true);
            }
            if (this.fRightChild != null) {
                this.fRightChild.printTree(true);
            }
        }
    }
}

