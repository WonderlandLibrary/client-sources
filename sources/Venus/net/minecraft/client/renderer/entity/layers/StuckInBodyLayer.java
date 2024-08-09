/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Random;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public abstract class StuckInBodyLayer<T extends LivingEntity, M extends PlayerModel<T>>
extends LayerRenderer<T, M> {
    public StuckInBodyLayer(LivingRenderer<T, M> livingRenderer) {
        super(livingRenderer);
    }

    protected abstract int func_225631_a_(T var1);

    protected abstract void func_225632_a_(MatrixStack var1, IRenderTypeBuffer var2, int var3, Entity var4, float var5, float var6, float var7, float var8);

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        int n2 = this.func_225631_a_(t);
        Random random2 = new Random(((Entity)t).getEntityId());
        if (n2 > 0) {
            for (int i = 0; i < n2; ++i) {
                matrixStack.push();
                ModelRenderer modelRenderer = ((PlayerModel)this.getEntityModel()).getRandomModelRenderer(random2);
                ModelRenderer.ModelBox modelBox = modelRenderer.getRandomCube(random2);
                modelRenderer.translateRotate(matrixStack);
                float f7 = random2.nextFloat();
                float f8 = random2.nextFloat();
                float f9 = random2.nextFloat();
                float f10 = MathHelper.lerp(f7, modelBox.posX1, modelBox.posX2) / 16.0f;
                float f11 = MathHelper.lerp(f8, modelBox.posY1, modelBox.posY2) / 16.0f;
                float f12 = MathHelper.lerp(f9, modelBox.posZ1, modelBox.posZ2) / 16.0f;
                matrixStack.translate(f10, f11, f12);
                f7 = -1.0f * (f7 * 2.0f - 1.0f);
                f8 = -1.0f * (f8 * 2.0f - 1.0f);
                f9 = -1.0f * (f9 * 2.0f - 1.0f);
                this.func_225632_a_(matrixStack, iRenderTypeBuffer, n, (Entity)t, f7, f8, f9, f3);
                matrixStack.pop();
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

