/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.EnchantingTableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class EnchantmentTableTileEntityRenderer
extends TileEntityRenderer<EnchantingTableTileEntity> {
    public static final RenderMaterial TEXTURE_BOOK = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("entity/enchanting_table_book"));
    private final BookModel modelBook = new BookModel();

    public EnchantmentTableTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(EnchantingTableTileEntity enchantingTableTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        float f2;
        matrixStack.push();
        matrixStack.translate(0.5, 0.75, 0.5);
        float f3 = (float)enchantingTableTileEntity.ticks + f;
        matrixStack.translate(0.0, 0.1f + MathHelper.sin(f3 * 0.1f) * 0.01f, 0.0);
        for (f2 = enchantingTableTileEntity.nextPageAngle - enchantingTableTileEntity.pageAngle; f2 >= (float)Math.PI; f2 -= (float)Math.PI * 2) {
        }
        while (f2 < (float)(-Math.PI)) {
            f2 += (float)Math.PI * 2;
        }
        float f4 = enchantingTableTileEntity.pageAngle + f2 * f;
        matrixStack.rotate(Vector3f.YP.rotation(-f4));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(80.0f));
        float f5 = MathHelper.lerp(f, enchantingTableTileEntity.field_195524_g, enchantingTableTileEntity.field_195523_f);
        float f6 = MathHelper.frac(f5 + 0.25f) * 1.6f - 0.3f;
        float f7 = MathHelper.frac(f5 + 0.75f) * 1.6f - 0.3f;
        float f8 = MathHelper.lerp(f, enchantingTableTileEntity.pageTurningSpeed, enchantingTableTileEntity.nextPageTurningSpeed);
        this.modelBook.setBookState(f3, MathHelper.clamp(f6, 0.0f, 1.0f), MathHelper.clamp(f7, 0.0f, 1.0f), f8);
        IVertexBuilder iVertexBuilder = TEXTURE_BOOK.getBuffer(iRenderTypeBuffer, RenderType::getEntitySolid);
        this.modelBook.renderAll(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStack.pop();
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((EnchantingTableTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }
}

