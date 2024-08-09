/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.RBBIDataWrapper;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.RBBINode;
import com.ibm.icu.text.RBBIRuleBuilder;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class RBBITableBuilder {
    private RBBIRuleBuilder fRB;
    private int fRootIx;
    private List<RBBIStateDescriptor> fDStates;
    private List<short[]> fSafeTable;
    static final boolean $assertionsDisabled = !RBBITableBuilder.class.desiredAssertionStatus();

    RBBITableBuilder(RBBIRuleBuilder rBBIRuleBuilder, int n) {
        this.fRootIx = n;
        this.fRB = rBBIRuleBuilder;
        this.fDStates = new ArrayList<RBBIStateDescriptor>();
    }

    void buildForwardTable() {
        RBBINode rBBINode;
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return;
        }
        this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(false);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            RBBINode rBBINode2;
            rBBINode = new RBBINode(8);
            rBBINode.fLeftChild = rBBINode2 = new RBBINode(3);
            rBBINode.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            rBBINode2.fParent = rBBINode;
            rBBINode2.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = rBBINode;
        }
        rBBINode = new RBBINode(8);
        rBBINode.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
        this.fRB.fTreeRoots[this.fRootIx].fParent = rBBINode;
        rBBINode.fRightChild = new RBBINode(6);
        rBBINode.fRightChild.fParent = rBBINode;
        this.fRB.fTreeRoots[this.fRootIx] = rBBINode;
        this.fRB.fTreeRoots[this.fRootIx].flattenSets();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(false);
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

    void calcNullable(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 0 || rBBINode.fType == 6) {
            rBBINode.fNullable = false;
            return;
        }
        if (rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fNullable = true;
            return;
        }
        this.calcNullable(rBBINode.fLeftChild);
        this.calcNullable(rBBINode.fRightChild);
        rBBINode.fNullable = rBBINode.fType == 9 ? rBBINode.fLeftChild.fNullable || rBBINode.fRightChild.fNullable : (rBBINode.fType == 8 ? rBBINode.fLeftChild.fNullable && rBBINode.fRightChild.fNullable : rBBINode.fType == 10 || rBBINode.fType == 12);
    }

    void calcFirstPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 3 || rBBINode.fType == 6 || rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fFirstPosSet.add(rBBINode);
            return;
        }
        this.calcFirstPos(rBBINode.fLeftChild);
        this.calcFirstPos(rBBINode.fRightChild);
        if (rBBINode.fType == 9) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
            rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
        } else if (rBBINode.fType == 8) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
            if (rBBINode.fLeftChild.fNullable) {
                rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
            }
        } else if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
            rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
        }
    }

    void calcLastPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType == 3 || rBBINode.fType == 6 || rBBINode.fType == 4 || rBBINode.fType == 5) {
            rBBINode.fLastPosSet.add(rBBINode);
            return;
        }
        this.calcLastPos(rBBINode.fLeftChild);
        this.calcLastPos(rBBINode.fRightChild);
        if (rBBINode.fType == 9) {
            rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
            rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
        } else if (rBBINode.fType == 8) {
            rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
            if (rBBINode.fRightChild.fNullable) {
                rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
            }
        } else if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
            rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
        }
    }

    void calcFollowPos(RBBINode rBBINode) {
        if (rBBINode == null || rBBINode.fType == 3 || rBBINode.fType == 6) {
            return;
        }
        this.calcFollowPos(rBBINode.fLeftChild);
        this.calcFollowPos(rBBINode.fRightChild);
        if (rBBINode.fType == 8) {
            for (RBBINode rBBINode2 : rBBINode.fLeftChild.fLastPosSet) {
                rBBINode2.fFollowPos.addAll(rBBINode.fRightChild.fFirstPosSet);
            }
        }
        if (rBBINode.fType == 10 || rBBINode.fType == 11) {
            for (RBBINode rBBINode2 : rBBINode.fLastPosSet) {
                rBBINode2.fFollowPos.addAll(rBBINode.fFirstPosSet);
            }
        }
    }

    void addRuleRootNodes(List<RBBINode> list, RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fRuleRoot) {
            list.add(rBBINode);
            return;
        }
        this.addRuleRootNodes(list, rBBINode.fLeftChild);
        this.addRuleRootNodes(list, rBBINode.fRightChild);
    }

    void calcChainedFollowPos(RBBINode rBBINode) {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        ArrayList<RBBINode> arrayList2 = new ArrayList<RBBINode>();
        rBBINode.findNodes(arrayList, 6);
        rBBINode.findNodes(arrayList2, 3);
        ArrayList<RBBINode> arrayList3 = new ArrayList<RBBINode>();
        this.addRuleRootNodes(arrayList3, rBBINode);
        HashSet<RBBINode> hashSet = new HashSet<RBBINode>();
        for (RBBINode rBBINode2 : arrayList3) {
            if (!rBBINode2.fChainIn) continue;
            hashSet.addAll(rBBINode2.fFirstPosSet);
        }
        for (RBBINode rBBINode2 : arrayList2) {
            int n;
            int n2;
            RBBINode rBBINode3 = null;
            for (RBBINode rBBINode4 : arrayList) {
                if (!rBBINode2.fFollowPos.contains(rBBINode4)) continue;
                rBBINode3 = rBBINode2;
                break;
            }
            if (rBBINode3 == null || this.fRB.fLBCMNoChain && (n2 = this.fRB.fSetBuilder.getFirstChar(rBBINode3.fVal)) != -1 && (n = UCharacter.getIntPropertyValue(n2, 4104)) == 9) continue;
            for (RBBINode rBBINode4 : hashSet) {
                if (rBBINode4.fType != 3 || rBBINode3.fVal != rBBINode4.fVal) continue;
                rBBINode3.fFollowPos.addAll(rBBINode4.fFollowPos);
            }
        }
    }

    void bofFixup() {
        RBBINode rBBINode = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
        Assert.assrt(rBBINode.fType == 3);
        Assert.assrt(rBBINode.fVal == 2);
        Set<RBBINode> set = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet;
        for (RBBINode rBBINode2 : set) {
            if (rBBINode2.fType != 3 || rBBINode2.fVal != rBBINode.fVal) continue;
            rBBINode.fFollowPos.addAll(rBBINode2.fFollowPos);
        }
    }

    void buildStateTable() {
        int n = this.fRB.fSetBuilder.getNumCharCategories() - 1;
        RBBIStateDescriptor rBBIStateDescriptor = new RBBIStateDescriptor(n);
        this.fDStates.add(rBBIStateDescriptor);
        RBBIStateDescriptor rBBIStateDescriptor2 = new RBBIStateDescriptor(n);
        rBBIStateDescriptor2.fPositions.addAll(this.fRB.fTreeRoots[this.fRootIx].fFirstPosSet);
        this.fDStates.add(rBBIStateDescriptor2);
        block0: while (true) {
            RBBIStateDescriptor rBBIStateDescriptor3 = null;
            for (int i = 1; i < this.fDStates.size(); ++i) {
                RBBIStateDescriptor rBBIStateDescriptor4 = this.fDStates.get(i);
                if (rBBIStateDescriptor4.fMarked) continue;
                rBBIStateDescriptor3 = rBBIStateDescriptor4;
                break;
            }
            if (rBBIStateDescriptor3 == null) break;
            rBBIStateDescriptor3.fMarked = true;
            int n2 = 1;
            while (true) {
                if (n2 > n) continue block0;
                Set<RBBINode> set = null;
                for (RBBINode rBBINode : rBBIStateDescriptor3.fPositions) {
                    if (rBBINode.fType != 3 || rBBINode.fVal != n2) continue;
                    if (set == null) {
                        set = new HashSet<RBBINode>();
                    }
                    set.addAll(rBBINode.fFollowPos);
                }
                int n3 = 0;
                boolean bl = false;
                if (set != null) {
                    RBBIStateDescriptor rBBIStateDescriptor5;
                    Assert.assrt(set.size() > 0);
                    for (int i = 0; i < this.fDStates.size(); ++i) {
                        rBBIStateDescriptor5 = this.fDStates.get(i);
                        if (!set.equals(rBBIStateDescriptor5.fPositions)) continue;
                        set = rBBIStateDescriptor5.fPositions;
                        n3 = i;
                        bl = true;
                        break;
                    }
                    if (!bl) {
                        rBBIStateDescriptor5 = new RBBIStateDescriptor(n);
                        rBBIStateDescriptor5.fPositions = set;
                        this.fDStates.add(rBBIStateDescriptor5);
                        n3 = this.fDStates.size() - 1;
                    }
                    rBBIStateDescriptor3.fDtran[n2] = n3;
                }
                ++n2;
            }
            break;
        }
    }

    void flagAcceptingStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 6);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                if (rBBIStateDescriptor.fAccepting == 0) {
                    rBBIStateDescriptor.fAccepting = rBBINode.fVal;
                    if (rBBIStateDescriptor.fAccepting == 0) {
                        rBBIStateDescriptor.fAccepting = -1;
                    }
                }
                if (rBBIStateDescriptor.fAccepting == -1 && rBBINode.fVal != 0) {
                    rBBIStateDescriptor.fAccepting = rBBINode.fVal;
                }
                if (!rBBINode.fLookAheadEnd) continue;
                rBBIStateDescriptor.fLookAhead = rBBIStateDescriptor.fAccepting;
            }
        }
    }

    void flagLookAheadStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 4);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                rBBIStateDescriptor.fLookAhead = rBBINode.fVal;
            }
        }
    }

    void flagTaggedStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 5);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                rBBIStateDescriptor.fTagVals.add(rBBINode.fVal);
            }
        }
    }

    void mergeRuleStatusVals() {
        Serializable serializable;
        SortedSet<Integer> sortedSet;
        Object object;
        if (this.fRB.fRuleStatusVals.size() == 0) {
            this.fRB.fRuleStatusVals.add(1);
            this.fRB.fRuleStatusVals.add(0);
            object = new TreeSet();
            sortedSet = 0;
            this.fRB.fStatusSets.put((Set<Integer>)object, (Integer)((Object)sortedSet));
            serializable = new TreeSet();
            serializable.add(sortedSet);
            this.fRB.fStatusSets.put((Set<Integer>)object, (Integer)((Object)sortedSet));
        }
        for (int i = 0; i < this.fDStates.size(); ++i) {
            object = this.fDStates.get(i);
            sortedSet = ((RBBIStateDescriptor)object).fTagVals;
            serializable = this.fRB.fStatusSets.get(sortedSet);
            if (serializable == null) {
                serializable = Integer.valueOf(this.fRB.fRuleStatusVals.size());
                this.fRB.fStatusSets.put((Set<Integer>)sortedSet, (Integer)serializable);
                this.fRB.fRuleStatusVals.add(sortedSet.size());
                this.fRB.fRuleStatusVals.addAll((Collection<Integer>)sortedSet);
            }
            ((RBBIStateDescriptor)object).fTagsIdx = (Integer)serializable;
        }
    }

    void printPosSets(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        RBBINode.printNode(rBBINode);
        System.out.print("         Nullable:  " + rBBINode.fNullable);
        System.out.print("         firstpos:  ");
        this.printSet(rBBINode.fFirstPosSet);
        System.out.print("         lastpos:   ");
        this.printSet(rBBINode.fLastPosSet);
        System.out.print("         followpos: ");
        this.printSet(rBBINode.fFollowPos);
        this.printPosSets(rBBINode.fLeftChild);
        this.printPosSets(rBBINode.fRightChild);
    }

    boolean findDuplCharClassFrom(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fDStates.size();
        int n2 = this.fRB.fSetBuilder.getNumCharCategories();
        int n3 = 0;
        int n4 = 0;
        while (intPair.first < n2 - 1) {
            intPair.second = intPair.first + 1;
            while (intPair.second < n2) {
                for (int i = 0; i < n; ++i) {
                    RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
                    n3 = rBBIStateDescriptor.fDtran[intPair.first];
                    n4 = rBBIStateDescriptor.fDtran[intPair.second];
                    if (n3 != n4) break;
                }
                if (n3 == n4) {
                    return false;
                }
                ++intPair.second;
            }
            ++intPair.first;
        }
        return true;
    }

    void removeColumn(int n) {
        int n2 = this.fDStates.size();
        for (int i = 0; i < n2; ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            if (!$assertionsDisabled && n >= rBBIStateDescriptor.fDtran.length) {
                throw new AssertionError();
            }
            int[] nArray = Arrays.copyOf(rBBIStateDescriptor.fDtran, rBBIStateDescriptor.fDtran.length - 1);
            System.arraycopy(rBBIStateDescriptor.fDtran, n + 1, nArray, n, nArray.length - n);
            rBBIStateDescriptor.fDtran = nArray;
        }
    }

    boolean findDuplicateState(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fDStates.size();
        int n2 = this.fRB.fSetBuilder.getNumCharCategories();
        while (intPair.first < n - 1) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(intPair.first);
            intPair.second = intPair.first + 1;
            while (intPair.second < n) {
                RBBIStateDescriptor rBBIStateDescriptor2 = this.fDStates.get(intPair.second);
                if (rBBIStateDescriptor.fAccepting == rBBIStateDescriptor2.fAccepting && rBBIStateDescriptor.fLookAhead == rBBIStateDescriptor2.fLookAhead && rBBIStateDescriptor.fTagsIdx == rBBIStateDescriptor2.fTagsIdx) {
                    boolean bl = true;
                    for (int i = 0; i < n2; ++i) {
                        int n3 = rBBIStateDescriptor.fDtran[i];
                        int n4 = rBBIStateDescriptor2.fDtran[i];
                        if (n3 == n4 || (n3 == intPair.first || n3 == intPair.second) && (n4 == intPair.first || n4 == intPair.second)) continue;
                        bl = false;
                        break;
                    }
                    if (bl) {
                        return false;
                    }
                }
                ++intPair.second;
            }
            ++intPair.first;
        }
        return true;
    }

    boolean findDuplicateSafeState(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fSafeTable.size();
        while (intPair.first < n - 1) {
            short[] sArray = this.fSafeTable.get(intPair.first);
            intPair.second = intPair.first + 1;
            while (intPair.second < n) {
                short[] sArray2 = this.fSafeTable.get(intPair.second);
                boolean bl = true;
                int n2 = sArray.length;
                for (int i = 0; i < n2; ++i) {
                    short s = sArray[i];
                    short s2 = sArray2[i];
                    if (s == s2 || (s == intPair.first || s == intPair.second) && (s2 == intPair.first || s2 == intPair.second)) continue;
                    bl = false;
                    break;
                }
                if (bl) {
                    return false;
                }
                ++intPair.second;
            }
            ++intPair.first;
        }
        return true;
    }

    void removeState(RBBIRuleBuilder.IntPair intPair) {
        int n = intPair.first;
        int n2 = intPair.second;
        if (!$assertionsDisabled && n >= n2) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n2 >= this.fDStates.size()) {
            throw new AssertionError();
        }
        this.fDStates.remove(n2);
        int n3 = this.fDStates.size();
        int n4 = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i = 0; i < n3; ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            for (int j = 0; j < n4; ++j) {
                int n5;
                int n6 = n5 = rBBIStateDescriptor.fDtran[j];
                if (n5 == n2) {
                    n6 = n;
                } else if (n5 > n2) {
                    n6 = n5 - 1;
                }
                rBBIStateDescriptor.fDtran[j] = n6;
            }
            if (rBBIStateDescriptor.fAccepting == n2) {
                rBBIStateDescriptor.fAccepting = n;
            } else if (rBBIStateDescriptor.fAccepting > n2) {
                --rBBIStateDescriptor.fAccepting;
            }
            if (rBBIStateDescriptor.fLookAhead == n2) {
                rBBIStateDescriptor.fLookAhead = n;
                continue;
            }
            if (rBBIStateDescriptor.fLookAhead <= n2) continue;
            --rBBIStateDescriptor.fLookAhead;
        }
    }

    void removeSafeState(RBBIRuleBuilder.IntPair intPair) {
        int n = intPair.first;
        int n2 = intPair.second;
        if (!$assertionsDisabled && n >= n2) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n2 >= this.fSafeTable.size()) {
            throw new AssertionError();
        }
        this.fSafeTable.remove(n2);
        int n3 = this.fSafeTable.size();
        for (int i = 0; i < n3; ++i) {
            short[] sArray = this.fSafeTable.get(i);
            for (int j = 0; j < sArray.length; ++j) {
                int n4;
                int n5 = n4 = sArray[j];
                if (n4 == n2) {
                    n5 = n;
                } else if (n4 > n2) {
                    n5 = n4 - 1;
                }
                sArray[j] = (short)n5;
            }
        }
    }

    int removeDuplicateStates() {
        RBBIRuleBuilder.IntPair intPair = new RBBIRuleBuilder.IntPair(3, 0);
        int n = 0;
        while (this.findDuplicateState(intPair)) {
            this.removeState(intPair);
            ++n;
        }
        return n;
    }

    int getTableSize() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return 1;
        }
        int n = 16;
        int n2 = this.fDStates.size();
        int n3 = this.fRB.fSetBuilder.getNumCharCategories();
        int n4 = 8 + 2 * n3;
        n += n2 * n4;
        n = n + 7 & 0xFFFFFFF8;
        return n;
    }

    RBBIDataWrapper.RBBIStateTable exportTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return rBBIStateTable;
        }
        Assert.assrt(this.fRB.fSetBuilder.getNumCharCategories() < Short.MAX_VALUE && this.fDStates.size() < Short.MAX_VALUE);
        rBBIStateTable.fNumStates = this.fDStates.size();
        int n = 4 + this.fRB.fSetBuilder.getNumCharCategories();
        int n2 = (this.getTableSize() - 16) / 2;
        rBBIStateTable.fTable = new short[n2];
        rBBIStateTable.fRowLen = n * 2;
        if (this.fRB.fLookAheadHardBreak) {
            rBBIStateTable.fFlags |= 1;
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            rBBIStateTable.fFlags |= 2;
        }
        int n3 = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i = 0; i < rBBIStateTable.fNumStates; ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            int n4 = i * n;
            Assert.assrt(Short.MIN_VALUE < rBBIStateDescriptor.fAccepting && rBBIStateDescriptor.fAccepting <= Short.MAX_VALUE);
            Assert.assrt(Short.MIN_VALUE < rBBIStateDescriptor.fLookAhead && rBBIStateDescriptor.fLookAhead <= Short.MAX_VALUE);
            rBBIStateTable.fTable[n4 + 0] = (short)rBBIStateDescriptor.fAccepting;
            rBBIStateTable.fTable[n4 + 1] = (short)rBBIStateDescriptor.fLookAhead;
            rBBIStateTable.fTable[n4 + 2] = (short)rBBIStateDescriptor.fTagsIdx;
            for (int j = 0; j < n3; ++j) {
                rBBIStateTable.fTable[n4 + 4 + j] = (short)rBBIStateDescriptor.fDtran[j];
            }
        }
        return rBBIStateTable;
    }

    void buildSafeReverseTable() {
        int n;
        int n2;
        int n3;
        int n4;
        StringBuilder stringBuilder = new StringBuilder();
        int n5 = this.fRB.fSetBuilder.getNumCharCategories();
        int n6 = this.fDStates.size();
        for (n4 = 0; n4 < n5; ++n4) {
            for (n3 = 0; n3 < n5; ++n3) {
                n2 = -1;
                n = 0;
                for (int i = 1; i < n6; ++i) {
                    RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
                    int n7 = rBBIStateDescriptor.fDtran[n4];
                    RBBIStateDescriptor rBBIStateDescriptor2 = this.fDStates.get(n7);
                    n = rBBIStateDescriptor2.fDtran[n3];
                    if (n2 < 0) {
                        n2 = n;
                        continue;
                    }
                    if (n2 != n) break;
                }
                if (n2 != n) continue;
                stringBuilder.append((char)n4);
                stringBuilder.append((char)n3);
            }
        }
        if (!$assertionsDisabled && this.fSafeTable != null) {
            throw new AssertionError();
        }
        this.fSafeTable = new ArrayList<short[]>();
        for (n4 = 0; n4 < n5 + 2; ++n4) {
            this.fSafeTable.add(new short[n5]);
        }
        short[] sArray = this.fSafeTable.get(1);
        for (n3 = 0; n3 < n5; ++n3) {
            sArray[n3] = (short)(n3 + 2);
        }
        for (n3 = 2; n3 < n5 + 2; ++n3) {
            System.arraycopy(sArray, 0, this.fSafeTable.get(n3), 0, sArray.length);
        }
        for (n3 = 0; n3 < stringBuilder.length(); n3 += 2) {
            n2 = stringBuilder.charAt(n3);
            n = stringBuilder.charAt(n3 + 1);
            short[] sArray2 = this.fSafeTable.get(n + 2);
            sArray2[n2] = 0;
        }
        RBBIRuleBuilder.IntPair intPair = new RBBIRuleBuilder.IntPair(1, 0);
        while (this.findDuplicateSafeState(intPair)) {
            this.removeSafeState(intPair);
        }
    }

    int getSafeTableSize() {
        if (this.fSafeTable == null) {
            return 1;
        }
        int n = 16;
        int n2 = this.fSafeTable.size();
        int n3 = this.fSafeTable.get(0).length;
        int n4 = 8 + 2 * n3;
        n += n2 * n4;
        n = n + 7 & 0xFFFFFFF8;
        return n;
    }

    RBBIDataWrapper.RBBIStateTable exportSafeTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        rBBIStateTable.fNumStates = this.fSafeTable.size();
        int n = this.fSafeTable.get(0).length;
        int n2 = 4 + n;
        int n3 = (this.getSafeTableSize() - 16) / 2;
        rBBIStateTable.fTable = new short[n3];
        rBBIStateTable.fRowLen = n2 * 2;
        for (int i = 0; i < rBBIStateTable.fNumStates; ++i) {
            short[] sArray = this.fSafeTable.get(i);
            int n4 = i * n2;
            for (int j = 0; j < n; ++j) {
                rBBIStateTable.fTable[n4 + 4 + j] = sArray[j];
            }
        }
        return rBBIStateTable;
    }

    void printSet(Collection<RBBINode> collection) {
        for (RBBINode rBBINode : collection) {
            RBBINode.printInt(rBBINode.fSerialNum, 8);
        }
        System.out.println();
    }

    void printStates() {
        int n;
        System.out.print("state |           i n p u t     s y m b o l s \n");
        System.out.print("      | Acc  LA    Tag");
        for (n = 0; n < this.fRB.fSetBuilder.getNumCharCategories(); ++n) {
            RBBINode.printInt(n, 3);
        }
        System.out.print("\n");
        System.out.print("      |---------------");
        for (n = 0; n < this.fRB.fSetBuilder.getNumCharCategories(); ++n) {
            System.out.print("---");
        }
        System.out.print("\n");
        for (int i = 0; i < this.fDStates.size(); ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            RBBINode.printInt(i, 5);
            System.out.print(" | ");
            RBBINode.printInt(rBBIStateDescriptor.fAccepting, 3);
            RBBINode.printInt(rBBIStateDescriptor.fLookAhead, 4);
            RBBINode.printInt(rBBIStateDescriptor.fTagsIdx, 6);
            System.out.print(" ");
            for (n = 0; n < this.fRB.fSetBuilder.getNumCharCategories(); ++n) {
                RBBINode.printInt(rBBIStateDescriptor.fDtran[n], 3);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    void printReverseTable() {
        int n;
        System.out.printf("    Safe Reverse Table \n", new Object[0]);
        if (this.fSafeTable == null) {
            System.out.printf("   --- nullptr ---\n", new Object[0]);
            return;
        }
        int n2 = this.fSafeTable.get(0).length;
        System.out.printf("state |           i n p u t     s y m b o l s \n", new Object[0]);
        System.out.printf("      | Acc  LA    Tag", new Object[0]);
        for (n = 0; n < n2; ++n) {
            System.out.printf(" %2d", n);
        }
        System.out.printf("\n", new Object[0]);
        System.out.printf("      |---------------", new Object[0]);
        for (n = 0; n < n2; ++n) {
            System.out.printf("---", new Object[0]);
        }
        System.out.printf("\n", new Object[0]);
        for (int i = 0; i < this.fSafeTable.size(); ++i) {
            short[] sArray = this.fSafeTable.get(i);
            System.out.printf("  %3d | ", i);
            System.out.printf("%3d %3d %5d ", 0, 0, 0);
            for (n = 0; n < n2; ++n) {
                System.out.printf(" %2d", sArray[n]);
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n", new Object[0]);
    }

    void printRuleStatusTable() {
        int n = 0;
        int n2 = 0;
        List<Integer> list = this.fRB.fRuleStatusVals;
        System.out.print("index |  tags \n");
        System.out.print("-------------------\n");
        while (n2 < list.size()) {
            n = n2;
            n2 = n + list.get(n) + 1;
            RBBINode.printInt(n, 7);
            for (int i = n + 1; i < n2; ++i) {
                int n3 = list.get(i);
                RBBINode.printInt(n3, 7);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    static class RBBIStateDescriptor {
        boolean fMarked;
        int fAccepting;
        int fLookAhead;
        SortedSet<Integer> fTagVals = new TreeSet<Integer>();
        int fTagsIdx;
        Set<RBBINode> fPositions = new HashSet<RBBINode>();
        int[] fDtran;

        RBBIStateDescriptor(int n) {
            this.fDtran = new int[n + 1];
        }
    }
}

