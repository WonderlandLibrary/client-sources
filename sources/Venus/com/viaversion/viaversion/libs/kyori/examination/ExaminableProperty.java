/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.examination;

import com.viaversion.viaversion.libs.kyori.examination.Examiner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExaminableProperty {
    private ExaminableProperty() {
    }

    @NotNull
    public abstract String name();

    @NotNull
    public abstract <R> R examine(@NotNull Examiner<? extends R> var1);

    public String toString() {
        return "ExaminableProperty{" + this.name() + "}";
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, @Nullable Object object) {
        return new ExaminableProperty(string, object){
            final String val$name;
            final Object val$value;
            {
                this.val$name = string;
                this.val$value = object;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, @Nullable String string2) {
        return new ExaminableProperty(string, string2){
            final String val$name;
            final String val$value;
            {
                this.val$name = string;
                this.val$value = string2;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, boolean bl) {
        return new ExaminableProperty(string, bl){
            final String val$name;
            final boolean val$value;
            {
                this.val$name = string;
                this.val$value = bl;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, boolean[] blArray) {
        return new ExaminableProperty(string, blArray){
            final String val$name;
            final boolean[] val$value;
            {
                this.val$name = string;
                this.val$value = blArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, byte by) {
        return new ExaminableProperty(string, by){
            final String val$name;
            final byte val$value;
            {
                this.val$name = string;
                this.val$value = by;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, byte[] byArray) {
        return new ExaminableProperty(string, byArray){
            final String val$name;
            final byte[] val$value;
            {
                this.val$name = string;
                this.val$value = byArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, char c) {
        return new ExaminableProperty(string, c){
            final String val$name;
            final char val$value;
            {
                this.val$name = string;
                this.val$value = c;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, char[] cArray) {
        return new ExaminableProperty(string, cArray){
            final String val$name;
            final char[] val$value;
            {
                this.val$name = string;
                this.val$value = cArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, double d) {
        return new ExaminableProperty(string, d){
            final String val$name;
            final double val$value;
            {
                this.val$name = string;
                this.val$value = d;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, double[] dArray) {
        return new ExaminableProperty(string, dArray){
            final String val$name;
            final double[] val$value;
            {
                this.val$name = string;
                this.val$value = dArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, float f) {
        return new ExaminableProperty(string, f){
            final String val$name;
            final float val$value;
            {
                this.val$name = string;
                this.val$value = f;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, float[] fArray) {
        return new ExaminableProperty(string, fArray){
            final String val$name;
            final float[] val$value;
            {
                this.val$name = string;
                this.val$value = fArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, int n) {
        return new ExaminableProperty(string, n){
            final String val$name;
            final int val$value;
            {
                this.val$name = string;
                this.val$value = n;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, int[] nArray) {
        return new ExaminableProperty(string, nArray){
            final String val$name;
            final int[] val$value;
            {
                this.val$name = string;
                this.val$value = nArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, long l) {
        return new ExaminableProperty(string, l){
            final String val$name;
            final long val$value;
            {
                this.val$name = string;
                this.val$value = l;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, long[] lArray) {
        return new ExaminableProperty(string, lArray){
            final String val$name;
            final long[] val$value;
            {
                this.val$name = string;
                this.val$value = lArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, short s) {
        return new ExaminableProperty(string, s){
            final String val$name;
            final short val$value;
            {
                this.val$name = string;
                this.val$value = s;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    @NotNull
    public static ExaminableProperty of(@NotNull String string, short[] sArray) {
        return new ExaminableProperty(string, sArray){
            final String val$name;
            final short[] val$value;
            {
                this.val$name = string;
                this.val$value = sArray;
                super(null);
            }

            @Override
            @NotNull
            public String name() {
                return this.val$name;
            }

            @Override
            @NotNull
            public <R> R examine(@NotNull Examiner<? extends R> examiner) {
                return examiner.examine(this.val$value);
            }
        };
    }

    ExaminableProperty(1 var1_1) {
        this();
    }
}

