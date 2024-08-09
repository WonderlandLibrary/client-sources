/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.nio.ByteBuffer;

public final class UResource {

    public static abstract class Sink {
        public abstract void put(Key var1, Value var2, boolean var3);
    }

    public static abstract class Value {
        protected Value() {
        }

        public abstract int getType();

        public abstract String getString();

        public abstract String getAliasString();

        public abstract int getInt();

        public abstract int getUInt();

        public abstract int[] getIntVector();

        public abstract ByteBuffer getBinary();

        public abstract Array getArray();

        public abstract Table getTable();

        public abstract boolean isNoInheritanceMarker();

        public abstract String[] getStringArray();

        public abstract String[] getStringArrayOrStringAsArray();

        public abstract String getStringOrFirstOfArray();

        public String toString() {
            switch (this.getType()) {
                case 0: {
                    return this.getString();
                }
                case 7: {
                    return Integer.toString(this.getInt());
                }
                case 14: {
                    int[] nArray = this.getIntVector();
                    StringBuilder stringBuilder = new StringBuilder("[");
                    stringBuilder.append(nArray.length).append("]{");
                    if (nArray.length != 0) {
                        stringBuilder.append(nArray[0]);
                        for (int i = 1; i < nArray.length; ++i) {
                            stringBuilder.append(", ").append(nArray[i]);
                        }
                    }
                    return stringBuilder.append('}').toString();
                }
                case 1: {
                    return "(binary blob)";
                }
                case 8: {
                    return "(array)";
                }
                case 2: {
                    return "(table)";
                }
            }
            return "???";
        }
    }

    public static interface Table {
        public int getSize();

        public boolean getKeyAndValue(int var1, Key var2, Value var3);

        public boolean findValue(CharSequence var1, Value var2);
    }

    public static interface Array {
        public int getSize();

        public boolean getValue(int var1, Value var2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Key
    implements CharSequence,
    Cloneable,
    Comparable<Key> {
        private byte[] bytes;
        private int offset;
        private int length;
        private String s;
        static final boolean $assertionsDisabled = !UResource.class.desiredAssertionStatus();

        public Key() {
            this.s = "";
        }

        public Key(String string) {
            this.setString(string);
        }

        private Key(byte[] byArray, int n, int n2) {
            this.bytes = byArray;
            this.offset = n;
            this.length = n2;
        }

        public Key setBytes(byte[] byArray, int n) {
            this.bytes = byArray;
            this.offset = n;
            this.length = 0;
            while (byArray[n + this.length] != 0) {
                ++this.length;
            }
            this.s = null;
            return this;
        }

        public Key setToEmpty() {
            this.bytes = null;
            this.length = 0;
            this.offset = 0;
            this.s = "";
            return this;
        }

        public Key setString(String string) {
            if (string.isEmpty()) {
                this.setToEmpty();
            } else {
                this.bytes = new byte[string.length()];
                this.offset = 0;
                this.length = string.length();
                for (int i = 0; i < this.length; ++i) {
                    char c = string.charAt(i);
                    if (c > '\u007f') {
                        throw new IllegalArgumentException('\"' + string + "\" is not an ASCII string");
                    }
                    this.bytes[i] = (byte)c;
                }
                this.s = string;
            }
            return this;
        }

        public Key clone() {
            try {
                return (Key)super.clone();
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
        }

        @Override
        public char charAt(int n) {
            if (!($assertionsDisabled || 0 <= n && n < this.length)) {
                throw new AssertionError();
            }
            return (char)this.bytes[this.offset + n];
        }

        @Override
        public int length() {
            return this.length;
        }

        @Override
        public Key subSequence(int n, int n2) {
            if (!($assertionsDisabled || 0 <= n && n < this.length)) {
                throw new AssertionError();
            }
            if (!($assertionsDisabled || n <= n2 && n2 <= this.length)) {
                throw new AssertionError();
            }
            return new Key(this.bytes, this.offset + n, n2 - n);
        }

        @Override
        public String toString() {
            if (this.s == null) {
                this.s = this.internalSubString(0, this.length);
            }
            return this.s;
        }

        private String internalSubString(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder(n2 - n);
            for (int i = n; i < n2; ++i) {
                stringBuilder.append((char)this.bytes[this.offset + i]);
            }
            return stringBuilder.toString();
        }

        public String substring(int n) {
            if (!($assertionsDisabled || 0 <= n && n < this.length)) {
                throw new AssertionError();
            }
            return this.internalSubString(n, this.length);
        }

        public String substring(int n, int n2) {
            if (!($assertionsDisabled || 0 <= n && n < this.length)) {
                throw new AssertionError();
            }
            if (!($assertionsDisabled || n <= n2 && n2 <= this.length)) {
                throw new AssertionError();
            }
            return this.internalSubString(n, n2);
        }

        private boolean regionMatches(byte[] byArray, int n, int n2) {
            for (int i = 0; i < n2; ++i) {
                if (this.bytes[this.offset + i] == byArray[n + i]) continue;
                return true;
            }
            return false;
        }

        private boolean regionMatches(int n, CharSequence charSequence, int n2) {
            for (int i = 0; i < n2; ++i) {
                if (this.bytes[this.offset + n + i] == charSequence.charAt(i)) continue;
                return true;
            }
            return false;
        }

        public boolean equals(Object object) {
            if (object == null) {
                return true;
            }
            if (this == object) {
                return false;
            }
            if (object instanceof Key) {
                Key key = (Key)object;
                return this.length == key.length && this.regionMatches(key.bytes, key.offset, this.length);
            }
            return true;
        }

        public boolean contentEquals(CharSequence charSequence) {
            if (charSequence == null) {
                return true;
            }
            return this == charSequence || charSequence.length() == this.length && this.regionMatches(0, charSequence, this.length);
        }

        public boolean startsWith(CharSequence charSequence) {
            int n = charSequence.length();
            return n <= this.length && this.regionMatches(0, charSequence, n);
        }

        public boolean endsWith(CharSequence charSequence) {
            int n = charSequence.length();
            return n <= this.length && this.regionMatches(this.length - n, charSequence, n);
        }

        public boolean regionMatches(int n, CharSequence charSequence) {
            int n2 = charSequence.length();
            return n2 == this.length - n && this.regionMatches(n, charSequence, n2);
        }

        public int hashCode() {
            if (this.length == 0) {
                return 1;
            }
            int n = this.bytes[this.offset];
            for (int i = 1; i < this.length; ++i) {
                n = 37 * n + this.bytes[this.offset];
            }
            return n;
        }

        @Override
        public int compareTo(Key key) {
            return this.compareTo((CharSequence)key);
        }

        @Override
        public int compareTo(CharSequence charSequence) {
            int n = charSequence.length();
            int n2 = this.length <= n ? this.length : n;
            for (int i = 0; i < n2; ++i) {
                int n3 = this.charAt(i) - charSequence.charAt(i);
                if (n3 == 0) continue;
                return n3;
            }
            return this.length - n;
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            return this.subSequence(n, n2);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Key)object);
        }
    }
}

