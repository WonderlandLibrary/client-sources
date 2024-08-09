/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform.viaversion;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.libs.fastutil.ints.IntLinkedOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSortedSet;
import com.viaversion.viaversion.libs.gson.JsonObject;

public class ViaInjector
implements com.viaversion.viaversion.api.platform.ViaInjector {
    @Override
    public void inject() {
    }

    @Override
    public void uninject() {
    }

    @Override
    public String getDecoderName() {
        return "via-decoder";
    }

    @Override
    public String getEncoderName() {
        return "via-encoder";
    }

    @Override
    public IntSortedSet getServerProtocolVersions() {
        IntLinkedOpenHashSet intLinkedOpenHashSet = new IntLinkedOpenHashSet();
        for (ProtocolVersion protocolVersion : ProtocolVersion.getProtocols()) {
            if (protocolVersion.getVersion() < ProtocolVersion.v1_7_1.getVersion()) continue;
            intLinkedOpenHashSet.add(protocolVersion.getVersion());
        }
        return intLinkedOpenHashSet;
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

