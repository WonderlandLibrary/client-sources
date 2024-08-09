/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.optifine.Config;
import net.optifine.shaders.Program;
import net.optifine.shaders.Shaders;

public class ItemPickupParticle
extends Particle {
    private final RenderTypeBuffers renderTypeBuffers;
    private final Entity item;
    private final Entity target;
    private int age;
    private final EntityRendererManager renderManager;

    public ItemPickupParticle(EntityRendererManager entityRendererManager, RenderTypeBuffers renderTypeBuffers, ClientWorld clientWorld, Entity entity2, Entity entity3) {
        this(entityRendererManager, renderTypeBuffers, clientWorld, entity2, entity3, entity2.getMotion());
    }

    private ItemPickupParticle(EntityRendererManager entityRendererManager, RenderTypeBuffers renderTypeBuffers, ClientWorld clientWorld, Entity entity2, Entity entity3, Vector3d vector3d) {
        super(clientWorld, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), vector3d.x, vector3d.y, vector3d.z);
        this.renderTypeBuffers = renderTypeBuffers;
        this.item = this.func_239181_a_(entity2);
        this.target = entity3;
        this.renderManager = entityRendererManager;
    }

    private Entity func_239181_a_(Entity entity2) {
        return !(entity2 instanceof ItemEntity) ? entity2 : ((ItemEntity)entity2).func_234273_t_();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.CUSTOM;
    }

    @Override
    public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
        Program program = null;
        if (Config.isShaders()) {
            program = Shaders.activeProgram;
            Shaders.nextEntity(this.item);
        }
        float f2 = ((float)this.age + f) / 3.0f;
        f2 *= f2;
        double d = MathHelper.lerp((double)f, this.target.lastTickPosX, this.target.getPosX());
        double d2 = MathHelper.lerp((double)f, this.target.lastTickPosY, this.target.getPosY()) + 0.5;
        double d3 = MathHelper.lerp((double)f, this.target.lastTickPosZ, this.target.getPosZ());
        double d4 = MathHelper.lerp((double)f2, this.item.getPosX(), d);
        double d5 = MathHelper.lerp((double)f2, this.item.getPosY(), d2);
        double d6 = MathHelper.lerp((double)f2, this.item.getPosZ(), d3);
        IRenderTypeBuffer.Impl impl = this.renderTypeBuffers.getBufferSource();
        Vector3d vector3d = activeRenderInfo.getProjectedView();
        this.renderManager.renderEntityStatic(this.item, d4 - vector3d.getX(), d5 - vector3d.getY(), d6 - vector3d.getZ(), this.item.rotationYaw, f, new MatrixStack(), impl, this.renderManager.getPackedLight(this.item, f));
        impl.finish();
        if (Config.isShaders()) {
            Shaders.setEntityId(null);
            Shaders.useProgram(program);
        }
    }

    @Override
    public void tick() {
        ++this.age;
        if (this.age == 3) {
            this.setExpired();
        }
    }
}

