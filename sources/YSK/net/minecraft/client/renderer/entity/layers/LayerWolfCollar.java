package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerWolfCollar implements LayerRenderer<EntityWolf>
{
    private final RenderWolf wolfRenderer;
    private static final String[] I;
    private static final ResourceLocation WOLF_COLLAR;
    
    static {
        I();
        WOLF_COLLAR = new ResourceLocation(LayerWolfCollar.I["".length()]);
    }
    
    @Override
    public void doRenderLayer(final EntityWolf entityWolf, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityWolf.isTamed() && !entityWolf.isInvisible()) {
            this.wolfRenderer.bindTexture(LayerWolfCollar.WOLF_COLLAR);
            final float[] func_175513_a = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(entityWolf.getCollarColor().getMetadata()));
            GlStateManager.color(func_175513_a["".length()], func_175513_a[" ".length()], func_175513_a["  ".length()]);
            this.wolfRenderer.getMainModel().render(entityWolf, n, n2, n4, n5, n6, n7);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("##-,'%#&w792<,+x1:44x1:44\b%:4>64{(<0", "WFUXR");
    }
    
    public LayerWolfCollar(final RenderWolf wolfRenderer) {
        this.wolfRenderer = wolfRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityWolf)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
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
            if (3 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
