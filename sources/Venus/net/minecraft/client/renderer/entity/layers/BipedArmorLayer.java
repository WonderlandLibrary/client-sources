/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.AgeableModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.CustomItems;
import net.optifine.reflect.Reflector;

public class BipedArmorLayer<T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>>
extends LayerRenderer<T, M> {
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
    private final A modelLeggings;
    private final A modelArmor;

    public BipedArmorLayer(IEntityRenderer<T, M> iEntityRenderer, A a, A a2) {
        super(iEntityRenderer);
        this.modelLeggings = a;
        this.modelArmor = a2;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        this.func_241739_a_(matrixStack, iRenderTypeBuffer, t, EquipmentSlotType.CHEST, n, this.func_241736_a_(EquipmentSlotType.CHEST));
        this.func_241739_a_(matrixStack, iRenderTypeBuffer, t, EquipmentSlotType.LEGS, n, this.func_241736_a_(EquipmentSlotType.LEGS));
        this.func_241739_a_(matrixStack, iRenderTypeBuffer, t, EquipmentSlotType.FEET, n, this.func_241736_a_(EquipmentSlotType.FEET));
        this.func_241739_a_(matrixStack, iRenderTypeBuffer, t, EquipmentSlotType.HEAD, n, this.func_241736_a_(EquipmentSlotType.HEAD));
    }

    private void func_241739_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, T t, EquipmentSlotType equipmentSlotType, int n, A a) {
        ArmorItem armorItem;
        ItemStack itemStack = ((LivingEntity)t).getItemStackFromSlot(equipmentSlotType);
        if (itemStack.getItem() instanceof ArmorItem && (armorItem = (ArmorItem)itemStack.getItem()).getEquipmentSlot() == equipmentSlotType) {
            if (Reflector.ForgeHooksClient.exists()) {
                a = this.getArmorModelHook(t, itemStack, equipmentSlotType, a);
            }
            ((BipedModel)this.getEntityModel()).setModelAttributes(a);
            this.setModelSlotVisible(a, equipmentSlotType);
            this.isLegSlot(equipmentSlotType);
            boolean bl = itemStack.hasEffect();
            if (armorItem instanceof IDyeableArmorItem) {
                int n2 = ((IDyeableArmorItem)((Object)armorItem)).getColor(itemStack);
                float f = (float)(n2 >> 16 & 0xFF) / 255.0f;
                float f2 = (float)(n2 >> 8 & 0xFF) / 255.0f;
                float f3 = (float)(n2 & 0xFF) / 255.0f;
                this.renderModel(matrixStack, iRenderTypeBuffer, n, bl, a, f, f2, f3, this.getArmorResource((Entity)t, itemStack, equipmentSlotType, null));
                this.renderModel(matrixStack, iRenderTypeBuffer, n, bl, a, 1.0f, 1.0f, 1.0f, this.getArmorResource((Entity)t, itemStack, equipmentSlotType, "overlay"));
            } else {
                this.renderModel(matrixStack, iRenderTypeBuffer, n, bl, a, 1.0f, 1.0f, 1.0f, this.getArmorResource((Entity)t, itemStack, equipmentSlotType, null));
            }
        }
    }

    protected void setModelSlotVisible(A a, EquipmentSlotType equipmentSlotType) {
        ((BipedModel)a).setVisible(true);
        switch (1.$SwitchMap$net$minecraft$inventory$EquipmentSlotType[equipmentSlotType.ordinal()]) {
            case 1: {
                ((BipedModel)a).bipedHead.showModel = true;
                ((BipedModel)a).bipedHeadwear.showModel = true;
                break;
            }
            case 2: {
                ((BipedModel)a).bipedBody.showModel = true;
                ((BipedModel)a).bipedRightArm.showModel = true;
                ((BipedModel)a).bipedLeftArm.showModel = true;
                break;
            }
            case 3: {
                ((BipedModel)a).bipedBody.showModel = true;
                ((BipedModel)a).bipedRightLeg.showModel = true;
                ((BipedModel)a).bipedLeftLeg.showModel = true;
                break;
            }
            case 4: {
                ((BipedModel)a).bipedRightLeg.showModel = true;
                ((BipedModel)a).bipedLeftLeg.showModel = true;
            }
        }
    }

    private void func_241738_a_(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, ArmorItem armorItem, boolean bl, A a, boolean bl2, float f, float f2, float f3, @Nullable String string) {
        this.renderModel(matrixStack, iRenderTypeBuffer, n, bl, a, f, f2, f3, this.func_241737_a_(armorItem, bl2, string));
    }

    private void renderModel(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, boolean bl, A a, float f, float f2, float f3, ResourceLocation resourceLocation) {
        IVertexBuilder iVertexBuilder = ItemRenderer.getArmorVertexBuilder(iRenderTypeBuffer, RenderType.getArmorCutoutNoCull(resourceLocation), false, bl);
        ((AgeableModel)a).render(matrixStack, iVertexBuilder, n, OverlayTexture.NO_OVERLAY, f, f2, f3, 1.0f);
    }

    private A func_241736_a_(EquipmentSlotType equipmentSlotType) {
        return this.isLegSlot(equipmentSlotType) ? this.modelLeggings : this.modelArmor;
    }

    private boolean isLegSlot(EquipmentSlotType equipmentSlotType) {
        return equipmentSlotType == EquipmentSlotType.LEGS;
    }

    private ResourceLocation func_241737_a_(ArmorItem armorItem, boolean bl, @Nullable String string) {
        String string2 = "textures/models/armor/" + armorItem.getArmorMaterial().getName() + "_layer_" + (bl ? 2 : 1) + (String)(string == null ? "" : "_" + string) + ".png";
        return ARMOR_TEXTURE_RES_MAP.computeIfAbsent(string2, ResourceLocation::new);
    }

    protected A getArmorModelHook(T t, ItemStack itemStack, EquipmentSlotType equipmentSlotType, A a) {
        return (A)((BipedModel)(Reflector.ForgeHooksClient_getArmorModel.exists() ? Reflector.ForgeHooksClient_getArmorModel.call(new Object[]{t, itemStack, equipmentSlotType, a}) : a));
    }

    public ResourceLocation getArmorResource(Entity entity2, ItemStack itemStack, EquipmentSlotType equipmentSlotType, String string) {
        ResourceLocation resourceLocation;
        ArmorItem armorItem = (ArmorItem)itemStack.getItem();
        String string2 = armorItem.getArmorMaterial().getName();
        String string3 = "minecraft";
        int n = string2.indexOf(58);
        if (n != -1) {
            string3 = string2.substring(0, n);
            string2 = string2.substring(n + 1);
        }
        String string4 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", string3, string2, this.isLegSlot(equipmentSlotType) ? 2 : 1, string == null ? "" : String.format("_%s", string));
        if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
            string4 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, new Object[]{entity2, itemStack, string4, equipmentSlotType, string});
        }
        if ((resourceLocation = ARMOR_TEXTURE_RES_MAP.get(string4)) == null) {
            resourceLocation = new ResourceLocation(string4);
            ARMOR_TEXTURE_RES_MAP.put(string4, resourceLocation);
        }
        if (Config.isCustomItems()) {
            resourceLocation = CustomItems.getCustomArmorTexture(itemStack, equipmentSlotType, string, resourceLocation);
        }
        return resourceLocation;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

