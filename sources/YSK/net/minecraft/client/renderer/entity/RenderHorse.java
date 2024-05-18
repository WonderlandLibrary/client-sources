package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import net.minecraft.client.model.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;

public class RenderHorse extends RenderLiving<EntityHorse>
{
    private static final ResourceLocation donkeyTextures;
    private static final ResourceLocation skeletonHorseTextures;
    private static final ResourceLocation zombieHorseTextures;
    private static final ResourceLocation whiteHorseTextures;
    private static final String[] I;
    private static final Map<String, ResourceLocation> field_110852_a;
    private static final ResourceLocation muleTextures;
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityHorse entityHorse) {
        if (entityHorse.func_110239_cn()) {
            return this.func_110848_b(entityHorse);
        }
        switch (entityHorse.getHorseType()) {
            default: {
                return RenderHorse.whiteHorseTextures;
            }
            case 1: {
                return RenderHorse.donkeyTextures;
            }
            case 2: {
                return RenderHorse.muleTextures;
            }
            case 3: {
                return RenderHorse.zombieHorseTextures;
            }
            case 4: {
                return RenderHorse.skeletonHorseTextures;
            }
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase entityLivingBase, final float n) {
        this.preRenderCallback((EntityHorse)entityLivingBase, n);
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
            if (1 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected void preRenderCallback(final EntityHorse entityHorse, final float n) {
        float n2 = 1.0f;
        final int horseType = entityHorse.getHorseType();
        if (horseType == " ".length()) {
            n2 *= 0.87f;
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else if (horseType == "  ".length()) {
            n2 *= 0.92f;
        }
        GlStateManager.scale(n2, n2, n2);
        super.preRenderCallback(entityHorse, n);
    }
    
    private static void I() {
        (I = new String[0x22 ^ 0x27])["".length()] = I("#\u000e\u001b\u0018\u0013%\u000e\u0010C\u00039\u001f\n\u0018\u001fx\u0003\f\u001e\u00152D\u000b\u0003\u0014$\u000e<\u001b\u000e>\u001f\u0006B\u00169\f", "Wkclf");
        RenderHorse.I[" ".length()] = I("#=:=/%=1f?9,+=#x0-;)2w/<62v2'=", "WXBIZ");
        RenderHorse.I["  ".length()] = I("3\u000b\r\u000e35\u000b\u0006U#)\u001a\u001c\u000e?h\u0006\u001a\b5\"A\u0011\u0015(,\u000b\fT6)\t", "GnuzF");
        RenderHorse.I["   ".length()] = I("\u00121\u00121\u0018\u00141\u0019j\b\b \u00031\u0014I<\u00057\u001e\u0003{\u0002*\u001f\u001515?\u0002\u000b6\u0003 C\u0016:\r", "fTjEm");
        RenderHorse.I[0x97 ^ 0x93] = I(">\f\b\u000e;8\f\u0003U+$\u001d\u0019\u000e7e\u0001\u001f\b=/F\u0018\u0015<9\f/\t%/\u0005\u0015\u000e!$G\u0000\u0014)", "JipzN");
    }
    
    static {
        I();
        field_110852_a = Maps.newHashMap();
        whiteHorseTextures = new ResourceLocation(RenderHorse.I["".length()]);
        muleTextures = new ResourceLocation(RenderHorse.I[" ".length()]);
        donkeyTextures = new ResourceLocation(RenderHorse.I["  ".length()]);
        zombieHorseTextures = new ResourceLocation(RenderHorse.I["   ".length()]);
        skeletonHorseTextures = new ResourceLocation(RenderHorse.I[0xAD ^ 0xA9]);
    }
    
    public RenderHorse(final RenderManager renderManager, final ModelHorse modelHorse, final float n) {
        super(renderManager, modelHorse, n);
    }
    
    private ResourceLocation func_110848_b(final EntityHorse entityHorse) {
        final String horseTexture = entityHorse.getHorseTexture();
        if (!entityHorse.func_175507_cI()) {
            return null;
        }
        ResourceLocation resourceLocation = RenderHorse.field_110852_a.get(horseTexture);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(horseTexture);
            Minecraft.getMinecraft().getTextureManager().loadTexture(resourceLocation, new LayeredTexture(entityHorse.getVariantTexturePaths()));
            RenderHorse.field_110852_a.put(horseTexture, resourceLocation);
        }
        return resourceLocation;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityHorse)entity);
    }
}
