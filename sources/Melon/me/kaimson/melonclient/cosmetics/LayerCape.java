package me.kaimson.melonclient.cosmetics;

public class LayerCape extends bbo implements blb<pr>
{
    private final bct bipedCape;
    
    public LayerCape() {
        (this.bipedCape = new bct((bbo)this, 0, 0).b(22, 17).a(0, 0)).a(-5.0f, 0.0f, -1.0f, 10, 16, 1);
    }
    
    public void a(final pr entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        final bet player = (bet)entitylivingbaseIn;
        if (player.a() && !player.ax() && player.a(wo.a) && CosmeticManager.hasCape(player.aK().toString())) {
            this.a((pk)entitylivingbaseIn, p_177141_2_, p_177141_3_, 0.0f, p_177141_5_, partialTicks, scale);
        }
    }
    
    public void a(final pk entityIn, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float partialTicks, final float scale) {
        final bet entitylivingbaseIn = (bet)entityIn;
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        ave.A().P().a(CosmeticManager.cosmetics.get(entitylivingbaseIn.aK().toString()).getCapeTexture());
        bfl.E();
        bfl.b(0.0f, 0.0f, 0.125f);
        final double d0 = entitylivingbaseIn.bq + (entitylivingbaseIn.bt - entitylivingbaseIn.bq) * partialTicks - (entitylivingbaseIn.p + (entitylivingbaseIn.s - entitylivingbaseIn.p) * partialTicks);
        final double d2 = entitylivingbaseIn.br + (entitylivingbaseIn.bu - entitylivingbaseIn.br) * partialTicks - (entitylivingbaseIn.q + (entitylivingbaseIn.t - entitylivingbaseIn.q) * partialTicks);
        final double d3 = entitylivingbaseIn.bs + (entitylivingbaseIn.bv - entitylivingbaseIn.bs) * partialTicks - (entitylivingbaseIn.r + (entitylivingbaseIn.u - entitylivingbaseIn.r) * partialTicks);
        final float f = entitylivingbaseIn.aJ + (entitylivingbaseIn.aI - entitylivingbaseIn.aJ) * partialTicks;
        final double d4 = ns.a(f * 3.1415927f / 180.0f);
        final double d5 = -ns.b(f * 3.1415927f / 180.0f);
        float f2 = (float)d2 * 10.0f;
        f2 = ns.a(f2, -6.0f, 32.0f);
        float f3 = (float)(d0 * d4 + d3 * d5) * 100.0f;
        final float f4 = (float)(d0 * d5 - d3 * d4) * 100.0f;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f3 > 165.0f) {
            f3 = 165.0f;
        }
        if (f2 < -5.0f) {
            f2 = -5.0f;
        }
        final float f5 = entitylivingbaseIn.bn + (entitylivingbaseIn.bo - entitylivingbaseIn.bn) * partialTicks;
        f2 += ns.a((entitylivingbaseIn.L + (entitylivingbaseIn.M - entitylivingbaseIn.L) * partialTicks) * 6.0f) * 32.0f * f5;
        if (entitylivingbaseIn.av()) {
            f2 += 25.0f;
            bfl.b(0.0f, 0.142f, -0.0178f);
        }
        bfl.b(6.0f + f3 / 2.0f + f2, 1.0f, 0.0f, 0.0f);
        bfl.b(f4 / 2.0f, 0.0f, 0.0f, 1.0f);
        bfl.b(-f4 / 2.0f, 0.0f, 1.0f, 0.0f);
        bfl.b(180.0f, 0.0f, 1.0f, 0.0f);
        this.bipedCape.a(scale);
        bfl.F();
    }
    
    public boolean b() {
        return false;
    }
}
