package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;

public class ArmorStandRenderer extends RendererLivingEntity<EntityArmorStand>
{
    public static final ResourceLocation TEXTURE_ARMOR_STAND;
    private static final String[] I;
    
    @Override
    public ModelArmorStand getMainModel() {
        return (ModelArmorStand)super.getMainModel();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityArmorStand)entity);
    }
    
    static {
        I();
        TEXTURE_ARMOR_STAND = new ResourceLocation(ArmorStandRenderer.I["".length()]);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityArmorStand entityArmorStand) {
        return ArmorStandRenderer.TEXTURE_ARMOR_STAND;
    }
    
    @Override
    protected boolean canRenderName(final EntityLivingBase entityLivingBase) {
        return this.canRenderName((EntityArmorStand)entityLivingBase);
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
    
    @Override
    public ModelBase getMainModel() {
        return this.getMainModel();
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3) {
        this.rotateCorpse((EntityArmorStand)entityLivingBase, n, n2, n3);
    }
    
    @Override
    protected void rotateCorpse(final EntityArmorStand entityArmorStand, final float n, final float n2, final float n3) {
        GlStateManager.rotate(180.0f - n2, 0.0f, 1.0f, 0.0f);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("6).%\"0)%~2,8?%.m-$<80?\"09&c!>8&b&?0", "BLVQW");
    }
    
    public ArmorStandRenderer(final RenderManager renderManager) {
        super(renderManager, new ModelArmorStand(), 0.0f);
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerBipedArmor(this, this) {
            final ArmorStandRenderer this$0;
            
            @Override
            protected void initArmor() {
                this.field_177189_c = (T)new ModelArmorStandArmor(0.5f);
                this.field_177186_d = (T)new ModelArmorStandArmor(1.0f);
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
                    if (3 >= 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        });
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerHeldItem(this));
        ((RendererLivingEntity<EntityLivingBase>)this).addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
    }
    
    @Override
    protected boolean canRenderName(final EntityArmorStand entityArmorStand) {
        return entityArmorStand.getAlwaysRenderNameTag();
    }
}
