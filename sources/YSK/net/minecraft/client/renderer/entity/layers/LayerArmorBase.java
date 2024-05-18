package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;

public abstract class LayerArmorBase<T extends ModelBase> implements LayerRenderer<EntityLivingBase>
{
    protected T field_177189_c;
    private float colorG;
    private float colorB;
    private final RendererLivingEntity<?> renderer;
    private boolean field_177193_i;
    private static int[] $SWITCH_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial;
    private float alpha;
    private float colorR;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP;
    private static final String[] I;
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    protected T field_177186_d;
    
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, 0x2B ^ 0x2F);
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, "   ".length());
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, "  ".length());
        this.renderLayer(entityLivingBase, n, n2, n3, n4, n5, n6, n7, " ".length());
    }
    
    private void renderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final int n8) {
        final ItemStack currentArmor = this.getCurrentArmor(entityLivingBase, n8);
        if (currentArmor != null && currentArmor.getItem() instanceof ItemArmor) {
            final ItemArmor itemArmor = (ItemArmor)currentArmor.getItem();
            final ModelBase func_177175_a = this.func_177175_a(n8);
            func_177175_a.setModelAttributes(this.renderer.getMainModel());
            func_177175_a.setLivingAnimations(entityLivingBase, n, n2, n3);
            this.func_177179_a((T)func_177175_a, n8);
            final boolean slotForLeggings = this.isSlotForLeggings(n8);
            this.renderer.bindTexture(this.getArmorResource(itemArmor, slotForLeggings));
            switch ($SWITCH_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial()[itemArmor.getArmorMaterial().ordinal()]) {
                case 1: {
                    final int color = itemArmor.getColor(currentArmor);
                    GlStateManager.color(this.colorR * ((color >> (0x20 ^ 0x30) & 50 + 177 + 1 + 27) / 255.0f), this.colorG * ((color >> (0x7A ^ 0x72) & 90 + 65 + 42 + 58) / 255.0f), this.colorB * ((color & 80 + 47 - 58 + 186) / 255.0f), this.alpha);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    this.renderer.bindTexture(this.getArmorResource(itemArmor, slotForLeggings, LayerArmorBase.I[" ".length()]));
                }
                case 2:
                case 3:
                case 4:
                case 5: {
                    GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                    func_177175_a.render(entityLivingBase, n, n2, n4, n5, n6, n7);
                    break;
                }
            }
            if (!this.field_177193_i && currentArmor.isItemEnchanted()) {
                this.func_177183_a(entityLivingBase, (T)func_177175_a, n, n2, n3, n4, n5, n6, n7);
            }
        }
    }
    
    protected abstract void initArmor();
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    private static void I() {
        (I = new String[0x32 ^ 0x37])["".length()] = I("\u0011\u0003\b\u00119\u0017\u0003\u0003J!\f\u0015\u0013J)\u000b\u0005\u0018\u0004\"\u0011\u0003\u0014:%\u0011\u0003\u001d:+\t\u000f\u001e\u0011b\u0015\b\u0017", "efpeL");
        LayerArmorBase.I[" ".length()] = I("$2\"$\u0005*=", "KDGVi");
        LayerArmorBase.I["  ".length()] = I("#?1'-%?:|58>,?+x;;>7%ul \u0007;;06*\b\u007f-v+y*'4", "WZISX");
        LayerArmorBase.I["   ".length()] = I("", "kJdHZ");
        LayerArmorBase.I[0x27 ^ 0x23] = I(">h:", "aMIyn");
    }
    
    public ItemStack getCurrentArmor(final EntityLivingBase entityLivingBase, final int n) {
        return entityLivingBase.getCurrentArmor(n - " ".length());
    }
    
    private ResourceLocation getArmorResource(final ItemArmor itemArmor, final boolean b, final String s) {
        final String s2 = LayerArmorBase.I["  ".length()];
        final Object[] array = new Object["   ".length()];
        array["".length()] = itemArmor.getArmorMaterial().getName();
        final int length = " ".length();
        int n;
        if (b) {
            n = "  ".length();
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        array[length] = n;
        final int length2 = "  ".length();
        String format;
        if (s == null) {
            format = LayerArmorBase.I["   ".length()];
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            final String s3 = LayerArmorBase.I[0x18 ^ 0x1C];
            final Object[] array2 = new Object[" ".length()];
            array2["".length()] = s;
            format = String.format(s3, array2);
        }
        array[length2] = format;
        final String format2 = String.format(s2, array);
        ResourceLocation resourceLocation = LayerArmorBase.ARMOR_TEXTURE_RES_MAP.get(format2);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(format2);
            LayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(format2, resourceLocation);
        }
        return resourceLocation;
    }
    
    public T func_177175_a(final int n) {
        ModelBase modelBase;
        if (this.isSlotForLeggings(n)) {
            modelBase = this.field_177189_c;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            modelBase = this.field_177186_d;
        }
        return (T)modelBase;
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial() {
        final int[] $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial = LayerArmorBase.$SWITCH_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial;
        if ($switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial != null) {
            return $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial;
        }
        final int[] $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2 = new int[ItemArmor.ArmorMaterial.values().length];
        try {
            $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2[ItemArmor.ArmorMaterial.CHAIN.ordinal()] = "  ".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2[ItemArmor.ArmorMaterial.DIAMOND.ordinal()] = (0x24 ^ 0x21);
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2[ItemArmor.ArmorMaterial.GOLD.ordinal()] = (0x13 ^ 0x17);
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2[ItemArmor.ArmorMaterial.IRON.ordinal()] = "   ".length();
            "".length();
            if (-1 == 3) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2[ItemArmor.ArmorMaterial.LEATHER.ordinal()] = " ".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        return LayerArmorBase.$SWITCH_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial = $switch_TABLE$net$minecraft$item$ItemArmor$ArmorMaterial2;
    }
    
    private ResourceLocation getArmorResource(final ItemArmor itemArmor, final boolean b) {
        return this.getArmorResource(itemArmor, b, null);
    }
    
    public LayerArmorBase(final RendererLivingEntity<?> renderer) {
        this.alpha = 1.0f;
        this.colorR = 1.0f;
        this.colorG = 1.0f;
        this.colorB = 1.0f;
        this.renderer = renderer;
        this.initArmor();
    }
    
    private boolean isSlotForLeggings(final int n) {
        if (n == "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void func_177183_a(final EntityLivingBase entityLivingBase, final T t, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final float n8 = entityLivingBase.ticksExisted + n3;
        this.renderer.bindTexture(LayerArmorBase.ENCHANTED_ITEM_GLINT_RES);
        GlStateManager.enableBlend();
        GlStateManager.depthFunc(297 + 453 - 284 + 48);
        GlStateManager.depthMask("".length() != 0);
        final float n9 = 0.5f;
        GlStateManager.color(n9, n9, n9, 1.0f);
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < "  ".length()) {
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(581 + 322 - 839 + 704, " ".length());
            final float n10 = 0.76f;
            GlStateManager.color(0.5f * n10, 0.25f * n10, 0.8f * n10, 1.0f);
            GlStateManager.matrixMode(4791 + 5769 - 4768 + 98);
            GlStateManager.loadIdentity();
            final float n11 = 0.33333334f;
            GlStateManager.scale(n11, n11, n11);
            GlStateManager.rotate(30.0f - i * 60.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(0.0f, n8 * (0.001f + i * 0.003f) * 20.0f, 0.0f);
            GlStateManager.matrixMode(3723 + 828 - 1935 + 3272);
            t.render(entityLivingBase, n, n2, n4, n5, n6, n7);
            ++i;
        }
        GlStateManager.matrixMode(4891 + 4023 - 7775 + 4751);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(1029 + 5326 - 4892 + 4425);
        GlStateManager.enableLighting();
        GlStateManager.depthMask(" ".length() != 0);
        GlStateManager.depthFunc(96 + 368 - 407 + 458);
        GlStateManager.disableBlend();
    }
    
    static {
        I();
        ENCHANTED_ITEM_GLINT_RES = new ResourceLocation(LayerArmorBase.I["".length()]);
        ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    }
    
    protected abstract void func_177179_a(final T p0, final int p1);
}
