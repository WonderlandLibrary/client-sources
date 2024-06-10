package me.kaimson.melonclient.utils;

import org.lwjgl.opengl.*;
import me.kaimson.melonclient.features.modules.*;
import me.kaimson.melonclient.features.*;

public class NametagRenderer
{
    public static void renderTag(final String tag, final double x, final double y, final double z) {
        final ave mc = ave.A();
        final pk entity = mc.af().c;
        if (entity != null && entity.f_() != null) {
            final avn fontrenderer = mc.k;
            final float f = 1.6f;
            final float f2 = 0.016666668f * f;
            bfl.E();
            bfl.b((float)x + 0.0f, (float)y + mc.h.K + 0.5f, (float)z);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            final float viewX = PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraPitch : mc.af().f;
            final float viewY = PerspectiveModule.INSTANCE.isHeld() ? PerspectiveModule.INSTANCE.cameraYaw : mc.af().e;
            bfl.b(-viewY, 0.0f, 1.0f, 0.0f);
            bfl.b((SettingsManager.INSTANCE.fixNametagRot.getBoolean() && mc.t.aB == 2) ? (-viewX) : viewX, 1.0f, 0.0f, 0.0f);
            bfl.a(-f2, -f2, f2);
            if (entity.av()) {
                bfl.b(0.0f, 9.374999f, 0.0f);
            }
            bfl.f();
            bfl.a(false);
            bfl.i();
            bfl.l();
            bfl.a(770, 771, 1, 0);
            final bfx tessellator = bfx.a();
            final bfd worldrenderer = tessellator.c();
            final int i = fontrenderer.a(tag) / 2;
            if (!SettingsManager.INSTANCE.transparentNametags.getBoolean()) {
                bfl.x();
                worldrenderer.a(7, bms.f);
                worldrenderer.b((double)(-i - 1), -1.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(-i - 1), 8.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(i + 1), 8.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                worldrenderer.b((double)(i + 1), -1.0, 0.0).a(0.0f, 0.0f, 0.0f, 0.25f).d();
                tessellator.b();
                bfl.w();
            }
            fontrenderer.a(tag, -fontrenderer.a(tag) / 2, 0, 553648127);
            bfl.j();
            bfl.a(true);
            if (!entity.av()) {
                fontrenderer.a(tag, -fontrenderer.a(tag) / 2, 0, -1);
            }
            else {
                fontrenderer.a(tag, -fontrenderer.a(tag) / 2, 0, 553648127);
            }
            bfl.e();
            bfl.k();
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            bfl.F();
        }
    }
    
    public static void render(final double x, double y, final double z) {
        final ave mc = ave.A();
        final auo scoreboard = mc.h.cp();
        final auk scoreobjective = scoreboard.a(2);
        if (scoreobjective != null && !mc.h.av()) {
            final aum score = scoreboard.c(mc.h.e_(), scoreobjective);
            renderTag(score.c() + " " + scoreobjective.d(), x, y, z);
            y += mc.k.a * 1.15f * 0.02666667;
        }
        renderTag((mc.h.aM() == null || mc.h.aM().isEmpty()) ? mc.h.f_().d() : mc.h.aM(), x, y, z);
    }
}
