/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.net.URI;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RedirectLocations
extends AbstractList<Object> {
    private final Set<URI> unique = new HashSet<URI>();
    private final List<URI> all = new ArrayList<URI>();

    public boolean contains(URI uRI) {
        return this.unique.contains(uRI);
    }

    public void add(URI uRI) {
        this.unique.add(uRI);
        this.all.add(uRI);
    }

    public boolean remove(URI uRI) {
        boolean bl = this.unique.remove(uRI);
        if (bl) {
            Iterator<URI> iterator2 = this.all.iterator();
            while (iterator2.hasNext()) {
                URI uRI2 = iterator2.next();
                if (!uRI2.equals(uRI)) continue;
                iterator2.remove();
            }
        }
        return bl;
    }

    public List<URI> getAll() {
        return new ArrayList<URI>(this.all);
    }

    @Override
    public URI get(int n) {
        return this.all.get(n);
    }

    @Override
    public int size() {
        return this.all.size();
    }

    @Override
    public Object set(int n, Object object) {
        URI uRI = this.all.set(n, (URI)object);
        this.unique.remove(uRI);
        this.unique.add((URI)object);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return uRI;
    }

    @Override
    public void add(int n, Object object) {
        this.all.add(n, (URI)object);
        this.unique.add((URI)object);
    }

    @Override
    public URI remove(int n) {
        URI uRI = this.all.remove(n);
        this.unique.remove(uRI);
        if (this.all.size() != this.unique.size()) {
            this.unique.addAll(this.all);
        }
        return uRI;
    }

    @Override
    public boolean contains(Object object) {
        return this.unique.contains(object);
    }

    @Override
    public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public Object get(int n) {
        return this.get(n);
    }
}

