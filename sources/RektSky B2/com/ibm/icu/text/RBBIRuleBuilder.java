package com.ibm.icu.text;

import com.ibm.icu.impl.*;
import java.util.*;
import java.io.*;

class RBBIRuleBuilder
{
    String fDebugEnv;
    String fRules;
    StringBuilder fStrippedRules;
    RBBIRuleScanner fScanner;
    RBBINode[] fTreeRoots;
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    int fDefaultTree;
    boolean fChainRules;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    RBBISetBuilder fSetBuilder;
    List<RBBINode> fUSetNodes;
    RBBITableBuilder fForwardTable;
    Map<Set<Integer>, Integer> fStatusSets;
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
    
    RBBIRuleBuilder(final String rules) {
        this.fTreeRoots = new RBBINode[4];
        this.fDefaultTree = 0;
        this.fStatusSets = new HashMap<Set<Integer>, Integer>();
        this.fDebugEnv = (ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null);
        this.fRules = rules;
        this.fStrippedRules = new StringBuilder(rules);
        this.fUSetNodes = new ArrayList<RBBINode>();
        this.fRuleStatusVals = new ArrayList<Integer>();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }
    
    static final int align8(final int i) {
        return i + 7 & 0xFFFFFFF8;
    }
    
    void flattenData(final OutputStream os) throws IOException {
        final DataOutputStream dos = new DataOutputStream(os);
        final String strippedRules = RBBIRuleScanner.stripRules(this.fStrippedRules.toString());
        final int headerSize = 80;
        final int forwardTableSize = align8(this.fForwardTable.getTableSize());
        final int reverseTableSize = align8(this.fForwardTable.getSafeTableSize());
        final int trieSize = align8(this.fSetBuilder.getTrieSize());
        final int statusTableSize = align8(this.fRuleStatusVals.size() * 4);
        final int rulesSize = align8(strippedRules.length() * 2);
        final int totalSize = headerSize + forwardTableSize + reverseTableSize + statusTableSize + trieSize + rulesSize;
        int outputPos = 0;
        ICUBinary.writeHeader(1114794784, 83886080, 0, dos);
        final int[] header = new int[20];
        header[0] = 45472;
        header[1] = 83886080;
        header[2] = totalSize;
        header[3] = this.fSetBuilder.getNumCharCategories();
        header[4] = headerSize;
        header[5] = forwardTableSize;
        header[6] = header[4] + forwardTableSize;
        header[7] = reverseTableSize;
        header[8] = header[6] + header[7];
        header[9] = this.fSetBuilder.getTrieSize();
        header[12] = header[8] + header[9];
        header[13] = statusTableSize;
        header[10] = header[12] + statusTableSize;
        header[11] = strippedRules.length() * 2;
        for (int i = 0; i < header.length; ++i) {
            dos.writeInt(header[i]);
            outputPos += 4;
        }
        RBBIDataWrapper.RBBIStateTable table = this.fForwardTable.exportTable();
        assert outputPos == header[4];
        outputPos += table.put(dos);
        table = this.fForwardTable.exportSafeTable();
        Assert.assrt(outputPos == header[6]);
        outputPos += table.put(dos);
        Assert.assrt(outputPos == header[8]);
        this.fSetBuilder.serializeTrie(os);
        for (outputPos += header[9]; outputPos % 8 != 0; ++outputPos) {
            dos.write(0);
        }
        Assert.assrt(outputPos == header[12]);
        for (final Integer val : this.fRuleStatusVals) {
            dos.writeInt(val);
            outputPos += 4;
        }
        while (outputPos % 8 != 0) {
            dos.write(0);
            ++outputPos;
        }
        Assert.assrt(outputPos == header[10]);
        dos.writeChars(strippedRules);
        for (outputPos += strippedRules.length() * 2; outputPos % 8 != 0; ++outputPos) {
            dos.write(0);
        }
    }
    
    static void compileRules(final String rules, final OutputStream os) throws IOException {
        final RBBIRuleBuilder builder = new RBBIRuleBuilder(rules);
        builder.build(os);
    }
    
    void build(final OutputStream os) throws IOException {
        this.fScanner.parse();
        this.fSetBuilder.buildRanges();
        (this.fForwardTable = new RBBITableBuilder(this, 0)).buildForwardTable();
        this.optimizeTables();
        this.fForwardTable.buildSafeReverseTable();
        if (this.fDebugEnv != null && this.fDebugEnv.indexOf("states") >= 0) {
            this.fForwardTable.printStates();
            this.fForwardTable.printRuleStatusTable();
            this.fForwardTable.printReverseTable();
        }
        this.fSetBuilder.buildTrie();
        this.flattenData(os);
    }
    
    void optimizeTables() {
        final IntPair duplPair = new IntPair(3, 0);
        while (this.fForwardTable.findDuplCharClassFrom(duplPair)) {
            this.fSetBuilder.mergeCategories(duplPair);
            this.fForwardTable.removeColumn(duplPair.second);
        }
        this.fForwardTable.removeDuplicateStates();
    }
    
    static class IntPair
    {
        int first;
        int second;
        
        IntPair() {
            this.first = 0;
            this.second = 0;
        }
        
        IntPair(final int f, final int s) {
            this.first = 0;
            this.second = 0;
            this.first = f;
            this.second = s;
        }
    }
}
