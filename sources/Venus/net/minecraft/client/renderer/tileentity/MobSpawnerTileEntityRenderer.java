/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.spawner.AbstractSpawner;

public class MobSpawnerTileEntityRenderer
extends TileEntityRenderer<MobSpawnerTileEntity> {
    public MobSpawnerTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(MobSpawnerTileEntity mobSpawnerTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        matrixStack.push();
        matrixStack.translate(0.5, 0.0, 0.5);
        AbstractSpawner abstractSpawner = mobSpawnerTileEntity.getSpawnerBaseLogic();
        Entity entity2 = abstractSpawner.getCachedEntity();
        if (entity2 != null) {
            float f2 = 0.53125f;
            float f3 = Math.max(entity2.getWidth(), entity2.getHeight());
            if ((double)f3 > 1.0) {
                f2 /= f3;
            }
            matrixStack.translate(0.0, 0.4f, 0.0);
            matrixStack.rotate(Vector3f.YP.rotationDegrees((float)MathHelper.lerp((double)f, abstractSpawner.getPrevMobRotation(), abstractSpawner.getMobRotation()) * 10.0f));
            matrixStack.translate(0.0, -0.2f, 0.0);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-30.0f));
            matrixStack.scale(f2, f2, f2);
            Minecraft.getInstance().getRenderManager().renderEntityStatic(entity2, 0.0, 0.0, 0.0, 0.0f, f, matrixStack, iRenderTypeBuffer, n);
        }
        matrixStack.pop();
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((MobSpawnerTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

