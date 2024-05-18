package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class LayerSlimeGel implements LayerRenderer<EntitySlime>
{
    private final ModelBase slimeModel;
    private final RenderSlime slimeRenderer;
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
    }
    
    @Override
    public void doRenderLayer(final EntitySlime entitySlime, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySlime.isInvisible()) {
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(60 + 537 - 19 + 192, 627 + 413 - 800 + 531);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entitySlime, n, n2, n4, n5, n6, n7);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
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
            if (1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public LayerSlimeGel(final RenderSlime slimeRenderer) {
        this.slimeModel = new ModelSlime("".length());
        this.slimeRenderer = slimeRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySlime)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
