/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.platform.GlStateManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemModelGenerator;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.optifine.Config;
import net.optifine.CustomItemProperties;
import net.optifine.CustomItemsComparator;
import net.optifine.config.NbtTagValue;
import net.optifine.render.Blender;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.EnchantmentUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;

public class CustomItems {
    private static CustomItemProperties[][] itemProperties = null;
    private static CustomItemProperties[][] enchantmentProperties = null;
    private static Map mapPotionIds = null;
    private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
    private static boolean useGlint = true;
    private static boolean renderOffHand = false;
    public static final int MASK_POTION_SPLASH = 16384;
    public static final int MASK_POTION_NAME = 63;
    public static final int MASK_POTION_EXTENDED = 64;
    public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
    public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
    public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
    public static final String DEFAULT_TEXTURE_OVERLAY = "item/potion_overlay";
    public static final String DEFAULT_TEXTURE_SPLASH = "item/potion_bottle_splash";
    public static final String DEFAULT_TEXTURE_DRINKABLE = "item/potion_bottle_drinkable";
    private static final int[][] EMPTY_INT2_ARRAY = new int[0][];
    private static final Map<String, Integer> mapPotionDamages = CustomItems.makeMapPotionDamages();
    private static final String TYPE_POTION_NORMAL = "normal";
    private static final String TYPE_POTION_SPLASH = "splash";
    private static final String TYPE_POTION_LINGER = "linger";

    public static void update() {
        itemProperties = null;
        enchantmentProperties = null;
        useGlint = true;
        if (Config.isCustomItems()) {
            CustomItems.readCitProperties("optifine/cit.properties");
            IResourcePack[] iResourcePackArray = Config.getResourcePacks();
            for (int i = iResourcePackArray.length - 1; i >= 0; --i) {
                IResourcePack iResourcePack = iResourcePackArray[i];
                CustomItems.update(iResourcePack);
            }
            CustomItems.update(Config.getDefaultResourcePack());
            if (itemProperties.length <= 0) {
                itemProperties = null;
            }
            if (enchantmentProperties.length <= 0) {
                enchantmentProperties = null;
            }
        }
    }

