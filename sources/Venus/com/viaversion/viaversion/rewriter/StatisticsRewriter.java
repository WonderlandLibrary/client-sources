/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StatisticsRewriter<C extends ClientboundPacketType> {
    private static final int CUSTOM_STATS_CATEGORY = 8;
    private final Protocol<C, ?, ?, ?> protocol;

    public StatisticsRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void register(C c) {
        this.protocol.registerClientbound(c, this::lambda$register$0);
    }

    protected @Nullable IdRewriteFunction getRewriter(RegistryType registryType) {
        switch (1.$SwitchMap$com$viaversion$viaversion$api$minecraft$RegistryType[registryType.ordinal()]) {
            case 1: {
                return this.protocol.getMappingData().getBlockMappings() != null ? this::lambda$getRewriter$1 : null;
            }
            case 2: {
                return this.protocol.getMappingData().getItemMappings() != null ? this::lambda$getRewriter$2 : null;
            }
            case 3: {
                return this.protocol.getEntityRewriter() != null ? this::lambda$getRewriter$3 : null;
            }
        }
        throw new IllegalArgumentException("Unknown registry type in statistics packet: " + (Object)((Object)registryType));
    }

    public @Nullable RegistryType getRegistryTypeForStatistic(int n) {
        switch (n) {
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

    private int lambda$getRewriter$3(int n) {
        return this.protocol.getEntityRewriter().newEntityId(n);
    }

    private int lambda$getRewriter$2(int n) {
        return this.protocol.getMappingData().getNewItemId(n);
    }

    private int lambda$getRewriter$1(int n) {
        return this.protocol.getMappingData().getNewBlockId(n);
    }

    private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = n = packetWrapper.passthrough(Type.VAR_INT).intValue();
        for (int i = 0; i < n; ++i) {
            int n3 = packetWrapper.read(Type.VAR_INT);
            int n4 = packetWrapper.read(Type.VAR_INT);
            int n5 = packetWrapper.read(Type.VAR_INT);
            if (n3 == 8 && this.protocol.getMappingData().getStatisticsMappings() != null) {
                n4 = this.protocol.getMappingData().getStatisticsMappings().getNewId(n4);
                if (n4 == -1) {
                    --n2;
                    continue;
                }
            } else {
                IdRewriteFunction idRewriteFunction;
                RegistryType registryType = this.getRegistryTypeForStatistic(n3);
                if (registryType != null && (idRewriteFunction = this.getRewriter(registryType)) != null) {
                    n4 = idRewriteFunction.rewrite(n4);
                }
            }
            packetWrapper.write(Type.VAR_INT, n3);
            packetWrapper.write(Type.VAR_INT, n4);
            packetWrapper.write(Type.VAR_INT, n5);
        }
        if (n2 != n) {
            packetWrapper.set(Type.VAR_INT, 0, n2);
        }
    }
}

