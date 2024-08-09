/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.StringRegistry;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;

public class IdRegistry<T extends Identifiable>
extends StringRegistry<T> {
    public static final Function<Identifiable, String> FN = new Function<Identifiable, String>(){

        @Override
        public String apply(Identifiable identifiable) {
            Assert.notNull(identifiable, "Identifiable argument cannot be null.");
            return Assert.notNull(Strings.clean(identifiable.getId()), "Identifier cannot be null or empty.");
        }

        @Override
        public Object apply(Object object) {
            return this.apply((Identifiable)object);
        }
    };

    public static <T extends Identifiable> Function<T, String> fn() {
        return FN;
    }

    public IdRegistry(String string, Collection<T> collection) {
        this(string, collection, true);
    }

    public IdRegistry(String string, Collection<T> collection, boolean bl) {
        super(string, "id", Assert.notEmpty(collection, "Collection of Identifiable instances may not be null or empty."), IdRegistry.fn(), bl);
    }
}

