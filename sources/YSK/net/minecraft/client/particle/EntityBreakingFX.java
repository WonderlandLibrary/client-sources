package net.minecraft.client.particle;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class EntityBreakingFX extends EntityFX
{
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final double n4, final double n5, final double n6, final Item item, final int n7) {
        this(world, n, n2, n3, item, n7);
        this.motionX *= 0.10000000149011612;
        this.motionY *= 0.10000000149011612;
        this.motionZ *= 0.10000000149011612;
        this.motionX += n4;
        this.motionY += n5;
        this.motionZ += n6;
    }
    
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final Item item) {
        this(world, n, n2, n3, item, "".length());
    }
    
    protected EntityBreakingFX(final World world, final double n, final double n2, final double n3, final Item item, final int n4) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, n4));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        this.particleGravity = Blocks.snow.blockParticleGravity;
        this.particleScale /= 2.0f;
    }
    
    @Override
    public int getFXLayer() {
        return " ".length();
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
            if (4 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        float interpolatedU = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0f) / 16.0f;
        float interpolatedU2 = interpolatedU + 0.015609375f;
        float interpolatedV = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0f) / 16.0f;
        float interpolatedV2 = interpolatedV + 0.015609375f;
        final float n7 = 0.1f * this.particleScale;
        if (this.particleIcon != null) {
            interpolatedU = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0f * 16.0f);
            interpolatedU2 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f);
            interpolatedV = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0f * 16.0f);
            interpolatedV2 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f);
        }
        final float n8 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - EntityBreakingFX.interpPosX);
        final float n9 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - EntityBreakingFX.interpPosY);
        final float n10 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - EntityBreakingFX.interpPosZ);
        final int brightnessForRender = this.getBrightnessForRender(n);
        final int n11 = brightnessForRender >> (0x92 ^ 0x82) & 18206 + 15124 - 6299 + 38504;
        final int n12 = brightnessForRender & 37281 + 49739 - 22138 + 653;
        worldRenderer.pos(n8 - n2 * n7 - n5 * n7, n9 - n3 * n7, n10 - n4 * n7 - n6 * n7).tex(interpolatedU, interpolatedV2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 - n2 * n7 + n5 * n7, n9 + n3 * n7, n10 - n4 * n7 + n6 * n7).tex(interpolatedU, interpolatedV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 + n5 * n7, n9 + n3 * n7, n10 + n4 * n7 + n6 * n7).tex(interpolatedU2, interpolatedV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
        worldRenderer.pos(n8 + n2 * n7 - n5 * n7, n9 - n3 * n7, n10 + n4 * n7 - n6 * n7).tex(interpolatedU2, interpolatedV2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n11, n12).endVertex();
    }
    
    public static class SlimeFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBreakingFX(world, n2, n3, n4, Items.slime_ball);
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
                if (3 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
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
                if (4 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            int length;
            if (array.length > " ".length()) {
                length = array[" ".length()];
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
            else {
                length = "".length();
            }
            return new EntityBreakingFX(world, n2, n3, n4, n5, n6, n7, Item.getItemById(array["".length()]), length);
        }
    }
    
    public static class SnowballFactory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new EntityBreakingFX(world, n2, n3, n4, Items.snowball);
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
                if (4 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
