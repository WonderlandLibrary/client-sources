/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.server.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;

public class StatsComponent
extends JComponent {
    private static final DecimalFormat FORMATTER = Util.make(new DecimalFormat("########0.000"), StatsComponent::lambda$static$0);
    private final int[] values = new int[256];
    private int vp;
    private final String[] msgs = new String[11];
    private final MinecraftServer server;
    private final Timer field_219054_f;

    public StatsComponent(MinecraftServer minecraftServer) {
        this.server = minecraftServer;
        this.setPreferredSize(new Dimension(456, 246));
        this.setMinimumSize(new Dimension(456, 246));
        this.setMaximumSize(new Dimension(456, 246));
        this.field_219054_f = new Timer(500, this::lambda$new$1);
        this.field_219054_f.start();
        this.setBackground(Color.BLACK);
    }

    private void tick() {
        long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        this.msgs[0] = "Memory use: " + l / 1024L / 1024L + " mb (" + Runtime.getRuntime().freeMemory() * 100L / Runtime.getRuntime().maxMemory() + "% free)";
        this.msgs[1] = "Avg tick: " + FORMATTER.format(this.mean(this.server.tickTimeArray) * 1.0E-6) + " ms";
        this.values[this.vp++ & 0xFF] = (int)(l * 100L / Runtime.getRuntime().maxMemory());
        this.repaint();
    }

    private double mean(long[] lArray) {
        long l = 0L;
        for (long l2 : lArray) {
            l += l2;
        }
        return (double)l / (double)lArray.length;
    }

    @Override
    public void paint(Graphics graphics) {
        int n;
        graphics.setColor(new Color(0xFFFFFF));
        graphics.fillRect(0, 0, 456, 246);
        for (n = 0; n < 256; ++n) {
            int n2 = this.values[n + this.vp & 0xFF];
            graphics.setColor(new Color(n2 + 28 << 16));
            graphics.fillRect(n, 100 - n2, 1, n2);
        }
        graphics.setColor(Color.BLACK);
        for (n = 0; n < this.msgs.length; ++n) {
            String string = this.msgs[n];
            if (string == null) continue;
            graphics.drawString(string, 32, 116 + n * 16);
        }
    }

    public void func_219053_a() {
        this.field_219054_f.stop();
    }

    private void lambda$new$1(ActionEvent actionEvent) {
        this.tick();
    }

    private static void lambda$static$0(DecimalFormat decimalFormat) {
        decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
    }
}

