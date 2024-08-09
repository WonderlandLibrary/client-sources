/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.commons.lang3.StringUtils;

public class HeadLayer<T extends LivingEntity, M extends EntityModel<T>>
extends LayerRenderer<T, M> {
    private final float field_239402_a_;
    private final float field_239403_b_;
    private final float field_239404_c_;

    public HeadLayer(IEntityRenderer<T, M> iEntityRenderer) {
        this(iEntityRenderer, 1.0f, 1.0f, 1.0f);
    }

    public HeadLayer(IEntityRenderer<T, M> iEntityRenderer, float f, float f2, float f3) {
        super(iEntityRenderer);
        this.field_239402_a_ = f;
        this.field_239403_b_ = f2;
        this.field_239404_c_ = f3;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = ((LivingEntity)t).getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (!itemStack.isEmpty()) {
            float f7;
            boolean bl;
            Item item = itemStack.getItem();
            matrixStack.push();
            matrixStack.scale(this.field_239402_a_, this.field_239403_b_, this.field_239404_c_);
            boolean bl2 = bl = t instanceof VillagerEntity || t instanceof ZombieVillagerEntity;
            if (((LivingEntity)t).isChild() && !(t instanceof VillagerEntity)) {
                f7 = 2.0f;
                float f8 = 1.4f;
                matrixStack.translate(0.0, 0.03125, 0.0);
                matrixStack.scale(0.7f, 0.7f, 0.7f);
                matrixStack.translate(0.0, 1.0, 0.0);
            }
            ((IHasHead)this.getEntityModel()).getModelHead().translateRotate(matrixStack);
            if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
                f7 = 1.1875f;
                matrixStack.scale(1.1875f, -1.1875f, -1.1875f);
                if (bl) {
                    matrixStack.translate(0.0, 0.0625, 0.0);
                }
                GameProfile gameProfile = null;
                if (itemStack.hasTag()) {
                    String string;
                    CompoundNBT compoundNBT = itemStack.getTag();
                    if (compoundNBT.contains("SkullOwner", 1)) {
                        gameProfile = NBTUtil.readGameProfile(compoundNBT.getCompound("SkullOwner"));
                    } else if (compoundNBT.contains("SkullOwner", 1) && !StringUtils.isBlank(string = compoundNBT.getString("SkullOwner"))) {
                        gameProfile = SkullTileEntity.updateGameProfile(new GameProfile(null, string));
                        compoundNBT.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile));
                    }
                }
                matrixStack.translate(-0.5, 0.0, -0.5);
                SkullTileEntityRenderer.render(null, 180.0f, ((AbstractSkullBlock)((BlockItem)item).getBlock()).getSkullType(), gameProfile, f, matrixStack, iRenderTypeBuffer, n);
            } else if (!(item instanceof ArmorItem) || ((ArmorItem)item).getEquipmentSlot() != EquipmentSlotType.HEAD) {
                f7 = 0.625f;
                matrixStack.translate(0.0, -0.25, 0.0);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f));
                matrixStack.scale(0.625f, -0.625f, -0.625f);
                if (bl) {
                    matrixStack.translate(0.0, 0.1875, 0.0);
                }
                Minecraft.getInstance().getFirstPersonRenderer().renderItemSide((LivingEntity)t, itemStack, ItemCameraTransforms.TransformType.HEAD, false, matrixStack, iRenderTypeBuffer, n);
            }
            matrixStack.pop();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

