package com.ibm.icu.impl;

import java.nio.*;

public final class UResource
{
    public static final class Key implements CharSequence, Cloneable, Comparable<Key>
    {
        private byte[] bytes;
        private int offset;
        private int length;
        private String s;
        
        public Key() {
            this.s = "";
        }
        
        public Key(final String s) {
            this.setString(s);
        }
        
        private Key(final byte[] keyBytes, final int keyOffset, final int keyLength) {
            this.bytes = keyBytes;
            this.offset = keyOffset;
            this.length = keyLength;
        }
        
        public Key setBytes(final byte[] keyBytes, final int keyOffset) {
            this.bytes = keyBytes;
            this.offset = keyOffset;
            this.length = 0;
            while (keyBytes[keyOffset + this.length] != 0) {
                ++this.length;
            }
            this.s = null;
            return this;
        }
        
        public Key setToEmpty() {
            this.bytes = null;
            final int n = 0;
            this.length = n;
            this.offset = n;
            this.s = "";
            return this;
        }
        
        public Key setString(final String s) {
            if (s.isEmpty()) {
                this.setToEmpty();
            }
            else {
                this.bytes = new byte[s.length()];
                this.offset = 0;
                this.length = s.length();
                for (int i = 0; i < this.length; ++i) {
                    final char c = s.charAt(i);
                    if (c > '\u007f') {
                        throw new IllegalArgumentException('\"' + s + "\" is not an ASCII string");
                    }
                    this.bytes[i] = (byte)c;
                }
                this.s = s;
            }
            return this;
        }
        
        public Key clone() {
            try {
                return (Key)super.clone();
            }
            catch (CloneNotSupportedException cannotOccur) {
                return null;
            }
        }
        
        @Override
        public char charAt(final int i) {
            assert 0 <= i && i < this.length;
            return (char)this.bytes[this.offset + i];
        }
        
        @Override
        public int length() {
            return this.length;
        }
        
        @Override
        public Key subSequence(final int start, final int end) {
            assert 0 <= start && start < this.length;
            assert start <= end && end <= this.length;
            return new Key(this.bytes, this.offset + start, end - start);
        }
        
        @Override
        public String toString() {
            if (this.s == null) {
                this.s = this.internalSubString(0, this.length);
            }
            return this.s;
        }
        
        private String internalSubString(final int start, final int end) {
            final StringBuilder sb = new StringBuilder(end - start);
            for (int i = start; i < end; ++i) {
                sb.append((char)this.bytes[this.offset + i]);
            }
            return sb.toString();
        }
        
        public String substring(final int start) {
            assert 0 <= start && start < this.length;
            return this.internalSubString(start, this.length);
        }
        
        public String substring(final int start, final int end) {
            assert 0 <= start && start < this.length;
            assert start <= end && end <= this.length;
            return this.internalSubString(start, end);
        }
        
        private boolean regionMatches(final byte[] otherBytes, final int otherOffset, final int n) {
            for (int i = 0; i < n; ++i) {
                if (this.bytes[this.offset + i] != otherBytes[otherOffset + i]) {
                    return false;
                }
            }
            return true;
        }
        
        private boolean regionMatches(final int start, final CharSequence cs, final int n) {
            for (int i = 0; i < n; ++i) {
                if (this.bytes[this.offset + start + i] != cs.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean equals(final Object other) {
            if (other == null) {
                return false;
            }
            if (this == other) {
                return true;
            }
            if (other instanceof Key) {
                final Key otherKey = (Key)other;
                return this.length == otherKey.length && this.regionMatches(otherKey.bytes, otherKey.offset, this.length);
            }
            return false;
        }
        
        public boolean contentEquals(final CharSequence cs) {
            return cs != null && (this == cs || (cs.length() == this.length && this.regionMatches(0, cs, this.length)));
        }
        
        public boolean startsWith(final CharSequence cs) {
            final int csLength = cs.length();
            return csLength <= this.length && this.regionMatches(0, cs, csLength);
        }
        
        public boolean endsWith(final CharSequence cs) {
            final int csLength = cs.length();
            return csLength <= this.length && this.regionMatches(this.length - csLength, cs, csLength);
        }
        
        public boolean regionMatches(final int start, final CharSequence cs) {
            final int csLength = cs.length();
            return csLength == this.length - start && this.regionMatches(start, cs, csLength);
        }
        
        @Override
        public int hashCode() {
            if (this.length == 0) {
                return 0;
            }
            int h = this.bytes[this.offset];
            for (int i = 1; i < this.length; ++i) {
                h = 37 * h + this.bytes[this.offset];
            }
            return h;
        }
        
        @Override
        public int compareTo(final Key other) {
            return this.compareTo((CharSequence)other);
        }
        
        public int compareTo(final CharSequence cs) {
            final int csLength = cs.length();
            for (int minLength = (this.length <= csLength) ? this.length : csLength, i = 0; i < minLength; ++i) {
                final int diff = this.charAt(i) - cs.charAt(i);
                if (diff != 0) {
                    return diff;
                }
            }
            return this.length - csLength;
        }
    }
    
    public abstract static class Value
    {
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
        
        @Override
        public String toString() {
            switch (this.getType()) {
                case 0: {
                    return this.getString();
                }
                case 7: {
                    return Integer.toString(this.getInt());
                }
                case 14: {
                    final int[] iv = this.getIntVector();
                    final StringBuilder sb = new StringBuilder("[");
                    sb.append(iv.length).append("]{");
                    if (iv.length != 0) {
                        sb.append(iv[0]);
                        for (int i = 1; i < iv.length; ++i) {
                            sb.append(", ").append(iv[i]);
                        }
                    }
                    return sb.append('}').toString();
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
                default: {
                    return "???";
                }
            }
        }
    }
    
    public abstract static class Sink
    {
        public abstract void put(final Key p0, final Value p1, final boolean p2);
    }
    
    public interface Array
    {
        int getSize();
        
        boolean getValue(final int p0, final Value p1);
    }
    
    public interface Table
    {
        int getSize();
        
        boolean getKeyAndValue(final int p0, final Key p1, final Value p2);
    }
}
