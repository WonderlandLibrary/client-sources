package net.minecraft.client.particle;

import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;

public class EntityLargeExplodeFX extends EntityFX
{
    private int field_70581_a;
    private float field_70582_as;
    private static final ResourceLocation EXPLOSION_TEXTURE;
    private TextureManager theRenderEngine;
    private static final String[] I;
    private int field_70584_aq;
    private static final VertexFormat field_181549_az;
    
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.field_70581_a += " ".length();
        if (this.field_70581_a == this.field_70584_aq) {
            this.setDead();
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("0?\u000f\u001c\u00016?\u0004G\u0011*.\u001e\u001c\rk?\u000f\u0018\u0018+)\u001e\u0007\u001aj*\u0019\u000f", "DZwht");
    }
    
    @Override
    public int getFXLayer() {
        return "   ".length();
    }
    
    static {
        I();
        EXPLOSION_TEXTURE = new ResourceLocation(EntityLargeExplodeFX.I["".length()]);
        field_181549_az = new VertexFormat().func_181721_a(DefaultVertexFormats.POSITION_3F).func_181721_a(DefaultVertexFormats.TEX_2F).func_181721_a(DefaultVertexFormats.COLOR_4UB).func_181721_a(DefaultVertexFormats.TEX_2S).func_181721_a(DefaultVertexFormats.NORMAL_3B).func_181721_a(DefaultVertexFormats.PADDING_1B);
    }
    
    protected EntityLargeExplodeFX(final TextureManager theRenderEngine, final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.theRenderEngine = theRenderEngine;
        this.field_70584_aq = (0x80 ^ 0x86) + this.rand.nextInt(0x7A ^ 0x7E);
        final float particleRed = this.rand.nextFloat() * 0.6f + 0.4f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.field_70582_as = 1.0f - (float)n4 * 0.5f;
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 1218 + 50959 + 6544 + 2959;
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final int n7 = (int)((this.field_70581_a + n) * 15.0f / this.field_70584_aq);
        if (n7 <= (0x8E ^ 0x81)) {
            this.theRenderEngine.bindTexture(EntityLargeExplodeFX.EXPLOSION_TEXTURE);
            final float n8 = n7 % (0x80 ^ 0x84) / 4.0f;
            final float n9 = n8 + 0.24975f;
            final float n10 = n7 / (0x7A ^ 0x7E) / 4.0f;
            final float n11 = n10 + 0.24975f;
            final float n12 = 2.0f * this.field_70582_as;
            final float n13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityLargeExplodeFX.interpPosX);
            final float n14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityLargeExplodeFX.interpPosY);
            final float n15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityLargeExplodeFX.interpPosZ);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableLighting();
            RenderHelper.disableStandardItemLighting();
            worldRenderer.begin(0x97 ^ 0x90, EntityLargeExplodeFX.field_181549_az);
            worldRenderer.pos(n13 - n2 * n12 - n5 * n12, n14 - n3 * n12, n15 - n4 * n12 - n6 * n12).tex(n9, n11).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap("".length(), 6 + 36 + 143 + 55).normal(0.0f, 1.0f, 0.0f).endVertex();
            worldRenderer.pos(n13 - n2 * n12 + n5 * n12, n14 + n3 * n12, n15 - n4 * n12 + n6 * n12).tex(n9, n10).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap("".length(), 79 + 70 + 62 + 29).normal(0.0f, 1.0f, 0.0f).endVertex();
            worldRenderer.pos(n13 + n2 * n12 + n5 * n12, n14 + n3 * n12, n15 + n4 * n12 + n6 * n12).tex(n8, n10).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap("".length(), 126 + 82 - 155 + 187).normal(0.0f, 1.0f, 0.0f).endVertex();
            worldRenderer.pos(n13 + n2 * n12 - n5 * n12, n14 - n3 * n12, n15 + n4 * n12 - n6 * n12).tex(n8, n11).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap("".length(), 219 + 224 - 241 + 38).normal(0.0f, 1.0f, 0.0f).endVertex();
            Tessellator.getInstance().draw();
            GlStateManager.enableLighting();
        }
    }
    
    public static class Factory implements IParticleFactory
    {
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), world, n2, n3, n4, n5, n6, n7);
        }
    }
}
