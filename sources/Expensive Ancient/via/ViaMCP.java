/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import im.expensive.Expensive;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViaMCP {
    public final int NATIVE_VERSION = 754;
    public final List<ProtocolVersion> PROTOCOLS;
    public final VersionSelectScreen viaScreen;

    public ViaMCP() {
        List<ProtocolVersion> protocolList = ProtocolVersion.getProtocols().stream().filter(pv -> pv.getVersion() == 47 || pv.getVersion() >= 107).sorted((f, s) -> Integer.compare(s.getVersion(), f.getVersion())).toList();
        this.PROTOCOLS = new ArrayList<ProtocolVersion>(protocolList.size() + 1);
        this.PROTOCOLS.addAll(protocolList);
        ViaLoadingBase.ViaLoadingBaseBuilder.create().runDirectory(new File(Expensive.getInstance().getFilesDir().getName())).nativeVersion(754).build();
        this.viaScreen = new VersionSelectScreen(Minecraft.getInstance().fontRenderer, 5,5,100,20, ITextComponent.getTextComponentOrEmpty("1.16.5"));
    }

    public int getNATIVE_VERSION() {
        return this.NATIVE_VERSION;
    }

    public List<ProtocolVersion> getPROTOCOLS() {
        return this.PROTOCOLS;
    }

    public VersionSelectScreen getViaScreen() {
        return this.viaScreen;
    }
}

