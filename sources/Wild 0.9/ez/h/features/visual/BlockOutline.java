package ez.h.features.visual;

import java.awt.*;
import org.lwjgl.opengl.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class BlockOutline extends Feature
{
    OptionColor color;
    
    void drawOutline(final bhb bhb, final float n, final Color color) {
        GL11.glPushMatrix();
        GL11.glLineWidth(n);
        GL11.glDisable(290 + 2819 - 502 + 946);
        GL11.glDisable(450 + 2112 + 213 + 154);
        final bve a = bve.a();
        final buk c = a.c();
        c.a(3, cdy.f);
        c.b(bhb.a, bhb.b, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xEF ^ 0x8B).d();
        c.b(bhb.a, bhb.b, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0x60 ^ 0x4).d();
        c.b(bhb.d, bhb.b, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0xDD ^ 0xB9).d();
        c.b(bhb.d, bhb.b, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xE8 ^ 0x8C).d();
        c.b(bhb.a, bhb.b, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xEB ^ 0x8F).d();
        c.b(bhb.a, bhb.e, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xEE ^ 0x8A).d();
        c.b(bhb.a, bhb.e, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0x29 ^ 0x4D).d();
        c.b(bhb.a, bhb.b, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0x2E ^ 0x4A).d();
        c.b(bhb.d, bhb.b, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0xCE ^ 0xAA).d();
        c.b(bhb.d, bhb.e, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0x7B ^ 0x1F).d();
        c.b(bhb.a, bhb.e, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0xEC ^ 0x88).d();
        c.b(bhb.d, bhb.e, bhb.f).b(color.getRed(), color.getGreen(), color.getBlue(), 0x30 ^ 0x54).d();
        c.b(bhb.d, bhb.e, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xE0 ^ 0x84).d();
        c.b(bhb.d, bhb.b, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0x7C ^ 0x18).d();
        c.b(bhb.d, bhb.e, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xD6 ^ 0xB2).d();
        c.b(bhb.a, bhb.e, bhb.c).b(color.getRed(), color.getGreen(), color.getBlue(), 0xCE ^ 0xAA).d();
        a.b();
        GL11.glEnable(1167 + 3105 - 2886 + 2167);
        GL11.glEnable(1430 + 1788 - 391 + 102);
        GL11.glPopMatrix();
    }
    
    public BlockOutline() {
        super("BlockOutline", "\u041e\u0431\u0432\u043e\u0434\u0438\u0442 \u0432\u044b\u0431\u0440\u0430\u043d\u043d\u044b\u0439 \u0431\u043b\u043e\u043a.", Category.VISUAL);
        this.color = new OptionColor(this, "Color", new Color(0x44 ^ 0x50, 171 + 8 - 169 + 190, 57 + 170 - 88 + 61), true);
        this.addOptions(this.color);
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D eventRender3D) {
        if (BlockOutline.mc.h == null || BlockOutline.mc.f == null) {
            return;
        }
        if (BlockOutline.mc.s == null) {
            return;
        }
        if (BlockOutline.mc.s.a() == null) {
            return;
        }
        if (BlockOutline.mc.f.o(BlockOutline.mc.s.a()) == null) {
            return;
        }
        if (BlockOutline.mc.f.o(BlockOutline.mc.s.a()).u() != aox.a && BlockOutline.mc.f.o(BlockOutline.mc.s.a()).u().l(BlockOutline.mc.f.o(BlockOutline.mc.s.a()))) {
            final double[] array = { BlockOutline.mc.s.a().p() - BlockOutline.mc.ac().o, BlockOutline.mc.s.a().q() - BlockOutline.mc.ac().p, BlockOutline.mc.s.a().r() - BlockOutline.mc.ac().q };
            this.drawOutline(new bhb(array[0], array[1] + 1.0, array[2], array[0] + 1.0, array[1] + 1.0, array[2] + 1.0), 1.7f, this.color.getColor());
        }
    }
}
