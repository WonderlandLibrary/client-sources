package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.client.*;

public class EntityFootStepFX extends EntityFX
{
    private static final String[] I;
    private TextureManager currentFootSteps;
    private static final ResourceLocation FOOTPRINT_TEXTURE;
    private int footstepAge;
    private int footstepMaxAge;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0019$,\u001e\u0004\u001f$'E\u0001\f3 \u0003\u0012\u0001${\f\u001e\u00025$\u0018\u0018\u00035z\u001a\u001f\n", "mATjq");
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float n7 = (this.footstepAge + n) / this.footstepMaxAge;
        float n8 = 2.0f - n7 * n7 * 2.0f;
        if (n8 > 1.0f) {
            n8 = 1.0f;
        }
        final float n9 = n8 * 0.2f;
        GlStateManager.disableLighting();
        final float n10 = (float)(this.posX - EntityFootStepFX.interpPosX);
        final float n11 = (float)(this.posY - EntityFootStepFX.interpPosY);
        final float n12 = (float)(this.posZ - EntityFootStepFX.interpPosZ);
        final float lightBrightness = this.worldObj.getLightBrightness(new BlockPos(this));
        this.currentFootSteps.bindTexture(EntityFootStepFX.FOOTPRINT_TEXTURE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(442 + 174 - 213 + 367, 644 + 568 - 1161 + 720);
        worldRenderer.begin(0x81 ^ 0x86, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(n10 - 0.125f, n11, n12 + 0.125f).tex(0.0, 1.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 + 0.125f, n11, n12 + 0.125f).tex(1.0, 1.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 + 0.125f, n11, n12 - 0.125f).tex(1.0, 0.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        worldRenderer.pos(n10 - 0.125f, n11, n12 - 0.125f).tex(0.0, 0.0).color(lightBrightness, lightBrightness, lightBrightness, n9).endVertex();
        Tessellator.getInstance().draw();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        FOOTPRINT_TEXTURE = new ResourceLocation(EntityFootStepFX.I["".length()]);
    }
    
    @Override
    public void onUpdate() {
        this.footstepAge += " ".length();
        if (this.footstepAge == this.footstepMaxAge) {
            this.setDead();
        }
    }
    
    protected EntityFootStepFX(final TextureManager currentFootSteps, final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.currentFootSteps = currentFootSteps;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.footstepMaxAge = 143 + 103 - 92 + 46;
    }
    
    @Override
    public int getFXLayer() {
        return "   ".length();
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
                if (3 < 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), world, n2, n3, n4);
        }
    }
}
