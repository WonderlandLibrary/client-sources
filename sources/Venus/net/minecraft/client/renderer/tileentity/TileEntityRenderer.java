/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.util.Either;

public abstract class TileEntityRenderer<T extends TileEntity>
implements IEntityRenderer {
    protected final TileEntityRendererDispatcher renderDispatcher;
    private TileEntityType type = null;
    private ResourceLocation locationTextureCustom = null;

    public TileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        this.renderDispatcher = tileEntityRendererDispatcher;
    }

    public abstract void render(T var1, float var2, MatrixStack var3, IRenderTypeBuffer var4, int var5, int var6);

    public boolean isGlobalRenderer(T t) {
        return true;
    }

    @Override
    public Either<EntityType, TileEntityType> getType() {
        return this.type == null ? null : Either.makeRight(this.type);
    }

    @Override
    public void setType(Either<EntityType, TileEntityType> either) {
        this.type = either.getRight().get();
    }

    @Override
    public ResourceLocation getLocationTextureCustom() {
        return this.locationTextureCustom;
    }

    @Override
    public void setLocationTextureCustom(ResourceLocation resourceLocation) {
        this.locationTextureCustom = resourceLocation;
    }
}

