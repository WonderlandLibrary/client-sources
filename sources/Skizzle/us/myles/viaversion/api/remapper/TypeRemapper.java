/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.remapper;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.ValueReader;
import us.myles.ViaVersion.api.remapper.ValueWriter;
import us.myles.ViaVersion.api.type.Type;

public class TypeRemapper<T>
implements ValueReader<T>,
ValueWriter<T> {
    private final Type<T> type;

    public TypeRemapper(Type<T> type) {
        this.type = type;
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

