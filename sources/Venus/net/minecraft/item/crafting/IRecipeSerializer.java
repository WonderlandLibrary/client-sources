/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.ArmorDyeRecipe;
import net.minecraft.item.crafting.BannerDuplicateRecipe;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.BookCloningRecipe;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.FireworkRocketRecipe;
import net.minecraft.item.crafting.FireworkStarFadeRecipe;
import net.minecraft.item.crafting.FireworkStarRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.MapCloningRecipe;
import net.minecraft.item.crafting.MapExtendingRecipe;
import net.minecraft.item.crafting.RepairItemRecipe;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.item.crafting.ShieldRecipes;
import net.minecraft.item.crafting.ShulkerBoxColoringRecipe;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.item.crafting.StonecuttingRecipe;
import net.minecraft.item.crafting.SuspiciousStewRecipe;
import net.minecraft.item.crafting.TippedArrowRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IRecipeSerializer<T extends IRecipe<?>> {
    public static final IRecipeSerializer<ShapedRecipe> CRAFTING_SHAPED = IRecipeSerializer.register("crafting_shaped", new ShapedRecipe.Serializer());
    public static final IRecipeSerializer<ShapelessRecipe> CRAFTING_SHAPELESS = IRecipeSerializer.register("crafting_shapeless", new ShapelessRecipe.Serializer());
    public static final SpecialRecipeSerializer<ArmorDyeRecipe> CRAFTING_SPECIAL_ARMORDYE = IRecipeSerializer.register("crafting_special_armordye", new SpecialRecipeSerializer<ArmorDyeRecipe>(ArmorDyeRecipe::new));
    public static final SpecialRecipeSerializer<BookCloningRecipe> CRAFTING_SPECIAL_BOOKCLONING = IRecipeSerializer.register("crafting_special_bookcloning", new SpecialRecipeSerializer<BookCloningRecipe>(BookCloningRecipe::new));
    public static final SpecialRecipeSerializer<MapCloningRecipe> CRAFTING_SPECIAL_MAPCLONING = IRecipeSerializer.register("crafting_special_mapcloning", new SpecialRecipeSerializer<MapCloningRecipe>(MapCloningRecipe::new));
    public static final SpecialRecipeSerializer<MapExtendingRecipe> CRAFTING_SPECIAL_MAPEXTENDING = IRecipeSerializer.register("crafting_special_mapextending", new SpecialRecipeSerializer<MapExtendingRecipe>(MapExtendingRecipe::new));
    public static final SpecialRecipeSerializer<FireworkRocketRecipe> CRAFTING_SPECIAL_FIREWORK_ROCKET = IRecipeSerializer.register("crafting_special_firework_rocket", new SpecialRecipeSerializer<FireworkRocketRecipe>(FireworkRocketRecipe::new));
    public static final SpecialRecipeSerializer<FireworkStarRecipe> CRAFTING_SPECIAL_FIREWORK_STAR = IRecipeSerializer.register("crafting_special_firework_star", new SpecialRecipeSerializer<FireworkStarRecipe>(FireworkStarRecipe::new));
    public static final SpecialRecipeSerializer<FireworkStarFadeRecipe> CRAFTING_SPECIAL_FIREWORK_STAR_FADE = IRecipeSerializer.register("crafting_special_firework_star_fade", new SpecialRecipeSerializer<FireworkStarFadeRecipe>(FireworkStarFadeRecipe::new));
    public static final SpecialRecipeSerializer<TippedArrowRecipe> CRAFTING_SPECIAL_TIPPEDARROW = IRecipeSerializer.register("crafting_special_tippedarrow", new SpecialRecipeSerializer<TippedArrowRecipe>(TippedArrowRecipe::new));
    public static final SpecialRecipeSerializer<BannerDuplicateRecipe> CRAFTING_SPECIAL_BANNERDUPLICATE = IRecipeSerializer.register("crafting_special_bannerduplicate", new SpecialRecipeSerializer<BannerDuplicateRecipe>(BannerDuplicateRecipe::new));
    public static final SpecialRecipeSerializer<ShieldRecipes> CRAFTING_SPECIAL_SHIELD = IRecipeSerializer.register("crafting_special_shielddecoration", new SpecialRecipeSerializer<ShieldRecipes>(ShieldRecipes::new));
    public static final SpecialRecipeSerializer<ShulkerBoxColoringRecipe> CRAFTING_SPECIAL_SHULKERBOXCOLORING = IRecipeSerializer.register("crafting_special_shulkerboxcoloring", new SpecialRecipeSerializer<ShulkerBoxColoringRecipe>(ShulkerBoxColoringRecipe::new));
    public static final SpecialRecipeSerializer<SuspiciousStewRecipe> CRAFTING_SPECIAL_SUSPICIOUSSTEW = IRecipeSerializer.register("crafting_special_suspiciousstew", new SpecialRecipeSerializer<SuspiciousStewRecipe>(SuspiciousStewRecipe::new));
    public static final SpecialRecipeSerializer<RepairItemRecipe> CRAFTING_SPECIAL_REPAIRITEM = IRecipeSerializer.register("crafting_special_repairitem", new SpecialRecipeSerializer<RepairItemRecipe>(RepairItemRecipe::new));
    public static final CookingRecipeSerializer<FurnaceRecipe> SMELTING = IRecipeSerializer.register("smelting", new CookingRecipeSerializer<FurnaceRecipe>(FurnaceRecipe::new, 200));
    public static final CookingRecipeSerializer<BlastingRecipe> BLASTING = IRecipeSerializer.register("blasting", new CookingRecipeSerializer<BlastingRecipe>(BlastingRecipe::new, 100));
    public static final CookingRecipeSerializer<SmokingRecipe> SMOKING = IRecipeSerializer.register("smoking", new CookingRecipeSerializer<SmokingRecipe>(SmokingRecipe::new, 100));
    public static final CookingRecipeSerializer<CampfireCookingRecipe> CAMPFIRE_COOKING = IRecipeSerializer.register("campfire_cooking", new CookingRecipeSerializer<CampfireCookingRecipe>(CampfireCookingRecipe::new, 100));
    public static final IRecipeSerializer<StonecuttingRecipe> STONECUTTING = IRecipeSerializer.register("stonecutting", new SingleItemRecipe.Serializer<StonecuttingRecipe>(StonecuttingRecipe::new));
    public static final IRecipeSerializer<SmithingRecipe> SMITHING = IRecipeSerializer.register("smithing", new SmithingRecipe.Serializer());

    public T read(ResourceLocation var1, JsonObject var2);

    public T read(ResourceLocation var1, PacketBuffer var2);

    public void write(PacketBuffer var1, T var2);

    public static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(String string, S s) {
        return (S)Registry.register(Registry.RECIPE_SERIALIZER, string, s);
    }
}

