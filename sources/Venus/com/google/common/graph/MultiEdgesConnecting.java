/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;

abstract class MultiEdgesConnecting<E>
extends AbstractSet<E> {
    private final Map<E, ?> outEdgeToNode;
    private final Object targetNode;

    MultiEdgesConnecting(Map<E, ?> map, Object object) {
        this.outEdgeToNode = Preconditions.checkNotNull(map);
        this.targetNode = Preconditions.checkNotNull(object);
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        Iterator<Map.Entry<E, ?>> iterator2 = this.outEdgeToNode.entrySet().iterator();
        return new AbstractIterator<E>(this, iterator2){
            final Iterator val$entries;
            final MultiEdgesConnecting this$0;
            {
                this.this$0 = multiEdgesConnecting;
                this.val$entries = iterator2;
            }

            @Override
            protected E computeNext() {
                while (this.val$entries.hasNext()) {
                    Map.Entry entry = (Map.Entry)this.val$entries.next();
                    if (!MultiEdgesConnecting.access$000(this.this$0).equals(entry.getValue())) continue;
                    return entry.getKey();
                }
                return this.endOfData();
            }
        };
    }

    @Override
    public boolean contains(@Nullable Object object) {
        return this.targetNode.equals(this.outEdgeToNode.get(object));
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    static Object access$000(MultiEdgesConnecting multiEdgesConnecting) {
        return multiEdgesConnecting.targetNode;
    }
}

