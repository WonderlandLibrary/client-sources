package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;

public class LayerSaddle implements LayerRenderer<EntityPig>
{
    private final RenderPig pigRenderer;
    private static final ResourceLocation TEXTURE;
    private final ModelPig pigModel;
    private static final String[] I;
    
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
            if (4 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0003<\f,!\u0005<\u0007w1\u0019-\u001d,-X)\u001d?{\u00070\u0013\u0007'\u0016=\u001041Y)\u001a?", "wYtXT");
    }
    
    public LayerSaddle(final RenderPig pigRenderer) {
        this.pigModel = new ModelPig(0.5f);
        this.pigRenderer = pigRenderer;
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityPig)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    static {
        I();
        TEXTURE = new ResourceLocation(LayerSaddle.I["".length()]);
    }
    
    @Override
    public void doRenderLayer(final EntityPig entityPig, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityPig.getSaddled()) {
            this.pigRenderer.bindTexture(LayerSaddle.TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(entityPig, n, n2, n4, n5, n6, n7);
        }
    }
}
