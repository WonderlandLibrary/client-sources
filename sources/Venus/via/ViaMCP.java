/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import via.VersionSelectScreen;
import via.ViaLoadingBase;

public class ViaMCP {
    public final int NATIVE_VERSION = 754;
    public final List<ProtocolVersion> PROTOCOLS;
    public final VersionSelectScreen viaScreen;

    public ViaMCP() {
        List list = ProtocolVersion.getProtocols().stream().filter(ViaMCP::lambda$new$0).sorted(ViaMCP::lambda$new$1).toList();
        this.PROTOCOLS = new ArrayList<ProtocolVersion>(list.size() + 1);
        this.PROTOCOLS.addAll(list);
        ViaLoadingBase.ViaLoadingBaseBuilder.create().runDirectory(new File(venusfr.getInstance().getFilesDir().getName())).nativeVersion(754).build();
        this.viaScreen = new VersionSelectScreen(Minecraft.getInstance().fontRenderer, 5, 5, 100, 20, ITextComponent.getTextComponentOrEmpty("1.16.5"));
    }

    public int getNATIVE_VERSION() {
        Objects.requireNonNull(this);
        return 1;
    }

    public List<ProtocolVersion> getPROTOCOLS() {
        return this.PROTOCOLS;
    }

    public VersionSelectScreen getViaScreen() {
        return this.viaScreen;
    }

    private static int lambda$new$1(ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
        return Integer.compare(protocolVersion2.getVersion(), protocolVersion.getVersion());
    }

    private static boolean lambda$new$0(ProtocolVersion protocolVersion) {
        return protocolVersion.getVersion() == 47 || protocolVersion.getVersion() >= 107;
    }
}

