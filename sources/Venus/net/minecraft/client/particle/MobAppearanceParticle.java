/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ElderGuardianRenderer;
import net.minecraft.client.renderer.entity.model.GuardianModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class MobAppearanceParticle
extends Particle {
    private final Model model = new GuardianModel();
    private final RenderType renderType = RenderType.getEntityTranslucent(ElderGuardianRenderer.GUARDIAN_ELDER_TEXTURE);

    private MobAppearanceParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
        this.particleGravity = 0.0f;
        this.maxAge = 30;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
        float f2 = ((float)this.age + f) / (float)this.maxAge;
        float f3 = 0.05f + 0.5f * MathHelper.sin(f2 * (float)Math.PI);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.rotate(activeRenderInfo.getRotation());
        matrixStack.rotate(Vector3f.XP.rotationDegrees(150.0f * f2 - 60.0f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.101f, 1.5);
        IRenderTypeBuffer.Impl impl = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        IVertexBuilder iVertexBuilder2 = impl.getBuffer(this.renderType);
        this.model.render(matrixStack, iVertexBuilder2, 0xF000F0, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, f3);
        impl.finish();
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new MobAppearanceParticle(clientWorld, d, d2, d3);
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}

