/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api;

import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class BackwardsProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
extends AbstractProtocol<CU, CM, SM, SU> {
    protected BackwardsProtocol() {
    }

    protected BackwardsProtocol(@Nullable Class<CU> clazz, @Nullable Class<CM> clazz2, @Nullable Class<SM> clazz3, @Nullable Class<SU> clazz4) {
        super(clazz, clazz2, clazz3, clazz4);
    }

    protected void executeAsyncAfterLoaded(Class<? extends Protocol> clazz, Runnable runnable) {
        Via.getManager().getProtocolManager().addMappingLoaderFuture(this.getClass(), clazz, runnable);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        BackwardsMappings backwardsMappings = this.getMappingData();
        if (backwardsMappings != null && backwardsMappings.getViaVersionProtocolClass() != null) {
            this.executeAsyncAfterLoaded(backwardsMappings.getViaVersionProtocolClass(), this::loadMappingData);
        }
    }

    @Override
    public boolean hasMappingDataToLoad() {
        return true;
    }

    @Override
    public @Nullable BackwardsMappings getMappingData() {
        return null;
    }

    public @Nullable TranslatableRewriter<CU> getTranslatableRewriter() {
        return null;
    }

    @Override
    public @Nullable MappingData getMappingData() {
        return this.getMappingData();
    }
}

