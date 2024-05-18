package net.minecraft.client.particle;

import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;

public class Barrier extends EntityFX
{
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderParticle(final WorldRenderer worldRenderer, final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        final float minU = this.particleIcon.getMinU();
        final float maxU = this.particleIcon.getMaxU();
        final float minV = this.particleIcon.getMinV();
        final float maxV = this.particleIcon.getMaxV();
        final float n7 = (float)(this.prevPosX + (this.posX - this.prevPosX) * n - Barrier.interpPosX);
        final float n8 = (float)(this.prevPosY + (this.posY - this.prevPosY) * n - Barrier.interpPosY);
        final float n9 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * n - Barrier.interpPosZ);
        final int brightnessForRender = this.getBrightnessForRender(n);
        final int n10 = brightnessForRender >> (0x43 ^ 0x53) & 3375 + 38380 + 3692 + 20088;
        final int n11 = brightnessForRender & 10388 + 43390 - 45027 + 56784;
        worldRenderer.pos(n7 - n2 * 0.5f - n5 * 0.5f, n8 - n3 * 0.5f, n9 - n4 * 0.5f - n6 * 0.5f).tex(maxU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n10, n11).endVertex();
        worldRenderer.pos(n7 - n2 * 0.5f + n5 * 0.5f, n8 + n3 * 0.5f, n9 - n4 * 0.5f + n6 * 0.5f).tex(maxU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n10, n11).endVertex();
        worldRenderer.pos(n7 + n2 * 0.5f + n5 * 0.5f, n8 + n3 * 0.5f, n9 + n4 * 0.5f + n6 * 0.5f).tex(minU, minV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n10, n11).endVertex();
        worldRenderer.pos(n7 + n2 * 0.5f - n5 * 0.5f, n8 - n3 * 0.5f, n9 + n4 * 0.5f - n6 * 0.5f).tex(minU, maxV).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f).lightmap(n10, n11).endVertex();
    }
    
    protected Barrier(final World world, final double n, final double n2, final double n3, final Item item) {
        super(world, n, n2, n3, 0.0, 0.0, 0.0);
        this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item));
        final float particleRed = 1.0f;
        this.particleBlue = particleRed;
        this.particleGreen = particleRed;
        this.particleRed = particleRed;
        final double motionX = 0.0;
        this.motionZ = motionX;
        this.motionY = motionX;
        this.motionX = motionX;
        this.particleGravity = 0.0f;
        this.particleMaxAge = (0x39 ^ 0x69);
    }
    
    public static class Factory implements IParticleFactory
    {
        @Override
        public EntityFX getEntityFX(final int n, final World world, final double n2, final double n3, final double n4, final double n5, final double n6, final double n7, final int... array) {
            return new Barrier(world, n2, n3, n4, Item.getItemFromBlock(Blocks.barrier));
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
                if (1 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
