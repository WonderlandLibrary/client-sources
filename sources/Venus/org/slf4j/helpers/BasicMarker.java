/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.slf4j.helpers;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Marker;

public class BasicMarker
implements Marker {
    private static final long serialVersionUID = -2849567615646933777L;
    private final String name;
    private final List<Marker> referenceList = new CopyOnWriteArrayList<Marker>();
    private static final String OPEN = "[ ";
    private static final String CLOSE = " ]";
    private static final String SEP = ", ";

    BasicMarker(String string) {
        if (string == null) {
            throw new IllegalArgumentException("A marker name cannot be null");
        }
        this.name = string;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void add(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("A null value cannot be added to a Marker as reference.");
        }
        if (this.contains(marker)) {
            return;
        }
        if (marker.contains(this)) {
            return;
        }
        this.referenceList.add(marker);
    }

    @Override
    public boolean hasReferences() {
        return this.referenceList.size() > 0;
    }

    @Override
    @Deprecated
    public boolean hasChildren() {
        return this.hasReferences();
    }

    @Override
    public Iterator<Marker> iterator() {
        return this.referenceList.iterator();
    }

    @Override
    public boolean remove(Marker marker) {
        return this.referenceList.remove(marker);
    }

    @Override
    public boolean contains(Marker marker) {
        if (marker == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (this.equals(marker)) {
            return false;
        }
        if (this.hasReferences()) {
            for (Marker marker2 : this.referenceList) {
                if (!marker2.contains(marker)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }
        if (this.name.equals(string)) {
            return false;
        }
        if (this.hasReferences()) {
            for (Marker marker : this.referenceList) {
                if (!marker.contains(string)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof Marker)) {
            return true;
        }
        Marker marker = (Marker)object;
        return this.name.equals(marker.getName());
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        if (!this.hasReferences()) {
            return this.getName();
        }
        Iterator<Marker> iterator2 = this.iterator();
        StringBuilder stringBuilder = new StringBuilder(this.getName());
        stringBuilder.append(' ').append(OPEN);
        while (iterator2.hasNext()) {
            Marker marker = iterator2.next();
            stringBuilder.append(marker.getName());
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(SEP);
        }
        stringBuilder.append(CLOSE);
        return stringBuilder.toString();
    }
}

