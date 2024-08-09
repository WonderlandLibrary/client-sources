/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.function.Consumer;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.optifine.EmissiveTextures;

public abstract class Model
implements Consumer<ModelRenderer> {
    protected final Function<ResourceLocation, RenderType> renderType;
    public int textureWidth = 64;
    public int textureHeight = 32;

    public Model(Function<ResourceLocation, RenderType> function) {
        this.renderType = function;
    }

    @Override
    public void accept(ModelRenderer modelRenderer) {
    }

    public final RenderType getRenderType(ResourceLocation resourceLocation) {
        RenderType renderType = this.renderType.apply(resourceLocation);
        if (EmissiveTextures.isRenderEmissive() && renderType.isEntitySolid()) {
            renderType = RenderType.getEntityCutout(resourceLocation);
        }
        return renderType;
    }

    public abstract void render(MatrixStack var1, IVertexBuilder var2, int var3, int var4, float var5, float var6, float var7, float var8);

    @Override
    public void accept(Object object) {
        this.accept((ModelRenderer)object);
    }
}

