/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StringTrieBuilder {
    private State state = State.ADDING;
    @Deprecated
    protected StringBuilder strings = new StringBuilder();
    private Node root;
    private HashMap<Node, Node> nodes = new HashMap();
    private ValueNode lookupFinalValueNode = new ValueNode();
    static final boolean $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();

    @Deprecated
    protected StringTrieBuilder() {
    }

    @Deprecated
    protected void addImpl(CharSequence charSequence, int n) {
        if (this.state != State.ADDING) {
            throw new IllegalStateException("Cannot add (string, value) pairs after build().");
        }
        if (charSequence.length() > 65535) {
            throw new IndexOutOfBoundsException("The maximum string length is 0xffff.");
        }
        this.root = this.root == null ? this.createSuffixNode(charSequence, 0, n) : this.root.add(this, charSequence, 0, n);
    }

    @Deprecated
    protected final void buildImpl(Option option) {
        switch (1.$SwitchMap$com$ibm$icu$util$StringTrieBuilder$State[this.state.ordinal()]) {
            case 1: {
                if (this.root == null) {
                    throw new IndexOutOfBoundsException("No (string, value) pairs were added.");
                }
                if (option == Option.FAST) {
                    this.state = State.BUILDING_FAST;
                    break;
                }
                this.state = State.BUILDING_SMALL;
                break;
            }
            case 2: 
            case 3: {
                throw new IllegalStateException("Builder failed and must be clear()ed.");
            }
            case 4: {
                return;
            }
        }
        this.root = this.root.register(this);
        this.root.markRightEdgesFirst(-1);
        this.root.write(this);
        this.state = State.BUILT;
    }

    @Deprecated
    protected void clearImpl() {
        this.strings.setLength(0);
        this.nodes.clear();
        this.root = null;
        this.state = State.ADDING;
    }

    private final Node registerNode(Node node) {
        if (this.state == State.BUILDING_FAST) {
            return node;
        }
        Node node2 = this.nodes.get(node);
        if (node2 != null) {
            return node2;
        }
        node2 = this.nodes.put(node, node);
        if (!$assertionsDisabled && node2 != null) {
            throw new AssertionError();
        }
        return node;
    }

    private final ValueNode registerFinalValue(int n) {
        ValueNode.access$000(this.lookupFinalValueNode, n);
        Node node = this.nodes.get(this.lookupFinalValueNode);
        if (node != null) {
            return (ValueNode)node;
        }
        ValueNode valueNode = new ValueNode(n);
        node = this.nodes.put(valueNode, valueNode);
        if (!$assertionsDisabled && node != null) {
            throw new AssertionError();
        }
        return valueNode;
    }

    private ValueNode createSuffixNode(CharSequence charSequence, int n, int n2) {
        ValueNode valueNode = this.registerFinalValue(n2);
        if (n < charSequence.length()) {
            int n3 = this.strings.length();
            this.strings.append(charSequence, n, charSequence.length());
            valueNode = new LinearMatchNode(this.strings, n3, charSequence.length() - n, valueNode);
        }
        return valueNode;
    }

    @Deprecated
    protected abstract boolean matchNodesCanHaveValues();

    @Deprecated
    protected abstract int getMaxBranchLinearSubNodeLength();

    @Deprecated
    protected abstract int getMinLinearMatch();

    @Deprecated
    protected abstract int getMaxLinearMatchLength();

    @Deprecated
    protected abstract int write(int var1);

    @Deprecated
    protected abstract int write(int var1, int var2);

    @Deprecated
    protected abstract int writeValueAndFinal(int var1, boolean var2);

    @Deprecated
    protected abstract int writeValueAndType(boolean var1, int var2, int var3);

    @Deprecated
    protected abstract int writeDeltaTo(int var1);

    static ValueNode access$100(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
        return stringTrieBuilder.createSuffixNode(charSequence, n, n2);
    }

    static Node access$200(StringTrieBuilder stringTrieBuilder, Node node) {
        return stringTrieBuilder.registerNode(node);
    }

    private static enum State {
        ADDING,
        BUILDING_FAST,
        BUILDING_SMALL,
        BUILT;

    }

    private static final class BranchHeadNode
    extends ValueNode {
        private int length;
        private Node next;

        public BranchHeadNode(int n, Node node) {
            this.length = n;
            this.next = node;
        }

        @Override
        public int hashCode() {
            return (0xECCCCBE + this.length) * 37 + this.next.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            BranchHeadNode branchHeadNode = (BranchHeadNode)object;
            return this.length == branchHeadNode.length && this.next == branchHeadNode.next;
        }

        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.offset = n = this.next.markRightEdgesFirst(n);
            }
            return n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            if (this.length <= stringTrieBuilder.getMinLinearMatch()) {
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, this.length - 1);
            } else {
                stringTrieBuilder.write(this.length - 1);
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, 1);
            }
        }
    }

    private static final class SplitBranchNode
    extends BranchNode {
        private char unit;
        private Node lessThan;
        private Node greaterOrEqual;
        static final boolean $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();

        public SplitBranchNode(char c, Node node, Node node2) {
            this.hash = ((206918985 + c) * 37 + node.hashCode()) * 37 + node2.hashCode();
            this.unit = c;
            this.lessThan = node;
            this.greaterOrEqual = node2;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            SplitBranchNode splitBranchNode = (SplitBranchNode)object;
            return this.unit == splitBranchNode.unit && this.lessThan == splitBranchNode.lessThan && this.greaterOrEqual == splitBranchNode.greaterOrEqual;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.firstEdgeNumber = n;
                n = this.greaterOrEqual.markRightEdgesFirst(n);
                this.offset = n = this.lessThan.markRightEdgesFirst(n - 1);
            }
            return n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.lessThan.writeUnlessInsideRightEdge(this.firstEdgeNumber, this.greaterOrEqual.getOffset(), stringTrieBuilder);
            this.greaterOrEqual.write(stringTrieBuilder);
            if (!$assertionsDisabled && this.lessThan.getOffset() <= 0) {
                throw new AssertionError();
            }
            stringTrieBuilder.writeDeltaTo(this.lessThan.getOffset());
            this.offset = stringTrieBuilder.write(this.unit);
        }
    }

    private static final class ListBranchNode
    extends BranchNode {
        private Node[] equal;
        private int length;
        private int[] values;
        private char[] units;
        static final boolean $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();

        public ListBranchNode(int n) {
            this.hash = 0x9DDDDD4 + n;
            this.equal = new Node[n];
            this.values = new int[n];
            this.units = new char[n];
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            ListBranchNode listBranchNode = (ListBranchNode)object;
            for (int i = 0; i < this.length; ++i) {
                if (this.units[i] == listBranchNode.units[i] && this.values[i] == listBranchNode.values[i] && this.equal[i] == listBranchNode.equal[i]) continue;
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.firstEdgeNumber = n;
                int n2 = 0;
                int n3 = this.length;
                do {
                    Node node;
                    if ((node = this.equal[--n3]) != null) {
                        n = node.markRightEdgesFirst(n - n2);
                    }
                    n2 = 1;
                } while (n3 > 0);
                this.offset = n;
            }
            return n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            int n;
            int n2 = this.length - 1;
            Node node = this.equal[n2];
            int n3 = n = node == null ? this.firstEdgeNumber : node.getOffset();
            do {
                if (this.equal[--n2] == null) continue;
                this.equal[n2].writeUnlessInsideRightEdge(this.firstEdgeNumber, n, stringTrieBuilder);
            } while (n2 > 0);
            n2 = this.length - 1;
            if (node == null) {
                stringTrieBuilder.writeValueAndFinal(this.values[n2], false);
            } else {
                node.write(stringTrieBuilder);
            }
            this.offset = stringTrieBuilder.write(this.units[n2]);
            while (--n2 >= 0) {
                boolean bl;
                int n4;
                if (this.equal[n2] == null) {
                    n4 = this.values[n2];
                    bl = true;
                } else {
                    if (!$assertionsDisabled && this.equal[n2].getOffset() <= 0) {
                        throw new AssertionError();
                    }
                    n4 = this.offset - this.equal[n2].getOffset();
                    bl = false;
                }
                stringTrieBuilder.writeValueAndFinal(n4, bl);
                this.offset = stringTrieBuilder.write(this.units[n2]);
            }
        }

        public void add(int n, int n2) {
            this.units[this.length] = (char)n;
            this.equal[this.length] = null;
            this.values[this.length] = n2;
            ++this.length;
            this.hash = (this.hash * 37 + n) * 37 + n2;
        }

        public void add(int n, Node node) {
            this.units[this.length] = (char)n;
            this.equal[this.length] = node;
            this.values[this.length] = 0;
            ++this.length;
            this.hash = (this.hash * 37 + n) * 37 + node.hashCode();
        }
    }

    private static abstract class BranchNode
    extends Node {
        protected int hash;
        protected int firstEdgeNumber;

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

    private static final class DynamicBranchNode
    extends ValueNode {
        private StringBuilder chars = new StringBuilder();
        private ArrayList<Node> equal = new ArrayList();

        public void add(char c, Node node) {
            int n = this.find(c);
            this.chars.insert(n, c);
            this.equal.add(n, node);
        }

        @Override
        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            char c;
            int n3;
            if (n == charSequence.length()) {
                if (this.hasValue) {
                    throw new IllegalArgumentException("Duplicate string.");
                }
                this.setValue(n2);
                return this;
            }
            if ((n3 = this.find(c = charSequence.charAt(n++))) < this.chars.length() && c == this.chars.charAt(n3)) {
                this.equal.set(n3, this.equal.get(n3).add(stringTrieBuilder, charSequence, n, n2));
            } else {
                this.chars.insert(n3, c);
                this.equal.add(n3, StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n, n2));
            }
            return this;
        }

        @Override
        public Node register(StringTrieBuilder stringTrieBuilder) {
            BranchHeadNode branchHeadNode;
            Node node = this.register(stringTrieBuilder, 0, this.chars.length());
            ValueNode valueNode = branchHeadNode = new BranchHeadNode(this.chars.length(), node);
            if (this.hasValue) {
                if (stringTrieBuilder.matchNodesCanHaveValues()) {
                    branchHeadNode.setValue(this.value);
                } else {
                    valueNode = new IntermediateValueNode(this.value, StringTrieBuilder.access$200(stringTrieBuilder, branchHeadNode));
                }
            }
            return StringTrieBuilder.access$200(stringTrieBuilder, valueNode);
        }

        private Node register(StringTrieBuilder stringTrieBuilder, int n, int n2) {
            int n3 = n2 - n;
            if (n3 > stringTrieBuilder.getMaxBranchLinearSubNodeLength()) {
                int n4 = n + n3 / 2;
                return StringTrieBuilder.access$200(stringTrieBuilder, new SplitBranchNode(this.chars.charAt(n4), this.register(stringTrieBuilder, n, n4), this.register(stringTrieBuilder, n4, n2)));
            }
            ListBranchNode listBranchNode = new ListBranchNode(n3);
            do {
                char c = this.chars.charAt(n);
                Node node = this.equal.get(n);
                if (node.getClass() == ValueNode.class) {
                    listBranchNode.add((int)c, ((ValueNode)node).value);
                    continue;
                }
                listBranchNode.add((int)c, node.register(stringTrieBuilder));
            } while (++n < n2);
            return StringTrieBuilder.access$200(stringTrieBuilder, listBranchNode);
        }

        private int find(char c) {
            int n = 0;
            int n2 = this.chars.length();
            while (n < n2) {
                int n3 = (n + n2) / 2;
                char c2 = this.chars.charAt(n3);
                if (c < c2) {
                    n2 = n3;
                    continue;
                }
                if (c == c2) {
                    return n3;
                }
                n = n3 + 1;
            }
            return n;
        }
    }

    private static final class LinearMatchNode
    extends ValueNode {
        private CharSequence strings;
        private int stringOffset;
        private int length;
        private Node next;
        private int hash;

        public LinearMatchNode(CharSequence charSequence, int n, int n2, Node node) {
            this.strings = charSequence;
            this.stringOffset = n;
            this.length = n2;
            this.next = node;
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            LinearMatchNode linearMatchNode = (LinearMatchNode)object;
            if (this.length != linearMatchNode.length || this.next != linearMatchNode.next) {
                return true;
            }
            int n = this.stringOffset;
            int n2 = linearMatchNode.stringOffset;
            int n3 = this.stringOffset + this.length;
            while (n < n3) {
                if (this.strings.charAt(n) != this.strings.charAt(n2)) {
                    return true;
                }
                ++n;
                ++n2;
            }
            return false;
        }

        @Override
        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            if (n == charSequence.length()) {
                if (this.hasValue) {
                    throw new IllegalArgumentException("Duplicate string.");
                }
                this.setValue(n2);
                return this;
            }
            int n3 = this.stringOffset + this.length;
            int n4 = this.stringOffset;
            while (n4 < n3) {
                char c;
                char c2;
                if (n == charSequence.length()) {
                    c2 = n4 - this.stringOffset;
                    LinearMatchNode linearMatchNode = new LinearMatchNode(this.strings, n4, this.length - c2, this.next);
                    linearMatchNode.setValue(n2);
                    this.length = c2;
                    this.next = linearMatchNode;
                    return this;
                }
                c2 = this.strings.charAt(n4);
                if (c2 != (c = charSequence.charAt(n))) {
                    ValueNode valueNode;
                    Node node;
                    DynamicBranchNode dynamicBranchNode = new DynamicBranchNode();
                    if (n4 == this.stringOffset) {
                        if (this.hasValue) {
                            dynamicBranchNode.setValue(this.value);
                            this.value = 0;
                            this.hasValue = false;
                        }
                        ++this.stringOffset;
                        --this.length;
                        node = this.length > 0 ? this : this.next;
                        valueNode = dynamicBranchNode;
                    } else if (n4 == n3 - 1) {
                        --this.length;
                        node = this.next;
                        this.next = dynamicBranchNode;
                        valueNode = this;
                    } else {
                        int n5 = n4 - this.stringOffset;
                        node = new LinearMatchNode(this.strings, ++n4, this.length - (n5 + 1), this.next);
                        this.length = n5;
                        this.next = dynamicBranchNode;
                        valueNode = this;
                    }
                    ValueNode valueNode2 = StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n + 1, n2);
                    dynamicBranchNode.add(c2, node);
                    dynamicBranchNode.add(c, valueNode2);
                    return valueNode;
                }
                ++n4;
                ++n;
            }
            this.next = this.next.add(stringTrieBuilder, charSequence, n, n2);
            return this;
        }

        @Override
        public Node register(StringTrieBuilder stringTrieBuilder) {
            ValueNode valueNode;
            this.next = this.next.register(stringTrieBuilder);
            int n = stringTrieBuilder.getMaxLinearMatchLength();
            while (this.length > n) {
                int n2 = this.stringOffset + this.length - n;
                this.length -= n;
                LinearMatchNode linearMatchNode = new LinearMatchNode(this.strings, n2, n, this.next);
                linearMatchNode.setHashCode();
                this.next = StringTrieBuilder.access$200(stringTrieBuilder, linearMatchNode);
            }
            if (this.hasValue && !stringTrieBuilder.matchNodesCanHaveValues()) {
                int n3 = this.value;
                this.value = 0;
                this.hasValue = false;
                this.setHashCode();
                valueNode = new IntermediateValueNode(n3, StringTrieBuilder.access$200(stringTrieBuilder, this));
            } else {
                this.setHashCode();
                valueNode = this;
            }
            return StringTrieBuilder.access$200(stringTrieBuilder, valueNode);
        }

        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.offset = n = this.next.markRightEdgesFirst(n);
            }
            return n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            stringTrieBuilder.write(this.stringOffset, this.length);
            this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, stringTrieBuilder.getMinLinearMatch() + this.length - 1);
        }

        private void setHashCode() {
            this.hash = (124151391 + this.length) * 37 + this.next.hashCode();
            if (this.hasValue) {
                this.hash = this.hash * 37 + this.value;
            }
            int n = this.stringOffset + this.length;
            for (int i = this.stringOffset; i < n; ++i) {
                this.hash = this.hash * 37 + this.strings.charAt(i);
            }
        }
    }

    private static final class IntermediateValueNode
    extends ValueNode {
        private Node next;

        public IntermediateValueNode(int n, Node node) {
            this.next = node;
            this.setValue(n);
        }

        @Override
        public int hashCode() {
            return (0x4EEEEEA + this.value) * 37 + this.next.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            IntermediateValueNode intermediateValueNode = (IntermediateValueNode)object;
            return this.next == intermediateValueNode.next;
        }

        @Override
        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.offset = n = this.next.markRightEdgesFirst(n);
            }
            return n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, true);
        }
    }

    private static class ValueNode
    extends Node {
        protected boolean hasValue;
        protected int value;
        static final boolean $assertionsDisabled = !StringTrieBuilder.class.desiredAssertionStatus();

        public ValueNode() {
        }

        public ValueNode(int n) {
            this.hasValue = true;
            this.value = n;
        }

        public final void setValue(int n) {
            if (!$assertionsDisabled && this.hasValue) {
                throw new AssertionError();
            }
            this.hasValue = true;
            this.value = n;
        }

        private void setFinalValue(int n) {
            this.hasValue = true;
            this.value = n;
        }

        @Override
        public int hashCode() {
            int n = 0x111111;
            if (this.hasValue) {
                n = n * 37 + this.value;
            }
            return n;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!super.equals(object)) {
                return true;
            }
            ValueNode valueNode = (ValueNode)object;
            return this.hasValue == valueNode.hasValue && (!this.hasValue || this.value == valueNode.value);
        }

        @Override
        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            if (n == charSequence.length()) {
                throw new IllegalArgumentException("Duplicate string.");
            }
            ValueNode valueNode = StringTrieBuilder.access$100(stringTrieBuilder, charSequence, n, n2);
            valueNode.setValue(this.value);
            return valueNode;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, false);
        }

        static void access$000(ValueNode valueNode, int n) {
            valueNode.setFinalValue(n);
        }
    }

    private static abstract class Node {
        protected int offset = 0;

        public abstract int hashCode();

        public boolean equals(Object object) {
            return this == object || this.getClass() == object.getClass();
        }

        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            return this;
        }

        public Node register(StringTrieBuilder stringTrieBuilder) {
            return this;
        }

        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.offset = n;
            }
            return n;
        }

        public abstract void write(StringTrieBuilder var1);

        public final void writeUnlessInsideRightEdge(int n, int n2, StringTrieBuilder stringTrieBuilder) {
            if (this.offset < 0 && (this.offset < n2 || n < this.offset)) {
                this.write(stringTrieBuilder);
            }
        }

        public final int getOffset() {
            return this.offset;
        }
    }

    public static enum Option {
        FAST,
        SMALL;

    }
}

