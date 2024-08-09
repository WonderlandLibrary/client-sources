/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.util.Freezable;
import com.ibm.icu.util.Output;
import java.util.Arrays;
import java.util.EnumSet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public final class PluralRanges
implements Freezable<PluralRanges>,
Comparable<PluralRanges> {
    private volatile boolean isFrozen;
    private Matrix matrix = new Matrix();
    private boolean[] explicit = new boolean[StandardPlural.COUNT];

    @Deprecated
    public PluralRanges() {
    }

    @Deprecated
    public void add(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
        if (this.isFrozen) {
            throw new UnsupportedOperationException();
        }
        this.explicit[standardPlural3.ordinal()] = true;
        if (standardPlural == null) {
            for (StandardPlural standardPlural4 : StandardPlural.values()) {
                if (standardPlural2 == null) {
                    for (StandardPlural standardPlural5 : StandardPlural.values()) {
                        this.matrix.setIfNew(standardPlural4, standardPlural5, standardPlural3);
                    }
                    continue;
                }
                this.explicit[standardPlural2.ordinal()] = true;
                this.matrix.setIfNew(standardPlural4, standardPlural2, standardPlural3);
            }
        } else if (standardPlural2 == null) {
            this.explicit[standardPlural.ordinal()] = true;
            for (StandardPlural standardPlural6 : StandardPlural.values()) {
                this.matrix.setIfNew(standardPlural, standardPlural6, standardPlural3);
            }
        } else {
            this.explicit[standardPlural.ordinal()] = true;
            this.explicit[standardPlural2.ordinal()] = true;
            this.matrix.setIfNew(standardPlural, standardPlural2, standardPlural3);
        }
    }

    @Deprecated
    public StandardPlural get(StandardPlural standardPlural, StandardPlural standardPlural2) {
        StandardPlural standardPlural3 = this.matrix.get(standardPlural, standardPlural2);
        return standardPlural3 == null ? standardPlural2 : standardPlural3;
    }

    @Deprecated
    public boolean isExplicit(StandardPlural standardPlural, StandardPlural standardPlural2) {
        return this.matrix.get(standardPlural, standardPlural2) != null;
    }

    @Deprecated
    public boolean isExplicitlySet(StandardPlural standardPlural) {
        return this.explicit[standardPlural.ordinal()];
    }

    @Deprecated
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof PluralRanges)) {
            return true;
        }
        PluralRanges pluralRanges = (PluralRanges)object;
        return this.matrix.equals(pluralRanges.matrix) && Arrays.equals(this.explicit, pluralRanges.explicit);
    }

    @Deprecated
    public int hashCode() {
        return this.matrix.hashCode();
    }

    @Override
    @Deprecated
    public int compareTo(PluralRanges pluralRanges) {
        return this.matrix.compareTo(pluralRanges.matrix);
    }

    @Override
    @Deprecated
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    @Deprecated
    public PluralRanges freeze() {
        this.isFrozen = true;
        return this;
    }

    @Override
    @Deprecated
    public PluralRanges cloneAsThawed() {
        PluralRanges pluralRanges = new PluralRanges();
        pluralRanges.explicit = (boolean[])this.explicit.clone();
        pluralRanges.matrix = this.matrix.clone();
        return pluralRanges;
    }

    @Deprecated
    public String toString() {
        return this.matrix.toString();
    }

    @Override
    @Deprecated
    public Object cloneAsThawed() {
        return this.cloneAsThawed();
    }

    @Override
    @Deprecated
    public Object freeze() {
        return this.freeze();
    }

    @Override
    @Deprecated
    public int compareTo(Object object) {
        return this.compareTo((PluralRanges)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class Matrix
    implements Comparable<Matrix>,
    Cloneable {
        private byte[] data = new byte[StandardPlural.COUNT * StandardPlural.COUNT];

        Matrix() {
            for (int i = 0; i < this.data.length; ++i) {
                this.data[i] = -1;
            }
        }

        void set(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
            this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()] = (byte)(standardPlural3 == null ? -1 : (byte)standardPlural3.ordinal());
        }

        void setIfNew(StandardPlural standardPlural, StandardPlural standardPlural2, StandardPlural standardPlural3) {
            byte by = this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()];
            if (by >= 0) {
                throw new IllegalArgumentException("Previously set value for <" + (Object)((Object)standardPlural) + ", " + (Object)((Object)standardPlural2) + ", " + (Object)((Object)StandardPlural.VALUES.get(by)) + ">");
            }
            this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()] = (byte)(standardPlural3 == null ? -1 : (byte)standardPlural3.ordinal());
        }

        StandardPlural get(StandardPlural standardPlural, StandardPlural standardPlural2) {
            byte by = this.data[standardPlural.ordinal() * StandardPlural.COUNT + standardPlural2.ordinal()];
            return by < 0 ? null : StandardPlural.VALUES.get(by);
        }

        StandardPlural endSame(StandardPlural standardPlural) {
            StandardPlural standardPlural2 = null;
            for (StandardPlural standardPlural3 : StandardPlural.VALUES) {
                StandardPlural standardPlural4 = this.get(standardPlural3, standardPlural);
                if (standardPlural4 == null) continue;
                if (standardPlural2 == null) {
                    standardPlural2 = standardPlural4;
                    continue;
                }
                if (standardPlural2 == standardPlural4) continue;
                return null;
            }
            return standardPlural2;
        }

        StandardPlural startSame(StandardPlural standardPlural, EnumSet<StandardPlural> enumSet, Output<Boolean> output) {
            output.value = false;
            StandardPlural standardPlural2 = null;
            for (StandardPlural standardPlural3 : StandardPlural.VALUES) {
                StandardPlural standardPlural4 = this.get(standardPlural, standardPlural3);
                if (standardPlural4 == null) continue;
                if (standardPlural2 == null) {
                    standardPlural2 = standardPlural4;
                    continue;
                }
                if (standardPlural2 != standardPlural4) {
                    return null;
                }
                if (enumSet.contains((Object)standardPlural3)) continue;
                output.value = true;
            }
            return standardPlural2;
        }

        public int hashCode() {
            int n = 0;
            for (int i = 0; i < this.data.length; ++i) {
                n = n * 37 + this.data[i];
            }
            return n;
        }

        public boolean equals(Object object) {
            if (!(object instanceof Matrix)) {
                return true;
            }
            return 0 == this.compareTo((Matrix)object);
        }

        @Override
        public int compareTo(Matrix matrix) {
            for (int i = 0; i < this.data.length; ++i) {
                int n = this.data[i] - matrix.data[i];
                if (n == 0) continue;
                return n;
            }
            return 1;
        }

        public Matrix clone() {
            Matrix matrix = new Matrix();
            matrix.data = (byte[])this.data.clone();
            return matrix;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            for (StandardPlural standardPlural : StandardPlural.values()) {
                for (StandardPlural standardPlural2 : StandardPlural.values()) {
                    StandardPlural standardPlural3 = this.get(standardPlural, standardPlural2);
                    if (standardPlural3 == null) continue;
                    stringBuilder.append((Object)((Object)standardPlural) + " & " + (Object)((Object)standardPlural2) + " \u2192 " + (Object)((Object)standardPlural3) + ";\n");
                }
            }
            return stringBuilder.toString();
        }

        public Object clone() throws CloneNotSupportedException {
            return this.clone();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Matrix)object);
        }
    }
}

