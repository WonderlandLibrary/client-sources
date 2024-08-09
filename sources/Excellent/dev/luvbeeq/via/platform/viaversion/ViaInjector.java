package dev.luvbeeq.via.platform.viaversion;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;
import dev.luvbeeq.via.netty.ViaPipeLine;

public class ViaInjector implements com.viaversion.viaversion.api.platform.ViaInjector {

    @Override
    public void inject() {
    }

    @Override
    public void uninject() {
    }

    @Override
    public String getDecoderName() {
        return ViaPipeLine.VIA_DECODER_HANDLER_NAME;
    }

    @Override
    public String getEncoderName() {
        return ViaPipeLine.VIA_ENCODER_HANDLER_NAME;
    }

    @Override
    public IntSortedSet getServerProtocolVersions() {
        final IntSortedSet versions = new IntLinkedOpenHashSet();
        for (ProtocolVersion value : ProtocolVersion.getProtocols()) {
            if (value.getVersion() >= ProtocolVersion.v1_7_1.getVersion()) {
                versions.add(value.getVersion());
            }
        }

        return versions;
    }

    @Override
    public int getServerProtocolVersion() {
        return this.getServerProtocolVersions().firstInt();
    }

    @Override
    public JsonObject getDump() {
        return new JsonObject();
    }
}
