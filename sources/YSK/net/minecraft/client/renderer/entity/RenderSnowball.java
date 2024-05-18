package net.minecraft.client.renderer.entity;

import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;

public class RenderSnowball<T extends Entity> extends Render<T>
{
    protected final Item field_177084_a;
    private final RenderItem field_177083_e;
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public RenderSnowball(final RenderManager renderManager, final Item field_177084_a, final RenderItem field_177083_e) {
        super(renderManager);
        this.field_177084_a = field_177084_a;
        this.field_177083_e = field_177083_e;
    }
    
    public ItemStack func_177082_d(final T t) {
        return new ItemStack(this.field_177084_a, " ".length(), "".length());
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    public void doRender(final T t, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        this.bindTexture(TextureMap.locationBlocksTexture);
        this.field_177083_e.func_181564_a(this.func_177082_d(t), ItemCameraTransforms.TransformType.GROUND);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(t, n, n2, n3, n4, n5);
    }
}
