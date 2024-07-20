/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package dev.intave.viamcp;

import dev.intave.ViaInstalatorPanel;
import dev.intave.vialoadingbase.ViaLoadingBase;
import java.io.File;

public class ViaMCP {
    public static final int NATIVE_VERSION = 340;
    public static ViaMCP INSTANCE;
    private final ViaInstalatorPanel viaPanel;

    public static void create() {
        INSTANCE = new ViaMCP();
    }

    public ViaMCP() {
        ViaLoadingBase.ViaLoadingBaseBuilder.create().runDirectory(new File("ViaMCP")).nativeVersion(340).build();
        this.viaPanel = new ViaInstalatorPanel(15.0f, 15.0f);
    }

    public ViaInstalatorPanel getViaPanel() {
        return this.viaPanel == null ? new ViaInstalatorPanel(0.0f, 0.0f) : this.viaPanel;
    }
}

