package com.shroomclient.shroomclientnextgen.util;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.viafabricplus.protocoltranslator.ProtocolTranslator;

public class ViaVersionUtil {

    public static void setProtocolVersion(ProtocolVersion protocolVersion) {
        ProtocolTranslator.setTargetVersion(protocolVersion);
    }
}
