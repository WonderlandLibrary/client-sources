package dev.luvbeeq.via;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import dev.excellent.client.screen.widget.VersionSelectScreen;
import i.gishreloaded.deadcode.api.DeadCodeConstants;
import lombok.Data;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

@Data
public class ViaMCP {
    public final int NATIVE_VERSION = 754;
    public final List<ProtocolVersion> PROTOCOLS;
    public final VersionSelectScreen viaScreen;


    public ViaMCP() {
        List<ProtocolVersion> protocolList = ProtocolVersion.getProtocols()
                .stream()
                .filter(pv -> pv.getVersion() == 47 || pv.getVersion() >= 107)
                .sorted((f, s) -> Integer.compare(s.getVersion(), f.getVersion()))
                .toList();

        PROTOCOLS = new ArrayList<>(protocolList.size() + 1);
        PROTOCOLS.addAll(protocolList);

        ViaLoadingBase.ViaLoadingBaseBuilder.create().runDirectory(DeadCodeConstants.MAIN_DIRECTORY).nativeVersion(NATIVE_VERSION).build();
        viaScreen = new VersionSelectScreen(new Vector2f(7, 7));

    }

}