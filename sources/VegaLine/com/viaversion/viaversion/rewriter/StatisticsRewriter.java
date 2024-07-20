/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StatisticsRewriter<C extends ClientboundPacketType> {
    private static final int CUSTOM_STATS_CATEGORY = 8;
    private final Protocol<C, ?, ?, ?> protocol;

    public StatisticsRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void register(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                int categoryId = wrapper.read(Type.VAR_INT);
                int statisticId = wrapper.read(Type.VAR_INT);
                int value = wrapper.read(Type.VAR_INT);
                if (categoryId == 8 && this.protocol.getMappingData().getStatisticsMappings() != null) {
                    statisticId = this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
                    if (statisticId == -1) {
                        --newSize;
                        continue;
                    }
                } else {
                    IdRewriteFunction statisticsRewriter;
                    RegistryType type2 = this.getRegistryTypeForStatistic(categoryId);
                    if (type2 != null && (statisticsRewriter = this.getRewriter(type2)) != null) {
                        statisticId = statisticsRewriter.rewrite(statisticId);
                    }
                }
                wrapper.write(Type.VAR_INT, categoryId);
                wrapper.write(Type.VAR_INT, statisticId);
                wrapper.write(Type.VAR_INT, value);
            }
            if (newSize != size) {
                wrapper.set(Type.VAR_INT, 0, newSize);
            }
        });
    }

    protected @Nullable IdRewriteFunction getRewriter(RegistryType type2) {
        switch (type2) {
            case BLOCK: {
                return this.protocol.getMappingData().getBlockMappings() != null ? id -> this.protocol.getMappingData().getNewBlockId(id) : null;
            }
            case ITEM: {
                return this.protocol.getMappingData().getItemMappings() != null ? id -> this.protocol.getMappingData().getNewItemId(id) : null;
            }
            case ENTITY: {
                return this.protocol.getEntityRewriter() != null ? id -> this.protocol.getEntityRewriter().newEntityId(id) : null;
            }
        }
        throw new IllegalArgumentException("Unknown registry type in statistics packet: " + (Object)((Object)type2));
    }

    public @Nullable RegistryType getRegistryTypeForStatistic(int statisticsId) {
        switch (statisticsId) {
            case 0: {
                return RegistryType.BLOCK;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                return RegistryType.ITEM;
            }
            case 6: 
            case 7: {
                return RegistryType.ENTITY;
            }
        }
        return null;
    }
}

