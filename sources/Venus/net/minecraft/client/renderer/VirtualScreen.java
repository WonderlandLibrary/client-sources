/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import javax.annotation.Nullable;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Monitor;
import net.minecraft.client.renderer.MonitorHandler;
import net.minecraft.client.renderer.ScreenSize;

public final class VirtualScreen
implements AutoCloseable {
    private final Minecraft mc;
    private final MonitorHandler monitorHandler;

    public VirtualScreen(Minecraft minecraft) {
        this.mc = minecraft;
        this.monitorHandler = new MonitorHandler(Monitor::new);
    }

    public MainWindow create(ScreenSize screenSize, @Nullable String string, String string2) {
        return new MainWindow(this.mc, this.monitorHandler, screenSize, string, string2);
    }

    @Override
    public void close() {
        this.monitorHandler.close();
    }
}

