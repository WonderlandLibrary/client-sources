package me.kaimson.melonclient.features.modules;

import me.kaimson.melonclient.features.*;
import me.kaimson.melonclient.utils.*;
import java.awt.*;

public class BlockOverlayModule extends Module
{
    public static BlockOverlayModule INSTANCE;
    public final Setting outlineColor;
    public final Setting outlineWidth;
    public final Setting ignoreDepth;
    public final Setting persistent;
    public final Setting fill;
    public final Setting fillColor;
    
    public BlockOverlayModule() {
        super("Block Overlay", 14);
        new Setting(this, "Outline Options");
        this.outlineColor = new Setting(this, "Color").setDefault(new Color(0, 0, 0, 100).getRGB(), 0);
        this.outlineWidth = new Setting(this, "Width").setDefault(2.0f).setRange(0.5f, 5.0f, 0.5f);
        this.ignoreDepth = new Setting(this, "Ignore Depth").setDefault(false);
        this.persistent = new Setting(this, "Persistent").setDefault(false);
        new Setting(this, "Fill Options");
        this.fill = new Setting(this, "Fill").setDefault(false);
        this.fillColor = new Setting(this, "Fill Color").setDefault(new Color(0, 0, 0, 150).getRGB(), 0);
        BlockOverlayModule.INSTANCE = this;
    }
    
    public void drawFilledWithGL(final aug box) {
        final bfx tessellator = bfx.a();
        final bfd worldRenderer = tessellator.c();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        worldRenderer.b(box.d, box.e, box.c).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.a, box.e, box.f).d();
        tessellator.b();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.d, box.e, box.c).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.a, box.e, box.f).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        tessellator.b();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.d, box.e, box.c).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.a, box.e, box.f).d();
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.a, box.e, box.f).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.d, box.e, box.c).d();
        tessellator.b();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        tessellator.b();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.a, box.e, box.f).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        worldRenderer.b(box.d, box.e, box.c).d();
        tessellator.b();
        worldRenderer.a(7, bms.e);
        worldRenderer.b(box.a, box.e, box.f).d();
        worldRenderer.b(box.a, box.b, box.f).d();
        worldRenderer.b(box.a, box.e, box.c).d();
        worldRenderer.b(box.a, box.b, box.c).d();
        worldRenderer.b(box.d, box.e, box.c).d();
        worldRenderer.b(box.d, box.b, box.c).d();
        worldRenderer.b(box.d, box.e, box.f).d();
        worldRenderer.b(box.d, box.b, box.f).d();
        tessellator.b();
    }
}
