/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system.macosx;

import javax.annotation.Nullable;
import org.lwjgl.system.Callback;
import org.lwjgl.system.macosx.EnumerationMutationHandlerI;

public abstract class EnumerationMutationHandler
extends Callback
implements EnumerationMutationHandlerI {
    public static EnumerationMutationHandler create(long l) {
        EnumerationMutationHandlerI enumerationMutationHandlerI = (EnumerationMutationHandlerI)Callback.get(l);
        return enumerationMutationHandlerI instanceof EnumerationMutationHandler ? (EnumerationMutationHandler)enumerationMutationHandlerI : new Container(l, enumerationMutationHandlerI);
    }

    @Nullable
    public static EnumerationMutationHandler createSafe(long l) {
        return l == 0L ? null : EnumerationMutationHandler.create(l);
    }

    public static EnumerationMutationHandler create(EnumerationMutationHandlerI enumerationMutationHandlerI) {
        return enumerationMutationHandlerI instanceof EnumerationMutationHandler ? (EnumerationMutationHandler)enumerationMutationHandlerI : new Container(enumerationMutationHandlerI.address(), enumerationMutationHandlerI);
    }

    protected EnumerationMutationHandler() {
        super("(p)v");
    }

    EnumerationMutationHandler(long l) {
        super(l);
    }

    private static final class Container
    extends EnumerationMutationHandler {
        private final EnumerationMutationHandlerI delegate;

        Container(long l, EnumerationMutationHandlerI enumerationMutationHandlerI) {
            super(l);
            this.delegate = enumerationMutationHandlerI;
        }

        @Override
        public void invoke(long l) {
            this.delegate.invoke(l);
        }
    }
}

