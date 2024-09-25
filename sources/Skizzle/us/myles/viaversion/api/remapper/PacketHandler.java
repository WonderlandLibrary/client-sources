/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.remapper;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.ValueWriter;
import us.myles.ViaVersion.exception.InformativeException;

@FunctionalInterface
public interface PacketHandler
extends ValueWriter {
    public void handle(PacketWrapper var1) throws Exception;

    default public void write(PacketWrapper writer, Object inputValue) throws Exception {
        try {
            this.handle(writer);
        }
        catch (InformativeException e) {
            e.addSource(this.getClass());
            throw e;
        }
    }
}

