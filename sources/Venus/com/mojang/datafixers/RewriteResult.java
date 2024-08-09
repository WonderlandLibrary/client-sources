/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.datafixers;

import com.mojang.datafixers.View;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.RecursivePoint;
import java.util.BitSet;
import java.util.Objects;
import org.apache.commons.lang3.ObjectUtils;

public final class RewriteResult<A, B> {
    protected final View<A, B> view;
    protected final BitSet recData;

    public RewriteResult(View<A, B> view, BitSet bitSet) {
        this.view = view;
        this.recData = bitSet;
    }

    public static <A, B> RewriteResult<A, B> create(View<A, B> view, BitSet bitSet) {
        return new RewriteResult<A, B>(view, bitSet);
    }

    public static <A> RewriteResult<A, A> nop(Type<A> type) {
        return new RewriteResult<A, A>(View.nopView(type), new BitSet());
    }

    public <C> RewriteResult<C, B> compose(RewriteResult<C, A> rewriteResult) {
        BitSet bitSet;
        if (this.view.type() instanceof RecursivePoint.RecursivePointType && rewriteResult.view.type() instanceof RecursivePoint.RecursivePointType) {
            bitSet = ObjectUtils.clone(this.recData);
            bitSet.or(rewriteResult.recData);
        } else {
            bitSet = this.recData;
        }
        return RewriteResult.create(this.view.compose(rewriteResult.view), bitSet);
    }

    public BitSet recData() {
        return this.recData;
    }

    public View<A, B> view() {
        return this.view;
    }

    public String toString() {
        return "RR[" + this.view + "]";
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        RewriteResult rewriteResult = (RewriteResult)object;
        return Objects.equals(this.view, rewriteResult.view);
    }

    public int hashCode() {
        return Objects.hash(this.view);
    }
}

