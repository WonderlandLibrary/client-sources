/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.metadata;

import java.util.HashMap;
import java.util.Optional;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.metadata.MetaIndex;

public class MetaIndex1_8to1_9 {
    private static final HashMap<Pair<Entity1_10Types.EntityType, Integer>, MetaIndex> metadataRewrites = new HashMap();

    private static Optional<MetaIndex> getIndex(Entity1_10Types.EntityType type, int index) {
        Pair<Entity1_10Types.EntityType, Integer> pair = new Pair<Entity1_10Types.EntityType, Integer>(type, index);
        if (metadataRewrites.containsKey(pair)) {
            return Optional.of(metadataRewrites.get(pair));
        }
        return Optional.empty();
    }

    public static MetaIndex searchIndex(Entity1_10Types.EntityType type, int index) {
        Entity1_10Types.EntityType currentType = type;
        do {
            Optional<MetaIndex> optMeta;
            if (!(optMeta = MetaIndex1_8to1_9.getIndex(currentType, index)).isPresent()) continue;
            return optMeta.get();
        } while ((currentType = currentType.getParent()) != null);
        return null;
    }

    static {
        for (MetaIndex index : MetaIndex.values()) {
            metadataRewrites.put(new Pair<Entity1_10Types.EntityType, Integer>(index.getClazz(), index.getNewIndex()), index);
        }
    }
}

