/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import optifine.Blender;
import optifine.Config;
import optifine.CustomItemProperties;
import optifine.CustomItemsComparator;
import optifine.NbtTagValue;
import optifine.RangeListInt;
import optifine.ResUtils;
import optifine.StrUtils;
import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

public class CustomItems {
    private static CustomItemProperties[][] itemProperties = null;
    private static CustomItemProperties[][] enchantmentProperties = null;
    private static Map mapPotionIds = null;
    private static ItemModelGenerator itemModelGenerator = new ItemModelGenerator();
    private static boolean useGlint = true;
    public static final int MASK_POTION_SPLASH = 16384;
    public static final int MASK_POTION_NAME = 63;
    public static final String KEY_TEXTURE_OVERLAY = "texture.potion_overlay";
    public static final String KEY_TEXTURE_SPLASH = "texture.potion_bottle_splash";
    public static final String KEY_TEXTURE_DRINKABLE = "texture.potion_bottle_drinkable";
    public static final String DEFAULT_TEXTURE_OVERLAY = "items/potion_overlay";
    public static final String DEFAULT_TEXTURE_SPLASH = "items/potion_bottle_splash";
    public static final String DEFAULT_TEXTURE_DRINKABLE = "items/potion_bottle_drinkable";
    private static final int[] EMPTY_INT_ARRAY = new int[0];
    private static final int[][] EMPTY_INT2_ARRAY = new int[0][];

    public static void updateIcons(TextureMap textureMap) {
        itemProperties = null;
        enchantmentProperties = null;
        useGlint = true;
        if (Config.isCustomItems()) {
            CustomItems.readCitProperties("mcpatcher/cit.properties");
            IResourcePack[] rps = Config.getResourcePacks();
            for (int i2 = rps.length - 1; i2 >= 0; --i2) {
                IResourcePack rp2 = rps[i2];
                CustomItems.updateIcons(textureMap, rp2);
            }
            CustomItems.updateIcons(textureMap, Config.getDefaultResourcePack());
            if (itemProperties.length <= 0) {
                itemProperties = null;
            }
            if (enchantmentProperties.length <= 0) {
                enchantmentProperties = null;
            }
        }
    }

