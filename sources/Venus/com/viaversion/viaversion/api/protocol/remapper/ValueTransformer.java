/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.exception.InformativeException;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class ValueTransformer<T1, T2>
implements ValueWriter<T1> {
    private final Type<T1> inputType;
    private final Type<T2> outputType;

    protected ValueTransformer(@Nullable Type<T1> type, Type<T2> type2) {
        this.inputType = type;
        this.outputType = type2;
    }

    protected ValueTransformer(Type<T2> type) {
        this(null, type);
    }

    public abstract T2 transform(PacketWrapper var1, T1 var2) throws Exception;

    @Override
    public void write(PacketWrapper packetWrapper, T1 T1) throws Exception {
        try {
            packetWrapper.write(this.outputType, this.transform(packetWrapper, T1));
        } catch (InformativeException informativeException) {
            informativeException.addSource(this.getClass());
            throw informativeException;
        }
    }

    public @Nullable Type<T1> getInputType() {
        return this.inputType;
    }

    public Type<T2> getOutputType() {
        return this.outputType;
    }
}

