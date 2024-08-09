/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.Optional;

public class Either<L, R> {
    private Optional<L> left;
    private Optional<R> right;

    private Either(Optional<L> optional, Optional<R> optional2) {
        this.left = optional;
        this.right = optional2;
        if (!this.left.isPresent() && !this.right.isPresent()) {
            throw new IllegalArgumentException("Both left and right are not present");
        }
        if (this.left.isPresent() && this.right.isPresent()) {
            throw new IllegalArgumentException("Both left and right are present");
        }
    }

    public Optional<L> getLeft() {
        return this.left;
    }

    public Optional<R> getRight() {
        return this.right;
    }

    public static <L, R> Either<L, R> makeLeft(L l) {
        return new Either(Optional.of(l), Optional.empty());
    }

    public static <L, R> Either makeRight(R r) {
        return new Either(Optional.empty(), Optional.of(r));
    }
}