    private static void readCitProperties(String fileName) {
        try {
            ResourceLocation e2 = new ResourceLocation(fileName);
            InputStream in2 = Config.getResourceStream(e2);
            if (in2 == null) {
                return;
            }
            Config.dbg("CustomItems: Loading " + fileName);
            Properties props = new Properties();
            props.load(in2);
            in2.close();
            useGlint = Config.parseBoolean(props.getProperty("useGlint"), true);
        }
        catch (FileNotFoundException var4) {
            return;
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    private static void updateIcons(TextureMap textureMap, IResourcePack rp2) {
        CustomItemProperties[] var18;
        int var17;
        Object[] names = ResUtils.collectFiles(rp2, "mcpatcher/cit/", ".properties", null);
        Map mapAutoProperties = CustomItems.makeAutoImageProperties(rp2);
        if (mapAutoProperties.size() > 0) {
            Set itemList = mapAutoProperties.keySet();
            Object[] enchantmentList = itemList.toArray(new String[itemList.size()]);
            names = (String[])Config.addObjectsToArray(names, enchantmentList);
        }
        Arrays.sort(names);
        List var14 = CustomItems.makePropertyList(itemProperties);
        List var15 = CustomItems.makePropertyList(enchantmentProperties);
        for (int comp = 0; comp < names.length; ++comp) {
            Object i2 = names[comp];
            Config.dbg("CustomItems: " + (String)i2);
            try {
                CustomItemProperties cips = null;
                if (mapAutoProperties.containsKey(i2)) {
                    cips = (CustomItemProperties)mapAutoProperties.get(i2);
                }
                if (cips == null) {
                    ResourceLocation locFile = new ResourceLocation((String)i2);
                    InputStream in2 = rp2.getInputStream(locFile);
                    if (in2 == null) {
                        Config.warn("CustomItems file not found: " + (String)i2);
                        continue;
                    }
                    Properties props = new Properties();
                    props.load(in2);
                    cips = new CustomItemProperties(props, (String)i2);
                }
                if (!cips.isValid((String)i2)) continue;
                cips.updateIcons(textureMap);
                CustomItems.addToItemList(cips, var14);
                CustomItems.addToEnchantmentList(cips, var15);
                continue;
            }
            catch (FileNotFoundException var12) {
                Config.warn("CustomItems file not found: " + (String)i2);
                continue;
            }
            catch (Exception var13) {
                var13.printStackTrace();
            }
        }
        itemProperties = CustomItems.propertyListToArray(var14);
        enchantmentProperties = CustomItems.propertyListToArray(var15);
        Comparator var16 = CustomItems.getPropertiesComparator();
        for (var17 = 0; var17 < itemProperties.length; ++var17) {
            var18 = itemProperties[var17];
            if (var18 == null) continue;
            Arrays.sort(var18, var16);
        }
        for (var17 = 0; var17 < enchantmentProperties.length; ++var17) {
            var18 = enchantmentProperties[var17];
            if (var18 == null) continue;
            Arrays.sort(var18, var16);
        }
    }

    private static Comparator getPropertiesComparator() {
        Comparator comp = new Comparator(){

            public int compare(Object o1, Object o2) {
                CustomItemProperties cip1 = (CustomItemProperties)o1;
                CustomItemProperties cip2 = (CustomItemProperties)o2;
                return cip1.layer != cip2.layer ? cip1.layer - cip2.layer : (cip1.weight != cip2.weight ? cip2.weight - cip1.weight : (!cip1.basePath.equals(cip2.basePath) ? cip1.basePath.compareTo(cip2.basePath) : cip1.name.compareTo(cip2.name)));
            }
        };
        return comp;
    }

    public static void updateModels() {
        if (itemProperties != null) {
            for (int id2 = 0; id2 < itemProperties.length; ++id2) {
                CustomItemProperties[] cips = itemProperties[id2];
                if (cips == null) continue;
                for (int c2 = 0; c2 < cips.length; ++c2) {
                    CustomItemProperties cip = cips[c2];
                    if (cip == null || cip.type != 1) continue;
                    TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
                    cip.updateModel(textureMap, itemModelGenerator);
                }
            }
        }
    }

    private static Map makeAutoImageProperties(IResourcePack rp2) {
        HashMap map = new HashMap();
        map.putAll(CustomItems.makePotionImageProperties(rp2, false));
        map.putAll(CustomItems.makePotionImageProperties(rp2, true));
        return map;
    }

    private static Map makePotionImageProperties(IResourcePack rp2, boolean splash) {
        HashMap<String, CustomItemProperties> map = new HashMap<String, CustomItemProperties>();
        String type = splash ? "splash/" : "normal/";
        String[] prefixes = new String[]{"mcpatcher/cit/potion/" + type, "mcpatcher/cit/Potion/" + type};
        String[] suffixes = new String[]{".png"};
        String[] names = ResUtils.collectFiles(rp2, prefixes, suffixes);
        for (int i2 = 0; i2 < names.length; ++i2) {
            String path = names[i2];
            String name = StrUtils.removePrefixSuffix(path, prefixes, suffixes);
            Properties props = CustomItems.makePotionProperties(name, splash, path);
            if (props == null) continue;
            String pathProp = String.valueOf(StrUtils.removeSuffix(path, suffixes)) + ".properties";
            CustomItemProperties cip = new CustomItemProperties(props, pathProp);
            map.put(pathProp, cip);
        }
        return map;
    }

    private static Properties makePotionProperties(String name, boolean splash, String path) {
        if (StrUtils.endsWith(name, new String[]{"_n", "_s"})) {
            return null;
        }
        if (name.equals("empty") && !splash) {
            int potionItemId = Item.getIdFromItem(Items.glass_bottle);
            Properties var8 = new Properties();
            var8.put("type", "item");
            var8.put("items", "" + potionItemId);
            return var8;
        }
        int potionItemId = Item.getIdFromItem(Items.potionitem);
        int[] damages = (int[])CustomItems.getMapPotionIds().get(name);
        if (damages == null) {
            Config.warn("Potion not found for image: " + path);
            return null;
        }
        StringBuffer bufDamage = new StringBuffer();
        for (int damageMask = 0; damageMask < damages.length; ++damageMask) {
            int props = damages[damageMask];
            if (splash) {
                props |= 16384;
            }
            if (damageMask > 0) {
                bufDamage.append(" ");
            }
            bufDamage.append(props);
        }
        int var9 = 16447;
        Properties var10 = new Properties();
        var10.put("type", "item");
        var10.put("items", "" + potionItemId);
        var10.put("damage", bufDamage.toString());
        var10.put("damageMask", "" + var9);
        if (splash) {
            var10.put(KEY_TEXTURE_SPLASH, name);
        } else {
            var10.put(KEY_TEXTURE_DRINKABLE, name);
        }
        return var10;
    }

    private static Map getMapPotionIds() {
        if (mapPotionIds == null) {
            mapPotionIds = new LinkedHashMap();
            mapPotionIds.put("water", new int[1]);
            mapPotionIds.put("awkward", new int[]{16});
            mapPotionIds.put("thick", new int[]{32});
            mapPotionIds.put("potent", new int[]{48});
            mapPotionIds.put("regeneration", CustomItems.getPotionIds(1));
            mapPotionIds.put("moveSpeed", CustomItems.getPotionIds(2));
            mapPotionIds.put("fireResistance", CustomItems.getPotionIds(3));
            mapPotionIds.put("poison", CustomItems.getPotionIds(4));
            mapPotionIds.put("heal", CustomItems.getPotionIds(5));
            mapPotionIds.put("nightVision", CustomItems.getPotionIds(6));
            mapPotionIds.put("clear", CustomItems.getPotionIds(7));
            mapPotionIds.put("bungling", CustomItems.getPotionIds(23));
            mapPotionIds.put("charming", CustomItems.getPotionIds(39));
            mapPotionIds.put("rank", CustomItems.getPotionIds(55));
            mapPotionIds.put("weakness", CustomItems.getPotionIds(8));
            mapPotionIds.put("damageBoost", CustomItems.getPotionIds(9));
            mapPotionIds.put("moveSlowdown", CustomItems.getPotionIds(10));
            mapPotionIds.put("diffuse", CustomItems.getPotionIds(11));
            mapPotionIds.put("smooth", CustomItems.getPotionIds(27));
            mapPotionIds.put("refined", CustomItems.getPotionIds(43));
            mapPotionIds.put("acrid", CustomItems.getPotionIds(59));
            mapPotionIds.put("harm", CustomItems.getPotionIds(12));
            mapPotionIds.put("waterBreathing", CustomItems.getPotionIds(13));
            mapPotionIds.put("invisibility", CustomItems.getPotionIds(14));
            mapPotionIds.put("thin", CustomItems.getPotionIds(15));
            mapPotionIds.put("debonair", CustomItems.getPotionIds(31));
            mapPotionIds.put("sparkling", CustomItems.getPotionIds(47));
            mapPotionIds.put("stinky", CustomItems.getPotionIds(63));
        }
        return mapPotionIds;
    }

    private static int[] getPotionIds(int baseId) {
        return new int[]{baseId, baseId + 16, baseId + 32, baseId + 48};
    }

    private static int getPotionNameDamage(String name) {
        String fullName = "potion." + name;
        Potion[] effectPotions = Potion.potionTypes;
        for (int i2 = 0; i2 < effectPotions.length; ++i2) {
            String potionName;
            Potion potion = effectPotions[i2];
            if (potion == null || !fullName.equals(potionName = potion.getName())) continue;
            return potion.getId();
        }
        return -1;
    }

    private static List makePropertyList(CustomItemProperties[][] propsArr) {
        ArrayList<ArrayList<CustomItemProperties>> list = new ArrayList<ArrayList<CustomItemProperties>>();
        if (propsArr != null) {
            for (int i2 = 0; i2 < propsArr.length; ++i2) {
                CustomItemProperties[] props = propsArr[i2];
                ArrayList<CustomItemProperties> propList = null;
                if (props != null) {
                    propList = new ArrayList<CustomItemProperties>(Arrays.asList(props));
                }
                list.add(propList);
            }
        }
        return list;
    }

    private static CustomItemProperties[][] propertyListToArray(List list) {
        CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
        for (int i2 = 0; i2 < list.size(); ++i2) {
            List subList = (List)list.get(i2);
            if (subList == null) continue;
            CustomItemProperties[] subArr = subList.toArray(new CustomItemProperties[subList.size()]);
            Arrays.sort(subArr, new CustomItemsComparator());
            propArr[i2] = subArr;
        }
        return propArr;
    }

    private static void addToItemList(CustomItemProperties cp2, List itemList) {
        if (cp2.items != null) {
            for (int i2 = 0; i2 < cp2.items.length; ++i2) {
                int itemId = cp2.items[i2];
                if (itemId <= 0) {
                    Config.warn("Invalid item ID: " + itemId);
                    continue;
                }
                CustomItems.addToList(cp2, itemList, itemId);
            }
        }
    }

    private static void addToEnchantmentList(CustomItemProperties cp2, List enchantmentList) {
        if (cp2.type == 2 && cp2.enchantmentIds != null) {
            for (int i2 = 0; i2 < 256; ++i2) {
                if (!cp2.enchantmentIds.isInRange(i2)) continue;
                CustomItems.addToList(cp2, enchantmentList, i2);
            }
        }
    }

    private static void addToList(CustomItemProperties cp2, List list, int id2) {
        while (id2 >= list.size()) {
            list.add(null);
        }
        ArrayList<CustomItemProperties> subList = (ArrayList<CustomItemProperties>)list.get(id2);
        if (subList == null) {
            subList = new ArrayList<CustomItemProperties>();
            list.set(id2, subList);
        }
        subList.add(cp2);
    }

    public static IBakedModel getCustomItemModel(ItemStack itemStack, IBakedModel model, ModelResourceLocation modelLocation) {
        if (model.isAmbientOcclusionEnabled()) {
            return model;
        }
        if (itemProperties == null) {
            return model;
        }
        CustomItemProperties props = CustomItems.getCustomItemProperties(itemStack, 1);
        return props == null ? model : props.getModel(modelLocation);
    }

    public static boolean bindCustomArmorTexture(ItemStack itemStack, int layer, String overlay) {
        if (itemProperties == null) {
            return false;
        }
        ResourceLocation loc = CustomItems.getCustomArmorLocation(itemStack, layer, overlay);
        if (loc == null) {
            return false;
        }
        Config.getTextureManager().bindTexture(loc);
        return true;
    }

    private static ResourceLocation getCustomArmorLocation(ItemStack itemStack, int layer, String overlay) {
        CustomItemProperties props = CustomItems.getCustomItemProperties(itemStack, 3);
        if (props == null) {
            return null;
        }
        if (props.mapTextureLocations == null) {
            return null;
        }
        Item item = itemStack.getItem();
        if (!(item instanceof ItemArmor)) {
            return null;
        }
        ItemArmor itemArmor = (ItemArmor)item;
        String material = itemArmor.getArmorMaterial().func_179242_c();
        StringBuffer sb2 = new StringBuffer();
        sb2.append("texture.");
        sb2.append(material);
        sb2.append("_layer_");
        sb2.append(layer);
        if (overlay != null) {
            sb2.append("_");
            sb2.append(overlay);
        }
        String key = sb2.toString();
        ResourceLocation loc = (ResourceLocation)props.mapTextureLocations.get(key);
        return loc;
    }

    private static CustomItemProperties getCustomItemProperties(ItemStack itemStack, int type) {
        CustomItemProperties[] cips;
        if (itemProperties == null) {
            return null;
        }
        if (itemStack == null) {
            return null;
        }
        Item item = itemStack.getItem();
        int itemId = Item.getIdFromItem(item);
        if (itemId >= 0 && itemId < itemProperties.length && (cips = itemProperties[itemId]) != null) {
            for (int i2 = 0; i2 < cips.length; ++i2) {
                CustomItemProperties cip = cips[i2];
                if (cip.type != type || !CustomItems.matchesProperties(cip, itemStack, null)) continue;
                return cip;
            }
        }
        return null;
    }

    private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
        boolean var9;
        int ntv;
        int i2;
        Item item = itemStack.getItem();
        if (cip.damage != null) {
            int idLevels = itemStack.getItemDamage();
            if (cip.damageMask != 0) {
                idLevels &= cip.damageMask;
            }
            if (cip.damagePercent) {
                int nbt = item.getMaxDamage();
                idLevels = (int)((double)(idLevels * 100) / (double)nbt);
            }
            if (!cip.damage.isInRange(idLevels)) {
                return false;
            }
        }
        if (cip.stackSize != null && !cip.stackSize.isInRange(itemStack.stackSize)) {
            return false;
        }
        int[][] var8 = enchantmentIdLevels;
        if (cip.enchantmentIds != null) {
            if (enchantmentIdLevels == null) {
                var8 = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            var9 = false;
            for (i2 = 0; i2 < var8.length; ++i2) {
                ntv = var8[i2][0];
                if (!cip.enchantmentIds.isInRange(ntv)) continue;
                var9 = true;
                break;
            }
            if (!var9) {
                return false;
            }
        }
        if (cip.enchantmentLevels != null) {
            if (var8 == null) {
                var8 = CustomItems.getEnchantmentIdLevels(itemStack);
            }
            var9 = false;
            for (i2 = 0; i2 < var8.length; ++i2) {
                ntv = var8[i2][1];
                if (!cip.enchantmentLevels.isInRange(ntv)) continue;
                var9 = true;
                break;
            }
            if (!var9) {
                return false;
            }
        }
        if (cip.nbtTagValues != null) {
            NBTTagCompound var10 = itemStack.getTagCompound();
            for (i2 = 0; i2 < cip.nbtTagValues.length; ++i2) {
                NbtTagValue var11 = cip.nbtTagValues[i2];
                if (var11.matches(var10)) continue;
                return false;
            }
        }
        return true;
    }

    private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
        NBTTagList nbt;
        Item item = itemStack.getItem();
        NBTTagList nBTTagList = nbt = item == Items.enchanted_book ? Items.enchanted_book.func_92110_g(itemStack) : itemStack.getEnchantmentTagList();
        if (nbt != null && nbt.tagCount() > 0) {
            int[][] arr2 = new int[nbt.tagCount()][2];
            for (int i2 = 0; i2 < nbt.tagCount(); ++i2) {
                NBTTagCompound tag = nbt.getCompoundTagAt(i2);
                short id2 = tag.getShort("id");
                short lvl = tag.getShort("lvl");
                arr2[i2][0] = id2;
                arr2[i2][1] = lvl;
            }
            return arr2;
        }
        return EMPTY_INT2_ARRAY;
    }

    public static boolean renderCustomEffect(RenderItem renderItem, ItemStack itemStack, IBakedModel model) {
        if (enchantmentProperties == null) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        int[][] idLevels = CustomItems.getEnchantmentIdLevels(itemStack);
        if (idLevels.length <= 0) {
            return false;
        }
        HashSet<Integer> layersRendered = null;
        boolean rendered = false;
        TextureManager textureManager = Config.getTextureManager();
        for (int i2 = 0; i2 < idLevels.length; ++i2) {
            CustomItemProperties[] cips;
            int id2 = idLevels[i2][0];
            if (id2 < 0 || id2 >= enchantmentProperties.length || (cips = enchantmentProperties[id2]) == null) continue;
            for (int p2 = 0; p2 < cips.length; ++p2) {
                CustomItemProperties cip = cips[p2];
                if (layersRendered == null) {
                    layersRendered = new HashSet<Integer>();
                }
                if (!layersRendered.add(id2) || !CustomItems.matchesProperties(cip, itemStack, idLevels) || cip.textureLocation == null) continue;
                textureManager.bindTexture(cip.textureLocation);
                float width = cip.getTextureWidth(textureManager);
                if (!rendered) {
                    rendered = true;
                    GlStateManager.depthMask(false);
                    GlStateManager.depthFunc(514);
                    GlStateManager.disableLighting();
                    GlStateManager.matrixMode(5890);
                }
                Blender.setupBlend(cip.blend, 1.0f);
                GlStateManager.pushMatrix();
                GlStateManager.scale(width / 2.0f, width / 2.0f, width / 2.0f);
                float offset = cip.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translate(offset, 0.0f, 0.0f);
                GlStateManager.rotate(cip.rotation, 0.0f, 0.0f, 1.0f);
                renderItem.func_175035_a(model, -1);
                GlStateManager.popMatrix();
            }
        }
        if (rendered) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthFunc(515);
            GlStateManager.depthMask(true);
            textureManager.bindTexture(TextureMap.locationBlocksTexture);
        }
        return rendered;
    }

