package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class RenderBiped<T extends EntityLiving> extends RenderLiving<T>
{
    protected float field_77070_b;
    private static final String[] I;
    private static final ResourceLocation DEFAULT_RES_LOC;
    protected ModelBiped modelBipedMain;
    
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0f, 0.1875f, 0.0f);
    }
    
    static {
        I();
        DEFAULT_RES_LOC = new ResourceLocation(RenderBiped.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final T t) {
        return RenderBiped.DEFAULT_RES_LOC;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLiving)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("!\u000b>\u00042'\u000b5_\";\u001a/\u0004>z\u001d2\u001510@6\u001e ", "UnFpG");
    }
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBiped, final float n) {
        this(renderManager, modelBiped, n, 1.0f);
        this.addLayer(new LayerHeldItem(this));
    }
    
    public RenderBiped(final RenderManager renderManager, final ModelBiped modelBipedMain, final float n, final float field_77070_b) {
        super(renderManager, modelBipedMain, n);
        this.modelBipedMain = modelBipedMain;
        this.field_77070_b = field_77070_b;
        this.addLayer(new LayerCustomHead(modelBipedMain.bipedHead));
    }
}
