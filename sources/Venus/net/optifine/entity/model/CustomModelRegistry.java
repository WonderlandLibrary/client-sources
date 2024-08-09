/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntityType;
import net.optifine.Config;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.entity.model.ModelAdapterArmorStand;
import net.optifine.entity.model.ModelAdapterBanner;
import net.optifine.entity.model.ModelAdapterBat;
import net.optifine.entity.model.ModelAdapterBed;
import net.optifine.entity.model.ModelAdapterBee;
import net.optifine.entity.model.ModelAdapterBell;
import net.optifine.entity.model.ModelAdapterBlaze;
import net.optifine.entity.model.ModelAdapterBoat;
import net.optifine.entity.model.ModelAdapterBook;
import net.optifine.entity.model.ModelAdapterBookLectern;
import net.optifine.entity.model.ModelAdapterCat;
import net.optifine.entity.model.ModelAdapterCaveSpider;
import net.optifine.entity.model.ModelAdapterChest;
import net.optifine.entity.model.ModelAdapterChestLarge;
import net.optifine.entity.model.ModelAdapterChicken;
import net.optifine.entity.model.ModelAdapterCod;
import net.optifine.entity.model.ModelAdapterConduit;
import net.optifine.entity.model.ModelAdapterCow;
import net.optifine.entity.model.ModelAdapterCreeper;
import net.optifine.entity.model.ModelAdapterDolphin;
import net.optifine.entity.model.ModelAdapterDonkey;
import net.optifine.entity.model.ModelAdapterDragon;
import net.optifine.entity.model.ModelAdapterDrowned;
import net.optifine.entity.model.ModelAdapterElderGuardian;
import net.optifine.entity.model.ModelAdapterEnderChest;
import net.optifine.entity.model.ModelAdapterEnderCrystal;
import net.optifine.entity.model.ModelAdapterEnderman;
import net.optifine.entity.model.ModelAdapterEndermite;
import net.optifine.entity.model.ModelAdapterEvoker;
import net.optifine.entity.model.ModelAdapterEvokerFangs;
import net.optifine.entity.model.ModelAdapterFox;
import net.optifine.entity.model.ModelAdapterGhast;
import net.optifine.entity.model.ModelAdapterGiant;
import net.optifine.entity.model.ModelAdapterGuardian;
import net.optifine.entity.model.ModelAdapterHeadCreeper;
import net.optifine.entity.model.ModelAdapterHeadDragon;
import net.optifine.entity.model.ModelAdapterHeadPlayer;
import net.optifine.entity.model.ModelAdapterHeadSkeleton;
import net.optifine.entity.model.ModelAdapterHeadWitherSkeleton;
import net.optifine.entity.model.ModelAdapterHeadZombie;
import net.optifine.entity.model.ModelAdapterHoglin;
import net.optifine.entity.model.ModelAdapterHorse;
import net.optifine.entity.model.ModelAdapterHusk;
import net.optifine.entity.model.ModelAdapterIllusioner;
import net.optifine.entity.model.ModelAdapterIronGolem;
import net.optifine.entity.model.ModelAdapterLeadKnot;
import net.optifine.entity.model.ModelAdapterLlama;
import net.optifine.entity.model.ModelAdapterLlamaDecor;
import net.optifine.entity.model.ModelAdapterLlamaSpit;
import net.optifine.entity.model.ModelAdapterMagmaCube;
import net.optifine.entity.model.ModelAdapterMinecart;
import net.optifine.entity.model.ModelAdapterMinecartChest;
import net.optifine.entity.model.ModelAdapterMinecartCommandBlock;
import net.optifine.entity.model.ModelAdapterMinecartFurnace;
import net.optifine.entity.model.ModelAdapterMinecartHopper;
import net.optifine.entity.model.ModelAdapterMinecartMobSpawner;
import net.optifine.entity.model.ModelAdapterMinecartTnt;
import net.optifine.entity.model.ModelAdapterMooshroom;
import net.optifine.entity.model.ModelAdapterMule;
import net.optifine.entity.model.ModelAdapterOcelot;
import net.optifine.entity.model.ModelAdapterPanda;
import net.optifine.entity.model.ModelAdapterParrot;
import net.optifine.entity.model.ModelAdapterPhantom;
import net.optifine.entity.model.ModelAdapterPig;
import net.optifine.entity.model.ModelAdapterPiglin;
import net.optifine.entity.model.ModelAdapterPiglinBrute;
import net.optifine.entity.model.ModelAdapterPillager;
import net.optifine.entity.model.ModelAdapterPolarBear;
import net.optifine.entity.model.ModelAdapterPufferFishBig;
import net.optifine.entity.model.ModelAdapterPufferFishMedium;
import net.optifine.entity.model.ModelAdapterPufferFishSmall;
import net.optifine.entity.model.ModelAdapterRabbit;
import net.optifine.entity.model.ModelAdapterRavager;
import net.optifine.entity.model.ModelAdapterSalmon;
import net.optifine.entity.model.ModelAdapterSheep;
import net.optifine.entity.model.ModelAdapterSheepWool;
import net.optifine.entity.model.ModelAdapterShulker;
import net.optifine.entity.model.ModelAdapterShulkerBox;
import net.optifine.entity.model.ModelAdapterShulkerBullet;
import net.optifine.entity.model.ModelAdapterSign;
import net.optifine.entity.model.ModelAdapterSilverfish;
import net.optifine.entity.model.ModelAdapterSkeleton;
import net.optifine.entity.model.ModelAdapterSkeletonHorse;
import net.optifine.entity.model.ModelAdapterSlime;
import net.optifine.entity.model.ModelAdapterSnowman;
import net.optifine.entity.model.ModelAdapterSpider;
import net.optifine.entity.model.ModelAdapterSquid;
import net.optifine.entity.model.ModelAdapterStray;
import net.optifine.entity.model.ModelAdapterStrider;
import net.optifine.entity.model.ModelAdapterTraderLlama;
import net.optifine.entity.model.ModelAdapterTrappedChest;
import net.optifine.entity.model.ModelAdapterTrappedChestLarge;
import net.optifine.entity.model.ModelAdapterTrident;
import net.optifine.entity.model.ModelAdapterTropicalFishA;
import net.optifine.entity.model.ModelAdapterTropicalFishB;
import net.optifine.entity.model.ModelAdapterTurtle;
import net.optifine.entity.model.ModelAdapterVex;
import net.optifine.entity.model.ModelAdapterVillager;
import net.optifine.entity.model.ModelAdapterVindicator;
import net.optifine.entity.model.ModelAdapterWanderingTrader;
import net.optifine.entity.model.ModelAdapterWitch;
import net.optifine.entity.model.ModelAdapterWither;
import net.optifine.entity.model.ModelAdapterWitherSkeleton;
import net.optifine.entity.model.ModelAdapterWitherSkull;
import net.optifine.entity.model.ModelAdapterWolf;
import net.optifine.entity.model.ModelAdapterZoglin;
import net.optifine.entity.model.ModelAdapterZombie;
import net.optifine.entity.model.ModelAdapterZombieHorse;
import net.optifine.entity.model.ModelAdapterZombieVillager;
import net.optifine.entity.model.ModelAdapterZombifiedPiglin;
import net.optifine.util.Either;

