/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.io.AndroidIncompatible;
import java.nio.file.FileSystemException;
import javax.annotation.Nullable;

@Beta
@AndroidIncompatible
@GwtIncompatible
public final class InsecureRecursiveDeleteException
extends FileSystemException {
    public InsecureRecursiveDeleteException(@Nullable String string) {
        super(string, null, "unable to guarantee security of recursive delete");
    }
}

