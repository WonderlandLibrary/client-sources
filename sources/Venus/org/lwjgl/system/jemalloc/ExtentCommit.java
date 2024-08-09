/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.jemalloc;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.jemalloc.ExtentCommitI;

public abstract class ExtentCommit
extends Callback
implements ExtentCommitI {
    public static ExtentCommit create(long l) {
        ExtentCommitI extentCommitI = (ExtentCommitI)Callback.get(l);
        return extentCommitI instanceof ExtentCommit ? (ExtentCommit)extentCommitI : new Container(l, extentCommitI);
    }

    @Nullable
    public static ExtentCommit createSafe(long l) {
        return l == 0L ? null : ExtentCommit.create(l);
    }

    public static ExtentCommit create(ExtentCommitI extentCommitI) {
        return extentCommitI instanceof ExtentCommit ? (ExtentCommit)extentCommitI : new Container(extentCommitI.address(), extentCommitI);
    }

    protected ExtentCommit() {
        super("(pppppi)B");
    }

    ExtentCommit(long l) {
        super(l);
    }

    private static final class Container
    extends ExtentCommit {
        private final ExtentCommitI delegate;

        Container(long l, ExtentCommitI extentCommitI) {
            super(l);
            this.delegate = extentCommitI;
        }

        @Override
        public boolean invoke(long l, long l2, long l3, long l4, long l5, int n) {
            return this.delegate.invoke(l, l2, l3, l4, l5, n);
        }
    }
}

