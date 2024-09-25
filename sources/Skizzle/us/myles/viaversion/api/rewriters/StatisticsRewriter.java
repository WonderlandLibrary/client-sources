/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.rewriters;

import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.IdRewriteFunction;
import us.myles.ViaVersion.api.rewriters.RegistryType;
import us.myles.ViaVersion.api.type.Type;

public class StatisticsRewriter {
    private final Protocol protocol;
    private final IdRewriteFunction entityRewriter;
    private final int customStatsCategory = 8;

    public StatisticsRewriter(Protocol protocol, @Nullable IdRewriteFunction entityRewriter) {
        this.protocol = protocol;
        this.entityRewriter = entityRewriter;
    }

    public void register(ClientboundPacketType packetType) {
        this.protocol.registerOutgoing(packetType, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(wrapper -> {
                    int size;
                    int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
                    for (int i = 0; i < size; ++i) {
                        int categoryId = wrapper.read(Type.VAR_INT);
                        int statisticId = wrapper.read(Type.VAR_INT);
                        int value = wrapper.read(Type.VAR_INT);
                        if (categoryId == 8 && StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings() != null) {
                            statisticId = StatisticsRewriter.this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
                            if (statisticId == -1) {
                                --newSize;
                                continue;
                            }
                        } else {
                            IdRewriteFunction statisticsRewriter;
                            RegistryType type = StatisticsRewriter.this.getRegistryTypeForStatistic(categoryId);
                            if (type != null && (statisticsRewriter = StatisticsRewriter.this.getRewriter(type)) != null) {
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
        });
    }

    @Nullable
    protected IdRewriteFunction getRewriter(RegistryType type) {
        switch (type) {
            case BLOCK: {
                return this.protocol.getMappingData().getBlockMappings() != null ? id -> this.protocol.getMappingData().getNewBlockId(id) : null;
            }
            case ITEM: {
                return this.protocol.getMappingData().getItemMappings() != null ? id -> this.protocol.getMappingData().getNewItemId(id) : null;
            }
            case ENTITY: {
                return this.entityRewriter;
            }
        }
        throw new IllegalArgumentException("Unknown registry type in statistics packet: " + (Object)((Object)type));
    }

    @Nullable
    public RegistryType getRegistryTypeForStatistic(int statisticsId) {
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

