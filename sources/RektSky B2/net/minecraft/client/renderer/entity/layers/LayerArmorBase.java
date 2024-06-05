package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    protected T field_177189_c;
    protected T field_177186_d;
    private final RendererLivingEntity<?> renderer;
    private float alpha;
    private float colorR;
    private float colorG;
    private float colorB;
    private boolean field_177193_i;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP;
    
    public LayerArmorBase(final RendererLivingEntity<?> rendererIn) {
        this.alpha = 1.0f;
        this.colorR = 1.0f;
        this.colorG = 1.0f;
        this.colorB = 1.0f;
        this.renderer = rendererIn;
        this.initArmor();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 4);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 3);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 2);
        this.renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 1);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    private void renderLayer(final EntityLivingBase entitylivingbaseIn, final float p_177182_2_, final float p_177182_3_, final float p_177182_4_, final float p_177182_5_, final float p_177182_6_, final float p_177182_7_, final float p_177182_8_, final int armorSlot) {
        final ItemStack itemstack = this.getCurrentArmor(entitylivingbaseIn, armorSlot);
        if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
            final ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
            final T t = this.func_177175_a(armorSlot);
            t.setModelAttributes(this.renderer.getMainModel());
            t.setLivingAnimations(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_4_);
            this.func_177179_a(t, armorSlot);
            final boolean flag = this.isSlotForLeggings(armorSlot);
            this.renderer.bindTexture(this.getArmorResource(itemarmor, flag));
            switch (itemarmor.getArmorMaterial()) {
                case LEATHER: {
                    final int i = itemarmor.getColor(itemstack);
                    final float f = (i >> 16 & 0xFF) / 255.0f;
                    final float f2 = (i >> 8 & 0xFF) / 255.0f;
                    final float f3 = (i & 0xFF) / 255.0f;
                    GlStateManager.color(this.colorR * f, this.colorG * f2, this.colorB * f3, this.alpha);
                    t.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    this.renderer.bindTexture(this.getArmorResource(itemarmor, flag, "overlay"));
                }
                case CHAIN:
                case IRON:
                case GOLD:
                case DIAMOND: {
                    GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                    t.render(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
                    break;
                }
            }
            if (!this.field_177193_i && itemstack.isItemEnchanted()) {
                this.func_177183_a(entitylivingbaseIn, t, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
            }
        }
    }
    
    public ItemStack getCurrentArmor(final EntityLivingBase entitylivingbaseIn, final int armorSlot) {
        return entitylivingbaseIn.getCurrentArmor(armorSlot - 1);
    }
    
    public T func_177175_a(final int p_177175_1_) {
        return this.isSlotForLeggings(p_177175_1_) ? this.field_177189_c : this.field_177186_d;
    }
    
    private boolean isSlotForLeggings(final int armorSlot) {
        return armorSlot == 2;
    }
    
    private void func_177183_a(final EntityLivingBase entitylivingbaseIn, final T modelbaseIn, final float p_177183_3_, final float p_177183_4_, final float p_177183_5_, final float p_177183_6_, final float p_177183_7_, final float p_177183_8_, final float p_177183_9_) {
        final float f = entitylivingbaseIn.ticksExisted + p_177183_5_;
        this.renderer.bindTexture(LayerArmorBase.ENCHANTED_ITEM_GLINT_RES);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(514);
        GlStateManager.depthMask(false);
        final float f2 = 0.5f;
        GlStateManager.color(f2, f2, f2, 1.0f);
        for (int i = 0; i < 2; ++i) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(768, 1);
            final float f3 = 0.76f;
            GlStateManager.color(0.5f * f3, 0.25f * f3, 0.8f * f3, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            final float f4 = 0.33333334f;
            GlStateManager.scale(f4, f4, f4);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, f * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(5888);
            modelbaseIn.render(entitylivingbaseIn, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_, p_177183_9_);
        }
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
        GlStateManager.disableBlend();
    }
    
    private ResourceLocation getArmorResource(final ItemArmor p_177181_1_, final boolean p_177181_2_) {
        return this.getArmorResource(p_177181_1_, p_177181_2_, null);
    }
    
    private ResourceLocation getArmorResource(final ItemArmor p_177178_1_, final boolean p_177178_2_, final String p_177178_3_) {
        final String s = String.format("textures/models/armor/%s_layer_%d%s.png", p_177178_1_.getArmorMaterial().getName(), p_177178_2_ ? 2 : 1, (p_177178_3_ == null) ? "" : String.format("_%s", p_177178_3_));
        ResourceLocation resourcelocation = LayerArmorBase.ARMOR_TEXTURE_RES_MAP.get(s);
        if (resourcelocation == null) {
            resourcelocation = new ResourceLocation(s);
            LayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
        }
        return resourcelocation;
    }
    
    protected abstract void initArmor();
    
    protected abstract void func_177179_a(final T p0, final int p1);
    
    static {
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    }
}
