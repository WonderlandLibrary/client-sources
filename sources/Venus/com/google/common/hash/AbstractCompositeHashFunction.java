/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.PrimitiveSink;
import java.nio.charset.Charset;

abstract class AbstractCompositeHashFunction
extends AbstractStreamingHashFunction {
    final HashFunction[] functions;
    private static final long serialVersionUID = 0L;

    AbstractCompositeHashFunction(HashFunction ... hashFunctionArray) {
        for (HashFunction hashFunction : hashFunctionArray) {
            Preconditions.checkNotNull(hashFunction);
        }
        this.functions = hashFunctionArray;
    }

    abstract HashCode makeHash(Hasher[] var1);

    @Override
    public Hasher newHasher() {
        Hasher[] hasherArray = new Hasher[this.functions.length];
        for (int i = 0; i < hasherArray.length; ++i) {
            hasherArray[i] = this.functions[i].newHasher();
        }
        return new Hasher(this, hasherArray){
            final Hasher[] val$hashers;
            final AbstractCompositeHashFunction this$0;
            {
                this.this$0 = abstractCompositeHashFunction;
                this.val$hashers = hasherArray;
            }

            @Override
            public Hasher putByte(byte by) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putByte(by);
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] byArray) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putBytes(byArray);
                }
                return this;
            }

            @Override
            public Hasher putBytes(byte[] byArray, int n, int n2) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putBytes(byArray, n, n2);
                }
                return this;
            }

            @Override
            public Hasher putShort(short s) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putShort(s);
                }
                return this;
            }

            @Override
            public Hasher putInt(int n) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putInt(n);
                }
                return this;
            }

            @Override
            public Hasher putLong(long l) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putLong(l);
                }
                return this;
            }

            @Override
            public Hasher putFloat(float f) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putFloat(f);
                }
                return this;
            }

            @Override
            public Hasher putDouble(double d) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putDouble(d);
                }
                return this;
            }

            @Override
            public Hasher putBoolean(boolean bl) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putBoolean(bl);
                }
                return this;
            }

            @Override
            public Hasher putChar(char c) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putChar(c);
                }
                return this;
            }

            @Override
            public Hasher putUnencodedChars(CharSequence charSequence) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putUnencodedChars(charSequence);
                }
                return this;
            }

            @Override
            public Hasher putString(CharSequence charSequence, Charset charset) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putString(charSequence, charset);
                }
                return this;
            }

            @Override
            public <T> Hasher putObject(T t, Funnel<? super T> funnel) {
                for (Hasher hasher : this.val$hashers) {
                    hasher.putObject(t, funnel);
                }
                return this;
            }

            @Override
            public HashCode hash() {
                return this.this$0.makeHash(this.val$hashers);
            }

            @Override
            public PrimitiveSink putString(CharSequence charSequence, Charset charset) {
                return this.putString(charSequence, charset);
            }

            @Override
            public PrimitiveSink putUnencodedChars(CharSequence charSequence) {
                return this.putUnencodedChars(charSequence);
            }

            @Override
            public PrimitiveSink putChar(char c) {
                return this.putChar(c);
            }

            @Override
            public PrimitiveSink putBoolean(boolean bl) {
                return this.putBoolean(bl);
            }

            @Override
            public PrimitiveSink putDouble(double d) {
                return this.putDouble(d);
            }

            @Override
            public PrimitiveSink putFloat(float f) {
                return this.putFloat(f);
            }

            @Override
            public PrimitiveSink putLong(long l) {
                return this.putLong(l);
            }

            @Override
            public PrimitiveSink putInt(int n) {
                return this.putInt(n);
            }

            @Override
            public PrimitiveSink putShort(short s) {
                return this.putShort(s);
            }

            @Override
            public PrimitiveSink putBytes(byte[] byArray, int n, int n2) {
                return this.putBytes(byArray, n, n2);
            }

            @Override
            public PrimitiveSink putBytes(byte[] byArray) {
                return this.putBytes(byArray);
            }

            @Override
            public PrimitiveSink putByte(byte by) {
                return this.putByte(by);
            }
        };
    }
}

