/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StatisticsRewriter {
    private final Protocol protocol;
    private final int customStatsCategory = 8;

    public StatisticsRewriter(Protocol protocol) {
        this.protocol = protocol;
    }

    public void register(ClientboundPacketType packetType) {
        this.protocol.registerClientbound(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int size;
                    int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
                    int i = 0;
                    while (true) {
                        block5: {
                            int value;
                            int statisticId;
                            int categoryId;
                            block4: {
                                IdRewriteFunction statisticsRewriter;
                                block3: {
                                    if (i >= size) {
                                        if (newSize == size) return;
                                        wrapper.set(Type.VAR_INT, 0, newSize);
                                        return;
                                    }
                                    categoryId = wrapper.read(Type.VAR_INT);
                                    statisticId = wrapper.read(Type.VAR_INT);
                                    value = wrapper.read(Type.VAR_INT);
                                    if (categoryId != 8 || StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings() == null) break block3;
                                    statisticId = StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
                                    if (statisticId != -1) break block4;
                                    --newSize;
                                    break block5;
                                }
                                RegistryType type = StatisticsRewriter.this.getRegistryTypeForStatistic(categoryId);
                                if (type != null && (statisticsRewriter = StatisticsRewriter.this.getRewriter(type)) != null) {
                                    statisticId = statisticsRewriter.rewrite(statisticId);
                                }
                            }
                            wrapper.write(Type.VAR_INT, categoryId);
                            wrapper.write(Type.VAR_INT, statisticId);
                            wrapper.write(Type.VAR_INT, value);
                        }
                        ++i;
                    }
                });
            }
        });
    }

    protected @Nullable IdRewriteFunction getRewriter(RegistryType type) {
        switch (2.$SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[type.ordinal()]) {
            case 1: {
                if (this.protocol.getMappingData().getBlockMappings() == null) return null;
                IdRewriteFunction idRewriteFunction = id -> this.protocol.getMappingData().getNewBlockId(id);
                return idRewriteFunction;
            }
            case 2: {
                if (this.protocol.getMappingData().getItemMappings() == null) return null;
                IdRewriteFunction idRewriteFunction = id -> this.protocol.getMappingData().getNewItemId(id);
                return idRewriteFunction;
            }
            case 3: {
                if (this.protocol.getEntityRewriter() == null) return null;
                IdRewriteFunction idRewriteFunction = id -> this.protocol.getEntityRewriter().newEntityId(id);
                return idRewriteFunction;
            }
        }
        throw new IllegalArgumentException("Unknown registry type in statistics packet: " + (Object)((Object)type));
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

