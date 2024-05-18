package net.minecraft.client.renderer.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.*;

public class RenderPigZombie extends RenderBiped<EntityPigZombie>
{
    private static final String[] I;
    private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000e1\u0012\u001c'\b1\u0019G7\u0014 \u0003\u001c+U.\u0005\u00050\u001315\u0018;\u001d9\u000b\u0006|\n:\r", "zTjhR");
    }
    
    public RenderPigZombie(final RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5f, 1.0f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerBipedArmor(this, this) {
            final RenderPigZombie this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = (T)new ModelZombie(0.5f, " ".length() != 0);
                this.field_177186_d = (T)new ModelZombie(1.0f, " ".length() != 0);
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
                    if (3 <= 2) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving entityLiving) {
        return this.getEntityTexture((EntityPigZombie)entityLiving);
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
            if (0 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityPigZombie entityPigZombie) {
        return RenderPigZombie.ZOMBIE_PIGMAN_TEXTURE;
    }
    
    static {
        I();
        ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation(RenderPigZombie.I["".length()]);
    }
}
