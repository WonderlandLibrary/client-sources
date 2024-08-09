/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers.types.families;

import com.mojang.datafixers.RewriteResult;
import com.mojang.datafixers.functions.PointFree;
import com.mojang.datafixers.types.families.Algebra;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ListAlgebra
implements Algebra {
    private final String name;
    private final List<RewriteResult<?, ?>> views;
    private int hashCode;

    public ListAlgebra(String string, List<RewriteResult<?, ?>> list) {
        this.name = string;
        this.views = list;
    }

    @Override
    public RewriteResult<?, ?> apply(int n) {
        return this.views.get(n);
    }

    public String toString() {
        return this.toString(0);
    }

    @Override
    public String toString(int n) {
        String string = "\n" + PointFree.indent(n + 1);
        return "Algebra[" + this.name + string + this.views.stream().map(arg_0 -> ListAlgebra.lambda$toString$0(n, arg_0)).collect(Collectors.joining(string)) + "\n" + PointFree.indent(n) + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof ListAlgebra)) {
            return true;
        }
        ListAlgebra listAlgebra = (ListAlgebra)object;
        return Objects.equals(this.views, listAlgebra.views);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.views.hashCode();
        }
        return this.hashCode;
    }

    private static String lambda$toString$0(int n, RewriteResult rewriteResult) {
        return rewriteResult.view().function().toString(n + 1);
    }
}