    public static boolean renderCustomArmorEffect(EntityLivingBase entity, ItemStack itemStack, ModelBase model, float limbSwing, float prevLimbSwing, float partialTicks, float timeLimbSwing, float yaw, float pitch, float scale) {
        if (enchantmentProperties == null) {
            return false;
        }
        if (Config.isShaders() && Shaders.isShadowPass) {
            return false;
        }
        if (itemStack == null) {
            return false;
        }
        int[][] idLevels = CustomItems.getEnchantmentIdLevels(itemStack);
        if (idLevels.length <= 0) {
            return false;
        }
        HashSet<Integer> layersRendered = null;
        boolean rendered = false;
        TextureManager textureManager = Config.getTextureManager();
        for (int i2 = 0; i2 < idLevels.length; ++i2) {
            CustomItemProperties[] cips;
            int id2 = idLevels[i2][0];
            if (id2 < 0 || id2 >= enchantmentProperties.length || (cips = enchantmentProperties[id2]) == null) continue;
            for (int p2 = 0; p2 < cips.length; ++p2) {
                CustomItemProperties cip = cips[p2];
                if (layersRendered == null) {
                    layersRendered = new HashSet<Integer>();
                }
                if (!layersRendered.add(id2) || !CustomItems.matchesProperties(cip, itemStack, idLevels) || cip.textureLocation == null) continue;
                textureManager.bindTexture(cip.textureLocation);
                float width = cip.getTextureWidth(textureManager);
                if (!rendered) {
                    rendered = true;
                    if (Config.isShaders()) {
                        ShadersRender.layerArmorBaseDrawEnchantedGlintBegin();
                    }
                    GlStateManager.enableBlend();
                    GlStateManager.depthFunc(514);
                    GlStateManager.depthMask(false);
                }
                Blender.setupBlend(cip.blend, 1.0f);
                GlStateManager.disableLighting();
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                GlStateManager.rotate(cip.rotation, 0.0f, 0.0f, 1.0f);
                float texScale = width / 8.0f;
                GlStateManager.scale(texScale, texScale / 2.0f, texScale);
                float offset = cip.speed * (float)(Minecraft.getSystemTime() % 3000L) / 3000.0f / 8.0f;
                GlStateManager.translate(0.0f, offset, 0.0f);
                GlStateManager.matrixMode(5888);
                model.render(entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
            }
        }
        if (rendered) {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (Config.isShaders()) {
                ShadersRender.layerArmorBaseDrawEnchantedGlintEnd();
            }
        }
        return rendered;
    }

    public static boolean isUseGlint() {
        return useGlint;
    }

}

