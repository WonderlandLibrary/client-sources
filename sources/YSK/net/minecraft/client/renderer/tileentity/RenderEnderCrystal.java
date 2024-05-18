package net.minecraft.client.renderer.tileentity;

import net.minecraft.entity.item.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;

public class RenderEnderCrystal extends Render<EntityEnderCrystal>
{
    private static final ResourceLocation enderCrystalTextures;
    private static final String[] I;
    private ModelBase modelEnderCrystal;
    
    public RenderEnderCrystal(final RenderManager renderManager) {
        super(renderManager);
        this.modelEnderCrystal = new ModelEnderCrystal(0.0f, " ".length() != 0);
        this.shadowSize = 0.5f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityEnderCrystal entityEnderCrystal) {
        return RenderEnderCrystal.enderCrystalTextures;
    }
    
    static {
        I();
        enderCrystalTextures = new ResourceLocation(RenderEnderCrystal.I["".length()]);
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityEnderCrystal)entity, n, n2, n3, n4, n5);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityEnderCrystal)entity);
    }
    
    @Override
    public void doRender(final EntityEnderCrystal entityEnderCrystal, final double n, final double n2, final double n3, final float n4, final float n5) {
        final float n6 = entityEnderCrystal.innerRotation + n5;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)n3);
        this.bindTexture(RenderEnderCrystal.enderCrystalTextures);
        final float n7 = MathHelper.sin(n6 * 0.2f) / 2.0f + 0.5f;
        this.modelEnderCrystal.render(entityEnderCrystal, 0.0f, n6 * 3.0f, (n7 * n7 + n7) * 0.2f, 0.0f, 0.0f, 0.0625f);
        GlStateManager.popMatrix();
        super.doRender(entityEnderCrystal, n, n2, n3, n4, n5);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u001c\u0004\u0011!\u0013\u001a\u0004\u001az\u0003\u0006\u0015\u0000!\u001fG\u0004\u00071\u0003\u001a\u0002\u001b,\u0015\u001c\u0000\u0005z\u0003\u0006\u0005\f'\u0005\u001a\u0018\u001a!\u0007\u0004O\u0019;\u0001", "haiUf");
    }
}
