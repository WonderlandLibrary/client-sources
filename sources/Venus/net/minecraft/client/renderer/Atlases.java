/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.block.WoodType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.EnderChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.ResourceLocation;

public class Atlases {
    public static final ResourceLocation SHULKER_BOX_ATLAS = new ResourceLocation("textures/atlas/shulker_boxes.png");
    public static final ResourceLocation BED_ATLAS = new ResourceLocation("textures/atlas/beds.png");
    public static final ResourceLocation BANNER_ATLAS = new ResourceLocation("textures/atlas/banner_patterns.png");
    public static final ResourceLocation SHIELD_ATLAS = new ResourceLocation("textures/atlas/shield_patterns.png");
    public static final ResourceLocation SIGN_ATLAS = new ResourceLocation("textures/atlas/signs.png");
    public static final ResourceLocation CHEST_ATLAS = new ResourceLocation("textures/atlas/chest.png");
    private static final RenderType SHULKER_BOX_TYPE = RenderType.getEntityCutoutNoCull(SHULKER_BOX_ATLAS);
    private static final RenderType BED_TYPE = RenderType.getEntitySolid(BED_ATLAS);
    private static final RenderType BANNER_TYPE = RenderType.getEntityNoOutline(BANNER_ATLAS);
    private static final RenderType SHIELD_TYPE = RenderType.getEntityNoOutline(SHIELD_ATLAS);
    private static final RenderType SIGN_TYPE = RenderType.getEntityCutoutNoCull(SIGN_ATLAS);
    private static final RenderType CHEST_TYPE = RenderType.getEntityCutout(CHEST_ATLAS);
    private static final RenderType SOLID_BLOCK_TYPE = RenderType.getEntitySolid(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    private static final RenderType CUTOUT_BLOCK_TYPE = RenderType.getEntityCutout(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    private static final RenderType ITEM_ENTITY_TRANSLUCENT_CULL_BLOCK_TYPE = RenderType.getItemEntityTranslucentCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    private static final RenderType TRANSLUCENT_CULL_BLOCK_TYPE = RenderType.getEntityTranslucentCull(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
    public static final RenderMaterial DEFAULT_SHULKER_TEXTURE = new RenderMaterial(SHULKER_BOX_ATLAS, new ResourceLocation("entity/shulker/shulker"));
    public static final List<RenderMaterial> SHULKER_TEXTURES = Stream.of("white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black").map(Atlases::lambda$static$0).collect(ImmutableList.toImmutableList());
    public static final Map<WoodType, RenderMaterial> SIGN_MATERIALS = WoodType.getValues().collect(Collectors.toMap(Function.identity(), Atlases::getSignMaterial));
    public static final RenderMaterial[] BED_TEXTURES = (RenderMaterial[])Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(Atlases::lambda$static$1).toArray(Atlases::lambda$static$2);
    public static final RenderMaterial CHEST_TRAPPED_MATERIAL = Atlases.getChestMaterial("trapped");
    public static final RenderMaterial CHEST_TRAPPED_LEFT_MATERIAL = Atlases.getChestMaterial("trapped_left");
    public static final RenderMaterial CHEST_TRAPPED_RIGHT_MATERIAL = Atlases.getChestMaterial("trapped_right");
    public static final RenderMaterial CHEST_XMAS_MATERIAL = Atlases.getChestMaterial("christmas");
    public static final RenderMaterial CHEST_XMAS_LEFT_MATERIAL = Atlases.getChestMaterial("christmas_left");
    public static final RenderMaterial CHEST_XMAS_RIGHT_MATERIAL = Atlases.getChestMaterial("christmas_right");
    public static final RenderMaterial CHEST_MATERIAL = Atlases.getChestMaterial("normal");
    public static final RenderMaterial CHEST_LEFT_MATERIAL = Atlases.getChestMaterial("normal_left");
    public static final RenderMaterial CHEST_RIGHT_MATERIAL = Atlases.getChestMaterial("normal_right");
    public static final RenderMaterial ENDER_CHEST_MATERIAL = Atlases.getChestMaterial("ender");

    public static RenderType getBannerType() {
        return BANNER_TYPE;
    }

    public static RenderType getShieldType() {
        return SHIELD_TYPE;
    }

    public static RenderType getBedType() {
        return BED_TYPE;
    }

    public static RenderType getShulkerBoxType() {
        return SHULKER_BOX_TYPE;
    }

    public static RenderType getSignType() {
        return SIGN_TYPE;
    }

    public static RenderType getChestType() {
        return CHEST_TYPE;
    }

    public static RenderType getSolidBlockType() {
        return SOLID_BLOCK_TYPE;
    }

    public static RenderType getCutoutBlockType() {
        return CUTOUT_BLOCK_TYPE;
    }

    public static RenderType getItemEntityTranslucentCullType() {
        return ITEM_ENTITY_TRANSLUCENT_CULL_BLOCK_TYPE;
    }

    public static RenderType getTranslucentCullBlockType() {
        return TRANSLUCENT_CULL_BLOCK_TYPE;
    }

    public static void collectAllMaterials(Consumer<RenderMaterial> consumer) {
        consumer.accept(DEFAULT_SHULKER_TEXTURE);
        SHULKER_TEXTURES.forEach(consumer);
        for (BannerPattern bannerPattern : BannerPattern.values()) {
            consumer.accept(new RenderMaterial(BANNER_ATLAS, bannerPattern.getTextureLocation(false)));
            consumer.accept(new RenderMaterial(SHIELD_ATLAS, bannerPattern.getTextureLocation(true)));
        }
        SIGN_MATERIALS.values().forEach(consumer);
        for (RenderMaterial renderMaterial : BED_TEXTURES) {
            consumer.accept(renderMaterial);
        }
        consumer.accept(CHEST_TRAPPED_MATERIAL);
        consumer.accept(CHEST_TRAPPED_LEFT_MATERIAL);
        consumer.accept(CHEST_TRAPPED_RIGHT_MATERIAL);
        consumer.accept(CHEST_XMAS_MATERIAL);
        consumer.accept(CHEST_XMAS_LEFT_MATERIAL);
        consumer.accept(CHEST_XMAS_RIGHT_MATERIAL);
        consumer.accept(CHEST_MATERIAL);
        consumer.accept(CHEST_LEFT_MATERIAL);
        consumer.accept(CHEST_RIGHT_MATERIAL);
        consumer.accept(ENDER_CHEST_MATERIAL);
    }

    public static RenderMaterial getSignMaterial(WoodType woodType) {
        return new RenderMaterial(SIGN_ATLAS, new ResourceLocation("entity/signs/" + woodType.getName()));
    }

    private static RenderMaterial getChestMaterial(String string) {
        return new RenderMaterial(CHEST_ATLAS, new ResourceLocation("entity/chest/" + string));
    }

    public static RenderMaterial getChestMaterial(TileEntity tileEntity, ChestType chestType, boolean bl) {
        if (bl) {
            return Atlases.getChestMaterial(chestType, CHEST_XMAS_MATERIAL, CHEST_XMAS_LEFT_MATERIAL, CHEST_XMAS_RIGHT_MATERIAL);
        }
        if (tileEntity instanceof TrappedChestTileEntity) {
            return Atlases.getChestMaterial(chestType, CHEST_TRAPPED_MATERIAL, CHEST_TRAPPED_LEFT_MATERIAL, CHEST_TRAPPED_RIGHT_MATERIAL);
        }
        return tileEntity instanceof EnderChestTileEntity ? ENDER_CHEST_MATERIAL : Atlases.getChestMaterial(chestType, CHEST_MATERIAL, CHEST_LEFT_MATERIAL, CHEST_RIGHT_MATERIAL);
    }

    private static RenderMaterial getChestMaterial(ChestType chestType, RenderMaterial renderMaterial, RenderMaterial renderMaterial2, RenderMaterial renderMaterial3) {
        switch (1.$SwitchMap$net$minecraft$state$properties$ChestType[chestType.ordinal()]) {
            case 1: {
                return renderMaterial2;
            }
            case 2: {
                return renderMaterial3;
            }
        }
        return renderMaterial;
    }

    private static RenderMaterial[] lambda$static$2(int n) {
        return new RenderMaterial[n];
    }

    private static RenderMaterial lambda$static$1(DyeColor dyeColor) {
        return new RenderMaterial(BED_ATLAS, new ResourceLocation("entity/bed/" + dyeColor.getTranslationKey()));
    }

    private static RenderMaterial lambda$static$0(String string) {
        return new RenderMaterial(SHULKER_BOX_ATLAS, new ResourceLocation("entity/shulker/shulker_" + string));
    }
}

