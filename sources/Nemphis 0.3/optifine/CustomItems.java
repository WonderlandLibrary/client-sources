/*
 * Decompiled with CFR 0_118.
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
            int i = rps.length - 1;
            while (i >= 0) {
                IResourcePack rp = rps[i];
                CustomItems.updateIcons(textureMap, rp);
                --i;
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
            ResourceLocation e = new ResourceLocation(fileName);
            InputStream in = Config.getResourceStream(e);
            if (in == null) {
                return;
            }
            Config.dbg("CustomItems: Loading " + fileName);
            Properties props = new Properties();
            props.load(in);
            in.close();
            useGlint = Config.parseBoolean(props.getProperty("useGlint"), true);
        }
        catch (FileNotFoundException var4) {
            return;
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void updateIcons(TextureMap textureMap, IResourcePack rp) {
        names = ResUtils.collectFiles(rp, "mcpatcher/cit/", ".properties", null);
        mapAutoProperties = CustomItems.makeAutoImageProperties(rp);
        if (mapAutoProperties.size() > 0) {
            itemList = mapAutoProperties.keySet();
            enchantmentList = itemList.toArray(new String[itemList.size()]);
            names = (String[])Config.addObjectsToArray(names, enchantmentList);
        }
        Arrays.sort(names);
        var14 = CustomItems.makePropertyList(CustomItems.itemProperties);
        var15 = CustomItems.makePropertyList(CustomItems.enchantmentProperties);
        comp = 0;
        while (comp < names.length) {
            i = names[comp];
            Config.dbg("CustomItems: " + (String)i);
            try {
                cips = null;
                if (mapAutoProperties.containsKey(i) && (cips = (CustomItemProperties)mapAutoProperties.get(i)) != null) ** GOTO lbl25
                locFile = new ResourceLocation((String)i);
                in = rp.getInputStream(locFile);
                if (in == null) {
                    Config.warn("CustomItems file not found: " + (String)i);
                } else {
                    props = new Properties();
                    props.load(in);
                    cips = new CustomItemProperties(props, (String)i);
lbl25: // 2 sources:
                    if (cips.isValid((String)i)) {
                        cips.updateIcons(textureMap);
                        CustomItems.addToItemList(cips, var14);
                        CustomItems.addToEnchantmentList(cips, var15);
                    }
                }
            }
            catch (FileNotFoundException var12) {
                Config.warn("CustomItems file not found: " + (String)i);
            }
            catch (Exception var13) {
                var13.printStackTrace();
            }
            ++comp;
        }
        CustomItems.itemProperties = CustomItems.propertyListToArray(var14);
        CustomItems.enchantmentProperties = CustomItems.propertyListToArray(var15);
        var16 = CustomItems.getPropertiesComparator();
        var17 = 0;
        while (var17 < CustomItems.itemProperties.length) {
            var18 = CustomItems.itemProperties[var17];
            if (var18 != null) {
                Arrays.sort(var18, var16);
            }
            ++var17;
        }
        var17 = 0;
        while (var17 < CustomItems.enchantmentProperties.length) {
            var18 = CustomItems.enchantmentProperties[var17];
            if (var18 != null) {
                Arrays.sort(var18, var16);
            }
            ++var17;
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
            int id = 0;
            while (id < itemProperties.length) {
                CustomItemProperties[] cips = itemProperties[id];
                if (cips != null) {
                    int c = 0;
                    while (c < cips.length) {
                        CustomItemProperties cip = cips[c];
                        if (cip != null && cip.type == 1) {
                            TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
                            cip.updateModel(textureMap, itemModelGenerator);
                        }
                        ++c;
                    }
                }
                ++id;
            }
        }
    }

    private static Map makeAutoImageProperties(IResourcePack rp) {
        HashMap map = new HashMap();
        map.putAll(CustomItems.makePotionImageProperties(rp, false));
        map.putAll(CustomItems.makePotionImageProperties(rp, true));
        return map;
    }

    private static Map makePotionImageProperties(IResourcePack rp, boolean splash) {
        HashMap<String, CustomItemProperties> map = new HashMap<String, CustomItemProperties>();
        String type = splash ? "splash/" : "normal/";
        String[] prefixes = new String[]{"mcpatcher/cit/potion/" + type, "mcpatcher/cit/Potion/" + type};
        String[] suffixes = new String[]{".png"};
        String[] names = ResUtils.collectFiles(rp, prefixes, suffixes);
        int i = 0;
        while (i < names.length) {
            String path = names[i];
            String name = StrUtils.removePrefixSuffix(path, prefixes, suffixes);
            Properties props = CustomItems.makePotionProperties(name, splash, path);
            if (props != null) {
                String pathProp = String.valueOf(StrUtils.removeSuffix(path, suffixes)) + ".properties";
                CustomItemProperties cip = new CustomItemProperties(props, pathProp);
                map.put(pathProp, cip);
            }
            ++i;
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
        int damageMask = 0;
        while (damageMask < damages.length) {
            int props = damages[damageMask];
            if (splash) {
                props |= 16384;
            }
            if (damageMask > 0) {
                bufDamage.append(" ");
            }
            bufDamage.append(props);
            ++damageMask;
        }
        int var9 = 16447;
        Properties var10 = new Properties();
        var10.put("type", "item");
        var10.put("items", "" + potionItemId);
        var10.put("damage", bufDamage.toString());
        var10.put("damageMask", "" + var9);
        if (splash) {
            var10.put("texture.potion_bottle_splash", name);
        } else {
            var10.put("texture.potion_bottle_drinkable", name);
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
        int i = 0;
        while (i < effectPotions.length) {
            String potionName;
            Potion potion = effectPotions[i];
            if (potion != null && fullName.equals(potionName = potion.getName())) {
                return potion.getId();
            }
            ++i;
        }
        return -1;
    }

    private static List makePropertyList(CustomItemProperties[][] propsArr) {
        ArrayList<Object> list = new ArrayList<Object>();
        if (propsArr != null) {
            int i = 0;
            while (i < propsArr.length) {
                CustomItemProperties[] props = propsArr[i];
                ArrayList<CustomItemProperties> propList = null;
                if (props != null) {
                    propList = new ArrayList<CustomItemProperties>(Arrays.asList(props));
                }
                list.add(propList);
                ++i;
            }
        }
        return list;
    }

    private static CustomItemProperties[][] propertyListToArray(List list) {
        CustomItemProperties[][] propArr = new CustomItemProperties[list.size()][];
        int i = 0;
        while (i < list.size()) {
            List subList = (List)list.get(i);
            if (subList != null) {
                CustomItemProperties[] subArr = subList.toArray(new CustomItemProperties[subList.size()]);
                Arrays.sort(subArr, new CustomItemsComparator());
                propArr[i] = subArr;
            }
            ++i;
        }
        return propArr;
    }

    private static void addToItemList(CustomItemProperties cp, List itemList) {
        if (cp.items != null) {
            int i = 0;
            while (i < cp.items.length) {
                int itemId = cp.items[i];
                if (itemId <= 0) {
                    Config.warn("Invalid item ID: " + itemId);
                } else {
                    CustomItems.addToList(cp, itemList, itemId);
                }
                ++i;
            }
        }
    }

    private static void addToEnchantmentList(CustomItemProperties cp, List enchantmentList) {
        if (cp.type == 2 && cp.enchantmentIds != null) {
            int i = 0;
            while (i < 256) {
                if (cp.enchantmentIds.isInRange(i)) {
                    CustomItems.addToList(cp, enchantmentList, i);
                }
                ++i;
            }
        }
    }

    private static void addToList(CustomItemProperties cp, List list, int id) {
        while (id >= list.size()) {
            list.add(null);
        }
        ArrayList<CustomItemProperties> subList = (ArrayList<CustomItemProperties>)list.get(id);
        if (subList == null) {
            subList = new ArrayList<CustomItemProperties>();
            list.set(id, subList);
        }
        subList.add(cp);
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
        StringBuffer sb = new StringBuffer();
        sb.append("texture.");
        sb.append(material);
        sb.append("_layer_");
        sb.append(layer);
        if (overlay != null) {
            sb.append("_");
            sb.append(overlay);
        }
        String key = sb.toString();
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
            int i = 0;
            while (i < cips.length) {
                CustomItemProperties cip = cips[i];
                if (cip.type == type && CustomItems.matchesProperties(cip, itemStack, null)) {
                    return cip;
                }
                ++i;
            }
        }
        return null;
    }

    private static boolean matchesProperties(CustomItemProperties cip, ItemStack itemStack, int[][] enchantmentIdLevels) {
        boolean var9;
        int i;
        int ntv;
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
            i = 0;
            while (i < var8.length) {
                ntv = var8[i][0];
                if (cip.enchantmentIds.isInRange(ntv)) {
                    var9 = true;
                    break;
                }
                ++i;
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
            i = 0;
            while (i < var8.length) {
                ntv = var8[i][1];
                if (cip.enchantmentLevels.isInRange(ntv)) {
                    var9 = true;
                    break;
                }
                ++i;
            }
            if (!var9) {
                return false;
            }
        }
        if (cip.nbtTagValues != null) {
            NBTTagCompound var10 = itemStack.getTagCompound();
            i = 0;
            while (i < cip.nbtTagValues.length) {
                NbtTagValue var11 = cip.nbtTagValues[i];
                if (!var11.matches(var10)) {
                    return false;
                }
                ++i;
            }
        }
        return true;
    }

    private static int[][] getEnchantmentIdLevels(ItemStack itemStack) {
        NBTTagList nbt;
        Item item = itemStack.getItem();
        NBTTagList nBTTagList = nbt = item == Items.enchanted_book ? Items.enchanted_book.func_92110_g(itemStack) : itemStack.getEnchantmentTagList();
        if (nbt != null && nbt.tagCount() > 0) {
            int[][] arr = new int[nbt.tagCount()][2];
            int i = 0;
            while (i < nbt.tagCount()) {
                NBTTagCompound tag = nbt.getCompoundTagAt(i);
                short id = tag.getShort("id");
                short lvl = tag.getShort("lvl");
                arr[i][0] = id;
                arr[i][1] = lvl;
                ++i;
            }
            return arr;
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
        int i = 0;
        while (i < idLevels.length) {
            CustomItemProperties[] cips;
            int id = idLevels[i][0];
            if (id >= 0 && id < enchantmentProperties.length && (cips = enchantmentProperties[id]) != null) {
                int p = 0;
                while (p < cips.length) {
                    CustomItemProperties cip = cips[p];
                    if (layersRendered == null) {
                        layersRendered = new HashSet<Integer>();
                    }
                    if (layersRendered.add(id) && CustomItems.matchesProperties(cip, itemStack, idLevels) && cip.textureLocation != null) {
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
                        float offset = cip.speed * (float)(Minecraft.getSystemTime() % 3000) / 3000.0f / 8.0f;
                        GlStateManager.translate(offset, 0.0f, 0.0f);
                        GlStateManager.rotate(cip.rotation, 0.0f, 0.0f, 1.0f);
                        renderItem.func_175035_a(model, -1);
                        GlStateManager.popMatrix();
                    }
                    ++p;
                }
            }
            ++i;
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
        int i = 0;
        while (i < idLevels.length) {
            CustomItemProperties[] cips;
            int id = idLevels[i][0];
            if (id >= 0 && id < enchantmentProperties.length && (cips = enchantmentProperties[id]) != null) {
                int p = 0;
                while (p < cips.length) {
                    CustomItemProperties cip = cips[p];
                    if (layersRendered == null) {
                        layersRendered = new HashSet<Integer>();
                    }
                    if (layersRendered.add(id) && CustomItems.matchesProperties(cip, itemStack, idLevels) && cip.textureLocation != null) {
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
                        float offset = cip.speed * (float)(Minecraft.getSystemTime() % 3000) / 3000.0f / 8.0f;
                        GlStateManager.translate(0.0f, offset, 0.0f);
                        GlStateManager.matrixMode(5888);
                        model.render(entity, limbSwing, prevLimbSwing, timeLimbSwing, yaw, pitch, scale);
                    }
                    ++p;
                }
            }
            ++i;
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

