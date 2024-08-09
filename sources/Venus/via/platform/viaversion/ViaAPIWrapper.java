/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform.viaversion;

import com.viaversion.viaversion.ViaAPIBase;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class ViaAPIWrapper
extends ViaAPIBase<UUID> {
    @Override
    public void sendRawPacket(Object object, ByteBuf byteBuf) {
        super.sendRawPacket((UUID)object, byteBuf);
    }

    @Override
    public int getPlayerVersion(Object object) {
        return super.getPlayerVersion((UUID)object);
    }
}

