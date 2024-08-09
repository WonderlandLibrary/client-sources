/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultKeyOperation;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.KeyOperation;
import io.jsonwebtoken.security.KeyOperationBuilder;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultKeyOperationBuilder
implements KeyOperationBuilder {
    private String id;
    private String description;
    private final Set<String> related = new LinkedHashSet<String>();

    @Override
    public KeyOperationBuilder id(String string) {
        this.id = string;
        return this;
    }

    @Override
    public KeyOperationBuilder description(String string) {
        this.description = string;
        return this;
    }

    @Override
    public KeyOperationBuilder related(String string) {
        if (Strings.hasText(string)) {
            this.related.add(string);
        }
        return this;
    }

    @Override
    public KeyOperation build() {
        return new DefaultKeyOperation(this.id, this.description, this.related);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

