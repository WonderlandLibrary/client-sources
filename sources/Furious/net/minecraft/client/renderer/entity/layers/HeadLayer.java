package net.minecraft.client.renderer.entity.layers;

import com.google.common.eventbus.Subscribe;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import im.expensive.functions.impl.render.ChinaHat;
import im.expensive.functions.impl.render.HUD;
import im.expensive.utils.render.ColorUtils;
import java.util.UUID;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.monster.ZombieVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class HeadLayer<T extends LivingEntity, M extends EntityModel<T> & IHasHead> extends LayerRenderer<T, M> {
    private final float field_239402_a_;
    private final float field_239403_b_;
    private final float field_239404_c_;

    public HeadLayer(IEntityRenderer<T, M> iEntityRenderer) {
        this(iEntityRenderer, 1.0F, 1.0F, 1.0F);
    }

    public HeadLayer(IEntityRenderer<T, M> iEntityRenderer, float f, float f2, float f3) {
        super(iEntityRenderer);
        this.field_239402_a_ = f;
        this.field_239403_b_ = f2;
        this.field_239404_c_ = f3;
    }

    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        ItemStack itemStack = t.getItemStackFromSlot(EquipmentSlotType.HEAD);
        if (!itemStack.isEmpty()) {
            Object object = itemStack.getItem();
            matrixStack.push();
            matrixStack.scale(this.field_239402_a_, this.field_239403_b_, this.field_239404_c_);
            boolean bl = t instanceof VillagerEntity || t instanceof ZombieVillagerEntity;
            float f7;
            if (t.isChild() && !(t instanceof VillagerEntity)) {
                f7 = 2.0F;
                float f8 = 1.4F;
                matrixStack.translate(0.0, 0.03125, 0.0);
                matrixStack.scale(0.7F, 0.7F, 0.7F);
                matrixStack.translate(0.0, 1.0, 0.0);
            }

            ((IHasHead) this.getEntityModel()).getModelHead().translateRotate(matrixStack);
            if (object instanceof BlockItem && ((BlockItem) object).getBlock() instanceof AbstractSkullBlock) {
                f7 = 1.1875F;
                matrixStack.scale(1.1875F, -1.1875F, -1.1875F);
                if (bl) {
                    matrixStack.translate(0.0, 0.0625, 0.0);
                }

                GameProfile gameProfile = null;
                if (itemStack.hasTag()) {
                    CompoundNBT compoundNBT = itemStack.getTag();
                    if (compoundNBT.contains("SkullOwner", 1)) {
                        gameProfile = NBTUtil.readGameProfile(compoundNBT.getCompound("SkullOwner"));
                    } else {
                        String string;
                        if (compoundNBT.contains("SkullOwner", 1) && !StringUtils.isBlank(string = compoundNBT.getString("SkullOwner"))) {
                            gameProfile = SkullTileEntity.updateGameProfile(new GameProfile((UUID) null, string));
                            compoundNBT.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile));
                        }
                    }
                }

                matrixStack.translate(-0.5, 0.0, -0.5);
                SkullTileEntityRenderer.render((Direction) null, 180.0F, ((AbstractSkullBlock) ((BlockItem) object).getBlock()).getSkullType(), gameProfile, f, matrixStack, iRenderTypeBuffer, n);
            } else if (!(object instanceof ArmorItem) || ((ArmorItem) object).getEquipmentSlot() != EquipmentSlotType.HEAD) {
                f7 = 0.625F;
                matrixStack.translate(0.0, -0.25, 0.0);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
                matrixStack.scale(0.625F, -0.625F, -0.625F);
                if (bl) {
                    matrixStack.translate(0.0, 0.1875, 0.0);
                }

                Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(t, itemStack, TransformType.HEAD, false, matrixStack, iRenderTypeBuffer, n);
            }

            matrixStack.pop();
        }
    }
}