    private static void readCitProperties(String string) {
        try {
            ResourceLocation resourceLocation = new ResourceLocation(string);
            InputStream inputStream = Config.getResourceStream(resourceLocation);
            if (inputStream == null) {
                return;
            }
            Config.dbg("CustomItems: Loading " + string);
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            propertiesOrdered.load(inputStream);
            inputStream.close();
            useGlint = Config.parseBoolean(propertiesOrdered.getProperty("useGlint"), true);
        } catch (FileNotFoundException fileNotFoundException) {
            return;
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    private static void update(IResourcePack iResourcePack) {
        int n;
        Object object;
        Object[] objectArray;
        Collection<Object> collection;
        Object[] objectArray2 = ResUtils.collectFiles(iResourcePack, "optifine/cit/", ".properties", (String[])null);
        Map<String, CustomItemProperties> map = CustomItems.makeAutoImageProperties(iResourcePack);
        if (map.size() > 0) {
            collection = map.keySet();
            objectArray = collection.toArray(new String[collection.size()]);
            objectArray2 = (String[])Config.addObjectsToArray(objectArray2, objectArray);
        }
        Arrays.sort(objectArray2);
        collection = CustomItems.makePropertyList(itemProperties);
        objectArray = CustomItems.makePropertyList(enchantmentProperties);
        for (int i = 0; i < objectArray2.length; ++i) {
            Object object2 = objectArray2[i];
            Config.dbg("CustomItems: " + (String)object2);
            try {
                object = null;
                if (map.containsKey(object2)) {
                    object = map.get(object2);
                }
                if (object == null) {
                    ResourceLocation resourceLocation = new ResourceLocation((String)object2);
                    InputStream inputStream = iResourcePack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, resourceLocation);
                    if (inputStream == null) {
                        Config.warn("CustomItems file not found: " + (String)object2);
                        continue;
                    }
                    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                    propertiesOrdered.load(inputStream);
                    inputStream.close();
                    object = new CustomItemProperties(propertiesOrdered, (String)object2);
                }
                if (!object.isValid((String)object2)) continue;
                CustomItems.addToItemList((CustomItemProperties)object, collection);
                CustomItems.addToEnchantmentList((CustomItemProperties)object, (List<List<CustomItemProperties>>)objectArray);
                continue;
            } catch (FileNotFoundException fileNotFoundException) {
                Config.warn("CustomItems file not found: " + (String)object2);
                continue;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        itemProperties = CustomItems.propertyListToArray(collection);
        enchantmentProperties = CustomItems.propertyListToArray((List)objectArray);
        Comparator comparator = CustomItems.getPropertiesComparator();
        for (n = 0; n < itemProperties.length; ++n) {
            object = itemProperties[n];
            if (object == null) continue;
            Arrays.sort(object, comparator);
        }
        for (n = 0; n < enchantmentProperties.length; ++n) {
            object = enchantmentProperties[n];
            if (object == null) continue;
            Arrays.sort(object, comparator);
        }
    }

    private static Comparator getPropertiesComparator() {
        return new Comparator(){

            public int compare(Object object, Object object2) {
                CustomItemProperties customItemProperties = (CustomItemProperties)object;
                CustomItemProperties customItemProperties2 = (CustomItemProperties)object2;
                if (customItemProperties.layer != customItemProperties2.layer) {
                    return customItemProperties.layer - customItemProperties2.layer;
                }
                if (customItemProperties.weight != customItemProperties2.weight) {
                    return customItemProperties2.weight - customItemProperties.weight;
                }
                return !customItemProperties.basePath.equals(customItemProperties2.basePath) ? customItemProperties.basePath.compareTo(customItemProperties2.basePath) : customItemProperties.name.compareTo(customItemProperties2.name);
            }
        };
    }

    public static void updateIcons(AtlasTexture atlasTexture) {
        for (CustomItemProperties customItemProperties : CustomItems.getAllProperties()) {
            customItemProperties.updateIcons(atlasTexture);
        }
    }

    public static void refreshIcons(AtlasTexture atlasTexture) {
        for (CustomItemProperties customItemProperties : CustomItems.getAllProperties()) {
            customItemProperties.refreshIcons(atlasTexture);
        }
    }

    public static void loadModels(ModelBakery modelBakery) {
        for (CustomItemProperties customItemProperties : CustomItems.getAllProperties()) {
            customItemProperties.loadModels(modelBakery);
        }
    }

    public static void updateModels() {
        for (CustomItemProperties customItemProperties : CustomItems.getAllProperties()) {
            if (customItemProperties.type != 1) continue;
            AtlasTexture atlasTexture = Config.getTextureMap();
            customItemProperties.updateModelTexture(atlasTexture, itemModelGenerator);
            customItemProperties.updateModelsFull();
        }
    }

    private static List<CustomItemProperties> getAllProperties() {
        ArrayList<CustomItemProperties> arrayList = new ArrayList<CustomItemProperties>();
        CustomItems.addAll(itemProperties, arrayList);
        CustomItems.addAll(enchantmentProperties, arrayList);
        return arrayList;
    }

    private static void addAll(CustomItemProperties[][] customItemPropertiesArray, List<CustomItemProperties> list) {
        if (customItemPropertiesArray != null) {
            for (int i = 0; i < customItemPropertiesArray.length; ++i) {
                CustomItemProperties[] customItemPropertiesArray2 = customItemPropertiesArray[i];
                if (customItemPropertiesArray2 == null) continue;
                for (int j = 0; j < customItemPropertiesArray2.length; ++j) {
                    CustomItemProperties customItemProperties = customItemPropertiesArray2[j];
                    if (customItemProperties == null) continue;
                    list.add(customItemProperties);
                }
            }
        }
    }

    private static Map<String, CustomItemProperties> makeAutoImageProperties(IResourcePack iResourcePack) {
        HashMap<String, CustomItemProperties> hashMap = new HashMap<String, CustomItemProperties>();
        hashMap.putAll(CustomItems.makePotionImageProperties(iResourcePack, TYPE_POTION_NORMAL, Registry.ITEM.getKey(Items.POTION)));
        hashMap.putAll(CustomItems.makePotionImageProperties(iResourcePack, TYPE_POTION_SPLASH, Registry.ITEM.getKey(Items.SPLASH_POTION)));
        hashMap.putAll(CustomItems.makePotionImageProperties(iResourcePack, TYPE_POTION_LINGER, Registry.ITEM.getKey(Items.LINGERING_POTION)));
        return hashMap;
    }

    private static Map<String, CustomItemProperties> makePotionImageProperties(IResourcePack iResourcePack, String string, ResourceLocation resourceLocation) {
        HashMap<String, CustomItemProperties> hashMap = new HashMap<String, CustomItemProperties>();
        String string2 = string + "/";
        String[] stringArray = new String[]{"optifine/cit/potion/" + string2, "optifine/cit/Potion/" + string2};
        String[] stringArray2 = new String[]{".png"};
        String[] stringArray3 = ResUtils.collectFiles(iResourcePack, stringArray, stringArray2);
        for (int i = 0; i < stringArray3.length; ++i) {
            String string3 = stringArray3[i];
            String string4 = StrUtils.removePrefixSuffix(string3, stringArray, stringArray2);
            Properties properties = CustomItems.makePotionProperties(string4, string, resourceLocation, string3);
            if (properties == null) continue;
            String string5 = StrUtils.removeSuffix(string3, stringArray2) + ".properties";
            CustomItemProperties customItemProperties = new CustomItemProperties(properties, string5);
            hashMap.put(string5, customItemProperties);
        }
        return hashMap;
    }

    private static Properties makePotionProperties(String string, String string2, ResourceLocation resourceLocation, String string3) {
        int n;
        if (StrUtils.endsWith(string, new String[]{"_n", "_s"})) {
            return null;
        }
        if (string.equals("empty") && string2.equals(TYPE_POTION_NORMAL)) {
            resourceLocation = Registry.ITEM.getKey(Items.GLASS_BOTTLE);
            PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
            ((Hashtable)propertiesOrdered).put("type", "item");
            ((Hashtable)propertiesOrdered).put("items", resourceLocation.toString());
            return propertiesOrdered;
        }
        int[] nArray = (int[])CustomItems.getMapPotionIds().get(string);
        if (nArray == null) {
            Config.warn("Potion not found for image: " + string3);
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (n = 0; n < nArray.length; ++n) {
            int n2 = nArray[n];
            if (string2.equals(TYPE_POTION_SPLASH)) {
                n2 |= 0x4000;
            }
            if (n > 0) {
                stringBuffer.append(" ");
            }
            stringBuffer.append(n2);
        }
        n = 16447;
        if (string.equals("water") || string.equals("mundane")) {
            n |= 0x40;
        }
        PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
        ((Hashtable)propertiesOrdered).put("type", "item");
        ((Hashtable)propertiesOrdered).put("items", resourceLocation.toString());
        ((Hashtable)propertiesOrdered).put("damage", stringBuffer.toString());
        ((Hashtable)propertiesOrdered).put("damageMask", "" + n);
        if (string2.equals(TYPE_POTION_SPLASH)) {
            ((Hashtable)propertiesOrdered).put(KEY_TEXTURE_SPLASH, string);
        } else {
            ((Hashtable)propertiesOrdered).put(KEY_TEXTURE_DRINKABLE, string);
        }
        return propertiesOrdered;
    }

    private static Map getMapPotionIds() {
        if (mapPotionIds == null) {
            mapPotionIds = new LinkedHashMap();
            mapPotionIds.put("water", CustomItems.getPotionId(0, 0));
            mapPotionIds.put("awkward", CustomItems.getPotionId(0, 1));
            mapPotionIds.put("thick", CustomItems.getPotionId(0, 2));
            mapPotionIds.put("potent", CustomItems.getPotionId(0, 3));
            mapPotionIds.put("regeneration", CustomItems.getPotionIds(1));
            mapPotionIds.put("movespeed", CustomItems.getPotionIds(2));
            mapPotionIds.put("fireresistance", CustomItems.getPotionIds(3));
            mapPotionIds.put("poison", CustomItems.getPotionIds(4));
            mapPotionIds.put("heal", CustomItems.getPotionIds(5));
            mapPotionIds.put("nightvision", CustomItems.getPotionIds(6));
            mapPotionIds.put("clear", CustomItems.getPotionId(7, 0));
            mapPotionIds.put("bungling", CustomItems.getPotionId(7, 1));
            mapPotionIds.put("charming", CustomItems.getPotionId(7, 2));
            mapPotionIds.put("rank", CustomItems.getPotionId(7, 3));
            mapPotionIds.put("weakness", CustomItems.getPotionIds(8));
            mapPotionIds.put("damageboost", CustomItems.getPotionIds(9));
            mapPotionIds.put("moveslowdown", CustomItems.getPotionIds(10));
            mapPotionIds.put("leaping", CustomItems.getPotionIds(11));
            mapPotionIds.put("harm", CustomItems.getPotionIds(12));
            mapPotionIds.put("waterbreathing", CustomItems.getPotionIds(13));
            mapPotionIds.put("invisibility", CustomItems.getPotionIds(14));
            mapPotionIds.put("thin", CustomItems.getPotionId(15, 0));
            mapPotionIds.put("debonair", CustomItems.getPotionId(15, 1));
            mapPotionIds.put("sparkling", CustomItems.getPotionId(15, 2));
            mapPotionIds.put("stinky", CustomItems.getPotionId(15, 3));
            mapPotionIds.put("mundane", CustomItems.getPotionId(0, 4));
            mapPotionIds.put("speed", mapPotionIds.get("movespeed"));
            mapPotionIds.put("fire_resistance", mapPotionIds.get("fireresistance"));
            mapPotionIds.put("instant_health", mapPotionIds.get("heal"));
            mapPotionIds.put("night_vision", mapPotionIds.get("nightvision"));
            mapPotionIds.put("strength", mapPotionIds.get("damageboost"));
            mapPotionIds.put("slowness", mapPotionIds.get("moveslowdown"));
            mapPotionIds.put("instant_damage", mapPotionIds.get("harm"));
            mapPotionIds.put("water_breathing", mapPotionIds.get("waterbreathing"));
        }
        return mapPotionIds;
    }

    private static int[] getPotionIds(int n) {
        return new int[]{n, n + 16, n + 32, n + 48};
    }

    private static int[] getPotionId(int n, int n2) {
        return new int[]{n + n2 * 16};
    }

    private static int getPotionNameDamage(String string) {
        String string2 = "effect." + string;
        for (ResourceLocation resourceLocation : Registry.EFFECTS.keySet()) {
            Effect effect;
            String string3;
            if (!Registry.EFFECTS.containsKey(resourceLocation) || !string2.equals(string3 = (effect = Registry.EFFECTS.getOrDefault(resourceLocation)).getName())) continue;
            return Effect.getId(effect);
        }
        return 1;
    }

    private static List<List<CustomItemProperties>> makePropertyList(CustomItemProperties[][] customItemPropertiesArray) {
        ArrayList<List<CustomItemProperties>> arrayList = new ArrayList<List<CustomItemProperties>>();
        if (customItemPropertiesArray != null) {
            for (int i = 0; i < customItemPropertiesArray.length; ++i) {
                CustomItemProperties[] customItemPropertiesArray2 = customItemPropertiesArray[i];
                ArrayList<CustomItemProperties> arrayList2 = null;
                if (customItemPropertiesArray2 != null) {
                    arrayList2 = new ArrayList<CustomItemProperties>(Arrays.asList(customItemPropertiesArray2));
                }
                arrayList.add(arrayList2);
            }
        }
        return arrayList;
    }

    private static CustomItemProperties[][] propertyListToArray(List list) {
        CustomItemProperties[][] customItemPropertiesArray = new CustomItemProperties[list.size()][];
        for (int i = 0; i < list.size(); ++i) {
            List list2 = (List)list.get(i);
            if (list2 == null) continue;
            CustomItemProperties[] customItemPropertiesArray2 = list2.toArray(new CustomItemProperties[list2.size()]);
            Arrays.sort(customItemPropertiesArray2, new CustomItemsComparator());
            customItemPropertiesArray[i] = customItemPropertiesArray2;
        }
        return customItemPropertiesArray;
    }

    private static void addToItemList(CustomItemProperties customItemProperties, List<List<CustomItemProperties>> list) {
        if (customItemProperties.items != null) {
            for (int i = 0; i < customItemProperties.items.length; ++i) {
                int n = customItemProperties.items[i];
                if (n <= 0) {
                    Config.warn("Invalid item ID: " + n);
                    continue;
                }
                CustomItems.addToList(customItemProperties, list, n);
            }
        }
    }

    private static void addToEnchantmentList(CustomItemProperties customItemProperties, List<List<CustomItemProperties>> list) {
        if (customItemProperties.type == 2 && customItemProperties.enchantmentIds != null) {
            int n = CustomItems.getMaxEnchantmentId() + 1;
            for (int i = 0; i < n; ++i) {
                if (!Config.equalsOne(i, customItemProperties.enchantmentIds)) continue;
                CustomItems.addToList(customItemProperties, list, i);
            }
        }
    }

    private static int getMaxEnchantmentId() {
        int n = 0;
        Enchantment enchantment;
        while ((enchantment = (Enchantment)Registry.ENCHANTMENT.getByValue(n)) != null) {
            ++n;
        }
        return n;
    }

    private static void addToList(CustomItemProperties customItemProperties, List<List<CustomItemProperties>> list, int n) {
        while (n >= list.size()) {
            list.add(null);
        }
        List<CustomItemProperties> list2 = list.get(n);
        if (list2 == null) {
            list2 = new ArrayList<CustomItemProperties>();
            list.set(n, list2);
        }
        list2.add(customItemProperties);
    }

    public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel iBakedModel, ResourceLocation resourceLocation, boolean bl) {
        if (!bl && iBakedModel.isGui3d()) {
            return iBakedModel;
        }
        if (itemProperties == null) {
            return iBakedModel;
        }
        CustomItemProperties customItemProperties = CustomItems.getCustomItemProperties(itemStack, 1);
        if (customItemProperties == null) {
            return iBakedModel;
        }
        IBakedModel iBakedModel2 = customItemProperties.getBakedModel(resourceLocation, bl);
        return iBakedModel2 != null ? iBakedModel2 : iBakedModel;
    }

    public static ResourceLocation getCustomArmorTexture(ItemStack itemStack, EquipmentSlotType equipmentSlotType, String string, ResourceLocation resourceLocation) {
        if (itemProperties == null) {
            return resourceLocation;
        }
        ResourceLocation resourceLocation2 = CustomItems.getCustomArmorLocation(itemStack, equipmentSlotType, string);
        return resourceLocation2 == null ? resourceLocation : resourceLocation2;
    }

    private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, EquipmentSlotType equipmentSlotType, String string) {
        String string2;
        ResourceLocation resourceLocation;
        CustomItemProperties customItemProperties = CustomItems.getCustomItemProperties(itemStack, 3);
        if (customItemProperties == null) {
            return null;
        }
        if (customItemProperties.mapTextureLocations == null) {
            return customItemProperties.textureLocation;
        }
        Item item = itemStack.getItem();
        if (!(item instanceof ArmorItem)) {
            return null;
        }
        ArmorItem armorItem = (ArmorItem)item;
        String string3 = armorItem.getArmorMaterial().getName();
        int n = equipmentSlotType == EquipmentSlotType.LEGS ? 2 : 1;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("texture.");
        stringBuffer.append(string3);
        stringBuffer.append("_layer_");
        stringBuffer.append(n);
        if (string != null) {
            stringBuffer.append("_");
            stringBuffer.append(string);
        }
        return (resourceLocation = (ResourceLocation)customItemProperties.mapTextureLocations.get(string2 = stringBuffer.toString())) == null ? customItemProperties.textureLocation : resourceLocation;
    }

    public static ResourceLocation getCustomElytraTexture(ItemStack itemStack, ResourceLocation resourceLocation) {
        if (itemProperties == null) {
            return resourceLocation;
        }
        CustomItemProperties customItemProperties = CustomItems.getCustomItemProperties(itemStack, 4);
        if (customItemProperties == null) {
            return resourceLocation;
        }
        return customItemProperties.textureLocation == null ? resourceLocation : customItemProperties.textureLocation;
    }

    private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int n) {
        CustomItemProperties[] customItemPropertiesArray;
        CustomItemProperties[][] customItemPropertiesArray2 = itemProperties;
        if (customItemPropertiesArray2 == null) {
            return null;
        }
        if (itemStack == null) {
            return null;
        }
        Item item = itemStack.getItem();
        int n2 = Item.getIdFromItem(item);
        if (n2 >= 0 && n2 < customItemPropertiesArray2.length && (customItemPropertiesArray = customItemPropertiesArray2[n2]) != null) {
            for (int i = 0; i < customItemPropertiesArray.length; ++i) {
                CustomItemProperties customItemProperties = customItemPropertiesArray[i];
                if (customItemProperties.type != n || !CustomItems.matchesProperties(customItemProperties, itemStack, null)) continue;
                return customItemProperties;
            }
        }
        return null;
    }

    private static boolean matchesProperties(CustomItemProperties customItemProperties, ItemStack itemStack, int[][] nArray) {
        int n;
        int n2;
        int n3;
        Item item = itemStack.getItem();
        if (customItemProperties.damage != null) {
            int n4 = CustomItems.getItemStackDamage(itemStack);
            if (n4 < 0) {
                return true;
            }
            if (customItemProperties.damageMask != 0) {
                n4 &= customItemProperties.damageMask;
            }
            if (customItemProperties.damagePercent) {
                n3 = item.getMaxDamage();
                n4 = (int)((double)(n4 * 100) / (double)n3);
            }
            if (!customItemProperties.damage.isInRange(n4)) {
                return true;
            }
        }
        if (customItemProperties.stackSize != null && !customItemProperties.stackSize.isInRange(itemStack.getCount())) {
            return true;
        }
        int[][] nArray2 = nArray;
        if (customItemProperties.enchantmentIds != null) {
            if (nArray == null) {
                nArray2 = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            n3 = 0;
            for (n2 = 0; n2 < nArray2.length; ++n2) {
                n = nArray2[n2][0];
                if (!Config.equalsOne(n, customItemProperties.enchantmentIds)) continue;
                n3 = 1;
                break;
            }
            if (n3 == 0) {
                return true;
            }
        }
        if (customItemProperties.enchantmentLevels != null) {
            if (nArray2 == null) {
                nArray2 = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            n3 = 0;
            for (n2 = 0; n2 < nArray2.length; ++n2) {
                n = nArray2[n2][1];
                if (!customItemProperties.enchantmentLevels.isInRange(n)) continue;
                n3 = 1;
                break;
            }
            if (n3 == 0) {
                return true;
            }
        }
        if (customItemProperties.nbtTagValues != null) {
            CompoundNBT compoundNBT = itemStack.getTag();
            for (n2 = 0; n2 < customItemProperties.nbtTagValues.length; ++n2) {
                NbtTagValue nbtTagValue = customItemProperties.nbtTagValues[n2];
                if (nbtTagValue.matches(compoundNBT)) continue;
                return true;
            }
        }
        if (customItemProperties.hand != 0) {
            if (customItemProperties.hand == 1 && renderOffHand) {
                return true;
            }
            if (customItemProperties.hand == 2 && !renderOffHand) {
                return true;
            }
        }
        return false;
    }

    private static int getItemStackDamage(ItemStack itemStack) {
        Item item = itemStack.getItem();
        return item instanceof PotionItem ? CustomItems.getPotionDamage(itemStack) : itemStack.getDamage();
    }

    private static int getPotionDamage(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getTag();
        if (compoundNBT == null) {
            return 1;
        }
        String string = compoundNBT.getString("Potion");
        if (string != null && !string.equals("")) {
            Integer n = mapPotionDamages.get(string);
            if (n == null) {
                return 1;
            }
            int n2 = n;
            if (itemStack.getItem() == Items.SPLASH_POTION) {
                n2 |= 0x4000;
            }
            return n2;
        }
        return 1;
    }

    private static Map<String, Integer> makeMapPotionDamages() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        CustomItems.addPotion("water", 0, false, hashMap);
        CustomItems.addPotion("awkward", 16, false, hashMap);
        CustomItems.addPotion("thick", 32, false, hashMap);
        CustomItems.addPotion("mundane", 64, false, hashMap);
        CustomItems.addPotion("regeneration", 1, true, hashMap);
        CustomItems.addPotion("swiftness", 2, true, hashMap);
        CustomItems.addPotion("fire_resistance", 3, true, hashMap);
        CustomItems.addPotion("poison", 4, true, hashMap);
        CustomItems.addPotion("healing", 5, true, hashMap);
        CustomItems.addPotion("night_vision", 6, true, hashMap);
        CustomItems.addPotion("weakness", 8, true, hashMap);
        CustomItems.addPotion("strength", 9, true, hashMap);
        CustomItems.addPotion("slowness", 10, true, hashMap);
        CustomItems.addPotion("leaping", 11, true, hashMap);
        CustomItems.addPotion("harming", 12, true, hashMap);
        CustomItems.addPotion("water_breathing", 13, true, hashMap);
        CustomItems.addPotion("invisibility", 14, true, hashMap);
        return hashMap;
    }

    private static void addPotion(String string, int n, boolean bl, Map<String, Integer> map) {
        if (bl) {
            n |= 0x2000;
        }
        map.put("minecraft:" + string, n);
        if (bl) {
            int n2 = n | 0x20;
            map.put("minecraft:strong_" + string, n2);
            int n3 = n | 0x40;
            map.put("minecraft:long_" + string, n3);
        }
    }

    private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
        ListNBT listNBT;
        Object object;
        Item item = itemStack.getItem();
        if (item == Items.ENCHANTED_BOOK) {
            object = (EnchantedBookItem)Items.ENCHANTED_BOOK;
            listNBT = EnchantedBookItem.getEnchantments(itemStack);
        } else {
            listNBT = itemStack.getEnchantmentTagList();
        }
        object = listNBT;
        if (object != null && ((ListNBT)object).size() > 0) {
            int[][] nArray = new int[((ListNBT)object).size()][2];
            for (int i = 0; i < ((ListNBT)object).size(); ++i) {
                int n;
                CompoundNBT compoundNBT = ((ListNBT)object).getCompound(i);
                String string = compoundNBT.getString("id");
                int n2 = compoundNBT.getInt("lvl");
                Enchantment enchantment = EnchantmentUtils.getEnchantment(string);
                if (enchantment == null) continue;
                nArray[i][0] = n = Registry.ENCHANTMENT.getId(enchantment);
                nArray[i][1] = n2;
            }
            return nArray;
        }
        return EMPTY_INT2_ARRAY;
    }

    public static boolean renderCustomEffect(ItemRenderer itemRenderer, ItemStack itemStack, IBakedModel iBakedModel) {
        CustomItemProperties[][] customItemPropertiesArray = enchantmentProperties;
        if (customItemPropertiesArray == null) {
            return true;
        }
        if (itemStack == null) {
            return true;
        }
        int[][] nArray = CustomItems.getEnchantmentIdLevels(itemStack);
        if (nArray.length <= 0) {
            return true;
        }
        HashSet<Integer> hashSet = null;
        boolean bl = false;
        TextureManager textureManager = Config.getTextureManager();
        for (int i = 0; i < nArray.length; ++i) {
            CustomItemProperties[] customItemPropertiesArray2;
            int n = nArray[i][0];
            if (n < 0 || n >= customItemPropertiesArray.length || (customItemPropertiesArray2 = customItemPropertiesArray[n]) == null) continue;
            for (int j = 0; j < customItemPropertiesArray2.length; ++j) {
                CustomItemProperties customItemProperties = customItemPropertiesArray2[j];
                if (hashSet == null) {
                    hashSet = new HashSet<Integer>();
                }
                if (!hashSet.add(n) || !CustomItems.matchesProperties(customItemProperties, itemStack, nArray) || customItemProperties.textureLocation == null) continue;
                textureManager.bindTexture(customItemProperties.textureLocation);
                float f = customItemProperties.getTextureWidth(textureManager);
                if (!bl) {
                    bl = true;
                    GlStateManager.depthMask(false);
                    GlStateManager.depthFunc(514);
                    GlStateManager.disableLighting();
                    GlStateManager.matrixMode(5890);
                }
                Blender.setupBlend(customItemProperties.blend, 1.0f);
                GlStateManager.pushMatrix();
                GlStateManager.scalef(f, f, f);
                float f2 = customItemProperties.speed * (float)(Util.milliTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translatef(f2, 0.0f, 0.0f);
                GlStateManager.rotatef(customItemProperties.rotation, 0.0f, 0.0f, 1.0f);
                GlStateManager.popMatrix();
            }
        }
        if (bl) {
            GlStateManager.enableAlphaTest();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        }
        return bl;
    }

    public static boolean renderCustomArmorEffect(LivingEntity livingEntity, ItemStack itemStack, EntityModel entityModel, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        CustomItemProperties[][] customItemPropertiesArray = enchantmentProperties;
        if (customItemPropertiesArray == null) {
            return true;
        }
        if (Config.isShaders() && Shaders.isShadowPass) {
            return true;
        }
        if (itemStack == null) {
            return true;
        }
        int[][] nArray = CustomItems.getEnchantmentIdLevels(itemStack);
        if (nArray.length <= 0) {
            return true;
        }
        HashSet<Integer> hashSet = null;
        boolean bl = false;
        TextureManager textureManager = Config.getTextureManager();
        for (int i = 0; i < nArray.length; ++i) {
            CustomItemProperties[] customItemPropertiesArray2;
            int n = nArray[i][0];
            if (n < 0 || n >= customItemPropertiesArray.length || (customItemPropertiesArray2 = customItemPropertiesArray[n]) == null) continue;
            for (int j = 0; j < customItemPropertiesArray2.length; ++j) {
                CustomItemProperties customItemProperties = customItemPropertiesArray2[j];
                if (hashSet == null) {
                    hashSet = new HashSet<Integer>();
                }
                if (!hashSet.add(n) || !CustomItems.matchesProperties(customItemProperties, itemStack, nArray) || customItemProperties.textureLocation == null) continue;
                textureManager.bindTexture(customItemProperties.textureLocation);
                float f8 = customItemProperties.getTextureWidth(textureManager);
                if (!bl) {
                    bl = true;
                    if (Config.isShaders()) {
                        ShadersRender.renderEnchantedGlintBegin();
                    }
                    GlStateManager.enableBlend();
                    GlStateManager.depthFunc(514);
                    GlStateManager.depthMask(false);
                }
                Blender.setupBlend(customItemProperties.blend, 1.0f);
                GlStateManager.disableLighting();
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.rotatef(customItemProperties.rotation, 0.0f, 0.0f, 1.0f);
                float f9 = f8 / 8.0f;
                GlStateManager.scalef(f9, f9 / 2.0f, f9);
                float f10 = customItemProperties.speed * (float)(Util.milliTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translatef(0.0f, f10, 0.0f);
                GlStateManager.matrixMode(5888);
            }
        }
        if (bl) {
            GlStateManager.enableAlphaTest();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (Config.isShaders()) {
                ShadersRender.renderEnchantedGlintEnd();
            }
        }
        return bl;
    }

    public static boolean isUseGlint() {
        return useGlint;
    }

    public static void setRenderOffHand(boolean bl) {
        renderOffHand = bl;
    }
}

