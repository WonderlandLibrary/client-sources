/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public abstract class TexturedParticle
extends Particle {
    protected float particleScale;

    protected TexturedParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
        this.particleScale = 0.1f * (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
    }

    protected TexturedParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.particleScale = 0.1f * (this.rand.nextFloat() * 0.5f + 0.5f) * 2.0f;
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
        Quaternion quaternion;
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        float f2 = (float)(MathHelper.lerp((double)f, this.prevPosX, this.posX) - vector3d.getX());
        float f3 = (float)(MathHelper.lerp((double)f, this.prevPosY, this.posY) - vector3d.getY());
        float f4 = (float)(MathHelper.lerp((double)f, this.prevPosZ, this.posZ) - vector3d.getZ());
        if (this.particleAngle == 0.0f) {
            quaternion = activeRenderInfo.getRotation();
        } else {
            quaternion = new Quaternion(activeRenderInfo.getRotation());
            float f5 = MathHelper.lerp(f, this.prevParticleAngle, this.particleAngle);
            quaternion.multiply(Vector3f.ZP.rotation(f5));
        }
        Vector3f vector3f = new Vector3f(-1.0f, -1.0f, 0.0f);
        vector3f.transform(quaternion);
        Vector3f[] vector3fArray = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float f6 = this.getScale(f);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f2 = vector3fArray[i];
            vector3f2.transform(quaternion);
            vector3f2.mul(f6);
            vector3f2.add(f2, f3, f4);
        }
        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f9 = this.getMinV();
        float f10 = this.getMaxV();
        int n = this.getBrightnessForRender(f);
        iVertexBuilder.pos(vector3fArray[0].getX(), vector3fArray[0].getY(), vector3fArray[0].getZ()).tex(f8, f10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n).endVertex();
        iVertexBuilder.pos(vector3fArray[5].getX(), vector3fArray[5].getY(), vector3fArray[5].getZ()).tex(f8, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n).endVertex();
        iVertexBuilder.pos(vector3fArray[5].getX(), vector3fArray[5].getY(), vector3fArray[5].getZ()).tex(f7, f9).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n).endVertex();
        iVertexBuilder.pos(vector3fArray[5].getX(), vector3fArray[5].getY(), vector3fArray[5].getZ()).tex(f7, f10).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(n).endVertex();
    }

    public float getScale(float f) {
        return this.particleScale;
    }

    @Override
    public Particle multiplyParticleScaleBy(float f) {
        this.particleScale *= f;
        return super.multiplyParticleScaleBy(f);
    }

    protected abstract float getMinU();

    protected abstract float getMaxU();

    protected abstract float getMinV();

    protected abstract float getMaxV();
}

