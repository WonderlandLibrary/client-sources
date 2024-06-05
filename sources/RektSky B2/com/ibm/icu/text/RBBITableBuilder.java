package com.ibm.icu.text;

import com.ibm.icu.lang.*;
import java.util.*;
import com.ibm.icu.impl.*;

class RBBITableBuilder
{
    private RBBIRuleBuilder fRB;
    private int fRootIx;
    private List<RBBIStateDescriptor> fDStates;
    private List<short[]> fSafeTable;
    
    RBBITableBuilder(final RBBIRuleBuilder rb, final int rootNodeIx) {
        this.fRootIx = rootNodeIx;
        this.fRB = rb;
        this.fDStates = new ArrayList<RBBIStateDescriptor>();
    }
    
    void buildForwardTable() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return;
        }
        this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            final RBBINode bofTop = new RBBINode(8);
            final RBBINode bofLeaf = new RBBINode(3);
            bofTop.fLeftChild = bofLeaf;
            bofTop.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            bofLeaf.fParent = bofTop;
            bofLeaf.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = bofTop;
        }
        final RBBINode cn = new RBBINode(8);
        cn.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
        this.fRB.fTreeRoots[this.fRootIx].fParent = cn;
        cn.fRightChild = new RBBINode(6);
        cn.fRightChild.fParent = cn;
        (this.fRB.fTreeRoots[this.fRootIx] = cn).flattenSets();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        this.calcNullable(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFirstPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcLastPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("pos") >= 0) {
            System.out.print("\n");
            this.printPosSets(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fChainRules) {
            this.calcChainedFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            this.bofFixup();
        }
        this.buildStateTable();
        this.flagAcceptingStates();
        this.flagLookAheadStates();
        this.flagTaggedStates();
        this.mergeRuleStatusVals();
    }
    
    void calcNullable(final RBBINode n) {
        if (n == null) {
            return;
        }
        if (n.fType == 0 || n.fType == 6) {
            n.fNullable = false;
            return;
        }
        if (n.fType == 4 || n.fType == 5) {
            n.fNullable = true;
            return;
        }
        this.calcNullable(n.fLeftChild);
        this.calcNullable(n.fRightChild);
        if (n.fType == 9) {
            n.fNullable = (n.fLeftChild.fNullable || n.fRightChild.fNullable);
        }
        else if (n.fType == 8) {
            n.fNullable = (n.fLeftChild.fNullable && n.fRightChild.fNullable);
        }
        else if (n.fType == 10 || n.fType == 12) {
            n.fNullable = true;
        }
        else {
            n.fNullable = false;
        }
    }
    
    void calcFirstPos(final RBBINode n) {
        if (n == null) {
            return;
        }
        if (n.fType == 3 || n.fType == 6 || n.fType == 4 || n.fType == 5) {
            n.fFirstPosSet.add(n);
            return;
        }
        this.calcFirstPos(n.fLeftChild);
        this.calcFirstPos(n.fRightChild);
        if (n.fType == 9) {
            n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
            n.fFirstPosSet.addAll(n.fRightChild.fFirstPosSet);
        }
        else if (n.fType == 8) {
            n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
            if (n.fLeftChild.fNullable) {
                n.fFirstPosSet.addAll(n.fRightChild.fFirstPosSet);
            }
        }
        else if (n.fType == 10 || n.fType == 12 || n.fType == 11) {
            n.fFirstPosSet.addAll(n.fLeftChild.fFirstPosSet);
        }
    }
    
    void calcLastPos(final RBBINode n) {
        if (n == null) {
            return;
        }
        if (n.fType == 3 || n.fType == 6 || n.fType == 4 || n.fType == 5) {
            n.fLastPosSet.add(n);
            return;
        }
        this.calcLastPos(n.fLeftChild);
        this.calcLastPos(n.fRightChild);
        if (n.fType == 9) {
            n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
            n.fLastPosSet.addAll(n.fRightChild.fLastPosSet);
        }
        else if (n.fType == 8) {
            n.fLastPosSet.addAll(n.fRightChild.fLastPosSet);
            if (n.fRightChild.fNullable) {
                n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
            }
        }
        else if (n.fType == 10 || n.fType == 12 || n.fType == 11) {
            n.fLastPosSet.addAll(n.fLeftChild.fLastPosSet);
        }
    }
    
    void calcFollowPos(final RBBINode n) {
        if (n == null || n.fType == 3 || n.fType == 6) {
            return;
        }
        this.calcFollowPos(n.fLeftChild);
        this.calcFollowPos(n.fRightChild);
        if (n.fType == 8) {
            for (final RBBINode i : n.fLeftChild.fLastPosSet) {
                i.fFollowPos.addAll(n.fRightChild.fFirstPosSet);
            }
        }
        if (n.fType == 10 || n.fType == 11) {
            for (final RBBINode i : n.fLastPosSet) {
                i.fFollowPos.addAll(n.fFirstPosSet);
            }
        }
    }
    
    void addRuleRootNodes(final List<RBBINode> dest, final RBBINode node) {
        if (node == null) {
            return;
        }
        if (node.fRuleRoot) {
            dest.add(node);
            return;
        }
        this.addRuleRootNodes(dest, node.fLeftChild);
        this.addRuleRootNodes(dest, node.fRightChild);
    }
    
    void calcChainedFollowPos(final RBBINode tree) {
        final List<RBBINode> endMarkerNodes = new ArrayList<RBBINode>();
        final List<RBBINode> leafNodes = new ArrayList<RBBINode>();
        tree.findNodes(endMarkerNodes, 6);
        tree.findNodes(leafNodes, 3);
        final List<RBBINode> ruleRootNodes = new ArrayList<RBBINode>();
        this.addRuleRootNodes(ruleRootNodes, tree);
        final Set<RBBINode> matchStartNodes = new HashSet<RBBINode>();
        for (final RBBINode node : ruleRootNodes) {
            if (node.fChainIn) {
                matchStartNodes.addAll(node.fFirstPosSet);
            }
        }
        for (final RBBINode tNode : leafNodes) {
            RBBINode endNode = null;
            for (final RBBINode endMarkerNode : endMarkerNodes) {
                if (tNode.fFollowPos.contains(endMarkerNode)) {
                    endNode = tNode;
                    break;
                }
            }
            if (endNode == null) {
                continue;
            }
            if (this.fRB.fLBCMNoChain) {
                final int c = this.fRB.fSetBuilder.getFirstChar(endNode.fVal);
                if (c != -1) {
                    final int cLBProp = UCharacter.getIntPropertyValue(c, 4104);
                    if (cLBProp == 9) {
                        continue;
                    }
                }
            }
            for (final RBBINode startNode : matchStartNodes) {
                if (startNode.fType != 3) {
                    continue;
                }
                if (endNode.fVal != startNode.fVal) {
                    continue;
                }
                endNode.fFollowPos.addAll(startNode.fFollowPos);
            }
        }
    }
    
    void bofFixup() {
        final RBBINode bofNode = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
        Assert.assrt(bofNode.fType == 3);
        Assert.assrt(bofNode.fVal == 2);
        final Set<RBBINode> matchStartNodes = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet;
        for (final RBBINode startNode : matchStartNodes) {
            if (startNode.fType != 3) {
                continue;
            }
            if (startNode.fVal != bofNode.fVal) {
                continue;
            }
            bofNode.fFollowPos.addAll(startNode.fFollowPos);
        }
    }
    
    void buildStateTable() {
        final int lastInputSymbol = this.fRB.fSetBuilder.getNumCharCategories() - 1;
        final RBBIStateDescriptor failState = new RBBIStateDescriptor(lastInputSymbol);
        this.fDStates.add(failState);
        final RBBIStateDescriptor initialState = new RBBIStateDescriptor(lastInputSymbol);
        initialState.fPositions.addAll(this.fRB.fTreeRoots[this.fRootIx].fFirstPosSet);
        this.fDStates.add(initialState);
        while (true) {
            RBBIStateDescriptor T = null;
            for (int tx = 1; tx < this.fDStates.size(); ++tx) {
                final RBBIStateDescriptor temp = this.fDStates.get(tx);
                if (!temp.fMarked) {
                    T = temp;
                    break;
                }
            }
            if (T == null) {
                break;
            }
            T.fMarked = true;
            for (int a = 1; a <= lastInputSymbol; ++a) {
                Set<RBBINode> U = null;
                for (final RBBINode p : T.fPositions) {
                    if (p.fType == 3 && p.fVal == a) {
                        if (U == null) {
                            U = new HashSet<RBBINode>();
                        }
                        U.addAll(p.fFollowPos);
                    }
                }
                int ux = 0;
                boolean UinDstates = false;
                if (U != null) {
                    Assert.assrt(U.size() > 0);
                    for (int ix = 0; ix < this.fDStates.size(); ++ix) {
                        final RBBIStateDescriptor temp2 = this.fDStates.get(ix);
                        if (U.equals(temp2.fPositions)) {
                            U = temp2.fPositions;
                            ux = ix;
                            UinDstates = true;
                            break;
                        }
                    }
                    if (!UinDstates) {
                        final RBBIStateDescriptor newState = new RBBIStateDescriptor(lastInputSymbol);
                        newState.fPositions = U;
                        this.fDStates.add(newState);
                        ux = this.fDStates.size() - 1;
                    }
                    T.fDtran[a] = ux;
                }
            }
        }
    }
    
    void flagAcceptingStates() {
        final List<RBBINode> endMarkerNodes = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(endMarkerNodes, 6);
        for (int i = 0; i < endMarkerNodes.size(); ++i) {
            final RBBINode endMarker = endMarkerNodes.get(i);
            for (int n = 0; n < this.fDStates.size(); ++n) {
                final RBBIStateDescriptor sd = this.fDStates.get(n);
                if (sd.fPositions.contains(endMarker)) {
                    if (sd.fAccepting == 0) {
                        sd.fAccepting = endMarker.fVal;
                        if (sd.fAccepting == 0) {
                            sd.fAccepting = -1;
                        }
                    }
                    if (sd.fAccepting == -1 && endMarker.fVal != 0) {
                        sd.fAccepting = endMarker.fVal;
                    }
                    if (endMarker.fLookAheadEnd) {
                        sd.fLookAhead = sd.fAccepting;
                    }
                }
            }
        }
    }
    
    void flagLookAheadStates() {
        final List<RBBINode> lookAheadNodes = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(lookAheadNodes, 4);
        for (int i = 0; i < lookAheadNodes.size(); ++i) {
            final RBBINode lookAheadNode = lookAheadNodes.get(i);
            for (int n = 0; n < this.fDStates.size(); ++n) {
                final RBBIStateDescriptor sd = this.fDStates.get(n);
                if (sd.fPositions.contains(lookAheadNode)) {
                    sd.fLookAhead = lookAheadNode.fVal;
                }
            }
        }
    }
    
    void flagTaggedStates() {
        final List<RBBINode> tagNodes = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(tagNodes, 5);
        for (int i = 0; i < tagNodes.size(); ++i) {
            final RBBINode tagNode = tagNodes.get(i);
            for (int n = 0; n < this.fDStates.size(); ++n) {
                final RBBIStateDescriptor sd = this.fDStates.get(n);
                if (sd.fPositions.contains(tagNode)) {
                    sd.fTagVals.add(tagNode.fVal);
                }
            }
        }
    }
    
    void mergeRuleStatusVals() {
        if (this.fRB.fRuleStatusVals.size() == 0) {
            this.fRB.fRuleStatusVals.add(1);
            this.fRB.fRuleStatusVals.add(0);
            final SortedSet<Integer> s0 = new TreeSet<Integer>();
            final Integer izero = 0;
            this.fRB.fStatusSets.put(s0, izero);
            final SortedSet<Integer> s2 = new TreeSet<Integer>();
            s2.add(izero);
            this.fRB.fStatusSets.put(s0, izero);
        }
        for (int n = 0; n < this.fDStates.size(); ++n) {
            final RBBIStateDescriptor sd = this.fDStates.get(n);
            final Set<Integer> statusVals = sd.fTagVals;
            Integer arrayIndexI = this.fRB.fStatusSets.get(statusVals);
            if (arrayIndexI == null) {
                arrayIndexI = this.fRB.fRuleStatusVals.size();
                this.fRB.fStatusSets.put(statusVals, arrayIndexI);
                this.fRB.fRuleStatusVals.add(statusVals.size());
                this.fRB.fRuleStatusVals.addAll(statusVals);
            }
            sd.fTagsIdx = arrayIndexI;
        }
    }
    
    void printPosSets(final RBBINode n) {
        if (n == null) {
            return;
        }
        RBBINode.printNode(n);
        System.out.print("         Nullable:  " + n.fNullable);
        System.out.print("         firstpos:  ");
        this.printSet(n.fFirstPosSet);
        System.out.print("         lastpos:   ");
        this.printSet(n.fLastPosSet);
        System.out.print("         followpos: ");
        this.printSet(n.fFollowPos);
        this.printPosSets(n.fLeftChild);
        this.printPosSets(n.fRightChild);
    }
    
    boolean findDuplCharClassFrom(final RBBIRuleBuilder.IntPair categories) {
        final int numStates = this.fDStates.size();
        final int numCols = this.fRB.fSetBuilder.getNumCharCategories();
        int table_base = 0;
        int table_dupl = 0;
        while (categories.first < numCols - 1) {
            categories.second = categories.first + 1;
            while (categories.second < numCols) {
                for (int state = 0; state < numStates; ++state) {
                    final RBBIStateDescriptor sd = this.fDStates.get(state);
                    table_base = sd.fDtran[categories.first];
                    table_dupl = sd.fDtran[categories.second];
                    if (table_base != table_dupl) {
                        break;
                    }
                }
                if (table_base == table_dupl) {
                    return true;
                }
                ++categories.second;
            }
            ++categories.first;
        }
        return false;
    }
    
    void removeColumn(final int column) {
        for (int numStates = this.fDStates.size(), state = 0; state < numStates; ++state) {
            final RBBIStateDescriptor sd = this.fDStates.get(state);
            assert column < sd.fDtran.length;
            final int[] newArray = Arrays.copyOf(sd.fDtran, sd.fDtran.length - 1);
            System.arraycopy(sd.fDtran, column + 1, newArray, column, newArray.length - column);
            sd.fDtran = newArray;
        }
    }
    
    boolean findDuplicateState(final RBBIRuleBuilder.IntPair states) {
        final int numStates = this.fDStates.size();
        final int numCols = this.fRB.fSetBuilder.getNumCharCategories();
        while (states.first < numStates - 1) {
            final RBBIStateDescriptor firstSD = this.fDStates.get(states.first);
            states.second = states.first + 1;
            while (states.second < numStates) {
                final RBBIStateDescriptor duplSD = this.fDStates.get(states.second);
                if (firstSD.fAccepting == duplSD.fAccepting && firstSD.fLookAhead == duplSD.fLookAhead) {
                    if (firstSD.fTagsIdx == duplSD.fTagsIdx) {
                        boolean rowsMatch = true;
                        for (int col = 0; col < numCols; ++col) {
                            final int firstVal = firstSD.fDtran[col];
                            final int duplVal = duplSD.fDtran[col];
                            if (firstVal != duplVal && ((firstVal != states.first && firstVal != states.second) || (duplVal != states.first && duplVal != states.second))) {
                                rowsMatch = false;
                                break;
                            }
                        }
                        if (rowsMatch) {
                            return true;
                        }
                    }
                }
                ++states.second;
            }
            ++states.first;
        }
        return false;
    }
    
    boolean findDuplicateSafeState(final RBBIRuleBuilder.IntPair states) {
        final int numStates = this.fSafeTable.size();
        while (states.first < numStates - 1) {
            final short[] firstRow = this.fSafeTable.get(states.first);
            states.second = states.first + 1;
            while (states.second < numStates) {
                final short[] duplRow = this.fSafeTable.get(states.second);
                boolean rowsMatch = true;
                for (int numCols = firstRow.length, col = 0; col < numCols; ++col) {
                    final int firstVal = firstRow[col];
                    final int duplVal = duplRow[col];
                    if (firstVal != duplVal && ((firstVal != states.first && firstVal != states.second) || (duplVal != states.first && duplVal != states.second))) {
                        rowsMatch = false;
                        break;
                    }
                }
                if (rowsMatch) {
                    return true;
                }
                ++states.second;
            }
            ++states.first;
        }
        return false;
    }
    
    void removeState(final RBBIRuleBuilder.IntPair duplStates) {
        final int keepState = duplStates.first;
        final int duplState = duplStates.second;
        assert keepState < duplState;
        assert duplState < this.fDStates.size();
        this.fDStates.remove(duplState);
        final int numStates = this.fDStates.size();
        final int numCols = this.fRB.fSetBuilder.getNumCharCategories();
        for (int state = 0; state < numStates; ++state) {
            final RBBIStateDescriptor sd = this.fDStates.get(state);
            for (int col = 0; col < numCols; ++col) {
                final int existingVal = sd.fDtran[col];
                int newVal;
                if ((newVal = existingVal) == duplState) {
                    newVal = keepState;
                }
                else if (existingVal > duplState) {
                    newVal = existingVal - 1;
                }
                sd.fDtran[col] = newVal;
            }
            if (sd.fAccepting == duplState) {
                sd.fAccepting = keepState;
            }
            else if (sd.fAccepting > duplState) {
                final RBBIStateDescriptor rbbiStateDescriptor = sd;
                --rbbiStateDescriptor.fAccepting;
            }
            if (sd.fLookAhead == duplState) {
                sd.fLookAhead = keepState;
            }
            else if (sd.fLookAhead > duplState) {
                final RBBIStateDescriptor rbbiStateDescriptor2 = sd;
                --rbbiStateDescriptor2.fLookAhead;
            }
        }
    }
    
    void removeSafeState(final RBBIRuleBuilder.IntPair duplStates) {
        final int keepState = duplStates.first;
        final int duplState = duplStates.second;
        assert keepState < duplState;
        assert duplState < this.fSafeTable.size();
        this.fSafeTable.remove(duplState);
        for (int numStates = this.fSafeTable.size(), state = 0; state < numStates; ++state) {
            final short[] row = this.fSafeTable.get(state);
            for (int col = 0; col < row.length; ++col) {
                final int existingVal = row[col];
                int newVal;
                if ((newVal = existingVal) == duplState) {
                    newVal = keepState;
                }
                else if (existingVal > duplState) {
                    newVal = existingVal - 1;
                }
                row[col] = (short)newVal;
            }
        }
    }
    
    void removeDuplicateStates() {
        final RBBIRuleBuilder.IntPair dupls = new RBBIRuleBuilder.IntPair(3, 0);
        while (this.findDuplicateState(dupls)) {
            this.removeState(dupls);
        }
    }
    
    int getTableSize() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return 0;
        }
        int size = 16;
        final int numRows = this.fDStates.size();
        final int numCols = this.fRB.fSetBuilder.getNumCharCategories();
        final int rowSize = 8 + 2 * numCols;
        size += numRows * rowSize;
        size = (size + 7 & 0xFFFFFFF8);
        return size;
    }
    
    RBBIDataWrapper.RBBIStateTable exportTable() {
        final RBBIDataWrapper.RBBIStateTable table = new RBBIDataWrapper.RBBIStateTable();
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return table;
        }
        Assert.assrt(this.fRB.fSetBuilder.getNumCharCategories() < 32767 && this.fDStates.size() < 32767);
        table.fNumStates = this.fDStates.size();
        final int rowLen = 4 + this.fRB.fSetBuilder.getNumCharCategories();
        final int tableSize = (this.getTableSize() - 16) / 2;
        table.fTable = new short[tableSize];
        table.fRowLen = rowLen * 2;
        if (this.fRB.fLookAheadHardBreak) {
            final RBBIDataWrapper.RBBIStateTable rbbiStateTable = table;
            rbbiStateTable.fFlags |= 0x1;
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            final RBBIDataWrapper.RBBIStateTable rbbiStateTable2 = table;
            rbbiStateTable2.fFlags |= 0x2;
        }
        final int numCharCategories = this.fRB.fSetBuilder.getNumCharCategories();
        for (int state = 0; state < table.fNumStates; ++state) {
            final RBBIStateDescriptor sd = this.fDStates.get(state);
            final int row = state * rowLen;
            Assert.assrt(-32768 < sd.fAccepting && sd.fAccepting <= 32767);
            Assert.assrt(-32768 < sd.fLookAhead && sd.fLookAhead <= 32767);
            table.fTable[row + 0] = (short)sd.fAccepting;
            table.fTable[row + 1] = (short)sd.fLookAhead;
            table.fTable[row + 2] = (short)sd.fTagsIdx;
            for (int col = 0; col < numCharCategories; ++col) {
                table.fTable[row + 4 + col] = (short)sd.fDtran[col];
            }
        }
        return table;
    }
    
    void buildSafeReverseTable() {
        final StringBuilder safePairs = new StringBuilder();
        final int numCharClasses = this.fRB.fSetBuilder.getNumCharCategories();
        final int numStates = this.fDStates.size();
        for (int c1 = 0; c1 < numCharClasses; ++c1) {
            for (int c2 = 0; c2 < numCharClasses; ++c2) {
                int wantedEndState = -1;
                int endState = 0;
                for (int startState = 1; startState < numStates; ++startState) {
                    final RBBIStateDescriptor startStateD = this.fDStates.get(startState);
                    final int s2 = startStateD.fDtran[c1];
                    final RBBIStateDescriptor s2StateD = this.fDStates.get(s2);
                    endState = s2StateD.fDtran[c2];
                    if (wantedEndState < 0) {
                        wantedEndState = endState;
                    }
                    else if (wantedEndState != endState) {
                        break;
                    }
                }
                if (wantedEndState == endState) {
                    safePairs.append((char)c1);
                    safePairs.append((char)c2);
                }
            }
        }
        assert this.fSafeTable == null;
        this.fSafeTable = new ArrayList<short[]>();
        for (int row = 0; row < numCharClasses + 2; ++row) {
            this.fSafeTable.add(new short[numCharClasses]);
        }
        final short[] startState2 = this.fSafeTable.get(1);
        for (int charClass = 0; charClass < numCharClasses; ++charClass) {
            startState2[charClass] = (short)(charClass + 2);
        }
        for (int row2 = 2; row2 < numCharClasses + 2; ++row2) {
            System.arraycopy(startState2, 0, this.fSafeTable.get(row2), 0, startState2.length);
        }
        for (int pairIdx = 0; pairIdx < safePairs.length(); pairIdx += 2) {
            final int c3 = safePairs.charAt(pairIdx);
            final int c4 = safePairs.charAt(pairIdx + 1);
            final short[] rowState = this.fSafeTable.get(c4 + 2);
            rowState[c3] = 0;
        }
        final RBBIRuleBuilder.IntPair states = new RBBIRuleBuilder.IntPair(1, 0);
        while (this.findDuplicateSafeState(states)) {
            this.removeSafeState(states);
        }
    }
    
    int getSafeTableSize() {
        if (this.fSafeTable == null) {
            return 0;
        }
        int size = 16;
        final int numRows = this.fSafeTable.size();
        final int numCols = this.fSafeTable.get(0).length;
        final int rowSize = 8 + 2 * numCols;
        size += numRows * rowSize;
        size = (size + 7 & 0xFFFFFFF8);
        return size;
    }
    
    RBBIDataWrapper.RBBIStateTable exportSafeTable() {
        final RBBIDataWrapper.RBBIStateTable table = new RBBIDataWrapper.RBBIStateTable();
        table.fNumStates = this.fSafeTable.size();
        final int numCharCategories = this.fSafeTable.get(0).length;
        final int rowLen = 4 + numCharCategories;
        final int tableSize = (this.getSafeTableSize() - 16) / 2;
        table.fTable = new short[tableSize];
        table.fRowLen = rowLen * 2;
        for (int state = 0; state < table.fNumStates; ++state) {
            final short[] rowArray = this.fSafeTable.get(state);
            final int row = state * rowLen;
            for (int col = 0; col < numCharCategories; ++col) {
                table.fTable[row + 4 + col] = rowArray[col];
            }
        }
        return table;
    }
    
    void printSet(final Collection<RBBINode> s) {
        for (final RBBINode n : s) {
            RBBINode.printInt(n.fSerialNum, 8);
        }
        System.out.println();
    }
    
    void printStates() {
        System.out.print("state |           i n p u t     s y m b o l s \n");
        System.out.print("      | Acc  LA    Tag");
        for (int c = 0; c < this.fRB.fSetBuilder.getNumCharCategories(); ++c) {
            RBBINode.printInt(c, 3);
        }
        System.out.print("\n");
        System.out.print("      |---------------");
        for (int c = 0; c < this.fRB.fSetBuilder.getNumCharCategories(); ++c) {
            System.out.print("---");
        }
        System.out.print("\n");
        for (int n = 0; n < this.fDStates.size(); ++n) {
            final RBBIStateDescriptor sd = this.fDStates.get(n);
            RBBINode.printInt(n, 5);
            System.out.print(" | ");
            RBBINode.printInt(sd.fAccepting, 3);
            RBBINode.printInt(sd.fLookAhead, 4);
            RBBINode.printInt(sd.fTagsIdx, 6);
            System.out.print(" ");
            for (int c = 0; c < this.fRB.fSetBuilder.getNumCharCategories(); ++c) {
                RBBINode.printInt(sd.fDtran[c], 3);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }
    
    void printReverseTable() {
        System.out.printf("    Safe Reverse Table \n", new Object[0]);
        if (this.fSafeTable == null) {
            System.out.printf("   --- nullptr ---\n", new Object[0]);
            return;
        }
        final int numCharCategories = this.fSafeTable.get(0).length;
        System.out.printf("state |           i n p u t     s y m b o l s \n", new Object[0]);
        System.out.printf("      | Acc  LA    Tag", new Object[0]);
        for (int c = 0; c < numCharCategories; ++c) {
            System.out.printf(" %2d", c);
        }
        System.out.printf("\n", new Object[0]);
        System.out.printf("      |---------------", new Object[0]);
        for (int c = 0; c < numCharCategories; ++c) {
            System.out.printf("---", new Object[0]);
        }
        System.out.printf("\n", new Object[0]);
        for (int n = 0; n < this.fSafeTable.size(); ++n) {
            final short[] rowArray = this.fSafeTable.get(n);
            System.out.printf("  %3d | ", n);
            System.out.printf("%3d %3d %5d ", 0, 0, 0);
            for (int c = 0; c < numCharCategories; ++c) {
                System.out.printf(" %2d", rowArray[c]);
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n", new Object[0]);
    }
    
    void printRuleStatusTable() {
        int thisRecord = 0;
        int nextRecord = 0;
        final List<Integer> tbl = this.fRB.fRuleStatusVals;
        System.out.print("index |  tags \n");
        System.out.print("-------------------\n");
        while (nextRecord < tbl.size()) {
            thisRecord = nextRecord;
            nextRecord = thisRecord + tbl.get(thisRecord) + 1;
            RBBINode.printInt(thisRecord, 7);
            for (int i = thisRecord + 1; i < nextRecord; ++i) {
                final int val = tbl.get(i);
                RBBINode.printInt(val, 7);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }
    
    static class RBBIStateDescriptor
    {
        boolean fMarked;
        int fAccepting;
        int fLookAhead;
        SortedSet<Integer> fTagVals;
        int fTagsIdx;
        Set<RBBINode> fPositions;
        int[] fDtran;
        
        RBBIStateDescriptor(final int maxInputSymbol) {
            this.fTagVals = new TreeSet<Integer>();
            this.fPositions = new HashSet<RBBINode>();
            this.fDtran = new int[maxInputSymbol + 1];
        }
    }
}
