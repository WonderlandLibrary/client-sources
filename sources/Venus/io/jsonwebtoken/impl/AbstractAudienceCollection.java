/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.impl.lang.DefaultNestedCollection;
import java.util.Collection;

abstract class AbstractAudienceCollection<P>
extends DefaultNestedCollection<String, P>
implements ClaimsMutator.AudienceCollection<P> {
    protected AbstractAudienceCollection(P p, Collection<? extends String> collection) {
        super(p, collection);
    }
}

