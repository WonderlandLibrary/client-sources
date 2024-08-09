/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.markers.KMutableMap;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\b'\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0005J\u001f\u0010\u0006\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\b\u001a\u00028\u0001H&\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/collections/AbstractMutableMap;", "K", "V", "", "Ljava/util/AbstractMap;", "()V", "put", "key", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"})
@SinceKotlin(version="1.1")
public abstract class AbstractMutableMap<K, V>
extends AbstractMap<K, V>
implements Map<K, V>,
KMutableMap {
    protected AbstractMutableMap() {
    }

    @Override
    @Nullable
    public abstract V put(K var1, V var2);

    public abstract Set getEntries();

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.getEntries();
    }

    public Set<Object> getKeys() {
        return super.keySet();
    }

    @Override
    public final Set<K> keySet() {
        return this.getKeys();
    }

    public int getSize() {
        return super.size();
    }

    @Override
    public final int size() {
        return this.getSize();
    }

    public Collection<Object> getValues() {
        return super.values();
    }

    @Override
    public final Collection<V> values() {
        return this.getValues();
    }
}

