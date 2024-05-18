package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSheepWool implements LayerRenderer<EntitySheep>
{
    private final ModelSheep1 sheepModel;
    private static final ResourceLocation TEXTURE;
    private static final String[] I;
    private final RenderSheep sheepRenderer;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySheep)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public void doRenderLayer(final EntitySheep entitySheep, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySheep.getSheared() && !entitySheep.isInvisible()) {
            this.sheepRenderer.bindTexture(LayerSheepWool.TEXTURE);
            if (entitySheep.hasCustomName() && LayerSheepWool.I[" ".length()].equals(entitySheep.getCustomNameTag())) {
                final int n8 = entitySheep.ticksExisted / (0x28 ^ 0x31) + entitySheep.getEntityId();
                final int length = EnumDyeColor.values().length;
                final int n9 = n8 % length;
                final int n10 = (n8 + " ".length()) % length;
                final float n11 = (entitySheep.ticksExisted % (0x45 ^ 0x5C) + n3) / 25.0f;
                final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(n9));
                final float[] func_175513_a2 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(n10));
                GlStateManager.color(func_175513_a["".length()] * (1.0f - n11) + func_175513_a2["".length()] * n11, func_175513_a[" ".length()] * (1.0f - n11) + func_175513_a2[" ".length()] * n11, func_175513_a["  ".length()] * (1.0f - n11) + func_175513_a2["  ".length()] * n11);
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                final float[] func_175513_a3 = EntitySheep.func_175513_a(entitySheep.getFleeceColor());
                GlStateManager.color(func_175513_a3["".length()], func_175513_a3[" ".length()], func_175513_a3["  ".length()]);
            }
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitySheep, n, n2, n3);
            this.sheepModel.render(entitySheep, n, n2, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("5\u0006(513\u0006#n!/\u001795=n\u00108$!1L#)!$\u0013\u000f'13M /#", "AcPAD");
        LayerSheepWool.I[" ".length()] = I("\u0004\u00063\u0010", "ncQOG");
    }
    
    public LayerSheepWool(final RenderSheep sheepRenderer) {
        this.sheepModel = new ModelSheep1();
        this.sheepRenderer = sheepRenderer;
    }
    
    static {
        I();
        TEXTURE = new ResourceLocation(LayerSheepWool.I["".length()]);
    }
}
