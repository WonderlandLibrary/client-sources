/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TextTrieMap<V> {
    private Node _root = new Node(this, null);
    boolean _ignoreCase;

    public TextTrieMap(boolean bl) {
        this._ignoreCase = bl;
    }

    public TextTrieMap<V> put(CharSequence charSequence, V v) {
        CharIterator charIterator = new CharIterator(charSequence, 0, this._ignoreCase);
        this._root.add(charIterator, v);
        return this;
    }

    public Iterator<V> get(String string) {
        return this.get(string, 0);
    }

    public Iterator<V> get(CharSequence charSequence, int n) {
        return this.get(charSequence, n, null);
    }

    public Iterator<V> get(CharSequence charSequence, int n, Output output) {
        LongestMatchHandler longestMatchHandler = new LongestMatchHandler(null);
        this.find(charSequence, n, longestMatchHandler, output);
        if (output != null) {
            output.matchLength = longestMatchHandler.getMatchLength();
        }
        return longestMatchHandler.getMatches();
    }

    public void find(CharSequence charSequence, ResultHandler<V> resultHandler) {
        this.find(charSequence, 0, resultHandler, null);
    }

    public void find(CharSequence charSequence, int n, ResultHandler<V> resultHandler) {
        this.find(charSequence, n, resultHandler, null);
    }

    private void find(CharSequence charSequence, int n, ResultHandler<V> resultHandler, Output output) {
        CharIterator charIterator = new CharIterator(charSequence, n, this._ignoreCase);
        this.find(this._root, charIterator, resultHandler, output);
    }

    private synchronized void find(Node node, CharIterator charIterator, ResultHandler<V> resultHandler, Output output) {
        Iterator iterator2 = node.values();
        if (iterator2 != null && !resultHandler.handlePrefixMatch(charIterator.processedLength(), iterator2)) {
            return;
        }
        Node node2 = node.findMatch(charIterator, output);
        if (node2 != null) {
            this.find(node2, charIterator, resultHandler, output);
        }
    }

    public void putLeadCodePoints(UnicodeSet unicodeSet) {
        this._root.putLeadCodePoints(unicodeSet);
    }

    private static char[] toCharArray(CharSequence charSequence) {
        char[] cArray = new char[charSequence.length()];
        for (int i = 0; i < cArray.length; ++i) {
            cArray[i] = charSequence.charAt(i);
        }
        return cArray;
    }

    private static char[] subArray(char[] cArray, int n) {
        if (n == 0) {
            return cArray;
        }
        char[] cArray2 = new char[cArray.length - n];
        System.arraycopy(cArray, n, cArray2, 0, cArray2.length);
        return cArray2;
    }

    private static char[] subArray(char[] cArray, int n, int n2) {
        if (n == 0 && n2 == cArray.length) {
            return cArray;
        }
        char[] cArray2 = new char[n2 - n];
        System.arraycopy(cArray, n, cArray2, 0, n2 - n);
        return cArray2;
    }

    static char[] access$200(CharSequence charSequence) {
        return TextTrieMap.toCharArray(charSequence);
    }

    static char[] access$300(char[] cArray, int n) {
        return TextTrieMap.subArray(cArray, n);
    }

    static char[] access$400(char[] cArray, int n, int n2) {
        return TextTrieMap.subArray(cArray, n, n2);
    }

    private class Node {
        private char[] _text;
        private List<V> _values;
        private List<Node> _children;
        final TextTrieMap this$0;

        private Node(TextTrieMap textTrieMap) {
            this.this$0 = textTrieMap;
        }

        private Node(TextTrieMap textTrieMap, char[] cArray, List<V> list, List<Node> list2) {
            this.this$0 = textTrieMap;
            this._text = cArray;
            this._values = list;
            this._children = list2;
        }

        public int charCount() {
            return this._text == null ? 0 : this._text.length;
        }

        public Iterator<V> values() {
            if (this._values == null) {
                return null;
            }
            return this._values.iterator();
        }

        public void add(CharIterator charIterator, V v) {
            StringBuilder stringBuilder = new StringBuilder();
            while (charIterator.hasNext()) {
                stringBuilder.append(charIterator.next());
            }
            this.add(TextTrieMap.access$200(stringBuilder), 0, v);
        }

        public Node findMatch(CharIterator charIterator, Output output) {
            if (this._children == null) {
                return null;
            }
            if (!charIterator.hasNext()) {
                if (output != null) {
                    output.partialMatch = true;
                }
                return null;
            }
            Node node = null;
            Character c = charIterator.next();
            for (Node node2 : this._children) {
                if (c.charValue() < node2._text[0]) break;
                if (c.charValue() != node2._text[0]) continue;
                if (!node2.matchFollowing(charIterator, output)) break;
                node = node2;
                break;
            }
            return node;
        }

        public void putLeadCodePoints(UnicodeSet unicodeSet) {
            if (this._children == null) {
                return;
            }
            for (Node node : this._children) {
                char c = node._text[0];
                if (!UCharacter.isHighSurrogate(c)) {
                    unicodeSet.add(c);
                    continue;
                }
                if (node.charCount() >= 2) {
                    unicodeSet.add(Character.codePointAt(node._text, 0));
                    continue;
                }
                if (node._children == null) continue;
                for (Node node2 : node._children) {
                    char c2 = node2._text[0];
                    int n = Character.toCodePoint(c, c2);
                    unicodeSet.add(n);
                }
            }
        }

        private void add(char[] cArray, int n, V v) {
            if (cArray.length == n) {
                this._values = this.addValue(this._values, v);
                return;
            }
            if (this._children == null) {
                this._children = new LinkedList<Node>();
                Node node = new Node(this.this$0, TextTrieMap.access$300(cArray, n), this.addValue(null, v), null);
                this._children.add(node);
                return;
            }
            ListIterator<Node> listIterator2 = this._children.listIterator();
            while (listIterator2.hasNext()) {
                Node node = listIterator2.next();
                if (cArray[n] < node._text[0]) {
                    listIterator2.previous();
                    break;
                }
                if (cArray[n] != node._text[0]) continue;
                int n2 = node.lenMatches(cArray, n);
                if (n2 == node._text.length) {
                    node.add(cArray, n + n2, v);
                } else {
                    node.split(n2);
                    node.add(cArray, n + n2, v);
                }
                return;
            }
            listIterator2.add(new Node(this.this$0, TextTrieMap.access$300(cArray, n), this.addValue(null, v), null));
        }

        private boolean matchFollowing(CharIterator charIterator, Output output) {
            boolean bl = true;
            for (int i = 1; i < this._text.length; ++i) {
                if (!charIterator.hasNext()) {
                    if (output != null) {
                        output.partialMatch = true;
                    }
                    bl = false;
                    break;
                }
                Character c = charIterator.next();
                if (c.charValue() == this._text[i]) continue;
                bl = false;
                break;
            }
            return bl;
        }

        private int lenMatches(char[] cArray, int n) {
            int n2;
            int n3 = cArray.length - n;
            int n4 = this._text.length < n3 ? this._text.length : n3;
            for (n2 = 0; n2 < n4 && this._text[n2] == cArray[n + n2]; ++n2) {
            }
            return n2;
        }

        private void split(int n) {
            char[] cArray = TextTrieMap.access$300(this._text, n);
            this._text = TextTrieMap.access$400(this._text, 0, n);
            Node node = new Node(this.this$0, cArray, this._values, this._children);
            this._values = null;
            this._children = new LinkedList<Node>();
            this._children.add(node);
        }

        private List<V> addValue(List<V> list, V v) {
            if (list == null) {
                list = new LinkedList();
            }
            list.add(v);
            return list;
        }

        Node(TextTrieMap textTrieMap, 1 var2_2) {
            this(textTrieMap);
        }
    }

    private static class LongestMatchHandler<V>
    implements ResultHandler<V> {
        private Iterator<V> matches = null;
        private int length = 0;

        private LongestMatchHandler() {
        }

        @Override
        public boolean handlePrefixMatch(int n, Iterator<V> iterator2) {
            if (n > this.length) {
                this.length = n;
                this.matches = iterator2;
            }
            return false;
        }

        public Iterator<V> getMatches() {
            return this.matches;
        }

        public int getMatchLength() {
            return this.length;
        }

        LongestMatchHandler(1 var1_1) {
            this();
        }
    }

    public static interface ResultHandler<V> {
        public boolean handlePrefixMatch(int var1, Iterator<V> var2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class CharIterator
    implements Iterator<Character> {
        private boolean _ignoreCase;
        private CharSequence _text;
        private int _nextIdx;
        private int _startIdx;
        private Character _remainingChar;

        CharIterator(CharSequence charSequence, int n, boolean bl) {
            this._text = charSequence;
            this._nextIdx = this._startIdx = n;
            this._ignoreCase = bl;
        }

        @Override
        public boolean hasNext() {
            return this._nextIdx == this._text.length() && this._remainingChar == null;
        }

        @Override
        public Character next() {
            Character c;
            if (this._nextIdx == this._text.length() && this._remainingChar == null) {
                return null;
            }
            if (this._remainingChar != null) {
                c = this._remainingChar;
                this._remainingChar = null;
            } else if (this._ignoreCase) {
                int n = UCharacter.foldCase(Character.codePointAt(this._text, this._nextIdx), true);
                this._nextIdx += Character.charCount(n);
                char[] cArray = Character.toChars(n);
                c = Character.valueOf(cArray[0]);
                if (cArray.length == 2) {
                    this._remainingChar = Character.valueOf(cArray[1]);
                }
            } else {
                c = Character.valueOf(this._text.charAt(this._nextIdx));
                ++this._nextIdx;
            }
            return c;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not supproted");
        }

        public int nextIndex() {
            return this._nextIdx;
        }

        public int processedLength() {
            if (this._remainingChar != null) {
                throw new IllegalStateException("In the middle of surrogate pair");
            }
            return this._nextIdx - this._startIdx;
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    public static class Output {
        public int matchLength;
        public boolean partialMatch;
    }
}

