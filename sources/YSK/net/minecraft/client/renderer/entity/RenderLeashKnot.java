package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class RenderLeashKnot extends Render<EntityLeashKnot>
{
    private static final String[] I;
    private ModelLeashKnot leashKnotModel;
    private static final ResourceLocation leashKnotTextures;
    
    static {
        I();
        leashKnotTextures = new ResourceLocation(RenderLeashKnot.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLeashKnot entityLeashKnot) {
        return RenderLeashKnot.leashKnotTextures;
    }
    
    public RenderLeashKnot(final RenderManager renderManager) {
        super(renderManager);
        this.leashKnotModel = new ModelLeashKnot();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLeashKnot)entity);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(":/\u0000\u0006\u0018</\u000b]\b >\u0011\u0006\u0014a&\u001d\u0013\t\u0011!\u0016\u001d\u0019`:\u0016\u0015", "NJxrm");
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLeashKnot)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    public void doRender(final EntityLeashKnot entityLeashKnot, final double n, final double n2, final double n3, final float n4, final float n5) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        final float n6 = 0.0625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.enableAlpha();
        this.bindEntityTexture(entityLeashKnot);
        this.leashKnotModel.render(entityLeashKnot, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, n6);
        GlStateManager.popMatrix();
        super.doRender(entityLeashKnot, n, n2, n3, n4, n5);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