public class CustomModelRegistry {
    private static Map<String, ModelAdapter> mapModelAdapters = CustomModelRegistry.makeMapModelAdapters();

    private static Map<String, ModelAdapter> makeMapModelAdapters() {
        LinkedHashMap<String, ModelAdapter> linkedHashMap = new LinkedHashMap<String, ModelAdapter>();
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterArmorStand());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBat());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBee());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBlaze());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBoat());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterCaveSpider());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterChicken());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterCat());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterCow());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterCod());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterCreeper());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterDragon());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterDonkey());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterDolphin());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterDrowned());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterElderGuardian());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEnderCrystal());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEnderman());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEndermite());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEvoker());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEvokerFangs());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterFox());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterGhast());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterGiant());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterGuardian());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHoglin());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHorse());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHusk());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterIllusioner());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterIronGolem());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterLeadKnot());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterLlama());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterLlamaSpit());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMagmaCube());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecart());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartChest());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartCommandBlock());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartFurnace());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartHopper());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartTnt());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMinecartMobSpawner());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMooshroom());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterMule());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterOcelot());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPanda());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterParrot());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPhantom());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPig());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPiglin());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPiglinBrute());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPolarBear());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPillager());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPufferFishBig());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPufferFishMedium());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterPufferFishSmall());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterRabbit());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterRavager());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSalmon());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSheep());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterShulker());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterShulkerBullet());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSilverfish());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSkeleton());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSkeletonHorse());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSlime());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSnowman());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSpider());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSquid());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterStray());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterStrider());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTraderLlama());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTrident());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTropicalFishA());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTropicalFishB());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTurtle());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterVex());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterVillager());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterVindicator());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWanderingTrader());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWitch());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWither());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWitherSkeleton());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWitherSkull());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterWolf());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterZoglin());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterZombie());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterZombieHorse());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterZombieVillager());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterZombifiedPiglin());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSheepWool());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterLlamaDecor());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBanner());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBed());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBell());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBook());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterBookLectern());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterChest());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterChestLarge());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterConduit());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterEnderChest());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadCreeper());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadDragon());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadPlayer());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadSkeleton());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadWitherSkeleton());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterHeadZombie());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterShulkerBox());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterSign());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTrappedChest());
        CustomModelRegistry.addModelAdapter(linkedHashMap, new ModelAdapterTrappedChestLarge());
        return linkedHashMap;
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter) {
        Object object;
        CustomModelRegistry.addModelAdapter(map, modelAdapter, modelAdapter.getName());
        String[] stringArray = modelAdapter.getAliases();
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                object = stringArray[i];
                CustomModelRegistry.addModelAdapter(map, modelAdapter, (String)object);
            }
        }
        Model model = modelAdapter.makeModel();
        object = modelAdapter.getModelRendererNames();
        for (int i = 0; i < ((String[])object).length; ++i) {
            String string = object[i];
            ModelRenderer modelRenderer = modelAdapter.getModelRenderer(model, string);
            if (modelRenderer != null) continue;
            Config.warn("Model renderer not found, model: " + modelAdapter.getName() + ", name: " + string);
        }
    }

    private static void addModelAdapter(Map<String, ModelAdapter> map, ModelAdapter modelAdapter, String string) {
        if (map.containsKey(string)) {
            Object object = "?";
            Either<EntityType, TileEntityType> either = modelAdapter.getType();
            if (either.getLeft().isPresent()) {
                object = either.getLeft().get().getTranslationKey();
            }
            if (either.getRight().isPresent()) {
                object = "" + TileEntityType.getId(either.getRight().get());
            }
            Config.warn("Model adapter already registered for id: " + string + ", type: " + (String)object);
        }
        map.put(string, modelAdapter);
    }

    public static ModelAdapter getModelAdapter(String string) {
        return mapModelAdapters.get(string);
    }

    public static String[] getModelNames() {
        Set<String> set = mapModelAdapters.keySet();
        return set.toArray(new String[set.size()]);
    }
}

