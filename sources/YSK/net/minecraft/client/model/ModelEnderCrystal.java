package net.minecraft.client.model;

import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;

public class ModelEnderCrystal extends ModelBase
{
    private static final String[] I;
    private ModelRenderer cube;
    private ModelRenderer glass;
    private ModelRenderer base;
    
    static {
        I();
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("6\u001b(2\u001a", "QwIAi");
        ModelEnderCrystal.I[" ".length()] = I("92 \u0010", "ZGBuD");
        ModelEnderCrystal.I["  ".length()] = I(")\n1!", "KkBDy");
    }
    
    @Override
    public void render(final Entity entity, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.translate(0.0f, -0.5f, 0.0f);
        if (this.base != null) {
            this.base.render(n6);
        }
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        GlStateManager.translate(0.0f, 0.8f + n3, 0.0f);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        this.glass.render(n6);
        final float n7 = 0.875f;
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        this.glass.render(n6);
        GlStateManager.scale(n7, n7, n7);
        GlStateManager.rotate(60.0f, 0.7071f, 0.0f, 0.7071f);
        GlStateManager.rotate(n2, 0.0f, 1.0f, 0.0f);
        this.cube.render(n6);
        GlStateManager.popMatrix();
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
            if (-1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelEnderCrystal(final float n, final boolean b) {
        this.glass = new ModelRenderer(this, ModelEnderCrystal.I["".length()]);
        this.glass.setTextureOffset("".length(), "".length()).addBox(-4.0f, -4.0f, -4.0f, 0x73 ^ 0x7B, 0xA5 ^ 0xAD, 0x4B ^ 0x43);
        this.cube = new ModelRenderer(this, ModelEnderCrystal.I[" ".length()]);
        this.cube.setTextureOffset(0x4E ^ 0x6E, "".length()).addBox(-4.0f, -4.0f, -4.0f, 0xA2 ^ 0xAA, 0x95 ^ 0x9D, 0x7D ^ 0x75);
        if (b) {
            this.base = new ModelRenderer(this, ModelEnderCrystal.I["  ".length()]);
            this.base.setTextureOffset("".length(), 0xAC ^ 0xBC).addBox(-6.0f, 0.0f, -6.0f, 0x8 ^ 0x4, 0x2E ^ 0x2A, 0x43 ^ 0x4F);
        }
    }
}
