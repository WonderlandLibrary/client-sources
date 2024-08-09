/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.patchy;

import com.mojang.patchy.BlockingDC;
import java.util.Hashtable;
import java.util.function.Predicate;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.spi.InitialContextFactory;

public class BlockingICF
implements InitialContextFactory {
    private final Predicate<String> blockList;
    private final InitialContextFactory parent;

    public BlockingICF(Predicate<String> predicate, InitialContextFactory initialContextFactory) {
        this.blockList = predicate;
        this.parent = initialContextFactory;
    }

    @Override
    public Context getInitialContext(Hashtable<?, ?> hashtable) throws NamingException {
        return new BlockingDC(this.blockList, (DirContext)this.parent.getInitialContext(hashtable));
    }
}

