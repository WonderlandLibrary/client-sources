/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUDebug;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.text.RBBINode;
import com.ibm.icu.text.RBBIRuleScanner;
import com.ibm.icu.text.RBBISetBuilder;
import com.ibm.icu.text.RBBITableBuilder;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RBBIRuleBuilder {
    String fDebugEnv;
    String fRules;
    StringBuilder fStrippedRules;
    RBBIRuleScanner fScanner;
    RBBINode[] fTreeRoots = new RBBINode[4];
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    int fDefaultTree = 0;
    boolean fChainRules;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    RBBISetBuilder fSetBuilder;
    List<RBBINode> fUSetNodes;
    RBBITableBuilder fForwardTable;
    Map<Set<Integer>, Integer> fStatusSets = new HashMap<Set<Integer>, Integer>();
    List<Integer> fRuleStatusVals;
    static final int U_BRK_ERROR_START = 66048;
    static final int U_BRK_INTERNAL_ERROR = 66049;
    static final int U_BRK_HEX_DIGITS_EXPECTED = 66050;
    static final int U_BRK_SEMICOLON_EXPECTED = 66051;
    static final int U_BRK_RULE_SYNTAX = 66052;
    static final int U_BRK_UNCLOSED_SET = 66053;
    static final int U_BRK_ASSIGN_ERROR = 66054;
    static final int U_BRK_VARIABLE_REDFINITION = 66055;
    static final int U_BRK_MISMATCHED_PAREN = 66056;
    static final int U_BRK_NEW_LINE_IN_QUOTED_STRING = 66057;
    static final int U_BRK_UNDEFINED_VARIABLE = 66058;
    static final int U_BRK_INIT_ERROR = 66059;
    static final int U_BRK_RULE_EMPTY_SET = 66060;
    static final int U_BRK_UNRECOGNIZED_OPTION = 66061;
    static final int U_BRK_MALFORMED_RULE_TAG = 66062;
    static final int U_BRK_MALFORMED_SET = 66063;
    static final int U_BRK_ERROR_LIMIT = 66064;
    static final boolean $assertionsDisabled = !RBBIRuleBuilder.class.desiredAssertionStatus();

    RBBIRuleBuilder(String string) {
        this.fDebugEnv = ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null;
        this.fRules = string;
        this.fStrippedRules = new StringBuilder(string);
        this.fUSetNodes = new ArrayList<RBBINode>();
        this.fRuleStatusVals = new ArrayList<Integer>();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }

    static final int align8(int n) {
        return n + 7 & 0xFFFFFFF8;
    }

    void flattenData(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String string = RBBIRuleScanner.stripRules(this.fStrippedRules.toString());
        int n = 80;
        int n2 = RBBIRuleBuilder.align8(this.fForwardTable.getTableSize());
        int n3 = RBBIRuleBuilder.align8(this.fForwardTable.getSafeTableSize());
        int n4 = RBBIRuleBuilder.align8(this.fSetBuilder.getTrieSize());
        int n5 = RBBIRuleBuilder.align8(this.fRuleStatusVals.size() * 4);
        int n6 = RBBIRuleBuilder.align8(string.length() * 2);
        int n7 = n + n2 + n3 + n5 + n4 + n6;
        int n8 = 0;
        ICUBinary.writeHeader(1114794784, 0x5000000, 0, dataOutputStream);
        int[] nArray = new int[20];
        nArray[0] = 45472;
        nArray[1] = 0x5000000;
        nArray[2] = n7;
        nArray[3] = this.fSetBuilder.getNumCharCategories();
        nArray[4] = n;
        nArray[5] = n2;
        nArray[6] = nArray[4] + n2;
        nArray[7] = n3;
        nArray[8] = nArray[6] + nArray[7];
        nArray[9] = this.fSetBuilder.getTrieSize();
        nArray[12] = nArray[8] + nArray[9];
        nArray[13] = n5;
        nArray[10] = nArray[12] + n5;
        nArray[11] = string.length() * 2;
        for (int i = 0; i < nArray.length; ++i) {
            dataOutputStream.writeInt(nArray[i]);
            n8 += 4;
        }
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = this.fForwardTable.exportTable();
        if (!$assertionsDisabled && n8 != nArray[4]) {
            throw new AssertionError();
        }
        n8 += rBBIStateTable.put(dataOutputStream);
        rBBIStateTable = this.fForwardTable.exportSafeTable();
        Assert.assrt(n8 == nArray[6]);
        Assert.assrt((n8 += rBBIStateTable.put(dataOutputStream)) == nArray[8]);
        this.fSetBuilder.serializeTrie(outputStream);
        n8 += nArray[9];
        while (n8 % 8 != 0) {
            dataOutputStream.write(0);
            ++n8;
        }
        Assert.assrt(n8 == nArray[12]);
        for (Integer n9 : this.fRuleStatusVals) {
            dataOutputStream.writeInt(n9);
            n8 += 4;
        }
        while (n8 % 8 != 0) {
            dataOutputStream.write(0);
            ++n8;
        }
        Assert.assrt(n8 == nArray[10]);
        dataOutputStream.writeChars(string);
        n8 += string.length() * 2;
        while (n8 % 8 != 0) {
            dataOutputStream.write(0);
            ++n8;
        }
    }

    static void compileRules(String string, OutputStream outputStream) throws IOException {
        RBBIRuleBuilder rBBIRuleBuilder = new RBBIRuleBuilder(string);
        rBBIRuleBuilder.build(outputStream);
    }

    void build(OutputStream outputStream) throws IOException {
        this.fScanner.parse();
        this.fSetBuilder.buildRanges();
        this.fForwardTable = new RBBITableBuilder(this, 0);
        this.fForwardTable.buildForwardTable();
        this.optimizeTables();
        this.fForwardTable.buildSafeReverseTable();
        if (this.fDebugEnv != null && this.fDebugEnv.indexOf("states") >= 0) {
            this.fForwardTable.printStates();
            this.fForwardTable.printRuleStatusTable();
            this.fForwardTable.printReverseTable();
        }
        this.fSetBuilder.buildTrie();
        this.flattenData(outputStream);
    }

    void optimizeTables() {
        boolean bl;
        do {
            bl = false;
            IntPair intPair = new IntPair(3, 0);
            while (this.fForwardTable.findDuplCharClassFrom(intPair)) {
                this.fSetBuilder.mergeCategories(intPair);
                this.fForwardTable.removeColumn(intPair.second);
                bl = true;
            }
            while (this.fForwardTable.removeDuplicateStates() > 0) {
                bl = true;
            }
        } while (bl);
    }

    static class IntPair {
        int first = 0;
        int second = 0;

        IntPair() {
        }

        IntPair(int n, int n2) {
            this.first = n;
            this.second = n2;
        }
    }
}

