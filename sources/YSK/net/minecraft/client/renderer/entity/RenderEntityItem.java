package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;

public class RenderEntityItem extends Render<EntityItem>
{
    private Random field_177079_e;
    private final RenderItem itemRenderer;
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityItem)entity);
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
            if (3 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityItem entityItem) {
        return TextureMap.locationBlocksTexture;
    }
    
    public RenderEntityItem(final RenderManager renderManager, final RenderItem itemRenderer) {
        super(renderManager);
        this.field_177079_e = new Random();
        this.itemRenderer = itemRenderer;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    @Override
    public void doRender(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final float n5) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        this.field_177079_e.setSeed(187L);
        int n6 = "".length();
        if (this.bindEntityTexture(entityItem)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).setBlurMipmap("".length() != 0, "".length() != 0);
            n6 = " ".length();
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(423 + 170 - 151 + 74, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(48 + 640 - 426 + 508, 430 + 582 - 890 + 649, " ".length(), "".length());
        GlStateManager.pushMatrix();
        final IBakedModel itemModel = this.itemRenderer.getItemModelMesher().getItemModel(entityItem2);
        final int func_177077_a = this.func_177077_a(entityItem, n, n2, n3, n5, itemModel);
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < func_177077_a) {
            if (itemModel.isGui3d()) {
                GlStateManager.pushMatrix();
                if (i > 0) {
                    GlStateManager.translate((this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f, (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f, (this.field_177079_e.nextFloat() * 2.0f - 1.0f) * 0.15f);
                }
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
                itemModel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(entityItem2, itemModel);
                GlStateManager.popMatrix();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else {
                GlStateManager.pushMatrix();
                itemModel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(entityItem2, itemModel);
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0f * itemModel.getItemCameraTransforms().ground.scale.x, 0.0f * itemModel.getItemCameraTransforms().ground.scale.y, 0.046875f * itemModel.getItemCameraTransforms().ground.scale.z);
            }
            ++i;
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entityItem);
        if (n6 != 0) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entityItem)).restoreLastBlurMipmap();
        }
        super.doRender(entityItem, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityItem)entity, n, n2, n3, n4, n5);
    }
    
    private int func_177078_a(final ItemStack itemStack) {
        int n = " ".length();
        if (itemStack.stackSize > (0x7E ^ 0x4E)) {
            n = (0x4A ^ 0x4F);
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else if (itemStack.stackSize > (0x86 ^ 0xA6)) {
            n = (0x3E ^ 0x3A);
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else if (itemStack.stackSize > (0xB7 ^ 0xA7)) {
            n = "   ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (itemStack.stackSize > " ".length()) {
            n = "  ".length();
        }
        return n;
    }
    
    private int func_177077_a(final EntityItem entityItem, final double n, final double n2, final double n3, final float n4, final IBakedModel bakedModel) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (entityItem2.getItem() == null) {
            return "".length();
        }
        final boolean gui3d = bakedModel.isGui3d();
        final int func_177078_a = this.func_177078_a(entityItem2);
        GlStateManager.translate((float)n, (float)n2 + (MathHelper.sin((entityItem.getAge() + n4) / 10.0f + entityItem.hoverStart) * 0.1f + 0.1f) + 0.25f * bakedModel.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y, (float)n3);
        if (gui3d || this.renderManager.options != null) {
            GlStateManager.rotate(((entityItem.getAge() + n4) / 20.0f + entityItem.hoverStart) * 57.295776f, 0.0f, 1.0f, 0.0f);
        }
        if (!gui3d) {
            GlStateManager.translate(-0.0f * (func_177078_a - " ".length()) * 0.5f, -0.0f * (func_177078_a - " ".length()) * 0.5f, -0.046875f * (func_177078_a - " ".length()) * 0.5f);
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        return func_177078_a;
    }
}
