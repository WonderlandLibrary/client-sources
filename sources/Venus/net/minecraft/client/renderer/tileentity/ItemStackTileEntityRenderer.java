/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.entity.model.TridentModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.tileentity.BedTileEntity;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.ConduitTileEntity;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.optifine.EmissiveTextures;
import org.apache.commons.lang3.StringUtils;

public class ItemStackTileEntityRenderer {
    private static final ShulkerBoxTileEntity[] SHULKER_BOXES = (ShulkerBoxTileEntity[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(ShulkerBoxTileEntity::new).toArray(ItemStackTileEntityRenderer::lambda$static$0);
    private static final ShulkerBoxTileEntity SHULKER_BOX = new ShulkerBoxTileEntity((DyeColor)null);
    public static final ItemStackTileEntityRenderer instance = new ItemStackTileEntityRenderer();
    private final ChestTileEntity chestBasic = new ChestTileEntity();
    private final ChestTileEntity chestTrap = new TrappedChestTileEntity();
    private final EnderChestTileEntity enderChest = new EnderChestTileEntity();
    private final BannerTileEntity banner = new BannerTileEntity();
    private final BedTileEntity bed = new BedTileEntity();
    private final ConduitTileEntity conduit = new ConduitTileEntity();
    private final ShieldModel modelShield = new ShieldModel();
    public TridentModel trident = new TridentModel();

    public void func_239207_a_(ItemStack itemStack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        if (EmissiveTextures.isActive()) {
            EmissiveTextures.beginRender();
        }
        this.renderRaw(itemStack, matrixStack, iRenderTypeBuffer, n, n2);
        if (EmissiveTextures.isActive()) {
            if (EmissiveTextures.hasEmissive()) {
                EmissiveTextures.beginRenderEmissive();
                this.renderRaw(itemStack, matrixStack, iRenderTypeBuffer, LightTexture.MAX_BRIGHTNESS, n2);
                EmissiveTextures.endRenderEmissive();
            }
            EmissiveTextures.endRender();
        }
    }

    public void renderRaw(ItemStack itemStack, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        Item item = itemStack.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem)item).getBlock();
            if (block instanceof AbstractSkullBlock) {
                GameProfile gameProfile = null;
                if (itemStack.hasTag()) {
                    CompoundNBT compoundNBT = itemStack.getTag();
                    if (compoundNBT.contains("SkullOwner", 1)) {
                        gameProfile = NBTUtil.readGameProfile(compoundNBT.getCompound("SkullOwner"));
                    } else if (compoundNBT.contains("SkullOwner", 1) && !StringUtils.isBlank(compoundNBT.getString("SkullOwner"))) {
                        GameProfile gameProfile2 = new GameProfile(null, compoundNBT.getString("SkullOwner"));
                        gameProfile = SkullTileEntity.updateGameProfile(gameProfile2);
                        compoundNBT.remove("SkullOwner");
                        compoundNBT.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameProfile));
                    }
                }
                SkullTileEntityRenderer.render(null, 180.0f, ((AbstractSkullBlock)block).getSkullType(), gameProfile, 0.0f, matrixStack, iRenderTypeBuffer, n);
            } else {
                TileEntity tileEntity;
                if (block instanceof AbstractBannerBlock) {
                    this.banner.loadFromItemStack(itemStack, ((AbstractBannerBlock)block).getColor());
                    tileEntity = this.banner;
                } else if (block instanceof BedBlock) {
                    this.bed.setColor(((BedBlock)block).getColor());
                    tileEntity = this.bed;
                } else if (block == Blocks.CONDUIT) {
                    tileEntity = this.conduit;
                } else if (block == Blocks.CHEST) {
                    tileEntity = this.chestBasic;
                } else if (block == Blocks.ENDER_CHEST) {
                    tileEntity = this.enderChest;
                } else if (block == Blocks.TRAPPED_CHEST) {
                    tileEntity = this.chestTrap;
                } else {
                    if (!(block instanceof ShulkerBoxBlock)) {
                        return;
                    }
                    DyeColor dyeColor = ShulkerBoxBlock.getColorFromItem(item);
                    tileEntity = dyeColor == null ? SHULKER_BOX : SHULKER_BOXES[dyeColor.getId()];
                }
                TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStack, iRenderTypeBuffer, n, n2);
            }
        } else if (item == Items.SHIELD) {
            boolean bl = itemStack.getChildTag("BlockEntityTag") != null;
            matrixStack.push();
            matrixStack.scale(1.0f, -1.0f, -1.0f);
            RenderMaterial renderMaterial = bl ? ModelBakery.LOCATION_SHIELD_BASE : ModelBakery.LOCATION_SHIELD_NO_PATTERN;
            IVertexBuilder iVertexBuilder = renderMaterial.getSprite().wrapBuffer(ItemRenderer.getEntityGlintVertexBuilder(iRenderTypeBuffer, this.modelShield.getRenderType(renderMaterial.getAtlasLocation()), true, itemStack.hasEffect()));
            this.modelShield.func_228294_b_().render(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
            if (bl) {
                List<Pair<BannerPattern, DyeColor>> list = BannerTileEntity.getPatternColorData(ShieldItem.getColor(itemStack), BannerTileEntity.getPatternData(itemStack));
                BannerTileEntityRenderer.func_241717_a_(matrixStack, iRenderTypeBuffer, n, n2, this.modelShield.func_228293_a_(), renderMaterial, false, list, itemStack.hasEffect());
            } else {
                this.modelShield.func_228293_a_().render(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
            }
            matrixStack.pop();
        } else if (item == Items.TRIDENT) {
            matrixStack.push();
            matrixStack.scale(1.0f, -1.0f, -1.0f);
            IVertexBuilder iVertexBuilder = ItemRenderer.getEntityGlintVertexBuilder(iRenderTypeBuffer, this.trident.getRenderType(TridentModel.TEXTURE_LOCATION), false, itemStack.hasEffect());
            this.trident.render(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }

    private static ShulkerBoxTileEntity[] lambda$static$0(int n) {
        return new ShulkerBoxTileEntity[n];
    }
}

