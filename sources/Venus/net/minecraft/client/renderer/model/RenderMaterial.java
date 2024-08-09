/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.optifine.EmissiveTextures;
import net.optifine.render.RenderUtils;

public class RenderMaterial {
    private final ResourceLocation atlasLocation;
    private final ResourceLocation textureLocation;
    @Nullable
    private RenderType renderType;

    public RenderMaterial(ResourceLocation resourceLocation, ResourceLocation resourceLocation2) {
        this.atlasLocation = resourceLocation;
        this.textureLocation = resourceLocation2;
    }

    public ResourceLocation getAtlasLocation() {
        return this.atlasLocation;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public TextureAtlasSprite getSprite() {
        TextureAtlasSprite textureAtlasSprite = Minecraft.getInstance().getAtlasSpriteGetter(this.getAtlasLocation()).apply(this.getTextureLocation());
        if (EmissiveTextures.isActive()) {
            textureAtlasSprite = EmissiveTextures.getEmissiveSprite(textureAtlasSprite);
        }
        return textureAtlasSprite;
    }

    public RenderType getRenderType(Function<ResourceLocation, RenderType> function) {
        if (this.renderType == null) {
            this.renderType = function.apply(this.atlasLocation);
        }
        return this.renderType;
    }

    public IVertexBuilder getBuffer(IRenderTypeBuffer iRenderTypeBuffer, Function<ResourceLocation, RenderType> function) {
        TextureAtlasSprite textureAtlasSprite = this.getSprite();
        RenderType renderType = this.getRenderType(function);
        if (textureAtlasSprite.isSpriteEmissive && renderType.isEntitySolid()) {
            RenderUtils.flushRenderBuffers();
            renderType = RenderType.getEntityCutout(this.atlasLocation);
        }
        return textureAtlasSprite.wrapBuffer(iRenderTypeBuffer.getBuffer(renderType));
    }

    public IVertexBuilder getItemRendererBuffer(IRenderTypeBuffer iRenderTypeBuffer, Function<ResourceLocation, RenderType> function, boolean bl) {
        return this.getSprite().wrapBuffer(ItemRenderer.getEntityGlintVertexBuilder(iRenderTypeBuffer, this.getRenderType(function), true, bl));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            RenderMaterial renderMaterial = (RenderMaterial)object;
            return this.atlasLocation.equals(renderMaterial.atlasLocation) && this.textureLocation.equals(renderMaterial.textureLocation);
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.atlasLocation, this.textureLocation);
    }

    public String toString() {
        return "Material{atlasLocation=" + this.atlasLocation + ", texture=" + this.textureLocation + "}";
    }
}

