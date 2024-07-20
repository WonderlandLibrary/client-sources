/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueReader;
import com.viaversion.viaversion.api.protocol.remapper.ValueWriter;
import com.viaversion.viaversion.api.type.Type;

public class TypeRemapper<T>
implements ValueReader<T>,
ValueWriter<T> {
    private final Type<T> type;

    public TypeRemapper(Type<T> type2) {
        this.type = type2;
    }

    @Override
    public T read(PacketWrapper wrapper) throws Exception {
        return wrapper.read(this.type);
    }

    @Override
    public void write(PacketWrapper output, T inputValue) {
        output.write(this.type, inputValue);
    }
}

